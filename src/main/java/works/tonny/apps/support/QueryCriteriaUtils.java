/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-23 下午3:22:32
 * @author tonny
 */
package works.tonny.apps.support;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.llama.library.utils.ClassUtils;
import org.llama.library.utils.ConvertUtils;

import works.tonny.apps.Query;
import works.tonny.apps.exception.WebException;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public abstract class QueryCriteriaUtils {

	/**
	 * 根据参数自动去匹配查询条件和排序条件进行查询
	 * 
	 * @param query
	 * @param params
	 * @author tonny
	 */
	public static void buildQuery(Query query, Map<String, String[]> params) {
		String order = StringUtils.join(params.get("_order"));
		String param = StringUtils.join(params.get("_orderparam"));
		// query.boardId(boardId).isTopic(true);
		final Class ifc = ClassUtils.getInterface(query.getClass(), Query.class);
		final Method[] methods = ifc.getMethods();
		for (Method method : methods) {
			final QueryCriteria annotation = method.getAnnotation(QueryCriteria.class);
			if (annotation == null) {
				continue;
			}

			String value = StringUtils.join(params.get(method.getName()));
			if (StringUtils.isNotEmpty(value)) {
				try {
					if (annotation.type().equals(QueryCriteria.TYPE_BOOLEAN) && method.getParameterTypes().length == 0) {
						method.invoke(query);
					} else {
						method.invoke(query, ConvertUtils.convert(method.getParameterTypes()[0], value));
					}
				} catch (Exception e) {
					throw new WebException(e);
				}
			}
		}
		if (StringUtils.isEmpty(order)) {
			return;
		}

		Method m = null;
		try {
			if (StringUtils.isEmpty(param)) {
				m = ifc.getMethod(order);
			} else {
				m = ifc.getMethod(order, Query.Direction.class);
			}
			m.invoke(query, Query.Direction.valueOf(param.toUpperCase()));
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

}
