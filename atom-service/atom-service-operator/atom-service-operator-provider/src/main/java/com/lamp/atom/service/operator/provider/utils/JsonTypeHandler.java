package com.lamp.atom.service.operator.provider.utils;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public JsonTypeHandler(Class<T> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = (Class<T>) Map.class;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, JSON.toJSONString(parameter));
	}

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return JSON.parseObject(rs.getString(columnName), type);
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return JSON.parseObject(rs.getString(columnIndex), type);
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return JSON.parseObject(cs.getString(columnIndex), type);
	}
}