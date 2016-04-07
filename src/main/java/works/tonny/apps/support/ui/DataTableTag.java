/**
 * 
 */
package works.tonny.apps.support.ui;

import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 祥栋
 */
public class DataTableTag extends AbstractUITag {
	private String headerNames;

	private String fields;

	private String widths;

	private String bundle;

	private String textPrefix;

	private boolean multiSelect;

	private boolean showNo;

	private String defaultColumns;

	private String checkboxName;

	private String checkboxValue;

    private int limit;

    private int offset;



    /**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.views.jsp.ComponentTagSupport#getBean(com.opensymphony.xwork2.util.ValueStack,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new DataTable(stack, request, response);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts2.views.jsp.ui.AbstractUITag#populateParams()
	 */
	@Override
	protected void populateParams() {
		super.populateParams();
		DataTable table = (DataTable) component;
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
			table.setFields(fields.split("\\|"));
		}
		if (StringUtils.isNotEmpty(textPrefix)) {
			table.setTextPrefix(textPrefix);
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
		table.setShowNo(showNo);
        table.setOffset(offset);
        table.setLimit(limit);
		table.setMultiSelect(multiSelect);
		table.setCheckboxName(checkboxName);
		table.setCheckboxValue(checkboxValue);
	}

	/**
	 * @return the headerNames
	 */
	public String getHeaderNames() {
		return headerNames;
	}

	/**
	 * @param headerNames the headerNames to set
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
	 * @param fields the fields to set
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
	 * @param widths the widths to set
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
	public String getDefaultColumns() {
		return defaultColumns;
	}

	/**
	 * @param defaultColumns the defaultColumns to set
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
