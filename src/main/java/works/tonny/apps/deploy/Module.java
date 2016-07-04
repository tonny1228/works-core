/**
 *
 */
package works.tonny.apps.deploy;

import org.apache.commons.lang.StringUtils;

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

/**
 * @author tonny
 * @version 1.0.0
 * @date 2012-10-12
 */
public class Module implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * id,唯一的编号
     */
    protected String id;

    /**
     * 名称
     */
    protected String name;

    /**
     * 描述
     */
    protected String description;

    /**
     * 依赖的模块的id
     */
    protected String[] dependencies;

    /**
     * 配置的页面
     */
    protected Map<String, List<String>> pages;

    /**
     * 配置的资源文件
     */
    protected Map<String, List<String>> resources;

    /**
     * 设置菜单数据
     */
    protected Map<String, ModuleSetting> settings;

    /**
     * web资源
     */
    protected Set<String> webapp;

    /**
     * 其它参数
     */
    protected Map<String, String> params;

    /**
     * 设置菜单的名称
     */
    protected String settingName;

    /**
     * 版本号
     */
    protected String version;

    /**
     * 上次部署的版本号
     */
    protected String lastVersion;

    /**
     * 状态
     */
    protected int status;

    /**
     * 包所在的路径
     */
    protected String mappedPath;

    // boolean forceUpdate;

    /**
     * 部署处理器
     */
    protected ModuleInstallHandler handler;

    /**
     * 首次部署的时间，以后更新不变
     */
    protected Date firstDeployDate = new Date();

    /**
     * 最后一次更新的时间
     */
    protected Date updateDate;

    /**
     * 只提供api的支持
     */
    protected boolean apiSupport;

    /**
     * @param name
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


    public boolean isApiSupport() {
        return apiSupport;
    }

    public void setApiSupport(boolean apiSupport) {
        this.apiSupport = apiSupport;
    }
}
