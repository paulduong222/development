package gov.nwcg.isuite.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nwcg.isuite.core.cost.utilities.DefaultAccrualCodeHandler;
import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AddressImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.AssignmentTimeDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.persistence.WorkPeriodDao;
import gov.nwcg.isuite.core.rules.IncidentResourceDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentResourceSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.service.IncidentResource2Service;
import gov.nwcg.isuite.core.vo.AdPaymentInfoVo;
import gov.nwcg.isuite.core.vo.AddressVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.CostDataVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.WorkPeriodOvernightStayInfoVo;
import gov.nwcg.isuite.core.vo.WorkPeriodQuestionValueVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.RequestNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

public class IncidentResource2ServiceImpl extends BaseService implements IncidentResource2Service {
	protected IncidentResourceDao incidentResourceDao;
	protected ResourceDao resourceDao;
	
	@Autowired
	private KindDao kindDao;

	public IncidentResource2ServiceImpl(){
		super();
	}

	public void initialization(){
		incidentResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		resourceDao = (ResourceDao)context.getBean("resourceDao");
	}

	/**
	 * @param incidentResourceFilter
	 * @param sortFields
	 * @param dialogVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo getGrid(IncidentResourceFilter incidentResourceFilter, Collection<String> sortFields,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			
			Collection<IncidentResourceGridVo> vos = new ArrayList<IncidentResourceGridVo>();
			Collection<IncidentResourceGridVo> rtnVos=new ArrayList<IncidentResourceGridVo>();
			
			vos=incidentResourceDao.getGrid2(incidentResourceFilter, sortFields);

			if(null != incidentResourceFilter.getCheckboxFilter() && incidentResourceFilter.getCheckboxFilter().isPersonnel()) {
				// when personnel filter is set, 
				// return vos as a single view (no tree structure)
				rtnVos=vos;
			}else if(null != incidentResourceFilter.getCheckboxFilter() && incidentResourceFilter.getCheckboxFilter().isNonpersonnel()) {
					// when nonpersonnel filter is set, 
					// return vos as a single view (no tree structure)
					rtnVos=vos;
			} else {
				if(BooleanUtility.isFalse(incidentResourceFilter.getHierarchal()))
					rtnVos=vos;
				else
					rtnVos = IncidentResourceGridVo.toHierarchyCollection(vos,null);
			}
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_INCIDENT_RESOURCE_GRID");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setRecordset(rtnVos);

			// if filter has rosterParentId, grab the irvo
			if(LongUtility.hasValue(incidentResourceFilter.getRosterParentId())){
				if(CollectionUtility.hasValue(rtnVos)){
					IncidentResourceGridVo irgvo = rtnVos.iterator().next();
					IncidentResource ir = incidentResourceDao.getById(irgvo.getIncidentResourceId(), IncidentResourceImpl.class);
					IncidentResourceVo irvo = IncidentResourceVo.getInstance(ir,true);
					dialogueVo.setResultObjectAlternate3(irvo);
				}
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		return this.getById(id, null, dialogueVo);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long incidentResourceId, Long assignmentId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			
			if(null == dialogueVo) dialogueVo = new DialogueVo();
			
			/*
			 * clean up duplicate resource questions
			 */
			incidentResourceDao.cleanUpDuplicateQuestionIssue(incidentResourceId);

			TimePostDao tpDao2 = (TimePostDao)context.getBean("timePostDao");
			if(LongUtility.hasValue(incidentResourceId)){
				// fix stop times
				tpDao2.fixStopTimes(incidentResourceId,null);
			}
			
			IncidentResource entity = incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);

			//IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			//Incident incidentEntity = incidentDao.getById(entity.getIncidentId());
			
			IncidentResourceVo vo = new IncidentResourceVo();

			vo = IncidentResourceVo.getInstance(entity, true);

			if(null != vo.getWorkPeriodVo()){
				if(vo.getWorkPeriodVo().getWorkPeriodQuestionValueVos().size()==0){
					Collection<WorkPeriodQuestionValueVo> vos=this.getDefaultIncidentQuestions(entity.getIncidentId());
					if(null != vos && vos.size()>0){
						vo.getWorkPeriodVo().setWorkPeriodQuestionValueVos(vos);
						for(WorkPeriodQuestionValueVo qvo : vos){
							if(qvo.getIncidentQuestionVo().getQuestionVo().getQuestionType()==QuestionTypeEnum.AIRTRAVEL){
								if(qvo.getIncidentQuestionVo().getVisible())
									vo.getWorkPeriodVo().getAirTravelQuestions().add(qvo);
							}else{
								if(qvo.getIncidentQuestionVo().getVisible())
									vo.getWorkPeriodVo().getCheckInQuestions().add(qvo);
							}
						}
					}
				}else{
					// add any that are missing, new questions could have been added to the system
					Collection<WorkPeriodQuestionValueVo> vos=this.getDefaultIncidentQuestions(entity.getIncidentId());
					if(null != vos && vos.size()>0){
						for(WorkPeriodQuestionValueVo qvo : vos){
							boolean exists = false;
							for(WorkPeriodQuestionValueVo existing : vo.getWorkPeriodVo().getWorkPeriodQuestionValueVos()){
								if(qvo.getIncidentQuestionVo().getId().compareTo(existing.getIncidentQuestionVo().getId()) == 0){
									exists=true;
									break;
								}
							}
							
							if(!exists){
								// add it
								if(qvo.getIncidentQuestionVo().getQuestionVo().getQuestionType()==QuestionTypeEnum.AIRTRAVEL){
									if(qvo.getIncidentQuestionVo().getVisible())
										vo.getWorkPeriodVo().getAirTravelQuestions().add(qvo);
								}else{
									if(qvo.getIncidentQuestionVo().getVisible())
										vo.getWorkPeriodVo().getCheckInQuestions().add(qvo);
								}
							}
							
						}
					}
					
				}
				
				if(null != assignmentId){
					if(null != vo.getWorkPeriodVo().getAssignmentVos()){
						for(AssignmentVo assignVo : vo.getWorkPeriodVo().getAssignmentVos()){
							if(assignVo.getId().compareTo(assignmentId)==0){
								vo.getWorkPeriodVo().setCurrentAssignmentVo(assignVo);
							}
						}
					}
				}
			}
			
			// not sure why the countrysubdivision is not getting abbrv set,
			// so do a quick a lookup and set them
			if(null != vo.getWorkPeriodVo().getWorkPeriodOvernightStayInfoVos()){
				Collection<WorkPeriodOvernightStayInfoVo> gcvos = new ArrayList<WorkPeriodOvernightStayInfoVo>();
				GlobalCacheVo gcvo = super.getGlobalCacheVo();
				for(WorkPeriodOvernightStayInfoVo v : vo.getWorkPeriodVo().getWorkPeriodOvernightStayInfoVos()){
					for(CountryCodeSubdivisionVo csvo : gcvo.getCountryCodeSubdivisionVos()){
						if(csvo.getId().compareTo(v.getCountrySubdivisionVo().getId())==0){
							v.getCountrySubdivisionVo().setCountrySubAbbreviation(csvo.getCountrySubAbbreviation());
							gcvos.add(v);
							break;
						}
					}
				}
				vo.getWorkPeriodVo().setWorkPeriodOvernightStayInfoVos(gcvos);
			}
