package gov.nwcg.isuite.framework.customreports.clauses;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsDataTypeEnum;

public class IsFalseClause extends AbstractClause {

	@Override
	public String toClause(CustomReportCriteriaVo criteriaVo, String mode) throws Exception {
		String s = "";
		
		switch(CustomReportsDataTypeEnum.valueOf(criteriaVo.getSourceViewFieldVo().getDataType())) {
			case STRING:
				s = forString(criteriaVo, mode);
				break;
			case DATE:
				s = forDate(criteriaVo);
				break;
			case NUMBER:
				s = forNumber(criteriaVo);
				break;
			case TIME:
				s = forTime(criteriaVo);
				break;
			case BOOLEAN:
				s = forBoolean(criteriaVo, mode);
				break;
		}
		
		return s;
	}

	public String forString(CustomReportCriteriaVo criteriaVo, String mode) {
		String s = "";
		return s;
	}
	
	public String forDate(CustomReportCriteriaVo criteriaVo) {
		String s = "";
		return s;
	}
	
	public String forNumber(CustomReportCriteriaVo criteriaVo) {
		String s = "";
		return s;
	}
	
	public String forTime(CustomReportCriteriaVo criteriaVo) {
		String s = "";
		return s;
	}
	
	public String forBoolean(CustomReportCriteriaVo criteriaVo, String mode) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(criteriaVo.getSourceViewFieldVo().getSqlName());
		
		sb.append(" = 'N' ");
		
		return sb.toString();
	}

}
