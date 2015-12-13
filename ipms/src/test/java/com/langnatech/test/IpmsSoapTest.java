package com.langnatech.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

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

  // @Test
  // public void testAuthc() {
  // String username = "ipms";
  // String password = DigestUtils.md5Hex("ipms$@#PWD");
  // String timestamp = DateTime.now().toString("yyyyMMddHHmmssSSS");
  // String auth = DigestUtils.md5Hex(username + timestamp + password);
  // Authenticator authenticator = new Authenticator(username, timestamp, auth);
  // service.call(authenticator, "test", "{\"name\":\"ipms\"}");
  // }

  // @Test
  // public void testQuery() {
  // // 申请业务类型参数不正确
  // CallResultBean callResult = service.call(null, "IP_AVAILABLE_QUERY",
  // "{\"applyCity\":\"101\",\"ipCount\":\"8\",\"busiType\":\"111\"}");
  // Assert.assertSame("可用IP查询接口，接口调用传入申请业务类型参数不正确", IPApplyServRespCode.Parameter_Error.getCode(),
  // callResult.getCode());
  // System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  // // 申请地市不存在
  // callResult = service.call(null, "IP_AVAILABLE_QUERY",
  // "{\"applyCity\":\"2222\",\"ipCount\":\"8\",\"busiType\":\"1\"}");
  // Assert.assertSame("可用IP查询接口，接口调用传入申请业务类型参数不正确", IPApplyServRespCode.Parameter_Error.getCode(),
  // callResult.getCode());
  // System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  // // 成功调用
  // callResult = service.call(null, "IP_AVAILABLE_QUERY",
  // "{\"applyCity\":\"101\",\"ipCount\":\"8\",\"busiType\":\"1\"}");
  // System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
  // Assert.assertSame("可用IP查询接口，成功调用", IPApplyServRespCode.Success.getCode(),
  // callResult.getCode());
  // }

  @Test
  public void testAdd() {
    ApplyInfoBean applyInfo = new ApplyInfoBean();
    applyInfo.setApplyCity("991");
    applyInfo.setBusiType(1);
    applyInfo.setApplyCode("222245222");
    applyInfo.setApplyDesc("申请测试");
    applyInfo.setCoAddress("地址测试");
    applyInfo.setCoCity("650100");
    applyInfo.setCoClassify("电信业务经营者");
    applyInfo.setCoCounty("650101");
    applyInfo.setCoIndustry("测试");
    applyInfo.setCoLevel("省部以上级别");
    applyInfo.setCoLicense("测试");
    applyInfo.setCoLevel("级别");
    applyInfo.setCoName("测试");
    applyInfo.setCoNature("企业");
    applyInfo.setContact("张三");
    applyInfo.setContactEmail("admin@139.com");
    applyInfo.setContactTel("139001001001");
    applyInfo.setCoProvince("650000");
    applyInfo.setExpiredDate(
        DateTime.parse("2016-12-31", DateTimeFormat.forPattern("yyyy-MM-dd")).toDate());
    applyInfo.setGatewayLocation("测试");
    applyInfo.setIpCount(18);
    applyInfo.setOperator("admin");
    applyInfo.setUseWay("动态分配");


    // ApplyInfoBean applyInfo2 =
    // JsonConvertUtil.nonDefaultMapper().fromJson("{OperateTime:\"2015-11-23\",contractTel:\"123455445\",contact:\"test\",applyDesc:\".....\",applyCode:\"XJ-IPO-151123-00006\",applyCity:\"991\",operator:\"sysAdmin\",contactEmail:\"2234@sdf.ckd\",busiType:\"1\",expiredDate:\"2015-11-26\",ipCount:\"1\"}",
    // ApplyInfoBean.class);

    // CallResultBean callResult =
    // service.call(null, "IP_APPLY_ADD",
    // "{\"applyCode\":\"315112317453100029741\",\"busiType\":\"1\",\"ipCount\":\"4\",\"applyDesc\":\"集团专线申请IP\",\"applyCity\":\"991\",\"operator\":\"SUPERUSR\",\"expiredDate\":\"2050-12-31\",\"coName\":\"石0英新疆\",\"coClassify\":\"其他\",\"coLicense\":\"\",\"coNature\":\"1\",\"coProvince\":\"650000\",\"coCity\":\"650100\",\"coAddress\":\"123456\",\"contact\":\"冯国平\",\"contactTel\":\"13999158003\",\"contactEmail\":\"qita\",\"useWay\":\"静态\"}");
//     try {
//     CallResultBean callResult =
//     service.call(null, "IP_APPLY_ADD", JsonConvertUtil.toJSON(applyInfo));
//    
//     System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
//     } catch (Exception e) {
//     e.printStackTrace();
//     }
  }

//   @Test
//   public void testCancel() throws Exception {
//   CallResultBean callResult =
//   service.call(null, "IP_APPLY_CANCEL", "{\"applyCode\":\"22222\",\"operator\":\"ADMIN\"}");
//   System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
//   }
  
//  
//   @Test
//   public void testUse() throws Exception{
//   CallResultBean callResult =
//   service.call(null, "IP_APPLY_USE_EMOS", "{\"applyCode\":\"22222\",\"operator\":\"ADMIN\"}");
//   System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
//   }
//  
   @Test
   public void testRecycle() throws Exception {
   CallResultBean callResult =
   service.call(null, "IP_APPLY_RECYCLE", "{\"applyCode\":\"22222\",\"operator\":\"ADMIN\"}");
   System.out.println(JsonConvertUtil.nonEmptyMapper().toJson(callResult));
   }
}
