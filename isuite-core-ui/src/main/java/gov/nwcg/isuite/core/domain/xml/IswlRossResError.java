package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlRossResError", table = "iswl_ross_res_error")
public class IswlRossResError {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ROSS_RES_ERROR", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	public IswlRossResError() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public Boolean isStandard() {
		return this.standard;
	}

	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

}
