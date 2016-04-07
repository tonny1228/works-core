/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-9-28 下午4:31:58
 * @author tonny
 */
package works.tonny.apps.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class POIExcelReader implements ExcelReader {

	private HSSFSheet sheet;
	private FileInputStream is;

	/**
	 * @throws IOException
	 * @see works.tonny.apps.util.ExcelReader#open(java.io.File, int)
	 */
	@Override
	public void open(File excelFile, int sheetIndex) throws IOException {
		is = new FileInputStream(excelFile);
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		sheet = workbook.getSheetAt(sheetIndex);
	}

	/**
	 * @see works.tonny.apps.util.ExcelReader#close()
	 */
	@Override
	public void close() {
		IOUtils.closeQuietly(is);
	}

	/**
	 * @see works.tonny.apps.util.ExcelReader#rows()
	 */
	@Override
	public int rows() {
		return sheet.getPhysicalNumberOfRows();
	}

	/**
	 * @see works.tonny.apps.util.ExcelReader#read(int)
	 */
	@Override
	public Map<String, Object> read(int r) {
		Map<String, Object> list = new LinkedHashMap<String, Object>();
		HSSFRow row = sheet.getRow(r);
		if (row == null)
			return list;

		for (int i = 0; i < row.getLastCellNum(); i++) {
			HSSFCell cell = row.getCell(i);
			if (cell == null) {
				list.put(ExcelUtil.columnIndex(i), null);
			} else {
				list.put(ExcelUtil.columnIndex(i), cell2object(cell));
			}
		}
		return list;
	}

	/**
	 * @see works.tonny.apps.util.ExcelReader#read(int, int)
	 */
	@Override
	public Object read(int r, int c) {
		HSSFRow row = sheet.getRow(r);
		if (row == null)
			return null;
		HSSFCell cell = row.getCell(c);
		if (cell == null) {
			return null;
		}
		return cell2object(cell);
	}

	/**
	 * @see works.tonny.apps.util.ExcelReader#read(int, java.lang.String)
	 */
	@Override
	public Object read(int r, String c) {
		return read(r, ExcelUtil.columnIndex(c));
	}

	/**
	 * @param cell
	 * @return
	 */
	private Object cell2object(HSSFCell cell) {
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			HSSFCellStyle cellStyle = cell.getCellStyle();
			String dataFormatString = cellStyle.getDataFormatString();
			if (dataFormatString.indexOf(".") >= 0) {
				String data = new DecimalFormat(dataFormatString).format(cell.getNumericCellValue()).replace(",", "");
				if (data == null) {
					return new BigDecimal(0);
				}
				return new BigDecimal(data);
			} else if (dataFormatString.indexOf("yy") >= 0) {
				return (Date) cell.getDateCellValue();
			} else {
				return (int) cell.getNumericCellValue();
			}
		}
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
			return cell.getStringCellValue();
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK)
			return cell.getStringCellValue();
		if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
			return cell.getBooleanCellValue();
		return null;
	}
}
