package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.core.domain.CustomReportViewField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswCustomReportFilter", table="isw_custom_report_filter")
public class IswCustomReportFilter {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_CUSTOM_REPORT_FILTER", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "IswCustomReport", type = "COMPLEX", target = IswCustomReport.class, 
			lookupname = "ID", sourcename = "CustomReportId")
	private IswCustomReport iswCustomReport;

	@XmlTransferField(name = "CustomReportId", sqlname = "CUSTOM_REPORT_ID", type = "LONG", 
			derived = true, derivedfield = "IswCustomReport")
	private Long customReportId;
	
	@XmlTransferField(name = "Connective", sqlname = "CONNECTIVE", type="STRING")
	private String connective;
	
	@XmlTransferField(name = "IswCustomReportFilter", type = "COMPLEX", target = IswCustomReportFilter.class, 
			lookupname = "ID", sourcename = "CustomReportFilterId")
	private IswCustomReportFilter iswCustomReportFilter;

	@XmlTransferField(name = "CustomReportFilterId", sqlname = "PARENT_FILTER_ID", type = "LONG", 
			derived = true, derivedfield = "IswCustomReportFilter")
	private Long parentFilterId;
	
	@XmlTransferField(name = "CriteriaType", sqlname = "CRITERIA_TYPE", type="STRING")
	private String criteriaType;
	
	@XmlTransferField(name = "SourceEvaluatorType", sqlname = "SOURCE_EVALUATOR_TYPE", type="STRING")
	private String sourceEvaluatorType;
	
	@XmlTransferField(name = "SourceField", type = "COMPLEX", target = IswlCustomReportViewField.class, 
			lookupname = "ID", sourcename = "SourceFieldId")
	private CustomReportViewField sourceField;

	@XmlTransferField(name = "SourceFieldId", sqlname = "SOURCE_FIELD_ID", type = "LONG", 
			derived = true, derivedfield = "SourceField")
	private Long sourceFieldId;
	
	@XmlTransferField(name = "SourceInputValue", sqlname = "SOURCE_INPUT_VALUE", type="STRING")
	private String sourceInputValue;
	
	@XmlTransferField(name = "Operand", sqlname = "OPERAND", type="STRING")
	private String operand;
	
	@XmlTransferField(name = "TargetType", sqlname = "TARGET_TYPE", type="STRING")
	private String targetType;
	
	@XmlTransferField(name = "TargetTermType", sqlname = "TARGET_TERM_TYPE", type="STRING")
	private String targetTermType;
	
	@XmlTransferField(name = "TargetField", type = "COMPLEX", target = IswlCustomReportViewField.class, 
			lookupname = "ID", sourcename = "TargetFieldId")
	private CustomReportViewField targetField;

	@XmlTransferField(name = "TargetFieldId", sqlname = "TARGET_FIELD_ID", type = "LONG", 
			derived = true, derivedfield = "TargetField")
	private Long targetFieldId;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomReportId() {
		return customReportId;
	}

	public void setCustomReportId(Long customReportId) {
		this.customReportId = customReportId;
	}

	public String getConnective() {
		return connective;
	}

	public void setConnective(String connective) {
		this.connective = connective;
	}

	public Long getParentFilterId() {
		return parentFilterId;
	}

	public void setParentFilterId(Long parentFilterId) {
		this.parentFilterId = parentFilterId;
	}

	public String getCriteriaType() {
		return criteriaType;
	}

	public void setCriteriaType(String criteriaType) {
		this.criteriaType = criteriaType;
	}

	public String getSourceEvaluatorType() {
		return sourceEvaluatorType;
	}

	public void setSourceEvaluatorType(String sourceEvaluatorType) {
		this.sourceEvaluatorType = sourceEvaluatorType;
	}

	/**
	 * @param sourceField the sourceField to set
	 */
	public void setSourceField(CustomReportViewField sourceField) {
		this.sourceField = sourceField;
	}

	/**
	 * @return the sourceField
	 */
	public CustomReportViewField getSourceField() {
		return sourceField;
	}

	public Long getSourceFieldId() {
		return sourceFieldId;
	}

	public void setSourceFieldId(Long sourceFieldId) {
		this.sourceFieldId = sourceFieldId;
	}

	public String getSourceInputValue() {
		return sourceInputValue;
	}

	public void setSourceInputValue(String sourceInputValue) {
		this.sourceInputValue = sourceInputValue;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetTermType() {
		return targetTermType;
	}

	public void setTargetTermType(String targetTermType) {
		this.targetTermType = targetTermType;
	}

	/**
	 * @param targetField the targetField to set
	 */
	public void setTargetField(CustomReportViewField targetField) {
		this.targetField = targetField;
	}

	/**
	 * @return the targetField
	 */
	public CustomReportViewField getTargetField() {
		return targetField;
	}

	public Long getTargetFieldId() {
		return targetFieldId;
	}

	public void setTargetFieldId(Long targetFieldId) {
		this.targetFieldId = targetFieldId;
	}

	public String getTargetEvaluatorType() {
		return targetEvaluatorType;
	}

	public void setTargetEvaluatorType(String targetEvaluatorType) {
		this.targetEvaluatorType = targetEvaluatorType;
	}

	public String getTargetValue1() {
		return targetValue1;
	}

	public void setTargetValue1(String targetValue1) {
		this.targetValue1 = targetValue1;
	}

	public String getTargetValue2() {
		return targetValue2;
	}

	public void setTargetValue2(String targetValue2) {
		this.targetValue2 = targetValue2;
	}

	public String getTargetValueList() {
		return targetValueList;
	}

	public void setTargetValueList(String targetValueList) {
		this.targetValueList = targetValueList;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Short displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param iswCustomReport the iswCustomReport to set
	 */
	public void setIswCustomReport(IswCustomReport iswCustomReport) {
		this.iswCustomReport = iswCustomReport;
	}

	/**
	 * @return the iswCustomReport
	 */
	public IswCustomReport getIswCustomReport() {
		return iswCustomReport;
	}

	/**
	 * @param iswCustomReportFilter the iswCustomReportFilter to set
	 */
	public void setIswCustomReportFilter(IswCustomReportFilter iswCustomReportFilter) {
		this.iswCustomReportFilter = iswCustomReportFilter;
	}

	/**
	 * @return the iswCustomReportFilter
	 */
	public IswCustomReportFilter getIswCustomReportFilter() {
		return iswCustomReportFilter;
	}

}
