/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.web;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;

import com.google.common.net.HttpHeaders;
import com.langnatech.util.EncodeUtil;
 /**
 * Http与Servlet工具类。
 * 
 * @author zangchuqiang
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class Servlets {
    /** ONE_YEAR_SECONDS:一年的换算成秒*/
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/** 
	 * 设置客户端缓存过期时间的Header。<br/>
	 * @param response HttpServletResponse对象。
	 * @param expiresSeconds long缓存过长时间过期(秒)。
	 */ 
	public static void setExpiresHeader(HttpServletResponse response,
			long expiresSeconds) {
		// Http 1.0 header, set a fix expires date.
		response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis()
				+ expiresSeconds * 1000);
		// Http 1.1 header, set a time after now.
		response.setHeader(HttpHeaders.CACHE_CONTROL, "private, max-age="
				+ expiresSeconds);
	}

	/** 
	 * 设置禁止客户端缓存的Header。<br/>
	 * @param response HttpServletResponse对象。
	 */ 
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader(HttpHeaders.EXPIRES, 1L);
		response.addHeader(HttpHeaders.PRAGMA, "no-cache");
		// Http 1.1 header
		response.setHeader(HttpHeaders.CACHE_CONTROL,
				"no-cache, no-store, max-age=0");
	}

	/** 
	 * 设置LastModified Header。<br/>
	 * @param response HttpServletResponse对象。
	 * @param lastModifiedDate header的最后修改时间。
	 */ 
	public static void setLastModifiedHeader(HttpServletResponse response,
			long lastModifiedDate) {
		response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModifiedDate);
	}

	/** 
	 * 设置Etag Header。<br/>
	 * @param response HttpServletResponse对象。
	 * @param etag head中需要设置的内容。
	 */ 
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader(HttpHeaders.ETAG, etag);
	}

	/** 
	 * 根据浏览器If-Modified-Since Header，计算文件是否已被修改，如果无修改，checkIfModify返回false，设置304 not modify status。<br/>
	 * @param request HttpServletRequest对象。
	 * @param response HttpServletResponse对象。
	 * @param lastModified long类型，内容的最后修改时间。
	 * @return 返回boolean类型。
	 */ 
	public static boolean checkIfModifiedSince(HttpServletRequest request,
			HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/** 
	 * 根据浏览器 If-None-Match Header，计算Etag是否已无效。如果Etag有效，checkIfNoneMatch返回false，设置304 not modify status。<br/>
	 * @param request HttpServletRequest对象。
	 * @param response HttpServletResponse对象。
	 * @param etag 内容的ETag。
	 * @return 返回true或者false
	 */ 
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
			HttpServletResponse response, String etag) {
		String headerValue = request.getHeader(HttpHeaders.IF_NONE_MATCH);
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}
			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(HttpHeaders.ETAG, etag);
				return false;
			}
		}
		return true;
	}

	/** 
	 * 设置让浏览器弹出下载对话框的Header。<br/>
	 * @param response HttpServletResponse对象。
	 * @param fileName 下载后的文件名。
	 */ 
	public static void setFileDownloadHeader(HttpServletResponse response,String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(),"ISO8859-1");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

    /** 
     * 取得带相同前缀的Request Parameters，copy from spring WebUtils。<br/>
     * @param request ServletRequest对象。
     * @param prefix 需要匹配参数名的前缀。
     * @return 返回的结果的Parameter名已去除前缀。
     */ 
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	
	/** 
	 * 组合Parameters生成Query String的Parameter部分，并在paramter name上加上prefix。<br/>
	 * @param params 参数map。
	 * @return prefix 匹配参数名的前缀。
	 * @see #getParametersStartingWith
	 */ 
	public static String encodeParameterStringWithPrefix(
			Map<String, Object> params, String prefix) {
		if (params == null || params.size() == 0) {
			return "";
		}
		if (prefix == null) {
			prefix = "";
		}
		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	/** 
	 * 客户端对Http Basic验证的 Header进行编码。<br/>
	 * @param userName 用户名。
	 * @param password 密码。
	 * @return 编码后的字符串。
	 */ 
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + EncodeUtil.encodeBase64(encode.getBytes());
	}
}