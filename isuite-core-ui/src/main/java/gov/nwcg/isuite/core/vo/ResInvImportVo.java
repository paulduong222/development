package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.domain.ResInvImportConflict;
import gov.nwcg.isuite.core.domain.impl.ResInvImportImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ResInvImportVo extends AbstractVo implements PersistableVo {
	private String fileName;
	private String dispatchCenterCode;
	private String gaccUnitCode;
	private String errorDescription;
	private String status;
	private Date processedDate;
	private String importDateString;
	private Integer newImportCount=0;
	private Integer updatedImportCount=0;
	private Integer deletedCount=0;
	private String fileType;
	private Integer errorCount;
	
	private Collection<ResInvImportConflictVo> resInvImportConflictVos = new ArrayList<ResInvImportConflictVo>();

	public ResInvImportVo() {
	}

	/**
	 * Returns a ResInvImportVo instance from an ResInvImport entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of ResInvImportVo
	 * @throws Exception
	 */
	public static ResInvImportVo getInstance(ResInvImport entity,boolean cascadable) throws Exception {
		ResInvImportVo vo = new ResInvImportVo();

		if(null == entity)
			throw new Exception("Unable to create ResInvImportVo from null ResInvImport entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setFileName(entity.getFileName());
			vo.setDeletedCount(entity.getDeletedCount());
			vo.setDispatchCenterCode(entity.getDispatchCenterCode());
			vo.setGaccUnitCode(entity.getGaccUnitCode());
			vo.setErrorDescription(entity.getErrorDescription());
			vo.setNewImportCount(entity.getNewImportCount());
			vo.setProcessedDate(entity.getProcessedDate());
			if(DateUtil.hasValue(vo.getProcessedDate()))
				vo.setImportDateString(DateUtil.toDateString(vo.getProcessedDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
			vo.setStatus(entity.getStatus());
			vo.setUpdatedImportCount(entity.getUpdatedImportCount());

			if(StringUtility.hasValue(vo.getFileName())){
				if(vo.getFileName().indexOf("ACE")>0)
					vo.setFileType("ACE");
				else
					vo.setFileType("OH");
			}
			if(CollectionUtility.hasValue(entity.getResInvImportConflicts())){
				vo.setResInvImportConflictVos(ResInvImportConflictVo.getInstances(entity.getResInvImportConflicts(), true));
				vo.setErrorCount(vo.getResInvImportConflictVos().size());
			}else
				vo.setErrorCount(0);
		}

		return vo;
	}

	public static Collection<ResInvImportVo> getInstances(Collection<ResInvImport> entities, boolean cascadable) throws Exception {
		Collection<ResInvImportVo> vos = new ArrayList<ResInvImportVo>();

		for(ResInvImport entity : entities){
			vos.add(ResInvImportVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a ResInvImport entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of ResInvImport entity
	 * @throws Exception
	 */
	public static ResInvImport toEntity(ResInvImport entity, ResInvImportVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity){
			entity=new ResInvImportImpl();
			entity.setId(vo.getId());
		}
		
		
		if(cascadable){
			entity.setFileName(vo.getFileName());
			entity.setDeletedCount(vo.getDeletedCount());
			entity.setDispatchCenterCode(vo.getDispatchCenterCode());
			entity.setGaccUnitCode(vo.getGaccUnitCode());
			entity.setErrorDescription(vo.getErrorDescription());
			entity.setNewImportCount(vo.getNewImportCount());
			entity.setProcessedDate(vo.getProcessedDate());
			entity.setStatus(vo.getStatus());
			entity.setUpdatedImportCount(vo.getUpdatedImportCount());
			
			if(CollectionUtility.hasValue(vo.getResInvImportConflictVos())){
				for(ResInvImportConflictVo cvo : vo.getResInvImportConflictVos()){
					ResInvImportConflict conflict = ResInvImportConflictVo.toEntity(null, cvo, true);
					conflict.setResInvImport(entity);
					entity.getResInvImportConflicts().add(conflict);
				}
			}
			
		}

		return entity;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the dispatchCenterCode
	 */
	public String getDispatchCenterCode() {
		return dispatchCenterCode;
	}

	/**
	 * @param dispatchCenterCode the dispatchCenterCode to set
	 */
	public void setDispatchCenterCode(String dispatchCenterCode) {
		this.dispatchCenterCode = dispatchCenterCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the processedDate
	 */
	public Date getProcessedDate() {
		return processedDate;
	}

	/**
	 * @param processedDate the processedDate to set
	 */
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	/**
	 * @return the newImportCount
	 */
	public Integer getNewImportCount() {
		return newImportCount;
	}

	/**
	 * @param newImportCount the newImportCount to set
	 */
	public void setNewImportCount(Integer newImportCount) {
		this.newImportCount = newImportCount;
	}

	/**
	 * @return the updatedImportCount
	 */
	public Integer getUpdatedImportCount() {
		return updatedImportCount;
	}

	/**
	 * @param updatedImportCount the updatedImportCount to set
	 */
	public void setUpdatedImportCount(Integer updatedImportCount) {
		this.updatedImportCount = updatedImportCount;
	}

	/**
	 * @return the deletedCount
	 */
	public Integer getDeletedCount() {
		return deletedCount;
	}

	/**
	 * @param deletedCount the deletedCount to set
	 */
	public void setDeletedCount(Integer deletedCount) {
		this.deletedCount = deletedCount;
	}

	/**
	 * @return the resInvImportConflictVos
	 */
	public Collection<ResInvImportConflictVo> getResInvImportConflictVos() {
		return resInvImportConflictVos;
	}

	/**
	 * @param resInvImportConflictVos the resInvImportConflictVos to set
	 */
	public void setResInvImportConflictVos(
			Collection<ResInvImportConflictVo> resInvImportConflictVos) {
		this.resInvImportConflictVos = resInvImportConflictVos;
	}

	/**
	 * @return the gaccUnitCode
	 */
	public String getGaccUnitCode() {
		return gaccUnitCode;
	}

	/**
	 * @param gaccUnitCode the gaccUnitCode to set
	 */
	public void setGaccUnitCode(String gaccUnitCode) {
		this.gaccUnitCode = gaccUnitCode;
	}

	/**
	 * @return the importDateString
	 */
	public String getImportDateString() {
		return importDateString;
	}

	/**
	 * @param importDateString the importDateString to set
	 */
	public void setImportDateString(String importDateString) {
		this.importDateString = importDateString;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the errorCount
	 */
	public Integer getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount the errorCount to set
	 */
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public void incrementNewCount(){
		this.newImportCount++;
	}
	
	public void incrementUpdatedCount(){
		this.updatedImportCount++;
	}
	
	public void incrementDeletedCount(){
		this.deletedCount++;
	}
	
	public void incrementErrorCount(){
		this.errorCount++;
	}
}
