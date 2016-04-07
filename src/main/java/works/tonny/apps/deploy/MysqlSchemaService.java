/**
 * 
 */
package works.tonny.apps.deploy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * 数据库schema的管理器
 * 
 * @author 祥栋
 * @date 2012-11-22
 * @version 1.0.0
 */
public class MysqlSchemaService extends AbstractSchemaService {

	/**
	 * 
	 */
	public MysqlSchemaService() {
		type = "mysql";
	}

	/**
	 * @param schema
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	protected boolean isSchemaExists(String schema, Statement statement) throws SQLException {
		// mysql
		ResultSet rs = null;
		boolean exits = false;
		try {
			rs = statement.executeQuery("select * from INFORMATION_SCHEMA.schemata where schema_name='" + schema + "'");
			exits = rs.next();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}
		return exits;
	}

	/**
	 * @param schema
	 * @param statement
	 * @throws SQLException
	 */
	protected void doCreateSchema(String schema, Statement statement) throws SQLException {
		// mysql
        log.info("创建数据库{0}", schema);
        try {
            statement.execute("create database if not exists " + schema + " default charset utf8");
        } catch (Exception e) {

        }
        log.info("切换数据库{0}", schema);
        statement.execute("use " + schema);
	}

	/**
	 * @throws SQLException
	 * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#createObjects(java.lang.String)
	 */
	public void createObjects(Connection conn, String schema, String text) throws SQLException {
		Statement statement = conn.createStatement();
		if (schema != null)
			statement.execute("use " + schema);
		String[] sqls = text.split(";");
		for (String sql : sqls) {
			String trim = sql.replaceAll("[\\\r\\\n]", " ").trim();
			if (StringUtils.isEmpty(trim)) {
				continue;
			}
			log.info(trim);
			statement.execute(trim);
		}
		statement.close();
	}
}
