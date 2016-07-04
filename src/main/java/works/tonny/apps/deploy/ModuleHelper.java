/**
 *
 */
package works.tonny.apps.deploy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 模块管理工具
 *
 * @author tonny
 * @version 1.0.0
 * @date 2012-10-12
 */
public class ModuleHelper implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private transient static Log log = LogFactory.getLog(ModuleHelper.class);

    private static ModuleHelper moduleHelper;

    private Map<String, Module> modules;

    private Set<String> loaded = new HashSet<String>();

    private transient List<Module> installed;

    private ModuleHelper() {
        if (modules == null) {
            modules = new LinkedHashMap<String, Module>();
        }
    }

    static void set(ModuleHelper moduleHelper) {
        ModuleHelper.moduleHelper = moduleHelper;
        log = LogFactory.getLog(ModuleHelper.class);
        moduleHelper.loaded.clear();
    }

    public synchronized static ModuleHelper getInstance() {
        if (moduleHelper == null) {
            log.info("创建ModuleHelper");
            moduleHelper = new ModuleHelper();
        }
        return moduleHelper;
    }

    public Collection<Module> getModules() {
        return installed;
    }

    /**
     * 安装配置文件到服务器
     *
     * @throws IOException void install1() throws IOException { list = new ArrayList<Module>(); for
     *             (String name : loadNames) { log.info("装载模块-" + name); Module module =
     *             modules.get(name); if (list.size() < module.getOrder()) { for (int i =
     *             list.size(); i < module.getOrder() - 1; i++) { list.add(i, null); }
     *             list.add(module.getOrder() - 1, module); } else if (list.size() >
     *             module.getOrder()) { if (list.get(module.getOrder() - 1) == null) {
     *             list.add(module.getOrder() - 1, module); list.remove(module.getOrder()); } else {
     *             list.add(module.getOrder() - 1, module); } } else if (list.size() ==
     *             module.getOrder()) { list.add(module.getOrder(), module); } if
     *             (module.getResources("struts") != null) for (String struts :
     *             module.getResources("struts")) { File strutsFile = new
     *             File(WebAppPath.classesPath(), struts); if (!module.forceUpdate &&
     *             strutsFile.exists()) { continue; } if (!strutsFile.getParentFile().exists()) {
     *             strutsFile.getParentFile().mkdirs(); } FileOutputStream strutsOutputStream = new
     *             FileOutputStream(strutsFile); IOUtils.write(
     *             "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE struts PUBLIC	\"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN\"	\"http://struts.apache.org/dtds/struts-2.0.dtd\"><struts></struts>"
     *             , strutsOutputStream); IOUtils.closeQuietly(strutsOutputStream); } if
     *             (module.getResources("sqlmapping") == null) { continue; } for (String sql :
     *             module.getResources("sqlmapping")) { File sqlFile = new
     *             File(WebAppPath.classesPath(), sql); if (!module.forceUpdate && sqlFile.exists())
     *             { continue; } else { FileUtils.deleteQuietly(sqlFile); } if
     *             (!sqlFile.getParentFile().exists()) { sqlFile.getParentFile().mkdirs(); }
     *             FileOutputStream sqlOutputStream = new FileOutputStream(sqlFile);
     *             IOUtils.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<sql-mapping></sql-mapping>"
     *             , sqlOutputStream); IOUtils.closeQuietly(sqlOutputStream); } } while
     *             (list.indexOf(null) >= 0) { list.remove(null); } }
     */

    /**
     * 安装所有的模块
     *
     * @throws DeployException
     */
    void install() throws DeployException {
        Set<String> ids = modules.keySet();
        installed = new ArrayList<Module>();
        for (String id : ids) {
            Module m = modules.get(id);
            if (m == null) {
                throw new DeployException(id + "不存在");
            }
            install(m);
        }
    }

    /**
     * 安装一个模块
     *
     * @param m
     * @throws DeployException
     */
    protected void install(Module m) throws DeployException {
        if (installed.contains(m)) {
            return;
        }
        String[] dependencies = m.getDependencies();
        for (int i = 0; dependencies != null && i < dependencies.length; i++) {
            install(modules.get(dependencies[i].trim()));
        }

        if (m.isApiSupport()) {
            log.info("装载模块-" + m.getName());
        } else {
            log.info("装载并部署模块-" + m.getName());
            m.getHandler().prepareInstall();
            m.getHandler().install();
        }

        installed.add(m);
    }

    /**
     * 卸载没有发现的模块
     *
     * @throws IOException
     * @throws DeployException
     */
    void uninstall() throws DeployException {
        Set<String> unstall = new HashSet<String>();
        for (String id : modules.keySet()) {
            if (!loaded.contains(id)) {
                unstall.add(id);
            }
        }
        for (String id : unstall) {
            log.info("卸载模块-" + id);
            Module module = modules.get(id);
            module.getHandler().uninstall();
            modules.remove(id);
        }
    }

    /**
     * @param module
     */
    void add(Module module) {
        loaded.add(module.getId());
        String lastVersion = null;
        if (modules.containsKey(module.getId())) {
            Module m = modules.get(module.getId());
            lastVersion = m.getVersion();
            module.setStatus(m.getStatus() + 1);
            module.setWebapp(m.getWebapp());
        }
        if (lastVersion != null) {
            // module.forceUpdate = true;
            module.setLastVersion(lastVersion);
        }
        modules.put(module.getId(), module);
    }

}
