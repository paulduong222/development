package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface BranchSettingPosition extends Persistable {
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the position
	 */
	public String getPosition();

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position);
	
	/**
	 * @return the kind
	 */
	public Kind getKind();

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) ;

	/**
	 * @return the kindId
	 */
	public Long getKindId() ;
	
	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) ;

	/**
	 * @return the branchSetting
	 */
	public BranchSetting getBranchSetting();

	/**
	 * @param branchSetting the branchSetting to set
	 */
	public void setBranchSetting(BranchSetting branchSetting);

	/**
	 * @return the branchSettingId
	 */
	public Long getBranchSettingId();

	/**
	 * @param branchSettingId the branchSettingId to set
	 */
	public void setBranchSettingId(Long branchSettingId) ;


	
}