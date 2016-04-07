/**
 * 
 */
package works.tonny.apps.core.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.llama.library.utils.ThreadLocalMap;
import works.tonny.apps.auth.DataOwnerConfig;
import works.tonny.apps.core.IDGenerator;
import works.tonny.apps.core.service.IDGeneratorEntityService;
import works.tonny.apps.user.AuthedAction;

/**
 * idgenerator action
 * 
 * @author 祥栋
 */
public class IDGeneratorAction extends AuthedAction {
	private IDGeneratorEntityService generatorService;

	private IDGenerator generator;

	private String id;

	private String[] ids;

	public String edit() {
		generator = generatorService.get(id);
		return "edit";
	}

	public String list() {
        ThreadLocalMap.getInstance().putObject(DataOwnerConfig.Type.class, DataOwnerConfig.Type.EDIT);
		List<IDGenerator> list = generatorService.list();
		request.setAttribute("list", list);
		return "listjsp";
	}

	public String save() {
		generatorService.save(generator);
		return SUCCESS;
	}

	public String update() {
		generatorService.update(generator);
		return SUCCESS;
	}

	public String delete() {
		for (String id : ids) {
			generatorService.delete(id);
		}
		return SUCCESS;
	}

	/**
	 * @return the generatorService
	 */
	public IDGeneratorEntityService getGeneratorService() {
		return generatorService;
	}

	/**
	 * @param generatorService the generatorService to set
	 */
	public void setGeneratorService(IDGeneratorEntityService generatorService) {
		this.generatorService = generatorService;
	}

	/**
	 * @return the generator
	 */
	public IDGenerator getGenerator() {
		return generator;
	}

	/**
	 * @param generator the generator to set
	 */
	public void setGenerator(IDGenerator generator) {
		this.generator = generator;
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

	/**
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

}
