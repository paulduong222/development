package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface CustomReport extends Persistable {

	/**
	 * @param user the user to set
	 */
	public void setUser(User user);

	/**
	 * @return the user
	 */
	public User getUser();

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId);

	/**
	 * @return the userId
	 */
	public Long getUserId();

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title);

	/**
	 * @return the title
	 */
	public String getTitle();

	/**
	 * @param subTitle the subTitle to set
	 */
	public void setSubTitle(String subTitle);

	/**
	 * @return the subTitle
	 */
	public String getSubTitle();

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @return the customReportView
	 */
	public CustomReportView getCustomReportView();

	/**
	 * @param customReportView the customReportView to set
	 */
	public void setCustomReportView(CustomReportView customReportView);
	/**
	 * @param landscape the landscape to set
	 */
	public void setIsLandscape(StringBooleanEnum landscape);

	/**
	 * @return the landscape
	 */
	public StringBooleanEnum getIsLandscape();

	/**
	 * @param lineSpacing the lineSpacing to set
	 */
	public void setLineSpacing(String lineSpacing);

	/**
	 * @return the lineSpacing
	 */
	public String getLineSpacing();

	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(StringBooleanEnum isPublic);

	/**
	 * @return the isPublic
	 */
	public StringBooleanEnum getIsPublic();

	/**
	 * @param customReportColumns the customReportColumns to set
	 */
	public void setCustomReportColumns(Collection<CustomReportColumn> customReportColumns);

	/**
	 * @return the customReportColumns
	 */
	public Collection<CustomReportColumn> getCustomReportColumns();

	/**
	 * @param customReportFilters the customReportFilters to set
	 */
	public void setCustomReportFilters(Collection<CustomReportFilter> customReportFilters);

	/**
	 * @return the customReportFilters
	 */
	public Collection<CustomReportFilter> getCustomReportFilters();

}
