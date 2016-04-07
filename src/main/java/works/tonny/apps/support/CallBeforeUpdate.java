package works.tonny.apps.support;

/**
 * 保存之前如何复制属性
 * 
 * @author tonny
 *
 */
public interface CallBeforeUpdate<T> {
	/**
	 * 将edited的属性复制到toUpdate以进行保存
	 * 
	 * @param toUpdate
	 *            从数据库中获取的待保存的对象
	 * @param edited
	 *            修改后的对象
	 */
	void execute(T toUpdate, T edited);
}
