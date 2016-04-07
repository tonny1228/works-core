package works.tonny.apps.core.dao;

import java.util.Date;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.Attachment;
import works.tonny.apps.support.ListSupport;


/**
 * 附件存储和查询接口
 * 
 * @ClassName: AttachDAO
 * @Description:
 * @author Tonny
 * @date 2011-10-22 下午12:58:38
 * @version 1.0
 */
public interface AttachDAO extends EntityDAO<Attachment> {

	/**
	 * 附件查询
	 * 
	 * @Title: list
	 * @Description:
	 * @param @param page
	 * @param @param pagesize
	 * @param @return 设定文件
	 * @return PagedList<Attachment> 返回类型
	 * @throws
	 */
	@ListSupport(field = { "title", "catalog", "filename", "fileext", "mimetype", "updateTime" }, operator = {
			ListSupport.LIKE, ListSupport.EQUALS, ListSupport.LIKE, ListSupport.EQUALS, ListSupport.LIKE,
			ListSupport.BETWEEN }, order = "updateTime desc")
	PagedList<Attachment> list(String title, String catalog, String filename, String fileext, String mimetype,
			Date[] updateTime, int page, int pagesize);

	/**
	 * 附件查询
	 * 
	 * @Title: list
	 * @Description:
	 * @param @param page
	 * @param @param pagesize
	 * @param @return 设定文件
	 * @return PagedList<Attachment> 返回类型
	 * @throws
	 */
	@ListSupport(field = { "catalog", "fileext" }, operator = { ListSupport.LIKE, ListSupport.IN }, order = "updateTime desc")
	PagedList<Attachment> list(String catalog, String[] fileext, int page, int pagesize);

}
