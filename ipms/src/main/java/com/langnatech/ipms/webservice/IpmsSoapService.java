package com.langnatech.ipms.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.langnatech.ipms.webservice.bean.CallResultBean;

@WebService(name = "IpmsService")
public interface IpmsSoapService {

  public CallResultBean call(@WebParam(name = "authenticator") Authenticator authenticator,
      @WebParam(name = "serviceCode") String serviceCode,
      @WebParam(name = "arguments") String arguments);
}
