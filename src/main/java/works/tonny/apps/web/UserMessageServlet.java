package works.tonny.apps.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import works.tonny.apps.user.LoginedUser;
import works.tonny.apps.user.web.LoginAction;

/**
 * 用户消息实时通知
 * 
 * @author tonny
 *
 */
@WebServlet(urlPatterns = "/servlet/UserMessageAsyncServlet", asyncSupported = true)
public class UserMessageServlet extends HttpServlet {

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		final PrintWriter out = response.getWriter();
		final AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(NumberUtils.toInt(request.getParameter("timeout"), 120) * 1000);
		LoginedUser user = (LoginedUser) request.getSession().getAttribute(LoginAction.USER_SESSION);
		if (user == null) {
			// 要求用户登录
			response.sendError(401);
			return;
		}
		final UserMessageSender run = new UserMessageSender(user.getUser().getUsername(), asyncContext, request,
				response);
		UserMessageASyncListener listener = new UserMessageASyncListener(run);
		asyncContext.addListener(listener);
		asyncContext.start(run);
		out.flush();
	}
}

class UserMessageASyncListener implements AsyncListener {
	private Log log = LogFactory.getLog(getClass());

	UserMessageSender sender;

	/**
	 * @param run
	 */
	public UserMessageASyncListener(UserMessageSender run) {
		this.sender = run;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.AsyncListener#onComplete(javax.servlet.AsyncEvent)
	 */
	@Override
	public void onComplete(AsyncEvent event) throws IOException {
		log.debug(sender + "complete");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.AsyncListener#onTimeout(javax.servlet.AsyncEvent)
	 */
	@Override
	public void onTimeout(AsyncEvent event) throws IOException {
		sender.abort();
		log.debug(sender + "onTimeout");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.AsyncListener#onError(javax.servlet.AsyncEvent)
	 */
	@Override
	public void onError(AsyncEvent event) throws IOException {
		sender.abort();
		log.debug(sender + "onError");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.AsyncListener#onStartAsync(javax.servlet.AsyncEvent)
	 */
	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {
		log.debug(sender + "onStartAsync");
	}

}
