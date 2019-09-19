package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class SystemTypeVo extends AbstractVo{
	
	private String code;
	
	public SystemTypeVo() {
		super();
	}
	
	public SystemTypeVo(Long id, String code) {
		super();
		setId(id);
		setCode(code);
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
}
