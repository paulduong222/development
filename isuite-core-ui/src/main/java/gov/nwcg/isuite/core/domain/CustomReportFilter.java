package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface CustomReportFilter extends Persistable {
	public Long getId();

	public void setId(Long id);

	public CustomReport getCustomReport();

	public void setCustomReport(CustomReport customReport);

	public Long getCustomReportId();

	public void setCustomReportId(Long customReportId);

	public String getConnective();

	public void setConnective(String connective);

	public CustomReportFilter getCustomReportFilter();

	public void setCustomReportFilter(CustomReportFilter customReportFilter);

	public Long getParentFilterId();

	public void setParentFilterId(Long parentFilterId);

	public Collection<CustomReportFilter> getChildCustomReportFilters();

	public void setChildCustomReportFilters(
			Collection<CustomReportFilter> childCustomReportFilters);

	public String getCriteriaType();

	public void setCriteriaType(String criteriaType);

	public String getSourceEvaluatorType();

	public void setSourceEvaluatorType(String sourceEvaluatorType);
	
	/**
	 * @param sourceField the sourceField to set
	 */
	public void setSourceField(CustomReportViewField sourceField);

	/**
	 * @return the sourceField
	 */
	public CustomReportViewField getSourceField();

	public Long getSourceFieldId();

	public void setSourceFieldId(Long sourceFieldId);

	public String getSourceInputValue();

	public void setSourceInputValue(String sourceInputValue);

	public String getOperand();

	public void setOperand(String operand);

	public String getTargetType();

	public void setTargetType(String targetType);

	public String getTargetTermType();

	public void setTargetTermType(String targetTermType);
	
	/**
	 * @param targetField the targetField to set
	 */
	public void setTargetField(CustomReportViewField targetField);
	
	/**
	 * @return the targetField
	 */
	public CustomReportViewField getTargetField();

	public Long getTargetFieldId();

	public void setTargetFieldId(Long targetFieldId);

	public String getTargetEvaluatorType();

	public void setTargetEvaluatorType(String targetEvaluatorType);

	public String getTargetValue1();

	public void setTargetValue1(String targetValue1);

	public String getTargetValue2();

	public void setTargetValue2(String targetValue2);

	public String getTargetValueList();

	public void setTargetValueList(String targetValueList);
	
	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Short displayOrder);

	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder();
}
