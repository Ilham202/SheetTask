package com.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompareModel {
	private Integer empId;
	private String empName;
	private String fromDate;
	private String toDate;
	private Integer tsProjectBillableHours;
	private Integer grandTotalFromSummary;
	private Integer difference;

}
