package works.tonny.apps.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.llama.library.utils.CalendarUtil;
import org.llama.library.utils.LunarCalendar;
import org.llama.library.xhtml.Document;
import org.llama.library.xhtml.Element;
import org.llama.library.xhtml.impl.Table;
import org.llama.library.xhtml.impl.TableCell;
import org.llama.library.xhtml.impl.TableRow;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import freemarker.template.utility.DateUtil;
import freemarker.template.utility.StringUtil;

/**
 * 生成日历
 * 
 * @author tonny
 * 
 */
public class Calendar {
	/**
	 * 节日信息
	 */
	static Map<String, String> holiday = new Hashtable<String, String>();

	/**
	 * 星期中文
	 */
	private static final String[] DATE = { "日", "一", "二", "三", "四", "五", "六" };

	/**
	 * 星期英文
	 */
	private static final String[] DATE_E = { "Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sta" };

	/**
	 * 年
	 */
	private int year;

	/**
	 * 月
	 */
	private int month;
	/**
	 * html table
	 */
	private Table table = new Table();

	private Table dt = new Table();

	/**
	 * 日历工具
	 */
	private CalendarUtil cal;
	/**
	 * 农历
	 */
	private LunarCalendar lc = new LunarCalendar();

	/**
	 * 操作
	 */
	private HashMap<String, String> operation = new HashMap<String, String>();

	static {
		try {
			initHoliday();
		} catch (Exception e) {
		}
	}

	public Calendar() {
		cal = new CalendarUtil();
		year = cal.getYear();
		month = cal.getMonthInt();
	}

	public Calendar(int year, int month) {
		this.year = year;
		this.month = month;
		cal = new CalendarUtil(year, month, 1);
	}

	public void addOperation(String event, String oper) {
		operation.put(event, oper);
	}

	/**
	 * 生存头部导航年度与月份 生存年度和月份下拉列表，前月、下月的导航
	 * 
	 * @return
	 */
	private void makeTitle() {
		Table h = new Table();
		TableRow hr = table.addRow();
		hr.addCol().appendChild(h);
		hr.setClassName("cal_head1");
		TableRow head = h.addRow();

		// Element year = Document.createElement("div");
		// Element month = Document.createElement("div");
		// year.setId("_year");
		// month.setId("_month");
		// for (int i = 1949; i < 2051; i++)// 年度导航
		// {
		// Element y = Document.createElement("div");
		// y.appendChild(Document.createTextNode(String.valueOf(i)));
		// if (cal.getYear() == i)
		// y.setClassName("cal_thisyear");
		// y
		// .addAttribute(
		// "onclick",
		// "_goto(this.innerText,document.getElementById('now').childNodes[2].data.substring(0,document.getElementById('now').childNodes[2].data.indexOf('月')))");
		// year.appendChild(y);
		// }
		// for (int i = 1; i < 13; i++) {// 月度导航
		// Element m = Document.createElement("div");
		// m.appendChild(Document.createTextNode(String.valueOf(i)));
		// if (cal.getMonthInt() == i)
		// m.setClassName("cal_thismonth");
		// m.addAttribute("onclick",
		// "_goto(document.getElementById('now').firstChild.data.substring(0,4),this.innerText)");
		// month.appendChild(m);
		// }

		table.setClassName("calendar");
		table.setId("_calendar");

		h.setId("YearMonth");

		CalendarUtil noc = new CalendarUtil();
		h.addAttribute("ondblclick", "_goto(" + noc.getYear() + "," + noc.getMonthInt() + ")");

		TableCell prevYear = head.addCol();
		TableCell prev = head.addCol();
		TableCell now = head.addCol();
		TableCell next = head.addCol();
		TableCell nextYear = head.addCol();

		prevYear.setId("prevYear");
		prevYear.appendChild(Document.createTextNode(""));
		prevYear.addAttribute("onclick", "_goto(" + (cal.getYear() - 1) + "," + cal.getMonthInt() + ")");

		nextYear.setId("nextYear");
		nextYear.appendChild(Document.createTextNode(""));
		nextYear.addAttribute("onclick", "_goto(" + (cal.getYear() + 1) + "," + cal.getMonthInt() + ")");

		now.setId("now");
		now.appendChild(Document.createTextNode(String.valueOf(cal.getYear()) + "年"));
		// Element sy = Document.createElement("span");
		// sy.setId("_select_year");
		// sy.setInnerHtml("&nbsp;");
		// sy.addAttribute("onclick", "_showYear(this)");
		//
		// now.appendChild(sy);
		now.appendChild(Document.createTextNode(String.valueOf(cal.getMonthInt()) + "月"));
		// Element sm = Document.createElement("span");
		// sm.setId("_select_month");
		// sm.setInnerHtml("&nbsp;");
		// sm.addAttribute("onclick", "_showMonth(this)");
		//
		// now.appendChild(sm);
		// now.appendChild(year);
		// now.appendChild(month);
		now.addAttribute("onclick", "_selectDate(this)");
		cal.addMonth(-1);
		prev.setId("prevMonth");
		prev.appendChild(Document.createTextNode(""));
		prev.addAttribute("onclick", "_goto(" + cal.getYear() + "," + cal.getMonthInt() + ")");

		next.setId("nextMonth");
		next.appendChild(Document.createTextNode(""));
		cal.addMonth(1);
		cal.addMonth(1);
		next.addAttribute("onclick", "_goto(" + cal.getYear() + "," + cal.getMonthInt() + ")");
		cal.addMonth(-1);

	}

