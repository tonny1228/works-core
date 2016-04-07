package works.tonny.apps.support;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.sqlmapping.Query;
import org.llama.library.sqlmapping.QueryParameters;
import org.llama.library.sqlmapping.SQLMappingManager;
import org.llama.library.utils.Assert;
import org.llama.library.utils.ClassUtils;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.ThreadLocalMap;
import works.tonny.apps.auth.DataOwner;
import works.tonny.apps.auth.DataOwnerAware;
import works.tonny.apps.auth.DataOwnerConfig;
import works.tonny.apps.auth.DataOwnerConfigFactory;
import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.DepartmentType;
import works.tonny.apps.user.model.Position;
import works.tonny.apps.user.model.UserInfo;
import works.tonny.apps.user.service.OrgnizationHelper;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * hibernate dao 支持抽象类
 *
 * @param <T>
 * @author tonny
 * @version 1.1
 */
public class BaseDAOSupport<T> {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[]{};

    private static final String[] EMPTY_STRING_ARRAY = new String[]{};

    protected Logger log = LogFactory.getLogger(getClass());

    protected HibernateDAO dao;

    /**
     * dao要保存的类型
     */
    protected Class<T> entityClass;

    protected SQLMappingManager sqlMappingManager;

    protected Class<? extends DataOwner<T>> ownerClass;

    @SuppressWarnings("unchecked")
    public BaseDAOSupport() {
        // 自动获取t的类型
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            entityClass = (Class<T>) Object.class;
        }
    }

    /**
     * 初始化dao的类
     *
     * @param clz
     */
    public BaseDAOSupport(Class<T> clz) {
        this.entityClass = clz;
    }

    /**
     * 按id读取记录
     *
     * @param id
     * @return
     * @see works.tonny.apps.EntityDAO#get(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public T get(String id) {
        return (T) dao.get(entityClass, id);
    }

    public T get(Class clz, String id) {
        return (T) dao.get(clz, id);
    }

    /**
     * 删除记录
     *
     * @param o
     */
    public void delete(T o) {
        dao.delete(o);
    }

    /**
     * 根据id删除记录
     *
     * @param o
     */
    public void delete(String id) {
        dao.delete(get(id));
    }

    /**
     * 批量删除记录
     *
     * @param entities
     */
    public void deleteAll(String[] id) {
        for (int i = 0; i < id.length; i++) {
            delete(id[i]);
            if (i > 0 && i % 50 == 0) {
                dao.flush();
            }
        }
    }

    /**
     * 批量删除记录
     *
     * @param entities
     */
    public void deleteAll(Collection<T> collection) {
        dao.deleteAll(collection);
    }

    /**
     * 创建一条记录
     *
     * @param o 映射对象
     * @return id
     */
    public String save(T o) {
        setOwner(o);
        return (String) dao.save(o);
    }

    /**
     * 更新一条记录
     *
     * @param o 映射对象
     */
    public void update(T o) {
        dao.update(o);
    }

    public void flush() {
        dao.flush();
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    @Deprecated
    public PagedList<T> find(String[] names, Object[] args, String[] connectors, String[] operators, String[] order,
                             String[] defaults, int page, int pagesize) {
        StringBuffer buffer = new StringBuffer("from ");
        List<Object> arg = new ArrayList<Object>();
        List<String> paramNames = new ArrayList<String>();
        buildQuery(names, args, connectors, operators, order, defaults, buffer, paramNames, arg);
        dataOwnerFilter(buffer, arg, paramNames);
        String[] paramName = new String[paramNames.size()];
        paramName = paramNames.toArray(paramName);
        return dao.findByNamedParam(buffer.toString(), arg.toArray(), paramName, page, pagesize);
        // return dao.find(buffer.toString(), page, pagesize, arg.toArray());
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    public PagedList<T> list(String[] names, Object[] args, String[] connectors, String[] operators, String[] order,
                             String[] defaults, int offset, int limit) {
        StringBuffer buffer = new StringBuffer("from ");
        List<Object> arg = new ArrayList<Object>();
        List<String> paramNames = new ArrayList<String>();
        buildQuery(names, args, connectors, operators, order, defaults, buffer, paramNames, arg);

        String[] paramName = new String[paramNames.size()];
        paramName = paramNames.toArray(paramName);
        return dao.listByNamedParam(buffer.toString(), arg.toArray(), paramName, offset, limit);
        // return dao.find(buffer.toString(), page, pagesize, arg.toArray());
    }

    public PagedList<T> list(String queryString, Object[] args, String[] paramName, String[] order, int offset,
                             int limit) {
        StringBuffer buffer = new StringBuffer("from ");
        List<Object> arg = new ArrayList<Object>();
        List<String> paramNames = new ArrayList<String>();
        buildQuery(EMPTY_STRING_ARRAY, EMPTY_OBJECT_ARRAY, EMPTY_STRING_ARRAY, EMPTY_STRING_ARRAY, order,
                new String[]{queryString}, buffer, paramNames, arg);

        return dao.listByNamedParam(buffer.toString(), args, paramName, offset, limit);
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    @Deprecated
    public PagedList<T> find(String name, String[] appender, Object[] args, String[] paramNames, int page, int pagesize) {
        Query query = sqlMappingManager.getQuery(name);
        query.addParamNames(paramNames);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof QueryParameters) {
                query.append((QueryParameters) args[i]);
            } else {
                query.addParamter(args[i]);
            }
        }
        for (int i = 0; i < appender.length; i++) {
            query.append(appender[i]);
        }
        query.build();
        return dao.findByNamedParam(query.toString(), query.parameters(), query.getParamNames(), page, pagesize);
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    public PagedList<T> list(String name, String[] appender, Object[] args, String[] paramNames, int offset, int limit) {
        Query query = sqlMappingManager.getQuery(name);
        query.addParamNames(paramNames);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof QueryParameters) {
                query.append((QueryParameters) args[i]);
            } else {
                query.addParamter(args[i]);
            }
        }
        for (int i = 0; i < appender.length; i++) {
            query.append(appender[i]);
        }
        query.build();
        return dao.listByNamedParam(query.toString(), query.parameters(), query.getParamNames(), offset, limit);
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names
     *            字段名
     * @param args
     *            参数值
     * @param connectors
     *            连接符
     * @param operators
     *            操作符
     * @param order
     *            排序字段
     */
    // public PagedList<T> find(String name, String[] appender, Object[] args,
    // String[] paramNames,
    // int page, int pagesize) {
    // Query query = sqlMappingManager.getQuery(name);
    //
    // for (int i = 0; i < appender.length; i++) {
    // query.append(appender[i]);
    // }
    // return dao.findByNamedParam(query.toString(), paramNames, args, page,
    // pagesize);
    // }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    public List<T> find(String name, Object[] args, String[] appender) {
        Query query = sqlMappingManager.getQuery(name);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof QueryParameters) {
                query.append((QueryParameters) args[i]);
            } else {
                query.addParamter(args[i]);
            }
        }
        for (int i = 0; i < appender.length; i++) {
            query.append(appender[i]);
        }
        query.build();
        return dao.findByNamedParam(query.toString(), query.parameters(), query.getParamNames());
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    public List<T> find(String name, String[] appender, Object[] args, String[] paramNames) {
        Query query = sqlMappingManager.getQuery(name);
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof QueryParameters) {
                query.append((QueryParameters) args[i]);
            } else {
                query.addParamter(args[i]);
            }
        }
        for (int i = 0; i < appender.length; i++) {
            query.append(appender[i]);
        }
