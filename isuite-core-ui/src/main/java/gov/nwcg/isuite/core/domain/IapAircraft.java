package gov.nwcg.isuite.core.domain;

import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface IapAircraft extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param wingType the wingType to set
	 */
	public void setWingType(String wingType);

	/**
	 * @return the wingType
	 */
	public String getWingType();

	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(String aircraft);

	/**
	 * @return the aircraft
	 */
	public String getAircraft();

	/**
	 * @param nbrAvailable the nbrAvailable to set
	 */
	public void setNbrAvailable(Integer nbrAvailable);

	/**
	 * @return the nbrAvailable
	 */
	public Integer getNbrAvailable();

	/**
	 * @param type the type to set
	 */
	public void setType(String type);

	/**
	 * @return the type
	 */
	public String getType();

	/**
	 * @param makeModel the makeModel to set
	 */
	public void setMakeModel(String makeModel);

	/**
	 * @return the makeModel
	 */
	public String getMakeModel();

	/**
	 * @param faaNbr the faaNbr to set
	 */
	public void setFaaNbr(String faaNbr);

	/**
	 * @return the faaNbr
	 */
	public String getFaaNbr();

	/**
	 * @param base the base to set
	 */
	public void setBase(String base);

	/**
	 * @return the base
	 */
	public String getBase();

	/**
	 * @param baseFax the baseFax to set
	 */
	public void setBaseFax(String baseFax);

	/**
	 * @return the baseFax
	 */
	public String getBaseFax();

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String available);

	/**
	 * @return the available
	 */
	public String getAvailable();

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime);

	/**
	 * @return the startTime
	 */
	public String getStartTime();

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks);

	/**
	 * @return the remarks
	 */
	public String getRemarks();

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
	 * @return the availableDate
	 */
	public Date getAvailableDate();

	/**
	 * @param availableDate the availableDate to set
	 */
	public void setAvailableDate(Date availableDate);

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
