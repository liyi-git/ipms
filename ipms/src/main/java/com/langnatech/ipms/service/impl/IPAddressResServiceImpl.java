package com.langnatech.ipms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.core.web.page.Paginator;
import com.langnatech.ipms.bean.IPAddressResBean;
import com.langnatech.ipms.bean.SubNetResBean;
import com.langnatech.ipms.dao.IPAddressResDao;
import com.langnatech.ipms.entity.IPAddressResEntity;
import com.langnatech.ipms.enums.IPAddressTypeEnum;
import com.langnatech.ipms.enums.IPStatusEnum;
import com.langnatech.ipms.enums.SubNetUseStatusEnum;
import com.langnatech.ipms.service.IPAddressResService;
import com.langnatech.util.IpUtils;


@Service
public class IPAddressResServiceImpl implements IPAddressResService
{

    @Autowired
    private IPAddressResDao ipAddressResDao;

    public PageList<IPAddressResBean> getAllAddressBySubnetId(String subnetId, PageQuery pageQuery)
    {
        return this.ipAddressResDao.selectAllAddressBySubnetId(subnetId, pageQuery);
    }

    public boolean insertBatchIp(List<IPAddressResEntity> entitys)
    {
        return this.ipAddressResDao.insertBatchIp(entitys);
    }

    public boolean deleteIpBySubnetId(String subnetId)
    {
        return this.ipAddressResDao.deleteIpBySubnetId(subnetId);
    }

    public PageList<IPAddressResEntity> generateIPAddressList(SubNetResBean subNetResBean, PageQuery pageQuery)
    {
        PageList<IPAddressResEntity> pageList = new PageList<IPAddressResEntity>();
        Paginator paginator = new Paginator(pageQuery.getPagenum(), pageQuery.getPagesize(), subNetResBean.getIpCount());
        pageList.setPaginator(paginator);
        long begin = subNetResBean.getBeginIpDecimal() + pageQuery.getPagenum() * pageQuery.getPagesize();
        long end = subNetResBean.getEndIpDecimal();
        if (begin < end)
        {
            for (int i = 0; i < pageQuery.getPagesize(); i++ )
            {
                if ( (begin + i) <= end)
                {
                    String id = subNetResBean.getSubnetId() + "_" + i;
                    String address = IpUtils.getIpByDec(begin + i);
                    int status = IPStatusEnum.AVAILABLE.getCode();
                    int type = IPAddressTypeEnum.HOST.getCode();
                    if (subNetResBean.getUseStatus() == SubNetUseStatusEnum.USED.getCode())
                    {
                        status = IPStatusEnum.USED.getCode();
                    }
                    if (subNetResBean.getUseStatus() == SubNetUseStatusEnum.RESERVE.getCode())
                    {
                        status = IPStatusEnum.RESERVE.getCode();
                    }
                    if (subNetResBean.getUseStatus() == SubNetUseStatusEnum.FROZEN.getCode())
                    {
                        status = IPStatusEnum.FROZEN.getCode();
                    }
                    if (subNetResBean.getBeginIpDecimal() == (begin + i))
                    {
                        type = IPAddressTypeEnum.NET.getCode();
                    }
                    if (subNetResBean.getEndIpDecimal() == (begin + i))
                    {
                        type = IPAddressTypeEnum.BROADCAST.getCode();
                    }
                    pageList.add(new IPAddressResEntity(id, address, subNetResBean.getSubnetId(), status, type, -1));
                }
                else
                {
                    break;
                }
            }
        }
        return pageList;
    }

    @Override
    public IPAddressResBean getByAddressId(String addressId)
    {
        return this.ipAddressResDao.selectByAddressId(addressId);
    }

    @Override
    public int saveAddressRegistry(IPAddressResEntity addressEntity)
    {
        return this.ipAddressResDao.updateByPrimaryKeySelective(addressEntity);
    }

}
