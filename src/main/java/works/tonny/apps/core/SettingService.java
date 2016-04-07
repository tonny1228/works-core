/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-26 上午11:48:55
 * @author tonny
 */
package works.tonny.apps.core;

import java.util.List;

/**
 * <p>
 * 系统配置服务
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface SettingService {

	/**
	 * 保存设置属性
	 * 
	 * @param key
	 * @param value
	 * @author tonny
	 */
	void setSetting(Setting setting);

	/**
	 * 根据key查询配置信息
	 * 
	 * @author tonny
	 */
	Setting getSetting(Class<? extends Setting> cla, String key);

	/**
	 * 删除配置信息
	 * 
	 * @param key
	 * @author tonny
	 */
	void deleteSetting(Setting setting);

	/**
	 * 查询所有的配置信息
	 * 
	 * @return
	 * @author tonny
	 */
	List<Setting> list();
}