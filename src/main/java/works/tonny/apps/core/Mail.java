package works.tonny.apps.core;

// Generated 2012-5-8 13:17:01 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * Mail generated by hbm2java
 * 
 * @author Tonny Liu
 */
@javax.persistence.Entity
@Table(name = "s_mail")
public class Mail extends Entity {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private String title;
	private String content;
	private String to;
	private String cc;
	private String bcc;
	private Date sendDate;
	private Date updateDate;
	private String updateUser;

	public Mail() {
	}

	public Mail(String id, Date updateDate, String updateUser) {
		setId(id);
		setUpdateDate(updateDate);
		setUpdateUser(updateUser);
	}

	public Mail(String title, String content, String to, String cc, String bcc, Date sendDate, Date updateDate,
				String updateUser) {
		setTitle(title);
		setContent(content);
		this.to = to;
		setCc(cc);
		setBcc(bcc);
		setSendDate(sendDate);
		setUpdateDate(updateDate);
		setUpdateUser(updateUser);
	}

	public Mail(String title, String content, String to) {
		setTitle(title);
		setContent(content);
		this.to = to;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.Entity#getId()
	 */
	@Override
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	@Column(length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(length = 2000)
	public String getCc() {
		return this.cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	@Column(length = 2000)
	public String getBcc() {
		return this.bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	@Column(name = "send_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_user", length = 50)
	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "mail", length = 2000)
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}