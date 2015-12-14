package com.langnatech.ipms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.exception.BaseRuntimeException;
import com.langnatech.core.holder.IDGeneratorHolder;
import com.langnatech.core.holder.SecurityContextHolder;
import com.langnatech.core.web.event.WebVisitEventPublish;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.dao.IPAddressResDao;
import com.langnatech.ipms.dao.SubNetResDao;
import com.langnatech.ipms.entity.IPAddressResEntity;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.IPPoolConfEntity;
import com.langnatech.ipms.entity.SubNetResEntity;
import com.langnatech.ipms.enums.OperateObjTypeEnum;
import com.langnatech.ipms.enums.OperateTypeEnum;
import com.langnatech.ipms.enums.SubNetPlanStatusEnum;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.service.IPPoolConfService;
import com.langnatech.ipms.service.SubNetResService;
import com.langnatech.util.IpUtils;

@Service
public class SubNetResServiceImpl implements SubNetResService {

  @Autowired
  private SubNetResDao subNetResDao;

  @Autowired
  private IPPoolConfService poolService;

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
    SubNetResBean subnet = this.subNetResDao.selectSubnetById(subnetId);
    if (subnet == null) {
      throw new BaseRuntimeException("子网段不存在，子网编码:" + subnetId);
    }
    if (subnet.getPlannedCount() > 0 || subnet.getIpKeepCount() > 0 || subnet.getIpUseCount() > 0) {
      return false;
    }
    if (subnet.getPoolId().equals("-1") || subnet.getPoolId().equals("-9")) {
      subnet.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
    } else {
      subnet.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
    }
    subnet.setCityId("-1");
    subnet.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
    this.subNetResDao.deleteChildByPid(subnetId);
    return this.subNetResDao.updateSubnet(subnet);
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
  public boolean insertSubNet(String ip, int maskbits, String area, final String poolId) {
    String beginIp = IpUtils.getFirstIp(ip, maskbits);
    String endIp = IpUtils.getLastIp(ip, maskbits);
    SubNetResEntity entity = new SubNetResEntity();
    entity.setSubnetId(IDGeneratorHolder.getId());
    entity.setSubnetDesc(IpUtils.getNetWorkIp(ip, maskbits) + "/" + maskbits);

    if (poolId.equals(POOL_ROOT_ID)) {
      entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
      entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
    } else {
      IPPoolConfEntity poolConf = this.poolService.getIPPoolConfByPoolId(poolId);
      // 是否有子地址池
      boolean isExistSubPool =
          CollectionUtils.exists(this.poolService.getAllIPPoolConf(), new Predicate() {
            public boolean evaluate(Object object) {
              return ((IPPoolConfEntity) object).getPoolPid().equals(poolId);
            }
          });
      if (isExistSubPool) {// 存在子地址池，则子网状态为规划中
        entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
        entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
      } else {
        if (poolConf.getIsPlanCity() == IS_PLAN_CITY) {// 如果地址池不按地市分配
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
          entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
          entity.setCityId("-1");
        } else {// 如果地址池按地市分配
          if (area.equals("-1")) {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
            entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
            entity.setCityId("-1");
          } else {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
            entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
            entity.setCityId(area);
          }
        }
      }
      // 如果是手动分配IP的地址池则添加IP地址
      if (poolConf.getAssignType() == ASSIGN_TYPE) {
        this.insertBatchIp(beginIp, endIp, entity.getSubnetId());
      }
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
    final String poolId = entity.getPoolId();
    // 先删除之前插入的IP地址
    this.ipAddressResDao.deleteIpBySubnetId(entity.getSubnetId());
    if (poolId.equals(POOL_ROOT_ID)) {
      entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
      entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
    } else {
      IPPoolConfEntity poolConf = this.poolService.getIPPoolConfByPoolId(poolId);
      // 是否有子地址池
      boolean isExistSubPool =
          CollectionUtils.exists(this.poolService.getAllIPPoolConf(), new Predicate() {
            public boolean evaluate(Object object) {
              return ((IPPoolConfEntity) object).getPoolPid().equals(poolId);
            }
          });
      if (isExistSubPool) {// 存在子地址池，则子网状态为规划中
        entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
        entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
      } else {
        if (poolConf.getIsPlanCity() == IS_PLAN_CITY) {// 如果地址池不按地市分配
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
          entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
          entity.setCityId("-1");
        } else {// 如果地址池按地市分配
          if (entity.getCityId().equals("-1")) {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
            entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
            entity.setCityId("-1");
          } else {
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
            entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
          }
        }
      }
      // 如果是手动分配IP的地址池则添加IP地址
      if (poolConf.getAssignType() == ASSIGN_TYPE) {
        this.insertBatchIp(entity.getBeginIp(), entity.getEndIp(), entity.getSubnetId());
      }
    }
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
      final String poolId =
          entitys[i].getPoolId() == null ? pEntity.getPoolId() : entitys[i].getPoolId();
      IPPoolConfEntity poolConf = this.poolService.getIPPoolConfByPoolId(poolId);
      String cityId = entitys[i].getCityId() == null ? pEntity.getCityId() : entitys[i].getCityId();
      SubNetResEntity entity = new SubNetResEntity();
      String beginIp = entitys[i].getBeginIp();
      String netMask = entitys[i].getNetmask();
      String endIp = entitys[i].getEndIp();
      int maskbits = IpUtils.getMaskBits(netMask);
      entity.setSubnetId(IDGeneratorHolder.getId());
      entity
          .setSubnetDesc(IpUtils.getNetWorkIp(entitys[i].getBeginIp(), maskbits) + "/" + maskbits);
      if (poolId.equals(POOL_ROOT_ID)) {
        entity.setPlanStatus(SubNetPlanStatusEnum.WAIT_PLAN.getCode());
        entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
      } else {
        // 是否有子地址池
        boolean isExistSubPool =
            CollectionUtils.exists(this.poolService.getAllIPPoolConf(), new Predicate() {
              public boolean evaluate(Object object) {
                return ((IPPoolConfEntity) object).getPoolPid().equals(poolId);
              }
            });
        if (isExistSubPool) {// 存在子地址池，则子网状态为规划中
          entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
          entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
        } else {
          if (poolConf.getIsPlanCity() == IS_PLAN_CITY) {// 如果地址池不按地市分配
            entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
            entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
            entity.setCityId("-1");
          } else {// 如果地址池按地市分配
            if (cityId == null || cityId.equals("-1")) {
              entity.setPlanStatus(SubNetPlanStatusEnum.PLANNING.getCode());
              entity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
              entity.setCityId("-1");
            } else {
              entity.setPlanStatus(SubNetPlanStatusEnum.PLANNED.getCode());
              entity.setUseStatus(SubNetUseStatusEnum.AVAILABLE.getCode());
            }
          }
        }
        // 如果是手动分配IP的地址池则添加IP地址
        if (poolConf.getAssignType() == ASSIGN_TYPE) {
          this.insertBatchIp(beginIp, endIp, entity.getSubnetId());
        }
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
    pEntity.setUseStatus(SubNetUseStatusEnum.ILLEGAL.getCode());
    pEntity.setPoolId("-9");
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
