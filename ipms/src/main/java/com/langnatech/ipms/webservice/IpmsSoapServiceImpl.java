package com.langnatech.ipms.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "IpmsService", endpointInterface = "com.langnatech.ipms.webservice.IpmsSoapService")
//@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IpmsSoapServiceImpl implements IpmsSoapService {

	public CallResult call(
			@WebParam(name = "authenticator") Authenticator authenticator,
			@WebParam(name = "serviceCode") String serviceCode,
			@WebParam(name = "arguments") String arguments) {
		return null;
	}
}
