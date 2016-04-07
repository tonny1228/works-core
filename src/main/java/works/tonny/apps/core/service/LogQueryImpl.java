package works.tonny.apps.core.service;

import works.tonny.apps.core.Log;
import works.tonny.apps.core.LogQuery;
import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.ListSupport;

import java.util.Date;

/**
 * Created by tonny on 2015/11/26.
 */
public class LogQueryImpl extends AbstractCriteriaQuery<LogQuery, Log> implements LogQuery {
    /**
     * thisable persistent field
     */
    private Date from;
    /**
     * thisable persistent field
     */
    private Date end;

    /**
     * thisable persistent field
     */
    private String user;

    /**
     * thisable persistent field
     */
    private String catalog;

    /**
     * thisable persistent field
     */
    private String[] action;

    /**
     * thisable persistent field
     */
    private String objectId;

    /**
     * thisable persistent field
     */
    private String info;


    /**
     * @param catalogDAO
     */
    public LogQueryImpl(BaseDAOSupport catalogDAO) {
        this.dao = catalogDAO;
    }

    @Override
    protected void doBuild() {
        addParameter(user, "admin", ListSupport.MUST, ListSupport.EQUALS);
        addParameter(catalog, "catalog", ListSupport.MUST, ListSupport.EQUALS);
        addParameter(action, "action", ListSupport.MUST, ListSupport.IN);
        addParameter(objectId, "objectId", ListSupport.MUST, ListSupport.EQUALS);
        addParameter(info, "info", ListSupport.MUST, ListSupport.LIKE);
        if (from != null && end != null)
            addParameter(new Date[]{from, new Date(end.getTime() + 24 * 3600000 - 1)}, "logTime",
                    ListSupport.MUST, ListSupport.BETWEEN);
    }

    @Override
    public LogQuery user(String user) {
        this.user = user;
        return this;
    }

    @Override
    public LogQuery catalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    @Override
    public LogQuery action(String... action) {
        this.action = action;
        return this;
    }

    @Override
    public LogQuery objectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    @Override
    public LogQuery infoLike(String info) {
        this.info = info;
        return this;
    }

    @Override
    public LogQuery timeRange(Date from, Date to) {
        this.from = from;
        this.end = to;
        return this;
    }

    @Override
    public LogQuery orderByTime(Direction direction) {
        orderBy("logTime", direction);
        return this;
    }
}
