package works.tonny.apps.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户消息发送器，向异步线程发送数据
 * 
 * @author tonny
 *
 */
public class UserMessageSender implements Runnable {
	private static final Map<String, UserMessageSender> SENDERS = new HashMap<String, UserMessageSender>();
	HttpServletResponse response;
	HttpServletRequest request;
	AsyncContext asyncContext;
	private boolean abort = false;
	private Executor executor;
	private String username;

	/**
	 * 返回用户注册消息发送器，用户没注册过或过期返回空
	 * 
	 * @param username
	 * @return
	 */
	public static UserMessageSender getInstance(String username) {
		return SENDERS.get(username);
	}

	UserMessageSender(String username, AsyncContext asyncContext,
			HttpServletRequest request, HttpServletResponse response) {
		SENDERS.put(username, this);
		this.username = username;
		this.asyncContext = asyncContext;
		this.response = response;
		this.request = request;
	}

	/**
	 * 执行发送消息
	 * 
	 * @param executor
	 */
	public synchronized void execute(Executor executor) {
		this.executor = executor;
		notify();
	}

	/**
	 * 取消发送消息
	 */
	synchronized void abort() {
		this.abort = true;
		notify();
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (SENDERS.containsValue(this))
			SENDERS.remove(username);
		if (abort) {
			return;
		}
		if (executor != null)
			executor.execute(request, response);
		asyncContext.complete();
	}

	/**
	 * 执行器
	 * 
	 * @author tonny
	 *
	 */
	public static interface Executor {
		void execute(HttpServletRequest request, HttpServletResponse response);
	}

	/**
	 * 写消息执行器
	 * 
	 * @author tonny
	 *
	 */
	public static class MessageWriteExecutor implements Executor {

		private String message;

		public MessageWriteExecutor(String message) {
			super();
			this.message = message;
		}

		@Override
		public void execute(HttpServletRequest request,
				HttpServletResponse response) {
			try {
				response.getWriter().print(message);
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转向某页面
	 * 
	 * @author tonny
	 *
	 */
	public static class RedirectExecutor implements Executor {
		private String url;
		private Map<String, Object> params;

		public RedirectExecutor(String url) {
			super();
			this.url = url;
		}

		public void addRequestAttribute(String name, Object value) {
			if (params == null) {
				params = new HashMap<String, Object>();
			}
			params.put(name, value);
		}

		@Override
		public void execute(HttpServletRequest request,
				HttpServletResponse response) {
			try {
				Set<String> keySet = params.keySet();
				for (String name : keySet) {
					request.setAttribute(name, params.get(name));
				}
				request.getRequestDispatcher(url).forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
