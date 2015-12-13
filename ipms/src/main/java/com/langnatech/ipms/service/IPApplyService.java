package com.langnatech.ipms.service;

import com.langnatech.ipms.exception.IPApplyException;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;


public interface IPApplyService {

  /**
   * 可用IP查询
   * 
   * @param busiType 业务类型
   * @param IpCount ip数量
   * @return 无可用ip返回Null
   */
  public ApplyResultBean availableQuery(String applyCity, Integer busiType, Integer IpCount)
      throws IPApplyException;

  /**
   * IP地址段申请预留
   * 
   * @param archiveInfo
   * @return
   */
  public ApplyResultBean applyReserveIP(ApplyInfoBean applyInfo) throws IPApplyException;

  /**
   * IP地址申请单取消
   * 
   * @param applyCode
   * @param operator
   */
  public ApplyResultBean applyCancel(String applyCode, String operator) throws IPApplyException;

  /**
   * IP地址申请单申请开通
   * 
   * @param applyCode
   * @param operator
   */
  public ApplyResultBean applyUse(String applyCode, String operator) throws IPApplyException;

  /**
   * IP地址申请单申请取消
   * 
   * @param applyCode
   * @param operator
   */
  public ApplyResultBean applyRecycle(String applyCode, String operator) throws IPApplyException;

  public void validateApplyHasExist(String applyCode) throws IPApplyException;
}
