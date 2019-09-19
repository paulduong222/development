package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for CheckOutReport.jrxml.
 */
public class CheckoutReportData extends BaseReportData {
	private Long incidentGroupId;
	private Date tentativeReleaseDate = null;
	private String requestNumber = "";
	private String kindCode = "";
	private BigInteger numberPersonnel;
	private String leaderName = "";
	private String travelMethod = "";
	private Date actualReleaseDate = null;
	private String demobCity = "";
	private String demobState = "";
	private String destination="";
	private String agency = "";
	private String releaseRemarks = "";
	private Long resourceId;
	private String unitId="";
	private Date eta=null;
	private String ron="";
	private String includeBoxTwelve="";
	
	private ArrayList<DemobCheckoutSectionData> sectionData = new ArrayList<DemobCheckoutSectionData>();
	private JRBeanCollectionDataSource dataSourceSectionData;
	
	public CheckoutReportData() {
	}
	
	/**
	 * Returns the leaderName
	 * 
	 * @return
	 * 		the leaderName to return
	 */
	public String getLeaderName() {
		return leaderName;
	}
	
	/**
	 * Sets the leaderName
	 * 
	 * @param leaderName
	 * 		the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	/**
	 * Returns the travelMethod
	 * 
	 * @return
	 * 		the travelMethod to return
	 */
	public String getTravelMethod() {
		return travelMethod;
	}
	
	/**
	 * Sets the travelMethod
	 * 
	 * @param travelMethod
	 * 		the travelMethod to set
	 */
	public void setTravelMethod(String travelMethod) {
		this.travelMethod = travelMethod;
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
	 * Sets the actualReleaseDate
	 * 
	 * @param actualReleaseDate
	 * 		the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
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
	 * Sets the demobCity
	 * 
	 * @param demobCity
	 * 		the demobCity to set
	 */
	public void setDemobCity(String demobCity) {
		this.demobCity = demobCity;
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
	 * Sets the demobState
	 * 
	 * @param demobState
	 * 		the demobState to set
	 */
	public void setDemobState(String demobState) {
		this.demobState = demobState;
	}
	
	/**
	 * Returns the releaseRemarks
	 * 
	 * @return
	 * 		the releaseRemarks to return
	 */
	public String getReleaseRemarks() {
		String rtn=(StringUtility.hasValue(releaseRemarks) ? "Release Remarks: " + releaseRemarks : "");
		if(this.includeBoxTwelve.equals("YES")){
			rtn=rtn+"\n";
			String etaTime="";
			if(DateUtil.hasValue(eta)){
				etaTime=DateUtil.toMilitaryTime(eta);
				if(StringUtility.hasValue(etaTime)&& etaTime.equals("0000")){
					etaTime="";
				}
				
			}
			rtn=rtn+(DateUtil.hasValue(eta) ? "Estimate Date of Arrival: " + DateUtil.toDateString(eta) + (" " + etaTime)+"\n" : "");
			rtn=rtn+(StringUtility.hasValue(ron) ? "Rest Overnight Locations: " + ron : "");
		}
		
		return rtn;
	}
	
	/**
	 * Sets the releaseRemarks
	 * 
	 * @param releaseRemarks
	 * 		the releaseRemarks to set
	 */
	public void setReleaseRemarks(String releaseRemarks) {
		this.releaseRemarks = releaseRemarks;
	}
	
	/**
	 * Sets the tentativeReleaseDate
	 * 
	 * @param tentativeReleaseDate
	 * 		the tentativeReleaseDate to set
	 */
	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}
	
	/**
	 * Returns the tentativeReleaseDate
	 * 
	 * @return
	 * 		the tentativeReleaseDate to return
	 */
	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
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
	 * Sets the kindCode
	 * 
	 * @param kindCode
	 * 		the kindCodet to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	
	/**
	 * Returns the kindCode
	 * 
	 * @return
	 * 		the kindCode to return
	 */
	public String getKindCode() {
		return kindCode;
	}
	
	/**
	 * Sets the numberPersonnel
	 * 
	 * @param numberPersonnel
	 * 		the numberPersonnel to set
	 */
	public void setNumberPersonnel(BigInteger numberPersonnel) {
		this.numberPersonnel = numberPersonnel;
	}
	
	/**
	 * Returns the numberPersonnel
	 * 
	 * @return
	 * 		the numberPersonnel to return
	 */
	public BigInteger getNumberPersonnel() {
		return numberPersonnel;
	}

	/**
	 * @param sectionData the sectionData to set
	 */
	public void setSectionData(ArrayList<DemobCheckoutSectionData> sectionData) {
		this.sectionData = sectionData;
	}

	/**
	 * @return the sectionData
	 */
	public ArrayList<DemobCheckoutSectionData> getSectionData() {
		return sectionData;
	}
	
	/**
	 * Returns the incidentGroupId.
	 *
	 * @return 
	 *		the incidentGroupId to return
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * Sets the incidentGroupId.
	 *
	 * @param incidentGroupId 
	 *			the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/**
	 * Returns the dataSourceSectionData based on the sectionData collection.
	 * 
	 * @return 
	 *		the dataSourceSectionData to return
	 */
	public JRBeanCollectionDataSource getDataSourceSectionData() {
		return new JRBeanCollectionDataSource(sectionData);
	}

	/**
	 * Sets the dataSourceSectionData.
	 *
	 * @param dataSourceSectionData 
	 *			the dataSourceSectionData to set
	 */
	public void setDataSourceSectionData(JRBeanCollectionDataSource dataSourceSectionData) {
		this.dataSourceSectionData = dataSourceSectionData;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * @return the agency
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	public String getDestination() {
		String rtn="";
		if( (null != demobCity && !demobCity.isEmpty()))
		{
			rtn=demobCity;
		}
		
		if( (null != demobState && !demobState.isEmpty()))
		{
			if(!rtn.isEmpty()){
				rtn=rtn+", "+demobState;
			}else{
				rtn=", "+demobState;
			}
		}
		
		return rtn;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public Date getEta() {
		return eta;
	}

	public void setEta(Date eta) {
		this.eta = eta;
	}

	public String getRon() {
		return ron;
	}

	public void setRon(String ron) {
		this.ron = ron;
	}

	public String getIncludeBoxTwelve() {
		return includeBoxTwelve;
	}

	public void setIncludeBoxTwelve(String includeBoxTwelve) {
		this.includeBoxTwelve = includeBoxTwelve;
	}

	
}
