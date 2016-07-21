package works.tonny.apps.support.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.llama.library.utils.DBUtils;
import org.llama.library.utils.PageList;
import org.llama.library.utils.ThreadLocalMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Tonny on 2016/7/18.
 */

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
        ResultHandler.class})})
public class MysqlPageQueryInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (ThreadLocalMap.getInstance().getObject(PageList.class) == null) {
            return invocation.proceed();
        }
        Object target = invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String originalSql = boundSql.getSql().trim();
        Object parameterObject = boundSql.getParameterObject();
        String countSql = makeCountString(originalSql);
        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
            parameterHandler.setParameters(countStmt);
            rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            ThreadLocalMap.getInstance().putObject(PageList.class, count);
        } catch (Exception e) {
            throw e;
        } finally {
            DBUtils.closeQuietly(rs);
            DBUtils.closeQuietly(countStmt);
            DBUtils.closeQuietly(connection);
        }

        return invocation.proceed();
    }

    private String makeCountString(String originalSql) {
        return "select count(0) from (" + originalSql.replaceAll("(?i)limit\\s+\\?\\s*,\\s*\\?", "") + ") query";
    }

    /**
     * 复制BoundSql对象
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Map parameterObject = (Map) boundSql.getParameterObject();
        List<ParameterMapping> newMapper = parameterMappings.subList(0, parameterMappings.size() - 2);


        Map map = new HashMap();
        for (ParameterMapping parameterMapping : newMapper) {
            map.put(parameterMapping.getProperty(), parameterObject.get(parameterMapping.getProperty()));
        }
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, newMapper, map);
        for (ParameterMapping mapping : newMapper) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }


    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
