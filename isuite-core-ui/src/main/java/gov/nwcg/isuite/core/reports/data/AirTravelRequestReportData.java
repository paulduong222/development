package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import gov.nwcg.isuite.framework.report.data.BaseReportData;

public class AirTravelRequestReportData extends BaseReportData {
	private Date tentativeReleaseDate = null;
	private String requestNumber = "";
	private Long resourceId;
	private String demobStatus = "";
	private String unitId = "";
	private String demobCity = "";
	private String demobState = "";
	private String specialInstructions = "";
	private Integer travelTimeHours = null;
	private Integer travelTimeMinutes = null;
	private String departJetport = "";
	private String returnJetport = "";
	private Date firstWorkDate = null;
	private Long lengthAtAssignment = null;
	private Boolean reassignment = false;
	private String reassignmentQuals = "";
	private Date calcDate;
	private String departJetportCityState="";
	private String returnJetportCityState="";
	private String departCity="";
	private String departState="";
	private String returnCity="";
	private String returnState="";
	private String demobCityState="";
	private String travelMethod="";
	
	private ArrayList<AirTravelQuestionReportData> travelQuestionData = new ArrayList<AirTravelQuestionReportData>();
	private JRBeanCollectionDataSource dataSourceTravelQuestionData;
	
	
	/**
	 * @return the demobCity
	 */
	public String getDemobCity() {
		return demobCity;
	}

	/**
	 * @param demobCity the demobCity to set
	 */
	public void setDemobCity(String demobCity) {
		this.demobCity = demobCity;
	}

	/**
	 * @return the demobState
	 */
	public String getDemobState() {
		return demobState;
	}

	/**
	 * @param demobState the demobState to set
	 */
	public void setDemobState(String demobState) {
		this.demobState = demobState;
	}

	public AirTravelRequestReportData() {
	}

	/**
	 * @return the tentativeReleaseDate
	 */
	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	/**
	 * @param tentativeReleaseDate the tentativeReleaseDate to set
	 */
	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @param demobStatus the demobStatus to set
	 */
	public void setDemobStatus(String demobStatus) {
		this.demobStatus = demobStatus;
	}

