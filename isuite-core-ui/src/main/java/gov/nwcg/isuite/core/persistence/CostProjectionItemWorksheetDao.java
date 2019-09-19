package gov.nwcg.isuite.core.persistence;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface CostProjectionItemWorksheetDao extends TransactionSupport, CrudDao<ProjectionItemWorksheet> {
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ProjectionItemWorksheet> getProjectionWorksheetGrid(Long projectionId) throws PersistenceException;
	public Collection<ProjectionItemWorksheet> getSupportingCostWorksheetGrid(Long projectionId) throws PersistenceException;
	public Collection<ProjectionItemWorksheet> getProjectionWorksheetByItemCode(Long projectionId, String itemCode) throws PersistenceException;
	public Collection<ProjectionItemWorksheet> getProjectionWorksheetPersonnelTotal(Long projectionId) throws PersistenceException;
	public Date getProjectionWorksheetStartDay(Long projectionId) throws PersistenceException;
}
