package com.wme.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/17
 * @Version: 1.0
 */
public class ReflectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 反射调用Getter方法.
     * @author Administrator
     * @param target
     * @param propertyName
     * @return  Object
     */
    public static Object invokeGetterMethod(Object target, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     * @param target
     * @param propertyName
     * @param value
     */
    public static void invokeSetterMethod(Object target, String propertyName, Object value) {
        invokeSetterMethod(target, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     *
     * @param propertyType
     * 用于查找Setter方法,为空时使用value的Class替代.
     */
    public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(target, setterMethodName, new Class[] {type}, new Object[] {value});
    }


    /**
     * 直接调用对象方法,无视private/protected修饰符.
     */
    public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] parameterType " + parameterTypes + " on target [" + object + "]");
        }

        try {
            method.setAccessible(true);
            return method.invoke(object, parameters);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod.
     * 如向上转型到Object仍无法找到, 返回null.
     */
    private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        if(object == null){
            throw new IllegalStateException("object不能为空");
        }

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("ReflectionUtils getFieldValue throws exception:{}" + e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("ReflectionUtils getFieldValue throws exception:{}" + e.getMessage());
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     * 如向上转型到Object仍无法找到,返回null.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        if(object == null){
            throw new IllegalArgumentException("object不能为空");
        }
        if(StringUtils.isBlank(fieldName)){
            throw new IllegalArgumentException("fieldName");
        }
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强行设置Field可访问.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射,获得Class定义中声明的父类的泛型参数的类型,如无法找到,返回Object.class.
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的泛型参数的类型.如无法找到,返回Object.class.
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of "
                    + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName()
                    + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

}
