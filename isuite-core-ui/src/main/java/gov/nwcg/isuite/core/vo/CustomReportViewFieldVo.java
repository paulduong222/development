package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CustomReportViewField;
import gov.nwcg.isuite.core.domain.impl.CustomReportViewFieldImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomReportViewFieldVo extends AbstractVo {
	
	private Long viewId;
	private CustomReportViewVo customReportViewVo;
	private String displayName;	// This is what is initially shown to the user. Also what gets exported in the XML. 
	private String sqlName; // For imports, this value is determined using the displayName field imported from a previously exported XML.
	private String dataType;
	private String refDataType;
	private String formatType;
	
	public CustomReportViewFieldVo() {
		super();
	}

	/**
	 * Static method to return a CustomReportViewFieldVo from a list of Vos based on the sqlName field. This will be
	 * used to retrieve a CustomReportViewFieldVo when importing a previously exported CR XML file.
	 */
	public static CustomReportViewFieldVo findBySQLName(String columnNameToFind, Collection<CustomReportViewFieldVo> vos){
		if(vos==null || vos.size()<1 || columnNameToFind == null || "".equals(columnNameToFind.trim())) return null;
		
		for(CustomReportViewFieldVo vo: vos){
			if(vo.getSqlName().equalsIgnoreCase(columnNameToFind)){
				return vo;
			}
		}
		return null;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Collection<CustomReportViewFieldVo> getInstances(Collection<CustomReportViewField> entities, boolean cascadable) throws Exception {
		Collection<CustomReportViewFieldVo> vos = new ArrayList<CustomReportViewFieldVo>();
		for(CustomReportViewField entity : entities) {
			if(!entity.getColumnName().equalsIgnoreCase("INCIDENT_ID") && !entity.getColumnName().equalsIgnoreCase("INCIDENT_GROUP_ID")) {
				vos.add(CustomReportViewFieldVo.getInstance(entity, cascadable));
			}
		}
		Collections.sort((List)vos, new DisplayNameComparator());
		return vos;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportViewFieldVo getInstance(CustomReportViewField entity, boolean cascadable) throws Exception {
		if(null == entity) {
			throw new Exception("Unable to create CustomReportViewFieldVo from null CustomReportViewField entity.");
		}
		
		CustomReportViewFieldVo vo = new CustomReportViewFieldVo();
		
		vo.setId(entity.getId());
		if(cascadable){
			vo.setViewId(entity.getCustomReportView().getId());
			
			// The following line is causing a circular loop.
			//vo.setCustomReportViewVo(CustomReportViewVo.getInstance(entity.getCustomReportView(), cascadable));
			vo.setDisplayName(entity.getDisplayName());
			vo.setSqlName(entity.getColumnName());
			vo.setDataType(entity.getDataType());
			vo.setRefDataType(entity.getRefDataType());
			vo.setFormatType(entity.getFormatType());
			if(StringUtility.hasValue(vo.getFormatType())){
				//System.out.println("");
			}
		}
		return vo;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportViewField> toEntities(Collection<CustomReportViewFieldVo> vos, Boolean cascadable) throws Exception {
		Collection<CustomReportViewField> entities = new ArrayList<CustomReportViewField>();
		
		for(CustomReportViewFieldVo vo : vos){
			entities.add(CustomReportViewFieldVo.toEntity(vo,cascadable));
		}
		return entities;
	}
	
	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportViewField toEntity(CustomReportViewFieldVo vo, Boolean cascadable) throws Exception {
		if(vo==null) {
			throw new Exception("Unable to create CustomReportViewField entity from null CustomReportViewFieldVo.");
		}
		
		CustomReportViewField entity = new CustomReportViewFieldImpl();
		entity.setId(vo.getId());
		if(cascadable){
			// Possible circular loop below?
			// entity.setCustomReportView(CustomReportViewVo.toEntity(vo.getCustomReportViewVo(), cascadable));
			entity.setDisplayName(vo.getDisplayName());
			entity.setColumnName(vo.getSqlName());
			entity.setDataType(vo.getDataType());
			entity.setRefDataType(vo.getRefDataType());
		}
		return entity;
	}
	
	/**
	 * @param viewId
	 */
	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}
	
	/**
	 * @return
	 */
	public Long getViewId() {
		return viewId;
	}

	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * @return
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @param sqlName
	 */
	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}
	
	/**
	 * @return
	 */
	public String getSqlName() {
		return sqlName;
	}
	
	/**
	 * @param dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * @return
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * @param refDataType
	 */
	public void setRefDataType(String refDataType) {
		this.refDataType = refDataType;
	}
	
	/**
	 * @return
	 */
	public String getRefDataType() {
		return refDataType;
	}

	/**
	 * @return the customReportViewVo
	 */
	public CustomReportViewVo getCustomReportViewVo() {
		return customReportViewVo;
	}

	/**
	 * @param customReportViewVo the customReportViewVo to set
	 */
	public void setCustomReportViewVo(CustomReportViewVo customReportViewVo) {
		this.customReportViewVo = customReportViewVo;
	}
	
	@SuppressWarnings("unchecked")
	static class DisplayNameComparator implements Comparator {
		
		public int compare(Object vo1, Object vo2){
			
			String displayName1 = ((CustomReportViewFieldVo) vo1).getDisplayName();
			String displayName2 = ((CustomReportViewFieldVo) vo2).getDisplayName();
			
			return displayName1.compareToIgnoreCase(displayName2) ;
		}
		
	}

	public String getFormatType() {
		return formatType;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

}
