package org.lr.handler.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTypeHandler extends BaseTypeHandler<List<Object>> {

    private static final String TYPE_NAME_VARCHAR = "varchar";
    private static final String TYPE_NAME_INTEGER = "integer";
    private static final String TYPE_NAME_BOOLEAN = "boolean";
    private static final String TYPE_NAME_NUMERIC = "numeric";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType) throws SQLException {
        Class type = null;
        Field[] fields = parameter.getClass().getDeclaredFields();
        for(Field f : fields){
            f.setAccessible(true);
            if(f.getType() == java.util.List.class){
                // 如果是List类型，得到其Generic的类型
                Type genericType = f.getGenericType();
                if(genericType == null) continue;
                // 如果是泛型参数的类型
                if(genericType instanceof ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    type = (Class)pt.getActualTypeArguments()[0];
                }
            }
        }
        String typeName = null;
        try {
            if (type.newInstance() instanceof Integer) {
                typeName = TYPE_NAME_INTEGER;
            } else if (type.newInstance() instanceof String) {
                typeName = TYPE_NAME_VARCHAR;
            } else if (type.newInstance() instanceof Boolean) {
                typeName = TYPE_NAME_BOOLEAN;
            } else if (type.newInstance() instanceof Double) {
                typeName = TYPE_NAME_NUMERIC;
            } else {
                throw new TypeException("ArrayTypeHandler parameter typeName error, your type is " + parameter.getClass().getName());
            }
        } catch (InstantiationException e) {
            throw new TypeException("ArrayTypeHandler parameter typeName error, your type is " + parameter.getClass().getName());
        } catch (IllegalAccessException e) {
            throw new TypeException("ArrayTypeHandler parameter typeName error, your type is " + parameter.getClass().getName());
        }
        // 这3行是关键的代码，创建Array，然后ps.setArray(i, array)就可以了
        Connection conn = ps.getConnection();
        Array array = conn.createArrayOf(typeName, parameter.toArray());
        ps.setArray(i, array);

    }

    @Override
    public List<Object> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return getArray(resultSet.getArray(s));
    }

    @Override
    public List<Object> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return getArray(resultSet.getArray(i));
    }

    @Override
    public List<Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return getArray(callableStatement.getArray(i));
    }

    private List<Object> getArray(Array array) {
        if (array == null) {
            return null;
        }
        try {
            return new ArrayList<>(Arrays.asList((Object[])array.getArray()));
        } catch (Exception e) {
        }
        return null;
    }
}

