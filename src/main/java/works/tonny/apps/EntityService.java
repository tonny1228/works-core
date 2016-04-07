/**  
 * @Title: EntityService.java
 * @Package works.tonny.framework.service
 * @author Tonny
 * @date 2012-4-18 下午4:59:19
 */
package works.tonny.apps;

import org.springframework.transaction.annotation.Transactional;

/**
 * 实体对象服务类
 * 
 * @ClassName: EntityService
 * @Description:
 * @author Tonny
 * @date 2012-4-18 下午4:59:19
 * @version 1.0
 */
public interface EntityService<T> {

	/**
	 * 保存对象
	 * 
	 * @Title: save
	 * @param t
	 * @return
	 * @date 2012-4-18 下午5:11:05
	 * @author tonny
	 * @version 1.0
	 */
	String save(T t);

	/**
	 * 读取对象
	 * 
	 * @Title: save
	 * @param id 编号
	 * @return 对象
	 * @date 2012-4-18 下午5:11:05
	 * @author tonny
	 * @version 1.0
	 */
	T get(String id);

	/**
	 * 更新对象
	 * 
	 * @Title: update
	 * @param t
	 * @date 2012-4-18 下午5:11:19
	 * @author tonny
	 * @version 1.0
	 */
	void update(T t);

	/**
	 * 通过id删除对象
	 * 
	 * @Title: delete
	 * @param id
	 * @date 2012-4-18 下午5:11:25
	 * @author tonny
	 * @version 1.0
	 */
	void delete(String id);

	/**
	 * 通过多个id删除多个对象
	 * 
	 * @Title: delete
	 * @param ids
	 * @date 2012-4-18 下午5:11:35
	 * @author tonny
	 * @version 1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	void delete(String[] ids);
}
