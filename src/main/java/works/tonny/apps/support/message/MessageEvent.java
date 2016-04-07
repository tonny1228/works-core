/**
 * 
 */
package works.tonny.apps.support.message;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 广播的消息
 * 
 * @author 祥栋
 */
public class MessageEvent<T> implements Serializable {
	//
	// public static final int STATUS_ADD = 1;
	//
	// public static final int STATUS_UPDATE = 2;
	//
	// public static final int STATUS_DELETE = -1;
	//
	// public static final int STATUS_ENABLE = 20;
	//
	// public static final int STATUS_DISABLE = 10;

	public static final Status BEFORE_CREATE = new Status("BEFORE_CREATE");
	public static final Status CREATED = new Status("CREATED");
	public static final Status BEFORE_UPDATE = new Status("BEFORE_UPDATE");
	public static final Status UPDATED = new Status("UPDATED");
	public static final Status BEFORE_DELETE = new Status("BEFORE_DELETE");
	public static final Status DELETED = new Status("DELETED");
	public static final Status ENABLED = new Status("ENABLED");
	public static final Status DISABLED = new Status("DISABLED");
	public static final Status BEFORE_MOVE = new Status("BEFORE_MOVE");
	public static final Status MOVED = new Status("MOVED");

	private Status status;

	private T data;

	private String category;

	/**
	 * @param status
	 * @param data
	 * @param category
	 */
	public MessageEvent(String category, Status status, T data) {
		super();
		this.status = status;
		this.data = data;
		this.category = category;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new ToStringBuilder(this).append(category).append(status).append(data).build();
	}

	public static class Status {
		String name;

		/**
		 * @param name
		 */
		public Status(String name) {
			super();
			this.name = name;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Status[" + name + "]";
		}
	}
}
