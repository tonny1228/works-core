/**
 *
 */
package works.tonny.apps.core.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.utils.DateUtils;

import org.llama.library.utils.ImageUtil;
import works.tonny.apps.core.AttachService;
import works.tonny.apps.core.Attachment;
import works.tonny.apps.support.ServiceManager;

/**
 * @author 祥栋
 * @version 1.0.0
 * @date 2012-11-23
 */
@WebServlet(name = "filedown", urlPatterns = "/filedown/*")
public class FileDown extends HttpServlet {
    private Logger logger = LogFactory.getLogger(FileDown.class);
    private static AttachService attachService = ServiceManager.lookup("attachService");

    /**
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        Enumeration<String> names = request.getHeaderNames();
        if (request.getHeader("If-Modified-Since") != null) {
            response.setStatus(304);
            return;
        }
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Attachment attachment = null;
        if (StringUtils.isNotEmpty(id)) {
            attachment = attachService.get(id);
            if (StringUtils.isEmpty(attachment.getMimetype()) || !attachment.getMimetype().startsWith("image")) {
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(attachment.getFilename().getBytes("GBK"), "ISO8859_1"));
            }
            response.setContentType(attachment.getMimetype() + "; charset=GBK");

        } else {
            if (StringUtils.isNotEmpty(name)) {
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(name.getBytes("GBK"), "ISO8859_1"));
            }
            response.setContentType("application/octet-stream; charset=GBK");
        }
        String url = request.getRequestURI();
        FileInputStream fileInputStream = null;
        try {
            File file = new File(attachService.getRootFolder(), StringUtils.substringAfter(url, "/filedown/"));
            if (!file.exists()) {
                logger.warn("文件不存在:{0}", file);
                request.setAttribute("javax.servlet.error.message", url);
                response.sendError(404);
                return;
            }
            String width = request.getParameter("w");
            if (attachment != null && "jpg".equalsIgnoreCase(attachment.getFileext()) && StringUtils.isNotEmpty(width)
                    && NumberUtils.isNumber(width)) {
                File sfile = new File(StringUtils.substringBeforeLast(file.getAbsolutePath(), ".") + "_" + width + "."
                        + StringUtils.substringAfterLast(file.getAbsolutePath(), "."));
                if (sfile.exists()) {
                    file = sfile;
                } else {
                    ImageUtil.scale(file, sfile, NumberUtils.toInt(width));
                    file = sfile;
                }
            }
            String date = DateUtils.toString(new Date(file.lastModified()), "EEE, dd MMM yyyy hh:mm:ss z",
                    Locale.ENGLISH) + " GMT";
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Last-Modified", date);
            response.setHeader("ETag", file.getName());
            response.setHeader("Date", date);
            fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (fileInputStream != null)
                fileInputStream.close();
        }

    }
}
