package com.langnatech.ipms.cache;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.langnatech.core.cache.BaseCacheAspect;
import com.langnatech.core.cache.bean.CacheConfigBean;
import com.langnatech.core.exception.BaseRuntimeException;
import com.langnatech.core.web.IWebInitializable;
import com.langnatech.ipms.entity.DeviceTypeEntity;
import com.langnatech.ipms.service.DeviceTypeService;


@Component
@Aspect
public class DeviceTypeCacheAspect extends BaseCacheAspect<DeviceTypeEntity>
{
    @Override
    public void initialize()
    {
        CacheConfigBean configBean = new CacheConfigBean();
        configBean.setModuleKey(getModuleKey());
        configBean.setServiceName(DeviceTypeService.class.getCanonicalName());
        configBean.setMethodName("getAllDeviceType");
        configBean.setArgsObjects(null);
        configBean.setInitlized(true);
        configBean.setDesc("获取设备类型维度数据");
        configBean.setCacheName("设备类型维度");
        this.getICache().registryConfig(configBean);
    }

    @Override
    public Class<? extends IWebInitializable> setInitDepend()
    {
        return null;

    }

    @Override
    protected String getModuleKey()
    {
        return this.getClass().getCanonicalName();
    }

    @Pointcut(value = "target(com.langnatech.ipms.service.DeviceTypeService)")
    private void servicePointcut()
    {
    }

    @SuppressWarnings("unchecked")
    @Around(value = "servicePointcut() && execution(* getAllDeviceType())")
    public List<DeviceTypeEntity> queryAdvice(ProceedingJoinPoint pjp)
    {
        List<DeviceTypeEntity> list = this.getByModule();
        if (list != null)
        {
            return list;
        }
        try
        {
            Object var = pjp.proceed();
            if (var == null)
            {
                return null;
            }
            list = (List<DeviceTypeEntity>)var;
            for (DeviceTypeEntity entity : list)
            {
                this.put(entity.getDeviceTypeId(), entity);
            }
            return list;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new BaseRuntimeException(e);
        }
    }

    @Around(value = "servicePointcut() && execution(* getDeviceTypeById(*))")
    public DeviceTypeEntity querySingleAdvice(ProceedingJoinPoint pjp)
    {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0)
        {
            String deviceTypeId = args[0].toString();
            Object deviceTypeEntity = this.get(deviceTypeId);
            if (deviceTypeEntity != null)
            {
                return (DeviceTypeEntity)deviceTypeEntity;
            }
        }
        try
        {
            Object var = pjp.proceed();
            if (var != null)
            {
                DeviceTypeEntity deviceTypeEntity = (DeviceTypeEntity)var;
                this.put(deviceTypeEntity.getDeviceTypeId(), deviceTypeEntity);
                return deviceTypeEntity;
            }
        }
        catch (Throwable e)
        {
            throw new BaseRuntimeException(e);
        }
        return null;
    }
}
