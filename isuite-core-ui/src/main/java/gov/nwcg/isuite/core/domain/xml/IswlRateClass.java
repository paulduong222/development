package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswlRateClass", table = "iswl_rate_class")
public class IswlRateClass {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RATE_CLASS", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "RateClassName", sqlname = "RATE_CLASS_NAME", type="STRING")
	private String rateClassName;

	@XmlTransferField(name = "RateClassRate", type="COMPLEX", target=IswlRateClassRate.class
						, lookupname="RateClassId", sourcename="Id"
						, cascade=true)
	private Collection<IswlRateClassRate> rateClassRates = new ArrayList<IswlRateClassRate>();

	@XmlTransferField(name = "RateYear", sqlname = "RATE_YEAR", type="INTEGER")
	private Integer rateYear;

	@XmlTransferField(name = "Standard", sqlname = "IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	public IswlRateClass() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRateClassName() {
		return rateClassName;
	}

	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
	}

	public Integer getRateYear() {
		return rateYear;
	}

	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
	}

	public Boolean isStandard() {
		return standard;
	}

	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @return the iswlRateClassRates
	 */
	public Collection<IswlRateClassRate> getRateClassRates() {
		return rateClassRates;
	}

	/**
	 * @param iswlRateClassRates the iswlRateClassRates to set
	 */
	public void setRateClassRates(
			Collection<IswlRateClassRate> iswlRateClassRates) {
		this.rateClassRates = iswlRateClassRates;
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
}
