/**
 * 
 */
package works.tonny.apps.deploy;

import java.util.List;

import org.llama.library.configuration.XMLConfig;

/**
 * 模块安装处理
 * 
 * @author 祥栋
 */
public interface ModuleInstallHandler {

	/**
	 * 安装前准备工作
	 * 
	 * @param module
	 */
	Module load(XMLConfig config) throws DeployException;

	void prepareInstall() throws DeployException;

	// void prepareUpdate() throws DeployException;

	/**
	 * 进行安装处理
	 * 
	 * @param module
	 */
	void install() throws DeployException;

	void installDatabase(List<Schema> schemas, SchemaService schemaService) throws DeployException;

	/**
	 * 升级模块
	 * 
	 * @param module
	 */
	// void update() throws DeployException;

	// void updateDatabase(String dbType, List<Schema> schemas, SchemaService
	// schemaService) throws
	// DeployException;

	/**
	 * 卸载模块处理方法
	 * 
	 * @param module
	 */
	void uninstall() throws DeployException;

	/**
	 * @param parseBoolean
	 * @author tonny
	 */
	void setIgnoreDatabaseException(boolean parseBoolean);
}
