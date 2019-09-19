package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.domain.IapAreaLocationCapability;
import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapBranchCommSummary;
import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPersonnelRes;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.IapAircraftImpl;
import gov.nwcg.isuite.core.domain.impl.IapAircraftTaskImpl;
import gov.nwcg.isuite.core.domain.impl.IapAreaLocationCapabilityImpl;
import gov.nwcg.isuite.core.domain.impl.IapAttachmentImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchCommSummaryImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchPersonnelImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchRscAssignImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm202Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm203Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm205Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.core.domain.impl.IapFrequencyImpl;
import gov.nwcg.isuite.core.domain.impl.IapHospitalImpl;
import gov.nwcg.isuite.core.domain.impl.IapMedicalAidImpl;
import gov.nwcg.isuite.core.domain.impl.IapPersonnelImpl;
import gov.nwcg.isuite.core.domain.impl.IapPersonnelResImpl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.core.domain.impl.IapRemoteCampLocationsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.filter.impl.IapPlanFilterImpl;
import gov.nwcg.isuite.core.persistence.IapAircraftDao;
import gov.nwcg.isuite.core.persistence.IapAircraftTaskDao;
import gov.nwcg.isuite.core.persistence.IapAreaLocationCapabilityDao;
import gov.nwcg.isuite.core.persistence.IapAttachmentDao;
import gov.nwcg.isuite.core.persistence.IapBranchCommSummaryDao;
import gov.nwcg.isuite.core.persistence.IapBranchDao;
import gov.nwcg.isuite.core.persistence.IapBranchPersonnelDao;
import gov.nwcg.isuite.core.persistence.IapBranchRscAssignDao;
import gov.nwcg.isuite.core.persistence.IapForm202Dao;
import gov.nwcg.isuite.core.persistence.IapForm203Dao;
import gov.nwcg.isuite.core.persistence.IapForm205Dao;
import gov.nwcg.isuite.core.persistence.IapForm206Dao;
import gov.nwcg.isuite.core.persistence.IapForm220Dao;
import gov.nwcg.isuite.core.persistence.IapFrequencyDao;
import gov.nwcg.isuite.core.persistence.IapHospitalDao;
import gov.nwcg.isuite.core.persistence.IapMedicalAidDao;
import gov.nwcg.isuite.core.persistence.IapPersonnelDao;
import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.persistence.IapRemoteCampLocationsDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.rules.IapAddBranchRscAssignRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteAirAmbulanceRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteAmbulanceRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteAreaLocCapRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteBranchCommRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteBranchPersonnelRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteBranchRscAssignRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteForm203PositionRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteFrequencyRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteHelicopterRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteHospitalRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteRemoteCampLocRulesHandler;
import gov.nwcg.isuite.core.rules.IapDeleteTaskRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveAirAmbulanceRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveAmbulanceRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveAreaLocCapRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveAttachmentRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveBranchCommRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveBranchPersonnelRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveBranchRscAssignRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm202RulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm203PositionRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm203RulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm204RulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm205RulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm206RulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveForm220RulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveFrequencyRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveHelicopterRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveHospitalRulesHandler;
import gov.nwcg.isuite.core.rules.IapSavePlanRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveRemoteCampLocRulesHandler;
import gov.nwcg.isuite.core.rules.IapSaveTaskRulesHandler;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.service.IapPlanService;
import gov.nwcg.isuite.core.service.IncidentPrefsService;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IapAircraftFrequencyVo;
import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;
import gov.nwcg.isuite.core.vo.IapAircraftVo;
import gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo;
import gov.nwcg.isuite.core.vo.IapAttachmentVo;
import gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo;
import gov.nwcg.isuite.core.vo.IapBranchPersonnelVo;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.core.vo.IapForm202Vo;
import gov.nwcg.isuite.core.vo.IapForm203Vo;
import gov.nwcg.isuite.core.vo.IapForm204Vo;
import gov.nwcg.isuite.core.vo.IapForm205Vo;
import gov.nwcg.isuite.core.vo.IapForm206Vo;
import gov.nwcg.isuite.core.vo.IapForm220Vo;
import gov.nwcg.isuite.core.vo.IapFrequencyVo;
import gov.nwcg.isuite.core.vo.IapGridVo;
import gov.nwcg.isuite.core.vo.IapHospitalVo;
import gov.nwcg.isuite.core.vo.IapMedicalAidVo;
import gov.nwcg.isuite.core.vo.IapPersonnelVo;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.IapSectionEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.PersonsNameOrderEnum;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

public class IapPlanServiceImpl extends BaseService implements IapPlanService{
	private IapPlanDao iapPlanDao=null;
	private IapForm202Dao iapForm202Dao=null;
	private IapForm203Dao iapForm203Dao=null;
	private IapBranchDao iapBranchDao=null;
	private IapForm205Dao iapForm205Dao=null;
	private IapForm206Dao iapForm206Dao=null;
	private IapForm220Dao iapForm220Dao=null;
	private IapAttachmentDao iapAttachmentDao=null;
	private IapFrequencyDao iapFrequencyDao=null;
	private IapHospitalDao iapHospitalDao=null;
	private IapMedicalAidDao iapMedicalAidDao=null;
	private IapAircraftDao iapAircraftDao=null;
	private IapAircraftTaskDao iapAircraftTaskDao=null;
	private IapBranchPersonnelDao iapBranchPersonnelDao=null;
	private IapBranchCommSummaryDao iapBranchCommSummaryDao=null;
	private IapBranchRscAssignDao iapBranchRscAssignDao=null;
	private IapPersonnelDao iapPersonnelDao=null;
	private IapAreaLocationCapabilityDao iapAreaLocationCapabilityDao=null;
	private IapRemoteCampLocationsDao iapRemoteCampLocationsDao=null;
	
	private IncidentPrefsService incidentPrefsService;
	
	// //////////////////////////////////////////////////////////////////////////
	// IAP 204 BLOCK 5 RELATED STRINGS: 
	// These are hardcoded values being saved in the database ISW_INCIDENT_PREFS table. D
	// Do NOT change the String values.
	// NOTE:::: These are currently saved in the database as BLOCK6 and not BLOCK5. This should be
	// 			fixed both in the database and IncidentPrefsSectionNameEnum later.
	private static final String IAP_204_BLOCK5_PREF_FIELD_LABEL_RESOURCENAME = "Resource Name";
	private static final String IAP_204_BLOCK5_PREF_FIELD_LABEL_REQUESTNUMBER = "Request Number";
	private static final String IAP_204_BLOCK5_PREF_FIELD_LABEL_ITEMCODE = "Item Code";

	// The separator between RESOURCE IDENTIFIER COMPONENTS.
	// Per requirements, this is set to "2 spaces". DO NOT MODIFY. 
	private static final String IAP_204_BLOCK5_RESOURCE_IDENTIFIER_SEPARATOR = "  "; 
	// //////////////////////////////////////////////////////////////////////////

	public IapPlanServiceImpl(){
		super();
	}
	
	public void initialization() {
		iapPlanDao = (IapPlanDao)context.getBean("iapPlanDao");
		iapForm202Dao = (IapForm202Dao)context.getBean("iapForm202Dao");
		iapForm203Dao = (IapForm203Dao)context.getBean("iapForm203Dao");
		iapBranchDao = (IapBranchDao)context.getBean("iapBranchDao");
		iapForm205Dao = (IapForm205Dao)context.getBean("iapForm205Dao");
		iapForm206Dao = (IapForm206Dao)context.getBean("iapForm206Dao");
		iapForm220Dao = (IapForm220Dao)context.getBean("iapForm220Dao");
		iapAttachmentDao = (IapAttachmentDao)context.getBean("iapAttachmentDao");
		iapFrequencyDao = (IapFrequencyDao)context.getBean("iapFrequencyDao");
		iapHospitalDao = (IapHospitalDao)context.getBean("iapHospitalDao");
		iapMedicalAidDao = (IapMedicalAidDao)context.getBean("iapMedicalAidDao");
		iapAircraftDao = (IapAircraftDao)context.getBean("iapAircraftDao");
		iapAircraftTaskDao = (IapAircraftTaskDao)context.getBean("iapAircraftTaskDao");
		iapBranchPersonnelDao = (IapBranchPersonnelDao)context.getBean("iapBranchPersonnelDao");
		iapBranchCommSummaryDao = (IapBranchCommSummaryDao)context.getBean("iapBranchCommSummaryDao");
		iapBranchRscAssignDao = (IapBranchRscAssignDao)context.getBean("iapBranchRscAssignDao");
		iapPersonnelDao = (IapPersonnelDao)context.getBean("iapPersonnelDao");
		iapAreaLocationCapabilityDao = (IapAreaLocationCapabilityDao)context.getBean("iapAreaLocationCapabilityDao");
		iapRemoteCampLocationsDao = (IapRemoteCampLocationsDao)context.getBean("iapRemoteCampLocDao");
		incidentPrefsService = (IncidentPrefsService)context.getBean("incidentPrefsService");
	}

	private String getDestinationFileName(String prefix) throws Exception {
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return prefix + timestamp + ".pdf";
	}

