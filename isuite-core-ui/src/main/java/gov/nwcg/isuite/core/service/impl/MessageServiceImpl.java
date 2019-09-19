package gov.nwcg.isuite.core.service.impl;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.impl.MessageImpl;
import gov.nwcg.isuite.core.persistence.MessageDao;
import gov.nwcg.isuite.core.rules.MessagePopUpSaveRulesHandler;
import gov.nwcg.isuite.core.rules.MessageSaveRulesHandler;
import gov.nwcg.isuite.core.service.MessageService;
import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class MessageServiceImpl extends BaseService implements MessageService {
	private MessageDao messageDao;
	
	
	public MessageServiceImpl(){
		
	}
	
	public void initialization(){
		messageDao = (MessageDao)super.context.getBean("messageDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.MessageService#save(gov.nwcg.isuite.core.vo.MessageBoardVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(MessageBoardVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Message entity = null;
			
			if(LongUtility.hasValue(vo.getId())){
				entity = messageDao.getById(vo.getId(), MessageImpl.class);
			}
			
			MessageSaveRulesHandler rulesHandler = new MessageSaveRulesHandler(context);
			
			if(rulesHandler.execute(dialogueVo)==MessageSaveRulesHandler._OK) {
				entity = MessageBoardVo.toEntity(entity, vo, true);
				
				messageDao.save(entity);
				messageDao.flushAndEvict(entity);
				
				entity = messageDao.getById(entity.getId(), MessageImpl.class);
				vo = MessageBoardVo.getInstance(entity, true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_MESSAGE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.message", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getStaticMessage(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			MessageBoardVo vo = messageDao.getStaticMessage();
			
			if(super.getRunMode().equals("SITE")){
				// adding manual insert for default static message in case
				// this database originated from a restore and the message is not in the db
				if(null==vo || !LongUtility.hasValue(vo.getId())){
					messageDao.createDefaultStaticMessage();
					vo=messageDao.getStaticMessage();
				}
			}
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_STATIC_MESSAGE");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(vo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getPopUpGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			Collection<MessageBoardVo> vos = messageDao.getPopUpGrid();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_POPUP_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo savePopUp(MessageBoardVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Message entity = null;
			
			if(LongUtility.hasValue(vo.getId())){
				entity = messageDao.getById(vo.getId(), MessageImpl.class);
			}
			
			MessagePopUpSaveRulesHandler rulesHandler = new MessagePopUpSaveRulesHandler(context);
			
			if(rulesHandler.execute(vo, dialogueVo)==MessageSaveRulesHandler._OK) {
				entity = MessageBoardVo.toEntity(entity, vo, true);
				
				messageDao.save(entity);
				messageDao.flushAndEvict(entity);
				
				entity = messageDao.getById(entity.getId(), MessageImpl.class);
				vo = MessageBoardVo.getInstance(entity, true);
				
				//Build course of action
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_POPUP_MESSAGE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.message", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getPopUpMessages(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			Collection<MessageBoardVo> vos = messageDao.getPopUpMessages(super.getUserSessionVo().getClientLocalDate());
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_POPUP_MESSAGES");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo deletePopUpMessage(MessageBoardVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) { dialogueVo = new DialogueVo(); }
		
		try{
			if((null == vo.getId()) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown message.");
			
			Message entity = null;
			if(LongUtility.hasValue(vo.getId())) {
				entity = messageDao.getById(vo.getId(), MessageImpl.class);
			}
			
			messageDao.delete(entity);
			
			//Build course of action
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_POPUP_MESSAGE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.message", "info.0028" , new String[]{"Message"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	
	

}
