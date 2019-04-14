package com.dengjk.common.utils;

import org.springframework.cglib.beans.BeanMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dengjk
 * @create 2019-04-14 22:59
 * @desc map 和bean之间的互转
 **/
public class BeanMapUitls {


    /**
     * bean转map
     *
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.forEach((k, v) -> {
            map.put(k + "", v);
        });
        return map;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }
}
