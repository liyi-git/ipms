package com.langnatech.ipms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.service.IPAssignService;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;

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
		int i = 0;
		int ipC = 1;
		if (ipCount <= 0) {
			ipC = 1;
		} else {
			while ((ipC = (int) Math.pow(2, i)) <= ipCount) {
				i++;
			}
		}
		// 2.根据IP数量，查询地址池中等于该IP数量的空闲地址段
		List<SubNetResEntity> usableSubnets = this.subNetResDao.selectUsableSubnets(poolId, cityId);
		int targetIndex = binSearchSubnet(usableSubnets, ipC);
		// 3.如存在合适的地址段，则更新改地址段使用状态为已预留
		SubNetResEntity subnetEntity = new SubNetResEntity();
		if (-1 != targetIndex) {
			subnetEntity = usableSubnets.get(targetIndex);
			subnetEntity.setUseStatus(SubNetUseStatusEnum.RESERVE.getCode());
			this.subNetResDao.updateSubnet(subnetEntity);
		} else {
			targetIndex=binSearchSimilarSubnet(usableSubnets, ipC*2);
			subnetEntity = usableSubnets.get(targetIndex);
			int mbtis=subnetEntity.getMaskBits()+1;
			
//			if(mbits<30){
//				subnetEntity.setUseStatus(SubNetUseStatusEnum.RESERVE.getCode());
//			}else{
//				//没网段了
//			}
			
		}
		// 4.如不存在，则找一个大地址段进行拆分，并设置使用状态为已预留
		ApplyResultBean resultBean = new ApplyResultBean();
		resultBean.setStartIP("111.20.40.1");
		resultBean.setEndIP("111.20.40.6");
		resultBean.setNetmask("255.255.255.248");
		resultBean.setIpCount(6);
		resultBean.setIplist(Lists.newArrayList("111.20.40.1", "111.20.40.2", "111.20.40.3", "111.20.40.4",
				"111.20.40.5", "111.20.40.6"));
		return resultBean;
	}

	/**
	 * 查找指定值
	 * 
	 * @param subnetList
	 * @param data
	 * @return 查找不到返回-1
	 */
	public static int binSearchSubnet(List<SubNetResEntity> subnetList, int key) {
		int beginIndex = 0; // 定义起始位置
		int endIndex = subnetList.size() - 1; // 定义结束位置
		int midIndex = -1; // 定义中点
		// 用二分法查找的数据必须是排好序的，因此只要比较第一个元素和最后一个元素就可以确定所查找的数据是否在数组中
		if (key < subnetList.get(beginIndex).getIpCount() || key > subnetList.get(endIndex).getIpCount()
				|| beginIndex > endIndex) {
			return -1;
		}
		while (beginIndex <= endIndex) {
			midIndex = (beginIndex + endIndex) / 2;// 初始化中点
			if (key < subnetList.get(midIndex).getIpCount()) {
				endIndex = midIndex - 1; // 如果查找的数据小于中点位置的数据，则把查找的结束位置定义在中点
			} else if (key > subnetList.get(midIndex).getIpCount()) { 
				beginIndex = midIndex + 1;
			} else {
				return midIndex; // 返回查找到的数据的位置
			}
		}
		return -1;
	}

	/**
	 * 查找相近的值
	 * 
	 * @param subnetList
	 * @param key
	 * @return
	 */
	public static Integer binSearchSimilarSubnet(List<SubNetResEntity> subnetList, int key) {
		int diffNum = Math.abs(subnetList.get(0).getIpCount() - key);
		@SuppressWarnings("unused")
		int result = subnetList.get(0).getIpCount();
		int index = 0;
		for (int i = 0; i < subnetList.size(); i++) {
			int diffNumTemp = Math.abs(subnetList.get(0).getIpCount() - key);
			if (diffNumTemp < diffNum) {
				diffNum = diffNumTemp;
				result = subnetList.get(i).getIpCount();
				index = i;
			} else {
				continue;
			}
		}
		return index;
	}

}