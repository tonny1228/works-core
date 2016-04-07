/**
 * Copied from the package:'works.tonny.apps.support'. Please don't modify the code at any time,otherwise it will be overwritten!
 */

package works.tonny.apps.support.ui;


import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.FastByteArrayOutputStream;
import org.llama.library.utils.ClassUtils;
import works.tonny.apps.web.CommonTag;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by tonny on 2016/3/1.
 */
public class CustomDataListTag extends CommonTag {
    private String contentLayout;
    private String headerLayout;
    private String footerLayout;
    private String dataName;
    private List data;
    private int index = -1;
    private String var;
    private String onItemInit;
    private boolean executed;

    @Override
    public int doStartTag() throws JspException {
        index = -1;
        executed = false;
        ServletRequest request = pageContext.getRequest();
        ServletResponse response = pageContext.getResponse();
        this.data = (List) pageContext.getAttribute(dataName, PageContext.REQUEST_SCOPE);
        if (headerLayout != null) {
            includePage(request, response, headerLayout);
        }

        return EVAL_PAGE;
    }

    private void includePage(ServletRequest request, ServletResponse response, String page) throws JspException {
        CustomDataListTag.PageResponse res = new CustomDataListTag.PageResponse((HttpServletResponse) response);
        RequestDispatcher rd = request.getRequestDispatcher(page);
        try {
            rd.include(request, res);
            res.getContent().writeTo(pageContext.getOut(), null);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }

    private void includeContent(ServletRequest request, ServletResponse response) throws JspException {
        for (int i = 0; i < data.size(); i++) {
            request.setAttribute(StringUtils.defaultString(var, "_data"), data.get(i));
            initOneData(request, response, i);
            includePage(request, response, contentLayout);
        }
    }

    private void initOneData(ServletRequest request, ServletResponse response, int i) {
        if (onItemInit == null) {
            return;
        }
        OnItemInit o = ClassUtils.newInstance(onItemInit);
        o.init(request, response, data, i);
    }

    @Override
    public void doInitBody() throws JspException {
        super.doInitBody();
        index++;
        ServletRequest request = pageContext.getRequest();
        request.setAttribute(StringUtils.defaultString(var, "_data"), data.get(index));
//        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
//        initOneData(request, response, index);
//        if (contentLayout != null) {
//            PageResponse pageResponse = new PageResponse(response);
//            RequestDispatcher rd = request.getRequestDispatcher(contentLayout);
//            try {
//                rd.include(request, pageResponse);
//            } catch (Exception e) {
//                throw new JspException(e);
//            }
//            try {
////                pageResponse.getContent().writeTo(response.getWriter(), System.getProperty("file.encoding"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


    @Override
    public int doAfterBody() throws JspException {
        ServletRequest request = pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
//        try {
//            response.flushBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bodyContent.
//        if (contentLayout != null) {
//            PageResponse pageResponse = new PageResponse(response);
//            RequestDispatcher rd = request.getRequestDispatcher(contentLayout);
//            try {
//                rd.include(request, pageResponse);
////                bodyContent.getEnclosingWriter().flush();
////                bodyContent.append("111");
//                pageResponse.getContent().writeTo(pageContext.getOut(), null);
////                bodyContent.getEnclosingWriter().flush();
//
//            } catch (Exception e) {
//                throw new JspException(e);
//            }
//        }
        index++;
        if (index < data.size()) {
            request.setAttribute(StringUtils.defaultString(var, "_data"), data.get(index));
            initOneData(request, response, index);
            return EVAL_BODY_AGAIN;
        } else return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        if (contentLayout != null) {
            includeContent(pageContext.getRequest(), pageContext.getResponse());
        }
        return super.doEndTag();
    }

    public String getContentLayout() {
        return contentLayout;
    }

    public void setContentLayout(String contentLayout) {
        this.contentLayout = contentLayout;
    }

    public String getHeaderLayout() {
        return headerLayout;
    }

    public void setHeaderLayout(String headerLayout) {
        this.headerLayout = headerLayout;
    }

    public String getFooterLayout() {
        return footerLayout;
    }

    public void setFooterLayout(String footerLayout) {
        this.footerLayout = footerLayout;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getOnItemInit() {
        return onItemInit;
    }

    public void setOnItemInit(String onItemInit) {
        this.onItemInit = onItemInit;
    }

    /**
     * 初始化数据
     */
    public static interface OnItemInit {
        void init(ServletRequest request, ServletResponse response, List data, int index);
    }

    static final class PageOutputStream extends ServletOutputStream {

        private FastByteArrayOutputStream buffer;


        public PageOutputStream() {
            buffer = new FastByteArrayOutputStream();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }


        /**
         * Return all data that has been written to this OutputStream.
         */
        public FastByteArrayOutputStream getBuffer() throws IOException {
            flush();

            return buffer;
        }

        public void close() throws IOException {
            buffer.close();
        }

        public void flush() throws IOException {
            buffer.flush();
        }

        public void write(byte[] b, int o, int l) throws IOException {
            buffer.write(b, o, l);
        }

        public void write(int i) throws IOException {
            buffer.write(i);
        }

        public void write(byte[] b) throws IOException {
            buffer.write(b);
        }
    }

    static final class PageResponse extends HttpServletResponseWrapper {

        protected PrintWriter pagePrintWriter;
        protected ServletOutputStream outputStream;
        private PageOutputStream pageOutputStream = null;


        /**
         * Create PageResponse wrapped around an existing HttpServletResponse.
         */
        public PageResponse(HttpServletResponse response) {
            super(response);
        }


        /**
         * Return the content buffered inside the {@link PageOutputStream}.
         *
         * @return
         * @throws IOException
         */
        public FastByteArrayOutputStream getContent() throws IOException {
            //if we are using a writer, we need to flush the
            //data to the underlying outputstream.
            //most containers do this - but it seems Jetty 4.0.5 doesn't
            if (pagePrintWriter != null) {
                pagePrintWriter.flush();
            }

            return ((PageOutputStream) getOutputStream()).getBuffer();
        }

        /**
         * Return instance of {@link PageOutputStream}
         * allowing all data written to stream to be stored in temporary buffer.
         */
        public ServletOutputStream getOutputStream() throws IOException {
            if (pageOutputStream == null) {
                pageOutputStream = new PageOutputStream();
            }

            return pageOutputStream;
        }

        /**
         * Return PrintWriter wrapper around PageOutputStream.
         */
        public PrintWriter getWriter() throws IOException {
            if (pagePrintWriter == null) {
                pagePrintWriter = new PrintWriter(new OutputStreamWriter(getOutputStream(), getCharacterEncoding()));
            }

            return pagePrintWriter;
        }
    }
}