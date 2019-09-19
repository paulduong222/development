package gov.nwcg.isuite.core.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class GeographicAreaVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Boolean standard;
	
	public GeographicAreaVo() {
		
	}
	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return standard
	 */
	@JsonIgnore
	public Boolean isStandard() {
		return this.standard;
	}
	
	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}
	

}
