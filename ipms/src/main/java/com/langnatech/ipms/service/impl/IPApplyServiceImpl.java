package com.langnatech.ipms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.exception.IPApplyException;
import com.langnatech.ipms.service.IPApplyService;
import com.langnatech.ipms.service.IPArchiveInfoService;
import com.langnatech.ipms.webservice.IPApplyServRespCode;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;

@Service
public class IPApplyServiceImpl implements IPApplyService {

  @Autowired
  private IPArchiveInfoService archiveInfoService;

  @Override
  public ApplyResultBean availableQuery(String applyCity, Integer busiType, Integer IpCount)
      throws IPApplyException {
    ApplyResultBean resultBean = new ApplyResultBean();
    // TODO 实现查询可用IP的逻辑，如果无可用IP，返回null
    resultBean.setStartIP("111.20.40.1");
    resultBean.setEndIP("111.20.40.6");
    resultBean.setNetmask("255.255.255.248");
    resultBean.setIpCount(6);
    resultBean.setIplist(Lists.newArrayList("111.20.40.1", "111.20.40.2", "111.20.40.3",
        "111.20.40.4", "111.20.40.5", "111.20.40.6"));
    return resultBean;
  }

  @Override
  public ApplyResultBean applyReserveIP(ApplyInfoBean applyInfo) throws IPApplyException {
    // TODO 申请备案信息校验
    String validateErr = validateArchiveInfo(applyInfo);
    if (StringUtils.isNotEmpty(validateErr)) {
      throw new IPApplyException(IPApplyServRespCode.Validate_Error.getCode(), validateErr);
    }
    // TODO 根据申请ip数量，验证是否有可用ip，拆分子网并更新为预留状态
    ApplyResultBean resultBean = null;
    try {
      resultBean = reserveSubnet(applyInfo);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "动态拆分网段出错！" + e.getMessage());
    }
    // TODO 备案信息入库
    if (resultBean != null) {
      IPArchiveInfoEntity archiveInfo = fillArchiveInfo(applyInfo, resultBean);
      int flag = archiveInfoService.saveArchiveInfo(archiveInfo);
      if (flag == 1) {
        return resultBean;
      }
    } else {
      throw new IPApplyException(IPApplyServRespCode.No_Available_IP);
    }
    
    
    //TODO 临时代替
    resultBean.setStartIP("111.20.40.1");
    resultBean.setEndIP("111.20.40.6");
    resultBean.setNetmask("255.255.255.248");
    resultBean.setIpCount(6);
    return resultBean;
  }

  @Override
  public void applyCancel(String applyCode, String operator) throws IPApplyException {
    // TODO 判断申请单号是否存在
    boolean isExist = isExistApplyCode(applyCode);
    if (!isExist) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    // TODO 取消申请单，标记网段状态为空闲，备案信息标记为删除
  }

  @Override
  public void applyUse(String applyCode, String operator) throws IPApplyException {
    // TODO 判断申请单号是否存在
    boolean isExist = isExistApplyCode(applyCode);
    if (!isExist) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    // TODO 更新网段状态为已使用
  }

  @Override
  public void applyRecycle(String applyCode, String operator) throws IPApplyException {
    // TODO 判断申请单号是否存在
    boolean isExist = isExistApplyCode(applyCode);
    if (!isExist) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    // TODO 更新网段状态为空闲，备案信息标记为删除
  }

  private IPArchiveInfoEntity fillArchiveInfo(ApplyInfoBean applyInfo, ApplyResultBean resultBean) {
    IPArchiveInfoEntity archiveInfo = new IPArchiveInfoEntity();
    archiveInfo.setApplicant(applyInfo.getOperator());
    archiveInfo.setApplyId(applyInfo.getApplyCode());
    archiveInfo.setApplyReason(applyInfo.getApplyDesc());
    archiveInfo.setApplyTime(DateTime.now().toDate());
    archiveInfo.setArchiveId(IDGeneratorHolder.getId());
    archiveInfo.setAreaId(applyInfo.getApplyCity());
    archiveInfo.setBeginIp(resultBean.getStartIP());
    archiveInfo.setContactEmail(resultBean.getEndIP());
    archiveInfo.setContactPhone(applyInfo.getContactTel());
    archiveInfo.setEndIp(resultBean.getEndIP());
    archiveInfo.setGatewayIp(resultBean.getGatewayIp());
    archiveInfo.setGatewayPlace(applyInfo.getGatewayLocation());
    archiveInfo.setInvalidDate(applyInfo.getExpiredDate());
    archiveInfo.setIpCount(resultBean.getIpCount());
    archiveInfo.setLicenceCode(applyInfo.getCoLicense());
    archiveInfo.setLinkman(applyInfo.getContact());
    archiveInfo.setOrgAddress(applyInfo.getCoAddress());
    archiveInfo.setOrgCityId(applyInfo.getCoCity());
    archiveInfo.setOrgNature(applyInfo.getCoNature());
    archiveInfo.setOrgName(applyInfo.getCoName());
    archiveInfo.setOrgNature(applyInfo.getCoNature());
    archiveInfo.setOrgProvId(applyInfo.getCoProvince());
    archiveInfo.setOrgTrade(applyInfo.getCoIndustry());
    archiveInfo.setOrgType(applyInfo.getCoClassify());
    if (applyInfo.getBusiType() == 1) {
      archiveInfo.setPoolId("");// TODO 根据地址编码，修改为专线地址池编码
    }
    if (applyInfo.getBusiType() == 2) {
      archiveInfo.setPoolId("");// TODO 根据地址编码，修改为IDC地址池编码
    }
    archiveInfo.setSubnetId(resultBean.getSubnetId());
    archiveInfo.setUseDate(null);
    archiveInfo.setUseType(applyInfo.getUseWay());
    return archiveInfo;
  }

  // TODO 根据申请ip数量，验证是否有可用ip，拆分子网并更新为预留状态
  private ApplyResultBean reserveSubnet(ApplyInfoBean applyInfoBean) throws Exception {
    return null;
  }

  // TODO 校验备案信息
  private String validateArchiveInfo(ApplyInfoBean applyInfoBean) {
    return null;
  }

  // TODO 判断申请单号是否存在
  private boolean isExistApplyCode(String applyCode) {
    return true;
  }
}
