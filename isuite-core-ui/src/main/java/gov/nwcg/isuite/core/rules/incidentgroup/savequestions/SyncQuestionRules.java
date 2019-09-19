package gov.nwcg.isuite.core.rules.incidentgroup.savequestions;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.QuestionImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupQuestionDao;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.vo.IncidentGroupQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class SyncQuestionRules extends AbstractIncidentGroupSaveQuestionsRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupSaveQuestionsRuleFactory.RuleEnum.SYNC_QUESTIONS.name();

	public SyncQuestionRules(ApplicationContext ctx, String rulename)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
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
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));

				
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
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME+"ACTION");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			courseOfActionVo.setIsComplete(true);
			
			dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
		
		return _OK;
	}

	private Collection<String> getRemovedQuestions(IncidentGroupVo igVo) {
		Collection<String> questions = new ArrayList<String>();
		Collection<IncidentGroupQuestionVo> originalQuestions = igVo.getAirTravelQuestions();

		for(IncidentGroupQuestionVo orig : originalQuestions) {
			boolean found = false;
			for(IncidentGroupQuestionVo vo : vos) {
				if(LongUtility.hasValue(vo.getId()) && vo.getId().equals(orig.getId()) ) {
					found = true;
					break;
				}
			}
			if(!found) {
				questions.add(orig.getQuestionVo().getQuestion());
			}
		}
		
		return questions;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME+"ACTION");
		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			Long groupId=super.entity.getId();
			IncidentGroupVo igVo = IncidentGroupVo.getInstance(entity, true);
			
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
			IncidentGroupQuestionDao igQuestionDao=(IncidentGroupQuestionDao)context.getBean("incidentGroupQuestionDao");
			IncidentQuestionDao iQuestionDao=(IncidentQuestionDao)context.getBean("incidentQuestionDao");
			
			Collection<String> removedQuestions = this.getRemovedQuestions(igVo);
			
			if(CollectionUtility.hasValue(removedQuestions)){
				// remove questions from group and all incidents in group
				igDao.removeQuestionFromGroup(groupId,removedQuestions);
			}
			
			/* for each questionvo in vos
			 * update/insert into other incidents in group
			 */
			Collection<IncidentGroupQuestionVo> originalQuestions = igVo.getAirTravelQuestions();
			
			// update/insert other incident incquestions
			Collection<Long> incidentIds = igDao.getIncidentIdsInGroup(groupId);
			for(Long incId : incidentIds){
					for(IncidentGroupQuestionVo vo : super.vos){
						String q = vo.getQuestionVo().getQuestion();
						if(!q.endsWith("?"))
							q=vo.getQuestionVo().getQuestion()+"?";

						Long iQuestionId=incDao.getIncidentQuestionId(q, incId);
						IncidentQuestion incQ = null;
						if(!LongUtility.hasValue(iQuestionId)){
							// insert 
							incQ=new IncidentQuestionImpl();
							Incident inc = new IncidentImpl();
							inc.setId(incId);
							incQ.setIncident(inc);
							incQ.setVisible(vo.getVisible());
							incQ.setPosition(vo.getPosition());
							Question question = new QuestionImpl();
							question.setQuestion(q);
							question.setQuestionType(QuestionTypeEnum.AIRTRAVEL);
							question.setStandard(false);
							incQ.setQuestion(question);
							
							iQuestionDao.save(incQ);
							iQuestionDao.flushAndEvict(incQ);
						}else{
							// update
							IncidentQuestion iQuestion=iQuestionDao.getById(iQuestionId, IncidentQuestionImpl.class);
							if(null != iQuestion){
								iQuestion.setPosition(vo.getPosition());
								iQuestion.setVisible(vo.getVisible());
								
								iQuestionDao.save(iQuestion);
								iQuestionDao.flushAndEvict(iQuestion);
							}
						}
					}
			}
			
		}
	}

}
