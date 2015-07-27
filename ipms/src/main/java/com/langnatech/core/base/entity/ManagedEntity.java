package com.langnatech.core.base.entity;

import java.util.Date;


public class ManagedEntity implements IEntity
{

    private static final long serialVersionUID = 8013088410470861993L;

    /** isValid:是否有效。 */
    private Integer isValid;

    /** createUser:创建人。 */
    private String creator;

    /** createTime:创建时间。 */
    private Date createTime;

    /** modifyUser:修改人。 */
    private String modifier;

    /** modifyTime:修改时间。 */
    private Date modifyTime;
    
    public boolean isValid(){
        return this.getIsValid()!=-1;
    }

    public Integer getIsValid()
    {
        return isValid;
    }

    public void setIsValid(Integer isValid)
    {
        this.isValid = isValid;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getModifier()
    {
        return modifier;
    }

    public void setModifier(String modifier)
    {
        this.modifier = modifier;
    }

    public Date getModifyTime()
    {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

}