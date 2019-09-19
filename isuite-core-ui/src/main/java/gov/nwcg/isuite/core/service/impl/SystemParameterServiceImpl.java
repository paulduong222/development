package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.service.SystemParameterService;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

public class SystemParameterServiceImpl extends BaseService implements SystemParameterService {
	private SystemParameterDao systemParameterDao;
	
	/**
	 * Default Constructor
	 */
	public SystemParameterServiceImpl() {
	}
	
	public void initialization(){
		systemParameterDao = (SystemParameterDao)super.context.getBean("systemParameterDao");
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemParameterService#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try 
		{
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter[id=null]");

			SystemParameter entity = systemParameterDao.getById(id, SystemParameterImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter[id=null]");
				
			SystemParameterVo vo = SystemParameterVo.getInstance(entity, true);         
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_BY_ID_SYSPARAM");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemParameterService#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try {
			Collection<SystemParameterVo> vos = SystemParameterVo.getInstances(systemParameterDao.getGrid(), true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_GRID_SYSPARAM");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setRecordset(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemParameterService#save(gov.nwcg.isuite.core.vo.SystemParameterVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(SystemParameterVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{

			SystemParameter entity = null;

			/*
			 *  Updating existing one
			 */
			entity = systemParameterDao.getById(vo.getId(), SystemParameterImpl.class);

			if (entity == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter["+vo.getId()+"]");
				
			if(LongUtility.hasValue(vo.getId())){
				systemParameterDao.updateParameter(vo);
			}else{
				entity = SystemParameterVo.toEntity(vo, true);
				systemParameterDao.save(entity);
				systemParameterDao.flushAndEvict(entity);
				vo=SystemParameterVo.getInstance(entity, true);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_SYSPARAM");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.systemAdmin", "info.0030" , new String[]{}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemParameterService#deleteSystemParameter(gov.nwcg.isuite.core.vo.SystemParameterVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteSystemParameter (SystemParameterVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			SystemParameter entity = null;
			entity = SystemParameterVo.toEntity(vo,true);
			systemParameterDao.delete(entity);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_SYSPARAM");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.systemAdmin", "info.0028" , new String[]{"System Parameter"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);

		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemParameterService#getByParameterName(java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getByParameterName(String nm, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			SystemParameterVo vo= SystemParameterVo.getInstance(systemParameterDao.getByParameterName(nm), true);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_BY_SYSPARAM_NAME");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);

		}catch ( Exception e ) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
