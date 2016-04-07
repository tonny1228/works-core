package works.tonny.apps.user.service;

// Generated 2012-12-3 13:14:30 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import works.tonny.apps.EntityService;
import works.tonny.apps.user.model.Company;

/**
 * Service interface for domain model class Company.
 * 
 * @see entity.Company
 * @author Tonny Liu
 */
public interface CompanyService extends EntityService<Company> {
	/**
	 * 查询Company列表
	 * 
	 * @return 分页列表
	 */
	List<Company> list();
}
