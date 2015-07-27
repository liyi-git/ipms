package com.langnatech.core.base.dao;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.langnatech.util.LoggerUtil;

/**
 * @Title:SqlHandler
 * @Description: 这里用一句话描述这个类的作用
 * @author Administrator
 * @date Jul 25, 2014 11:29:15 AM
 */

public class SqlHandle {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(SqlHandle.class);

    private String sql;

    private Object[] args;

    public SqlHandle(String sql, Object[] args) {
        this.sql = sql;
        this.args = args;
    }

    public void addSql(String sql) {
        this.sql = sql;
    }

    public void addArgs(Object[] args) {
        this.args = args;
    }

    public SqlHandle() {
    }

    public String getSql() {
        return sql;
    }

    public Object[] getArgs() {
        return args;
    }

    public PreparedStatement handle(Connection con) {
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            // 赋值
            setV(stmt, 1);
            return stmt;
        } catch (SQLException e) {
            LoggerUtil.error(logger, e.getMessage());
        }
        return null;
    }

    /**
     * 给占位符设置值
     * 
     * @param psmt
     * @param i 传递值索引位置
     * @return
     * @throws SQLException
     */
    public int setV(PreparedStatement psmt, int i) throws SQLException {
        if (args != null)
            for (int j = 0; j < args.length; j++ ) {
                if (args[j] != null) {
                    setV(psmt, i, args[j]);
                    i++ ;
                }
            }
        return i;
    }

    /**
     * 给占位符设置值
     * 
     * @param psmt
     * @param i 值索引位置
     * @param arg 值
     * @throws SQLException
     */
    public void setV(PreparedStatement psmt, int i, Object arg) throws SQLException {
        if (arg instanceof String) {
            psmt.setString(i, (String)arg);
        } else if (arg instanceof Integer) {
            psmt.setInt(i, (Integer)arg);
        } else if (arg instanceof Float) {
            psmt.setFloat(i, (Float)arg);
        } else if (arg instanceof Double) {
            psmt.setDouble(i, (Double)arg);
        } else if (arg instanceof java.sql.Date) {
            psmt.setDate(i, (java.sql.Date)arg);
        } else if (arg instanceof Boolean) {
            psmt.setBoolean(i, (Boolean)arg);
        } else if (arg instanceof byte[]) {
            psmt.setBytes(i, (byte[])arg);
        } else if (arg instanceof Byte) {
            psmt.setByte(i, (Byte)arg);
        } else if (arg instanceof Blob) {
            psmt.setBlob(i, (Blob)arg);
        } else if (arg instanceof Clob) {
            psmt.setClob(i, (Clob)arg);
        }
    }

}
