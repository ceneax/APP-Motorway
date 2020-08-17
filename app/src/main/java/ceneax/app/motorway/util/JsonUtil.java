package ceneax.app.motorway.util;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class JsonUtil {

    /**
     * 将json数组转换为list
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(json, clazz);
        return Arrays.asList(arr);
    }

    /**
     * json转换为实体类
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    /**
     * 实体类转换为json
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> String beanToJson(T bean) {
        return new Gson().toJson(bean);
    }

}
