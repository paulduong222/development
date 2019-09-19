package gov.nwcg.isuite.framework.core.persistence.datefilter;

import org.hibernate.Criteria;

public interface ICustomDateFilter {

	public void applyDateFiltersOracle(Criteria crit, String crypticDateFilterCode, String dbFieldName) throws Exception;

	public String getDateFiltersOracle(String crypticDateFilterCode, String dbFieldName) throws Exception;
	
	public void applyDateFiltersPostgres(Criteria crit, String crypticDateFilterCode, String dbFieldName) throws Exception;
	
}

