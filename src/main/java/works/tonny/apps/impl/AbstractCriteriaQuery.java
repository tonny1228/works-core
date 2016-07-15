/**
 *
 */
package works.tonny.apps.impl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.StringUtils;
import works.tonny.apps.Query;
import works.tonny.apps.support.ListSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 祥栋
 */
public abstract class AbstractCriteriaQuery<T extends Query<?, ?>, U> extends AbstractQuery<T, U> {

    private List<String> names;

    private List<Object> args;

    private List<String> connectors;

    private List<String> operators;

    /**
     * 是否使用sqlmapping模式
     */
    private Boolean mappingMode;

    /**
     * 是否使用hibernate criteria模式查询
     */
    private boolean hibernateCriteriaMode;

    private String queryName;

    private List<String> appenders;

    private List<String> paramNames;

    private Criterion criterion;


    protected void addParameter(Object arg, String name, String connector, String operator) {
        if (mappingMode != null && mappingMode) {
            throw new java.lang.IllegalStateException("已经通过SQLMapping模式调用，不能再调用此方法");
        }
        if (hibernateCriteriaMode) {
            if (arg != null) {
                Criterion c = addCriterion(name, operator, arg);
                addToCriterion(connector, c);
            }
            return;
        }
        mappingMode = false;
        if (arg != null) {
            if ((arg instanceof Boolean) && !((Boolean) arg).booleanValue()) {
                return;
            }
            names.add(name);
            args.add(arg);
            connectors.add(connector);
            operators.add(operator);
        }
    }

    private void addToCriterion(String connector, Criterion c) {
        if (this.criterion == null) {
            this.criterion = c;
            return;
        }
        if (connector == ListSupport.MUST) {
            this.criterion = Restrictions.and(this.criterion, c);
        } else {
            this.criterion = Restrictions.or(this.criterion, c);
        }
    }

    protected void addParameterGroup(String connector, String innerConnector, GroupEntity... entity) {
        hibernateCriteriaMode = true;
        if (paramNames == null)
            paramNames = new ArrayList<String>();

        if (names != null) {
            List<Object> temp = new ArrayList<Object>();
            temp.addAll(args);
            args.clear();
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                String pname = name + i;
                String opt = operators.get(i);
                String conn = connectors.get(i);

                if (temp.get(i) != null) {
                    paramNames.add(pname);
                    args.add(temp.get(i));
                }

                Criterion c = createCriterion(opt, name, ":" + pname, temp.get(i));

                addToCriterion(conn, c);
            }

            for (int j = args.size() - 1; j >= 0; j--) {
                if (args.get(j) == null) {
                    args.remove(j);
                }
            }
            names = null;
        }

        Junction conjunction = null;
        if (innerConnector == ListSupport.MUST) {
            conjunction = Restrictions.conjunction();
        } else {
            conjunction = Restrictions.disjunction();
        }

        for (GroupEntity groupEntity : entity) {
            conjunction.add(addCriterion(groupEntity.name, groupEntity.operator, groupEntity.args));
            // addCriterion(connector, to);
        }

