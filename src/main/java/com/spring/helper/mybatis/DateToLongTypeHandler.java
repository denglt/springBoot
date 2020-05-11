package com.spring.helper.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 当我们进行数据库的读取操作的时候，秒数就会自动转为Date对象;
 * 但是对于：insert 或 update 操作时必须指定下面之一：
 *    1、jdbcType = BIGINT
 *    2、typeHandler = com.spring.helper.mybatis.DateToLongTypeHandler
 *
 *  如果是Mybatis-plus, 在model使用@TableField 来指定 jdbcType = JdbcType.BIGINT or typeHandler = DateToLongTypeHandler.class
 *
 * @Description:
 * @Package: com.spring.helper.mybatis
 * @Author: denglt
 * @Date: 2020/5/11 16:17
 * @Copyright: 版权归 HSYUNTAI 所有
 */

@MappedJdbcTypes(value = {JdbcType.BIGINT}) // 这个必须要指定，不然会把mybatis默认的DateTypeHandler给屏蔽掉。
@MappedTypes({Date.class})
public class DateToLongTypeHandler extends BaseTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getTime());
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long result = rs.getLong(columnName);
        return result == 0 && rs.wasNull() ? null : new Date(result);
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long result = rs.getLong(columnIndex);
        return result == 0 && rs.wasNull() ? null : new Date(result);
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        long result = cs.getLong(columnIndex);
        return result == 0 && cs.wasNull() ? null : new Date(result);
    }
}
