package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;

@XmlTransferTable(name = "IswlRateClassRate", table="iswl_rate_class_rate")
public class IswlRateClassRate {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RATE_CLASS_RATE", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "RateClassId", sqlname = "RATE_CLASS_ID", type="LONG")
	private Long rateClassId;

	@XmlTransferField(name = "Area", sqlname = "AREA", type="STRING")
	private String area;

	@XmlTransferField(name = "Rate", sqlname = "RATE", type="BIGDECIMAL")
	private BigDecimal rate;

	@XmlTransferField(name = "RateYear", sqlname = "RATE_YEAR", type="INTEGER")
	private Integer rateYear;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "RateClassName", sqlname = "RATE_CLASSNAME", type="STRING")
	private String rateClassName;

	@XmlTransferField(name = "RateClassCode", sqlname = "RATE_CLASSCODE", type="STRING")
	private String rateClassCode;
	
	@XmlTransferField(name = "Active", sqlname = "IS_ACTIVE", type="STRING")
    private String active;
	
	public IswlRateClassRate() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * @return the rateYear
	 */
	public Integer getRateYear() {
		return rateYear;
	}

	/**
	 * @param rateYear the rateYear to set
	 */
	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @param standard the standard to set
	 */
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the rateClassName
	 */
	public String getRateClassName() {
		return rateClassName;
	}

	/**
	 * @param rateClassName the rateClassName to set
	 */
	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
	}

	/**
	 * @return the rateClassCode
	 */
	public String getRateClassCode() {
		return rateClassCode;
	}

	/**
	 * @param rateClassCode the rateClassCode to set
	 */
	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the rateClassId
	 */
	public Long getRateClassId() {
		return rateClassId;
	}

	/**
	 * @param rateClassId the rateClassId to set
	 */
	public void setRateClassId(Long rateClassId) {
		this.rateClassId = rateClassId;
	}

}
