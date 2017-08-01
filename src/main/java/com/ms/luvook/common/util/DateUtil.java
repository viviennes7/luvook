package com.ms.luvook.common.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

public class DateUtil{
    public static <T> void initializeRegAndModDate(T domain){
        Class clazz = domain.getClass();
        Class[] paramType = new Class[] {Date.class};
        Object[] paramValue = new Object[] {new Date()};

        try {
            Method setRegMethod = clazz.getMethod("setRegDate", paramType);
            setRegMethod.invoke(domain, paramValue);

            Method setModMethod = clazz.getMethod("setModDate", paramType);
            setModMethod.invoke(domain, paramValue);
        }catch (Exception e) {
            //임시
            e.printStackTrace();
        }
    }
}
