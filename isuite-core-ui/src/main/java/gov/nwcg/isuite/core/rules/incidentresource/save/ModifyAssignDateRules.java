package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.EarliestDateVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;

public class ModifyAssignDateRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.MODIFY_ASSIGN_DATE.name();

	public ModifyAssignDateRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
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
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	@SuppressWarnings("unchecked")
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		EarliestDateVo edv = (EarliestDateVo) super.irDao.getEarliestDatesByIncResId(super.irEntity.getId());
		
		SortedSet<Date> dates = new TreeSet<Date>();
						
		// the modify assign date is also implemented in CostGenerator.java
		// if a change is needed, make change to the other class as well
		
		Date modifiedAssignDate;
					
		if (edv != null) {
			
			if (edv.getAssignDate() != null) dates.add(edv.getAssignDate());
			if (edv.getHiredDate() != null) dates.add(edv.getHiredDate());
			if (edv.getCheckInDate() != null) dates.add(edv.getCheckInDate());
			if (edv.getEarliestTimePostDate() != null) dates.add(edv.getEarliestTimePostDate());
			if (edv.getEarliestTimeAdjDate() != null) dates.add(edv.getEarliestTimeAdjDate());
			if (edv.getEarliestSubAssignDate() != null) dates.add(edv.getEarliestSubAssignDate());

			if(dates.size() > 0){
				modifiedAssignDate = DateUtil.addMilitaryTimeToDate(dates.first(), "2359");
			
				/*
				System.out.println("incidentResourceId: " + (edv.getIncidentResourceId() != null ? edv.getIncidentResourceId() : "null"));
				System.out.println("costDataId: " + (edv.getCostDataId() != null ? edv.getCostDataId() : "null"));
				System.out.println("assignDate: " + (edv.getAssignDate() != null ? edv.getAssignDate().toString() : "null"));
				System.out.println("hiredDate: " + (edv.getHiredDate() != null ? edv.getHiredDate().toString() : "null"));
				System.out.println("checkInDate: " + (edv.getCheckInDate() != null ? edv.getCheckInDate().toString() : "null"));
				System.out.println("earliestTimePostDate: " + (edv.getEarliestTimePostDate() != null ? edv.getEarliestTimePostDate().toString() : "null"));
				System.out.println("earliestTimeAdjDate: " + (edv.getEarliestTimeAdjDate() != null ? edv.getEarliestTimeAdjDate().toString() : "null"));
				System.out.println("earliestSubAssignDate: " + (edv.getEarliestSubAssignDate() != null ? edv.getEarliestSubAssignDate().toString() : "null"));			
				System.out.println("modifiedAssignDate: " + modifiedAssignDate.toString());
				*/
				
				super.irDao.updateCostAssignDate2(edv.getCostDataId(),modifiedAssignDate);
			}
		}
				

		//System.out.println("done");		
		
		
		
	}
}