//			return vo;
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_INCIDENT_RESOURCE_BY_ID");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			
			dialogueVo.setCourseOfActionVo(coa);
			
			//need to sort vo using demob settings - defect 5035 - begin
			Collection<WorkPeriodQuestionValueVo> airTravelQuestions2 = new ArrayList<WorkPeriodQuestionValueVo>();
			
			for(WorkPeriodQuestionValueVo qvo1 : this.getDefaultIncidentQuestions(entity.getIncidentId())) {
				//System.out.println("entity: " + qvo1.getIncidentQuestionVo().getQuestionVo().getQuestion());
				for(WorkPeriodQuestionValueVo qvo2 : vo.getWorkPeriodVo().getAirTravelQuestions()) {
					if (qvo1.getIncidentQuestionVo().getQuestionVo().getQuestion().equals(qvo2.getIncidentQuestionVo().getQuestionVo().getQuestion())) {
						//System.out.println("add from getAirTravelQuestions");
						airTravelQuestions2.add(qvo2);
					}
				}				
			}
			
			Boolean found = false;
			for(WorkPeriodQuestionValueVo qvo3 : vo.getWorkPeriodVo().getAirTravelQuestions()) {
				found = false;
				for(WorkPeriodQuestionValueVo qvo4 : this.getDefaultIncidentQuestions(entity.getIncidentId())) {
					if (qvo3.getIncidentQuestionVo().getQuestionVo().getQuestion().equals(qvo4.getIncidentQuestionVo().getQuestionVo().getQuestion())) {
						found = true;
					}
				}
				
				if (!found) {
					//System.out.println("!found: add from getAirTravelQuestions");
					airTravelQuestions2.add(qvo3);
				}
			}
			
			vo.getWorkPeriodVo().setAirTravelQuestions(airTravelQuestions2);
			//System.out.println("air travel questions sorted");
			//need to sort vo using demob settings - defect 5035 - end
			
		//testing	vo.setCostDataVo(null);
		//testing	vo.setWorkPeriodVo(null);
			// vo.setCostDataVo(null);
			//vo.setWorkPeriodVo(null);
			//vo.setResourceVo(null);
			//vo.setIncidentVo(null);
			//vo.getWorkPeriodVo().setAirTravelQuestions(null);
//			vo.getWorkPeriodVo().setAssignmentVos(null);
			//vo.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentTimeVo(null);
