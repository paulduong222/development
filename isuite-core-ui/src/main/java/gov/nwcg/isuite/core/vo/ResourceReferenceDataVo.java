package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.controllers.restdata.DropdownData;

public class ResourceReferenceDataVo {
	private Collection<DropdownData> agencyDropdownData = new ArrayList<DropdownData>();
	private Collection<DropdownData> kindDropdownData = new ArrayList<DropdownData>();
	private Collection<DropdownData> unitDropdownData = new ArrayList<DropdownData>();
	private Collection<DropdownData> jetportDropdownData = new ArrayList<DropdownData>();
	private Collection<DropdownData> incidentAccountCodeData = new ArrayList<DropdownData>();
	private Collection<DropdownData> incidentData = new ArrayList<DropdownData>();
	private Collection<DropdownData> accrualCodeData = new ArrayList<DropdownData>();
	
	private Collection<SpecialPayVo> specialPayVos = new ArrayList<SpecialPayVo>();
	private Collection<RateClassRateVo> rateClassRateVos = new ArrayList<RateClassRateVo>();
	private Collection<ContractorVo> contractorVos = new ArrayList<ContractorVo>();
	private Collection<KindVo> kindVos = new ArrayList<KindVo>();

	public Collection<DropdownData> getAgencyDropdownData() {
		return agencyDropdownData;
	}
	public void setAgencyDropdownData(Collection<DropdownData> agencyDropdownData) {
		this.agencyDropdownData = agencyDropdownData;
	}
	public Collection<DropdownData> getKindDropdownData() {
		return kindDropdownData;
	}
	public void setKindDropdownData(Collection<DropdownData> kindDropdownData) {
		this.kindDropdownData = kindDropdownData;
	}
	public Collection<DropdownData> getUnitDropdownData() {
		return unitDropdownData;
	}
	public void setUnitDropdownData(Collection<DropdownData> unitDropdownData) {
		this.unitDropdownData = unitDropdownData;
	}
	public Collection<DropdownData> getJetportDropdownData() {
		return jetportDropdownData;
	}
	public void setJetportDropdownData(Collection<DropdownData> jetportDropdownData) {
		this.jetportDropdownData = jetportDropdownData;
	}
	public Collection<DropdownData> getIncidentAccountCodeData() {
		return incidentAccountCodeData;
	}
	public void setIncidentAccountCodeData(Collection<DropdownData> incidentAccountCodeData) {
		this.incidentAccountCodeData = incidentAccountCodeData;
	}
	public Collection<DropdownData> getIncidentData() {
		return incidentData;
	}
	public void setIncidentData(Collection<DropdownData> incidentData) {
		this.incidentData = incidentData;
	}
	public Collection<DropdownData> getAccrualCodeData() {
		return accrualCodeData;
	}
	public void setAccrualCodeData(Collection<DropdownData> accrualCodeData) {
		this.accrualCodeData = accrualCodeData;
	}
	public Collection<SpecialPayVo> getSpecialPayVos() {
		return specialPayVos;
	}
	public void setSpecialPayVos(Collection<SpecialPayVo> specialPayVos) {
		this.specialPayVos = specialPayVos;
	}
	public Collection<RateClassRateVo> getRateClassRateVos() {
		return rateClassRateVos;
	}
	public void setRateClassRateVos(Collection<RateClassRateVo> rateClassRateVos) {
		this.rateClassRateVos = rateClassRateVos;
	}
	public Collection<ContractorVo> getContractorVos() {
		return contractorVos;
	}
	public void setContractorVos(Collection<ContractorVo> contractorVos) {
		this.contractorVos = contractorVos;
	}
	public Collection<KindVo> getKindVos() {
		return kindVos;
	}
	public void setKindVos(Collection<KindVo> kindVos) {
		this.kindVos = kindVos;
	}	
}
