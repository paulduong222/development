package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IapAircraftFrequency extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency);

	/**
	 * @return the frequency
	 */
	public String getFrequency();

	/**
	 * @param amRxTx the amRxTx to set
	 */
	public void setAmRxTx(String amRxTx);

	/**
	 * @return the amRxTx
	 */
	public String getAmRxTx();

	/**
	 * @param fmRxTx the fmRxTx to set
	 */
	public void setFmRxTx(String fmRxTx);

	/**
	 * @return the fmRxTx
	 */
	public String getFmRxTx();

	/**
	 * @return the iapForm220
	 */
	public IapForm220 getIapForm220();


	/**
	 * @param iapForm220 the iapForm220 to set
	 */
	public void setIapForm220(IapForm220 iapForm220);


	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id();


	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id);

	/**
	 * @return the amTone
	 */
	public String getAmTone();

	/**
	 * @param amTone the amTone to set
	 */
	public void setAmTone(String amTone);

	/**
	 * @return the fmTone
	 */
	public String getFmTone();

	/**
	 * @param fmTone the fmTone to set
	 */
	public void setFmTone(String fmTone);
	
}
