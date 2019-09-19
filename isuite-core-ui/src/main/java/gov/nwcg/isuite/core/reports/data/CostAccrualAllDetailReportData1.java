package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


  
public class CostAccrualAllDetailReportData1 {
	private String header1;
	private String header2;
	private String header3;
	private String header4;
	private Collection<CostAccrualAllDetailSubReportData> subReportData = new ArrayList<CostAccrualAllDetailSubReportData>();
	private JRBeanCollectionDataSource accrualDatasource;
	
	public CostAccrualAllDetailReportData1(){
		super();
	}

	/**
	 * @return the header1
	 */
	public String getHeader1() {
		return header1;
	}

	/**
	 * @param header1 the header1 to set
	 */
	public void setHeader1(String header1) {
		this.header1 = header1;
	}

	/**
	 * @return the header2
	 */
	public String getHeader2() {
		return header2;
	}

	/**
	 * @param header2 the header2 to set
	 */
	public void setHeader2(String header2) {
		this.header2 = header2;
	}

	/**
	 * @return the header3
	 */
	public String getHeader3() {
		return header3;
	}

	/**
	 * @param header3 the header3 to set
	 */
	public void setHeader3(String header3) {
		this.header3 = header3;
	}

	/**
	 * @return the header4
	 */
	public String getHeader4() {
		return header4;
	}

	/**
	 * @param header4 the header4 to set
	 */
	public void setHeader4(String header4) {
		this.header4 = header4;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<CostAccrualAllDetailSubReportData> getSubReportData() {
		return subReportData;
	}

	/**
	 * @param subReportData the subReportData to set
	 */
	public void setSubReportData(
			Collection<CostAccrualAllDetailSubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	/**
	 * @return the accrualDataSource
	 */
	public JRBeanCollectionDataSource getAccrualDatasource() {
		return new JRBeanCollectionDataSource(this.subReportData);
	}

	/**
	 * @param accrualDataSource the accrualDataSource to set
	 */
	public void setAccrualDatasource(JRBeanCollectionDataSource accrualDataSource) {
		this.accrualDatasource = accrualDataSource;
	}

	
}
