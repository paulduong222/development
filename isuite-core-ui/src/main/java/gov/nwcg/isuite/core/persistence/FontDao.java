package gov.nwcg.isuite.core.persistence;

import java.util.Collection;
import gov.nwcg.isuite.core.domain.Font;
import gov.nwcg.isuite.core.vo.FontVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface FontDao extends TransactionSupport, CrudDao<Font> {
	/**
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<FontVo> getPicklist() throws PersistenceException;
}
