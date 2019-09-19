package gov.nwcg.isuite.framework.customreports.exportimport;

import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.core.vo.CustomReportCriteriaVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.xml.customreport.ColumnListType;
import gov.nwcg.isuite.xml.customreport.ColumnType;
import gov.nwcg.isuite.xml.customreport.CriteriaListType;
import gov.nwcg.isuite.xml.customreport.CriteriaType;
import gov.nwcg.isuite.xml.customreport.CustomReportExport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.context.ApplicationContext;

/**
 * Converts a CustomReportVo object to a CustomReportExport object.
 *
 */
public class CustomReportExportGenerator {
	private ApplicationContext context = null;
	
	public CustomReportExportGenerator(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public CustomReportExport generateCustomReportExport(CustomReportVo crVo) {

		CustomReportExport crExport = new CustomReportExport();
		crExport.setTitle(crVo.getReportTitle());
		crExport.setSubtitle(crVo.getSubTitle());
		crExport.setDescription(crVo.getDescription());
		crExport.setDataView(crVo.getCustomReportViewVo().getDataView());
		
		GregorianCalendar gregCalReportCreated = new GregorianCalendar();
		gregCalReportCreated.setTime(crVo.getCreatedDate());
		try {
			crExport.setCreateDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalReportCreated));
		} catch (DatatypeConfigurationException e) {
			System.out.println("Cannot instantiate DatatypeFactory for creating XMLGregorianCalendar for created date.");
		}

		GregorianCalendar gregCalNow = new GregorianCalendar();
		gregCalNow.setTime(new Date());
		try {
			crExport.setExportDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregCalNow));
		} catch (DatatypeConfigurationException e) {
			System.out.println("Cannot instantiate DatatypeFactory for creating XMLGregorianCalendar for exported date.");
		}
		
		crExport.setLandscape(crVo.getLandscape());
		crExport.setLineSpacing(crVo.getLineSpacing());

		crExport.setVisibleToPublic(crVo.getIsPublic());
		crExport.setCreatorFirstName(crVo.getUserVo().getFirstName());
		crExport.setCreatorLastName(crVo.getUserVo().getLastName());
		
		// Set Criterias/Filters
		if(crVo.getCustomReportCriteriaVos()!=null && crVo.getCustomReportCriteriaVos().size()>0){
			CriteriaListType childrenCriterias = new CriteriaListType();
			setCriterias(childrenCriterias.getCriteria(), crVo.getCustomReportCriteriaVos());
			crExport.setCriterias(childrenCriterias);
		} 
		
		// Set Columns
		Collection<CustomReportColumnVo> columnVos = new ArrayList<CustomReportColumnVo>();
		columnVos.addAll(crVo.getCustomReportColumnVos());
		columnVos.addAll(crVo.getSortOrderColumnVos());
		
		setColumns(crExport, columnVos);
		return crExport;
	}
	
	private void setCriterias(List<CriteriaType> criteriaList, Collection<CustomReportCriteriaVo> crCriteriaVos) {
		for(CustomReportCriteriaVo crCriteriaVo : crCriteriaVos){
			CriteriaType criteria = new CriteriaType();
			criteria.setConnective(crCriteriaVo.getConnective());
			criteria.setCriteriaType(crCriteriaVo.getType().equalsIgnoreCase("BLOCK") ? "B" : "S");
			
			if(null != crCriteriaVo.getSourceViewFieldVo()) {
				criteria.setSourceViewFieldSQLName(crCriteriaVo.getSourceViewFieldVo().getSqlName());
			}
			
			criteria.setSourceInputValue(crCriteriaVo.getSourceInputValue());
			criteria.setSourceEvaluatorType(crCriteriaVo.getSourceEvaluator()!=null?
					crCriteriaVo.getSourceEvaluator().getEvaluatorType():null);
			
			if(null != crCriteriaVo.getOperatorVo()) {
				criteria.setOperatorType(crCriteriaVo.getOperatorVo().getOperatorType());
			}
			criteria.setTargetType(crCriteriaVo.getTargetType());
			criteria.setTargetValue1(crCriteriaVo.getTargetValue());
			criteria.setTargetValue2(crCriteriaVo.getTargetValue2());
			
			if(null != crCriteriaVo.getTargetViewFieldVo()) {
				criteria.setTargetViewFieldSQLName(crCriteriaVo.getTargetViewFieldVo().getSqlName());
			}
			
			criteria.setTargetList(crCriteriaVo.getTargetListAsString());
			criteria.setTargetTermType(crCriteriaVo.getTargetTermVo()!=null?
					crCriteriaVo.getTargetTermVo().getTermType():null);
			criteria.setTargetEvaluatorType(crCriteriaVo.getTargetEvaluator()!=null?
					crCriteriaVo.getTargetEvaluator().getEvaluatorType():null);
			criteria.setDisplayOrder(crCriteriaVo.getDisplayOrder());
			criteria.setGroupID(crCriteriaVo.getGroupId());
			criteria.setParentGroupID(crCriteriaVo.getParentGroupId());
			// Set Child criterias/filters recursively
			if(crCriteriaVo.getChildren()!=null && crCriteriaVo.getChildren().size()>0){
				CriteriaListType childrenCriterias = new CriteriaListType();
				setCriterias(childrenCriterias.getCriteria(), crCriteriaVo.getChildren());
				criteria.setChildrenCriterias(childrenCriterias);
			}
			criteriaList.add(criteria);
		}
	}
	
	private void setColumns(CustomReportExport crExport, Collection<CustomReportColumnVo> crColumnVos){
		/*
			<cr:Columns>  		-- Class ColumnListType
    			<cr:Column>		-- Class ColumnType
    				...
    			</cr:Column>
    			<cr:Column>		-- Class ColumnType
    				...
    			</cr:Column>
			</cr:Columns>		
		
		*/
		
		ColumnListType columnList = crExport.getColumns();
		List<ColumnType> columns = null; 
		if(columnList==null){
			columnList = new ColumnListType();
			crExport.setColumns(columnList);
		}
		columns = columnList.getColumn();
		
		
		for(CustomReportColumnVo crColumnVo : crColumnVos){
			ColumnType column = new ColumnType();
			
			column.setColumnName(crColumnVo.getCustomReportViewFieldVo().getSqlName());
			column.setHeader(crColumnVo.getHeader());
			column.setWidth(crColumnVo.getWidth());
			column.setDisplayOrder(crColumnVo.getDisplayOrder());
			column.setFormatType(crColumnVo.getFormatVo()!=null?crColumnVo.getFormatVo().getFormatType():null);
			column.setAggregateType(crColumnVo.getAggregateVo()!=null?crColumnVo.getAggregateVo().getAggregateType():null);
			
			if(crColumnVo.getSortBySequence()!=null){
				column.setSortBySeq(crColumnVo.getSortBySequence());
			}
			column.setSortByType(crColumnVo.getSortByType());
			if(crColumnVo.getGroupBySequence()!=null){
				column.setGroupBySeq(crColumnVo.getGroupBySequence());
			}
			column.setGroupByType(crColumnVo.getGroupByType());
			columns.add(column);
		}
	}
}
