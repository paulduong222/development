package gov.nwcg.isuite.core.persistence;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface DbAvailDao extends TransactionSupport, CrudDao<DbAvail> {

	public void closeSession() throws Exception;

	public void openSession() throws Exception;
	
	public Collection<String> getDbNames() throws PersistenceException;

	public Collection<String> getDbNames(Long excludeId) throws PersistenceException;
	
	public Collection<DbAvail> getAll() throws PersistenceException;

	public void updateDatabaseName(String origName, String newName) throws PersistenceException;
	
	public DbAvail getByDatabaseName(String name) throws PersistenceException;

	public void dropDatabase(String name ) throws PersistenceException;

	public void updateLastAutoBackupDate(Long id, Date dt) throws PersistenceException;
	
}
