package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.core.domain.CustomReportViewField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswCustomReportFilter", table="isw_custom_report_filter")
public class IswCustomReportFilter {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_CUSTOM_REPORT_FILTER", type="LONG")
	private Long id = 0L;
	
	/*
	@XmlTransferField(name = "IswCustomReport", type = "COMPLEX", target = IswCustomReport.class, 
			lookupname = "ID", sourcename = "CustomReportId")
	private IswCustomReport iswCustomReport;

	@XmlTransferField(name = "CustomReportId", sqlname = "CUSTOM_REPORT_ID", type = "LONG", 
			derived = true, derivedfield = "IswCustomReport")
	private Long customReportId;
	*/
	
	@XmlTransferField(name = "Connective", sqlname = "CONNECTIVE", type="STRING")
	private String connective;
	
	/*
	@XmlTransferField(name = "IswCustomReportFilter", type = "COMPLEX", target = IswCustomReportFilter.class, 
			lookupname = "ID", sourcename = "CustomReportFilterId")
	private IswCustomReportFilter iswCustomReportFilter;

	@XmlTransferField(name = "CustomReportFilterId", sqlname = "PARENT_FILTER_ID", type = "LONG", 
			derived = true, derivedfield = "IswCustomReportFilter")
	private Long parentFilterId;
	*/
	
	@XmlTransferField(name = "CriteriaType", sqlname = "CRITERIA_TYPE", type="STRING")
	private String criteriaType;
	
	@XmlTransferField(name = "SourceEvaluatorType", sqlname = "SOURCE_EVALUATOR_TYPE", type="STRING")
	private String sourceEvaluatorType;

	/*
	@XmlTransferField(name = "SourceField", type = "COMPLEX", target = IswlCustomReportViewField.class, 
			lookupname = "ID", sourcename = "SourceFieldId")
	private CustomReportViewField sourceField;

	@XmlTransferField(name = "SourceFieldId", sqlname = "SOURCE_FIELD_ID", type = "LONG", 
			derived = true, derivedfield = "SourceField")
	private Long sourceFieldId;
	*/
	
	@XmlTransferField(name = "SourceInputValue", sqlname = "SOURCE_INPUT_VALUE", type="STRING")
	private String sourceInputValue;
	
	@XmlTransferField(name = "Operand", sqlname = "OPERAND", type="STRING")
	private String operand;
	
	@XmlTransferField(name = "TargetType", sqlname = "TARGET_TYPE", type="STRING")
	private String targetType;
	
	@XmlTransferField(name = "TargetTermType", sqlname = "TARGET_TERM_TYPE", type="STRING")
	private String targetTermType;

	/*
	@XmlTransferField(name = "TargetField", type = "COMPLEX", target = IswlCustomReportViewField.class, 
			lookupname = "ID", sourcename = "TargetFieldId")
	private CustomReportViewField targetField;

	@XmlTransferField(name = "TargetFieldId", sqlname = "TARGET_FIELD_ID", type = "LONG", 
			derived = true, derivedfield = "TargetField")
	private Long targetFieldId;
	*/
	
	@XmlTransferField(name = "TargetEvaluatorType", sqlname = "TARGET_EVALUATOR_TYPE", type="STRING")
	private String targetEvaluatorType;
	
	@XmlTransferField(name = "TargetValue1", sqlname = "TARGET_VALUE_1", type="STRING")
	private String targetValue1;
	
	@XmlTransferField(name = "TargetValue2", sqlname = "TARGET_VALUE_2", type="STRING")
	private String targetValue2;
	
	@XmlTransferField(name = "TargetValueList", sqlname = "TARGET_LIST", type="STRING")
	private String targetValueList;
	
	@XmlTransferField(name = "DisplayOrder", sqlname = "DISPLAY_ORDER", type="SHORT")
	private Short displayOrder;
	
	public IswCustomReportFilter() {
		super();
	}

}
