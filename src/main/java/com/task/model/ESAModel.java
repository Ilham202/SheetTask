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
public class ESAModel {
	private Integer empId;
	private String empName;
	private String dateRange;
	private String projectRole;
	private String billFlag;
	private String billingLocation;
	private String onOrOff;
	private Integer tsProjectHours;
	private Integer tsProjectBillableHours;
	private Integer billableQuantity;
	private Integer regularRate;
	private Integer overTimeQuantity;
	private Integer overTimeRate;
	private Integer billableAmount;
	private Integer overTimeAmount;
	private Integer grandTotalAmount;
	private Integer invoiceSplitId;
	private String reason;
	private String deferBilling;
	private String deferDate;
	private String correctionAtStore;

	public static Comparator<ESAModel> empIdCompare = new Comparator<ESAModel>() {
		@Override
		public int compare(ESAModel o1, ESAModel o2) {
			int eid1 = o1.getEmpId();
			int eid2 = o2.getEmpId();
			return eid1 - eid2;
		}
	};

}
