/**
 * 
 */
package works.tonny.apps.core;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;

/**
 * @author 祥栋
 */
@javax.persistence.Entity
@Table(name = "s_id_sequence")
public class IDSequence extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 下一个值
	 */
	@Column(name = "next_val")
	private long nextVal;

	/**
	 * 一次越过的值，即更新数据库的周期，值越大，越少更新数据库
	 */
	private int step = 10;

	/**
	 * 
	 */
	public IDSequence() {
	}

	/**
	 * @param nextVal
	 * @param step
	 */
	public IDSequence(String id, long nextVal, int step) {
		this.id = id;
		this.nextVal = nextVal;
		this.step = step;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@Column(length = 50)
	public String getId() {
		return super.getId();
	}

	/**
	 * @return the nextVal
	 */
	@Column(name = "next_val")
	public long getNextVal() {
		return nextVal;
	}

	/**
	 * @param nextVal the nextVal to set
	 */
	public void setNextVal(long nextVal) {
		this.nextVal = nextVal;
	}

	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}

}
