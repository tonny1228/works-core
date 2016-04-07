package works.tonny.apps.core.dao;

// Generated 2012-5-5 11:21:00 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.Mail;
import works.tonny.apps.support.ListSupport;


/**
 * DAO interface for domain model class Mail.
 * 
 * @see works.tonny.core.elearning.entity.Mail
 * @author Tonny Liu
 */
public interface MailDAO extends EntityDAO<Mail> {
	/**
	 * 分页查询Mail列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	@ListSupport()
	PagedList<Mail> list(int page, int pagesize);
}
