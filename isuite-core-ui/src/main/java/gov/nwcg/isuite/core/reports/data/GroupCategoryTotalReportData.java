package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for AircraftDetailReport.jrxml.
 */
public class GroupCategoryTotalReportData extends CostReportData{

	private Collection<CostReportSubReportData> subReportData = new ArrayList<CostReportSubReportData>();
	private Collection<CostReportChartReportData> subReportChartData = new ArrayList<CostReportChartReportData>();
	private Collection<CostReportSubReportData> allIncidentsSubReportData = new ArrayList<CostReportSubReportData>();
	private JRBeanCollectionDataSource dataSourceSubReportData;
	private JRBeanCollectionDataSource dataSourceSubReportChartData;
	private JRBeanCollectionDataSource dataSourceAllIncidentsSubReportData;
	
	
	public GroupCategoryTotalReportData() {}
	
	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(Collection<CostReportSubReportData> subReportData) {
		this.subReportData = (List<CostReportSubReportData>)subReportData;
	}
	
	public Collection<CostReportSubReportData> getSubReportData( ) {
		return this.subReportData;
	}
	
	public void setSubReportChartData(Collection<CostReportChartReportData> subReportChartData) {
		this.subReportChartData = (List<CostReportChartReportData>)subReportChartData;
	}
	
	public Collection<CostReportChartReportData> getSubReportChartData( ) {
		return this.subReportChartData;
	}
	
	public void addSubReportChartData(Collection<CostReportChartReportData> subReportChartData) {
		this.subReportChartData.addAll(subReportChartData);
	}
	
	public void setAllIncidentsSubReportData(Collection<CostReportSubReportData> allIncidentsSubReportData) {
		this.allIncidentsSubReportData = (List<CostReportSubReportData>)allIncidentsSubReportData;
	}

	public void addAllIncidentsSubReportData(Collection<CostReportSubReportData> allIncidentsSubReportData) {
		this.allIncidentsSubReportData.addAll(allIncidentsSubReportData);
	}
	
	public Collection<CostReportSubReportData> getAllIncidentsSubReportData( ) {
		return allIncidentsSubReportData;
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
		//dataSourceSubReportChartData = new JRBeanCollectionDataSource(new ArrayList());
		return dataSourceSubReportChartData;
	}

	public void setDataSourceSubReportChartData(
			JRBeanCollectionDataSource dataSourceSubReportChartData) {
		this.dataSourceSubReportChartData = dataSourceSubReportChartData;
	}
	
	public JRBeanCollectionDataSource getDataSourceAllIncidentsSubReportData() {
		this.dataSourceAllIncidentsSubReportData = new JRBeanCollectionDataSource(allIncidentsSubReportData);
		return this.dataSourceAllIncidentsSubReportData;
	}

	public void setDataSourceAllIncidentsSubReportData(
			JRBeanCollectionDataSource dataSourceAllIncidentsSubReportData) {
		this.dataSourceAllIncidentsSubReportData = dataSourceAllIncidentsSubReportData;
	}
}
