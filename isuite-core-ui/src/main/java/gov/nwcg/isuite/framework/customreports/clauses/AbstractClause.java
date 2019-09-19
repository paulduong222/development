package gov.nwcg.isuite.framework.customreports.clauses;


import gov.nwcg.isuite.core.vo.CustomReportCriteriaEvaluatorVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaTermVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaEvaluatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaTermEnum;

public abstract class AbstractClause {
	
	protected static final String ORACLE = "ORACLE";
	
	public abstract String toClause(CustomReportCriteriaVo criteriaVo, String mode) throws Exception;
	
	protected static String toFormatString(String sqlField, CustomReportCriteriaEvaluatorVo vo, String mode) {
		final String UPPER = "upper(";
		final String R_PAREN = ")";
		final String CHAR_LENGTH = "CHAR_LENGTH(";
		final String LENGTH = "LENGTH(";
		
		StringBuffer sb = new StringBuffer();
		
		switch(CriteriaEvaluatorEnum.valueOf(vo.getEvaluatorType())) {
			case AS_IS:
				sb.append(UPPER);
				sb.append(sqlField);
				sb.append(R_PAREN);
				break;
			case AS_TEXT_LENGTH:
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append(LENGTH);
					sb.append(sqlField);
					sb.append(R_PAREN);
				}else {
					sb.append( CHAR_LENGTH);
					sb.append(sqlField);
					sb.append(R_PAREN);
				}
				break;
		}
		
