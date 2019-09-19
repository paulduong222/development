package gov.nwcg.isuite.framework.customreports.sql.builders;

import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

public class SortBuilder {
	
	public static final String NEWLINE = "\n";
	public static final String INDENT = "     ";
	public static final String COMMA = ", ";
	public static final String ORDER_BY = "ORDER BY ";
	public static final String SPACE_DESC = " DESC";		
	
	public SortBuilder() {}
	
	public static String buildSort(Collection<CustomReportColumnVo> vos, Collection<CustomReportColumnVo> columnvos,String databaseType) throws Exception {
		
		if(!CollectionUtility.hasValue(vos)){
			return "";
		}
		
		StringBuffer sql = new StringBuffer(ORDER_BY);
		
		for(CustomReportColumnVo vo : vos) {
			sql.append(NEWLINE);
			sql.append(INDENT);

			String formatType="";
			for(CustomReportColumnVo cvo : columnvos){
				if(cvo.getCustomReportViewFieldVo().getDisplayName().equalsIgnoreCase(vo.getCustomReportViewFieldVo().getDisplayName())){
					if(null != cvo.getFormatVo()){
						if(StringUtility.hasValue(cvo.getFormatVo().getFormatType())){
							formatType = cvo.getFormatVo().getFormatType();
							break;
						}
					}
				}
			}
			
			if(vo.getCustomReportViewFieldVo().getSqlName().equalsIgnoreCase("REQUEST_NUMBER")) {
				sql.append("SORTREQUESTNUMBER(");
				sql.append(vo.getCustomReportViewFieldVo().getSqlName());
				sql.append(")");
			}else if(StringUtility.hasValue(formatType) || StringUtility.hasValue(vo.getCustomReportViewFieldVo().getFormatType()) ){
				String col = "";
				if(StringUtility.hasValue(formatType))
					col = FieldFormatterTypeEnum.getFieldFormatter(formatType, databaseType).toSqlField(vo.getCustomReportViewFieldVo().getSqlName());
				else
					col = FieldFormatterTypeEnum.getFieldFormatter(vo.getCustomReportViewFieldVo().getFormatType(), databaseType).toSqlField(vo.getCustomReportViewFieldVo().getSqlName());
				sql.append(col);
			}else{
				//sql.append(vo.getCustomReportViewFieldVo().getSqlName());
				if(vo.getCustomReportViewFieldVo().getDataType().equalsIgnoreCase("DATE")){
					if(databaseType.equals("ORACLE")){
						sql.append("TRUNC(");
					}else{
						sql.append("DATE(");
					}
					sql.append(vo.getCustomReportViewFieldVo().getSqlName());
					sql.append(")");
				}else{
					sql.append(vo.getCustomReportViewFieldVo().getSqlName());
				}
			}
			
			/*
			sql.append(vo.getCustomReportViewFieldVo().getSqlName());
			
			if(vo.getCustomReportViewFieldVo().getSqlName().equalsIgnoreCase("REQUEST_NUMBER") ||
					vo.getCustomReportViewFieldVo().getDataType().equalsIgnoreCase("DATE")) {
				sql.append(")");
			}
			*/
			
			if(vo.getSortByType().equalsIgnoreCase(CustomReportColumnVo.SORT_ORDER_DESCENDING_INSTANCE)) {
				sql.append(SPACE_DESC);
			}
			sql.append(COMMA);
		}
		
		//Remove the last comma and space
		return sql.substring(0, sql.length()-(COMMA.length()));
	}
	
}
