package works.tonny.apps.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 上传的附件类
 * 
 * @author tonny
 * @date 二○一一年五月十八日
 */
public class UploadFile {

	private static final Map<String, String> CONTENT_TYPE = new HashMap<String, String>();
	static {
		CONTENT_TYPE.put("", "");
	}

	private String contentType;

	private String filename;

	private long size;

	private String charset;

	private File location;

	private String path;

	private String newName;

	private InputStream is;

	private DiskFileItem file;

	/**
	 * 初始化文件
	 * 
	 * @param file 上传的文件
	 */
	public UploadFile(DiskFileItem file) {
		this.charset = file.getCharSet();
		this.filename = file.getName();
		this.contentType = file.getContentType();
		this.location = file.getStoreLocation();
		this.size = file.getSize();
		this.filename = filename.substring(filename.lastIndexOf("\\") + 1);
		this.newName = java.util.UUID.randomUUID().toString() + ".att";
		this.file = file;
	}

	/**
	 * 返回文件扩展名
	 * 
	 * @Title: getFileExt
	 * @return String 扩展名
	 */
	public String getFileExt() {
		return StringUtils.substringAfterLast(filename, ".").toLowerCase();
	}

	/**
	 * 将上传文件写到相对于内容管理根目录的目标目录
	 * 
	 * @Title: write
	 * @param baseFolder 基础目录
	 * @param folder 目录
	 * @throws IOException io异常
	 */
	public void write(String baseFolder, String folder) throws IOException {
		this.path = folder + this.newName;
		File f = new File(baseFolder + path);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		if (location.exists())
			FileUtils.moveFile(location, f);
		else
			IOUtils.copy(file.getInputStream(), new FileOutputStream(f));
	}

	/**
	 * 返回path
	 * 
	 * @return 路径
	 */
	public String getPath() {
		if (path == null) {
			return null;
		}
		return path.replaceAll("\\\\", "/");
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	/**
	 * 删除上传附件
	 */
	public void delete() {
		file.delete();
	}

	/**
	 * 获取文件流
	 * 
	 * @return 文件流
	 * @throws IOException 异常
	 */
	public InputStream getInputStream() throws IOException {
		if (location.exists()) {
			return new FileInputStream(location);
		} else {
			return file.getInputStream();
		}
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
	}

}
