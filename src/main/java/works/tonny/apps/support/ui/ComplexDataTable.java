/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-19 下午1:27:27
 * @author tonny
 */
package works.tonny.apps.support.ui;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;
import org.llama.library.utils.HttpRequestUtils;
import org.llama.library.utils.PagedList;

import works.tonny.apps.Query;
import works.tonny.apps.support.QueryCriteria;
import works.tonny.apps.support.QueryDesc;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@StrutsTag(name = "complexDataTable", tldTagClass = "works.tonny.apps.support.ui.ComplexDataTableTag", description = "Render an HTML input field of type datetime", allowDynamicAttributes = true)
public class ComplexDataTable extends UIBean {
	private String[] headerNames;

	private String[] fields;

	private String[] widths;

	private String bundle;

	private int offset = 0;

	private int limit = 10;

	private boolean multiSelect;

	private boolean showRowNo;

	private List<String> defaultColumns;

	private String checkboxName;

	private String checkboxValue;

	private String id;

	private boolean showSelectButton;

	private boolean showOrderButton;

	private boolean showQueryButton;

	private boolean showColumnButton;

	private List buttons;

	private String formAction;

	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	/**
	 * @param stack
	 * @param request
	 * @param response
	 */
	public ComplexDataTable(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		addParameter("tableId", id);
		addParameter("headerNames", headerNames);
		addParameter("widths", widths);
		final Query query = (Query) findValue(bundle);
		List list = null;
		if (offset > -1 && limit > 0) {
			list = query.listRange(offset, limit);
			addParameter("value", list);
		} else {
			list = query.list();
		}
		addParameter("bundle", bundle);
		addParameter("fields", fields);
		addParameter("id", RandomUtils.nextInt(100));
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
		addParameter("showRowNo", showRowNo);
		addParameter("defaultColumns", defaultColumns);
		addParameter("showSelectButton", showSelectButton);
		addParameter("showOrderButton", showOrderButton);
		addParameter("showQueryButton", showQueryButton);
		addParameter("showColumnButton", showColumnButton);
		addParameter("formAction", formAction);

		if (buttons != null)
			addParameter("buttons", buttons);

		if (showOrderButton) {
			List<QueryDesc> desc = (List<QueryDesc>) stack.findValue("descQueryOrder(" + bundle + ")");
			for (QueryDesc queryDesc : desc) {
				queryDesc.setType(StringUtils.substringAfterLast(queryDesc.getName(), "."));
				String actualName = findString(queryDesc.getName(), "name",
						"You must specify the i18n key. Example: welcome.header");
				queryDesc.setName(TextProviderHelper.getText(actualName, queryDesc.getName(), Collections.EMPTY_LIST,
						getStack(), false));
			}
			addParameter("orderDesc", desc);
			final String parameter = request.getParameter("_order");
			final String orderParam = request.getParameter("_orderparam");
			if (parameter != null) {
				addParameter("ordervalue", parameter);
			}
			if (orderParam != null) {
				addParameter("orderparam", orderParam);
			}
		}

		if (showQueryButton) {
			List<QueryDesc> qs = (List<QueryDesc>) stack.findValue("descQueryCriteria(" + bundle + ")");
			for (QueryDesc queryDesc : qs) {
				String actualName = findString(queryDesc.getName(), "name",
						"You must specify the i18n key. Example: welcome.header");
				final String parameter = request.getParameter(StringUtils.substringAfterLast(queryDesc.getName(), "."));
				queryDesc.setValue(StringUtils.defaultIfEmpty(parameter, ""));
				queryDesc.setName(TextProviderHelper.getText(actualName, queryDesc.getName(), Collections.EMPTY_LIST,
						getStack(), false));
				if (QueryCriteria.TYPE_BOOLEAN.equals(queryDesc.getType())) {
					queryDesc.setValues(new String[] { "" });
				}
			}
			addParameter("queryDesc", qs);
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return "complexDataTable";
	}

	/**
	 * @return the headerNames
	 */
	public String[] getHeaderNames() {
		return headerNames;
	}

	/**
	 * @param headerNames
	 *            the headerNames to set
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
	 * @param fields
	 *            the fields to set
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
	 * @param widths
	 *            the widths to set
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
	 * @param bundle
	 *            the bundle to set
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @return the multiSelect
	 */
	public boolean isMultiSelect() {
		return multiSelect;
	}

	/**
	 * @param multiSelect
	 *            the multiSelect to set
	 */
	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	/**
	 * @return the showRowNo
	 */
	public boolean isShowRowNo() {
		return showRowNo;
	}

	/**
	 * @param showRowNo
	 *            the showRowNo to set
	 */
	public void setShowRowNo(boolean showRowNo) {
		this.showRowNo = showRowNo;
	}

	/**
	 * @return the defaultColumns
	 */
	public List<String> getDefaultColumns() {
		return defaultColumns;
	}

	/**
	 * @param defaultColumns
	 *            the defaultColumns to set
	 */
	public void setDefaultColumns(List<String> defaultColumns) {
		this.defaultColumns = defaultColumns;
	}

	/**
	 * @return the checkboxName
	 */
	public String getCheckboxName() {
		return checkboxName;
	}

	/**
	 * @param checkboxName
	 *            the checkboxName to set
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
	 * @param checkboxValue
	 *            the checkboxValue to set
	 */
	public void setCheckboxValue(String checkboxValue) {
		this.checkboxValue = checkboxValue;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the showSelectButton
	 */
	public boolean isShowSelectButton() {
		return showSelectButton;
	}

	/**
	 * @param showSelectButton
	 *            the showSelectButton to set
	 */
	public void setShowSelectButton(boolean showSelectButton) {
		this.showSelectButton = showSelectButton;
	}

	/**
	 * @return the showOrderButton
	 */
	public boolean isShowOrderButton() {
		return showOrderButton;
	}

	/**
	 * @param showOrderButton
	 *            the showOrderButton to set
	 */
	public void setShowOrderButton(boolean showOrderButton) {
		this.showOrderButton = showOrderButton;
	}

	/**
	 * @return the showQueryButton
	 */
	public boolean isShowQueryButton() {
		return showQueryButton;
	}

	/**
	 * @param showQueryButton
	 *            the showQueryButton to set
	 */
	public void setShowQueryButton(boolean showQueryButton) {
		this.showQueryButton = showQueryButton;
	}

	/**
	 * @return the showColumnButton
	 */
	public boolean isShowColumnButton() {
		return showColumnButton;
	}

	/**
	 * @param showColumnButton
	 *            the showColumnButton to set
	 */
	public void setShowColumnButton(boolean showColumnButton) {
		this.showColumnButton = showColumnButton;
	}

	/**
	 * @return the buttons
	 */
	public List getButtons() {
		return buttons;
	}

	/**
	 * @param buttons
	 *            the buttons to set
	 */
	public void setButtons(List buttons) {
		this.buttons = buttons;
	}

}
