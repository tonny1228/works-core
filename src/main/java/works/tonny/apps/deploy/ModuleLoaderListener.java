/**
 *
 */
package works.tonny.apps.deploy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.llama.library.configuration.XMLConfig;
import org.llama.library.utils.WebAppPath;
import works.tonny.apps.support.ServiceRegisterHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author tonny
 * @version 1.0.0
 * @date 2012-10-11
 */
@WebListener()
public class ModuleLoaderListener implements ServletContextListener {
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
            Properties properties = new Properties();
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("apps.properties");
            properties.load(resourceAsStream);
            resourceAsStream.close();
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
            String[] apiSupport = properties.getProperty("module.deploy.api", "").split(",");
            Arrays.sort(apiSupport);
            while (resources.hasMoreElements()) {
                URL url = (URL) resources.nextElement();
                XMLConfig config = new XMLConfig(url.toString());
                ModuleInstallHandler handler = org.llama.library.utils.ClassUtils.newInstance(config
                        .getString("handler"));
                handler.setIgnoreDatabaseException(Boolean.parseBoolean(properties.getProperty(
                        "module.ignoreDatabaseException", "false")));
                Module module = handler.load(config);
                module.setMappedPath(url);
                module.setApiSupport(Arrays.binarySearch(apiSupport, module.getId()) >= 0);
                ModuleHelper.getInstance().add(module);
            }
            ModuleHelper.getInstance().uninstall();
            ModuleHelper.getInstance().install();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(moduleCache));
            outputStream.writeObject(ModuleHelper.getInstance());
            outputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }

    }
}
