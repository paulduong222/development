package gov.nwcg.isuite.framework.customreports.fieldformatters;

public class NoneFieldFormatter extends AbstractFieldFormatter {

	public String toSqlField(String fieldName) throws Exception {
		return fieldName;
	}

}
