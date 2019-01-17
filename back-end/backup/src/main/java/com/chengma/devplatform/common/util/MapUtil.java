package com.chengma.devplatform.common.util;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;


/**
 * 将map列表转成实体列表
 * Created by admin on 2016-7-12.
 */
public class MapUtil {

    /**
     * 将map列表转成实体列表
     *
     * @param maps       map列表
     * @param modelClass 实体类
     * @param <T>        泛型
     * @return List
     */
    public static <T> List<T> convertMapList2DtoList(List<Map<String, Object>> maps, Class<T> modelClass) {
        List<T> results = new ArrayList<>();
        if (maps != null && !maps.isEmpty()) {
            for (Map<String, Object> sendM : maps) {
                T obj = null;
                try {
                    obj = convertMap2Bean(sendM, modelClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (obj != null) {
                    results.add(obj);
                }
            }
        }
        return results;
    }

    /**
     * 将map转成实体
     *
     * @param map map
     * @param T   实体
     * @param <T> 泛型
     * @return 实体
     * @throws Exception 异常
     */
    public static <T> T convertMap2Bean(Map<String, Object> map, Class<T> T) throws Exception {
        if (map == null || map.size() == 0) {
            return null;
        }
        //获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            Object mvalue = map.get(key);
            if ("_".equals(key.substring(1, 2))) {
                key = key.substring(2);
            }
            newMap.put(StringUtil.lineToHump(key.toUpperCase(Locale.US).toLowerCase()), mvalue);
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(T);
        T bean = T.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            String type = descriptor.getPropertyType().toString();
            if (descriptor.getWriteMethod() != null) {
                if (newMap.keySet().contains(propertyName)) {
                    Object value = newMap.get(propertyName);
                    //这个方法不会报参数类型不匹配的错误。
                    if (value != null) {
                        //Instant是jdk1.8新增的，用1.7以下的需要注释掉
                        if (type.contains("java.time.Instant")) {
//                            Instant valueInstant = ((Timestamp) value).toInstant();
//                            BeanUtils.copyProperty(bean, propertyName, valueInstant);
                            BeanUtils.copyProperty(bean, propertyName, value);
                        } else {
                            BeanUtils.copyProperty(bean, propertyName, value);
                        }
                    }
                }
            }
        }
        return bean;
    }

}
