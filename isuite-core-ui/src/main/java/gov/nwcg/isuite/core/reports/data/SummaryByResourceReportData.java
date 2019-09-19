package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for SummaryByResourceReport.jrxml.
 */
public class SummaryByResourceReportData extends CostReportData{

	private Boolean isNonOH;
	private List<SummaryByResourceSubReportData> subReportDataByGroup = new ArrayList<SummaryByResourceSubReportData>();
	private List<SummaryByResourceSubReportByDateData> subReportDataByDate = new ArrayList<SummaryByResourceSubReportByDateData>();
	private JRBeanCollectionDataSource dataSourceReportDataByGroup;
	private JRBeanCollectionDataSource dataSourceReportDataByDate;
	
	public SummaryByResourceReportData() {}

	/**
	 * @return the subReportData
	 */
	public Collection<SummaryByResourceSubReportData> getSubReportDataByGroup() {
		return subReportDataByGroup;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportDataByGroup(Collection<SummaryByResourceSubReportData> subReportDataByGroup) {
		this.subReportDataByGroup = (List<SummaryByResourceSubReportData>)subReportDataByGroup;
	}

	public JRBeanCollectionDataSource getDataSourceReportDataByGroup() {
		this.dataSourceReportDataByGroup = new JRBeanCollectionDataSource(subReportDataByGroup);
		return this.dataSourceReportDataByGroup;
	}

	public void setDataSourceReportDataByGroup(
			JRBeanCollectionDataSource dataSourceReportDataByGroup) {
		this.dataSourceReportDataByGroup = dataSourceReportDataByGroup;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<SummaryByResourceSubReportByDateData> getSubReportDataByDate() {
		return subReportDataByDate;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportDataByDate(Collection<SummaryByResourceSubReportByDateData> subReportDataByDate) {
		this.subReportDataByDate = (List<SummaryByResourceSubReportByDateData>)subReportDataByDate;
	}

	public JRBeanCollectionDataSource getDataSourceReportDataByDate() {
		this.dataSourceReportDataByDate = new JRBeanCollectionDataSource(subReportDataByDate);
		return this.dataSourceReportDataByDate;
	}

	public void setDataSourceReportDataByDate(
			JRBeanCollectionDataSource dataSourceReportDataByDate) {
		this.dataSourceReportDataByDate = dataSourceReportDataByDate;
	}

	/**
	 * @param isNonOH the isNonOH to set
	 */
	public void setIsNonOH(Boolean isNonOH) {
		this.isNonOH = isNonOH;
	}

	/**
	 * @return the isNonOH
	 */
	public Boolean getIsNonOH() {
		return isNonOH;
	}
}
