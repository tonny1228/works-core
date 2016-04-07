/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.FileNotFoundException;

/**
 * @author 祥栋
 */
public class DeployException extends Exception {

	/**
	 * @param string
	 */
	public DeployException(String string) {
		super(string);
	}

	/**
	 * @param e
	 */
	public DeployException(Exception e) {
		super(e);
	}
}
