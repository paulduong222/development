package gov.nwcg.isuite.core.rules.costgroups.save;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.CostGroupDefaultAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDayShareImpl;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDaySharePercentageImpl;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDayShareDao;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDaySharePercentageDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class SyncAgencyDatesRules extends AbstractCostGroupsSaveRule implements IRule {
	public static final String _RULE_NAME=CostGroupsSaveRuleFactory.RuleEnum.SYNC_AGENCY_DATES.name();
	
	public SyncAgencyDatesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * This rule will be implemented when user roles are revisited in the future.
			 * Skip rule.
			 */
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_RULE_NAME+"SYNCAGENCYDATES");
		coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
		if(null==entity)
			coaVo.setStoredObject("NEWCOSTGROUP");
		else{
			Date origStartDate=entity.getStartDate();
			coaVo.setStoredObject1(origStartDate);
		}
		
		dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		 * sync the agency percentages dates based on default agency percentages
		 */
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName+"SYNCAGENCYDATES");
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			
			CostGroupAgencyDayShareDao cgAgDsDao = (CostGroupAgencyDayShareDao)context.getBean("costGroupAgencyDayShareDao");
			CostGroupAgencyDaySharePercentageDao cgAgDsPctDao = (CostGroupAgencyDaySharePercentageDao)context.getBean("costGroupAgencyDaySharePercentageDao");
			
			Object object1=coaVo.getStoredObject();
			String value1="";
			if(null!=object1){
				value1=(String)object1;
			}
			
			// if new cost group create agency dates records from cost group start date to system date
			if(StringUtility.hasValue(value1) && value1.equals("NEWCOSTGROUP")){
				
				// create agency percentage dates from costgroupstartdate to clientDate
				String clientDate=super.getUserSessionVo().getClientLocalDate();
				//Date dtClient=DateUtil.toDate(clientDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				//dtClient=DateUtil.addMilitaryTimeToDate(dtClient, "2359");
				Date dt=null;
				if(super.getRunMode().equals("SITE")){
					dt=EISuiteCalendar.getCalendarDate(super.getUserSessionDbName());
				}else{
					dt=EISuiteCalendar.getCalendarDate("");
				}
				Date dtClient=DateUtil.addMilitaryTimeToDate(dt, "2359");
				dtClient = DateUtil.addDays(dtClient, 1);

				Date startDate=entity.getStartDate();
				startDate=DateUtil.addMilitaryTimeToDate(startDate, "2359");
			
				// create from startdate to dtclient
				this.createAgencyPercentageDates(startDate, dtClient, true);

			}
			
			// if cost group start date changed, sync the agency dates 
			if(!StringUtility.hasValue(value1) || !value1.equals("NEWCOSTGROUP")){
				Object object2=coaVo.getStoredObject1();
				if(null != object2){
					Date origDate=(Date)object2;
					origDate=DateUtil.addMilitaryTimeToDate(origDate, "2359");
					
					Date newDate=super.costGroupVo.getStartDate();
					newDate=DateUtil.addMilitaryTimeToDate(newDate, "2359");
					
					if(!DateUtil.isSameDate(origDate, newDate)){
						// do the sync
						if(origDate.before(newDate)){
							// remove records before newDate
							cgAgDsDao.deleteAllBeforeDate(entity.getId(), newDate);
						}
						
						if(origDate.after(newDate)){
							// add new records starting at newdate  (backfill dates)
							this.createAgencyPercentageDates(newDate, origDate, false);
						}
						
					}
				}
			}
			
		}

	}

	private void createAgencyPercentageDates(Date startDate, Date endDate, Boolean useCGDefault) throws Exception{
		CostGroupAgencyDayShareDao cgAgDsDao = (CostGroupAgencyDayShareDao)context.getBean("costGroupAgencyDayShareDao");
		CostGroupAgencyDaySharePercentageDao cgAgDsPctDao = (CostGroupAgencyDaySharePercentageDao)context.getBean("costGroupAgencyDaySharePercentageDao");

		CostGroupAgencyDayShare dayShare=null;
		if(useCGDefault==false){
			dayShare=cgAgDsDao.getLastByCostGroup(entity.getId());			
		}
		
		while(startDate.before(endDate)){
		///while(!startDate.after(endDate) && !DateUtil.isSameDate(startDate, endDate)){
			CostGroupAgencyDayShare newDs=new CostGroupAgencyDayShareImpl();
			newDs.setCostGroup(entity);
			newDs.setAgencyShareDate(startDate);
			
			cgAgDsDao.save(newDs);
			cgAgDsDao.flushAndEvict(newDs);

			if(useCGDefault==true){
				for(CostGroupDefaultAgencyDaySharePercentage pct : entity.getCostGroupDfAgPcts()){
					CostGroupAgencyDaySharePercentage newPct= new CostGroupAgencyDaySharePercentageImpl();
					newPct.setCostGroupAgencyDayShare(newDs);
					newPct.setAgency(pct.getAgency());
					newPct.setPercentage(pct.getPercentage());
					
					cgAgDsPctDao.save(newPct);
					cgAgDsPctDao.flushAndEvict(newPct);
				}
				
			}else{
				if(null != dayShare){
					for(CostGroupAgencyDaySharePercentage pct : dayShare.getCostGroupAgencyDaySharePercentages()){
						CostGroupAgencyDaySharePercentage newPct= new CostGroupAgencyDaySharePercentageImpl();
						newPct.setCostGroupAgencyDayShare(newDs);
						newPct.setAgency(pct.getAgency());
						newPct.setPercentage(pct.getPercentage());
						
						cgAgDsPctDao.save(newPct);
						cgAgDsPctDao.flushAndEvict(newPct);
					}
				}
				
			}
			startDate=DateUtil.addDays(startDate, 1);
		}
		
	}
}
