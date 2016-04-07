/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-9-28 上午9:53:16
 * @author tonny
 */
package works.tonny.apps.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * excel写数据接口
 * </p>
 * 代理示例：
 * <pre>
 * ExcelWriter writer = new POIExcelWriter();
 * writer.open();
 * writer.addSheet(&quot;测试数据&quot;);
 * writer.addSheet(&quot;数据2&quot;);
 * writer.useSheet(&quot;测试数据&quot;);
 * writer.setRowData(1, &quot;表头1&quot;, &quot;列2&quot;, &quot;列3&quot;, &quot;列4&quot;);
 * for (int i = 2; i &lt; 200; i++) {
 * 	writer.setData(i, &quot;A&quot;, &quot;hello&quot;);
 * 	writer.setData(i, &quot;B&quot;, 2);
 * 	writer.setData(i, &quot;C&quot;, 45.67, 2);
 * 	writer.setData(i, &quot;D&quot;, new Date(), &quot;m/d/yy&quot;);
 * }
 * writer.setColumnWidth(&quot;B&quot;, 200);
 * writer.setColumnWidth(&quot;D&quot;, 1000);
 * writer.merge(0, &quot;B&quot;, 10, &quot;D&quot;);
 * writer.writeTo(new File(&quot;1.xlsx&quot;));
 * </pre>
 * 
 * @author tonny
 * @version 1.0.0
 */
public interface ExcelWriter {

	/**
	 * 创建一个excel文件
	 * 
	 * @author tonny
	 */
	void open();

	/**
	 * 创建一个sheet
	 * 
	 * @param name sheet的名字
	 * @author tonny
	 */
	void addSheet(String name);

	/**
	 * 使用指定的表，调用方法之后的操作都在此表上进行
	 * 
	 * @param name
	 * @author tonny
	 */
	void useSheet(String name);

	/**
	 * 设置列的宽度
	 * 
	 * @param cell 列标
	 * @param width 宽度值根据不同字体不同ppi都会不同。参考数据：Calibri字体11pt时一个英文字符应该设置为8
	 * @author tonny
	 */
	void setColumnWidth(String cell, int width);

	/**
	 * 设置一行数据
	 * 
	 * @param row 行标
	 * @param datas 从a开始所有列数据
	 * @author tonny
	 */
	void setRowData(int row, Object... datas);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, String data);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, int data);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, long data);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, Date data, String dateFormat);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, boolean data);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, double data, int scale);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, float data, int scale);

	/**
	 * 设置单元格数据
	 * 
	 * @param row 行标
	 * @param cell 列标
	 * @param data 数据
	 * @author tonny
	 */
	void setData(int row, String cell, BigDecimal data);

	/**
	 * 合并单元格
	 * 
	 * @param fromRowNum 起始行标，1开始
	 * @param fromCellNum 起始列标，A开始
	 * @param toRowNum 截止行标，1开始
	 * @param toCellNum 截止列标，A开始
	 * @author tonny
	 */
	void merge(int fromRowNum, String fromCellNum, int toRowNum, String toCellNum);

	/**
	 * 输出excel到流
	 * 
	 * @param stream 输出excel到流
	 * @author tonny
	 */
	void writeTo(OutputStream stream) throws IOException;

	/**
	 * 输出excel到流
	 * 
	 * @param file 文件
	 * @author tonny
	 */
	void writeTo(File file) throws IOException;

}
