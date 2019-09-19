package gov.nwcg.isuite.core.persistence.hibernate.query;

public class SystemParameterQuery {

	/*
	 *  Finds the system parameter by its name
	 */
	public static final String FIND_BY_PARAMETER_NAME="FIND_BY_PARAMETER_NAME";
	public static final String FIND_BY_PARAMETER_NAME_QUERY =
		"SELECT model FROM SystemParameterImpl model " +
		"WHERE model.parameterName = :nm";

}
