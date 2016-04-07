/**
 * Copied from the package:'works.tonny.apps.support'. Please don't modify the code at any time,otherwise it will be overwritten!
 */

package works.tonny.apps.support.ui;

import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.llama.library.utils.HttpRequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 祥栋
 */
@StrutsTag(name = "page", tldTagClass = "com.zxtx.apps.support.ui.PageTag", description = "Render an HTML input field of type datetime", allowDynamicAttributes = true)
public class Page extends UIBean {

    private int page;

    private int pagesize;

    private int offset;

    private int limit;

    private int total = -1;

    private int totalPage = -1;

    private String requestURL;

    private String queryString;

    private String emptyMessage;

    /**
     * @param stack
     * @param request
     * @param response
     */
    public Page(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
     */
    @Override
    protected String getDefaultTemplate() {
        return "page";
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();
        if (totalPage == -1) {
            totalPage = (int) Math.ceil(total * 1.0 / Math.max(pagesize, limit));
        }
        addParameter("page", page);
        addParameter("limit", limit);
        addParameter("pagesize", pagesize);
        addParameter("offset", offset);
        addParameter("total", total);
        addParameter("totalPage", totalPage);


        if (StringUtils.isEmpty(requestURL)) {
            requestURL = (request.getContextPath() != null ? request.getContextPath() : "")
                    + request.getAttribute("struts.request_uri") != null ? request.getAttribute("struts.request_uri")
                    .toString() : request.getRequestURI();
        }

        StringBuffer buffer = new StringBuffer();
        buffer.append(requestURL);
        if (StringUtils.isEmpty(queryString)) {
            String qs = HttpRequestUtils.queryString(request, "utf-8");
            buffer.append("?");
            if (qs != null) {
                buffer.append(qs.replaceAll("&?page=\\d+", ""));
            }
            if (buffer.charAt(buffer.length() - 1) != '?') {
                buffer.append("&");
            }
            queryString = buffer.toString();
        }
        addParameter("queryString", queryString);
        addParameter("emptyMessage", emptyMessage);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.apache.struts2.components.Component#addParameter(String, Object)
     */
    public void addParameter(String key, String value) {
        if (value != null) {
            super.addParameter(key, findString(value));
        }
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
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
     * @return the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * @param totalPage the totalPage to set
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
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


    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
