/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * @author tonny
 * @date 2012-10-12
 * @version 1.0.0
 */
public class Module implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String id;

	protected String name;

	protected String description;

	protected String[] dependencies;

	protected Map<String, List<String>> pages;

	protected Map<String, List<String>> resources;

	protected Map<String, ModuleSetting> settings;

	protected Set<String> webapp;

	protected Map<String, String> params;

	protected String settingName;

	protected String version;

	protected String lastVersion;

	protected int status;

	protected String mappedPath;

	// boolean forceUpdate;

	protected ModuleInstallHandler handler;

	protected Date firstDeployDate = new Date();

	protected Date updateDate;

	/**
	 * @param name
	 * @param adminMenu
	 * @param menu
	 */
	public Module(String id, String name, String desc, String version) {
		super();
		this.id = id;
		this.name = name;
		this.description = desc;
		this.version = version;
		settings = new LinkedHashMap();
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public void addPage(String name, String page) {
		if (pages == null) {
			pages = new HashMap<String, List<String>>();
		}

		add(pages, name, page);
	}

	public void addResource(String name, String page) {
		if (resources == null) {
			resources = new HashMap<String, List<String>>();
		}
		add(resources, name, page);
	}

	public void addSetting(ModuleSetting setting) {
		settings.put(setting.getName(), setting);
	}

	/**
	 * @param map
	 * @param name
	 * @param page
	 */
	private void add(Map<String, List<String>> map, String name, String page) {

		List<String> ps = null;
		if ((ps = map.get(name)) == null) {
			ps = new ArrayList<String>();
			map.put(name, ps);
		}
		ps.add(page);
	}

	public List<String> getPages(String name) {
		if (pages == null) {
			return null;
		}
		return pages.get(name);
	}

	public List<String> getResources(String name) {
		if (resources == null) {
			return null;
		}
		return resources.get(name);
	}

	public Map<String, List<String>> getPages() {
		return pages;
	}

	public Map<String, List<String>> getResources() {
		return resources;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the settingName
	 */
	public String getSettingName() {
		return settingName;
	}

	/**
	 * @param settingName the settingName to set
	 */
	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	/**
	 * @return the settings
	 */
	public Map<String, ModuleSetting> getSettings() {
		return settings;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @param params the params to set
	 */
	public void addParam(String name, String value) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		params.put(name, value);
	}

	/**
	 * @return the dependency
	 */
	public String[] getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependency the dependency to set
	 */
	public void setDependencies(String[] dependency) {
		this.dependencies = dependency;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the handler
	 */
	public ModuleInstallHandler getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(ModuleInstallHandler handler) {
		this.handler = handler;
	}

	/**
	 * @return the lastVersion
	 */
	public String getLastVersion() {
		return lastVersion;
	}

	/**
	 * @param lastVersion the lastVersion to set
	 */
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName() + getVersion();
	}

	/**
	 * @return the mappedPath
	 */
	public String getMappedPath() {
		return mappedPath;
	}

	/**
	 * @param mappedPath the mappedPath to set
	 */
	public void setMappedPath(URL url) {
		if (url.getProtocol().equalsIgnoreCase("jar")) {
			this.mappedPath = StringUtils.substringBetween(url.getFile(), "file:", "!");
		} else {
			this.mappedPath = url.getFile();
		}
	}

	/**
	 * @return the firstDeployDate
	 */
	public Date getFirstDeployDate() {
		return firstDeployDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the webapp
	 */
	public Set<String> getWebapp() {
		if (webapp == null) {
			webapp = new HashSet<String>();
		}
		return webapp;
	}

	public void addWebResource(String name) {
		if (webapp == null) {
			webapp = new HashSet<String>();
		}
		webapp.add(name);
	}

	/**
	 * @param webapp the webapp to set
	 */
	public void setWebapp(Set<String> webapp) {
		this.webapp = webapp;
	}
}
