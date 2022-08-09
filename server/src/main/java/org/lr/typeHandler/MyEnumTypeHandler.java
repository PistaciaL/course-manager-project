package org.lr.typeHandler;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.lr.enumType.CCTypeEnum;
import org.lr.enumType.IdentityEnum;
import org.lr.enumType.NoticeEnum;
import org.lr.enumType.PermissionEnum;
import sun.util.resources.cldr.ps.CurrencyNames_ps;

import java.lang.reflect.InvocationTargetException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@MappedTypes(value = {CCTypeEnum.class, IdentityEnum.class, NoticeEnum.class, PermissionEnum.class})
public class MyEnumTypeHandler extends BaseTypeHandler<IEnum> {

    private Class type;

    public MyEnumTypeHandler(){

    }
    public MyEnumTypeHandler(Class<IEnum> type){
        this.type = type;
    }


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IEnum iEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,(String) iEnum.getValue());
    }

    @Override
    public IEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        IEnum e = null;
        try {
            IEnum[] enums = (IEnum[]) type.getEnumConstants();
            for(IEnum ie:enums){
                if(StringUtils.equals(string, ie.getValue().toString())){
                    e=ie;
                    break;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return e;
    }

    @Override
    public IEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public IEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
