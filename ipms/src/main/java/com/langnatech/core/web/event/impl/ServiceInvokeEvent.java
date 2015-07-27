
package com.langnatech.core.web.event.impl;

import com.langnatech.core.web.event.AbstractWebVisitEvent;

/**
 * @Title:与外部系统服务接口调用事件,如:webservice、RMI、HttpClient等
 * @author liyi
 * @date Dec 21, 2013 5:18:21 PM
 */

public class ServiceInvokeEvent extends AbstractWebVisitEvent {

    private static final long serialVersionUID = -6779226084902143975L;

    public ServiceInvokeEvent(Object source) {
        super(source);

    }
}
