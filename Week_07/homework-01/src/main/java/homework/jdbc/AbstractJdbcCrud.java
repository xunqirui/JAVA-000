package homework.jdbc;

import com.google.common.base.CaseFormat;
import homework.jdbc.util.ConnectionMethod;
import org.springframework.util.StringUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static homework.util.ReflectUtil.*;
import static homework.util.ReflectUtil.getWriteMethod;
import static javax.persistence.GenerationType.IDENTITY;


/**
 * AbstractJdbcCrud
 *
 * @author qrXun on 2020/12/1
 */
public abstract class AbstractJdbcCrud<T, R extends ConnectionMethod> implements JdbcCrud<T> {

    protected ConnectionMethod connectionMethod;

    public AbstractJdbcCrud(ConnectionMethod connectionMethod) {
        this.connectionMethod = connectionMethod;
    }

    @Override
    public void insertOne(T object) {
        try (Connection connection = connectionMethod.getConnection()) {
            connection.setAutoCommit(false);
            // 获取实体类 Class
            Class<?> clazz = object.getClass();
            Table table = clazz.getAnnotation(Table.class);
            String tableName;
            if (!StringUtils.hasText(table.name())) {
                tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
            } else {
                tableName = table.name();
            }
            String sql = "insert into " + tableName;
            // 去除注有 Transient 注解的属性
            List<Field> fieldList = getFilterList(clazz);
            StringBuilder fieldBuilder = new StringBuilder();
            StringBuilder valueBuilder = new StringBuilder();
            stringInsertSqlUnite(fieldBuilder, valueBuilder, fieldList);
            sql += (fieldBuilder + " values " + valueBuilder);
            System.out.println("当前执行的 sql 为" + sql);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < fieldList.size(); i++) {
                    Field field = fieldList.get(i);
                    preparedStatement.setObject(i + 1, getReadMethod(field.getName(), clazz).invoke(object));
                }
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertAll(List<T> listObject) {
        try (Connection connection = connectionMethod.getConnection()) {
            connection.setAutoCommit(false);
            // 获取当前泛型类型
            Class<?> clazz = getGenericTypeClass(getCurrentClass());
            // 获取当前类型去除 @Transient 注解后的所有字段
            List<Field> fieldList = getFilterList(clazz);
            // 获取 table 名称
            Table table = clazz.getAnnotation(Table.class);
            String tableName;
            if (!StringUtils.hasText(table.name())) {
                tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
            } else {
                tableName = table.name();
            }
            String sql = "insert into " + tableName;
            StringBuilder fieldBuilder = new StringBuilder();
            StringBuilder valueBuilder = new StringBuilder();
            stringInsertSqlUnite(fieldBuilder, valueBuilder, fieldList);
            sql += (fieldBuilder + "values" + valueBuilder);
            System.out.println(sql);
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < listObject.size(); i++) {
                    for (int j = 0; j < fieldList.size(); j++) {
                        Field field = fieldList.get(j);
                        preparedStatement.setObject(j + 1, getReadMethod(field.getName(), clazz).invoke(listObject.get(i)));
                    }
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> findAll() {
        // 获取对应实体类的类型
        Class<?> clazz = getGenericTypeClass(getCurrentClass());
        // 获取当前类型去除 @Transient 注解后的所有字段
        List<Field> fieldList = getFilterIncludeIdList(clazz);
        // 获取 table 名称
        Table table = clazz.getAnnotation(Table.class);
        String tableName;
        if (!StringUtils.hasText(table.name())) {
            tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
        } else {
            tableName = table.name();
        }
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select ");
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            selectSql.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()));
            if (i != (fieldList.size() - 1)) {
                selectSql.append(",");
            }
        }
        selectSql.append(" from " + tableName);
        System.out.println(selectSql.toString());
        try (Connection connection = connectionMethod.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql.toString())) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<T> list = new ArrayList<>();
                    while (resultSet.next()) {
                        T object = (T) clazz.newInstance();
                        for (Field field : fieldList) {
                            Object colmValue = resultSet.getObject(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()));
                            if (colmValue != null) {
                                if (colmValue instanceof Timestamp) {
                                    // 时间类型
                                    getWriteMethod(field.getName(), clazz).invoke(object, colmValue.toString());
                                } else if (field.getName().equals("id")) {
                                    // id
                                    getWriteMethod(field.getName(), clazz).invoke(object, Long.valueOf(colmValue.toString()));
                                } else {
                                    getWriteMethod(field.getName(), clazz).invoke(object, colmValue);
                                }
                            }
                        }
                        list.add(object);
                    }
                    return list;
                }
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 当前实际类型 Class 对象
     *
     * @return
     */
    protected abstract Class<?> getCurrentClass();

    /**
     * 新增 sql 字符串拼接
     *
     * @param fieldBuilder (field,field,field...)
     * @param valueBuilder (?,?,?,?...)
     * @param fieldList    所有属性
     */
    private void stringInsertSqlUnite(StringBuilder fieldBuilder, StringBuilder valueBuilder, List<Field> fieldList) {
        fieldBuilder.append("(");
        valueBuilder.append("(");
        int i = 0;
        for (Field field : fieldList) {
            // 循环拼接每一个字段
            // file 拼接
            fieldBuilder.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()));
            if (i != (fieldList.size() - 1)) {
                fieldBuilder.append(",");
            }

            // value 拼接
            valueBuilder.append("?");
            if (i != (fieldList.size() - 1)) {
                valueBuilder.append(",");
            }
            i++;
        }
        fieldBuilder.append(")");
        valueBuilder.append(")");
    }

    /**
     * 过滤类字段
     *
     * @param clazz
     * @return
     */
    protected List<Field> getFilterList(Class<?> clazz) {
        // 去除注有 Transient 注解的属性
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> {
                    Annotation[] annotationArray = field.getAnnotations();
                    for (Annotation annotation : annotationArray) {
                        if (annotation instanceof Transient) {
                            return false;
                        } else if (annotation instanceof GeneratedValue) {
                            GeneratedValue idAnnotation = (GeneratedValue) annotation;
                            if ("jdbc".equals(idAnnotation.generator()) && IDENTITY.equals(idAnnotation.strategy())) {
                                // 根据数据库自增，无需添加 id 属性
                                return false;
                            } else {
                                return true;
                            }
                        }

                    }
                    return true;
                }).collect(Collectors.toList());
    }

    protected List<Field> getFilterIncludeIdList(Class<?> clazz) {
        // 去除注有 Transient 注解的属性
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> {
                    Annotation[] annotationArray = field.getAnnotations();
                    for (Annotation annotation : annotationArray) {
                        if (annotation instanceof Transient) {
                            return false;
                        }

                    }
                    return true;
                }).collect(Collectors.toList());
    }


}
