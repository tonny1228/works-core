/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-9-28 上午9:40:10
 * @author tonny
 */
package works.tonny.apps.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * Excel导入服务接口
 * </p>
 * 代码示例：
 * 
 * <pre>
 * ExcelReader reader = new POIExcelReader();
 * reader.open(new File(&quot;1.xlsx&quot;), 0);
 * int rows = reader.rows();
 * for (int i = 1; i &lt;= rows; i++) {
 * 	Map&lt;String, Object&gt; values = reader.read(i);
 * 	Integer i = (Integer) values.get(&quot;B&quot;);
 * }
 * reader.close();
 * </pre>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface ExcelReader {
	
	/**
	 * 打开一个excel表
	 * 
	 * @param excelFile excel文件
	 * @param sheetIndex excel表，从0开始
	 * @author tonny
	 */
	void open(File excelFile, int sheetIndex) throws IOException;

	/**
	 * 关闭打开的文件
	 * 
	 * @author tonny
	 */
	void close();

	/**
	 * 表内总共的数据条数
	 * 
	 * @return
	 * @author tonny
	 */
	int rows();

	/**
	 * 读取一行数据，行标从1开始
	 * 
	 * @param r 行标，从1开始，对应excel中的行标
	 * @return <p>
	 *         此行中的数据map集合，String为excel中的列标：A-Z AA-AZ BA-BZ……
	 *         </p>
	 *         <p>
	 *         值是根据excel中类型获取的值对象,小数:BigDecimal 日期Date,其它数值int
	 *         CELL_TYPE_STRING:String CELL_TYPE_BLANK:String
	 *         CELL_TYPE_BOOLEAN:boolean
	 *         </p>
	 * @author tonny
	 */
	Map<String, Object> read(int r);

	/**
	 * 读取某个单元格数据
	 * 
	 * @param r 行数
	 * @param c 列数
	 * @return
	 * @author tonny
	 */
	Object read(int r, int c);

	/**
	 * 读取某个单元格数据
	 * 
	 * @param r 行数
	 * @param c 列标
	 * @return
	 * @author tonny
	 */
	Object read(int r, String c);

}