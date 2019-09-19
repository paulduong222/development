package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for AircraftDetailReport.jrxml.
 */
public class AircraftDetailReportData extends CostReportData{

	private List<AircraftDetailSubReportData> subReportData = new ArrayList<AircraftDetailSubReportData>();
	private List<CostReportChartReportData> subReportChartData = new ArrayList<CostReportChartReportData>();
	private JRBeanCollectionDataSource dataSourceSubReportData;
	private JRBeanCollectionDataSource dataSourceSubReportChartData;
	
	
	public AircraftDetailReportData() {}

	/**
	 * @return the subReportData
	 */
	public Collection<AircraftDetailSubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(Collection<AircraftDetailSubReportData> subReportData) {
		this.subReportData = (List<AircraftDetailSubReportData>)subReportData;
	}
	
	public List<CostReportChartReportData> getSubReportDchartData() {
		return subReportChartData;
	}

	public void setSubReportChartData(Collection<CostReportChartReportData> subReportChartData) {
		this.subReportChartData = (List<CostReportChartReportData>)subReportChartData;
	}

	public JRBeanCollectionDataSource getDataSourceSubReportData() {
		this.dataSourceSubReportData = new JRBeanCollectionDataSource(subReportData);
		return this.dataSourceSubReportData;
	}

	public void setDataSourceSubReportData(
			JRBeanCollectionDataSource dataSourceSubReportData) {
		this.dataSourceSubReportData = dataSourceSubReportData;
	}
	                          
	public JRBeanCollectionDataSource getDataSourceSubReportChartData() {
		dataSourceSubReportChartData = new JRBeanCollectionDataSource(subReportChartData);
		return dataSourceSubReportChartData;
	}

	public void setDataSourceSubReportChartData(
			JRBeanCollectionDataSource dataSourceSubReportChartData) {
		this.dataSourceSubReportChartData = dataSourceSubReportChartData;
	}
}
