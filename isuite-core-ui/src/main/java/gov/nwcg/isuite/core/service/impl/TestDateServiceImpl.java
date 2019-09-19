package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.service.TestDateService;
import gov.nwcg.isuite.core.vo.TestDateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestDateServiceImpl extends BaseService implements TestDateService {

	public TestDateServiceImpl(){

	}

	public void initialization(){
	}

	public DialogueVo getVo(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			//System.setProperty("user.timezone","UTC");
			
			TestDateVo vo = new TestDateVo();
			vo.setGmtDate(Calendar.getInstance().getTime());
			//vo.setUtcDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
			
			TimeZone tz = Calendar.getInstance().getTimeZone();
			
			Date dt1 = Calendar.getInstance().getTime();
			
			long ms = dt1.getTime();
			int offset = tz.getOffset(ms);
			
			Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	        utcCal.setTime(dt1);
	        utcCal.add(Calendar.MILLISECOND, (-offset));
	        dt1=utcCal.getTime();
	        
			System.out.println(DateUtil.toDateString(dt1, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
			
			vo.setUtcDate(dt1);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_VO");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(vo);
			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public static void main(String[] args){
		Date dt1 = Calendar.getInstance().getTime();
		System.out.println(DateUtil.toDateString(dt1, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));
		
		TimeZone tz = Calendar.getInstance().getTimeZone();
		
		
		long ms = dt1.getTime();
		int offset = tz.getOffset(ms);
		
		Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utcCal.setTime(dt1);
        utcCal.add(Calendar.MILLISECOND, offset);
        dt1=utcCal.getTime();
        
		System.out.println(DateUtil.toDateString(dt1, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MI_SS));

	}

}
