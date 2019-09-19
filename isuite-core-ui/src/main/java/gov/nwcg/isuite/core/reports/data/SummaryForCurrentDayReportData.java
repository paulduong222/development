package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/**
 * Report data object for SummaryForCurrentDayReportData.jrxml.
 */
public class SummaryForCurrentDayReportData extends CostReportData{

	private List<CostReportSubReportData> subReportDataByGroup = new ArrayList<CostReportSubReportData>();
	private List<CostReportSubReportData> subReportDataByDate = new ArrayList<CostReportSubReportData>();
	private JRBeanCollectionDataSource dataSourceReportDataByGroup;
	private JRBeanCollectionDataSource dataSourceReportDataByDate;
	private String groupingNameList;
	
	public SummaryForCurrentDayReportData() {}

	public String getGroupingNameList() {
		return groupingNameList;
	}


	public void setGroupingNameList(String groupingNameList) {
		this.groupingNameList = groupingNameList;
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
