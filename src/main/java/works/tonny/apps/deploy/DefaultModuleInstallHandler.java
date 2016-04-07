/**
 *
 */
package works.tonny.apps.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.configuration.XMLConfig;
import org.llama.library.log.Log;
import org.llama.library.utils.WebAppPath;

import works.tonny.apps.exception.ServiceException;

/**
 * @author 祥栋
 */
public class DefaultModuleInstallHandler implements ModuleInstallHandler, Serializable {

	protected Module module;

	protected Date updateDate = new Date();

	protected boolean ignoreDatabaseException;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#prepare(works.tonny.apps.deploy.Module)
	 */
	public Module load(XMLConfig config) throws DeployException {
		String name = config.getString("name");
		String id = config.getString("id");
		String desc = config.getString("desc");
		String version = config.getString("version");
		module = new Module(id, name, desc, version);
		module.setHandler(this);
		List<String> keys = config.keys("page");
		String den = config.getString("dependency");
		if (StringUtils.isNotEmpty(den)) {
			module.setDependencies(den.split(","));
		}
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
					module.addResource(StringUtils.substringAfter(key, "."), config.getString(key + "(" + i + ")"));
				}
			}
		}
		if (config.size("settings") > 0) {
			String settingName = config.getString("settings.name");
			module.setSettingName(settingName);
			int size = config.size("settings.setting");
			for (int i = 0; i < size; i++) {
				module.addSetting(new ModuleSetting(config.getString("settings.setting", "", i), config.getString(
						"settings.setting", "url", i), config.getString("settings.setting", "auth", i)));
			}
		}
		int paramSize = config.size("param");
		if (paramSize > 0) {
			for (int i = 0; i < paramSize; i++) {
				module.addParam(config.getString("param", "name", i), config.getString("param", "value", i));
			}
		}
		return module;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#prepareInstall()
	 */
	public void prepareInstall() throws DeployException {
		if (module.getLastVersion() != null && !module.getLastVersion().equals(module.getVersion())) {
			module.setUpdateDate(new Date());
			prepareUpdate();
			return;
		} else if (module.getLastVersion() != null && module.getLastVersion().equals(module.getVersion())) {
			if (module.getVersion().endsWith("-SNAPSHOT")) {
				prepareUpdate();
			}
			return;
		}
		module.setUpdateDate(new Date());
		installStruts();
		installSQLMapping();
		try {
			installWebapp();
		} catch (IOException e) {
			throw new DeployException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#prepareUpdate()
	 */
	public void prepareUpdate() throws DeployException {
		try {
			installWebapp();
		} catch (IOException e) {
			throw new DeployException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#install(works.tonny.apps.deploy.Module)
	 */
	public void install() throws DeployException {
		if (module.getLastVersion() != null && !module.getLastVersion().equals(module.getVersion())) {
			update();
			return;
		} else if (module.getLastVersion() != null && module.getLastVersion().equals(module.getVersion())) {
			return;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#installDatabase(java.lang.String,
	 *      java.sql.Connection, works.tonny.apps.deploy.SchemaService)
	 */
	public void installDatabase(List<Schema> schemas, SchemaService schemaService) throws DeployException {
		if (module.getLastVersion() != null && !module.getLastVersion().equals(module.getVersion())) {
			updateDatabase(schemas, schemaService);
			return;
		}
		if (module.getStatus() > 0) {
			return;
		}
		List<String> files = module.getResources("db-" + schemaService.getType());
		List<String> initFiles = module.getResources("db-" + schemaService.getType() + "-init");
		if (files != null && initFiles != null && !initFiles.isEmpty())
			files.addAll(initFiles);
		if (files == null && initFiles != null) {
			files = initFiles;
		}
		if (files == null) {
			return;
		}
		for (Schema schema : schemas) {
			Connection connection = null;
			try {
				connection = schema.getWriteDataSource().getConnection();
				for (String file : files) {
					Log.info("运行数据库文件{0}", file);
                    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
                    String text = IOUtils.toString(inputStream, "utf-8");
					inputStream.close();
					// 创建表,初始化语句
					// statement.execute(text);
					schemaService.createObjects(connection, schema.getId(), text);
				}
			} catch (IOException e) {
				throw new ServiceException("sql配置文件读取出错", e);
			} catch (SQLException e) {
				Log.error("sql安装出错", e);
				if (!ignoreDatabaseException)
					throw new ServiceException("sql执行出错:" + e.getMessage());
			} finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						throw new ServiceException("链接关闭错误:" + schema.getName(), e);
					}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#updateDatabase(java.lang.String,
	 *      java.util.List, works.tonny.apps.deploy.SchemaService)
	 */
	public void updateDatabase(List<Schema> schemas, SchemaService schemaService) throws DeployException {
	}

	/**
	 * @throws IOException
	 * @throws ZipException
	 */
	protected void installWebapp() throws ZipException, IOException {
		File file = new File(module.getMappedPath());
		ZipFile zipFile = new ZipFile(file);
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		String prefix = "webapp/";
		Log.info("copy web resources");
		long time = updateDate.getTime() - 1000;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			if (!fileName.startsWith(prefix) || fileName.endsWith("/")) {
				continue;
			}
			File target = new File(WebAppPath.webinfPath() + File.separator + "custom", fileName.replaceFirst(prefix,
					""));
			File f = new File(WebAppPath.webRootPath(), fileName.replaceFirst(prefix, ""));
			if (target.exists()) {
				Log.debug("custom web resource " + fileName);
				FileUtils.copyFile(target, f);
				continue;
			}

			if (!f.getParentFile().exists())
				f.getParentFile().mkdirs();
			OutputStream os = new FileOutputStream(f);
			// 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			InputStream is = zipFile.getInputStream(zipEntry);
			IOUtils.copy(is, os);
			module.addWebResource(fileName);
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
			Log.debug("release web resource " + fileName);
		}
		zipInputStream.close();
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void installSQLMapping() throws DeployException {
		if (module.getResources("sqlmapping") == null) {
			return;
		}
		for (String sql : module.getResources("sqlmapping")) {
			File sqlFile = new File(WebAppPath.classesPath(), sql);
			if (module.getVersion().equals(module.getLastVersion()) && sqlFile.exists()) {
				continue;
			} else {
				FileUtils.deleteQuietly(sqlFile);
			}
			if (!sqlFile.getParentFile().exists()) {
				sqlFile.getParentFile().mkdirs();
			}
			FileOutputStream sqlOutputStream = null;
			try {
				sqlOutputStream = new FileOutputStream(sqlFile);
				IOUtils.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<sql-mapping></sql-mapping>",
						sqlOutputStream);
			} catch (FileNotFoundException e) {
				throw new DeployException(e);
			} catch (IOException e) {
				throw new DeployException(e);
			} finally {
				IOUtils.closeQuietly(sqlOutputStream);
			}
		}
	}

	/**
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void installStruts() throws DeployException {
		if (module.getResources("struts") == null) {
			return;
		}
		for (String struts : module.getResources("struts")) {
			File strutsFile = new File(WebAppPath.classesPath(), struts);
			if (!module.getVersion().equals(module.getLastVersion()) && strutsFile.exists()) {
				continue;
			}
			if (!strutsFile.getParentFile().exists()) {
				strutsFile.getParentFile().mkdirs();
			}
			FileOutputStream strutsOutputStream = null;
			try {
				strutsOutputStream = new FileOutputStream(strutsFile);
				IOUtils.write(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE struts PUBLIC	\"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN\"	\"http://struts.apache.org/dtds/struts-2.0.dtd\"><struts></struts>",
						strutsOutputStream);
			} catch (FileNotFoundException e) {
				throw new DeployException(e);
			} catch (IOException e) {
				throw new DeployException(e);
			} finally {
				IOUtils.closeQuietly(strutsOutputStream);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#uninstall(works.tonny.apps.deploy.Module)
	 */
	public void uninstall() throws DeployException {
		for (String struts : module.getResources("struts")) {
			File strutsFile = new File(WebAppPath.classesPath(), struts);
			FileUtils.deleteQuietly(strutsFile);
		}
		for (String sql : module.getResources("sqlmapping")) {
			File sqlFile = new File(WebAppPath.classesPath(), sql);
			FileUtils.deleteQuietly(sqlFile);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see works.tonny.apps.deploy.ModuleInstallHandler#update()
	 */
	public void update() throws DeployException {
	}

	/**
	 * @param ignoreDatabaseException the ignoreDatabaseException to set
	 */
	public void setIgnoreDatabaseException(boolean ignoreDatabaseException) {
		this.ignoreDatabaseException = ignoreDatabaseException;
	}

}
