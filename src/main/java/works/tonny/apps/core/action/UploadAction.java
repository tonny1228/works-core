package works.tonny.apps.core.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import works.tonny.apps.core.AttachReference;
import works.tonny.apps.core.AttachReferenceService;
import works.tonny.apps.core.AttachService;
import works.tonny.apps.core.Attachment;
import works.tonny.apps.user.AnonymousAction;

/**
 * 文件上传接口，可获取文件名、mime，扩展名，限制数量、大小等。
 * 
 * @ClassName: UploadAction
 * @Description:
 * @author TonnyO
 * @date 2012-2-15 下午3:49:44
 * @version 1.0
 */
public class UploadAction extends AnonymousAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected File[] file;
	protected String[] fileFilename;
	protected String type;
	protected String catalog;
	protected String objectId;
	protected long singleMaxSize;
	protected int num;
	protected AttachService attachService;
	protected AttachReferenceService attachReferenceService;
	protected String[] fileContentType;
	protected String folder;

	public String upload() throws IOException {
		if (singleMaxSize > 0) {
			for (int i = 0; i < file.length; i++) {
				if (file[i].length() > singleMaxSize) {
					request.setAttribute("error",
							"允许上传的单个文件最大为:" + org.llama.library.utils.Formatter.formatByte(singleMaxSize));
					return ERROR;
				}
			}
		}
		if (num > 0 && attachReferenceService.referenceAttachment(objectId, catalog).size() + file.length > num) {
			request.setAttribute("error", "允许上传的文件个数为:" + num);
			return ERROR;
		}
		List<Attachment> list = new ArrayList<Attachment>();
		List<String> refs = new ArrayList<String>();
		for (int i = 0; i < file.length; i++) {
			Attachment attachment = new Attachment();
			attachment.setFilename(fileFilename[i]);
			attachment.setMimetype(fileContentType[i]);
			attachment.setCatalog(StringUtils.substringBefore(catalog, "."));
			attachService.save(attachment, new FileInputStream(file[i]));
			file[i].delete();
			if (StringUtils.isNotEmpty(objectId)) {
				refs.add(attachReferenceService.saveAttachReference(attachment, catalog, objectId));
			}
			list.add(attachment);
		}
		request.setAttribute("attach", list);
		request.setAttribute("ref", refs);
		return SUCCESS;
	}

	public String dispatchUpload() throws IOException {
		if (singleMaxSize > 0) {
			for (int i = 0; i < file.length; i++) {
				if (file[i].length() > singleMaxSize) {
					request.setAttribute("error",
							"允许上传的单个文件最大为:" + org.llama.library.utils.Formatter.formatByte(singleMaxSize));
					return ERROR;
				}
			}
		}
		if ("replace".equals(type)) {
			List<AttachReference> lists = attachReferenceService.referenceAttachment(objectId, catalog);
			for (AttachReference attachReference : lists) {
				attachReferenceService.delete(attachReference.getId());
			}
		} else if (num > 0 && attachReferenceService.referenceAttachment(objectId, catalog).size() + file.length > num) {
			request.setAttribute("error", "允许上传的文件个数为:" + num);
			return ERROR;
		}
		List<Attachment> list = new ArrayList<Attachment>();
		List<String> refs = new ArrayList<String>();
		for (int i = 0; i < file.length; i++) {
			Attachment attachment = new Attachment();
			attachment.setFilename(fileFilename[i]);
			attachment.setMimetype(fileContentType[i]);
			attachment.setCatalog(catalog);
			attachService.save(attachment, new FileInputStream(file[i]));
			file[i].delete();
			if (StringUtils.isNotEmpty(objectId)) {
				refs.add(attachReferenceService.saveAttachReference(attachment, catalog, objectId));
			}
			list.add(attachment);
		}
		request.setAttribute("attach", list);
		request.setAttribute("ref", refs);
		return SUCCESS;
	}

	public String specailFile() {
		String path = getParameter("path");
		if ("replace".equals(type)) {
			List<AttachReference> lists = attachReferenceService.referenceAttachment(objectId, catalog);
			for (AttachReference attachReference : lists) {
				attachReferenceService.delete(attachReference.getId());
			}
		} else if (num > 0 && attachReferenceService.referenceAttachment(objectId, catalog).size() >= num) {
			request.setAttribute("error", "允许上传的文件个数为:" + num);
			return ERROR;
		}
		File file = new File(attachService.getRootFolder(), folder + path);
		Attachment attachment = new Attachment();
		attachment.setFilename(file.getName());
		attachment.setPath(path);
		attachment.setCatalog(catalog);
		attachService.save(attachment);
		if (StringUtils.isNotEmpty(objectId)) {
			request.setAttribute("ref", attachReferenceService.saveAttachReference(attachment, catalog, objectId));
		}
		request.setAttribute("attach", attachment);
		return SUCCESS;
	}

	public void setFileFileName(String[] fileName) {
		this.fileFilename = fileName;
	}

	public void setFileContentType(String[] contentType) {
		this.fileContentType = contentType;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public AttachService getAttachService() {
		return attachService;
	}

	public void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public AttachReferenceService getAttachReferenceService() {
		return attachReferenceService;
	}

	public void setAttachReferenceService(AttachReferenceService attachReferenceService) {
		this.attachReferenceService = attachReferenceService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * @return the singleMaxSize
	 */
	public long getSingleMaxSize() {
		return singleMaxSize;
	}

	/**
	 * @param singleMaxSize
	 *            the singleMaxSize to set
	 */
	public void setSingleMaxSize(long singleMaxSize) {
		this.singleMaxSize = singleMaxSize;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

}
