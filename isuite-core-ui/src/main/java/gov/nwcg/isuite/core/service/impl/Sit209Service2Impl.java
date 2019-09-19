package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.impl.Sit209Impl;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.rules.ReferenceDataDelete209CodeRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSave209CodeRulesHandler;
import gov.nwcg.isuite.core.service.Sit209Service2;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class Sit209Service2Impl extends BaseService implements Sit209Service2 {
	private Sit209Dao dao;

	public Sit209Service2Impl(){

	}

	public void initialization(){
		dao = (Sit209Dao)super.context.getBean("sit209Dao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.Sit209Service2#deleteSit209(gov.nwcg.isuite.core.vo.Sit209Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteSit209(Sit209Vo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown sit 209 code.");
			
			Sit209 entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), Sit209Impl.class);
			
			ReferenceDataDelete209CodeRulesHandler ruleHandler = new ReferenceDataDelete209CodeRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK) {
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<Sit209Vo> newList = new ArrayList<Sit209Vo>();
				
				for(Sit209Vo svo : gvo.getSit209CodeVos()) {
					if(svo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(svo);
				}
				
				gvo.setSit209CodeVos(newList);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_209");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.sit209Codes", "info.0028" , new String[]{"209 Code"}, MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.core.service.Sit209Service2#save(gov.nwcg.isuite.core.vo.Sit209Vo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(Sit209Vo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		   
			
			Sit209 entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), Sit209Impl.class);
			
			ReferenceDataSave209CodeRulesHandler rulesHandler = new ReferenceDataSave209CodeRulesHandler(context);
			if(rulesHandler.execute(vo, entity,dialogueVo)==ReferenceDataSave209CodeRulesHandler._OK){

				entity = Sit209Vo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity=dao.getById(entity.getId(), Sit209Impl.class);
				
				vo = Sit209Vo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<Sit209Vo> sit209CodeVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getSit209CodeVos();
				Sit209Vo sit209Vo = null;
				
				for(Sit209Vo svo : sit209CodeVos) {
					if(svo.getId().compareTo(vo.getId())==0) {
						sit209Vo = svo;
						break;
					}
				}
				
				sit209CodeVos.remove(sit209Vo);
				sit209CodeVos.add(vo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_209");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.sit209Codes", "info.0030" , null, MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.core.service.Sit209Service2#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
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
	 * @see gov.nwcg.isuite.core.service.Sit209Service2#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<Sit209Vo> vos = new ArrayList<Sit209Vo>();

			vos = dao.getPicklist();
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_SIT209CODES");
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
