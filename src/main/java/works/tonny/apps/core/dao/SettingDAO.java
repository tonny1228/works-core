/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-26 上午11:46:25
 * @author tonny
 */
package works.tonny.apps.core.dao;

import java.util.List;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.Setting;
import works.tonny.apps.support.ListSupport;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface SettingDAO extends EntityDAO<Setting> {

	/**
	 * @return
	 * @author tonny
	 */
	@ListSupport
	List<Setting> list();

	@ListSupport(field = "key")
	List<Setting> list(String key);

}
