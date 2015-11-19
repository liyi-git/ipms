package com.langnatech.ipms.service.impl;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.langnatech.ipms.service.IPAssignService;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;

@Service
public class IPAssignServiceImpl implements IPAssignService {

  // TODO 可用IP查询
  public ApplyResultBean availableQuery(String poolId, String cityId, Integer ipCount) {
    ApplyResultBean resultBean = new ApplyResultBean();
    resultBean.setStartIP("111.20.40.1");
    resultBean.setEndIP("111.20.40.6");
    resultBean.setNetmask("255.255.255.248");
    resultBean.setIpCount(6);
    resultBean.setIplist(Lists.newArrayList("111.20.40.1", "111.20.40.2", "111.20.40.3",
        "111.20.40.4", "111.20.40.5", "111.20.40.6"));
    return resultBean;
  }

  // TODO 分配IP子网,子网状态为预留状态
  public ApplyResultBean assignIpSubnet(String poolId, String cityId, Integer ipCount)
      throws Exception {
    //1.修正IP数量
    //2.根据IP数量，查询地址池中等于该IP数量的空闲地址段
    //3.如存在合适的地址段，则更新改地址段使用状态为已预留
    //4.如不存在，则找一个大地址段进行拆分，并设置使用状态为已预留
    ApplyResultBean resultBean = new ApplyResultBean();
    resultBean.setStartIP("111.20.40.1");
    resultBean.setEndIP("111.20.40.6");
    resultBean.setNetmask("255.255.255.248");
    resultBean.setIpCount(6);
    resultBean.setIplist(Lists.newArrayList("111.20.40.1", "111.20.40.2", "111.20.40.3",
        "111.20.40.4", "111.20.40.5", "111.20.40.6"));    
    return resultBean;
  }
}
