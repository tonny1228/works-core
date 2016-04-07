/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-9-28 下午7:02:53
 * @author tonny
 */
package works.tonny.apps.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class POIExcelWriter implements ExcelWriter {
	private Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();
	private HSSFWorkbook wb;
	private HSSFSheet currentSheet;

	/**
	 * @see works.tonny.apps.util.ExcelWriter#open()
	 */
	@Override
	public void open() {
		wb = new HSSFWorkbook();
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#addSheet(java.lang.String)
	 */
	@Override
	public void addSheet(String name) {
		currentSheet = wb.createSheet("数据");
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#useSheet(java.lang.String)
	 */
	@Override
	public void useSheet(String name) {
		currentSheet = wb.getSheet(name);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setColumnWidth(java.lang.String,
	 *      int)
	 */
	@Override
	public void setColumnWidth(String cell, int width) {
		currentSheet.setColumnWidth(ExcelUtil.columnIndex(cell), width);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setRowData(int,
	 *      java.lang.Object[])
	 */
	@Override
	public void setRowData(int row, Object... datas) {
		HSSFRow r = getRow(row);
		for (int i = 0; i < datas.length; i++) {
			insertCell(getCell(getRow(row), i), datas[i], null);
		}
	}

	protected HSSFRow getRow(int row) {
		HSSFRow r = null;
		if (row <= currentSheet.getLastRowNum() + 1) {
			r = currentSheet.getRow(row - 1);
		}
		if (r == null) {
			r = currentSheet.createRow(row - 1);
		}
		return r;
	}

	private void insertCell(HSSFCell cell, Object name, String format) {
		// HSSFCell cell = row.createCell(row.getPhysicalNumberOfCells());

		if (name instanceof String) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue((String) name);
			if (styles.get("string") == null) {
				HSSFCellStyle style = getDefaultStyle(cell.getSheet());
				styles.put("string", style);
			}
			HSSFCellStyle style = styles.get("string");
			cell.setCellStyle(style);
		}
		if (name instanceof Date) {
			cell.setCellValue((Date) name);
			if (styles.get("date") == null) {
				HSSFCellStyle style = getDefaultStyle(cell.getSheet());
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat(StringUtils.defaultIfEmpty(format, "m/d/yy")));
				styles.put("date", style);
			}
			HSSFCellStyle style = styles.get("date");
			cell.setCellStyle(style);
		}
		if (name instanceof Boolean) {
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue((Boolean) name);
			if (styles.get("bool") == null) {
				HSSFCellStyle style = getDefaultStyle(cell.getSheet());
				styles.put("bool", style);
			}
			HSSFCellStyle style = styles.get("bool");
			cell.setCellStyle(style);
		}
		if (name instanceof Double) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Double) name);
			if (styles.get("double") == null) {
				HSSFCellStyle style = getDefaultStyle(cell.getSheet());
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat(StringUtils.defaultIfEmpty(format, "#,##0.00")));
				styles.put("double", style);
			}
			HSSFCellStyle style = styles.get("double");
			cell.setCellStyle(style);
		}
		if (name instanceof BigDecimal) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(((BigDecimal) name).doubleValue());
			if (styles.get("double") == null) {
				HSSFCellStyle style = getDefaultStyle(cell.getSheet());
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat(StringUtils.defaultIfEmpty(format, "#,##0.00")));
				styles.put("double", style);
			}
			HSSFCellStyle style = styles.get("double");
			cell.setCellStyle(style);
		}
		if (name instanceof Integer) {
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue((Integer) name);
			if (styles.get("int") == null) {
				HSSFCellStyle style = getDefaultStyle(cell.getSheet());
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat(StringUtils.defaultIfEmpty(format, "0")));
				styles.put("int", style);
			}
			HSSFCellStyle style = styles.get("int");
			cell.setCellStyle(style);

		}
	}

	private HSSFCellStyle getDefaultStyle(HSSFSheet sheet) {
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		return style;
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void setData(int row, String cell, String data) {
		HSSFRow r = getRow(row);
		int columnIndex = ExcelUtil.columnIndex(cell);
		HSSFCell c = getCell(r, columnIndex);
		insertCell(c, data, null);
	}

	protected HSSFCell getCell(HSSFRow r, int columnIndex) {
		HSSFCell c = null;
		if (columnIndex <= r.getLastCellNum()) {
			c = r.getCell(columnIndex);
		}
		if (c == null) {
			c = r.createCell(columnIndex);
		}
		return c;
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      int)
	 */
	@Override
	public void setData(int row, String cell, int data) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data, null);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      long)
	 */
	@Override
	public void setData(int row, String cell, long data) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data, null);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	@Override
	public void setData(int row, String cell, Date data, String dateFormat) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data, dateFormat);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      boolean)
	 */
	@Override
	public void setData(int row, String cell, boolean data) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data, null);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      double, int)
	 */
	@Override
	public void setData(int row, String cell, double data, int scale) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data,
				"#,##0." + StringUtils.leftPad("", scale, '0'));
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      float, int)
	 */
	@Override
	public void setData(int row, String cell, float data, int scale) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data,
				"#,##0." + StringUtils.leftPad("", scale, '0'));

	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#setData(int, java.lang.String,
	 *      java.math.BigDecimal)
	 */
	@Override
	public void setData(int row, String cell, BigDecimal data) {
		insertCell(getCell(getRow(row), ExcelUtil.columnIndex(cell)), data, null);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#merge(int, java.lang.String, int,
	 *      java.lang.String)
	 */
	@Override
	public void merge(int fromRowNum, String fromCellNum, int toRowNum, String toCellNum) {
		currentSheet.addMergedRegion(new CellRangeAddress(fromRowNum, ExcelUtil.columnIndex(fromCellNum), toRowNum,
				ExcelUtil.columnIndex(toCellNum)));
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#writeTo(java.io.OutputStream)
	 */
	@Override
	public void writeTo(OutputStream stream) throws IOException {
		wb.write(stream);
	}

	/**
	 * @see works.tonny.apps.util.ExcelWriter#writeTo(java.io.File)
	 */
	@Override
	public void writeTo(File file) throws IOException {
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream out = new FileOutputStream(file);
		wb.write(out);
		out.flush();
		out.close();
	}

}
