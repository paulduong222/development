package gov.nwcg.isuite.framework.customreports.fieldaggregators;

public class NoneFieldAggregator extends AbstractFieldAggregator {

	public String toSqlField(String fieldName) throws Exception {
		return fieldName;
	}

}
