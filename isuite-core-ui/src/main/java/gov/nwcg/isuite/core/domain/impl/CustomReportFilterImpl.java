package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportFilter;
import gov.nwcg.isuite.core.domain.CustomReportViewField;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_CUSTOM_REPORT_FILTER", sequenceName="SEQ_CUSTOM_REPORT_FILTER")
@Table(name="isw_custom_report_filter")
public class CustomReportFilterImpl extends PersistableImpl implements CustomReportFilter {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SEQ_CUSTOM_REPORT_FILTER")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=CustomReportImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "CUSTOM_REPORT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private CustomReport customReport;
	
	@Column(name="CUSTOM_REPORT_ID", insertable = false, updatable = false)
	private Long customReportId;
	
	@Column(name = "CONNECTIVE", length = 50)
	private String connective;
	
	@ManyToOne(targetEntity=CustomReportFilterImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "PARENT_FILTER_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private CustomReportFilter customReportFilter;
	
	@Column(name = "PARENT_FILTER_ID", insertable = false, updatable = false, nullable = true)
	private Long parentFilterId;
	
	@OneToMany(targetEntity=CustomReportFilterImpl.class,cascade=CascadeType.ALL)
	@JoinColumn(name = "PARENT_FILTER_ID")
	private Collection<CustomReportFilter> childCustomReportFilters;
	
	@Column(name = "CRITERIA_TYPE", nullable = false, length = 1)
	private String criteriaType;
	
	@Column(name = "SOURCE_EVALUATOR_TYPE", length = 50)
	private String sourceEvaluatorType;
	
	@ManyToOne(targetEntity=CustomReportViewFieldImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name="SOURCE_FIELD_ID")
	private CustomReportViewField sourceField;
	
	@Column(name = "SOURCE_FIELD_ID", insertable = false, updatable = false)
	private Long sourceFieldId;
	
	@Column(name = "SOURCE_INPUT_VALUE", length = 50)
	private String sourceInputValue;
	
	@Column(name = "OPERAND", length = 50)
	private String operand;
	
	@Column(name = "TARGET_TYPE", length = 50)
	private String targetType;
	
	@Column(name = "TARGET_TERM_TYPE", length = 50)
	private String targetTermType;
	
	@ManyToOne(targetEntity=CustomReportViewFieldImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name="target_field_id")
	private CustomReportViewField targetField;

	@Column(name = "TARGET_FIELD_ID", insertable = false, updatable = false)
	private Long targetFieldId;
	
	@Column(name = "TARGET_EVALUATOR_TYPE", length = 50)
	private String targetEvaluatorType;
	
	@Column(name = "TARGET_VALUE_1", length = 1000)
	private String targetValue1;
	
	@Column(name = "TARGET_VALUE_2", length = 1000)
	private String targetValue2;
	
	@Column(name = "TARGET_LIST", length = 1000)
	private String targetValueList;
	
	@Column(name = "DISPLAY_ORDER", nullable = false)
	private Short displayOrder;
	
	public CustomReportFilterImpl() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomReport getCustomReport() {
		return customReport;
	}

	public void setCustomReport(CustomReport customReport) {
		this.customReport = customReport;
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

	public CustomReportFilter getCustomReportFilter() {
		return customReportFilter;
	}

	public void setCustomReportFilter(CustomReportFilter customReportFilter) {
		this.customReportFilter = customReportFilter;
	}

	public Long getParentFilterId() {
		return parentFilterId;
	}

	public void setParentFilterId(Long parentFilterId) {
		this.parentFilterId = parentFilterId;
	}

	public Collection<CustomReportFilter> getChildCustomReportFilters() {
		if(null==childCustomReportFilters)
			childCustomReportFilters = new ArrayList<CustomReportFilter>();
		return childCustomReportFilters;
	}

	public void setChildCustomReportFilters(
			Collection<CustomReportFilter> childCustomReportFilters) {
		this.childCustomReportFilters = childCustomReportFilters;
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
}
