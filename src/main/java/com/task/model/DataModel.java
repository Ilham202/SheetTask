package com.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class DataModel {

	private Integer id;
	private String name;
	private Integer empId;
	private String weekStartingDate;
	private String weekEndingDate;
	private String workDate;
	private Integer units;
	private String status;
	private Double rate;
	private Integer projectId;
	private String projectName;
	private Integer pmId;
	private String pmName;

}
