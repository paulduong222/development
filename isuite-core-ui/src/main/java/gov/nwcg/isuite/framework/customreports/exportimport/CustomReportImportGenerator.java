package gov.nwcg.isuite.framework.customreports.exportimport;

import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.core.vo.CustomReportViewFieldVo;
import gov.nwcg.isuite.core.vo.CustomReportViewVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.framework.customreports.enumerators.ComparisonOperatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaEvaluatorEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaTermEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldAggregatorTypeEnum;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.xml.customreport.ColumnListType;
import gov.nwcg.isuite.xml.customreport.ColumnType;
import gov.nwcg.isuite.xml.customreport.CriteriaListType;
import gov.nwcg.isuite.xml.customreport.CriteriaType;
import gov.nwcg.isuite.xml.customreport.CustomReportExport;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

/**
 * Converts a CustomReportExport object to a CustomReportVo object.
 *
 */
public class CustomReportImportGenerator {
	private ApplicationContext context = null;
	private Long tempId = 1L;
	
	private static final String BAD_CR_VIEW_IMPORT_VALUE = "CustomReportViewVo could not be determined based on view name value imported from the Custom Report import file.";
	private static final String BAD_CR_VIEWFIELD_IMPORT_VALUE = "CustomReportViewFieldVo could not be determined based on view field value imported from the Custom Report import file.";
	private static final String NO_COLUMNS_IN_IMPORT_FILE = "Could not find any colums in the imported file.";
	
