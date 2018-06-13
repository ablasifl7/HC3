package ricoh.es.presentation.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;



public class Excel {
	
	
	private XSSFWorkbook workbook;
	//private XSSFSheet sheet;	
	private String pathDestin;
	private java.util.HashMap<String,XSSFSheet> hmSheet = new java.util.HashMap<String,XSSFSheet>(); 
	public Excel(String pathOrigin,String pathDestin, String sheetname) {
		super();
		this.pathDestin = pathDestin;		
		try {
			workbook = new XSSFWorkbook(
					OPCPackage.open(new FileInputStream(pathOrigin)));
			hmSheet.put(sheetname, workbook.getSheet(sheetname));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Excel(String pathOrigin,String pathDestin) {
		super();
		this.pathDestin = pathDestin;	
		try {
			workbook = new XSSFWorkbook(
					OPCPackage.open(new FileInputStream(pathOrigin)));
			
			String sheetname = workbook.getSheetAt(workbook.getActiveSheetIndex()).getSheetName();
			hmSheet.put(sheetname, workbook.getSheet(sheetname));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String readCell(int row,int col){
		try {
			XSSFRow row1 = workbook.getSheetAt(workbook.getActiveSheetIndex()).getRow(row);
			XSSFCell cell1 = row1.getCell(col);
			return cell1.toString();
		} catch (NullPointerException e) {
			return null;
		}
	}
	public String readCell(String sheetname,int row,int col){
		try {
			XSSFRow row1 = hmSheet.get(sheetname).getRow(row);
			XSSFCell cell1 = row1.getCell(col);
			return cell1.toString();
		} catch (NullPointerException e) {
			return null;
		}
	}
	public String readCell(int row,int col,String ifNull){
		try {
			String sheetname = workbook.getSheetAt(workbook.getActiveSheetIndex()).getSheetName();
			XSSFRow row1 = hmSheet.get(sheetname).getRow(row);
			XSSFCell cell1 = row1.getCell(col);
			return cell1.toString();
		} catch (NullPointerException e) {
			return ifNull;
		}
	}
	public String readCell(String sheetname,int row,int col,String ifNull){
		try {
			XSSFRow row1 = hmSheet.get(sheetname).getRow(row);
			XSSFCell cell1 = row1.getCell(col);
			return cell1.toString();
		} catch (NullPointerException e) {
			return ifNull;
		}
	}
	public void writeCell(int row,int col,Object value,String sheetname){
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		cell(row,col,value,sheet);
		write();
	}
	public void writeRow(int iniRow,int col,Object[] values,String sheetname){
		if(values == null){
			throw new IllegalArgumentException("values is null");
		}
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		int i = 0;
		for(int row = iniRow; row < iniRow + values.length; row++){
			cell(row,col,values[i++],sheet);
		}	
		write();
	}
	
	public void writeCol(int row,int iniCol,Object[] values,String sheetname){
		if(values == null){
			throw new IllegalArgumentException("values is null");
		}
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		int j = 0;
		for(int col = iniCol ; col < iniCol  + values.length; col++ ) {
			cell(row,col,values[j++],sheet);
		}	
		write();
	}
	public void writeCell(int iniRow,int iniCol,Object[][] values,String sheetname){	
		if(values == null){
			throw new IllegalArgumentException("values is null");
		}
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		int i = 0;
		for(int row = iniRow; row < iniRow + values.length; row++){
			int j = 0;
			for(int col = iniCol ; col < iniCol  + values[i].length; col++ ) {				
				cell(row,col,values[i][j++],sheet);
			}
			i++;
		}
		write();
	}
	
	public void createSheet(String sheetname) {
		if(sheetname == null){
			throw new IllegalArgumentException("sheetname is null");
		}
		hmSheet.put(sheetname, workbook.createSheet(sheetname));
	}
	public void setColorToCell(String[] value, int[] color, String range, String sheetname) {
		if(value == null || color == null || range == null){
			throw new IllegalArgumentException("value or color or range is/are null");
		}
		if(value.length != color.length) {
			throw new IllegalArgumentException("value.length <> color.length");
		}
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		XSSFSheetConditionalFormatting cf = sheet.getSheetConditionalFormatting();
		for(int i=0;i<value.length;i++) {
			XSSFConditionalFormattingRule cfrule = cf.createConditionalFormattingRule(
					org.apache.poi.hssf.record.CFRuleRecord.ComparisonOperator.EQUAL, "\"" + value[i] + "\"");
	        //fill_pattern.setFillBackgroundColor(IndexedColors.GREEN.index);
	        cfrule.createPatternFormatting().setFillBackgroundColor(IndexedColors.fromInt(color[i]).index);
	        //example: range = "C1:C100"
	        CellRangeAddress[] my_data_range = {CellRangeAddress.valueOf(range)};
	        cf.addConditionalFormatting(my_data_range,cfrule);
		}   

		
	}
	public void setColorToCell(String[] value, int[] color, String range, String sheetname,boolean equal) {
		if(value == null || color == null || range == null){
			throw new IllegalArgumentException("value or color or range is/are null");
		}
		if(value.length != color.length) {
			throw new IllegalArgumentException("value.length <> color.length");
		}
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		XSSFSheetConditionalFormatting cf = sheet.getSheetConditionalFormatting();
		for(int i=0;i<value.length;i++) {
			XSSFConditionalFormattingRule cfrule = null;
			if(equal) {
				cfrule = cf.createConditionalFormattingRule(
						org.apache.poi.hssf.record.CFRuleRecord.ComparisonOperator.EQUAL, "\"" + value[i] + "\"");
			}else {
				cfrule = cf.createConditionalFormattingRule(
						org.apache.poi.hssf.record.CFRuleRecord.ComparisonOperator.NOT_EQUAL, "\"" + value[i] + "\"");
			}
	        //fill_pattern.setFillBackgroundColor(IndexedColors.GREEN.index);
	        cfrule.createPatternFormatting().setFillBackgroundColor(IndexedColors.fromInt(color[i]).index);
	        //example: range = "C1:C100"
	        CellRangeAddress[] my_data_range = {CellRangeAddress.valueOf(range)};
	        cf.addConditionalFormatting(my_data_range,cfrule);
		}   

		
	}
	
	public void setColorToCell(String[] value, int[] color, String range, boolean backgroundColor,String sheetname) {
		if(value == null || color == null || range == null){
			throw new IllegalArgumentException("value or color or range is/are null");
		}
		if(value.length != color.length) {
			throw new IllegalArgumentException("value.length <> color.length");
		}
		XSSFSheet sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
		if(sheetname != null) {
			sheet = workbook.getSheet(sheetname);
		}
		XSSFSheetConditionalFormatting cf = sheet.getSheetConditionalFormatting();
		for(int i=0;i<value.length;i++) {
			XSSFConditionalFormattingRule cfrule = cf.createConditionalFormattingRule(
					org.apache.poi.hssf.record.CFRuleRecord.ComparisonOperator.EQUAL, "\"" + value[i] + "\"");
	        //fill_pattern.setFillBackgroundColor(IndexedColors.GREEN.index);
		    cfrule.createPatternFormatting().setFillBackgroundColor(IndexedColors.fromInt(color[i]).index);				
	        //example: range = "C1:C100"
	        CellRangeAddress[] my_data_range = {CellRangeAddress.valueOf(range)};
	        cf.addConditionalFormatting(my_data_range,cfrule);
		}   

		
	}
	
	
	//sheet.getLastRowNum()
	private void cell(int row,int col,Object value,XSSFSheet sheet){	
		Row rowSheet = sheet.getRow(row);
		if(rowSheet == null) {
			rowSheet = sheet.createRow(row);
		}
		Cell cell = rowSheet.createCell(col);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof java.util.Date) {
            cell.setCellValue((java.util.Date) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
	}
	private void write() {
		try {
			java.io.FileOutputStream os = new java.io.FileOutputStream(pathDestin);
			workbook.write(os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
