package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswIapAircraft", table = "isw_iap_aircraft")
public class IswIapAircraft {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_AIRCRAFT", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapForm220TransferableIdentity", alias="fti2", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapForm220Id"
		, disjoined=true, disjoinedtable="isw_iap_form_220", disjoinedfield="transferable_identity",disjoinedsource="iap_form_220_id")
	private String iapForm220TransferableIdentity;

	@XmlTransferField(name = "IapForm220Id", sqlname="IAP_FORM_220_ID", type="LONG"
		,derived=true,derivedfield="IapForm220TransferableIdentity")
	private Long iapForm220Id;
	
	@XmlTransferField(name = "WingType", sqlname="WING_TYPE", type="STRING")
	private String wingType;
	
	@XmlTransferField(name = "Aircraft", sqlname="AIRCRAFT", type="STRING")
	private String aircraft;
	
	@XmlTransferField(name = "NbrAvailable", sqlname="NBR_AVAILABLE", type="INTEGER")
	private Integer nbrAvailable;
	
	@XmlTransferField(name = "Type", sqlname="TYPE", type="STRING")
	private String type;
	
	@XmlTransferField(name = "MakeModel", sqlname="MAKE_MODEL", type="STRING")
	private String makeModel;
	
	@XmlTransferField(name = "FaaNbr", sqlname="FAA_NBR", type="STRING")
	private String faaNbr;
	
	@XmlTransferField(name = "Base", sqlname="BASE", type="STRING")
	private String base;
	
	@XmlTransferField(name = "BaseFax", sqlname="BASE_FAX", type="STRING")
	private String baseFax;
	
	@XmlTransferField(name = "Available", sqlname="AVAILABLE", type="STRING")
	private String available;
	
	@XmlTransferField(name = "StartTime", sqlname="START_TIME", type="STRING")
	private String startTime;
	
	@XmlTransferField(name = "Remarks", sqlname="REMARKS", type="STRING")
	private String remarks;
	
	@XmlTransferField(name = "AvailableDate", sqlname="AVAILABLE_DATE", type="DATE")
	private Date availableDate;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAircraft() {
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
	 * @return the iapForm220TransferableIdentity
	 */
	public String getIapForm220TransferableIdentity() {
		return iapForm220TransferableIdentity;
	}

	/**
	 * @param iapForm220TransferableIdentity the iapForm220TransferableIdentity to set
	 */
	public void setIapForm220TransferableIdentity(
			String iapForm220TransferableIdentity) {
		this.iapForm220TransferableIdentity = iapForm220TransferableIdentity;
	}

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
	}

	/**
	 * @return the wingType
	 */
	public String getWingType() {
		return wingType;
	}

	/**
	 * @param wingType the wingType to set
	 */
	public void setWingType(String wingType) {
		this.wingType = wingType;
	}

	/**
	 * @return the aircraft
	 */
	public String getAircraft() {
		return aircraft;
	}

	/**
	 * @param aircraft the aircraft to set
	 */
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	/**
	 * @return the nbrAvailable
	 */
	public Integer getNbrAvailable() {
		return nbrAvailable;
	}

	/**
	 * @param nbrAvailable the nbrAvailable to set
	 */
	public void setNbrAvailable(Integer nbrAvailable) {
		this.nbrAvailable = nbrAvailable;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the makeModel
	 */
	public String getMakeModel() {
		return makeModel;
	}

	/**
	 * @param makeModel the makeModel to set
	 */
	public void setMakeModel(String makeModel) {
		this.makeModel = makeModel;
	}

	/**
	 * @return the faaNbr
	 */
	public String getFaaNbr() {
		return faaNbr;
	}

	/**
	 * @param faaNbr the faaNbr to set
	 */
	public void setFaaNbr(String faaNbr) {
		this.faaNbr = faaNbr;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the baseFax
	 */
	public String getBaseFax() {
		return baseFax;
	}

	/**
	 * @param baseFax the baseFax to set
	 */
	public void setBaseFax(String baseFax) {
		this.baseFax = baseFax;
	}

	/**
	 * @return the available
	 */
	public String getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(String available) {
		this.available = available;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the availableDate
	 */
	public Date getAvailableDate() {
		return availableDate;
	}

	/**
	 * @param availableDate the availableDate to set
	 */
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the isBlankLine
	 */
	public String getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(String isBlankLine) {
		this.isBlankLine = isBlankLine;
	}



}
