/**
 * <p>
 * <p/>
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 *
 * @date 2014-12-18 上午11:04:33
 * @author tonny
 */
package works.tonny.apps.auth;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.exception.DataException;
import works.tonny.apps.support.BeanInitListener;
import works.tonny.apps.user.AuthedAbstractService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author tonny
 * @version 1.0.0
 */
@Lazy
public class DataOwnerConfigServiceImpl extends AuthedAbstractService implements DataOwnerConfigService, BeanInitListener {

    private DataOwnerConfigDAO dataOwnerConfigDAO;

    private boolean inited;

    public synchronized void initialize() {
        if(true){
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        }
        DataOwnerConfigFactory.CONFIGS.clear();
        List<DataOwnerConfig> all = all();
        for (DataOwnerConfig dataOwnerConfig : all) {
            DataOwnerConfigFactory.CONFIGS.put(dataOwnerConfig.getEntity(), dataOwnerConfig);
        }
        inited = true;
    }

    /**
     * @see works.tonny.apps.EntityService#save(java.lang.Object)
     */
    @Override
    public String save(DataOwnerConfig t) {
        if (!inited) {
            initialize();
        }
        String id = dataOwnerConfigDAO.save(t);
        DataOwnerConfigFactory.CONFIGS.put(t.getEntity(), t);
        return id;
    }

    /**
     * @see works.tonny.apps.EntityService#get(java.lang.String)
     */
    @Override
    public DataOwnerConfig get(String id) {
        return dataOwnerConfigDAO.get(id);
    }

    /**
     * @see works.tonny.apps.EntityService#update(java.lang.Object)
     */
    @Override
    public void update(DataOwnerConfig t) {
        if (!inited) {
            initialize();
        }
        dataOwnerConfigDAO.update(t);
        try {
            BeanUtils.copyProperties(DataOwnerConfigFactory.CONFIGS.get(t.getEntity()), t);
        } catch (Exception e) {
            throw new DataException(e);
        }
    }

    /**
     * @see works.tonny.apps.EntityService#delete(java.lang.String)
     */
    @Override
    public void delete(String id) {
        if (!inited) {
            initialize();
        }
        DataOwnerConfig obj = get(id);
        dataOwnerConfigDAO.delete(obj);
        DataOwnerConfigFactory.CONFIGS.remove(obj.getEntity());
    }

    /**
     * @see works.tonny.apps.EntityService#delete(java.lang.String[])
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] ids) {
        for (String id : ids) {
            delete(id);
        }
    }

    /**
     * @see works.tonny.apps.auth.DataOwnerConfigService#all()
     */
    @Override
    public List<DataOwnerConfig> all() {
        return dataOwnerConfigDAO.list();
    }

    /**
     * @return the dataOwnerConfigDAO
     */
    public DataOwnerConfigDAO getDataOwnerConfigDAO() {
        return dataOwnerConfigDAO;
    }

    /**
     * @param dataOwnerConfigDAO
     *            the dataOwnerConfigDAO to set
     */
    @Resource()
    public void setDataOwnerConfigDAO(DataOwnerConfigDAO dataOwnerConfigDAO) {
        this.dataOwnerConfigDAO = dataOwnerConfigDAO;
    }

}
