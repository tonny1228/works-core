package works.tonny.apps.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.exception.ExceptionManager;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;


/**
 * servlet公共服务类，为servlet提供解析入口，公共服务等
 * 
 * @ClassName: DispatchServlet
 * @author 刘祥栋
 * @date 2010-12-6 上午10:08:17
 * 
 */
public class DispatchServlet extends HttpServlet {
	/**
	 * @Fields serialVersionUID :
	 */
	protected static final long serialVersionUID = 1L;

	/**
	 * error
	 */
	protected final static String ERROR_MESSAGE = "访问失败";

	/**
	 * 日志
	 */
	protected Logger log = LogFactory.getLogger(getClass());

	/**
	 * 方法参数
	 */
	protected static Class[] types = { HttpServletRequest.class, HttpServletResponse.class };

	/**
	 * 所有的方法缓存
	 */
	protected static Map<String, Method> methods = new HashMap<String, Method>();

	/**
	 * 所有的对象缓存
	 */
	protected static Map<String, Object> objects = new HashMap<String, Object>();

	protected static ExceptionManager exceptionManager;

	/*
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Enumeration<String> names = config.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String method = config.getInitParameter(name);
			Method m;
			try {
				Class clazz = objects.get(name).getClass();
				m = clazz.getMethod(method, types);
				methods.put(name, m);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	/**
	 * post
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param response 响应
	 * @throws ServletException 异常
	 * @throws IOException 异常
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * get
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param response 响应
	 * @throws ServletException 异常
	 * @throws IOException 异常
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * http 请求处理
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param response 响应
	 * @throws ServletException 异常
	 * @throws IOException 异常
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		// 中间件停止时，不提供服务
		log.debug(request.getMethod());
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				buildParameter(request);
			}
			// 初始化中间件地址和web服务名称

			String name = StringUtils.substringBeforeLast(request.getServletPath().substring(1), "/")
					.replace("/", "");
			log.debug(request.getQueryString());
			if (name == null) {
				throw new ServletException("没有指定方法名");
			}

			dispatchMethod(request, response, name);

		} catch (Exception e) {
			Exception other = exceptionManager.process(e, "log");
			response.setStatus(500);
			if (other != null) {
				writeError(request, response, e.getMessage());
			} else {
				writeError(request, response, ERROR_MESSAGE);
			}
		} catch (Throwable e) {
			response.setStatus(500);
			log.error(e.getMessage(), e);
			writeError(request, response, ERROR_MESSAGE);
		}
	}

	/**
	 * 参数处理
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @throws ServletException 异常
	 */
	public void buildParameter(HttpServletRequest request) throws ServletException {
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		List<UploadFile> files = new ArrayList<UploadFile>();
		request.setAttribute("_$parameters", parameters);
		request.setAttribute("_$files", files);

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 得到所有的表单域，它们目前都被当作FileItem
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// 判断是否是正常表单数据还是附件
				String name = item.getFieldName();
				log.info(name);
				if (item.isFormField()) { // 正常表单数据
					String value = item.getString();
					try {
						value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
						if (parameters.containsKey(name)) {
							parameters.get(name).add(value);
						} else {
							List<String> p = new ArrayList<String>();
							p.add(value);
							parameters.put(name, p);
						}
					} catch (UnsupportedEncodingException e) {
						throw new ServletException(e);
					}

				} else {
					// 如果item是文件上传表单域
					// 获得文件名及路径
					files.add(new UploadFile((DiskFileItem) item));
				}
			}
		} catch (FileUploadException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 根据名称查询方法
	 * 
	 * @Title: getMethod
	 * @Description: 根据方法名获得方法
	 * @param name 方法名
	 * @return Method 方法
	 * @throws NoSuchMethodException 没有方法排除异常
	 */
	protected Method getMethod(String name) throws NoSuchMethodException {
		Method method = (Method) methods.get(name);
		return (method);
	}

	/**
	 * 分发请求
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param response 响应
	 * @param name 方法名
	 * @throws Throwable 异常
	 */
	protected void dispatchMethod(HttpServletRequest request, HttpServletResponse response, String name)
			throws Throwable {
		Method method = null;
		try {
			method = getMethod(name);
		} catch (NoSuchMethodException e) {
			throw new ServletException(e);
		}
		Object[] args = { request, response };
		try {
			method.invoke(objects.get(name), args);
			if (ServletFileUpload.isMultipartContent(request)) {
				request.removeAttribute("_$parameters");
				request.removeAttribute("_$files");
			}
		} catch (IllegalAccessException e) {
			throw new ServletException("请求失败", e);
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			throw t;
		}
	}

	/**
	 * 获取整型参数
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param name 参数名
	 * @return 值
	 */
	protected static int getIntParameter(HttpServletRequest request, String name) {
		try {
			int a = NumberUtils.toInt(getParameter(request, name), 0);
			return a;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	/**
	 * 获取参数
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param name 参数名
	 * @return 值
	 */
	protected static String getParameter(HttpServletRequest request, String name) {
		if (ServletFileUpload.isMultipartContent(request)) {
			List<String> l = ((Map<String, List<String>>) request.getAttribute("_$parameters")).get(name);
			if (l == null) {
				request.getParameter(name);
			} else {
				return l.get(0);
			}
		}
		return request.getParameter(name);
	}

	/**
	 * 
	 * @Title: getFile
	 * @Description: 获得用户上传的文件
	 * @param request 请求
	 * @param name 文件名
	 * @return 文件
	 */
	protected static List<UploadFile> getFile(HttpServletRequest request, String name) {
		if (ServletFileUpload.isMultipartContent(request)) {
			List<UploadFile> l = ((Map<String, List<UploadFile>>) request.getAttribute("_$files")).get(name);
			return l;
		}
		return null;
	}

	/**
	 * 获取参数数组
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param name 参数名
	 * @return 值
	 */
	protected static String[] getParameterValues(HttpServletRequest request, String name) {
		if (ServletFileUpload.isMultipartContent(request)) {
			List<String> l = ((Map<String, List<String>>) request.getAttribute("_$parameters")).get(name);
			if (l == null) {
				return null;
			}

			String[] v = new String[l.size()];
			return l.toArray(v);
		}
		return request.getParameterValues(name);
	}

	/**
	 * 获取参数
	 * 
	 * @author 刘祥栋
	 * @Date 2010-2-10 上午10:38:35
	 * @param request 请求
	 * @param name 参数名
	 * @return 值
	 */
	protected static String getFiltedParameter(HttpServletRequest request, String name) {
		String value = getParameter(request, name);
		if (value != null && value.indexOf("'") >= 0) {
			value = value.replaceAll("'", "");
		}
		if (value != null && (value.indexOf("<") >= 0 || value.indexOf("<") >= 0)) {
			value = value.replaceAll("(<|>)", "");
		}
		return value;
	}

	/**
	 * 输出数据
	 * 
	 * @param request 请求
	 * @param response rseponse
	 * @param obj 数据
	 * @throws IOException 写异常
	 */
	protected void write(HttpServletRequest request, HttpServletResponse response, String obj) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.flush();
		out.close();
	}

	/**
	 * 输出错误信息
	 * 
	 * @param request 请求
	 * @param response 响应
	 * @param message 错误消息
	 * @throws IOException 异常
	 */
	private void writeError(HttpServletRequest request, HttpServletResponse response, String message)
			throws IOException {
		PrintWriter out = response.getWriter();
		out.print(message);
		out.flush();
		out.close();
	}

	/**
	 * debug request的参数
	 * 
	 * @param request http请求
	 * @throws IOException io异常
	 */
	protected void debugParameters(HttpServletRequest request) throws IOException {
		if (log.isDebugEnabled()) {
			if (request.getInputStream() != null) {
				log.debug(IOUtils.toString(request.getInputStream()));
			}
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				log.debug("参数[{0}]={1}", name, request.getParameter(name));
			}
		}
	}

	public void setObjects(Map<String, Object> objects) {
		DispatchServlet.objects = objects;
	}

}
