package works.tonny.apps;

/**
 * 信息状态
 * 
 * @author tonny
 * 
 */
public class Status<T extends Comparable<T>> implements Comparable<Status<T>> {
	/**
	 * 状态值
	 */
	protected T value;

	/**
	 * 状态名称
	 */
	protected String name;

	public Status(T v, String name) {
		this.value = v;
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * 两个对象的值相等则两个对象相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Status) || this.value == null) {
			return false;
		}
		return this.value.equals(((Status) obj).getValue());
	}

	/**
	 * 比较两个状态的值
	 */
	public int compareTo(Status<T> o) {
		return this.getValue().compareTo(o.getValue());
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}

}
