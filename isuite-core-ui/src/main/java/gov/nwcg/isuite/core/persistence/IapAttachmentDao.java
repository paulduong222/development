package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapAttachmentDao extends TransactionSupport, CrudDao<IapAttachment> {
	
	public Collection<IapAttachment> getByPlanId(Long planId) throws PersistenceException;
	
	public Collection<String> getIapAttachmentFilenames() throws PersistenceException;	
}
