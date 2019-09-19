package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface AccrualCode extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId();


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;


	/**
	 * @return the code
	 */
	public String getCode() ;


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) ;


	/**
	 * @return the description
	 */
	public String getDescription() ;

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) ;


	/**
	 * @return the standard
	 */
	public Boolean getStandard();


	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard);


	/**
	 * @return the costDatas
	 */
	public Collection<CostData> getCostDatas();


	/**
	 * @param costDatas the costDatas to set
	 */
	public void setCostDatas(Collection<CostData> costDatas);

	/**
	 * @return the rcLineNumber
	 */
	public String getRcLineNumber();

	/**
	 * @param rcLineNumber the rcLineNumber to set
	 */
	public void setRcLineNumber(String rcLineNumber);

	/**
	 * @return the reportable
	 */
	public StringBooleanEnum getReportable();

	/**
	 * @param reportable the reportable to set
	 */
	public void setReportable(StringBooleanEnum reportable);
	
}
