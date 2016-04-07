/**
 * 
 */
package works.tonny.apps.support;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.llama.library.cache.Cache;

import works.tonny.apps.exception.WebException;
import works.tonny.apps.web.CommonTag;

/**
 * @author 祥栋
 */
public class CacheTag extends CommonTag {

	Cache cache;

	private int seconds;

	private String url;

	private String content;

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		content = null;
		if (cache == null)
			cache = ServiceManager.lookup(Cache.class);
		if (cache == null) {
			return EVAL_PAGE;
		}
		url = (String) pageContext.getRequest().getAttribute("_cache.key");
		String fromCache = cache.getFromCache(url);

		if (fromCache == null) {
			log.info("无缓存");
			return EVAL_PAGE;
		} else {
			log.info("有缓存");
			content = fromCache;
			return SKIP_BODY;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.zxtx.apps.web.CommonTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			if (content == null) {
				content = getBodyContent().getString();
				if (cache != null) {
					try {
						if (seconds > 0)
							cache.putInCache(url, content, seconds);
						else {
							cache.putInCache(url, content);
						}
					} catch (Exception e) {
						log.error(e);
					}
				}
			}
			pageContext.getOut().write(content);
		} catch (IOException e) {
			throw new WebException(e);
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the seconds
	 */
	public int getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
