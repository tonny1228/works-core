/**
 * 
 */
package works.tonny.apps.support.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author tonny
 * @date 2012-8-17
 * @version 1.0.0
 */
public class JQueryDatetimePickerTag extends AbstractUITag {
	private static final long serialVersionUID = 1L;
	protected String type;
	protected String dateFormat;
	protected String timeFormat;
	protected String buttonImage;
	protected String yearRange;
	protected String defaultDate;
	protected String minDate;
	protected String maxDate;
	protected String readonly;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new JQueryDatetimePicker(stack, request, response);
	}

	protected void populateParams() {
		super.populateParams();
		JQueryDatetimePicker picker = (JQueryDatetimePicker) component;
		picker.setType(type);
		picker.setDateFormat(dateFormat);
		picker.setTimeFormat(timeFormat);
		picker.setButtonImage(buttonImage);
		picker.setDefaultDate(defaultDate);
		picker.setMinDate(minDate);
		picker.setMaxDate(maxDate);
		picker.setReadonly(readonly);
		picker.setYearRange(yearRange);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public void setButtonImage(String buttonImage) {
		this.buttonImage = buttonImage;
	}

	public String getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
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
	 * @param yearRange the yearRange to set
	 */
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

}
