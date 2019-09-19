package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "DataTransferIncGroup", table = "datatransferincgrp")
public class DataTransferIncGroup {

	@XmlTransferField(name = "Id", sqlname="", type="LONG")
	private Long id;
	
	@XmlTransferField(name = "Name", sqlname="", type="STRING")
	private String name;

	@XmlTransferField(name = "TI", sqlname="", type="STRING")
	private String tI;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
	}
	
	
}
