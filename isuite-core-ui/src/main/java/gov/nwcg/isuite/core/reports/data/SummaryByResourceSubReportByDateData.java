package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for GroupCategoryTotalSubReport.jrxml.
 */
public class SummaryByResourceSubReportByDateData {

	private Long incidentId;
	private String incidentName;
	private String groupByName;
	private List<SummaryByResourceSubReportData> subReportDataByDate = new ArrayList<SummaryByResourceSubReportData>();
    private JRBeanCollectionDataSource dataSourceReportDataByDate;
	
	
	public SummaryByResourceSubReportByDateData() {}

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
	public Collection<SummaryByResourceSubReportData> getSubReportDataByDate() {
		return subReportDataByDate;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportDataByDate(Collection<SummaryByResourceSubReportData> subReportDataByDate) {
		this.subReportDataByDate = (List<SummaryByResourceSubReportData>)subReportDataByDate;
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
