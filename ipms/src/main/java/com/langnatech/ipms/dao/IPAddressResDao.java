package com.langnatech.ipms.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.IPAddressResBean;
import com.langnatech.ipms.entity.IPAddressResEntity;

public interface IPAddressResDao {

    int deleteByPrimaryKey(String addressId);

    int insert(IPAddressResEntity record);

    int insertSelective(IPAddressResEntity record);

    public boolean insertBatchIp(List<IPAddressResEntity> list);

    public boolean deleteIpBySubnetId(String subnetId);

    public boolean deleteSubnetByPid(String subnetPid);

    IPAddressResBean selectByAddressId(@Param(value = "addressId") String addressId);

    int updateByPrimaryKeySelective(IPAddressResEntity record);

    int updateByPrimaryKey(IPAddressResEntity record);

    PageList<IPAddressResBean> selectAllAddressBySubnetId(String subnetId, PageQuery pageQuery);
}