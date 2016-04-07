/**
 * 
 */
package works.tonny.apps.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.llama.library.utils.Formatter;

/**
 * 自定义标签：数据处理函数
 * 
 * @author 祥栋
 */
public abstract class Functions {

	/**
	 * 截取固定长度的字符串，按汉字长度计算，两个英文字符为一个长度
	 * 
	 * @param text
	 * @param length
	 * @param ellipsis
	 * @return
	 */
	public static String substring(String text, int length, String ellipsis) {
		StringBuffer buffer = new StringBuffer();
		int l = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c < 256) {
				l += 1;
			} else {
				l += 2;
			}
			buffer.append(c);
			if (l >= length * 2) {
				break;
			}
		}
		if (text.length() > buffer.length())
			buffer.append(ellipsis);
		return buffer.toString();
	}

	/**
	 * @param text
	 * @param length
	 * @param ellipsis
	 * @return
	 */
	public static String substringBeforeLast(String text, String search) {
		return StringUtils.substringBeforeLast(text, search);
	}

	/**
	 * @param text
	 * @param length
	 * @param ellipsis
	 * @return
	 */
	public static String substringBefore(String text, String search) {
		return StringUtils.substringBefore(text, search);
	}

	/**
	 * @param text
	 * @param length
	 * @param ellipsis
	 * @return
	 */
	public static String substringAfterLast(String text, String search) {
		return StringUtils.substringAfterLast(text, search);
	}

	/**
	 * @param text
	 * @param length
	 * @param ellipsis
	 * @return
	 */
	public static String substringAfter(String text, String search) {
		return StringUtils.substringAfter(text, search);
	}

	/**
	 * url编码
	 * 
	 * @param text 待编码的文本
	 * @param encoding 编码
	 * @return url编码的文本
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String text, String encoding) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, encoding);
	}

	/**
	 * 将字节转换为友好的字符串
	 * 
	 * @param num
	 * @return
	 * @author tonny
	 */
	public static String formatByte(long num) {
		return Formatter.formatByte(num);
	}

}
