package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteAgencyGroupRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveAgencyGroupRulesHandler;
import gov.nwcg.isuite.core.service.AgencyGroupService2;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class AgencyGroupService2Impl extends BaseService implements AgencyGroupService2 {
	private AgencyGroupDao dao;

	public AgencyGroupService2Impl(){

	}

	public void initialization(){
		dao = (AgencyGroupDao)super.context.getBean("agencyGroupDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService2#deleteAgencyGroup(gov.nwcg.isuite.core.vo.AgencyGroupVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteAgencyGroup(AgencyGroupVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown agency group.");
			
			AgencyGroup entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), AgencyGroupImpl.class);
			
			ReferenceDataDeleteAgencyGroupRulesHandler ruleHandler = new ReferenceDataDeleteAgencyGroupRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<AgencyGroupVo> newList = new ArrayList<AgencyGroupVo>();
				
				for(AgencyGroupVo avo : gvo.getAgencyGroupVos()) {
					if(avo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(avo);
				}
				
				gvo.setAgencyGroupVos(newList);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_AGENCY_GROUP");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.agencyGroup", "info.0028" , new String[]{"Agency Group"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService2#save(gov.nwcg.isuite.core.vo.AgencyGroupVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(AgencyGroupVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		
			
			AgencyGroup entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), AgencyGroupImpl.class);
			
			ReferenceDataSaveAgencyGroupRulesHandler ruleHandler = new ReferenceDataSaveAgencyGroupRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				entity = AgencyGroupVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), AgencyGroupImpl.class);
				
				vo=AgencyGroupVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<AgencyGroupVo> agencyGroupVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getAgencyGroupVos();
				AgencyGroupVo agencyGroupVo = null;
				
				for(AgencyGroupVo avo : agencyGroupVos) {
					if(avo.getId().compareTo(vo.getId())==0) {
						agencyGroupVo = avo;
						break;
					}
				}
				
				agencyGroupVos.remove(agencyGroupVo);
				agencyGroupVos.add(vo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_AGENCY_GROUP");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.agencyGroup", "info.0030" , null, MessageTypeEnum.INFO));
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

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService2#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<AgencyGroupVo> vos = new ArrayList<AgencyGroupVo>();

			vos = dao.getPicklist();
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_AGENCY_GROUPS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

}
