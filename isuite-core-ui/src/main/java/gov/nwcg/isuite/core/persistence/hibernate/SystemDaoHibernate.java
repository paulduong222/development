package gov.nwcg.isuite.core.persistence.hibernate;

import org.hibernate.SQLQuery;

import gov.nwcg.isuite.core.persistence.SystemDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

public class SystemDaoHibernate extends TransactionSupportImpl implements SystemDao {

	public String getRevisionLevel() throws PersistenceException {
		String sql = "SELECT REVISIONLEVEL FROM REVISION";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		try{
			return TypeConverter.convertToString(rslt);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
}
