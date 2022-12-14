package com.task.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "compare")
public class CompareModel {
	@Id
	private Integer empId;
	private String empName;
	private String fromDate;
	private String toDate;
	private Integer tsProjectBillableHours;
	private Integer grandTotalFromSummary;
	private Integer difference;

}
