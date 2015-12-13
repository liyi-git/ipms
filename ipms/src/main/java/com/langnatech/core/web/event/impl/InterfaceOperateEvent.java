package com.langnatech.core.web.event.impl;

import com.langnatech.logging.entity.OperateLogEntity;

public class InterfaceOperateEvent extends OperateEvent {

  private static final long serialVersionUID = 5176049764083142296L;

  public InterfaceOperateEvent(Object source) {
    super(source);
  }

  public InterfaceOperateEvent(Object source, OperateLogEntity operateLog)
  {
      super(source,operateLog);
  }
}
