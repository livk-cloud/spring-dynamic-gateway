package com.livk.cloud.sys.support;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * InetAddressTypeHandler
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
@SuppressWarnings("unused")
@MappedTypes(InetAddress.class)
@MappedJdbcTypes(JdbcType.BIGINT)
public class InetAddressTypeHandler implements TypeHandler<InetAddress> {

	@Override
	public void setParameter(PreparedStatement ps, int i, InetAddress parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setLong(i, ip2Long(parameter));
	}

	@Override
	public InetAddress getResult(ResultSet rs, String columnName) throws SQLException {
		return long2Ip(rs.getLong(columnName));
	}

	@Override
	public InetAddress getResult(ResultSet rs, int columnIndex) throws SQLException {
		return long2Ip(rs.getLong(columnIndex));
	}

	@Override
	public InetAddress getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return long2Ip(cs.getLong(columnIndex));
	}

	public Long ip2Long(InetAddress address) {
		String ipAddress = address.getHostAddress();
		String[] ips = ipAddress.split("\\.");
		return (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16) + (Long.parseLong(ips[2]) << 8)
				+ (Long.parseLong(ips[3]));
	}

	public InetAddress long2Ip(Long ipLong) {
		String ipStr = (ipLong >>> 24) + "." + (ipLong >>> 16 & 0xFF) + "." + (ipLong >>> 8 & 0xFF) + "."
				+ (ipLong & 0xFF);
		try {
			return InetAddress.getByName(ipStr);
		}
		catch (UnknownHostException e) {
			return null;
		}
	}

}
