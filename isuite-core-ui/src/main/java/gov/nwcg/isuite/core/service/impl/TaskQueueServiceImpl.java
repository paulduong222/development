package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.domain.impl.TaskQueueImpl;
import gov.nwcg.isuite.core.persistence.TaskQueueDao;
import gov.nwcg.isuite.core.service.TaskQueueService;
import gov.nwcg.isuite.core.vo.TaskFrequencyTypeVo;
import gov.nwcg.isuite.core.vo.TaskQueueVo;
import gov.nwcg.isuite.core.vo.TaskStatusTypeVo;
import gov.nwcg.isuite.core.vo.TaskTypeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;
import gov.nwcg.isuite.framework.types.TaskTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

public class TaskQueueServiceImpl extends BaseService implements TaskQueueService {
	private TaskQueueDao dao = null;
	
	public TaskQueueServiceImpl(){

	}

	public void initialization(){
		dao=(TaskQueueDao)context.getBean("taskQueueDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TaskQueueService#getCurrentTaskQueues(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getCurrentTaskQueues(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{

			Collection<TaskQueueVo> vos = new ArrayList<TaskQueueVo>();
			
			Collection<TaskQueue> entities = dao.getTaskQueues();

			if(CollectionUtility.hasValue(entities)){
				vos=TaskQueueVo.getInstances(entities, true);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_CURRENT_TASKQUEUES");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setRecordset(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.TaskQueueService#getSupportedTaskQueueList(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSupportedTaskQueueList(DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
			Collection<TaskTypeVo> list = TaskTypeEnum.getTaskTypeVos();
			Collection<TaskFrequencyTypeVo> freqList=TaskFrequencyTypeEnum.getTaskFrequencyTypeVos();
			Collection<TaskStatusTypeVo> statusList = TaskStatusTypeEnum.getTaskStatusTypeVos();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SUPPORTED_TASKLIST");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setRecordset(list);
			dialogueVo.setResultObjectAlternate(freqList);
			dialogueVo.setResultObjectAlternate2(statusList);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo saveTaskQueue(TaskQueueVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try{
		
			TaskQueue entity = TaskQueueVo.toEntity(vo,true);
			dao.save(entity);
			dao.flushAndEvict(entity);
			
			entity=dao.getById(entity.getId(),TaskQueueImpl.class);
			vo=TaskQueueVo.getInstance(entity,true);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_TASKQUEUE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.systemAdmin", "info.0030" , new String[]{}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
