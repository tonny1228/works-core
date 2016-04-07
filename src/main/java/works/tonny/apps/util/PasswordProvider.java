/**
 * 
 */
package works.tonny.apps.util;

import org.llama.library.cryptography.Decryptable;
import org.llama.library.cryptography.Encryptable;

/**
 * 配置文件中密码解码器
 * 
 * @author 祥栋
 * @date 2012-11-21
 * @version 1.1.0
 */
public class PasswordProvider {

	private Decryptable decryptable;

	private Encryptable encryptable;

	/**
	 * 
	 */
	public PasswordProvider() {

	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public String decode(String source) throws Exception {
		return decryptable.decrypt(source);
	}

	/**
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public String encode(String source) throws Exception {
		String encrypt = encryptable.encrypt(source);
		return encrypt;
	}

	public Decryptable getDecryptable() {
		return decryptable;
	}

	public void setDecryptable(Decryptable decryptable) {
		this.decryptable = decryptable;
	}

	/**
	 * @return the encryptable
	 */
	public Encryptable getEncryptable() {
		return encryptable;
	}

	/**
	 * @param encryptable the encryptable to set
	 */
	public void setEncryptable(Encryptable encryptable) {
		this.encryptable = encryptable;
	}

}
