package com.langnatech.privilege.sysmgr.dao;

import java.util.List;
import com.langnatech.privilege.sysmgr.entity.SysMenuEntity;

public interface SysMenuDao {

    int insert(SysMenuEntity record);

    public List<SysMenuEntity> selectAllMenu();

}