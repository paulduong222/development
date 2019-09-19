package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;


public interface KindAltDesc extends Persistable {
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the id
	 */
	public Long getId();
	
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
	 * @param description the description to set
	 */
	public void setDescription(String description);

	/**
	 * @return the description
	 */
	public String getDescription();

}
