package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIapAircraftTask", table = "isw_iap_aircraft_task")
public class IswIapAircraftTask {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_AIRCRAFT_TASK", type="LONG")
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

	@XmlTransferField(name = "Type", sqlname="TYPE", type="STRING")
	private String type;
	
	@XmlTransferField(name = "Name", sqlname="NAME", type="STRING")
	private String name;
	
	@XmlTransferField(name = "StartTime", sqlname="START_TIME", type="STRING")
	private String startTime;
	
	@XmlTransferField(name = "FlyFrom", sqlname="FLY_FROM", type="STRING")
	private String flyFrom;
	
	@XmlTransferField(name = "FlyTo", sqlname="FLY_TO", type="STRING")
	private String flyTo;
	
	@XmlTransferField(name = "PositionNum", sqlname="POSITION_NUM", type="INTEGER")
	private Integer positionNum;

	@XmlTransferField(name = "IsBlankLine", sqlname="IS_BLANK_LINE", type="STRING")
	private String isBlankLine;
	

	/**
	 * Default constructor.
	 * 
	 */
	public IswIapAircraftTask() {
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the flyFrom
	 */
	public String getFlyFrom() {
		return flyFrom;
	}


	/**
	 * @param flyFrom the flyFrom to set
	 */
	public void setFlyFrom(String flyFrom) {
		this.flyFrom = flyFrom;
	}


	/**
	 * @return the flyTo
	 */
	public String getFlyTo() {
		return flyTo;
	}


	/**
	 * @param flyTo the flyTo to set
	 */
	public void setFlyTo(String flyTo) {
		this.flyTo = flyTo;
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
