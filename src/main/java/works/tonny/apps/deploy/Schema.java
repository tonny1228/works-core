/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.Serializable;
import java.util.Date;

import javax.sql.DataSource;

/**
 * @author 祥栋
 * @date 2012-11-20
 * @version 1.0.0
 */
public class Schema implements Serializable {

	public final static Schema EMPTY = new Schema();

	protected String id;

	protected Pool writePool;

	protected Pool readPool;

	protected String host;

	protected String coporation;

	protected String name;

	protected Date createTime;

	protected Date updateTime;
	
	protected DataSource writeDataSource;
	
	protected DataSource readDataSource;

	/**
	 * 
	 */
	public Schema() {
		super();
	}

	/**
	 * @param id
	 * @param writePool
	 * @param readPool
	 */
	public Schema(String id, Pool writePool, Pool readPool) {
		super();
		this.id = id;
		this.writePool = writePool;
		this.readPool = readPool;
	}

	/**
	 * @param id
	 * @param name
	 */
	public Schema(String id, String name, String host) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Pool getWritePool() {
		return writePool;
	}

	public void setWritePool(Pool writePool) {
		this.writePool = writePool;
	}

	public Pool getReadPool() {
		return readPool;
	}

	public void setReadPool(Pool readPool) {
		this.readPool = readPool;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCoporation() {
		return coporation;
	}

	public void setCoporation(String coporation) {
		this.coporation = coporation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the writeDataSource
	 */
	public DataSource getWriteDataSource() {
		return writeDataSource;
	}

	/**
	 * @param writeDataSource the writeDataSource to set
	 */
	public void setWriteDataSource(DataSource writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	/**
	 * @return the readDataSource
	 */
	public DataSource getReadDataSource() {
		return readDataSource;
	}

	/**
	 * @param readDataSource the readDataSource to set
	 */
	public void setReadDataSource(DataSource readDataSource) {
		this.readDataSource = readDataSource;
	}

}
