package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchPersonnelRes;
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
 * IapBranchPersonnelRes entity.
 */
@Entity
@Table(name = "isw_iap_branch_personnel_res")
@SequenceGenerator(name="SEQ_IAP_BRANCH_PERSONNEL_RES", sequenceName="SEQ_IAP_BRANCH_PERSONNEL_RES")
public class IapBranchPersonnelResImpl extends PersistableImpl implements IapBranchPersonnelRes {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_BRANCH_PERSONNEL_RES")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IapBranchPersonnelImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_BRANCH_PERSONNEL_ID", nullable = true)
	private IapBranchPersonnel iapBranchPersonnel;
	
	@Column(name = "IAP_BRANCH_PERSONNEL_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long iapBranchPersonnelId;
	
	@Column(name = "NAME", length = 200)
	private String name;
	
    @Column(name = "POSITION_NUM", nullable = true)
	private Integer positionNum;

	@Column(name = "IS_TRAINEE", nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isTrainee;
	
	/** 
	 * Default constructor 
	 */
	public IapBranchPersonnelResImpl() {
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
	 * @return the iapBranchPersonnel
	 */
	public IapBranchPersonnel getIapBranchPersonnel() {
		return iapBranchPersonnel;
	}

	/**
	 * @param iapBranchPersonnel the iapBranchPersonnel to set
	 */
	public void setIapBranchPersonnel(IapBranchPersonnel iapBranchPersonnel) {
		this.iapBranchPersonnel = iapBranchPersonnel;
	}

	/**
	 * @return the iapBranchPersonnelId
	 */
	public Long getIapBranchPersonnelId() {
		return iapBranchPersonnelId;
	}

	/**
	 * @param iapBranchPersonnelId the iapBranchPersonnelId to set
	 */
	public void setIapBranchPersonnelId(Long iapBranchPersonnelId) {
		this.iapBranchPersonnelId = iapBranchPersonnelId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the isTrainee
	 */
	public StringBooleanEnum getIsTrainee() {
		return isTrainee;
	}

	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(StringBooleanEnum isTrainee) {
		this.isTrainee = isTrainee;
	}

	

}
