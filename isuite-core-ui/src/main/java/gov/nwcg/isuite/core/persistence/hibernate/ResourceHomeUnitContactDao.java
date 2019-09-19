package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ResourceHomeUnitContactDao extends TransactionSupport,
		CrudDao<ResourceHomeUnitContact> {
	
	public Collection<ResourceHomeUnitContactVo> getGrid() throws PersistenceException;
	
	public Collection<ResourceHomeUnitContactVo> getGrid2(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public ResourceHomeUnitContact getResourceHomeUnitContact(Long incidentResourceId) throws PersistenceException;

}
