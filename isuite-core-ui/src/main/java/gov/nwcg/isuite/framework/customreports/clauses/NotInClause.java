package gov.nwcg.isuite.framework.customreports.clauses;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsDataTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsTargetTypeEnum;

public class NotInClause extends AbstractClause {

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
				s = forBoolean(criteriaVo);
				break;
		}
		
		return s;
	}

	public String forString(CustomReportCriteriaVo criteriaVo, String mode) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(toFormatString(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceEvaluator(), mode));
		
		int i = 0;
		
		if(criteriaVo.getTargetType().equals(CustomReportsTargetTypeEnum.INPUT_LIST.name())) {
			if(criteriaVo.getSourceEvaluator().getOperatorType().equalsIgnoreCase(CustomReportsDataTypeEnum.STRING.name())) {
				sb.append(" NOT IN (");
				for(String item : criteriaVo.getTargetList()) {
					if(i == 0) {
						sb.append("'");
						i++;
					}else {
						sb.append(", '");
					}
					sb.append(item);
					sb.append("'");
				}
				sb.append(") ");
			}
		}
		
		return sb.toString();
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
	
	public String forBoolean(CustomReportCriteriaVo criteriaVo) {
		String s = "";
		return s;
	}

}
