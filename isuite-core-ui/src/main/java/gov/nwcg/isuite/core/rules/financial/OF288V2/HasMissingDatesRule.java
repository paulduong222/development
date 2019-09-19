package gov.nwcg.isuite.core.rules.financial.OF288V2;

import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.MissingPostingDateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;

public class HasMissingDatesRule extends AbstractInvoiceGenerationRule implements IRule {

	public HasMissingDatesRule(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
			
			if(isCurrentCourseOfAction(dialogueVo, ruleName)){
				// add to processed
				return checkPromptResult(dialogueVo);
			}
			
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
			    return _FAIL;
			} 	
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		if(filter.getPrintInvoiceOnly() || filter.getPrintDeductionsAndInvoice()) {
			
			TimePostDao tpDao= (TimePostDao)context.getBean("timePostDao");
			
			Collection<MissingPostingDateVo> mpdVos = new ArrayList<MissingPostingDateVo>();

			for(IncidentResourceTimeDataVo irTimeDataVo : super.irTimeDataVos){
				Collection<Date> irDates = new ArrayList<Date>();
				
				for(IncidentResourceTimePostDataVo dataVo : super.timePostDataVos){
					if(dataVo.getIncidentResourceId().compareTo(irTimeDataVo.getIncidentResourceId())==0){
						irDates.add(dataVo.getPostStartDate());
					}
				}
				//Collection<Date> irDates = tpDao.getNonInvoicedUniqueTimePostDates(irTimeDataVo.getIncidentResourceId());

				if(CollectionUtility.hasValue(irDates)){
					Date firstDate=null;
					Date lastDate=null;
					Set<String> dateHash = new HashSet<String>();

					String resourceName = 
						(StringUtility.hasValue(irTimeDataVo.getResourceName())
								? irTimeDataVo.getResourceName()
								: irTimeDataVo.getFirstName()+" "+irTimeDataVo.getLastName());
					
					String requestNumber = irTimeDataVo.getRequestNumber();
					
					int i=0;
					for(Date dt : irDates){
						if(i==0)
							firstDate=dt;
					
						String sdate=DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY);
						if(!dateHash.contains(sdate))
							dateHash.add(sdate);
						
						lastDate=dt;
						i++;
					}
					
					while(!DateUtil.isSameDate(firstDate, lastDate)){
						String sdate=DateUtil.toDateString(firstDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
						if(!dateHash.contains(sdate)){
							MissingPostingDateVo mpdVo = new MissingPostingDateVo();
							mpdVo.setIncidentResourceId(irTimeDataVo.getIncidentResourceId());
							mpdVo.setRequestNumber(requestNumber);
							mpdVo.setResourceName(resourceName);
							mpdVo.setPostDate(DateUtil.toDateString(firstDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));
							mpdVos.add(mpdVo);
						}
						
						firstDate=DateUtil.addDays(firstDate, 1);
					}
					
				}
			}
			
			if(CollectionUtility.hasValue(mpdVos)){
			    CourseOfActionVo coa = new CourseOfActionVo();
			    coa.setCoaName(ruleName);
			    coa.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
				coa.setCustomPromptVo(new CustomPromptVo("MISSINGPOSTINGS","text.time" ,"action.0142",mpdVos));
				coa.setStoredObject(mpdVos);
		    
			    dialogueVo.setCourseOfActionVo(coa);
			    
			    return _FAIL;
			}
				
		
		}
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
		// check prompt result
		if(getCustomPromptResult(dialogueVo) == PromptVo._YES) {

			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

		}else if(getCustomPromptResult(dialogueVo) == PromptVo._NO){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "text.abortProcess" , new String[]{"post time"}, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
