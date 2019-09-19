package gov.nwcg.isuite.framework.customreports.sql.builders;

import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldAggregatorTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;

import java.util.Collection;

public class FieldBuilder {
	
	public static final String NEWLINE = "\n";
	public static final String INDENT = "     ";
	public static final String COMMA = ", ";
	public static final String ASCOLUMN = " AS column";
	
	public FieldBuilder() {
		
	}
	
	public static String buildFields(Collection<CustomReportColumnVo> vos, String databaseType) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		
		int i = 1;
		
		for(CustomReportColumnVo vo : vos) {
			String col = "";
			
			sql.append(INDENT);
			
			if(i != 1) {
				sql.append(COMMA);
			}
			
			col = vo.getCustomReportViewFieldVo().getSqlName();
			
			if(null != vo.getAggregateVo()) {
				col = FieldAggregatorTypeEnum.getFieldAggregator(vo.getAggregateVo().getAggregateType(), databaseType).toSqlField(col);
			}
			
			if(null != vo.getFormatVo()) {
				col = FieldFormatterTypeEnum.getFieldFormatter(vo.getFormatVo().getFormatType(), databaseType).toSqlField(col);
			}
			
			sql.append(col);
			sql.append(ASCOLUMN);
			sql.append(i);
			
			sql.append(NEWLINE);
			
			i++;
		}
		
		return sql.toString();
	}
	
}
