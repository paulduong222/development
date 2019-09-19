package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupPrefsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.vo.IncidentGroupPrefsVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;

public class PromptApplySettingsToGroupRules extends AbstractIncidentGroupSaveRule implements IRule{
	
	public PromptApplySettingsToGroupRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			if(isCurrentCourseOfAction(dialogueVo, ruleName)){

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
					.add(super.buildNoActionCoaVo(ruleName,true));
				
			}
			
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
		
		/*
		 * C.R. 72
		 *	  When a user adds an Incident to an Incident Group, 
		 *    the system must display a message asking the user whether 
		 *    they would like to add those settings to all 
		 *    Incidents in the Incident Group. 
		 *    Yes and No buttons are available. 
		 *    
		 *    When the user selects Yes, the system must do the following:
		 *    
		 *	     Copy any non-standard Travel and Check-In Questions to all of 
		 *       the Incidents in the Incident Group.
		 *       
		 *	     Add any Other 1, 2, 3 fields to the other Incidents in the group. 
		 *       The system must append the fields to the other Incidents in the 
		 *       Incident Group, so the Incident may have several Other fields. 
		 *       There is no limit to the number of Other fields that can be appended 
		 *       to an Incident.
		 *       
		 *	     The system must retain the Quick Stats Item Codes Settings defined 
		 *       for each separate Incident. These will not be combined.
		 *       
		 *	     The system must retain all ICS-221 Customizations for each separate 
		 *       Incident. These will not be combined.
		 */
		if(hasNewIncidents()){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(ruleName);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.incidentGroup","action.cr72",null,PromptVo._YES | PromptVo._NO));
			coaVo.getPromptVo().setYesLabel("Yes");
			coaVo.getPromptVo().setNoLabel("No");
			
			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
		}
		
