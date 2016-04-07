/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-17 上午11:07:56
 * @author tonny
 */
package works.tonny.apps.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class DatetimeTag extends CommonTag {
	private Date date;

	private String format = "yyyy年M月d日H时m分";// yyyy-MM-dd HH:mm:ss

	/**
	 * @see works.tonny.apps.web.CommonTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Calendar now = Calendar.getInstance();

		if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)
				&& calendar.get(Calendar.DATE) == now.get(Calendar.DATE)) {
			format = format.replaceAll("y+[^M]*M+[^d]*d+[^dH]*", "");
		} else if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)
				&& calendar.get(Calendar.MONTH) == now.get(Calendar.MONTH)) {
			format = format.replaceAll("y+[^M]*M+[^d]*", "");
		} else if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
			format = format.replaceAll("y+[^M]*", "");
		}

		try {
			pageContext.getOut().write(DateFormatUtils.format(date, format));
		} catch (IOException e) {
			log.error(e);
		}
		return super.doEndTag();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
}