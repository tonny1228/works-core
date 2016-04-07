package works.tonny.apps.support;

import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.llama.library.utils.Formatter;

public class ActionUtils {
	private EntityLazyProxy entityLazyProxy;

	/**
	 * 将字节转换为合适的显示
	 * 
	 * @param bytes
	 * @return
	 */
	public String formatByte(long bytes) {
		return Formatter.formatByte(bytes);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public String formatDate(Date date, String pattern) {
		return Formatter.formatDate(date, pattern);
	}

	/**
	 * 过滤xml
	 * 
	 * @param str
	 * @return
	 */
	public String escapeXML(String str) {
		return StringEscapeUtils.escapeXml(str);
	}

	/**
	 * 过滤html
	 * 
	 * @param str
	 * @return
	 */
	public String escapeHTML(String str) {
		return StringEscapeUtils.escapeHtml4(str);
	}

	/**
	 * 过滤javascript
	 * 
	 * @param str
	 * @return
	 */
	public String escapeJavaScript(String str) {
		return StringEscapeUtils.escapeEcmaScript(str);
	}

	/**
	 * 代理输出延迟加载对象
	 * 
	 * @param object
	 * @param property
	 * @return
	 */
	public Object lazyProxy(Object object, String property) {
		StringTokenizer tokenizer = new StringTokenizer(property, ".");
		while (tokenizer.hasMoreElements()) {
			String prop = tokenizer.nextToken();
			try {
				object = PropertyUtils.getProperty(object, prop);
			} catch (Exception e) {
				return null;
			}
			getEntityLazyProxy().refresh(object);
		}
		return object;
	}

	public EntityLazyProxy getEntityLazyProxy() {
		if (entityLazyProxy == null) {
			entityLazyProxy = ServiceManager.lookup(ServiceManager.LAZY_SERVICE);
		}
		return entityLazyProxy;
	}

}