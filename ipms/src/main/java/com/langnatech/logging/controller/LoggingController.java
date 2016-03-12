package com.langnatech.logging.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.IPCheckLogEntity;
import com.langnatech.logging.entity.OperateLogEntity;
import com.langnatech.logging.service.IPCheckLogService;
import com.langnatech.logging.service.OperateLogService;


@Controller
@RequestMapping("/logging")
public class LoggingController
{

    @Autowired
    private OperateLogService operateLogService;
    
    @Autowired
    private IPCheckLogService ipCheckLogService;

    @RequestMapping("/operate")
    public String showOperateLog()
    {
        return "logging/operateLog";
    }

    @RequestMapping("/login")
    public String showLoginLog()
    {
        return "logging/loginlog";
    }

    @RequestMapping("/operate/getData")
    @ResponseBody
    public PageList<OperateLogEntity> getOperateLogData(OperateLogEntity operateLog, PageQuery pageQuery)
    {
        return this.operateLogService.getLogsByCond(operateLog, pageQuery);
    }
    
    @RequestMapping("/ipCheck")
    public ModelAndView showIPCheckLog()
    {
    	ModelAndView mv=new ModelAndView();
    	List<Map<String,Object>> list=this.ipCheckLogService.selectCountData();
    	if(!list.isEmpty()){
    		mv.addObject("mismatching",list.get(0).get("NUM"));
    		mv.addObject("nodetected",list.get(1).get("NUM"));
    		mv.addObject("noexist",list.get(2).get("NUM"));
    	}
    	mv.setViewName("logging/ipCheckLog");
        return mv;
    }
    
    @RequestMapping("/ipCheck/getData")
    @ResponseBody
    public PageList<IPCheckLogEntity> getIPCheckLogData(IPCheckLogEntity ipCheckLog, PageQuery pageQuery)
    {
        return this.ipCheckLogService.getLogsByCond(ipCheckLog, pageQuery);
    }
}
