package com.task.model;

import java.util.Comparator;

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
	
	public static Comparator<SummeryModel> sortBasedonEmpId = new Comparator<SummeryModel>() {
		@Override
		public int compare(SummeryModel o1, SummeryModel o2) {
			int eid1 = o1.getEmpId();
			int eid2 = o2.getEmpId();
			return eid1 - eid2;
		}
	};

}
