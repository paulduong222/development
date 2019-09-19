package gov.nwcg.isuite.framework.core.persistence.datefilter.impl;

import gov.nwcg.isuite.framework.core.persistence.datefilter.ICustomDateFilter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class CustomDateFilterType1Impl implements ICustomDateFilter {

	private final String monthMask = "MM";
	private final String startMonth = "01";
	private final String endMonth = "09";
	private String oracleSQL;
	private String postgreSQL;
	
	@Override
	public void applyDateFiltersOracle(Criteria crit, String crypticDateFilterCode, String dbFieldName) throws Exception {
		//TODO:  The date field we want in the query needs to be passed in.
		//       it is not always created date we are filtering on. -dbudge
		this.oracleSQL = "to_char(" + dbFieldName + ", '" + this.monthMask + "') between '" + this.startMonth + "' and '" + this.endMonth + "'";
		crit.add(Restrictions.sqlRestriction(this.oracleSQL));
	}

	public String getDateFiltersOracle(String crypticDateFilterCode, String dbFieldName) throws Exception {
		//TODO:  The date field we want in the query needs to be passed in.
		//       it is not always created date we are filtering on. -dbudge
		this.oracleSQL = "to_char(" + dbFieldName + ", '" + this.monthMask + "') between '" + this.startMonth + "' and '" + this.endMonth + "'";
		return oracleSQL;
	}
	
	@Override
	public void applyDateFiltersPostgres(Criteria crit, String crypticDateFilterCode, String dbFieldName) throws Exception {

	}

}
