package gov.nwcg.isuite.framework.customreports.clauses;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.criteriaformatters.ToUpperCase;
import gov.nwcg.isuite.framework.customreports.enumerators.ComparisonOperatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsDataTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsTargetTypeEnum;

public class CommonClause extends AbstractClause {

	private static final String TO_DATE = "to_date('";
	private static final String TO_DATE_FORMAT = "','MM/DD/YYYY') ";
	
	enum EvaluatorOperatorEnum {
		STRING
		,STR_LENGTH
		;
	}
	
	public CommonClause() {
	}
	
	@Override
	public String toClause(CustomReportCriteriaVo criteriaVo, String mode) throws Exception {
		String s = "";
		String symbol = "";
		
		for(ComparisonOperatorEnum oEnum : ComparisonOperatorEnum.values()) {
			if(oEnum.getOperatorType().equals(criteriaVo.getOperatorVo().getOperatorType())) {
				symbol = oEnum.getSymbol();
			}
		}
		
		switch(CustomReportsDataTypeEnum.valueOf(criteriaVo.getSourceViewFieldVo().getDataType())) {
			case STRING:
				s = forString(criteriaVo, symbol, mode);
				break;
			case DATE:
				s = forDate(criteriaVo, symbol, mode);
				break;
			case NUMBER:
				s = forNumber(criteriaVo, symbol);
				break;
			case TIME:
				s = forTime(criteriaVo, symbol, mode);
				break;
			case BOOLEAN:
				s = forBoolean(criteriaVo, symbol);
				break;
			case CURRENCY:
				s = forCurrency(criteriaVo, symbol);
				break;
		}
	
		return s;
	}
	
	public static String forString(CustomReportCriteriaVo criteriaVo, String symbol, String mode) throws Exception {
		String s = "";
		
		s = toFormatString(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceEvaluator(), mode);
		
		switch(CustomReportsTargetTypeEnum.valueOf(criteriaVo.getTargetType())) {
			case INPUT_VALUE:
				switch(EvaluatorOperatorEnum.valueOf(criteriaVo.getSourceEvaluator().getOperatorType())) {
					case STRING:
						s = s + " " + symbol + " " + ToUpperCase.toFormat(criteriaVo.getTargetValue());
						break;
					case STR_LENGTH:
						s = s + " " + symbol + " " + criteriaVo.getTargetValue();
						break;
				}
				
				break;
			case SELECT_FIELD:
				s = s + " " + symbol + " upper(" + criteriaVo.getTargetViewFieldVo().getSqlName() + ") ";
				break;
		}
		
		return s;
	}
	
	public static String forDate(CustomReportCriteriaVo criteriaVo, String symbol, String mode) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append(toFormatDate(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceInputValue(), criteriaVo.getSourceEvaluator(), mode));
		
		sb.append(symbol);
		sb.append(" ");
		
		switch(CustomReportsTargetTypeEnum.valueOf(criteriaVo.getTargetType())) {
			case INPUT_VALUE:
				sb.append(criteriaVo.getTargetValue());
				sb.append(" ");
				break;
			case SELECT_TERM:
				sb.append(toTerm(criteriaVo.getTargetTermVo(), criteriaVo.getTargetEvaluator(), criteriaVo.getTargetValue(), mode));
				break;
			case SELECT_VIEW_FIELD:
				sb.append(toFormatDate(criteriaVo.getTargetViewFieldVo().getSqlName(), "", criteriaVo.getTargetEvaluator(), mode));
				break;
			case SELECT_DATE:
				sb.append(TO_DATE);
				sb.append(criteriaVo.getTargetValue());
				sb.append(TO_DATE_FORMAT);
				break;
		
		}
		
		return sb.toString();
	}
	
	public static String forTime(CustomReportCriteriaVo criteriaVo, String symbol, String mode) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append(toFormatTime(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceEvaluator(), mode));
		
		sb.append(symbol);
		sb.append(" ");
		
		switch(CustomReportsTargetTypeEnum.valueOf(criteriaVo.getTargetType())) {
			case INPUT_VALUE:
				sb.append(criteriaVo.getTargetValue());
				sb.append(" ");
				break;
			case SELECT_VIEW_FIELD:
				sb.append(toFormatTime(criteriaVo.getTargetFieldSqlName(), criteriaVo.getTargetEvaluator(), mode));
				break;
		}
		
		return sb.toString();
	}
	
	public static String forNumber(CustomReportCriteriaVo criteriaVo, String symbol) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append(criteriaVo.getSourceViewFieldVo().getSqlName());
		
		sb.append(" ");
		sb.append(symbol);
		sb.append(" ");
		
		switch(CustomReportsTargetTypeEnum.valueOf(criteriaVo.getTargetType())) {
			case INPUT_VALUE:
				sb.append(criteriaVo.getTargetValue());
				sb.append(" ");
				break;
		}
		
		return sb.toString();
	}
	
	public static String forBoolean(CustomReportCriteriaVo criteriaVo, String symbol) throws Exception {
		String s = "";
		return s;
	}
	
	public static String forCurrency(CustomReportCriteriaVo criteriaVo, String symbol) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append(criteriaVo.getSourceViewFieldVo().getSqlName());
		
		sb.append(" ");
		sb.append(symbol);
		sb.append(" ");
		
		switch(CustomReportsTargetTypeEnum.valueOf(criteriaVo.getTargetType())) {
			case INPUT_VALUE:
				sb.append(criteriaVo.getTargetValue());
				sb.append(" ");
				break;
		}
		
		return sb.toString();
	}

}
