/**
 * 
 */
package works.tonny.apps.support.rpc;

import java.util.Map;

/**
 * 
 * @author tonny
 * @date 2012-10-9
 * @version 1.0.0
 */
public interface HttpServiceCall {
	String get(String url);

	String post(String url, Map<String, String> params);
}
