package com.langnatech.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

public class DatabaseProviderUtil {
    
    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(DatabaseProviderUtil.class);

	private DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();

	private DataSource dataSource;

	/**
	 * Sets the DatabaseIdProvider.
	 * 
	 * @since 1.1.0
	 * @return
	 */
	public DatabaseIdProvider getDatabaseIdProvider() {
		return databaseIdProvider;
	}

	/**
	 * Gets the DatabaseIdProvider
	 * 
	 * @since 1.1.0
	 * @param databaseIdProvider
	 */
	public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
		this.databaseIdProvider = databaseIdProvider;
	}

	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			this.dataSource = ((TransactionAwareDataSourceProxy)dataSource).getTargetDataSource();
		} else {
			this.dataSource = dataSource;
		}
	}
    
	/**
	 * 获取数据库提供商ID
	 * @Title: getDatabaseProviderId
	 * @Description: 这里用一句话描述这个方法的作用
	 * @return
	 */
	public String getDatabaseProviderId() {
		try {
			return this.databaseIdProvider.getDatabaseId(this.dataSource);
		} catch (SQLException e) {
		    LoggerUtil.error(logger, e.getMessage());
		}
		return null;
	}
}
