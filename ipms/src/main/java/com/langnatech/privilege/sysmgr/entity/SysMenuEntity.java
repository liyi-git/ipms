package com.langnatech.privilege.sysmgr.entity;

import java.util.Date;
import com.langnatech.core.base.entity.IEntity;

public class SysMenuEntity implements IEntity {

    private static final long serialVersionUID = 3362710792100241292L;

    private String menuId;

    private String menuPid;

    private String menuName;

    private String menuDesc;

    private Short menuType;

    private Short menuLevel;

    private String urlPath;

    private Short isValid;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

    private String menuCls;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuPid() {
        return menuPid;
    }

    public void setMenuPid(String menuPid) {
        this.menuPid = menuPid == null ? null : menuPid.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc == null ? null : menuDesc.trim();
    }

    public Short getMenuType() {
        return menuType;
    }

    public void setMenuType(Short menuType) {
        this.menuType = menuType;
    }

    public Short getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Short menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath == null ? null : urlPath.trim();
    }

    public Short getIsValid() {
        return isValid;
    }

    public void setIsValid(Short isValid) {
        this.isValid = isValid;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getMenuCls() {
        return menuCls;
    }

    public void setMenuCls(String menuCls) {
        this.menuCls = menuCls;
    }

}