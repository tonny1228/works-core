package works.tonny.apps.core;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.llama.library.utils.PagedList;

import works.tonny.apps.EntityService;
import works.tonny.apps.auth.AuthType;
import works.tonny.apps.auth.AuthVerify;


public interface AttachService extends EntityService<Attachment> {

	/**
	 * 
	 * @Title: save
	 * @param att
	 * @param in
	 * @date 2012-6-4 上午10:05:58
	 * @author tonny
	 * @version 1.0
	 */
	@AuthVerify(AuthType.CREATE)
	String save(Attachment att, InputStream in);

	/**
	 * 附件查询
	 * 
	 * @param title 标题
	 * @param fromDate 开始时间
	 * @param endDate 结束时间
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@AuthVerify(AuthType.LIST)
	PagedList<Attachment> listAttach(String title, String filename, Date fromDate, Date endDate, String fileext,
			String catalog, int page, int pagesize);

	/**
	 * 附件查询
	 * 
	 * @param title 标题
	 * @param fromDate 开始时间
	 * @param endDate 结束时间
	 * @param page
	 * @param pagesize
	 * @return
	 */
	@AuthVerify(AuthType.LIST)
	PagedList<Attachment> listAttach(String[] fileext, String catalog, int page, int pagesize);

	/**
	 * 修改附件信息
	 * 
	 * @param id 附件ID
	 * @param title 附件名称
	 * @param info 附件说明
	 * @param admin 管理员
	 */
	@AuthVerify(AuthType.UPDATE)
	void updateAttach(String id, String title, String info);

	/**
	 * 下载远程文件
	 * 
	 * @param url 远程地址
	 * @param site 站点编号
	 * @param path 路径
	 * @param admin 管理员
	 */
	@AuthVerify(AuthType.CREATE)
	void loadURLAsAttachment(String url);

	/**
	 * 获得附件文件
	 * 
	 * @Title: attachFile
	 * @param attachment
	 * @return
	 * @date 2012-6-27 上午11:34:30
	 * @author tonny
	 * @version 1.0
	 */
	@AuthVerify(AuthType.READ)
	File attachFile(Attachment attachment);

	/**
	 * 获得应用上传根目录
	 * 
	 * @return
	 */
	String getRootFolder();

}
