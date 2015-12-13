package com.langnatech.ipms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.PropertiesHolder;
import com.langnatech.ipms.IPConstant;
import com.langnatech.ipms.bean.SubNetResBean;
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

  @Autowired
  private SubNetResService subnetService;

  @Override
  public ApplyResultBean availableQuery(String applyCity, Integer busiType, Integer ipCount)
      throws IPApplyException {
    String poolId = paramValidate(applyCity, busiType);
    ApplyResultBean resultBean = assignService.availableQuery(poolId, applyCity, ipCount - 2);
    if (resultBean == null || CollectionUtils.isEmpty(resultBean.getIplist())) {
      throw new IPApplyException(IPApplyServRespCode.No_Available_IP);
    }
    return resultBean;
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
      e.printStackTrace();
      throw new IPApplyException(IPApplyServRespCode.Validate_Error.getCode(), e.getMessage());
    }
    // 3.分配子网，标记网段为预留状态
    try {
      if (IPConstant.APPLY_CONTAIN_NET_ADDRESS) {
        applyInfo.setIpCount(applyInfo.getIpCount() - 2);
      }
      resultBean =
          assignService.assignIpSubnet(poolId, applyInfo.getApplyCity(), applyInfo.getIpCount());

    } catch (Exception e) {
      e.printStackTrace();
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "分配网段出错！" + e.getMessage());
    }
    // 4.如分配子网成功，保存备案信息；如无可用IP，返回异常信息
    if (resultBean != null && CollectionUtils.isNotEmpty(resultBean.getIplist())) {
      IPArchiveInfoEntity archiveInfo = fillArchiveInfo(poolId, applyInfo, resultBean);
      archiveInfo.setSubnetId(resultBean.getSubnetId());
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
  public ApplyResultBean applyCancel(String applyCode, String operator) throws IPApplyException {
    ApplyResultBean resultBean = null;
    // 查询申请单号是否存在
    IPArchiveInfoEntity archiveInfo = archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo == null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    SubNetResBean subnet = subnetService.selectSubnetById(archiveInfo.getSubnetId());
    if (subnet == null) {
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "申请取消的网段在ip系统中不存在，网段编码：" + archiveInfo.getSubnetId());
    }
    // ip状态为预留时，才可取消申请单
    if (subnet.getUseStatus().intValue() != SubNetUseStatusEnum.RESERVE.getCode().intValue()) {
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "已经申请预留的地址段，才可以申请取消！网段状态：" + SubNetUseStatusEnum.getNameByValue(subnet.getUseStatus()));
    }
    // 标记网段状态为空闲
    subnetService.updateSubnetUseStatus(archiveInfo.getSubnetId(),
        SubNetUseStatusEnum.AVAILABLE.getCode());
    resultBean = new ApplyResultBean();
    resultBean.setIpCount(subnet.getIpCount());
    resultBean.setPoolId(subnet.getPoolId());
    resultBean.setCityId(subnet.getCityId());
    resultBean.setSubnetDesc(subnet.getSubnetDesc());
    resultBean.setSubnetId(subnet.getSubnetId());
    // 备案信息标记为删除
    archiveInfoService.delArchiveInfo(applyCode);
    return resultBean;
  }

  @Override
  public ApplyResultBean applyUse(String applyCode, String operator) throws IPApplyException {
    ApplyResultBean resultBean = null;
    // 查询申请单号是否存在
    IPArchiveInfoEntity archiveInfo = archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo == null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    SubNetResBean subnet = subnetService.selectSubnetById(archiveInfo.getSubnetId());
    if (subnet == null) {
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "申请开通的网段在ip系统中不存在，网段编码：" + archiveInfo.getSubnetId());
    }
    // 标记网段状态为已使用
    subnetService.updateSubnetUseStatus(archiveInfo.getSubnetId(),
        SubNetUseStatusEnum.USED.getCode());
    resultBean = new ApplyResultBean();
    resultBean.setIpCount(subnet.getIpCount());
    resultBean.setPoolId(subnet.getPoolId());
    resultBean.setCityId(subnet.getCityId());
    resultBean.setSubnetDesc(subnet.getSubnetDesc());
    resultBean.setSubnetId(subnet.getSubnetId());
    return resultBean;
  }

  @Override
  public ApplyResultBean applyRecycle(String applyCode, String operator) throws IPApplyException {
    ApplyResultBean resultBean = null;
    // 查询申请单号是否存在
    IPArchiveInfoEntity archiveInfo = archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo == null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Not_Exist);
    }
    SubNetResBean subnet = subnetService.selectSubnetById(archiveInfo.getSubnetId());
    if (subnet == null) {
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "申请开通的网段在ip系统中不存在，网段编码：" + archiveInfo.getSubnetId());
    }
    if (subnet.getUseStatus().intValue() != SubNetUseStatusEnum.USED.getCode().intValue()) {
      throw new IPApplyException(IPApplyServRespCode.Invoke_Error.getCode(),
          "已经开通使用的地址段，才可以被回收！网段状态：" + SubNetUseStatusEnum.getNameByValue(subnet.getUseStatus()));
    }
    // 标记网段状态为空闲
    subnetService.updateSubnetUseStatus(archiveInfo.getSubnetId(),
        SubNetUseStatusEnum.AVAILABLE.getCode());
    // 备案信息标记为删除
    archiveInfoService.delArchiveInfo(applyCode);
    resultBean = new ApplyResultBean();
    resultBean.setIpCount(subnet.getIpCount());
    resultBean.setPoolId(subnet.getPoolId());
    resultBean.setCityId(subnet.getCityId());
    resultBean.setSubnetDesc(subnet.getSubnetDesc());
    resultBean.setSubnetId(subnet.getSubnetId());
    return resultBean;
  }

  private IPArchiveInfoEntity fillArchiveInfo(String poolId, ApplyInfoBean applyInfo,
      ApplyResultBean resultBean) {
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
    archiveInfo.setPoolId(poolId);
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
    if (busiType == 11) {
      poolId = PropertiesHolder.getProperty("POOL_ID_SPECIAL");
    } else if (busiType == 12) {
      poolId = PropertiesHolder.getProperty("POOL_ID_IDC");
    } else if (busiType == 21) {
      poolId = PropertiesHolder.getProperty("POOL_ID_HOME");
    } else if (busiType == 22) {
      poolId = PropertiesHolder.getProperty("POOL_ID_WLAN");
    }
    if (StringUtils.isEmpty(poolId)) {
      throw new IPApplyException(IPApplyServRespCode.Parameter_Error.getCode(),
          "接口调用传参不正确 , 申请业务类型参数不正确！[busiType=" + busiType + "] ]");
    }
    DimCityEntity cityBean = cityService.getCityByCityId(applyCity);
    if (cityBean == null) {
      throw new IPApplyException(IPApplyServRespCode.Parameter_Error.getCode(),
          "接口调用传参不正确 ， 申请地市不存在！[applyCity=" + applyCity + "] ]");
    }
    return poolId;
  }

  @Override
  public void validateApplyHasExist(String applyCode) throws IPApplyException{
    IPArchiveInfoEntity archiveInfo = this.archiveInfoService.getArchiveInfoByApplyCode(applyCode);
    if (archiveInfo != null) {
      throw new IPApplyException(IPApplyServRespCode.ApplyCode_Duplicate.getCode(),
          "申请单号["+applyCode+"]已存在，不允许重复申请！");
    }      
  }

}
