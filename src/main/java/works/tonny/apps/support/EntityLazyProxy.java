/**
 *
 */
package works.tonny.apps.support;

/**
 * hibernate延迟加载
 *
 * @author 祥栋
 * @version 1.0.0
 * @date 2012-12-18
 */
public interface EntityLazyProxy {
    /**
     * 刷新一个对象
     *
     * @param entity
     * @author tonny
     */
    void refresh(Object entity);

    /**
     * 刷新一个对象及其多个属性
     *
     * @param entity
     * @param fields
     * @author tonny
     */
    void refresh(Object entity, String... fields);

    /**
     * 刷新一个对象
     *
     * @param entity
     * @author tonny
     */
    <T> T refreshObject(Object entity);

    /**
     * 刷新一个对象及其多个属性
     *
     * @param entity
     * @param fields
     * @author tonny
     */
    <T> T refreshObject(Object entity, String... fields);

}
