package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.KindAltDesc;
import gov.nwcg.isuite.core.vo.KindAltDescVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface KindAltDescDao extends TransactionSupport, CrudDao<KindAltDesc> {
	
	public Collection<KindAltDescVo> getGrid() throws PersistenceException;

}
