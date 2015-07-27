package com.langnatech.util;

import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 * 日志工具类
 * 
 * @author jiqk
 */
public class LoggerUtil {

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param msg 日志信息
     */
    public static void error(Logger log, String msg) {
        if (log.isErrorEnabled()) {
            log.error(msg);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg the argument
     */
    public static void error(Logger log, String format, Object arg) {
        if (log.isErrorEnabled()) {
            log.error(format, arg);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void error(Logger log, String format, Object[] argArray) {
        if (log.isErrorEnabled()) {
            log.error(format, argArray);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void error(Logger log, String msg, Throwable t) {
        if (log.isErrorEnabled()) {
            log.error(msg, t);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void error(Logger log, String format, Object arg1, Object arg2) {
        if (log.isErrorEnabled()) {
            log.error(format, arg1, arg2);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg 日志信息
     */
    public static void error(Logger log, Marker marker, String msg) {
        if (log.isErrorEnabled(marker)) {
            log.error(marker, msg);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg the argument
     */
    public static void error(Logger log, Marker marker, String format, Object arg) {
        if (log.isErrorEnabled(marker)) {
            log.error(format, arg);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void error(Logger log, Marker marker, String format, Object[] argArray) {
        if (log.isErrorEnabled(marker)) {
            log.error(format, argArray);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void error(Logger log, Marker marker, String msg, Throwable t) {
        if (log.isErrorEnabled(marker)) {
            log.error(msg, t);
        }
    }

    /**
     * 输出error级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void error(Logger log, Marker marker, String format, Object arg1, Object arg2) {
        if (log.isErrorEnabled(marker)) {
            log.error(format, arg1, arg2);
        }
    }
    
    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param msg 日志信息
     */
    public static void warn(Logger log, String msg) {
        if (log.isWarnEnabled()) {
            log.warn(msg);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg the argument
     */
    public static void warn(Logger log, String format, Object arg) {
        if (log.isWarnEnabled()) {
            log.warn(format, arg);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void warn(Logger log, String format, Object[] argArray) {
        if (log.isWarnEnabled()) {
            log.warn(format, argArray);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void warn(Logger log, String msg, Throwable t) {
        if (log.isWarnEnabled()) {
            log.warn(msg, t);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void warn(Logger log, String format, Object arg1, Object arg2) {
        if (log.isWarnEnabled()) {
            log.warn(format, arg1, arg2);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg 日志信息
     */
    public static void warn(Logger log, Marker marker, String msg) {
        if (log.isWarnEnabled(marker)) {
            log.warn(marker, msg);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg the argument
     */
    public static void warn(Logger log, Marker marker, String format, Object arg) {
        if (log.isWarnEnabled(marker)) {
            log.warn(format, arg);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void warn(Logger log, Marker marker, String format, Object[] argArray) {
        if (log.isWarnEnabled(marker)) {
            log.warn(format, argArray);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void warn(Logger log, Marker marker, String msg, Throwable t) {
        if (log.isWarnEnabled(marker)) {
            log.warn(msg, t);
        }
    }

    /**
     * 输出warn级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void warn(Logger log, Marker marker, String format, Object arg1, Object arg2) {
        if (log.isWarnEnabled(marker)) {
            log.warn(format, arg1, arg2);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param msg 日志信息
     */
    public static void info(Logger log, String msg) {
        if (log.isInfoEnabled()) {
            log.info(msg);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg the argument
     */
    public static void info(Logger log, String format, Object arg) {
        if (log.isInfoEnabled()) {
            log.info(format, arg);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void info(Logger log, String format, Object[] argArray) {
        if (log.isInfoEnabled()) {
            log.info(format, argArray);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void info(Logger log, String msg, Throwable t) {
        if (log.isInfoEnabled()) {
            log.info(msg, t);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void info(Logger log, String format, Object arg1, Object arg2) {
        if (log.isInfoEnabled()) {
            log.info(format, arg1, arg2);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg 日志信息
     */
    public static void info(Logger log, Marker marker, String msg) {
        if (log.isInfoEnabled(marker)) {
            log.info(marker, msg);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg the argument
     */
    public static void info(Logger log, Marker marker, String format, Object arg) {
        if (log.isInfoEnabled(marker)) {
            log.info(format, arg);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void info(Logger log, Marker marker, String format, Object[] argArray) {
        if (log.isInfoEnabled(marker)) {
            log.info(format, argArray);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void info(Logger log, Marker marker, String msg, Throwable t) {
        if (log.isInfoEnabled(marker)) {
            log.info(msg, t);
        }
    }

    /**
     * 输出info级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void info(Logger log, Marker marker, String format, Object arg1, Object arg2) {
        if (log.isInfoEnabled(marker)) {
            log.info(format, arg1, arg2);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param msg 日志信息
     */
    public static void debug(Logger log, String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg the argument
     */
    public static void debug(Logger log, String format, Object arg) {
        if (log.isDebugEnabled()) {
            log.debug(format, arg);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void debug(Logger log, String format, Object[] argArray) {
        if (log.isDebugEnabled()) {
            log.debug(format, argArray);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void debug(Logger log, String msg, Throwable t) {
        if (log.isDebugEnabled()) {
            log.debug(msg, t);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void debug(Logger log, String format, Object arg1, Object arg2) {
        if (log.isDebugEnabled()) {
            log.debug(format, arg1, arg2);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg 日志信息
     */
    public static void debug(Logger log, Marker marker, String msg) {
        if (log.isDebugEnabled(marker)) {
            log.debug(marker, msg);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg the argument
     */
    public static void debug(Logger log, Marker marker, String format, Object arg) {
        if (log.isDebugEnabled(marker)) {
            log.debug(format, arg);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void debug(Logger log, Marker marker, String format, Object[] argArray) {
        if (log.isDebugEnabled(marker)) {
            log.debug(format, argArray);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void debug(Logger log, Marker marker, String msg, Throwable t) {
        if (log.isDebugEnabled(marker)) {
            log.debug(msg, t);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void debug(Logger log, Marker marker, String format, Object arg1, Object arg2) {
        if (log.isDebugEnabled(marker)) {
            log.debug(format, arg1, arg2);
        }
    }

    /**
     * 输出debug级别日志
     * 
     * @param log 对象
     * @param msg 日志信息
     */
    public static void trace(Logger log, String msg) {
        if (log.isTraceEnabled()) {
            log.trace(msg);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg the argument
     */
    public static void trace(Logger log, String format, Object arg) {
        if (log.isTraceEnabled()) {
            log.trace(format, arg);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void trace(Logger log, String format, Object[] argArray) {
        if (log.isTraceEnabled()) {
            log.trace(format, argArray);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void trace(Logger log, String msg, Throwable t) {
        if (log.isTraceEnabled()) {
            log.trace(msg, t);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void trace(Logger log, String format, Object arg1, Object arg2) {
        if (log.isTraceEnabled()) {
            log.trace(format, arg1, arg2);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg 日志信息
     */
    public static void trace(Logger log, Marker marker, String msg) {
        if (log.isTraceEnabled(marker)) {
            log.trace(marker, msg);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg the argument
     */
    public static void trace(Logger log, Marker marker, String format, Object arg) {
        if (log.isTraceEnabled(marker)) {
            log.trace(format, arg);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param argArray an array of arguments
     */
    public static void trace(Logger log, Marker marker, String format, Object[] argArray) {
        if (log.isTraceEnabled(marker)) {
            log.trace(format, argArray);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param msg the message accompanying the exception
     * @param t the exception (throwable) to log
     */
    public static void trace(Logger log, Marker marker, String msg, Throwable t) {
        if (log.isTraceEnabled(marker)) {
            log.trace(msg, t);
        }
    }

    /**
     * 输出trace级别日志
     * 
     * @param log 对象
     * @param marker 标记
     * @param format the format string
     * @param arg1 the first argument
     * @param arg2 the second argument
     */
    public static void trace(Logger log, Marker marker, String format, Object arg1, Object arg2) {
        if (log.isTraceEnabled(marker)) {
            log.trace(format, arg1, arg2);
        }
    }
}