	private String getOutputFile(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_FOLDER.name());
			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}
		}catch(Exception e){
			throw e;
		}
	}

	public DialogueVo getIapPlan(Long iapPlanId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			IapPlanVo iapPlanVo = new IapPlanVo();
			
			IapPlan entity = this.iapPlanDao.getById(iapPlanId, IapPlanImpl.class);
			
			if ( null != entity ) {
				iapPlanVo = IapPlanVo.getInstance(entity, true);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IAP_PLAN");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(iapPlanVo);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#getIapPlanGrid(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIapPlanGrid(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IapPlanFilterImpl filter=new IapPlanFilterImpl();
			if(LongUtility.hasValue(incidentId)){
				IncidentDao incDao=(IncidentDao)context.getBean("incidentDao");
				Incident inc=incDao.getById(incidentId);
				if(null != inc){
					if(ShortUtil.hasValue(inc.getIapTreeviewDisplay()))
						filter.treeviewDisplay=inc.getIapTreeviewDisplay();
					
					if(filter.treeviewDisplay==1){
						if(null != inc.getByDate()){
							Date dt = inc.getByDate();
							dt=DateUtil.addMilitaryTimeToDate(dt, "0000");
							filter.byDate=dt;
						}
					}
					
					if(filter.treeviewDisplay==2){
						if(ShortUtil.hasValue(inc.getNbrOfDaysPrior())){
							Date dt = null;
							if(super.getRunMode().equals("SITE")){
								dt=EISuiteCalendar.getCalendarDate(super.getUserSessionDbName());
							}else{
								dt=EISuiteCalendar.getCalendarDate("");
							}
							
							dt=DateUtil.addMilitaryTimeToDate(dt, "0000");
							int priorDays=new Integer(inc.getNbrOfDaysPrior()).intValue();
							dt=DateUtil.subtractDaysFromDate(dt, priorDays);
							filter.byDate=dt;
							filter.nbrOfPriorDays=inc.getNbrOfDaysPrior();
						}
					}
					incDao.flushAndEvict(inc);
				}
			}
			if(LongUtility.hasValue(incidentGroupId)){
				IncidentGroupDao incGroupDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup incGroup=incGroupDao.getById(incidentGroupId);
				if(null != incGroup){
					if(ShortUtil.hasValue(incGroup.getIapTreeviewDisplay()))
						filter.treeviewDisplay=incGroup.getIapTreeviewDisplay();
					
					if(filter.treeviewDisplay==1){
						if(null != incGroup.getByDate()){
							Date dt = incGroup.getByDate();
							dt=DateUtil.addMilitaryTimeToDate(dt, "0000");
							filter.byDate=dt;
						}
					}
					
					if(filter.treeviewDisplay==2){
						if(ShortUtil.hasValue(incGroup.getNbrOfDaysPrior())){
							Date dt = null;
							if(super.getRunMode().equals("SITE")){
								dt=EISuiteCalendar.getCalendarDate(super.getUserSessionDbName());
							}else{
								dt=EISuiteCalendar.getCalendarDate("");
							}
							
							dt=DateUtil.addMilitaryTimeToDate(dt, "0000");
							int priorDays=new Integer(incGroup.getNbrOfDaysPrior()).intValue();
							dt=DateUtil.subtractDaysFromDate(dt, priorDays);
							filter.byDate=dt;
							filter.nbrOfPriorDays=incGroup.getNbrOfDaysPrior();
						}
					}
					
					incGroupDao.flushAndEvict(incGroup);
				}
			}
			
			Collection<IapGridVo> iapGridVos = new ArrayList<IapGridVo>();
			
			Collection<IapPlan> entities=
				iapPlanDao.getAllIapPlans(incidentId,incidentGroupId,filter);
				
			for (IapPlan entity : entities) {
				IapGridVo gridVo = IapGridVo.getInstance(entity);
				String planName = gridVo.getDisplayName();
				gridVo.getHierachalGroupField().add(planName);
				iapGridVos.add(gridVo);

				if(CollectionUtility.hasValue(entity.getIapForm202s())){
					// get number of 202 forms for the parent
					boolean hasMultiple = (entity.getIapForm202s().size() > 1 ? true: false);

					int cnt = 1;
					for(IapForm202 form : entity.getIapForm202s()){
						IapGridVo childVo = IapGridVo.getForm202Instance(form);
						childVo.getHierachalGroupField().add(planName);
						if ( hasMultiple == true ){ 
							childVo.setHasMultiple(true);
							childVo.getHierachalGroupField().add(childVo.getDisplayName() + " ( " + cnt + " )");
							childVo.setFormSequence(cnt);
							cnt++;
						}else
							childVo.getHierachalGroupField().add(childVo.getDisplayName());
							
						iapGridVos.add(childVo);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapForm203s())){
					// get number of 203 forms for the parent
					boolean hasMultiple = (entity.getIapForm203s().size() > 1 ? true: false);

					int cnt = 1;
					for(IapForm203 form : entity.getIapForm203s()){
						IapGridVo childVo = IapGridVo.getForm203Instance(form);
						childVo.getHierachalGroupField().add(planName);
						if ( hasMultiple == true ){ 
							childVo.setHasMultiple(true);
							childVo.getHierachalGroupField().add(childVo.getDisplayName() + " ( " + cnt + " )");
							childVo.setFormSequence(cnt);
							cnt++;
						}else
							childVo.getHierachalGroupField().add(childVo.getDisplayName());
							
						iapGridVos.add(childVo);
					}
					/*
					for(IapForm203 form : entity.getIapForm203s()){
						IapGridVo childVo = IapGridVo.getForm203Instance(form);
						childVo.getHierachalGroupField().add(planName);
						childVo.getHierachalGroupField().add(childVo.getDisplayName());
						iapGridVos.add(childVo);
					}
					*/
				}
				if(CollectionUtility.hasValue(entity.getIapBranchs())){
					// get number of 204 forms for the parent
					boolean hasMultiple = (entity.getIapBranchs().size() > 1 ? true: false);

					int cnt = 1;
					for(IapBranch form : entity.getIapBranchs()){
						IapGridVo childVo = IapGridVo.getForm204Instance(form);
						childVo.getHierachalGroupField().add(planName);
						if ( hasMultiple == true ){ 
							childVo.setHasMultiple(true);
							childVo.getHierachalGroupField().add(childVo.getDisplayName() + " ( " + cnt + " )");
							childVo.setFormSequence(cnt);
							cnt++;
						}else
							childVo.getHierachalGroupField().add(childVo.getDisplayName());
							
						iapGridVos.add(childVo);
					}
					/*
					for(IapBranch form : entity.getIapBranchs()){
						IapGridVo childVo = IapGridVo.getForm204Instance(form);
						childVo.getHierachalGroupField().add(planName);
						childVo.getHierachalGroupField().add(childVo.getDisplayName());
						iapGridVos.add(childVo);
					}
					*/
				}
				if(CollectionUtility.hasValue(entity.getIapForm205s())){
					// get number of 205 forms for the parent
					boolean hasMultiple = (entity.getIapForm205s().size() > 1 ? true: false);

					int cnt = 1;
					for(IapForm205 form : entity.getIapForm205s()){
						IapGridVo childVo = IapGridVo.getForm205Instance(form);
						childVo.getHierachalGroupField().add(planName);
						if ( hasMultiple == true ){ 
							childVo.setHasMultiple(true);
							childVo.getHierachalGroupField().add(childVo.getDisplayName() + " ( " + cnt + " )");
							childVo.setFormSequence(cnt);
							cnt++;
						}else
							childVo.getHierachalGroupField().add(childVo.getDisplayName());
							
						iapGridVos.add(childVo);
					}

				}
				if(CollectionUtility.hasValue(entity.getIapForm206s())){
					// get number of 206 forms for the parent
					boolean hasMultiple = (entity.getIapForm206s().size() > 1 ? true: false);

					int cnt = 1;
					for(IapForm206 form : entity.getIapForm206s()){
						IapGridVo childVo = IapGridVo.getForm206Instance(form);
						childVo.getHierachalGroupField().add(planName);
						if ( hasMultiple == true ){ 
							childVo.setHasMultiple(true);
							childVo.getHierachalGroupField().add(childVo.getDisplayName() + " ( " + cnt + " )");
							childVo.setFormSequence(cnt);
							cnt++;
						}else
							childVo.getHierachalGroupField().add(childVo.getDisplayName());
							
						iapGridVos.add(childVo);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapForm220s())){
					// get number of 220 forms for the parent
					boolean hasMultiple = (entity.getIapForm220s().size() > 1 ? true: false);

					int cnt = 1;
					for(IapForm220 form : entity.getIapForm220s()){
						IapGridVo childVo = IapGridVo.getForm220Instance(form);
						childVo.getHierachalGroupField().add(planName);
						if ( hasMultiple == true ){ 
							childVo.setHasMultiple(true);
							childVo.getHierachalGroupField().add(childVo.getDisplayName() + " ( " + cnt + " )");
							childVo.setFormSequence(cnt);
							cnt++;
						}else
							childVo.getHierachalGroupField().add(childVo.getDisplayName());
							
						iapGridVos.add(childVo);
					}
				}
			}
			
			//iapGridVos = IapGridVo.getInstances(entities);
			
			for(IapPlan iapPlan : entities){
				iapPlanDao.flushAndEvict(iapPlan);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IAP_PLAN_GRID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setRecordset(iapGridVos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#getIapForm(java.lang.Long, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIapForm(Long id, String formType, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{

			/*
			 * Build course of action for HANDLE_RESULTOBJECT and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			int ftype=TypeConverter.convertToInt(formType);
			switch(ftype)
			{
				case 202:
					IapForm202 iapForm202=this.iapForm202Dao.getById(id,IapForm202Impl.class);
					IapForm202Vo vo = new IapForm202Vo();
					if(null != iapForm202){
						vo = IapForm202Vo.getInstance(iapForm202, true);
						iapForm202Dao.flushAndEvict(iapForm202);
					}
					coaVo.setCoaName("GET_IAP_FORM_202");
					dialogueVo.setResultObject(vo);
					break;
				case 203:
					IapForm203 iapForm203=this.iapForm203Dao.getById(id,IapForm203Impl.class);
					IapForm203Vo iapForm203Vo = new IapForm203Vo();
					if(null != iapForm203){
						iapForm203Vo = IapForm203Vo.getInstance(iapForm203, true);
						iapForm203Dao.flushAndEvict(iapForm203);
					}
					coaVo.setCoaName("GET_IAP_FORM_203");
					dialogueVo.setResultObject(iapForm203Vo);
					break;
				case 204:
					IapBranch iapForm204=iapBranchDao.getById(id,IapBranchImpl.class);
					IapForm204Vo iapForm204Vo = new IapForm204Vo();
					if(null != iapForm204){
						iapForm204Vo = IapForm204Vo.getInstance(iapForm204, true);
						iapBranchDao.flushAndEvict(iapForm204);
					}
					coaVo.setCoaName("GET_IAP_FORM_204");
					dialogueVo.setResultObject(iapForm204Vo);
					break;
				case 205:
					IapForm205 iapForm205=iapForm205Dao.getById(id,IapForm205Impl.class);
					IapForm205Vo iapForm205Vo = new IapForm205Vo();
					if(null != iapForm205){
						iapForm205Vo = IapForm205Vo.getInstance(iapForm205, true);
						iapForm205Dao.flushAndEvict(iapForm205);
					}
					coaVo.setCoaName("GET_IAP_FORM_205");
					dialogueVo.setResultObject(iapForm205Vo);
					break;
				case 206:
					IapForm206 iapForm206=iapForm206Dao.getById(id,IapForm206Impl.class);
					IapForm206Vo iapForm206Vo = new IapForm206Vo();
					if(null != iapForm206){
						iapForm206Vo = IapForm206Vo.getInstance(iapForm206, true);
						iapForm206Dao.flushAndEvict(iapForm206);
					}
					coaVo.setCoaName("GET_IAP_FORM_206");
					dialogueVo.setResultObject(iapForm206Vo);
					break;
				case 220:
					IapForm220 iapForm220=iapForm220Dao.getById(id,IapForm220Impl.class);
					IapForm220Vo iapForm220Vo = new IapForm220Vo();
					if(null != iapForm220){
						iapForm220Vo = IapForm220Vo.getInstance(iapForm220, true);
						iapForm220Dao.flushAndEvict(iapForm220);
					}
					coaVo.setCoaName("GET_IAP_FORM_220");
					dialogueVo.setResultObject(iapForm220Vo);
					break;
			}

			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapPlan(gov.nwcg.isuite.core.vo.iap.IapPlanVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapPlan(IapPlanVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// defect 4988 fix begin
            if (vo.getFromDateTime() != null) {
                            if (vo.getFromDateTime().equals("2400")) {
                                            vo.setFromDateTime("2359");
                            }
            }
            
            if (vo.getToDateTime() != null) {
                            if (vo.getToDateTime().equals("2400")) {
                                            vo.setToDateTime("2359");
                            }
            }
            // defect 4988 fix end

			// fix date/times to avoid timezone adjustment issues
			IapPlanVo.fixDateTimes(vo);

			IapPlan entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = iapPlanDao.getById(vo.getId(), IapPlanImpl.class);
			
			// run rule checks before processing
			IapSavePlanRulesHandler ruleHandler = new IapSavePlanRulesHandler(super.context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				entity = IapPlanVo.toEntity(entity, vo, true);
				iapPlanDao.save(entity);
				iapPlanDao.flushAndEvict(entity);
				entity = iapPlanDao.getById(entity.getId(), IapPlanImpl.class);

				vo = IapPlanVo.getInstance(entity,true);
				IapGridVo gridVo = IapGridVo.getInstance(entity);
				
                // defect 4988 fix begin
                if (vo.getFromDateTime() != null) {
                                if (vo.getFromDateTime().equals("2359")) {
                                                vo.setFromDateTime("2400");
                                }
                }
                
                if (vo.getToDateTime() != null) {
                                if (vo.getToDateTime().equals("2359")) {
                                                vo.setToDateTime("2400");
                                }
                }
                
                if (gridVo.getFromTimeString() != null) {
                                if (gridVo.getFromTimeString().equals("2359")) {
                                                gridVo.setFromTimeString("2400");
                                }
                }                                              
                
                if (gridVo.getToTimeString() != null) {
                                if (gridVo.getToTimeString().equals("2359")) {
                                                gridVo.setToTimeString("2400");
                                }
                }
                
                // build the displayName, copy codes from IapGridVo
                String dname = gridVo.getFromDateString() + " - " + gridVo.getToDateString() + " " +
                gridVo.getFromTimeString() + " - " + gridVo.getToTimeString() +
                (StringUtility.hasValue(entity.getOperationPeriod()) ? " " + entity.getOperationPeriod() : "");
                gridVo.setDisplayName(dname);
                gridVo.getHierachalGroupField().add(dname);
                // defect 4988 fix end
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_PLAN");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.0030.01" , new String[]{"IAP Plan "}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);

			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#lockUnlockIapPlan(java.lang.Long, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo lockUnlockIapPlan(Long iapPlanId, String action, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			StringBooleanEnum sbEnum = StringBooleanEnum.N;
			Boolean islocked=false;
			if(action.equalsIgnoreCase("LOCK")){
				sbEnum=StringBooleanEnum.Y;
				islocked=true;
			}

			IapPlan iapPlan = iapPlanDao.getById(iapPlanId, IapPlanImpl.class);
			iapPlan.setIsPlanLocked(sbEnum);
			
			iapPlanDao.save(iapPlan);
			iapPlanDao.flushAndEvict(iapPlan);

			// lock/unlock all forms in plan
			iapPlanDao.lockUnlockForms(iapPlanId, islocked);
			
			// get updated plan forms

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("LOCK_UNLOCK_PLAN");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			String actionMsg = (action.equalsIgnoreCase("LOCK") ? "Locked" : "Unlocked");
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Plan was "+actionMsg+"."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(iapPlanId);
			dialogueVo.setResultObjectAlternate((action.equalsIgnoreCase("LOCK") ? "lock" : "unlock"));
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#lockUnlockIapForm(java.lang.Long, java.lang.String, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo lockUnlockIapForm(Long formId, String formType,String action, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			Long iapPlanId=0L;
			
			StringBooleanEnum sbEnum = StringBooleanEnum.N;
			if(action.equalsIgnoreCase("LOCK"))
				sbEnum=StringBooleanEnum.Y;
			
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			
			int ftype=TypeConverter.convertToInt(formType);
			switch(ftype)
			{
				case 202:
					IapForm202 iapForm202=this.iapForm202Dao.getById(formId,IapForm202Impl.class);
					if(null != iapForm202){
						iapPlanId=iapForm202.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm202.setIsFormLocked(sbEnum);
						iapForm202Dao.save(iapForm202);
						iapForm202Dao.flushAndEvict(iapForm202);
					}else
						throw new Exception("Unable to load IapForm202 entity with id " + formId);
					break;
				case 203:
					IapForm203 iapForm203=this.iapForm203Dao.getById(formId,IapForm203Impl.class);
					if(null != iapForm203){
						iapPlanId=iapForm203.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm203.setIsFormLocked(sbEnum);
						iapForm203Dao.save(iapForm203);
						iapForm203Dao.flushAndEvict(iapForm203);
					}else
						throw new Exception("Unable to load IapForm203 entity with id " + formId);
					break;
				case 204:
					IapBranch iapForm204=this.iapBranchDao.getById(formId,IapBranchImpl.class);
					if(null != iapForm204){
						iapPlanId=iapForm204.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm204.setIsForm204Locked(sbEnum);
						iapBranchDao.save(iapForm204);
						iapBranchDao.flushAndEvict(iapForm204);
					}else
						throw new Exception("Unable to load IapForm204 entity with id " + formId);
					break;
				case 205:
					IapForm205 iapForm205=this.iapForm205Dao.getById(formId,IapForm205Impl.class);
					if(null != iapForm205){
						iapPlanId=iapForm205.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm205.setIsFormLocked(sbEnum);
						iapForm205Dao.save(iapForm205);
						iapForm205Dao.flushAndEvict(iapForm205);
					}else
						throw new Exception("Unable to load IapForm205 entity with id " + formId);
					break;
				case 206:
					IapForm206 iapForm206=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
					if(null != iapForm206){
						iapPlanId=iapForm206.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm206.setIsFormLocked(sbEnum);
						iapForm206Dao.save(iapForm206);
						iapForm206Dao.flushAndEvict(iapForm206);
					}else
						throw new Exception("Unable to load IapForm206 entity with id " + formId);
					break;
				case 220:
					IapForm220 iapForm220=this.iapForm220Dao.getById(formId,IapForm220Impl.class);
					if(null != iapForm220){
						iapPlanId=iapForm220.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm220.setIsFormLocked(sbEnum);
						iapForm220Dao.save(iapForm220);
						iapForm220Dao.flushAndEvict(iapForm220);
					}else
						throw new Exception("Unable to load IapForm220 entity with id " + formId);
					break;
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("LOCK_UNLOCK_FORM");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			String actionMsg = (action.equalsIgnoreCase("LOCK") ? "Locked" : "Unlocked");
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Form was "+actionMsg+"."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(formId);
			dialogueVo.setResultObjectAlternate((action.equalsIgnoreCase("LOCK") ? "lock" : "unlock" ));
			dialogueVo.setResultObjectAlternate2(iapPlanId);
			dialogueVo.setResultObjectAlternate3(formType);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapPlan(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapPlan(Long iapPlanId, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			IapPlan iapPlan = iapPlanDao.getById(iapPlanId, IapPlanImpl.class);
			
			iapPlanDao.delete(iapPlan);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_IAP_PLAN");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Plan was deleted."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(iapPlanId);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm(java.lang.Long, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm(Long formId, String formType,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Long iapPlanId=0L;
			
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			int ftype=TypeConverter.convertToInt(formType);
			switch(ftype)
			{
				case 202:
					IapForm202 iapForm202=this.iapForm202Dao.getById(formId,IapForm202Impl.class);
					if(null != iapForm202){
						iapPlanId=iapForm202.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm202Dao.delete(iapForm202);
					}else
						throw new Exception("Unable to delete IapForm202 entity with id " + formId);
					break;
				case 203:
					IapForm203 iapForm203=this.iapForm203Dao.getById(formId,IapForm203Impl.class);
					if(null != iapForm203){
						iapPlanId=iapForm203.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm203Dao.delete(iapForm203);
					}else
						throw new Exception("Unable to delete IapForm203 entity with id " + formId);
					break;
				case 204:
					IapBranch iapForm204=this.iapBranchDao.getById(formId,IapBranchImpl.class);
					if(null != iapForm204){
						iapPlanId=iapForm204.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapBranchDao.delete(iapForm204);
					}else
						throw new Exception("Unable to delete IapForm204 entity with id " + formId);
					break;
				case 205:
					IapForm205 iapForm205=this.iapForm205Dao.getById(formId,IapForm205Impl.class);
					if(null != iapForm205){
						iapPlanId=iapForm205.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm205Dao.delete(iapForm205);
					}else
						throw new Exception("Unable to delete IapForm205 entity with id " + formId);
					break;
				case 206:
					IapForm206 iapForm206=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
					if(null != iapForm206){
						iapPlanId=iapForm206.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm206Dao.delete(iapForm206);
					}else
						throw new Exception("Unable to delete IapForm206 entity with id " + formId);
					break;
				case 220:
					IapForm220 iapForm220=this.iapForm220Dao.getById(formId,IapForm220Impl.class);
					if(null != iapForm220){
						iapPlanId=iapForm220.getIapPlanId();
						if(lockedRuleHandler.execute(iapPlanId, "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
						iapForm220Dao.delete(iapForm220);
					}else
						throw new Exception("Unable to delete IapForm220 entity with id " + formId);
					break;
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_IAP_FORM");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Form was deleted."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(formId);
			dialogueVo.setResultObjectAlternate(iapPlanId);
			dialogueVo.setResultObjectAlternate2(formType);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm202(gov.nwcg.isuite.core.vo.IapForm202Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm202(IapForm202Vo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm202 entity = null;
			Boolean isNew=true;
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				entity = iapForm202Dao.getById(vo.getId(), IapForm202Impl.class);
				iapForm202Dao.flushAndEvict(entity);
				isNew=false;
			}

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}

			// run the rule handler
			IapSaveForm202RulesHandler ruleHandler = new IapSaveForm202RulesHandler(super.context);
			if(ruleHandler.execute(vo, null, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				entity = IapForm202Vo.toEntity(entity, vo, true,(null != entity ? entity.getIapPlan() : null));
				iapForm202Dao.save(entity);
				iapForm202Dao.flushAndEvict(entity);

				entity = iapForm202Dao.getById(entity.getId(), IapForm202Impl.class);
				
				// get update vo to return
				vo = IapForm202Vo.getInstance(entity, true);
				
				// get the grid instance for form
				IapGridVo gridVo = IapGridVo.getForm202Instance(entity);

				IapPlan planEntity = this.iapPlanDao.getById(vo.getIapPlanId(), IapPlanImpl.class);
				IapGridVo gridVoParent = IapGridVo.getInstance(planEntity);
				String planName = gridVoParent.getDisplayName();
				gridVo.getHierachalGroupField().add(planName);

				// get number of 202 forms for the parent
				int cnt = 1;
				boolean hasMultiple = false;
				if ( CollectionUtility.hasValue(planEntity.getIapForm202s()) 
						&& planEntity.getIapForm202s().size() > 1){
					cnt = planEntity.getIapForm202s().size();
				}

				if ( cnt > 1) {
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName() + " ( " + cnt + " )");
					gridVo.setFormSequence(cnt);
					gridVo.setHasMultiple(true);
					cnt++;
				} else
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName());
					
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_FORM_202");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 202 Form was added to the plan."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 202 Form was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm203(gov.nwcg.isuite.core.vo.IapForm203Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm203(IapForm203Vo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm203 entity = null;
			Boolean isNew=true;
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				entity = iapForm203Dao.getById(vo.getId(), IapForm203Impl.class);
				iapForm203Dao.flushAndEvict(entity);
				isNew=false;
			}

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			// run the rule handler
			IapSaveForm203RulesHandler ruleHandler = new IapSaveForm203RulesHandler(super.context);
			if(ruleHandler.execute(vo, null, dialogueVo)==AbstractRule._OK){

				/* 4/24/2015 - for some reason whenever saving
				 * all of the position resource names are getting deleted.
				 * 
				 * so for iap form 203, only save block 9 info
				 */
				// update entity from vo and save
				if(isNew==false){
					entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
					entity.setPreparedByPosition(StringUtility.toUpper(vo.getPreparedByPosition()));
					if(DateTransferVo.hasDateString(vo.getPreparedDateVo())){
						Date dt=DateTransferVo.getDate(vo.getPreparedDateVo());
						if(StringUtility.hasValue(vo.getPreparedTime())){
							dt=DateUtil.addMilitaryTimeToDate(dt, vo.getPreparedTime());
							entity.setPreparedDate(dt);
						}
					}else{
						entity.setPreparedDate(null);
					}
				}else
					entity = IapForm203Vo.toEntity(entity, vo, true,(null != entity ? entity.getIapPlan() : null));
				
				iapForm203Dao.save(entity);
				iapForm203Dao.flushAndEvict(entity);

				if(isNew==true){
					// add default 203 positions from template
					this.createDefaut203Positions(entity.getId(),vo.getIapPlanId());
					
					// create default empty branch
					IapPersonnel defaultBranch = IapPersonnelVo.buildDefaultBlankBranch(entity.getId());
					this.iapPersonnelDao.save(defaultBranch);
					this.iapPersonnelDao.flushAndEvict(defaultBranch);					
					
					// create 3 empty division group records
					IapPersonnel defaultDivGroup1 = IapPersonnelVo.buildDefaultBlankDivGroup(entity.getId(),defaultBranch.getId(),1);
					IapPersonnel defaultDivGroup2 = IapPersonnelVo.buildDefaultBlankDivGroup(entity.getId(),defaultBranch.getId(),2);
					IapPersonnel defaultDivGroup3 = IapPersonnelVo.buildDefaultBlankDivGroup(entity.getId(),defaultBranch.getId(),3);
					this.iapPersonnelDao.save(defaultDivGroup1);
					this.iapPersonnelDao.flushAndEvict(defaultDivGroup1);					
					this.iapPersonnelDao.save(defaultDivGroup2);
					this.iapPersonnelDao.flushAndEvict(defaultDivGroup2);					
					this.iapPersonnelDao.save(defaultDivGroup3);
					this.iapPersonnelDao.flushAndEvict(defaultDivGroup3);					
					
				}

				// get update entity from db
				entity=iapForm203Dao.getById(entity.getId(), IapForm203Impl.class);
				
				// get update vo to return
				vo = IapForm203Vo.getInstance(entity, true);
				
				// get the grid instance for form
				IapGridVo gridVo = IapGridVo.getForm203Instance(entity);

				IapPlan planEntity = this.iapPlanDao.getById(vo.getIapPlanId(), IapPlanImpl.class);
				IapGridVo gridVoParent = IapGridVo.getInstance(planEntity);
				String planName = gridVoParent.getDisplayName();
				gridVo.getHierachalGroupField().add(planName);

				// get number of 203 forms for the parent
				int cnt = 1;
				boolean hasMultiple = false;
				if ( CollectionUtility.hasValue(planEntity.getIapForm203s()) 
						&& planEntity.getIapForm203s().size() > 1){
					cnt = planEntity.getIapForm203s().size();
				}

				if ( cnt > 1) {
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName() + " ( " + cnt + " )");
					gridVo.setFormSequence(cnt);
					gridVo.setHasMultiple(true);
					cnt++;
				} else
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName());
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_FORM_203");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 203 Form was added to the plan."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 203 Form was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204(gov.nwcg.isuite.core.vo.IapForm204Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204(IapForm204Vo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranch entity = null;
			Boolean isNew=true;
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				entity = iapBranchDao.getById(vo.getId(), IapBranchImpl.class);
				iapBranchDao.flushAndEvict(entity);
				isNew=false;
			}

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}

			// run the rule handler
			IapSaveForm204RulesHandler ruleHandler = new IapSaveForm204RulesHandler(super.context);
			if(ruleHandler.execute(vo, null, dialogueVo)==AbstractRule._OK){
				
				if(isNew==true){
					// create default 3 positions
					//Collection<IapBranchPersonnelVo> branchPersonnelVos = IapBranchPersonnelVo.buildDefaultVos(vo);
					//vo.setIapBranchPersonnelVos(branchPersonnelVos);
				}
				
				if(isNew==false){
					entity.setBranchName(StringUtility.toUpper(vo.getBranchName()));
					entity.setDivisionName(StringUtility.toUpper(vo.getDivisionName()));
					entity.setGroupName(StringUtility.toUpper(vo.getGroupName()));
					entity.setStagingArea(StringUtility.toUpper(vo.getStagingArea()));
					entity.setWorkAssignment(vo.getWorkAssignment());
					entity.setSpecialInstructions(vo.getSpecialInstructions());
					entity.setPreparedBy(StringUtility.toUpper(vo.getPreparedBy()));
					entity.setPreparedByPosition(StringUtility.toUpper(vo.getPreparedByPosition()));
					entity.setApprovedBy(StringUtility.toUpper(vo.getApprovedBy()));
					
					if(DateTransferVo.hasDateString(vo.getPreparedDateVo())){
						if(StringUtility.hasValue(vo.getPreparedTime()))
							vo.getPreparedDateVo().setTimeString(vo.getPreparedTime());
						Date dt = DateTransferVo.getTransferDate(vo.getPreparedDateVo());
						entity.setPreparedDate(dt);
					}else{
						entity.setPreparedDate(null);
					}
				}else{
					// update entity from vo and save
					entity = IapForm204Vo.toEntity(entity, vo, true,(null != entity ? entity.getIapPlan() : null));
				}
				iapBranchDao.save(entity);
				if(CollectionUtility.hasValue(entity.getIapBranchCommSummaries())){
					for(IapBranchCommSummary comm : entity.getIapBranchCommSummaries()){
						iapForm205Dao.flushAndEvict(comm);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapBranchPersonnels())){
					for(IapBranchPersonnel p : entity.getIapBranchPersonnels()){
						iapForm205Dao.flushAndEvict(p);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapBranchRscAssigns())){
					for(IapBranchRscAssign r : entity.getIapBranchRscAssigns()){
						iapForm205Dao.flushAndEvict(r);
					}
				}
				iapBranchDao.flushAndEvict(entity);

				if(isNew==true){
					// add default 204 positions from template
					this.createDefaut204Positions(entity.getId(),vo.getIapPlanId());
				}
				
				entity = iapBranchDao.getById(entity.getId(), IapBranchImpl.class);
				
				// get update vo to return
				vo = IapForm204Vo.getInstance(entity, true);
				
				// get the grid instance for form
				IapGridVo gridVo = IapGridVo.getForm204Instance(entity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_FORM_204");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Form was added to the plan."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Form was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm205(gov.nwcg.isuite.core.vo.IapForm205Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm205(IapForm205Vo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm205 entity = null;
			Boolean isNew=true;
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				entity = iapForm205Dao.getById(vo.getId(), IapForm205Impl.class);
				iapForm205Dao.flushAndEvict(entity);
				isNew=false;

				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapSaveForm205RulesHandler ruleHandler = new IapSaveForm205RulesHandler(super.context);
			if(ruleHandler.execute(vo, null, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				entity = IapForm205Vo.toEntity(entity, vo, true,(null != entity ? entity.getIapPlan() : null));
				iapForm205Dao.save(entity);
				if(CollectionUtility.hasValue(entity.getIapFrequencies())){
					for(IapFrequency freq : entity.getIapFrequencies()){
						iapForm205Dao.flushAndEvict(freq);
					}
				}
				iapForm205Dao.flushAndEvict(entity);
				
				entity = iapForm205Dao.getById(entity.getId(), IapForm205Impl.class);
				
				// get update vo to return
				vo = IapForm205Vo.getInstance(entity, true);
				
				// get the grid instance for form
				IapGridVo gridVo = IapGridVo.getForm205Instance(entity);
				IapPlan planEntity = this.iapPlanDao.getById(vo.getIapPlanId(), IapPlanImpl.class);
				IapGridVo gridVoParent = IapGridVo.getInstance(planEntity);
				String planName = gridVoParent.getDisplayName();
				gridVo.getHierachalGroupField().add(planName);

				// get number of 205 forms for the parent
				int cnt = 1;
				boolean hasMultiple = false;
				if ( CollectionUtility.hasValue(planEntity.getIapForm205s()) 
						&& planEntity.getIapForm205s().size() > 1){
					cnt = planEntity.getIapForm205s().size();
				}

				if ( cnt > 1) {
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName() + " ( " + cnt + " )");
					gridVo.setFormSequence(cnt);
					gridVo.setHasMultiple(true);
					cnt++;
				} else
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName());
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_FORM_205");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 205 Form was added to the plan."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 205 Form was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo saveIapForm205Frequency(Long iapForm205Id, IapFrequencyVo frequencyVo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm205 entity = null;
			IapFrequency frequencyEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm205Id)){
				entity = iapForm205Dao.getById(iapForm205Id, IapForm205Impl.class);
				iapForm205Dao.flushAndEvict(entity);
				
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(frequencyVo.getId())){
				frequencyEntity = iapFrequencyDao.getById(frequencyVo.getId(), IapFrequencyImpl.class);
				iapFrequencyDao.flushAndEvict(frequencyEntity);
				isNew=false;
			}

			// run the rule handler
			IapSaveFrequencyRulesHandler ruleHandler = new IapSaveFrequencyRulesHandler(super.context);
			if(ruleHandler.execute(frequencyVo, frequencyEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				frequencyEntity = IapFrequencyVo.toEntity(frequencyEntity, frequencyVo, true,(null != entity ? entity : null));
				iapFrequencyDao.save(frequencyEntity);
				iapFrequencyDao.flushAndEvict(frequencyEntity);
				
				IapFrequencyVo returnVo = IapFrequencyVo.getInstance(frequencyEntity, true);
				Collection<IapFrequencyVo> vos = new ArrayList<IapFrequencyVo>();

				if( BooleanUtility.isTrue(returnVo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm205Dao.getById(iapForm205Id, IapForm205Impl.class);
					for ( IapFrequency iapFrequency : entity.getIapFrequencies()) {
							if ( iapFrequency.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& iapFrequency.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( iapFrequency.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									iapFrequency.setPositionNum(iapFrequency.getPositionNum() + 1);
									iapFrequencyDao.save(iapFrequency);
									iapFrequencyDao.flushAndEvict(iapFrequency);
								}
							}
					}
				}
				// get updated vos
				IapForm205 iapForm205Entity= iapForm205Dao.getById(iapForm205Id, IapForm205Impl.class);
				IapForm205Vo iapForm205Vo = IapForm205Vo.getInstance(iapForm205Entity, true);
				vos = iapForm205Vo.getIapFrequencieVos();

				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_205_FREQUENCY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 205 Frequency was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 205 Frequency was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo verifyFrequencies(Long iapPlanId, Long iapForm205Id, IapFrequencyVo frequencyVo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		try {
			List<String> freq205To204 = iapForm205Dao.verifyFrequenciesFrom205To204(iapPlanId, iapForm205Id);
			List<String> freq204To205 = iapForm205Dao.verifyFrequenciesFrom204To205(iapPlanId, iapForm205Id);
			
			StringBuffer  bufFreq205To204 = new StringBuffer();
			StringBuffer  bufFreq204To205 = new StringBuffer();
			
			bufFreq205To204.append("Frequencies on this ICS205 that are not on the ICS204: ");
			
			if (freq205To204.size() > 0) {
				for (String frequency : freq205To204) {
					//System.out.println("freq205To204: " + frequency);
					bufFreq205To204.append(frequency).append(" ");
				}
			}
			else {
				    bufFreq205To204.append("None ");
			}
			
			bufFreq204To205.append("Frequencies on the ICS204 that are not on this ICS205: ");
			
			if (freq204To205.size() > 0) {
				for (String frequency : freq204To205) {
					//System.out.println("freq204To205: " + frequency);
					bufFreq204To205.append(frequency).append(" ");
				}
			}
			else {
				bufFreq204To205.append("None ");
			}		
			
			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("VERIFY_FREQUENCIES");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.9917",
					new String[] { bufFreq205To204.toString(), bufFreq204To205.toString() },
					MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(frequencyVo);

			dialogueVo.setCourseOfActionVo(coaVo);
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}	

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#addIapForm205Frequencies(java.lang.Long, java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo addIapForm205Frequencies(Long iapForm205Id, Collection<IapFrequencyVo> frequencyVos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm205 entity = null;

			if(LongUtility.hasValue(iapForm205Id)){
				entity = iapForm205Dao.getById(iapForm205Id, IapForm205Impl.class);
				iapForm205Dao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			Collection<IapFrequencyVo> vos = new ArrayList<IapFrequencyVo>();
			
			for(IapFrequencyVo v : frequencyVos){
				
				// update entity from vo and save
				IapFrequency frequencyEntity = IapFrequencyVo.toEntity(null, v, true,(null != entity ? entity : null));
				iapFrequencyDao.save(frequencyEntity);
				iapFrequencyDao.flushAndEvict(frequencyEntity);
			}				

			// get updated vos
			IapForm205 iapForm205Entity= iapForm205Dao.getById(iapForm205Id, IapForm205Impl.class);
			IapForm205Vo iapForm205Vo = IapForm205Vo.getInstance(iapForm205Entity, true);
			vos = iapForm205Vo.getIapFrequencieVos();
			
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ADD_FORM_205_FREQUENCIES");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The Master Frequencies were added to the 205 form."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setRecordset(vos);
				
				dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo deleteIapForm205Frequency(IapFrequencyVo frequencyVo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapFrequency frequencyEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(frequencyVo.getId())){
				frequencyEntity = iapFrequencyDao.getById(frequencyVo.getId(), IapFrequencyImpl.class);
				iapFrequencyDao.flushAndEvict(frequencyEntity);
				
				IapForm205 iapForm=this.iapForm205Dao.getById(frequencyEntity.getIapForm205Id(),IapForm205Impl.class);
				iapForm205Dao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteFrequencyRulesHandler ruleHandler = new IapDeleteFrequencyRulesHandler(super.context);
			if(ruleHandler.execute(frequencyVo, dialogueVo)==AbstractRule._OK){
				
				Long formId = frequencyVo.getIapForm205Id();
				int positionNum = frequencyVo.getPositionNum().intValue();
				
				iapFrequencyDao.delete(frequencyEntity);
				iapFrequencyDao.flushAndEvict(frequencyEntity);
				
				// reorder the positions based on deleted position
				IapForm205 form205 = this.iapForm205Dao.getById(formId, IapForm205Impl.class);

				for ( IapFrequency iapFrequency : form205.getIapFrequencies()) {
						if ( iapFrequency.getPositionNum().intValue() > positionNum) {
							// decrease positionNum by 1
							iapFrequency.setPositionNum(iapFrequency.getPositionNum() - 1);
							iapFrequencyDao.save(iapFrequency);
							iapFrequencyDao.flushAndEvict(iapFrequency);
						}
				}
				iapForm205Dao.flushAndEvict(form205);

				// get updated vos
				Collection<IapFrequencyVo> vos = new ArrayList<IapFrequencyVo>();
				IapForm205 iapForm205Entity= iapForm205Dao.getById(frequencyVo.getIapForm205Id(), IapForm205Impl.class);
				IapForm205Vo iapForm205Vo = IapForm205Vo.getInstance(iapForm205Entity, true);
				vos = iapForm205Vo.getIapFrequencieVos();
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_205_FREQUENCY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 205 Frequency was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(frequencyVo);
				dialogueVo.setRecordset(vos);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206(gov.nwcg.isuite.core.vo.IapForm206Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206(IapForm206Vo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm206 entity = null;
			Boolean isNew=true;
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				entity = iapForm206Dao.getById(vo.getId(), IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(entity);
				isNew=false;
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			// run the rule handler
			IapSaveForm206RulesHandler ruleHandler = new IapSaveForm206RulesHandler(super.context);
			if(ruleHandler.execute(vo, null, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				entity = IapForm206Vo.toEntity(entity, vo, true,(null != entity ? entity.getIapPlan() : null));
				iapForm206Dao.save(entity);
				if(CollectionUtility.hasValue(entity.getIapMedicalAids())){
					for(IapMedicalAid ima : entity.getIapMedicalAids()){
						iapForm206Dao.flushAndEvict(ima);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapAreaLocationCapabilities())){
					for(IapAreaLocationCapability alc : entity.getIapAreaLocationCapabilities()){
						iapForm206Dao.flushAndEvict(alc);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapHospitals())){
					for(IapHospital h : entity.getIapHospitals()){
						iapForm206Dao.flushAndEvict(h);
					}
				}
				if(CollectionUtility.hasValue(entity.getIapRemoteCampLocations())){
					for(IapRemoteCampLocations rcl : entity.getIapRemoteCampLocations()){
						iapForm206Dao.flushAndEvict(rcl);
					}
				}
				iapForm206Dao.flushAndEvict(entity);

				entity = iapForm206Dao.getById(entity.getId(), IapForm206Impl.class);
				
				// get update vo to return
				vo = IapForm206Vo.getInstance(entity, true);
				
				// get the grid instance for form
				IapGridVo gridVo = IapGridVo.getForm206Instance(entity);
				IapPlan planEntity = this.iapPlanDao.getById(vo.getIapPlanId(), IapPlanImpl.class);
				IapGridVo gridVoParent = IapGridVo.getInstance(planEntity);
				String planName = gridVoParent.getDisplayName();
				gridVo.getHierachalGroupField().add(planName);

				// get number of 206 forms for the parent
				int cnt = 1;
				boolean hasMultiple = false;
				if ( CollectionUtility.hasValue(planEntity.getIapForm206s()) 
						&& planEntity.getIapForm206s().size() > 1){
					cnt = planEntity.getIapForm206s().size();
				}

				if ( cnt > 1) {
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName() + " ( " + cnt + " )");
					gridVo.setFormSequence(cnt);
					gridVo.setHasMultiple(true);
					cnt++;
				} else
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName());
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_FORM_206");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Form was added to the plan."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Form was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm220(gov.nwcg.isuite.core.vo.IapForm220Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm220(IapForm220Vo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm220 entity = null;
			Boolean isNew=true;
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				entity = iapForm220Dao.getById(vo.getId(), IapForm220Impl.class);
				iapForm220Dao.flushAndEvict(entity);
				isNew=false;
				
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			// run the rule handler
			IapSaveForm220RulesHandler ruleHandler = new IapSaveForm220RulesHandler(super.context);
			if(ruleHandler.execute(vo, null, dialogueVo)==AbstractRule._OK){
				
				if(isNew==true){
					// create 11 default personnel placeholder records
					Collection<IapPersonnelVo> personnelVos = IapPersonnelVo.buildDefaultVos(vo);
					vo.setIapPersonnelVos(personnelVos);

					// create 12 default iap aircraft fixed wing placeholder records
					Collection<IapAircraftVo> fixedWingVos = IapAircraftVo.buildDefaultFixedWingVos(vo);
					vo.setIapFixedWingVos(fixedWingVos);
					vo.setIapAircraftVos(fixedWingVos);
					
					// create 12 default iap aircraft frequency placeholder records
					Collection<IapAircraftFrequencyVo> frequencyVos = IapAircraftFrequencyVo.buildDefaultVos(vo); 
					vo.setIapAircraftFrequencyVos(frequencyVos);
				}
				
				// update entity from vo and save
				entity = IapForm220Vo.toEntity(entity, vo, true,(null != entity ? entity.getIapPlan() : null));
				iapForm220Dao.save(entity);
				iapForm220Dao.flushAndEvict(entity);
				entity = iapForm220Dao.getById(entity.getId(), IapForm220Impl.class);
				
				// get update vo to return
				vo = IapForm220Vo.getInstance(entity, true);
				
				// get the grid instance for form
				IapGridVo gridVo = IapGridVo.getForm220Instance(entity);
				
				IapPlan planEntity = this.iapPlanDao.getById(vo.getIapPlanId(), IapPlanImpl.class);
				IapGridVo gridVoParent = IapGridVo.getInstance(planEntity);
				String planName = gridVoParent.getDisplayName();
				gridVo.getHierachalGroupField().add(planName);

				// get number of 220 forms for the parent
				int cnt = 1;
				boolean hasMultiple = false;
				if ( CollectionUtility.hasValue(planEntity.getIapForm220s()) 
						&& planEntity.getIapForm220s().size() > 1){
					cnt = planEntity.getIapForm220s().size();
				}

				if ( cnt > 1) {
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName() + " ( " + cnt + " )");
					gridVo.setFormSequence(cnt);
					gridVo.setHasMultiple(true);
					cnt++;
				} else
					gridVo.getHierachalGroupField().add(gridVo.getDisplayName());

				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_FORM_220");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Form was added to the plan."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Form was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(gridVo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveAttachment(gov.nwcg.isuite.core.vo.IapAttachmentVo, byte[], gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveAttachment(IapAttachmentVo vo, byte[] pdfByteArray, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}

			// run the rule handler
			IapSaveAttachmentRulesHandler ruleHandler = new IapSaveAttachmentRulesHandler(context);
			if(ruleHandler.execute(vo, pdfByteArray,dialogueVo)==AbstractRule._OK){
				// write bytes to file if saving new
				if(!LongUtility.hasValue(vo.getId())){
					String filename=this.getDestinationFileName("iapAttach_");
					vo.setFilename(filename);
					if(null != pdfByteArray){
						String pdfFile=this.getOutputFile(filename);
						FileUtil.writeFile(pdfByteArray, pdfFile);
					}
				}
				
				// load the entity if saving existing
				IapAttachment entity = null;
				if(LongUtility.hasValue(vo.getId()))
					entity=iapAttachmentDao.getById(vo.getId(), IapAttachmentImpl.class);
				
				// update the entity
				entity = IapAttachmentVo.toEntity(entity, vo, true);

				// save
				iapAttachmentDao.save(entity);
				iapAttachmentDao.flushAndEvict(entity);

				// get updated return object
				vo=IapAttachmentVo.getInstance(entity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_ATTACHMENT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Attachment was added to the plan."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteAttachment(gov.nwcg.isuite.core.vo.IapAttachmentVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAttachment(IapAttachmentVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			if(null == vo || !LongUtility.hasValue(vo.getId()))
				throw new Exception("Cannot delete unknown attachment reference");
			
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(vo.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			// delete the entity
			IapAttachment entity = iapAttachmentDao.getById(vo.getId(), IapAttachmentImpl.class);
			String filename=entity.getFilename();
			if(null != entity)
				iapAttachmentDao.delete(entity);
			
			// delete the file
			FileUtil.deleteFile(this.getOutputFile(filename));
			
			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_IAP_ATTACHMENT");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP Attachment was deleted from the plan."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(vo);
			
			dialogueVo.setCourseOfActionVo(coaVo);				
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#validate220Helicopter(gov.nwcg.isuite.core.vo.IapAircraftVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo validate220Helicopter(IapAircraftVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			// run the rule handler
			IapSaveForm220RulesHandler ruleHandler = new IapSaveForm220RulesHandler(super.context);
			if(ruleHandler.validateHelicopter(vo, dialogueVo)==AbstractRule._OK){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("VALIDATE_220_HELICOPTER");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206Ambulance(java.lang.Long, gov.nwcg.isuite.core.vo.IapMedicalAidVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206Ambulance(Long iapForm206Id, IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm206 entity = null;
			IapMedicalAid medicalAidEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm206Id)){
				entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(entity);
				
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				medicalAidEntity = this.iapMedicalAidDao.getById(vo.getId(), IapMedicalAidImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveAmbulanceRulesHandler ruleHandler = new IapSaveAmbulanceRulesHandler(super.context);
			if(ruleHandler.execute(vo, medicalAidEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				medicalAidEntity = IapMedicalAidVo.toEntity(medicalAidEntity, vo, true,(null != entity ? entity : null));
				iapMedicalAidDao.save(medicalAidEntity);
								
				iapMedicalAidDao.flushAndEvict(medicalAidEntity.getAddress());
				iapMedicalAidDao.flushAndEvict(medicalAidEntity);
				

				// get update vo to return
				IapMedicalAidVo returnVo = IapMedicalAidVo.getInstance(medicalAidEntity, true);
				Collection<IapMedicalAidVo> vos = new ArrayList<IapMedicalAidVo>();

				if( BooleanUtility.isTrue(vo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
					for ( IapMedicalAid iapMedicalAid: entity.getIapMedicalAids()) {
						if ( iapMedicalAid.getType().equalsIgnoreCase("AMBULANCE")){
							if ( iapMedicalAid.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& iapMedicalAid.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( iapMedicalAid.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									iapMedicalAid.setPositionNum(iapMedicalAid.getPositionNum() + 1);
									iapMedicalAidDao.save(iapMedicalAid);
									iapMedicalAidDao.flushAndEvict(iapMedicalAid);
								}
							}
						}
					}
				}
				// get updated vos
				IapForm206 iapForm206Entity= iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapAmbulanceVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);

				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_206_AMBULANCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Ambulance was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Ambulance was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm206Ambulance(gov.nwcg.isuite.core.vo.IapMedicalAidVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm206Ambulance(IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapMedicalAid medicalAidEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				medicalAidEntity = iapMedicalAidDao.getById(vo.getId(), IapMedicalAidImpl.class);
				iapMedicalAidDao.flushAndEvict(medicalAidEntity);
				
				IapForm206 iapForm=this.iapForm206Dao.getById(medicalAidEntity.getIapForm206Id(),IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteAmbulanceRulesHandler ruleHandler = new IapDeleteAmbulanceRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapMedicalAidDao.delete(medicalAidEntity);
				iapMedicalAidDao.flushAndEvict(medicalAidEntity);

				// reorder the positions based on deleted position
				IapForm206 iapForm206 = iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				for ( IapMedicalAid iapMedicalAid : iapForm206.getIapMedicalAids()) {
					if (iapMedicalAid.getType().equalsIgnoreCase("AMBULANCE")){
						if ( iapMedicalAid.getPositionNum().intValue() > vo.getPositionNum().intValue()) {
							// increment positionNum + 1
							iapMedicalAid.setPositionNum(iapMedicalAid.getPositionNum() - 1);
							iapMedicalAidDao.save(iapMedicalAid);
							iapMedicalAidDao.flushAndEvict(iapMedicalAid);
						}
					}
				}
				iapForm206Dao.flushAndEvict(iapForm206);

				// get updated vos
				Collection<IapMedicalAidVo> vos = new ArrayList<IapMedicalAidVo>();
				IapForm206 iapForm206Entity= iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapAmbulanceVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);

				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_206_AMBULANCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Ambulance was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);

				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206AirAmbulance(java.lang.Long, gov.nwcg.isuite.core.vo.IapMedicalAidVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206AirAmbulance(Long iapForm206Id, IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm206 entity = null;
			IapMedicalAid medicalAidEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm206Id)){
				entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(entity);

				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}

			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				medicalAidEntity = this.iapMedicalAidDao.getById(vo.getId(), IapMedicalAidImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveAirAmbulanceRulesHandler ruleHandler = new IapSaveAirAmbulanceRulesHandler(super.context);
			if(ruleHandler.execute(vo, medicalAidEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				medicalAidEntity = IapMedicalAidVo.toEntity(medicalAidEntity, vo, true,(null != entity ? entity : null));
				iapMedicalAidDao.save(medicalAidEntity);
				iapMedicalAidDao.flushAndEvict(medicalAidEntity);
				
				// get update vo to return
				IapMedicalAidVo returnVo = IapMedicalAidVo.getInstance(medicalAidEntity, true);
				Collection<IapMedicalAidVo> vos = new ArrayList<IapMedicalAidVo>();

				if( BooleanUtility.isTrue(vo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
					for ( IapMedicalAid iapMedicalAid: entity.getIapMedicalAids()) {
						if ( iapMedicalAid.getType().equalsIgnoreCase("AIRAMBULANCE")){
							if ( iapMedicalAid.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& iapMedicalAid.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( iapMedicalAid.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									iapMedicalAid.setPositionNum(iapMedicalAid.getPositionNum() + 1);
									iapMedicalAidDao.save(iapMedicalAid);
									iapMedicalAidDao.flushAndEvict(iapMedicalAid);
								}
							}
						}
					}
				}
				// get updated vos
				IapForm206 iapForm206Entity= iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapAirAmbulanceVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_206_AIR_AMBULANCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Air Ambulance was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Air Ambulance was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm206AirAmbulance(gov.nwcg.isuite.core.vo.IapMedicalAidVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm206AirAmbulance(IapMedicalAidVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapMedicalAid medicalAidEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				medicalAidEntity = iapMedicalAidDao.getById(vo.getId(), IapMedicalAidImpl.class);
				iapMedicalAidDao.flushAndEvict(medicalAidEntity);
				
				IapForm206 iapForm=this.iapForm206Dao.getById(medicalAidEntity.getIapForm206Id(),IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteAirAmbulanceRulesHandler ruleHandler = new IapDeleteAirAmbulanceRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapMedicalAidDao.delete(medicalAidEntity);
				iapMedicalAidDao.flushAndEvict(medicalAidEntity);

				// reorder the positions based on deleted position
				IapForm206 iapForm206 = iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				for ( IapMedicalAid iapMedicalAid : iapForm206.getIapMedicalAids()) {
					if (iapMedicalAid.getType().equalsIgnoreCase("AIRAMBULANCE")){
						if ( iapMedicalAid.getPositionNum().intValue() > vo.getPositionNum().intValue()) {
							// increment positionNum + 1
							iapMedicalAid.setPositionNum(iapMedicalAid.getPositionNum() - 1);
							iapMedicalAidDao.save(iapMedicalAid);
							iapMedicalAidDao.flushAndEvict(iapMedicalAid);
						}
					}
				}
				iapForm206Dao.flushAndEvict(iapForm206);

				// get updated vos
				Collection<IapMedicalAidVo> vos = new ArrayList<IapMedicalAidVo>();
				IapForm206 iapForm206Entity= iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapAirAmbulanceVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_206_AIR_AMBULANCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Air Ambulance was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206Hospital(java.lang.Long, gov.nwcg.isuite.core.vo.IapHospitalVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206Hospital(Long iapForm206Id, IapHospitalVo hospitalVo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm206 entity = null;
			IapHospital hospitalEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm206Id)){
				entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(entity);
				
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(hospitalVo.getId())){
				isNew=false;
			}

			// run the rule handler
			IapSaveHospitalRulesHandler ruleHandler = new IapSaveHospitalRulesHandler(super.context);
			if(ruleHandler.execute(hospitalVo, hospitalEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				hospitalEntity = IapHospitalVo.toEntity(hospitalEntity, hospitalVo, true,(null != entity ? entity : null));
				iapHospitalDao.save(hospitalEntity);
				
				iapHospitalDao.flushAndEvict(hospitalEntity.getAddress());
				iapHospitalDao.flushAndEvict(hospitalEntity);
				
				
				// get update vo to return
				IapHospitalVo returnVo = IapHospitalVo.getInstance(hospitalEntity, true);
				Collection<IapHospitalVo> vos = new ArrayList<IapHospitalVo>();

				if( BooleanUtility.isTrue(returnVo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
					for ( IapHospital hospital: entity.getIapHospitals()) {
							if ( hospital.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& hospital.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( hospital.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									hospital.setPositionNum(hospital.getPositionNum() + 1);
									iapHospitalDao.save(hospital);
									iapHospitalDao.flushAndEvict(hospital);
								}
							}
					}
				}
				// get updated vos
				IapForm206 iapForm206Entity= iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapHospitalVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);

			
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_206_HOSPITAL");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Hospital was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Hospital was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo deleteIapForm206Hospital(IapHospitalVo hospitalVo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapHospital hospitalEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(hospitalVo.getId())){
				hospitalEntity = iapHospitalDao.getById(hospitalVo.getId(), IapHospitalImpl.class);
				iapHospitalDao.flushAndEvict(hospitalEntity);
				
				IapForm206 iapForm=this.iapForm206Dao.getById(hospitalEntity.getIapForm206Id(),IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteHospitalRulesHandler ruleHandler = new IapDeleteHospitalRulesHandler(super.context);
			if(ruleHandler.execute(hospitalVo, dialogueVo)==AbstractRule._OK){
				
				iapHospitalDao.delete(hospitalEntity);
				iapHospitalDao.flushAndEvict(hospitalEntity);

				// reorder the positions based on deleted position
				IapForm206 iapForm206 = iapForm206Dao.getById(hospitalVo.getIapForm206Id(), IapForm206Impl.class);
				for ( IapHospital iapHospital : iapForm206.getIapHospitals()) {
						if ( iapHospital.getPositionNum().intValue() > hospitalVo.getPositionNum().intValue()) {
							// increment positionNum + 1
							iapHospital.setPositionNum(iapHospital.getPositionNum() - 1);
							iapHospitalDao.save(iapHospital);
							iapHospitalDao.flushAndEvict(iapHospital);
						}
				}
				iapForm206Dao.flushAndEvict(iapForm206);

				// get updated vos
				Collection<IapHospitalVo> vos = new ArrayList<IapHospitalVo>();
				IapForm206 iapForm206Entity= iapForm206Dao.getById(hospitalVo.getIapForm206Id(), IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapHospitalVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);

				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_206_HOSPITAL");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Hospital was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(hospitalVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm220Helicopter(java.lang.Long, gov.nwcg.isuite.core.vo.IapAircraftVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm220Helicopter(Long iapForm220Id, IapAircraftVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm220 entity = null;
			IapAircraft aircraftEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm220Id)){
				entity = iapForm220Dao.getById(iapForm220Id, IapForm220Impl.class);
				iapForm220Dao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				aircraftEntity = this.iapAircraftDao.getById(vo.getId(), IapAircraftImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveHelicopterRulesHandler ruleHandler = new IapSaveHelicopterRulesHandler(super.context);
			if(ruleHandler.execute(vo, aircraftEntity, dialogueVo)==AbstractRule._OK){

				// update entity from vo and save
				aircraftEntity = IapAircraftVo.toEntity(aircraftEntity, vo, true,(null != entity ? entity : null));
				iapAircraftDao.save(aircraftEntity);
				iapAircraftDao.flushAndEvict(aircraftEntity);
				
				// get update vo to return
				IapAircraftVo returnVo = IapAircraftVo.getInstance(aircraftEntity, true);
				Collection<IapAircraftVo> vos = new ArrayList<IapAircraftVo>();

				if( BooleanUtility.isTrue(vo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm220Dao.getById(iapForm220Id, IapForm220Impl.class);
					for ( IapAircraft iapAircraft : entity.getIapAircrafts()) {
						if ( iapAircraft.getWingType().equalsIgnoreCase(vo.getWingType())) {
							if ( iapAircraft.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& iapAircraft.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( iapAircraft.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									iapAircraft.setPositionNum(iapAircraft.getPositionNum() + 1);
									iapAircraftDao.save(iapAircraft);
									iapAircraftDao.flushAndEvict(iapAircraft);
								}
							}
						}
					}
				}
				// get updated vos
				IapForm220 iapForm220Entity= iapForm220Dao.getById(iapForm220Id, IapForm220Impl.class);
				IapForm220Vo iapForm220Vo = IapForm220Vo.getInstance(iapForm220Entity, true);
				vos = iapForm220Vo.getIapHelicopterVos();

				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_220_HELICOPTER");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Helicopter was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Helicopter was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);

				if (CollectionUtility.hasValue(vos)){
					dialogueVo.setRecordset(vos);
				}
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm220Helicopter(gov.nwcg.isuite.core.vo.IapAircraftVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm220Helicopter(IapAircraftVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapAircraft aircraftEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				aircraftEntity = iapAircraftDao.getById(vo.getId(), IapAircraftImpl.class);
				iapAircraftDao.flushAndEvict(aircraftEntity);
				if(LongUtility.hasValue(aircraftEntity.getIapForm220Id())){
					Long form220Id=aircraftEntity.getIapForm220Id();
					IapForm220 iapForm220=this.iapForm220Dao.getById(form220Id,IapForm220Impl.class);
					iapForm220Dao.flushAndEvict(iapForm220);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm220.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
			}

			// run the rule handler
			IapDeleteHelicopterRulesHandler ruleHandler = new IapDeleteHelicopterRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapAircraftDao.delete(aircraftEntity);
				iapAircraftDao.flushAndEvict(aircraftEntity);

				
				// reorder the positions based on deleted position
				IapForm220 iapForm220 = iapForm220Dao.getById(vo.getIapForm220Id(), IapForm220Impl.class);
				for ( IapAircraft iapAircraft : iapForm220.getIapAircrafts()) {
					if ( iapAircraft.getWingType().equalsIgnoreCase(vo.getWingType())) {
						if ( iapAircraft.getPositionNum().intValue() > vo.getPositionNum().intValue()) {
							// increment positionNum + 1
							iapAircraft.setPositionNum(iapAircraft.getPositionNum() - 1);
							iapAircraftDao.save(iapAircraft);
							iapAircraftDao.flushAndEvict(iapAircraft);
						}
					}
				}
				iapForm220Dao.flushAndEvict(iapForm220);

				// get updated vos
				Collection<IapAircraftVo> vos = new ArrayList<IapAircraftVo>();
				IapForm220 iapForm220Entity= iapForm220Dao.getById(vo.getIapForm220Id(), IapForm220Impl.class);
				IapForm220Vo iapForm220Vo = IapForm220Vo.getInstance(iapForm220Entity, true);
				vos = iapForm220Vo.getIapHelicopterVos();
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_220_HELICOPTER");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Helicopter was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm220Task(java.lang.Long, gov.nwcg.isuite.core.vo.IapAircraftTaskVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm220Task(Long iapForm220Id, IapAircraftTaskVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm220 entity = null;
			IapAircraftTask aircraftTaskEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm220Id)){
				entity = iapForm220Dao.getById(iapForm220Id, IapForm220Impl.class);
				iapForm220Dao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				aircraftTaskEntity = this.iapAircraftTaskDao.getById(vo.getId(), IapAircraftTaskImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveTaskRulesHandler ruleHandler = new IapSaveTaskRulesHandler(super.context);
			if(ruleHandler.execute(vo, aircraftTaskEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				aircraftTaskEntity = IapAircraftTaskVo.toEntity(aircraftTaskEntity, vo, true,(null != entity ? entity : null));
				iapAircraftTaskDao.save(aircraftTaskEntity);
				iapAircraftTaskDao.flushAndEvict(aircraftTaskEntity);
				
				// get update vo to return
				IapAircraftTaskVo returnVo = IapAircraftTaskVo.getInstance(aircraftTaskEntity, true);
				Collection<IapAircraftTaskVo> vos = new ArrayList<IapAircraftTaskVo>();

				if( BooleanUtility.isTrue(vo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm220Dao.getById(iapForm220Id, IapForm220Impl.class);
					for ( IapAircraftTask iapAircraftTask : entity.getIapAircraftTasks()) {
							if ( iapAircraftTask.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& iapAircraftTask.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( iapAircraftTask.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									iapAircraftTask.setPositionNum(iapAircraftTask.getPositionNum() + 1);
									iapAircraftTaskDao.save(iapAircraftTask);
									iapAircraftTaskDao.flushAndEvict(iapAircraftTask);
								}
							}
					}
				}
				// get updated vos
				IapForm220 iapForm220Entity= iapForm220Dao.getById(iapForm220Id, IapForm220Impl.class);
				IapForm220Vo iapForm220Vo = IapForm220Vo.getInstance(iapForm220Entity, true);
				vos = iapForm220Vo.getIapAircraftTaskVos();
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_220_TASK");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Task was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Task was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm220Task(gov.nwcg.isuite.core.vo.IapAircraftTaskVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm220Task(IapAircraftTaskVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapAircraftTask aircraftTaskEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				aircraftTaskEntity = iapAircraftTaskDao.getById(vo.getId(), IapAircraftTaskImpl.class);
				iapAircraftTaskDao.flushAndEvict(aircraftTaskEntity);
				if(LongUtility.hasValue(aircraftTaskEntity.getIapForm220Id())){
					Long form220Id=aircraftTaskEntity.getIapForm220Id();
					IapForm220 iapForm220=this.iapForm220Dao.getById(form220Id,IapForm220Impl.class);
					iapForm220Dao.flushAndEvict(iapForm220);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm220.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
			}

			// run the rule handler
			IapDeleteTaskRulesHandler ruleHandler = new IapDeleteTaskRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapAircraftTaskDao.delete(aircraftTaskEntity);
				iapAircraftTaskDao.flushAndEvict(aircraftTaskEntity);

				
				// reorder the positions based on deleted position
				IapForm220 iapForm220 = iapForm220Dao.getById(vo.getIapForm220Id(), IapForm220Impl.class);
				for ( IapAircraftTask iapAircraftTask : iapForm220.getIapAircraftTasks()) {
						if ( iapAircraftTask.getPositionNum().intValue() > vo.getPositionNum().intValue()) {
							// increment positionNum + 1
							iapAircraftTask.setPositionNum(iapAircraftTask.getPositionNum() - 1);
							iapAircraftTaskDao.save(iapAircraftTask);
							iapAircraftTaskDao.flushAndEvict(iapAircraftTask);
						}
				}
				iapForm220Dao.flushAndEvict(iapForm220);

				// get updated vos
				Collection<IapAircraftTaskVo> vos = new ArrayList<IapAircraftTaskVo>();
				IapForm220 iapForm220Entity= iapForm220Dao.getById(vo.getIapForm220Id(), IapForm220Impl.class);
				IapForm220Vo iapForm220Vo = IapForm220Vo.getInstance(iapForm220Entity, true);
				vos = iapForm220Vo.getIapAircraftTaskVos();
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_220_TASK");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 220 Task was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204OpPersonnel(java.lang.Long, gov.nwcg.isuite.core.vo.IapBranchPersonnelVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204OpPersonnel(Long iapForm204Id, IapBranchPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranch entity = null;
			IapBranchPersonnel branchPersonnelEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm204Id)){
				entity = iapBranchDao.getById(iapForm204Id, IapBranchImpl.class);
				iapBranchDao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				branchPersonnelEntity = this.iapBranchPersonnelDao.getById(vo.getId(), IapBranchPersonnelImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveBranchPersonnelRulesHandler ruleHandler = new IapSaveBranchPersonnelRulesHandler(super.context);
			if(ruleHandler.execute(vo, branchPersonnelEntity, iapForm204Id,dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				branchPersonnelEntity = IapBranchPersonnelVo.toEntity(branchPersonnelEntity, vo, true,(null != entity ? entity : null));
				iapBranchPersonnelDao.save(branchPersonnelEntity);
				if(null != branchPersonnelEntity.getIncidentResource()){
					iapBranchPersonnelDao.flushAndEvict(branchPersonnelEntity.getIncidentResource().getResource());
					iapBranchPersonnelDao.flushAndEvict(branchPersonnelEntity.getIncidentResource());
				}
				iapBranchPersonnelDao.flushAndEvict(branchPersonnelEntity);
				branchPersonnelEntity = iapBranchPersonnelDao.getById(branchPersonnelEntity.getId(), IapBranchPersonnelImpl.class);
				
				// get update vo to return
				vo = IapBranchPersonnelVo.getInstance(branchPersonnelEntity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_204_OP_PERSONNEL");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Operations Person was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Operations Person was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm204OpPersonnel(gov.nwcg.isuite.core.vo.IapBranchPersonnelVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm204OpPersonnel(IapBranchPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranchPersonnel branchPersonnelEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				branchPersonnelEntity = iapBranchPersonnelDao.getById(vo.getId(), IapBranchPersonnelImpl.class);
				iapBranchPersonnelDao.flushAndEvict(branchPersonnelEntity);
				
				IapBranch iapForm=this.iapBranchDao.getById(branchPersonnelEntity.getIapBranchId(),IapBranchImpl.class);
				iapBranchDao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteBranchPersonnelRulesHandler ruleHandler = new IapDeleteBranchPersonnelRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapBranchPersonnelDao.delete(branchPersonnelEntity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_204_OP_PERSONNEL");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Operation Personnel was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204BranchComm(java.lang.Long, gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204BranchComm(Long iapForm204Id, IapBranchCommSummaryVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranch entity = null;
			IapBranchCommSummary branchCommSummaryEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm204Id)){
				entity = iapBranchDao.getById(iapForm204Id, IapBranchImpl.class);
				iapBranchDao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				branchCommSummaryEntity = this.iapBranchCommSummaryDao.getById(vo.getId(), IapBranchCommSummaryImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveBranchCommRulesHandler ruleHandler = new IapSaveBranchCommRulesHandler(super.context);
			if(ruleHandler.execute(vo, branchCommSummaryEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				branchCommSummaryEntity = IapBranchCommSummaryVo.toEntity(branchCommSummaryEntity, vo, true,(null != entity ? entity : null));
				iapBranchCommSummaryDao.save(branchCommSummaryEntity);
				iapBranchCommSummaryDao.flushAndEvict(branchCommSummaryEntity);
				branchCommSummaryEntity = iapBranchCommSummaryDao.getById(branchCommSummaryEntity.getId(), IapBranchCommSummaryImpl.class);
				
				// get update vo to return
				vo = IapBranchCommSummaryVo.getInstance(branchCommSummaryEntity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_204_COMMUNICATION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Communication was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Communication was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm204BranchComm(gov.nwcg.isuite.core.vo.IapBranchCommSummaryVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm204BranchComm(IapBranchCommSummaryVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranchCommSummary branchCommSummaryEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				branchCommSummaryEntity = iapBranchCommSummaryDao.getById(vo.getId(), IapBranchCommSummaryImpl.class);
				iapBranchCommSummaryDao.flushAndEvict(branchCommSummaryEntity);

				IapBranch iapForm=this.iapBranchDao.getById(branchCommSummaryEntity.getIapBranchId(),IapBranchImpl.class);
				iapBranchDao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
				
			}

			// run the rule handler
			IapDeleteBranchCommRulesHandler ruleHandler = new IapDeleteBranchCommRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapBranchCommSummaryDao.delete(branchCommSummaryEntity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_204_COMMUNICATION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Communication was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204BranchRscAssign(java.lang.Long, gov.nwcg.isuite.core.vo.IapBranchRscAssignVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204BranchRscAssign(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranch entity = null;
			IapBranchRscAssign branchRscAssignEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm204Id)){
				entity = iapBranchDao.getById(iapForm204Id, IapBranchImpl.class);
				iapBranchDao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				branchRscAssignEntity = this.iapBranchRscAssignDao.getById(vo.getId(), IapBranchRscAssignImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveBranchRscAssignRulesHandler ruleHandler = new IapSaveBranchRscAssignRulesHandler(super.context);
			if(ruleHandler.execute(vo, branchRscAssignEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				branchRscAssignEntity = IapBranchRscAssignVo.toEntity(branchRscAssignEntity, vo, true,(null != entity ? entity : null));
				iapBranchRscAssignDao.save(branchRscAssignEntity);
				iapBranchRscAssignDao.flushAndEvict(branchRscAssignEntity);
				branchRscAssignEntity = iapBranchRscAssignDao.getById(branchRscAssignEntity.getId(), IapBranchRscAssignImpl.class);
				
				// get update vo to return
				vo = IapBranchRscAssignVo.getInstance(branchRscAssignEntity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_204_RSC_ASSIGN");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Resource Assigned was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Resource Assigned was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm204BranchRscAssign(gov.nwcg.isuite.core.vo.IapBranchRscAssignVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm204BranchRscAssign(IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranchRscAssign branchRscAssignEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				branchRscAssignEntity = iapBranchRscAssignDao.getById(vo.getId(), IapBranchRscAssignImpl.class);
				iapBranchRscAssignDao.flushAndEvict(branchRscAssignEntity);
				
				IapBranch iapForm=this.iapBranchDao.getById(branchRscAssignEntity.getIapBranchId(),IapBranchImpl.class);
				iapBranchDao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteBranchRscAssignRulesHandler ruleHandler = new IapDeleteBranchRscAssignRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapBranchRscAssignDao.delete(branchRscAssignEntity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_204_RSC_ASSIGN");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Resource Assigned was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm203Position(java.lang.Long, gov.nwcg.isuite.core.vo.IapPersonnelVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm203Position(Long iapForm203Id, IapPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm203 entity = null;
			IapPersonnel personnelEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm203Id)){
				entity = iapForm203Dao.getById(iapForm203Id, IapForm203Impl.class);
				iapForm203Dao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				personnelEntity = this.iapPersonnelDao.getById(vo.getId(), IapPersonnelImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveForm203PositionRulesHandler ruleHandler = new IapSaveForm203PositionRulesHandler(super.context);
			if(ruleHandler.execute(vo, personnelEntity, dialogueVo)==AbstractRule._OK){
				
				IapPersonnel parentBranch=null;
				if(BooleanUtility.isFalse(vo.getIsBranchName())){
					if(LongUtility.hasValue(vo.getIapBranchPersonnelParentId())){
						parentBranch=iapPersonnelDao.getById(vo.getIapBranchPersonnelParentId(), IapPersonnelImpl.class);
						iapPersonnelDao.flushAndEvict(parentBranch);
					}
				}
				
				// update entity from vo and save
				personnelEntity = IapPersonnelVo.toEntity(personnelEntity, vo, true,(null != entity ? entity : null));
				if(null != parentBranch){
					personnelEntity.setIapBranchPersonnelParent(parentBranch);
				}
				iapPersonnelDao.save(personnelEntity);
				iapPersonnelDao.flushAndEvict(personnelEntity);
				
				// if isnew==true and isbranchname=true, create default empty positions
				if(BooleanUtility.isTrue(isNew) && BooleanUtility.isTrue(vo.getIsBranchName())){
					this.createDefaut203BranchPositions(iapForm203Id, personnelEntity, entity.getIapPlanId());
				}
				
				personnelEntity = iapPersonnelDao.getById(personnelEntity.getId(), IapPersonnelImpl.class);
				
				// get update vo to return
				vo = IapPersonnelVo.getInstance(personnelEntity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_203_POSITION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 203 Position was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 203 Position was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm203Positions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm203Positions(Collection<IapPersonnelVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			Collection<IapPersonnelVo> newVos = new ArrayList<IapPersonnelVo>();			
			int cnt=0;
			for(IapPersonnelVo vo : vos){
				if(cnt==0){
					IapForm203 iapForm=this.iapForm203Dao.getById(vo.getIapForm203Id(),IapForm203Impl.class);
					iapBranchDao.flushAndEvict(iapForm);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
					cnt++;
				}
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm203Position(vo.getIapForm203Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapPersonnelVo newVo = (IapPersonnelVo)dvo.getResultObject();
					newVos.add(newVo);
				}
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_203_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo saveIapForm204Positions(Collection<IapBranchPersonnelVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		// Since this function is also called when blank lines are added, the updated
		// vo with the id of the newly saved object needs to be returned back.
		Collection<IapBranchPersonnelVo> updatedVos = new ArrayList<IapBranchPersonnelVo>();
		int cnt=0;
		try{
			for(IapBranchPersonnelVo vo : vos){
				if(cnt==0){
					IapBranch iapForm=this.iapBranchDao.getById(vo.getIapBranchId(),IapBranchImpl.class);
					iapBranchDao.flushAndEvict(iapForm);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
					cnt++;
				}
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm204OpPersonnel(vo.getIapBranchId(), vo, dvo);
				updatedVos.add((IapBranchPersonnelVo) dvo.getResultObject());
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_204_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(updatedVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm203Position(gov.nwcg.isuite.core.vo.IapPersonnelVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm203Position(IapPersonnelVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapPersonnel personnelEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				personnelEntity = iapPersonnelDao.getById(vo.getId(), IapPersonnelImpl.class);
				iapPersonnelDao.flushAndEvict(personnelEntity);
				
				if(LongUtility.hasValue(personnelEntity.getIapForm203Id())){
					Long form203Id=personnelEntity.getIapForm203Id();
					IapForm203 iapForm203=this.iapForm203Dao.getById(form203Id,IapForm203Impl.class);
					iapForm203Dao.flushAndEvict(iapForm203);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm203.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
				if(LongUtility.hasValue(personnelEntity.getIapForm220Id())){
					Long form220Id=personnelEntity.getIapForm220Id();
					IapForm220 iapForm220=this.iapForm220Dao.getById(form220Id,IapForm220Impl.class);
					iapForm220Dao.flushAndEvict(iapForm220);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm220.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
			}

			// run the rule handler
			IapDeleteForm203PositionRulesHandler ruleHandler = new IapDeleteForm203PositionRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapPersonnelDao.delete(personnelEntity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_203_POSITION");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 203 Position was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo addIapForm204RscAssign(Long iapForm204Id, String personNameOrder, 
											Collection<IapBranchRscAssignVo> resourcesBeingAdded,
											DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranch iapBranch = null;
			Long incidentId = null;
			Long incidentGroupId = null;
			IapAddBranchRscAssignRulesHandler rulesHandler = new IapAddBranchRscAssignRulesHandler(context);
			
			Collection<IapBranchRscAssignVo> addedVos = new ArrayList<IapBranchRscAssignVo>();
			
			if(LongUtility.hasValue(iapForm204Id)){
				iapBranch = iapBranchDao.getById(iapForm204Id, IapBranchImpl.class);
				iapBranchDao.flushAndEvict(iapBranch);
				
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapBranch.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
				
				if(iapBranch!= null && iapBranch.getIapPlan()!=null){ 
					if(rulesHandler.execute(iapBranch.getIapPlanId(), resourcesBeingAdded, dialogueVo)==IapAddBranchRscAssignRulesHandler._OK){
						
						incidentId = iapBranch.getIapPlan().getIncidentId();
						incidentGroupId = iapBranch.getIapPlan().getIncidentGroupId();
						
						// Get the final list of resources being added from the COA's saved object.
						// They were saved in the savedObject by the rules handler.
						// If it is null, ie, no duplicates were found by the rules handler, then use the incoming collection - resourcesBeingAdded
						List<IapBranchRscAssignVo> resourcesToAdd = null;
						if(dialogueVo.getCourseOfActionVo()!=null) {
							resourcesToAdd = (List<IapBranchRscAssignVo>)dialogueVo.getCourseOfActionVo().getStoredObject();
						}
						if(resourcesToAdd==null) { 
							resourcesToAdd = (List<IapBranchRscAssignVo>)resourcesBeingAdded;
						}
						
						// Get List of preferences for this incident OR incidentGroup so that the Resource Identifier can be constucted
						Collection preferences = null;
						if(incidentId!=null){
							//incidentPreferences = incidentPrefsService.getByIncidentId(incidentId);
							preferences = incidentPrefsService.getICS204Block5FieldsForIncident(incidentId, Boolean.TRUE);
						} else {
							preferences  = incidentPrefsService.getICS204Block5FieldsForIncidentGroup(incidentGroupId, Boolean.TRUE);
						}

						//Determine if the IAP Settings require the names to be FirstName,Space,LastName
						boolean firstNameLastName=false;
						if(personNameOrder!=null){
							if(PersonsNameOrderEnum.FIRST_NAME_LAST_NAME.equals(PersonsNameOrderEnum.valueOf(personNameOrder))){
								firstNameLastName=true;
							}
						}

						for(IapBranchRscAssignVo v : resourcesToAdd){
											
							// update entity from vo and save
							IapBranchRscAssign branchRscAssignEntity = IapBranchRscAssignVo.toEntity(null, v, true,(null != iapBranch ? iapBranch : null));
							
							// If the order if FirstName>LastName, then set that "First Last" as resource name if this is a person resource. 
							// If this is a non-person resource, do not modify the previously set resource name. 
							String resname=v.getResourceName();
							if(firstNameLastName && (StringUtility.hasValue(v.getFirstName()) || StringUtility.hasValue(v.getLastName())) ){
								v.setResourceName(StringUtility.hasValue(v.getFirstName())?(v.getFirstName() +" " + v.getLastName()):(v.getLastName()));
								resname=v.getFirstName()+" " + v.getLastName();
							}else if(!firstNameLastName && (StringUtility.hasValue(v.getFirstName()) || StringUtility.hasValue(v.getLastName())) ){
								resname=v.getLastName()+", " + v.getFirstName();
							}
							if(BooleanUtility.isTrue(v.getTrainee()))
								resname=resname+" (T)";
							v.setResourceName(resname);
							
							if(StringUtility.hasValue(v.getLeaderName())){
								String lname="";
								if(firstNameLastName){
									lname=v.getLeaderFirstName()+" " + v.getLeaderLastName();
								}else{
									lname=v.getLeaderLastName()+", " + v.getLeaderFirstName();
								}
								
								if(BooleanUtility.isTrue(v.getLeaderIsTrainee()))
									lname=lname+" (T)";
								
								branchRscAssignEntity.setLeaderName(lname);
							}
							// Update entity with modified resource name
							String resourceIdentifier = getResourceIdentifierBasedOnForm204Block5Prefs(v,preferences);
							branchRscAssignEntity.setResourceName(resourceIdentifier);
							
							iapBranchRscAssignDao.save(branchRscAssignEntity);
							iapBranchRscAssignDao.flushAndEvict(branchRscAssignEntity);
							iapBranchRscAssignDao.getById(branchRscAssignEntity.getId(), IapBranchRscAssignImpl.class);
							
							// get update vo to return
							IapBranchRscAssignVo vo = IapBranchRscAssignVo.getInstance(branchRscAssignEntity, true);
						
							addedVos.add(vo);
						}
						// return result
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName("ADD_IAP_204_RSC_ASSIGN");
						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The Resources were added to the 204 form."}, MessageTypeEnum.INFO));
						coaVo.setIsDialogueEnding(Boolean.TRUE);
						dialogueVo.setRecordset(addedVos);
						dialogueVo.setCourseOfActionVo(coaVo);
					}
				}
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/**
	 * Private static method 
	 * @param v an object of IapBranchRscAssignVo
	 * @param iapForm204Block5ResourcePrefList Sorted arrayList of either IncidentPrefsVo or IncidentGroupPrefsVo objects
	 * 	that specify the IAP Form 204 Block 5 Preferences
	 * @return The resource Identifier to be saved and shown on the Added Resources grid in IAP For 204 Block 5
	 */
	@SuppressWarnings("unchecked")
	private static String getResourceIdentifierBasedOnForm204Block5Prefs(
			IapBranchRscAssignVo v,
			Collection iapForm204Block5ResourcePrefList) {
			
		String resourceIdentifier = "";

		// Check to exclude last work day preference
		for(Object preference: iapForm204Block5ResourcePrefList) {
			String preferenceLabel = null;
			
			if(preference instanceof IncidentPrefsVo) {
				preferenceLabel = ((IncidentPrefsVo)preference).getFieldLabel();
			} else {
				preferenceLabel = ((IncidentGroupPrefsVo)preference).getFieldLabel();
			}
			
			if(IAP_204_BLOCK5_PREF_FIELD_LABEL_RESOURCENAME.equals(preferenceLabel)){
				resourceIdentifier += v.getResourceName() 
									+ IAP_204_BLOCK5_RESOURCE_IDENTIFIER_SEPARATOR;
			} else if(IAP_204_BLOCK5_PREF_FIELD_LABEL_REQUESTNUMBER.equals(preferenceLabel)){
				resourceIdentifier += v.getRequestNumber() 
									+ IAP_204_BLOCK5_RESOURCE_IDENTIFIER_SEPARATOR;
			} else if(IAP_204_BLOCK5_PREF_FIELD_LABEL_ITEMCODE.equals(preferenceLabel)){
				resourceIdentifier += v.getItemCode()  
									+ IAP_204_BLOCK5_RESOURCE_IDENTIFIER_SEPARATOR;
			} 
		}
		
		int lastSeparatorIndex = resourceIdentifier.lastIndexOf(IAP_204_BLOCK5_RESOURCE_IDENTIFIER_SEPARATOR);
		if(lastSeparatorIndex>0){
			resourceIdentifier = resourceIdentifier.substring(0, lastSeparatorIndex);
		}
		
		return resourceIdentifier;
	}
		
	public DialogueVo addIapForm204BranchComms(Long iapForm204Id, Collection<IapBranchCommSummaryVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapBranch entity = null;

			if(LongUtility.hasValue(iapForm204Id)){
				entity = iapBranchDao.getById(iapForm204Id, IapBranchImpl.class);
				iapBranchDao.flushAndEvict(entity);
				
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			Collection<IapBranchCommSummaryVo> addedVos = new ArrayList<IapBranchCommSummaryVo>();
			
			for(IapBranchCommSummaryVo v : vos){
				
				// update entity from vo and save
				IapBranchCommSummary branchCommSummaryEntity = IapBranchCommSummaryVo.toEntity(null, v, true,(null != entity ? entity : null));
				iapBranchCommSummaryDao.save(branchCommSummaryEntity);
				iapBranchCommSummaryDao.flushAndEvict(branchCommSummaryEntity);
				iapBranchCommSummaryDao.getById(branchCommSummaryEntity.getId(), IapBranchCommSummaryImpl.class);
				
				// get update vo to return
				IapBranchCommSummaryVo vo = IapBranchCommSummaryVo.getInstance(branchCommSummaryEntity, true);
			
				addedVos.add(vo);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ADD_FORM_204_BRANCH_COMMS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The Master Frequencies were added to the 204 form."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setRecordset(addedVos);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206AreaLocationCap(java.lang.Long, gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206AreaLocationCap(Long iapForm206Id, IapAreaLocationCapabilityVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm206 entity = null;
			IapAreaLocationCapability areaLocationCapabilityEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm206Id)){
				entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(entity);

				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				areaLocationCapabilityEntity = this.iapAreaLocationCapabilityDao.getById(vo.getId(), IapAreaLocationCapabilityImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveAreaLocCapRulesHandler ruleHandler = new IapSaveAreaLocCapRulesHandler(super.context);
			if(ruleHandler.execute(vo, areaLocationCapabilityEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				areaLocationCapabilityEntity = IapAreaLocationCapabilityVo.toEntity(areaLocationCapabilityEntity, vo, true,(null != entity ? entity : null));
				iapAreaLocationCapabilityDao.save(areaLocationCapabilityEntity);
				iapAreaLocationCapabilityDao.flushAndEvict(areaLocationCapabilityEntity);
				areaLocationCapabilityEntity = iapAreaLocationCapabilityDao.getById(areaLocationCapabilityEntity.getId(), IapAreaLocationCapabilityImpl.class);

				// get update vo to return
				IapAreaLocationCapabilityVo returnVo = IapAreaLocationCapabilityVo.getInstance(areaLocationCapabilityEntity, true);
				Collection<IapAreaLocationCapabilityVo> vos = new ArrayList<IapAreaLocationCapabilityVo>();

				if( BooleanUtility.isTrue(returnVo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
					for ( IapAreaLocationCapability alc: entity.getIapAreaLocationCapabilities()) {
							if ( alc.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& alc.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( alc.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									alc.setPositionNum(alc.getPositionNum() + 1);
									iapAreaLocationCapabilityDao.save(alc);
									iapAreaLocationCapabilityDao.flushAndEvict(alc);
								}
							}
					}
				}
				// get updated vos
				IapForm206 iapForm206Entity= iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapAreaLocationCapabilityVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);
				
				
				// get update vo to return
				vo = IapAreaLocationCapabilityVo.getInstance(areaLocationCapabilityEntity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_206_AREA_LOC_CAP");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Area Location Capability was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Area Location Capability was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm206AreaLocationCap(gov.nwcg.isuite.core.vo.IapAreaLocationCapabilityVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm206AreaLocationCap(IapAreaLocationCapabilityVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapAreaLocationCapability areaLocationCapabilityEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				areaLocationCapabilityEntity = iapAreaLocationCapabilityDao.getById(vo.getId(), IapAreaLocationCapabilityImpl.class);
				iapAreaLocationCapabilityDao.flushAndEvict(areaLocationCapabilityEntity);

				IapForm206 iapForm=this.iapForm206Dao.getById(areaLocationCapabilityEntity.getIapForm206Id(),IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			// run the rule handler
			IapDeleteAreaLocCapRulesHandler ruleHandler = new IapDeleteAreaLocCapRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapAreaLocationCapabilityDao.delete(areaLocationCapabilityEntity);
				iapAreaLocationCapabilityDao.flushAndEvict(areaLocationCapabilityEntity);

				// reorder the positions based on deleted position
				IapForm206 iapForm206 = iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				for ( IapAreaLocationCapability alc : iapForm206.getIapAreaLocationCapabilities()) {
						if ( alc.getPositionNum().intValue() > vo.getPositionNum().intValue()) {
							// increment positionNum + 1
							alc.setPositionNum(alc.getPositionNum() - 1);
							iapAreaLocationCapabilityDao.save(alc);
							iapAreaLocationCapabilityDao.flushAndEvict(alc);
						}
				}
				iapForm206Dao.flushAndEvict(iapForm206);

				// get updated vos
				Collection<IapAreaLocationCapabilityVo> vos = new ArrayList<IapAreaLocationCapabilityVo>();
				IapForm206 iapForm206Entity= iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapAreaLocationCapabilityVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_206_AREA_LOC_CAP");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Area Location Capability was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206RemoteCampLocations(java.lang.Long, gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206RemoteCampLocations(Long iapForm206Id, IapRemoteCampLocationsVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapForm206 entity = null;
			IapRemoteCampLocations remoteCampLocationsEntity = null;
			Boolean isNew=true;

			if(LongUtility.hasValue(iapForm206Id)){
				entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				iapForm206Dao.flushAndEvict(entity);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(entity.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}

			
			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				remoteCampLocationsEntity = this.iapRemoteCampLocationsDao.getById(vo.getId(), IapRemoteCampLocationsImpl.class);
				isNew=false;
			}

			// run the rule handler
			IapSaveRemoteCampLocRulesHandler ruleHandler = new IapSaveRemoteCampLocRulesHandler(super.context);
			if(ruleHandler.execute(vo, remoteCampLocationsEntity, dialogueVo)==AbstractRule._OK){
				
				// update entity from vo and save
				remoteCampLocationsEntity = IapRemoteCampLocationsVo.toEntity(remoteCampLocationsEntity, vo, true,(null != entity ? entity : null));
				iapRemoteCampLocationsDao.save(remoteCampLocationsEntity);
				iapRemoteCampLocationsDao.flushAndEvict(remoteCampLocationsEntity);
				remoteCampLocationsEntity = iapRemoteCampLocationsDao.getById(remoteCampLocationsEntity.getId(), IapRemoteCampLocationsImpl.class);
				
				// get update vo to return
				IapRemoteCampLocationsVo returnVo = IapRemoteCampLocationsVo.getInstance(remoteCampLocationsEntity, true);
				Collection<IapRemoteCampLocationsVo> vos = new ArrayList<IapRemoteCampLocationsVo>();

				if( BooleanUtility.isTrue(returnVo.getIsBlankLine())) {
					// reorder the positions based on blank line position
					entity = iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
					for ( IapRemoteCampLocations rcl : entity.getIapRemoteCampLocations()) {
							if ( rcl.getPositionNum().intValue() == returnVo.getPositionNum().intValue()
									&& rcl.getId().compareTo(returnVo.getId()) == 0) {
								// do nothing, this is the row being inserted
							} else {
								if ( rcl.getPositionNum().intValue() >= returnVo.getPositionNum().intValue()) {
									// increment positionNum + 1
									rcl.setPositionNum(rcl.getPositionNum() + 1);
									iapRemoteCampLocationsDao.save(rcl);
									iapRemoteCampLocationsDao.flushAndEvict(rcl);
								}
							}
					}
				}
				// get updated vos
				IapForm206 iapForm206Entity= iapForm206Dao.getById(iapForm206Id, IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapRemoteCampLocationsVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);

				// get update vo to return
				vo = IapRemoteCampLocationsVo.getInstance(remoteCampLocationsEntity, true);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_FORM_206_REMOTE_CAMP_LOC");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				if(isNew==true)
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Remote Camp Location was added to the form."}, MessageTypeEnum.INFO));
				else
					coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Remote Camp Location was saved."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(returnVo);
				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#deleteIapForm206RemoteCampLocations(gov.nwcg.isuite.core.vo.IapRemoteCampLocationsVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteIapForm206RemoteCampLocations(IapRemoteCampLocationsVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			IapRemoteCampLocations remoteCampLocationsEntity = null;

			// load entity if updating
			if(LongUtility.hasValue(vo.getId())){
				remoteCampLocationsEntity = iapRemoteCampLocationsDao.getById(vo.getId(), IapRemoteCampLocationsImpl.class);
				iapRemoteCampLocationsDao.flushAndEvict(remoteCampLocationsEntity);
				if(LongUtility.hasValue(remoteCampLocationsEntity.getIapForm206Id())){
					IapForm206 iapForm=this.iapForm206Dao.getById(remoteCampLocationsEntity.getIapForm206Id(),IapForm206Impl.class);
					iapBranchDao.flushAndEvict(iapForm);
					IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
					if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
						return dialogueVo;
					}
				}
			}

			// run the rule handler
			IapDeleteRemoteCampLocRulesHandler ruleHandler = new IapDeleteRemoteCampLocRulesHandler(super.context);
			if(ruleHandler.execute(vo, dialogueVo)==AbstractRule._OK){
				
				iapRemoteCampLocationsDao.delete(remoteCampLocationsEntity);
				iapRemoteCampLocationsDao.flushAndEvict(remoteCampLocationsEntity);

				// reorder the positions based on deleted position
				IapForm206 iapForm206 = iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				for ( IapRemoteCampLocations rcl : iapForm206.getIapRemoteCampLocations()) {
						if ( rcl.getPositionNum().intValue() > vo.getPositionNum().intValue()) {
							// increment positionNum + 1
							rcl.setPositionNum(rcl.getPositionNum() - 1);
							iapRemoteCampLocationsDao.save(rcl);
							iapRemoteCampLocationsDao.flushAndEvict(rcl);
						}
				}
				iapForm206Dao.flushAndEvict(iapForm206);

				// get updated vos
				Collection<IapRemoteCampLocationsVo> vos = new ArrayList<IapRemoteCampLocationsVo>();
				IapForm206 iapForm206Entity= iapForm206Dao.getById(vo.getIapForm206Id(), IapForm206Impl.class);
				IapForm206Vo iapForm206Vo = IapForm206Vo.getInstance(iapForm206Entity, true);
				vos = iapForm206Vo.getIapRemoteCampLocationsVos();
				iapForm206Dao.flushAndEvict(iapForm206Entity);
				
				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_FORM_206_REMOTE_CAMP_LOC");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 206 Remote Camp Location was deleted."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setRecordset(vos);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	private void createDefaut203Positions(Long form203Id, Long planId) throws Exception {
		Incident incident=null;
		IncidentGroup incidentGroup=null;
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
		
		if(LongUtility.hasValue(planId)){
			IapPlan iapPlan = iapPlanDao.getById(planId, IapPlanImpl.class);
			iapPlanDao.flushAndEvict(iapPlan);
			Collection<IapPositionItemCode> iapTemplatePositions = new ArrayList<IapPositionItemCode>();
			if(LongUtility.hasValue(iapPlan.getIncidentId())){
				incident = incidentDao.getById(iapPlan.getIncidentId(),IncidentImpl.class);
				iapTemplatePositions = incident.getIapPositionItemCodes();
			}
			if(LongUtility.hasValue(iapPlan.getIncidentGroupId())){
				incidentGroup = incidentGroupDao.getById(iapPlan.getIncidentGroupId(),IncidentGroupImpl.class);
				iapTemplatePositions = incidentGroup.getIapPositionItemCodes();
			}

			for(IapSectionEnum iapSection : IapSectionEnum.values()){
				int posNum=1;
				HashMap<String,String> processedRoles = new HashMap<String,String>();
				for(IapPositionItemCode defaultPosition : iapTemplatePositions){
					if( defaultPosition.getSection().name().equals(iapSection.name()) && defaultPosition.getForm().equals("203")){
						if(!iapSection.formSection.equals("AGENCY_SECTION") && !iapSection.formSection.equals("BRANCH_SECTION")){
							if(!processedRoles.containsKey(defaultPosition.getPosition())){
								IapForm203 iapForm203 = new IapForm203Impl();
								iapForm203.setId(form203Id);
								IapPersonnel personnel = new IapPersonnelImpl();
								personnel.setForm("203");
								personnel.setIapForm203(iapForm203);
								personnel.setRole(defaultPosition.getPosition());
								personnel.setSection(iapSection.formSection);
								personnel.setName("");
								personnel.setPositionNum(Integer.valueOf(posNum));
								personnel.setIsBlankLine(StringBooleanEnum.N);
								personnel.setIsBlankBranch(StringBooleanEnum.N);
								personnel.setDivisionGroupName("");
								personnel.setIsTrainee(StringBooleanEnum.N);
								personnel.setIsBranchName(StringBooleanEnum.N);
								// create 2 default name holders IapPersonnelRes
								IapPersonnelRes res1 = new IapPersonnelResImpl();
								res1.setIapPersonnel(personnel);
								res1.setPositionNum(Integer.valueOf(1));
								res1.setIsTrainee(StringBooleanEnum.N);
								res1.setName("");
								IapPersonnelRes res2 = new IapPersonnelResImpl();
								res2.setIapPersonnel(personnel);
								res2.setPositionNum(Integer.valueOf(2));
								res2.setIsTrainee(StringBooleanEnum.N);
								res2.setName("");
								
								personnel.getIapPersonnelResources().add(res1);
								personnel.getIapPersonnelResources().add(res2);
								
								this.iapPersonnelDao.save(personnel);
								this.iapPersonnelDao.flushAndEvict(personnel);
								posNum++;
								processedRoles.put(defaultPosition.getPosition(), defaultPosition.getPosition());
							}
						}
					}
				}
			}
			
			if(null != incident)
				incidentDao.flushAndEvict(incident);
			if(null != incidentGroup)
				incidentGroupDao.flushAndEvict(incidentGroup);
				
		}
	}

	private void createDefaut203BranchPositions(Long form203Id, IapPersonnel parentBranch, Long planId) throws Exception {
		Incident incident=null;
		IncidentGroup incidentGroup=null;
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
		
		if(LongUtility.hasValue(planId)){
			IapPlan iapPlan = iapPlanDao.getById(planId, IapPlanImpl.class);
			iapPlanDao.flushAndEvict(iapPlan);
			Collection<IapPositionItemCode> iapTemplatePositions = new ArrayList<IapPositionItemCode>();
			if(LongUtility.hasValue(iapPlan.getIncidentId())){
				incident = incidentDao.getById(iapPlan.getIncidentId(),IncidentImpl.class);
				iapTemplatePositions = incident.getIapPositionItemCodes();
			}
			if(LongUtility.hasValue(iapPlan.getIncidentGroupId())){
				incidentGroup = incidentGroupDao.getById(iapPlan.getIncidentGroupId(),IncidentGroupImpl.class);
				iapTemplatePositions = incidentGroup.getIapPositionItemCodes();
			}

			int posNum=1;
			for(IapSectionEnum iapSection : IapSectionEnum.values()){
				HashMap<String,String> processedRoles = new HashMap<String,String>();
				for(IapPositionItemCode defaultPosition : iapTemplatePositions){
					if( defaultPosition.getSection().name().equals(iapSection.name()) && defaultPosition.getForm().equals("203")){
						if(iapSection.formSection.equals("BRANCH_SECTION")){
							if(!defaultPosition.getPosition().equalsIgnoreCase("DIVISION/GROUP")){
								if(!processedRoles.containsKey(defaultPosition.getPosition())){
									IapForm203 iapForm203 = new IapForm203Impl();
									iapForm203.setId(form203Id);
									IapPersonnel personnel = new IapPersonnelImpl();
									personnel.setForm("203");
									personnel.setIapForm203(iapForm203);
									personnel.setRole(defaultPosition.getPosition());
									personnel.setSection(iapSection.formSection);
									personnel.setIsBranchName(StringBooleanEnum.N);
									personnel.setName("");
									personnel.setIapBranchPersonnelParent(parentBranch);
									personnel.setPositionNum(Integer.valueOf(posNum));
									personnel.setIsBlankLine(StringBooleanEnum.N);
									personnel.setIsBlankBranch(StringBooleanEnum.N);
									personnel.setDivisionGroupName("");
									personnel.setIsTrainee(StringBooleanEnum.N);
									this.iapPersonnelDao.save(personnel);
									this.iapPersonnelDao.flushAndEvict(personnel);
									posNum++;
									processedRoles.put(defaultPosition.getPosition(), defaultPosition.getPosition());
								}
							}
						}
					}
				}
			}
			// 3 default div/groups
			IapPersonnel divgroup1 = IapPersonnelVo.buildDefaultBlankDivGroup(form203Id, parentBranch.getId(), posNum);
			posNum++;
			IapPersonnel divgroup2 = IapPersonnelVo.buildDefaultBlankDivGroup(form203Id, parentBranch.getId(), posNum);
			posNum++;
			IapPersonnel divgroup3 = IapPersonnelVo.buildDefaultBlankDivGroup(form203Id, parentBranch.getId(), posNum);
			posNum++;
			this.iapPersonnelDao.save(divgroup1);
			this.iapPersonnelDao.flushAndEvict(divgroup1);
			this.iapPersonnelDao.save(divgroup2);
			this.iapPersonnelDao.flushAndEvict(divgroup2);
			this.iapPersonnelDao.save(divgroup3);
			this.iapPersonnelDao.flushAndEvict(divgroup3);
			
			if(null != incident)
				incidentDao.flushAndEvict(incident);
			if(null != incidentGroup)
				incidentGroupDao.flushAndEvict(incidentGroup);
				
		}
	}

	private void createDefaut204Positions(Long form204Id, Long planId) throws Exception {
		Incident incident=null;
		IncidentGroup incidentGroup=null;
		IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
		IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
		
		if(LongUtility.hasValue(planId)){
			IapPlan iapPlan = iapPlanDao.getById(planId, IapPlanImpl.class);
			iapPlanDao.flushAndEvict(iapPlan);
			Collection<IapPositionItemCode> iapTemplatePositions = new ArrayList<IapPositionItemCode>();
			if(LongUtility.hasValue(iapPlan.getIncidentId())){
				incident = incidentDao.getById(iapPlan.getIncidentId(),IncidentImpl.class);
				iapTemplatePositions = incident.getIapPositionItemCodes();
			}
			if(LongUtility.hasValue(iapPlan.getIncidentGroupId())){
				incidentGroup = incidentGroupDao.getById(iapPlan.getIncidentGroupId(),IncidentGroupImpl.class);
				iapTemplatePositions = incidentGroup.getIapPositionItemCodes();
			}

			for(IapSectionEnum iapSection : IapSectionEnum.values()){
				int posNum=1;
				HashMap<String,String> processedRoles = new HashMap<String,String>();
				for(IapPositionItemCode defaultPosition : iapTemplatePositions){
					if( defaultPosition.getSection().name().equals(iapSection.name()) && defaultPosition.getForm().equals("204")){
						if(!processedRoles.containsKey(defaultPosition.getPosition())){
							IapBranch iapBranch = new IapBranchImpl();
							iapBranch.setId(form204Id);
							IapBranchPersonnel personnel = new IapBranchPersonnelImpl();
							personnel.setIapBranch(iapBranch);
							personnel.setIsTrainee(StringBooleanEnum.N);
							personnel.setRole(defaultPosition.getPosition());
							personnel.setPositionNum(new Integer(posNum));
							personnel.setIsBlankLine(StringBooleanEnum.N);
							
							this.iapBranchPersonnelDao.save(personnel);
							this.iapBranchPersonnelDao.flushAndEvict(personnel);
							posNum++;
							processedRoles.put(defaultPosition.getPosition(), defaultPosition.getPosition());
						}
					}
				}
			}
			
			if(null != incident)
				incidentDao.flushAndEvict(incident);
			if(null != incidentGroup)
				incidentGroupDao.flushAndEvict(incidentGroup);
				
		}
	}
	
	public DialogueVo resetToBranch(Long form203Id, String type,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(LongUtility.hasValue(form203Id)){
				IapForm203 iapForm=this.iapForm203Dao.getById(form203Id,IapForm203Impl.class);
				iapBranchDao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			if(type.equals("BRANCH")){
				//this.iapForm203Dao.deleteAllBranchPositions(form203Id);
				this.iapForm203Dao.deleteAllBranches(form203Id);
				
				IapForm203 entity = this.iapForm203Dao.getById(form203Id, IapForm203Impl.class);
				entity.setIsNoBranch(StringBooleanEnum.N);
				
				//entity.getIapPersonnels().clear();
				
				this.iapForm203Dao.save(entity);
				IapForm203Vo vo = IapForm203Vo.getInstance(entity, true);
				iapForm203Dao.flushAndEvict(entity);

				// return result
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RESET_TO_BRANCH");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}else{
				dialogueVo=this.deleteAndResetToNoBranch(form203Id, dialogueVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo deleteAndResetToNoBranch(Long form203Id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			if(LongUtility.hasValue(form203Id)){
				IapForm203 iapForm=this.iapForm203Dao.getById(form203Id,IapForm203Impl.class);
				iapBranchDao.flushAndEvict(iapForm);
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
			}
			
			// delete all branches
			this.iapForm203Dao.deleteAllBranches(form203Id);
			
			// create default blank branch
			IapPersonnel branch = IapPersonnelVo.buildDefaultBlankBranch(form203Id);
			this.iapPersonnelDao.save(branch);
			this.iapPersonnelDao.flushAndEvict(branch);
			
			// create 3 empty division group records
			IapPersonnel defaultDivGroup1 = IapPersonnelVo.buildDefaultBlankDivGroup(form203Id,branch.getId(),1);
			IapPersonnel defaultDivGroup2 = IapPersonnelVo.buildDefaultBlankDivGroup(form203Id,branch.getId(),2);
			IapPersonnel defaultDivGroup3 = IapPersonnelVo.buildDefaultBlankDivGroup(form203Id,branch.getId(),3);
			this.iapPersonnelDao.save(defaultDivGroup1);
			this.iapPersonnelDao.flushAndEvict(defaultDivGroup1);					
			this.iapPersonnelDao.save(defaultDivGroup2);
			this.iapPersonnelDao.flushAndEvict(defaultDivGroup2);					
			this.iapPersonnelDao.save(defaultDivGroup3);
			this.iapPersonnelDao.flushAndEvict(defaultDivGroup3);					

			IapForm203 entity = this.iapForm203Dao.getById(form203Id, IapForm203Impl.class);
			entity.setIsNoBranch(StringBooleanEnum.Y);
			this.iapForm203Dao.save(entity);
			IapForm203Vo vo = IapForm203Vo.getInstance(entity, true);
			
			this.iapForm203Dao.flushAndEvict(entity);

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_AND_RESET_TO_NOBRANCH");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#autoFill(java.lang.Long, gov.nwcg.isuite.core.vo.IapBranchRscAssignVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo autoFill(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException {
		if (null == dialogueVo)
			dialogueVo = new DialogueVo();

		try {

			if ((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException(
						"Unable to auto-fill data for unknown resource.");
								
            iapBranchRscAssignDao.autoFill(vo);
            
            
            CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("AUTO_FILL");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The IAP 204 Resource Assigned was auto filled."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(vo);
			
			dialogueVo.setCourseOfActionVo(coaVo);
	

		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#getRscAssignGrid(java.lang.Long, gov.nwcg.isuite.core.vo.IapBranchRscAssignVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getRscAssignGrid(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException {				
        if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			Collection<IapBranchRscAssignVo> iapBranchRscAssignVos = new ArrayList<IapBranchRscAssignVo>();
			
			iapBranchRscAssignVos = iapBranchRscAssignDao.getRscAssignGrid(vo);
						
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_IAP_BRANCH_RSC_ASSIGN_GRID");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setRecordset(iapBranchRscAssignVos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;		
		
	}	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204Position(java.lang.Long, gov.nwcg.isuite.core.vo.IapBranchRscAssignVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204Position(Long iapForm204Id, IapBranchRscAssignVo vo,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
				
		return dialogueVo;
	}
		
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204REsourceAssignPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204ResourceAssignPositions(Collection<IapBranchRscAssignVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		// Since this function is also called when blank lines are added, the updated
		// vo with the id of the newly saved object needs to be returned back.
		Collection<IapBranchRscAssignVo> updatedVos = new ArrayList<IapBranchRscAssignVo>();
		int cnt=0;
		try{
			for(IapBranchRscAssignVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapBranchId())){
						Long formId=vo.getIapBranchId();
						IapBranch iapForm=this.iapBranchDao.getById(formId,IapBranchImpl.class);
						iapBranchDao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm204BranchRscAssign(vo.getIapBranchId(), vo, dvo);
				updatedVos.add((IapBranchRscAssignVo) dvo.getResultObject());
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_204_RESOURCE_ASSIGN_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(updatedVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm204CommunicationPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm204CommunicationPositions(Collection<IapBranchCommSummaryVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		// Since this function is also called when blank lines are added, the updated
		// vo with the id of the newly saved object needs to be returned back.
		Collection<IapBranchCommSummaryVo> updatedVos = new ArrayList<IapBranchCommSummaryVo>();
		int cnt=0;
		try{
			for(IapBranchCommSummaryVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapBranchId())){
						Long formId=vo.getIapBranchId();
						IapBranch iapForm=this.iapBranchDao.getById(formId,IapBranchImpl.class);
						iapBranchDao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm204BranchComm(vo.getIapBranchId(), vo, dvo);
				updatedVos.add((IapBranchCommSummaryVo) dvo.getResultObject());
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_204_COMMUNICATION_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(updatedVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm220AircraftPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm220AircraftPositions(Collection<IapAircraftVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapAircraftVo> newVos = new ArrayList<IapAircraftVo>();	
			int cnt=0;
			IapForm220 iapForm220 = null;
			for(IapAircraftVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm220Id())){
						Long form220Id=vo.getIapForm220Id();
						iapForm220=this.iapForm220Dao.getById(form220Id,IapForm220Impl.class);
						iapForm220Dao.flushAndEvict(iapForm220);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm220.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				IapAircraft entity = IapAircraftVo.toEntity(null, vo, true, iapForm220);
				this.iapAircraftDao.save(entity);
				this.iapAircraftDao.flushAndEvict(entity);
				IapAircraftVo newVo = IapAircraftVo.getInstance(entity, true);
				newVos.add(newVo);

				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm220Helicopter(vo.getIapForm220Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapAircraftVo newVo = (IapAircraftVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_220_AIRCRAFT_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm220TaskPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm220TaskPositions(Collection<IapAircraftTaskVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapAircraftTaskVo> newVos = new ArrayList<IapAircraftTaskVo>();	
			int cnt=0;
			IapForm220 iapForm220 = null;
			for(IapAircraftTaskVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm220Id())){
						Long form220Id=vo.getIapForm220Id();
						iapForm220=this.iapForm220Dao.getById(form220Id,IapForm220Impl.class);
						iapForm220Dao.flushAndEvict(iapForm220);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm220.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				
				IapAircraftTask entity = IapAircraftTaskVo.toEntity(null, vo, true, iapForm220);
				this.iapAircraftTaskDao.save(entity);
				this.iapAircraftTaskDao.flushAndEvict(entity);
				IapAircraftTaskVo newVo = IapAircraftTaskVo.getInstance(entity, true);
				newVos.add(newVo);
				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm220Task(vo.getIapForm220Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapAircraftTaskVo newVo = (IapAircraftTaskVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_220_TASK_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm205FrequencyPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm205FrequencyPositions(Collection<IapFrequencyVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapFrequencyVo> newVos = new ArrayList<IapFrequencyVo>();	
			int cnt=0;
			IapForm205 iapForm = null;
			for(IapFrequencyVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm205Id())){
						Long formId=vo.getIapForm205Id();
						iapForm=this.iapForm205Dao.getById(formId,IapForm205Impl.class);
						iapForm205Dao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				IapFrequency entity = IapFrequencyVo.toEntity(null, vo, true, iapForm);
				this.iapFrequencyDao.save(entity);
				this.iapFrequencyDao.flushAndEvict(entity);
				IapFrequencyVo newVo = IapFrequencyVo.getInstance(entity, true);
				newVos.add(newVo);

				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm205Frequency(vo.getIapForm205Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapFrequencyVo newVo = (IapFrequencyVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_205_FREQUENCY_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206AirAmbulancePositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206AirAmbulancePositions(Collection<IapMedicalAidVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapMedicalAidVo> newVos = new ArrayList<IapMedicalAidVo>();	
			int cnt=0;
			IapForm206 iapForm = null;
			for(IapMedicalAidVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm206Id())){
						Long formId=vo.getIapForm206Id();
						iapForm=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
						iapForm206Dao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}

				IapMedicalAid entity = IapMedicalAidVo.toEntity(null, vo, true, iapForm);
				this.iapMedicalAidDao.save(entity);
				this.iapMedicalAidDao.flushAndEvict(entity);
				IapMedicalAidVo newVo = IapMedicalAidVo.getInstance(entity, true);
				newVos.add(newVo);
				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm206AirAmbulance(vo.getIapForm206Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapMedicalAidVo newVo = (IapMedicalAidVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_206_AIR_AMBULANCE_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	    
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206AmbulancePositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206AmbulancePositions(Collection<IapMedicalAidVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapMedicalAidVo> newVos = new ArrayList<IapMedicalAidVo>();	
			int cnt=0;
			IapForm206 iapForm = null;
			for(IapMedicalAidVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm206Id())){
						Long formId=vo.getIapForm206Id();
						iapForm=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
						iapForm206Dao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				DialogueVo dvo = new DialogueVo();

				IapMedicalAid entity = IapMedicalAidVo.toEntity(null, vo, true, iapForm);
				this.iapMedicalAidDao.save(entity);
				this.iapMedicalAidDao.flushAndEvict(entity);
				IapMedicalAidVo newVo = IapMedicalAidVo.getInstance(entity, true);
				newVos.add(newVo);
				
				/*
				this.saveIapForm206Ambulance(vo.getIapForm206Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapMedicalAidVo newVo = (IapMedicalAidVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_206_AMBULANCE_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	    	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206HospitalPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206HospitalPositions(Collection<IapHospitalVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapHospitalVo> newVos = new ArrayList<IapHospitalVo>();
			int cnt=0;
			IapForm206 iapForm = null;
			for(IapHospitalVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm206Id())){
						Long formId=vo.getIapForm206Id();
						iapForm=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
						iapForm206Dao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}

				IapHospital entity = IapHospitalVo.toEntity(null, vo, true, iapForm);
				this.iapHospitalDao.save(entity);
				this.iapHospitalDao.flushAndEvict(entity);
				IapHospitalVo newVo = IapHospitalVo.getInstance(entity, true);
				newVos.add(newVo);
				
				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm206Hospital(vo.getIapForm206Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapHospitalVo newVo = (IapHospitalVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_206_HOSPITAL_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	    		
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206AlcPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206AlcPositions(Collection<IapAreaLocationCapabilityVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapAreaLocationCapabilityVo> newVos = new ArrayList<IapAreaLocationCapabilityVo>();	
			int cnt=0;
			IapForm206 iapForm = null;
			for(IapAreaLocationCapabilityVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm206Id())){
						Long formId=vo.getIapForm206Id();
						iapForm=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
						iapForm206Dao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}

				IapAreaLocationCapability entity = IapAreaLocationCapabilityVo.toEntity(null, vo, true, iapForm);
				this.iapAreaLocationCapabilityDao.save(entity);
				this.iapAreaLocationCapabilityDao.flushAndEvict(entity);
				IapAreaLocationCapabilityVo newVo = IapAreaLocationCapabilityVo.getInstance(entity, true);
				newVos.add(newVo);
				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm206AreaLocationCap(vo.getIapForm206Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapAreaLocationCapabilityVo newVo = (IapAreaLocationCapabilityVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_206_ALC_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	    			
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapPlanService#saveIapForm206RclPositions(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIapForm206RclPositions(Collection<IapRemoteCampLocationsVo> vos,DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			// Since this function is also called when blank lines are added, the updated
			// vo with the id of the newly saved object needs to be returned back.
			Collection<IapRemoteCampLocationsVo> newVos = new ArrayList<IapRemoteCampLocationsVo>();	
			int cnt=0;
			IapForm206 iapForm = null;
			for(IapRemoteCampLocationsVo vo : vos){
				if(cnt==0){
					if(LongUtility.hasValue(vo.getIapForm206Id())){
						Long formId=vo.getIapForm206Id();
						iapForm=this.iapForm206Dao.getById(formId,IapForm206Impl.class);
						iapForm206Dao.flushAndEvict(iapForm);
						IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
						if(lockedRuleHandler.execute(iapForm.getIapPlanId(), "IAPPLAN", dialogueVo)!=AbstractRule._OK){
							return dialogueVo;
						}
					}
					cnt++;
				}
				IapRemoteCampLocations entity = IapRemoteCampLocationsVo.toEntity(null, vo, true, iapForm);
				this.iapRemoteCampLocationsDao.save(entity);
				this.iapRemoteCampLocationsDao.flushAndEvict(entity);
				IapRemoteCampLocationsVo newVo = IapRemoteCampLocationsVo.getInstance(entity, true);
				newVos.add(newVo);

				/*
				DialogueVo dvo = new DialogueVo();
				this.saveIapForm206RemoteCampLocations(vo.getIapForm206Id(), vo, dvo);
				if(null != dvo.getResultObject()){
					IapRemoteCampLocationsVo newVo = (IapRemoteCampLocationsVo)dvo.getResultObject();
					newVos.add(newVo);
				}
				*/
			}

			// return result
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_FORM_206_RCL_POSITIONS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.iap", "info.generic" , new String[]{"The data was saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(newVos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}	    			
}
