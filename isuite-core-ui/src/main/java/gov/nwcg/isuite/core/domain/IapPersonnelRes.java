package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapPersonnelRes  extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the iapPersonnel
	 */
	public IapPersonnel getIapPersonnel();

	/**
	 * @param iapPersonnel the iapPersonnel to set
	 */
	public void setIapPersonnel(IapPersonnel iapPersonnel);

	/**
	 * @return the iapPersonnelId
	 */
	public Long getIapPersonnelId();

	/**
	 * @param iapPersonnelId the iapPersonnelId to set
	 */
	public void setIapPersonnelId(Long iapPersonnelId);

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
