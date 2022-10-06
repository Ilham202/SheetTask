package com.task.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.task.model.CompareModel;
import com.task.model.DataModel;
import com.task.model.ESAModel;
import com.task.model.SummeryModel;
import com.task.repository.CompareRepository;

@Service
public class ReadService {

	@Autowired
	private CompareRepository compareRepository;

	List<DataModel> datalist = new ArrayList<DataModel>();
	List<SummeryModel> summarylist = new ArrayList<SummeryModel>();
	List<ESAModel> emplist = new ArrayList<ESAModel>();
	List<CompareModel> comparelist = new ArrayList<CompareModel>();

	@SuppressWarnings("unchecked")
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
		List<SummeryModel> summarylist1 = new ArrayList<SummeryModel>();
		XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		for (int i = 4; i <= worksheet.getPhysicalNumberOfRows(); i++) {
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
		Collections.sort(summarylist,SummeryModel.sortBasedonEmpId);
		workbook.close();
		return summarylist;
	}

	public List<ESAModel> readEsaDataModel(MultipartFile excel) throws IOException {
		List<ESAModel> emplist1 = new ArrayList<ESAModel>();
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

			} else {
				break;
			}
		}
		emplist.clear();
		emplist.addAll(emplist1);
		Collections.sort(emplist,ESAModel.empIdCompare);
		workbook.close();
		return emplist;
	}

	public List<CompareModel> compareSheets() throws IOException {
		// TODO Auto-generated method stub
		List<CompareModel> comparelist1 = new ArrayList<CompareModel>();
		for (int i = 0; i < summarylist.size(); i++) {
			if (summarylist.get(i).getEmpId().equals(emplist.get(i).getEmpId())) {
				CompareModel cmpmodel = new CompareModel();
				cmpmodel.setEmpId(summarylist.get(i).getEmpId());
				cmpmodel.setEmpName(summarylist.get(i).getName());
				cmpmodel.setTsProjectBillableHours(emplist.get(i).getTsProjectBillableHours());
				cmpmodel.setGrandTotalFromSummary(summarylist.get(i).getGrandTotal());
				cmpmodel.setDifference(
						Math.abs(emplist.get(i).getTsProjectBillableHours() - (summarylist.get(i).getGrandTotal())));
				String[] date = emplist.get(i).getDateRange().split("-", 7);
				cmpmodel.setFromDate(date[0] + "-".concat(date[1]) + "-".concat(date[2]));
				cmpmodel.setToDate(date[3] + "-".concat(date[4]) + "-".concat(date[5]));
				comparelist1.add(cmpmodel);
				compareRepository.save(cmpmodel);
			} else {
				System.out.println("User Not Present");
			}

		}
		comparelist.clear();
		comparelist.addAll(comparelist1);

		return comparelist;
	}

}
