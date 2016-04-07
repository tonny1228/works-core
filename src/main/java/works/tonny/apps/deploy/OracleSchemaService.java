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
public class OracleSchemaService extends AbstractSchemaService {

	private String tablespace;

	/**
	 * 
	 */
	public OracleSchemaService() {
		type = "oracle";
	}

	/**
	 * @param schema
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	protected boolean isSchemaExists(String schema, Statement statement) throws SQLException {
		// oracle
		ResultSet rs = null;
		boolean exits = false;
		try {
			rs = statement.executeQuery("select * from dba_users where username='" + schema + "'");
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
		// oracle
		statement.execute("create user " + schema + " identified by " + schema + " default tablespace " + tablespace);
        statement.execute("GRANT resource,dba,CONNECT TO " + schema);
		statement.execute("alter session set current_schema=" + schema);
	}

	public String getTablespace() {
		return tablespace;
	}

	public void setTablespace(String tablespace) {
		this.tablespace = tablespace;
	}

	/**
	 * 
	 * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#createObjects(java.sql.Connection,
	 *      java.lang.String, java.lang.String)
	 */
	public void createObjects(Connection conn, String schema, String text) throws SQLException {
		Statement statement = conn.createStatement();
		statement.execute("alter session set current_schema=" + schema);
		String[] sqls = text.split(";");
		for (String sql : sqls) {
			if (StringUtils.isEmpty(sql.trim())) {
				continue;
			}
			statement.execute(sql.trim());
		}
		statement.close();
	}
}
