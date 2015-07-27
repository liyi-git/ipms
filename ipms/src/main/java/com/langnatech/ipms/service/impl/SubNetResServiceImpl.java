package com.langnatech.ipms.service.impl;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.OperateObjTypeEnum;
import com.langnatech.ipms.enums.OperateTypeEnum;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.service.SubNetResService;
import com.langnatech.util.IpUtils;

@Service
public class SubNetResServiceImpl implements SubNetResService {

    @Autowired
    private SubNetResDao subNetResDao;

    public SubNetResBean selectSubnetById(String subnetId) {
        return this.subNetResDao.selectSubnetById(subnetId);

    }

    public List<SubNetResBean> getAllSubNet(String subnetId, Integer planStatus) {
        return subNetResDao.selectAllSubNet(subnetId, planStatus);
    }

    public PageList<SubNetResBean> getAllSubNet(String subnetId, PageQuery pageQuery) {
        return subNetResDao.selectAllSubNet(subnetId, -1, pageQuery);
    }

    public boolean insertSubNet(SubNetResEntity entity) {
        WebVisitEventPublish.getInstance().operateEvent(OperateTypeEnum.ADD_SUBNET, entity.getSubnetDesc(), OperateObjTypeEnum.SUBNET, entity.getPoolId(), entity.getCityId());
        return subNetResDao.insertSubNet(entity);
    }

    public boolean updateSubnetById(SubNetResEntity entity) {
        WebVisitEventPublish.getInstance().operateEvent(OperateTypeEnum.ASSIGN_SUBNET, entity.getSubnetDesc(), OperateObjTypeEnum.SUBNET, entity.getPoolId(), entity.getCityId());
        return this.subNetResDao.updateSubnetById(entity);

    }

    public boolean splitSubnet(SubNetResEntity entity) {
        WebVisitEventPublish.getInstance().operateEvent(OperateTypeEnum.SPLIT_SUBNET, entity.getSubnetDesc(), OperateObjTypeEnum.SUBNET, entity.getPoolId(), entity.getCityId());
        return this.subNetResDao.updateSubnetById(entity);

    }

    public boolean insertBatchSubnet(List<SubNetResEntity> entitys) {
        return this.subNetResDao.insertBatchSubnet(entitys);
    }

    public boolean deleteSubnetById(String subnetId) {
        return this.subNetResDao.deleteSubnetById(subnetId);

    }

    public boolean deleteSubnetByPid(String subnetPid) {
        return this.subNetResDao.deleteSubnetByPid(subnetPid);
    }

    public PageList<Map<String, Object>> getSubNetsBySelfQuery(String[] poolIds, SubNetResEntity subNetRes, IPArchiveInfoEntity archiveInfo, PageQuery page) {
        if (StringUtils.isNotBlank(subNetRes.getBeginIp())) {
            long beginIpDecimal = IpUtils.getDecByIp(subNetRes.getBeginIp());
            subNetRes.setBeginIpDecimal(beginIpDecimal);
        }
        PageList<Map<String, Object>> pageList = subNetResDao.selectSubNetsBySelfQuery(poolIds, subNetRes, archiveInfo, page);
        return pageList;
    }

    public List<SubNetResEntity> getParentSubNetsBySubId(String subnetId) {
        return this.subNetResDao.selectParentSubNetsBySubId(subnetId);
    }

    public PageList<SubNetResBean> getAssignSubnetByPoolId(String poolId, PageQuery pageQuery) {
        return this.subNetResDao.selectAssignSubnetByPoolId(poolId, pageQuery);
    }

    public boolean mergeSubnetById(String subnetId) {
        List<String> planChildList = this.subNetResDao.selectPlanedOrPlaningByPid(subnetId);
        if (planChildList.size() > 0) {
            return false;
        } else {
            SubNetResEntity entity = this.subNetResDao.selectSubnetById(subnetId);
            entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
            entity.setCityId("-1");
            entity.setUseStatus(Integer.valueOf("-1"));
            this.subNetResDao.updateSubnetById(entity);
            return this.subNetResDao.deleteChildByPid(subnetId);
        }
    }

    public boolean isExistSubnet(String desc) {
        List<String> subnetList = this.subNetResDao.isExistSubnet(desc);
        if (subnetList.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

}
