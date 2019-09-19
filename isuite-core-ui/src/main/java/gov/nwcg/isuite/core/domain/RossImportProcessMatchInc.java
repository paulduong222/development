package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossImportProcessMatchInc extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;
	
	/**
	 * @return the rossImportProcess
	 */
	public RossImportProcess getRossImportProcess() ;

	/**
	 * @param rossImportProcess the rossImportProcess to set
	 */
	public void setRossImportProcess(RossImportProcess rossImportProcess) ;

	/**
	 * @return the rossImportProcessId
	 */
	public Long getRossImportProcessId();

	/**
	 * @param rossImportProcessId the rossImportProcessId to set
	 */
	public void setRossImportProcessId(Long rossImportProcessId) ;

	/**
	 * @return the matchingIncidentId
	 */
	public Long getMatchingIncidentId();

	/**
	 * @param matchingIncidentId the matchingIncidentId to set
	 */
	public void setMatchingIncidentId(Long matchingIncidentId) ;
	
}