		return _OK;
	}
	
	private Boolean hasNewIncidents() {
		if(null != entity){
			for(IncidentVo ivo : newVo.getIncidentVos()){
				boolean bFound = false;
				
				for(Incident i : entity.getIncidents()){
					if(ivo.getId().compareTo(i.getId())==0)
						bFound=true;
				}
				
				if(bFound==false)
					return true;
			}
		}

		return false;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// ask trudi what is supposed to happen here
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			/*
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "text.abortProcess" , new String[]{"post adjustment"}, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
			*/
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

		/*
		 * Check Prompt Result.
		 * 
		 * When the user selects Yes, the system must do the following:
		 * 
		 *  Copy any non-standard Travel and Check-In Questions to all of the Incidents in the Incident Group.
		 * 
		 *  Add any Other 1, 2, 3 fields to the other Incidents in the group. 
		 *  		The system must append the fields to the other Incidents in the Incident Group, 
		 *  		so the Incident may have several Other fields. 
		 *  		There is no limit to the number of Other fields that can be appended to an Incident.
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(ruleName);
		
		try {
			if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
				
			}
		} catch(Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * Collect the Incident Group Settings for comparison
	 * 
	 * @param incGroup
	 * @param otherQuestions
	 * @param incQuestions
	 * @param otherPosition
	 * @param airPosition
	 * @param checkPosition
	 * @throws Exception
	 */
	private void loadIncidentGroupValues(IncidentGroup incGroup, 
										 ArrayList<IncidentGroupPrefs> otherQuestions, 
										 ArrayList<IncidentGroupQuestion> incQuestions, 
										 Positions positions) throws Exception {
		// Add IG Other field labels
		for(IncidentGroupPrefs igPref : incGroup.getIncidentGroupPrefs()) {
			if(igPref.getSectionName().equals(IncidentPrefsSectionNameEnum.OTHER_LABEL)){
				if(igPref.getFieldLabel() != null 
						&& !igPref.getFieldLabel().equalsIgnoreCase("") 
						&& !igPref.getFieldLabel().equalsIgnoreCase("Security")) {
					otherQuestions.add(igPref);
					if(positions.getOtherPosition() < igPref.getPosition())
						positions.setOtherPosition(igPref.getPosition());
				}
			}
		}
		
		// Add IG non-standard Air Travel & Check-In Questions
		for(IncidentGroupQuestion igQ : incGroup.getIncidentGroupQuestions()) {
			if(!igQ.getQuestion().isStandard() 
					&& (igQ.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)
						|| igQ.getQuestion().getQuestionType().equals(QuestionTypeEnum.PREPLANNING)	)) {
				incQuestions.add(igQ);
				if(igQ.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL) 
						&& positions.getAirPosition() < igQ.getPosition())
					positions.setAirPosition(igQ.getPosition());
				else if(igQ.getQuestion().getQuestionType().equals(QuestionTypeEnum.PREPLANNING) 
						&& positions.getCheckPosition() < igQ.getPosition())
					positions.setCheckPosition(igQ.getPosition());
			}
		}
	}
	
	/**
	 * Collect the unique Incident Settings for comparison
	 * 
	 * @param incGroup
	 * @param incident
	 * @param otherQuestions
	 * @param incQuestions
	 * @param otherPosition
	 * @param airPosition
	 * @param checkPosition
	 * @return
	 * @throws Exception
	 */
	private Boolean loadIncidentValues(IncidentGroup incGroup,
										 Incident incident,
										 ArrayList<IncidentGroupPrefs> otherQuestions, 
										 ArrayList<IncidentGroupQuestion> incQuestions, 
										 Positions positions) throws Exception {
		
		Boolean found;
		Boolean igUpdated = false;
		
		// check for any uncommon OTHER prefs
		for(IncidentPrefs iq : incident.getIncidentPrefs()) {
			found = false;
			if(iq.getSectionName().equals(IncidentPrefsSectionNameEnum.OTHER_LABEL)) {
				if(iq.getFieldLabel() != null
						&& !iq.getFieldLabel().equalsIgnoreCase("")
						&& !iq.getFieldLabel().equalsIgnoreCase("Security")) {
					for(IncidentGroupPrefs have : otherQuestions) {
						// check if we already have the field label in our list
						if(have.getFieldLabel().equalsIgnoreCase(iq.getFieldLabel())) {
							found = true;
							break;
						}
					}
					if(!found) {
						IncidentGroupPrefs newPref = new IncidentGroupPrefsImpl();
						newPref.setFieldLabel(iq.getFieldLabel());
						newPref.setIncidentGroup(incGroup);
						newPref.setIncidentGroupId(incGroup.getId());
						newPref.setSectionName(IncidentPrefsSectionNameEnum.OTHER_LABEL);
						newPref.setSelected(iq.isSelected());
						newPref.setPosition(positions.getOtherPositionPlus());
						
						otherQuestions.add(newPref);
						incGroup.getIncidentGroupPrefs().add(newPref);
						igUpdated = true;
					}
				}
			}
		}
		
		// check for uncommon Air Travel and Check-in Questions
		for(IncidentQuestion iq : incident.getIncidentQuestions()) {
			if(!iq.getQuestion().isStandard() 
					&& (iq.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)
						|| iq.getQuestion().getQuestionType().equals(QuestionTypeEnum.PREPLANNING))) {
				found = false;
				for(IncidentGroupQuestion q : incQuestions) {
					if(q.getQuestion().getQuestion().equalsIgnoreCase(iq.getQuestion().getQuestion())) {
						found = true;
						break;
					}
				}
				if(!found) {
					IncidentGroupQuestion newQ = new IncidentGroupQuestionImpl();
					newQ.setVisible(iq.isVisible());
					newQ.setIncidentGroup(incGroup);
					newQ.setIncidentGroupId(incGroup.getId());
					newQ.setQuestion(iq.getQuestion());
					
					if(iq.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)) {
						newQ.setPosition(positions.getAirPositionPlus());
						
					} else {
						newQ.setPosition(positions.getCheckPositionPlus());
						
					}
					
					incQuestions.add(newQ);
					incGroup.getIncidentGroupQuestions().add(newQ);
					igUpdated = true;
				}
			} 
		}
		return igUpdated;
	}

	/**
	 * Push the collected OtherFieldLabels into the Incidents
	 * 
	 * @param incident
	 * @param otherQuestions
	 * @param otherPosition
	 * @param updated
	 * @return
	 */
	private Boolean pushOtherFieldLables(Incident incident,
									 ArrayList<IncidentGroupPrefs> otherQuestions, 
									 Boolean updated) {
		Boolean found;
		
		if(otherQuestions.size() > 0) {
			int position = 0;
			for(IncidentPrefs ip : incident.getIncidentPrefs()) {
				if(ip.getPosition() > position)
					position = ip.getPosition();
			}
			
			for(IncidentGroupPrefs pref : otherQuestions) {
				found = false;
				for(IncidentPrefs ip : incident.getIncidentPrefs()) {
					if(ip.getSectionName().equals(IncidentPrefsSectionNameEnum.OTHER_LABEL)) {
						if(ip.getFieldLabel() != null && ip.getFieldLabel().equalsIgnoreCase(pref.getFieldLabel())) {
							found = true;
							break;
						}
					}
				}
				if(!found) {
					IncidentPrefs newIP = new IncidentPrefsImpl();
					newIP.setFieldLabel(pref.getFieldLabel());
					newIP.setIncident(incident);
					newIP.setSectionName(IncidentPrefsSectionNameEnum.OTHER_LABEL);
					newIP.setSelected(pref.isSelected());
					newIP.setPosition(++position);
					
					incident.getIncidentPrefs().add(newIP);
					updated = true;
				}
			}
		}
		return updated;
	}

	/**
	 * Push the collected Questions into the Incidents
	 * 
	 * @param incident
	 * @param incQuestions
	 * @param airPosition
	 * @param checkPosition
	 * @param updated
	 * @return
	 * @throws PersistenceException 
	 */
	private Boolean pushQuestions(Incident incident,
									 ArrayList<IncidentGroupQuestion> incQuestions, 
									 Positions positions, 
									 Boolean updated,
									 IncidentQuestionDao iqDao) throws PersistenceException {
		Boolean found;
		
		if(incQuestions.size() > 0) {
			for(IncidentGroupQuestion quest : incQuestions) {
				found = false;
				for(IncidentQuestion incQuest : incident.getIncidentQuestions()) {
					if(incQuest.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)
							|| incQuest.getQuestion().getQuestionType().equals(QuestionTypeEnum.PREPLANNING)) {
						if(incQuest.getQuestion().getQuestion().equalsIgnoreCase(quest.getQuestion().getQuestion())) {
							found = true;
							break;
						}
					} 
				} 
				if(!found) {
					IncidentQuestion newIQ = new IncidentQuestionImpl();
					newIQ.setIncident(incident);
					newIQ.setQuestion(quest.getQuestion());
					newIQ.setVisible(quest.isVisible());
					if(quest.getQuestion().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)) {
						newIQ.setPosition(positions.getAirPositionPlus());
					} else if(quest.getQuestion().getQuestionType().equals(QuestionTypeEnum.PREPLANNING)) {
						newIQ.setPosition(positions.getCheckPositionPlus());
					}
					
					iqDao.save(newIQ);
					
					incident.getIncidentQuestions().add(newIQ);
					updated = true;
				}
			}
		}
		return updated;
	}
	
	/**
	 * Push the Incident Group Checkout selected values onto the corresponding Incident values
	 * 
	 * @param incGroup
	 * @param incident
	 * @param updated
	 * @return
	 */
	private Boolean pushCheckoutSelectedValues(IncidentGroup incGroup, Incident incident, Boolean updated){
	
		for(IncidentPrefs pref : incident.getIncidentPrefs()) {
			if(pref.getSectionName().equals(IncidentPrefsSectionNameEnum.FINANCE)) {
				for(IncidentGroupPrefsVo prefVo : newVo.getCheckOutFinanceVos()) {
					if(pref.getFieldLabel().equalsIgnoreCase(prefVo.getFieldLabel())) {
						pref.setSelected(prefVo.getSelected());
						updated = true;
						break;
					}
				}
			} else if(pref.getSectionName().equals(IncidentPrefsSectionNameEnum.LOGISTICS)) {
				for(IncidentGroupPrefsVo prefVo : newVo.getCheckOutLogisticsVos()) {
					if(pref.getFieldLabel().equalsIgnoreCase(prefVo.getFieldLabel())) {
						pref.setSelected(prefVo.getSelected());
						updated = true;
						break;
					}
				}
			} else if(pref.getSectionName().equals(IncidentPrefsSectionNameEnum.PLANNING)) {
				for(IncidentGroupPrefsVo prefVo : newVo.getCheckOutPlanningVos()) {
					if(pref.getFieldLabel().equalsIgnoreCase(prefVo.getFieldLabel())) {
						pref.setSelected(prefVo.getSelected());
						updated = true;
						break;
					}
				}
			} else if(pref.getSectionName().equals(IncidentPrefsSectionNameEnum.OTHER_LABEL)) {
				for(IncidentGroupPrefsVo prefVo : newVo.getCheckOutOtherVos()) {
					if(pref.getFieldLabel() != null && pref.getFieldLabel().equalsIgnoreCase(prefVo.getFieldLabel())) {
						pref.setSelected(prefVo.getSelected());
						updated = true;
						break;
					}
				}
			} 
		}
		
		return updated;
	}
	
	private class Positions {
		Integer otherPosition = 0;
		Integer airPosition = 0;
		Integer checkPosition = 0;
		public Integer getOtherPosition() {
			return otherPosition;
		}
		public Integer getOtherPositionPlus() {
			return ++otherPosition;
		}
		public void setOtherPosition(Integer otherPosition) {
			this.otherPosition = otherPosition;
		}
		public Integer getAirPosition() {
			return airPosition;
		}
		public Integer getAirPositionPlus() {
			return ++airPosition;
		}
		public void setAirPosition(Integer airPosition) {
			this.airPosition = airPosition;
		}
		public Integer getCheckPosition() {
			return checkPosition;
		}
		public Integer getCheckPositionPlus() {
			return ++checkPosition;
		}
		public void setCheckPosition(Integer checkPosition) {
			this.checkPosition = checkPosition;
		}
	}
}
	
