package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Date;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for GroupCategorySummaryReport.jrxml.
 */
public class GroupCategorySummaryReportData extends CostReportData{

	private Collection<GroupCategorySummarySubReportByDateData> subReportDataByDate = new ArrayList<GroupCategorySummarySubReportByDateData>();
	private Collection<CostReportSubReportData> subReportDataByGroup = new ArrayList<CostReportSubReportData>();
	private Collection<CostReportChartReportData> subReportChartData = new ArrayList<CostReportChartReportData>();
	private JRBeanCollectionDataSource dataSourceSubReportDataByDate;
	private JRBeanCollectionDataSource dataSourceSubReportDataByGroup;
	private JRBeanCollectionDataSource dataSourceSubReportChartData;
	
	public GroupCategorySummaryReportData() {}

	/**
	 * @return the subReportData
	 */
	public Collection<GroupCategorySummarySubReportByDateData> getSubReportDataByDate() {
		return subReportDataByDate;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportDataByDate(Collection<GroupCategorySummarySubReportByDateData> subReportDataByDate) {
		this.subReportDataByDate = (List<GroupCategorySummarySubReportByDateData>)subReportDataByDate;
	}

	public JRBeanCollectionDataSource getDataSourceSubReportDataByDate() {
		this.dataSourceSubReportDataByDate = new JRBeanCollectionDataSource(subReportDataByDate);
		return this.dataSourceSubReportDataByDate;
	}

	public void setDataSourceSubReportDataByDate(
			JRBeanCollectionDataSource dataSourceSubReportDataByDate) {
		this.dataSourceSubReportDataByDate = dataSourceSubReportDataByDate;
	}
	
	/**
	 * @return the subReportData
	 */
	public Collection<CostReportSubReportData> getSubReportDataByGroup() {
		return subReportDataByGroup;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportDataByGroup(Collection<CostReportSubReportData> subReportDataByGroup) {
		this.subReportDataByGroup = (List<CostReportSubReportData>)subReportDataByGroup;
	}

	public JRBeanCollectionDataSource getDataSourceSubReportDataByGroup() {
		this.dataSourceSubReportDataByGroup = new JRBeanCollectionDataSource(subReportDataByGroup);
		return this.dataSourceSubReportDataByGroup;
	}

	public void setDataSourceSubReportDataByGroup(
			JRBeanCollectionDataSource dataSourceSubReportDataByGroup) {
		this.dataSourceSubReportDataByGroup = dataSourceSubReportDataByGroup;
	}
	
	
	public JRBeanCollectionDataSource getDataSourceSubReportChartData() {
		dataSourceSubReportChartData = new JRBeanCollectionDataSource(subReportChartData);
		return dataSourceSubReportChartData;
	}

	public void setDataSourceSubReportChartData(
			JRBeanCollectionDataSource dataSourceSubReportChartData) {
		this.dataSourceSubReportChartData = dataSourceSubReportChartData;
	}	
	
	public void setSubReportChartData(Collection<CostReportChartReportData> subReportChartData) {
		this.subReportChartData = (List<CostReportChartReportData>)subReportChartData;
	}
}
