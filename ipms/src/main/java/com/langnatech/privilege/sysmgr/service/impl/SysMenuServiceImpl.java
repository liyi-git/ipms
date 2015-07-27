package com.langnatech.privilege.sysmgr.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.langnatech.privilege.sysmgr.dao.SysMenuDao;
import com.langnatech.privilege.sysmgr.entity.SysMenuEntity;
import com.langnatech.privilege.sysmgr.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    public List<SysMenuEntity> getAllMenu() {
        return this.sysMenuDao.selectAllMenu();
    }

}