//			vo.getWorkPeriodVo().getCurrentAssignmentVo().setTimeAssignAdjustVos(null);
			//vo.getWorkPeriodVo().setCurrentAssignmentVo(null);
			//vo.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentTimeVo(null);
		//	vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
	//			.getAdPaymentInfoVo().setRateAreaVo(null);
			//vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
			//	.setContractorPaymentInfoVo(null);
			//vo.getWorkPeriodVo().getCurrentAssignmentVo().
			//vo.getWorkPeriodVo().setCiResourceMobilizationVo(null);
			//vo.getWorkPeriodVo().setWorkPeriodOvernightStayInfoVos(null);
			//vo.getWorkPeriodVo().setWorkPeriodQuestionValueVos(null);
			//vo.getWorkPeriodVo().setWpDefaultIncidentAccountCodeVo(null);
			dialogueVo.setResultObject(vo);
			return dialogueVo;
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getDefaultIncidentQuestions(java.lang.Long)
	 */
	public Collection<WorkPeriodQuestionValueVo> getDefaultIncidentQuestions(Long incidentId) throws ServiceException {
		try{
			Collection<IncidentQuestionVo> questionVos = incidentResourceDao.getIncidentQuestions(incidentId);
			
			Collection<WorkPeriodQuestionValueVo> vos = new ArrayList<WorkPeriodQuestionValueVo>();
			
			for(IncidentQuestionVo vo : questionVos){
				WorkPeriodQuestionValueVo wpqvv = new WorkPeriodQuestionValueVo();
				wpqvv.setIncidentQuestionVo(vo);

				vos.add(wpqvv);
			}
			
			return vos;
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#deleteResource(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteResource(IncidentResourceGridVo gridVo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Collection<Long> irIds = new ArrayList<Long>();
			irIds.add(gridVo.getIncidentResourceId());
			
			Collection<Long> resourceIds = new ArrayList<Long>();
			resourceIds.add(gridVo.getResourceId());

			IncidentResourceDeleteRulesHandler ruleHandler = new IncidentResourceDeleteRulesHandler(context);
			
			if(ruleHandler.execute(irIds, gridVo, dialogueVo)==AbstractRule._OK){

				/*
				 * delete children per
				 * Defect #3276 - CR 146
				 * 
				 */
				Collection<Long> childIds = new ArrayList<Long>();
				for(Long resId : resourceIds){
					Resource r = resourceDao.getById(resId,ResourceImpl.class);
					if(CollectionUtility.hasValue(r.getChildResources()))
						childIds.addAll(this.getChildResourceIds(r.getChildResources()));
				}
				if(CollectionUtility.hasValue(childIds))
					resourceIds.addAll(childIds);
				
				// delete ir and workperiod info
				int count=resourceDao.deleteIncidentResources2(resourceIds, false);

				// set deleted date
				count+=resourceDao.deleteIncidentResources(resourceIds, false);
				
				if(count==0) {
					throw new ServiceException("Unable to delete resources, count returned 0.");
				} 
				
				/*
				 * update children, set parentresourceid = null
				 * 
				 * commenting out unrostering of children per
				 * Defect #3276 - CR 146
				 */
				//resourceDao.unRosterIncidentResourcesChildren(resourceIds);

				// remove assignment
				
				dialogueVo.setResultObject(gridVo);
				
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("DELETE_RESOURCE");
				coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coa.setMessageVo(new MessageVo("text.incidentResources", "text.affirmDeleteIncidentResources", null, MessageTypeEnum.INFO));
				coa.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coa);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	private Collection<Long> getChildResourceIds(Collection<Resource> childs) throws Exception{
		Collection<Long> rtnIds = new ArrayList<Long>();

		for(Resource c : childs){
			rtnIds.add(c.getId());
			
			if(CollectionUtility.hasValue(c.getChildResources())){
				rtnIds.addAll(getChildResourceIds(c.getChildResources()));
			}
		}
		
		return rtnIds;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#saveResource(gov.nwcg.isuite.core.vo.IncidentResourceVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveResource(IncidentResourceVo incidentResourceVo, Boolean propagateToChildren, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			IncidentResource irEntity = null;
			if(LongUtility.hasValue(incidentResourceVo.getId())){
				irEntity = incidentResourceDao.getById(incidentResourceVo.getId(), IncidentResourceImpl.class);
			}
			
			RequestNumberUtil.formatRequestNumber(incidentResourceVo);
			
			IncidentResourceSaveRulesHandler rulesHandler = new IncidentResourceSaveRulesHandler(context);

			if(rulesHandler.execute(incidentResourceVo, irEntity, dialogueVo)==AbstractRuleHandler._OK){
				
				Resource permResource = null;
				Resource resource = null;
				ResourceData origResourceData = null;

				/*
				 * If the resourceId is available, load the resource instance
				 * If the resourceId not available, must be a new resource being added to incident
				 */
				if(LongUtility.hasValue(incidentResourceVo.getResourceVo().getId())){
					resource=resourceDao.getById(incidentResourceVo.getResourceVo().getId(), ResourceImpl.class);
					origResourceData = new ResourceData(resource);
					IncidentResource ir = resource.getIncidentResources().iterator().next();
					origResourceData.iacId=ir.getWorkPeriod().getDefIncidentAccountCodeId();
				}else{
					if(LongUtility.hasValue(incidentResourceVo.getResourceVo().getPermanentResourceId())){
						permResource=resourceDao.getById(incidentResourceVo.getResourceVo().getPermanentResourceId(), ResourceImpl.class);
					}
				}

				// set default accrual code
				DefaultAccrualCodeHandler acHandler = new DefaultAccrualCodeHandler(super.context);
				if(null != incidentResourceVo.getCostDataVo() && BooleanUtility.isFalse(incidentResourceVo.getCostDataVo().getAccrualLocked()))
					acHandler.setDefaultAccrualCodeVo(incidentResourceVo);

				// dan 7/29/2019 
				// since kind selection is coming from dropdownData
				// get full kind data and set it
				if ( incidentResourceVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo() != null
						&& LongUtility.hasValue(incidentResourceVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getId())){
					Kind kind = this.kindDao.getById(incidentResourceVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getId(), KindImpl.class);
					incidentResourceVo.getWorkPeriodVo().getCurrentAssignmentVo().setKindVo(
						KindVo.getInstance(kind, true));
				}
				
				// populate the entity
				resource = IncidentResourceVo.toResourceEntity(resource,incidentResourceVo, permResource);
				
				// save the entity
				resourceDao.save(resource);
				
				//flushing is good practice when the entity needs to be returned immediately
				resourceDao.flushAndEvict(resource.getIncidentResources().iterator().next());
				resourceDao.flushAndEvict(resource);
				if(null != irEntity)incidentResourceDao.flushAndEvict(irEntity);
				
				irEntity = incidentResourceDao.getById(incidentResourceVo.getId(), IncidentResourceImpl.class);
				if(null != resource.getIncidentResources() && resource.getIncidentResources().size() > 0) {
					IncidentResource ir = resource.getIncidentResources().iterator().next();
					resourceDao.flushAndEvict(resource);
					incidentResourceDao.flushAndEvict(ir);
					if(!LongUtility.hasValue(incidentResourceVo.getResourceVo().getId())){
						incidentResourceVo.getResourceVo().setId(resource.getId());
					}
				}

				resource  = resourceDao.getById(resource.getId(), ResourceImpl.class);

				irEntity=resource.getIncidentResources().iterator().next();
				if(null != irEntity){
					Long resNumId=incidentResourceDao.updateResNumId(irEntity.getId());
					irEntity.setResNumId(resNumId);
				}
				resourceDao.flushAndEvict(irEntity);
				
				// execute post process actions
				rulesHandler.executeProcessedActions(incidentResourceVo, origResourceData, irEntity, dialogueVo);
				// dan 7/27/2013 commenting out next 2 lines so flush/evict does not roll back updated from executeProcessedActions
				//resourceDao.flushAndEvict(resource.getIncidentResources().iterator().next());
				//resourceDao.flushAndEvict(resource);
				
				resource  = resourceDao.getById(resource.getId(), ResourceImpl.class);
				//irEntity=resource.getIncidentResources().iterator().next();

				
				IncidentResourceVo irVo = null;
				if(null != resource.getIncidentResources() && resource.getIncidentResources().size() > 0) {
					IncidentResource ir = resource.getIncidentResources().iterator().next();
					resourceDao.flushAndEvict(resource.getIncidentResources().iterator().next());
					Long irid = ir.getId();
					resourceDao.flushAndEvict(resource);
					
					ir = incidentResourceDao.getById(irid, IncidentResourceImpl.class);
					
					if(CollectionUtility.hasValue(ir.getIncident().getIncidentAccountCodes())){
						for(IncidentAccountCode iac : ir.getIncident().getIncidentAccountCodes()){
							//incidentResourceDao.flushAndEvict(iac.getAccountCode());
							//incidentResourceDao.flushAndEvict(iac);
						}
					}
					//incidentResourceDao.flushAndEvict(ir.getIncident());
					//incidentResourceDao.flushAndEvict(ir.getWorkPeriod().getDefIncidentAccountCode());

					/*
					try{
						if(CollectionUtility.hasValue(ir.getWorkPeriod().getAssignments())){
							for(Assignment a : ir.getWorkPeriod().getAssignments()){
								if(null != a.getAssignmentTime()){
									for(AssignmentTimePost atp : a.getAssignmentTime().getAssignmentTimePosts()){
										if(null != atp && DateUtil.hasValue(atp.getPostStopDate())){
											String dt = DateUtil.toDateString(atp.getPostStopDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
											String tm = DateUtil.toMilitaryTime(atp.getPostStopDate());
											Date dt2 = DateUtil.toDate(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY);
											dt2 = DateUtil.addMilitaryTimeToDate(dt2, tm);
											atp.setPostStopDate(dt2);
										}
										incidentResourceDao.flushAndEvict(atp);
									}
									for(TimeAssignAdjust taa :  a.getTimeAssignAdjusts()){
										incidentResourceDao.flushAndEvict(taa);
									}
									incidentResourceDao.flushAndEvict(a.getAssignmentTime());
								}
								incidentResourceDao.flushAndEvict(a);
							}
						}
					}catch(Exception ee){
						
					}
					*/
					incidentResourceDao.flushAndEvict(ir);
					resourceDao.flushAndEvict(resource);
					
					IncidentResource entity = incidentResourceDao.getById(ir.getId(), IncidentResourceImpl.class);

					IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
					//Incident incidentEntity = incidentDao.getById(entity.getIncidentId());
					
					irVo = IncidentResourceVo.getInstance(entity, true);
//					irVo = this.getById(ir.getId()); 
				} 

				/*
				 * Get the incidentResourceGridVo for this resource
				 */
				IncidentResourceFilter irFilter = new IncidentResourceFilterImpl();
				irFilter.setIncidentResourceId(irVo.getId());
				Collection<IncidentResourceGridVo> irgVos = incidentResourceDao.getGrid2(irFilter, null);
				
				IncidentResourceGridVo irgVo = null;
				if(CollectionUtility.hasValue(irgVos))
					irgVo = irgVos.iterator().next();
				
				// get grid vo and children grid vos
				Collection<IncidentResourceGridVo> updatedVos = new ArrayList<IncidentResourceGridVo>();
				this.loadRosterTreeVos(resource.getId(),incidentResourceVo.getIncidentVo().getId(), updatedVos);
				
				DialogueVo dvo2;
				/*
				irFilter = new IncidentResourceFilterImpl();
				irFilter.setRosterParentId(resource.getId());
				irFilter.setIncidentId(incidentResourceVo.getIncidentVo().getId());
				DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
				if(CollectionUtility.hasValue(dvo2.getRecordset())){
					updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
					Collection<IncidentResourceGridVo> irgvos = (Collection<IncidentResourceGridVo>)dvo2.getRecordset();
					for(IncidentResourceGridVo irgvo : irgvos){
						if(CollectionUtility.hasValue(irgvo.getChildren())){
							for(IncidentResourceGridVo cvo : irgvo.getChildren()){
								updatedVos.add(cvo);	
							}
						}
					}
				}
				*/
				
				// if resource being saved has a parent, get the updated parent grid vo
				if(LongUtility.hasValue(incidentResourceVo.getResourceVo().getParentResourceId())){
					irFilter = new IncidentResourceFilterImpl();
					irFilter.setRosterParentId(incidentResourceVo.getResourceVo().getParentResourceId());
					irFilter.setIncidentId(incidentResourceVo.getIncidentVo().getId());
					dvo2 = this.getGrid(irFilter, null, new DialogueVo());
					if(CollectionUtility.hasValue(dvo2.getRecordset()))
						updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
				}

				if(CollectionUtility.hasValue(updatedVos))
					dialogueVo.setResultObjectAlternate3(updatedVos);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_INCIDENT_RESOURCE_COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentResources", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
				//need to sort vo using demob settings - defect 5035 - begin
				Collection<WorkPeriodQuestionValueVo> airTravelQuestions2 = new ArrayList<WorkPeriodQuestionValueVo>();
				
				for(WorkPeriodQuestionValueVo qvo1 : this.getDefaultIncidentQuestions(irEntity.getIncidentId())) {
					//System.out.println("entity: " + qvo1.getIncidentQuestionVo().getQuestionVo().getQuestion());
					for(WorkPeriodQuestionValueVo qvo2 : irVo.getWorkPeriodVo().getAirTravelQuestions()) {
						if (qvo1.getIncidentQuestionVo().getQuestionVo().getQuestion().equals(qvo2.getIncidentQuestionVo().getQuestionVo().getQuestion())) {
							//System.out.println("add from getAirTravelQuestions");
							airTravelQuestions2.add(qvo2);
						}
					}				
				}
				
				Boolean found = false;
				for(WorkPeriodQuestionValueVo qvo3 : irVo.getWorkPeriodVo().getAirTravelQuestions()) {
					found = false;
					for(WorkPeriodQuestionValueVo qvo4 : this.getDefaultIncidentQuestions(irEntity.getIncidentId())) {
						if (qvo3.getIncidentQuestionVo().getQuestionVo().getQuestion().equals(qvo4.getIncidentQuestionVo().getQuestionVo().getQuestion())) {
							found = true;
						}
					}
					
					if (!found) {
						//System.out.println("!found: add from getAirTravelQuestions");
						airTravelQuestions2.add(qvo3);
					}
				}
				
				irVo.getWorkPeriodVo().setAirTravelQuestions(airTravelQuestions2);
				//System.out.println("air travel questions sorted");
				//need to sort vo using demob settings - defect 5035 - end

				dialogueVo.setResultObject(irVo);
				dialogueVo.setResultObjectAlternate(irgVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	private void loadRosterTreeVos(Long resourceId,Long incidentId,Collection<IncidentResourceGridVo> updatedVos ) throws Exception{
		IncidentResourceFilter irFilter = new IncidentResourceFilterImpl();
		irFilter.setRosterParentId(resourceId);
		irFilter.setIncidentId(incidentId);
		DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
		if(CollectionUtility.hasValue(dvo2.getRecordset())){
			updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
			Collection<IncidentResourceGridVo> irgvos = (Collection<IncidentResourceGridVo>)dvo2.getRecordset();
			for(IncidentResourceGridVo irgvo : irgvos){
				if(CollectionUtility.hasValue(irgvo.getChildren())){
					for(IncidentResourceGridVo cvo : irgvo.getChildren()){
						this.loadRosterTreeVos(cvo.getResourceId(), incidentId, updatedVos);
						//updatedVos.add(cvo);	
					}
				}
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#addPermanentResource(java.lang.Long, gov.nwcg.isuite.core.domain.Resource, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo addPermanentResource(Long incidentId,Resource resource,DialogueVo dialogueVo) throws ServiceException{
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#getUnassignedInventoryResources(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getUnassignedInventoryResources(String dispatchCenter,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			
			Collection<ResourceInventoryGridVo> vos = this.incidentResourceDao.getUnassignedInventoryResources(dispatchCenter,super.getUserVo().getId());
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_AVAILABLE_INVENORY_RESOURCES");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#getPrepInventoryResources(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getPrepInventoryResources(Collection<ResourceInventoryGridVo> gridVos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			Collection<Long> ids = ResourceVo.toIds(gridVos);
			Collection<Resource> entities = resourceDao.getAllByIds(ids);
			
			Collection<IncidentResourceVo> irVos = new ArrayList<IncidentResourceVo>();
			
			for (Resource r : entities){
				
				IncidentResourceVo vo = new IncidentResourceVo();
				
				// resource info
				vo.getResourceVo().setPermanent(false);
				vo.getResourceVo().setPermanentResourceId(r.getId());
				vo.getResourceVo().setRossResId(r.getRossResId());
				vo.getResourceVo().setLastName(r.getLastName());
				vo.getResourceVo().setFirstName(r.getFirstName());
				vo.getResourceVo().setResourceName(r.getResourceName());
				vo.getResourceVo().setPerson(r.isPerson());
				vo.getResourceVo().setEnabled((true));

				vo.setCostDataVo(new CostDataVo());
				vo.getCostDataVo().setGenerateCosts(true);
				
				if(null != r.getAgency())
					vo.getResourceVo().setAgencyVo(AgencyVo.getInstance(r.getAgency(), true));
				if(null != r.getOrganization())
					vo.getResourceVo().setOrganizationVo(OrganizationVo.getInstance(r.getOrganization(), true));
				if(null != r.getPrimaryDispatchCenter())
					vo.getResourceVo().setPrimaryDispatchCenterVo(OrganizationVo.getInstance(r.getPrimaryDispatchCenter(), true));
				
				vo.getResourceVo().setPhone(r.getPhone());
				
				// assignment info
				vo.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentStatus(AssignmentStatusTypeEnum.C);
				vo.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentStatusVo(AssignmentStatusTypeEnum.getAssignmentVoByCode("C"));
				vo.getWorkPeriodVo().getCurrentAssignmentVo().setStartDate(Calendar.getInstance().getTime());
				vo.getWorkPeriodVo().getCiCheckInDateVo().setDateString(DateUtil.toDateString(Calendar.getInstance().getTime()));
				//vo.getWorkPeriodVo().setCiCheckInDate(Calendar.getInstance().getTime());
				vo.getWorkPeriodVo().getCiFirstWorkDateVo().setDateString(DateUtil.toDateString(Calendar.getInstance().getTime()));
				//vo.getWorkPeriodVo().setCiFirstWorkDate(Calendar.getInstance().getTime());
				vo.getWorkPeriodVo().setCiLengthAtAssignment(Long.parseLong("14"));
				
				if(r.isPerson()){
					vo.getResourceVo().setNumberOfPersonnel(new Long(1));
				}else{
					if(CollectionUtility.hasValue(r.getResourceKinds())){
						ResourceKind rk = r.getResourceKinds().iterator().next();
						if(null != rk && null != rk.getKind()){
							KindVo kindVo = new KindVo();
							kindVo.setId(rk.getKind().getId());
							kindVo.setCode(rk.getKind().getCode());
							kindVo.setDescription(rk.getKind().getDescription());
							vo.getWorkPeriodVo().getCurrentAssignmentVo().setKindVo(kindVo);
						}
					}
				}
				
				irVos.add(vo);
				
				resourceDao.flushAndEvict(r);
			}
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_PREP_INVENTORY_RESOURCES");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setRecordset(irVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo saveAndRoster(Long parentResourceId, IncidentResourceVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(parentResourceId, "RESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			Resource parentResource = resourceDao.getById(parentResourceId, ResourceImpl.class);
			if(null != parentResource.getIncidentResources() && parentResource.getIncidentResources().size() > 0) {
				IncidentResource ir = parentResource.getIncidentResources().iterator().next();
				if(ir.getIncidentId().compareTo(vo.getIncidentVo().getId()) != 0){
					throw new Exception("The new Roster Resource's Incident must be the same as the Parent Resource's Incident.");
							
				}
				
				ResourceVo parentVo=ResourceVo.getInstance(parentResource, false);
				vo.getResourceVo().setParentResourceVo(parentVo);
				
				vo.getCostDataVo().getAssignDateVo().setDateString(vo.getWorkPeriodVo().getCiCheckInDateVo().getDateString());
			}
			
			dialogueVo = this.saveResource(vo, false, dialogueVo);
			
			if(dialogueVo.getCourseOfActionVo().getCoaName().equals("SAVE_INCIDENT_RESOURCE_COMPLETE")){
				dialogueVo.getCourseOfActionVo().setCoaName("SAVE_AND_ROSTER");
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#loadParentAndSupplimentals(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo loadParentAndSupplimentals(Long rosterParentId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			Boolean isThisResAlsoRostered=false;
			Resource parent = resourceDao.getById(rosterParentId, ResourceImpl.class);
			if(LongUtility.hasValue(parent.getParentResourceId())){
				isThisResAlsoRostered=true;
			}
			IncidentResource ir=parent.getIncidentResources().iterator().next();
			
			// get roster grid
			IncidentResourceFilter filter = new IncidentResourceFilterImpl();
			filter.setRosterParentId(rosterParentId);
			DialogueVo dvo2 = this.getGrid(filter, null, new DialogueVo());
			if(CollectionUtility.hasValue(dvo2.getRecordset())){
				dialogueVo.setRecordset(dvo2.getRecordset());
				if(null != dvo2.getResultObjectAlternate3())
					dialogueVo.setResultObjectAlternate3(dvo2.getResultObjectAlternate3());
				
			}
			
			// get available res inventory resources
			String dispatchCenter=super.getUserSessionVo().getPdcUnitCode();
			DialogueVo dvo3 = this.getUnassignedInventoryResources(dispatchCenter, new DialogueVo());
			if(CollectionUtility.hasValue(dvo3.getRecordset()))
				dialogueVo.setResultObjectAlternate(dvo3.getRecordset());
			
			// get available incident resources
			filter = new IncidentResourceFilterImpl();
			filter.setUnRosteredOnly(true);
			filter.setExcludeResourceId(rosterParentId);
			filter.setIncidentId(ir.getIncidentId());
			
			DialogueVo dvo4 = this.getGrid(filter, null, new DialogueVo());
			if(CollectionUtility.hasValue(dvo4.getRecordset())){
				// if rosterParentId is already part of a roster
				// , then we want to make sure the very top level resource
				// is not in the list to prevent 2 resources in the same tree
				// from being rostered to each other
				if(isThisResAlsoRostered==true){
					Collection<IncidentResourceGridVo> rtnVos = (Collection<IncidentResourceGridVo>)dvo4.getRecordset();
					Collection<IncidentResourceGridVo> newVos = new ArrayList<IncidentResourceGridVo>();
					Long topLevelId=resourceDao.getTopLevelResourceId(rosterParentId);

					if(topLevelId.compareTo(rosterParentId)!=0){
						for(IncidentResourceGridVo v : rtnVos){
							if(v.getResourceId().compareTo(topLevelId)!=0){
								newVos.add(v);
							}
						}
						dialogueVo.setResultObjectAlternate2(newVos);
					}else
						dialogueVo.setResultObjectAlternate2(dvo4.getRecordset());
				}else{
					dialogueVo.setResultObjectAlternate2(dvo4.getRecordset());
				}
			}
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_PARENT_AND_SUPPLIMENTALS");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#unroster(gov.nwcg.isuite.core.vo.IncidentResourceGridVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo unroster(IncidentResourceGridVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getResourceId(), "RESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			Resource entity = this.resourceDao.getById(vo.getResourceId(), ResourceImpl.class);
			entity.setParentResource(null);
			entity.setLeaderType(new Integer(99));
			resourceDao.save(entity);
			resourceDao.flushAndEvict(entity);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("UNROSTER_RESOURCE");
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setMessageVo(new MessageVo("text.incidentResources", "info.generic" , new String[]{"Resource was unrostered"}, MessageTypeEnum.INFO));
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#rosterExisting(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo rosterExisting(Long parentResourceId,Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(parentResourceId, "RESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			Collection<IncidentResourceGridVo> rosteredGridVos = new ArrayList<IncidentResourceGridVo>();
			
			Resource parent = new ResourceImpl();
			parent.setId(parentResourceId);
			
			for(IncidentResourceGridVo vo : vos ){
				Resource entity = resourceDao.getById(vo.getResourceId(), ResourceImpl.class);
				entity.setParentResource(parent);
				resourceDao.save(entity);
				resourceDao.flushAndEvict(entity);
				
				vo.setParentResourceId(parentResourceId);
				rosteredGridVos.add(vo);
			}
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("ROSTER_EXISTING");
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setMessageVo(new MessageVo("text.incidentResources", "info.generic" , new String[]{"Resources were rostered"}, MessageTypeEnum.INFO));
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			
			dialogueVo.setResultObject(vos);
			dialogueVo.setResultObjectAlternate(rosteredGridVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
	
		return dialogueVo;
	}

	public DialogueVo getStrikeTeams(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Collection<Long> ids = new ArrayList<Long>();
			if(LongUtility.hasValue(incidentGroupId)){
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup igEntity = igdao.getById(incidentGroupId, IncidentGroupImpl.class);
				
				if(null != igEntity && (null != igEntity.getIncidents() && igEntity.getIncidents().size() > 0)){
					for(Incident i : igEntity.getIncidents()){
						ids.add(i.getId());
					}
				}
			}else
				ids.add(incidentId);
			
			Collection<IncidentResourceGridVo> vos = incidentResourceDao.getStrikeTeams(incidentId, ids);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_STRIKE_TEAMS");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#propagateCrewEmploymentType(java.lang.String, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo propagateCrewEmploymentType(String employmentType,Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentResourceId, "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}

			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			IncidentResourceVo irvo = IncidentResourceVo.getInstance(irEntity, true);
			incidentResourceDao.flushAndEvict(irEntity.getResource());
			incidentResourceDao.flushAndEvict(irEntity);
			Long rid = irEntity.getResourceId();

			ResourceDao rdao = (ResourceDao)context.getBean("resourceDao");
			Resource resource = rdao.getById(rid,ResourceImpl.class);
			//System.out.println(resource.getId());
			//System.out.println(resource.getResourceName());
			
			Resource parent = null;
			
			if(null == resource.getParentResource()){
				parent=resource;
				/*
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("MSG_FINISHED");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setMessageVo(new MessageVo("text.time"
						, "error.800000"
						, new String[]{"This feature is only available when a subordinate is selected."}
						, MessageTypeEnum.CRITICAL));
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
				*/
			}else{
				if(BooleanUtility.isTrue(resource.isPerson())){
					parent = resource.getParentResource();
				}else{
					parent = resource;
				}
			}
			

			/*
			 * insert an assignment time record for any crew members for which
			 * a record does not exist
			 */
			//Long atCount = incidentResourceDao.assignmentTimeRecordCount(parent.getId());
			//if(atCount>0) {
			//	incidentResourceDao.insertAssignmentTime(parent.getId());
			//}
			this.insertTimeRecord(parent);
			
			/*
			 * Get list of crew members that need updated
			 */
			Collection<Long> irIdsToBeUpdated=new ArrayList<Long>();
			this.getCrewUpdateIds(parent,irIdsToBeUpdated);
			
			/*
			 * update the employment type for crew assignment time records that
			 * do not have an employment type
			 */
			for(Long id : irIdsToBeUpdated){
				IncidentResource ir = incidentResourceDao.getById(id, IncidentResourceImpl.class);
				IncidentResourceVo updateIrVo = IncidentResourceVo.getInstance(ir,true);
				incidentResourceDao.flushAndEvict(ir.getResource());
				incidentResourceDao.flushAndEvict(ir);
				Resource r = rdao.getById(ir.getResourceId(), ResourceImpl.class);
				incidentResourceDao.updateCrewEmploymentType(employmentType, r.getParentResourceId());
				if(employmentType.equals("OTHER")){
					BigDecimal otherRate=irvo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getOtherDefaultRate();
					incidentResourceDao.updateOtherRate(updateIrVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getId(),otherRate);
				}
			}

			incidentResourceDao.flushAndEvict(irEntity);
			irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			IncidentResourceVo irVo = IncidentResourceVo.getInstance(irEntity, true);
			
			// get grid vo and children grid vos
			IncidentResourceFilterImpl irFilter = new IncidentResourceFilterImpl();
			//irFilter.setRosterParentId(parent.getId());
			Collection<IncidentResourceGridVo> updatedVos = new ArrayList<IncidentResourceGridVo>();
			for(Long irid : irIdsToBeUpdated){
				irFilter.setIncidentResourceId(irid);
				//irFilter.setIncidentId(irEntity.getIncidentId());
				DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
				if(CollectionUtility.hasValue(dvo2.getRecordset()))
					updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
			}
			
			dialogueVo.setResultObjectAlternate(updatedVos);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("PROPAGATE_CREW_EMPTYPE");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentResources", 
							   "info.9920" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(irVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	private void insertTimeRecord(Resource parent) throws Exception {
		Long atCount = incidentResourceDao.assignmentTimeRecordCount(parent.getId());
		if(atCount>0) {
			incidentResourceDao.insertAssignmentTime(parent.getId());
		}
		for(Resource r : parent.getChildResources()){
			ResourceDao rdao=(ResourceDao)context.getBean("resourceDao");
			rdao.flushAndEvict(r);
			Resource resource = rdao.getById(r.getId(),ResourceImpl.class);
			//System.out.println(resource.getId());
			//System.out.println(resource.getResourceName());
			if(CollectionUtility.hasValue(resource.getChildResources())){
				insertTimeRecord(resource);
			}
		}
		
	}

	private void getCrewUpdateIds(Resource parent, Collection<Long> ids) throws Exception {
		//System.out.println(parent.getId());
		Collection<Long> crewIds=incidentResourceDao.getCrewUpdateIds(parent.getId());
		ids.addAll(crewIds);
		for(Resource r : parent.getChildResources()){
			ResourceDao rdao=(ResourceDao)context.getBean("resourceDao");
			rdao.flushAndEvict(r);
			Resource resource = rdao.getById(r.getId(),ResourceImpl.class);
			//System.out.println(resource.getId());
			//System.out.println(resource.getResourceName());
			if(CollectionUtility.hasValue(resource.getChildResources())){
				getCrewUpdateIds(resource,ids);
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#propagateCrewAddress(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo propagateCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentResourceId, "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			// save parent address, phone, fax first
			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();
			
			Resource parent = null;

			if(null == resource.getParentResource()){
				parent=resource;
				/*
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("MSG_FINISHED");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setMessageVo(new MessageVo("text.time"
						, "error.800000"
						, new String[]{"This feature is only available when a subordinate is selected."}
						, MessageTypeEnum.CRITICAL));
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
				*/
			}else{
				parent = resource.getParentResource();
			}

			IncidentResource irParent = parent.getIncidentResources().iterator().next();
			
			/*
			 * Get the address from the selected incident resource
			 * 
			 * 6.0002 – Add/Edit Time Data for a Non-Contracted Person Resource at an Incident 
			 * Propagate Crew Address - This button displays when a Subordinate Resource is selected. 
			 * When a user clicks this button, the system must propagate the Address data for 
			 * the currently selected Subordinate Resource to all other Subordinate Resources rostered to the same parent 
			 * that do not have address data defined.
			 * NOTE: If there is Address data defined for the Resource, the system must not change that Address data.
			 */
			AssignmentTime atIncResource = AssignmentTimeVo.getAssignmentTime(irEntity);
			Address addrIncResource = atIncResource.getMailingAddress();
			
			
			/*
			 * Only need to update if the selected resource actually has an address
			 */
			if(null != addrIncResource && !AddressVo.isEmpty(addrIncResource)){
				AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
				
				/*
				 * insert an assignment time record for any crew members for which
				 * a record does not exist
				 */
				Long atCount = incidentResourceDao.assignmentTimeRecordCount(parent.getId());
				if(atCount>0) {
					incidentResourceDao.insertAssignmentTime(parent.getId());
				}
								
				/*
				 * Loop through all parent children
				 */
				Collection<AssignmentTime> entities = new ArrayList<AssignmentTime>();
				for(Resource subordinate : parent.getChildResources()){
					AssignmentTime atSubordinate = AssignmentTimeVo.getAssignmentTime(subordinate.getIncidentResources().iterator().next());
					if(null != atSubordinate){
						/*
						 * Determine if this subordinate's address needs to be updated
						 */
						if(null == atSubordinate.getMailingAddress() 
								|| AddressVo.isEmpty(atSubordinate.getMailingAddress())){

							Address addr = atSubordinate.getMailingAddress();
							if(null==addr)addr=new AddressImpl();
							addr.setAddressLine1(addrIncResource.getAddressLine1());
							addr.setAddressLine2(addrIncResource.getAddressLine2());
							addr.setCity(addrIncResource.getCity());
							addr.setPostalCode(addrIncResource.getPostalCode());
							if(null != addrIncResource.getCountrySubdivision()){
								CountrySubdivision cs = new CountrySubdivisionImpl();
								cs.setId(addrIncResource.getCountrySubdivision().getId());
								addr.setCountrySubdivision(cs);
							}
							atSubordinate.setMailingAddress(addr);
							if(!StringUtility.hasValue(atSubordinate.getPhone()))
								atSubordinate.setPhone(atIncResource.getPhone());
							
							if(!StringUtility.hasValue(atSubordinate.getFax()))
								atSubordinate.setFax(atIncResource.getFax());
							
							entities.add(atSubordinate);
						}else{
							// do not overwrite subordinate address
							// but update phone/fax
							if(!StringUtility.hasValue(atSubordinate.getPhone()))
								atSubordinate.setPhone(atIncResource.getPhone());
							
							if(!StringUtility.hasValue(atSubordinate.getFax()))
								atSubordinate.setFax(atIncResource.getFax());
							entities.add(atSubordinate);
						}
					}
				}
				
				if(CollectionUtility.hasValue(entities))
					atDao.saveAll(entities);
			}else{
				// try and propagate phone/fax
				if(null != atIncResource){
					AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
					
					if(StringUtility.hasValue(atIncResource.getPhone()) || StringUtility.hasValue(atIncResource.getFax())){

						Collection<AssignmentTime> entities = new ArrayList<AssignmentTime>();
						for(Resource subordinate : parent.getChildResources()){
							AssignmentTime atSubordinate = AssignmentTimeVo.getAssignmentTime(subordinate.getIncidentResources().iterator().next());
							if(null != atSubordinate){
								if(!StringUtility.hasValue(atSubordinate.getPhone()) || !StringUtility.hasValue(atSubordinate.getFax())){
									if(!StringUtility.hasValue(atSubordinate.getPhone()))
										atSubordinate.setPhone(atIncResource.getPhone());
									
									if(!StringUtility.hasValue(atSubordinate.getFax()))
										atSubordinate.setFax(atIncResource.getFax());
									
									entities.add(atSubordinate);
								}
							
							}
						}
						
						if(CollectionUtility.hasValue(entities))
							atDao.saveAll(entities);
						
					}
				}
			}

			// get grid vo and children grid vos
			IncidentResourceFilterImpl irFilter = new IncidentResourceFilterImpl();
			irFilter.setRosterParentId(parent.getId());
			irFilter.setIncidentId(irEntity.getIncidentId());
			Collection<IncidentResourceGridVo> updatedVos = new ArrayList<IncidentResourceGridVo>();
			DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
			if(CollectionUtility.hasValue(dvo2.getRecordset()))
				updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
			
			dialogueVo.setResultObjectAlternate(updatedVos);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("PROPAGATE_CREW_ADDRESS");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentResources", 
							   "info.0030" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#clearAllCrewAddress(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo clearAllCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentResourceId, "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();

			Resource parent = null;
			
			AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
			
			if(null == resource.getParentResource()){
				/*
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("MSG_FINISHED");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setMessageVo(new MessageVo("text.time"
						, "error.800000"
						, new String[]{"This feature is only available when a subordinate is selected."}
						, MessageTypeEnum.CRITICAL));
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
				*/
				parent=resource;
			}else{
				parent = resource.getParentResource();
			}

			Collection<AssignmentTime> entities = new ArrayList<AssignmentTime>();
			
			// update the primary record
			AssignmentTime atPrimary = AssignmentTimeVo.getAssignmentTime(resource.getIncidentResources().iterator().next());
			if(null != atPrimary){
				/*
				 * Determine if this subordinate's address needs to be cleared
				 */
				if(null != atPrimary.getMailingAddress()){
				
					Address addr = atPrimary.getMailingAddress();
					addr.setAddressLine1("");
					addr.setAddressLine2("");
					addr.setCity("");
					addr.setPostalCode("");
					addr.setCountrySubdivision(null);
					atPrimary.setPhone("");
					atPrimary.setFax("");
					entities.add(atPrimary);
				}else{
					atPrimary.setPhone("");
					atPrimary.setFax("");
					entities.add(atPrimary);
				}
			}
			
			/*
			 * Loop through all parent children
			 */
			for(Resource subordinate : parent.getChildResources()){
				AssignmentTime atSubordinate = AssignmentTimeVo.getAssignmentTime(subordinate.getIncidentResources().iterator().next());
				if(null != atSubordinate){
					/*
					 * Determine if this subordinate's address needs to be cleared
					 */
					if(null != atSubordinate.getMailingAddress()){
					
						Address addr = atSubordinate.getMailingAddress();
						addr.setAddressLine1("");
						addr.setAddressLine2("");
						addr.setCity("");
						addr.setPostalCode("");
						addr.setCountrySubdivision(null);
						atSubordinate.setPhone("");
						atSubordinate.setFax("");
						entities.add(atSubordinate);
					}else{
						atSubordinate.setPhone("");
						atSubordinate.setFax("");
						entities.add(atSubordinate);
					}
				}
			}

			if(CollectionUtility.hasValue(entities))
				atDao.saveAll(entities);
			
			incidentResourceDao.flushAndEvict(irEntity);
			irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			IncidentResourceVo irVo = IncidentResourceVo.getInstance(irEntity, true);

			// get grid vo and children grid vos
			IncidentResourceFilterImpl irFilter = new IncidentResourceFilterImpl();
			irFilter.setRosterParentId(parent.getId());
			irFilter.setIncidentId(irEntity.getIncidentId());
			Collection<IncidentResourceGridVo> updatedVos = new ArrayList<IncidentResourceGridVo>();
			DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
			if(CollectionUtility.hasValue(dvo2.getRecordset()))
				updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
			
			dialogueVo.setResultObjectAlternate(updatedVos);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("CLEAR_CREW_ADDRESS");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentResources", 
							   "info.0030" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(irVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
/*
	public DialogueVo getAllCrewMembers(Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		Collection<IncidentResourceVo> irvos = new ArrayList<IncidentResourceVo>();
		Collection<IncidentResourceVo> invalidIrvos = new ArrayList<IncidentResourceVo>();
		
		try{
		
			
			for(IncidentResourceGridVo vo : vos){
				DialogueVo dvo = this.getById(vo.getIncidentResourceId(), new DialogueVo());
				if(null != dvo.getResultObject()){
					IncidentResourceVo v =(IncidentResourceVo)(dvo.getResultObject());
					
					if( (v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == null)
							|| (v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus() == null 
									|| v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()==AssignmentStatusTypeEnum.F
									|| v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()==AssignmentStatusTypeEnum.D
										)){
						invalidIrvos.add(v);
					}else{
						if(v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() != null
								&& v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.CONTRACTOR){
							invalidIrvos.add(v);
						}else
							irvos.add(v);
					}
				}
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("GET_ALL_CREW_MEMBERS");
		    coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(irvos);
			dialogueVo.setResultObjectAlternate(invalidIrvos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
*/
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResource2Service#getAllCrewMembers(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAllCrewMembers(Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		Collection<IncidentResourceVo> irvos = new ArrayList<IncidentResourceVo>();
		Collection<IncidentResourceVo> invalidIrvos = new ArrayList<IncidentResourceVo>();
		
		try{
			for(IncidentResourceGridVo vo : vos){
				if(BooleanUtility.isTrue(vo.getIsPerson()) && null != vo.getEmploymentType()){
					IncidentResourceVo v = new IncidentResourceVo();
					v.setId(vo.getIncidentResourceId());
					v.setResourceVo(new ResourceVo());
					v.getResourceVo().setId(vo.getResourceId());
					v.getResourceVo().setResourceName(vo.getResourceName());
					v.getResourceVo().setLastName(vo.getLastName());
					v.getResourceVo().setFirstName(vo.getFirstName());
					v.setWorkPeriodVo(new WorkPeriodVo());
					
					WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
					WorkPeriod wpEntity=wpDao.getByIncidentResourceId(vo.getIncidentResourceId());
					
					if(null != wpEntity && null != wpEntity.getDefIncidentAccountCode() && null != wpEntity.getDefIncidentAccountCodeId()){
						IncidentAccountCodeVo avo = IncidentAccountCodeVo.getInstance(wpEntity.getDefIncidentAccountCode(),true);
						v.getWorkPeriodVo().setWpDefaultIncidentAccountCodeVo(avo);
					}else{ 
						//System.out.println("");
					}
					AssignmentDao aDao = (AssignmentDao)context.getBean("assignmentDao");
					Assignment assignEntity=aDao.getById(vo.getAssignmentId(), AssignmentImpl.class);
					
					v.getWorkPeriodVo().setCurrentAssignmentVo(new AssignmentVo());
					v.getWorkPeriodVo().getCurrentAssignmentVo().setId(vo.getAssignmentId());
					
					v.getWorkPeriodVo().getCurrentAssignmentVo().setKindVo(new KindVo());
					if(null != assignEntity){
						KindVo kindVo = new KindVo();
						kindVo.setId(assignEntity.getKind().getId());
						kindVo.setCode(assignEntity.getKind().getCode());
						kindVo.setDescription(assignEntity.getKind().getDescription());
						v.getWorkPeriodVo().getCurrentAssignmentVo().setKindVo(kindVo);
					}
					v.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentStatus(vo.getAssignmentStatus());
					v.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentStatusVo(new AssignmentStatusVo());
					v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().setCode(String.valueOf(vo.getAssignmentStatus()));
					v.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentTimeVo(new AssignmentTimeVo());
					v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().setId(vo.getAssignmentTimeId());
					v.getWorkPeriodVo().getCurrentAssignmentVo()
						.getAssignmentTimeVo().setEmploymentType(EmploymentTypeEnum.valueOf(vo.getEmploymentType()));
					
					if(vo.getEmploymentType().equalsIgnoreCase("AD")){
						AdPaymentInfoVo adPayInfoVo = AdPaymentInfoVo.getInstance(assignEntity.getAssignmentTime().getAdPaymentInfo(), true);
						v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().setAdPaymentInfoVo(
								adPayInfoVo);
					}
					if(vo.getEmploymentType().equalsIgnoreCase("OTHER")){
						v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().setOtherDefaultRate(
								assignEntity.getAssignmentTime().getOtherDefaultRate());
						
					}

					TimePostDao tpDao2 = (TimePostDao)context.getBean("timePostDao");
					if(LongUtility.hasValue(vo.getIncidentResourceId())){
						// fix stop times
						//tpDao2.fixStopTimes(vo.getIncidentResourceId(),null);
					}
					
					Collection<AssignmentTimePost> postings=tpDao2.getByAssignmentId(vo.getAssignmentId());
					Collection<AssignmentTimePostVo> atpVos = AssignmentTimePostVo.getInstances(postings, true);

					v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().setAssignmentTimePostVos(atpVos);
					
					if( (v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == null)
							|| (v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus() == null 
									|| v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()==AssignmentStatusTypeEnum.F
									|| v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()==AssignmentStatusTypeEnum.D
										)){
						invalidIrvos.add(v);
					}else{
						if(v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() != null
								&& v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.CONTRACTOR){
							invalidIrvos.add(v);
						}else
							irvos.add(v);
					}
					
				}else{
					IncidentResourceVo v = new IncidentResourceVo();
					v.setId(vo.getIncidentResourceId());
					v.setResourceVo(new ResourceVo());
					v.getResourceVo().setId(vo.getResourceId());
					v.getResourceVo().setResourceName(vo.getResourceName());
					v.getResourceVo().setLastName(vo.getLastName());
					v.getResourceVo().setFirstName(vo.getFirstName());
					v.setWorkPeriodVo(new WorkPeriodVo());
					
					WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
					WorkPeriod wpEntity=wpDao.getByIncidentResourceId(vo.getIncidentResourceId());
					
					if(null != wpEntity && null != wpEntity.getDefIncidentAccountCode() && null != wpEntity.getDefIncidentAccountCodeId()){
						IncidentAccountCodeVo avo = IncidentAccountCodeVo.getInstance(wpEntity.getDefIncidentAccountCode(),true);
						v.getWorkPeriodVo().setWpDefaultIncidentAccountCodeVo(avo);
					}else{ 
						//System.out.println("");
					}
					AssignmentDao aDao = (AssignmentDao)context.getBean("assignmentDao");
					Assignment assignEntity=aDao.getById(vo.getAssignmentId(), AssignmentImpl.class);
					
					v.getWorkPeriodVo().setCurrentAssignmentVo(new AssignmentVo());
					v.getWorkPeriodVo().getCurrentAssignmentVo().setId(vo.getAssignmentId());
					
					v.getWorkPeriodVo().getCurrentAssignmentVo().setKindVo(new KindVo());
					if(null != assignEntity){
						KindVo kindVo = new KindVo();
						kindVo.setId(assignEntity.getKind().getId());
						kindVo.setCode(assignEntity.getKind().getCode());
						kindVo.setDescription(assignEntity.getKind().getDescription());
						v.getWorkPeriodVo().getCurrentAssignmentVo().setKindVo(kindVo);
					}
					v.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentStatus(vo.getAssignmentStatus());
					v.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentStatusVo(new AssignmentStatusVo());
					v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().setCode(String.valueOf(vo.getAssignmentStatus()));
					v.getWorkPeriodVo().getCurrentAssignmentVo().setAssignmentTimeVo(new AssignmentTimeVo());
					v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().setId(vo.getAssignmentTimeId());
					invalidIrvos.add(v);
				}
			}
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("GET_ALL_CREW_MEMBERS");
		    coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(irvos);
			dialogueVo.setResultObjectAlternate(invalidIrvos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
/*		
		try{
		
			
			for(IncidentResourceGridVo vo : vos){
				DialogueVo dvo = this.getById(vo.getIncidentResourceId(), new DialogueVo());
				if(null != dvo.getResultObject()){
					IncidentResourceVo v =(IncidentResourceVo)(dvo.getResultObject());
					
					if( (v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == null)
							|| (v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus() == null 
									|| v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()==AssignmentStatusTypeEnum.F
									|| v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()==AssignmentStatusTypeEnum.D
										)){
						invalidIrvos.add(v);
					}else{
						if(v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() != null
								&& v.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.CONTRACTOR){
							invalidIrvos.add(v);
						}else
							irvos.add(v);
					}
				}
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("GET_ALL_CREW_MEMBERS");
		    coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(irvos);
			dialogueVo.setResultObjectAlternate(invalidIrvos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
	*/	
		return dialogueVo;
	}
	
	public DialogueVo getMaxPostingDate(Long irId, Boolean isCrew, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		Date maxDateParent=null;
		Date maxDateChild=null;
		
		try{
			Date maxDate=null;
			IncidentResource entity = this.incidentResourceDao.getById(irId, IncidentResourceImpl.class);
			Resource resource = null;
			if(null != entity)
				resource = entity.getResource();
			
			TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");

			if(null != resource){
				if(BooleanUtility.isFalse(isCrew)){
					maxDate=tpDao.getLatestTimePostingDateByResourceId(resource.getId());
				}else{
					maxDateParent=tpDao.getLatestTimePostingDateByResourceId(resource.getId());
					maxDateChild=tpDao.getLatestTimePostingDateForParentId(irId);
					
					if(BooleanUtility.isTrue(resource.isContracted())){
						if(null != maxDateParent)
							maxDate=maxDateParent;
					}else{
						if(!DateUtil.hasValue(maxDateChild)){
							maxDate=maxDateParent;
						}else{
							if(DateUtil.hasValue(maxDateParent)){
								if(maxDateParent.before(maxDateChild))
									maxDate=maxDateChild;
								else
									maxDate=maxDateParent;
							}else{
								if(DateUtil.hasValue(maxDateChild)){
									maxDate=maxDateChild;
								}
							}
						}
					}
					
					/* Dan:02/24/2017
					maxDate=tpDao.getLatestTimePostingDateByResourceId(resource.getId());
					if(CollectionUtility.hasValue(resource.getChildResources())){
						for(Resource r : resource.getChildResources()){
							Date maxChildDate=tpDao.getLatestTimePostingDateByResourceId(r.getId());
							if(null != maxChildDate){
								if(null == maxDate)
									maxDate=maxChildDate;
								else if(null != maxDate && null != maxChildDate){
									if(maxDate.before(maxChildDate))
										maxDate=maxChildDate;
								}
							}
						}
					}
					*/
				}
			}
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_MAX_POSTING_DATE");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(true);
			if(null != maxDate)
				dialogueVo.setResultObject(DateUtil.toDateString(maxDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));
			else
				dialogueVo.setResultObject(null);

			if(null != maxDateParent){
				dialogueVo.setResultObjectAlternate2(DateUtil.toDateString(maxDateParent,DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			if(null != maxDateChild){
				dialogueVo.setResultObjectAlternate3(DateUtil.toDateString(maxDateChild,DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}
			
			dialogueVo.setCourseOfActionVo(coa);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getIncidentGroupAccountCodes(DialogueVo dialogueVo, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			Collection<IncidentAccountCodeVo> vos = this.incidentResourceDao.getIncidentGroupAccountCodes(incidentGroupId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IG_ACCOUNT_CODES");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getIapResources(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			Collection<IncidentResourceGridVo> vos = this.incidentResourceDao.getIapResources(incidentId, incidentGroupId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IAP_RESOURCES");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}

	public DialogueVo clearAllCrewHireInfo(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentResourceId, "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();

			Resource parent = null;
			
			AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
			
			if(null == resource.getParentResource()){
				/*
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("MSG_FINISHED");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setMessageVo(new MessageVo("text.time"
						, "error.800000"
						, new String[]{"This feature is only available when a subordinate is selected."}
						, MessageTypeEnum.CRITICAL));
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
				*/
				parent=resource;
			}else{
				parent = resource.getParentResource();
			}

			Collection<AssignmentTime> entities = new ArrayList<AssignmentTime>();
			
			// update the primary record
			AssignmentTime atPrimary = AssignmentTimeVo.getAssignmentTime(resource.getIncidentResources().iterator().next());
			if(null != atPrimary){
				atPrimary.setHiringFax("");
				atPrimary.setHiringPhone("");
				atPrimary.setHiringUnitName("");
				entities.add(atPrimary);
			}
			
			/*
			 * Loop through all parent children
			 */
			for(Resource subordinate : parent.getChildResources()){
				AssignmentTime atSubordinate = AssignmentTimeVo.getAssignmentTime(subordinate.getIncidentResources().iterator().next());
				if(null != atSubordinate){
					atSubordinate.setHiringFax("");
					atSubordinate.setHiringPhone("");
					atSubordinate.setHiringUnitName("");
					entities.add(atSubordinate);
				}
			}

			if(CollectionUtility.hasValue(entities))
				atDao.saveAll(entities);
			
			incidentResourceDao.flushAndEvict(irEntity);
			irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			IncidentResourceVo irVo = IncidentResourceVo.getInstance(irEntity, true);

			// get grid vo and children grid vos
			IncidentResourceFilterImpl irFilter = new IncidentResourceFilterImpl();
			irFilter.setRosterParentId(parent.getId());
			irFilter.setIncidentId(irEntity.getIncidentId());
			Collection<IncidentResourceGridVo> updatedVos = new ArrayList<IncidentResourceGridVo>();
			DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
			if(CollectionUtility.hasValue(dvo2.getRecordset()))
				updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
			
			dialogueVo.setResultObjectAlternate(updatedVos);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("CLEAR_CREW_HIRE_INFO");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentResources", 
							   "info.0030" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(irVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo propagateCrewHireInfo(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(incidentResourceId, "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			// save parent address, phone, fax first
			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();
			
			Resource parent = null;

			if(null == resource.getParentResource()){
				parent=resource;
				/*
				CourseOfActionVo coaVo = new CourseOfActionVo();
			    coaVo.setCoaName("MSG_FINISHED");
			    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setMessageVo(new MessageVo("text.time"
						, "error.800000"
						, new String[]{"This feature is only available when a subordinate is selected."}
						, MessageTypeEnum.CRITICAL));
				dialogueVo.setCourseOfActionVo(coaVo);
				return dialogueVo;
				*/
			}else{
				parent = resource.getParentResource();
			}

			IncidentResource irParent = parent.getIncidentResources().iterator().next();
			
			/*
			 * Get the address from the selected incident resource
			 * 
			 * 6.0002 – Add/Edit Time Data for a Non-Contracted Person Resource at an Incident 
			 * Propagate Crew Address - This button displays when a Subordinate Resource is selected. 
			 * When a user clicks this button, the system must propagate the Address data for 
			 * the currently selected Subordinate Resource to all other Subordinate Resources rostered to the same parent 
			 * that do not have address data defined.
			 * NOTE: If there is Address data defined for the Resource, the system must not change that Address data.
			 */
			AssignmentTime atIncResource = AssignmentTimeVo.getAssignmentTime(irEntity);
			
			/*
			 * Only need to update if the selected resource actually has hiring info
			 */
			if(null != atIncResource && (
						StringUtility.hasValue(atIncResource.getHiringUnitName())
						||
						StringUtility.hasValue(atIncResource.getHiringFax())
						||
						StringUtility.hasValue(atIncResource.getHiringPhone())
					)){
				
				AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
				
				/*
				 * insert an assignment time record for any crew members for which
				 * a record does not exist
				 */
				Long atCount = incidentResourceDao.assignmentTimeRecordCount(parent.getId());
				if(atCount>0) {
					incidentResourceDao.insertAssignmentTime(parent.getId());
				}
								
				/*
				 * Loop through all parent children
				 */
				Collection<AssignmentTime> entities = new ArrayList<AssignmentTime>();
				for(Resource subordinate : parent.getChildResources()){
					AssignmentTime atSubordinate = AssignmentTimeVo.getAssignmentTime(subordinate.getIncidentResources().iterator().next());
					if(null != atSubordinate){
						if(!StringUtility.hasValue(atSubordinate.getHiringUnitName()) 
								&& !StringUtility.hasValue(atSubordinate.getHiringFax())
								&& !StringUtility.hasValue(atSubordinate.getHiringPhone())){

							atSubordinate.setHiringFax(atIncResource.getHiringFax());
							atSubordinate.setHiringPhone(atIncResource.getHiringPhone());
							atSubordinate.setHiringUnitName(atIncResource.getHiringUnitName());
							
							entities.add(atSubordinate);
						}else{
						}
					}
				}
				
				if(CollectionUtility.hasValue(entities))
					atDao.saveAll(entities);
			}else{
			}

			// get grid vo and children grid vos
			IncidentResourceFilterImpl irFilter = new IncidentResourceFilterImpl();
			irFilter.setRosterParentId(parent.getId());
			irFilter.setIncidentId(irEntity.getIncidentId());
			Collection<IncidentResourceGridVo> updatedVos = new ArrayList<IncidentResourceGridVo>();
			DialogueVo dvo2 = this.getGrid(irFilter, null, new DialogueVo());
			if(CollectionUtility.hasValue(dvo2.getRecordset()))
				updatedVos.addAll((Collection<IncidentResourceGridVo>)(dvo2.getRecordset()));
			
			dialogueVo.setResultObjectAlternate(updatedVos);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("PROPAGATE_CREW_HIRE_INFO");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentResources", 
							   "info.0030" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
}