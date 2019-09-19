package gov.nwcg.isuite.framework.customreports.sql.builders;

import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

public class GroupByBuilder {
	
	public static final String COMMA = ", ";
	public static final String GROUPBY = "GROUP BY ";
	public static final String NEWLINE = "\n";
	public static final String NONE = "NONE";
	
	public GroupByBuilder() {
	}
	
	public static String buildGroupBy(Collection<CustomReportColumnVo> vos, Collection<CustomReportColumnVo> orderVos, String databaseType) throws Exception {
		StringBuffer sql = new StringBuffer();
		Boolean hasAggregate = false;
		
		for(CustomReportColumnVo vo : vos) {
			
			if (null != vo.getAggregateVo() && vo.getAggregateVo().getDisplayName().compareToIgnoreCase(NONE) != 0 ) {
				hasAggregate = true;
			}else {
				if(StringUtility.hasValue(sql.toString())) {
					sql.append(COMMA);
				}else{
					sql.append(GROUPBY);
				}
				
				if(vo.getFormatVo() != null && StringUtility.hasValue(vo.getFormatVo().getFormatType()) ){
					String col = FieldFormatterTypeEnum.getFieldFormatter(vo.getFormatVo().getFormatType(), databaseType).toSqlField(vo.getCustomReportViewFieldVo().getSqlName());
					sql.append(col);
					// update sort order vo if same col is in sort list
					for(CustomReportColumnVo orderVo: orderVos){
						if(orderVo.getCustomReportViewFieldVo().getSqlName().equals(vo.getCustomReportViewFieldVo().getSqlName()))
							orderVo.getCustomReportViewFieldVo().setFormatType(vo.getCustomReportViewFieldVo().getFormatType());
					}
				}else
					sql.append(vo.getCustomReportViewFieldVo().getSqlName());
			}
		}
		
		if(hasAggregate){
			for(CustomReportColumnVo orderVo: orderVos){
				if(StringUtility.contains(sql.toString(), orderVo.getCustomReportViewFieldVo().getSqlName())){
					//nothing
				}else{
					if(StringUtility.hasValue(sql.toString())) {
						sql.append(COMMA);
					}else{
						sql.append(GROUPBY);
					}
					
					if(orderVo.getFormatVo() != null && StringUtility.hasValue(orderVo.getFormatVo().getFormatType()) ){
						String col = FieldFormatterTypeEnum.getFieldFormatter(orderVo.getFormatVo().getFormatType(), databaseType).toSqlField(orderVo.getCustomReportViewFieldVo().getSqlName());
						sql.append(col);
						
					}else
						sql.append(orderVo.getCustomReportViewFieldVo().getSqlName());
				}	
			}
		}
		
		sql.append(NEWLINE);
		
		if(!hasAggregate) {
			sql = new StringBuffer();
		}
		
		return sql.toString();
	}

}
