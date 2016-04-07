package works.tonny.apps.core.service;

// Generated 2012-5-5 11:21:00 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PagedList;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.AttachReference;
import works.tonny.apps.core.AttachReferenceService;
import works.tonny.apps.core.AttachService;
import works.tonny.apps.core.Mail;
import works.tonny.apps.core.dao.MailDAO;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;

/**
 * Service object for domain model class Mail.
 * 
 * @see works.tonny.core.elearning.entity.Mail
 * @author Tonny Liu
 */
public class MailServiceImpl extends AuthedAbstractService implements MailEntityService {
	private MailDAO mailDAO;

	private JavaMailSenderImpl mailSender;

	private AttachReferenceService attachReferenceService;

	private AttachService attachService;

	/**
	 * @see com.zxtx.apps.core.MailService#sendMail(com.zxtx.apps.core.Mail)
	 */
	public void sendMail(Mail mail) {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mime, true, "utf-8");
			helper.setFrom(mailSender.getJavaMailProperties().getProperty("mail.from", mailSender.getUsername()));
			InternetAddress[] iaToList = new InternetAddress().parse(mail.getTo());
			helper.setTo(iaToList);
			if (StringUtils.isNotBlank(mail.getCc()))
				helper.setCc(mail.getCc());
			if (StringUtils.isNotBlank(mail.getBcc()))
				helper.setBcc(mail.getBcc());
			helper.setSubject(mail.getTitle());
			helper.setText(mail.getContent(), true);
			if (StringUtils.isNotEmpty(mail.getId())) {
				List<AttachReference> list = attachReferenceService.referenceAttachment(mail.getId());
				for (AttachReference attachReference : list) {
					helper.addAttachment(attachReference.getAttachment().getFilename(),
							attachService.attachFile(attachReference.getAttachment()));
				}
				mail.setSendDate(new Date());
				// update(mail);
				mailSender.send(helper.getMimeMessage());
			} else {
				mail.setSendDate(new Date());
				// save(mail);
				mailSender.send(helper.getMimeMessage());
			}

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取Mail
	 * 
	 * @param id 编号
	 * @return Mail
	 */
	public Mail get(String id) {
		Mail mail = mailDAO.get(id);
		return mail;
	}

	/**
	 * 创建Mail
	 * 
	 * @param mail
	 * @return
	 */
	public String save(Mail mail) {
		mail.setUpdateDate(new Date());
		LoginedUser admin = getLoginedUser();

		mail.setUpdateUser(admin.getUser().getUsername());
		return mailDAO.save(mail);
	}

	/**
	 * 更新Mail
	 * 
	 * @param mail
	 */
	public void update(Mail mail) {
		mailDAO.update(mail);
	}

	/**
	 * 通过id删除Mail
	 * 
	 * @param id 编号
	 */
	public void delete(String id) {
		Mail mail = mailDAO.get(id);
		Assert.notNull(mail);
		mailDAO.delete(mail);
	}

	/**
	 * 通过多个id删除Mail
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
	 * 分页查询Mail列表
	 * 
	 * @param page 页码
	 * @param pagesize 每页条数
	 * @return 分页列表
	 */
	public PagedList<Mail> list(int page, int pagesize) {
		return mailDAO.list(page, pagesize);
	}

	public MailDAO getMailDAO() {
		return mailDAO;
	}

	public void setMailDAO(MailDAO mailDAO) {
		this.mailDAO = mailDAO;
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public AttachReferenceService getAttachReferenceService() {
		return attachReferenceService;
	}

	public void setAttachReferenceService(AttachReferenceService attachReferenceService) {
		this.attachReferenceService = attachReferenceService;
	}

	public AttachService getAttachService() {
		return attachService;
	}

	public void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}

}
