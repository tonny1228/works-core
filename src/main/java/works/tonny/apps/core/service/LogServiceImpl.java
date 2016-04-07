package works.tonny.apps.core.service;

import org.apache.commons.beanutils.PropertyUtils;
import works.tonny.apps.AbstractService;
import works.tonny.apps.core.Log;
import works.tonny.apps.core.LogQuery;
import works.tonny.apps.core.LogService;
import works.tonny.apps.core.dao.LogDAO;
import works.tonny.apps.support.BaseDAOSupport;

import java.util.Date;

/**
 * Created by tonny on 2015/11/26.
 */
public class LogServiceImpl extends AbstractService implements LogService {

    private LogDAO logDAO;


    @Override
    public void log(String catalog, String username, String type, String objectId, String message) {
        Log log = new Log();
        log.setCatalog(catalog);
        log.setAdmin(username);
        log.setAction(type);
        log.setInfo(message);
        log.setLogTime(new Date());
        log.setObjectId(objectId);
        logDAO.save(log);
    }

    @Override
    public LogQuery createQuery() {
        try {
            return new LogQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(logDAO, "targetSource.target"));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }
}
