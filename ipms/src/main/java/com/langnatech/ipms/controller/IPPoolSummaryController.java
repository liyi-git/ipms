package com.langnatech.ipms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.langnatech.core.exception.BaseRuntimeException;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.service.IPPoolConfService;
import com.langnatech.ipms.service.IPPoolService;


@Controller
@RequestMapping("/pool")
public class IPPoolSummaryController
{
    @Autowired
    private IPPoolService ipPoolService;

    @Autowired
    private IPPoolConfService ipPoolConfService;

    /**
     * 根据地址池ID，展示地址池概览页面
     */
    @RequestMapping({"/summary"})
    public ModelAndView summary()
        throws BaseRuntimeException
    {
        ModelAndView mv = new ModelAndView();
        List<Map<String, Object>> list = ipPoolService.getSubPoolStatByPool("-9");
        if (list == null)
        {
            throw new BaseRuntimeException("查询IP地址池失败！");
        }
        mv.addObject("TOTAL_DATA", ipPoolService.statPoolAggregate(list, "POOL_ID"));
        mv.addObject("DATA_LIST", list);
        mv.setViewName("pool/view/summary/summary-pool");
        return mv;
    }

    /**
     * 根据地址池ID，展示地址池概览页面
     */
    @RequestMapping("P_{poolId}/summary")
    public ModelAndView summary(@PathVariable String poolId)
        throws BaseRuntimeException
    {
        ModelAndView mv = new ModelAndView();
        List<Map<String, Object>> list = null;
        IPPoolConfEntity ipPoolConf = ipPoolConfService.getIPPoolConfByPoolId(poolId);
        if (ipPoolConf == null)
        {
            throw new BaseRuntimeException("地址池编码 [ " + poolId + " ] 不存在!");
        }
        if (ipPoolConf.isDir())
        {//如果是目录，查询子地址池
            list = ipPoolService.getSubPoolStatByPool(poolId);
            mv.setViewName("pool/view/summary/summary-pool");
            mv.addObject("TOTAL_DATA", ipPoolService.statPoolAggregate(list, "POOL_ID"));
        }
        else if (ipPoolConf.isPlanCity())
        {//如果是按地市拆分地址池，查询地市地址池
            list = ipPoolService.getCitySubPoolStatByPool(poolId);
            mv.setViewName("pool/view/summary/summary-citypool");
            mv.addObject("POOL_ID",poolId);
            mv.addObject("TOTAL_DATA", ipPoolService.statPoolAggregate(list, "CITY_ID"));
        }
        else
        {//查询网段
            list = ipPoolService.getSubnetStatByPool(poolId);
            mv.setViewName("pool/view/summary/summary-subnet");
            mv.addObject("POOL_ID",poolId);
            mv.addObject("TOTAL_DATA", ipPoolService.statPoolAggregate(list, "SUBNET_ID"));
        }
        if (list == null)
        {
            throw new BaseRuntimeException("查询IP地址池失败！");
        }
        mv.addObject("DATA_LIST", list);
        return mv;
    }

    /**
     * 根据地市ID，展示地市地址池概览
     */
    @RequestMapping("C_{cityId}/summary")
    public ModelAndView summaryCity(@PathVariable String cityId)
    {
        ModelAndView mv = new ModelAndView();
        List<Map<String, Object>> list = ipPoolService.getPoolStatByCity(cityId);
        if (list == null)
        {
            throw new BaseRuntimeException("查询IP地址池失败！");
        }
        mv.addObject("DATA_LIST", list);
        mv.addObject("TOTAL_DATA", ipPoolService.statPoolAggregate(list, "POOL_ID"));        
        mv.addObject("CITY_ID", cityId);
        mv.setViewName("pool/view/summary/summary-city");
        return mv;
    }

    /**
     * 根据地址池ID、地市ID，展示该地市地址池的概览页面
     */
    @RequestMapping("PC_{poolId}-{cityId}/summary")
    public ModelAndView summaryCityPool(@PathVariable String poolId, @PathVariable String cityId)
    {
        ModelAndView mv = new ModelAndView();
        List<Map<String, Object>> list = ipPoolService.getSubnetStatByPool(poolId, cityId);
        if (list == null)
        {
            throw new BaseRuntimeException("查询IP地址池失败！");
        }
        mv.addObject("DATA_LIST", list);
        mv.addObject("TOTAL_DATA", ipPoolService.statPoolAggregate(list, "SUBNET_ID"));
        mv.setViewName("pool/view/summary/summary-subnet");
        return mv;
    }

}
