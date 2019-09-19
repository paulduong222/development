package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.vo.ProjectionItemVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface CostProjectionItemDao extends TransactionSupport, CrudDao<ProjectionItem> {
	public void deleteAll(Collection<ProjectionItem> persistables) throws PersistenceException;
	public Collection<ProjectionItemVo> getIncidentSummaryProjectionItems(Long projectionId,String itemCode) throws PersistenceException;
}
