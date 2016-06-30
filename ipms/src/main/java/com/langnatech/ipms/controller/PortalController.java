package com.langnatech.ipms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.privilege.shiro.extend.CustomAuthenticationToken;
import com.langnatech.privilege.sysmgr.entity.SysMenuEntity;
import com.langnatech.privilege.sysmgr.service.SysMenuService;


@Controller
@RequestMapping("")
public class PortalController
{

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping({"index", ""})
    public String index(Model model)
    {
        List<SysMenuEntity> menuList = this.sysMenuService.getAllMenu();
        model.addAttribute("loginUser", SecurityContextHolder.getLoginName());
        model.addAttribute("MenuList", menuList);
        return "index";
    }

    @RequestMapping("login")
    public String login(String username, String password, HttpServletRequest request)
    {
        try
        {
            Subject subject = SecurityUtils.getSubject();
            if (username == null)
            {
                return "login";
            }
            if (subject.isAuthenticated())
            {
                if (subject.getPrincipal().toString().equals(username))
                {
                    return "redirect:/index";
                }
                else
                {
                    WebVisitEventPublish.getInstance().logoutEvent(false);
                    subject.logout();
                }
            }
            AuthenticationToken token = new CustomAuthenticationToken(username, password);
            subject.login(token);
            return "redirect:/index";
        }
        catch (AuthenticationException e)
        {
            request.setAttribute("LOGIN_ERROR", e.getClass().getName());
        }
        return "login";
    }

    @RequestMapping("logout")
    public String logout()
    {
        WebVisitEventPublish.getInstance().logoutEvent(false);
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @RequestMapping("error/unauth")
    public String unauth()
    {
        return "common/error/error-403";
    }
    
    @RequestMapping("sys/menuMgr")
    public String menuMgr()
    {
        return "sysmgr/menuMgr";
    }
    @RequestMapping("sys/userMgr")
    public String userMgr()
    {
        return "sysmgr/userMgr";
    } 
}
