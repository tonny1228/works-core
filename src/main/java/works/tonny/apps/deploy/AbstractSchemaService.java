/**
 *
 */
package works.tonny.apps.deploy;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.cryptography.Decryptable;
import org.llama.library.cryptography.Encryptable;
import org.llama.library.utils.Assert;
import works.tonny.apps.AbstractService;
import works.tonny.apps.exception.ServiceException;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * 数据库schema的管理器
 *
 * @author 祥栋
 * @version 1.0.0
 * @date 2012-11-22
 */
public abstract class AbstractSchemaService extends AbstractService implements SchemaService {
    protected String type;

    private String driver;
    /**
     * 管理数据库的连接,使用单线程,需要时打开,完成后关闭
     */
    private Map<String, Pool> pools = new HashMap<String, Pool>();

    private SessionFactoryManager sessionFactoryManager;

    private Encryptable encryptable;

    private Decryptable decryptable;

    private DataSource dataSource;

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#createSchema(works.tonny.deploy.zxtx.apps.schema.Schema)
     */
    public void createSchema(Schema schema) {

        Connection conn = null;
        PreparedStatement prepareStatement = null;
        try {
            conn = createConnection("default");

            if (schema.getWritePool() != null) {
                schema.setWritePool(getPoolInfo(conn, schema.getWritePool().getId()));
            }
            if (schema.getReadPool() != null) {
                schema.setReadPool(getPoolInfo(conn, schema.getReadPool().getId()));
            }

            Connection connection = null;
            Statement statement = null;
            try {
                connection = createConnection(schema.getWritePool() != null ? schema.getWritePool().getHost()
                        : "default");
                boolean auto = connection.getAutoCommit();
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                boolean exists = isSchemaExists(schema.getId(), statement);
                if (exists) {
                    log.warn("schema " + schema.getId() + "已存在");
                }
                doCreateSchema(schema.getId(), statement);
                createObjects(statement);
                connection.commit();
                connection.setAutoCommit(auto);
            } catch (Exception e) {
                throw new ServiceException("数据库中创建schema出错", e);
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        throw new ServiceException("数据库中创建schema出错", e);
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        throw new ServiceException("数据库中创建schema出错", e);
                    }
                }
            }

