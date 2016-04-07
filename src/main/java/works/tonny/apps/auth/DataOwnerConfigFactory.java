/**
 * <p>
 * <p/>
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 *
 * @date 2014-12-3 下午4:16:58
 * @author tonny
 */
package works.tonny.apps.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 数据所有者的配置F
 * </p>
 *
 * @author tonny
 * @version 1.0.0
 */
public class DataOwnerConfigFactory implements Serializable {

    static final Map<String, DataOwnerConfig> CONFIGS = new HashMap<String, DataOwnerConfig>();
    private static final DataOwnerConfigFactory INSTANCE = new DataOwnerConfigFactory();
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected DataOwnerConfigFactory() {
    }

    public static DataOwnerConfigFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 根据entity查询配置
     *
     * @param entityClass
     * @return
     * @author tonny
     */
    public DataOwnerConfig getConfig(Class entityClass) {
        return CONFIGS.get(entityClass.getSimpleName());
    }

    /**
     * 判断类是否配置了数据权限的验证
     *
     * @param entifyClass
     * @return
     * @author tonny
     */
    public boolean neededVerify(Class entifyClass) {
        return CONFIGS.containsKey(entifyClass.getSimpleName());
    }
}
