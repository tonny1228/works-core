/**
 * 
 */
package works.tonny.apps.user.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import works.tonny.apps.Entity;
import works.tonny.apps.user.util.LayerUtil;

/**
 * @author 祥栋
 * @date 2012-12-7
 * @version 1.0.0
 */
@javax.persistence.Entity
@Table(name = "u_business_unit")
public class BusinessUnit extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5916509914763287810L;

	protected Department dept;

	protected Department parent;

	protected String idLayer;

	protected String type;

	/**
	 * 
	 */
	public BusinessUnit() {
	}

	/**
	 * @param dept2
	 * @param bu
	 * @param type2
	 */
	public BusinessUnit(Department dept, BusinessUnit parent, String type) {
		this.dept = dept;
		this.type = type;
		this.parent = parent.getDept();
		this.idLayer = LayerUtil.createLayer(parent.getIdLayer(), this.parent.getId());
	}

	/**
	 * @param dept2
	 * @param p
	 * @param type2
	 */
	public BusinessUnit(Department dept, Department p, String type) {
		this.dept = dept;
		this.type = type;
		this.parent = p;
		if (p != null) {
			this.idLayer = LayerUtil.createLayer(null, p.getId());
		} else {
			this.idLayer = LayerUtil.createLayer(null, StringUtils.EMPTY);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see works.tonny.apps.Entity#getId()
	 */
	@Override
	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(name = "id", length = 50)
	public String getId() {
		return super.getId();
	}

	@ManyToOne
	@JoinColumn(name = "dept_id", nullable = false)
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	@Column(name = "ID_LAYER", length = 300)
	public String getIdLayer() {
		return idLayer;
	}

	public void setIdLayer(String idLayer) {
		this.idLayer = idLayer;
	}

	@Column(length = 2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