		return sb.toString();
	}
	
	protected static String toFormatDate(String sqlName, String inputVal, CustomReportCriteriaEvaluatorVo evalVo, String mode) {
		
		StringBuffer sb = new StringBuffer();
		StringBuffer char_sb = new StringBuffer();
		
		switch(CriteriaEvaluatorEnum.valueOf(evalVo.getEvaluatorType())) {
			case MM_DD_YYYY:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'MM/DD/YYYY')");
				sb.append("to_date(");
				sb.append(char_sb);
				sb.append(",'MM/DD/YYYY') ");
				break;
				
			case MONTH:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'MM')");
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append("to_number(");
					sb.append(char_sb);
					sb.append(") ");
				}else {
					sb.append("cast(");
					sb.append(char_sb);
					sb.append(" as int) ");
				}
				break;
				
			case DAY:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'DD')");
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append("to_number(");
					sb.append(char_sb);
					sb.append(") ");
				}else {
					sb.append("cast(");
					sb.append(char_sb);
					sb.append(" as int) ");
				}
				break;
				
			case YEAR:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'YYYY')");
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append("to_number(");
					sb.append(char_sb);
					sb.append(") ");
				}else {
					sb.append("cast(");
					sb.append(char_sb);
					sb.append(" as int) ");
				}
				break;
				
			case PLUS_N_DAYS:
				if(mode.equalsIgnoreCase(ORACLE)) {
					char_sb.append("to_char(");
					char_sb.append(sqlName);
					char_sb.append(" + ");
					char_sb.append(inputVal);
					char_sb.append(",'MM/DD/YYYY') ");
				}else {
					char_sb.append("to_char(");
					char_sb.append(sqlName);
					char_sb.append(" + interval '");
					char_sb.append(inputVal);
					char_sb.append(" day','MM/DD/YYYY') ");
				}
				sb.append("to_date(");
				sb.append(char_sb);
				sb.append(",'MM/DD/YYYY') ");
				break;
				
			case MINUS_N_DAYS:
				if(mode.equalsIgnoreCase(ORACLE)) {
					char_sb.append("to_char(");
					char_sb.append(sqlName);
					char_sb.append(" - ");
					char_sb.append(inputVal);
					char_sb.append(",'MM/DD/YYYY') ");
				}else {
					char_sb.append("to_char(");
					char_sb.append(sqlName);
					char_sb.append(" - interval '");
					char_sb.append(inputVal);
					char_sb.append(" day','MM/DD/YYYY') ");
				}
				sb.append("to_date(");
				sb.append(char_sb);
				sb.append(",'MM/DD/YYYY') ");
				break;
				
			case PLUS_N_MONTHS:
				if(mode.equalsIgnoreCase(ORACLE)) {
					char_sb.append("to_char(add_months(");
					char_sb.append(sqlName);
					char_sb.append(inputVal);
					char_sb.append(",'MM/DD/YYYY') ");
				}else {
					char_sb.append("to_char(");
					char_sb.append(sqlName);
					char_sb.append(" + interval '");
					char_sb.append(inputVal);
					char_sb.append(" month','MM/DD/YYYY') ");
				}
				sb.append("to_date(");
				sb.append(char_sb);
				sb.append(",'MM/DD/YYYY') ");
				break;
				
			case MINUS_N_MONTHS:
				if(mode.equalsIgnoreCase(ORACLE)) {
					char_sb.append("to_char(add_months(");
					char_sb.append(sqlName);
					char_sb.append(",-");
					char_sb.append(inputVal);
					char_sb.append(",'MM/DD/YYYY') ");
				}else {
					char_sb.append("to_char(");
					char_sb.append(sqlName);
					char_sb.append(" - interval '");
					char_sb.append(inputVal);
					char_sb.append(" month','MM/DD/YYYY') ");
				}
				sb.append("to_date(");
				sb.append(char_sb);
				sb.append(",'MM/DD/YYYY') ");
				break;
		}
		
		return sb.toString();
	}
	
	protected static String toFormatTime(String sqlName, CustomReportCriteriaEvaluatorVo evalVo, String mode) {
		
		StringBuffer sb = new StringBuffer();
		StringBuffer char_sb = new StringBuffer();
		
		switch(CriteriaEvaluatorEnum.valueOf(evalVo.getEvaluatorType())) {
		
			case MILITARY_HOUR_MINUTE:
			char_sb.append("to_char(");
			char_sb.append(sqlName);
			char_sb.append(",'HH24MI')");
			if(mode.equalsIgnoreCase(ORACLE)) {
				sb.append("to_number(");
				sb.append(char_sb);
				sb.append(") ");
			}else {
				sb.append("cast(");
				sb.append(char_sb);
				sb.append(" as int) ");
			}
			break;	
		
			case MILITARY_HOUR:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'HH24')");
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append("to_number(");
					sb.append(char_sb);
					sb.append(") ");
				}else {
					sb.append("cast(");
					sb.append(char_sb);
					sb.append(" as int) ");
				}
				break;
				
			case MINUTE:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'MI')");
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append("to_number(");
					sb.append(char_sb);
					sb.append(") ");
				}else {
					sb.append("cast(");
					sb.append(char_sb);
					sb.append(" as int) ");
				}
				break;
				
			case SECOND:
				char_sb.append("to_char(");
				char_sb.append(sqlName);
				char_sb.append(",'SS')");
				if(mode.equalsIgnoreCase(ORACLE)) {
					sb.append("to_number(");
					sb.append(char_sb);
					sb.append(") ");
				}else {
					sb.append("cast(");
					sb.append(char_sb);
					sb.append(" as int) ");
				}
				break;
				
		}
		
		return sb.toString();
	}
	
	protected static String toTerm(CustomReportCriteriaTermVo termVo, CustomReportCriteriaEvaluatorVo evalVo, String inputVal, String mode) {
		final String SYSDATE = "sysdate";
		final String NOW = "now()";
		final String SYSDATE_MINUS_ONE = "(sysdate - 1)";
		final String SYSDATE_PLUS_ONE = "(sysdate + 1)";
		final String NOW_PLUS_ONE = "(now() + interval '1 day')";
		final String NOW_MINUS_ONE = "(now() - interval '1 day')";
		
		String s = "";
		
		switch(CriteriaTermEnum.valueOf(termVo.getTermType())) {
			case TODAY:
				if (mode.equalsIgnoreCase(ORACLE)) {
					s = toFormatDate(SYSDATE, inputVal, evalVo, mode);
				}else {
					s = toFormatDate(NOW, inputVal, evalVo, mode);
				}
				break;
			case YESTERDAY:
				if (mode.equalsIgnoreCase(ORACLE)) {
					s = toFormatDate(SYSDATE_MINUS_ONE, inputVal, evalVo, mode);
				}else {
					s = toFormatDate(NOW_MINUS_ONE, inputVal, evalVo, mode);
				}
				break;
			case TOMORROW:
				if (mode.equalsIgnoreCase(ORACLE)) {
					s = toFormatDate(SYSDATE_PLUS_ONE, inputVal, evalVo, mode);
				}else {
					s = toFormatDate(NOW_PLUS_ONE, inputVal, evalVo, mode);
				}
				break;
		}
		
		return s;
	}
	
}
