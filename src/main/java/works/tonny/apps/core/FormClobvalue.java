package works.tonny.apps.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

// Generated 2012-7-17 9:34:17 by Hibernate Tools 3.4.0.CR1

/**
 * FormClobvalue generated by hbm2java
 * 
 * @author Tonny Liu
 */
@Entity
@Table(name = "fr_form_clobvalue")
public class FormClobvalue extends FormValue {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private String value;

	public FormClobvalue() {
	}

	public FormClobvalue(String id) {
		setId(id);
	}

	public FormClobvalue(FormElement formElement, String itemId, String value) {
		setFormElement(formElement);
		setItemId(itemId);
		setValue(value);
	}

	@Lob
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
