/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-18 下午4:31:57
 * @author tonny
 */
package works.tonny.apps.support;

import java.lang.reflect.Method;

/**
 * <p>
 * Query对象字段的支持
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class QueryDesc {

	private String name;

	private String[] values;
	
	private String[] valueNames;

	private String type;

	private Method method;
	
	private String value;

	/**
	 * @param name
	 * @param values
	 * @param type
	 * @param method
	 */
	public QueryDesc(String name, String[] values, String type, Method method) {
		this.name = name;
		this.values = values;
		this.type = type;
		this.method = method;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String[] getValueNames() {
		return valueNames;
	}

	public void setValueNames(String[] valueNames) {
		this.valueNames = valueNames;
	}

}
