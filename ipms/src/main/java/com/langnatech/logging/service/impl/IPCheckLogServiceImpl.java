package com.langnatech.logging.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langnatech.core.enums.LoggingTypeEnum;
import com.langnatech.core.web.page.PageList;
import com.langnatech.core.web.page.PageQuery;
import com.langnatech.logging.dao.IPCheckLogDao;
import com.langnatech.logging.entity.IPCheckLogEntity;
import com.langnatech.logging.service.ILoggingService;
import com.langnatech.logging.service.IPCheckLogService;

@Service
public class IPCheckLogServiceImpl implements IPCheckLogService, ILoggingService<IPCheckLogEntity> {
	@Autowired
	private IPCheckLogDao ipCheckLogLogDao;

	public void insertLogBatch(Collection<IPCheckLogEntity> logList) {
		ipCheckLogLogDao.insertBatch(logList);
	}

	public void insertLog(IPCheckLogEntity loggingBean) {
		ipCheckLogLogDao.insert(loggingBean);
	}

	@Override
	public LoggingTypeEnum getLoggingType() {
		return LoggingTypeEnum.IP_CHECK_LOG;
	}

	@Override
	public PageList<IPCheckLogEntity> getLogsByCond(IPCheckLogEntity ipCheckLog, PageQuery pageQuery) {
		return this.ipCheckLogLogDao.selectLogsByCond(ipCheckLog, pageQuery);
	}

	@Override
	public List<Map<String, Integer>> selectCountData() {
		return this.ipCheckLogLogDao.selectCountData();
	}

}
