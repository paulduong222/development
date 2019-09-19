package gov.nwcg.isuite.core.domain.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportColumn;
import gov.nwcg.isuite.core.domain.CustomReportViewField;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_CUSTOM_REPORT_COLUMN", sequenceName="SEQ_CUSTOM_REPORT_COLUMN")
@Table(name="isw_custom_report_column")
public class CustomReportColumnImpl extends PersistableImpl implements CustomReportColumn {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SEQ_CUSTOM_REPORT_COLUMN")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=CustomReportImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "CUSTOM_REPORT_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private CustomReport customReport;
	
	@Column(name="CUSTOM_REPORT_ID", insertable = false, updatable = false)
	private Long customReportId;
	
//	@Column(name = "FIELD_NAME", nullable = false, length = 50)
//	private String fieldName;
	
	@ManyToOne(targetEntity=CustomReportViewFieldImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "FIELD_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private CustomReportViewField customReportViewField;
	
	@Column(name = "CAPTION", length = 50)
	private String caption;
	
	@Column(name = "DISPLAY_ORDER", nullable = false)
	private Short displayOrder;
	
	@Column(name = "WIDTH")
	private Short width;
	
	@Column(name = "FORMAT_TYPE", length = 50)
	private String formatType;
	
	@Column(name = "AGGREGATE_TYPE", length = 50)
	private String aggregateType;
	
	@Column(name = "REF_TABLE", length = 50)
	private String refTable;
	
	@Column(name = "SORT_BY_SEQ")
	private Short sortBySeq;
	
	@Column(name = "SORT_BY_TYPE", length = 1)
	private String sortByType;
	
	@Column(name = "GROUP_BY_SEQ")
	private Short groupBySeq;
	
	@Column(name = "GROUP_BY_TYPE", length = 20)
	private String groupByType;
	
	
	public CustomReportColumnImpl() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param customReport the customReport to set
	 */
	public void setCustomReport(CustomReport customReport) {
		this.customReport = customReport;
	}

	/**
	 * @return the customReport
	 */
	public CustomReport getCustomReport() {
		return customReport;
	}

	/**
	 * @param customReportId the customReportId to set
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
	 * @return the customReportViewField
	 */
	public CustomReportViewField getCustomReportViewField() {
		return customReportViewField;
	}

	/**
	 * @param customReportViewField the customReportViewField to set
	 */
	public void setCustomReportViewField(CustomReportViewField customReportViewField) {
		this.customReportViewField = customReportViewField;
	}

	/**
	 * @param caption the caption to set
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
	 * @param width the width to set
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
	 * @param formatType the formatType to set
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
	 * @param aggregateType the aggregateType to set
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
	 * @param refTable the refTable to set
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
	 * @param sortBySeq the sortBySeq to set
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
	 * @param sortByType the sortByType to set
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
	 * @param groupBySeq the groupBySeq to set
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
	 * @param groupByType the groupByType to set
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

}
