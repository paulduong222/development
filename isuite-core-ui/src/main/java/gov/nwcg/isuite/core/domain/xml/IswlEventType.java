package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlEventType", table = "iswl_event_type")
public class IswlEventType {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_EVENT_TYPE", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "EventTypeCode", sqlname = "EVENT_TYPE_CODE", type="STRING")
	private String eventTypeCode;

	@XmlTransferField(name = "EventType", sqlname = "EVENT_TYPE", type="STRING")
	private String eventType;


	/**
	 * Default Constructor
	 */
	public IswlEventType() {
		super();
	}

	/**
	 * @return
	 */
	public String getEventTypeCode() {
		return eventTypeCode;
	}

	/**
	 * @param eventTypeCode
	 */
	public void setEventTypeCode(String eventTypeCode) {
		if (eventTypeCode == null) {
			throw new IllegalArgumentException("eventTypeCode can not be null");
		}
		this.eventTypeCode = eventTypeCode;
	}

	/**
	 * @return
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType
	 */
	public void setEventType(String eventType) {
		if (eventType == null) {
			throw new IllegalArgumentException("eventType can not be null");
		}
		this.eventType = eventType;
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
