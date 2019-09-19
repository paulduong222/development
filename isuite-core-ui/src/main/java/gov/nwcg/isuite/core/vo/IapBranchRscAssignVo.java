package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchRscAssignImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import java.util.Date;

public class IapBranchRscAssignVo extends AbstractVo implements PersistableVo {
	private Long iapBranchId;
	private String resourceName;
	private String lastName;
	private String firstName;
	private String leaderName;
	private Integer nbrOfPersonnel;
	private String nbrOfPersonnelString;
	private Boolean transportation;
	private String dropOffPoint;
	private String dropOffTime;
	private String pickUpPoint;
	private String pickUpTime;
	private String contactInfo;
	private String additionalInfo;
	private DateTransferVo lastDayToWorkDateVo;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	private String requestNumber;
	private String itemCode;
	private Long resourceId;

	// helper properties
	private Boolean trainee=false;
	private String leaderLastName;
	private String leaderFirstName;
	private Boolean leaderIsTrainee=false;
	
	/**
	 * Constructor
	 */
	public IapBranchRscAssignVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapBranchRscAssignVo getInstance(IapBranchRscAssign entity, boolean cascadable) throws Exception {
		IapBranchRscAssignVo vo = new IapBranchRscAssignVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapBranchRscAssignVo from null IapBranchRscAssign entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			// Set blank line property first as it is used ahead
			vo.setIsBlankLine(entity.getIsBlankLine().getValue());
			
			vo.setIapBranchId(entity.getIapBranchId());
			vo.setResourceName(entity.getResourceName());
			vo.setResourceId(entity.getResourceId());
			vo.setLeaderName(entity.getLeaderName());
			vo.setNbrOfPersonnel(entity.getNbrOfPersonnel());
			if(IntegerUtility.hasValue(entity.getNbrOfPersonnel())){
				vo.setNbrOfPersonnelString(String.valueOf(entity.getNbrOfPersonnel()));
			}
//			NOTE: #4473 - Removing the default setting of nbr personnel to 0
//			else {
//				if(!vo.getIsBlankLine()) {
//					vo.setNbrOfPersonnelString("0");
//				} // Else - If this is empty and a blank line, let nbr personnel string be null
//			}
			vo.setTransportation(entity.getTransportation().getValue());
			vo.setDropOffPoint(entity.getDropOffPoint());
			vo.setDropOffTime(entity.getDropOffTime());
			vo.setPickUpPoint(entity.getPickUpPoint());
			vo.setPickUpTime(entity.getPickUpTime());
			vo.setContactInfo(entity.getContactInfo());
			vo.setAdditionalInfo(entity.getAdditionalInfo());
			vo.setLastDayToWorkDateVo(new DateTransferVo());
			if(DateUtil.hasValue(entity.getLastDayToWorkDate())){
				DateTransferVo.populateDate(vo.getLastDayToWorkDateVo(), entity.getLastDayToWorkDate());
				// 5/26/2017 force 0000 timestamp on lastworkdaytodate field
				vo.getLastDayToWorkDateVo().setTimeString("0000");
			}
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapBranchRscAssignVo> getInstances(Collection<IapBranchRscAssign> entities, boolean cascadable) throws Exception {
		Collection<IapBranchRscAssignVo> vos = new ArrayList<IapBranchRscAssignVo>();
		
		for(IapBranchRscAssign entity : entities) {
			vos.add(IapBranchRscAssignVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}

	public static Collection<IapBranchRscAssign> getInstancesForIapCopy(Collection<IapBranchRscAssign> entities, boolean cascadable) throws Exception {
		Collection<IapBranchRscAssign> copies = new ArrayList<IapBranchRscAssign>();
		
		for(IapBranchRscAssign entity : entities) {
			IapBranchRscAssign copy = new IapBranchRscAssignImpl();
				
				copy.setIsBlankLine(entity.getIsBlankLine());
				
				
				//copy.setIapBranchId(entity.getIapBranchId());
				copy.setResourceName(entity.getResourceName());
				copy.setResourceId(entity.getResourceId());
				copy.setLeaderName(entity.getLeaderName());
				copy.setNbrOfPersonnel(entity.getNbrOfPersonnel());
				
				copy.setTransportation(entity.getTransportation());
				copy.setDropOffPoint(entity.getDropOffPoint());
				copy.setDropOffTime(entity.getDropOffTime());
				copy.setPickUpPoint(entity.getPickUpPoint());
				copy.setPickUpTime(entity.getPickUpTime());
				copy.setContactInfo(entity.getContactInfo());
				copy.setAdditionalInfo(entity.getAdditionalInfo());
				copy.setLastDayToWorkDate(entity.getLastDayToWorkDate());
				copy.setPositionNum(entity.getPositionNum());
				
			copies.add(copy);
		}
		
		return copies;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static IapBranchRscAssign toEntity(IapBranchRscAssign entity, IapBranchRscAssignVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapBranchRscAssignImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			// Set blank line property first as it is used ahead
			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			
			entity.setResourceName(StringUtility.toUpper(vo.getResourceName()));
			entity.setResourceId(vo.getResourceId());
			entity.setLeaderName(StringUtility.toUpper(vo.getLeaderName()));
			if(StringUtility.hasValue(vo.getNbrOfPersonnelString())){
				entity.setNbrOfPersonnel(Integer.valueOf(vo.getNbrOfPersonnelString()));
			}
//			NOTE: #4473 - Removing the default setting of nbr personnel to 0
//			else {
//				if(!vo.getIsBlankLine()){
//					entity.setNbrOfPersonnel(Integer.valueOf(0));
//				} // Else - If this is empty and a blank line, let nbr personnel be null
//			}
			entity.setTransportation(StringBooleanEnum.toEnumValue(vo.getTransportation()));
			entity.setDropOffPoint(vo.getDropOffPoint());
			entity.setDropOffTime(vo.getDropOffTime());
			entity.setPickUpPoint(vo.getPickUpPoint());
			entity.setPickUpTime(vo.getPickUpTime());
			entity.setContactInfo(StringUtility.toUpper(vo.getContactInfo()));
			entity.setAdditionalInfo(StringUtility.toUpper(vo.getAdditionalInfo()));
						
			if(DateTransferVo.hasDateString(vo.getLastDayToWorkDateVo())){
				// 5/26/2017 force 0000 timestamp on lastworkdaytodate field
				vo.getLastDayToWorkDateVo().setTimeString("0000");
				Date dt=DateTransferVo.getTransferDate(vo.getLastDayToWorkDateVo());
				entity.setLastDayToWorkDate(dt);
			}else{
				entity.setLastDayToWorkDate(null);
			}
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			IapBranch branchEntity = (IapBranch)(AbstractVo.getPersistableObject(persistables, IapBranchImpl.class));
			if(null != branchEntity){
				entity.setIapBranch(branchEntity);
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
	public static Collection<IapBranchRscAssign> toEntityList(Collection<IapBranchRscAssignVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapBranchRscAssign> entities = new ArrayList<IapBranchRscAssign>();
		
		for(IapBranchRscAssignVo vo : vos) {
			entities.add(IapBranchRscAssignVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}


	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}


	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}


	/**
	 * @param nbrOfPersonnel the nbrOfPersonnel to set
	 */
	public void setNbrOfPersonnel(Integer nbrOfPersonnel) {
		this.nbrOfPersonnel = nbrOfPersonnel;
	}


	/**
	 * @return the nbrOfPersonnel
	 */
	public Integer getNbrOfPersonnel() {
		return nbrOfPersonnel;
	}


	/**
	 * @param transportation the transportation to set
	 */
	public void setTransportation(Boolean transportation) {
		this.transportation = transportation;
	}


	/**
	 * @return the transportation
	 */
	public Boolean getTransportation() {
		return transportation;
	}


	/**
	 * @param dropOffPoint the dropOffPoint to set
	 */
	public void setDropOffPoint(String dropOffPoint) {
		this.dropOffPoint = dropOffPoint;
	}


	/**
	 * @return the dropOffPoint
	 */
	public String getDropOffPoint() {
		return dropOffPoint;
	}


	/**
	 * @param dropOffTime the dropOffTime to set
	 */
	public void setDropOffTime(String dropOffTime) {
		this.dropOffTime = dropOffTime;
	}


	/**
	 * @return the dropOffTime
	 */
	public String getDropOffTime() {
		return dropOffTime;
	}


	/**
	 * @param pickUpPoint the pickUpPoint to set
	 */
	public void setPickUpPoint(String pickUpPoint) {
		this.pickUpPoint = pickUpPoint;
	}


	/**
	 * @return the pickUpPoint
	 */
	public String getPickUpPoint() {
		return pickUpPoint;
	}


	/**
	 * @param pickUpTime the pickUpTime to set
	 */
	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}


	/**
	 * @return the pickUpTime
	 */
	public String getPickUpTime() {
		return pickUpTime;
	}


	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}


	/**
	 * @return the contactInfo
	 */
	public String getContactInfo() {
		return contactInfo;
	}


	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}


	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * @return the iapBranchId
	 */
	public Long getIapBranchId() {
		return iapBranchId;
	}

	/**
	 * @param iapBranchId the iapBranchId to set
	 */
	public void setIapBranchId(Long iapBranchId) {
		this.iapBranchId = iapBranchId;
	}

	/**
	 * @return the nbrOfPersonnelString
	 */
	public String getNbrOfPersonnelString() {
		return nbrOfPersonnelString;
	}

	/**
	 * @param nbrOfPersonnelString the nbrOfPersonnelString to set
	 */
	public void setNbrOfPersonnelString(String nbrOfPersonnelString) {
		this.nbrOfPersonnelString = nbrOfPersonnelString;
	}
	
	/**
	 * @return the lastDayToWorkDateVo
	 */
	public DateTransferVo getLastDayToWorkDateVo() {
		return lastDayToWorkDateVo;
	}

	/**
	 * @param lastDayToWorkDateVo the lastDayToWorkDateVo to set
	 */
	public void setLastDayToWorkDateVo(DateTransferVo lastDayToWorkDateVo) {
		this.lastDayToWorkDateVo = lastDayToWorkDateVo;
	}
	
	/**
	 * @return the isBlankLine
	 */
	public Boolean getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(Boolean isBlankLine) {
		this.isBlankLine = isBlankLine;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the trainee
	 */
	public Boolean getTrainee() {
		return trainee;
	}

	/**
	 * @param trainee the trainee to set
	 */
	public void setTrainee(Boolean trainee) {
		this.trainee = trainee;
	}

	/**
	 * @return the leaderLastName
	 */
	public String getLeaderLastName() {
		return leaderLastName;
	}

	/**
	 * @param leaderLastName the leaderLastName to set
	 */
	public void setLeaderLastName(String leaderLastName) {
		this.leaderLastName = leaderLastName;
	}

	/**
	 * @return the leaderFirstName
	 */
	public String getLeaderFirstName() {
		return leaderFirstName;
	}

	/**
	 * @param leaderFirstName the leaderFirstName to set
	 */
	public void setLeaderFirstName(String leaderFirstName) {
		this.leaderFirstName = leaderFirstName;
	}

	/**
	 * @return the leaderIsTrainee
	 */
	public Boolean getLeaderIsTrainee() {
		return leaderIsTrainee;
	}

	/**
	 * @param leaderIsTrainee the leaderIsTrainee to set
	 */
	public void setLeaderIsTrainee(Boolean leaderIsTraineee) {
		this.leaderIsTrainee = leaderIsTraineee;
	}
}
