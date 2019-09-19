package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswCustomReportColumn", table = "isw_custom_report")
public class IswCustomReportColumn {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CUSTOM_REPORT_COLUMN", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "IswCustomReport", type = "COMPLEX", target = IswCustomReport.class, 
			lookupname = "ID", sourcename = "CustomReportId")
	private IswCustomReport iswCustomReport;

	@XmlTransferField(name = "CustomReportId", sqlname = "CUSTOM_REPORT_ID", type = "LONG", 
			derived = true, derivedfield = "IswCustomReport")
	private Long customReportId;
	
	@XmlTransferField(name = "IswlCustomReportViewField", type = "COMPLEX", target = IswlCustomReportViewField.class, 
			lookupname = "ID", sourcename = "CustomReportViewFieldId")
	private IswlCustomReportViewField iswlCustomReportViewField;

	@XmlTransferField(name = "CustomReportViewFieldId", sqlname = "FIELD_ID", type = "LONG", 
			derived = true, derivedfield = "IswlCustomReportViewField")
	private Long customReportViewFieldId;

	@XmlTransferField(name = "Caption", sqlname = "CAPTION", type="STRING")
	private String caption;

	@XmlTransferField(name = "DisplayOrder", sqlname = "DISPLAY_ORDER", type="SHORT")
	private Short displayOrder;

	@XmlTransferField(name = "Width", sqlname = "WIDTH", type="SHORT")
	private Short width;

	@XmlTransferField(name = "FormatType", sqlname = "FORMAT_TYPE", type="STRING")
	private String formatType;

	@XmlTransferField(name = "AggregateType", sqlname = "AGGREGATE_TYPE", type="STRING")
	private String aggregateType;

	@XmlTransferField(name = "RefTable", sqlname = "REF_TABLE", type="STRING")
	private String refTable;

	@XmlTransferField(name = "SortBySeq", sqlname = "SORT_BY_SEQ", type="SHORT")
	private Short sortBySeq;

	@XmlTransferField(name = "SortByType", sqlname = "SORT_BY_TYPE", type="STRING")
	private String sortByType;

	@XmlTransferField(name = "GroupBySeq", sqlname = "GROUP_BY_SEQ", type="SHORT")
	private Short groupBySeq;

	@XmlTransferField(name = "GroupByType", sqlname = "GROUP_BY_TYPE", type="STRING")
	private String groupByType;

	public IswCustomReportColumn() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param customReportId
	 *            the customReportId to set
	 */
	public void setCustomReportId(Long customReportId) {
		this.customReportId = customReportId;
	}

	/**
	 * @return the customReportId
	 */
	public Long getCustomReportId() {
		return customReportId;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param displayOrder
	 *            the displayOrder to set
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
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Short width) {
		this.width = width;
	}

	/**
	 * @return the width
	 */
	public Short getWidth() {
		return width;
	}

	/**
	 * @param formatType
	 *            the formatType to set
	 */
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	/**
	 * @return the formatType
	 */
	public String getFormatType() {
		return formatType;
	}

	/**
	 * @param aggregateType
	 *            the aggregateType to set
	 */
	public void setAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	/**
	 * @return the aggregateType
	 */
	public String getAggregateType() {
		return aggregateType;
	}

	/**
	 * @param refTable
	 *            the refTable to set
	 */
	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	/**
	 * @return the refTable
	 */
	public String getRefTable() {
		return refTable;
	}

	/**
	 * @param sortBySeq
	 *            the sortBySeq to set
	 */
	public void setSortBySeq(Short sortBySeq) {
		this.sortBySeq = sortBySeq;
	}

	/**
	 * @return the sortBySeq
	 */
	public Short getSortBySeq() {
		return sortBySeq;
	}

	/**
	 * @param sortByType
	 *            the sortByType to set
	 */
	public void setSortByType(String sortByType) {
		this.sortByType = sortByType;
	}

	/**
	 * @return the sortByType
	 */
	public String getSortByType() {
		return sortByType;
	}

	/**
	 * @param groupBySeq
	 *            the groupBySeq to set
	 */
	public void setGroupBySeq(Short groupBySeq) {
		this.groupBySeq = groupBySeq;
	}

	/**
	 * @return the groupBySeq
	 */
	public Short getGroupBySeq() {
		return groupBySeq;
	}

	/**
	 * @param groupByType
	 *            the groupByType to set
	 */
	public void setGroupByType(String groupByType) {
		this.groupByType = groupByType;
	}

	/**
	 * @return the groupByType
	 */
	public String getGroupByType() {
		return groupByType;
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
	 * @param customReportViewFieldId the customReportViewFieldId to set
	 */
	public void setCustomReportViewFieldId(Long customReportViewFieldId) {
		this.customReportViewFieldId = customReportViewFieldId;
	}

	/**
	 * @return the customReportViewFieldId
	 */
	public Long getCustomReportViewFieldId() {
		return customReportViewFieldId;
	}

	/**
	 * @param iswlCustomReportViewField the iswlCustomReportViewField to set
	 */
	public void setIswlCustomReportViewField(IswlCustomReportViewField iswlCustomReportViewField) {
		this.iswlCustomReportViewField = iswlCustomReportViewField;
	}

	/**
	 * @return the iswlCustomReportViewField
	 */
	public IswlCustomReportViewField getIswlCustomReportViewField() {
		return iswlCustomReportViewField;
	}

}
