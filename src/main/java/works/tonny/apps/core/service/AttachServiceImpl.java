package works.tonny.apps.core.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.ELHelper;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.WebAppPath;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.core.AttachService;
import works.tonny.apps.core.Attachment;
import works.tonny.apps.core.dao.AttachDAO;
import works.tonny.apps.exception.ServiceException;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.LoginedUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AttachServiceImpl extends AuthedAbstractService implements AttachService {

    private AttachDAO attachDAO;

    private String pathPattern;

    private String baseFolder;

    /**
     * @param t
     * @return
     * @throws ServiceException
     */
    public String save(Attachment t) throws ServiceException {
        LoginedUser admin = getLoginedUser();
        t.setId(UUID.randomUUID().toString());
        t.setUpdateTime(new Date());
        File file = new File(getRootFolder(), t.getPath());
        t.setFileext(StringUtils.substringAfterLast(t.getFilename(), "."));
        t.setFilesize((int) file.length());
        t.setAdmin(admin.getUser().getUsername());
        return attachDAO.save(t);
    }

    /**
     * @param t
     * @return
     * @throws ServiceException
     */
    public String save(Attachment t, InputStream inputStream) throws ServiceException {
        LoginedUser admin = getLoginedUser();

        Map<String, Object> context = getPathContext(t);
        t.setId(UUID.randomUUID().toString());
        t.setFileext(StringUtils.substringAfterLast(t.getFilename(), ".").toLowerCase());
        t.setUpdateTime(new Date());
        File file = new File(ELHelper.execute(baseFolder, context), ELHelper.execute(pathPattern, context));
        FileOutputStream os = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            os = new FileOutputStream(file);
            IOUtils.copy(inputStream, os);
        } catch (IOException e) {
            throw new ServiceException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }
        t.setPath(ELHelper.execute(pathPattern, context));
        t.setFilesize((int) file.length());
        t.setAdmin(admin.getUser().getUsername());
        return attachDAO.save(t);
    }

    protected Map<String, Object> getPathContext(Attachment t) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("webroot", WebAppPath.webRootPath());
        context.put("attach", t);
        return context;
    }

    /**
     * @param id
     * @return
     */
    public Attachment get(String id) {
        return attachDAO.get(id);
    }

    /**
     * @param t
     */
    public void update(Attachment t) {
        attachDAO.update(t);
    }

    /**
     * @param id
     */
    public void delete(String id) {
        Attachment att = get(id);
        attachDAO.delete(att);
        File file = new File(ELHelper.execute(baseFolder, getPathContext(att)), att.getPath());
        FileUtils.deleteQuietly(file);
    }

    /**
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] ids) {
        for (String id : ids) {
            delete(id);
        }
    }

    /**
     * @param title
     * @param filename
     * @param fromDate
     * @param endDate
     * @param fileext
     * @param catalog
     * @param page
     * @param pagesize
     * @return
     * java.lang.String, java.util.Date, java.util.Date, java.lang.String,
     * java.lang.String, java.lang.String, int, int)
     */
    public PagedList<Attachment> listAttach(String title, String filename, Date fromDate, Date endDate, String fileext,
                                            String catalog, int page, int pagesize) {
        if (fromDate == null) {
            fromDate = new Date(new Date().getTime() - 1000L * 3600 * 24 * 365 * 20);
        }
        if (endDate == null) {
            endDate = new Date();
        }
        Date[] time = new Date[]{fromDate, endDate};
        return attachDAO.list(title, catalog, filename, fileext, null, time, page, pagesize);
    }

    /**
     * @return
     * java.lang.String, java.util.Date, java.util.Date, java.lang.String,
     * java.lang.String, java.lang.String, int, int)
     */
    public PagedList<Attachment> listAttach(String[] type, String catalog, int page, int pagesize) {
        if (type != null && type.length == 0) {
            type = null;
        }
        return attachDAO.list(catalog, type, page, pagesize);
    }

    /**
     * @param id
     * @param title
     * @param info
     * java.lang.String, java.lang.String)
     */
    public void updateAttach(String id, String title, String info) {
        Attachment attachment = attachDAO.get(id);
        attachment.setTitle(title);
        attachment.setInfo(info);
        attachDAO.update(attachment);
    }

    /**
     * @param url
     */
    public void loadURLAsAttachment(String url) {
        Attachment t = new Attachment();
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            conn.connect();
            String filename = conn.getHeaderField("Content-disposition");
            if (filename != null) {
                t.setFilename(filename);
            } else {
                t.setFilename(StringUtils.substringAfterLast(url, "/"));
            }
            save(t, u.openStream());
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    public AttachDAO getAttachDAO() {
        return attachDAO;
    }

    public void setAttachDAO(AttachDAO attachDAO) {
        this.attachDAO = attachDAO;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public String getBaseFolder() {
        return baseFolder;
    }

    public void setBaseFolder(String baseFolder) {
        this.baseFolder = baseFolder;
    }

    /**
     * @return
     */
    public File attachFile(Attachment t) {
        Map<String, Object> context = getPathContext(t);
        String execute = ELHelper.execute(baseFolder, context);
        String s = null;
        if (!execute.endsWith(File.separator) && !t.getPath().startsWith("/")) {
            s = File.separator;
        } else {
            s = "";
        }
        File file = new File(execute + s + org.apache.commons.lang3.StringUtils.replace(t.getPath(), "/", File.separator));
        return file;
    }

    /**
     */
    public String getRootFolder() {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("webroot", WebAppPath.webRootPath());
        return ELHelper.execute(baseFolder, context);
    }

}
