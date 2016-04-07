/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.llama.library.configuration.XMLConfig;
import org.llama.library.utils.WebAppPath;

import works.tonny.apps.support.ServiceRegisterHelper;

/**
 * @author tonny
 * @date 2012-10-11
 * @version 1.0.0
 */
public class ModuleLoaderListener1 implements ServletContextListener {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent contextEvent) {
		ServiceRegisterHelper.initSpring(contextEvent.getServletContext());
		Enumeration<URL> resources;
		try {
			resources = this.getClass().getClassLoader().getResources("module.xml");
			File moduleCache = new File(WebAppPath.webinfPath(), "modules.app");
			if (moduleCache.exists()) {
				ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(moduleCache));
				try {
					ModuleHelper helper = (ModuleHelper) inputStream.readObject();
					ModuleHelper.set(helper);
				} catch (ClassNotFoundException e) {

				} finally {
					inputStream.close();
				}
			}
			while (resources.hasMoreElements()) {
				URL url = (URL) resources.nextElement();
				XMLConfig config = new XMLConfig(url.toString());
				String name = config.getString("name");
				String desc = config.getString("desc");
				String version = config.getString("version");

				Module module = new Module(null,name, desc, version);
				List<String> keys = config.keys("page");
				//module.setOrder(config.getInt("order"));
				log.info(url);
				for (String key : keys) {
					int size = config.size(key);
					if (size == 1) {
						module.addPage(StringUtils.substringAfter(key, "."), config.getString(key));
					} else {
						for (int i = 0; i < size; i++) {
							module.addPage(StringUtils.substringAfter(key, "."), config.getString(key + "(" + i + ")"));
						}
					}
				}
				keys = config.keys("resource");
				for (String key : keys) {
					int size = config.size(key);
					if (size == 1) {
						module.addResource(StringUtils.substringAfter(key, "."), config.getString(key));
					} else {
						for (int i = 0; i < size; i++) {
							module.addResource(StringUtils.substringAfter(key, "."),
									config.getString(key + "(" + i + ")"));
						}
					}
				}
				if (config.size("settings") > 0) {
					String settingName = config.getString("settings.name");
					module.setSettingName(settingName);
					int size = config.size("settings.setting");
					for (int i = 0; i < size; i++) {
						module.addSetting(new ModuleSetting(config.getString("settings.setting", "", i), config
								.getString("settings.setting", "url", i), config.getString("settings.setting", "auth",
								i)));
					}
				}
				ModuleHelper.getInstance().add(module);
			}
			ModuleHelper.getInstance().uninstall();
			ModuleHelper.getInstance().install();
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(moduleCache));
			outputStream.writeObject(ModuleHelper.getInstance());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