	/**
	 * 生成月历的星期导航
	 * 
	 */
	private void makeTop() {
		TableRow hr = table.addRow();
		hr.addCol().appendChild(dt);
		hr.setClassName("cal_head2");
		dt.setId("day");
		TableRow week = dt.addRow();

		week.setId("Weekday");
		for (int i = 0; i < DATE.length; i++) {
			TableCell weekDay = week.addCol();
			weekDay.setClassName("cal_dayname");
			weekDay.appendChild(Document.createTextNode(DATE[i]));
			weekDay.setId(DATE_E[i]);
		}
	}

	private TableRow makeDayContainer(String id) {
		TableRow week = dt.addRow();
		week.setId(id);
		week.setClassName("days_container");
		return week;
	}

	private TableCell makeDay(TableRow daysDiv, String id, int y, int m, int d, boolean preMonth, boolean today,
			boolean nextMonth, boolean weekend) {
		TableCell dayDiv = daysDiv.addCol();
		dayDiv.setId(id);
		dayDiv.setClassName("cal_day" + (preMonth ? " cal_prveMonth" : "") + (today ? " cal_today" : "")
				+ (nextMonth ? " cal_nextmonth" : "") + (weekend ? " cal_weekend" : ""));
		makeTableColumn(dayDiv, y, m, d);
		return dayDiv;
	}

	/**
	 * 生存日期的布局及农历
	 * 
	 */
	private void makeDays() {
		TableRow daysDiv = makeDayContainer("days1");
		cal.reset(year, month, 1);
		int days = cal.getDaysInMonth();
		int left = cal.getDayOfWeek() - 1;
		int lines = 0;
		int d = 0;
		if (left > 0) {
			cal.firstDayOfWeek();
			for (int i = 0; i < left; i++) {
				d++;
				makeDay(daysDiv, "day" + d, cal.getYear(), cal.getMonthInt(), cal.getDayOfMonth(), true, false, false,
						i == 0);
				cal.addDays(1);
			}
		} else {
			cal.addDays(-7 + cal.getDayOfWeek());
			for (int i = 0; i < 7; i++) {
				d++;
				makeDay(daysDiv, "day" + d, cal.getYear(), cal.getMonthInt(), cal.getDayOfMonth(), true, false, false,
						i == 0 || i == 6);
				cal.addDays(1);
			}
			lines++;
			daysDiv = makeDayContainer("days2");
		}

		cal.set(new Date());

		for (int i = 1; i <= days; i++) {
			d++;
			makeDay(daysDiv, "day" + d, year, month, i, false, year == cal.getYear() && month == cal.getMonthInt()
					&& i == cal.getDayOfMonth(), false, (i + left) % 7 < 2);
			if ((left + i) % 7 == 0 && (i < days)) {
				lines++;
				daysDiv = makeDayContainer("days" + (lines + 1));
			}
		}

		cal.reset(year, month, 1);
		int right = 7 - (left + days) % 7;
		right = right == 7 ? 0 : right;
		cal.addMonth(1);
		if (right > 0) {
			for (int i = 1; i <= right; i++) {
				d++;
				makeDay(daysDiv, "day" + d, cal.getYear(), cal.getMonthInt(), i, false, false, true,
						(i + (left + days)) % 7 < 2);
				cal.addDays(1);
			}
			lines++;
		}
		if (lines < 6) {
			daysDiv = makeDayContainer("days6");
			for (int i = 1; i <= 7; i++) {
				d++;
				makeDay(daysDiv, "day" + d, cal.getYear(), cal.getMonthInt(), cal.getDayOfMonth(), false, false, true,
						i == 1 || i == 7);
				cal.addDays(1);
			}
		}
	}

