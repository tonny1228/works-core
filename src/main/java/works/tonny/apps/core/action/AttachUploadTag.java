/**
 * 
 */
package works.tonny.apps.core.action;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import works.tonny.apps.web.CommonTag;

/**
 * 文件上传组件自定义标签，可控制文件上传显示和限制文件上传类型、数量、大小等
 * 
 * @author 祥栋
 */
public class AttachUploadTag extends CommonTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1967796656296406255L;

	private String objectId;

	private String catalog;

	private String fileext;

	private String name;

	private String title;

	private String type;

	private String buttonText;

	private long singleMaxSize;

	private int num;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.web.CommonTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		try {
			String container = (String) request.getAttribute("_att_container");
            if (container == null || (StringUtils.isNotEmpty(catalog) && !container.contains(catalog))) {
				container = "_att_files_" + objectId + "_" + StringUtils.replaceChars(catalog, ".", "_");
			}
			StringBuffer bodyContent = new StringBuffer();
			if (StringUtils.isEmpty(objectId)) {
				objectId = "temp-"+UUID.randomUUID().toString();
				if (StringUtils.isNotEmpty(name)) {
					bodyContent.append("<input type='hidden' id='attach_object_id' name='" + name + "' value='"
							+ objectId + "'/>\r\n");
				}
				bodyContent.append("<div id='" + container + "'></div>");
			}
			String name = "uploader" + RandomUtils.nextInt(1000);
			bodyContent.append("<script src='" + StringUtils.stripToEmpty(request.getContextPath())
					+ "/apps/attach/upload.js.jsp'></script>\r\n");
			bodyContent.append("<script>\r\nvar " + name + " = new FileUpload('" + title + "',750,400,'" + catalog
					+ "','" + type + "','" + StringUtils.stripToEmpty(fileext) + "');\r\n");
			bodyContent.append(" " + name + ".objectId='" + objectId + "';");
			bodyContent.append(" " + name + ".container='" + container + "';\r\n");
			if (num > 0) {
				bodyContent.append(" " + name + ".num=" + num + ";");
			}
			if (singleMaxSize > 0) {
				bodyContent.append(" " + name + ".singleMaxSize=" + singleMaxSize + ";");
			}
			bodyContent.append("</script>\r\n");
			bodyContent.append("<a onclick=' " + name + ".show()' class='js-button button-icon-upload'>")
					.append(buttonText != null ? buttonText : "上传").append("</a>\r\n");
			pageContext.getOut().write(bodyContent.toString());
		} catch (IOException e) {
			log.error(e);
		}
		objectId = null;
		return super.doEndTag();
	}

	/**
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	/**
	 * @return the fileext
	 */
	public String getFileext() {
		return fileext;
	}

	/**
	 * @param fileext the fileext to set
	 */
	public void setFileext(String fileext) {
		this.fileext = fileext;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the buttonText
	 */
	public String getButtonText() {
		return buttonText;
	}

	/**
	 * @param buttonText the buttonText to set
	 */
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	/**
	 * @return the singleMaxSize
	 */
	public long getSingleMaxSize() {
		return singleMaxSize;
	}

	/**
	 * @param singleMaxSize the singleMaxSize to set
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
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

}
