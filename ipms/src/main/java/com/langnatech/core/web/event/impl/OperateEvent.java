
package com.langnatech.core.web.event.impl;

import com.langnatech.core.web.event.AbstractWebVisitEvent;
import com.langnatech.logging.entity.OperateLogEntity;

/**
 * @Title:功能操作事件,如（导出、上传、下载、增删改等）
 * @author liyi
 * @date Dec 21, 2013 4:34:50 PM
 */

public class OperateEvent extends AbstractWebVisitEvent {

	public OperateEvent(Object source) {
		super(source);
	}

	private static final long serialVersionUID = -7372309216314198520L;

	private OperateLogEntity operateLog;

    public OperateEvent(Object source, OperateLogEntity operateLog)
    {
        super(source);
        this.operateLog = operateLog;
    }

    public OperateLogEntity getOperateLog()
    {
        return operateLog;
    }

    public void setOperateLog(OperateLogEntity operateLog)
    {
        this.operateLog = operateLog;
    }


}
