package works.tonny.apps.user.service.impl;

// Generated 2012-12-3 13:14:30 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.dao.TitleDAO;
import works.tonny.apps.user.model.Title;
import works.tonny.apps.user.service.TitleService;

/**
 * Service object for domain model class Title.
 * 
 * @see entity.Title
 * @author Tonny Liu
 */
public class TitleServiceImpl extends AuthedAbstractService implements TitleService {
	private TitleDAO titleDAO;

	/**
	 * 读取Title
	 * 
	 * @param id 编号
	 * @return Title
	 */
	public Title get(String id) {
		Title title = titleDAO.get(id);
		return title;
	}

	/**
	 * 创建Title
	 * 
	 * @param title
	 * @return
	 */
	public String save(Title title) {
		validate(title);
		return titleDAO.save(title);
	}

	/**
	 * 验证并初始数据
	 */
	private void validate(Title title) {
	}

	/**
	 * 更新Title
	 * 
	 * @param title
	 */
	public void update(Title title) {
		Title db = get(title.getId());
		PropertiesUtils.copyProperties(db, title, "name", "info", "orderNo");
		titleDAO.update(db);
	}

	/**
	 * 通过id删除Title
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		Title title = titleDAO.get(id);
		Assert.notNull(title);
		titleDAO.delete(title);
	}

	/**
	 * 通过多个id删除Title
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
	 * 查询Title列表
	 * 
	 * @return 列表
	 */
	public List<Title> list() {
		return titleDAO.list();
	}

	public TitleDAO getTitleDAO() {
		return titleDAO;
	}

	public void setTitleDAO(TitleDAO titleDAO) {
		this.titleDAO = titleDAO;
	}

}
