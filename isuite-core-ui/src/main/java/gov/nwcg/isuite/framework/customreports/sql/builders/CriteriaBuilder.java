package gov.nwcg.isuite.framework.customreports.sql.builders;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.framework.customreports.enumerators.ComparisonOperatorEnum;

import java.util.Collection;

public class CriteriaBuilder {
	
	public static final String BLOCK = "BLOCK";
	public static final String SPACE = "     ";
	public static final String LPAREN = "(";
	public static final String RPAREN = ")";
	public static final String NEWLINE = "\n";
	
	
	public CriteriaBuilder() {
	}
	
	public static String buildCriteria(Collection<CustomReportCriteriaVo> vos, String mode) throws Exception {
		StringBuffer sql = new StringBuffer();
		Integer level = 0;
		
		for(CustomReportCriteriaVo vo : vos) {
			if(vo.getType().equalsIgnoreCase(BLOCK)) {
				sql.append(CriteriaBuilder.buildBlockCriteria(vo, level, mode));
			}
		}
		
		return sql.toString();
	}
	
	private static String buildBlockCriteria(CustomReportCriteriaVo vo, Integer level, String mode) throws Exception {
		StringBuffer sql = new StringBuffer();
		Integer thisLevel = level + 1;
		String indent = CriteriaBuilder.buildSpace(thisLevel);
		
		if(null != vo.getChildren() && vo.getChildren().size() > 0) {
			if(null == vo.getConnective() || vo.getConnective() == "") {
				//nothing
			}else {
				sql.append(indent);
				sql.append(vo.getConnective());
				sql.append(NEWLINE);
			}
			sql.append(indent);
			sql.append(LPAREN);
			sql.append(NEWLINE);
			
			for(CustomReportCriteriaVo child : vo.getChildren()) {
				if(child.getType().equalsIgnoreCase(BLOCK)) {
					sql.append(buildBlockCriteria(child, thisLevel, mode));
				}else {
					if(null != child.getOperatorVo()) {
						sql.append(buildStatementCriteria(child, thisLevel, mode));
					}
				}
				
				sql.append(NEWLINE);
			}
			
			sql.append(indent);
			sql.append(RPAREN);
			sql.append(NEWLINE);
		}
		
		return sql.toString();
	}
	
	private static String buildStatementCriteria(CustomReportCriteriaVo vo, Integer level, String mode) throws Exception {
		StringBuffer sql = new StringBuffer();
		Integer thisLevel = level + 1;
		String indent = CriteriaBuilder.buildSpace(thisLevel);
		
		sql.append(indent);
		
		if(null == vo.getConnective() || vo.getConnective() == "") {
			//nothing
		}else {
			sql.append(vo.getConnective());
			sql.append(" ");
		}
		
		sql.append(ComparisonOperatorEnum.getAbstractClause(vo.getOperatorVo(), mode).toClause(vo, mode));
		
		return sql.toString();
	}
	
	private static String buildSpace(Integer level) throws Exception {
		StringBuffer space = new StringBuffer();
		
		for(int i=1;i<=level;i++) {
			space.append(SPACE);
		}
		
		return space.toString();
	}
	
	

}
