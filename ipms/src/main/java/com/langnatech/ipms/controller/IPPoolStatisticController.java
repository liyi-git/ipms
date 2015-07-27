package com.langnatech.ipms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.langnatech.ipms.service.IPStatisticService;


@Controller
@RequestMapping("/pool")
public class IPPoolStatisticController
{

    @Autowired
    private IPStatisticService statisticService;

    @RequestMapping("/statistic")
    public ModelAndView statisticPool()
    {
        ModelAndView mv = new ModelAndView("pool/view/statistic/statistic-root");
        return mv;
    }

    @RequestMapping("/P_{poolId}/statistic")
    public ModelAndView statisticPool(@PathVariable String poolId)
    {
        ModelAndView mv = new ModelAndView("pool/view/statistic/statistic-pool");
        mv.addObject("POOL_ID", poolId);
        return mv;
    }

    @RequestMapping("/C_{cityId}/statistic")
    public ModelAndView statisticCity(@PathVariable String cityId)
    {
        ModelAndView mv = new ModelAndView("pool/view/statistic/statistic-city");
        mv.addObject("CITY_ID", cityId);
        return mv;
    }

    @RequestMapping("/PC_{poolId}-{cityId}/statistic")
    public ModelAndView statisticCityPool(@PathVariable String poolId, @PathVariable String cityId)
    {
        ModelAndView mv = new ModelAndView("pool/view/statistic/statistic-citypool");
        mv.addObject("CITY_ID", cityId);
        mv.addObject("POOL_ID", poolId);
        return mv;
    }

    @RequestMapping("/statistic/statPlanStatus")
    @ResponseBody
    public List<Map<String, Object>> statPlanStatus()
    {
        return statisticService.statIPPlanStatusForPool(null, null);
    }

    @RequestMapping("/statistic/statUseStatus")
    @ResponseBody
    public List<Map<String, Object>> statUseStatus()
    {
        return statisticService.statIPUseStatusForPool(null, null);
    }

    @RequestMapping("/P_{poolId}/statistic/statPlanStatus")
    @ResponseBody
    public List<Map<String, Object>> statPlanStatusForPool(@PathVariable String poolId)
    {
        return statisticService.statIPPlanStatusForPool(poolId, null);
    }

    @RequestMapping("/P_{poolId}/statistic/statUseStatus")
    @ResponseBody
    public List<Map<String, Object>> statUseStatusForPool(@PathVariable String poolId)
    {
        return statisticService.statIPUseStatusForPool(poolId, null);
    }

    @RequestMapping("/PC_{poolId}-{cityId}/statistic/statUseStatus")
    @ResponseBody
    public List<Map<String, Object>> statUseStatusForPoolCity(@PathVariable String poolId, @PathVariable String cityId)
    {
        return statisticService.statIPUseStatusForPool(poolId, cityId);
    }

    @RequestMapping("/C_{cityId}/statistic/statUseStatus")
    @ResponseBody
    public List<Map<String, Object>> statUseStatusForCity(@PathVariable String cityId)
    {
        return statisticService.statIPUseStatusForPool(null, cityId);
    }

}
