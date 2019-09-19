package gov.nwcg.isuite.core.persistence;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.vo.ResInvImportVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface ResInvImportDao extends TransactionSupport, CrudDao<ResInvImport>{

	   public Collection<ResInvImportVo> getByDates(Date fromDate, Date toDate) throws PersistenceException;

	   
	   public void createResInvImportRecord(String type) throws PersistenceException;
	   public void doUpdatesOH() throws PersistenceException;
	   public void doUpdatesNonOH() throws PersistenceException;
	   public void doInsertsOH() throws PersistenceException;
	   public void doInsertsNonOH() throws PersistenceException;
	   public void markCompleted() throws PersistenceException;
	   
}
