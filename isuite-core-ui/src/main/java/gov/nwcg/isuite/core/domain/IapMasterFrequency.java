package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemTypeEnum;

public interface IapMasterFrequency extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return the incident
	 */
	public Incident getIncident();
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * @return show
	 */
	public StringBooleanEnum getShow();
	
	/**
	 * @param show the show to set
	 */
	public void setShow(StringBooleanEnum show);
	
	/**
	 * @return system
	 */
	public SystemTypeEnum getSystem();
	
	/**
	 * @param system
	 */
	public void setSystem(SystemTypeEnum system);
	
	/**
	 * @return the group
	 */
	public String getGroup();
	
	/**
	 * @param group the group to set
	 */
	public void setGroup(String Group);
	
	/**
	 * @return the channel
	 */
	public String getChannel();
	
	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel);
	
	/**
	 * @return the rfunction
	 */
	public String getRfunction();
	
	/**
	 * @param rfunction the rfunction to set
	 */
	public void setRfunction(String rfunction);
	
	/**
	 * @return rx
	 */
	public String getRx();
	
	/**
	 * @param Rx the rx to set
	 */
	public void setRx(String Rx);
	
	/**
	 * @return tx
	 */
	public String getTx();
	
	/**
	 * @param Tx the tx to set
	 */
	public void setTx(String Tx);
	
	/**
	 * @return the tone
	 */
	public String getTone();
	
	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone);
	
	/**
	 * @return the assignment
	 */
	public String getAssignment();
	
	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(String assignment);
	
	/**
	 * @return the remarks
	 */
	public String getRemarks();
	
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks);

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);	
	
	/**
	 * @return the channelName
	 */
	public String getChannelName();
	
	/**
	 * @param channelName
	 */
	public void setChannelName(String channelName);  
	  
	/**
	 * @return the rxFreq
	 */
	public String getRxFreq();
		
	/**
	 * @param rxFreq
	 */
	public void setRxFreq(String rxFreq);	
	  
	/**
	 * @return the rxTone
	 */
	public String getRxTone();
		
	/**
	 * @param rxTone
	 */
	public void setRxTone(String rxTone);
		  
	 /**
	 * @return the txFreq
	 */
	public String getTxFreq();
		
	/**
	 * @param txFreq
	 */
	public void setTxFreq(String txFreq);
		 
	/**
	 * @return the txTone
	 */
	public String getTxTone();
		
	/**
	 * @param txTone
	 */
	public void setTxTone(String txTone);
	  
	/**
	 * @return the mode
	 */
	public String getMode();
		
	/**
	 * @param mode
	 */
	public void setMode(String mode);
	
	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum(); 

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum); 	
	
}
