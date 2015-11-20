package com.langnatech.ipms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.service.SubNetResService;

@Controller
@RequestMapping("/subnet/plan")
public class SubNetPlanController {

    @Autowired
    private SubNetResService subNetResService;

    /**
     * 展示地址段规划页面
     */
    @RequestMapping("")
    public String index() {
        return "plan/plan-index";
    }

    /**
     * 展示IP地址段注入页面
     */
    @RequestMapping("showAddSubnet")
    public String showAddSegment() {
        return "plan/plan-addsubnet";
    }

    /**
     * 添加地址段
     */
    @RequestMapping("/addSubnet")
    @ResponseBody
    public Object addSubnet(String ip, String maskbits, String area, String pool) {
        return subNetResService.insertSubNet(ip,Integer.valueOf(maskbits),area,pool);
    }

    /**
     * 验证网段是否存在
     */
    @RequestMapping("/isExistSubnet")
    @ResponseBody
    public Object isExistSubnet(String subnet, String maskbits) {
        return this.subNetResService.isExistSubnet(subnet + "/" + maskbits);
    }

    /**
     * 展示地址段规划页面
     */
    @RequestMapping("showPlanSubnet/{subnetId}")
    public ModelAndView showPlanSubnet(@PathVariable String subnetId) {
        ModelAndView mv = new ModelAndView();
        SubNetResEntity entity = new SubNetResEntity();
        entity = this.subNetResService.selectSubnetById(subnetId);
        mv.addObject("entity", entity);
        mv.setViewName("plan/plan-plansubnet");
        return mv;
    }

    /**
     * 地址段规划
     */
    @RequestMapping("planSubnet")
    @ResponseBody
    public Object planSubnet(SubNetResEntity entity) {
        return this.subNetResService.planSubnet(entity);
    }

    /**
     * 展示地址段拆分页面
     */
    @RequestMapping("showSplitSubnet/{subnetId}")
    public ModelAndView showSplitSubnet(@PathVariable String subnetId) {
        ModelAndView mv = new ModelAndView();
        SubNetResEntity entity = new SubNetResEntity();
        entity = this.subNetResService.selectSubnetById(subnetId);
        mv.addObject("entity", entity);
        mv.setViewName("plan/plan-splitsubnet");
        return mv;
    }

    /**
     * 拆分地址段
     */
    @RequestMapping("splitSubnet")
    @ResponseBody
    public Object splitSubnet(@RequestBody SubNetResEntity[] entitys) {
        return this.subNetResService.splitSubnet(entitys);
    }

    /**
     * 删除网段
     */
    @RequestMapping("deleteSubnet/{subnetId}")
    @ResponseBody
    public Object deleteSubnet(@PathVariable String subnetId) {
        return this.subNetResService.deleteSubnetById(subnetId);
    }

    /**
     * 回收子网
     */
    @RequestMapping("mergeSubnet/{subnetId}")
    @ResponseBody
    public Object mergeSubnet(@PathVariable String subnetId) {
        return this.subNetResService.mergeSubnetById(subnetId);
    }
}
