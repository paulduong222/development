package gov.nwcg.isuite.core.domain.impl;

import java.util.Date;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

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

/**
 * IapAircraft entity.
 */
@Entity
@Table(name = "isw_iap_aircraft")
@SequenceGenerator(name="SEQ_IAP_AIRCRAFT", sequenceName="SEQ_IAP_AIRCRAFT")
public class IapAircraftImpl extends PersistableImpl implements IapAircraft {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_AIRCRAFT")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapForm220Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_220_ID", nullable = false)
	private IapForm220 iapForm220;
	
	@Column(name = "IAP_FORM_220_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm220Id;
	
	@Column(name = "WING_TYPE", length = 50)
	private String wingType;
	
	@Column(name = "AIRCRAFT", length = 50)
	private String aircraft;
	
	@Column(name = "NBR_AVAILABLE")
	private Integer nbrAvailable;
	
	@Column(name = "TYPE", length = 50)
	private String type;
	
	@Column(name = "MAKE_MODEL", length = 50)
	private String makeModel;
	
	@Column(name = "FAA_NBR", length = 50)
	private String faaNbr;
	
	@Column(name = "BASE", length = 50)
	private String base;
	
	@Column(name = "BASE_FAX", length = 50)
	private String baseFax;
	
	@Column(name = "AVAILABLE", length = 50)
	private String available;
	
	@Column(name = "START_TIME", length = 50)
	private String startTime;
	
	@Column(name = "REMARKS", length = 200)
	private String remarks;
	
	@Column(name = "AVAILABLE_DATE")
	private Date availableDate;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapAircraftImpl() {
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
	 * @param wingType the wingType to set
	 */
	public void setWingType(String wingType) {
		this.wingType = wingType;
	}

	/**
	 * @return the wingType
	 */
	public String getWingType() {
		return wingType;
	}

	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	/**
	 * @return the aircraft
	 */
	public String getAircraft() {
		return aircraft;
	}

	/**
	 * @param nbrAvailable the nbrAvailable to set
	 */
	public void setNbrAvailable(Integer nbrAvailable) {
		this.nbrAvailable = nbrAvailable;
	}

	/**
	 * @return the nbrAvailable
	 */
	public Integer getNbrAvailable() {
		return nbrAvailable;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param makeModel the makeModel to set
	 */
	public void setMakeModel(String makeModel) {
		this.makeModel = makeModel;
	}

	/**
	 * @return the makeModel
	 */
	public String getMakeModel() {
		return makeModel;
	}

	/**
	 * @param faaNbr the faaNbr to set
	 */
	public void setFaaNbr(String faaNbr) {
		this.faaNbr = faaNbr;
	}

	/**
	 * @return the faaNbr
	 */
	public String getFaaNbr() {
		return faaNbr;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param baseFax the baseFax to set
	 */
	public void setBaseFax(String baseFax) {
		this.baseFax = baseFax;
	}

	/**
	 * @return the baseFax
	 */
	public String getBaseFax() {
		return baseFax;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String available) {
		this.available = available;
	}

	/**
	 * @return the available
	 */
	public String getAvailable() {
		return available;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
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
	 * @return the availableDate
	 */
	public Date getAvailableDate() {
		return availableDate;
	}

	/**
	 * @param availableDate the availableDate to set
	 */
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
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
