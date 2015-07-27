/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.extend.dialect.impl;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import com.langnatech.core.extend.dialect.Dialect;
/**
 * MyBatis分页处理DB2数据库方言实现类，对Dialect父类的部分抽象方法根据DB2的规则进行了相应的实现。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
@Component
public class DB2Dialect extends Dialect {
    /**
     * 根据传入的日期类型数据，转换为字符型日期作为where条件之一。<br/>
     * 详细描述：把日期类型数据转换为字符型日期，主要是为了替换sql语句中where条件的参数。<br/>
     * 使用方式：DB2Dialect实例化后即可调用该方法。
     * @param value 日期类型参数。
     * @return 符合数据库类型的日期格式。
     */
    public String getDateCondByValue(Object value) {
        return "'" + new DateTime( ((Date)value).getTime()).toString("yyyy-MM-dd") + "'";
    }
    
    /**
     * DB2数据库支持分页功能。<br/>
     * 详细描述：DB2数据库支持分页功能因此返回true。<br/>
     * 使用方式：DB2Dialect实例化后，即可调用。
     * @return true支持分页。
     */
    public boolean supportsLimit() {
        return true;
    }
    
    /**
     * DB2数据库支持分页偏移量指定功能。<br/>
     * 详细描述：DB2数据库支持分页偏移量的指定返回true。<br/>
     * 使用方式：DB2Dialect实例化后，即可调用。
     * @return true支持偏移量。
     */
    public boolean supportsLimitOffset() {
        return true;
    }
    
    /**
     * 如果sql语句中带有order by则会在over()函数中添加进去，带有索引的字段会提高效率。
     */
    private static String getRowNumber(String sql) {
        StringBuffer rownumber = new StringBuffer(50).append("ROWNUMBER() OVER(");
        int orderByIndex = sql.toUpperCase().indexOf("ORDER BY");
        if (orderByIndex > 0) {
        	String orderBy = sql.substring(orderByIndex);
        	StringBuffer orderByBuffer = new StringBuffer();
        	if(orderBy.indexOf(",") > 0){
        		String[] orderByArray = orderBy.split(",");
        		for(int i = 0;i < orderByArray.length;i++){
        			if(i == 0){
        				orderByBuffer.append(orderByArray[i]).append(" nulls last");
        			}else{
        				orderByBuffer.append(",").append(orderByArray[i]).append(" nulls last");
        			}
        		}
        	}else{
        		orderByBuffer.append(orderBy).append(" nulls last");
        	}
            rownumber.append(orderByBuffer);
        }
        rownumber.append(") AS ROWNUMBER_,");
        return rownumber.toString();
    }
    
    /**
     * 根据sql和分页信息返回拼接好的分页sql语句。<br/>
     * 详细描述：根据分页信息，把需要分页的sql语句拼接成db2支持的分页逻辑语句并返回，该逻辑利用了ROWNUMBER() OVER()聚合函数实现。<br/>
     * 使用方式：DB2Dialect实例化后，即可调用。
     * @param sql 需要分页的sql语句。
     * @param offset 分页数据偏移量。
     * @param offsetPlaceholder offset转换为字符型。
     * @param limit 每页显示多少条。
     * @param limitPlaceholder limit转换为字符型。
     * @return 带有分页条件的sql语句。
     */
    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit,
                                 String limitPlaceholder) {
        int startOfSelect = sql.toUpperCase().indexOf("SELECT");
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100)
            .append(sql.substring(0, startOfSelect)).append("SELECT * FROM ( SELECT ")
            .append(getRowNumber(sql));
        if(sql.toUpperCase().indexOf("ORDER BY") > 0){
        	sql = sql.substring(0,sql.toUpperCase().indexOf("ORDER BY")-1);
        }
        pagingSelect.append(" ROW_.* FROM ( ").append(sql.substring(startOfSelect))
            .append(" ) AS ROW_");
        pagingSelect.append(" ) AS TEMP_ WHERE ROWNUMBER_ ");
        if (offset > 0) {
            String endString = offsetPlaceholder + "+" + limitPlaceholder;
            pagingSelect.append("BETWEEN " + offsetPlaceholder + "+1 AND " + endString);
        } else {
            pagingSelect.append("<= " + limitPlaceholder);
        }
        pagingSelect.append(" WITH UR");
        return pagingSelect.toString();
    }
}