package com.task.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.exception.FileFormatException;
import com.task.model.CompareModel;
import com.task.model.DataModel;
import com.task.model.ESAModel;
import com.task.model.SummeryModel;
import com.task.repository.CompareRepository;
import com.task.service.ExcelExportservice;
import com.task.service.ReadService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ReadController {

	@Autowired
	ReadService readService;

	@Autowired
	CompareRepository CompareRepository;

	@Autowired
	ExcelExportservice ExcelExportservice;

	@GetMapping("/")
	public String welcome() {
		return "Hello World";
	}

	@PostMapping("/readdata")
	public List<JSONObject> readData(@RequestParam("file") MultipartFile excel) {
		return readService.readExcelData(excel);

	}

	@PostMapping("/readsummarysheet")
	public List<SummeryModel> readSheetModel(@RequestParam("file") MultipartFile excel) throws IOException {
		return readService.readSheetModelData(excel);

	}

	@PostMapping("/readdatasheet")
	public List<DataModel> readDataModel(@RequestParam("file") MultipartFile excel) throws IOException {
		return readService.readExcelDataModel(excel);

	}

	@PostMapping("/readesasheet")
	public List<ESAModel> readEsaDataModel(@RequestParam("file") MultipartFile excel) throws IOException {
		return readService.readEsaDataModel(excel);

	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/comparetwosheets")
	public List<CompareModel> compareSheets(@RequestParam("Summary") MultipartFile excel1,
			@RequestParam("ESA") MultipartFile excel2) throws IOException, ServletException, ParseException {
		String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		if (TYPE.equals(excel1.getContentType()) && TYPE.equals(excel2.getContentType())) {
			readService.readSheetModelData(excel1);
			readService.readEsaDataModel(excel2);
			return readService.compareSheets();
		}else {
			throw new FileFormatException("Please Upload .xlsx format file");
		}
		

	}

	@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Content-Disposition")
	@GetMapping("/excelexport")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");

		DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";

		String headerValue = "attachment; filename=emps_" + currentDateTime + ".xlsx";
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");

		response.setHeader(headerKey, headerValue);

		List<CompareModel> emps = CompareRepository.findAll();

		ExcelExportservice excelExportservice = new ExcelExportservice(emps);
		excelExportservice.exportData(response);

	}

}
