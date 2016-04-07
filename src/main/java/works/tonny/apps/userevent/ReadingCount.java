package works.tonny.apps.userevent;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * 计数器
 * 
 * @author tonny
 *
 */
@javax.persistence.Entity
@Table(name = "ue_reading_count")
public class ReadingCount extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主功能
	 */
	private String mainFunction;

	/**
	 * 子功能
	 */
	private String subFunction;

	/**
	 * 关联数据编号
	 */
	private String dataId;

	/**
	 * 访问数
	 */
	private int num = 1;

	public ReadingCount() {
	}

	public ReadingCount(String mainFunction, String subFunction, String dataId) {
		super();
		this.mainFunction = mainFunction;
		this.subFunction = subFunction;
		this.dataId = dataId;
	}

	@Override
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	@Column(name = "main_function", length = 50)
	public String getMainFunction() {
		return mainFunction;
	}

	public void setMainFunction(String mainFunction) {
		this.mainFunction = mainFunction;
	}

	@Column(name = "sub_function", length = 50)
	public String getSubFunction() {
		return subFunction;
	}

	public void setSubFunction(String subFunction) {
		this.subFunction = subFunction;
	}

	@Column(name = "data_id", length = 50)
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}