//        query.addParamter(args);
        query.build();
        return dao.findByNamedParam(query.toString(), query.parameters(), query.getParamNames());
    }

    /**
     * 根据配置组合查询语句
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     * @param paramNames
     * @param buffer     hql缓存
     * @param arg        参数
     */
    private void buildQuery(String[] names, Object[] args, String[] connectors, String[] operators, String[] order,
                            String[] defaults, StringBuffer bf, List<String> paramNames, List<Object> arg) {
        bf.append(entityClass.getSimpleName()).append(" t");
        Assert.isTrue(connectors.length == 0 || names.length == connectors.length, "要查询的参数与连接符个数不一置");
        Assert.isTrue(operators.length == 0 || names.length == operators.length, "要查询的参数与匹配符个数不一置");
        Assert.isTrue(connectors.length == 0 || connectors[0].equals(ListSupport.MUST), "第一个参数必须为must");
        if (ArrayUtils.isEmpty(args)) {
            if (defaults != null && defaults.length > 0) {
                appendParam(bf, " where ", defaults[0]);
                for (int j = 1; j < defaults.length; j++) {
                    appendParam(bf, " and ", defaults[j]);
                }
            }
            if (order.length > 0) {
                appendParam(bf, " order by ", order[0]);
                for (int i = 1; i < order.length; i++) {
                    // bf.append("t." + StringUtils.join(order, ",t."));
                    appendParam(bf, ",", order[i]);
                }

            }
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < names.length; i++) {
            if (args[i] == null) {
                continue;
            }
            if (i > 0 && connectors.length > 0 && connectors.length > i) {
                buffer.append(connectors[i]);
            } else if (i > 0 && connectors.length == 0) {
                buffer.append(ListSupport.MUST);
            }

            // if (operators.length > 0 && operators[i].equals(ListSupport.IN))
            // {
            // //appendIN(names[i], args[i], arg, buffer);
            // continue;
            // }

            // buffer.append("t.").append(names[i]);
            String paramName = makeParamName(names[i]);
            if (operators.length > 0 && operators[i].equals(ListSupport.IN_ELEMENTS)) {
                buffer.append(":").append(paramName).append(ListSupport.IN_ELEMENTS.replace("#FIELD", "t." + names[i]));
                paramNames.add(paramName);
                arg.add(args[i]);
                continue;
            }
            appendParam(buffer, "", names[i]);

            paramNames.add(paramName);
            arg.add(args[i]);
            if (operators.length > 0 && operators[i].equals(ListSupport.LIKE)) {
                String newName = paramName + "_like";
                paramNames.add(newName);
                buffer.append(ListSupport.LIKE).append(newName);
                arg.add("%" + args[i] + "%");
            } else if (operators.length > 0 && operators[i].equals(ListSupport.LLIKE)) {
                String newName = paramName + "_llike";
                paramNames.add(newName);
                buffer.append(ListSupport.LIKE).append(newName);
                arg.add("%" + args[i]);
            } else if (operators.length > 0 && operators[i].equals(ListSupport.RLIKE)) {
                String newName = paramName + "_rlike";
                paramNames.add(newName);
                buffer.append(ListSupport.LIKE).append(newName);
                arg.add(args[i] + "%");
            } else if (operators.length > 0 && operators[i].equals(ListSupport.NOT_NULL)) {
                buffer.append(ListSupport.NOT_NULL);
            } else if (operators.length > 0 && operators[i].equals(ListSupport.NULL)) {
                buffer.append(ListSupport.NULL);
            } else if (operators.length > 0
                    && (operators[i].equals(ListSupport.BETWEEN) || operators[i].equals(ListSupport.BETWEEN_N_L)
                    || operators[i].equals(ListSupport.BETWEEN_N_L_R) || operators[i]
                    .equals(ListSupport.BETWEEN_N_R))) {
                String text = StringUtils.replace(operators[i], "param", paramName);
                buffer.append(StringUtils.replace(text, "$column", "t." + names[i]));
                paramNames.add(paramName + "1");
                paramNames.add(paramName + "2");
                arg.add(((Object[]) args[i])[0]);
                arg.add(((Object[]) args[i])[1]);
            } else if (operators.length > 0) {
                buffer.append(operators[i]).append(paramName);
            } else {
                buffer.append(ListSupport.EQUALS).append(paramName);
            }

        }
        if (buffer.indexOf(ListSupport.MUST) == 0) {
            buffer.delete(0, ListSupport.MUST.length());
        }

        if (buffer.indexOf(ListSupport.SHOULD) == 0) {
            buffer.delete(0, ListSupport.SHOULD.length());
        }

        if (defaults != null && defaults.length > 0) {
            if (buffer.length() == 0) {
                appendParam(buffer, " ", defaults[0]);
                for (int j = 1; j < defaults.length; j++) {
                    // buffer.append(" and t.").append(defaults[j]);
                    appendParam(buffer, " and ", defaults[j]);
                }
            } else {
                for (int j = 0; j < defaults.length; j++) {
                    // buffer.append(" and t.").append(defaults[j]);
                    appendParam(buffer, " and ", defaults[j]);
                }
            }
        }
        if (buffer.length() > 0) {
            bf.append(" where ");
        }
        if (order.length > 0) {
            // buffer.append(" order by t." + StringUtils.join(order, ",t."));
            appendParam(buffer, " order by ", order[0]);
            for (int i = 1; i < order.length; i++) {
                // bf.append("t." + StringUtils.join(order, ",t."));
                appendParam(buffer, ",", order[i]);
            }
        }
        String string = buffer.toString();
        for (int i = paramNames.size() - 1; i >= 0; i--) {
            if (!string.matches(".*:" + paramNames.get(i) + "\\s.*") && !string.matches(".*:" + paramNames.get(i))) {
                paramNames.remove(i);
                arg.remove(i);
            }
        }
        bf.append(buffer);
    }

    /**
     * @param bf
     * @param str
     */
    public void appendParam(StringBuffer bf, String connect, String str) {
        if (str.trim().matches("\\w+\\(.*\\)")) {
            bf.append(connect).append(str);
            return;
        }
        if (str.trim().matches(".+\\sor\\s.+") || str.trim().matches(".+\\sand\\s.+")) {
            bf.append(connect).append(str);
            return;
        }
        bf.append(connect).append("t.").append(str);
    }

    /**
     * @param string
     * @return
     */
    private String makeParamName(String field) {
        return StringUtils.replace(field, ".", "_").replaceAll("\\(", "_").replaceAll("\\)", "_").trim();
    }

    /**
     * @param names
     * @param args
     * @param order
     * @param arg
     * @param buffer
     * @param i
     */
    public void appendIN(String name, Object args, List<Object> arg, StringBuffer buffer) {
        buffer.append("(t.").append(name).append(ListSupport.EQUALS);
        if (args instanceof List) {
            List a = (List) args;
            if (a.size() == 0) {
                arg.add(null);
                buffer.append(")");
                return;
            }
            arg.add(a.get(0));
            for (int j = 1; j < a.size(); j++) {
                buffer.append(ListSupport.SHOULD).append("t.").append(name).append(ListSupport.EQUALS);
                arg.add(a.get(j));
            }
            buffer.append(")");
        }

        if (args.getClass().isArray()) {
            int len = Array.getLength(args);
            if (len == 0) {
                arg.add(null);
                buffer.append(")");
                return;
            }
            arg.add(Array.get(args, 0));
            for (int j = 1; j < len; j++) {
                buffer.append(ListSupport.SHOULD).append("t.").append(name).append(ListSupport.EQUALS);
                arg.add(Array.get(args, j));
            }
            buffer.append(")");
        }
    }

    /**
     * 根据参数自动创建查询
     *
     * @param names      字段名
     * @param args       参数值
     * @param connectors 连接符
     * @param operators  操作符
     * @param order      排序字段
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String[] names, Object[] args, String[] connectors, String[] operators, String[] order,
                        String[] defaults) {
        StringBuffer buffer = new StringBuffer("from ");
        List<Object> arg = new ArrayList<Object>();
        // buildQuery(names, args, connectors, operators, order, defaults,
        // buffer, arg);
        // return dao.find(buffer.toString(), arg.toArray());
        List<String> paramNames = new ArrayList<String>();
        buildQuery(names, args, connectors, operators, order, defaults, buffer, paramNames, arg);

        dataOwnerFilter(buffer, arg, paramNames);

        String[] paramName = new String[paramNames.size()];
        paramName = paramNames.toArray(paramName);
        return dao.findByNamedParam(buffer.toString(), arg.toArray(), paramName);
    }

    public List<T> list(String queryString, Object[] args, String[] paramName, String[] order) {
        StringBuffer buffer = new StringBuffer("from ");
        List<Object> arg = new ArrayList<Object>();
        List<String> paramNames = new ArrayList<String>();
        buildQuery(EMPTY_STRING_ARRAY, EMPTY_OBJECT_ARRAY, EMPTY_STRING_ARRAY, EMPTY_STRING_ARRAY, order,
                new String[]{queryString}, buffer, paramNames, arg);

        return dao.findByNamedParam(buffer.toString(), args, paramName);
    }

    public void setOwner(T t) {
        if (!(t instanceof DataOwnerAware)) {
            return;
        }
        // 模块需要验证时才进行数据权限的认证
        if (!DataOwnerConfigFactory.getInstance().neededVerify(entityClass)) {
            return;
        }
        DataOwnerAware aware = (DataOwnerAware) t;
        if (aware.getOwner() != null && !aware.getOwner().isEmpty()) {
            return;
        }
        LoginedUser user = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
        if (user == null) {
            return;
        }
        if (!(user.getUser() instanceof UserInfo)) {
            if (user.getUser().getUsername().equals("admin")) {
                return;
            } else {
                return;
            }
        }
        UserInfo u = ((UserInfo) user.getUser());
        DataOwnerConfig config = DataOwnerConfigFactory.getInstance().getConfig(entityClass);
        Set<DataOwner> set = new HashSet<DataOwner>();

        DataOwner owner = ClassUtils.newInstance(aware.getOwnerClass());
        owner.setData(t);
        owner.setWritable(config.isOwnerEdit());
        owner.setAuthTime(new Date());
        set.add(owner);
        aware.setOwner(set);
        if (config.isBelongingToUser()) {
            owner.setOwnerId(u.getId());
            owner.setOwnerName(u.getName());
            owner.setOwnerType("user");
        } else if (config.isBelongingToPosition()) {
            owner.setOwnerId(u.getPosition().getId());
            owner.setOwnerName(u.getPosition().getName());
            owner.setOwnerType("pos");
        } else if (config.isBelongingToDepartment()) {
            owner.setOwnerId(u.getPosition().getDepartment().getId());
            owner.setOwnerName(u.getPosition().getDepartment().getName());
            owner.setOwnerType("dept");
        } else if (config.isBelongingToBusinessUnit()) {
            Department dept = u.getPosition().getDepartment();
            while (true) {
                if (dept.getType().equals(DepartmentType.BusinessUnit)) {
                    owner.setOwnerId(dept.getId());
                    owner.setOwnerName(dept.getName());
                    owner.setOwnerType("bu");
                    break;
                }
                dept = (Department) dao.get(Department.class, dept.getTreeNode().getParentId());
            }
            //owner.setOwnerType(DepartmentType.BusinessUnit.toString());
        }
    }

    /**
     * 数据权限认证
     *
     * @param buffer
     * @param arg
     * @param paramNames
     * @author tonny
     */
    protected void dataOwnerFilter(StringBuffer buffer, List<Object> arg, List<String> paramNames) {
        StringBuffer dataBuffer = new StringBuffer();

        // 模块需要验证时才进行数据权限的认证
        if (!DataOwnerConfigFactory.getInstance().neededVerify(entityClass)) {
            return;
        }
        // 功能不需要权限认证时，设置为NOT_VERIFY，如后台的文章管理认证，前台文章查看不认证
        DataOwnerConfig.Type type = ThreadLocalMap.getInstance().getObject(DataOwnerConfig.Type.class);
        if (type == DataOwnerConfig.Type.NOT_VERIFY) {
            return;
        }

        if (type == null) {
            type = DataOwnerConfig.Type.VIEW;
        }

        LoginedUser user = ThreadLocalMap.getInstance().getObject(LoginedUser.class);

        // 用户没登录又需要认证时不能返回任何数据
        if (user == null) {
            dataBuffer.append(" 1=2 ");
            reBuildQuery(buffer, dataBuffer);
            return;
        }

        if (!(user.getUser() instanceof UserInfo)) {
            if (user.getUser().getUsername().equals("admin")) {
                return;
            } else {
                dataBuffer.append(" 1=2 ");
                reBuildQuery(buffer, dataBuffer);
                return;
            }
        }

        UserInfo u = ((UserInfo) user.getUser());
        DataOwnerConfig config = DataOwnerConfigFactory.getInstance().getConfig(entityClass);

        if (config.isBelongingToUser()) {
            if (StringUtils.isNotEmpty(config.getOrganizationRoleId()) && u.isRole(config.getOrganizationRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='user' and o.ownerId in ("
                                + "select p.id.user.id from UserPosition p,DepartmentTreeNode n where p.id.position.department.id=n.data.id and n.idLayer like concat(:data_owner_deptId,'%')))");
                paramNames.add("data_owner_deptId");
                arg.add(u.getPosition().getDepartment().getRootId());
            } else if (StringUtils.isNotEmpty(config.getBusinessUnitRoleId())
                    && u.isRole(config.getBusinessUnitRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='user' and o.ownerId in ("
                                + "select p.id.user.id from UserPosition p,DepartmentTreeNode n,DepartmentTreeNode un where p.id.position.department.id=n.data.id and n.idLayer like concat(un.idLayer,'%') and un.id =:data_owner_deptId ))");
                paramNames.add("data_owner_deptId");
                OrgnizationHelper helper = ServiceManager.lookup(OrgnizationHelper.class);
                arg.add(helper.getBusinessUnit(u).getId());
            } else if ((config.isParentView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isParentEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='user' and o.ownerId in ("
                                + "select p.id.user.id from UserPosition p,PositionTreeNode n where n.idLayer like concat(p.id.position.treeNode.idLayer,'%') and n.data.id in (:data_owner_posId)))");
                paramNames.add("data_owner_posId");
                Set<Position> positions = u.getPositions();
                List<String> posIds = new ArrayList<String>();
                for (Position position : positions) {
                    posIds.add(position.getId());
                }
                arg.add(posIds);
            } else if ((config.isOwnerView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isOwnerEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer.append("t.id in  (select o.data.id from " + ownerClass.getSimpleName()
                        + " o  where  o.ownerType='user' and o.ownerId=:data_owner_userId)");
                paramNames.add("data_owner_userId");
                arg.add(user.getUser().getId());
            }
        }

        if (config.isBelongingToPosition()) {
            if (StringUtils.isNotEmpty(config.getOrganizationRoleId()) && u.isRole(config.getOrganizationRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='pos' and o.ownerId in ("
                                + "select n.id from PositionTreeNode n where n.data.department.treeNode.idLayer like concat(:data_owner_deptId,'%')))");
                paramNames.add("data_owner_deptId");
                arg.add(u.getPosition().getDepartment().getRootId());
            } else if (StringUtils.isNotEmpty(config.getBusinessUnitRoleId())
                    && u.isRole(config.getBusinessUnitRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='pos' and o.ownerId in ("
                                + "select n.id from PositionTreeNode n,DepartmentTreeNode un where n.data.department.treeNode.idLayer like concat(un.idLayer,'%') and un.id =:data_owner_deptId ))");
                paramNames.add("data_owner_deptId");
                OrgnizationHelper helper = ServiceManager.lookup(OrgnizationHelper.class);
                arg.add(helper.getBusinessUnit(u).getId());
            } else if ((config.isParentView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isParentEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='pos' and o.ownerId in ("
                                + "select n.id from PositionTreeNode n,PositionTreeNode un where n.idLayer like concat(un.idLayer,'%') and un.data.id in (:data_owner_posId)))");
                paramNames.add("data_owner_posId");
                Set<Position> positions = u.getPositions();
                List<String> posIds = new ArrayList<String>();
                for (Position position : positions) {
                    posIds.add(position.getId());
                }
                arg.add(posIds);
            } else if ((config.isOwnerView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isOwnerEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer.append("t.id in  (select o.data.id from " + ownerClass.getSimpleName()
                        + " o  where  o.ownerType='pos' and o.ownerId in(:data_owner_posId))");
                paramNames.add("data_owner_posId");
                Set<Position> positions = u.getPositions();
                List<String> posIds = new ArrayList<String>();
                for (Position position : positions) {
                    posIds.add(position.getId());
                }
                arg.add(posIds);
            }
        }

        if (config.isBelongingToDepartment()) {
            if (StringUtils.isNotEmpty(config.getOrganizationRoleId()) && u.isRole(config.getOrganizationRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where (o.ownerType='dept' or o.ownerType='bu')and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n where n.data.treeNode.idLayer like concat(:data_owner_deptId,'%')))");
                paramNames.add("data_owner_deptId");
                arg.add(u.getPosition().getDepartment().getRootId());
            } else if (StringUtils.isNotEmpty(config.getBusinessUnitRoleId())
                    && u.isRole(config.getBusinessUnitRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='dept' and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n,DepartmentTreeNode un where n.data.treeNode.idLayer like concat(un.idLayer,'%') and un.id =:data_owner_deptId ))");
                paramNames.add("data_owner_deptId");
                OrgnizationHelper helper = ServiceManager.lookup(OrgnizationHelper.class);
                arg.add(helper.getBusinessUnit(u).getId());
            } else if ((config.isParentView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isParentEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='dept' and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n,DepartmentTreeNode un where n.idLayer like concat(un.idLayer,'%') and un.data.id in (:data_owner_deptId)))");
                paramNames.add("data_owner_deptId");
                Set<Position> positions = u.getPositions();
                List<String> deptIds = new ArrayList<String>();
                for (Position position : positions) {
                    deptIds.add(position.getDepartment().getId());
                }
                arg.add(deptIds);
            } else if ((config.isOwnerView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isOwnerEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer.append("t.id in  (select o.data.id from " + ownerClass.getSimpleName()
                        + " o  where  o.ownerType='dept' and o.ownerId in(:data_owner_posId))");
                paramNames.add("data_owner_posId");
                Set<Position> positions = u.getPositions();
                List<String> deptIds = new ArrayList<String>();
                for (Position position : positions) {
                    deptIds.add(position.getId());
                }
                arg.add(deptIds);
            }
            if ((config.isChildView()) && type == DataOwnerConfig.Type.VIEW) {
                dataBuffer
                        .append(" or t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='dept' and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n,DepartmentTreeNode un where n.idLayer like concat(un.idLayer,'%') and un.data.id in (:data_owner_deptId)))");
                paramNames.add("data_owner_deptId");
                Set<Position> positions = u.getPositions();
                List<String> deptIds = new ArrayList<String>();
                for (Position position : positions) {
                    deptIds.add(position.getDepartment().getId());
                }
                arg.add(deptIds);
            }
        }

        if (config.isBelongingToBusinessUnit()) {
            if (StringUtils.isNotEmpty(config.getOrganizationRoleId()) && u.isRole(config.getOrganizationRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where (o.ownerType='dept' or o.ownerType='bu')and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n where n.data.treeNode.idLayer like concat(:data_owner_deptId,'%')))");
                paramNames.add("data_owner_deptId");
                arg.add(u.getPosition().getDepartment().getRootId());
            } else if (StringUtils.isNotEmpty(config.getBusinessUnitRoleId())
                    && u.isRole(config.getBusinessUnitRoleId())) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='bu' and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n,DepartmentTreeNode un where n.data.treeNode.idLayer like concat(un.idLayer,'%') and un.id =:data_owner_deptId ))");
                paramNames.add("data_owner_deptId");
                OrgnizationHelper helper = ServiceManager.lookup(OrgnizationHelper.class);
                arg.add(helper.getBusinessUnit(u).getId());
            } else if ((config.isParentView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isParentEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer
                        .append("t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='bu' and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n,DepartmentTreeNode un where n.idLayer like concat(un.idLayer,'%') and un.data.id in (:data_owner_deptId)))");
                paramNames.add("data_owner_deptId");
                Set<Position> positions = u.getPositions();
                List<String> deptIds = new ArrayList<String>();
                for (Position position : positions) {
                    deptIds.add(position.getDepartment().getId());
                }
                arg.add(deptIds);
            } else if ((config.isOwnerView() && type == DataOwnerConfig.Type.VIEW)
                    || (config.isOwnerEdit() && type == DataOwnerConfig.Type.EDIT)) {
                dataBuffer.append("t.id in  (select o.data.id from " + ownerClass.getSimpleName()
                        + " o  where  o.ownerType='bu' and o.ownerId in(:data_owner_buId))");
                paramNames.add("data_owner_buId");
                Set<Position> positions = u.getPositions();
                List<String> deptIds = new ArrayList<String>();
                for (Position position : positions) {
                    deptIds.add(position.getDepartment().getId());
//                    String idLayer = position.getDepartment().getTreeNode().getIdLayer();
//                    String[] ids = idLayer.split(",");
//                    for (String id : ids) {
//                        deptIds.add(id);
//                    }
                }
                arg.add(deptIds);
            }
            if ((config.isChildView()) && type == DataOwnerConfig.Type.VIEW) {
                dataBuffer
                        .append(" or t.id in  (select o.data.id from "
                                + ownerClass.getSimpleName()
                                + " o where o.ownerType='bu' and o.ownerId in ("
                                + "select n.id from DepartmentTreeNode n,DepartmentTreeNode un where un.idLayer like concat(n.idLayer,'%') and un.data.id in (:data_owner_deptId1)))");
                paramNames.add("data_owner_deptId1");
                Set<Position> positions = u.getPositions();
                List<String> deptIds = new ArrayList<String>();
                for (Position position : positions) {
                    deptIds.add(position.getDepartment().getId());
                }
                arg.add(deptIds);
            }
        }

        if (dataBuffer.length() == 0) {
            return;
        }

        reBuildQuery(buffer, dataBuffer);
    }

    protected void reBuildQuery(StringBuffer buffer, StringBuffer dataBuffer) {
        int orderIndex = buffer.indexOf(" order by ");
        int groupIndex = buffer.indexOf(" group by ");
        int index = 0;
        if (orderIndex > 0 && groupIndex > 0) {
            index = Math.max(groupIndex, orderIndex);
        } else if (orderIndex > 0) {
            index = orderIndex;
        } else if (groupIndex > 0) {
            index = groupIndex;
        }

        if (index == 0) {
            if (buffer.indexOf(" where ") > 0) {
                buffer.append(" and (");
            } else {
                buffer.append(" where (");
            }
            buffer.append(dataBuffer).append(")");
        } else {
            if (buffer.indexOf(" where ") > 0) {
                dataBuffer.insert(0, " and (");
            } else {
                dataBuffer.insert(0, " where (");
            }
            buffer.insert(index, dataBuffer + ")");
        }
    }

    protected void dataOwnerFilter2(StringBuffer buffer, List<Object> arg, List<String> paramNames) {
        StringBuffer dataBuffer = new StringBuffer();
        if (DataOwnerConfigFactory.getInstance().neededVerify(entityClass)) {
            LoginedUser user = ThreadLocalMap.getInstance().getObject(LoginedUser.class);
            if (user == null) {
                return;
            }
            UserInfo u = ((UserInfo) user.getUser());
            DataOwnerConfig config = DataOwnerConfigFactory.getInstance().getConfig(entityClass);
            if (config.isOwnerView()) {
                // 仅当前用户可查看的用户数据
                dataBuffer.append("t.id in  (select o.data.id from " + ownerClass.getSimpleName()
                        + " o  where  o.ownerType='user' and o.ownerId=:data_owner_userId)");
                paramNames.add("data_owner_userId");
                arg.add(user.getUser().getId());
            }
            if (config.isOwnerView()) {
                if (config.isBelongingToPosition()) {
                    // 当前岗位能查看岗位数据
                    if (dataBuffer.length() > 0) {
                        dataBuffer.append(" or ");
                    }
                    dataBuffer.append("t.id in  (select o.data.id from " + ownerClass.getSimpleName()
                            + " o where  o.ownerType='pos' and o.ownerId in (:data_owner_posId))");
                    paramNames.add("data_owner_posId");
                    Set<Position> positions = u.getPositions();
                    List<String> posIds = new ArrayList<String>();
                    for (Position position : positions) {
                        posIds.add(position.getId());
                    }
                    arg.add(posIds);
                }
                if (config.isBelongingToUser()) {
                    // 当前岗位能查看同岗位所有用户数据
                    if (dataBuffer.length() > 0) {
                        dataBuffer.append(" or ");
                    }
                    dataBuffer
                            .append("t.id in  (select o.data.id from "
                                    + ownerClass.getSimpleName()
                                    + " o where o.ownerType='user' and o.ownerId in ("
                                    + "select p.id.user.id from UserPosition p where p.id.position.id in (:data_owner_posId)))");
                    paramNames.add("data_owner_posId");
                    Set<Position> positions = u.getPositions();
                    List<String> posIds = new ArrayList<String>();
                    for (Position position : positions) {
                        posIds.add(position.getId());
                    }
                    arg.add(posIds);
                }
            }
            if (config.isOwnerView()) {
                // if (config.isDataBelongingToDepartment()) {
                // // 查询用户所在部门的部门数据
                // buffer.append("c.id in  (select o.data.id from " +
                // ownerClass.getSimpleName()
                // +
                // " o where  (o.ownerType='bu' or o.ownerType='dept') and o.ownerId in ?)");
                // }
                if (config.isBelongingToDepartment()) {
                    // 查询用户所在部门的部门数据(包含子部门)
                    if (dataBuffer.length() > 0) {
                        dataBuffer.append(" or ");
                    }
                    dataBuffer
                            .append("t.id in  (select o.data.id from "
                                    + ownerClass.getSimpleName()
                                    + " o where  (o.ownerType='bu' or o.ownerType='dept') and o.ownerId in"
                                    + " (select n2.id from DepartmentTreeNode n1,DepartmentTreeNode n2 where n1.data.id in (:data_owner_deptId) and n2.idLayer like concat(n1.idLayer,'%')))");
                    paramNames.add("data_owner_deptId");
                    Set<Position> positions = u.getPositions();
                    List<String> deptIds = new ArrayList<String>();
                    for (Position position : positions) {
                        deptIds.add(position.getDepartment().getId());
                    }
                    arg.add(deptIds);
                }
                if (config.isBelongingToUser()) {
                    // 查询用户所在部门包含子部门的所有用户数据
                    if (dataBuffer.length() > 0) {
                        dataBuffer.append(" or ");
                    }
                    dataBuffer
                            .append("t.id in  (select o.data.id from "
                                    + ownerClass.getSimpleName()
                                    + " o where o.ownerType='user' and o.ownerId in ("
                                    + "select p.id.user.id from UserPosition p where p.id.user.id=o.ownerId and p.id.position.department.id in(select n2.id from DepartmentTreeNode n1,DepartmentTreeNode n2 where n1.data.id in (:data_owner_deptId) and n2.idLayer like concat(n1.idLayer,'%'))))");
                    paramNames.add("data_owner_deptId");
                    Set<Position> positions = u.getPositions();
                    List<String> deptIds = new ArrayList<String>();
                    for (Position position : positions) {
                        deptIds.add(position.getDepartment().getId());
                    }
                    arg.add(deptIds);
                }
            }
            reBuildQuery(buffer, dataBuffer);
        }
    }

    public void setDao(HibernateDAO dao) {
        this.dao = dao;
    }

    public void setSqlMappingManager(SQLMappingManager sqlMappingManager) {
        this.sqlMappingManager = sqlMappingManager;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * @param entity
     */
    public void refresh(Object entity) {
        dao.refresh(entity);
    }

    /**
     * @return the ownerClass
     */
    public Class<? extends DataOwner<T>> getOwnerClass() {
        return ownerClass;
    }

    /**
     * @param ownerClass the ownerClass to set
     */
    public void setOwnerClass(Class<? extends DataOwner<T>> ownerClass) {
        this.ownerClass = ownerClass;
    }

}
