/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-28 下午2:52:25
 * @author tonny
 */
package works.tonny.apps.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import works.tonny.apps.auth.DataOwner;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
@Entity
@Table(name = "s_catalog_owner")
public class CatalogDataOwner extends DataOwner<Catalog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "data_id")
	public Catalog getData() {
		return super.getData();
	}

}