	/**
	 * @return the demobStatus
	 */
	public String getDemobStatus() {
		return demobStatus;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	
	/**
	 * @return the travelTimeMinutes
	 */
	public Integer getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	/**
	 * @param travelTimeMinutes the travelTimeMinutes to set
	 */
	public void setTravelTimeMinutes(Integer travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}
	
	/**
	 * @return the travelTimeHours
	 */
	public Integer getTravelTimeHours() {
		return travelTimeHours;
	}

	/**
	 * @param travelTimeHours the travelTimeHours to set
	 */
	public void setTravelTimeHours(Integer travelTimeHours) {
		this.travelTimeHours = travelTimeHours;
	}
	
	/**
	 * @return the departJetport
	 */
	public String getDepartJetport() {
		return departJetport;
	}

	/**
	 * @param departJetport the departJetport to set
	 */
	public void setDepartJetport(String departJetport) {
		this.departJetport = departJetport;
	}

	/**
	 * @return the returnJetport
	 */
	public String getReturnJetport() {
		return returnJetport;
	}

	/**
	 * @param returnJetport the returnJetport to set
	 */
	public void setReturnJetport(String returnJetport) {
		this.returnJetport = returnJetport;
	}
	
	/**
	 * @return the firstWorkDate
	 */
	public Date getFirstWorkDate() {
		return firstWorkDate;
	}

	/**
	 * @param firstWorkDate the firstWorkDate to set
	 */
	public void setFirstWorkDate(Date firstWorkDate) {
		this.firstWorkDate = firstWorkDate;
	}

	/**
	 * @return the lengthAtAssignment
	 */
	public Long getLengthAtAssignment() {
		return lengthAtAssignment;
	}

	/**
	 * @param lengthAtAssignment the lengthAtAssignment to set
	 */
	public void setLengthAtAssignment(Long lengthAtAssignment) {
		this.lengthAtAssignment = lengthAtAssignment;
	}

	/**
	 * @return the reassignment
	 */
	public Boolean getReassignment() {
		return reassignment;
	}

	/**
	 * @param reassignment the reassignment to set
	 */
	public void setReassignment(Boolean reassignment) {
		this.reassignment = reassignment;
	}
	
	
	/**
	 * @param reassignmentQuals the reassignmentQuals to set
	 */
	public void setReassignmentQuals(String reassignmentQuals) {
		this.reassignmentQuals = reassignmentQuals;
	}

	/**
	 * @return the reassignmentQuals
	 */
	public String getReassignmentQuals() {
		return reassignmentQuals;
	}

	/**
	 * @return the travelQuestionData
	 */
	public ArrayList<AirTravelQuestionReportData> getTravelQuestionData() {
		return travelQuestionData;
	}

	/**
	 * @param travelQuestionData the travelQuestionData to set
	 */
	public void setTravelQuestionData(
			ArrayList<AirTravelQuestionReportData> travelQuestionData) {
		this.travelQuestionData = travelQuestionData;
	}

	/**
	 * @return the dataSourceTravelQuestionData
	 */
	public JRBeanCollectionDataSource getDataSourceTravelQuestionData() {
		return new JRBeanCollectionDataSource(travelQuestionData);
	}

	/**
	 * @param dataSourceTravelQuestionData the dataSourceTravelQuestionData to set
	 */
	public void setDataSourceTravelQuestionData(
			JRBeanCollectionDataSource dataSourceTravelQuestionData) {
		this.dataSourceTravelQuestionData = dataSourceTravelQuestionData;
	}

   /**
    * @return the calcDate
    */
   public Date getCalcDate() {
	   if(null != firstWorkDate && null != lengthAtAssignment){
	      Calendar cal = Calendar.getInstance();
	      cal.setTime(this.firstWorkDate);
	      if(this.lengthAtAssignment != null) {
	         cal.add(Calendar.DATE, this.lengthAtAssignment.intValue() - 1);
	      }
	      this.setCalcDate(cal.getTime());
	      return cal.getTime();
	   }else
		   return null;
   }

   /**
    * @param calcDate the calcDate to set
    */
   public void setCalcDate(Date calcDate) {
      this.calcDate = calcDate;
   }

   public String getDepartJetportCityState() {
	   return super.getCityState(this.departCity, this.departState);
   }

   public void setDepartJetportCityState(String departJetportCityState) {
	   this.departJetportCityState = departJetportCityState;
   }

   public String getReturnJetportCityState() {
	   return super.getCityState(this.returnCity, this.returnState);
   }

   public void setReturnJetportCityState(String returnJetportCityState) {
	   this.returnJetportCityState = returnJetportCityState;
   }

   public String getDemobCityState() {
	   return super.getCityState(this.demobCity, this.demobState);
   }
   
   public void setDemobCityState(String demobCityState) {
	   this.demobCityState = demobCityState;
   }

	/**
	 * @param departCity the departCity to set
	 */
	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}
	
	/**
	 * @return the departCity
	 */
	public String getDepartCity() {
		return departCity;
	}
	
	/**
	 * @param departState the departState to set
	 */
	public void setDepartState(String departState) {
		this.departState = departState;
	}
	
	/**
	 * @return the departState
	 */
	public String getDepartState() {
		return departState;
	}
	
	/**
	 * @param returnCity the returnCity to set
	 */
	public void setReturnCity(String returnCity) {
		this.returnCity = returnCity;
	}
	
	/**
	 * @return the returnCity
	 */
	public String getReturnCity() {
		return returnCity;
	}
	
	/**
	 * @param returnState the returnState to set
	 */
	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}
	
	/**
	 * @return the returnState
	 */
	public String getReturnState() {
		return returnState;
	}

	/**
	 * @return the travelMethod
	 */
	public String getTravelMethod() {
		return travelMethod;
	}

	/**
	 * @param travelMethod the travelMethod to set
	 */
	public void setTravelMethod(String travelMethod) {
		this.travelMethod = travelMethod;
	}

}
