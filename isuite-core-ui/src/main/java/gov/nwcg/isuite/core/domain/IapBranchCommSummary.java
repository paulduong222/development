package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapBranchCommSummary extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param iapBranch the iapBranch to set
	 */
	public void setIapBranch(IapBranch iapBranch);

	/**
	 * @return the iapBranch
	 */
	public IapBranch getIapBranch();

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId);

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId();

	/**
	 * @param function the function to set
	 */
	public void setFunction(String function);

	/**
	 * @return the function
	 */
	public String getFunction();

	/**
	 * @param rx the rx to set
	 */
	public void setRx(String rx);

	/**
	 * @return the rx
	 */
	public String getRx();

	/**
	 * @param tx the tx to set
	 */
	public void setTx(String tx);

	/**
	 * @return the tx
	 */
	public String getTx();

	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone);

	/**
	 * @return the tone
	 */
	public String getTone();

	/**
	 * @param system1 the system1 to set
	 */
	public void setSystem1(String system1);

	/**
	 * @return the system1
	 */
	public String getSystem1();

	/**
	 * @param channel1 the channel1 to set
	 */
	public void setChannel1(String channel1);

	/**
	 * @return the channel1
	 */
	public String getChannel1();

	/**
	 * @param system2 the system2 to set
	 */
	public void setSystem2(String system2);

	/**
	 * @return the system2
	 */
	public String getSystem2();

	/**
	 * @param channel2 the channel2 to set
	 */
	public void setChannel2(String channel2);

	/**
	 * @return the channel2
	 */
	public String getChannel2();

	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(String primaryContact);

	/**
	 * @return the primaryContact
	 */
	public String getPrimaryContact();

	/**
	 * @param cellNbr the cellNbr to set
	 */
	public void setCellNbr(String cellNbr);

	/**
	 * @return the cellNbr
	 */
	public String getCellNbr();

	/**
	 * @param pagerNbr the pagerNbr to set
	 */
	public void setPagerNbr(String pagerNbr);

	/**
	 * @return the pagerNbr
	 */
	public String getPagerNbr();

	/**
	 * @return the rxTone
	 */
	public String getRxTone() ;

	/**
	 * @param rxTone the rxTone to set
	 */
	public void setRxTone(String rxTone);

	/**
	 * @return the txTone
	 */
	public String getTxTone();

	/**
	 * @param txTone the txTone to set
	 */
	public void setTxTone(String txTone) ;

	/**
	 * @return the mode
	 */
	public String getMode() ;

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode);

	/**
	 * @return the masterFreqId
	 */
	public Long getMasterFreqId() ;

	/**
	 * @param masterFreqId the masterFreqId to set
	 */
	public void setMasterFreqId(Long masterFreqId);

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine();

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine);
	
}
