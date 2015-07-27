package com.langnatech.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.joda.time.DateTime;
import org.junit.Test;

import com.langnatech.ipms.webservice.Authenticator;
import com.langnatech.ipms.webservice.IpmsSoapService;

public class IpmsSoapTest {

	@Test
	public void test() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IpmsSoapService.class);
		factory.setAddress("http://localhost:8080/soap/ipmsservice");
		IpmsSoapService service = (IpmsSoapService) factory.create();
		String username = "ipms";
		String password = DigestUtils.md5Hex("ipms$@#PWD");
		String timestamp = DateTime.now().toString("yyyyMMddHHmmssSSS");
		String auth = DigestUtils.md5Hex(username + timestamp + password);
		Authenticator authenticator = new Authenticator(username,timestamp,auth);
		service.call(authenticator, "test", "{name:\"ipms\"}");
	}

}
