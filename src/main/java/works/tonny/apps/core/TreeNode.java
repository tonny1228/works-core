/**
 * 
 */
package works.tonny.apps.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author 祥栋
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TreeNode<T> implements Serializable {

	protected String id;

	protected String parentId;

	protected String idLayer;

	protected int orderNo;

	protected int depth;

	private T data;

	/**
	 * 
	 */
	public TreeNode() {
	}

	/**
	 * @param id
	 * @param catalog
	 */
	public TreeNode(String id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "assigned")
	@Column(length = 50)
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "parent_id", length = 50)
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the idLayer
	 */
	@Column(name = "id_layer", length = 2000)
	public String getIdLayer() {
		return idLayer;
	}

	/**
	 * @param idLayer the idLayer to set
	 */
	public void setIdLayer(String idLayer) {
		this.idLayer = idLayer;
	}

	/**
	 * @return the orderNo
	 */
	@Column(name = "order_no")
	public int getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return the data
	 */
	@Transient
	public T getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

}