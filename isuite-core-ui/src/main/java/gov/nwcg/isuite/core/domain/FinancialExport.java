/**
 * 
 */
package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

/**
 * @author mpaskett
 *
 */
public interface FinancialExport extends Persistable {

		/**
		 * @param incident the incident to set
		 */
		public void setIncident(Incident incident);
		
		/**
		 * @return the incident
		 */
		public Incident getIncident();
		
		/**
		 * @param incidentId the incidentId to set
		 */
		public void setIncidentId(Long incidentId);

		/**
		 * @return the incidentId
		 */
		public Long getIncidentId() ;

		/**
		 * @param fileName the fileName to set
		 */
		public void setFileName(String fileName);

		/**
		 * @return the fileName
		 */
		public String getFileName();

		
		/**
		 * @param exportDate the exportDate to set
		 */
		public void setExportDate(Date exportDate);

		/**
		 * @return the exportDate
		 */
		public Date getExportDate();

				
		/**
		 * @param incidentGroup the incidentGroup to set
		 */
		public void setIncidentGroup(IncidentGroup incidentGroup);

		/**
		 * @return the incidentGroup
		 */
		public IncidentGroup getIncidentGroup();

		/**
		 * @param incidentGroupId the incidentGroupId to set
		 */
		public void setIncidentGroupId(Long incidentGroupId);

		/**
		 * @return the incidentGroupId
		 */
		public Long getIncidentGroupId();
		
		/**
		 * @param isFromSingleIncident the isFromSingleIncident to set
		 */
		public void setIsFromSingleIncident(StringBooleanEnum isFromSingleIncident);

		/**
		 * @return the isFromSingleIncident
		 */
		public StringBooleanEnum getIsFromSingleIncident();

		/**
		 * @param incidentReferenceId the incidentReferenceId to set
		 */
		public void setIncidentReferenceId(Long incidentReferenceId);

		/**
		 * @return the incidentReferenceId
		 */
		public Long getIncidentReferenceId();

		/**
		 * @param incidentName the incidentName to set
		 */
		public void setIncidentName(String incidentName);

		/**
		 * @return the incidentName
		 */
		public String getIncidentName();
		
		
		
}
