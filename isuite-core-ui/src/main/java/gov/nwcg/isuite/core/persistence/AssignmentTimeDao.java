package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface AssignmentTimeDao extends TransactionSupport, CrudDao<AssignmentTime>{

	public AssignmentTime getById(Long id, Class clazz) throws PersistenceException;

	public void save(AssignmentTime persistable) throws PersistenceException;

	public void saveAll(Collection<AssignmentTime> persistables) throws PersistenceException;

	public void delete(AssignmentTime persistable) throws PersistenceException;

	public void deleteAll(Collection<AssignmentTime> persistables) throws PersistenceException;

	public Collection<AssignmentTime> getByIds(Collection<Long> ids) throws PersistenceException;

	public Long getIncidentResourceId(Long assignmentTimeId) throws PersistenceException;

}
