package gov.nwcg.isuite.core.domain.impl;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportColumn;
import gov.nwcg.isuite.core.domain.CustomReportFilter;
import gov.nwcg.isuite.core.domain.CustomReportView;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

@Entity
@SequenceGenerator(name="SEQ_CUSTOM_REPORT", sequenceName="SEQ_CUSTOM_REPORT")
@Table(name="isw_custom_report")
public class CustomReportImpl extends PersistableImpl implements CustomReport {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SEQ_CUSTOM_REPORT")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=UserImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private User user;
	
	@Column(name="USER_ID", insertable = false, updatable = false)
	private Long userId;
	
	@Column(name = "TITLE", nullable = false, length = 50)
	private String title;
	
	@Column(name = "SUB_TITLE", length = 50)
	private String subTitle;
	
	@Column(name = "DESCRIPTION", length = 200)
	private String description;
	
	@ManyToOne(targetEntity=CustomReportViewImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "VIEW_ID", insertable = true, updatable = true, unique = false, nullable = false)
	private CustomReportView customReportView;
	
	@Column(name = "IS_LANDSCAPE", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum landscape;
	
	@Column(name = "LINE_SPACING", nullable = false, length = 50)
	private String lineSpacing;
	
	@Column(name = "IS_PUBLIC", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isPublic;
	
	@OneToMany(targetEntity=CustomReportColumnImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customReport")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<CustomReportColumn> customReportColumns;
	
	@OneToMany(targetEntity=CustomReportFilterImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customReport")
	private Collection<CustomReportFilter> customReportFilters;
	
	
	public CustomReportImpl() {
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
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param userId the userId to set
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
	 * @param title the title to set
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
	 * @param subTitle the subTitle to set
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
	 * @param description the description to set
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
	 * @return the customReportView
	 */
	public CustomReportView getCustomReportView() {
		return customReportView;
	}


	/**
	 * @param customReportView the customReportView to set
	 */
	public void setCustomReportView(CustomReportView customReportView) {
		this.customReportView = customReportView;
	}

	/**
	 * @param landscape the landscape to set
	 */
	public void setIsLandscape(StringBooleanEnum landscape) {
		this.landscape = landscape;
	}

	/**
	 * @return the landscape
	 */
	public StringBooleanEnum getIsLandscape() {
		return landscape;
	}

	/**
	 * @param lineSpacing the lineSpacing to set
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
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(StringBooleanEnum isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @return the isPublic
	 */
	public StringBooleanEnum getIsPublic() {
		return isPublic;
	}

	/**
	 * @param customReportColumns the customReportColumns to set
	 */
	public void setCustomReportColumns(Collection<CustomReportColumn> customReportColumns) {
		this.customReportColumns = customReportColumns;
	}

	/**
	 * @return the customReportColumns
	 */
	public Collection<CustomReportColumn> getCustomReportColumns() {
//		if(null == customReportColumns) {
//			customReportColumns = new ArrayList<CustomReportColumn>();
//		}
		return customReportColumns;
	}

	/**
	 * @param customReportFilters the customReportFilters to set
	 */
	public void setCustomReportFilters(Collection<CustomReportFilter> customReportFilters) {
		this.customReportFilters = customReportFilters;
	}

	/**
	 * @return the customReportFilters
	 */
	public Collection<CustomReportFilter> getCustomReportFilters() {
		return customReportFilters;
	}

}
