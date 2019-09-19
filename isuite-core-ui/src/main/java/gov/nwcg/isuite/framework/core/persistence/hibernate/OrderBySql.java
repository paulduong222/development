package gov.nwcg.isuite.framework.core.persistence.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;

public class OrderBySql extends Order {
	private String orderSql;
	
	public OrderBySql(String sql){
		super(sql,true);
		orderSql=sql;
	}

	public static Order sql(String s){
		return new OrderBySql(s);
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.criterion.Order#toSqlString(org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery)
	 */
	@Override
	public String toSqlString(Criteria arg0, CriteriaQuery arg1) throws HibernateException {
		return orderSql;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.criterion.Order#toString()
	 */
	public String toString() {
		return orderSql;
	}

}
