/**
 * 
 */
package works.tonny.apps.core.dao;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.IDSequence;

/**
 * @author 祥栋
 */
public interface IDSequenceDAO extends EntityDAO<IDSequence> {

	long makeValue(String id);
}
