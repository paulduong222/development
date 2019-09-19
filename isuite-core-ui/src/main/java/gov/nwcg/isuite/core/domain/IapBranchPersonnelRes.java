package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapBranchPersonnelRes  extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the iapBranchPersonnel
	 */
	public IapBranchPersonnel getIapBranchPersonnel();

	/**
	 * @param iapBranchPersonnel the iapBranchPersonnel to set
	 */
	public void setIapBranchPersonnel(IapBranchPersonnel iapBranchPersonnel);

	/**
	 * @return the iapBranchPersonnelId
	 */
	public Long getIapBranchPersonnelId();

	/**
	 * @param iapBranchPersonnelId the iapBranchPersonnelId to set
	 */
	public void setIapBranchPersonnelId(Long iapBranchPersonnelId);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() ;

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the isTrainee
	 */
	public StringBooleanEnum getIsTrainee();
	
	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(StringBooleanEnum isTrainee);
	
}
