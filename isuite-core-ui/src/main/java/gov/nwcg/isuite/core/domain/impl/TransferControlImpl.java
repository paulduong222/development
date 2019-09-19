package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.TransferControl;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@SequenceGenerator(name="SEQ_TRANSFER_CONTROL", sequenceName="SEQ_TRANSFER_CONTROL")
@Table(name="isw_transfer_control")
public class TransferControlImpl extends PersistableImpl implements TransferControl {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TRANSFER_CONTROL")
	private Long id = 0L;

	@Column(name = "START_TIME", nullable=true)
	private Date startTime;
	
	@Column(name = "STOP_TIME", nullable=true)
	private Date stopTime;

	@Column(name = "IS_INCIDENT_GROUP", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isIncidentGroup;

	@Column(name = "INCIDENT_TI", nullable=true)
	private String incidentTI;
	
	@Column(name = "INCIDENT_GROUP_TI", nullable=true)
	private String incidentGroupTI;

	@Column(name = "STATUS", nullable=true)
	private String status;

	//@Lob
	@Column(name = "LAST_ERROR", nullable=true)
	private String lastError;


	/**
	 * Default constructor.
	 *
	 */
	public TransferControlImpl() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getStopTime() {
		return stopTime;
	}


	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public String getIncidentTI() {
		return incidentTI;
	}


	public void setIncidentTI(String incidentTI) {
		this.incidentTI = incidentTI;
	}


	public String getIncidentGroupTI() {
		return incidentGroupTI;
	}


	public void setIncidentGroupTI(String incidentGroupTI) {
		this.incidentGroupTI = incidentGroupTI;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getLastError() {
		return lastError;
	}


	public void setLastError(String lastError) {
		this.lastError = lastError;
	}


	public StringBooleanEnum getIsIncidentGroup() {
		return isIncidentGroup;
	}


	public void setIsIncidentGroup(StringBooleanEnum isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}


}
