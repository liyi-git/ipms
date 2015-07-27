package com.langnatech.ipms.controller;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.ipms.entity.IPAddressResEntity;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.service.IPAddressResService;
import com.langnatech.ipms.service.IPPoolConfService;
import com.langnatech.ipms.service.SubNetResService;
import com.langnatech.util.IpUtils;

@Controller
@RequestMapping("/subnet/plan")
public class SubNetPlanController {

    @Autowired
    private SubNetResService subNetResService;

    @Autowired
    private IPPoolConfService ipPoolConfService;

    @Autowired
    private IPAddressResService ipAddressResService;

    private static final String POOL_ROOT_ID = "-9";

    private static final int IS_PLAN_CITY = -1;

    private static final int ASSIGN_TYPE = 1;

    /**
     * 展示地址段规划页面
     * 
     * @return
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
    public Object addSubnet(String ip, String maskbits, String area, String pool, String desc) {
        String poolId = pool;
        int mbits = Integer.valueOf(maskbits);
        String beginIp = IpUtils.getFirstIp(ip, mbits);
        String endIp = IpUtils.getLastIp(ip, mbits);
        SubNetResEntity entity = new SubNetResEntity();
        entity.setSubnetId(IDGeneratorHolder.getId());
        if (desc == "") {
            desc = ip + "/" + maskbits;
        }
        entity.setSubnetDesc(desc);

        // 设置地址段状态
        if ( !POOL_ROOT_ID.equals(poolId)) {
            IPPoolConfEntity poolEntity = this.ipPoolConfService.getIPPoolConfByPoolId(poolId);
            // 更改设备、IDC、业务三个地址池的状态
            if (poolEntity.getPoolPid().equals(POOL_ROOT_ID)) {
                // 设备或者业务地址池的状态设置
                if (poolEntity.getIsPlanCity() == IS_PLAN_CITY) {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
                    entity.setCityId("-1");
                } else {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
                    entity.setCityId("-1");
                }
            } else {
                // 更改业务地址池的下子地址池状态设置
                if ("-1".equals(area)) {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
                } else {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
                }
                entity.setCityId(area);

            }
            // 如果是手动分配IP的地址池则添加IP地址
            if (poolEntity.getAssignType() == ASSIGN_TYPE) {
                // 按照IP分配方式插入所有的IP数据
                List<IPAddressResEntity> ipList = new ArrayList<IPAddressResEntity>();
                Long beginAddr = IpUtils.getDecByIp(beginIp);
                Long endAddr = IpUtils.getDecByIp(endIp);
                while (beginAddr <= endAddr) {
                    IPAddressResEntity ipEntity = new IPAddressResEntity();
                    ipEntity.setAddressId(IDGeneratorHolder.getId());
                    ipEntity.setAddressIp(IpUtils.getIpByDec(beginAddr));
                    ipEntity.setSubnetId(entity.getSubnetId());
                    ipEntity.setAddressStatus(1);
                    ipEntity.setAddressType(2);
                    ipEntity.setIsIpv6( -1);
                    ipEntity.setOperator(SecurityContextHolder.getLoginName());
                    beginAddr = beginAddr + 1;
                    ipList.add(ipEntity);
                }
                this.ipAddressResService.insertBatchIp(ipList);
            }

        } else {
            entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
        }
        entity.setPoolId(poolId);
        entity.setBeginIp(beginIp);
        entity.setBeginIpDecimal(IpUtils.getDecByIp(beginIp));
        entity.setEndIp(endIp);
        entity.setEndIpDecimal(IpUtils.getDecByIp(endIp));
        entity.setMaskBits(Short.valueOf(maskbits));
        entity.setNetmask(IpUtils.getMask(mbits));
        entity.setIpCount(IpUtils.getHostNum(mbits) - 2);
        entity.setSubnetPid("-1");
        entity.setIsIpv6(Short.valueOf("-1"));
        entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
        entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
        entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
        entity.setOperator(SecurityContextHolder.getLoginName());
        entity.setOperateTime(DateTime.now().toDate());
        entity.setLft(1);
        entity.setRgt(1);
        return subNetResService.insertSubNet(entity);
    }

    /**
     * 验证网段是否存在
     */
    @RequestMapping("/isExistSubnet")
    @ResponseBody
    public Object isExistSubnet(String subnet, String maskbits) {
        String desc = subnet + "/" + maskbits;
        return this.subNetResService.isExistSubnet(desc);
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
        String poolId = entity.getPoolId();
        // 先删除之前的IP信息
        this.ipAddressResService.deleteIpBySubnetId(entity.getSubnetId());
        if ( !POOL_ROOT_ID.equals(poolId)) {
            IPPoolConfEntity poolEntity = this.ipPoolConfService.getIPPoolConfByPoolId(poolId);
            // 更改设备、IDC、业务三个地址池的状态
            if (poolEntity.getPoolPid().equals(POOL_ROOT_ID)) {
                // 设备或者业务地址池的状态设置
                if (poolEntity.getIsPlanCity() == IS_PLAN_CITY) {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
                    entity.setCityId("-1");
                } else {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
                    entity.setCityId("-1");
                }
            } else {
                // 更改业务地址池的下子地址池状态设置
                if ("-1".equals(entity.getCityId())) {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
                } else {
                    entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
                }
            }
            // 如果是手动分配IP的地址池则添加IP地址
            if (poolEntity.getAssignType() == ASSIGN_TYPE) {
                // 按照IP分配方式插入所有的IP数据
                List<IPAddressResEntity> ipList = new ArrayList<IPAddressResEntity>();
                Long beginAddr = entity.getBeginIpDecimal();
                Long endAddr = entity.getEndIpDecimal();
                while (beginAddr <= endAddr) {
                    IPAddressResEntity ipEntity = new IPAddressResEntity();
                    ipEntity.setAddressId(IDGeneratorHolder.getId());
                    ipEntity.setAddressIp(IpUtils.getIpByDec(beginAddr));
                    ipEntity.setSubnetId(entity.getSubnetId());
                    ipEntity.setAddressStatus(1);
                    ipEntity.setAddressType(2);
                    ipEntity.setIsIpv6( -1);
                    ipEntity.setOperator(SecurityContextHolder.getLoginName());
                    beginAddr = beginAddr + 1;
                    ipList.add(ipEntity);
                }
                this.ipAddressResService.insertBatchIp(ipList);
            }
        } else {
            entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
            entity.setCityId("-1");
            entity.setUseStatus(Integer.valueOf("-1"));
        }
        entity.setOperator(SecurityContextHolder.getLoginName());
        entity.setOperateTime(DateTime.now().toDate());
        return this.subNetResService.updateSubnetById(entity);
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
        List<SubNetResEntity> subnetList = new ArrayList<SubNetResEntity>();
        String subnetId = "";
        for (int i = 0; i < entitys.length; i++ ) {
            IPPoolConfEntity poolEntity = this.ipPoolConfService.getIPPoolConfByPoolId(entitys[i].getPoolId());
            SubNetResEntity entity = new SubNetResEntity();
            String poolId = entitys[i].getPoolId();
            String cityId = entitys[i].getCityId();
            String beginIp = entitys[i].getBeginIp();
            String netMask = entitys[i].getNetmask();
            String endIp = entitys[i].getEndIp();
            String subnetPid = entitys[i].getSubnetPid();
            subnetId = subnetPid;
            int maskbits = IpUtils.getMaskBits(netMask);
            entity.setSubnetId(IDGeneratorHolder.getId());
            entity.setSubnetDesc(beginIp + "/" + maskbits);

            // 设置地址段状态
            if ( !POOL_ROOT_ID.equals(poolId)) {
                // 更改设备、IDC、业务三个地址池的状态
                if (poolEntity.getPoolPid().equals(POOL_ROOT_ID)) {
                    // 设备或者业务地址池的状态设置
                    if (poolEntity.getIsPlanCity() == IS_PLAN_CITY) {
                        entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
                        entity.setCityId("-1");
                    } else {
                        entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
                        entity.setCityId("-1");
                    }
                } else {
                    // 更改业务地址池的子地址池状态设置
                    if ("-1".equals(cityId)) {
                        entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
                    } else {
                        entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
                    }
                    entity.setCityId(cityId);
                }

                if (poolEntity.getAssignType() == ASSIGN_TYPE) {
                    // 按照IP分配方式插入所有的IP数据
                    List<IPAddressResEntity> ipList = new ArrayList<IPAddressResEntity>();
                    Long beginAddr = IpUtils.getDecByIp(beginIp);
                    Long endAddr = IpUtils.getDecByIp(endIp);
                    while (beginAddr <= endAddr) {
                        IPAddressResEntity ipEntity = new IPAddressResEntity();
                        ipEntity.setAddressId(IDGeneratorHolder.getId());
                        ipEntity.setAddressIp(IpUtils.getIpByDec(beginAddr));
                        ipEntity.setSubnetId(entity.getSubnetId());
                        ipEntity.setAddressStatus(1);
                        ipEntity.setAddressType(2);
                        ipEntity.setIsIpv6( -1);
                        ipEntity.setOperator(SecurityContextHolder.getLoginName());
                        beginAddr = beginAddr + 1;
                        ipList.add(ipEntity);
                    }
                    this.ipAddressResService.insertBatchIp(ipList);
                }
            } else {
                entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
                entity.setCityId(cityId);
            }
            entity.setPoolId(poolId);
            entity.setBeginIp(beginIp);
            entity.setBeginIpDecimal(IpUtils.getDecByIp(beginIp));
            entity.setEndIp(endIp);
            entity.setEndIpDecimal(IpUtils.getDecByIp(endIp));
            entity.setMaskBits((short)maskbits);
            entity.setNetmask(netMask);
            entity.setIpCount(IpUtils.getHostNum(maskbits) - 2);
            entity.setSubnetPid(subnetPid);
            entity.setIsIpv6(Short.valueOf("-1"));
            entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
            entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
            entity.setLft(1);
            entity.setRgt(1);
            entity.setOperator(SecurityContextHolder.getLoginName());
            entity.setOperateTime(DateTime.now().toDate());
            subnetList.add(entity);
        }
        // 拆分子网后更新父网段信息
        SubNetResEntity pEntity = this.subNetResService.selectSubnetById(subnetId);
        pEntity.setPlanStatus(SubNetPlanStatusEnum.ILLEGAL.getCode());
        pEntity.setPoolId("-9");
        pEntity.setCityId("-1");
        pEntity.setUseStatus(Integer.valueOf("-1"));
        pEntity.setOperator(SecurityContextHolder.getLoginName());
        pEntity.setOperateTime(DateTime.now().toDate());
        this.subNetResService.splitSubnet(pEntity);
        return this.subNetResService.insertBatchSubnet(subnetList);
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
     * 回收子网段
     */
    @RequestMapping("mergeSubnet/{subnetId}")
    @ResponseBody
    public Object mergeSubnet(@PathVariable String subnetId) {
        return this.subNetResService.mergeSubnetById(subnetId);
    }
}
