package gov.nwcg.isuite.core.rules.incident.savequestions;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.QuestionImpl;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupQuestionDao;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class SyncQuestionRules extends AbstractIncidentSaveQuestionsRule implements IRule{
	public static final String _RULE_NAME=IncidentSaveQuestionsRuleFactory.RuleEnum.SYNC_QUESTIONS.name();

	public SyncQuestionRules(ApplicationContext ctx)
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
		Long incidentGroupId = incidentDao.getIncidentGroupId(super.entity.getId());
		
		if(LongUtility.hasValue(incidentGroupId)){
			IncidentVo incidentVo = IncidentVo.getInstance(entity, true);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName(_RULE_NAME+"ACTION");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			courseOfActionVo.setIsComplete(true);
			courseOfActionVo.setStoredObject(incidentGroupId);
			courseOfActionVo.setStoredObject1(incidentVo);
			
			dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);

		}
		
		return _OK;
	}

	private Collection<String> getRemovedQuestions(IncidentVo incidentVo) {
		Collection<String> questions = new ArrayList<String>();
		Collection<IncidentQuestionVo> originalQuestions = incidentVo.getAirTravelQuestions();

		for(IncidentQuestionVo orig : originalQuestions) {
			boolean found = false;
			for(IncidentQuestionVo vo : vos) {
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
	
	private Collection<String> getUpdatedQuestions(IncidentVo incidentVo) {
		Collection<String> questions = new ArrayList<String>();
		
		Collection<IncidentQuestionVo> originalQuestions = incidentVo.getAirTravelQuestions();
		
		for(IncidentQuestionVo vo : super.vos){
			String q = vo.getQuestionVo().getQuestion();
			if(!q.endsWith("?"))
				q=vo.getQuestionVo().getQuestion()+"?";
			
			if(LongUtility.hasValue(vo.getId())){
				for(IncidentQuestionVo orig : originalQuestions) {
					if(orig.getId().compareTo(vo.getId())==0){
						if(!orig.getQuestionVo().getQuestion().equals(q)){
							questions.add(orig.getQuestionVo().getQuestion());
						}
					}
				}
				
				questions.add(vo.getQuestionVo().getQuestion());
			}
		}
		
		return questions;
	}
	
	private Collection<String> getNewQuestions(IncidentVo incidentVo) {
		Collection<String> questions = new ArrayList<String>();
		
		for(IncidentQuestionVo vo : super.vos){
			if(!LongUtility.hasValue(vo.getId())){
				questions.add(vo.getQuestionVo().getQuestion());
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
			Long groupId=(Long)coaVo.getStoredObject();
			IncidentVo incidentVo = (IncidentVo)coaVo.getStoredObject1();
			
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			IncidentGroupQuestionDao igQuestionDao=(IncidentGroupQuestionDao)context.getBean("incidentGroupQuestionDao");
			IncidentQuestionDao iQuestionDao=(IncidentQuestionDao)context.getBean("incidentQuestionDao");
			
			
			Collection<String> removedQuestions = this.getRemovedQuestions(incidentVo);
			// remove questions from group and all incidents in group
			igDao.removeQuestionFromGroup(groupId,removedQuestions);
			
			
			/* for each questionvo in vos
			 * update/insert into incgroupquestion
			 * update/insert into other incidents in group
			 */
			Collection<IncidentQuestionVo> originalQuestions = incidentVo.getAirTravelQuestions();

			// update/insert incgroupquestions
			for(IncidentQuestionVo vo : super.vos){
				String q = vo.getQuestionVo().getQuestion();
				if(!q.endsWith("?"))
					q=vo.getQuestionVo().getQuestion()+"?";

				Long igQuestionId=igDao.getGroupQuestionId(q, groupId);
				IncidentGroupQuestion igq = null;
				if(!LongUtility.hasValue(igQuestionId)){
					// insert 
					igq=new IncidentGroupQuestionImpl();
					IncidentGroup incgroup = new IncidentGroupImpl();
					incgroup.setId(groupId);
					igq.setIncidentGroup(incgroup);
					igq.setVisible(vo.getVisible());
					igq.setPosition(vo.getPosition());
					Question question = new QuestionImpl();
					question.setQuestion(q);
					question.setQuestionType(QuestionTypeEnum.AIRTRAVEL);
					question.setStandard(false);
					igq.setQuestion(question);
					
					igQuestionDao.save(igq);
					igQuestionDao.flushAndEvict(igq);
				}else{
					// update
					IncidentGroupQuestion igQuestion=igQuestionDao.getById(igQuestionId, IncidentGroupQuestionImpl.class);
					if(null != igQuestion){
						igQuestion.setPosition(vo.getPosition());
						igQuestion.setVisible(vo.getVisible());
						
						igQuestionDao.save(igQuestion);
						igQuestionDao.flushAndEvict(igQuestion);
					}
				}
			}
			
			// update/insert other incident incquestions
			Collection<Long> incidentIds = igDao.getIncidentIdsInGroup(groupId);
			for(Long incId : incidentIds){
				if(incId.compareTo(incidentVo.getId())!=0){
					for(IncidentQuestionVo vo : super.vos){
						String q = vo.getQuestionVo().getQuestion();
						if(!q.endsWith("?"))
							q=vo.getQuestionVo().getQuestion()+"?";

						Long iQuestionId=super.incidentDao.getIncidentQuestionId(q, incId);
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

}
