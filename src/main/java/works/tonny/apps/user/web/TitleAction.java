package works.tonny.apps.user.web;

// Generated 2012-12-3 13:14:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.model.Title;
import works.tonny.apps.user.service.TitleService;

/**
 * <p>
 * Struts2 authed action for domain model class Title.
 * </p>
 * <p>
 * Title管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see entity.Title
 * @author Tonny Liu
 * @version 1.0.0
 */
public class TitleAction extends AuthedAction {
	private String id;

	private TitleService titleService;

	private Title title;

	public String add() {
		return SUCCESS;
	}

	/**
	 * 保存数据
	 */
	public String save() {
		titleService.save(title);
		return SUCCESS;
	}

	/**
	 * 载入数据并进入编辑页面
	 */
	public String edit() {
		title = titleService.get(id);
		request.setAttribute("title", title);
		return SUCCESS;
	}

	/**
	 * 载入数据并查看
	 */
	public String view() {
		title = titleService.get(id);
		request.setAttribute("title", title);
		return SUCCESS;
	}

	/**
	 * 更新数据
	 */
	public String update() {
		titleService.update(title);
		return SUCCESS;
	}

	/**
	 * 批量删除数据
	 */
	public String delete() {
		String[] ids = id.split(", ");
		titleService.delete(ids);
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String list() {
		List<Title> list = titleService.list();
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TitleService getTitleService() {
		return titleService;
	}

	public void setTitleService(TitleService service) {
		this.titleService = service;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}
}
