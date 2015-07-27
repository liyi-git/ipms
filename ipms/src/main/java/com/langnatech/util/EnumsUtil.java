package com.langnatech.util;

import java.util.ArrayList;
import java.util.List;

import com.langnatech.core.enums.BaseEnum;


public class EnumsUtil
{
    private static final String packagePath = "com.langnatech.ipms.enums";

    public static Object[] getEnumValues(String enumName)
        throws ClassNotFoundException
    {
        Class<?> clazz = Class.forName(packagePath + "." + enumName);
        if (clazz.isEnum())
        {
            Object[] objAry = clazz.getEnumConstants();
            List<Object> list = new ArrayList<Object>();
            for (Object obj : objAry)
            {
                if ( !obj.toString().equalsIgnoreCase("ILLEGAL"))
                {
                    list.add(obj);
                }
            }
            return list.toArray();
        }
        return null;
    }

    public static <T extends BaseEnum<?>> T get(Class<T> em, Integer code)
    {
        if (code == null)
            return null;
        T[] enums = em.getEnumConstants();
        for (T e : enums)
        {
            if (e.getCode() == code)
            {
                return e;
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static String mapJson(Class<? extends BaseEnum> clazz){
        BaseEnum[] enums = clazz.getEnumConstants();
        StringBuilder nameBuilder=new StringBuilder("{");
        StringBuilder idBuilder=new StringBuilder("{");
        int i=enums.length;
        for (BaseEnum e : enums)
        {
            i--;
            nameBuilder.append("'").append(((Enum)e).name()).append("' : '").append(e.getCode()).append("'");
            idBuilder.append("'").append(e.getCode()).append("' : '").append(e.getDesc()).append("'");
            if(i>0){
                nameBuilder.append(",");
                idBuilder.append(",");
            }
        }
        return nameBuilder.append(" }, ").append(idBuilder).append(" }").toString();
    }
}
