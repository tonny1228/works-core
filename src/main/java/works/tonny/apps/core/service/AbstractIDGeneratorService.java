/**
 * 
 */
package works.tonny.apps.core.service;

/**
 * @author 祥栋
 */
public abstract class AbstractIDGeneratorService implements IDGeneratorEntityService {

	private static AbstractIDGeneratorService generatorService;

	public static IDGeneratorEntityService getInstance() {
		return generatorService;
	}

	/**
	 * 
	 */
	public AbstractIDGeneratorService() {
		generatorService = this;
	}
}
