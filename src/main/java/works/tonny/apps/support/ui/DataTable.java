/**
 * 
 */
package works.tonny.apps.support.ui;

import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;
import org.llama.library.utils.HttpRequestUtils;
import org.llama.library.utils.PagedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 祥栋
 */
@StrutsTag(name = "dataTable", tldTagClass = "works.tonny.apps.support.DataTableTag", description = "Render an HTML input field of type datetime", allowDynamicAttributes = true)
public class DataTable extends UIBean {
	private String[] headerNames;

	private String[] fields;

	private String[] widths;

	private String bundle;

	private String textPrefix;

	private boolean multiSelect;

	private boolean showNo;

	private List<String> defaultColumns;

	private Map<String, String> extendValues;
	
	private String checkboxName;
	
	private String checkboxValue;

    private int offset = 0;

    private int limit = 10;



    /**
	 * @param stack
	 * @param request
	 * @param response
	 */
	public DataTable(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		extendValues = new HashMap<String, String>();
		if (headerNames == null && textPrefix != null) {
			headerNames = new String[fields.length];
			for (int i = 0; i < headerNames.length; i++) {
				if (fields[i].matches("\\w+")) {
					headerNames[i] = TextProviderHelper.getText(textPrefix + "." + fields[i], fields[i], stack);
					fields[i] = "%{" + fields[i] + "}";
				} else {
					headerNames[i] = fields[i];
				}
			}
		}
		addParameter("headerNames", headerNames);
		addParameter("widths", widths);
		final Object list = findValue(bundle);
		addParameter("value", list);
		addParameter("fields", fields);
		addParameter("id", RandomUtils.nextInt(100));
//		if (list instanceof PagedList) {
        //			final PagedList pagedList = (PagedList) list;
        //			addParameter("page", pagedList.getPage());
        //			int page = pagedList.getPage();
        //			int pagesize = pagedList.getPagesize();
        //			int total = pagedList.getTotal();
        //			int totalPage = -1;
        //			String requestURL;
        //			String queryString;
        //			String emptyMessage = "没有数据";
        //			if (totalPage == -1) {
        //				totalPage = (int) Math.ceil(total * 1.0 / pagesize);
        //			}
        //			addParameter("page", page);
        //
        //			addParameter("offset", pagedList.getOffset());
        //			addParameter("pagesize", pagesize);
        //			addParameter("total", total);
        //			addParameter("totalPage", totalPage);
        //
        //			requestURL = (request.getContextPath() != null ? request.getContextPath() : "")
        //					+ request.getAttribute("struts.request_uri") != null ? request.getAttribute("struts.request_uri")
        //					.toString() : request.getRequestURI();
        //
        //			StringBuffer buffer = new StringBuffer();
        //			buffer.append(requestURL);
        //			String qs = HttpRequestUtils.queryString(request, "utf-8");
        //			buffer.append("?");
        //			if (qs != null) {
        //				buffer.append(qs.replaceAll("&?page=\\d+", ""));
        //			}
        //			if (buffer.charAt(buffer.length() - 1) != '?') {
        //				buffer.append("&");
        //			}
        //			queryString = buffer.toString();
        //			addParameter("queryString", queryString);
        //			addParameter("emptyMessage", emptyMessage);
        //		}

        if (list instanceof PagedList) {
            final PagedList pagedList = (PagedList) list;
            addParameter("offset", pagedList.getOffset());
            int pagesize = pagedList.getPagesize();
            int total = pagedList.getTotal();
            int totalPage = -1;
            String requestURL;
            String queryString;
            String emptyMessage = "没有数据";
            if (totalPage == -1) {
                totalPage = (int) Math.ceil(total * 1.0 / pagesize);
            }
            addParameter("offset", offset);
            addParameter("page", (offset) / pagesize + 1);
            addParameter("limit", pagesize);
            addParameter("total", total);
            addParameter("totalPage", totalPage);

            requestURL = (request.getContextPath() != null ? request.getContextPath() : "")
                    + request.getAttribute("struts.request_uri") != null ? request.getAttribute("struts.request_uri")
                    .toString() : request.getRequestURI();

            StringBuffer buffer = new StringBuffer();
            buffer.append(requestURL);
            String qs = HttpRequestUtils.queryString(request, "utf-8");
            buffer.append("?");
            if (qs != null) {
                buffer.append(qs.replaceAll("&?offset=\\d+", ""));
            }
            if (buffer.charAt(buffer.length() - 1) != '?') {
                buffer.append("&");
            }
            queryString = buffer.toString();
            addParameter("queryString", queryString);
            addParameter("emptyMessage", emptyMessage);
        }

        addParameter("multiSelect", multiSelect);
		addParameter("checkboxName", checkboxName);
		addParameter("checkboxValue", checkboxValue);
		addParameter("showNo", showNo);
		addParameter("defaultColumns", defaultColumns);
		Map<String, String> da = (Map<String, String>) getParameters().get("dynamicAttributes");
		Set<String> keys = da.keySet();
		for (String key : keys) {
			if (ArrayUtils.contains(fields, key)) {
				extendValues.put(key, da.get(key));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {

		return "dataTable";
	}

	/**
	 * @return the headerNames
	 */
	public String[] getHeaderNames() {
		return headerNames;
	}

	/**
	 * @param headerNames the headerNames to set
	 */
	public void setHeaderNames(String[] headerNames) {
		this.headerNames = headerNames;
	}

	/**
	 * @return the fields
	 */
	public String[] getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}

	/**
	 * @return the widths
	 */
	public String[] getWidths() {
		return widths;
	}

	/**
	 * @param widths the widths to set
	 */
	public void setWidths(String[] widths) {
		this.widths = widths;
	}

	/**
	 * @return the bundle
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * @param bundle the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return the textPrefix
	 */
	public String getTextPrefix() {
		return textPrefix;
	}

	/**
	 * @param textPrefix the textPrefix to set
	 */
	public void setTextPrefix(String textPrefix) {
		this.textPrefix = textPrefix;
	}

	/**
	 * @return the multiSelect
	 */
	public boolean isMultiSelect() {
		return multiSelect;
	}

	/**
	 * @param multiSelect the multiSelect to set
	 */
	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	/**
	 * @return the showNo
	 */
	public boolean isShowNo() {
		return showNo;
	}

	/**
	 * @param showNo the showNo to set
	 */
	public void setShowNo(boolean showNo) {
		this.showNo = showNo;
	}

	/**
	 * @return the defaultColumns
	 */
	public List<String> getDefaultColumns() {
		return defaultColumns;
	}

	/**
	 * @param defaultColumns the defaultColumns to set
	 */
	public void setDefaultColumns(List<String> defaultColumns) {
		this.defaultColumns = defaultColumns;
	}

	/**
	 * @return the extendValues
	 */
	public Map<String, String> getExtendValues() {
		return extendValues;
	}

	/**
	 * @param extendValues the extendValues to set
	 */
	public void setExtendValues(Map<String, String> extendValues) {
		this.extendValues = extendValues;
	}

	/**
	 * @return the checkboxName
	 */
	public String getCheckboxName() {
		return checkboxName;
	}

	/**
	 * @param checkboxName the checkboxName to set
	 */
	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	/**
	 * @return the checkboxValue
	 */
	public String getCheckboxValue() {
		return checkboxValue;
	}

	/**
	 * @param checkboxValue the checkboxValue to set
	 */
	public void setCheckboxValue(String checkboxValue) {
		this.checkboxValue = checkboxValue;
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
