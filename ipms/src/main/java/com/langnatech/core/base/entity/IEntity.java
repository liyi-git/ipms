/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.base.entity;
import java.io.Serializable;
/**
 * 基于mybatis进行数据库操作时，实体都需要实现该接口，TypeAliasesPackagePropExtend
 * 已扩展了mybatis扫描实体包的功能，applicationContext-dao-base.xml配置了多个扫描
 * 包路径，加载所有实现该接口的实体类，这样就无需在mybatis配置文件中配置实体路径。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public interface IEntity extends Serializable {
}
