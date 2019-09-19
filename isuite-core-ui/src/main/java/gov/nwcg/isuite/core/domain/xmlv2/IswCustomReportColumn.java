package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswCustomReportColumn", table = "isw_custom_report")
public class IswCustomReportColumn {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CUSTOM_REPORT_COLUMN", type = "LONG")
	private Long id = 0L;

	/*
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
*/

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

}
