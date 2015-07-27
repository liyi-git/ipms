package com.langnatech.ipms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.service.IPCommonService;
import com.langnatech.ipms.service.SubNetResService;


@Controller
@RequestMapping("/selfquery")
public class SelfQueryController
{

    @Autowired
    private SubNetResService subNetResService;

    @Autowired
    private IPCommonService commonService;

    @InitBinder("subnet")
    private void initBind(WebDataBinder binder)
    {
        binder.setFieldDefaultPrefix("subnet.");
    }

    @InitBinder("archive")
    private void initBind2(WebDataBinder binder)
    {
        binder.setFieldDefaultPrefix("archive.");
    }

    @RequestMapping("")
    public String index()
    {
        return "subnet/self-query";
    }

    @RequestMapping("/query")
    @ResponseBody
    public List<Map<String, Object>> query(@ModelAttribute("subnet") SubNetResEntity subnet, @ModelAttribute("archive") IPArchiveInfoEntity archive,
                                           PageQuery pageQuery)
    {
        String[] poolIds = this.commonService.getSubPoolIdsByPoolId(subnet.getPoolId());
        PageList<Map<String, Object>> list = subNetResService.getSubNetsBySelfQuery(poolIds, subnet, archive, pageQuery);
        return list;
    }
}
