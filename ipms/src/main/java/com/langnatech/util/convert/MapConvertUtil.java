package com.langnatech.util.convert;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.langnatech.util.LoggerUtil;

/**
 * map转换
 */
public class MapConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(MapConvertUtil.class);

    /**
     * 转换时对map中的key里的字符串会做忽略处理的正则串
     */
    private static final String OMIT_REG = "_";

    /**
     * 将map集合转换成Bean集合，Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
     * 
     * @param <E>
     * @param cla
     * @param mapList
     * @return
     */
    public static <E> List<E> toBeanList(Class<E> cla, List<Map<String, Object>> mapList) {
        List<E> list = new ArrayList<E>(mapList.size());
        for (Map<String, Object> map : mapList) {
            E obj = toBean(cla, map);
            list.add(obj);
        }
        return list;
    }

    /**
     * 将map转换成Bean，Bean的属性名与map的key值对应时不区分大小写，并对map中key做忽略OMIT_REG正则处理
     * 
     * @param <E>
     * @param cla
     * @param map
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    public static <E> E toBean(Class<E> cla, Map<String, Object> map) {
        // 创建对象
        E obj = null;
        try {
            obj = cla.newInstance();
            if (obj == null) {
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error("类型实例化对象失败,类型:" + cla);
            return null;
        }
        // 处理map的key
        Map<String, Object> newmap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> en : map.entrySet()) {
            newmap.put("set" + en.getKey().trim().replaceAll(OMIT_REG, "").toLowerCase(), en.getValue());
        }
        // 进行值装入
        Method[] ms = cla.getMethods();
        for (Method method : ms) {
            String mname = method.getName().toLowerCase();
            if (mname.startsWith("set")) {
                Class[] clas = method.getParameterTypes();
                Object v = newmap.get(mname);
                if (v != null && clas.length == 1) {
                    try {
                        method.invoke(obj, v);
                    } catch (Exception e) {
                        logger.error("属性值装入失败,装入方法：" + cla + "." + method.getName() + ".可装入类型" + clas[0] + ";欲装入值的类型:"
                                     + v.getClass());
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     * 
     * @param bean要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     * @throws IntrospectionException如果分析类属性失败
     * @throws IllegalAccessException如果实例化 JavaBean 失败
     * @throws InvocationTargetException如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map convertBeanToMap(Object bean, boolean isNull) throws IntrospectionException,
        IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++ ) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if ( !propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (isNull && "".equals(String.valueOf(result))) {
                    returnMap.put(propertyName, null);
                } else {
                    returnMap.put(propertyName, result);
                }
            }
        }
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param type
     * @param map
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetExceptionObject 如果有违例，请使用@exception/throws [违例类型][违例说明：异常的注释必须说明该异常的含义及什么条件下抛出该
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMapToBean(Class type, Map map) throws IntrospectionException, IllegalAccessException,
        InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++ ) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * 输入Map，输出Bean对象 new Bean(); MapObjectUtil.mapToObject(入参Map,出参Bean); 〈一句话功能简述〉 〈功能详细描述〉
     * 
     * @param map
     * @param classPath
     * @param obj void 如果有违例，请使用@exception/throws [违例类型][违例说明：异常的注释必须说明该异常的含义及什么条件下抛出该
     * @see [类、类#方法、类#成员]
     */
    public static void mapToObject(Map<String, Object> map, String classPath, Object obj) {
        @SuppressWarnings("rawtypes")
        Class clazz = null;
        try {
            clazz = Class.forName(obj.getClass().getName());
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++ ) {
                String varName = fields[i].getName();
                Object value = map.get(varName);
                if (null != value) {
                    @SuppressWarnings("unchecked")
                    Method method = clazz.getMethod(
                        "set" + varName.toUpperCase().charAt(0) + varName.subSequence(1, varName.length()),
                        String.class);
                    method.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            LoggerUtil.error(logger, e.getMessage());
        }
    }
}