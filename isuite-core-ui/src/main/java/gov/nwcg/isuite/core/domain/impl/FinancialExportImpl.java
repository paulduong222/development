/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

/**
 * @author mpaskett
 *
 */
@Entity
@SequenceGenerator(name="SEQ_FINANCIAL_EXPORT", sequenceName="SEQ_FINANCIAL_EXPORT")
@Table(name="isw_financial_export")
public class FinancialExportImpl extends PersistableImpl implements
		FinancialExport {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SEQ_FINANCIAL_EXPORT")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID")
	private Incident incident;
   
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID")
	private IncidentGroup incidentGroup;
   
	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false)
	private Long incidentGroupId;
	
	@Column(name = "EXPORT_DATE")
	private Date exportDate;
	
	@Column(name = "FILE_NAME", length=55)
	private String fileName;
	
	@Column(name = "IS_FROM_SINGLE_INCIDENT", length=1)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFromSingleIncident;
	
	@Column(name = "INCIDENT_REFERENCE_ID")
	private Long incidentReferenceId;
	
	@Column(name = "INCIDENT_NAME", length=200)
	private String incidentName;
	
	
	
	/**
	 * Default Constructor
	 */
	public FinancialExportImpl(){
		super();
	}
	
	 /* 
	    * (non-Javadoc)
	    * @see gov.nwcg.isuite.domain.Persistable#getId()
	    */
	   public Long getId() {
	      return this.id;
	   }

	   /*
	    * (non-Javadoc)
	    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	    */
	   public void setId(Long id) {
	      this.id = id;
	   }

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	
	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the exportDate
	 */
	public Date getExportDate() {
		return exportDate;
	}

	
	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentReferenceId the incidentReferenceId to set
	 */
	public void setIncidentReferenceId(Long incidentReferenceId) {
		this.incidentReferenceId = incidentReferenceId;
	}

	/**
	 * @return the incidentReferenceId
	 */
	public Long getIncidentReferenceId() {
		return incidentReferenceId;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param isFromSingleIncident the isFromSingleIncident to set
	 */
	public void setIsFromSingleIncident(StringBooleanEnum isFromSingleIncident) {
		this.isFromSingleIncident = isFromSingleIncident;
	}

	/**
	 * @return the isFromSingleIncident
	 */
	public StringBooleanEnum getIsFromSingleIncident() {
		return isFromSingleIncident;
	}
	
}
