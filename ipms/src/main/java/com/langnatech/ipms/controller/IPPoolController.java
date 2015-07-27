package com.langnatech.ipms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.langnatech.ipms.service.IPCommonService;
import com.langnatech.ipms.service.IPPoolService;


@Controller
@RequestMapping("/pool")
public class IPPoolController
{
    @Autowired
    private IPPoolService ipPoolService;

    @Autowired
    private IPCommonService commonService;

    /**
     * 展示地址池页面
     */
    @RequestMapping({"", "/"})
    public String index()
    {
        return "pool/pool-index";
    }

    /**
     * 展示IP地址资源池
     * **/
    @RequestMapping("show")
    public ModelAndView showPool()
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("HIERARCHY_CRUMB", this.commonService.getHierarchyByPoolId("-9"));
        mv.setViewName("pool/view/poolview-root");
        return mv;
    }

    /**
     * 根据地址池编码，展示地址池查看页面
     * @param poolId 地址池编码
     * **/
    @RequestMapping("P_{poolId}/show")
    public ModelAndView showPool(@PathVariable String poolId)
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("POOL_ID", poolId);
        mv.addObject("HIERARCHY_CRUMB", this.commonService.getHierarchyByPoolId(poolId));
        mv.setViewName("pool/view/poolview-pool");
        return mv;
    }

    /**
     * 根据地市编码，展示该地市的地址池页面
     * @param cityId 地市编码
     */
    @RequestMapping("C_{cityId}/show")
    public ModelAndView showCity(@PathVariable String cityId)
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("CITY_ID", cityId);
        mv.addObject("HIERARCHY_CRUMB", this.commonService.getHierarchyByCityId(cityId));
        mv.setViewName("pool/view/poolview-city");
        return mv;
    }

    /**
     * 根据地市编码，展示该地市的地址池页面
     * @param poolId 地址池编码
     * @param cityId 地市编码
     */
    @RequestMapping("PC_{poolId}-{cityId}/show")
    public ModelAndView showCityPool(@PathVariable String poolId, @PathVariable String cityId)
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("POOL_ID", poolId);
        mv.addObject("CITY_ID", cityId);
        mv.addObject("HIERARCHY_CRUMB", this.commonService.getHierarchyByPoolCity(poolId, cityId));
        mv.setViewName("pool/view/poolview-citypool");
        return mv;
    }

    /**
     * 根据展示类型，获取地址池树形结构数据
     * @param viewby
     * @return
     */
    @RequestMapping("getPoolTreeFor{viewby}")
    @ResponseBody
    public List<Map<String, String>> getIPTree(@PathVariable String viewby)
    {
        List<Map<String, String>> resultList = null;
        if (viewby.equalsIgnoreCase("City"))
        {
            resultList = ipPoolService.getIPPoolTreeByCity();
        }
        else
        {
            resultList = ipPoolService.getIPPoolTreeByPool();
        }
        return resultList;
    }
}
