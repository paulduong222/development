package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapAircraftFrequency;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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

/**
 * IapAircraftFrequency entity.
 */
@Entity
@Table(name = "isw_iap_aircraft_frequency")
@SequenceGenerator(name="SEQ_IAP_AIRCRAFT_FREQUENCY", sequenceName="SEQ_IAP_AIRCRAFT_FREQUENCY")
public class IapAircraftFrequencyImpl extends PersistableImpl implements IapAircraftFrequency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_AIRCRAFT_FREQUENCY")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapForm220Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_220_ID", nullable = false)
	private IapForm220 iapForm220;
	
	@Column(name = "IAP_FORM_220_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm220Id;
	
	@Column(name = "FREQUENCY", length = 50)
	private String frequency;
	
	@Column(name = "AM_RX_TX", length = 50)
	private String amRxTx;
	
	@Column(name = "FM_RX_TX", length = 50)
	private String fmRxTx;
	
	@Column(name = "AM_TONE", length = 20)
	private String amTone;
	
	@Column(name = "FM_TONE", length = 20)
	private String fmTone;
	
	/** 
	 * Default constructor 
	 */
	public IapAircraftFrequencyImpl() {
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
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param amRxTx the amRxTx to set
	 */
	public void setAmRxTx(String amRxTx) {
		this.amRxTx = amRxTx;
	}

	/**
	 * @return the amRxTx
	 */
	public String getAmRxTx() {
		return amRxTx;
	}

	/**
	 * @param fmRxTx the fmRxTx to set
	 */
	public void setFmRxTx(String fmRxTx) {
		this.fmRxTx = fmRxTx;
	}

	/**
	 * @return the fmRxTx
	 */
	public String getFmRxTx() {
		return fmRxTx;
	}

	/**
	 * @return the iapForm220
	 */
	public IapForm220 getIapForm220() {
		return iapForm220;
	}

	/**
	 * @param iapForm220 the iapForm220 to set
	 */
	public void setIapForm220(IapForm220 iapForm220) {
		this.iapForm220 = iapForm220;
	}

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
	}

	/**
	 * @return the amTone
	 */
	public String getAmTone() {
		return amTone;
	}

	/**
	 * @param amTone the amTone to set
	 */
	public void setAmTone(String amTone) {
		this.amTone = amTone;
	}

	/**
	 * @return the fmTone
	 */
	public String getFmTone() {
		return fmTone;
	}

	/**
	 * @param fmTone the fmTone to set
	 */
	public void setFmTone(String fmTone) {
		this.fmTone = fmTone;
	}

}
