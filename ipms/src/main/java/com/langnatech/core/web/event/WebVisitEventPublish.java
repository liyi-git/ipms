
package com.langnatech.core.web.event;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import com.langnatech.core.enums.LoginAuthResult;
import com.langnatech.core.enums.LoginTypeEnum;
import com.langnatech.core.holder.SpringContextHolder;
import com.langnatech.core.web.event.impl.LoginEvent;
import com.langnatech.core.web.event.impl.OperateEvent;
import com.langnatech.ipms.enums.OperateObjTypeEnum;
import com.langnatech.ipms.enums.OperateTypeEnum;
import com.langnatech.ipms.enums.OperateWayEnum;
import com.langnatech.logging.entity.LoginLogEntity;
import com.langnatech.logging.entity.OperateLogEntity;


/**
 * @Title:Web事件发布
 * @author liyi
 * @date Dec 21, 2013 4:29:00 PM
 */
@Component
public class WebVisitEventPublish implements ApplicationEventPublisherAware
{

    private ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("static-access")
    public void loginEvent(LoginAuthResult loginResult)
    {
        if (loginResult.getCode() == loginResult.LOGIN_SUCCESS.getCode())
        {
            LoginLogEntity loggingBean = new LoginLogEntity();
            loggingBean.setLoginType(LoginTypeEnum.LOGIN.getCode());
            this.eventPublisher.publishEvent(new LoginEvent(this, loggingBean));
        }
    }

    public void logoutEvent(boolean isTimeout)
    {
        LoginLogEntity loggingBean = new LoginLogEntity();
        if (isTimeout)
        {
            loggingBean.setLoginType(LoginTypeEnum.TIMEOUT.getCode());
        }
        else
        {
            loggingBean.setLoginType(LoginTypeEnum.LOGOUT.getCode());
        }
        this.eventPublisher.publishEvent(new LoginEvent(this, loggingBean));
    }

    public void operateEvent(OperateTypeEnum operateCodeEnum, String operateObj, OperateObjTypeEnum ObjType, String poolId, String cityId)
    {
        this.operateEvent(operateCodeEnum, operateObj, ObjType, poolId, cityId, null, OperateWayEnum.INNER_INVOKE);
    }

    public void operateEvent(OperateTypeEnum operateCodeEnum, String operateObj, OperateObjTypeEnum ObjType, String poolId, String cityId,
                             String operateCont)
    {
        this.operateEvent(operateCodeEnum, operateObj, ObjType, poolId, cityId, operateCont, OperateWayEnum.INNER_INVOKE);
    }

    public void operateEvent(OperateTypeEnum operateCode, String operateObj, OperateObjTypeEnum ObjType, String poolId, String cityId,
                             String operateCont, OperateWayEnum operateWay)
    {
        OperateLogEntity operateLogEntity = new OperateLogEntity();
        operateLogEntity.setObjType(ObjType.getCode());
        operateLogEntity.setOperateObj(operateObj);
        if (StringUtils.isNotBlank(operateCont))
        {
            operateLogEntity.setOperateCont(operateCont);
        }
        operateLogEntity.setOperateSource(operateWay.getCode());
        operateLogEntity.setOperateType(operateCode.getCode());
        OperateEvent event = new OperateEvent(this, operateLogEntity);
        this.eventPublisher.publishEvent(event);
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
    {
        this.eventPublisher = applicationEventPublisher;
    }

    public static WebVisitEventPublish getInstance()
    {
        return SpringContextHolder.getBean(WebVisitEventPublish.class);
    }
}
