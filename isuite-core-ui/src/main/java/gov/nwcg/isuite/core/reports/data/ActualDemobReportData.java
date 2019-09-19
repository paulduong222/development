package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

public class ActualDemobReportData extends BaseReportData {
	private String requestNumber = "";
	private String unitId = "";
	private Date actualReleaseDate = null;
	private String returnTravelMethod = "";
	private String demobCity = "";
	private String demobState = "";
	private String remarks = "";
	private Long resourceId;
	private Long workPeriodId;
	private String demobCityState="";
	private ArrayList<ActualDemobRestOverNightData> restOverNightData = new ArrayList<ActualDemobRestOverNightData>();
	private Date estimatedArrivalDate = null;
	private JRBeanCollectionDataSource dataSourceRestOverNightData;
	
	public ActualDemobReportData() {
	}
	
	/**
	 * Sets the requestNumber
	 * 
	 * @param requestNumber
	 * 		the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the requestNumber
	 * 
	 * @return
	 * 		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the unitId
	 * 
	 * @param unitId
	 * 		the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * Returns the unitId
	 * 
	 * @return
	 * 		the unitId to return
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * Sets the actualReleaseDate
	 * 
	 * @param actualReleaseDate
	 * 		the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}

	/**
	 * Returns the actualReleaseDate
	 * 
	 * @return
	 * 		the actualReleaseDate to return
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	/**
	 * Sets the demobState
	 * 
	 * @param demobState
	 * 		the demobState to set
	 */
	public void setDemobState(String demobState) {
		this.demobState = demobState;
	}

	/**
	 * Returns the demobState
	 * 
	 * @return
	 * 		the demobState to return
	 */
	public String getDemobState() {
		return demobState;
	}

	/**
	 * Sets the returnTravelMethod
	 * 
	 * @param returnTravelMethod
	 * 		the returnTravelMethod to set
	 */
	public void setReturnTravelMethod(String returnTravelMethod) {
		this.returnTravelMethod = returnTravelMethod;
	}

	/**
	 * Returns the returnTravelMethod
	 * 
	 * @return
	 * 		the returnTravelMethod to return
	 */
	public String getReturnTravelMethod() {
		return returnTravelMethod;
	}

	/**
	 * Sets the demobCity
	 * 
	 * @param demobCity
	 * 		the demobCity to set
	 */
	public void setDemobCity(String demobCity) {
		this.demobCity = demobCity;
	}

	/**
	 * Returns the demobCity
	 * 
	 * @return
	 * 		the demobCity to return
	 */
	public String getDemobCity() {
		return demobCity;
	}

	/**
	 * Sets the remarks
	 * 
	 * @param remarks
	 * 		the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Returns the remarks
	 * 
	 * @return
	 * 		the remarks to return
	 */
	public String getRemarks() {
		if(null == remarks)
			return " ";
		else
			return remarks;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	public String getDemobCityState() {
		return super.getCityState(demobCity, demobState);
	}

	public void setDemobCityState(String demobCityState) {
		this.demobCityState = demobCityState;
	}

	/**
	 * @param restOverNightData
	 */
	public void setRestOverNightData(ArrayList<ActualDemobRestOverNightData> restOverNightData) {
		this.restOverNightData = restOverNightData;
	}

	/**
	 * @return restOverNightData
	 */
	public ArrayList<ActualDemobRestOverNightData> getRestOverNightData() {
		return this.restOverNightData;
	}
	
	/**
	 * Returns the dataSourceRestOverNightData based on the restOverNightData collection.
	 * 
	 * @return 
	 *		the dataSourceRestOverNightData to return
	 */
	public JRBeanCollectionDataSource getDataSourceRestOverNightData() {
		if(!CollectionUtility.hasValue(this.restOverNightData) && DateUtil.hasValue(this.estimatedArrivalDate)){
			// for defect 3545
			ActualDemobRestOverNightData data1=new ActualDemobRestOverNightData();
			data1.setEstimatedArrivalDate(this.estimatedArrivalDate);
			this.restOverNightData = new ArrayList<ActualDemobRestOverNightData>();
			this.restOverNightData.add(data1);
			return new JRBeanCollectionDataSource(this.restOverNightData);
		}else
			return new JRBeanCollectionDataSource(this.restOverNightData);
	}

	/**
	 * Sets the dataSourceRestOverNightData.
	 *
	 * @param dataSourceRestOverNightData 
	 *			the dataSourceRestOverNightData to set
	 */
	public void setDataSourceRestOverNightData(JRBeanCollectionDataSource dataSourceRestOverNightData) {
		this.dataSourceRestOverNightData = dataSourceRestOverNightData;
	}

	/**
	 * @return the estimatedArrivalDate
	 */
	public Date getEstimatedArrivalDate() {
		return estimatedArrivalDate;
	}

	/**
	 * @param estimatedArrivalDate the estimatedArrivalDate to set
	 */
	public void setEstimatedArrivalDate(Date estimatedArrivalDate) {
		this.estimatedArrivalDate = estimatedArrivalDate;
	}
}
