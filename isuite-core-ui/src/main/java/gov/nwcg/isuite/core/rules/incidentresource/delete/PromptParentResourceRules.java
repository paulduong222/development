package gov.nwcg.isuite.core.rules.incidentresource.delete;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;

import org.springframework.context.ApplicationContext;

public class PromptParentResourceRules extends AbstractIncidentResourceDeleteRule implements IRule {
	public static final String _RULE_NAME="PROMPT_PARENT_RESOURCE";

	public PromptParentResourceRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

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
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
				
			
		} catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception{

		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		Collection<Long> irChildIds = new ArrayList<Long>();

		for(Long irid : super.incidentResourceIds){
			IncidentResource irEntity =irDao.getById(irid, IncidentResourceImpl.class);
			Resource r = irEntity.getResource();
			if(r.getChildResources().size()>0)
				irChildIds.addAll(this.getChildIncidentResourceIds(r.getChildResources()));
		}

		if(CollectionUtility.hasValue(irChildIds)){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.incidentResources"
								,"action.0336"
								,new String[]{""}
								,PromptVo._YES | PromptVo._NO));
			
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

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

		}else if(getPromptResult(dialogueVo) == PromptVo._NO){

			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.resource", "text.abortProcess" , new String[]{"delete parent resource"}, MessageTypeEnum.INFO));
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
