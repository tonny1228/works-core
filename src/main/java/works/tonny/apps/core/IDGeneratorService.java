/**
 * 
 */
package works.tonny.apps.core;

import java.util.List;
import java.util.Map;

import works.tonny.apps.auth.AuthType;
import works.tonny.apps.auth.AuthVerify;

/**
 * 表单id生成工具
 * 
 * @author 祥栋
 */
public interface IDGeneratorService {
	/**
	 * 列出所有的id生成器
	 * 
	 * @return
	 */
	@AuthVerify(AuthType.READ)
	List<IDGenerator> list();

	/**
	 * 通过id查询生成器
	 * 
	 * @param id 生成器id
	 * @return
	 */
	@AuthVerify(AuthType.READ)
	IDGenerator get(String id);

	/**
	 * 通过id生成器生成id
	 * 
	 * @param id id生成器
	 * @param context 数据
	 * @return
	 */
	String create(String id, Map<String, Object> context);
}
