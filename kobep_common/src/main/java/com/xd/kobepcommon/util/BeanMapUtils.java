package com.xd.kobepcommon.util;

import org.springframework.cglib.beans.BeanMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: map与bean转换
 **/
public class BeanMapUtils {
    private static Pattern ANO_PATTERN = Pattern.compile("(.*)");

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) throws InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = maps.size(); i < size; i++) {
                map = maps.get(i);
                bean = clazz.newInstance();
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * 实体转换为map
     *
     * @param obj
     * @return
     */
    public static Map getMap(Object obj) {
        Map map = new HashMap();
        // System.out.println(obj.getClass());
        // 获取f对象对应类中的所有属性域
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null) {
                    String value = o.toString();
                    //根据序列号的注解格式化日期
                    if (o instanceof Timestamp) {
                        try {
                            String name = varName.substring(0, 1).toUpperCase() + varName.substring(1);
                            Method method = obj.getClass().getDeclaredMethod("get" + name);
                            if (method != null) {
                                Annotation[] annotations = method.getDeclaredAnnotations();
                                for (Annotation annotation : annotations) {
                                    String str = annotation.toString();
                                    if (str.trim().startsWith("@org.apache.struts2.json.annotations.JSON")) {
                                        Matcher matcher = ANO_PATTERN.matcher(str);
                                        if (matcher.find()) {
                                            String vals = matcher.group().replace("(", "").replace(")", "");
                                            String[] valArray = vals.split(",");
                                            for (String valStr : valArray) {
                                                if (valStr.trim().startsWith("format")) {
                                                    if (valStr.split("=").length > 1) {
                                                        String format = valStr.split("=")[1];
                                                        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
                                                        value = sdf1.format(o);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                    map.put(varName, value);
                }
                // System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
