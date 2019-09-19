package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswIncidentContractor", table="isw_incident_contractor",jointable=true)
public class IswIncidentContractor {
	
	@XmlTransferField(name = "IncidentTransferableIdentity", alias="iti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="IncidentId"
						, disjoined=true, disjoinedtable="isw_incident", disjoinedfield="transferable_identity",disjoinedsource="incident_id")
    private String incidentTransferableIdentity;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG"
						,joinkeyprimary=true)
	private Long incidentId;

	@XmlTransferField(name = "ContractorTransferableIdentity", alias="cti", type="STRING"
					, lookupname="TransferableIdentity", sourcename="ContractorId"
					, disjoined=true, disjoinedtable="isw_contractor", disjoinedfield="transferable_identity",disjoinedsource="contractor_id")
	private String contractorTransferableIdentity;
	
	@XmlTransferField(name = "Contractor", type="COMPLEX", target=IswContractor.class
						, lookupname="Id", sourcename="ContractorId")
	private IswContractor contractor;
	
	@XmlTransferField(name = "ContractorId", sqlname="CONTRACTOR_ID", type="LONG"
						,derived=true, derivedfield="Contractor"
							,joinkeysecondary=true)
	private Long contractorId;
	
	

    public IswIncidentContractor(){
    }

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the contractor
	 */
	public IswContractor getContractor() {
		return contractor;
	}



	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(IswContractor contractor) {
		this.contractor = contractor;
	}



	/**
	 * @return the contractorId
	 */
	public Long getContractorId() {
		return contractorId;
	}



	/**
	 * @param contractorId the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	/**
	 * @return the contractorTransferableIdentity
	 */
	public String getContractorTransferableIdentity() {
		return contractorTransferableIdentity;
	}

	/**
	 * @param contractorTransferableIdentity the contractorTransferableIdentity to set
	 */
	public void setContractorTransferableIdentity(
			String contractorTransferableIdentity) {
		this.contractorTransferableIdentity = contractorTransferableIdentity;
	}

	/**
	 * @return the incidentTransferableIdentity
	 */
	public String getIncidentTransferableIdentity() {
		return incidentTransferableIdentity;
	}

	/**
	 * @param incidentTransferableIdentity the incidentTransferableIdentity to set
	 */
	public void setIncidentTransferableIdentity(String incidentTransferableIdentity) {
		this.incidentTransferableIdentity = incidentTransferableIdentity;
	}

}
