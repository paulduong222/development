package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.FinancialExport;
import gov.nwcg.isuite.core.domain.impl.FinancialExportImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class FinancialExportVo extends AbstractVo implements PersistableVo {
	private IncidentVo incidentVo;
	private Date exportDate;
	private String fileName;
	private Date lastExportDate;
	private String exportFileDestination;
	private String exportedBy;
	private IncidentGroupVo incidentGroupVo;
	private Boolean isFromSingleIncident;
	private IncidentVo incidentReferenceVo;
	
	
	public FinancialExportVo() {
		super();
	}
	
	public static FinancialExportVo getInstance(FinancialExport entity,boolean cascadable) throws Exception {
		FinancialExportVo vo = new FinancialExportVo();
		
		if(null == entity)
			throw new Exception("Unable to create FinancialExportVo from null FinancialExport entity.");
		
		vo.setId(entity.getId());
		if(cascadable){
			IncidentVo incidentRefVo = new IncidentVo();
			 
	        if(null != entity.getIncident()) {
	        	vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
	        	incidentRefVo.setId(entity.getIncident().getId());
	        	incidentRefVo.setIncidentName(entity.getIncident().getIncidentName());
	        }
	        
	        if(null != entity.getIncidentGroup()) {
	        	vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), false));
	        	incidentRefVo.setId(entity.getIncidentReferenceId());
		        incidentRefVo.setIncidentName(entity.getIncidentName());
	        }
	        
	        vo.setExportDate(entity.getExportDate());
	        vo.setFileName(entity.getFileName());
	        vo.setExportedBy(entity.getCreatedBy());
	        vo.setIsFromSingleIncident(StringBooleanEnum.toBooleanValue(entity.getIsFromSingleIncident()));
	        vo.setIncidentReferenceVo(incidentRefVo);
		}
		
		return vo;
	}
	
	public static Collection<FinancialExportVo> getInstances(Collection<FinancialExport> entities,boolean cascadable) throws Exception {
		Collection<FinancialExportVo> vos = new ArrayList<FinancialExportVo>();
		
		for(FinancialExport entity : entities){
			vos.add(FinancialExportVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static FinancialExport toEntity(FinancialExport entity, FinancialExportVo vo,boolean cascadable,Persistable...persistables)throws Exception {
		if (null == entity){
			entity = new FinancialExportImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			 
	        if (null != vo.getIncidentVo()) {
	        	entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
	        	entity.setIsFromSingleIncident(StringBooleanEnum.toEnumValue(Boolean.TRUE));
	        }
	        entity.setExportDate(vo.getExportDate());
	        entity.setFileName(vo.getFileName());
	        
	        if (null != vo.getIncidentGroupVo()) {
	        	entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
	        	entity.setIsFromSingleIncident(StringBooleanEnum.toEnumValue(Boolean.FALSE));
	        }
	        
	        if(null != vo.getIncidentReferenceVo()){
	        	entity.setIncidentName(vo.getIncidentReferenceVo().getIncidentName());
		        entity.setIncidentReferenceId(vo.getIncidentReferenceVo().getId());
	        }
		}		
		return entity;
	}
	
	
	
	public void setIncidentVo(IncidentVo incidentVo){
		this.incidentVo = incidentVo;
	}
	
	public IncidentVo getIncidentVo(){
		return incidentVo;
	}
	
	
	public void setExportDate(Date exportDate){
		this.exportDate = exportDate;
	}
	
	public Date getExportDate(){
		return exportDate;
	}
	
	public void setLastExportDate(Date lastExport){
		this.lastExportDate = lastExport;
		
	}
	
	public Date getLastExportDate(){
		return this.lastExportDate;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setExportFileDestination(String exportFileDestination){
		this.exportFileDestination = exportFileDestination;
	}
		
	public String getExportFileDestination(){
		return exportFileDestination;
	
	}
	public void setExportedBy(String exportedBy){
		this.exportedBy = exportedBy;
			
	}
	public String getExportedBy(){
		return exportedBy;
	}
	
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo){
		this.incidentGroupVo = incidentGroupVo;
	}
	
	public IncidentGroupVo getIncidentGroupVo(){
		return incidentGroupVo;
	}

	/**
	 * @param isFromSingleIncident the isFromSingleIncident to set
	 */
	public void setIsFromSingleIncident(Boolean isFromSingleIncident) {
		this.isFromSingleIncident = isFromSingleIncident;
	}

	/**
	 * @return the isFromSingleIncident
	 */
	public Boolean getIsFromSingleIncident() {
		return isFromSingleIncident;
	}

	/**
	 * @param incidentReferenceVo the incidentReferenceVo to set
	 */
	public void setIncidentReferenceVo(IncidentVo incidentReferenceVo) {
		this.incidentReferenceVo = incidentReferenceVo;
	}

	/**
	 * @return the incidentReferenceVo
	 */
	public IncidentVo getIncidentReferenceVo() {
		return incidentReferenceVo;
	}

}