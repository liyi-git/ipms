package com.langnatech.ipms.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "IpmsService")
public interface IpmsSoapService {

	public CallResult call(
			@WebParam(name = "authenticator") Authenticator authenticator,
			@WebParam(name = "serviceCode") String serviceCode,
			@WebParam(name = "arguments") String arguments);
}
