package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface IapMasterFrequencyFilter extends Filter {
	
	/**
	 * @return incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return incidentGroupId
	 */
	public Long getIncidentGroupId();
	
	/**
	 * @param incidentGroupId
	 */
	public void setIncidentGroupId(Long incidentGroupId);	
	
	/**
	 * @return show
	 */
	public Boolean getShow();
	
	/**
	 * @param show
	 */
	public void setShow(Boolean show);
	
	/**
	 * @return the system
	 */
	public String getSystem();
	
	/**
	 * @param system
	 */
	public void setSystem(String system);
	
	/**
	 * @return the group
	 */
	public String getGroup();
	
	/**
	 * @param group
	 */
	public void setGroup(String group);
	
	/**
	 * @return the channel
	 */
	public String getChannel();
	
	/**
	 * @param channel
	 */
	public void setChannel(String channel);
	
	/**
	 * @return the rfunction
	 */
	public String getRfunction();
	
	/**
	 * @param rfunction
	 */
	public void setRfunction(String rfunction);
	
	/**
	 * @return the rx
	 */
	public String getRx();
	
	/**
	 * @param rx
	 */
	public void setRx(String rx);
	
	/**
	 * @return the tx
	 */
	public String getTx();
	
	/**
	 * @param tx
	 */
	public void setTx(String tx);
	
	/**
	 * @return the tone
	 */
	public String getTone();
	
	/**
	 * @param tone
	 */
	public void setTone(String tone);
	
	/**
	 * @return the assignment
	 */
	public String getAssignment();
	
	/**
	 * @param assignment
	 */
	public void setAssignment(String assignment);
	
	/**
	 * @return the remarks
	 */
	public String getRemarks();
	
	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks);
		
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


}
