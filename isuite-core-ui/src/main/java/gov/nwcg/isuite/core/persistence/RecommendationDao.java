package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Recommendation;
import gov.nwcg.isuite.core.vo.RecommendationVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface RecommendationDao extends TransactionSupport, CrudDao<Recommendation> {
	
	public Collection<RecommendationVo> getPicklist() throws PersistenceException;

}
