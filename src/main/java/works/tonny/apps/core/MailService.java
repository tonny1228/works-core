/**
 * 
 */
package works.tonny.apps.core;


/**
 * 邮件服务
 * 
 * @author 祥栋
 * @date 2013-1-1
 * @version 1.0.0
 */
public interface MailService {
	/**
	 * 发送邮件
	 * 
	 * @param mail 邮件内容及发送人信息
	 */
	void sendMail(Mail mail);

}
