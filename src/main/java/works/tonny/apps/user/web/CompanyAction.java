package works.tonny.apps.user.web;

// Generated 2012-12-3 13:14:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.user.AuthedAction;
import works.tonny.apps.user.model.Company;
import works.tonny.apps.user.service.CompanyService;

/**
 * <p>
 * Struts2 authed action for domain model class Company.
 * </p>
 * <p>
 * Company管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see entity.Company
 * @author Tonny Liu
 * @version 1.0.0
 */
public class CompanyAction extends AuthedAction {
	private String id;

	private CompanyService companyService;

	private Company company;

	/**
	 * 进入添加页面
	 */
	public String add() {
		return INPUT;
	}

	/**
	 * 保存数据
	 */
	public String save() {
		companyService.save(company);
		return SUCCESS;
	}

	/**
	 * 载入数据并进入编辑页面
	 */
	public String edit() {
		company = companyService.get(id);
		request.setAttribute("company", company);
		return INPUT;
	}

	/**
	 * 载入数据并查看
	 */
	public String view() {
		company = companyService.get(id);
		request.setAttribute("company", company);
		return VIEW;
	}

	/**
	 * 更新数据
	 */
	public String update() {
		companyService.update(company);
		return SUCCESS;
	}

	/**
	 * 批量删除数据
	 */
	public String delete() {
		String[] ids = id.split(", ");
		companyService.delete(ids);
		return SUCCESS;
	}

	/**
	 * 查询列表并进入列表页
	 */
	public String list() {
		List<Company> list = companyService.list();
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService service) {
		this.companyService = service;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
