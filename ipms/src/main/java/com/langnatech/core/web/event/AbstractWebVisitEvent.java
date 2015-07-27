
package com.langnatech.core.web.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Title:IWebVisitEvent
 * @author liyi
 * @date Dec 21, 2013 5:25:08 PM
 */

public abstract class AbstractWebVisitEvent extends ApplicationEvent {

    private static final long serialVersionUID = -4419575410503118959L;

    public AbstractWebVisitEvent(Object source) {
        super(source);
    }
}
