/**  
 * @Title: LayerUtil.java
 * @Package works.tonny.framework.util
 * @author Tonny
 * @date 2011-10-24 下午4:25:33
 */
package works.tonny.apps.user.util;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 树状结构的数据层创建
 * 
 * @ClassName: LayerUtil
 * @Description:
 * @author Tonny
 * @date 2011-10-24 下午4:25:33
 * @version 1.0
 */
public class LayerUtil {

	/**
	 * 创建新的数据层
	 * 
	 * @Title: createLayer
	 * @Description:
	 * @param @param parentLayer
	 * @param @param parentId
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String createLayer(String parentLayer, String parentId) {
		return BooleanUtils.toString(StringUtils.isNotEmpty(parentLayer), parentLayer + ",", "") + parentId;
	}
}
