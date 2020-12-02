package homework.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * ReflectUtil
 *
 * @author qrXun on 2020/11/27
 */
public class ReflectUtil {

    /**
     * 获取对应字段读方法
     *
     * @param propertyName 类属性名
     * @param clazz        类对应 class
     * @return 对应读方法
     * @throws IntrospectionException
     */
    public static Method getReadMethod(String propertyName, Class<?> clazz) throws IntrospectionException {
        return new PropertyDescriptor(propertyName, clazz).getReadMethod();
    }

    /**
     * 获取对应字段写方法
     *
     * @param propertyName 类属性名
     * @param clazz        类对应 class
     * @return 对应写方法
     * @throws IntrospectionException
     */
    public static Method getWriteMethod(String propertyName, Class<?> clazz) throws IntrospectionException {
        return new PropertyDescriptor(propertyName, clazz).getWriteMethod();
    }

    /**
     * 获取指定方法
     *
     * @param methodName 方法名称
     * @param clazz      对应 class
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getSpecifyMethod(String methodName, Class<?> clazz) throws NoSuchMethodException {
        return clazz.getMethod(methodName, Object.class);
    }

    /**
     * 获取接口类型的泛型类型
     *
     * @param interfaceServiceClass 泛型接口类型
     * @return 返回泛型类型
     * @throws ClassNotFoundException
     */
    public static Class<?> getGenericTypeClassForInterface(Class<?> interfaceServiceClass) throws ClassNotFoundException {
        ParameterizedType parameterizedType = (ParameterizedType) interfaceServiceClass.getGenericInterfaces()[0];
        return Class.forName(parameterizedType.getActualTypeArguments()[0].getTypeName());
    }

    public static Class<?> getGenericTypeClass(Class<?> normalClass){
        ParameterizedType parameterizedType = (ParameterizedType) normalClass.getGenericSuperclass();
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

}
