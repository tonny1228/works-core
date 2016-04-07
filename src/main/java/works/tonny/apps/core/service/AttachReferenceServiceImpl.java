/**  
 * @Title: AttachReferenceServiceImpl.java
 * @Package works.tonny.core.service
 * @author Tonny
 * @date 2012-6-6 下午12:00:01
 */
package works.tonny.apps.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.core.AttachReference;
import works.tonny.apps.core.AttachReferenceService;
import works.tonny.apps.core.Attachment;
import works.tonny.apps.core.dao.AttachDAO;
import works.tonny.apps.core.dao.AttachReferenceDAO;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;

/**
 * @ClassName: AttachReferenceServiceImpl
 * @Description:
 * @author Tonny
 * @date 2012-6-6 下午12:00:01
 * @version 1.0
 */
public class AttachReferenceServiceImpl extends AuthedAbstractService implements AttachReferenceService {

	private AttachReferenceDAO attachReferenceDAO;

	private AttachDAO attachDAO;

	/**
	 * @param t
	 * @return
	 * @see com.zxtx.apps.EntityService#save(java.lang.Object)
	 */
	public String save(AttachReference t) {
		LoginedUser admin = getLoginedUser();
		t.setAdmin(admin.getUser().getUsername());
		t.setUpdateTime(new Date());
		return attachReferenceDAO.save(t);
	}

	/**
	 * @param id
	 * @return
	 * @see com.zxtx.apps.EntityService#get(java.lang.String)
	 */
	public AttachReference get(String id) {
		return attachReferenceDAO.get(id);
	}

	/**
	 * @param t
	 * @see com.zxtx.apps.EntityService#updateArticle(java.lang.Object)
	 */
	public void update(AttachReference t) {
		t.setUpdateTime(new Date());
		LoginedUser admin = getLoginedUser();
		t.setAdmin(admin.getUser().getUsername());
		attachReferenceDAO.update(t);
	}

	/**
	 * @param id
	 * @see com.zxtx.apps.EntityService#delete(java.lang.String)
	 */
	public void delete(String id) {
		attachReferenceDAO.delete(get(id));
	}

	/**
	 * @param ids
	 * @see com.zxtx.apps.EntityService#delete(java.lang.String[])
	 */
	@Transactional(rollbackFor = Exception.class)
	public void delete(String[] ids) {
		for (String id : ids) {
			delete(id);
		}
	}

	/**
	 * @param objectId
	 * @return
	 * @see com.zxtx.apps.core.AttachReferenceService#referenceAttachment(java.lang.String)
	 */
	public List<AttachReference> referenceAttachment(String objectId) {
		if (StringUtils.isEmpty(objectId)) {
			return new ArrayList<AttachReference>();
		}
		return attachReferenceDAO.referenceAttachment(objectId);
	}

	/**
	 * @param attId
	 * @param catalog
	 * @param objectId
	 * @see com.zxtx.apps.core.AttachReferenceService#insertAttachReference(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String saveAttachReference(Attachment att, String catalog, String objectId) {
		AttachReference t = new AttachReference(att);
		t.setCatalog(catalog);
		t.setObjectId(objectId);
		return save(t);
	}

	/**
	 * @param objectId
	 * @param catalog
	 * @return
	 * @see com.zxtx.apps.core.AttachReferenceService#referenceAttachment(java.lang.String,
	 *      java.lang.String)
	 */
	public List<AttachReference> referenceAttachment(String objectId, String catalog) {
		return attachReferenceDAO.referenceAttachment(objectId, catalog);
	}

	/**
	 * @param newObjectId
	 * @param origObjectId
	 * @see com.zxtx.apps.core.AttachReferenceService#updateReference(java.lang.String,
	 *      java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateReference(String newObjectId, String origObjectId) {
		List<AttachReference> list = referenceAttachment(origObjectId);
		for (AttachReference attachReference : list) {
			attachReference.setObjectId(newObjectId);
			attachReferenceDAO.update(attachReference);
		}
	}

	public AttachReferenceDAO getAttachReferenceDAO() {
		return attachReferenceDAO;
	}

	public void setAttachReferenceDAO(AttachReferenceDAO attachReferenceDAO) {
		this.attachReferenceDAO = attachReferenceDAO;
	}

	public AttachDAO getAttachDAO() {
		return attachDAO;
	}

	public void setAttachDAO(AttachDAO attachDAO) {
		this.attachDAO = attachDAO;
	}

	/**
	 * @param attId
	 * @param catalog
	 * @param objectId
	 * @see com.zxtx.apps.core.AttachReferenceService#saveAttachReference(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String saveAttachReference(String attId, String catalog, String objectId) {
		AttachReference referenceAttachment = attachReferenceDAO.referenceAttachment(objectId, catalog, attId);
		if (referenceAttachment == null) {
			return saveAttachReference(attachDAO.get(attId), catalog, objectId);
		} else {
			referenceAttachment.setAttachment(attachDAO.get(attId));
			attachReferenceDAO.update(referenceAttachment);
			return referenceAttachment.getId();
		}
	}

	/**
	 * @param objectId
	 * @param catalog
	 * @param mimeType
	 * @param fileext
	 * @return
	 * @see com.zxtx.apps.core.AttachReferenceService#referenceAttachment(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<AttachReference> referenceAttachment(String objectId, String catalog, String mimeType, String fileext) {
		return attachReferenceDAO.referenceAttachment(objectId, catalog, mimeType, fileext);
	}

	/**
	 * @param catalog
	 * @param mimeType
	 * @param page
	 * @param pagesize
	 * @return
	 * @see com.zxtx.apps.core.AttachReferenceService#referenceAttachment(java.lang.String,
	 *      java.lang.String, int, int)
	 */
	public PagedList<AttachReference> referenceAttachment(String catalog, String mimeType, int page, int pagesize) {
		return attachReferenceDAO.referenceAttachment(catalog, mimeType, page, pagesize);
	}

	/**
	 * @param objectId
	 * @see com.zxtx.apps.core.AttachReferenceService#deleteReference(java.lang.String)
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteReference(String objectId) {
		List<AttachReference> references = attachReferenceDAO.referenceAttachment(objectId);
		for (AttachReference attachReference : references) {
			attachReferenceDAO.delete(attachReference);
		}
	}

}
