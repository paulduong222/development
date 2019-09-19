package gov.nwcg.isuite.core.rules.rossimportend2;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceMobilizationImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.KindAltDescDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.KindAltDescVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CreateResourcesRules extends AbstractRossImportEndRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessEndRuleFactory2.RuleEnum.CREATE_RESOURCES.name();
	
	public CreateResourcesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		Long incidentId = (Long)dialogueVo.getResultObjectAlternate4();
		IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
		Incident incident = incDao.getById(incidentId);
		incDao.flushAndEvict(incident);
		
		super.gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
		
		/*
		 * Get all resource not matched and not excluded and load into resourcesToCreate collection.
		 */
		Collection<RossResourceVo> resourcesToCreate=new ArrayList<RossResourceVo>();
		for(RossResourceVo rossResourceVo : rossImportVo.getRossResourceVos()){
			
			if(BooleanUtility.isFalse(rossResourceVo.getExcluded()) && BooleanUtility.isFalse(rossResourceVo.getMatched())){
				resourcesToCreate.add(rossResourceVo);
			}
		}
		
		/*
		 * put the resourcesToCreate in correct hierarchy
		 */
		if(CollectionUtility.hasValue(resourcesToCreate))
			resourcesToCreate=buildRosterTree(resourcesToCreate);
		
		/*
		 * add any other straggler resources not already added (overhead group children)
		 */
		for(RossResourceVo rossResourceVo : rossImportVo.getRossResourceVos()){
			Boolean bFound=isResourceAdded(rossResourceVo,resourcesToCreate);
			
			if(BooleanUtility.isTrue(rossResourceVo.getExcluded()) || BooleanUtility.isTrue(rossResourceVo.getMatched())){
				// do not add excluded or matched resources
				bFound=true;
			}
			
			if(!bFound)
				resourcesToCreate.add(rossResourceVo);
		}
		
		/*
		 * Build resource entities
		 */
		if(CollectionUtility.hasValue(resourcesToCreate)){
			RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");
			
			Collection<RossEISuiteResourceVo> eisuiteResources = ripDao.getEISuiteResources3(incidentId);
			
			Collection<String> updateSqls = new ArrayList<String>();
			
			for(RossResourceVo rrvo : resourcesToCreate){
				if(StringUtility.hasValue(rrvo.getRequestNumber()) 
						&& rrvo.getRequestNumber().indexOf(".")>0
						&& rrvo.getRequestNumber().indexOf("O-")<0){
					
					//System.out.println(rrvo.getRequestNumber());
					
					String parentReqNum=this.getParentRequestNumber(rrvo.getRequestNumber());
					if(StringUtility.hasValue(parentReqNum)){
						for(RossEISuiteResourceVo ervo : eisuiteResources){
							if(StringUtility.hasValue(ervo.getRequestNumber()) 
									&& ervo.getRequestNumber().equalsIgnoreCase(parentReqNum)){
								
								// parentResourceId = ervo.getResourceId();
								String sql = "UPDATE isw_resource  "+
											 "set parent_resource_id = " + ervo.getResourceId() + " " +
											 "where id = " +
											 "("+
											 "   select resource_id "+
											 "   from isw_incident_resource "+
											 "   where incident_id = " + incidentId + " " +
											 "   and ross_res_req_id = " + rrvo.getRossResReqId() + " " +
											 ") ";
								updateSqls.add(sql);
								break;
							}
						}
					}
				}
			}
			
			
			Collection<Resource> resourceEntities = createResourceEntities(null,resourcesToCreate,incident);
			
			IncidentResourceDao irdao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			ResourceDao resourceDao = (ResourceDao)context.getBean("resourceDao");

			if(CollectionUtility.hasValue(resourceEntities)){
				
				resourceDao.saveAll(resourceEntities);
				
				for(Resource r : resourceEntities){
					if(null != r.getIncidentResources() && r.getIncidentResources().size()>0){
						IncidentResource ir=r.getIncidentResources().iterator().next();
						Long irId=ir.getId();
						if(LongUtility.hasValue(irId)){
							Long resNumId=irdao.updateResNumId(irId);
						}
					}
				}
			
			}
			
			if(CollectionUtility.hasValue(updateSqls)){
				irdao.persistSqls(updateSqls);
			}
			
			incDao.createDefaultQuestionValues(incidentId);
		}
		
		
		return _OK;
	}

	private Boolean isResourceAdded(RossResourceVo vo, Collection<RossResourceVo> vos){
		Boolean bFound=false;
		
		for(RossResourceVo resVo : vos){
			if(String.valueOf(vo.getRossResReqId()).equals(String.valueOf(resVo.getRossResReqId())))
				return true;
			
			if(CollectionUtility.hasValue(resVo.getChildren())){
				bFound=isResourceAdded(vo,resVo.getChildren());
				if(bFound==true)
					return true;
			}
		}
		return bFound;
	}
	
	private Collection<RossResourceVo> buildRosterTree(Collection<RossResourceVo> sourceList){
		Collection<RossResourceVo> newList = new ArrayList<RossResourceVo>();
		
		/*
		 * for each top level resource in sourceList, build its children
		 */
		for(RossResourceVo vo : sourceList){
			String reqNum=vo.getRequestNumber();
			int firstDot=reqNum.indexOf(".");
			if(firstDot < 0){
				// its top level resource
				RossResourceVo newVo = vo;
				
				// if top level resource is overhead group, do not add children
				if(StringUtility.hasValue(vo.getRequestCatalogName()) 
						&& vo.getRequestCatalogName().equalsIgnoreCase("OVERHEAD")
						&& StringUtility.hasValue(vo.getRequestCategoryName())
						&& vo.getRequestCategoryName().equalsIgnoreCase("GROUPS")){
					// do not add children
				}else{
					//System.out.println(vo.getRequestNumber());
					vo.setChildren(buildChildren(reqNum,sourceList));
				}
				
				newList.add(newVo);
			}
		}
		
		return newList;
	}

	private Collection<RossResourceVo> buildChildren(String parentReqNum,Collection<RossResourceVo> sourceList) {
		Collection<RossResourceVo> children = new ArrayList<RossResourceVo>();
		
		for(RossResourceVo vo : sourceList){
			String reqNumSource=vo.getRequestNumber();
			if(reqNumSource.startsWith(parentReqNum+".") ){
				int firstDot=reqNumSource.indexOf(".",(parentReqNum+".").length());
				if(firstDot < 0){
					//System.out.println(vo.getRequestNumber() +"  parent["+parentReqNum+"]");
					// it is a child resource
					RossResourceVo childVo = vo;
					childVo.setChildren(buildChildren(reqNumSource,sourceList));
					children.add(childVo);
				}
			}
			
		}
		
		return children;
	}

	/**
	 * @param resourceVos
	 * @param incident
	 * @return
	 */
	private Collection<Resource> createResourceEntities(Resource parent,Collection<RossResourceVo> resourceVos,Incident incident) throws Exception{
		Collection<Resource> resourceEntities = new ArrayList<Resource>();

		for(RossResourceVo resourceVo : resourceVos){
			Resource resourceEntity = toResourceEntity(parent,resourceVo,incident);
			resourceEntities.add(resourceEntity);
		}
		
		return resourceEntities;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	private Resource toResourceEntity(Resource parent, RossResourceVo vo,Incident incident) throws Exception {
		//System.out.println(vo.getRossResReqId() + " " + vo.getRequestNumber() + " " + vo.getResourceName() + " " + vo.getLastName());
		Resource resourceEntity = new ResourceImpl();
		resourceEntity.setId(null);
		
		resourceEntity.setRossResId(vo.getRossResId());
		resourceEntity.setResourceName(StringUtility.leftTrim(vo.getResourceName(),55));
		resourceEntity.setEnabled(Boolean.TRUE);
		resourceEntity.setPerson(vo.getIsPerson());
		resourceEntity.setContracted(Boolean.FALSE);
		resourceEntity.setPermanent(Boolean.FALSE);
		resourceEntity.setLeader(Boolean.FALSE);
		resourceEntity.setNumberOfPersonnel(0L);
		
		if(BooleanUtility.isTrue(vo.getIsPerson())){
			resourceEntity.setLastName(StringUtility.leftTrim(vo.getLastName(),35).trim());
			resourceEntity.setFirstName(StringUtility.leftTrim((vo.getFirstName() + (StringUtility.hasValue(vo.getMiddleName()) ? " " + vo.getMiddleName().trim() : "") ),35).trim());
			resourceEntity.setNumberOfPersonnel(1L);
		}

		if(StringUtility.hasValue(resourceEntity.getResourceName())){
			resourceEntity.setResourceName(resourceEntity.getResourceName().toUpperCase());
		}
		if(StringUtility.hasValue(resourceEntity.getLastName())){
			resourceEntity.setLastName(resourceEntity.getLastName().toUpperCase());
		}
		if(StringUtility.hasValue(resourceEntity.getFirstName())){
			resourceEntity.setFirstName(resourceEntity.getFirstName().toUpperCase());
		}
		
		if(null != parent){
			resourceEntity.setParentResource(parent);
			resourceEntity.setRossGroupAssignment("ROSTERED");
		}else{
			resourceEntity.setRossGroupAssignment("SINGLE");
		}
		
		if(DateUtil.hasValue(vo.getMobDate())){
			ResourceMobilization rm = new ResourceMobilizationImpl();
			rm.setStartDate(vo.getMobDate());
			rm.setResource(resourceEntity);
			resourceEntity.setResourceMobilizations(new ArrayList<ResourceMobilization>());
			resourceEntity.getResourceMobilizations().add(rm);
		}

		resourceEntity.setOrganization(super.getHomeUnit(vo.getUnitCode()));
		resourceEntity.setPrimaryDispatchCenter(super.getPdc(vo.getUnitCode()));
		
		// meeting with donna 5/22/2014
		/*
		 * if ross vendor_owned_flag = 'YES', then always set agency to PVT
		 * otherwise evaluate ross agency abbrev is available,
		 * if ross agency abbrev is not available, try to derive agency based on 
		 * 2 letter state code from unit code
		 */
		if(StringUtility.hasValue(vo.getVendorOwnedFlag()) && vo.getVendorOwnedFlag().equalsIgnoreCase("YES")){
			resourceEntity.setAgency(super.getAgency("PVT", resourceEntity.getOrganization()));
		}else{
			resourceEntity.setAgency(super.getAgency(vo.getAgencyCode(), resourceEntity.getOrganization()));
		}

		IncidentResource incidentResourceEntity = this.toIncidentResource(resourceEntity, vo, incident);
		
		// add incidentResource to resource
		resourceEntity.getIncidentResources().add(incidentResourceEntity);

		// build the children
		if(CollectionUtility.hasValue(vo.getChildren())){
			Collection<Resource> childrenEntities = new ArrayList<Resource>();
			for(RossResourceVo child : vo.getChildren()){
				Resource childEntity = this.toResourceEntity(resourceEntity, child, incident);
				childrenEntities.add(childEntity);
			}
			resourceEntity.setChildResources(childrenEntities);
		}
		
		return resourceEntity;
	}
	
	public IncidentResource toIncidentResource(Resource resourceEntity, RossResourceVo vo, Incident incident) throws Exception {
		// create new incidentResource instance
		IncidentResource incidentResource = new IncidentResourceImpl();

		// associate incident with incidentResource
		Incident inc = new IncidentImpl();
		inc.setId(incident.getId());
		incidentResource.setIncident(inc);
		
		// associate incidentResource with resource
		incidentResource.setResource(resourceEntity);

		incidentResource.setRossResReqId(vo.getRossResReqId());
		
		incidentResource.setNameAtIncident("");
		
		// associate workPeriod with incidentResource
		incidentResource.setWorkPeriod(toWorkPeriod(incidentResource,vo));
		
		// associate costdata with incidentResource
		CostData cdEntity = new CostDataImpl();
		cdEntity.setIncidentResource(incidentResource);
		if(resourceEntity.getParentResource()==null)
			cdEntity.setGenerateCosts(true);
		else
			cdEntity.setGenerateCosts(false);
		
		// set default accrual code to EXCL
		cdEntity.setAccrualCode(super.getDefaultAccrualCode());
		cdEntity.setAccrualLocked(false);
		
		incidentResource.setCostData(cdEntity);
		
		return incidentResource;
	}

	private WorkPeriod toWorkPeriod(IncidentResource irEntity, RossResourceVo vo) throws Exception {
		WorkPeriod wpEntity = new WorkPeriodImpl();

		// associate incidentResource with workPeriod
		wpEntity.setIncidentResource(irEntity);

		wpEntity.setDMReAssignable(Boolean.FALSE);
		wpEntity.setDMCheckoutFormPrinted(Boolean.FALSE);
		wpEntity.setDMPlanningDispatchNotified(Boolean.FALSE);
		wpEntity.setDMReleaseDispatchNotified(Boolean.FALSE);
		wpEntity.setDMRestOvernight(Boolean.FALSE);
		
		String jetPortCode=super.getJetPortCode(vo.getJetPort());
		if(StringUtility.hasValue(jetPortCode)){
			JetPort jp = super.getJetPort(jetPortCode);
			if(null != jp){
				wpEntity.setCIArrivalJetPort(jp);
			}
		}
		
		// create new assignment
		Assignment assignment = new AssignmentImpl();
			
		wpEntity.setAssignments(new ArrayList<Assignment>());
		wpEntity.getAssignments().add(toAssignment(wpEntity,vo));

		if(CollectionUtility.hasValue(irEntity.getResource().getResourceMobilizations()) &&
				irEntity.getResource().getResourceMobilizations().size() > 0){
			ResourceMobilization rm = irEntity.getResource().getResourceMobilizations().iterator().next();
			wpEntity.setCIResourceMobilization(rm);
		}
		
		return wpEntity;
	}

	/**
	 * @param wpEntity
	 * @param vo
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private Assignment toAssignment(WorkPeriod wpEntity, RossResourceVo vo) throws Exception {
		Assignment assignmentEntity = new AssignmentImpl();

		assignmentEntity.setWorkPeriods(new ArrayList<WorkPeriod>());
		assignmentEntity.getWorkPeriods().add(wpEntity);
		assignmentEntity.setTraining(Boolean.FALSE);
		assignmentEntity.setRequestNumber(vo.getRequestNumber());
		assignmentEntity.setAssignmentStatus(AssignmentStatusTypeEnum.F);
		
		KindAltDescDao kindAltDescDao = (KindAltDescDao)context.getBean("kindAltDescDao");
		Collection<KindAltDescVo> kindAltDescVos = kindAltDescDao.getGrid();
		
		assignmentEntity.setKind(super.getKind(vo.getItemCode(),vo.getItemName(), kindAltDescVos));
		
		assignmentEntity.setAssignmentTime(toAssignmentTime(assignmentEntity,vo));
		
		return assignmentEntity;
	}

	
	private AssignmentTime toAssignmentTime(Assignment aEntity, RossResourceVo vo) throws Exception {
		AssignmentTime assignmentTimeEntity = new AssignmentTimeImpl();

		assignmentTimeEntity.setAssignment(aEntity);

		return assignmentTimeEntity;
	}

	private String getParentRequestNumber(String rq){
		String parentRq="";
		
		if(StringUtility.hasValue(rq)){
			int dotIdx=rq.lastIndexOf(".");
			parentRq=rq.substring(0, dotIdx);
		}
		
		return parentRq;
	}
	
	public static void main(String[] args){
		//System.out.println(CreateResourcesRules.getParentRequestNumber("A-17.3"));
	}
}
