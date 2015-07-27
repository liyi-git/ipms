/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.holder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.langnatech.util.EncryptUtil;
import com.langnatech.util.LoggerUtil;
import com.langnatech.util.PropertiesUtil;


/**
 * PropertiesHolder类是Spring中PropertyPlaceholderConfigurer的扩展，
 * 主要用于将应用属性文件由Spring加载后，属性可在XML配置文件、Java代码中获取，并实现属性文件中密码加密功能。
 * 
 * @author liyi
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class PropertiesHolder extends PropertyPlaceholderConfigurer
{

    private static Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);

    private static final String ENCRYPT_DES_KEY = "ABC-@-QT-#$^&*956*(*)";

    private static final Properties appProperties = new Properties();

    private PropertiesHolder()
    {
    }

    /**
     * 重写PropertyPlaceholderConfigurer类中的setLocations方法。<br/> 详细描述：接收一个Resource类型的数组作为参数，将locations标签下值都会被解析成Resource，
     * 而这个resource本身则包含了访问这个resource的方法，在这里resource代表的则是properties文件。
     * 
     * @param locations Resource类型的数组。
     * @see #setLocations(Resource[])
     */
    @Override
    public void setLocations(Resource[] locations)
    {
        super.setLocations(locations);
        // 对properties文件进行排序,先读取jar包中的属性文件,jar包外的属性配置可覆盖Jar包中的属性配置
        Arrays.sort(locations, new Comparator<Resource>()
        {

            @Override
            public int compare(Resource rs1, Resource rs2)
            {
                try
                {
                    if (ResourceUtils.JAR_URL_SEPARATOR.equals(rs1.getURL().getProtocol()))
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }
                }
                catch (IOException e)
                {
                    LoggerUtil.error(logger, e.getMessage());
                }
                return 0;
            }
        });
    }

    /** 输出所有属性文件中配置的属性配置项 **/
    @SuppressWarnings({"unused", "rawtypes"})
    private void printAllProperties(Resource[] locations)
    {
        for (Resource res : locations)
        {
            PropertiesUtil propertiesUtil = new PropertiesUtil();
            try
            {
                propertiesUtil.load(res.getInputStream());
                Enumeration enums = propertiesUtil.keys();
                while (enums.hasMoreElements())
                {
                    String propName = (String)enums.nextElement();
                    logger.debug(res.getURL().getPath().substring(res.getURL().getPath().lastIndexOf("/") + 1) + "," + propName + ","
                                 + propertiesUtil.getProperty(propName));
                }
            }
            catch (IOException e)
            {
                LoggerUtil.error(logger, e.getMessage());
            }
        }
    }

    /**
     * 覆盖Spring属性文件处理方法processProperties。<br/> 详细描述：1.将属性值获取至静态Map对象中，用于java代码中获取属性值。<br/> 2.对属性配置文件中需要解密的值进行解密。<br/>
     * 3.属性key末尾包含“.encrypted”则任务此属性需要解密，XML和代码中获取此属性时需去掉该后缀。<br/>
     * 
     * @param confFactory ConfigurableListableBeanFactory。
     * @param props Properties。
     * @see #postProcessBeanFactory(ConfigurableListableBeanFactory)
     * @throws BeansException异常，spring内部自定义的异常。
     */
    protected void processProperties(ConfigurableListableBeanFactory confFactory, Properties props)
        throws BeansException
    {
        boolean isDecrypted = getIsDecryptedProp(props);
        for (Object key : props.keySet())
        {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            if (keyStr.endsWith(".encrypted"))
            {
                String newKey = keyStr.substring(0, keyStr.lastIndexOf(".encrypted"));
                String newValue = value;
                if (isDecrypted)
                {
                    newValue = PropertiesHolder.dencryptProperty(value);
                }
                appProperties.put(newKey, newValue);
            }
            else
            {
                appProperties.put(keyStr, value);
            }
        }
        super.processProperties(confFactory, appProperties);
    }

    /**
     * 获取属性对象。<br/> 详细描述：通过属性key，获取属性对象。<br/> 使用方式：可在java代码中直接调用此静态方法。
     * 
     * @param key 属性key。
     * @return 返回查询到的属性对象。
     */
    public static Object getPropertyObj(String key)
    {
        if (appProperties == null)
        {
            logger.error("未初始化加载属性文件，请检查Spring配置");
            return null;
        }
        return appProperties.get(key.trim());
    }

    /**
     * 获取String类型属性值。<br/> 详细描述：通过属性key，获取属性值。<br/> 使用方式：java代码中可直接调用此静态方法。
     * 
     * @param key 属性key。
     * @return 返回String类型的属性值。
     */
    public static String getProperty(String key)
    {
        return (String)getPropertyObj(key);
    }

    /**
     * 获取int类型属性值。<br/> 详细描述：通过属性key，获取属性值。<br/> 使用方式：java代码中可直接调用此静态方法。
     * 
     * @param key 属性key。
     * @return 返回int类型的属性值。
     */
    public static int getIntProperty(String key)
    {
        return Integer.valueOf(getProperty(key));
    }

    /**
     * 获取boolean类型属性值。 <br/> 详细描述：通过属性key，获取属性值。<br/> 使用方式：java代码中可直接调用此静态方法。
     * 
     * @param key 属性key。
     * @return 返回boolean类型的属性值。
     */
    public static boolean getBooleanProperty(String key)
    {
        String val = getProperty(key);
        if (val != null && "true".equalsIgnoreCase(val))
        {
            return true;
        }
        return false;
    }

    /**
     * 从属性文件中获取是否解密属性文件。<br/>
     * 
     * @param props 属性文件。<br/>
     * @return 返回boolean类型。
     */
    private boolean getIsDecryptedProp(Properties props)
    {
        String isDecrypted = String.valueOf(props.getProperty("core.global.isDecrypted"));
        if (isDecrypted != null && isDecrypted.equalsIgnoreCase("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 加密属性。<br/> 详细描述：对属性进行加密。<br/> 使用方式：java代码中可直接调用此静态方法。
     * 
     * @param prop 要加密的属性。
     * @return 返回加密后的属性。
     */
    public static String EncryptProperty(String prop)
    {
        return EncryptUtil.encrypt(prop, PropertiesHolder.ENCRYPT_DES_KEY);
    }

    /**
     * 解密属性。<br/> 详细描述：对加密的属性进行解密。<br/> 使用方式：java代码中可直接调用此静态方法。
     * 
     * @param prop 加密的属性。
     * @return 返回解密后的属性。
     */
    public static String dencryptProperty(String prop)
    {
        return EncryptUtil.decrypt(prop, PropertiesHolder.ENCRYPT_DES_KEY);
    }

    /**
     * 根据前缀获取符合条件的所有properties属性值。 详细描述：根据前缀进行匹配，找出符合该前缀条件的所有的properties的属性值，以map返回。<br/>
     * 使用方式：PropertiesHolder.getPropertiesByPrefix("前缀")方式调用。
     * 
     * @param prefix properties的key前缀。
     * @return 符合参数条件的map。
     */
    public static Map<String, Object> getPropertiesByPrefix(String prefix)
    {
        if (appProperties == null)
        {
            logger.error("未初始化加载属性文件，请检查Spring配置");
            return null;
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<String> set = appProperties.stringPropertyNames();
        for (String key : set)
        {
            if (key.startsWith(prefix))
            {
                resultMap.put(key, getPropertyObj(key));
            }
        }
        return resultMap;
    }

    /**
     * 根据前缀获取符合条件的所有properties属性值。 详细描述：根据前缀进行匹配，找出符合该前缀条件的所有的properties的属性值，以map返回。<br/>
     * 使用方式：PropertiesHolder.getPropertiesBySuffix("后缀")方式调用。
     * 
     * @param Suffix properties的key后缀。
     * @return 符合参数条件的map。
     */
    public static Map<String, Object> getPropertiesBySuffix(String suffix)
    {
        if (appProperties == null)
        {
            logger.error("未初始化加载属性文件，请检查Spring配置");
            return null;
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Set<String> set = appProperties.stringPropertyNames();
        for (String key : set)
        {
            if (key.endsWith(suffix))
            {
                resultMap.put(key, getPropertyObj(key));
            }
        }
        return resultMap;
    }
}