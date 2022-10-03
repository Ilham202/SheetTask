package com.task.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.task.model.CompareModel;

@Service
public class ExcelExportservice {
	private XSSFWorkbook xssfworkbook;
	private XSSFSheet xssfsheet;
	private List<CompareModel> list;

	private void headerLine() {
		xssfsheet = xssfworkbook.createSheet("empdata");
		Row row = xssfsheet.createRow(0);
		// Define header cell style
		CellStyle headerCellStyle = xssfworkbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Creating header cells
		Cell cell = row.createCell(0);
		cell.setCellValue("EmpId");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(1);
		cell.setCellValue("EmpName");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(2);
		cell.setCellValue("FromDate");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(3);
		cell.setCellValue("ToDate");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(4);
		cell.setCellValue("TsProjectBillableHours");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(5);
		cell.setCellValue("GrandTotalFromSummary");
		cell.setCellStyle(headerCellStyle);

		cell = row.createCell(6);
		cell.setCellValue("Difference");
		cell.setCellStyle(headerCellStyle);
	}
	
	
	private void wrireDataLines() {
		
		CellStyle style = xssfworkbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		int rowcount=1;
		for (CompareModel user : list) {
			Row dataRow = xssfsheet.createRow(rowcount++);
			
			if (user.getDifference() != 0) {
				
				Cell datacell = dataRow.createCell(0);
				datacell.setCellValue(user.getEmpId());
				datacell.setCellStyle(style);

				datacell = dataRow.createCell(1);
				datacell.setCellValue(user.getEmpName());
				datacell.setCellStyle(style);

				datacell = dataRow.createCell(2);
				datacell.setCellValue(user.getFromDate());
				datacell.setCellStyle(style);

				datacell = dataRow.createCell(3);
				datacell.setCellValue(user.getToDate());
				datacell.setCellStyle(style);

				datacell = dataRow.createCell(4);
				datacell.setCellValue(user.getTsProjectBillableHours());
				datacell.setCellStyle(style);

				datacell = dataRow.createCell(5);
				datacell.setCellValue(user.getGrandTotalFromSummary());
				datacell.setCellStyle(style);

				datacell = dataRow.createCell(6);
				datacell.setCellValue(user.getDifference());
				datacell.setCellStyle(style);

			} else {
				Cell datacell = dataRow.createCell(0);
				datacell.setCellValue(user.getEmpId());

				datacell = dataRow.createCell(1);
				datacell.setCellValue(user.getEmpName());

				datacell = dataRow.createCell(2);
				datacell.setCellValue(user.getFromDate());

				datacell = dataRow.createCell(3);
				datacell.setCellValue(user.getToDate());

				datacell = dataRow.createCell(4);
				datacell.setCellValue(user.getTsProjectBillableHours());

				datacell = dataRow.createCell(5);
				datacell.setCellValue(user.getGrandTotalFromSummary());

				datacell = dataRow.createCell(6);
				datacell.setCellValue(user.getDifference());
			}
		}
		
		xssfsheet.autoSizeColumn(0);
		xssfsheet.autoSizeColumn(1);
		xssfsheet.autoSizeColumn(2);
		xssfsheet.autoSizeColumn(3);
		xssfsheet.autoSizeColumn(4);
		xssfsheet.autoSizeColumn(5);
		xssfsheet.autoSizeColumn(6);
		
	}

	public ExcelExportservice(List<CompareModel> list) {
		this.list=list;
		xssfworkbook = new XSSFWorkbook();	
	}
	
	public void exportData(HttpServletResponse response) throws IOException {
		
		headerLine();
		wrireDataLines();
		ServletOutputStream outputStream=response.getOutputStream();
		xssfworkbook.write(outputStream);
		xssfworkbook.close();
		outputStream.close();
	}
	

}