        addToCriterion(connector, conjunction);

    }

    private Criterion addCriterion(String name, String operator, Object arg) {
        String pname = name + paramNames.size();
        if (arg != null) {
            paramNames.add(pname);
            args.add(arg);
        }
        return createCriterion(operator, name, ":" + pname, arg);
    }

    private Criterion createCriterion(String opt, String name, String pname, Object arg) {
        Criterion c = null;
        name = "t." + name;
        if (opt == ListSupport.EQUALS) {
            c = Restrictions.eq(name, pname);
        } else if (opt == ListSupport.NOT_EQUALS) {
            c = Restrictions.not(Restrictions.ne(name, pname));
        } else if (opt == ListSupport.GREATER) {
            c = Restrictions.gt(name, pname);
        } else if (opt == ListSupport.GREATER_EQUALS) {
            c = Restrictions.ge(name, pname);
        } else if (opt == ListSupport.LESS) {
            c = Restrictions.lt(name, pname);
        } else if (opt == ListSupport.LESS_EQUALS) {
            c = Restrictions.le(name, pname);
        } else if (opt == ListSupport.LIKE) {
            c = Restrictions.like(name, pname);
            args.set(args.size() - 1, "%" + args.get(args.size() - 1) + "%");
        } else if (opt == ListSupport.RLIKE) {
            c = Restrictions.like(name, arg.toString());
            args.set(args.size() - 1, args.get(args.size() - 1) + "%");
        } else if (opt == ListSupport.LLIKE) {
            c = Restrictions.like(name, arg.toString(), MatchMode.START);
            args.set(args.size() - 1, "%" + args.get(args.size() - 1));
        } else if (opt == ListSupport.IN) {
            c = Restrictions.in(name, new String[]{pname});
        } else if (opt == ListSupport.NOT_NULL) {
            c = Restrictions.isNotNull(name);
        } else if (opt == ListSupport.NULL) {
            c = Restrictions.isNull(name);
        } else if (opt == ListSupport.BETWEEN) {
            c = Restrictions.between(name, ((Object[]) arg)[0], ((Object[]) arg)[1]);
            paramNames.remove(paramNames.size() - 1);
            args.remove(args.size() - 1);
        } else if (opt == ListSupport.BETWEEN_N_L) {
            c = Restrictions
                    .and(Restrictions.gt(name, ((Object[]) arg)[0]), Restrictions.le(name, ((Object[]) arg)[1]));
            paramNames.remove(paramNames.size() - 1);
            args.remove(args.size() - 1);
        } else if (opt == ListSupport.BETWEEN_N_L_R) {
            c = Restrictions
                    .and(Restrictions.gt(name, ((Object[]) arg)[0]), Restrictions.lt(name, ((Object[]) arg)[1]));
            paramNames.remove(paramNames.size() - 1);
            args.remove(args.size() - 1);
        } else if (opt == ListSupport.BETWEEN_N_R) {
            c = Restrictions
                    .and(Restrictions.gt(name, ((Object[]) arg)[0]), Restrictions.le(name, ((Object[]) arg)[1]));
            paramNames.remove(paramNames.size() - 1);
            args.remove(args.size() - 1);
        }
        return c;
    }

    protected void addParameter(boolean condition, String appender, String[] paramName, Object[] arg) {
        if (mappingMode != null && !mappingMode) {
            throw new java.lang.IllegalStateException("已经通过SQLMapping模式调用，不能再调用此方法");
        }
        mappingMode = true;

        if (!condition) {
            return;
        }

        appenders.add(appender);
        if (paramName != null)
            for (String name : paramName) {
                paramNames.add(name);
            }
        if (arg != null)
            for (Object a : arg) {
                args.add(a);
            }
    }

    /**
     * 初始化所有的参数，并将查询条件注入。order不能初始化，里面有值了
     *
     * @author tonny
     */
    protected void build() {
        names = new ArrayList<String>();
        args = new ArrayList<Object>();
        connectors = new ArrayList<String>();
        operators = new ArrayList<String>();

        queryName = null;
        appenders = new ArrayList<String>();
        paramNames = new ArrayList<String>();
        doBuild();
    }

    public List<U> executeList() {
        build();
        if (hibernateCriteriaMode) {
            return dao.list(criterion.toString(), args.toArray(), paramNames.toArray(new String[]{}),
                    order.toArray(new String[]{}));
        }
        if (mappingMode != null && mappingMode) {
            return dao.find(queryName, appenders.toArray(new String[]{}), args.toArray(),
                    paramNames.toArray(new String[]{}));
        } else {
            return dao.find(names.toArray(new String[]{}), args.toArray(), connectors.toArray(new String[]{}),
                    operators.toArray(new String[]{}), order.toArray(new String[]{}), null);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.impl.AbstractQuery#executeList(int, int)
     */
    @Override
    protected PagedList<U> executeList(int page, int pagesize) {
        build();
        if (hibernateCriteriaMode) {
            return dao.list(criterion.toString(), args.toArray(), paramNames.toArray(new String[]{}),
                    order.toArray(new String[]{}), (page - 1) * pagesize, pagesize);
        }
        if (mappingMode != null && mappingMode) {
            return dao.find(queryName, appenders.toArray(new String[]{}), args.toArray(),
                    paramNames.toArray(new String[]{}), page, pagesize);
        } else {
            return dao.find(names.toArray(new String[]{}), args.toArray(), connectors.toArray(new String[]{}),
                    operators.toArray(new String[]{}), order.toArray(new String[]{}), null, page, pagesize);
        }
    }

    /**
     * @see works.tonny.apps.impl.AbstractQuery#executeListPage(int, int)
     */
    @Override
    protected PagedList<U> executeSubList(int offset, int limit) {
        build();
        if (hibernateCriteriaMode) {
            return dao.list(criterion.toString(), args.toArray(), paramNames.toArray(new String[]{}),
                    order.toArray(new String[]{}), offset, limit);
        }
        if (mappingMode != null && mappingMode) {
            return dao.list(queryName, appenders.toArray(new String[]{}), args.toArray(),
                    paramNames.toArray(new String[]{}), offset, limit);
        } else {
            return dao.list(names.toArray(new String[]{}), args.toArray(), connectors.toArray(new String[]{}),
                    operators.toArray(new String[]{}), order.toArray(new String[]{}), null, offset, limit);
        }
    }

    protected String n(String str) {
        return StringUtils.defaultIfEmpty(str, null);
    }


    /**
     *
     */
    protected abstract void doBuild();

    /**
     * @return the queryName
     */
    public String getQueryName() {
        return queryName;
    }

    /**
     * @param queryName the queryName to set
     */
    public void setQueryName(String queryName) {
        this.queryName = queryName;
        mappingMode = true;
    }

    protected class GroupEntity {
        String name;

        String operator;

        Object args;

        public GroupEntity(String name, String operator, Object args) {
            super();
            this.name = name;
            this.operator = operator;
            this.args = args;
        }

    }

}