package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.UserSessionLogActivity;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;

public interface UserSessionLogActivityDao extends TransactionSupport, CrudDao<UserSessionLogActivity>{

}
