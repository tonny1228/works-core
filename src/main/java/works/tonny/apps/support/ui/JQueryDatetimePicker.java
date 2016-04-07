/**
 * 
 */
package works.tonny.apps.support.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 时间日期选择器
 * 
 * @author tonny
 * @date 2012-8-17
 * @version 1.0.0
 */
@StrutsTag(name = "datetimePicker", tldTagClass = "works.tonny.apps.support.JQueryDatetimePickerTag", description = "Render an HTML input field of type datetime", allowDynamicAttributes = true)
public class JQueryDatetimePicker extends UIBean {
	protected String type;
	protected String dateFormat;
	protected String timeFormat;
	protected String defaultDate;
	protected String yearRange;
	protected String minDate;
	protected String maxDate;
	protected String buttonImage;
	protected String readonly;

	/**
	 * @param stack
	 * @param request
	 * @param response
	 */
	public JQueryDatetimePicker(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	@Override
	protected void evaluateExtraParams() {
		super.evaluateExtraParams();
		if (type != null)
			addParameter("type", findString(type));
		else
			addParameter("type", "date");
		if (buttonImage != null)
			addParameter("buttonImage", findString(buttonImage));
		if (dateFormat != null)
			addParameter("dateFormat", findString(dateFormat));
		else
			addParameter("dateFormat", "yy-mm-dd");
		if (dateFormat != null)
			addParameter("timeFormat", findString(timeFormat));
		else
			addParameter("timeFormat", "hh:mm:ss");

		if (defaultDate != null) {
			addParameter("defaultDate", findString(defaultDate));
		}
		if (minDate != null) {
			addParameter("minDate", findString(minDate));
		}
		if (maxDate != null) {
			addParameter("maxDate", findString(maxDate));
		}
		if (readonly != null) {
			addParameter("readonly", "true".equalsIgnoreCase(findString(readonly)));
		}
		if (yearRange != null) {
			addParameter("yearRange", findString(yearRange));
		} else {

		}

	}

	/*
	 * 
	 * 
	 * @see org.apache.struts2.components.UIBean#getDefaultTemplate()
	 */
	@Override
	protected String getDefaultTemplate() {
		return "datetimePicker";
	}

	@StrutsTagAttribute(description = "date|datetime|time", type = "String")
	public void setType(String type) {
		this.type = type;
	}

	@StrutsTagAttribute(description = "date js format", type = "String")
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@StrutsTagAttribute(description = "time js format", type = "String")
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public String getButtonImage() {
		return buttonImage;
	}

	@StrutsTagAttribute(description = "image url", type = "String")
	public void setButtonImage(String buttonImage) {
		this.buttonImage = buttonImage;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	/**
	 * @param readonly the readonly to set
	 */
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return the yearRange
	 */
	public String getYearRange() {
		return yearRange;
	}

	/**
	 * @param yearRange the yearRange to set
	 */
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

}
