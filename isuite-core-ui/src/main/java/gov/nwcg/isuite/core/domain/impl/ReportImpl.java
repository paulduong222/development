package gov.nwcg.isuite.core.domain.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

/**
 * The report implementation
 * 
 * @author aroundy
 *
 */
@Entity
@SequenceGenerator(name="SEQ_REPORT", sequenceName="SEQ_REPORT")
@Table(name="isw_report")
public class ReportImpl extends PersistableImpl implements Report {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_REPORT")
	private Long id =0L;
	
	@Column(name="DATE_GENERATED")
	private Date dateGenerated;
	
	@Column(name="DATE_REQUESTED")
	private Date dateRequested;
	
	@Column(name="ERROR_DESC", length=4000, nullable=true)
	private String errorDesc;
	
	@Column(name="FILE_NAME", length=1024, nullable=true)
	private String fileName;
	
	@Column(name="REPORT_NAME", length=32, nullable=true)
	private String reportName;
	
	@Column(name="RESULT_CODE", length=30, nullable=true)
	private String resultCode;
	
	@ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=false)
	private User user;

	@Column(name="USER_ID", insertable=false, updatable=false, unique=false, nullable=false)
	private Long userId;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="INCIDENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Incident incident;
	
	@Column(name="INCIDENT_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="INCIDENT_GROUP_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private IncidentGroup incidentGroup;
	
	@Column(name="INCIDENT_GROUP_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long incidentGroupId;
	
	@Column(name="ORIGINAL_REPORT_ID", insertable=false, updatable=false, unique=false, nullable=true)
	private Long originalReportId;
	
	@ManyToOne(targetEntity=ReportImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="ORIGINAL_REPORT_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Report originalReport;
	
	public ReportImpl() {	
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getDateGenerated()
	 */
	public Date getDateGenerated() {
		return dateGenerated;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setDateGenerated(java.util.Date)
	 */
	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getDateRequested()
	 */
	public Date getDateRequested() {
		return dateRequested;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setDateRequested(java.util.Date)
	 */
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getErrorDesc()
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setErrorDesc(java.lang.String)
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getFileName()
	 */
	public String getFileName() {
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setFileName(java.lang.String)
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getReportName()
	 */
	public String getReportName() {
		return reportName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setReportName(java.lang.String)
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getResultCode()
	 */
	public String getResultCode() {
		return resultCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setResultCode(java.lang.String)
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getUser()
	 */
	public User getUser() {
		return user;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setUser(java.lang.Long)
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#getUserId()
	 */
	public Long getUserId() {
		return userId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Report#setUserId(java.lang.Long)
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
	public Long getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		ReportImpl o = (ReportImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,dateGenerated,dateRequested,errorDesc,fileName,reportName,resultCode,userId	},
				new Object[]{o.id,o.dateGenerated,o.dateRequested,o.errorDesc,o.fileName,o.reportName,o.resultCode,o.userId	})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,dateGenerated,dateRequested,errorDesc,fileName,reportName,resultCode,userId	})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("code", dateGenerated)
		.append("dateRequested", dateRequested)
		.append("errorDesc", errorDesc)
		.append("fileName", fileName)
		.append("reportName", reportName)
		.append("resultCode", resultCode)
		.append("userId", userId)
		.appendSuper(super.toString())
		.toString();
	}
	
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Long getOriginalReportId() {
		return originalReportId;
	}

	public void setOriginalReportId(Long originalReportId) {
		this.originalReportId = originalReportId;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	
	public Incident getIncident() {
		return incident;
	}

	
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	public Report getOriginalReport() {
		return originalReport;
	}

	public void setOriginalReport(Report originalReport) {
		this.originalReport = originalReport;
	}

	
}
