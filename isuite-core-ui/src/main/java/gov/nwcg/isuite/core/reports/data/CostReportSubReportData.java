package gov.nwcg.isuite.core.reports.data;

import java.util.Date;

/**
 * Cost Report Sub report data.
 */
public class CostReportSubReportData {

		private String directIndirectName;
		private String kindGroupDescription;
		private String subGroupCatDescription;
		private String incidentName;
		private Long incidentId;
		private Double costAmount;
		private String groupBy;
		private Date groupByDate;
		private Double currentDateCostAmount;
		
		public CostReportSubReportData() {}
		
		public Long getIncidentId() {
			return incidentId;
		}

		public void setIncidentId(Long incidentId) {
			this.incidentId = incidentId;
		}

		public String getGroupBy() {
			return groupBy;
		}

		public void setGroupBy(String groupBy) {
			this.groupBy = groupBy;
		}

		public Date getGroupByDate() {
			return groupByDate;
		}

		public void setGroupByDate(Date groupByDate) {
			this.groupByDate = groupByDate;
		}

		public String getDirectIndirectName() {
			return directIndirectName;
		}


		public void setDirectIndirectName(String directIndirectName) {
			this.directIndirectName = directIndirectName;
		}


		public String getKindGroupDescription() {
			return kindGroupDescription;
		}


		public void setKindGroupDescription(String kindGroupDescription) {
			this.kindGroupDescription = kindGroupDescription;
		}


		public String getSubGroupCatDescription() {
			return this.subGroupCatDescription;
		}


		public void setSubGroupCatDescription(String subGroupCatDescription) {
			this.subGroupCatDescription = subGroupCatDescription;
		}

		public String getIncidentName() {
			return incidentName;
		}


		public void setIncidentName(String incidentName) {
			this.incidentName = incidentName;
		}


		public Double getCostAmount() {
			return costAmount;
		}


		public void setCostAmount(Double costAmount) {
			this.costAmount = costAmount;
		}

		public Double getCurrentDateCostAmount() {
			return currentDateCostAmount;
		}

		public void setCurrentDateCostAmount(Double currentDateCostAmount) {
			this.currentDateCostAmount = currentDateCostAmount;
		}
}
