package works.tonny.apps;

import java.io.Serializable;
import java.util.Collection;

public interface EntityDAO<T> {

	/**
	 * 保存基本记录
	 * 
	 * @param o 数据对象
	 * @return id
	 */
	String save(T o);

	/**
	 * 更新数据记录
	 * 
	 * @param o 数据对象
	 */
	void update(T o);

	/**
	 * 删除数据对象
	 * 
	 * @param id id
	 */
	void delete(T obj);

	/**
	 * 按id删除
	 * 
	 * @param id
	 * @author tonny
	 */
	void delete(String id);

	/**
	 * 删除所有的信息
	 * 
	 * @param entities
	 */
	void deleteAll(String[] id);

	/**
	 * 删除所有的信息
	 * 
	 * @param entities
	 */
	void deleteAll(Collection<T> list);

	/**
	 * 查找对象
	 * 
	 * @param 对象
	 * @param id
	 */
	T get(String id);

	/**
	 * 查找对象
	 * 
	 * @param 对象
	 * @param id
	 */
	T get(Class clz, String id);

	/**
	 * 输出缓冲
	 * 
	 * @author tonny
	 */
	void flush();
}
