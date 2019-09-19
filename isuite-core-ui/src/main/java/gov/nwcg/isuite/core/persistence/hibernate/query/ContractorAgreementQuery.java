package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ContractorAgreementQuery {
	
	public static final String DISABLE_CONTRACTOR_AGREEMENTS="DISABLE_CONTRACTOR_AGREEMENTS";
	public static final String DISABLE_CONTRACTOR_AGREEMENTS_QUERY = 
		"UPDATE ContractorAgreementImpl ca " +
		"SET ca.enabled = :enabled " +
		"WHERE ca.id IN ( :ids )";
	
	public static final String ENABLE_CONTRACTOR_AGREEMENTS="ENABLE_CONTRACTOR_AGREEMENTS";
	public static final String ENABLE_CONTRACTOR_AGREEMENTS_QUERY =
		"UPDATE ContractorAgreementImpl ca " +
		"SET ca.enabled = :enabled " +
		"WHERE ca.id IN ( :ids )";
	
	public static String getAssignmentTimePostingCountQuery() {
		String sql = 
			"SELECT COUNT(*) FROM ISW_ASSIGN_TIME_POST ATP " + 
			"JOIN ISW_CONTR_PAYMENT_INFO CPI ON ATP.ASSIGNMENT_TIME_ID = CPI.ASSIGNMENT_TIME_ID " +
			"WHERE CPI.CONTRACTOR_AGREEMENT_ID = :id";
		return sql;
	}

}
