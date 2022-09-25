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
public class SummeryModel {
	
	private Integer empId;
	private String name;
	private Integer projectId;
	private String projectName;
	private Integer grandTotal;

}
