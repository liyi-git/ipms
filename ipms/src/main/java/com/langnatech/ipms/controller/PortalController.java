package com.langnatech.ipms.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.privilege.sysmgr.entity.SysMenuEntity;
import com.langnatech.privilege.sysmgr.service.SysMenuService;

@Controller
@RequestMapping("")
public class PortalController {

    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping({"index", ""})
    public String index(HttpServletRequest request, Model model) {
        List<SysMenuEntity> menuList = this.sysMenuService.getAllMenu();
        model.addAttribute("loginUser", SecurityContextHolder.getLoginName());
        model.addAttribute("MenuList", menuList);
        return "index";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("logout")
    public String logout() {
        WebVisitEventPublish.getInstance().logoutEvent(false);
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }

    @RequestMapping("error/unauth")
    public String unauth() {
        return "common/error/error-403";
    }
}
