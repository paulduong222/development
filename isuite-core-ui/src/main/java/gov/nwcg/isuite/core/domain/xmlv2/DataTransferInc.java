package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "DataTransferInc", table = "datatransferinc")
public class DataTransferInc {

	@XmlTransferField(name = "Id", sqlname="", type="LONG")
	private Long id;
	
	@XmlTransferField(name = "Name", sqlname="", type="STRING")
	private String name;
	
	@XmlTransferField(name = "Nbr", sqlname="", type="STRING")
	private String nbr;

	@XmlTransferField(name = "UnitCode", sqlname="", type="STRING")
	private String unitCode;

	@XmlTransferField(name = "TI", sqlname="", type="STRING")
	private String tI;

	@XmlTransferField(name = "HomeUnitTI", sqlname="", type="STRING")
	private String homeUnitTI;
	
	@XmlTransferField(name = "Year", sqlname="", type="INTEGER")
	private Integer year;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNbr() {
		return nbr;
	}

	public void setNbr(String nbr) {
		this.nbr = nbr;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getTI() {
		return tI;
	}

	public void setTI(String ti) {
		tI = ti;
	}

	public String getHomeUnitTI() {
		return homeUnitTI;
	}

	public void setHomeUnitTI(String homeUnitTI) {
		this.homeUnitTI = homeUnitTI;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	
}
