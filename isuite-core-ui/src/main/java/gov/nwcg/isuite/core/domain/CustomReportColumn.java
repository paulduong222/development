package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface CustomReportColumn extends Persistable {
	
	/**
	 * @param customReport the customReport to set
	 */
	public void setCustomReport(CustomReport customReport);

	/**
	 * @return the customReport
	 */
	public CustomReport getCustomReport();

	/**
	 * @param customReportId the customReportId to set
	 */
	public void setCustomReportId(Long customReportId);

	/**
	 * @return the customReportId
	 */
	public Long getCustomReportId();

	/**
	 * @param customReportViewField the customReportViewField to set
	 */
	public void setCustomReportViewField(CustomReportViewField customReportViewField);

	/**
	 * @return the customReportViewField
	 */
	public CustomReportViewField getCustomReportViewField();
	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption);

	/**
	 * @return the caption
	 */
	public String getCaption();

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Short displayOrder);

	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder();

	/**
	 * @param width the width to set
	 */
	public void setWidth(Short width);

	/**
	 * @return the width
	 */
	public Short getWidth();

	/**
	 * @param formatType the formatType to set
	 */
	public void setFormatType(String formatType);

	/**
	 * @return the formatType
	 */
	public String getFormatType();
	
	/**
	 * @param aggregateType the aggregateType to set
	 */
	public void setAggregateType(String aggregateType);

	/**
	 * @return the aggregateType
	 */
	public String getAggregateType();

	/**
	 * @param refTable the refTable to set
	 */
	public void setRefTable(String refTable);

	/**
	 * @return the refTable
	 */
	public String getRefTable();

	/**
	 * @param sortBySeq the sortBySeq to set
	 */
	public void setSortBySeq(Short sortBySeq);

	/**
	 * @return the sortBySeq
	 */
	public Short getSortBySeq();

	/**
	 * @param sortByType the sortByType to set
	 */
	public void setSortByType(String sortByType);

	/**
	 * @return the sortByType
	 */
	public String getSortByType();

	/**
	 * @param groupBySeq the groupBySeq to set
	 */
	public void setGroupBySeq(Short groupBySeq);

	/**
	 * @return the groupBySeq
	 */
	public Short getGroupBySeq();

	/**
	 * @param groupByType the groupByType to set
	 */
	public void setGroupByType(String groupByType);

	/**
	 * @return the groupByType
	 */
	public String getGroupByType();

}
