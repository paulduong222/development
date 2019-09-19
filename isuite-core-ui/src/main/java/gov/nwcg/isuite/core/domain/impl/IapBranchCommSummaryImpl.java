package gov.nwcg.isuite.core.domain.impl;

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

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

/**
 * IapBranchCommSummary entity.
 */
@Entity
@Table(name = "isw_iap_branch_comm_summary")
@SequenceGenerator(name="SEQ_IAP_BRANCH_COMM_SUMMARY", sequenceName="SEQ_IAP_BRANCH_COMM_SUMMARY")
public class IapBranchCommSummaryImpl extends PersistableImpl implements IapBranchCommSummary {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_BRANCH_COMM_SUMMARY")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapBranchImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_BRANCH_ID", nullable = false)
	private IapBranch iapBranch;
	
	@Column(name = "IAP_BRANCH_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapBranchId;
	
	@Column(name = "FUNCTION", length = 50)
	private String function;
	
	@Column(name = "RX", length = 50)
	private String rx;
	
	@Column(name = "TX", length = 50)
	private String tx;
	
	@Column(name = "TONE", length = 50)
	private String tone;
	
	@Column(name = "RX_TONE", length = 50)
	private String rxTone;
	
	@Column(name = "TX_TONE", length = 50)
	private String txTone;
	
	@Column(name = "SMODE", length = 50)
	private String mode;

	@Column(name = "SYSTEM_1", length = 50)
	private String system1;
	
	@Column(name = "CHANNEL_1", length = 50)
	private String channel1;
	
	@Column(name = "SYSTEM_2", length = 50)
	private String system2;
	
	@Column(name = "CHANNEL_2", length = 50)
	private String channel2;
	
	@Column(name = "PRIMARY_CONTACT", length = 50)
	private String primaryContact;
	
	@Column(name = "CELL_NBR", length = 20)
	private String cellNbr;
	
	@Column(name = "PAGER_NBR", length = 20)
	private String pagerNbr;

	@Column(name = "MASTER_FREQ_ID")
	private Long masterFreqId;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable = true)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapBranchCommSummaryImpl() {
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
	 * @param iapBranch the iapBranch to set
	 */
	public void setIapBranch(IapBranch iapBranch) {
		this.iapBranch = iapBranch;
	}

	/**
	 * @return the iapBranch
	 */
	public IapBranch getIapBranch() {
		return iapBranch;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * @param rx the rx to set
	 */
	public void setRx(String rx) {
		this.rx = rx;
	}

	/**
	 * @return the rx
	 */
	public String getRx() {
		return rx;
	}

	/**
	 * @param tx the tx to set
	 */
	public void setTx(String tx) {
		this.tx = tx;
	}

	/**
	 * @return the tx
	 */
	public String getTx() {
		return tx;
	}

	/**
	 * @param tone the tone to set
	 */
	public void setTone(String tone) {
		this.tone = tone;
	}

	/**
	 * @return the tone
	 */
	public String getTone() {
		return tone;
	}

	/**
	 * @param system1 the system1 to set
	 */
	public void setSystem1(String system1) {
		this.system1 = system1;
	}

	/**
	 * @return the system1
	 */
	public String getSystem1() {
		return system1;
	}

	/**
	 * @param channel1 the channel1 to set
	 */
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}

	/**
	 * @return the channel1
	 */
	public String getChannel1() {
		return channel1;
	}

	/**
	 * @param system2 the system2 to set
	 */
	public void setSystem2(String system2) {
		this.system2 = system2;
	}

	/**
	 * @return the system2
	 */
	public String getSystem2() {
		return system2;
	}

	/**
	 * @param channel2 the channel2 to set
	 */
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}

	/**
	 * @return the channel2
	 */
	public String getChannel2() {
		return channel2;
	}

	/**
	 * @param primaryContact the primaryContact to set
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}

	/**
	 * @return the primaryContact
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}

	/**
	 * @param cellNbr the cellNbr to set
	 */
	public void setCellNbr(String cellNbr) {
		this.cellNbr = cellNbr;
	}

	/**
	 * @return the cellNbr
	 */
	public String getCellNbr() {
		return cellNbr;
	}

	/**
	 * @param pagerNbr the pagerNbr to set
	 */
	public void setPagerNbr(String pagerNbr) {
		this.pagerNbr = pagerNbr;
	}

	/**
	 * @return the pagerNbr
	 */
	public String getPagerNbr() {
		return pagerNbr;
	}

	/**
	 * @return the rxTone
	 */
	public String getRxTone() {
		return rxTone;
	}

	/**
	 * @param rxTone the rxTone to set
	 */
	public void setRxTone(String rxTone) {
		this.rxTone = rxTone;
	}

	/**
	 * @return the txTone
	 */
	public String getTxTone() {
		return txTone;
	}

	/**
	 * @param txTone the txTone to set
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
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the masterFreqId
	 */
	public Long getMasterFreqId() {
		return masterFreqId;
	}

	/**
	 * @param masterFreqId the masterFreqId to set
	 */
	public void setMasterFreqId(Long masterFreqId) {
		this.masterFreqId = masterFreqId;
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

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

}
