package com.langnatech.logging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.entity.OperateLogEntity;
import com.langnatech.logging.service.OperateLogService;


@Controller
@RequestMapping("/logging")
public class LoggingController
{

    @Autowired
    private OperateLogService operateLogService;

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
}
