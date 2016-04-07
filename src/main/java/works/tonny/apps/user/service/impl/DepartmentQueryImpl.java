package works.tonny.apps.user.service.impl;

import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.user.model.Department;
import works.tonny.apps.user.model.DepartmentType;
import works.tonny.apps.user.service.DepartmentQuery;

public class DepartmentQueryImpl extends AbstractCriteriaQuery<DepartmentQuery, Department> implements DepartmentQuery {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String nameLike;
    private String subs;
    private Integer depth;
    private String[] types;

    public DepartmentQueryImpl(BaseDAOSupport property) {
        this.dao = property;
    }

    @Override
    protected void doBuild() {
        addParameter(id, "id", ListSupport.MUST, ListSupport.EQUALS);
        addParameter(nameLike, "name", ListSupport.MUST, ListSupport.LIKE);
        addParameter(types, "type", ListSupport.MUST, ListSupport.IN);
        if (subs != null && depth == null) {
            addParameter(subs, "treeNode.idLayer", ListSupport.MUST, ListSupport.RLIKE);
            addParameter(subs, "id", ListSupport.MUST, ListSupport.NOT_EQUALS);
        }
        if (subs != null && depth != null) {
            addParameter(subs, "treeNode.idLayer", ListSupport.MUST, ListSupport.RLIKE);
            addParameter(subs, "id", ListSupport.MUST, ListSupport.NOT_EQUALS);
            addParameter(depth, "treeNode.depth", ListSupport.MUST, ListSupport.EQUALS);
        }

    }

    @Override
    public DepartmentQuery id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public DepartmentQuery nameLike(String nameLike) {
        this.nameLike = nameLike;
        return this;
    }

    @Override
    public DepartmentQuery subs(String subs) {
        this.subs = subs;
        return this;
    }

    @Override
    public DepartmentQuery subs(String subs, int depth) {
        this.subs = subs;
        this.depth = depth;
        return this;
    }


    @Override
    public DepartmentQuery type(DepartmentType... types) {
        if (types != null && types.length > 0) {
            this.types = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                this.types[i] = types[i].toString();
            }
        } else {
            this.types = null;
        }
        return this;
    }
}
