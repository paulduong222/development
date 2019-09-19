package gov.nwcg.isuite.core.reports.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Report data object for AircraftDetailReport.jrxml.
 */
public class AircraftDetailSubReportData {

		private Date activityDate;
		private String costGrp;
		private String name;
		private String itemCode;
		private String itemDescription;
		private Double totalAmount;
		private Double flightHrs ;
		private Double galWater;
		private Double galRetard;
		private Double numOfLoads;
		private Double numOfTrips;
		private Double numOfPax;
		private Double lbsCargo;
	   
		public AircraftDetailSubReportData() {}

		public Date getActivityDate() {
			return activityDate;
		}

		public void setActivityDate(Date activityDate) {
			this.activityDate = activityDate;
		}

		public String getCostGrp() {
			return costGrp;
		}

		public void setCostGrp(String costGrp) {
			this.costGrp = costGrp;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getItemCode() {
			return itemCode;
		}

		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}

		public String getItemDescription() {
			return itemDescription;
		}

		public void setItemDescription(String itemDescription) {
			this.itemDescription = itemDescription;
		}

		public Double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public Double getFlightHrs() {
			return flightHrs;
		}

		public void setFlightHrs(Double flightHrs) {
			this.flightHrs = flightHrs;
		}

//		public Double getOtherHrs() {
//			return otherHrs;
//		}
//
//		public void setOtherHrs(Double otherHrs) {
//			this.otherHrs = otherHrs;
//		}
//
//		public Double getToralHrs() {
//			return toralHrs;
//		}
//
//		public void setToralHrs(Double toralHrs) {
//			this.toralHrs = toralHrs;
//		}

		public Double getGalWater() {
			return galWater;
		}

		public void setGalWater(Double galWater) {
			this.galWater = galWater;
		}

		public Double getGalRetard() {
			return galRetard;
		}

		public void setGalRetard(Double galRetard) {
			this.galRetard = galRetard;
		}

		public Double getNumOfLoads() {
			return numOfLoads;
		}

		public void setNumOfLoads(Double numOfLoads) {
			this.numOfLoads = numOfLoads;
		}

		public Double getNumOfTrips() {
			return numOfTrips;
		}

		public void setNumOfTrips(Double numOfTrips) {
			this.numOfTrips = numOfTrips;
		}

		public Double getNumOfPax() {
			return numOfPax;
		}

		public void setNumOfPax(Double numOfPax) {
			this.numOfPax = numOfPax;
		}

		public Double getLbsCargo() {
			return lbsCargo;
		}

		public void setLbsCargo(Double lbsCargo) {
			this.lbsCargo = lbsCargo;
		}
}
