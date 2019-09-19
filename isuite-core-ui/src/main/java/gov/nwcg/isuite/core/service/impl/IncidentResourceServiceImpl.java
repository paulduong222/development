package gov.nwcg.isuite.core.service.impl;

import edu.emory.mathcs.backport.java.util.Collections;
import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AddressImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorPaymentInfoImpl;
import gov.nwcg.isuite.core.domain.impl.CountrySubdivisionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceKindImpl;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.filter.ContractorAgreementFilter;
import gov.nwcg.isuite.core.filter.ContractorFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.filter.IncidentResourceGetFilter;
import gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.AssignmentTimeDao;
import gov.nwcg.isuite.core.persistence.ContractorAgreementDao;
import gov.nwcg.isuite.core.persistence.ContractorDao;
import gov.nwcg.isuite.core.persistence.ContractorPaymentInfoDao;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.persistence.WorkAreaDao;
import gov.nwcg.isuite.core.persistence.WorkPeriodDao;
import gov.nwcg.isuite.core.reports.AllIncidentResourcesReport;
import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.rules.IncidentResourceDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentResourceRosterPropagationHandler;
import gov.nwcg.isuite.core.rules.IncidentResourceSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentResourceTestDialogueHandler;
import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.service.IncidentResourceService;
import gov.nwcg.isuite.core.vo.AddressVo;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.ContractorAgreementGridVo;
import gov.nwcg.isuite.core.vo.ContractorGridVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCheckInDemobVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCommonVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceInWorkVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.WorkPeriodOvernightStayInfoVo;
import gov.nwcg.isuite.core.vo.WorkPeriodQuestionValueVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.AbstractRuleHandler;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.RequestNumberUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;

public class IncidentResourceServiceImpl extends BaseService implements IncidentResourceService {
	protected IncidentResourceDao incidentResourceDao;
	protected ResourceDao resourceDao;

	public IncidentResourceServiceImpl(){
		super();
	}

