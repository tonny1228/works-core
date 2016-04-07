/**  
 * @Title: AttachRefefenceService.java
 * @Package works.tonny.core.service
 * @author Tonny
 * @date 2012-5-10 上午11:22:18
 */
package works.tonny.apps.core;

import java.util.List;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityService;

/**
 * @ClassName: AttachRefefenceService
 * @Description:
 * @author Tonny
 * @date 2012-5-10 上午11:22:18
 * @version 1.0
 */
public interface AttachReferenceService extends EntityService<AttachReference> {

	List<AttachReference> referenceAttachment(String objectId, String catalog, String mimeType, String fileext);

	PagedList<AttachReference> referenceAttachment(String catalog, String mimeType, int page, int pagesize);

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
	void deleteReference(String objectId);

	/**
	 * 更新关联附件
	 * 
	 * @Title: referenceAttachment
	 * @param objectId
	 * @return
	 * @date 2012-5-10 上午11:11:32
	 * @author tonny
	 * @version 1.0
	 */
	void updateReference(String newObjectId, String origObjectId);

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
	List<AttachReference> referenceAttachment(String objectId, String catalog);

	/**
	 * 添加附件使用记录
	 * 
	 * @param attId 附件id
	 * @param function 模块
	 * @param objectId 使用对象id
	 * @param admin 管理员
	 */
	String saveAttachReference(Attachment att, String catalog, String objectId);

	/**
	 * 添加附件使用记录
	 * 
	 * @param attId 附件id
	 * @param function 模块
	 * @param objectId 使用对象id
	 * @param admin 管理员
	 */
	String saveAttachReference(String attId, String catalog, String objectId);

}