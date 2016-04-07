/**
 * 
 */
package works.tonny.apps.deploy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author 祥栋
 * @date 2012-11-22
 * @version 1.0.0
 */
public interface SchemaService {
	/**
	 * 创建新的schema
	 * 
	 * @param id
	 * @param writePoolId
	 * @param readPoolId
	 */
	void createSchema(Schema schema);

	/**
	 * 更新schema,更新后的数据如果schema已被使用,则重启应用生效
	 * 
	 * @param schema
	 */
	void updateSchema(Schema schema);

	/**
	 * 删除schema
	 * 
	 * @param id
	 */
	void deleteSchema(String id);

	/**
	 * 读取schema信息
	 * 
	 * @param pool
	 */
	Schema getSchema(String id);

	/**
	 * 新建数据库连接数据源
	 * 
	 * @param pool
	 */
	void newDataSource(Pool pool);

	/**
	 * 更新数据源信息
	 * 
	 * @param pool
	 */
	void updateDataSource(Pool pool);

	/**
	 * 读取数据源信息
	 * 
	 * @param pool
	 */
	Pool getDataSource(String id);

	/**
	 * 删除数据源信息
	 * 
	 * @param id
	 */
	void deleteDataSource(String id);

	/**
	 * 查询所有的schema
	 * 
	 * @return
	 */
	List<Schema> schemas();

	/**
	 * 查询所有的数据源
	 * 
	 * @return
	 */
	List<Pool> pools();

	/**
	 * 测试数据源
	 * 
	 * @param pool
	 * @return
	 */
	String testPool(Pool pool);

	/**
	 * 数据库类型
	 * 
	 * @return
	 */
	String getType();

	/**
	 * 创建数据库对象
	 * 
	 * @throws SQLException
	 */
	void createObjects(Connection conn, String schema, String sql) throws SQLException;
}
