package works.tonny.apps.core.dao;

import java.util.List;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityDAO;
import works.tonny.apps.core.AttachReference;
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
public interface AttachReferenceDAO extends EntityDAO<AttachReference> {
	/**
	 * 对象的关联附件
	 * 
	 * @Title: referenceAttachment
	 * @param objectId
	 * @return
	 * @date 2012-5-10 上午11:11:32
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = "objectId", order = "updateTime")
	List<AttachReference> referenceAttachment(String objectId);

	/**
	 * 对象的关联附件
	 * 
	 * @Title: referenceAttachment
	 * @param objectId
	 * @return
	 * @date 2012-5-10 上午11:11:32
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "objectId", "catalog" }, order = "updateTime")
	List<AttachReference> referenceAttachment(String objectId, String catalog);

	/**
	 * 对象的关联附件
	 * 
	 * @Title: referenceAttachment
	 * @param objectId
	 * @return
	 * @date 2012-5-10 上午11:11:32
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "objectId", "catalog", "attachment.id" }, order = "updateTime")
	AttachReference referenceAttachment(String objectId, String catalog, String attachId);

	/**
	 * @Title: referenceAttachment
	 * @param objectId
	 * @param catalog
	 * @param mimeType
	 * @param fileext
	 * @return
	 * @date 2012-6-6 下午9:31:24
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "objectId", "catalog", "attachment.mimetype", "attachment.fileext" }, operator = {
			ListSupport.EQUALS, ListSupport.EQUALS, ListSupport.RLIKE, ListSupport.EQUALS }, order = "updateTime")
	List<AttachReference> referenceAttachment(String objectId, String catalog, String mimeType, String fileext);

	/**
	 * @Title: referenceAttachment
	 * @param catalog
	 * @param mimeType
	 * @param page
	 * @param pagesize
	 * @return
	 * @date 2012-6-15 上午10:43:26
	 * @author tonny
	 * @version 1.0
	 */
	@ListSupport(field = { "catalog", "attachment.mimetype" }, operator = { ListSupport.EQUALS, ListSupport.RLIKE }, order = "updateTime")
	PagedList<AttachReference> referenceAttachment(String catalog, String mimeType, int page, int pagesize);
}
