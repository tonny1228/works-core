/**
 *
 */
package works.tonny.apps.deploy;

import org.llama.library.log.Log;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collection;
import java.util.List;

/**
 * hibernate sessionfactory 管理器工厂类
 *
 * @author 祥栋
 * @version 1.0.0
 * @date 2012-11-15
 */
public class AnnotationSessionFactoryManagerFactory extends SessionFactoryManagerFactory implements FactoryBean,
        InitializingBean, ApplicationContextAware, ApplicationListener {

    public AnnotationSessionFactoryManagerFactory() {

    }

    /**
     * 属性注入后创建默认的session factory
     *
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        sessionFactoryManager = new AnnotationSessionFactoryManager(this);
        ((AnnotationSessionFactoryManager) sessionFactoryManager).setSchemaService(schemaService);
        initSchema(Schema.EMPTY);
    }

    /**
     * spring加载完成后的处理
     *
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (!(event instanceof ContextRefreshedEvent)) {
            return;
        }
        if (schemaService == null) {
            return;
        }
        schemaService.setSessionFactoryManager(sessionFactoryManager);
        try {
            List<Schema> schemas = schemaService.schemas();
            for (Schema schema : schemas) {
                initSchema(schema);
            }
            multiSchemaSupport = true;
        } catch (Exception e) {
            multiSchemaSupport = false;
            logger.warn("没有查到schema信息,将不使用多schema:{0}", e.getMessage());
        }
        // initSchema(Schema.EMPTY);
        Collection<Module> modules = ModuleHelper.getInstance().getModules();
        if (modules != null)
            for (Module module : modules) {
//                try {
//                    //module.getHandler().installDatabase(sessionFactoryManager.getSchemas(), schemaService);
//                } catch (DeployException e) {
//                    Log.error(e);
//                    System.exit(0);
//                }
            }
    }

    /**
     * @param schema
     */
    protected void initSchema(Schema schema) {
//		bindReadDataSource(schema);
//		bindWriteDataSource(schema);
        sessionFactoryManager.createSchema(schema);
    }

    /**
     *
     private void wuyong() { DataSource ds = (DataSource) app.getBean(writeDataSource); Connection
     * conn = null; Statement statement = null; ResultSet rs = null; try { conn =
     * ds.getConnection(); statement = conn.createStatement(); rs =
     * statement.executeQuery("select s.id,s.write_pool,s.read_pool,host from db_schema s"); while
     * (rs.next()) { Schema schema = new Schema(); schema.setId(rs.getString("id")); String poolId =
     * rs.getString("write_pool"); String rpoolId = rs.getString("read_pool"); String host =
     * rs.getString("host"); if (StringUtils.isNotEmpty(poolId)) {
     * schema.setWritePool(getPoolInfo(conn, poolId)); } if (StringUtils.isNotEmpty(rpoolId)) {
     * schema.setReadPool(getPoolInfo(conn, rpoolId)); } schema.setHost(host);
     * sessionFactoryManager.createSchema(schema); } rs.close(); } catch (SQLException e) {
     * logger.warn("没有查到schema信息,将不使用多schema:{0}", e.getMessage()); } finally { if (rs != null) try
     * { rs.close(); } catch (SQLException e) { logger.error(e); } if (statement != null) try {
     * statement.close(); } catch (SQLException e) { logger.error(e); } if (conn != null) try {
     * conn.close(); } catch (SQLException e) { logger.error(e); } } }
     */

    /**
     * @param conn
     * @param poolId
     * @throws SQLException private Pool getPoolInfo(Connection conn, String poolId) throws
     *             SQLException { // ResultSet rs1 = null; // try { // Statement statement =
     *             conn.createStatement(); // rs1 = statement // .executeQuery(
     *             "select id,url,username,password,initial_size,min_size,max_size from db_pool where id="
     *             // + poolId); // if (rs1.next()) { // Pool pool = new Pool(rs1.getString("id"),
     *             rs1.getString("url"), // rs1.getString("username"), // rs1.getString("password"),
     *             rs1.getInt("initial_size"), // rs1.getInt("max_size"), //
     *             rs1.getInt("min_size")); // if (decryptable != null) { //
     *             pool.setPassword(decryptable.decrypt(pool.getPassword())); // } // return pool;
     *             // } // statement.close(); // } catch (SQLException e) { // throw e; // } finally
     *             { // if (rs1 != null) // rs1.close(); // } return null; }
     */

    /**
     * 获取或创建只读数据源,配置信息为空返回默认数据源
     *
     * @param schema 数据源配置
     * @return 数据源
    void bindReadDataSource(Schema schema) {
    Pool readPool = schema.getReadPool();
    if (readPool == null) {
    if (readDataSource == null || !app.containsBean(readDataSource)) {
    return;
    }
    DataSource bean = (DataSource) app.getBean(readDataSource);
    schema.setReadDataSource(bean);
    return;
    }

    String name = "ds-" + readPool.getId();
    if (app.containsBean(name)) {
    DataSource bean = (DataSource) app.getBean(name);
    schema.setReadDataSource(bean);
    return;
    }

    BeanDefinition beanDefinition = new ChildBeanDefinition(readDataSource == null ? writeDataSource
    : readDataSource);
    DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app.getAutowireCapableBeanFactory();
    acf.registerBeanDefinition(name, beanDefinition);
    ComboPooledDataSource cpds = (ComboPooledDataSource) app.getBean(name);
    cpds.setJdbcUrl(readPool.getUrl());
    cpds.setUser(readPool.getUsername());
    cpds.setPassword(readPool.getPassword());
    cpds.setInitialPoolSize(readPool.getInitialSize());
    cpds.setMinPoolSize(readPool.getMinSize());
    cpds.setMaxPoolSize(readPool.getMaxSize());
    schema.setReadDataSource(cpds);
    }
     */

    /**
     * 获取或创建可写数据源,配置信息为空返回默认数据源
     *
     * @param schema 数据源配置
     * @return 数据源
    void bindWriteDataSource(Schema schema) {
    Pool writePool = schema.getWritePool();
    if (writePool == null) {
    DataSource bean = (DataSource) app.getBean(writeDataSource);
    schema.setWriteDataSource(bean);
    return;
    }

    String name = "ds-" + writePool.getId();
    if (app.containsBean(name)) {
    DataSource bean = (DataSource) app.getBean(name);
    schema.setWriteDataSource(bean);
    return;
    }

    BeanDefinition beanDefinition = new ChildBeanDefinition(writeDataSource);
    DefaultListableBeanFactory acf = (DefaultListableBeanFactory) app.getAutowireCapableBeanFactory();
    acf.registerBeanDefinition(name, beanDefinition);
    ComboPooledDataSource cpds = (ComboPooledDataSource) app.getBean(name);
    cpds.setJdbcUrl(writePool.getUrl());
    cpds.setUser(writePool.getUsername());
    cpds.setPassword(writePool.getPassword());
    cpds.setInitialPoolSize(writePool.getInitialSize());
    cpds.setMinPoolSize(writePool.getMinSize());
    cpds.setMaxPoolSize(writePool.getMaxSize());
    schema.setReadDataSource(cpds);
    }
     */

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public Object getObject() throws Exception {
        if (sessionFactoryManager == null) {
            sessionFactoryManager = new AnnotationSessionFactoryManager(this);
            sessionFactoryManager.createSchema(Schema.EMPTY);
        }
        return sessionFactoryManager;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class getObjectType() {
        return AnnotationSessionFactoryManager.class;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

}
