package works.tonny.apps;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.beanutils.PropertyUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

import works.tonny.apps.deploy.SchemaAware;

public abstract class AbstractService {
	/**
	 * 日志
	 */
	protected final Logger log = LogFactory.getLogger(getClass());

	protected String schema;

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (!Modifier.isPrivate(field.getModifiers())
						&& SchemaAware.class.isAssignableFrom((Class) field.getGenericType())) {
					((SchemaAware) field.get(this)).setSchema(schema);
				} else if (!Modifier.isFinal(field.getModifiers())) {
					Object property = PropertyUtils.getProperty(this, field.getName());
					if (property != null && property instanceof SchemaAware) {
						((SchemaAware) property).setSchema(schema);
					}
				}
			} catch (Exception e) {
				log.warn("设置schema信息出错，请设置dao为proteced或设置get方法", e);
			}
		}
	}
}
