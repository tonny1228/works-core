/**  
 * @Title: AttachList.java
 * @Package works.tonny.core.action
 * @author Tonny
 * @date 2012-6-6 下午9:23:23
 */
package works.tonny.apps.core.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.Formatter;

import works.tonny.apps.core.AttachReference;
import works.tonny.apps.core.AttachReferenceService;
import works.tonny.apps.core.AttachService;
import works.tonny.apps.support.ServiceManager;
import works.tonny.apps.user.AnonymousTag;

/**
 * 附件输出标签
 * 
 * @ClassName: AttachList
 * @Description:
 * @author Tonny
 * @date 2012-6-6 下午9:23:23
 * @version 1.0
 */
public class AttachListTag extends AnonymousTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8629869602093029591L;

	private String objectId;

	private String id;

	private String catalog;

	private String mimeType;

	private String fileext;

	private int num = 5;

	private boolean outHtml = true;

	private boolean manage;

	private List<AttachReference> list;

	private int idx;

	public int doStartTag() throws JspException {
		loginedUser();
		list = null;
		AttachReferenceService attachReferenceService = ServiceManager.lookup(ServiceManager.ATTACH_REFERENCE_SERVICE);
		if (StringUtils.isNotEmpty(objectId) && StringUtils.isNotEmpty(catalog)) {
			list = attachReferenceService.referenceAttachment(objectId, catalog, mimeType, fileext);
		}
		if (StringUtils.isNotEmpty(objectId) && StringUtils.isEmpty(catalog)) {
			list = attachReferenceService.referenceAttachment(objectId);
		}
		if (StringUtils.isEmpty(objectId) && StringUtils.isNotEmpty(catalog)) {
			list = attachReferenceService.referenceAttachment(catalog, mimeType, 1, num);
		}
		if (StringUtils.isNotEmpty(id)) {
			list = new ArrayList<AttachReference>();
			AttachService attachService = ServiceManager.lookup(ServiceManager.ATTACH_SERVICE);
			list.add(new AttachReference(attachService.get(id)));
		}
		pageContext.getRequest().setAttribute("_attachList", list);
		idx = 0;
		if (list != null && list.size() > 0) {
			return EVAL_PAGE;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doInitBody()
	 */
	@Override
	public void doInitBody() throws JspException {
		super.doInitBody();
		AttachReference attachReference = list.get(idx++);
		setPath(attachReference);
		pageContext.getRequest().setAttribute("_attach", attachReference);
	}

	/**
	 * @return
	 * @throws JspException
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doAfterBody()
	 */
	@Override
	public int doAfterBody() throws JspException {
		if (idx >= list.size() || idx >= num) {
			return SKIP_BODY;
		}
		AttachReference attachReference = list.get(idx++);
		setPath(attachReference);
		pageContext.getRequest().setAttribute("_attach", attachReference);
		return EVAL_BODY_AGAIN;
	}

	/**
	 * @param attachReference
	 * @return
	 */
	private void setPath(AttachReference attachReference) {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		attachReference.getAttachment().setPath(
				StringUtils.trimToEmpty(request.getContextPath()) + "/filedown/"
						+ attachReference.getAttachment().getPath());
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		if (bodyContent == null && outHtml) {
			StringBuffer buffer = new StringBuffer();
			try {
				buffer.append("<div id='_att_files_" + objectId + "_" + StringUtils.replaceChars(catalog, ".", "_")
						+ "'>\r\n");
				request.setAttribute("_att_container",
						"_att_files_" + objectId + "_" + StringUtils.replaceChars(catalog, ".", "_"));
				String context = StringUtils.stripToEmpty(request.getContextPath());
				if (list != null) {
					for (AttachReference attachReference : list) {
						if (attachReference.getAttachment().getMimetype().startsWith("image/")) {
							buffer.append("<div id='att_" + attachReference.getId() + "' class='fileitem'><img src='"
									+ context + "/filedown/" + attachReference.getAttachment().getPath()
									+ "' width='32' class='attimg'/>");

						} else {
							buffer.append("<div id='att_" + attachReference.getId() + "' class='fileitem'><a href='"
									+ context + "/filedown/" + attachReference.getAttachment().getPath() + "?name="
									+ URLEncoder.encode(attachReference.getAttachment().getFilename(), "utf-8")
									+ "' title='" + attachReference.getAttachment().getFilename()
									+ "' class='attfile'>" + attachReference.getAttachment().getFilename() + "</a>");
						}
						if (manage) {
							buffer.append(" <a class='icon-del' href=\"javascript:delAtt('"
									+ attachReference.getId() + "')\" class=\"del\"></a><div class='upload_status'>"
									+ Formatter.formatByte(attachReference.getAttachment().getFilesize()) + "</div>");
						}

						buffer.append(" </div>\r\n");
					}
				}
				buffer.append("</div>\r\n");
				pageContext.getOut().write(buffer.toString());
			} catch (IOException e) {
				log.error(e);
			}
		} else {
		}
		pageContext.getRequest().removeAttribute("_attach");
		list = null;
		return super.doEndTag();
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFileext() {
		return fileext;
	}

	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the outHtml
	 */
	public boolean isOutHtml() {
		return outHtml;
	}

	/**
	 * @param outHtml the outHtml to set
	 */
	public void setOutHtml(boolean outHtml) {
		this.outHtml = outHtml;
	}

	/**
	 * @return the manage
	 */
	public boolean isManage() {
		return manage;
	}

	/**
	 * @param manage the manage to set
	 */
	public void setManage(boolean manage) {
		this.manage = manage;
	}

}
