package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.persistence.QuickStatsDao;
import gov.nwcg.isuite.core.service.QuickStatsService2;
import gov.nwcg.isuite.core.vo.QuickStatsVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

public class QuickStatsService2Impl extends BaseService implements QuickStatsService2 {

	@Override
	public DialogueVo getQuickStats(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(null == dialogueVo) {
				dialogueVo = new DialogueVo();
			}

			QuickStatsDao quickStatsDao = (QuickStatsDao)super.context.getBean("quickStatsDao");
			QuickStatsVo quickStatsVo = new QuickStatsVo();


			quickStatsVo = quickStatsDao.getQuickStats(incidentId, incidentGroupId);
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_QUICK_STATS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(quickStatsVo);
//			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
