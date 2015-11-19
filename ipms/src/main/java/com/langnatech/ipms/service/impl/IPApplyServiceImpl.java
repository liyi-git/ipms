package com.langnatech.ipms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.PropertiesHolder;
import com.langnatech.ipms.entity.DimCityEntity;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.exception.IPApplyException;
import com.langnatech.ipms.service.DimCityService;
import com.langnatech.ipms.service.IPApplyService;
import com.langnatech.ipms.service.IPArchiveInfoService;
import com.langnatech.ipms.service.IPAssignService;
import com.langnatech.ipms.service.SubNetResService;
import com.langnatech.ipms.webservice.IPApplyServRespCode;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;

@Service
public class IPApplyServiceImpl implements IPApplyService {

  @Autowired
  private IPArchiveInfoService archiveInfoService;

  @Autowired
  private IPAssignService assignService;

  @Autowired
  private DimCityService cityService;

  private SubNetResService subnetService;

  @Override
  public ApplyResultBean availableQuery(String applyCity, Integer busiType, Integer ipCount)
      throws IPApplyException {
    String poolId = paramValidate(applyCity, busiType);
    return assignService.availableQuery(poolId, applyCity, ipCount);
  }

  @Override
  public ApplyResultBean applyReserveIP(ApplyInfoBean applyInfo) throws IPApplyException {
    ApplyResultBean resultBean = null;
    // 1.接口调用参数验证
    String poolId = paramValidate(applyInfo.getApplyCity(), applyInfo.getBusiType());
    // 2.备案信息校验
    try {
      archiveInfoService.validateArchiveInfo(applyInfo);
    } catch (Exception e) {
      throw new IPApplyException(IPApplyServRespCode.Validate_Error.getCode(), e.getMessage());
    }
    // 3.分配子网，标记网段为预留状态
    try {
      resultBean =
          assignService.assignIpSubnet(poolId, applyInfo.getApplyCity(), applyInfo.getIpCount());
    } catch (Exception e) {
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "分配网段出错！" + e.getMessage());
    }
    // 4.如分配子网成功，保存备案信息；如无可用IP，返回异常信息
    if (resultBean != null && CollectionUtils.isNotEmpty(resultBean.getIplist())) {
      IPArchiveInfoEntity archiveInfo = fillArchiveInfo(applyInfo, resultBean);
      try {
        archiveInfoService.saveArchiveInfo(archiveInfo);
      } catch (Exception e) {
        e.printStackTrace();
        throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
            "分配网段出错！保存备案信息出错！" + e.getMessage());
      }
    } else {
      throw new IPApplyException(IPApplyServRespCode.No_Available_IP);
    }
    return resultBean;
  }

  @Override
  public void applyCancel(String applyCode, String operator) throws IPApplyException {
    // 查询申请单号是否存在
    IPArchiveInfoEntity archiveInfo = archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo == null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    // 标记网段状态为空闲
    subnetService.updateSubnetUseStatus(archiveInfo.getSubnetId(),
        SubNetUseStatusEnum.AVAILABLE.getCode());
    // 备案信息标记为删除
    archiveInfoService.delArchiveInfo(applyCode);
  }

  @Override
  public void applyUse(String applyCode, String operator) throws IPApplyException {
    // 查询申请单号是否存在
    IPArchiveInfoEntity archiveInfo = archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo == null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    // 标记网段状态为已使用
    subnetService.updateSubnetUseStatus(archiveInfo.getSubnetId(),
        SubNetUseStatusEnum.USED.getCode());
  }

  @Override
  public void applyRecycle(String applyCode, String operator) throws IPApplyException {
    // 查询申请单号是否存在
    IPArchiveInfoEntity archiveInfo = archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo == null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    // 标记网段状态为空闲
    subnetService.updateSubnetUseStatus(archiveInfo.getSubnetId(),
        SubNetUseStatusEnum.AVAILABLE.getCode());
    // 备案信息标记为删除
    archiveInfoService.delArchiveInfo(applyCode);
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
      archiveInfo.setPoolId(PropertiesHolder.getProperty("POOL_ID_SPECIAL"));
    }
    if (applyInfo.getBusiType() == 2) {
      archiveInfo.setPoolId(PropertiesHolder.getProperty("POOL_ID_IDC"));
    }
    archiveInfo.setSubnetId(resultBean.getSubnetId());
    archiveInfo.setUseDate(null);
    archiveInfo.setUseType(applyInfo.getUseWay());
    return archiveInfo;
  }

  /**
   * 接口调用入参校验
   * 
   * @param applyInfo
   * @return
   * @throws IPApplyException
   */
  private String paramValidate(String applyCity, Integer busiType) throws IPApplyException {
    String poolId = null;
    if (busiType == 1) {// 专线地址
      poolId = PropertiesHolder.getProperty("POOL_ID_SPECIAL");
    }
    if (busiType == 2) {// IDC地址
      poolId = PropertiesHolder.getProperty("POOL_ID_IDC");
    }
    if (StringUtils.isEmpty(poolId)) {
      throw new IPApplyException(IPApplyServRespCode.Parameter_Error.getCode(),
          "接口调用传参不正确 , 申请业务类型参数不正确！[busiType="+busiType+"] ]");
    }
    DimCityEntity cityBean = cityService.getCityByCityId(applyCity);
    if (cityBean == null) {
      throw new IPApplyException(IPApplyServRespCode.Parameter_Error.getCode(),
          "接口调用传参不正确 ， 申请地市不存在！[applyCity="+applyCity+"] ]");
    }
    return poolId;
  }
}
