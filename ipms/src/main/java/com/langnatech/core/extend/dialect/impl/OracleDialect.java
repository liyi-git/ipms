/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.extend.dialect.impl;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.langnatech.core.extend.dialect.Dialect;
/**
 * MyBatis分页处理Oracle数据库方言实现类，对Dialect父类的部分抽象方法根据Oracle的规则进行了相应的实现。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class OracleDialect extends Dialect {
    /**
     * 根据传入的日期类型数据，转换为字符型日期作为where条件之一。<br/>
     * 详细描述：把日期类型数据转换为字符型日期，主要是为了替换sql语句中where条件的参数，
     *        该方法利用Oracle的TO_DATE函数，最终转换为数据库中的日期类型字段。<br/>
     * 使用方式：OracleDialect实例化后即可调用该方法。
     * @param value 日期类型参数。
     * @return 符合数据库类型的日期格式。
     */
    public String getDateCondByValue(Object value) {
        return " TO_DATE('" + new DateTime( ((Date)value).getTime()).toString("yyyy-MM-dd")
               + "','YYYY-MM-DD')";
    }
    
    /**
     * Oracle数据库支持分页功能。<br/>
     * 详细描述：Oracle数据库支持分页功能因此返回true。<br/>
     * 使用方式：OracleDialect实例化后，即可调用。
     * @return true支持分页。
     */
    public boolean supportsLimit() {
        return true;
    }
    
    /**
     * Oracle数据库支持分页偏移量指定功能。<br/>
     * 详细描述：Oracle数据库支持分页偏移量的指定返回true。<br/>
     * 使用方式：OracleDialect实例化后，即可调用。
     * @return true支持偏移量。
     */
    public boolean supportsLimitOffset() {
        return true;
    }
    
    /**
     * 根据sql和分页信息返回拼接好的分页sql语句。<br/>
     * 详细描述：根据分页信息，把需要分页的sql语句拼接成Oracle支持的分页逻辑语句并返回，该逻辑利用了ROWNUM函数实现。<br/>
     * 使用方式：OracleDialect实例化后，即可调用。
     * @param sql 需要分页的sql语句。
     * @param offset 分页数据偏移量。
     * @param offsetPlaceholder offset转换为字符型。
     * @param limit 每页显示多少条。
     * @param limitPlaceholder limit转换为字符型。
     * @return 带有分页条件的sql语句。
     */
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit,
                                 String limitPlaceholder) {
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toUpperCase().endsWith(" FOR UPDATE")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        if (offset > 0) {
            pagingSelect.append("SELECT * FROM ( SELECT ROW_.*, ROWNUM ROWNUM_ FROM ( ");
        } else {
            pagingSelect.append("SELECT * FROM ( ");
        }
        pagingSelect.append(sql);
        if (offset > 0) {
            String endString = offsetPlaceholder + "+" + limitPlaceholder;
            pagingSelect.append(" ) ROW_ ) WHERE ROWNUM_ <= " + endString + " AND ROWNUM_ > "
                                + offsetPlaceholder);
        } else {
            pagingSelect.append(" ) WHERE ROWNUM <= " + limitPlaceholder);
        }
        if (isForUpdate) {
            pagingSelect.append(" FOR UPDATE");
        }
        return pagingSelect.toString();
    }
}