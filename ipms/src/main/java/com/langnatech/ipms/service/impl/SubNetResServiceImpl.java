package com.langnatech.ipms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.dao.IPAddressResDao;
import com.langnatech.ipms.dao.IPPoolConfDao;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.IPAddressResEntity;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.OperateObjTypeEnum;
import com.langnatech.ipms.enums.OperateTypeEnum;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.service.SubNetResService;
import com.langnatech.util.IpUtils;

@Service
public class SubNetResServiceImpl implements SubNetResService {

  @Autowired
  private SubNetResDao subNetResDao;

  @Autowired
  private IPPoolConfDao ipPoolConfDao;

  @Autowired
  private IPAddressResDao ipAddressResDao;

  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  private static final String POOL_ROOT_ID = "-9";

  private static final int IS_PLAN_CITY = -1;

  private static final int ASSIGN_TYPE = 1;

  public SubNetResBean selectSubnetById(String subnetId) {
    return this.subNetResDao.selectSubnetById(subnetId);

  }

  public List<SubNetResBean> getAllSubNet(String subnetId, Integer planStatus) {
    return subNetResDao.selectAllSubNet(subnetId, planStatus);
  }

  public PageList<SubNetResBean> getAllSubNet(String subnetId, PageQuery pageQuery) {
    return subNetResDao.selectAllSubNet(subnetId, -1, pageQuery);
  }

  public PageList<Map<String, Object>> getSubNetsBySelfQuery(String[] poolIds,
      SubNetResEntity subNetRes, IPArchiveInfoEntity archiveInfo, PageQuery page) {
    if (StringUtils.isNotBlank(subNetRes.getBeginIp())) {
      long beginIpDecimal = IpUtils.getDecByIp(subNetRes.getBeginIp());
      subNetRes.setBeginIpDecimal(beginIpDecimal);
    }
    PageList<Map<String, Object>> pageList =
        subNetResDao.selectSubNetsBySelfQuery(poolIds, subNetRes, archiveInfo, page);
    return pageList;
  }

  public List<SubNetResEntity> getParentSubNetsBySubId(String subnetId) {
    return this.subNetResDao.selectParentSubNetsBySubId(subnetId);
  }

  public PageList<SubNetResBean> getAssignSubnetByPoolId(String poolId, PageQuery pageQuery) {
    return this.subNetResDao.selectAssignSubnetByPoolId(poolId, pageQuery);
  }

  public boolean deleteSubnetById(String subnetId) {
    return this.subNetResDao.deleteSubnetById(subnetId);

  }

  public boolean deleteSubnetByPid(String subnetPid) {
    return this.subNetResDao.deleteSubnetByPid(subnetPid);
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
      this.subNetResDao.deleteChildByPid(subnetId);
      return this.subNetResDao.updateSubnet(entity);
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

  /**
   * 插入网段
   * 
   * @param ip
   * @param maskbits
   * @param area
   * @param poolId
   */
  public boolean insertSubNet(String ip, int maskbits, String area, String poolId) {
    System.out.println(ip);
    System.out.println(maskbits);

    String beginIp = IpUtils.getFirstIp(ip, maskbits);
    System.out.println(beginIp);

    String endIp = IpUtils.getLastIp(ip, maskbits);
    System.out.println(endIp);
    SubNetResEntity entity = new SubNetResEntity();
    entity.setSubnetId(IDGeneratorHolder.getId());
    entity.setSubnetDesc(IpUtils.getNetWorkIp(ip, maskbits) + "/" + maskbits);

    // 设置地址段状态(如果地址段不处于-9地址池向下判断，如果处于-9地址池则规划状态为待规划状态)
    if (!POOL_ROOT_ID.equals(poolId)) {
      IPPoolConfEntity poolEntity = this.ipPoolConfDao.selectPoolConfByPoolId(poolId);
      // 更改设备、IDC、业务三个地址池的状态
      if (poolEntity.getPoolPid().equals(POOL_ROOT_ID)) {
        // 设备或者IDC地址池的状态设置(无需分配到地市，状态为已规划)
        if (poolEntity.getIsPlanCity() == IS_PLAN_CITY) {
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
          entity.setCityId("-1");
        } else {
          // 业务地址池(无需分配到地市，状态为规划中)
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
          entity.setCityId("-1");
        }
      } else {
        // 更改业务地址池的下子地址池状态设置(已经规划到地市的为已规划，未规划到地市的为未规划)
        if ("-1".equals(area)) {
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
        } else {
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
        }
        entity.setCityId(area);
      }

      // 如果是手动分配IP的地址池则添加IP地址
      if (poolEntity.getAssignType() == ASSIGN_TYPE) {
        this.insertBatchIp(beginIp, endIp, entity.getSubnetId());
      }


    } else {
      // 未规划到任何地址池状态为待规划
      entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
      entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());;
    }
    entity.setPoolId(poolId);
    entity.setBeginIp(beginIp);
    entity.setBeginIpDecimal(IpUtils.getDecByIp(beginIp));
    entity.setEndIp(endIp);
    entity.setEndIpDecimal(IpUtils.getDecByIp(endIp));
    entity.setMaskBits((short) maskbits);
    entity.setNetmask(IpUtils.getMask(maskbits));
    entity.setIpCount(IpUtils.getHostNum(maskbits) - 2);
    entity.setSubnetPid("-1");
    entity.setIsIpv6(Short.valueOf("-1"));
    entity.setOperator(SecurityContextHolder.getLoginName());
    entity.setOperateTime(DateTime.now().toDate());
    entity.setLft(1);
    entity.setRgt(1);
    WebVisitEventPublish.getInstance().operateEvent(OperateTypeEnum.ADD_SUBNET,
        entity.getSubnetDesc(), OperateObjTypeEnum.SUBNET, entity.getPoolId(), entity.getCityId());
    return subNetResDao.insertSubNet(entity);
  }

