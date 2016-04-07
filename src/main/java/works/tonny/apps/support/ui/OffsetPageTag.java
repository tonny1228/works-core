package works.tonny.apps.support.ui;

import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;
import org.llama.library.utils.PagedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tonny on 2015/11/17.
 */
public class OffsetPageTag extends AbstractUITag {

    private int offset;

    private int total;

    private int limit;

    private String bindData;

    private String requestURL;

    private String queryString;

    private String emptyMessage;


    /**
     * {@inheritDoc}
     *
     * @see org.apache.struts2.views.jsp.ComponentTagSupport#getBean(com.opensymphony.xwork2.util.ValueStack,
     * HttpServletRequest, HttpServletResponse)
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
                offset = list.getOffset();
                total = list.getTotal();
                limit = list.getPagesize();
            }
        }
        page.setOffset(offset);
        page.setLimit(limit);
        page.setTotal(total);
        page.setEmptyMessage(emptyMessage);
        page.setQueryString(queryString);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getBindData() {
        return bindData;
    }

    public void setBindData(String bindData) {
        this.bindData = bindData;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }
}
