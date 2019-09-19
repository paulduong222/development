package gov.nwcg.isuite.framework.customreports.clauses;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.criteriaformatters.ToUpperCase;
import gov.nwcg.isuite.framework.customreports.enumerators.CustomReportsDataTypeEnum;

public class EndsWithClause extends AbstractClause {
	
	public EndsWithClause() {
		super();
	}

	@Override
	public String toClause(CustomReportCriteriaVo criteriaVo, String mode) throws Exception {
		String s = "";
		
		switch(CustomReportsDataTypeEnum.valueOf(criteriaVo.getSourceViewFieldVo().getDataType())) {
			case STRING:
				s = this.forString(criteriaVo, mode);
				break;
		}
		
		return s;
	}

	public String forString(CustomReportCriteriaVo criteriaVo, String mode) throws Exception {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toFormatString(criteriaVo.getSourceViewFieldVo().getSqlName(), criteriaVo.getSourceEvaluator(), mode));
		s.append(" LIKE ");
		s.append(ToUpperCase.toFormat("%" + criteriaVo.getTargetValue()));
		
		return s.toString();
	}

}
