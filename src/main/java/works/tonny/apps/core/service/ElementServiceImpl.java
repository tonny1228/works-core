package works.tonny.apps.core.service;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.llama.library.utils.Assert;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.Element;
import works.tonny.apps.core.ElementService;
import works.tonny.apps.core.dao.ElementDAO;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;

/**
 * Service object for domain model class Element.
 * 
 * @see works.tonny.core.elearning.entity.Element
 * @author Tonny Liu
 */
public class ElementServiceImpl extends AuthedAbstractService implements ElementService {
	private ElementDAO elementDAO;

	/**
	 * 读取Element
	 * 
	 * @param id 编号
	 * @return Element
	 */
	public Element get(String id) {
		Element element = elementDAO.get(id);
		return element;
	}

	/**
	 * 创建Element
	 * 
	 * @param element
	 * @return
	 */
	public String save(Element element) {
		verify(element);
		return elementDAO.save(element);
	}

	/**
	 * @Title: verify
	 * @param element
	 * @date 2012-7-17 下午4:07:37
	 * @author tonny
	 * @version 1.0
	 */
	private void verify(Element element) {
		element.setUpdateTime(new Date());
		if (element.getOptions() != null) {
			element.setOptions(element.getOptions().replaceAll("\\r", "\n").replaceAll("\\n\\n", "\n"));
		}
		LoginedUser admin = getLoginedUser();

		element.setAdmin(admin.getUser().getUsername());
	}

	/**
	 * 更新Element
	 * 
	 * @param element
	 */
	public void update(Element element) {
		Element db = get(element.getId());
		PropertiesUtils.copyProperties(db, element, "name", "dataType", "viewType", "options", "defaultValue",
				"required", "minLength", "maxLength", "regex", "catelog");
		verify(db);
		elementDAO.update(db);
	}

	/**
	 * 通过id删除Element
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		Element element = elementDAO.get(id);
		Assert.notNull(element);
		elementDAO.delete(element);
	}

	/**
	 * 通过多个id删除Element
	 * 
	 * @param ids id数组
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	/**
	 * 分页查询Element列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	public PagedList<Element> list(int page, int pagesize) {
		return elementDAO.list(page, pagesize);
	}

	public ElementDAO getElementDAO() {
		return elementDAO;
	}

	public void setElementDAO(ElementDAO elementDAO) {
		this.elementDAO = elementDAO;
	}

	/**
	 * @param catalog
	 * @param page
	 * @param pagesize
	 * @return
	 * @see com.zxtx.apps.core.ElementService#list(java.lang.String, int, int)
	 */
	public PagedList<Element> list(String catalog, int page, int pagesize) {
		return null;
	}

}
