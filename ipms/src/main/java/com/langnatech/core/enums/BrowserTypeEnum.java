
package com.langnatech.core.enums;

/**
 * BrowserCode是浏览器类型枚举
 * 
 * @author zangchuqiang
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public enum BrowserTypeEnum implements BaseEnum<String>
{
    IE9("BROWSER_IE9", "MSIE 9.0"), IE8("BROWSER_IE8", "MSIE 8.0"), IE7("BROWSER_IE7", "MSIE 7.0"), IE6("BROWSER_IE6",
        "MSIE 6.0"), MAXTHON("BROWSER_MAXTHON", "Maxthon"), QQ("BROWSER_QQ", "QQBrowser"), GREEN("BROWSER_GREEN",
            "GreenBrowser"), SE360("BROWSER_SE360", "360SE"), FIREFOX("BROWSER_FIREFOX", "Firefox"), OPERA("BROWSER_OPERA",
                "Opera"), CHROME("BROWSER_CHROME", "Chrome"), SAFARI("BROWSER_SAFARI", "Safari"), OTHER("BROWSER_OTHER", "其它");

    private String code;

    private String desc;

    BrowserTypeEnum(String code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

}