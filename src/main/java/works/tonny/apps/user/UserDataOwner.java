package works.tonny.apps.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import works.tonny.apps.auth.DataOwner;

@Entity
@Table(name = "u_user_owner")
public class UserDataOwner extends DataOwner<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "data_id")
	public User getData() {
		return super.getData();
	}

}