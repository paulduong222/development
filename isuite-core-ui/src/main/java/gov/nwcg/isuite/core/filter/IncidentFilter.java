package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.core.vo.IncidentRestrictedStatusVo;
import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.Collection;
import java.util.Date;

public interface IncidentFilter extends Filter {
   
   public Long getId();
   
   public void setId(Long id);
      /**
    * @return the agency
    */
   public String getAgency();
   
   /**
    * @param agency
    */
   public void setAgency(String agency);
   
   /**
    * @return the countrySubdivision
    */
   public String getCountrySubdivision();
   
   /**
    * @param countrySubdivision
    */
   public void setCountrySubdivision(String countrySubdivision);
   
   /**
    * @return the eventType
    */
   public String getEventType();
   
   /**
    * @param eventType
    */
   public void setEventType(String eventType);
   
   /**
    * @return the homeUnit
    */
   public String getHomeUnit();
   
   /**
    * @param homeUnit
    */
   public void setHomeUnit(String homeUnit);
   
   /**
    * @return the incidentName
    */
   public String getIncidentName();
   
   /**
    * @param incidentName
    */
   public void setIncidentName(String incidentName);
   
   /**
    * @return the incidentNumber
    */
   public String getIncidentNumber();
   
   /**
    * @param incidentNumber
    */
   public void setIncidentNumber(String incidentNumber);
   
   /**
    * @return the incidentStartDate
    */
   public Date getIncidentStartDate();
   
   /**
    * @return deletable
    */
   public Boolean getDeletable();
   
   /**
    * @param deletable
    */
   public void setDeletable(Boolean deletable);
   
   /**
    * Returns the riuType.
    *
    * @return 
    *		the riuType to return
    */
   public String getRiuType() ;

   /**
    * Sets the riuType.
    *
    * @param riuType 
    *			the riuType to set
    */
   public void setRiuType(String riuType) ;

   /**
    * Returns the defaultAccountingCode.
    *
    * @return 
    *		the defaultAccountingCode to return
    */
   public String getDefaultAccountingCode() ;

   /**
    * Sets the defaultAccountingCode.
    *
    * @param defaultAccountingCode 
    *			the defaultAccountingCode to set
    */
   public void setDefaultAccountingCode(String defaultAccountingCode) ;
   
   public void setRestricted(Boolean val);
   
   public Boolean getRestricted();
   
   /**
	 * Sets the incidentRestrictedStatuses.
	 *
	 * @param incidentRestrictedStatuses 
	 *			the incidentRestrictedStatuses to set
	 */
   public void setIncidentRestrictedStatuses(Collection<IncidentRestrictedStatusVo> incidentRestrictedStatuses);
   
   /**
	 * Returns the incidentRestrictedStatuses.
	 *
	 * @return 
	 *		the incidentRestrictedStatuses to return
	 */
   public Collection<IncidentRestrictedStatusVo> getIncidentRestrictedStatuses();
 
   public void setRossIncId(Long id);
   
   public Long getRossIncId();

   /**
    * @return the rossIncidentsOnly
    */
   public Boolean getRossIncidentsOnly();

   /**
    * @param rossIncidentsOnly the rossIncidentsOnly to set
    */
   public void setRossIncidentsOnly(Boolean rossIncidentsOnly);

   /**
    * @return the rossIncidentId
    */
   public String getRossIncidentId();

   /**
    * @param rossIncidentId the rossIncidentId to set
    */
   public void setRossIncidentId(String rossIncidentId);
   
   /**
    * 
    * @param startDateString
    */
   public void setStartDateString(String startDateString);
   
   /**
    * 
    * @return
    */
   public String getStartDateString();
   
	public String getIncidentTagNumber() ;

	public void setIncidentTagNumber(String incidentTagNumber) ;

	/**
	 * @return the incidentYear
	 */
	public Integer getIncidentYear();

	/**
	 * @param incidentYear the incidentYear to set
	 */
	public void setIncidentYear(Integer incidentYear);

}
