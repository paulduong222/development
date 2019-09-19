package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for GroupCategoryTotalSubReport.jrxml.
 */
public class GroupCategorySummarySubReportByDateData {

	private Long incidentId;
	private String incidentName;
	private String groupByName;
	private List<CostReportSubReportData> subReportDataByDate = new ArrayList<CostReportSubReportData>();
    private JRBeanCollectionDataSource dataSourceReportDataByDate;
	
	
	public GroupCategorySummarySubReportByDateData() {}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getGroupByName() {
		return groupByName;
	}

	public void setGroupByName(String groupByName) {
		this.groupByName = groupByName;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<CostReportSubReportData> getSubReportDataByDate() {
		return subReportDataByDate;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportDataByDate(Collection<CostReportSubReportData> subReportDataByDate) {
		this.subReportDataByDate = (List<CostReportSubReportData>)subReportDataByDate;
	}

	public JRBeanCollectionDataSource getDataSourceReportDataByDate() {
		this.dataSourceReportDataByDate = new JRBeanCollectionDataSource(subReportDataByDate);
		return this.dataSourceReportDataByDate;
	}

	public void setDataSourceReportDataByDate(
			JRBeanCollectionDataSource dataSourceReportDataByDate) {
		this.dataSourceReportDataByDate = dataSourceReportDataByDate;
	}
}
