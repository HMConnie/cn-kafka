package cn.kafka.common.dao.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.kafka.common.entity.MQMessageStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;


@MappedJdbcTypes(JdbcType.INTEGER)
public class MQMessageStatusTypeHandler extends BaseTypeHandler<MQMessageStatus> {

    public void setNonNullParameter(PreparedStatement ps, int i, MQMessageStatus status, JdbcType jdbcType)
        throws SQLException {
        ps.setInt(i, status.getId());
    }

    public MQMessageStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return MQMessageStatus.getInstance(rs.getInt(columnName));
    }

    public MQMessageStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return MQMessageStatus.getInstance(rs.getInt(columnIndex));
    }

    public MQMessageStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return MQMessageStatus.getInstance(cs.getInt(columnIndex));
    }

}