            // 插入记录
            prepareStatement = conn
                    .prepareStatement("insert into db_schema(id,name,host,corporation,write_pool,read_pool,create_time,update_time)"
                            + " values(?,?,?,?,?,?,?,?)");
            prepareStatement.setString(1, schema.getId());
            prepareStatement.setString(2, schema.getName());
            prepareStatement.setString(3, schema.getHost());
            prepareStatement.setString(4, schema.getCoporation());
            prepareStatement.setString(5, schema.getWritePool() != null ? schema.getWritePool().getId() : null);
            prepareStatement.setString(6, schema.getReadPool() != null ? schema.getReadPool().getId() : null);
            Timestamp now = new Timestamp(new Date().getTime());
            prepareStatement.setTimestamp(7, now);
            prepareStatement.setTimestamp(8, now);
            if (prepareStatement.executeUpdate() != 1) {
                throw new ServiceException("创建schema记录失败");
            }

        } catch (SQLException e) {
            throw new ServiceException("创建schema记录失败", e);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("创建schema记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("创建schema记录失败", e);
                }
            }
        }
        sessionFactoryManager.createSchema(schema);
    }

    /**
     * @param statement
     * @throws SQLException
     */
    private void createObjects(Statement statement) throws SQLException {
        Collection<Module> modules = ModuleHelper.getInstance().getModules();
        for (Module module : modules) {
            List<String> dbFiles = new ArrayList<String>();
            List<String> files = module.getResources("db-" + type);
            List<String> initFiles = module.getResources("db-" + type + "-init");
            if (files == null && initFiles == null) {
                continue;
            }
            if (files != null)
                dbFiles.addAll(files);
            if (initFiles != null)
                dbFiles.addAll(initFiles);
            try {
                for (String file : dbFiles) {
                    log.info("安装数据库{0}", file);
                    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
                    log.info("1111111111111111");
                    log.info(IOUtils.toString(inputStream));
                    log.info("1111111111111111");
                    log.info(IOUtils.toString(inputStream, "GBK"));
                    log.info("1111111111111111");
                    log.info(IOUtils.toString(inputStream, "utf-8"));
                    log.info("1111111111111111");
                    log.info(IOUtils.toString(inputStream, "iso-8859-1"));
                    String text = IOUtils.toString(inputStream, "GBK");
                    inputStream.close();
                    // 创建表,初始化语句
                    // statement.execute(text);
                    String[] sqls = text.split(";");
                    for (String sql : sqls) {
                        if (StringUtils.isEmpty(sql.trim())) {
                            continue;
                        }
                        try {
                            statement.execute(sql);
                        } catch (SQLException e) {
                            this.log.warn("sql语句{0}执行出错{1}", sql, e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                throw new ServiceException("sql配置文件读取出错", e);
            }
        }
    }

    /**
     * 在数据库中创建schema
     *
     * @param schema    名称
     * @param statement 查询
     * @throws SQLException
     */
    protected abstract void doCreateSchema(String schema, Statement statement) throws SQLException;

    /**
     * 判断待创建的schema是否存在
     *
     * @param schema    schema信息
     * @param statement 查询
     * @return 是否存在
     * @throws SQLException
     */
    protected abstract boolean isSchemaExists(String schema, Statement statement) throws SQLException;

    /**
     * 根据数据库配置创建管理连接
     *
     * @param host
     * @return
     * @throws SQLException
     */
    private Connection createConnection(String host) throws SQLException {
        if (host.equals("default")) {
            return dataSource.getConnection();
        }
        Pool pool = pools.get(host);
        Assert.notNull(pool, "待创建的schema host[" + host + "]是未知的");
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(pool.getUrl(), pool.getUsername(), pool.getPassword());
        } catch (ClassNotFoundException e) {
            throw new SQLException("数据库驱动错误,无法创建schema");
        }
        return connection;
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#updateSchema(works.tonny.deploy.zxtx.apps.schema.Schema)
     */
    public void updateSchema(Schema schema) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("update db_schema set name=?,host=?,corporation=?,write_pool=?,read_pool=?,update_time=? where id=?");
            prepareStatement.setString(1, schema.getName());
            prepareStatement.setString(2, schema.getHost());
            prepareStatement.setString(3, schema.getCoporation());
            prepareStatement.setString(4, schema.getWritePool() != null ? schema.getWritePool().getId() : null);
            prepareStatement.setString(5, schema.getReadPool() != null ? schema.getReadPool().getId() : null);
            Timestamp now = new Timestamp(new Date().getTime());
            prepareStatement.setTimestamp(6, now);
            prepareStatement.setString(7, schema.getId());
            try {
                if (prepareStatement.executeUpdate() != 1) {
                    throw new ServiceException("创建schema记录失败");
                }
            } catch (SQLException e) {
                log.warn("创建出错", e);
            }
        } catch (Exception e) {
            throw new ServiceException("更新schema记录失败", e);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("更新schema记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("更新schema记录失败", e);
                }
            }
        }
        if (sessionFactoryManager.getSessionFactory(schema.getHost()) == null) {
            sessionFactoryManager.createSchema(schema);
        }
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#deleteSchema(java.lang.String)
     */
    public void deleteSchema(String id) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn.prepareStatement("delete from db_schema where id=?");
            prepareStatement.setString(1, id);
            if (prepareStatement.executeUpdate() != 1) {
                throw new ServiceException("删除schema记录失败");
            }
        } catch (Exception e) {
            throw new ServiceException("删除schema记录失败", e);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("删除schema记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("删除schema记录失败", e);
                }
            }
        }
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#newDataSource(works.tonny.deploy.zxtx.apps.schema.Pool)
     */
    public void newDataSource(Pool pool) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        try {
            pool.setId(UUID.randomUUID().toString());
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("insert into db_pool(id,name,host,port,url,username,password,initial_size,min_size,max_size,create_time,update_time)"
                            + " values(?,?,?,?,?,?,?,?,?,?,?,?)");
            prepareStatement.setString(1, pool.getId());
            prepareStatement.setString(2, pool.getName());
            prepareStatement.setString(3, pool.getHost());
            prepareStatement.setInt(4, pool.getPort());
            prepareStatement.setString(5, pool.getUrl());
            prepareStatement.setString(6, pool.getUsername());
            if (encryptable != null) {
                prepareStatement.setString(7, encryptable.encrypt(pool.getPassword()));
            } else {
                prepareStatement.setString(7, pool.getPassword());
            }
            prepareStatement.setInt(8, pool.getInitialSize());
            prepareStatement.setInt(9, pool.getMinSize());
            prepareStatement.setInt(10, pool.getMaxSize());
            Timestamp now = new Timestamp(new Date().getTime());
            prepareStatement.setTimestamp(11, now);
            prepareStatement.setTimestamp(12, now);
            if (prepareStatement.executeUpdate() != 1) {
                throw new ServiceException("创建pool记录失败");
            }
        } catch (Exception e) {
            throw new ServiceException("创建pool记录失败", e);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("创建pool记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("创建pool记录失败", e);
                }
            }
        }
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#updateDataSource(works.tonny.deploy.zxtx.apps.schema.Pool)
     */
    public void updateDataSource(Pool pool) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("update db_pool set name=?,host=?,port=?,url=?,username=?,password=?,initial_size=?,min_size=?,max_size=?,update_time=? where id=?");
            prepareStatement.setString(1, pool.getName());
            prepareStatement.setString(2, pool.getHost());
            prepareStatement.setInt(3, pool.getPort());
            prepareStatement.setString(4, pool.getUrl());
            prepareStatement.setString(5, pool.getUsername());
            if (encryptable != null) {
                prepareStatement.setString(6, encryptable.encrypt(pool.getPassword()));
            } else {
                prepareStatement.setString(6, pool.getPassword());
            }
            prepareStatement.setInt(7, pool.getInitialSize());
            prepareStatement.setInt(8, pool.getMinSize());
            prepareStatement.setInt(9, pool.getMaxSize());
            Timestamp now = new Timestamp(new Date().getTime());
            prepareStatement.setTimestamp(10, now);
            prepareStatement.setString(11, pool.getId());
            if (prepareStatement.executeUpdate() != 1) {
                throw new ServiceException("更新pool记录失败");
            }
        } catch (Exception e) {
            throw new ServiceException("更新pool记录失败", e);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("更新pool记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("更新pool记录失败", e);
                }
            }
        }
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#deleteDataSource(java.lang.String)
     */
    public void deleteDataSource(String id) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn.prepareStatement("delete from db_pool where id=?");
            prepareStatement.setString(1, id);
            if (prepareStatement.executeUpdate() != 1) {
                throw new ServiceException("删除pool记录失败");
            }
        } catch (Exception e) {
            throw new ServiceException("删除pool记录失败", e);
        } finally {
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("删除pool记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("删除pool记录失败", e);
                }
            }
        }
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#schemas()
     */
    public List<Schema> schemas() {
        List<Schema> list = new ArrayList<Schema>();
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("select id,name,host,corporation,write_pool,read_pool,create_time,update_time from db_schema order by create_time");
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Schema schema = new Schema();
                schema.setId(rs.getString(1));
                schema.setName(rs.getString(2));
                schema.setHost(rs.getString(3));
                schema.setCoporation(rs.getString(4));
                schema.setCreateTime(rs.getTimestamp(7));
                schema.setUpdateTime(rs.getTimestamp(8));
                String poolId = rs.getString(5);
                String rpoolId = rs.getString(6);
                if (StringUtils.isNotEmpty(poolId)) {
                    schema.setWritePool(getPoolInfo(conn, poolId));
                }
                if (StringUtils.isNotEmpty(rpoolId)) {
                    schema.setReadPool(getPoolInfo(conn, rpoolId));
                }
                list.add(schema);
            }
            rs.close();
        } catch (Exception e) {
            throw new ServiceException("查询schema记录失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询schema记录失败", e);
                }
            }
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询schema记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询schema记录失败", e);
                }
            }
        }
        return list;
    }

    /**
     * @param conn
     * @param poolId
     * @throws SQLException
     */
    public Pool getPoolInfo(Connection conn, String poolId) throws SQLException {
        ResultSet rs1 = null;
        try {
            PreparedStatement statement = conn
                    .prepareStatement("select id,name,host,port,url,username,password,initial_size,min_size,max_size,create_time,update_time from db_pool where id=?");
            statement.setString(1, poolId);
            rs1 = statement.executeQuery();
            if (rs1.next()) {
                Pool pool = new Pool(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getInt(4),
                        rs1.getString(5), rs1.getString(6), rs1.getString(7), rs1.getInt(8), rs1.getInt(9),
                        rs1.getInt(10));
                if (decryptable != null) {
                    pool.setPassword(decryptable.decrypt(pool.getPassword()));
                }
                return pool;
            }
            statement.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs1 != null)
                rs1.close();
        }
        return null;
    }

    /**
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#getDataSource(java.lang.String)
     */
    public Pool getDataSource(String id) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("select id,name,host,port,url,username,password,initial_size,min_size,max_size,create_time,update_time from db_pool where id=? order by create_time");
            prepareStatement.setString(1, id);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Pool pool = new Pool(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
                if (decryptable != null) {
                    pool.setPassword(decryptable.decrypt(pool.getPassword()));
                }
                pool.setCreateTime(rs.getDate(11));
                pool.setUpdateTime(rs.getDate(12));
                return pool;
            }
        } catch (Exception e) {
            throw new ServiceException("查询pool记录失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询pool记录失败", e);
                }
            }
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询pool记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询pool记录失败", e);
                }
            }
        }
        return null;
    }

    /**
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#getSchema(java.lang.String)
     */
    public Schema getSchema(String id) {
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("select id,name,host,corporation,write_pool,read_pool,create_time,update_time from db_schema where id=? order by create_time");
            prepareStatement.setString(1, id);
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Schema schema = new Schema();
                schema.setId(rs.getString(1));
                schema.setName(rs.getString(2));
                schema.setHost(rs.getString(3));
                schema.setCoporation(rs.getString(4));
                schema.setCreateTime(rs.getTimestamp(7));
                schema.setUpdateTime(rs.getTimestamp(8));
                String poolId = rs.getString(5);
                String rpoolId = rs.getString(6);
                if (StringUtils.isNotEmpty(poolId)) {
                    schema.setWritePool(getPoolInfo(conn, poolId));
                }
                if (StringUtils.isNotEmpty(rpoolId)) {
                    schema.setReadPool(getPoolInfo(conn, rpoolId));
                }
                return schema;
            }
            rs.close();
        } catch (Exception e) {
            throw new ServiceException("查询schema记录失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询schema记录失败", e);
                }
            }
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询schema记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询schema记录失败", e);
                }
            }
        }
        return null;
    }

    /**
     * @throws SQLException
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#pools()
     */
    public List<Pool> pools() {
        List<Pool> list = new ArrayList<Pool>();
        Connection conn = null;
        PreparedStatement prepareStatement = null;
        ResultSet rs = null;
        try {
            conn = createConnection("default");
            prepareStatement = conn
                    .prepareStatement("select id,name,host,port,url,username,password,initial_size,min_size,max_size,create_time,update_time from db_pool order by create_time");
            rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Pool pool = new Pool(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
                if (decryptable != null) {
                    pool.setPassword(decryptable.decrypt(pool.getPassword()));
                }
                pool.setCreateTime(rs.getDate(11));
                pool.setUpdateTime(rs.getDate(12));
                list.add(pool);
            }
        } catch (Exception e) {
            throw new ServiceException("查询pool记录失败", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询pool记录失败", e);
                }
            }
            if (prepareStatement != null) {
                try {
                    prepareStatement.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询pool记录失败", e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new ServiceException("查询pool记录失败", e);
                }
            }
        }
        return list;
    }

    /**
     * @see works.tonny.deploy.zxtx.apps.schema.SchemaService#testPool(works.tonny.deploy.zxtx.apps.schema.Pool)
     */
    public String testPool(Pool pool) {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(pool.getUrl(), pool.getUsername(), pool.getPassword());
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
        }
        return null;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Map<String, Pool> getPools() {
        return pools;
    }

    public void setPools(Map<String, Pool> pools) {
        this.pools = pools;
    }

    public SessionFactoryManager getSessionFactoryManager() {
        return sessionFactoryManager;
    }

    public void setSessionFactoryManager(SessionFactoryManager sessionFactoryManager) {
        this.sessionFactoryManager = sessionFactoryManager;
    }

    public Encryptable getEncryptable() {
        return encryptable;
    }

    public void setEncryptable(Encryptable encryptable) {
        this.encryptable = encryptable;
    }

    public Decryptable getDecryptable() {
        return decryptable;
    }

    public void setDecryptable(Decryptable decryptable) {
        this.decryptable = decryptable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
