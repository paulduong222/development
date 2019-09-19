package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapAircraftTask;
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
 * IapAircraftTask entity.
 */
@Entity
@Table(name = "isw_iap_aircraft_task")
@SequenceGenerator(name="SEQ_IAP_AIRCRAFT_TASK", sequenceName="SEQ_IAP_AIRCRAFT_TASK")
public class IapAircraftTaskImpl extends PersistableImpl implements IapAircraftTask {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_AIRCRAFT_TASK")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapForm220Impl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_FORM_220_ID", nullable = false)
	private IapForm220 iapForm220;
	
	@Column(name = "IAP_FORM_220_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapForm220Id;
	
	@Column(name = "TYPE", length = 50)
	private String type;
	
	@Column(name = "NAME", length = 50)
	private String name;
	
	@Column(name = "START_TIME", length = 50)
	private String startTime;
	
	@Column(name = "FLY_FROM", length = 200)
	private String flyFrom;
	
	@Column(name = "FLY_TO", length = 200)
	private String flyTo;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_BLANK_LINE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isBlankLine;
	
	/** 
	 * Default constructor 
	 */
	public IapAircraftTaskImpl() {
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @param flyFrom the flyFrom to set
	 */
	public void setFlyFrom(String flyFrom) {
		this.flyFrom = flyFrom;
	}


	/**
	 * @return the flyFrom
	 */
	public String getFlyFrom() {
		return flyFrom;
	}


	/**
	 * @param flyTo the flyTo to set
	 */
	public void setFlyTo(String flyTo) {
		this.flyTo = flyTo;
	}


	/**
	 * @return the flyTo
	 */
	public String getFlyTo() {
		return flyTo;
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
