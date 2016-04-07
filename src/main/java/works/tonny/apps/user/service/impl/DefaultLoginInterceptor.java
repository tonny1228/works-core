/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-1-4 上午11:59:43
 * @author tonny
 */
package works.tonny.apps.user.service.impl;

import org.apache.commons.lang3.StringUtils;

import works.tonny.apps.user.service.LoginInterceptor;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class DefaultLoginInterceptor implements LoginInterceptor {

	private String schema;

	private String username;

	/**
	 * @param username
	 */
	public DefaultLoginInterceptor(String username) {
		if (username.indexOf("@") > 0) {
			this.username = StringUtils.substringBefore(username, "@");
			this.schema = StringUtils.substringAfter(username, "@");
		} else {
			this.username = username;
		}
	}

	/**
	 * @see works.tonny.apps.user.service.LoginInterceptor#getSchema()
	 */
	@Override
	public String getSchema() {
		return schema;
	}

	/**
	 * @see works.tonny.apps.user.service.LoginInterceptor#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

}
