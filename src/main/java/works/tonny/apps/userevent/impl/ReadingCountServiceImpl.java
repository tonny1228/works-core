package works.tonny.apps.userevent.impl;

// Generated 2015-6-15 9:40:02 by Hibernate Tools 3.4.0.CR1

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.userevent.ReadingCount;
import works.tonny.apps.userevent.ReadingCountQuery;
import works.tonny.apps.userevent.ReadingCountService;

/**
 * Service object for domain model class ReadingCount.
 *
 * @author Tonny Liu
 * @see .ReadingCount
 */
public class ReadingCountServiceImpl extends AuthedAbstractService implements ReadingCountService {
    private ReadingCountDAO readingDetailDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int read(String mainFunction, String subFunction, String dataId) {
        ReadingCount c = createQuery().mainFunction(mainFunction).subFunction(subFunction).dataId(dataId)
                .singleResult();
        if (c == null) {
            c = new ReadingCount(mainFunction, subFunction, dataId);
            readingDetailDAO.save(c);
        } else {
            c.setNum(c.getNum() + 1);
            readingDetailDAO.update(c);
        }
        return c.getNum();
    }


    @Override
    public int unread(String mainFunction, String subFunction, String dataId) {
        ReadingCount c = createQuery().mainFunction(mainFunction).subFunction(subFunction).dataId(dataId)
                .singleResult();
        if (c == null) {
            c = new ReadingCount(mainFunction, subFunction, dataId);
            readingDetailDAO.save(c);
        } else {
            c.setNum(c.getNum() - 1);
            readingDetailDAO.update(c);
        }
        return c.getNum();
    }

    @Override
    public int getCount(String mainFunction, String subFunction, String dataId) {
        ReadingCount c = createQuery().mainFunction(mainFunction).subFunction(subFunction).dataId(dataId).singleResult();
        if (c == null) {
            return 0;
        }
        return c.getNum();
    }

    public ReadingCountQuery createQuery() {
        try {
            return new ReadingCountQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(readingDetailDAO,
                    "targetSource.target"));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public ReadingCountDAO getReadingCountDAO() {
        return readingDetailDAO;
    }

    public void setReadingCountDAO(ReadingCountDAO readingDetailDAO) {
        this.readingDetailDAO = readingDetailDAO;
    }

}
