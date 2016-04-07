/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-2-28 下午1:40:27
 * @author tonny
 */
package works.tonny.apps.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.llama.library.utils.ClassUtils;
import org.springframework.util.Assert;

/**
 * <p>
 * 超级可配置的过滤器,可将多个相同地址的过滤器合并为一个过滤器,并支持排除路径的配置
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class SuperConfigurationFilter implements Filter {
	private List<Filter> casFilters = new ArrayList<Filter>();

	private String[] excludesPath;

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		final String uri = request.getRequestURI();
		for (String exclude : excludesPath) {
			if (uri.matches(exclude)) {
				filterChain.doFilter(req, res);
				return;
			}
		}
		SubChain subChain = new SubChain();
		subChain.doFilter(req, res);

		if (subChain.isFinished())
			filterChain.doFilter(req, res);
	}

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		final String clz = filterConfig.getInitParameter("filterClasses");
		final String[] filterClass = clz.split(",");
		for (int i = 0; i < filterClass.length; i++) {
			final Filter newInstance = (Filter) ClassUtils.newInstance(filterClass[i].trim());
			this.casFilters.add(newInstance);
			newInstance.init(filterConfig);
		}
		final String exclude = filterConfig.getInitParameter("excludes");
		if (exclude != null) {
			excludesPath = exclude.split(",");
		} else {
			excludesPath = ArrayUtils.EMPTY_STRING_ARRAY;
		}
	}

	class SubChain implements FilterChain {
		private ServletRequest request;

		private ServletResponse response;

		private Iterator<Filter> iterator;

		private boolean finished = false;

		/**
		 * 
		 */
		public SubChain() {
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
			Assert.notNull(request, "Request must not be null");
			Assert.notNull(response, "Response must not be null");

			if (this.request != null) {
				throw new IllegalStateException("This FilterChain has already been called!");
			}

			if (this.iterator == null) {
				this.iterator = casFilters.iterator();
			}

			if (this.iterator.hasNext()) {
				Filter nextFilter = this.iterator.next();
				nextFilter.doFilter(request, response, this);
			} else {
				finished = true;
			}

			this.request = request;
			this.response = response;
		}

		public boolean isFinished() {
			return finished;
		}
	}

}
