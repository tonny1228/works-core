/**
 * 
 */
package works.tonny.apps.core.service;

import works.tonny.apps.auth.AuthType;
import works.tonny.apps.auth.AuthVerify;
import works.tonny.apps.core.IDGenerator;
import works.tonny.apps.core.IDGeneratorService;

/**
 * @author 祥栋
 */
public interface IDGeneratorEntityService extends IDGeneratorService {
	@AuthVerify(AuthType.CREATE)
	void save(IDGenerator generator);

	@AuthVerify(AuthType.UPDATE)
	void update(IDGenerator generator);

	@AuthVerify(AuthType.DELETE)
	void delete(String id);
}
