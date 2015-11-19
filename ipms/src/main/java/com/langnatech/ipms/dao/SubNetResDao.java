package com.langnatech.ipms.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.entity.IPArchiveInfoEntity;
import com.langnatech.ipms.entity.SubNetResEntity;

public interface SubNetResDao {

    List<SubNetResBean> selectAllSubNet(@Param(value = "subnetId") String subnetPid, @Param(value = "planStatus") Integer planStatus);

    PageList<SubNetResBean> selectAllSubNet(@Param(value = "subnetId") String subnetPid, @Param(value = "planStatus") Integer planStatus, PageQuery pageQuery);

    public boolean insertSubNet(SubNetResEntity entity);

    public boolean insertBatchSubnet(List<SubNetResEntity> list);

    boolean deleteSubnetById(String subnetId);

    boolean deleteSubnetByPid(String subnetPid);

    boolean updateSubnet(SubNetResEntity entity);

    SubNetResBean selectSubnetById(@Param(value = "subnetId") String subnetId);

    public PageList<Map<String, Object>> selectSubNetsBySelfQuery(@Param(value = "poolIds") String[] poolIds, @Param(value = "subnet") SubNetResEntity subNetRes,
                                                                  @Param(value = "archive") IPArchiveInfoEntity archiveInfo, PageQuery page);

    public List<SubNetResEntity> selectParentSubNetsBySubId(@Param(value = "subnetId") String subnetId);

    public PageList<SubNetResBean> selectAssignSubnetByPoolId(@Param(value = "poolId") String poolId, PageQuery pageQuery);

    public List<String> selectPlanedOrPlaningByPid(@Param(value = "subnetId") String subnetId);

    boolean deleteChildByPid(@Param(value = "subnetId") String subnetId);

    public List<String> isExistSubnet(@Param(value = "desc") String desc);

    //ESOP接口扩展
    public List<SubNetResEntity> getUsableSubnetList(@Param(value = "cityId") String cityId);

}