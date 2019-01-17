package com.suitong.devplatform.common.util;

/**
 * Created by Administrator on 2017/7/10/0010.
 */

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.util.StringUtils;


/**
 * Created by admin on 2016-7-12.
 */
public abstract class MapUtil {

    public static <T> List<T> convertMapList2DtoList(List<Map<String, Object>> maps, Class<T> modelClass) {
        List<T> results = new ArrayList<>();
        T newModel;
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

    public static <T> T convertMap2Bean(Map<String, Object> map, Class<T> T)
            throws Exception {
        if (map == null || map.size() == 0) {
            return null;
        }
        //获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
        Object mvalue = null;
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            mvalue = map.get(key);
            /*if (key.indexOf(CharacterConstant.UNDERLINE) != -1)
            {
                key = key.replaceAll(CharacterConstant.UNDERLINE, "");
            }*/
            if("_".equals(key.substring(1, 2))) {
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
//            String upperPropertyName = propertyName.toUpperCase();
            String type = descriptor.getPropertyType().toString();
            if (descriptor.getWriteMethod() != null) {
                if (newMap.keySet().contains(propertyName)) {
                    Object value = newMap.get(propertyName);
                    //这个方法不会报参数类型不匹配的错误。
                    if(value != null) {
                        if (type.contains("java.time.Instant")) {
                            Instant valueInstant = ((Timestamp) value).toInstant();
                            BeanUtils.copyProperty(bean, propertyName, valueInstant);
                        } else {
                            BeanUtils.copyProperty(bean, propertyName, value);
                        }
                    }
                }
            }
        }
        return bean;
    }


    public static <T> T convertToString(Map<String, Object> map, Class<T> T)
            throws Exception {
        if (map == null || map.size() == 0) {
            return null;
        }
        //获取map中所有的key值，全部更新成大写，添加到keys集合中,与mybatis中驼峰命名匹配
        Object mvalue = null;
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            mvalue = map.get(key);
            /*if (key.indexOf(CharacterConstant.UNDERLINE) != -1)
            {
                key = key.replaceAll(CharacterConstant.UNDERLINE, "");
            }*/
            if("_".equals(key.substring(1, 2))) {
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
//            String upperPropertyName = propertyName.toUpperCase();
            String type = descriptor.getPropertyType().toString();
            if (descriptor.getWriteMethod() != null) {
                if (newMap.keySet().contains(propertyName)) {
                    Object value = newMap.get(propertyName);
                    //这个方法不会报参数类型不匹配的错误。
                    if(value != null) {
                        if (type.contains("java.time.Instant")) {
                            Instant valueInstant = ((Timestamp) value).toInstant();
                            BeanUtils.copyProperty(bean, propertyName, valueInstant);
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

