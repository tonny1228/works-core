/**
 * 
 */
package works.tonny.apps.support.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import org.llama.library.utils.PagedList;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 分页标签
 * 
 * @author 祥栋
 */
public class PageTag extends AbstractUITag {

	private int currentPage;

	private int total;

	private int pagesize;

	private String bindData;

	private String requestURL;

	private String queryString;

	private String emptyMessage;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.views.jsp.ComponentTagSupport#getBean(com.opensymphony.xwork2.util.ValueStack,
	 *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new Page(stack, request, response);
	}

	protected void populateParams() {
		super.populateParams();
		Page page = (Page) component;
		if (StringUtils.isNotEmpty(bindData)) {
			Object object = findValue(bindData);
			if (object instanceof PagedList) {
				PagedList list = (PagedList) object;
				currentPage = list.getPage();
				total = list.getTotal();
				pagesize = list.getPagesize();
			}
		}
		page.setPage(currentPage);
		page.setPagesize(pagesize);
		page.setTotal(total);
		page.setEmptyMessage(emptyMessage);
		page.setQueryString(queryString);
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the pagesize
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * @return the bindData
	 */
	public String getBindData() {
		return bindData;
	}

	/**
	 * @param bindData the bindData to set
	 */
	public void setBindData(String bindData) {
		this.bindData = bindData;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}

	/**
	 * @param queryString the queryString to set
	 */
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	/**
	 * @return the emptyMessage
	 */
	public String getEmptyMessage() {
		return emptyMessage;
	}

	/**
	 * @param emptyMessage the emptyMessage to set
	 */
	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	/**
	 * @return the requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param requestURL the requestURL to set
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
}