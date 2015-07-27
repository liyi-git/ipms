/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * BaseSpringTest主要服务于单元测试用例，每个测试用例继承该类就无需在配置spring的环境配置，
 * 只需写自己的逻辑代码来测试功能。
 * 
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:conf/spring/applicationContext-*.xml")
public class BaseSpringTest {
}