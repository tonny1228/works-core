package works.tonny.apps.core.service;

// Generated 2012-5-5 11:21:00 by Hibernate Tools 3.4.0.CR1

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityService;
import works.tonny.apps.core.Mail;
import works.tonny.apps.core.MailService;


/**
 * Service interface for domain model class Mail.
 * 
 * @see works.tonny.core.elearning.entity.Mail
 * @author Tonny Liu
 * @version 1.0
 * @updated 08-五月-2012 16:59:53
 */
public interface MailEntityService extends EntityService<Mail>, MailService {
	/**
	 * 邮件列表
	 * 
	 * @Title: list
	 * @param page
	 * @param pagesize
	 * @return
	 * @date 2012-6-29 下午3:39:14
	 * @author tonny
	 * @version 1.0
	 */
	PagedList<Mail> list(int page, int pagesize);

}
