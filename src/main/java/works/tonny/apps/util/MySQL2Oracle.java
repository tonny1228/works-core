/**
 * 
 */
package works.tonny.apps.util;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author 祥栋
 * @date 2013-2-18
 * @version 1.0.0
 */
public class MySQL2Oracle {

	static void convert(String f, String prefix) throws IOException {
		File mysql = new File(f, prefix + ".mysql.sql");
		File oracle = new File(f, prefix + ".oracle.sql");
		if (oracle.exists()) {
			throw new IOException("oracle 文件已存在");
		}
		String content = FileUtils.readFileToString(mysql, "utf-8");
		content = content.replaceAll("varchar\\(", "varchar2(");
		content = content.replaceAll("\\smediumtext([^\\w])", " clob$1");
		content = content.replaceAll("\\stinytext([^\\w])", " varchar2(4000)$1");
		content = content.replaceAll("\\stext([^\\w])", " clob$1");
		content = content.replaceAll("default NULL", "");
		content = content.replaceAll("\\sCOMMENT\\s'[^']*'", "");
		content = content.replaceAll("int\\(", "number(");
		content = content.replaceAll("tinynumber\\(", "number(");
		content = content.replaceAll("decimal\\(", "number(");
		content = content.replaceAll("\\sdatetime([^\\w])", " date$1");
		content = content.replaceAll("\\)\\s*ENGINE=InnoDB.*;", ");");
		content = content.replaceAll("'\\d\\d\\d\\d\\-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d'",
				"to_date($0,'yyyy-mm-dd hh24:mi:ss')");
		content = content.replaceAll("'\\d\\d\\d\\d\\-\\d\\d-\\d\\d'", "to_date($0,'yyyy-mm-dd')");
		content = content.replaceAll("character set \\w+", "");
		content = content.replaceAll("KEY\\s\\w+.*\\n", "");
		// StringBuffer buffer = new StringBuffer();
		// Pattern pattern =
		// Pattern.compile("\\s*KEY\\s+\\w+\\s+\\(\\w+\\)\\s*,");
		// Matcher matcher = pattern.matcher(content);
		// String append = "";
		// while (matcher.find()) {
		// matcher.appendReplacement(buffer, "");
		// append +=
		// "create unique index idx_form_element_list on FR_FORM_ELEMENT (id, list);";
		// }
		// matcher.appendTail(buffer);
		FileUtils.writeStringToFile(oracle, content, "utf-8");
	}

	/*
	 * static void table() { Pattern pattern =
	 * Pattern.compile("create\\stable\\s(\\w+)\\s\\(\\)",
	 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE); Matcher matcher =
	 * pattern.matcher(content); String append = ""; while (matcher.find()) {
	 * matcher.appendReplacement(buffer, ""); append +=
	 * "create unique index idx_form_element_list on FR_FORM_ELEMENT (id, list);"
	 * ; } matcher.appendTail(buffer); }
	 */
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		convert("F:\\mine\\Cms\\v3.0\\source\\src\\main\\resources\\sql", "cms");
	}

}
