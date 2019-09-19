package gov.nwcg.isuite.core.rules.incidentresource.delete;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

public class CheckTimeCostRecordsRules extends AbstractIncidentResourceDeleteRule implements IRule {
	public static final String _RULE_NAME="CHECK_TIME_COST_RECORDS";
	
	public CheckTimeCostRecordsRules(ApplicationContext ctx){
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
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
			}else{
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		StringBuffer msg = new StringBuffer();
		
		if(super.gridVo.getChildren().size() > 0){
			msg.append("The primary resource and all subordinate resources rostered to the primary will be deleted. \n\n");
		}else{
			msg.append("The resource will be deleted. \n\n");
		}
		
		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		
		//check subordinate resources
		Collection<Long> irChildIds = new ArrayList<Long>();
		
		for(Long irid : incidentResourceIds){
			IncidentResource irEntity = irDao.getById(irid, IncidentResourceImpl.class);
			Resource r = irEntity.getResource();
			if(r.getChildResources().size()>0){
				irChildIds.addAll(this.getChildIncidentResourceIds(r.getChildResources()));
			}
		}
		
		incidentResourceIds.addAll(irChildIds);
		
		
		Object[] critRecords = irDao.getResourceTimeCostRecordCount(incidentResourceIds);
		
		int timePostCount = 0;
		int costCount = 0;
		int timeAdjCount = 0;
		
		for(int i=0;i<critRecords.length;i++){
			switch(i){
				case 0:
					timePostCount = TypeConverter.convertToInt(critRecords[i]);
					break;
				case 1:
					costCount = TypeConverter.convertToInt(critRecords[i]);
					break;
				case 2:
					timeAdjCount = TypeConverter.convertToInt(critRecords[i]);
					break;
			}
		}
		
		if(timePostCount > 0 || costCount > 0 || timeAdjCount > 0 ){
			
			msg.append("The following data exists for the resource(s) and/or subordinate resource(s) you are deleting: \n\n");
			
			if(timePostCount > 0) {
				msg.append("\t Time Postings \n");
			}
			if(timeAdjCount > 0) {
				msg.append("\t Time Adjustments \n");
			}
			if(costCount > 0) {
				msg.append("\t Cost Records \n");
			}
		}
		
		msg.append("\nDo you want to continue?");
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_RULE_NAME);
		coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
		coaVo.setPromptVo(new PromptVo("text.incidentResources"
							,"info.generic"
							,new String[]{msg.toString()}
							,PromptVo._YES | PromptVo._NO));
		
		dialogueVo.setCourseOfActionVo(coaVo);
		
		return _FAIL;
	}
	
	private int checkPromptResult(DialogueVo dialogueVo) {
		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME+"ACTION");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			courseOfActionVo.setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
		}

		return _OK;
	}
	
	private Collection<Long> getChildIncidentResourceIds(Collection<Resource> childs) throws Exception{
		Collection<Long> rtnIds = new ArrayList<Long>();

		for(Resource c : childs){
			if(c.getIncidentResources().size()>0){
				IncidentResource ir = (IncidentResource)c.getIncidentResources().iterator().next();
				if(null != ir){
					rtnIds.add(ir.getId());
				}
			}
			
			if(CollectionUtility.hasValue(c.getChildResources())){
				rtnIds.addAll(getChildIncidentResourceIds(c.getChildResources()));
			}
		}
		
		return rtnIds;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
