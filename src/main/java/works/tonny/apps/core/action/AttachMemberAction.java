/**
 * 
 */
package works.tonny.apps.core.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.PagedList;

import works.tonny.apps.core.AttachReference;
import works.tonny.apps.core.AttachReferenceService;
import works.tonny.apps.core.AttachService;
import works.tonny.apps.core.Attachment;
import works.tonny.apps.user.MemberAuthedAction;

/**
 * @author 祥栋
 */
public class AttachMemberAction extends MemberAuthedAction {
	private String catalog;

	private String objectId;

	private String type;

	private AttachService attachService;

	private String folder;

	private AttachReferenceService attachReferenceService;

	private String id;

	public String list() {
		if (StringUtils.isEmpty(getParameter("type")) && StringUtils.isEmpty(StringUtils.substringBefore(catalog, "."))) {
			return SUCCESS;
		}
		// PagedList<Attachment> list =
		// attachService.listAttach(getParameterNullToEmpty("fileext").split(","),
		// StringUtils.substringBefore(catalog, "."), getPage(), getPagesize());
		String ext = getParameterNullToEmpty("fileext");

		String[] exts = null;
		if (StringUtils.isNotEmpty(ext)) {
			exts = ext.split(",");
		}

		PagedList<Attachment> list = attachService.listAttach(exts, catalog, getPage(), getPagesize());
		request.setAttribute("list", list);
		return SUCCESS;
	}

	public String ref() {
		if ("replace".equals(type)) {
			List<AttachReference> lists = attachReferenceService.referenceAttachment(objectId, catalog);
			for (AttachReference attachReference : lists) {
				attachReferenceService.delete(attachReference.getId());
			}
		}
		List<String> ref = new ArrayList<String>();
		ref.add(attachReferenceService.saveAttachReference(id, catalog, objectId));
		request.setAttribute("ref", ref);
		List<Attachment> list = new ArrayList<Attachment>();
		list.add(attachService.get(id));
		request.setAttribute("attach", list);
		return SUCCESS;
	}

	public String deleteRef() {
		attachReferenceService.delete(id);
		return SUCCESS;
	}

	public String upload() {
		list();
		browse();
		return SUCCESS;
	}

	public String browse() {
		String dir = getParameter("dir");
		File file = new File(attachService.getRootFolder(), folder + File.separator
				+ StringUtils.defaultIfEmpty(dir, ""));
		request.setAttribute("files", file.listFiles());
		if (StringUtils.isNotEmpty(dir)) {
			if (dir.contains("/")) {
				request.setAttribute("parent", StringUtils.substringBeforeLast(dir, "/"));
			} else {
				request.setAttribute("parent", "");
			}
		}
		request.setAttribute("query", request.getQueryString().replaceAll("&dir=[^&]+", ""));
		return SUCCESS;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AttachService getAttachService() {
		return attachService;
	}

	public void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}

	public AttachReferenceService getAttachReferenceService() {
		return attachReferenceService;
	}

	public void setAttachReferenceService(AttachReferenceService attachReferenceService) {
		this.attachReferenceService = attachReferenceService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

}
