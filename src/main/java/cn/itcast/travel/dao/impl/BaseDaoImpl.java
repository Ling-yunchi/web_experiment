package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseDaoImpl<T> implements BaseDao<T> {
    private final Class<T> clazz;
    private String tableName;
    private String idFieldName;
    private final Map<String, Method> columnGetterMap;

    protected final JdbcTemplate jdbcTemplate;

    public BaseDaoImpl() {
        jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        Type genericType = this.getClass().getGenericSuperclass();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("无法获取泛型类型");
        }
        // 获取T的表名
        tableName = clazz.getAnnotation(cn.itcast.travel.anotation.Table.class).name();
        if (tableName == null || tableName.equals("")) {
            tableName = clazz.getSimpleName();
        }
        // 检查表是否存在
        String sql = "select count(*) from information_schema.tables where table_name = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, tableName);
        if (count == 0) {
            throw new RuntimeException("表" + tableName + "不存在");
        }
        // 获取主键名与属性名
        idFieldName = null;
        Field[] fields = clazz.getDeclaredFields();
        columnGetterMap = Arrays.stream(fields).map(field -> {
            String columnName = field.getName();
            Method getter = null;
            try {
                getter = clazz.getMethod("get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("无法获取" + columnName + "的getter方法");
            }
            return new Object[]{columnName, getter};
        }).collect(Collectors.toMap(objects -> (String) objects[0], objects -> (Method) objects[1]));
        for (Field f : fields) {
            // 获取属性上的@id注解
            if (f.isAnnotationPresent(cn.itcast.travel.anotation.Id.class)) {
                // 获取注解中的name属性
                String idName = f.getAnnotation(cn.itcast.travel.anotation.Id.class).name();
                // 如果name属性不为空，则使用name属性
                if (idName != null && !idName.equals("")) {
                    idFieldName = idName;
                } else {
                    // 如果name属性为空，则使用属性名
                    idFieldName = f.getName();
                }
            } else if ("id".equals(f.getName())) {
                idFieldName = f.getName();
            }
        }
        if (idFieldName == null) {
            throw new RuntimeException("没有找到主键, 请检查是否有@Id注解或者id属性");
        }
    }

    @Override
    public T findById(int id) {
        String sql = "select * from " + tableName + " where " + idFieldName + " = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<T>(clazz), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int save(T t) {
        StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
        StringBuilder sql2 = new StringBuilder(" values (");
        for (Map.Entry<String, Method> entry : columnGetterMap.entrySet()) {
            String columnName = entry.getKey();
            Method getter = entry.getValue();
            try {
                sql.append(columnName).append(",");
                sql2.append(getter.invoke(t)).append(",");
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("无法调用" + columnName + "的setter方法");
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        sql2.deleteCharAt(sql2.length() - 1);
        sql.append(")");
        sql2.append(")");
        sql.append(sql2);
        return jdbcTemplate.update(sql.toString());
    }


    @Override
    public int delete(int id) {
        String sql = "delete from " + tableName + " where " + idFieldName + " = ?";
        return jdbcTemplate.update(sql, id);
    }
}