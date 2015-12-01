package com.langnatech.ipms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.ipms.IPConstant;
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

  public ApplyResultBean availableQuery(String poolId, String cityId, Integer ipCount) {
    return assignSubnet(poolId, cityId, ipCount, false);
  }

  public ApplyResultBean assignIpSubnet(String poolId, String cityId, Integer ipCount) {
    return assignSubnet(poolId, cityId, ipCount, true);
  }

  private ApplyResultBean assignSubnet(String poolId, String cityId, Integer ipCount,
      boolean isAssign) {
    int ipC = correctIpCount(ipCount);
    SubNetResEntity subnetEntity = this.subNetResDao.selectUsableSubnets(ipC - 2, poolId, cityId);
    if (subnetEntity == null) {
      return null;
    }
    int usableIpCount = subnetEntity.getIpCount() + 2;
    ApplyResultBean resultBean = null;
    // 无需拆分，直接返回子网
    if (ipC == usableIpCount || (ipC < usableIpCount && usableIpCount < ipC * 2)) {
      if (isAssign) {
        subnetEntity.setUseStatus(SubNetUseStatusEnum.RESERVE.getCode());
        subnetEntity.setOperateTime(DateTime.now().toDate());
        subnetEntity.setOperator(SecurityContextHolder.getLoginName());
        this.subNetResDao.updateSubnet(subnetEntity);
      }
      resultBean = getResultBeanBySubnet(subnetEntity);
      // 拆分后返回子网
    } else {
      List<SubNetResEntity> subnetList = getSplitSubnet(ipC, subnetEntity, resultBean);
      SubNetResEntity assignSubnet = subnetList.get(subnetList.size() - 1);
      assignSubnet.setUseStatus(SubNetUseStatusEnum.RESERVE.getCode());
      if (isAssign) {
        //分配拆分后的最后一个子网，其他网段进行子网合并
        subnetList.remove(subnetList.size() - 1);
        subnetList = this.subnetMerge(subnetList);
        subnetList.add(assignSubnet);
        for (int i = 0; i < subnetList.size(); i++) {
          SubNetResEntity subnet = subnetList.get(i);
          subnet.setSubnetId(IDGeneratorHolder.getId());
          subnet.setOperateTime(DateTime.now().toDate());
          subnet.setOperator(SecurityContextHolder.getLoginName());
        }
        
        this.subNetResDao.insertBatchSubnet(subnetList);
      }
      resultBean = getResultBeanBySubnet(assignSubnet);
    }
    if (IPConstant.APPLY_CONTAIN_NET_ADDRESS) {
      resultBean.setApplyCount(ipCount + 2);
    } else {
      resultBean.setApplyCount(ipCount);
    }
    return resultBean;
  }

  private List<SubNetResEntity> getSplitSubnet(int ipC, SubNetResEntity subnetEntity,
      ApplyResultBean resultBean) {
    String subnetIp = IpUtils.getIpByDec(subnetEntity.getBeginIpDecimal() - 1);
    int splitMbits = IpUtils.getmBitsByHostNum(ipC - 2);
    List<String[]> splitSubnets =
        IpUtils.getSplitSubnet(subnetIp, subnetEntity.getIpCount(), splitMbits);
    List<SubNetResEntity> subnetList = new ArrayList<SubNetResEntity>();
    for (int j = 0; j < splitSubnets.size(); j++) {
      String[] subnet = splitSubnets.get(j);
      SubNetResEntity entity = new SubNetResEntity();
      entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
      entity.setSubnetDesc(IpUtils.getNetWorkIp(subnet[0], splitMbits) + "/" + splitMbits);
      entity.setSubnetPid(subnetEntity.getSubnetId());
      entity.setBeginIp(subnet[0]);
      entity.setEndIp(subnet[1]);
      entity.setBeginIpDecimal(IpUtils.getDecByIp(subnet[0]));
      entity.setEndIpDecimal(IpUtils.getDecByIp(subnet[1]));
      entity.setCityId(subnetEntity.getCityId());
      entity.setPlanStatus(SubNetPlanStatusEnum.ILLEGAL.getCode());
      entity.setPoolId(subnetEntity.getPoolId());
      entity.setIpCount(ipC - 2);
      entity.setIsIpv6(Short.valueOf("-1"));
      entity.setLft(1);
      entity.setMaskBits((short) splitMbits);
      entity.setNetmask(IpUtils.getMask(splitMbits));
      subnetList.add(entity);
    }
    return subnetList;
  }

  private ApplyResultBean getResultBeanBySubnet(SubNetResEntity subnetEntity) {
    ApplyResultBean resultBean = new ApplyResultBean();
    resultBean.setNetmask(subnetEntity.getNetmask());
    resultBean.setSubnetId(subnetEntity.getSubnetId());
    Long beginIp;
    int ipCount;
    if (IPConstant.APPLY_CONTAIN_NET_ADDRESS) {
      beginIp = subnetEntity.getBeginIpDecimal() - 1;
      ipCount = subnetEntity.getIpCount() + 2;
      resultBean.setStartIP(IpUtils.getIpByDec(beginIp));
      resultBean.setEndIP(IpUtils.getIpByDec(subnetEntity.getEndIpDecimal() + 1));
    } else {
      beginIp = subnetEntity.getBeginIpDecimal();
      ipCount = subnetEntity.getIpCount();
      resultBean.setStartIP(subnetEntity.getBeginIp());
      resultBean.setEndIP(subnetEntity.getEndIp());
    }
    resultBean.setIpCount(ipCount);
    List<String> ipList = new ArrayList<String>();
    for (int i = 0; i < ipCount; i++) {
      ipList.add(IpUtils.getIpByDec(beginIp + i));
    }
    resultBean.setIplist(ipList);
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

  public List<SubNetResEntity> subnetMerge(List<SubNetResEntity> list) {
    List<SubNetResEntity> resultList = new ArrayList<SubNetResEntity>();
    int preCount = 0;
    boolean continueMerge = false;
    for (int i = 0; i < list.size(); i += 2) {
      SubNetResEntity oddSubnet = list.get(i), evenSubnet = null;
      if ((i + 1) < list.size()) {
        evenSubnet = list.get(i + 1);
      }
      if (oddSubnet != null && evenSubnet != null
          && oddSubnet.getIpCount().intValue() == evenSubnet.getIpCount().intValue()) {
        oddSubnet.setBeginIp(oddSubnet.getBeginIp());
        oddSubnet.setEndIp(evenSubnet.getEndIp());
        oddSubnet.setBeginIpDecimal(oddSubnet.getBeginIpDecimal());
        oddSubnet.setEndIpDecimal(evenSubnet.getEndIpDecimal());
        oddSubnet.setIpCount(oddSubnet.getIpCount() + evenSubnet.getIpCount() + 2);
        oddSubnet.setMaskBits(Integer.valueOf(oddSubnet.getMaskBits() - 1).shortValue());
        oddSubnet.setNetmask(IpUtils.getMask(oddSubnet.getMaskBits()));
        oddSubnet.setSubnetDesc(
            IpUtils.getIpByDec(oddSubnet.getBeginIpDecimal() - 1) + "/" + oddSubnet.getMaskBits());
      }else{
        if(evenSubnet!=null){
          resultList.add(evenSubnet);
        }
      }
      if ((i/2)%2 == 0) {
        preCount = oddSubnet.getIpCount();
      } else {
        if (preCount == oddSubnet.getIpCount().intValue()) {
          continueMerge = true;
        }
        preCount = 0;
      }
      resultList.add(oddSubnet);
    }
    if (continueMerge) {
      return subnetMerge(resultList);
    }
    return resultList;
  }
}