  /**
   * 规划网段
   * 
   * @param entity
   * 
   */
  public boolean planSubnet(SubNetResEntity entity) {
    String poolId = entity.getPoolId();
    // 先删除之前插入的IP地址
    this.ipAddressResDao.deleteIpBySubnetId(entity.getSubnetId());
    if (!POOL_ROOT_ID.equals(poolId)) {
      IPPoolConfEntity poolEntity = this.ipPoolConfDao.selectPoolConfByPoolId(poolId);
      // 更改设备、IDC、业务三个地址池的状态
      if (poolEntity.getPoolPid().equals(POOL_ROOT_ID)) {
        // 设备或者IDC地址池的状态设置(无需分配到地市，状态为已规划)
        if (poolEntity.getIsPlanCity() == IS_PLAN_CITY) {
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
          entity.setCityId("-1");
        } else {
          // 业务地址池(无需分配到地市，状态为规划中)
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
          entity.setCityId("-1");
        }
      } else {
        // 更改业务地址池的下子地址池状态设置(已经规划到地市的为已规划，未规划到地市的为未规划)
        if ("-1".equals(entity.getCityId())) {
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
        } else {
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
        }
      }

      // 如果是手动分配IP的地址池则添加IP地址
      if (poolEntity.getAssignType() == ASSIGN_TYPE) {
        this.insertBatchIp(entity.getBeginIp(), entity.getEndIp(), entity.getSubnetId());
      }
    } else {
      entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
      entity.setCityId("-1");
      entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
    }
    entity.setOperator(SecurityContextHolder.getLoginName());
    entity.setOperateTime(DateTime.now().toDate());
    WebVisitEventPublish.getInstance().operateEvent(OperateTypeEnum.ASSIGN_SUBNET,
        entity.getSubnetDesc(), OperateObjTypeEnum.SUBNET, entity.getPoolId(), entity.getCityId());
    return this.subNetResDao.updateSubnet(entity);
  }

  /**
   * 拆分网段
   * 
   * @param ip
   * @param maskbits
   * @param area
   * @param poolId
   */
  public boolean splitSubnet(SubNetResEntity[] entitys) {
    List<SubNetResEntity> subnetList = new ArrayList<SubNetResEntity>();
    String subnetPid = entitys[0].getSubnetPid();
    SubNetResEntity pEntity = this.selectSubnetById(subnetPid);
    for (int i = 0; i < entitys.length; i++) {
      IPPoolConfEntity poolEntity =
          this.ipPoolConfDao.selectPoolConfByPoolId(entitys[i].getPoolId());
      SubNetResEntity entity = new SubNetResEntity();
      String poolId = entitys[i].getPoolId();
      String cityId = entitys[i].getCityId();
      String beginIp = entitys[i].getBeginIp();
      String netMask = entitys[i].getNetmask();
      String endIp = entitys[i].getEndIp();
      int maskbits = IpUtils.getMaskBits(netMask);
      entity.setSubnetId(IDGeneratorHolder.getId());
      entity
          .setSubnetDesc(IpUtils.getNetWorkIp(entitys[i].getBeginIp(), maskbits) + "/" + maskbits);

      if (!POOL_ROOT_ID.equals(poolId)) {
        // 更改设备、IDC、业务三个地址池的状态
        if (poolEntity.getPoolPid().equals(POOL_ROOT_ID)) {
          // 设备或者IDC地址池的状态设置(无需分配到地市，状态为已规划)
          if (poolEntity.getIsPlanCity() == IS_PLAN_CITY) {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
            entity.setCityId("-1");
          } else {
            // 业务地址池(无需分配到地市，状态为规划中)
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
            entity.setCityId("-1");
          }
        } else {
          // 更改业务地址池的下子地址池状态设置(已经规划到地市的为已规划，未规划到地市的为未规划)
          if ("-1".equals(cityId)) {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
          } else {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
          }
          entity.setCityId(cityId);
        }

        // 如果是手动分配IP的地址池则添加IP地址
        if (poolEntity.getAssignType() == ASSIGN_TYPE) {
          this.insertBatchIp(beginIp, endIp, entity.getSubnetId());
        }

      } else {
        entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
        entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
        entity.setCityId(cityId);
      }
      if(pEntity.getPlanStatus()==SubNetPlanStatusEnum.PLANNED.getCode()){
          entity.setPoolId(pEntity.getPoolId());
          entity.setCityId(pEntity.getCityId());
          entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
      }
      entity.setPoolId(pEntity.getPoolId());
      entity.setCityId(cityId);
      entity.setBeginIp(beginIp);
      entity.setBeginIpDecimal(IpUtils.getDecByIp(beginIp));
      entity.setEndIp(endIp);
      entity.setEndIpDecimal(IpUtils.getDecByIp(endIp));
      entity.setMaskBits((short) maskbits);
      entity.setNetmask(netMask);
      entity.setIpCount(IpUtils.getHostNum(maskbits) - 2);
      entity.setSubnetPid(subnetPid);
      entity.setIsIpv6(Short.valueOf("-1"));
      entity.setLft(1);
      entity.setRgt(1);
      entity.setOperator(SecurityContextHolder.getLoginName());
      entity.setOperateTime(DateTime.now().toDate());
      subnetList.add(entity);
    }
    
    // 拆分子网后更新父网段信息
    pEntity.setPlanStatus(SubNetPlanStatusEnum.ILLEGAL.getCode());
    pEntity.setUseStatus(Integer.valueOf("-1"));
    pEntity.setOperator(SecurityContextHolder.getLoginName());
    pEntity.setOperateTime(DateTime.now().toDate());
    this.splitSubnet(pEntity);
    return this.subNetResDao.insertBatchSubnet(subnetList);
  }

