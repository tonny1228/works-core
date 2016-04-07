package works.tonny.apps.core.dao;

import java.util.List;

import org.llama.library.sqlmapping.QueryParameters;
import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.Catalog;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.SQLMappingListSupport;

/**
 * 目录存储和查询接口
 * 
 * @ClassName: AttachDAO
 * @Description:
 * @author Tonny
 * @date 2011-10-22 下午12:58:38
 * @version 1.0
 */
public interface CatalogDAO extends EntityDAO<Catalog> {

	/**
	 * 查询父目录下子目录最大orderNo+1,没有为0
	 * 
	 * @Title: maxLayer
	 * @param layer
	 * @param layerLength
	 * @return
	 * @date 2012-5-14 下午4:11:52
	 * @author tonny
	 * @version 1.0
	 */
	// @SQLMappingListSupport(name = "Catalog.maxOrderNo")
	// int nextOrderNo(String layer, int length);

	/**
	 * 通过layer查询所有子目录
	 * 
	 * @Title: listSubsByLayer
	 * @param layer
	 * @return
	 * @date 2012-5-14 下午4:49:13
	 * @author tonny
	 * @version 1.0
	 */
	// @SQLMappingListSupport(name = "Catalog.listSubLayer", appender = { "lengthlt", "order" })
	// PagedList<Catalog> listSubsByLayer(String layer, int length, int parentLength);

	@SQLMappingListSupport(name = "Catalog.listSubLayer")
	PagedList<Catalog> query(QueryParameters parameters, int page, int pagesize);

	/**
	 * 按层、类型查询
	 * 
	 * @param layer
	 * @param depth
	 * @param type
	 * @return
	 */
	@ListSupport(field = { "treeNode.idLayer", "treeNode.depth", "type" }, operator = { ListSupport.RLIKE,
			ListSupport.BETWEEN_N_L, ListSupport.EQUALS }, order = { "treeNode.depth","treeNode.orderNo" })
	List<Catalog> listSubsByLayer(String layer, Integer[] depth, Integer type);

	/**
	 * 通过layer查询所有子目录
	 * 
	 * @Title: listSubsByLayer
	 * @param layer
	 * @return
	 * @date 2012-5-14 下午4:49:13
	 * @author tonny
	 * @version 1.0
	 */
	 @SQLMappingListSupport(name = "Catalog.listSubLayer", appender = { "order" })
//	@ListSupport(field = { "treeNode.idLayer", "type" }, operator = { ListSupport.RLIKE, ListSupport.EQUALS }, defaults = "treeNode.idLayer<>:treeNode_idLayer", order = {
//			"substring(t.treeNode.idLayer,1,locate(t.id,t.treeNode.idLayer)-1)", "treeNode.orderNo" })
	List<Catalog> listSubsByLayer(String layer, Integer type);

	/**
	 * 查下子目录的类型目录
	 * 
	 * @Title: find
	 * @param layer
	 * @param type
	 * @return
	 * @date 2012-5-15 下午5:12:58
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "treeNode.parentId", "type" }, order = { "treeNode.orderNo" })
	List<Catalog> listSubs(String parentId, int type);

	/**
	 * 查下子目录的类型目录
	 * 
	 * @Title: find
	 * @param layer
	 * @param type
	 * @return
	 * @date 2012-5-15 下午5:12:58
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "treeNode.parentId" }, order = { "treeNode.orderNo" })
	List<Catalog> listSubs(String parentId);

	/**
	 * 按名称查询重名的栏目
	 * 
	 * @param id 不是此栏目
	 * @param parentId 上级栏目
	 * @param name 名称
	 */
	@ListSupport(field = { "id", "treeNode.parentId", "name" }, operator = { ListSupport.NOT_EQUALS,
			ListSupport.EQUALS, ListSupport.EQUALS }, order = { "treeNode.orderNo" })
	List<Catalog> list(String id, String parentId, String name);

	@ListSupport(field = { "name", "treeNode.parentId", "status", "display", "type" }, order = { "treeNode.orderNo" })
	List<Catalog> list(String name, String parentId, Integer status, Integer display, Integer type);

}
