package com.task.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.task.model.CompareModel;
import com.task.model.DataModel;
import com.task.model.ESAModel;
import com.task.model.SummeryModel;
import com.task.service.ReadService;

@CrossOrigin(origins = "*")
@RestController
public class ReadController {

	@Autowired
	ReadService readService;

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

	@PostMapping("/comparetwosheets")
	public List<CompareModel> compareSheets(@RequestParam("Summary") MultipartFile excel1,
			@RequestParam("ESA") MultipartFile excel2) throws IOException {
		readService.readSheetModelData(excel1);
		readService.readEsaDataModel(excel2);
		return readService.compareSheets();

	}

}
