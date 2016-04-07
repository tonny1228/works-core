/**  
 * @Title: CatalogUpdateListener.java
 * @Package works.tonny.core.service
 * @author Tonny
 * @date 2012-6-15 下午8:57:50
 */
package works.tonny.apps.support.message;

/**
 * @ClassName: CatalogUpdateListener
 * @Description:
 * @author Tonny
 * @date 2012-6-15 下午8:57:50
 * @version 1.0
 */
public interface EntityEventListener<T> extends java.util.EventListener {
	/**
	 * 修建目录事件
	 * 
	 * @Title: created
	 * @param catalog
	 * @date 2012-6-15 下午8:59:01
	 * @author tonny
	 * @version 1.0
	 */
	void beforeCreate(T t);

	/**
	 * 修建目录事件
	 * 
	 * @Title: created
	 * @param catalog
	 * @date 2012-6-15 下午8:59:01
	 * @author tonny
	 * @version 1.0
	 */
	void afterCreated(T t);

	/**
	 * 修改目录事件
	 * 
	 * @Title: updated
	 * @param catalog
	 * @date 2012-6-15 下午8:59:21
	 * @author tonny
	 * @version 1.0
	 */
	void beforeUpdate(T before);

	/**
	 * 修改目录事件
	 * 
	 * @Title: updated
	 * @param catalog
	 * @date 2012-6-15 下午8:59:21
	 * @author tonny
	 * @version 1.0
	 */
	void afterUpdated(T before);

	/**
	 * 删除目录事件
	 * 
	 * @Title: deleted
	 * @param catalog
	 * @date 2012-6-15 下午8:59:41
	 * @author tonny
	 * @version 1.0
	 */
	void beforeDelete(T t);

	/**
	 * 删除目录事件
	 * 
	 * @Title: deleted
	 * @param catalog
	 * @date 2012-6-15 下午8:59:41
	 * @author tonny
	 * @version 1.0
	 */
	void afterDeleted(T t);

}