  /**
   * 更新被拆分的网段
   * 
   * @param entity
   * @return
   */
  public boolean splitSubnet(SubNetResEntity entity) {
    WebVisitEventPublish.getInstance().operateEvent(OperateTypeEnum.SPLIT_SUBNET,
        entity.getSubnetDesc(), OperateObjTypeEnum.SUBNET, entity.getPoolId(), entity.getCityId());
    return this.subNetResDao.updateSubnet(entity);

  }

  /**
   * 批量插入IP地址
   * 
   * @param beginIp
   * @param endIp
   * @param subnetId
   */
  public void insertBatchIp(String beginIp, String endIp, String subnetId) {
    SqlSession sqlsession = null;
    try {
      List<IPAddressResEntity> ipList = new ArrayList<IPAddressResEntity>();
      Long beginAddr = IpUtils.getDecByIp(beginIp);
      Long endAddr = IpUtils.getDecByIp(endIp);
      while (beginAddr <= endAddr) {
        IPAddressResEntity ipEntity = new IPAddressResEntity();
        ipEntity.setAddressId(IDGeneratorHolder.getId());
        ipEntity.setAddressIp(IpUtils.getIpByDec(beginAddr));
        ipEntity.setSubnetId(subnetId);
        ipEntity.setAddressStatus(1);
        ipEntity.setAddressType(2);
        ipEntity.setIsIpv6(-1);
        ipEntity.setOperator(SecurityContextHolder.getLoginName());
        beginAddr = beginAddr + 1;
        ipList.add(ipEntity);
      }
      sqlsession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
      int batchCount = 1000;
      int batchTimes = new Double(Math.ceil(ipList.size() * 1.0 / batchCount)).intValue();
      int startIndex = 0;
      for (int i = 0; i < batchTimes; i++) {
        int endIndex = startIndex + batchCount;
        if (i == (batchTimes - 1)) {
          endIndex = ipList.size();
        }
        sqlsession.insert("com.langnatech.ipms.dao.IPAddressResDao.insertBatchIp",
            ipList.subList(startIndex, endIndex));
        startIndex = endIndex;
        sqlsession.commit();
      }
    } catch (Exception e) {
      e.printStackTrace();
      sqlsession.rollback();
    } finally {
      sqlsession.close();
    }

  }

  /**
   * 批量插入网段
   * 
   * @param beginIp
   * @param endIp
   * @param subnetId
   */
  public void insertBatchSubnet(List<SubNetResEntity> subnetList) {
    SqlSession sqlsession = null;
    try {

      sqlsession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
      for (SubNetResEntity subnetEntity : subnetList) {
        sqlsession.insert("com.langnatech.ipms.dao.SubNetResDao.insertSubnet", subnetEntity);
      }
      sqlsession.commit();
    } catch (Exception e) {
      e.printStackTrace();
      sqlsession.rollback();
    } finally {
      sqlsession.close();
    }

  }

  public boolean updateSubnetUseStatus(String subnetId, Integer status) {
    SubNetResEntity subnetRes = new SubNetResEntity();
    subnetRes.setSubnetId(subnetId);
    subnetRes.setUseStatus(status);
    return this.subNetResDao.updateSubnet(subnetRes);
  }
}
