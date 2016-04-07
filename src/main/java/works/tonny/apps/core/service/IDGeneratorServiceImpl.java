/**
 * 
 */
package works.tonny.apps.core.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.llama.library.utils.ELHelper;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.IDGenerator;
import works.tonny.apps.core.dao.IDGeneratorDAO;
import works.tonny.apps.core.dao.IDSequenceDAO;

/**
 * @author 祥栋
 */
public class IDGeneratorServiceImpl extends AbstractIDGeneratorService {

	private IDSequenceDAO sequenceDAO;

	private IDGeneratorDAO generatorDAO;

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.IDGeneratorService#list()
	 */
	public List<IDGenerator> list() {
		return generatorDAO.list();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.IDGeneratorService#get(java.lang.String)
	 */
	public IDGenerator get(String id) {
		IDGenerator generator = generatorDAO.get(id);
		return generator;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.IDGeneratorService#create(java.lang.String, java.util.Map)
	 */
	public String create(String id, Map<String, Object> context) {
		IDGenerator generator = get(id);

		if (!generator.getExpression().contains("${#seq(")) {
			return ELHelper.execute(generator.getExpression(), context);
		}

		String[] sections = generator.getExpression().split(generator.getSpliter());

		String[] values = new String[sections.length];
		for (int i = 0; i < sections.length; i++) {
			if (!sections[i].contains("${#seq("))
				values[i] = ELHelper.execute(sections[i], context);
			else {
				String params = StringUtils.substringBetween(sections[i], "(", ")");
				String seqId = id;

				if (StringUtils.isEmpty(params)) {
					if (i > 0) {
						String[] range = Arrays.copyOfRange(values, 0, i - 1);
						seqId += StringUtils.join(range, generator.getSpliter());
					}
				} else {
					String[] param = params.split("#");
					for (int j = 1; j < param.length; j++) {
						seqId += values[NumberUtils.toInt(param[j]) - 1] + "-";
					}
				}
				values[i] = StringUtils.substringBefore(sections[i], "${")
						+ String.valueOf(sequenceDAO.makeValue(seqId))
						+ StringUtils.substringAfter(StringUtils.substringAfter(sections[i], "${"), "}");
			}

			if (values[i].matches("\\d+\\{\\d\\}")) {
				int len = NumberUtils.toInt(StringUtils.substringBetween(values[i], "{", "}"));
				values[i] = StringUtils.leftPad(StringUtils.substringBefore(values[i], "{"), len, '0');
			}
		}

		return StringUtils.join(values, generator.getSpliter());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.service.IDGeneratorEntityService#save(works.tonny.apps.core.IDGenerator)
	 */
	public void save(IDGenerator generator) {
		generatorDAO.save(generator);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.service.IDGeneratorEntityService#update(works.tonny.apps.core.IDGenerator)
	 */
	public void update(IDGenerator generator) {
		generatorDAO.update(generator);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.core.service.IDGeneratorEntityService#delete(java.lang.String)
	 */
	public void delete(String id) {
		generatorDAO.delete(get(id));
	}

	/**
	 * @return the generatorDAO
	 */
	public IDGeneratorDAO getGeneratorDAO() {
		return generatorDAO;
	}

	/**
	 * @param generatorDAO the generatorDAO to set
	 */
	public void setGeneratorDAO(IDGeneratorDAO generatorDAO) {
		this.generatorDAO = generatorDAO;
	}

	/**
	 * @return the sequenceDAO
	 */
	public IDSequenceDAO getSequenceDAO() {
		return sequenceDAO;
	}

	/**
	 * @param sequenceDAO the sequenceDAO to set
	 */
	public void setSequenceDAO(IDSequenceDAO sequenceDAO) {
		this.sequenceDAO = sequenceDAO;
	}

}
