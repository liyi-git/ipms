package com.langnatech.ipms.service;

import java.util.List;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.ipms.bean.IPAddressResBean;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.entity.IPAddressResEntity;


public interface IPAddressResService
{

    public PageList<IPAddressResBean> getAllAddressBySubnetId(String subnetId, PageQuery pageQuery);

    public PageList<IPAddressResEntity> generateIPAddressList(SubNetResBean subNetResBean, PageQuery pageQuery);

    public boolean insertBatchIp(List<IPAddressResEntity> entitys);

    public boolean deleteIpBySubnetId(String subnetId);

    public IPAddressResBean getByAddressId(String addressId);

    public int saveAddressRegistry(IPAddressResEntity addressEntity);
}
