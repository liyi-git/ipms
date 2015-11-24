package com.langnatech.ipms.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.langnatech.ipms.exception.IPApplyException;
import com.langnatech.ipms.service.IPApplyService;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;
import com.langnatech.ipms.webservice.bean.ApplyResultBean;
import com.langnatech.ipms.webservice.bean.CallResultBean;
import com.langnatech.util.convert.JsonConvertUtil;


@WebService(serviceName = "IpmsService",
    endpointInterface = "com.langnatech.ipms.webservice.IpmsSoapService")
// @Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IpmsSoapServiceImpl implements IpmsSoapService {
  @Autowired
  private IPApplyService ipApplyService;
  private static final Logger logger = LoggerFactory.getLogger(IpmsSoapServiceImpl.class);

  public CallResultBean call(@WebParam(name = "authenticator") Authenticator authenticator,
      @WebParam(name = "serviceCode") String serviceCode,
      @WebParam(name = "arguments") String arguments) {
    CallResultBean callResultBean = new CallResultBean(IPApplyServRespCode.Success.getCode(),
        IPApplyServRespCode.Success.getDesc());
    String resultJSON = "{}";
    try {
      String interfaceCode = serviceCode.toUpperCase();
      // TODO 接口调用鉴权
      ApplyInfoBean applyInfo =
          JsonConvertUtil.nonDefaultMapper().fromJson(arguments, ApplyInfoBean.class);
      if (interfaceCode.equals("IP_APPLY_ADD")) {// 申请IP地址段
        logger.debug(
            "::: Webservice Invoke ::: Apply ip subnet , Apply city：[{}] Business type：[{}] Apply ip count：[{}]",
            new Object[] {applyInfo.getApplyCity(), applyInfo.getBusiType(),
                applyInfo.getIpCount()});
        ApplyResultBean applyResult = ipApplyService.applyReserveIP(applyInfo);
        resultJSON = JsonConvertUtil.nonEmptyMapper().toJson(applyResult);
      } else if (interfaceCode.equals("IP_AVAILABLE_QUERY")) {// 可用IP查询
        logger.debug(
            "::: WSInvoke ::: Query available ip , Apply city：[{}] Business type：[{}] Apply ip count：[{}]",
            new Object[] {applyInfo.getApplyCity(), applyInfo.getBusiType(),
                applyInfo.getIpCount()});
        ApplyResultBean applyResult = ipApplyService.availableQuery(applyInfo.getApplyCity(),
            applyInfo.getBusiType(), applyInfo.getIpCount());
        if (applyResult == null) {
          callResultBean.setCode(IPApplyServRespCode.No_Available_IP.getCode());
          callResultBean.setMsg(IPApplyServRespCode.No_Available_IP.getDesc());
        } else {
          resultJSON = JsonConvertUtil.nonEmptyMapper().toJson(applyResult);
        }
      } else if (interfaceCode.equals("IP_APPLY_CANCEL")) {// 取消IP申请
        logger.debug("::: WSInvoke ::: Cancel ip apply ,Apply code:[{}]", applyInfo.getApplyCode());
        ipApplyService.applyCancel(applyInfo.getApplyCode(), applyInfo.getOperator());
      } else if (interfaceCode.equals("IP_APPLY_USE")) {// IP申请开通
        logger.debug("::: WSInvoke ::: Use ip apply ,Apply code:[{}]", applyInfo.getApplyCode());
        ipApplyService.applyUse(applyInfo.getApplyCode(), applyInfo.getOperator());
      } else if (interfaceCode.equals("IP_APPLY_RECYCLE")) {// IP回收
        logger.debug("::: WSInvoke ::: Recycle ip apply ,Apply code:[{}]",
            applyInfo.getApplyCode());
        ipApplyService.applyRecycle(applyInfo.getApplyCode(), applyInfo.getOperator());
      }
    } catch (IPApplyException e) {
      callResultBean.setCode(e.getErrCode());
      callResultBean.setMsg(e.getErrDesc());
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
      callResultBean.setCode(IPApplyServRespCode.Invoke_Error.getCode());
      callResultBean
          .setMsg(IPApplyServRespCode.Invoke_Error.getDesc() + " ,异常信息:" + e.getMessage());
    }
    callResultBean.setResultJSON(resultJSON);
    Object[] params =
        {callResultBean.getCode(), callResultBean.getMsg(), callResultBean.getResultJSON()};
    logger.debug("::: WSInvoke Result::: Resond Code:[{}],Resond Desc:[{}],ResultJson:[{}]",
        params);
    return callResultBean;
  }
}
