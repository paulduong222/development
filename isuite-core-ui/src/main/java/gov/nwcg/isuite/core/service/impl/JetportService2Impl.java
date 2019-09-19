package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteJetPortRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveJetPortRulesHandler;
import gov.nwcg.isuite.core.service.JetportService2;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
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

public class JetportService2Impl extends BaseService implements JetportService2 {
	private JetPortDao dao;

	public JetportService2Impl(){

	}

	public void initialization(){
		dao = (JetPortDao)super.context.getBean("jetPortDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.JetportService2#deleteJetport(gov.nwcg.isuite.core.vo.JetPortVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteJetport(JetPortVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown jetport.");

			JetPort entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), JetPortImpl.class);
			
			ReferenceDataDeleteJetPortRulesHandler ruleHandler = new ReferenceDataDeleteJetPortRulesHandler(context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<JetPortVo> newList = new ArrayList<JetPortVo>();
				
				for(JetPortVo jvo : gvo.getJetPortVos()) {
					if(jvo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(jvo);
				}
				
				gvo.setJetPortVos(newList);
				((GlobalCacheVo)context.getBean("globalCacheVo")).setJetPortVos(newList);				
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_JETPORT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.jetPort", "info.0028" , new String[]{"JetPort"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.JetportService2#save(gov.nwcg.isuite.core.vo.JetPortVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(JetPortVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		   

			JetPort entity = null;
			Boolean bEdit=false;
			if(LongUtility.hasValue(vo.getId())){
				entity = dao.getById(vo.getId(), JetPortImpl.class);
				bEdit=true;
			}
			
			ReferenceDataSaveJetPortRulesHandler ruleHandler = new ReferenceDataSaveJetPortRulesHandler(context);
			if(ruleHandler.execute(vo,entity, dialogueVo)==AbstractRule._OK){

				entity = JetPortVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), JetPortImpl.class);
				
				vo=JetPortVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<JetPortVo> jetPortVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getJetPortVos();

				if(!bEdit){
					// add to cache
					jetPortVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setJetPortVos(jetPortVos);				
				}else{
					JetPortVo jetPortVo = null;
					
					for(JetPortVo jvo : jetPortVos) {
						if(jvo.getId().compareTo(vo.getId())==0) {
							jetPortVo = jvo;
							break;
						}
					}
					
					jetPortVos.remove(jetPortVo);
					jetPortVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setJetPortVos(jetPortVos);				
				}

				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_JETPORT");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.jetPort", "info.0030" , null, MessageTypeEnum.INFO));
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

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.JetportService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.JetportService2#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<JetPortVo> vos = new ArrayList<JetPortVo>();

			vos = dao.getPicklist();
			
			((GlobalCacheVo)context.getBean("globalCacheVo")).setJetPortVos(vos);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_JETPORTS");
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
			
			Collection<JetPortVo> vos = this.dao.getStandardAndNonStandard(incidentIds, incidentGroupId);
			Collection<JetPortVo> newvos = new ArrayList<JetPortVo>();
			for(JetPortVo v : vos){
				if(BooleanUtility.isTrue(v.getActive())){
					newvos.add(v);
				}
			}
			/*
			Collection<AgencyVo> vos = this.dao.getStandardAndNonStandard(incidentIds, incidentGroupId);
			Collection<AgencyVo> newvos = new ArrayList<AgencyVo>();
			for(AgencyVo v : vos){
				if(BooleanUtility.isTrue(v.getActive())){
					newvos.add(v);
				}
			}
			
			for(JetPortVo vo : super.getGlobalCacheVo().getJetPortVos()){
				if(BooleanUtility.isTrue(vo.getActive())){
					if(BooleanUtility.isTrue(vo.getStandard())){
						vos.add(vo);
					}else{
						if(LongUtility.hasValue(incidentId) && null != vo.getIncidentVo()){
							if(incidentId.compareTo(vo.getIncidentVo().getId())==0)
								vos.add(vo);
						}
						
						if(LongUtility.hasValue(incidentGroupId) && null != vo.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(vo.getIncidentVo().getId())==0)
									vos.add(vo);
							}
						}
					}
				}
			}
			*/
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_JETPORTS");
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
