package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Collections;

import net.sf.jasperreports.engine.type.LineSpacingEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportColumn;
import gov.nwcg.isuite.core.domain.impl.CustomReportImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.customreports.designer.DesignModifierDelegate;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CustomReportVo extends AbstractVo implements PersistableVo {
	
	private UserVo userVo;
	private String reportTitle;
	private String subTitle;
	private String description;
	private CustomReportViewVo customReportViewVo;
	private Boolean landscape = new Boolean(true);
	private String lineSpacing; // 'Single' or 'OneAndHalf' or 'Double'
	private Boolean isPublic;
	private Boolean exportToExcel = new Boolean(false);
	private Collection<CustomReportCriteriaVo> customReportCriteriaVos = new ArrayList<CustomReportCriteriaVo>();
	private Collection<CustomReportColumnVo> customReportColumnVos = new ArrayList<CustomReportColumnVo>();
	private Collection<CustomReportColumnVo> sortOrderColumnVos = new ArrayList<CustomReportColumnVo>();
	
	private int _totalWidthOfColumns = 0;
	
	public CustomReportVo() {
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportVo getInstance(CustomReport entity, boolean cascadable) throws Exception {
		CustomReportVo vo = new CustomReportVo();
		
		if(null == entity)
			throw new Exception("Unable to create CustomReportVo from null CustomReport entity.");
		
		vo.setId(entity.getId());
		System.out.println(vo.getId());
		
		if(cascadable){
			
			if(null != entity.getUser()) {
				vo.setUserVo(UserVo.getInstance(entity.getUser(), true));
			}
			
			vo.setReportTitle(entity.getTitle());
			vo.setSubTitle(entity.getSubTitle());
			vo.setDescription(entity.getDescription());
			vo.setCreatedDate(entity.getCreatedDate()); // Needed when exporting the CR as XML
			
			vo.setCustomReportViewVo(CustomReportViewVo.getInstance(entity.getCustomReportView(), cascadable));
			
			vo.setLandscape(StringBooleanEnum.toBooleanValue(entity.getIsLandscape()));
			vo.setLineSpacing(entity.getLineSpacing());
			vo.setIsPublic(StringBooleanEnum.toBooleanValue(entity.getIsPublic()));
			
			if(null != entity.getCustomReportFilters()) {
				vo.setCustomReportCriteriaVos(CustomReportCriteriaVo.getHierarchicalInstances(entity.getCustomReportFilters(), true));
			}
			
			if(null != entity.getCustomReportColumns()) {
				Collection<CustomReportColumnVo> vos = CustomReportColumnVo.getInstances(entity.getCustomReportColumns(), true);
				Collection<CustomReportColumnVo> sortOrderColumnVos = new ArrayList<CustomReportColumnVo>();
				Collection<CustomReportColumnVo> columnVos = new ArrayList<CustomReportColumnVo>();
				
				for(CustomReportColumnVo columnVo : vos ) {
					if(StringUtility.hasValue(columnVo.getSortByType())){
						sortOrderColumnVos.add(columnVo);
					}else {
						columnVos.add(columnVo);
					}
				}
				
				vo.setCustomReportColumnVos(columnVos);
				vo.setSortOrderColumnVos(sortOrderColumnVos);
			}
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportVo> getInstances(Collection<CustomReport> entities, boolean cascadable) throws Exception {
		Collection<CustomReportVo> vos = new ArrayList<CustomReportVo>();
		
		for(CustomReport entity : entities) {
			vos.add(CustomReportVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static CustomReport toEntity(CustomReport entity, CustomReportVo vo, boolean cascadable,Persistable...persistables)throws Exception {
		if(null == entity){
			entity = new CustomReportImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			if(null != vo.getUserVo()) {
				entity.setUser(UserVo.toEntity(null, vo.getUserVo(), false));
			}
		
			entity.setTitle(vo.getReportTitle());
			entity.setSubTitle(vo.getSubTitle());
			entity.setDescription(vo.getDescription());
			entity.setCustomReportView(CustomReportViewVo.toEntity(vo.getCustomReportViewVo(), cascadable));
			entity.setIsLandscape(StringBooleanEnum.toEnumValue(vo.getLandscape()));
			entity.setLineSpacing(vo.getLineSpacing());
			
			entity.setIsPublic(StringBooleanEnum.toEnumValue(vo.getIsPublic()));
			
			if(null != vo.getCustomReportCriteriaVos()) {
				entity.setCustomReportFilters(CustomReportCriteriaVo.toEntityList(vo.getCustomReportCriteriaVos(), true, entity));
			}
			
			Collection<CustomReportColumnVo> colVos = new ArrayList<CustomReportColumnVo>();
			colVos.addAll(vo.getCustomReportColumnVos());
			colVos.addAll(vo.getSortOrderColumnVos());
			
			Collection<CustomReportColumn> addList = CustomReportColumnVo.toAddList(colVos, entity.getCustomReportColumns(), entity);
			Collection<CustomReportColumn> updateList = CustomReportColumnVo.toUpdateList(colVos, entity.getCustomReportColumns(), entity);
			
			if(null == entity.getCustomReportColumns()) {
				entity.setCustomReportColumns(new ArrayList<CustomReportColumn>());
			}
			
			entity.getCustomReportColumns().clear();
			
			if(CollectionUtility.hasValue(updateList)) {
				entity.getCustomReportColumns().addAll(updateList);
			}
				
			if(CollectionUtility.hasValue(addList)) {
				entity.getCustomReportColumns().addAll(addList);
			}
			
		}
		
		return entity;
	}
	
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReport> toEntityList(Collection<CustomReportVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CustomReport> entities = new ArrayList<CustomReport>();
		
		for(CustomReportVo vo : vos) {
			entities.add(CustomReportVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	
	
	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}


	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return userVo;
	}


	/**
	 * @param reportTitle the reportTitle to set
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}


	/**
	 * @return the reportTitle
	 */
	public String getReportTitle() {
		return (reportTitle == null ? "" : reportTitle.toUpperCase());
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
		return (subTitle == null ? "" : subTitle.toUpperCase());
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
	 * @param customReportViewVo the customReportViewVo to set
	 */
	public void setCustomReportViewVo(CustomReportViewVo customReportViewVo) {
		this.customReportViewVo = customReportViewVo;
	}


	/**
	 * @return the customReportViewVo
	 */
	public CustomReportViewVo getCustomReportViewVo() {
		return customReportViewVo;
	}


	/**
	 * @param landscape the landscape to set
	 */
	public void setLandscape(Boolean landscape) {
		this.landscape = landscape;
	}


	/**
	 * @return the landscape
	 */
	public Boolean getLandscape() {
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
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}


	/**
	 * @return the isPublic
	 */
	public Boolean getIsPublic() {
		return isPublic;
	}


	/**
	 * @param customReportCriteriaVos the customReportCriteriaVos to set
	 */
	public void setCustomReportCriteriaVos(Collection<CustomReportCriteriaVo> customReportCriteriaVos) {
		this.customReportCriteriaVos = customReportCriteriaVos;
	}


	/**
	 * @return the customReportCriteriaVos
	 */
	public Collection<CustomReportCriteriaVo> getCustomReportCriteriaVos() {
		return customReportCriteriaVos;
	}


	/**
	 * @param customReportColumnVos the customReportColumnVos to set
	 */
	public void setCustomReportColumnVos(Collection<CustomReportColumnVo> customReportColumnVos) {
		this.customReportColumnVos = customReportColumnVos;
	}

	/**
	 * @return the customReportColumnVos
	 */
	public Collection<CustomReportColumnVo> getCustomReportColumnVos() {
		Collections.sort((List<CustomReportColumnVo>)this.customReportColumnVos, new PositionComparator());
		return customReportColumnVos;
	}
	
	/**
	 * @param sortOrderColumnVos the sortOrderColumnVos to set
	 */
	public void setSortOrderColumnVos(Collection<CustomReportColumnVo> sortOrderColumnVos) {
		this.sortOrderColumnVos = sortOrderColumnVos;
	}

	/**
	 * @return the sortOrderColumnVos
	 */
	public Collection<CustomReportColumnVo> getSortOrderColumnVos() {
		Collections.sort((List<CustomReportColumnVo>)this. sortOrderColumnVos, new SequenceComparator());
		return sortOrderColumnVos;
	}

	public Boolean getExportToExcel() {
		return exportToExcel;
	}

	public void setExportToExcel(Boolean exportToExcel) {
		this.exportToExcel = exportToExcel;
	}
	
	/**
	 * Returns the JasperReport specific OrientationEnum for this Custom Report
	 * @return the OrientationEnum
	 */
	public OrientationEnum getCanonicalOrientation() {
		return getLandscape()?OrientationEnum.LANDSCAPE:OrientationEnum.PORTRAIT;
	}
	
	/**
	 * Returns the JasperReport specific LineSpacingEnum for this Custom Report
	 * @return
	 */
	public LineSpacingEnum getCanonicalLineSpacingEnum() {
		if("Double".equalsIgnoreCase(lineSpacing)){
			return LineSpacingEnum.DOUBLE;
		} else if ("OneAndHalf".equalsIgnoreCase(lineSpacing)){
			return LineSpacingEnum.ONE_AND_HALF;
		} else {
			return LineSpacingEnum.SINGLE;
		}
	}
	
	public int getTotalWidthOfColumns() {
		if(_totalWidthOfColumns == 0){
			for(CustomReportColumnVo customReportColumnVo : this.customReportColumnVos){
				_totalWidthOfColumns += customReportColumnVo.getWidth();
			}
		}
		return _totalWidthOfColumns;
	} 
	
	public int getTotalWidthOfColumnsIncludingPadding() {
		if(this.customReportColumnVos == null) return 0;
		return (getTotalWidthOfColumns() + (DesignModifierDelegate.COLUMN_PADDING_RIGHT * (this.customReportColumnVos.size()-1)));
	}
	
	class PositionComparator implements Comparator<CustomReportColumnVo>{
		
		public int compare(CustomReportColumnVo vo1, CustomReportColumnVo vo2){
			
			Integer vo1Position = vo1.getDisplayOrder();
			Integer vo2Position = vo2.getDisplayOrder();
			
			Integer result = 0;
			if(vo1Position != null && vo2Position != null) {
				result = vo1Position.compareTo(vo2Position);
			}
			
			return result;
		}
	}
	
	class SequenceComparator implements Comparator<CustomReportColumnVo>{
		
		public int compare(CustomReportColumnVo vo1, CustomReportColumnVo vo2){
			
			Integer vo1Position = vo1.getSortBySequence();
			Integer vo2Position = vo2.getSortBySequence();
			
			Integer result = 0;
			if(vo1Position != null && vo2Position != null) {
				result = vo1Position.compareTo(vo2Position);
			}
			
			return result;
		}
	}

}
