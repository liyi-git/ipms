package com.langnatech.ipms.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.langnatech.core.exception.BaseRuntimeException;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.entity.IPAddressResEntity;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.enums.IPPoolAssignTypeEnum;
import com.langnatech.ipms.service.IPAddressResService;
import com.langnatech.ipms.service.IPArchiveInfoService;
import com.langnatech.ipms.service.IPCommonService;
import com.langnatech.ipms.service.IPPoolConfService;
import com.langnatech.ipms.service.SubNetResService;


@Controller
@RequestMapping("/subnet")
public class SubNetController
{

    @Autowired
    private SubNetResService subNetResService;

    @Autowired
    private IPPoolConfService poolConfService;

    @Autowired
    private IPArchiveInfoService archiveService;

    @Autowired
    private IPAddressResService addressResService;

    @Autowired
    private IPCommonService commonService;

    /**
     * 根据网段编码、网段状态，获取所有子网段
     * @param subnetId
     * @param planStatus
     * @return
     */
    @RequestMapping("/{subnetId}/list")
    @ResponseBody
    public List<SubNetResBean> list(@PathVariable(value = "subnetId") String subnetId, Integer planStatus)
    {
        String pid = StringUtils.isEmpty(subnetId) ? "-1" : subnetId;
        Integer status = (planStatus != null && planStatus <= 3) ? planStatus : -1;
        List<SubNetResBean> list = this.subNetResService.getAllSubNet(pid, status);
        return list;
    }

    /**
     * 根据网段状态，获取跟网段下的子网段
     * @param planStatus
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<SubNetResBean> listRoot(Integer planStatus)
    {
        return this.list("-1", planStatus);
    }

    /**
     * 根据网段编码，获取网段下的所有子网段（带分页）
     * @param subnetId
     * @param pageQuery
     * @return
     */
    @RequestMapping("/{subnetId}/listsubnet")
    @ResponseBody
    public PageList<SubNetResBean> listSubnet(@PathVariable(value = "subnetId") String subnetId, PageQuery pageQuery)
    {
        return this.subNetResService.getAllSubNet(subnetId, pageQuery);
    }

    /**
     * 根据网段编码，获取该网段下的所有IP地址
     * @param subnetId
     * @param pageQuery
     * @return
     */
    @RequestMapping("/{subnetId}/listip")
    @ResponseBody
    public PageList<? extends IPAddressResEntity> listIP(@PathVariable(value = "subnetId") String subnetId, PageQuery pageQuery)
    {
        SubNetResBean subNetResBean = subNetResService.selectSubnetById(subnetId);
        IPPoolConfEntity poolConfEntity = this.poolConfService.getIPPoolConfByPoolId(subNetResBean.getPoolId());
        if (subNetResBean.getSubnetCount() > 0)
        {
            throw new BaseRuntimeException("该网段下有子网段,不支持IP列表查看！");
        }
        //如果是手动分配类型，则从IP地址资源表获取IP地址
        if (poolConfEntity.getAssignType() == IPPoolAssignTypeEnum.Manual.getCode())
        {
            return this.addressResService.getAllAddressBySubnetId(subnetId, pageQuery);
        }
        else
        {//否则程序处理返回列表
            return this.addressResService.generateIPAddressList(subNetResBean, pageQuery);
        }
    }

    /**
     * 根据网段编码，展示该网段的网段概览页面
     * @param subnetId
     * @return
     */
    @RequestMapping("/{subnetId}/show")
    public ModelAndView show(@PathVariable(value = "subnetId") String subnetId)
    {
        SubNetResBean subNetResBean = subNetResService.selectSubnetById(subnetId);
        ModelAndView mv = new ModelAndView("subnet/subnet-show");
        IPPoolConfEntity poolConfEntity = this.poolConfService.getIPPoolConfByPoolId(subNetResBean.getPoolId());
        //获取备案信息
        IPArchiveInfoEntity archiveInfoEntity = this.archiveService.getArchiveInfoBySubnetId(subnetId);
        if (archiveInfoEntity != null)
        {
            mv.addObject("ARCHIVE_INFO", archiveInfoEntity);
        }
        //获取网段层级
        mv.addObject("HIERARCHY_SUBNET", this.subNetResService.getParentSubNetsBySubId(subnetId));
        mv.addObject("HIERARCHY_CRUMB", this.commonService.getHierarchyByPoolCity(subNetResBean.getPoolId(), subNetResBean.getCityId()));

        if (subNetResBean.getSubnetCount() > 0)
        {
            mv.addObject("SHOW_TYPE", "SUBNET");
        }
        else if (poolConfEntity.getAssignType() == IPPoolAssignTypeEnum.Manual.getCode())
        {
            mv.addObject("SHOW_TYPE", "IPLIST_1");
        }
        else
        {
            mv.addObject("SHOW_TYPE", "IPLIST_2");
        }
        mv.addObject("SUBNET", subNetResBean);
        return mv;
    }
}
