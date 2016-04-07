/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-12-3 下午4:12:10
 * @author tonny
 */
package works.tonny.apps.auth;

import java.util.List;

import works.tonny.apps.EntityService;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface DataOwnerConfigService extends EntityService<DataOwnerConfig> {
	/**
	 * 所有的配置
	 * 
	 * @return
	 * @author tonny
	 */
	List<DataOwnerConfig> all();
}