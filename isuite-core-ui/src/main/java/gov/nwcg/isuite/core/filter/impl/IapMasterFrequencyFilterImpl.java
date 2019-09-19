package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IapMasterFrequencyFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class IapMasterFrequencyFilterImpl extends FilterImpl implements IapMasterFrequencyFilter {

	private static final long serialVersionUID = 2276237907019397936L;

	public IapMasterFrequencyFilterImpl() {
	}
	
	private Long incidentId = 0L;
	private Long incidentGroupId = 0L;
	private Boolean show;
	private String system;
	private String group;
	private String channel;
	private String rfunction;
	private String rx;
	private String tx;
	private String tone;
	private String assignment;
	private String remarks;
	private String channelName;
	private String rxFreq;
	private String rxTone;
	private String txFreq;
	private String txTone;
	private String mode;
	
	/**
	 * @return incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/**
	 * @return incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	
	/**
	 * @param incidentGroupId
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/**
	 * @return show
	 */
	public Boolean getShow() {
		return show;
	}
	
	/**
	 * @param show
	 */
	public void setShow(Boolean show) {
		this.show = show;
	}
	
	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}
	
	/**
	 * @param system
	 */
	public void setSystem(String system) {
		this.system = system;
	}
	
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	
	/**
	 * @param group
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	
	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}
	
	/**
	 * @param channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	/**
	 * @return the rfunction
	 */
	public String getRfunction() {
		return rfunction;
	}
	
	/**
	 * @param rfunction
	 */
	public void setRfunction(String rfunction) {
		this.rfunction = rfunction;
	}
	
	/**
	 * @return the rx
	 */
	public String getRx() {
		return rx;
	}
	
	/**
	 * @param rx
	 */
	public void setRx(String rx) {
		this.rx = rx;
	}
	
	/**
	 * @return the tx
	 */
	public String getTx() {
		return tx;
	}
	
	/**
	 * @param tx
	 */
	public void setTx(String tx) {
		this.tx = tx;
	}
	
	/**
	 * @return the tone
	 */
	public String getTone() {
		return tone;
	}
	
	/**
	 * @param tone
	 */
	public void setTone(String tone) {
		this.tone = tone;
	}
	
	/**
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}
	
	/**
	 * @param assignment
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	
	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
