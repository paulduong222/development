package gov.nwcg.isuite.framework.customreports.clauses;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsDataTypeEnum;

public class BetweenClause extends AbstractClause {
	
	public BetweenClause() {
		super();
	}

	@Override
	public String toClause(CustomReportCriteriaVo criteriaVo, String mode) throws Exception {
		String s = "";
		
		switch(CustomReportsDataTypeEnum.valueOf(criteriaVo.getSourceViewFieldVo().getDataType())) {
			case STRING:
				s = forString(criteriaVo);
				break;
			case DATE:
				s = forDate(criteriaVo, mode);
				break;
			case NUMBER:
				s = forNumber(criteriaVo);
				break;
			case TIME:
				s = forTime(criteriaVo, mode);
				break;
			case BOOLEAN:
				s = forBoolean(criteriaVo);
				break;
			case CURRENCY:
				s = forCurrency(criteriaVo);
				break;
		}
		
		return s;
	}

	public String forString(CustomReportCriteriaVo criteriaVo) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	public String forDate(CustomReportCriteriaVo criteriaVo, String mode) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("(");
		sb.append(toFormatDate(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceInputValue(), criteriaVo.getSourceEvaluator(), mode));
		sb.append(" BETWEEN ");
		sb.append("to_date('");
		sb.append(criteriaVo.getTargetValue());
		sb.append("','MM/DD/YYYY') ");
		sb.append(" AND ");
		sb.append("to_date('");
		sb.append(criteriaVo.getTargetValue2());
		sb.append("','MM/DD/YYYY') ");
		sb.append(") ");
		
		return sb.toString();
	}
	
	public String forNumber(CustomReportCriteriaVo criteriaVo) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("(");
		sb.append(criteriaVo.getSourceViewFieldVo().getSqlName());
		sb.append(" BETWEEN ");
		sb.append(criteriaVo.getTargetValue());
		sb.append(" AND ");
		sb.append(criteriaVo.getTargetValue2());
		sb.append(") ");
		
		return sb.toString();
	}
	
	public String forTime(CustomReportCriteriaVo criteriaVo, String mode) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("(");
		sb.append(toFormatTime(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceEvaluator(), mode));
		sb.append(" BETWEEN ");
		sb.append(criteriaVo.getTargetValue());
		sb.append(" AND ");
		sb.append(criteriaVo.getTargetValue2());
		sb.append(") ");
		
		return sb.toString();
	}
	
	public String forBoolean(CustomReportCriteriaVo criteriaVo) {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
	public String forCurrency(CustomReportCriteriaVo criteriaVo) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("(");
		sb.append(criteriaVo.getSourceViewFieldVo().getSqlName());
		sb.append(" BETWEEN ");
		sb.append(criteriaVo.getTargetValue());
		sb.append(" AND ");
		sb.append(criteriaVo.getTargetValue2());
		sb.append(") ");
		
		return sb.toString();
	}

}
