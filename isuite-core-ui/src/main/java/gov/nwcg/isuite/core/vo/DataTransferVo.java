package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.DataTransfer;
import gov.nwcg.isuite.core.domain.impl.DataTransferImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class DataTransferVo extends AbstractVo implements PersistableVo {
	private String filename;
	private String email;
	private String phone;
	private String filePassword;
	private String confirmFilePassword;
	private String sourceSystem;
	private String storedFilepath;
	private Long incidentId;
	private Long incidentGroupId;
	private String exportDate;
	private String transferType;
	private String incidentOrGroupName;
	private String enterpriseLoginName;
	private String enterprisePassword;
	private String enterpriseFileId;
	private String enterpriseFilePath;
	private Long enterpriseDSUserId;
	private String mode;
	private String incidentGroupTransferableIdentity;
	private String incidentGroupName;
	private String exportFile;
	private String fileCreatedDate;
	private Boolean generateVersion2File;
	
	public DataTransferVo() {
	}

	/**
	 * Returns a DataTransferVo instance from an DataTransfer entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of DataTransferVo
	 * @throws Exception
	 */
	public static DataTransferVo getInstance(DataTransfer entity,boolean cascadable) throws Exception {
		DataTransferVo vo = new DataTransferVo();

		if(null == entity)
			throw new Exception("Unable to create DataTransferVo from null DataTransfer entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setEmail(entity.getEmail());
			vo.setFilename(entity.getFilename());
			vo.setFilePassword(entity.getFilePassword());
			vo.setIncidentGroupId(entity.getIncidentGroupId());
			vo.setIncidentId(entity.getIncidentId());
			vo.setPhone(entity.getPhone());
			vo.setSourceSystem(entity.getSourceSystem());
			vo.setStoredFilepath(entity.getStoredFilepath());
			
			if(DateUtil.hasValue(entity.getCreatedDate())){
				String dt=DateUtil.toDateString(entity.getCreatedDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
				vo.setExportDate(dt);
			}
			
			vo.setCreatedBy(entity.getCreatedBy());
		}

		return vo;
	}

	public static Collection<DataTransferVo> getInstances(Collection<DataTransfer> entities, boolean cascadable) throws Exception {
		Collection<DataTransferVo> vos = new ArrayList<DataTransferVo>();

		for(DataTransfer entity : entities){
			vos.add(DataTransferVo.getInstance(entity, cascadable));
		}
		return vos;
	}

	/**
	 * Returns a DataTransfer entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of DataTransfer entity
	 * @throws Exception
	 */
	public static DataTransfer toEntity(DataTransfer entity, DataTransferVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity){
			entity=new DataTransferImpl();
		}
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setEmail(vo.getEmail());
			entity.setFilename(vo.getFilename());
			entity.setFilePassword(vo.getFilePassword());
			if(LongUtility.hasValue(vo.getIncidentGroupId()))
				entity.setIncidentGroupId(vo.getIncidentGroupId());
			if(LongUtility.hasValue(vo.getIncidentId()))
				entity.setIncidentId(vo.getIncidentId());
			entity.setPhone(vo.getPhone());
			entity.setSourceSystem(vo.getSourceSystem());
			entity.setStoredFilepath(vo.getStoredFilepath());
		}

		return entity;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the filePassword
	 */
	public String getFilePassword() {
		return filePassword;
	}

	/**
	 * @param filePassword the filePassword to set
	 */
	public void setFilePassword(String filePassword) {
		this.filePassword = filePassword;
	}

	/**
	 * @return the sourceSystem
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the confirmFilePassword
	 */
	public String getConfirmFilePassword() {
		return confirmFilePassword;
	}

	/**
	 * @param confirmFilePassword the confirmFilePassword to set
	 */
	public void setConfirmFilePassword(String confirmFilePassword) {
		this.confirmFilePassword = confirmFilePassword;
	}

	/**
	 * @return the storedFilepath
	 */
	public String getStoredFilepath() {
		return storedFilepath;
	}

	/**
	 * @param storedFilepath the storedFilepath to set
	 */
	public void setStoredFilepath(String storedFilepath) {
		this.storedFilepath = storedFilepath;
	}

	/**
	 * @return the exportDate
	 */
	public String getExportDate() {
		return exportDate;
	}

	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the incidentOrGroupName
	 */
	public String getIncidentOrGroupName() {
		return incidentOrGroupName;
	}

	/**
	 * @param incidentOrGroupName the incidentOrGroupName to set
	 */
	public void setIncidentOrGroupName(String incidentOrGroupName) {
		this.incidentOrGroupName = incidentOrGroupName;
	}

	/**
	 * @return the enterpriseLoginName
	 */
	public String getEnterpriseLoginName() {
		return enterpriseLoginName;
	}

	/**
	 * @param enterpriseLoginName the enterpriseLoginName to set
	 */
	public void setEnterpriseLoginName(String enterpriseLoginName) {
		this.enterpriseLoginName = enterpriseLoginName;
	}

	/**
	 * @return the enterprisePassword
	 */
	public String getEnterprisePassword() {
		return enterprisePassword;
	}

	/**
	 * @param enterprisePassword the enterprisePassword to set
	 */
	public void setEnterprisePassword(String enterprisePassword) {
		this.enterprisePassword = enterprisePassword;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the incidentGroupTransferableIdentity
	 */
	public String getIncidentGroupTransferableIdentity() {
		return incidentGroupTransferableIdentity;
	}

	/**
	 * @param incidentGroupTransferableIdentity the incidentGroupTransferableIdentity to set
	 */
	public void setIncidentGroupTransferableIdentity(
			String incidentGroupTransferableIdentity) {
		this.incidentGroupTransferableIdentity = incidentGroupTransferableIdentity;
	}

	/**
	 * @return the exportFile
	 */
	public String getExportFile() {
		return exportFile;
	}

	/**
	 * @param exportFile the exportFile to set
	 */
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}

	/**
	 * @return the incidentGroupName
	 */
	public String getIncidentGroupName() {
		return incidentGroupName;
	}

	/**
	 * @param incidentGroupName the incidentGroupName to set
	 */
	public void setIncidentGroupName(String incidentGroupName) {
		this.incidentGroupName = incidentGroupName;
	}

	/**
	 * @return the enterpriseFileId
	 */
	public String getEnterpriseFileId() {
		return enterpriseFileId;
	}

	/**
	 * @param enterpriseFileId the enterpriseFileId to set
	 */
	public void setEnterpriseFileId(String enterpriseFileId) {
		this.enterpriseFileId = enterpriseFileId;
	}

	/**
	 * @return the enterpriseFilePath
	 */
	public String getEnterpriseFilePath() {
		return enterpriseFilePath;
	}

	/**
	 * @param enterpriseFilePath the enterpriseFilePath to set
	 */
	public void setEnterpriseFilePath(String enterpriseFilePath) {
		this.enterpriseFilePath = enterpriseFilePath;
	}

	/**
	 * @return the fileCreatedDate
	 */
	public String getFileCreatedDate() {
		return fileCreatedDate;
	}

	/**
	 * @param fileCreatedDate the fileCreatedDate to set
	 */
	public void setFileCreatedDate(String fileCreatedDate) {
		this.fileCreatedDate = fileCreatedDate;
	}

	/**
	 * @return the enterpriseDSUserId
	 */
	public Long getEnterpriseDSUserId() {
		return enterpriseDSUserId;
	}

	/**
	 * @param enterpriseDSUserId the enterpriseDSUserId to set
	 */
	public void setEnterpriseDSUserId(Long enterpriseDSUserId) {
		this.enterpriseDSUserId = enterpriseDSUserId;
	}

	/**
	 * @return the transferType
	 */
	public String getTransferType() {
		return transferType;
	}

	/**
	 * @param transferType the transferType to set
	 */
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public Boolean getGenerateVersion2File() {
		return generateVersion2File;
	}

	public void setGenerateVersion2File(Boolean generateVersion2File) {
		this.generateVersion2File = generateVersion2File;
	}



}
