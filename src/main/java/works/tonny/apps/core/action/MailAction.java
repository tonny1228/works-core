package works.tonny.apps.core.action;

// Generated 2012-5-5 11:21:00 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.llama.library.utils.PagedList;

import works.tonny.apps.core.AttachReference;
import works.tonny.apps.core.AttachReferenceService;
import works.tonny.apps.core.Mail;
import works.tonny.apps.core.service.MailEntityService;
import works.tonny.apps.user.AuthedAction;


/**
 * <p>
 * Struts2 authed action for domain model class Mail.
 * </p>
 * <p>
 * Mail管理web接口，用户添加、修改、删除、列表等操作.
 * </p>
 * 
 * @see works.tonny.core.elearning.entity.Mail
 * @see works.tonny.user.zxtx.apps.web.AuthedAction
 * @author Tonny Liu
 * @version 1.0.0
 */
public class MailAction extends AuthedAction {
	private String id;

	private MailEntityService mailService;

	private AttachReferenceService attachReferenceService;

	private Mail mail;
	
	

	public String add() {
		return INPUT;
	}

	public String save() {
		mailService.save(mail);
		List<AttachReference> list = attachReferenceService.referenceAttachment(getParameter("token"));
		for (AttachReference attachReference : list) {
			attachReference.setObjectId(mail.getId());
			attachReference.setCatalog("mail");
			attachReferenceService.update(attachReference);
		}
		mailService.sendMail(mail);
		return SUCCESS;
	}

	public String edit() {
		mail = mailService.get(id);

		request.setAttribute("mail", mail);
		return INPUT;
	}

	public String view() {
		mail = mailService.get(id);
		request.setAttribute("mail", mail);
		return VIEW;
	}

	public String update() {
		mailService.update(mail);
		return SUCCESS;
	}

	public String delete() {
		String[] ids = id.split(", ");
		mailService.delete(ids);
		return SUCCESS;
	}

	public String list() {
		PagedList<Mail> list = mailService.list(0, 20);
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MailEntityService getMailService() {
		return mailService;
	}

	public void setMailService(MailEntityService service) {
		this.mailService = service;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public AttachReferenceService getAttachReferenceService() {
		return attachReferenceService;
	}

	public void setAttachReferenceService(AttachReferenceService attachReferenceService) {
		this.attachReferenceService = attachReferenceService;
	}
}
