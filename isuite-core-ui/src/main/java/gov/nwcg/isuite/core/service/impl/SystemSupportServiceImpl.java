package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.service.SystemSupportService;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Calendar;
import java.util.Collection;


public class SystemSupportServiceImpl extends BaseService implements SystemSupportService {

	public SystemSupportServiceImpl(){
		super();
	}

	public DialogueVo getHello(String secondsDelay, DialogueVo dialogueVo) throws ServiceException {
		
		try{
			long milliStart=Calendar.getInstance().getTimeInMillis();
			
			if(StringUtility.hasValue(secondsDelay)){
				int val=Integer.parseInt(secondsDelay);
				long delay=(1000 * val);
				Thread.sleep(delay);			
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GETHELLO");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);

			long milliStop=Calendar.getInstance().getTimeInMillis();
			long milliDiff=milliStop-milliStart;
			String diff=String.valueOf(milliDiff);
			coaVo.setStoredObject3(diff);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo sendRetrieveVos(Collection<IncidentResourceGridVo> vos, DialogueVo dialogueVo) throws ServiceException {
		try{
			long milliStart=Calendar.getInstance().getTimeInMillis();
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SEND_RETRIEVE_VOS");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setStoredObject(vos);
			
			long milliStop=Calendar.getInstance().getTimeInMillis();
			long milliDiff=milliStop-milliStart;
			String diff=String.valueOf(milliDiff);
			coaVo.setStoredObject3(diff);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	
}