	public void initialization(){
		incidentResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		resourceDao = (ResourceDao)context.getBean("resourceDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getGrid2(gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl)
	 */
	public DialogueVo getGrid2(DialogueVo dialogVo, IncidentResourceFilter incidentResourceFilter, Collection<String> sortFields) throws ServiceException {
		
		Collection<IncidentResourceGridVo> vos = new ArrayList<IncidentResourceGridVo>();
		Collection<IncidentResourceGridVo> rtnVos=new ArrayList<IncidentResourceGridVo>();

		try{
			if(null == dialogVo)dialogVo=new DialogueVo();
			
			vos=incidentResourceDao.getGrid(incidentResourceFilter, sortFields);

			super.log("Get Grid returned count: " + vos.size(), Level.INFO);

			if(null != incidentResourceFilter.getCheckboxFilter() && incidentResourceFilter.getCheckboxFilter().isPersonnel()) {
				// when personnel filter is set, 
				// return vos as a single view (no tree structure)
				rtnVos=vos;
			} else {
				rtnVos = IncidentResourceGridVo.toHierarchyCollection(vos,null);
			}
			
			//TODO: Add additional criteria to determine if resource is deletable such as:
			// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
			// Keep consistent with deletable criteria outlined in IncidentResourceDaoHibernate.java-->getGrid method.
			for(IncidentResourceGridVo irgVo : rtnVos) {
				//System.out.println(irgVo.getResourceName() + " " + irgVo.getFirstName() + " " + irgVo.getLastName());
				//if(null == irgVo.getIncidentName()) {
				if(irgVo.getAssignmentStatus()== AssignmentStatusTypeEnum.NA ||
						irgVo.getAssignmentStatus()== AssignmentStatusTypeEnum.D ||
						irgVo.getAssignmentStatus()== AssignmentStatusTypeEnum.R ||
						irgVo.getAssignmentStatus()== null) {
					irgVo.setDeletable(true);
				} else {
					irgVo.setDeletable(false);
				}
			}
			
			CourseOfActionVo coa = new CourseOfActionVo();
			dialogVo.setCourseOfActionVo(coa);
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);
			dialogVo.setRecordset(rtnVos);
			return dialogVo;
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getGrid(gov.nwcg.isuite.core.filter.impl.IncidentResourceFilterImpl)
	 */
	public Collection<IncidentResourceGridVo> getGrid(IncidentResourceFilter filter) throws ServiceException {
		Collection<IncidentResourceGridVo> vos = new ArrayList<IncidentResourceGridVo>();
		Collection<IncidentResourceGridVo> rtnVos=new ArrayList<IncidentResourceGridVo>();

		try{
			vos=incidentResourceDao.getGrid(filter, null);

			super.log("Get Grid returned count: " + vos.size(), Level.INFO);

//			if(!filter.getPersonnel()){
//				rtnVos = IncidentResourceGridVo.toHierarchyCollection(vos,null);
//			}else{
//				// when personnel filter is set, 
//				// return vos as a single view (no tree structure)
//				rtnVos=vos;
//			}
			
			//TODO: Add additional criteria to determine if resource is deletable such as:
			// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
			// Keep consistent with deletable criteria outlined in IncidentResourceDaoHibernate.java-->getGrid method.
			for(IncidentResourceGridVo irgVo : rtnVos) {
				//System.out.println(irgVo.getResourceName() + " " + irgVo.getFirstName() + " " + irgVo.getLastName());
				//if(null == irgVo.getIncidentName()) {
				if(irgVo.getAssignmentStatus()== AssignmentStatusTypeEnum.NA ||
						irgVo.getAssignmentStatus()== AssignmentStatusTypeEnum.D ||
						irgVo.getAssignmentStatus()== AssignmentStatusTypeEnum.R ||
						irgVo.getAssignmentStatus()== null) {
					irgVo.setDeletable(true);
				} else {
					irgVo.setDeletable(false);
				}
			}
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}

		return rtnVos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getGridItem(java.lang.Long)
	 */
	public IncidentResourceGridVo getGridItem(IncidentResourceFilter filter) throws ServiceException {
		Collection<IncidentResourceGridVo> vos = new ArrayList<IncidentResourceGridVo>();

		try{
			vos=incidentResourceDao.getGrid(filter, null);

			if((null!=vos) && (vos.size()>0))
				return vos.iterator().next();
		}catch(Exception e){
			throw new ServiceException(e);
		}

		return null;
	}

	public IncidentResourceVo getById(Long id) throws ServiceException {
		return this.getById(id,null);
	}

	public IncidentResourceVo getById(Long id, Long assignmentId) throws ServiceException {

		try{
			IncidentResource entity = incidentResourceDao.getById(id, IncidentResourceImpl.class);

			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			Incident incidentEntity = incidentDao.getById(entity.getIncidentId());
			
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
			return vo;
		}catch(Exception e ){
			super.handleException(e);
		}
 
		return null;
	}

	public Collection<IncidentResourceVo> getAllByFilter(Collection<IncidentResourceGetFilter> filter) throws ServiceException {
		Collection<IncidentResourceVo> vos = new ArrayList<IncidentResourceVo>();
		try {
		
			for(IncidentResourceGetFilter f : filter){
				IncidentResourceVo vo =  this.getById(f.getId(),f.getAssignmentId());
				vos.add(vo);
			}
			
			for(IncidentResourceVo irvo : vos) {
				Collections.sort((List<WorkPeriodQuestionValueVo>)irvo.getWorkPeriodVo().getCheckInQuestions(), new QuestionPositionComparator());
				Collections.sort((List<WorkPeriodQuestionValueVo>)irvo.getWorkPeriodVo().getAirTravelQuestions(), new QuestionPositionComparator());
			}
			
			return vos;
			
		} catch(Exception e) {
			super.handleException(e);
		}
		
		return null;
	}
	
	public Collection<IncidentResourceVo> getAllById(Collection<Integer> ids) throws ServiceException {
		
		Collection<IncidentResourceVo> vos = new ArrayList<IncidentResourceVo>();
		try {
		
			for(Integer id : ids) {
				IncidentResourceVo vo = this.getById(new Long(id));
				vos.add(vo);
			}
			
			for(IncidentResourceVo irvo : vos) {
				Collections.sort((List<WorkPeriodQuestionValueVo>)irvo.getWorkPeriodVo().getCheckInQuestions(), new QuestionPositionComparator());
				Collections.sort((List<WorkPeriodQuestionValueVo>)irvo.getWorkPeriodVo().getAirTravelQuestions(), new QuestionPositionComparator());
			}
			
			return vos;
			
		} catch(Exception e) {
			super.handleException(e);
		}
		
		return null;
	}

	class QuestionPositionComparator implements Comparator<WorkPeriodQuestionValueVo>{

		public int compare(WorkPeriodQuestionValueVo vo1, WorkPeriodQuestionValueVo vo2){

			Integer vo1Position = vo1.getIncidentQuestionVo().getPosition();
			Integer vo2Position = vo2.getIncidentQuestionVo().getPosition();

			Integer result = 0;
			if(vo1Position != null && vo2Position != null) {
				result = vo1Position.compareTo(vo2Position);
			}
			
			return result;
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getIncidentResourceCheckInDemobData(java.lang.Long)
	 */
	public IncidentResourceCheckInDemobVo getIncidentResourceCheckInDemobData(Long incidentResourceId) throws ServiceException {
		try{
			IncidentResourceCheckInDemobVo vo = this.incidentResourceDao.getIncidentResourceCheckInDemobData(incidentResourceId);
			return vo;
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getIncidentResourceCommonVo(java.lang.Long, java.lang.Long)
	 */
	public IncidentResourceCommonVo getIncidentResourceCommonData(Long resourceId, Long assignmentId) throws ServiceException{
		try{
			IncidentResourceCommonVo vo = this.incidentResourceDao.getIncidentResourceCommonData(resourceId,assignmentId);
			return vo;
		}catch(PersistenceException e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#disableResources(java.util.Collection, java.lang.Boolean)
	 */
//	public void disableResources(Collection<ResourceVo> resources, Boolean removeParentAssociations) throws ServiceException {
//		if( (null != resources) && (resources.size()>0)){
//			// Build a collection of resource ids
//			Collection<Long> resourceIds = new ArrayList<Long>();
//			for(ResourceVo vo : resources){
//				resourceIds.add(vo.getId());
//			}
//
//			try{
//				int count=this.incidentResourceDao.disableResources(resourceIds, removeParentAssociations);
//				if(count==0)
//					throw new ServiceException("Unable to disable resources, count returned 0.");
//			}catch(PersistenceException pe){
//				throw new ServiceException(pe);
//			}
//
//		}else{
//			throw new ServiceException("Unable to disable resources with empty resources collection.");
//		}
//	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#disableResources(java.util.Collection, java.lang.Boolean)
	 */
	public void disableResources(Collection<Integer> resourceIds, Boolean removeParentAssociations) throws ServiceException {
		
		try {
			
			Collection<Long> longResourceIds = new ArrayList<Long>();
			for(Integer id : resourceIds){
				longResourceIds.add(Long.parseLong(id.toString()));
			}
			
			int count=this.incidentResourceDao.disableResources(longResourceIds, removeParentAssociations);
			if(count==0)
				throw new ServiceException("Unable to disable resources, count returned 0.");
			
		} catch(PersistenceException pe) {
			throw new ServiceException(pe);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#enableResources(java.util.Collection)
	 */	
//	public void enableResources(Collection<ResourceVo> resources) throws ServiceException {
//		if( (null != resources) && (resources.size()>0)){
//			// Build a collection of resource ids
//			Collection<Long> resourceIds = new ArrayList<Long>();
//			for(ResourceVo vo : resources){
//				resourceIds.add(vo.getId());
//			}
//
//			try{
//				int count=this.incidentResourceDao.enableResources(resourceIds);
//				if(count==0)
//					throw new ServiceException("Unable to enable resources, count returned 0.");
//			}catch(PersistenceException pe){
//				throw new ServiceException(pe);
//			}
//
//		}else{
//			throw new ServiceException("Unable to enable resources with empty resources collection.");
//		}
//	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#enableResources(java.util.Collection)
	 */	
	public void enableResources(Collection<Integer> resourceIds) throws ServiceException {

		try{
			
			Collection<Long> longResourceIds = new ArrayList<Long>();
			for(Integer id : resourceIds){
				longResourceIds.add(Long.parseLong(id.toString()));
			}	
			
			int count=this.incidentResourceDao.enableResources(longResourceIds);
			
			if(count==0)
				throw new ServiceException("Unable to enable resources, count returned 0.");
			
		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}
	}

	public DialogueVo save2(IncidentResourceVo vo, Boolean propagateToChildren, Boolean addResourceToWorkArea, Long workAreaId, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			IncidentResource irEntity = null;
			if(LongUtility.hasValue(vo.getId())){
				irEntity = incidentResourceDao.getById(vo.getId(), IncidentResourceImpl.class);
			}
			
			IncidentResourceSaveRulesHandler rulesHandler = new IncidentResourceSaveRulesHandler(context);

//			if(rulesHandler.execute(vo, workAreaId, irEntity, dialogueVo)==AbstractRuleHandler._OK){
			if(rulesHandler.execute(vo, irEntity, dialogueVo)==AbstractRuleHandler._OK){
				
				Resource cpyEntity = null;
				
				if(addResourceToWorkArea == true && !LongUtility.hasValue(vo.getResourceVo().getPermanentResourceId())) {
					
					//create an original resource
					Resource resourceEntity = ResourceVo.toEntity(null, vo.getResourceVo(), true);
					resourceEntity.setPermanent(Boolean.TRUE);
					resourceEntity.setId(null);
					Collection<ResourceKind> newRks = new ArrayList<ResourceKind>();
					for(ResourceKind rk : resourceEntity.getResourceKinds()){
						rk.setId(null);
						newRks.add(rk);
					}
					resourceEntity.getResourceKinds().clear();
					resourceEntity.getResourceKinds().addAll(newRks);
					
					// Set assignment item code as primary qual for resourceEntity 
					KindVo kvo = IncidentResourceVo.extractItemCode(vo);
					if(null != kvo){
						ResourceKind rk = new ResourceKindImpl();
						rk.setKind(KindVo.toEntity(null, kvo, false));
						rk.setResource(resourceEntity);
						rk.setPrimary(Boolean.TRUE);
						resourceEntity.getResourceKinds().add(rk);
					}
					
					//Set the Employment Type if it is available.
					resourceEntity.setEmploymentType(IncidentResourceVo.extractEmploymentType(vo));
					
					//create a copy of the original 
					cpyEntity = ResourceVo.toEntity(null, vo.getResourceVo(), true);
					//set the copy's permanent resource
					cpyEntity.setPermanentResource(resourceEntity);
					cpyEntity.setId(null);
					
					Collection<ResourceKind> newRks2 = new ArrayList<ResourceKind>();
					for(ResourceKind rk : cpyEntity.getResourceKinds()){
						rk.setId(null);
						newRks2.add(rk);
					}
					
					cpyEntity.getResourceKinds().clear();
					cpyEntity.getResourceKinds().addAll(newRks2);

					// Set assignment item code as primary qual for cpyEntity
					kvo = IncidentResourceVo.extractItemCode(vo);
					if(null != kvo){
						ResourceKind rk = new ResourceKindImpl();
						rk.setKind(KindVo.toEntity(null, kvo, false));
						rk.setResource(cpyEntity);
						rk.setPrimary(Boolean.TRUE);
						cpyEntity.getResourceKinds().add(rk);
					}
					
					//Set the copy's Employment Type if it is available.
					cpyEntity.setEmploymentType(IncidentResourceVo.extractEmploymentType(vo));
					
					//save the copy
					resourceDao.save(cpyEntity);
				}
				
				Resource resource = null;
				ResourceData origResourceData = null;

				RequestNumberUtil.formatRequestNumber(vo);
				
				/*
				 * If the resourceId is available, load the resource instance
				 * If the resourceId not available, must be a new resource being added to incident
				 */
				if( (null != vo.getResourceVo()) && (vo.getResourceVo().getId() > 0L)){
					resource=resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
					origResourceData = new ResourceData(resource);
					IncidentResource ir = resource.getIncidentResources().iterator().next();
					origResourceData.iacId=ir.getWorkPeriod().getDefIncidentAccountCodeId();
				}
				
				// populate the entity
				resource = IncidentResourceVo.toResourceEntity(resource,vo, cpyEntity);
				
				// save the entity
				resourceDao.save(resource);
				
				//flushing is good practice when the entity needs to be returned immediately
				resourceDao.flushAndEvict(resource);

				// execute post process actions
				rulesHandler.executeProcessedActions(vo, origResourceData, irEntity,dialogueVo);
				
				resource  = resourceDao.getById(resource.getId(), ResourceImpl.class);

				IncidentResourceVo irVo = null;
				if(null != resource.getIncidentResources() && resource.getIncidentResources().size() > 0) {
					IncidentResource ir = resource.getIncidentResources().iterator().next();
					resourceDao.flushAndEvict(resource);
					incidentResourceDao.flushAndEvict(ir);
					irVo = this.getById(ir.getId()); 
				} 

				/*
				 * Get the incidentResourceGridVo for this resource
				 */
				IncidentResourceFilter irFilter = new IncidentResourceFilterImpl();
				irFilter.setIncidentResourceId(irVo.getId());
				Collection<IncidentResourceGridVo> irgVos = incidentResourceDao.getGrid(irFilter, null);
				IncidentResourceGridVo irgVo = null;
				if(CollectionUtility.hasValue(irgVos))
					irgVo = irgVos.iterator().next();
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentResources", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(irVo);
				dialogueVo.setResultObjectAlternate(irgVo);
				
				return dialogueVo;
				
			}else{
				return dialogueVo;
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#save(gov.nwcg.isuite.core.vo.IncidentResourceVo, java.lang.Boolean, java.lang.Long)
	 */
	public IncidentResourceVo save(IncidentResourceVo vo, Boolean propagateToChildren, Boolean addResourceToWorkArea, Long workAreaId) throws ServiceException {
		
		try{
			Resource cpyEntity = null;
			
			if(addResourceToWorkArea == true) {
				//create an original resource
				Resource resourceEntity = ResourceVo.toEntity(null, vo.getResourceVo(), true);
				resourceEntity.setPermanent(Boolean.TRUE);
				resourceEntity.setId(null);
				
				//create a copy of the original 
				cpyEntity = ResourceVo.toEntity(null, vo.getResourceVo(), true);
				//set the copy's permanent resource
				cpyEntity.setPermanentResource(resourceEntity);
				cpyEntity.setId(null);
				
				//save the copy
				resourceDao.save(cpyEntity);
			}
			
			Resource resource = null;
			ResourceData origResourceData = null;
			
			RequestNumberUtil.formatRequestNumber(vo);
			
			/*
			 * If the resourceId is available, load the resource instance
			 * If the resourceId not available, must be a new resource being added to incident
			 */
			if( (null != vo.getResourceVo()) && (vo.getResourceVo().getId() > 0L)){
				resource=resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);
				origResourceData = new ResourceData(resource);
			}
			
			// populate the entity
			resource = IncidentResourceVo.toResourceEntity(resource,vo, cpyEntity);
			
			/*
			 * if resource is a strike team component and status is demob,
			 * then remove from parent
			 */
			if(null != resource.getParentResource()){
				if(resource.getParentResource().getResourceClassification()==ResourceClassificationEnum.ST
						||
						resource.getParentResource().getResourceClassification()==ResourceClassificationEnum.TF						
				){
					if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode().equals("D")){
						resource.setParentResource(null);
						resource.setComponent(false);
						resource.setLeaderType(new Integer(99));
					}
				}
					
			}
			
			
			// save the entity
			resourceDao.save(resource);
			
			//flushing is good practice when the entity needs to be returned immediately
			resourceDao.flushAndEvict(resource);

			if(null != resource.getLeaderType() && resource.getLeaderType().intValue() > 0 ){

				if(resource.getLeaderType().intValue() == 1){
					// update any existing primary's as 99
					if(null != resource.getParentResourceId() && resource.getParentResourceId() > 0)
						resourceDao.removeParentResourceLeader(resource.getParentResourceId(), new Integer(1), resource.getId());
				}else if(resource.getLeaderType().intValue() == 2){
					// update any existing secondary's as 99
					if(null != resource.getParentResourceId() && resource.getParentResourceId() > 0)
						resourceDao.removeParentResourceLeader(resource.getParentResourceId(), new Integer(2), resource.getId());
				}
				resourceDao.flushAndEvict(resource);
			}
			
			resource  = resourceDao.getById(resource.getId(), ResourceImpl.class);
			
			if(propagateToChildren){
				WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
				AssignmentDao aDao = (AssignmentDao)context.getBean("assignmentDao");

				IncidentResource irParent = resource.getIncidentResources().iterator().next();

				Collection<Long> ids = new ArrayList<Long>();
				for(Resource subResource : resource.getChildResources()){
					ids.add(subResource.getId());
				}
				for(Long id : ids){
					Resource subResource = resourceDao.getById(id, ResourceImpl.class);
					
					if(null != subResource.getIncidentResources()){
						IncidentResource irChild = subResource.getIncidentResources().iterator().next();
						
						IncidentResourceRosterPropagationHandler.propagateParentChanges(origResourceData,irParent.getResource(), irChild.getResource());

						/*
						 * if parent is strike team and status is demob,
						 * then remove children from parent
						 */
						if( irParent.getResource().getResourceClassification()==ResourceClassificationEnum.ST
								||
								irParent.getResource().getResourceClassification()==ResourceClassificationEnum.TF){
					
							if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode().equals("D")){
								irChild.getResource().setParentResource(null);
								irChild.getResource().setComponent(false);
								irChild.getResource().setLeaderType(new Integer(99));
							}
						}
						
						resourceDao.save(irChild.getResource());
						resourceDao.flushAndEvict(irChild);
						irChild = incidentResourceDao.getById(irChild.getId(), IncidentResourceImpl.class);

						AssignmentDao adao = (AssignmentDao)context.getBean("assignmentDao");
						adao.updateStatus(irParent.getResourceId(), vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());

						/*
						if(null != irChild.getWorkPeriod() && (null != irChild.getWorkPeriod().getAssignments() && irChild.getWorkPeriod().getAssignments().size()>0 )){
							for(Assignment assignment : irChild.getWorkPeriod().getAssignments()){
								if(null == assignment.getEndDate()){
									
									if(null != assignment.getAssignmentStatus() 
										&& assignment.getAssignmentStatus() != AssignmentStatusTypeEnum.D){
										if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus()){
											
											assignment.setAssignmentStatus(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatus());
											aDao.save(assignment);
											aDao.flushAndEvict(assignment);
										}
									}
								}
							}
						}
						*/
					}
				}
			}
			
			if(null != resource.getIncidentResources() && resource.getIncidentResources().size() > 0) {
				IncidentResource ir = resource.getIncidentResources().iterator().next();
				incidentResourceDao.flushAndEvict(ir);
				IncidentResourceVo irVo = this.getById(ir.getId()); 
				return irVo;
			} else {
				return null;
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#saveResource(gov.nwcg.isuite.core.vo.IncidentResourceInWorkVo)
	 */
	public void saveIncidentResource(IncidentResourceInWorkVo vo) throws ServiceException {

		try{
			Resource resource = null;

			/*
			 * If the resourceId is available, load the resource instance
			 * If the resourceId not available, must be a new resource being added to incident
			 */
			if( (null != vo.getIncidentResourceCommonVo().getResourceId()) && (vo.getIncidentResourceCommonVo().getResourceId() > 0L)){
				resource=resourceDao.getById(vo.getIncidentResourceCommonVo().getResourceId(), ResourceImpl.class);
			}

			// populate the entity
			resource = IncidentResourceInWorkVo.toEntity(resource, vo);

			// save the entity
			resourceDao.save(resource);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#saveResources(java.util.Collection)
	 */
	public void saveIncidentResources(Collection<IncidentResourceInWorkVo> vos) throws ServiceException{

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#generateAllIncidentResourcesReport(gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter)
	 */
	public String generateAllIncidentResourcesReport(AllIncidentResourcesReportFilter filter) throws ServiceException {

		try{
			/*
			 * TODO: Discuss the need to have isw_report table to hold
			 * information such as (reportname,daterequested,dategenerated,result,error,etc..).
			 */

			/*
			 * Get the collection report data objects needed for the AllIncidentResourcesReport.
			 */
			Collection<AllIncidentResourcesReportData> reportData = incidentResourceDao.getAllIncidentResourcesReportData(filter);

			/*
			 * get the incident name id key from the first object in the collection.
			 * the incident name id key will be used as the subtitle for the report
			 */
//			String subTitle="";
//			if( (null!=reportData) && (reportData.size()>0)){
//				AllIncidentResourcesReportData data = reportData.iterator().next();
//				subTitle=data.getIncidentName()+" " + data.getIncidentUnit() + "-" +data.getIncidentNumber();
//			}

			/*
			 * Instantiate the report controller object.
			 */
			IReport report = new AllIncidentResourcesReport(reportData);

			/*
			 * Instantiate a report builder
			 */
			ReportBuilder2 reportBuilder = new ReportBuilder2();

			/*
			 * Create the pdf report. Set the name of the output file.
			 * TODO: discuss the output buffer area and output file naming
			 */
			reportBuilder.applicationContext=super.context;
			return reportBuilder.createPdfReport(report, "allincidentresources.pdf");

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ReportException re){
			throw new ServiceException(re);
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#addNewResourceRoster(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.ResourceVo)
	 */
	public void addNewResourceRoster(Long workAreaId,Long parentResourceId, ResourceVo vo) throws ServiceException {
		try{
			
			Resource parent = resourceDao.getById(parentResourceId, ResourceImpl.class);

			Resource child = ResourceVo.toEntity(null, vo, true);
			child.setEnabled(true);
			
			// check if parent is ST or TF, if so set resource.component flag
			if(null != parent.getResourceClassification() 
					&&
				(parent.getResourceClassification().name().equals("ST") 
						||
				 parent.getResourceClassification().name().equals("TF"))){
				child.setComponent(true);
			}else{
				child.setComponent(false);
			}
			
			//child.setResourceClassification(ResourceClassificationEnum.C);
			child.setResourceClassification(null);
			
			// save an original
			resourceDao.save(child);
			
			Resource childCopy = ResourceVo.toEntity(null, vo, true);
			childCopy.setEnabled(true);
			childCopy.setPermanentResource(child);
			childCopy.setParentResource(parent);
			
			resourceDao.save(childCopy);
			
			WorkAreaDao workAreaDao = (WorkAreaDao)context.getBean("workAreaDao");
			workAreaDao.addResourceToWorkArea(workAreaId, childCopy.getId());
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getAvailableIncidentResourcesForRoster(gov.nwcg.isuite.core.filter.IncidentResourceFilter)
	 */
	public Collection<IncidentResourceGridVo> getAvailableIncidentResourcesForRoster(IncidentResourceFilter filter) throws ServiceException {
		try{
			filter.setRosterParentId(filter.getResourceId());
			
			Collection<IncidentResourceGridVo> vos = incidentResourceDao.getAvailableResourcesForRoster(filter);
			return vos;
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	public Collection<IncidentResourceGridVo> getAvailableWorkAreaResourcesForRoster(IncidentResourceFilter filter) throws ServiceException {
		try{
			return incidentResourceDao.getAvailableWorkAreaResourcesForRoster(filter);
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getRosterResourceItem(java.lang.Long, java.lang.Long)
	 */
	public Collection<IncidentResourceGridVo> getRosterResourceItem(Long workAreaId, Long parentResourceId) throws ServiceException {
		Collection<IncidentResourceGridVo> vos = new ArrayList<IncidentResourceGridVo>();
		Collection<IncidentResourceGridVo> rtnVos=new ArrayList<IncidentResourceGridVo>();
		
	
		try{
			IncidentResourceFilter filter = new IncidentResourceFilterImpl();
			filter.setWorkAreaId(workAreaId);
			filter.setResourceId(parentResourceId);
			
			vos=incidentResourceDao.getRosterResourceGrid(filter);
			
			if(vos.size()==1){
				return vos;
			}
			rtnVos = IncidentResourceGridVo.toHierarchyCollection(vos,parentResourceId);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return rtnVos;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#rosterIncidentResources(java.lang.Long, java.util.Collection)
	 */
	public void rosterIncidentResources(Long parentResourceId, Collection<Integer> childIds) throws ServiceException {

		try{
			Resource parent = resourceDao.getById(parentResourceId, ResourceImpl.class);

			Collection<Long> childrenIds = new ArrayList<Long>();
			Iterator iter = childIds.iterator();
			
			while(iter.hasNext()){
				String s = String.valueOf(iter.next());
				Long l = TypeConverter.convertToLong(s);
				childrenIds.add(l);
			}

			Collection<Resource> children=resourceDao.getAllByIds(childrenIds);

			if(null == parent.getChildResources())
				parent.setChildResources(new ArrayList<Resource>());

			Collection<Resource> childrenToAdd = new ArrayList<Resource>();
			
			for(Resource child : children){
				IncidentResource irParent = parent.getIncidentResources().iterator().next();
				IncidentResource irChild = child.getIncidentResources().iterator().next();
				Collection<Assignment> newChildAssignments=new ArrayList<Assignment>();
				
				for(Assignment childAssignment : irChild.getWorkPeriod().getAssignments()){
					if(null == childAssignment.getEndDate()){
						for(Assignment parentAssignment : irParent.getWorkPeriod().getAssignments()){
							if(null == parentAssignment.getEndDate()){
								childAssignment.setAssignmentStatus(parentAssignment.getAssignmentStatus());
								newChildAssignments.add(childAssignment);
							}
						}
					}else
						newChildAssignments.add(childAssignment);
				}

				irChild.getWorkPeriod().getAssignments().clear();
				irChild.getWorkPeriod().setAssignments(newChildAssignments);
				
				child.getIncidentResources().clear();
				child.getIncidentResources().add(irChild);
				
				child.setParentResource(parent);
				if(null!=child.getResourceClassification()
						&&
					(child.getResourceClassification().name().equals("ST")
							||
					 child.getResourceClassification().name().equals("TF")							
					) )
				{
					// do nothing
				}else{
					
					//child.setResourceClassification(ResourceClassificationEnum.C);
					child.setResourceClassification(null);
				}
				// check if parent is ST or TF, if so set resource.component flag
				if(null != parent.getResourceClassification() 
						&&
					(parent.getResourceClassification().name().equals("ST") 
							||
					 parent.getResourceClassification().name().equals("TF"))){
					child.setComponent(true);
				}else{
					child.setComponent(false);
				}
				
				childrenToAdd.add(child);
			}
			
			parent.getChildResources().addAll(childrenToAdd);
			 
			resourceDao.save(parent);

			resourceDao.flushAndEvict(parent);
			
			parent=resourceDao.getById(parent.getId(), ResourceImpl.class);

			// update new rostered children's status to parent's status
			
		}catch(Exception e){
			super.handleException(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#rosterWorkAreaResources(java.lang.Long, java.util.Collection)
	 */
	public void rosterWorkAreaResources(Long parentResourceId, Long incidentId,Collection<Integer> childIds) throws ServiceException {

		try{
			if(null==parentResourceId){
				throw new Exception("Unable to roster with unknown parent resource");
			}
			Resource parentResource = resourceDao.getById(parentResourceId, ResourceImpl.class);
			
			Collection<Long> copyIds = IntegerUtility.convertToLongs(childIds);
			
			Collection<Resource> resourceEntities= new ArrayList<Resource>();

			for(Long id : copyIds){
				Resource waResource = resourceDao.getById(id, ResourceImpl.class);
				Resource resEntity= this.copyResource(waResource);
				
				// check if parent is ST or TF, if so set resource.component flag
				if(null != parentResource.getResourceClassification() 
						&&
					(parentResource.getResourceClassification().name().equals("ST") 
							||
					 parentResource.getResourceClassification().name().equals("TF"))){
					resEntity.setComponent(true);
				}else{
					resEntity.setComponent(false);
				}
				
				parentResource.getChildResources().add(resEntity);
				resEntity.setParentResource(parentResource);
				//resEntity.setResourceClassification(ResourceClassificationEnum.C);
				resEntity.setResourceClassification(null);
				
				Incident incEntity = new IncidentImpl();
				incEntity.setId(incidentId);
				
				IncidentResource irEntity = new IncidentResourceImpl();
				irEntity.setIncident(incEntity);
				irEntity.setResource(resEntity);
				irEntity.setNameAtIncident((resEntity.isPerson() ? (resEntity.getLastName() + ", " + resEntity.getFirstName() ) : resEntity.getResourceName()));

				Assignment a = new AssignmentImpl();
				a.setAssignmentStatus(AssignmentStatusTypeEnum.F);
				
				if(null != waResource.getResourceKinds() && waResource.getResourceKinds().size() > 0){
					if(waResource.isPerson()){
						for(ResourceKind rk : waResource.getResourceKinds()){
							if(rk.getPrimary()){
								a.setKind(rk.getKind());
								a.setTraining(rk.getTraining());
							}
						}
					}else{
						ResourceKind rk = waResource.getResourceKinds().iterator().next();
						a.setKind(rk.getKind());
						a.setTraining(rk.getTraining());
					}
				}

				AssignmentTime atEntity = new AssignmentTimeImpl();
				atEntity.setEmploymentType(resEntity.getEmploymentType());
				atEntity.setAssignment(a);
				a.setAssignmentTime(atEntity);
				
				ContractorPaymentInfo cpiEntity = new ContractorPaymentInfoImpl();
				cpiEntity.setContractor(resEntity.getContractor());
				cpiEntity.setContractorAgreement(resEntity.getContractorAgreement());
				cpiEntity.setAssignmentTime(atEntity);
				atEntity.setContractorPaymentInfo(cpiEntity);
				
				WorkPeriod wp = new WorkPeriodImpl();
				wp.setAssignments(new ArrayList<Assignment>());
				wp.getAssignments().add(a);
				wp.setIncidentResource(irEntity);
				irEntity.setWorkPeriod(wp);
				
				resEntity.getIncidentResources().add(irEntity);
				
				
				resourceEntities.add(resEntity);
			}
			
			resourceDao.saveAll(resourceEntities);
			
		}catch(Exception e){
			super.handleException(e);
		}
		
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#saveAndRosterNewResource(java.lang.Long, gov.nwcg.isuite.core.vo.IncidentResourceVo)
	 */
	public IncidentResourceVo saveAndRosterNewResource(Long parentResourceId, IncidentResourceVo vo) throws ServiceException {

		try{
			Resource resource = null;

			RequestNumberUtil.formatRequestNumber(vo);

			// populate the entity
			resource = IncidentResourceVo.toResourceEntity(resource,vo);

			Resource parentResource = resourceDao.getById(parentResourceId, ResourceImpl.class);
			if(null != parentResource.getIncidentResources() && parentResource.getIncidentResources().size() > 0) {
				IncidentResource ir = parentResource.getIncidentResources().iterator().next();
				if(ir.getIncidentId().compareTo(vo.getIncidentVo().getId()) != 0){
					throw new ServiceException("The new Roster Resource's Incident must be the same as the Parent Resource's Incident.");
				}
			}

			// check if childvo has leader set
			if(null != vo.getResourceVo().getLeaderType() && vo.getResourceVo().getLeaderType().intValue() > 0 ){

				if(vo.getResourceVo().getLeaderType().intValue() == 1){
					// update any existing primary's as 99
					resourceDao.removeParentResourceLeader(parentResourceId, new Integer(1),null);
				}else if(vo.getResourceVo().getLeaderType().intValue() == 2){
					// update any existing secondary's as 99
					resourceDao.removeParentResourceLeader(parentResourceId, new Integer(2),null);
				}
			}
			
			// check if parent is ST or TF, if so set resource.component flag
			if(null != parentResource.getResourceClassification() 
					&&
				(parentResource.getResourceClassification().name().equals("ST") 
						||
						parentResource.getResourceClassification().name().equals("TF"))){
				resource.setComponent(true);
			}else{
				resource.setComponent(false);
			}
			
			resource.setParentResource(parentResource);

			// save the entity
			resourceDao.save(resource);
			
			resourceDao.flushAndEvict(resource);

			resource = resourceDao.getById(resource.getId(), ResourceImpl.class);
			
			if(null != resource.getIncidentResources() && resource.getIncidentResources().size() > 0) {
				IncidentResource ir = resource.getIncidentResources().iterator().next();
				return this.getById(ir.getId());
			} else return null;

		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			super.handleException(e);
			return null;
		}
		
	}
	
	private Resource copyResource(Resource resource) throws Exception {
		Resource copy = new ResourceImpl();
		copy.setAgency(resource.getAgency());
		copy.setContracted(resource.isContracted());
		copy.setPerson(resource.isPerson());
		copy.setEnabled(resource.isEnabled());
		copy.setPermanent(resource.isPermanent());
		copy.setLeader(resource.isLeader());
		copy.setContactName(resource.getContactName());
		copy.setEmail(resource.getEmail());
		copy.setFirstName(resource.getFirstName());
		copy.setLastName(resource.getLastName());
		copy.setResourceName(resource.getResourceName());
		copy.setOrganization(resource.getOrganization());
		copy.setPrimaryDispatchCenter(resource.getPrimaryDispatchCenter());
		copy.setNameOnPictureId(resource.getNameOnPictureId());
		copy.setOther1(resource.getOther1());
		copy.setOther2(resource.getOther2());
		copy.setOther3(resource.getOther3());
		copy.setPermanentResourceId(resource.getId());
		copy.setPermanentResource(resource);
		copy.setParentResourceId(resource.getParentResourceId());
		copy.setPhone(resource.getPhone());
		copy.setResourceClassification(resource.getResourceClassification());
		copy.setResourceStatus(resource.getResourceStatus());
		copy.setLeaderType(resource.getLeaderType());
		copy.setEmploymentType(resource.getEmploymentType());
		copy.setContractor(resource.getContractor());
		copy.setContractorAgreement(resource.getContractorAgreement());
		return copy;
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
	
	public void unrosterResources(Collection<Integer> ids) throws ServiceException {
		try{
			Collection<Long> resourceIds = IntegerUtility.convertToLongs(ids);
			
			incidentResourceDao.unrosterResources(resourceIds);
			
			
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	public void unassign(Collection<Integer> incidentResourceIds) throws ServiceException {
		try{
			
			for(Integer id : incidentResourceIds){
				Long longId = IntegerUtility.convertToLong(id);
				
				IncidentResource irEntity = incidentResourceDao.getById(longId, IncidentResourceImpl.class);
				
				if(null == irEntity)
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"IncidentResource");

				//TODO: check for conditions that would prevent unassignment
				// - time posts for the resource
				// - supply items assigned to the resource
				
				//if any of these conditions exist, throw an exception to notify the user that
				//the resource could not be unassigned and why
				
				irEntity.setActive(false);
			
				incidentResourceDao.save(irEntity);
					
			}
		}catch(Exception e){
			super.handleException(e);
		}		
	}

	public IncidentResourceVo reassignResourceOther(IncidentResourceVo vo) throws ServiceException {
		
		try{
			Resource resource = null;

			if( (null != vo.getResourceVo()) && (vo.getResourceVo().getId() > 0L)){
				resource=resourceDao.getById(vo.getResourceVo().getId(), ResourceImpl.class);

				vo.getWorkPeriodVo().getCurrentAssignmentVo().setEndDate(Calendar.getInstance().getTime());
				
				// populate the entity
				resource = IncidentResourceVo.toResourceEntity(resource,vo);
				
				// save the entity
				resourceDao.save(resource);
				
				resourceDao.flushAndEvict(resource);

				if(null != resource.getIncidentResources() && resource.getIncidentResources().size() > 0) {
					IncidentResource ir = resource.getIncidentResources().iterator().next();
					IncidentResourceVo irVo = this.getById(ir.getId()); 
					return irVo;
				} else {
					return null;
				}
				
			}else{
				throw new ServiceException("Cannot reassign unknown resource.");
			}

		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}

	public DialogueVo reassignResource2(Long assignmentId, Long targetIncidentId, KindVo kindVo, String requestNumber, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			Long incidentResourceId = null;
			WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
			
			// set the assignment status to R
			AssignmentDao assignmentDao = (AssignmentDao)context.getBean("assignmentDao");
			
			Assignment entity = assignmentDao.getById(assignmentId, AssignmentImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Assignment");
			
			
			WorkPeriod wpEntity = null;
			
			if(null != entity.getWorkPeriods()){
				wpEntity = entity.getWorkPeriods().iterator().next();
				incidentResourceId = wpEntity.getIncidentResourceId();
			}
			
			Collection<WorkPeriod> workPeriods = entity.getWorkPeriods();
			
			entity.setAssignmentStatus(AssignmentStatusTypeEnum.R);
			
			// set assignment end date
			entity.setEndDate(Calendar.getInstance().getTime());
			
			assignmentDao.save(entity);
			
			assignmentDao.flushAndEvict(entity);

			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null != irEntity){
				IncidentResourceVo irvo = IncidentResourceVo.getInstance(irEntity, true);
				dialogueVo.setResultObject(irvo);
			}
			
			// get original grid vo
			IncidentResourceFilter filter = new IncidentResourceFilterImpl();
			filter.setIncidentResourceId(incidentResourceId);
			filter.setWorkAreaIncidentId(irEntity.getIncidentId());
			filter.setAssignmentId(entity.getId());
			filter.setAssignmentStatus(AssignmentStatusTypeEnum.R);
			
			Collection<IncidentResourceGridVo> irgridvos = incidentResourceDao.getGrid(filter,new ArrayList<String>());
			if(CollectionUtility.hasValue(irgridvos) && irgridvos.size()>0){
				IncidentResourceGridVo irgvo = irgridvos.iterator().next();
				dialogueVo.setResultObjectAlternate(irgvo);
			}
			
			// create new assignment with status F
			
			Assignment newEntity = new AssignmentImpl();
			newEntity.setStartDate(Calendar.getInstance().getTime());
			newEntity.setAssignmentStatus(AssignmentStatusTypeEnum.F);
			newEntity.setTraining(Boolean.FALSE);
			newEntity.setRequestNumber(requestNumber);
			newEntity.setKind(KindVo.toEntity(null, kindVo, false));
			
			assignmentDao.save(newEntity);
			
			assignmentDao.flushAndEvict(newEntity);

			// add new assignment to workperiod
			if(null != wpEntity){
				WorkPeriod workPeriodEntity = wpDao.getById(wpEntity.getId(), WorkPeriodImpl.class);
				workPeriodEntity.setIncidentResourceId(targetIncidentId);
				workPeriodEntity.getAssignments().add(newEntity);
				
				wpDao.save(workPeriodEntity);
				wpDao.flushAndEvict(workPeriodEntity);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("REASSIGN_RESOURCE");
		    coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
		    coaVo.setMessageVo(
					   new MessageVo(
							   "text.incidentResources", 
							   "text.affirmReassignResource" , null, 
							   MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);

			// get and set the original irgridvo, new irgridvo, updated irvo
			incidentResourceDao.flushAndEvict(irEntity);
			irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null != irEntity){
				IncidentResourceVo irvo = IncidentResourceVo.getInstance(irEntity, true);
				dialogueVo.setResultObjectAlternate2(irvo);
			}
			
			// get original grid vo
			filter = new IncidentResourceFilterImpl();
			filter.setIncidentResourceId(incidentResourceId);
			filter.setWorkAreaIncidentId(irEntity.getIncidentId());
			filter.setAssignmentId(newEntity.getId());
			
			irgridvos = incidentResourceDao.getGrid(filter,new ArrayList<String>());
			if(CollectionUtility.hasValue(irgridvos) && irgridvos.size()>0){
				IncidentResourceGridVo irgvo = irgridvos.iterator().next();
				dialogueVo.setResultObjectAlternate3(irgvo);
			}
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public void reassignResource(Long assignmentId, Long targetIncidentId, KindVo kindVo, String requestNumber) throws ServiceException {
		try{
			// set the assignment status to R
			AssignmentDao assignmentDao = (AssignmentDao)context.getBean("assignmentDao");
			
			Assignment entity = assignmentDao.getById(assignmentId, AssignmentImpl.class);
			if(null == entity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Assignment");
			
			
			WorkPeriod wpEntity = null;
			
			if(null != entity.getWorkPeriods()){
				wpEntity = entity.getWorkPeriods().iterator().next();
			}
			
			Collection<WorkPeriod> workPeriods = entity.getWorkPeriods();
			
			entity.setAssignmentStatus(AssignmentStatusTypeEnum.R);
			
			// set assignment end date
			entity.setEndDate(Calendar.getInstance().getTime());
			
			assignmentDao.save(entity);
			
			assignmentDao.flushAndEvict(entity);
			
			// create new assignment with status F
			
			Assignment newEntity = new AssignmentImpl();
			newEntity.setStartDate(Calendar.getInstance().getTime());
			newEntity.setAssignmentStatus(AssignmentStatusTypeEnum.F);
			newEntity.setTraining(Boolean.FALSE);
			newEntity.setRequestNumber(requestNumber);
			newEntity.setKind(KindVo.toEntity(null, kindVo, false));
			
			assignmentDao.save(newEntity);
			
			assignmentDao.flushAndEvict(newEntity);

			// add new assignment to workperiod
			if(null != wpEntity){
				WorkPeriodDao wpDao = (WorkPeriodDao)context.getBean("workPeriodDao");
				WorkPeriod workPeriodEntity = wpDao.getById(wpEntity.getId(), WorkPeriodImpl.class);
				workPeriodEntity.setIncidentResourceId(targetIncidentId);
				workPeriodEntity.getAssignments().add(newEntity);
				
				wpDao.save(workPeriodEntity);
			}
		}catch(Exception e){
			super.handleException(e);
		}
	}
	
	public Collection<IncidentAccountCodeVo> getIncidentAccountCodes(Long incidentId, Long incidentGroupId) throws ServiceException {
		
		try{
			Collection<IncidentAccountCode> entities = null;
			
			IncidentAccountCodeDao iacDao = (IncidentAccountCodeDao)context.getBean("incidentAccountCodeDao");
			
			if(null != incidentId && incidentId > 0){
				entities = iacDao.getByIncidentId(incidentId);
			}else if(null != incidentGroupId && incidentGroupId > 0){
				entities = iacDao.getByIncidentGroupId(incidentGroupId);
			}
			
			if(null != entities && entities.size() > 0){
				return IncidentAccountCodeVo.getInstances(entities, true);
			}
			
		}catch(Exception e){
			super.handleException(e);
		}
		
		return null;
	}
	
	public Collection<ContractorAgreementGridVo> getContractorAgreements(ContractorAgreementFilter filter) throws ServiceException {

		if (filter.getContractorId() == null) {
			throw new ServiceException("ContractorId is required.");
		}
		
		try {
		
			ContractorAgreementDao dao = (ContractorAgreementDao)context.getBean("contractorAgreementDao");
			Collection<ContractorAgreementGridVo> contractorAgreementVos = dao.getGrid(filter);
			
			return contractorAgreementVos;
			
		} catch(Exception ex) {
			super.handleException(ex);
		}
		return null;
	}

	public Collection<ContractorGridVo> getContractors(ContractorFilter filter) throws ServiceException {

		if (filter.getIncidentIds() == null && filter.getWorkAreaId() == null ) {
			throw new ServiceException("WorkAreaId or IncidentId is required.");
		}
		
		try {
		
			ContractorDao dao = (ContractorDao)context.getBean("contractorDao");
			Collection<ContractorGridVo> contractorVos = dao.getGrid(filter);
			
			return contractorVos;
			
		} catch(Exception ex) {
			super.handleException(ex);
		}
		return null;
	}

	public Collection<ContractorVo> getContractorVos(ContractorFilter filter) throws ServiceException {

		if (filter.getIncidentIds() == null && filter.getWorkAreaId() == null ) {
			throw new ServiceException("WorkAreaId or IncidentId is required.");
		}
		
		try {
		
			ContractorDao dao = (ContractorDao)context.getBean("contractorDao");

			Long workAreaId=filter.getWorkAreaId();
			Long incidentGroupId=filter.getIncidentGroupId();
			Collection<Long> incidentIds = filter.getIncidentIds();
			
			filter.setWorkAreaId(null);
			filter.setIncidentGroupId(null);
			filter.setIncidentIds(new ArrayList<Long>());
			
			Collection<ContractorVo> vos = new ArrayList<ContractorVo>();
			
			if(LongUtility.hasValue(workAreaId)){
				filter.setWorkAreaId(workAreaId);
				vos = dao.getAll(filter);
				//System.out.println(vos.size());
			}

			if(LongUtility.hasValue(incidentGroupId)){
				filter.setWorkAreaId(null);
				filter.setIncidentGroupId(incidentGroupId);
				vos.addAll(dao.getAll(filter));
				//System.out.println(vos.size());
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				filter.setWorkAreaId(null);
				filter.setIncidentGroupId(null);
				filter.setIncidentIds(incidentIds);
				vos.addAll(dao.getAll(filter));
				//System.out.println(vos.size());
			}
			
			
			return vos;
			
		} catch(Exception ex) {
			super.handleException(ex);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#deleteResources(java.util.Collection, java.lang.Boolean)
	 */
	public Collection<IncidentResourceGridVo> deleteResources(Collection<IncidentResourceGridVo> resources, boolean removeParentAssociations) throws ServiceException {
		if( (null != resources) && (resources.size()>0)){
			// Build a collection of resource ids
			Collection<Long> resourceIds = new ArrayList<Long>();
			for(IncidentResourceGridVo vo : resources){
				resourceIds.add(vo.getResourceId());
			}

			try{
				int count=resourceDao.deleteIncidentResources(resourceIds, removeParentAssociations);
				if(count==0) {
					throw new ServiceException("Unable to delete resources, count returned 0.");
				} 
				
				// update children, set parentresourceid = null
				resourceDao.unRosterIncidentResourcesChildren(resourceIds);
				
			}catch(PersistenceException pe){
				throw new ServiceException(pe);
			}

		}else{
			throw new ServiceException("Unable to delete resources with empty resources collection.");
		}
		return resources;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#deleteResourcesDialogueVo(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, java.util.Collection, boolean)
	 */
	public DialogueVo deleteResourcesDialogueVo(DialogueVo dialogueVo, Collection<IncidentResourceGridVo> resources, boolean removeParentAssociations) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Collection<Long> irIds = new ArrayList<Long>();
			Collection<Long> resourceIds = new ArrayList<Long>();
			
			if(CollectionUtility.hasValue(resources)){
				// Build a collection of resource ids
				for(IncidentResourceGridVo vo : resources){
					resourceIds.add(vo.getResourceId());
					irIds.add(vo.getIncidentResourceId());
				}
			}

			IncidentResourceDeleteRulesHandler ruleHandler = new IncidentResourceDeleteRulesHandler(context);
			
			if(ruleHandler.execute(irIds, null, dialogueVo)==AbstractRule._OK){
				
				int count=resourceDao.deleteIncidentResources(resourceIds, removeParentAssociations);
				if(count==0) {
					throw new ServiceException("Unable to delete resources, count returned 0.");
				} 
				
				// update children, set parentresourceid = null
				resourceDao.unRosterIncidentResourcesChildren(resourceIds);
				
				dialogueVo.setRecordset(resources);
				
				CourseOfActionVo coa = new CourseOfActionVo();
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
	
	public Collection<IncidentResourceGridVo> checkRequestNumber(Long workAreaId, IncidentResourceVo vo) throws ServiceException {
		
		Collection<IncidentResourceGridVo> vos = null;
		
		try {
			RequestNumberUtil.formatRequestNumber(vo);
			vos = incidentResourceDao.checkRequestNumber(workAreaId, vo);
		} catch (PersistenceException ex) {
			super.handleException(ex);
		} catch(Exception e){
			throw new ServiceException(e);
		}
		
		return vos;
	}

	public Collection<IncidentResourceGridVo> getStrikeTeams(Long workAreaId,Long incidentId, Long incidentGroupId) throws ServiceException {
		
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
			
			return vos;
		}catch(Exception e){
			throw new ServiceException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#getByIncidentId(java.lang.Long)
	 */
	public Collection<IncidentResourceVo> getByIncidentId(Long incidentId) throws ServiceException {
		try {
			IncidentResourceDao dao = this.getIncidentResourceDao();
			Collection<IncidentResourceVo> irVos = dao.getByIncidentId(incidentId);
			return irVos;
		} catch (Exception e) {
			throw new ServiceException(new ErrorObject(ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
		}
	}
	
	private IncidentResourceDao getIncidentResourceDao() throws ServiceException {
		return (IncidentResourceDao)super.context.getBean("incidentResourceDao");
	}
	
	public ResourceVo getParentLeaderResource(Long parentResourceId, String leaderType) throws ServiceException {
		
		try{
			Resource resource = resourceDao.getResourceLeader(parentResourceId, Integer.valueOf(leaderType));

			if(null != resource){
				ResourceVo vo = ResourceVo.getInstance(resource,true);
				resourceDao.flushAndEvict(resource);
				return vo;
			}else
				return null;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public String checkUniqueVinName(String vinName, Long assignmentTimeId) throws ServiceException {

		try{
			ContractorPaymentInfoDao cpiDao = (ContractorPaymentInfoDao)context.getBean("contractorPaymentInfoDao");
			
			Collection<ContractorPaymentInfo> list = cpiDao.getByVinName(vinName);
			
			if(null != list && list.size()>0){
				// check assignmentTimeId
				ContractorPaymentInfo cpi = list.iterator().next();
				
				if(null != assignmentTimeId && assignmentTimeId > 0){
					if(cpi.getAssignmentTimeId().compareTo(assignmentTimeId) != 0)
						return "FAIL";
					else{
						// should be ok, the record found is the same as the assignmentTimeId passed in
					}
				}else{
					return "FAIL";
				}
			}
			
			return "OK";
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public DialogueVo testDialogue(DialogueVo dvo) throws ServiceException {
		try{
			
			IncidentResourceTestDialogueHandler handler = new IncidentResourceTestDialogueHandler(super.context);
			
			return handler.execute(dvo);
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#propagateCrewEmploymentType(java.lang.String, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo propagateCrewEmploymentType(String employmentType,Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();

			if(null == resource.getParentResource()){
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
			}
			
			Resource parent = resource.getParentResource();
			
			/*
			 * insert an assignment time record for any crew members for which
			 * a record does not exist
			 */
			Long atCount = incidentResourceDao.assignmentTimeRecordCount(parent.getId());
			if(atCount>0) {
				incidentResourceDao.insertAssignmentTime(parent.getId());
			}
			
			/*
			 * update the employment type for crew assignment time records that
			 * do not have an employment type
			 */
			incidentResourceDao.updateCrewEmploymentType(employmentType, parent.getId());

			incidentResourceDao.flushAndEvict(irEntity);
			irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			IncidentResourceVo irVo = IncidentResourceVo.getInstance(irEntity, true);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("COMPLETE");
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#propagateCrewAddress(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo propagateCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();
			
			if(null == resource.getParentResource()){
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
			}
			
			Resource parent = resource.getParentResource();

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
							entities.add(atSubordinate);
						}
					}
				}
				
				if(CollectionUtility.hasValue(entities))
					atDao.saveAll(entities);
			}
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("MSG_FINISHED");
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
	 * @see gov.nwcg.isuite.core.service.IncidentResourceService#clearAllCrewAddress(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo clearAllCrewAddress(Long incidentResourceId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			if (!LongUtility.hasValue(incidentResourceId))
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResourceId is required to propagate crew employment type.");

			IncidentResource irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			if(null == irEntity)
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"incidentResource["+incidentResourceId+"]");

			Resource resource = irEntity.getResource();
			
			if(null == resource.getParentResource()){
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
			}
			
			Resource parent = resource.getParentResource();

			AssignmentTimeDao atDao = (AssignmentTimeDao)context.getBean("assignmentTimeDao");
				
			/*
			 * Loop through all parent children
			 */
			Collection<AssignmentTime> entities = new ArrayList<AssignmentTime>();
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
						entities.add(atSubordinate);
					}
				}
				if(CollectionUtility.hasValue(entities))
					atDao.saveAll(entities);
			}
			
			incidentResourceDao.flushAndEvict(irEntity);
			irEntity = this.incidentResourceDao.getById(incidentResourceId, IncidentResourceImpl.class);
			IncidentResourceVo irVo = IncidentResourceVo.getInstance(irEntity, true);
			
			/*
			 * Build and set the message
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
		    coaVo.setCoaName("COMPLETE");
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

	
}
