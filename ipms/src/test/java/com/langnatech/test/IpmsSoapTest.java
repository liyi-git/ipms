package com.langnatech.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.joda.time.DateTime;
import org.junit.Test;

import com.langnatech.ipms.webservice.Authenticator;
import com.langnatech.ipms.webservice.IpmsSoapService;
import com.langnatech.ipms.webservice.bean.ApplyInfoBean;
import com.langnatech.ipms.webservice.bean.CallResultBean;
import com.langnatech.util.convert.JsonConvertUtil;

import junit.framework.TestCase;

public class IpmsSoapTest extends TestCase {
  private IpmsSoapService service;

  protected void setUp() throws Exception {
    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
    factory.setServiceClass(IpmsSoapService.class);
    factory.setAddress("http://localhost:8080/ipms/soap/ipmsservice");
    service = (IpmsSoapService) factory.create();
  }

  @Test
  public void testAuthc() {
    String username = "ipms";
    String password = DigestUtils.md5Hex("ipms$@#PWD");
    String timestamp = DateTime.now().toString("yyyyMMddHHmmssSSS");
    String auth = DigestUtils.md5Hex(username + timestamp + password);
    Authenticator authenticator = new Authenticator(username, timestamp, auth);
    service.call(authenticator, "test", "{name:\"ipms\"}");
  }

  @Test
  public void testQuery() {
    CallResultBean callResult = service.call(null, "IP_AVAILABLE_QUERY",
        "{\"applyCity\":\"2222\",\"ipCount\":\"20\",\"busiType\":\"101\"}");
    System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  }

  @Test
  public void testAdd() {
    CallResultBean callResult = service.call(null, "IP_APPLY_ADD",
        JsonConvertUtil.nonEmptyMapper().toJson(new ApplyInfoBean()));
    System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  }

  @Test
  public void testCancel() {
    CallResultBean callResult =
        service.call(null, "IP_APPLY_CANCEL", "{\"applyCode\":\"111\",\"operator\":\"ADMIN\"}");
    System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  }


  @Test
  public void testUse() {
    CallResultBean callResult =
        service.call(null, "IP_APPLY_USE", "{\"applyCode\":\"111\",\"operator\":\"ADMIN\"}");
    System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  }

  @Test
  public void testRecycle() {
    CallResultBean callResult =
        service.call(null, "IP_APPLY_RECYCLE", "{\"applyCode\":\"111\",\"operator\":\"ADMIN\"}");
    System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  }
}
