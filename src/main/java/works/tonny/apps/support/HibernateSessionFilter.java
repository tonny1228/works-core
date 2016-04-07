/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-12-3 上午10:09:10
 * @author tonny
 */
package works.tonny.apps.support;

import org.hibernate.Session;

/**
 * <p>
 * 数据查询过滤器
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface HibernateSessionFilter {
	void doFilter(Session session, Class entityClass);
}