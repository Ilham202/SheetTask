package com.task.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.task.model.CompareModel;
import com.task.model.DataModel;
import com.task.model.ESAModel;
import com.task.model.SummeryModel;

@Service
public class ReadService {

	List<DataModel> datalist = new ArrayList<DataModel>();
	List<SummeryModel> summarylist = new ArrayList<SummeryModel>();
	List<ESAModel> emplist = new ArrayList<ESAModel>();
	List<CompareModel> comparelist = new ArrayList<CompareModel>();

	public List<JSONObject> readExcelData(MultipartFile file) {
		List<JSONObject> datalist = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(1);

			// Read and print in Console
			/*
			 * for(int i=0; i<sheet.getPhysicalNumberOfRows();i++) { XSSFRow row =
			 * sheet.getRow(i); for(int j=0;j<row.getPhysicalNumberOfCells();j++) {
			 * DataFormatter dataformatter = new DataFormatter();
			 * 
			 * System.out.print(dataformatter.formatCellValue(row.getCell(j))+" "); }
			 * System.out.println(""); }
			 */

			// Read and Convert to Json

			XSSFRow header = sheet.getRow(0);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				XSSFRow row = sheet.getRow(i);
				JSONObject obj = new JSONObject();
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					DataFormatter dataformatter = new DataFormatter();
					String columnName = header.getCell(j).toString();
					String columnValue = dataformatter.formatCellValue(row.getCell(j)).toString();
					obj.put(columnName, columnValue);
				}
				datalist.add(obj);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datalist;

	}



	public List<DataModel> readExcelDataModel(MultipartFile excel) throws IOException {
		List<DataModel> datalist1 = new ArrayList<DataModel>();
		XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(1);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows() - 2; i++) {
			DataModel datamodel = new DataModel();
			XSSFRow row = worksheet.getRow(i);
			datamodel.setId((int) row.getCell(0).getNumericCellValue());
			datamodel.setName(row.getCell(1).getStringCellValue());
			datamodel.setEmpId((int) row.getCell(2).getNumericCellValue());
			datamodel.setWeekStartingDate(row.getCell(3).getStringCellValue());
			datamodel.setWeekEndingDate(row.getCell(4).getStringCellValue());
			DateFormat df = new SimpleDateFormat("dd-MM-yy");
			java.util.Date date = row.getCell(5).getDateCellValue();
			datamodel.setWorkDate(df.format(date));
			datamodel.setUnits((int) row.getCell(6).getNumericCellValue());
			datamodel.setStatus(row.getCell(7).getStringCellValue());
			datamodel.setRate((double) row.getCell(8).getNumericCellValue());
			datamodel.setProjectId((int) row.getCell(9).getNumericCellValue());
			datamodel.setProjectName(row.getCell(10).getStringCellValue());
			datamodel.setPmId((int) row.getCell(11).getNumericCellValue());
			datamodel.setPmName(row.getCell(12).getStringCellValue());
			datalist1.add(datamodel);

		}
		datalist.clear();
		datalist.addAll(datalist1);
		workbook.close();
		return datalist;
	}

	public List<SummeryModel> readSheetModelData(MultipartFile excel) throws IOException {
		List<SummeryModel> summarylist1 =new ArrayList<SummeryModel>();
		XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		
		for (int i = 4; i <=worksheet.getPhysicalNumberOfRows(); i++) {
			SummeryModel sheetmodel = new SummeryModel();
			XSSFRow row = worksheet.getRow(i);
			sheetmodel.setEmpId((int) row.getCell(0).getNumericCellValue());
			sheetmodel.setName(row.getCell(1).getStringCellValue());
			sheetmodel.setProjectId((int) row.getCell(2).getNumericCellValue());
			sheetmodel.setProjectName(row.getCell(3).getStringCellValue());
			sheetmodel.setGrandTotal((int) row.getCell(9).getNumericCellValue());
			summarylist1.add(sheetmodel);

		}
		summarylist.clear();
		summarylist.addAll(summarylist1);
		workbook.close();
		return summarylist;
	}

	public List<ESAModel> readEsaDataModel(MultipartFile excel) throws IOException {
		List<ESAModel> emplist1 = new ArrayList<ESAModel>() ;
		XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			ESAModel empmodel = new ESAModel();
			XSSFRow row = worksheet.getRow(i);
			if (row.getCell(1).getCellType().toString() != ("BLANK")) {
				String name = row.getCell(1).getStringCellValue();
				String[] arrname = name.split("- ", 2);
				empmodel.setEmpId(Integer.parseInt(arrname[1]));
				empmodel.setEmpName(arrname[0]);
				empmodel.setDateRange(row.getCell(2).getStringCellValue());
				empmodel.setProjectRole(row.getCell(3).getStringCellValue());
				empmodel.setBillFlag(row.getCell(4).getStringCellValue());
				empmodel.setBillingLocation(row.getCell(5).getStringCellValue());
				empmodel.setOnOrOff(row.getCell(6).getStringCellValue());
				empmodel.setTsProjectHours((int) row.getCell(7).getNumericCellValue());
				empmodel.setTsProjectBillableHours((int) row.getCell(8).getNumericCellValue());
				empmodel.setBillableQuantity((int) row.getCell(9).getNumericCellValue());
				empmodel.setRegularRate((int) row.getCell(10).getNumericCellValue());
				empmodel.setOverTimeQuantity((int) row.getCell(11).getNumericCellValue());
				empmodel.setOverTimeRate((int) row.getCell(12).getNumericCellValue());
				empmodel.setBillableAmount((int) row.getCell(13).getNumericCellValue());
				empmodel.setOverTimeAmount((int) row.getCell(14).getNumericCellValue());
				empmodel.setGrandTotalAmount((int) row.getCell(15).getNumericCellValue());
				empmodel.setInvoiceSplitId((int) row.getCell(16).getNumericCellValue());
				empmodel.setReason(row.getCell(17).getStringCellValue());
				empmodel.setDeferBilling(row.getCell(18).getStringCellValue());
				empmodel.setDeferDate(row.getCell(19).getStringCellValue());
				empmodel.setCorrectionAtStore(row.getCell(20).getStringCellValue());
				emplist1.add(empmodel);

			} 
		else {
				break;
			}
		}
		emplist.clear();
		emplist.addAll(emplist1);
		workbook.close();
		return emplist;
	}

	public List<CompareModel> compareSheets() throws IOException {
		// TODO Auto-generated method stub
		List<CompareModel> comparelist1= new ArrayList<CompareModel>();
		for (int i = 0; i < summarylist.size(); i++) {
			CompareModel cmpmodel = new CompareModel();
			cmpmodel.setEmpId(summarylist.get(i).getEmpId());
			cmpmodel.setEmpName(summarylist.get(i).getName());
			cmpmodel.setTsProjectBillableHours(emplist.get(i).getTsProjectBillableHours());
			cmpmodel.setGrandTotalFromSummary(summarylist.get(i).getGrandTotal());
			cmpmodel.setDifference((emplist.get(i).getTsProjectBillableHours() - (summarylist.get(i).getGrandTotal())));
			String[] date = emplist.get(i).getDateRange().split("-", 7);
			cmpmodel.setFromDate(date[0] + "-".concat(date[1]) + "-".concat(date[2]));
			cmpmodel.setToDate(date[3] + "-".concat(date[4]) + "-".concat(date[5]));
			comparelist1.add(cmpmodel);

		}
		comparelist.clear();
		comparelist.addAll(comparelist1);
		createExcelSheet();
		return comparelist;
	}

	public boolean createExcelSheet() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet sheet = workbook.createSheet("resultSheet");// creating a blank sheet
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			XSSFCellStyle headerstyle = workbook.createCellStyle();
			headerstyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			headerstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			writeHeaderLine(sheet,headerstyle);
			int rownum = 1;
			for (CompareModel user : comparelist) {
				Row row = sheet.createRow(rownum++);
				createList(user, row, style);

			}
			 DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		     String currentDateTime = dateFormatter.format(new Date());
		     String fileName = "newfile"+currentDateTime+".xlsx";

			FileOutputStream out = new FileOutputStream(new File("NewFile.xlsx"),true); // file name with path
			workbook.write(out);
			out.close();
			workbook.close();
			return true;
			
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	private void writeHeaderLine(XSSFSheet sheet, XSSFCellStyle headerstyle) {
		Row headrow = sheet.createRow(0);
		Cell cell = headrow.createCell(0);

		cell.setCellValue("EmpId");
		cell.setCellStyle(headerstyle);

		cell = headrow.createCell(1);
		cell.setCellStyle(headerstyle);
		cell.setCellValue("EmpName");

		cell = headrow.createCell(2);
		cell.setCellStyle(headerstyle);
		cell.setCellValue("FromDate");

		cell = headrow.createCell(3);
		cell.setCellStyle(headerstyle);
		cell.setCellValue("ToDate");

		cell = headrow.createCell(4);
		cell.setCellStyle(headerstyle);
		cell.setCellValue("TsProjectBillableHours");

		cell = headrow.createCell(5);
		cell.setCellStyle(headerstyle);
		cell.setCellValue("GrandTotalFromSummary");

		cell = headrow.createCell(6);
		cell.setCellStyle(headerstyle);
		cell.setCellValue("Difference");
		
	}

	private static void createList(CompareModel user, Row row, XSSFCellStyle style) // creating cells for each row
	{
		if (user.getDifference() != 0) {
			Cell cell = row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(user.getEmpId());

			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(user.getEmpName());

			cell = row.createCell(2);
			cell.setCellStyle(style);
			cell.setCellValue(user.getFromDate());

			cell = row.createCell(3);
			cell.setCellStyle(style);
			cell.setCellValue(user.getToDate());

			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(user.getTsProjectBillableHours());

			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue(user.getGrandTotalFromSummary());

			cell = row.createCell(6);
			cell.setCellStyle(style);
			cell.setCellValue(user.getDifference());
		}else {
			Cell cell = row.createCell(0);
			cell.setCellValue(user.getEmpId());

			cell = row.createCell(1);
			cell.setCellValue(user.getEmpName());

			cell = row.createCell(2);
			cell.setCellValue(user.getFromDate());

			cell = row.createCell(3);
			cell.setCellValue(user.getToDate());

			cell = row.createCell(4);
			cell.setCellValue(user.getTsProjectBillableHours());

			cell = row.createCell(5);
			cell.setCellValue(user.getGrandTotalFromSummary());

			cell = row.createCell(6);
			cell.setCellValue(user.getDifference());
			
		}

	}

}
