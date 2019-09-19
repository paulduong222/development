package gov.nwcg.isuite.core.rules.incident.syncsupplimental;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDayShareImpl;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDaySharePercentageImpl;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDayShareDao;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDaySharePercentageDao;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class SyncCostGroupAgenciesRules extends AbstractSyncSupplimentalRule implements IRule{
	public static final String _RULE_NAME=SyncSupplimentalRuleFactory.RuleEnum.SYNC_COST_GROUP_AGENCIES.name();

	public SyncCostGroupAgenciesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		try{
			
			// sync the cost group date agency pct allocations
			
			// get clientDate
			String clientDate="";
			Date dt=null;
			if(super.getRunMode().equals("SITE")){
				dt=EISuiteCalendar.getCalendarDate(super.getUserSessionDbName());
			}else{
				dt=EISuiteCalendar.getCalendarDate("");
			}
			Date dtClient=DateUtil.addMilitaryTimeToDate(dt, "2359");
			//dtClient = DateUtil.addDays(dtClient, 1);
			
			CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");
			CostGroupAgencyDayShareDao cgAgDsDao = (CostGroupAgencyDayShareDao)context.getBean("costGroupAgencyDayShareDao");
			CostGroupAgencyDaySharePercentageDao cgAgDsPctDao = (CostGroupAgencyDaySharePercentageDao)context.getBean("costGroupAgencyDaySharePercentageDao");
			
			Collection<CostGroup> costGroups=cgDao.getByIncidentId(super.incidentEntity.getId());
			if(CollectionUtility.hasValue(costGroups)){
				for(CostGroup cg : costGroups){
					CostGroupAgencyDayShare dayShare=cgAgDsDao.getLastByCostGroup(cg.getId());
					if(null != dayShare){
						Date dsDate=dayShare.getAgencyShareDate();
						dsDate=DateUtil.addMilitaryTimeToDate(dsDate, "2359");

						while(dsDate.before(dtClient)){
							dsDate=DateUtil.addDays(dsDate, 1);
							CostGroupAgencyDayShare newDs=new CostGroupAgencyDayShareImpl();
							newDs.setCostGroup(cg);
							newDs.setAgencyShareDate(dsDate);
							
							cgAgDsDao.save(newDs);
							cgAgDsDao.flushAndEvict(newDs);
							
							if(CollectionUtility.hasValue(dayShare.getCostGroupAgencyDaySharePercentages())){
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
					}
				}
			}
		}catch(Exception e){
			// smother
		}
	}

}
