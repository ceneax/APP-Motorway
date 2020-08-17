package ceneax.app.motorway.util;

import android.content.Context;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {

    /**
     * 获取 泛型.class
     * @param clazz clazz
     * @param i 第几个泛型
     * @return 返回Class
     */
    public static Class getTClass(Class clazz, int i) {
        Type genType = clazz.getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class) params[i];
    }

}
