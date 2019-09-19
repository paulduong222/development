package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.core.domain.Kind;
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
 * IapPositionItemCode entity.
 */
@Entity
@Table(name = "isw_branch_setting_position")
@SequenceGenerator(name="SEQ_BRANCH_SETTING_POSITION", sequenceName="SEQ_BRANCH_SETTING_POSITION")
public class BranchSettingPositionImpl extends PersistableImpl implements BranchSettingPosition {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_BRANCH_SETTING_POSITION")
	private Long id = 0L;
	
	@Column(name = "POSITION", nullable = false, length = 50)
	private String position;

	@ManyToOne(targetEntity=KindImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", nullable = false)
	private Kind kind;
	
	@Column(name = "KIND_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long kindId;

	@ManyToOne(targetEntity=BranchSettingImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_SETTING_ID", nullable = true)
	private BranchSetting branchSetting;
	
	@Column(name = "BRANCH_SETTING_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long branchSettingId;
	
	/** 
	 * Default constructor 
	 */
	public BranchSettingPositionImpl() {
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
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the branchSetting
	 */
	public BranchSetting getBranchSetting() {
		return branchSetting;
	}

	/**
	 * @param branchSetting the branchSetting to set
	 */
	public void setBranchSetting(BranchSetting branchSetting) {
		this.branchSetting = branchSetting;
	}

	/**
	 * @return the branchSettingId
	 */
	public Long getBranchSettingId() {
		return branchSettingId;
	}

	/**
	 * @param branchSettingId the branchSettingId to set
	 */
	public void setBranchSettingId(Long branchSettingId) {
		this.branchSettingId = branchSettingId;
	}


}
