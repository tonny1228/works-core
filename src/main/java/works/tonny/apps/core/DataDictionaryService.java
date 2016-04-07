/**
 * 
 */
package works.tonny.apps.core;

import java.util.List;
import java.util.Map;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityService;

/**
 * @author 祥栋
 */
public interface DataDictionaryService extends EntityService<DataDictionary> {

	/**
	 * 根据分类查询所有的数据
	 * 
	 * @param catalog 分类编号
	 * @return 所有的数据
	 */
	List<DataDictionary> list(String catalog);

	/**
	 * 查询所有的数据条目
	 * 
	 * @param page 页码
	 * @param pagesize 页面大小
	 * @return
	 */
	PagedList<DataDictionary> list(int page, int pagesize);

	/**
	 * 查询词条中所有的分类和名称
	 * 
	 * @return 前一个作为catalog,后一个值为catalogName
	 */
	Map<String, String> allCatalogs();

}
