/**  
 * @Title: ListMethodAdvisor.java
 * @Package works.tonny.framework.dao
 * @author Tonny
 * @date 2011-10-15 下午4:25:14
 */
package works.tonny.apps.support;

import org.springframework.aop.support.DefaultIntroductionAdvisor;

/**
 * @ClassName: ListMethodAdvisor
 * @Description:
 * @author Tonny
 * @date 2011-10-15 下午4:25:14
 * @version 1.0
 */
public class ListMethodAdvisor extends DefaultIntroductionAdvisor {
	/**
	 * <p>
	 * </p>
	 * 
	 * @param advice
	 */
	public ListMethodAdvisor() {
		super(new ListMethodInterceptor());
	}

}
