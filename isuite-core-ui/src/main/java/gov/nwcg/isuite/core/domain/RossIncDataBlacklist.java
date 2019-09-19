package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossIncDataBlacklist extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the rossIncBlacklist
	 */
	public RossIncBlacklist getRossIncBlacklist(); 

	/**
	 * @param rossIncBlacklist the rossIncBlacklist to set
	 */
	public void setRossIncBlacklist(RossIncBlacklist rossIncBlacklist) ;

	/**
	 * @return the resId
	 */
	public Long getResId();

	/**
	 * @param resId the resId to set
	 */
	public void setResId(Long resId) ;

	/**
	 * @return the rossIncId
	 */
	public String getRossIncId() ;

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(String rossIncId) ;
	
	/**
	 * @return the importStatus
	 */
	public String getImportStatus();

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus);

	/**
	 * @return the rossResReqId
	 */
	public Long getRossResReqId() ;
	
	/**
	 * @param rossResReqId the rossResReqId to set
	 */
	public void setRossResReqId(Long rossResReqId);

	
}
