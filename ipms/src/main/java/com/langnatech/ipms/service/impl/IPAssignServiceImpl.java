package com.langnatech.ipms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.service.IPAssignService;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;
import com.langnatech.util.IpUtils;

@Service
public class IPAssignServiceImpl implements IPAssignService {

	@Autowired
	private SubNetResDao subNetResDao;

	// TODO 可用IP查询
	public ApplyResultBean availableQuery(String poolId, String cityId, Integer ipCount) {
		ApplyResultBean resultBean = new ApplyResultBean();
		resultBean.setStartIP("111.20.40.1");
		resultBean.setEndIP("111.20.40.6");
		resultBean.setNetmask("255.255.255.248");
		resultBean.setIpCount(6);
		resultBean.setIplist(Lists.newArrayList("111.20.40.1", "111.20.40.2", "111.20.40.3", "111.20.40.4",
				"111.20.40.5", "111.20.40.6"));
		return resultBean;
	}

	// TODO 分配IP子网,子网状态为预留状态
	public ApplyResultBean assignIpSubnet(String poolId, String cityId, Integer ipCount) throws Exception {
		// 1.修正IP数量(修正为最小偶数)
		int ipC = correctIpCount(ipCount);
		// 2.根据IP数量，查询地址池中等于该IP数量的空闲地址段
		String subnetId = this.subNetResDao.selectUsableSubnets(ipC-2, poolId, cityId);
		ApplyResultBean resultBean = new ApplyResultBean();
		if (null != subnetId) {
			SubNetResEntity subnetEntity = this.subNetResDao.selectSubnetById(subnetId);
			int usableIpCount = subnetEntity.getIpCount() + 2;
			if (ipC == usableIpCount || (ipC < usableIpCount && usableIpCount <= ipC * 2)) {
				subnetEntity.setUseStatus(SubNetUseStatusEnum.RESERVE.getCode());
				this.subNetResDao.updateSubnet(subnetEntity);
				resultBean.setStartIP(subnetEntity.getBeginIp());
				resultBean.setEndIP(subnetEntity.getEndIp());
				resultBean.setNetmask(subnetEntity.getNetmask());
				resultBean.setIpCount(ipC);
				List<String> ipList = new ArrayList<String>();
				Long startIpDec = subnetEntity.getBeginIpDecimal();
				while (startIpDec <= subnetEntity.getEndIpDecimal()) {
					ipList.add(IpUtils.getIpByDec(startIpDec));
					startIpDec = startIpDec + 1;
				}
				resultBean.setIplist(ipList);
			} else if (usableIpCount > ipC * 2) {
				String subnetIp = subnetEntity.getSubnetDesc();
				subnetIp = subnetIp.substring(0, subnetIp.indexOf('/'));
				// int splitCount=(int)Math.ceil(usableIpCount/ipC);
				int splitMbits = IpUtils.getmBitsByHostNum(subnetIp, ipC);
				List<String[]> splitSubnets = IpUtils.getSplitSubnet(subnetIp, subnetEntity.getIpCount(), splitMbits);
				List<SubNetResEntity> subnetList = new ArrayList<SubNetResEntity>();
				for (int j = 0; j < splitSubnets.size(); j++) {
					String[] subnet = splitSubnets.get(j);
					SubNetResEntity entity = new SubNetResEntity();
					String beginAddr = subnet[0];
					String endAddr = subnet[1];
					Long beginDec = IpUtils.getDecByIp(beginAddr);
					Long endDec = IpUtils.getDecByIp(endAddr);
					if (j == 0) {
						resultBean.setStartIP(subnet[0]);
						resultBean.setEndIP(subnet[1]);
						resultBean.setNetmask(IpUtils.getMask(splitMbits));
						resultBean.setIpCount(ipC);
						List<String> ipList = new ArrayList<String>();
						Long startIpDec = beginDec;
						while (startIpDec <= endDec) {
							ipList.add(IpUtils.getIpByDec(startIpDec));
							startIpDec = startIpDec + 1;
						}
						resultBean.setIplist(ipList);
						entity.setUseStatus(SubNetUseStatusEnum.RESERVE.getCode());
					} else {
						entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
					}
					entity.setSubnetId(IDGeneratorHolder.getId());
					entity.setSubnetDesc(IpUtils.getNetWorkIp(beginAddr, splitMbits) + "/" + splitMbits);
					entity.setSubnetPid(subnetId);
					entity.setBeginIp(beginAddr);
					entity.setEndIp(endAddr);
					entity.setBeginIpDecimal(beginDec);
					entity.setEndIpDecimal(endDec);
					entity.setCityId(subnetEntity.getCityId());
					entity.setPlanStatus(subnetEntity.getPlanStatus());
					entity.setPoolId(subnetEntity.getPoolId());
					entity.setIpCount(ipC-2);
					entity.setIsIpv6(Short.valueOf("-1"));
					entity.setLft(1);
					entity.setMaskBits((short) splitMbits);
					entity.setNetmask(IpUtils.getMask(splitMbits));
					entity.setOperator(SecurityContextHolder.getLoginName());
					entity.setOperateTime(DateTime.now().toDate());
					subnetList.add(entity);
				}
				subnetEntity.setPlanStatus(SubNetPlanStatusEnum.ILLEGAL.getCode());
				
				subnetEntity.setOperator(SecurityContextHolder.getLoginName());
				subnetEntity.setOperateTime(DateTime.now().toDate());
				this.subNetResDao.updateSubnet(subnetEntity);
				this.insertBatchSubnet(subnetList);
			}
		}

		return resultBean;
	}

	private int correctIpCount(Integer ipCount) {
		ipCount += 2;
		int i = 0;
		int ipC = 1;
		if (ipCount > 0 & (ipCount & (ipCount - 1)) == 0) {
			ipC = ipCount;
		} else {
			if (ipCount <= 0) {
				ipC = 1;
			} else {
				while ((ipC = (int) Math.pow(2, i)) <= ipCount) {
					i++;
				}
			}
		}
		return ipC;
	}

	public void insertBatchSubnet(List<SubNetResEntity> subnetList) {
		this.subNetResDao.insertBatchSubnet(subnetList);
	}

}
