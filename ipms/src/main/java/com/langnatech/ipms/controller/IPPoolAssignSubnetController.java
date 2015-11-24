package com.langnatech.ipms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.service.SubNetResService;
import com.langnatech.util.convert.JsonConvertUtil;


@Controller
@RequestMapping("/pool")
public class IPPoolAssignSubnetController
{
    @Autowired
    private SubNetResService subNetResService;

    @RequestMapping("/assign")
    public ModelAndView assign()
    {
        ModelAndView mv = new ModelAndView("pool/view/assign/assign-subnet");
        return mv;
    }

    @RequestMapping("/P_{poolId}/assign")
    public ModelAndView assign(@PathVariable String poolId)
    {
        ModelAndView mv = new ModelAndView("pool/view/assign/assign-subnet");
        mv.addObject("POOL_ID", poolId);
        return mv;
    }

    @RequestMapping("/getAssignSubnet")
    @ResponseBody
    public PageList<SubNetResBean> getAssignSubnet(PageQuery pageQuery) throws Exception
    {
        PageList<SubNetResBean> list = this.subNetResService.getAssignSubnetByPoolId(null, pageQuery);
        JsonConvertUtil.nonDefaultMapper().toJson(list);
        return list;
    }

    @RequestMapping("/P_{poolId}/getAssignSubnet")
    @ResponseBody
    public PageList<SubNetResBean> getAssignSubnet(@PathVariable String poolId, PageQuery pageQuery)
    {
        if (poolId == null)
        {
            return this.subNetResService.getAssignSubnetByPoolId(null, pageQuery);
        }
        return this.subNetResService.getAssignSubnetByPoolId(poolId, pageQuery);
    }

}
