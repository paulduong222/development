package gov.nwcg.isuite.framework.customreports.clauses;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;

public class IsNullClause extends AbstractClause {

	@Override
	public String toClause(CustomReportCriteriaVo criteriaVo, String mode) throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append(criteriaVo.getSourceViewFieldVo().getSqlName());
		sb.append(" is null ");
		
		return sb.toString();
	}

	

}
