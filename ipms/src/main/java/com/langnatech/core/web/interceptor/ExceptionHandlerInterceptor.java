package com.langnatech.core.web.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.langnatech.core.exception.BaseRuntimeException;
import com.langnatech.core.exception.UnauthorizedAccessException;
import com.langnatech.util.AjaxUtil;
import com.langnatech.util.convert.JsonConvertUtil;

/**
 * 统一处理全局异常ExceptionHandlerInterceptor类，利用springmvc的拦截机制，
 * 继承了SimpleMappingExceptionResolver类，重写doResolveException方法，
 * 如果系统中出现运行时异常都会被该方法拦截并处理。
 * 
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class ExceptionHandlerInterceptor extends SimpleMappingExceptionResolver {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 异常处理拦截方法。<br/>
	 * 详细描述：springmvc全局异常拦截处理方法，系统出现运行时异常都会被拦截到。<br/>
	 * 使用方式：无需调用，默认的springmvc处理机制。
	 * 
	 * @param request
	 *            当前的HTTP request请求。
	 * @param response
	 *            当前的HTTP response请求。
	 * @param handler
	 *            异常处理对象。
	 * @param ex
	 *            抛出的异常对象。
	 * @return ModelAndView对象。
	 */
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		String excetionDesc = getExceptionMsg(ex, null);
		RuntimeException runTimeException = createRunTimeException(ex,
				excetionDesc);
		if (viewName != null) {
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
					.getHeader("X-Requested-With") != null && request
					.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				if (null != runTimeException) {
					logger.error(
							"系统发生异常被ExceptionHandlerInterceptor拦截，请求为非异步请求返回html，异常信息为："
									+ runTimeException.getMessage(),
							runTimeException);
					return getModelAndView(viewName, runTimeException, request);
				} else {
					logger.error(
							"系统发生异常被ExceptionHandlerInterceptor拦截，请求为非异步请求返回html，异常信息为："
									+ ex.getMessage(), ex);
					return getModelAndView(viewName, ex, request);
				}
			} else {
				try {
					String accept = request.getHeader("accept");
					if (ex instanceof UnauthorizedAccessException)
						response.setStatus(403);
					else
						response.setStatus(500);
					if (!StringUtils.isEmpty(accept)) {
						if (accept.indexOf("html") != -1) {
							if (null != runTimeException) {
								logger.error(
										"系统发生异常被ExceptionHandlerInterceptor拦截，请求为异步请求返回html，异常信息为："
												+ runTimeException.getMessage(),
										runTimeException);
								return getModelAndView(viewName,
										runTimeException, request);
							} else {
								logger.error(
										"系统发生异常被ExceptionHandlerInterceptor拦截，请求为异步请求返回html，异常信息为："
												+ ex.getMessage(), ex);
								return getModelAndView(viewName, ex, request);
							}
						} else {
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw, true);
							ex.printStackTrace(pw);
							logger.error(
									"系统发生异常被ExceptionHandlerInterceptor拦截，请求为异步请求返回json，异常信息为："
											+ ex.getMessage(), ex);
							PrintWriter writer = response.getWriter();
							writer.write(JsonConvertUtil.nonEmptyMapper()
									.toJson(AjaxUtil.getExceptionJson(
											excetionDesc, sw.toString())));
							writer.flush();
							writer.close();
						}
					}
				} catch (Exception e) {
					logger.error(
							"ExceptionHandlerInterceptor class (doResolveException method) PrintWriter返回异步json数据时发生IO异常",
							e);
				}
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 对sql异常处理返回对应的业务描述。<br/>
	 * 详细描述：根据传入的异常对象，解析是否为sql异常，并根据数据库错误代码，返回国际化中定义的中文描述。<br/>
	 * 使用方式：实例化该类后即可调用。
	 * 
	 * @param e
	 *            抛出异常对象。
	 * @param role
	 *            权限角色。
	 * @return 对应的业务描述。
	 */
	public String getExceptionMsg(Exception e, String role) {
		String msg = "";
		if (e instanceof DataAccessException) {
			Throwable throwable = ((DataAccessException) e).getRootCause();
			if (throwable != null && throwable instanceof SQLException) {
				// SQLException sqlException = (SQLException) throwable;
				String exceptionDesc = "";
				// ExceptionMessageHolder.getExceptionMessages(
				// SQLExceptionsEnums.getPropertiesCode(sqlException.getErrorCode()),
				// null);
				msg = exceptionDesc;
			}
		} else {
			if (!StringUtils.isEmpty(e.getMessage())) {
				msg = e.getMessage();
			} else {
				msg = "系统发生错误，请联系管理员";
			}
		}
		return msg;
	}

	private RuntimeException createRunTimeException(Exception ex, String message) {
		if (ex instanceof BaseRuntimeException) {
			return (RuntimeException) ex;
		} else if (ex instanceof DataAccessException
				|| ex instanceof SQLException) {
			BaseRuntimeException baseException = new BaseRuntimeException(
					message, ex);
			baseException.setStackTrace(ex.getStackTrace());
			return baseException;
		} else if (ex instanceof UnauthorizedAccessException) {
			return (RuntimeException) ex;
		} else {
			return (RuntimeException) ex;
		}
	}
}