	public CustomReportImportGenerator(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public CustomReportVo generateCustomReportImport(CustomReportExport crExport, Collection<CustomReportViewVo> customReportViewVos) throws Exception {
		if(crExport == null) return null;
		
		CustomReportVo crVo = new CustomReportVo();
		crVo.setReportTitle(crExport.getTitle().replaceAll("&amp;", "&"));
		crVo.setSubTitle(crExport.getSubtitle()!=null?crExport.getSubtitle().replaceAll("&amp;", "&"):null);
		
		crVo.setDescription(crExport.getDescription());
		
		CustomReportViewVo crViewVo = CustomReportViewVo.findByDataView(crExport.getDataView(), customReportViewVos);
		if(crViewVo == null) throw new Exception(BAD_CR_VIEW_IMPORT_VALUE);
		crVo.setCustomReportViewVo(crViewVo);
		
		crVo.setLandscape(crExport.isLandscape());
		crVo.setLineSpacing(crExport.getLineSpacing()!=null?crExport.getLineSpacing():"SINGLE");
		crVo.setIsPublic(crExport.isVisibleToPublic());
		crVo.setCustomReportCriteriaVos(generateCRCriteriaVos(crExport.getCriterias(), crViewVo.getCustomReportViewFieldVos()));
		
		if(null != crExport.getColumns()) {
			Collection<CustomReportColumnVo> vos = generateCRColumnVos(crExport.getColumns(), crViewVo.getCustomReportViewFieldVos());
			Collection<CustomReportColumnVo> sortOrderColumnVos = new ArrayList<CustomReportColumnVo>();
			Collection<CustomReportColumnVo> columnVos = new ArrayList<CustomReportColumnVo>();
			
			for(CustomReportColumnVo columnVo : vos ) {
				if(StringUtility.hasValue(columnVo.getSortByType())){
					sortOrderColumnVos.add(columnVo);
				}else {
					columnVos.add(columnVo);
				}
			}
			
			crVo.setCustomReportColumnVos(columnVos);
			crVo.setSortOrderColumnVos(sortOrderColumnVos);
		}
		
		return crVo;
	}
	
	private Collection<CustomReportCriteriaVo> generateCRCriteriaVos(CriteriaListType criteriaList, Collection<CustomReportViewFieldVo> viewFieldVos) throws Exception{
		Collection<CustomReportCriteriaVo> crCriteriaVos = null;
		
		if(criteriaList!=null && criteriaList.getCriteria() !=null && criteriaList.getCriteria().size()>0){ 
			crCriteriaVos = new ArrayList<CustomReportCriteriaVo>();
			for(CriteriaType criteria: criteriaList.getCriteria()){
				CustomReportCriteriaVo crCriteriaVo = new CustomReportCriteriaVo();
				crCriteriaVo.setGroupId(criteria.getGroupID());
				crCriteriaVo.setParentGroupId(criteria.getParentGroupID());
				
				crCriteriaVo.setConnective(criteria.getConnective());
				crCriteriaVo.setType(criteria.getCriteriaType().equalsIgnoreCase("B") ? "BLOCK" : "STATEMENT"); 
				crCriteriaVo.setSourceViewFieldVo(CustomReportViewFieldVo.findBySQLName(criteria.getSourceViewFieldSQLName(), viewFieldVos));
				crCriteriaVo.setSourceInputValue(criteria.getSourceInputValue());
				
				if(StringUtility.hasValue(criteria.getSourceEvaluatorType())) {
					crCriteriaVo.setSourceEvaluator(CriteriaEvaluatorEnum.getCriteriaEvaluatorVo(criteria.getSourceEvaluatorType()));
				}
				
				if(StringUtility.hasValue(criteria.getOperatorType())) {
					crCriteriaVo.setOperatorVo(ComparisonOperatorEnum.getCriteriaOperatorVo(criteria.getOperatorType()));
				}
				
				crCriteriaVo.setTargetType(criteria.getTargetType());
				crCriteriaVo.setTargetValue(criteria.getTargetValue1());
				crCriteriaVo.setTargetValue2(criteria.getTargetValue2());
				
				crCriteriaVo.setTargetViewFieldVo(CustomReportViewFieldVo.findBySQLName(criteria.getTargetViewFieldSQLName(), viewFieldVos));
				
				if(StringUtility.hasValue(criteria.getTargetTermType())) {
					crCriteriaVo.setTargetTermVo(CriteriaTermEnum.getCriteriaTermVo(criteria.getTargetTermType()));
				}
				
				if(StringUtility.hasValue(criteria.getTargetEvaluatorType())) {
					crCriteriaVo.setTargetEvaluator(CriteriaEvaluatorEnum.getCriteriaEvaluatorVo(criteria.getTargetEvaluatorType()));
				}
				
				crCriteriaVo.setDisplayOrder(criteria.getDisplayOrder());
				crCriteriaVo.setTempId(tempId++);
				
				crCriteriaVo.setTargetListFromString(criteria.getTargetList());
				
				crCriteriaVo.setCriteriaDescription(CustomReportCriteriaVo.getCritDesc(crCriteriaVo));
				
				// Set Children Criteria recursively
				crCriteriaVo.setChildren(generateCRCriteriaVos(criteria.getChildrenCriterias(), viewFieldVos));
				
				crCriteriaVos.add(crCriteriaVo);
			}
		}
		
		return crCriteriaVos;
	}
	
	private Collection<CustomReportColumnVo> generateCRColumnVos(ColumnListType columns, Collection<CustomReportViewFieldVo> viewFieldVos) throws Exception{ 
		if(columns == null || columns.getColumn() == null || columns.getColumn().size()<1) {
			throw new Exception(NO_COLUMNS_IN_IMPORT_FILE);
		}
		
		Collection<CustomReportColumnVo> crColumnVos = new ArrayList<CustomReportColumnVo>();
		
		for(ColumnType column: columns.getColumn()){
			CustomReportColumnVo crColumnVo = new CustomReportColumnVo();
			
			// Determine the View Field for this column. If it cannot be determined, throw an exception
			CustomReportViewFieldVo crViewFieldVo = 
				CustomReportViewFieldVo.findBySQLName(column.getColumnName(), viewFieldVos);
			
			if(crViewFieldVo == null) throw new Exception(BAD_CR_VIEWFIELD_IMPORT_VALUE);
			crColumnVo.setCustomReportViewFieldVo(crViewFieldVo);
			
			crColumnVo.setHeader(column.getHeader());
			crColumnVo.setWidth(column.getWidth());
			crColumnVo.setDisplayOrder(column.getDisplayOrder());
			if(null != column.getFormatType()) {
				crColumnVo.setFormatVo(FieldFormatterTypeEnum.getCRColomnFormatVo(column.getFormatType()));
			}
			if(null != column.getAggregateType()) {
				crColumnVo.setAggregateVo(FieldAggregatorTypeEnum.getCRColumnAggregateVo(column.getAggregateType()));
			}
			
			crColumnVo.setSortBySequence(column.getSortBySeq());
			crColumnVo.setSortByType(column.getSortByType());
			crColumnVo.setGroupBySequence(column.getGroupBySeq());
			crColumnVo.setGroupByType(column.getGroupByType());
			crColumnVos.add(crColumnVo);
		}
		
		return crColumnVos;
	}
	
}
