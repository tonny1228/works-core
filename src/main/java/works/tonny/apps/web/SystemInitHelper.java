/**
 * 
 */
package works.tonny.apps.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 系统启动工具，将所有要加载的类放到里面初始化
 * 
 * @author 祥栋
 */
public class SystemInitHelper extends HttpServlet {
	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
}
