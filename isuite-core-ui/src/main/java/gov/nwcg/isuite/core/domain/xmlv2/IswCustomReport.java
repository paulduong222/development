package gov.nwcg.isuite.core.domain.xmlv2;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

@XmlTransferTable(name = "IswCustomReport", table = "isw_custom_report")
public class IswCustomReport {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CUSTOM_REPORT", type = "LONG")
	private Long id = 0L;

	/*
	@XmlTransferField(name = "IswUser", type="COMPLEX", target=IswUser.class
				, lookupname="ID", sourcename="UserId")
	private IswUser iswUser;

	@XmlTransferField(name = "UserId", sqlname="USER_ID", type="LONG"
			,derived=true, derivedfield="IswUser")
	private Long userId;
*/
	
	@XmlTransferField(name = "Title", sqlname = "TITLE", type="STRING")
	private String title;

	@XmlTransferField(name = "SubTitle", sqlname = "SUB_TITLE", type="STRING")
	private String subTitle;

	@XmlTransferField(name = "Description", sqlname = "DESCRIPTION", type="STRING")
	private String description;
	
	/*
	@XmlTransferField(name = "IswlCustomReportView", type="COMPLEX", target=IswlCustomReportView.class
			, lookupname="ID", sourcename="CustomReportViewId")
	private IswlCustomReportView iswlCustomReportView;

	@XmlTransferField(name = "CustomReportViewId", sqlname="VIEW_ID", type="LONG"
		,derived=true, derivedfield="IswlCustomReportView")
	private Long customReportViewId;
	*/
	
	@XmlTransferField(name = "Landscape", sqlname = "IS_LANDSCAPE", type="STRING")
	private String landscape;

	@XmlTransferField(name = "LineSpacing", sqlname = "LINE_SPACING", type="STRING")
	private String lineSpacing;

	@XmlTransferField(name = "IsPublic", sqlname = "IS_PUBLIC", type="STRING")
	private String isPublic;


	public IswCustomReport() {
		super();
	}

}
