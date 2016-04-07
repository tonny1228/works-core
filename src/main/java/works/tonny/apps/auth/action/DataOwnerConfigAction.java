/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-12-18 上午11:23:52
 * @author tonny
 */
package works.tonny.apps.auth.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import works.tonny.apps.auth.DataOwnerConfig;
import works.tonny.apps.auth.DataOwnerConfigService;
import works.tonny.apps.user.AuthedAction;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@Repository("dataOwnerConfigAction")
@Scope("prototype")
public class DataOwnerConfigAction extends AuthedAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DataOwnerConfigService dataOwnerConfigService;

	private DataOwnerConfig config;

	private String id;

	/**
	 * 配置列表
	 * 
	 * @return
	 * @author tonny
	 */
	public String list() {
		request.setAttribute("list", dataOwnerConfigService.all());
		return "list";
	}

	public String add() {
		return "add";
	}

	public String edit() {
		config = dataOwnerConfigService.get(id);
		return "edit";
	}

	public String save() {
		dataOwnerConfigService.save(config);
		return "rdlist";
	}

	public String update() {
		dataOwnerConfigService.update(config);
		return "rdlist";
	}

	public String delete() {
		dataOwnerConfigService.delete(id);
		return "rdlist";
	}

	/**
	 * @return the dataOwnerConfigService
	 */
	public DataOwnerConfigService getDataOwnerConfigService() {
		return dataOwnerConfigService;
	}

	/**
	 * @param dataOwnerConfigService the dataOwnerConfigService to set
	 */
	@Resource()
	public void setDataOwnerConfigService(DataOwnerConfigService dataOwnerConfigService) {
		this.dataOwnerConfigService = dataOwnerConfigService;
	}

	/**
	 * @return the config
	 */
	public DataOwnerConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(DataOwnerConfig config) {
		this.config = config;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}