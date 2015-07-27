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
import com.langnatech.ipms.entity.DimCityEntity;
import com.langnatech.ipms.service.DimCityService;


@Component
@Aspect
public class DimCityCacheAspect extends BaseCacheAspect<DimCityEntity>
{
    @Override
    public void initialize()
    {
        CacheConfigBean configBean = new CacheConfigBean();
        configBean.setModuleKey(getModuleKey());
        configBean.setServiceName(DimCityService.class.getCanonicalName());
        configBean.setMethodName("getAllCity");
        configBean.setArgsObjects(null);
        configBean.setInitlized(true);
        configBean.setDesc("获取地市维度数据");
        configBean.setCacheName("地市维度");
        configBean.setModuleName("系统模块");
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

    @Pointcut(value = "target(com.langnatech.ipms.service.DimCityService)")
    private void servicePointcut()
    {
    }

    @SuppressWarnings("unchecked")
    @Around(value = "servicePointcut() && execution(* getAllCity())")
    public List<DimCityEntity> queryAdvice(ProceedingJoinPoint pjp)
    {
        List<DimCityEntity> list = this.getByModule();
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
            list = (List<DimCityEntity>)var;
            for (DimCityEntity entity : list)
            {
                this.put(entity.getCityId(), entity);
            }
            return list;
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            throw new BaseRuntimeException(e);
        }
    }

    @Around(value = "servicePointcut() && execution(* getCityByCityId(*))")
    public DimCityEntity querySingleAdvice(ProceedingJoinPoint pjp)
    {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0)
        {
            String cityId = args[0].toString();
            Object cityEntity = this.get(cityId);
            if (cityEntity != null)
            {
                return (DimCityEntity)cityEntity;
            }
        }
        try
        {
            Object var = pjp.proceed();
            if (var != null)
            {
                DimCityEntity cityEntity = (DimCityEntity)var;
                this.put(cityEntity.getCityId(), cityEntity);
                return cityEntity;
            }
        }
        catch (Throwable e)
        {
            throw new BaseRuntimeException(e);
        }
        return null;
    }
}
