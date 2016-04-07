package works.tonny.apps.userevent.web;

import works.tonny.apps.support.CommonAction;
import works.tonny.apps.userevent.ReadingCountService;

/**
 * 为异步加数器提供计数功能
 * 
 * @author tonny
 *
 */
public class ReadingCountAction extends CommonAction {

	private ReadingCountService readingCountService;
	private String main;
	private String sub;
	private String dataId;
	private int count;

	public String read() {
		count = readingCountService.read(main, sub, dataId);
		return SUCCESS;
	}

	public ReadingCountService getReadingCountService() {
		return readingCountService;
	}

	public void setReadingCountService(ReadingCountService readingCountService) {
		this.readingCountService = readingCountService;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}