package cn.kafka.common.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.kafka.common.entity.MQMessageType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

@MappedJdbcTypes(JdbcType.INTEGER)
public class MQMessageTypeTypeHandler extends BaseTypeHandler<MQMessageType> {

    public void setNonNullParameter(PreparedStatement ps, int i, MQMessageType status, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, status.getId());
    }

    public MQMessageType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return MQMessageType.getInstance(rs.getInt(columnName));
    }

    public MQMessageType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return MQMessageType.getInstance(rs.getInt(columnIndex));
    }

    public MQMessageType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return MQMessageType.getInstance(cs.getInt(columnIndex));
    }

}
