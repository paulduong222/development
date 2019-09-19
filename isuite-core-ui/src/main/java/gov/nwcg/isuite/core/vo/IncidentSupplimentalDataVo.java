package gov.nwcg.isuite.core.vo;

import java.util.Collection;

public class IncidentSupplimentalDataVo {
	private Collection<ContractorVo> contractorVos;
	private Collection<KindVo> kindVos;
	private Collection<JetPortVo> jetPortVos;
	private Collection<OrganizationVo> orgVos;
	private Collection<AgencyVo> agencyVos;
	
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

	public Collection<JetPortVo> getJetPortVos() {
		return jetPortVos;
	}

	public void setJetPortVos(Collection<JetPortVo> jetPortVos) {
		this.jetPortVos = jetPortVos;
	}

	public Collection<OrganizationVo> getOrgVos() {
		return orgVos;
	}

	public void setOrgVos(Collection<OrganizationVo> orgVos) {
		this.orgVos = orgVos;
	}

	public Collection<AgencyVo> getAgencyVos() {
		return agencyVos;
	}

	public void setAgencyVos(Collection<AgencyVo> agencyVos) {
		this.agencyVos = agencyVos;
	}
	
	
}
