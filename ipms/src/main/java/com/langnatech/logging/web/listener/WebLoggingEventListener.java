
package com.langnatech.logging.web.listener;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.core.web.event.AbstractWebVisitEvent;
import com.langnatech.core.web.event.impl.InterfaceOperateEvent;
import com.langnatech.core.web.event.impl.LoginEvent;
import com.langnatech.core.web.event.impl.OperateEvent;
import com.langnatech.core.web.event.impl.VisitResEvent;
import com.langnatech.logging.bean.LoggingBean;
import com.langnatech.logging.entity.LoginLogEntity;
import com.langnatech.logging.holder.LoggingHolder;
import com.langnatech.util.WebVisitUtil;


/**
 * @Title:Web访问监听,用于web访问事件发生时,记录日志
 * @author liyi
 * @date Dec 21, 2013 5:22:28 PM
 */
@Component
public class WebLoggingEventListener implements ApplicationListener<AbstractWebVisitEvent> {

  @Override
  public void onApplicationEvent(AbstractWebVisitEvent event) {

    String loginName = SecurityContextHolder.getLoginName();
    if (event instanceof InterfaceOperateEvent) {
      loginName = "admin";
    }
    if (null == loginName) {
      return;
    }
    LoggingBean loggingBean = null;
    if (event instanceof LoginEvent) {
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      LoginLogEntity loginLogEntity = ((LoginEvent) event).getLoginLog();
      loginLogEntity
          .setBrowserType(WebVisitUtil.getBrowseType(request.getHeader("User-Agent")).getCode());
      loginLogEntity.setClientIp(WebVisitUtil.getClientIP(request));
      loggingBean = loginLogEntity;
    } else if (event instanceof VisitResEvent) {
    } else if (event instanceof OperateEvent) {
      loggingBean = ((OperateEvent) event).getOperateLog();
    }

    if (loggingBean != null) {
      loggingBean.setLogId(IDGeneratorHolder.getId());
      loggingBean.setOperateTime(DateTime.now().toDate());
      loggingBean.setOperator(loginName);
      LoggingHolder.addLog(loggingBean);
    }
  }
}
