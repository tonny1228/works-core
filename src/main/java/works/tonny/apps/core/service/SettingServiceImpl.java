/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-26 下午2:43:55
 * @author tonny
 */
package works.tonny.apps.core.service;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.llama.library.utils.PropertiesUtils;

import works.tonny.apps.core.Setting;
import works.tonny.apps.core.SettingService;
import works.tonny.apps.core.dao.SettingDAO;
import works.tonny.apps.user.AuthedAbstractService;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class SettingServiceImpl extends AuthedAbstractService implements SettingService {
	private SettingDAO settingDAO;

	/**
	 * @see works.tonny.apps.core.SettingService#setSetting(works.tonny.apps.core.Setting)
	 */
	@Override
	public void setSetting(Setting setting) {
		final Setting setting2 = getSetting(setting.getClass(), setting.getKey());
		if (setting2 == null)
			settingDAO.save(setting);
		else {
			PropertiesUtils.copyProperties(setting2, setting, "name", "type", "stringValue", "numberValue",
					"objectBytes", "key");
			settingDAO.update(setting2);
		}
	}

	/**
	 * @param id
	 * @return
	 * @author tonny
	 */
	private Setting getSetting(String id) {
		return settingDAO.get(id);
	}

	/**
	 * @see works.tonny.apps.core.SettingService#getSetting(java.lang.String)
	 */
	@Override
	public Setting getSetting(Class<? extends Setting> cla, String key) {
		final List<Setting> list = settingDAO.list(key);
		for (Setting setting : list) {
			if (setting.getClass().equals(cla)) {
				return setting;
			}
		}
		return null;
	}

	/**
	 * @see works.tonny.apps.core.SettingService#deleteSetting(java.lang.String)
	 */
	@Override
	public void deleteSetting(Setting setting) {
		settingDAO.delete(setting);
	}

	/**
	 * @see works.tonny.apps.core.SettingService#list()
	 */
	@Override
	public List<Setting> list() {
		return (List<Setting>) settingDAO.list();
	}

	/**
	 * @return the settingDAO
	 */
	public SettingDAO getSettingDAO() {
		return settingDAO;
	}

	/**
	 * @param settingDAO the settingDAO to set
	 */
	public void setSettingDAO(SettingDAO settingDAO) {
		this.settingDAO = settingDAO;
	}

}
