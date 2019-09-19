package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ResourceCheckinDemobVo extends AbstractVo {

   private String city;
   private CountryCodeSubdivisionVo state;
   private TravelMethodTypeEnum travelMethod;
   private String rentalLocation;
   private JetPortVo jetPort; 
   private Date mobilizationDate;
   private Date firstWorkDate;
   private Long lengthAtAssignment;
   private Date demobDate;
   private Long daysLeft;
   private Collection<KindVo> qualifications;
   private Collection<String> prePlanningQuestions; // Not implemented
   private Collection<String> prePlanningRemarks; // Not implemented
   
   // Items from the Planning, Air Travel, and Actual tabs will be added later.
   
   /**
    * 
    * @return the city
    */
   public String getCity() {
      return city;
   }
   
   /**
    * @param city the city to set
    */
   public void setCity(String city) {
      this.city = city;
   }

   /**
    * @return the daysLeft
    */
   public Long getDaysLeft() {
      return daysLeft;
   }

   /**
    * @param daysLeft the daysLeft to set
    */
   public void setDaysLeft(Long daysLeft) {
      this.daysLeft = daysLeft;
   }

   /**
    * @return the demobDate
    */
   public Date getDemobDate() {
      return demobDate;
   }

   /**
    * @param demobDate the demobDate to set
    */
   public void setDemobDate(Date demobDate) {
      this.demobDate = demobDate;
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
    * @return the jetPort
    */
   public JetPortVo getJetPort() {
      if (jetPort == null) {
         jetPort = new JetPortVo();
      }
      return jetPort;
   }

   /**
    * @param jetPort the jetPort to set
    */
   public void setJetPort(JetPortVo jetPort) {
      this.jetPort = jetPort;
   }
   
   /**
    * Convenience method for transformation for hibernate.
    * @param jetPort
    */
   public void setArrivalJetPort(JetPort jetPort) throws Exception{
	   if(null!=jetPort){
		   this.jetPort = JetPortVo.getInstance(jetPort, true);
	   }
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
    * @return the mobilizationDate
    */
   public Date getMobilizationDate() {
      return mobilizationDate;
   }

   /**
    * @param mobilizationDate the mobilizationDate to set
    */
   public void setMobilizationDate(Date mobilizationDate) {
      this.mobilizationDate = mobilizationDate;
   }

   /**
    * @return the prePlanningQuestions
    */
   public Collection<String> getPrePlanningQuestions() {
      return prePlanningQuestions;
   }

   /**
    * @param prePlanningQuestions the prePlanningQuestions to set
    */
   public void setPrePlanningQuestions(Collection<String> prePlanningQuestions) {
      this.prePlanningQuestions = prePlanningQuestions;
   }

   /**
    * @return the prePlanningRemarks
    */
   public Collection<String> getPrePlanningRemarks() {
      return prePlanningRemarks;
   }

   /**
    * @param prePlanningRemarks the prePlanningRemarks to set
    */
   public void setPrePlanningRemarks(Collection<String> prePlanningRemarks) {
      this.prePlanningRemarks = prePlanningRemarks;
   }

   /**
    * @return the qualifications
    */
   public Collection<KindVo> getQualifications() {
      if (qualifications == null) {
         qualifications = new ArrayList<KindVo>();
      }
      return qualifications;
   }

   /**
    * @param qualifications the qualifications to set
    */
   public void setQualifications(Collection<KindVo> qualifications) {
      this.qualifications = qualifications;
   }

   /**
    * @return the rentalLocation
    */
   public String getRentalLocation() {
      return rentalLocation;
   }

   /**
    * @param rentalLocation the rentalLocation to set
    */
   public void setRentalLocation(String rentalLocation) {
      this.rentalLocation = rentalLocation;
   }

   /**
    * @return the state
    */
   public CountryCodeSubdivisionVo getState() {
      if (state == null) {
         state = new CountryCodeSubdivisionVo();
      }
      return state;
   }

   /**
    * @param state the state to set
    */
   public void setState(CountryCodeSubdivisionVo state) {
      this.state = state;
   }

   /**
    * Convenience method used in transformation.
    * 
    * @param state
    */
   public void setDemobState(CountrySubdivision state){
	   this.state=new CountryCodeSubdivisionVo(state);
   }
   
   /**
    * @return the travelMethod
    */
   public TravelMethodTypeEnum getTravelMethod() {
      return travelMethod;
   }

   /**
    * @param travelMethod the travelMethod to set
    */
   public void setTravelMethod(TravelMethodTypeEnum travelMethod) {
      this.travelMethod = travelMethod;
   }

}
