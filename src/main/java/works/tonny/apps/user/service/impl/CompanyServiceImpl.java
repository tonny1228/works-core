package works.tonny.apps.user.service.impl;

// Generated 2012-12-3 13:14:30 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.llama.library.utils.Assert;
import org.llama.library.utils.PropertiesUtils;

import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageEvent.Status;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.dao.CompanyDAO;
import works.tonny.apps.user.model.Company;
import works.tonny.apps.user.service.CompanyService;

/**
 * Service object for domain model class Company.
 * 
 * @see entity.Company
 * @author Tonny Liu
 */
public class CompanyServiceImpl extends AuthedAbstractService implements CompanyService {
	private CompanyDAO companyDAO;

	/**
	 * 读取Company
	 * 
	 * @param id 编号
	 * @return Company
	 */
	public Company get(String id) {
		Company company = companyDAO.get(id);
		return company;
	}

	/**
	 * 创建Company
	 * 
	 * @param company
	 * @return
	 */
	public String save(Company company) {
		validate(company);
		String id = companyDAO.save(company);
		MessageManager.notify(Company.class, MessageEvent.CREATED, company);
		return id;
	}

	/**
	 * 验证并初始数据
	 */
	private void validate(Company company) {
	}

	/**
	 * 更新Company
	 * 
	 * @param company
	 */
	public void update(Company company) {
		Company db = get(company.getId());
		PropertiesUtils.copyProperties(db, company, "name", "info", "orderNo", "type", "createTime", "updateTime",
				"departments");
		companyDAO.update(db);
		MessageManager.notify(Company.class, MessageEvent.UPDATED, db, company);
	}

	/**
	 * 通过id删除Company
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		Company company = companyDAO.get(id);
		Assert.notNull(company);
		companyDAO.delete(company);
		MessageManager.notify(Company.class, MessageEvent.DELETED, company);
	}

	/**
	 * 通过多个id删除Company
	 * 
	 * @param ids id数组
	 */
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	/**
	 * 查询Company列表
	 * 
	 * @return 分页列表
	 */
	public List<Company> list() {
		return companyDAO.list();
	}

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

}
