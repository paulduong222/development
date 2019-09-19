package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IapBranchPosItemCode extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind);

	/**
	 * @return the kind
	 */
	public Kind getKind();

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId);

	/**
	 * @return the kindId
	 */
	public Long getKindId();

	/**
	 * @param incidentBranch the incidentBranch to set
	 */
	public void setIncidentBranch(IncidentBranch incidentBranch);

	/**
	 * @return the incidentBranch
	 */
	public IncidentBranch getIncidentBranch();

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Long branchId);

	/**
	 * @return the branchId
	 */
	public Long getBranchId();

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position);

	/**
	 * @return the position
	 */
	public String getPosition();

	/**
	 * @param form the form to set
	 */
	public void setForm(String form);

	/**
	 * @return the form
	 */
	public String getForm();

}
