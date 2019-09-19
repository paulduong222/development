package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswCustomReport", table = "isw_custom_report")
public class IswCustomReport {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_CUSTOM_REPORT", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "IswUser", type="COMPLEX", target=IswUser.class
				, lookupname="ID", sourcename="UserId")
	private IswUser iswUser;
	
	@XmlTransferField(name = "UserId", sqlname="USER_ID", type="LONG"
			,derived=true, derivedfield="IswUser")
	private Long userId;

	@XmlTransferField(name = "Title", sqlname = "TITLE", type="STRING")
	private String title;

	@XmlTransferField(name = "SubTitle", sqlname = "SUB_TITLE", type="STRING")
	private String subTitle;

	@XmlTransferField(name = "Description", sqlname = "DESCRIPTION", type="STRING")
	private String description;
	
	@XmlTransferField(name = "IswlCustomReportView", type="COMPLEX", target=IswlCustomReportView.class
			, lookupname="ID", sourcename="CustomReportViewId")
	private IswlCustomReportView iswlCustomReportView;

	@XmlTransferField(name = "CustomReportViewId", sqlname="VIEW_ID", type="LONG"
		,derived=true, derivedfield="IswlCustomReportView")
	private Long customReportViewId;
	
	@XmlTransferField(name = "Landscape", sqlname = "IS_LANDSCAPE", type="STRING")
	private String landscape;

	@XmlTransferField(name = "LineSpacing", sqlname = "LINE_SPACING", type="STRING")
	private String lineSpacing;

	@XmlTransferField(name = "IsPublic", sqlname = "IS_PUBLIC", type="STRING")
	private String isPublic;


	public IswCustomReport() {
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
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param subTitle
	 *            the subTitle to set
	 */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	/**
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return subTitle;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param landscape
	 *            the landscape to set
	 */
	public void setIsLandscape(String landscape) {
		this.landscape = landscape;
	}

	/**
	 * @return the landscape
	 */
	public String getIsLandscape() {
		return landscape;
	}

	/**
	 * @param lineSpacing
	 *            the lineSpacing to set
	 */
	public void setLineSpacing(String lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

	/**
	 * @return the lineSpacing
	 */
	public String getLineSpacing() {
		return lineSpacing;
	}

	/**
	 * @param isPublic
	 *            the isPublic to set
	 */
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @return the isPublic
	 */
	public String getIsPublic() {
		return isPublic;
	}

	/**
	 * @param iswUser the iswUser to set
	 */
	public void setIswUser(IswUser iswUser) {
		this.iswUser = iswUser;
	}

	/**
	 * @return the iswUser
	 */
	public IswUser getIswUser() {
		return iswUser;
	}

	/**
	 * @param customReportViewId the customReportViewId to set
	 */
	public void setCustomReportViewId(Long customReportViewId) {
		this.customReportViewId = customReportViewId;
	}

	/**
	 * @return the customReportViewId
	 */
	public Long getCustomReportViewId() {
		return customReportViewId;
	}

	/**
	 * @param iswlCustomReportView the iswlCustomReportView to set
	 */
	public void setIswlCustomReportView(IswlCustomReportView iswlCustomReportView) {
		this.iswlCustomReportView = iswlCustomReportView;
	}

	/**
	 * @return the iswlCustomReportView
	 */
	public IswlCustomReportView getIswlCustomReportView() {
		return iswlCustomReportView;
	}

}