	private void makeTableColumn(TableCell div, int year, int month, int day) {
		lc.setTime(year + "-" + month + "-" + day);

		Element yl = Document.createElement("div");
		yl.setId(year + "-" + StringUtils.leftPad(String.valueOf(month), 2, '0') + "-"
				+ StringUtils.leftPad(String.valueOf(day), 2, '0'));
		yl.setClassName("cal_day_div");
		yl.appendChild(Document.createTextNode(Integer.toString(day)));
		Set o = operation.keySet();
		for (Iterator<String> itr = o.iterator(); itr.hasNext();) {
			String oper = itr.next();
			yl.addAttribute(oper, format(operation.get(oper), yl.getId()));
			yl.setClassName("cal_click");
		}
		div.appendChild(yl);
		Element nl = Document.createElement("div");
		nl.setId("$" + lc.getYear() + "-" + lc.getMonth() + "-" + lc.getDay());
		if (holiday.containsKey("n" + lc.getMonth() + "-" + lc.getDay())) {
			nl.appendChild(Document.createTextNode(holiday.get("n" + lc.getMonth() + "-" + lc.getDay()).toString()));
			nl.setClassName("ncal_month1");
		} else if (holiday.containsKey("" + month + "-" + day)) {
			nl.appendChild(Document.createTextNode(holiday.get("" + month + "-" + day).toString()));
			nl.setClassName("ncal_month1");
		} else if (lc.getDayName().equals("初一")) {
			nl.appendChild(Document.createTextNode(lc.getMonthName()));
			nl.setClassName("ncal_month1");
		} else {
			nl.appendChild(Document.createTextNode(lc.getDayName()));
			nl.setClassName("ncal_month");
		}
		div.appendChild(nl);
	}

	/**
	 * 生存月历
	 * 
	 * @return
	 */
	public Element makeCalendar() {
		table.addAttribute("ondblclick", "_cal_click()");
		this.makeTitle();
		this.makeTop();
		this.makeDays();
		return table;
	}

	public static void main(String[] argv) {
		Calendar cal = new Calendar(2009, 3);
	}

	/**
	 * 设置链接或事件的日期返回类型
	 * 
	 * @param url
	 * @return
	 */
	private String format(String url, String date) {
		if (url == null)
			return "";
		int posb = url.indexOf("#date(");
		if (posb < 0)
			return url;
		String temp = url.substring(posb);
		int pose = temp.indexOf(")");
		if (pose < 0)
			return url;

		String format = url.substring(posb + 6, posb + pose);
		url = url.substring(0, posb + 5) + url.substring(posb + pose + 1);
		Date d = null;
		try {
			d = DateUtils.parseDate(date, new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date = DateFormatUtils.format(d, format);
		return url.replaceAll("#date", date);

	}

	private static void initHoliday() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		org.w3c.dom.Document doc = db.parse(new File(Calendar.class.getResource("/holiday.xml").getPath()));
		NodeList datelist = doc.getElementsByTagName("date");
		for (int i = 0; i < datelist.getLength(); i++) {
			holiday.put(datelist.item(i).getAttributes().getNamedItem("date").getNodeValue().toString(),
					datelist.item(i).getFirstChild().getNodeValue().toString());
		}
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

}
