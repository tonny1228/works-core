/**
 * 
 */
package works.tonny.apps.support;

/**
 * hibernate延迟加载
 * 
 * @author 祥栋
 * @date 2012-12-18
 * @version 1.0.0
 */
public interface EntityLazyProxy {
	/**
	 * 刷新一个对象
	 * 
	 * @param entity
	 * @author tonny
	 */
    <T> T refresh(Object entity);

	/**
	 * 刷新一个对象及其多个属性
	 * 
	 * @param entity
	 * @param fields
	 * @author tonny
	 */
    <T> T refresh(Object entity, String... fields);

}
