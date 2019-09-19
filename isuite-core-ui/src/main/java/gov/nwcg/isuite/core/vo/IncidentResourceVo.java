package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class IncidentResourceVo extends AbstractVo implements PersistableVo {
	private IncidentVo incidentVo=new IncidentVo();
	private ResourceVo resourceVo=new ResourceVo();
	private Long resNumId=0L;
	private String nameAtIncident;
	private String dailyCostException;
	private WorkPeriodVo workPeriodVo=new WorkPeriodVo();
	private Date incStartDate;
	private IncidentPrefsOtherFieldsVo incidentPrefsOtherFieldsVo;
	private Collection<IncidentAccountCodeVo> incidentAccountCodeVos = new ArrayList<IncidentAccountCodeVo>();
	private CostDataVo costDataVo;

	private Boolean hasTimeVo=false;
	
	// the default for the incident
	private IncidentAccountCodeVo defaultIncidentAccountCodeVo;
	
	public IncidentResourceVo(){
		
	}
	
	public static Collection<IncidentResourceVo> getInstances(Collection<IncidentResource> entities, Boolean cascadable) throws Exception {
		Collection<IncidentResourceVo> irvos = new ArrayList<IncidentResourceVo>(); 
		for(IncidentResource ir : entities) {
			irvos.add(getInstance(ir, cascadable));
		}
		return irvos;
	}
	
	public static IncidentResourceVo getInstance(IncidentResource entity, Boolean cascadable) throws Exception {
		IncidentResourceVo vo = new IncidentResourceVo();
		
		if(null==entity)
			throw new Exception("Unable to create incident resource vo from null entity");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setNameAtIncident(StringUtility.toUpper(entity.getNameAtIncident()));
			vo.setResNumId(entity.getResNumId());
			
			if(null != entity.getResource()){
				vo.setResourceVo(ResourceVo.getInstance(entity.getResource(), true));
			}
			
			if(null != entity.getWorkPeriod()){
				vo.setWorkPeriodVo(WorkPeriodVo.getInstance(entity.getWorkPeriod(), true));
				if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo()
						&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()){
					vo.setHasTimeVo(true);	
				}
			}
			
			vo.setIncidentPrefsOtherFieldsVo(new IncidentPrefsOtherFieldsVo());
			vo.getIncidentPrefsOtherFieldsVo().setOther1Label("OTHER 1");
			vo.getIncidentPrefsOtherFieldsVo().setOther2Label("OTHER 2");
			vo.getIncidentPrefsOtherFieldsVo().setOther3Label("OTHER 3");
			
			if(null != entity.getIncident()){
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), true));
				vo.getIncidentVo().setIncidentPrefsOtherFieldsVo(new IncidentPrefsOtherFieldsVo());
				vo.setIncStartDate(entity.getIncident().getIncidentStartDate());
				
				if(null != entity.getIncident().getIncidentPrefsOtherFields()){
					vo.getIncidentVo().getIncidentPrefsOtherFieldsVo().setOther1Label( (StringUtility.hasValue(entity.getIncident().getIncidentPrefsOtherFields().getOther1Label()) ? entity.getIncident().getIncidentPrefsOtherFields().getOther1Label().toUpperCase() : "OTHER 1"));
					vo.getIncidentVo().getIncidentPrefsOtherFieldsVo().setOther2Label( (StringUtility.hasValue(entity.getIncident().getIncidentPrefsOtherFields().getOther2Label()) ? entity.getIncident().getIncidentPrefsOtherFields().getOther2Label().toUpperCase() : "OTHER 2"));
					vo.getIncidentVo().getIncidentPrefsOtherFieldsVo().setOther3Label( (StringUtility.hasValue(entity.getIncident().getIncidentPrefsOtherFields().getOther3Label()) ? entity.getIncident().getIncidentPrefsOtherFields().getOther3Label().toUpperCase() : "OTHER 3"));
					vo.getIncidentPrefsOtherFieldsVo().setOther1Label( (StringUtility.hasValue(entity.getIncident().getIncidentPrefsOtherFields().getOther1Label()) ? entity.getIncident().getIncidentPrefsOtherFields().getOther1Label().toUpperCase() : "OTHER 1"));
					vo.getIncidentPrefsOtherFieldsVo().setOther2Label( (StringUtility.hasValue(entity.getIncident().getIncidentPrefsOtherFields().getOther2Label()) ? entity.getIncident().getIncidentPrefsOtherFields().getOther2Label().toUpperCase() : "OTHER 2"));
					vo.getIncidentPrefsOtherFieldsVo().setOther3Label( (StringUtility.hasValue(entity.getIncident().getIncidentPrefsOtherFields().getOther3Label()) ? entity.getIncident().getIncidentPrefsOtherFields().getOther3Label().toUpperCase() : "OTHER 3"));
				}else{
					vo.getIncidentVo().getIncidentPrefsOtherFieldsVo().setOther1Label("OTHER 1");
					vo.getIncidentVo().getIncidentPrefsOtherFieldsVo().setOther2Label("OTHER 2");
					vo.getIncidentVo().getIncidentPrefsOtherFieldsVo().setOther3Label("OTHER 3");
				}
			}
			
			// set the default incident Account Code if available
			if(null != entity.getIncident().getIncidentAccountCodes()){
				for(IncidentAccountCode iac : entity.getIncident().getIncidentAccountCodes()){

					vo.getIncidentAccountCodeVos().add(IncidentAccountCodeVo.getInstance(iac, true));

					if(iac.getDefaultFlag()){
						vo.setDefaultIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(iac, true));
					}
					
					if(null == vo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo()){
						if(iac.getDefaultFlag()){
							vo.getWorkPeriodVo().setWpDefaultIncidentAccountCodeVo(IncidentAccountCodeVo.getInstance(iac, true));
						}
					}
				}
			}
			
			if(null != entity.getCostData()){
				vo.setCostDataVo(CostDataVo.getInstance(entity.getCostData(), true));
			}
			else {
				//This step is to ensure that cost data will be saved for resource records that were created prior to cost being implemented in the system 
				CostData cdEntity=null;
				cdEntity = new CostDataImpl();
				cdEntity.setIncidentResource(entity);
				cdEntity.setGenerateCosts(true);
				vo.setCostDataVo(CostDataVo.getInstance(cdEntity, true));
			}
		}
		
		return vo;
	}

	public static Resource toResourceEntity(Resource entity, IncidentResourceVo vo,Persistable... persistables) throws Exception {

		if(null == entity){
			entity = new ResourceImpl();
			entity.setEnabled(true);
		}
		
		/*
		 * Start by populating the resource entity.
		 * If relational data if available, the populateResourceEntity method
		 * will populate the incidentResource.
		 */
		populateResourceEntity(entity,vo,persistables);
		
		return entity;
	}

	private static void populateResourceEntity(Resource entity,IncidentResourceVo vo,Persistable... persistables) throws Exception {
		
		//QC Issue 1148
		//else clauses added to support changing a person resource to non-person and vice versa
		if(null != vo.getResourceVo().getFirstName() && vo.getResourceVo().getFirstName().length()>0) {
			entity.setFirstName(vo.getResourceVo().getFirstName().toUpperCase());			
		}
		else {
			entity.setFirstName(null);
		}
		
		if(null != vo.getResourceVo().getLastName() && vo.getResourceVo().getLastName().length()>0) {
			entity.setLastName(vo.getResourceVo().getLastName().toUpperCase());
		} else {
			entity.setLastName(null);
		}
			
		if(null != vo.getResourceVo().getResourceName() && vo.getResourceVo().getResourceName().length()>0) {
			entity.setResourceName(vo.getResourceVo().getResourceName().toUpperCase());
		} else {
			entity.setResourceName(null);
		}
		//end changes for QC Issue 1148
			
		entity.setPhone(StringUtility.removeNonNumeric(vo.getResourceVo().getPhone()));
		entity.setPerson(BooleanUtility.getValue(vo.getResourceVo().getPerson()));
		entity.setPermanent(BooleanUtility.getValue(vo.getResourceVo().getPermanent()));
		
		if (null != persistables && persistables.length > 0 && null != persistables[0]) {
			Resource permanentResource = (Resource)getPersistableObject(persistables,ResourceImpl.class);
			entity.setPermanentResource(permanentResource);
		}
		
		entity.setContracted(BooleanUtility.getValue(vo.getResourceVo().getContracted()));
		entity.setEnabled(BooleanUtility.getValue(vo.getResourceVo().getEnabled()));
		entity.setLeader(BooleanUtility.getValue(vo.getResourceVo().getLeader()));
		entity.setComponent(BooleanUtility.getValue(vo.getResourceVo().getComponent()));
		
    	if(null == vo.getResourceVo().getResourceClassification() || vo.getResourceVo().getResourceClassification().name().isEmpty()){
        	entity.setResourceClassification(null);
    	}else{
        	entity.setResourceClassification(vo.getResourceVo().getResourceClassification());
    	}
		entity.setOther1((null == vo.getResourceVo().getOther1()) ? "" : vo.getResourceVo().getOther1().toUpperCase());
		entity.setOther2((null == vo.getResourceVo().getOther2()) ? "" : vo.getResourceVo().getOther2().toUpperCase());
		entity.setOther3((null == vo.getResourceVo().getOther3()) ? "" : vo.getResourceVo().getOther3().toUpperCase());
		entity.setNumberOfPersonnel((null != vo.getResourceVo().getNumberOfPersonnel() ? vo.getResourceVo().getNumberOfPersonnel() : 0L));
		if(null != vo.getResourceVo().getResourceClassificationVo()){
			Collection<ResourceClassificationVo> rcList = ResourceClassificationEnum.getResourceClassificationVoList();
			
			for(ResourceClassificationVo rcvo : rcList){
				if(rcvo.getCode().equals(vo.getResourceVo().getResourceClassificationVo().getCode()) ){
					entity.setResourceClassification(ResourceClassificationEnum.valueOf(rcvo.getCode()));
				}
			}
		}

		entity.setLeaderType(vo.getResourceVo().getLeaderType());
		
		/*
    	 * Resource <--> ResourceKind (bidirectional relationship)
    	 * The relationship from resource to resourcekind
    	 * should cascade the resourcekinds (cascadable = true).
    	 * 
    	 * Pass in this entity instance as part of the referenced persistableObjects.
    	 */
		if(null != vo.getResourceVo().getResourceKindVos()){
			//if(null==entity.getResourceKinds())
			//	entity.setResourceKinds(new ArrayList<ResourceKind>());
			
			Collection<ResourceKind> removeList = 
				ResourceKindVo.toEntityRemoveList(vo.getResourceVo().getResourceKindVos(),entity.getResourceKinds());

			Collection<ResourceKind> addList = 
				ResourceKindVo.toEntityAddList(vo.getResourceVo().getResourceKindVos(),entity.getResourceKinds(),entity);
			
			Collection<ResourceKind> updateList = 
				ResourceKindVo.toEntityUpdateList(vo.getResourceVo().getResourceKindVos(),entity.getResourceKinds(),entity);

			if(CollectionUtility.hasValue(removeList)){
				entity.getResourceKinds().removeAll(removeList);
			}
			
			if(CollectionUtility.hasValue(addList))
				entity.getResourceKinds().addAll(addList);
			
			if(CollectionUtility.hasValue(updateList)){
				Collection<ResourceKind> newList = new ArrayList<ResourceKind>();
				for(ResourceKind rk : entity.getResourceKinds()){
					boolean bfound=false;
					
					for(ResourceKind rk2 : updateList){
						if(LongUtility.hasValue(rk.getId()) && LongUtility.hasValue(rk2.getId())){
							if(rk.getId().compareTo(rk2.getId())==0){
								rk.setTraining(rk2.getTraining());
								newList.add(rk);
								bfound=true;
								break;
							}
						}
					}
					
					if(!bfound)
						newList.add(rk);
				}
				
				entity.getResourceKinds().clear();
				entity.getResourceKinds().addAll(newList);
			}
		}
		/*
    	entity.getResourceKinds().clear();
    	if( null != vo.getResourceVo().getResourceKindVos() ){
    		Collection<ResourceKind> rks = ResourceKindVo.toEntityList(vo.getResourceVo().getResourceKindVos(), true, entity);
    		for(ResourceKind rk : rks){
    			entity.addResourceKind(rk);
    		}
    	}
		*/
		if(null != vo.getResourceVo().getNameOnPictureId() && vo.getResourceVo().getNameOnPictureId().length()>0)
			entity.setNameOnPictureId(vo.getResourceVo().getNameOnPictureId().toUpperCase());
			
		// Set agency if exists
		if( (null != vo.getResourceVo().getAgencyVo()) && (vo.getResourceVo().getAgencyVo().getId()>0L) ){
			entity.setAgency(AgencyVo.toEntity(null, vo.getResourceVo().getAgencyVo(), false));
		}else{
			entity.setAgency(null);
		}

		// Set organization if exists
		if( (null != vo.getResourceVo().getOrganizationVo()) && (vo.getResourceVo().getOrganizationVo().getId()>0L) ){
			entity.setOrganization(OrganizationVo.toEntity(null,vo.getResourceVo().getOrganizationVo(),false));
		}
		
		// Set primary dispatch center (organization) if exists
		if((null != vo.getResourceVo().getPrimaryDispatchCenterVo() && (vo.getResourceVo().getPrimaryDispatchCenterVo().getId()>0L))) {
			entity.setPrimaryDispatchCenter(OrganizationVo.toEntity(null, vo.getResourceVo().getPrimaryDispatchCenterVo(), false));
		}
		
		if(AbstractVo.hasValue(vo.getResourceVo().getParentResourceVo())){
			entity.setParentResource(ResourceVo.toEntity(null, vo.getResourceVo().getParentResourceVo(), false));
		}
		
		if( (null == entity.getIncidentResources()) || (entity.getIncidentResources().size()<1) ){
			// create new incidentResource instance
			IncidentResource incidentResource = new IncidentResourceImpl();
			
			// create new incident instance
			Incident incident = new IncidentImpl();
			if(null != vo.getIncidentVo()){
				incident.setId(vo.getIncidentVo().getId());
			}else{
				throw new Exception("[IncidentResourceVo] Unable to create entity with null incident.");
			}
			
			// associate incident with incidentResource
			incidentResource.setIncident(incident);
				
			// associate incidentResource with resource
			incidentResource.setResource(entity);
				
			// create new work period
			WorkPeriod workPeriod = new WorkPeriodImpl();
				
			// associate incidentResource with workPeriod
			workPeriod.setIncidentResource(incidentResource);
				
			// create new assignment
			Assignment assignment = new AssignmentImpl();
				
			workPeriod.setAssignments(new ArrayList<Assignment>());
			//workPeriod.getAssignments().add(assignment);
				
			// associate workPeriod with incidentResource
			incidentResource.setWorkPeriod(workPeriod);
				
			// add incidentResource to resource
			entity.getIncidentResources().add(incidentResource);
				
		}	
			
		populateIncidentResourceEntity(((List<IncidentResource>)entity.getIncidentResources()).get(0),vo,entity);
		
		populateCostDataEntity(((List<IncidentResource>)entity.getIncidentResources()).get(0),vo);
	}
	
	private static void populateCostDataEntity(IncidentResource entity,IncidentResourceVo vo, Persistable... persistables) throws Exception {
		if(null==entity.getCostData()){
			entity.setCostData(new CostDataImpl());
			entity.getCostData().setGenerateCosts(true);
			entity.getCostData().setIncidentResource(entity);
		}

		Date assignDate=null;
		Date checkInDate=entity.getWorkPeriod().getCICheckInDate();
		Date hireDate=null;
		
		if(null != vo.getCostDataVo()){
			// use assign date,checkindate,hireddate,earliest time posting date whichever is first
			if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
				assignDate=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
			}else
				assignDate=entity.getWorkPeriod().getCICheckInDate();
			/*
			assignDate=(DateUtil.hasValue(vo.getCostDataVo().getAssignDate()) 
					? vo.getCostDataVo().getAssignDate()
							: entity.getWorkPeriod().getCICheckInDate());
			*/
		}
		
		if(null != vo.getWorkPeriodVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo()
				&& vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.CONTRACTOR) {
			
			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDateVo())){
				hireDate=DateTransferVo.getTransferDate(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDateVo());
			}
			//hireDate=vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDate();
		}

		// override as needed
		Date firstDate=null;
		if(DateUtil.hasValue(checkInDate))
			firstDate=checkInDate;
			
		if(DateUtil.hasValue(checkInDate) && DateUtil.hasValue(hireDate)){
			checkInDate=DateUtil.addMilitaryTimeToDate(checkInDate,"2359");
			hireDate=DateUtil.addMilitaryTimeToDate(hireDate,"2359");
			if(hireDate.before(checkInDate))
				firstDate=hireDate;
		}else if(DateUtil.hasValue(hireDate))
			firstDate=hireDate;
			
		if(DateUtil.hasValue(firstDate) && DateUtil.hasValue(assignDate)){
			assignDate=DateUtil.addMilitaryTimeToDate(assignDate,"2359");
			if(DateUtil.hasValue(checkInDate)){
				checkInDate=DateUtil.addMilitaryTimeToDate(checkInDate,"2359");
				if(assignDate.before(firstDate))
					firstDate=assignDate;
			}else
				firstDate=assignDate;
		}else if(!DateUtil.hasValue(firstDate) && DateUtil.hasValue(assignDate)){
			firstDate=assignDate;
		}
		entity.getCostData().setAssignDate(firstDate);
		
		if(null != vo.getCostDataVo()){
			if(null != vo.getCostDataVo().getDefaultCostGroupVo()){
				entity.getCostData().setDefaultCostGroup(CostGroupVo.toEntity(null, vo.getCostDataVo().getDefaultCostGroupVo(), false));		
			}
			
			if(null != vo.getCostDataVo().getDefaultIncidentShiftVo()){
				entity.getCostData().setDefaultIncidentShift(IncidentShiftVo.toEntity(entity.getCostData().getDefaultIncidentShift(),vo.getCostDataVo().getDefaultIncidentShiftVo(), true));		
			}
			
			entity.getCostData().setCostRemarks(vo.getCostDataVo().getCostRemarks());
		}

		if(null != vo.getCostDataVo() && null != vo.getCostDataVo().getAccrualCodeVo())
			entity.getCostData().setAccrualCode(AccrualCodeVo.toEntity(vo.getCostDataVo().getAccrualCodeVo(),false));
	}
	
	private static void populateIncidentResourceEntity(IncidentResource entity,IncidentResourceVo vo, Persistable... persistables) throws Exception {
		entity.setNameAtIncident("");

		CostData cdEntity=null;
		if(null == vo.getCostDataVo()){
			cdEntity = new CostDataImpl();
			cdEntity.setIncidentResource(entity);
			cdEntity.setGenerateCosts(true);
		}else{
			cdEntity=CostDataVo.toEntity(vo.getCostDataVo(), true,entity);
			if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getDailyFormVo().getCode().equalsIgnoreCase("A")){
				cdEntity.setUseAccrualsOnly(false);
			}
		}
		entity.setCostData(cdEntity);
		
		entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));

		if(LongUtility.hasValue(vo.getResNumId()))
			entity.setResNumId(vo.getResNumId());
		else
			entity.setResNumId(0L);
		
		if(null==entity.getWorkPeriod()){
			WorkPeriod wpEntity = new WorkPeriodImpl();
			wpEntity.setIncidentResource(entity);
			entity.setWorkPeriod(wpEntity);
		}
		populateWorkPeriodEntity(entity.getWorkPeriod(),vo,persistables);
	}

	private static void populateWorkPeriodEntity(WorkPeriod entity,IncidentResourceVo vo,Persistable... persistables) throws Exception {
		
		if(null != vo.getWorkPeriodVo()){
			entity = WorkPeriodVo.toEntity(entity, vo.getWorkPeriodVo(),true, persistables);
		}
	}
	
	public static EmploymentTypeEnum extractEmploymentType(IncidentResourceVo irvo) throws Exception {
		EmploymentTypeEnum ete = null;
		if(irvo.getWorkPeriodVo().getCurrentAssignmentVo() != null && irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo() != null) {
			ete = irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType();
		}
		return ete;
	}
	
	public static KindVo extractItemCode(IncidentResourceVo irvo) throws Exception {
		if(null != irvo.getWorkPeriodVo().getCurrentAssignmentVo()
					&&
		   null != irvo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo()){
			return irvo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo();
		}
		return null;
	}

	/**
	 * Returns the incidentVo.
	 *
	 * @return 
	 *		the incidentVo to return
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	/**
	 * Sets the incidentVo.
	 *
	 * @param incidentVo 
	 *			the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * Returns the resourceVo.
	 *
	 * @return 
	 *		the resourceVo to return
	 */
	public ResourceVo getResourceVo() {
		return resourceVo;
	}

	/**
	 * Sets the resourceVo.
	 *
	 * @param resourceVo 
	 *			the resourceVo to set
	 */
	public void setResourceVo(ResourceVo resourceVo) {
		this.resourceVo = resourceVo;
	}

	/**
	 * Returns the nameAtIncident.
	 *
	 * @return 
	 *		the nameAtIncident to return
	 */
	public String getNameAtIncident() {
		return nameAtIncident;
	}

	/**
	 * Sets the nameAtIncident.
	 *
	 * @param nameAtIncident 
	 *			the nameAtIncident to set
	 */
	public void setNameAtIncident(String nameAtIncident) {
		this.nameAtIncident = nameAtIncident;	
	}

	/**
	 * Returns the workPeriodVo.
	 *
	 * @return 
	 *		the workPeriodVo to return
	 */
	public WorkPeriodVo getWorkPeriodVo() {
		return workPeriodVo;
	}

	/**
	 * Sets the workPeriodVo.
	 *
	 * @param workPeriodVo 
	 *			the workPeriodVo to set
	 */
	public void setWorkPeriodVo(WorkPeriodVo workPeriodVo) {
		this.workPeriodVo = workPeriodVo;
	}

	/**
	 * @return the incidentStartDate
	 */
	public Date getIncStartDate() {
		return incStartDate;
	}

	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncStartDate(Date incidentStartDate) {
		this.incStartDate = incidentStartDate;
	}

	/**
	 * @return the incidentPrefsOtherFieldsVo
	 */
	public IncidentPrefsOtherFieldsVo getIncidentPrefsOtherFieldsVo() {
		return incidentPrefsOtherFieldsVo;
	}

	/**
	 * @param incidentPrefsOtherFieldsVo the incidentPrefsOtherFieldsVo to set
	 */
	public void setIncidentPrefsOtherFieldsVo(
			IncidentPrefsOtherFieldsVo incidentPrefsOtherFieldsVo) {
		this.incidentPrefsOtherFieldsVo = incidentPrefsOtherFieldsVo;
	}

	/**
	 * @return the defaultIncidentAccountCodeVo
	 */
	public IncidentAccountCodeVo getDefaultIncidentAccountCodeVo() {
		return defaultIncidentAccountCodeVo;
	}

	/**
	 * @param defaultIncidentAccountCodeVo the defaultIncidentAccountCodeVo to set
	 */
	public void setDefaultIncidentAccountCodeVo(
			IncidentAccountCodeVo defaultIncidentAccountCodeVo) {
		this.defaultIncidentAccountCodeVo = defaultIncidentAccountCodeVo;
	}

	public Collection<IncidentAccountCodeVo> getIncidentAccountCodeVos() {
		return incidentAccountCodeVos;
	}

	public void setIncidentAccountCodeVos(
			Collection<IncidentAccountCodeVo> incidentAccountCodeVos) {
		this.incidentAccountCodeVos = incidentAccountCodeVos;
	}

	/**
	 * @return the costDataVo
	 */
	public CostDataVo getCostDataVo() {
		return costDataVo;
	}

	/**
	 * @param costDataVo the costDataVo to set
	 */
	public void setCostDataVo(CostDataVo costDataVo) {
		this.costDataVo = costDataVo;
	}

	public Boolean getHasTimeVo() {
		return hasTimeVo;
	}

	public void setHasTimeVo(Boolean hasTimeVo) {
		this.hasTimeVo = hasTimeVo;
	}

	/**
	 * @return the dailyCostException
	 */
	public String getDailyCostException() {
		return dailyCostException;
	}

	/**
	 * @param dailyCostException the dailyCostException to set
	 */
	public void setDailyCostException(String dailyCostException) {
		this.dailyCostException = dailyCostException;
	}

	/**
	 * @return the resNumId
	 */
	public Long getResNumId() {
		if(LongUtility.hasValue(resNumId))
			return resNumId;
		else
			return 0L;
	}

	/**
	 * @param resNumId the resNumId to set
	 */
	public void setResNumId(Long resNumId) {
		this.resNumId = resNumId;
	}

	
}
