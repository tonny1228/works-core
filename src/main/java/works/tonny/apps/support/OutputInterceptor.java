/**
 * 
 */
package works.tonny.apps.support;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.llama.library.utils.WebAppPath;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author 祥栋
 * @date 2013-3-1
 * @version 1.0.0
 */
public class OutputInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final String OUTPUT = "_output";

	/**
	 * 
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final Object pa = invocation.getInvocationContext().getParameters().get(OUTPUT);
		invocation.addPreResultListener(new PreResultListener() {
			public void beforeResult(ActionInvocation invocation, String resultCode) {
				if (pa != null && (invocation.getAction() instanceof CommonAction)) {
					CommonAction action = (CommonAction) invocation.getAction();
					String name = ((String[]) pa)[0];
					String forward = "/WEB-INF/" + name + "/" + action.getClass().getName().replaceAll("\\.", "/")
							+ "_" + invocation.getProxy().getMethod() + "_" + resultCode + ".jsp";
					File file = new File(WebAppPath.webRootPath(), forward);
					if (!file.exists()) {
						forward = "/WEB-INF/" + name + "/" + name + ".jsp";
						ActionContext actionContext = invocation.getInvocationContext();
						HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
						if ("xml".equals(name)) {
							request.setAttribute(name, toXML(request));
						}
						if ("json".equals(name)) {
							request.setAttribute(name, toJson(request));
						}
					}
					action.setForward(forward);
					invocation.setResultCode(OUTPUT);
				}
			}
		});
		invocation.invoke();
		return OUTPUT;
	}

	public String toXML(HttpServletRequest request) {
		XStream xStream = new XStream(new DomDriver("utf-8"));
		xStream.autodetectAnnotations(true);
		Enumeration<String> attr = request.getAttributeNames();
		StringBuffer buffer = new StringBuffer();
		while (attr.hasMoreElements()) {
			String name = attr.nextElement();
			if (!name.matches("[a-z]+")) {
				continue;
			}
			Object attribute = request.getAttribute(name);
			buffer.append(xStream.toXML(attribute));
		}
		return buffer.toString();
	}

	public String toJson(HttpServletRequest request) {
		XStream xStream = new XStream(new JettisonMappedXmlDriver());
		xStream.autodetectAnnotations(true);
		Enumeration<String> attr = request.getAttributeNames();
		StringBuffer buffer = new StringBuffer();
		while (attr.hasMoreElements()) {
			String name = attr.nextElement();
			if (!name.matches("[a-z]+")) {
				continue;
			}
			Object attribute = request.getAttribute(name);
			buffer.append(xStream.toXML(attribute));
		}
		return buffer.toString();
	}
}
