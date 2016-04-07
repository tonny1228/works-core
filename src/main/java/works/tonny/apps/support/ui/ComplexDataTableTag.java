/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-19 下午1:26:50
 * @author tonny
 */
package works.tonny.apps.support.ui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class ComplexDataTableTag extends AbstractUITag {
	private String id;

	private String headerNames;

	private String fields;

	private String widths;

	private String bundle;

	private boolean multiSelect;

	private boolean showRowNo;

	private String defaultColumns;

	private String checkboxName;

	private String checkboxValue;

	private int limit;

	private int offset;

	private boolean showSelectButton;

	private boolean showOrderButton;

	private boolean showQueryButton;

	private boolean showColumnButton;

	private String buttons;

	public String getFormAction() {
		return formAction;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	private String formAction;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.views.jsp.ComponentTagSupport#getBean(com.opensymphony.xwork2.util.ValueStack,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new ComplexDataTable(stack, request, response);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.views.jsp.ui.AbstractUITag#populateParams()
	 */
	@Override
	protected void populateParams() {
		super.populateParams();
		ComplexDataTable table = (ComplexDataTable) component;
		if (StringUtils.isNotEmpty(headerNames)) {
			table.setHeaderNames(headerNames.split("\\|"));
		}
		if (StringUtils.isNotEmpty(defaultColumns)) {
			final String[] split = defaultColumns.split("\\|");
			List<String> defaults = new ArrayList<String>();
			for (int i = 0; i < split.length; i++) {
				defaults.add(split[i]);
			}
			table.setDefaultColumns(defaults);
		}
		if (StringUtils.isNotEmpty(fields)) {
			fields = StringUtils.replace(fields, "\\|", "__or_");
			String[] split = fields.split("\\|");
			for (int i = 0; i < split.length; i++) {
				split[i] = StringUtils.replace(split[i], "__or_", "|");
			}
			table.setFields(split);
		}
		if (StringUtils.isNotEmpty(widths)) {
			String[] w = widths.split("\\|");
			String[] width = new String[table.getHeaderNames().length];
			for (int i = 0; i < width.length; i++) {
				width[i] = w[i];
			}
			table.setWidths(width);
		}

		if (StringUtils.isNotEmpty(bundle)) {
			table.setBundle(bundle);
		}
		table.setShowRowNo(showRowNo);
		table.setMultiSelect(multiSelect);
		table.setCheckboxName(checkboxName);
		table.setCheckboxValue(checkboxValue);
		table.setId(id);
		table.setOffset(offset);
		table.setLimit(limit);
		table.setShowColumnButton(showColumnButton);
		table.setShowOrderButton(showOrderButton);
		table.setShowQueryButton(showQueryButton);
		table.setShowSelectButton(showSelectButton);
		table.setFormAction(formAction);
		if (StringUtils.isNotEmpty(buttons)) {
			final Object findValue = findValue(buttons);
			table.setButtons((List) findValue);
		}
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
	 * @return the headerNames
	 */
	public String getHeaderNames() {
		return headerNames;
	}

	/**
	 * @param headerNames
	 *            the headerNames to set
	 */
	public void setHeaderNames(String headerNames) {
		this.headerNames = headerNames;
	}

	/**
	 * @return the fields
	 */
	public String getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	/**
	 * @return the widths
	 */
	public String getWidths() {
		return widths;
	}

	/**
	 * @param widths
	 *            the widths to set
	 */
	public void setWidths(String widths) {
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
	public String getDefaultColumns() {
		return defaultColumns;
	}

	/**
	 * @param defaultColumns
	 *            the defaultColumns to set
	 */
	public void setDefaultColumns(String defaultColumns) {
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
	public String getButtons() {
		return buttons;
	}

	/**
	 * @param buttons
	 *            the buttons to set
	 */
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

}
