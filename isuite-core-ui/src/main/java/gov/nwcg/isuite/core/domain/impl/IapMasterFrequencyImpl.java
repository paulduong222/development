package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapMasterFrequency;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemTypeEnum;

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

@Entity
@Table(name = "isw_iap_master_frequency")
@SequenceGenerator(name="SEQ_IAP_MASTER_FREQUENCY", sequenceName="SEQ_IAP_MASTER_FREQUENCY")
public class IapMasterFrequencyImpl extends PersistableImpl implements IapMasterFrequency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_MASTER_FREQUENCY")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name="INCIDENT_ID", insertable=true, updatable=true, nullable=true)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name="INCIDENT_GROUP_ID", insertable=true, updatable=true, nullable=true)
	private IncidentGroup incidentGroup;
	
	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentGroupId;

	@Column(name = "SHOW", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum show;
	
	@Column(name = "SYSTEM", length = 50)
	@Enumerated(EnumType.STRING)
	private SystemTypeEnum system;
	
	@Column(name =  "GROUP_NAME", length = 10)
	private String group;
	
	@Column(name = "CHANNEL", length = 10)
	private String channel;
	
	@Column(name = "RFUNCTION", length = 50)
	private String rfunction;
	
	@Column(name = "RX", length = 50)
	private String rx;
	
	@Column(name = "TX", length = 50)
	private String tx;
	
	@Column(name = "TONE", length = 50)
	private String tone;
	
	@Column(name = "ASSIGNMENT", length = 50)
	private String assignment;
	
	@Column(name = "REMARKS", length = 200)
	private String remarks;
	
	@Column(name = "CHANNEL_NAME_RADIO_TALKGROUP", length = 50)
	private String channelName;
	
	@Column(name = "RX_FREQ_N_W", length = 50)
	private String rxFreq;
	
	@Column(name = "RX_TONE_NAC", length = 50)
	private String rxTone;
	
	@Column(name = "TX_FREQ_N_W", length = 50)
	private String txFreq;
	
	@Column(name = "TX_TONE_NAC", length = 50)
	private String txTone;
	
	@Column(name = "MODE_A_D_M", length = 50)
	private String mode;
	
	@Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;
	
	/**
	 * Default constructor.
	 */
	public IapMasterFrequencyImpl() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return the rfunction
	 */
	public String getRfunction() {
		return rfunction;
	}

	/**
	 * @return rx
	 */
	public String getRx() {
		return rx;
	}

	/**
	 * @return show
	 */
	public StringBooleanEnum getShow() {
		return show;
	}

	/**
	 * @return system
	 */
	public SystemTypeEnum getSystem() {
		return system;
	}

	/**
	 * @return the tone
	 */
	public String getTone() {
		return tone;
	}

	/**
	 * @return tx
	 */
	public String getTx() {
		return tx;
	}

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @param rfunction the rfunction to set
	 */
	public void setRfunction(String rfunction) {
		this.rfunction = rfunction;
	}

	/**
	 * @param rx the rx to set
	 */
	public void setRx(String rx) {
		this.rx = rx;
	}

	/**
	 * @param show the show to set
	 */
	public void setShow(StringBooleanEnum show) {
		this.show = show;
	}

	/**
	 * @param system
	 */
	public void setSystem(SystemTypeEnum system) {
		this.system = system;
	}

	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone) {
		this.tone = tone;
	}

	/**
	 * @param tx the tx to set
	 */
	public void setTx(String tx) {
		this.tx = tx;
	}

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	
	/**
	 * @param channelName
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	  
	/**
	 * @return the rxFreq
	 */
	public String getRxFreq() {
		return rxFreq;
	}
		
	/**
	 * @param rxFreq
	 */
	public void setRxFreq(String rxFreq) {
		this.rxFreq = rxFreq;
	}
	  
	/**
	 * @return the rxTone
	 */
	public String getRxTone() {
		return rxTone;
	}
		
	/**
	 * @param rxTone
	 */
	public void setRxTone(String rxTone) {
		this.rxTone = rxTone;
	}
		  
	 /**
	 * @return the txFreq
	 */
	public String getTxFreq() {
		return txFreq;
	}
		
	/**
	 * @param txFreq
	 */
	public void setTxFreq(String txFreq) {
		this.txFreq = txFreq;
	}
		 
	/**
	 * @return the txTone
	 */
	public String getTxTone() {
		return txTone;
	}
		
	/**
	 * @param txTone
	 */
	public void setTxTone(String txTone) {
		this.txTone = txTone;
	}
	  
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
		
	/**
	 * @param mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

}
