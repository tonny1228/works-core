/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-9-28 下午7:07:46
 * @author tonny
 */
package works.tonny.apps.util;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
class ExcelUtil {
	static String columnIndex(int i) {
		StringBuffer buffer = new StringBuffer();
		int pow = (int) Math.floor(i / 26);
		if (pow > 0) {
			buffer.append((char) (64 + pow));
		}
		int y = i % 26;
		buffer.append((char) (64 + y + 1));
		return buffer.toString();
	}

	static int columnIndex(String index) {
		int idx = 0;
		for (int i = 0; i < index.length(); i++) {
			idx += (index.charAt(i) - 64) * Math.pow(26, index.length() - i - 1);
		}
		return idx - 1;
	}
}
