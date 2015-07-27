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
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.service.IPPoolConfService;

@Component
@Aspect
public class IPPoolConfCacheAspect extends BaseCacheAspect<IPPoolConfEntity>
{
    @Override
    public void initialize() {
        CacheConfigBean configBean = new CacheConfigBean();
        configBean.setModuleKey(getModuleKey());
        configBean.setServiceName(IPPoolConfService.class.getCanonicalName());
        configBean.setMethodName("getAllIPPoolConf");
        configBean.setArgsObjects(null);
        configBean.setInitlized(true);
        configBean.setDesc("获取IP地址池配置信息缓存");
        configBean.setCacheName("IP地址池配置");
        configBean.setModuleName("系统模块");
        this.getICache().registryConfig(configBean);
    }

    @Override
    public Class<? extends IWebInitializable> setInitDepend() {
        return null;
        
    }

    @Override
    protected String getModuleKey() {
        return this.getClass().getCanonicalName();
    }
    
    @Pointcut(value = "target(com.langnatech.ipms.service.IPPoolConfService)")
    private void servicePointcut() {
    }
    
    
    @SuppressWarnings("unchecked")
    @Around(value = "servicePointcut() && execution(* getAllIPPoolConf())")
    public List<IPPoolConfEntity> queryAdvice(ProceedingJoinPoint pjp){
        List<IPPoolConfEntity> list = this.getByModule();
        if(list!=null){
            return list;
        }
        try {
            Object var = pjp.proceed();
            if(var==null){
                return null;
            }
            list = (List<IPPoolConfEntity>)var;
            for(IPPoolConfEntity entity : list){
                this.put(entity.getPoolId(), entity);
            }
            return list;
        } catch (Throwable e) {
            throw new BaseRuntimeException(e);
        }
    }
    
    
    @Around(value = "servicePointcut() && execution(* getIPPoolConfByPoolId(*))")
    public IPPoolConfEntity querySingleAdvice(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        if(args!=null && args.length>0){
            String poolId = args[0].toString();
            Object poolConf = this.get(poolId);
            if(poolConf!=null){
                return (IPPoolConfEntity)poolConf;
            }
        }
        try {
            Object var = pjp.proceed();
            if(var!=null){
                IPPoolConfEntity poolConf = (IPPoolConfEntity)var;
                this.put(poolConf.getPoolId(), poolConf);
                return poolConf;
            }
        } catch (Throwable e) {
            throw new BaseRuntimeException(e);
        }
        return null;
    }    
}
