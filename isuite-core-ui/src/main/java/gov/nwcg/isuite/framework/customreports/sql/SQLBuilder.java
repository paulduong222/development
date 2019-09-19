package gov.nwcg.isuite.framework.customreports.sql;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.framework.customreports.sql.builders.CriteriaBuilder;
import gov.nwcg.isuite.framework.customreports.sql.builders.FieldBuilder;
import gov.nwcg.isuite.framework.customreports.sql.builders.GroupByBuilder;
import gov.nwcg.isuite.framework.customreports.sql.builders.SortBuilder;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class SQLBuilder {
	
	public static final String SELECT = "SELECT";
	public static final String NEWLINE = "\n";
	public static final String FROM = "FROM ";
	public static final String WHERE = "WHERE ";
	public static final String SPACE = "     ";
	public static final String AND = "AND ";
	public static final String LPAREN = "(";
	public static final String RPAREN = ")";
	public static final String OR = " OR ";
	
	public SQLBuilder() {
	}
	
	public static String buildSql(CustomReportVo vo, String mode, Collection<Long> incidentIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		String databaseType = "";
		
		if(mode.equalsIgnoreCase("ENTERPRISE")) {
			databaseType = "ORACLE";
		}
		
		sql.append(SELECT)
		.append(NEWLINE);
		
		if(CollectionUtility.hasValue(vo.getCustomReportColumnVos())) {
			sql.append(FieldBuilder.buildFields(vo.getCustomReportColumnVos(), databaseType));
		}
		
		if(null != vo.getCustomReportViewVo()) {
			sql.append(FROM);
			sql.append(vo.getCustomReportViewVo().getSqlName());
			sql.append(NEWLINE);
		}
		
		//incidents
		sql.append(WHERE);
		sql.append(NEWLINE);
		sql.append(SPACE);
		sql.append(LPAREN);
		sql.append(LPAREN);
		sql.append("INCIDENT_ID IN ('");
		sql.append(StringUtils.join(incidentIds.toArray(), "','"));
		sql.append("'");
		sql.append(RPAREN);
		sql.append(RPAREN);
		
		//reference data views
		if(null != vo.getCustomReportViewVo()) {
			if(vo.getCustomReportViewVo().getSqlName().equalsIgnoreCase("ISWV_CR_JETPORT") ||
					vo.getCustomReportViewVo().getSqlName().equalsIgnoreCase("ISWV_CR_ITEM_CODE") ||
					vo.getCustomReportViewVo().getSqlName().equalsIgnoreCase("ISWV_CR_UNIT")) {
				
				sql.append(NEWLINE);
				sql.append(OR);
				sql.append(LPAREN);
				sql.append("INCIDENT_ID IS NULL AND INCIDENT_GROUP_ID IS NULL");
				sql.append(RPAREN);
			}
		}
		
		sql.append(RPAREN);
		sql.append(NEWLINE);
		
		if(CollectionUtility.hasValue(vo.getCustomReportCriteriaVos())) {
			sql.append(SPACE);
			sql.append(AND);
			sql.append(NEWLINE);
			sql.append(CriteriaBuilder.buildCriteria(vo.getCustomReportCriteriaVos(), databaseType));
		}
		
		sql.append(GroupByBuilder.buildGroupBy(vo.getCustomReportColumnVos(), vo.getSortOrderColumnVos(),databaseType));
		
		sql.append(SortBuilder.buildSort(vo.getSortOrderColumnVos(),vo.getCustomReportColumnVos(), databaseType));
		
		return sql.toString();
	}

}
