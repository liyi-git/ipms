package com.langnatech.ipms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.langnatech.ipms.holder.IPArchiveDicHolder;
import com.langnatech.util.convert.JsonConvertUtil;


@Controller
@RequestMapping("/dic")
public class DicController
{
    /**
     * 返回系统枚举值 JS
     * @return
     */
    @RequestMapping("/enums")
    public String enums()
    {
        return "/dic/enum";
    }
    /**
     * 返回备案字典（地市）数据 JS
     * @return
     */
    @RequestMapping("/archivecity")
    @ResponseBody
    public String archivecity() throws Exception
    {
        return "define(function( ) {return " + JsonConvertUtil.nonDefaultMapper().toJson(IPArchiveDicHolder.INSTANCE.getAllCity()) + ";});";
    }

}
