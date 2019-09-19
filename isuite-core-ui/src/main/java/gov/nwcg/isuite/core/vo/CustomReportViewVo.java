package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CustomReportView;
import gov.nwcg.isuite.core.domain.impl.CustomReportViewImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class CustomReportViewVo extends AbstractVo {
	
	private String dataView; 		// CustomReportViewImpl:view_code
	private String displayName;		// CustomReportViewImpl:display_name
	private String sqlName;			// CustomReportViewImpl:view_name
	private Collection<SystemRoleVo> roleVos;
	private Collection<CustomReportViewFieldVo> customReportViewFieldVos;
	
	public CustomReportViewVo() {
		super();
	}
	
	/**
	 * Static method to return a CustomReportViewVo from a list of Vos based on the dataView field. This will be
	 * used to retrieve a CustomReportViewVo when importing a previously exported CR XML file.
	 */
	public static CustomReportViewVo findByDataView(String dataViewToFind, Collection<CustomReportViewVo> vos){
		if(vos==null || vos.size()<1 || dataViewToFind == null || "".equals(dataViewToFind.trim())) return null;
		
		for(CustomReportViewVo vo: vos){
			if(vo.getDataView().equalsIgnoreCase(dataViewToFind)){
				return vo;
			}
		}
		return null;
	}
	
	public static Collection<CustomReportViewVo> getInstances(Collection<CustomReportView> entities, boolean cascadable) throws Exception {
		Collection<CustomReportViewVo> vos = new ArrayList<CustomReportViewVo>();
		for(CustomReportView entity : entities) {
			vos.add(CustomReportViewVo.getInstance(entity, cascadable));
		}
		return vos;
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportViewVo getInstance(CustomReportView entity, boolean cascadable) throws Exception {
		if(null == entity)
			throw new Exception("Unable to create CustomReportViewVo from null CustomReportViewVo entity.");
		
		CustomReportViewVo vo = new CustomReportViewVo();
		vo.setId(entity.getId());
		if(cascadable){
			vo.setDataView(entity.getViewCode()); 			// dataView == CustomReportViewImpl:view_code
			vo.setDisplayName(entity.getDisplayName()); 	// displayName == CustomReportViewImpl:display_name
			vo.setSqlName(entity.getViewName());			// sqlName == CustomReportViewImpl:view_name
			vo.setRoleVos(SystemRoleVo.getInstances(entity.getSystemRoles(), cascadable));
			vo.setCustomReportViewFieldVos(CustomReportViewFieldVo.getInstances(entity.getCustomReportViewFields(), cascadable));
		}
		return vo;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CustomReportView> toEntities(Collection<CustomReportViewVo> vos, Boolean cascadable) throws Exception {
		Collection<CustomReportView> entities = new ArrayList<CustomReportView>();
		
		for(CustomReportViewVo vo : vos){
			entities.add(CustomReportViewVo.toEntity(vo,cascadable));
		}
		return entities;
	}
	
	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static CustomReportView toEntity(CustomReportViewVo vo, Boolean cascadable) throws Exception {
		if(vo==null) {
			throw new Exception("Unable to create CustomReportView entity from null CustomReportViewVo.");
		}
		
		CustomReportView entity = new CustomReportViewImpl();
		entity.setId(vo.getId());
		if(cascadable){
			entity.setViewCode(vo.getDataView()); 		// dataView == CustomReportViewImpl:view_code
			entity.setDisplayName(vo.getDisplayName());	// displayName == CustomReportViewImpl:display_name
			entity.setViewName(vo.getSqlName()); 		// sqlName == CustomReportViewImpl:view_name
			entity.setCustomReportViewFields(CustomReportViewFieldVo.toEntities(vo.getCustomReportViewFieldVos(), cascadable));
		}
		return entity;
	}
	
	/**
	 * @param dataView the dataView to set
	 */
	public void setDataView(String dataView) {
		this.dataView = dataView;
	}

	/**
	 * @return the dataView
	 */
	public String getDataView() {
		return dataView;
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
	 * @param roleVos the roleVos to set
	 */
	public void setRoleVos(Collection<SystemRoleVo> roleVos) {
		this.roleVos = roleVos;
	}

	/**
	 * @return the roleVos
	 */
	public Collection<SystemRoleVo> getRoleVos() {
		return roleVos;
	}

	/**
	 * @param customReportViewFieldVos
	 */
	public void setCustomReportViewFieldVos(Collection<CustomReportViewFieldVo> customReportViewFieldVos) {
		this.customReportViewFieldVos = customReportViewFieldVos;
	}
	
	/**
	 * @return
	 */
	public Collection<CustomReportViewFieldVo> getCustomReportViewFieldVos() {
		return customReportViewFieldVos;
	}
	
}
