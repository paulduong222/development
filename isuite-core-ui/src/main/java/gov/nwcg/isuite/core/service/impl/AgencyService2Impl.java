package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.filter.impl.AgencyFilterImpl;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteAgencyCodeRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveAgencyCodeRulesHandler;
import gov.nwcg.isuite.core.service.AgencyService2;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class AgencyService2Impl extends BaseService implements AgencyService2 {
	private AgencyDao dao;

	public AgencyService2Impl(){

	}

	public void initialization(){
		dao = (AgencyDao)super.context.getBean("agencyDao");
	}

	public DialogueVo deleteAgency(AgencyVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown agency.");

			Agency entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), AgencyImpl.class);
			
			ReferenceDataDeleteAgencyCodeRulesHandler ruleHandler = new ReferenceDataDeleteAgencyCodeRulesHandler(context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<AgencyVo> newList = new ArrayList<AgencyVo>();
				
				for(AgencyVo avo : gvo.getAgencyVos()) {
					if(avo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(avo);
				}
				
				gvo.setAgencyVos(newList);
				((GlobalCacheVo)context.getBean("globalCacheVo")).setAgencyVos(newList);				
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_AGENCY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.agency", "info.0028" , new String[]{"Agency"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}


		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo save(AgencyVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		   
			Agency entity = null;
			Boolean bEdit=false;
			if(LongUtility.hasValue(vo.getId())){
				entity = dao.getById(vo.getId(), AgencyImpl.class);
				bEdit=true;
			}
			
			ReferenceDataSaveAgencyCodeRulesHandler ruleHandler = new ReferenceDataSaveAgencyCodeRulesHandler(context);
			if(ruleHandler.execute(vo,entity, dialogueVo)==AbstractRule._OK){

				entity = AgencyVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), JetPortImpl.class);
				
				vo=AgencyVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<AgencyVo> agencyVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getAgencyVos();
				
				if(!bEdit){
					agencyVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setAgencyVos(agencyVos);				
				}else{
					AgencyVo agencyVo = null;
					
					for(AgencyVo avo : agencyVos) {
						if(avo.getId().compareTo(vo.getId())==0) {
							agencyVo = avo;
							break;
						}
					}
					
					agencyVos.remove(agencyVo);
					agencyVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setAgencyVos(agencyVos);				
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_AGENCY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.agency", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<AgencyVo> vos = new ArrayList<AgencyVo>();

			vos = dao.getAgencies(new AgencyFilterImpl());
			
			((GlobalCacheVo)context.getBean("globalCacheVo")).setAgencyVos(vos);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_AGENCIES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getStandardAgencies(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<AgencyVo> vos = new ArrayList<AgencyVo>();
			for(AgencyVo agVo : super.getGlobalCacheVo().getAgencyVos()){
				if(BooleanUtility.isTrue(agVo.getActive())){
					if(BooleanUtility.isTrue(agVo.getStandard())){
						vos.add(agVo);
					}
				}
			}

			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_STANDARD_AGENCIES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo getGridIncidentorGroup(Long incidentId, Long incidentGroupId, Boolean incidentOnly, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(incidentId) && BooleanUtility.isTrue(incidentOnly)){
				incidentIds.add(incidentId);
			}
			if(LongUtility.hasValue(incidentId) && BooleanUtility.isFalse(incidentOnly)){
				// determine if incident is part of a group
				IncidentDao idao = (IncidentDao)context.getBean("incidentDao");
				Long incGroupId=idao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(incGroupId)){
					incidentId=null; 
					incidentGroupId=incGroupId;
				}
			}

			if(LongUtility.hasValue(incidentGroupId)){
				// get all incidentids for group
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentIds=igdao.getIncidentIdsInGroup(incidentGroupId);
			}
			
			Collection<AgencyVo> vos = this.dao.getStandardAndNonStandard(incidentIds, incidentGroupId);
			Collection<AgencyVo> newvos = new ArrayList<AgencyVo>();
			for(AgencyVo v : vos){
				if(BooleanUtility.isTrue(v.getActive())){
					newvos.add(v);
				}
			}
			/*
			for(AgencyVo agVo : super.getGlobalCacheVo().getAgencyVos()){
				if(BooleanUtility.isTrue(agVo.getActive())){
					if(BooleanUtility.isTrue(agVo.getStandard())){
						vos.add(agVo);
					}else{
						if(LongUtility.hasValue(incidentId) && null != agVo.getIncidentVo()){
							if(incidentId.compareTo(agVo.getIncidentVo().getId())==0)
								vos.add(agVo);
						}

						if(LongUtility.hasValue(incidentGroupId) && null != agVo.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(agVo.getIncidentVo().getId())==0)
									vos.add(agVo);
							}
						}
						// if(LongUtility.hasValue(incidentGroupId) && null != agVo.getIncidentGroupVo()){
						//	if(incidentGroupId.compareTo(agVo.getIncidentGroupVo().getId())==0)
						//		vos.add(agVo);
						//}
					}
				}
			}
			*/
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_AGENCIES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(newvos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
}
