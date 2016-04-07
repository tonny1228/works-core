package works.tonny.apps.user.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Embeddable
@Table(name = "a_department_privilege")
public class DepartmentPrivilege extends EntityPrivilege {
	private Department department;

	/**
	 * @see works.tonny.apps.user.model.EntityPrivilege#getEntity()
	 */
	@Override
	@Transient
	public Object getEntity() {
		return department;
	}

	/**
	 * @return the department
	 */
	@ManyToOne()
	@JoinColumn(name = "dept_id", nullable = false)
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

}
