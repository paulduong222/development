package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapAircraftTask extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param type the type to set
	 */
	public void setType(String type);


	/**
	 * @return the type
	 */
	public String getType();


	/**
	 * @param name the name to set
	 */
	public void setName(String name);


	/**
	 * @return the name
	 */
	public String getName();


	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime);


	/**
	 * @return the startTime
	 */
	public String getStartTime();


	/**
	 * @param flyFrom the flyFrom to set
	 */
	public void setFlyFrom(String flyFrom);


	/**
	 * @return the flyFrom
	 */
	public String getFlyFrom();


	/**
	 * @param flyTo the flyTo to set
	 */
	public void setFlyTo(String flyTo);


	/**
	 * @return the flyTo
	 */
	public String getFlyTo();

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
	 * @return the positionNum
	 */
	public Integer getPositionNum();

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum);

	/**
	 * @return the isBlankLine
	 */
	public StringBooleanEnum getIsBlankLine();

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(StringBooleanEnum isBlankLine);
	
}
