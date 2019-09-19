package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class SectionVo extends AbstractVo {
	
	private String code;
	private String description;
	
	public SectionVo() {
		super();
	}
	
	public SectionVo(Long id, String code, String description) {
		super();
		setId(id);
		setCode(code);
		setDescription(description);
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code=code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
