/**
 * 
 */
package works.tonny.apps.core.dao;

import java.util.HashMap;
import java.util.Map;

import works.tonny.apps.core.IDSequence;
import works.tonny.apps.support.BaseDAOSupport;

/**
 * @author 祥栋
 */
public class IDSequenceDAOImpl extends BaseDAOSupport<IDSequence> implements IDSequenceDAO {

	private Map<String, Long> usedMappedValues = new HashMap<String, Long>();

	private Map<String, Long> unUsedMappedValues = new HashMap<String, Long>();

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.service.IDSequenceService#makeValue(java.lang.String)
	 */
	public long makeValue(String id) {
		Long current = usedMappedValues.get(id);
		Long max = unUsedMappedValues.get(id);
		if (current != null && current < max) {
			usedMappedValues.put(id, current + 1);
		} else {
			IDSequence sequence = get(id);
			if (sequence == null) {
				sequence = new IDSequence(id, 21, 20);
				save(sequence);
			} else {
				sequence.setNextVal(sequence.getNextVal() + sequence.getStep());
				update(sequence);
			}
			current = sequence.getNextVal() - sequence.getStep();
			usedMappedValues.put(id, current + 1);
			unUsedMappedValues.put(id, current + sequence.getStep());
		}
		return current;
	}

}
