package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.OF288ConflictVo;
import gov.nwcg.isuite.core.vo.WorkPeriodVo;
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
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class OF288EmpTypeChange1Rules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="OF288_EMP_TYPE_CHANGE1";

	private String originalEmploymentType="";
	private String newEmploymentType="";
	
	public OF288EmpTypeChange1Rules(ApplicationContext ctx){
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
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;

				
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Defect 5158 – Add Prompt When User Changes Employment Type for Resource

		1.	When a user changes the Employment Type for an OF-288 Resource that has time 
			or adjustment postings and saves that change, a prompt displays reminding the 
			user to review all Time and Adjustment postings. 
			
			NOTE: This prompt will only display if there is an existing time or adjustment posting for the selected resource.
		 *           
		 */
		
		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){
			
			if(isOF288EmpTypeChanged()){
				TimePostDao tpDao = (TimePostDao)super.context.getBean("timePostDao");
				TimeAssignAdjustDao taaDao = (TimeAssignAdjustDao)super.context.getBean("timeAssignAdjustDao");
				
				Collection<AssignmentTimePost> timePosts = tpDao.getResourceTimePosts(vo.getId(),null);
				Collection<IncidentResourceTimeAdustDataVo> adjustVos=taaDao.getTimeAdjustData2(vo.getId(), null,false);

				Collection<OF288ConflictVo> conflicts = getInvalidData(timePosts,adjustVos);
				
				if(CollectionUtility.hasValue(conflicts)){
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
					coaVo.setCustomPromptVo(new CustomPromptVo(_RULE_NAME,"text.incidentResources" ,"action.9920",conflicts));
					coaVo.setStoredObject(conflicts);
					dialogueVo.setCourseOfActionVo(coaVo);
					
					//CourseOfActionVo coaVo = new CourseOfActionVo();
					//coaVo.setCoaName(_RULE_NAME);
					//coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					//coaVo.setPromptVo(new PromptVo("text.incidentResources","action.9920",null,PromptVo._OK | PromptVo._CANCEL));
					//dialogueVo.setCourseOfActionVo(coaVo);
					//dialogueVo.setResultObjectAlternate4(conflicts);
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}

	private Collection<OF288ConflictVo> getInvalidData(Collection<AssignmentTimePost> timePosts,Collection<IncidentResourceTimeAdustDataVo> adjustVos){
		Collection<OF288ConflictVo> conflicts = new ArrayList<OF288ConflictVo>();
		
		if(CollectionUtility.hasValue(timePosts) && StringUtility.hasValue(this.newEmploymentType)){
			for(AssignmentTimePost atp : timePosts){
				if(!atp.getEmploymentType().name().equalsIgnoreCase(this.newEmploymentType)){
					// conflict record
					String c=DateUtil.toDateString(atp.getPostStartDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + 
						" - Time Posting is based on " + atp.getEmploymentType().name() + " Rate";
					if(null != atp.getSpecialPay()){
						if(newEmploymentType.equalsIgnoreCase("FED")){
							if(!atp.getSpecialPay().getAvailableToFed()){
								c=c+" and has invalid FED Special Pay selection ("+atp.getSpecialPay().getCode()+" - "+atp.getSpecialPay().getDescription()+")";
							}
						}
						if(newEmploymentType.equalsIgnoreCase("AD")){
								if(!atp.getSpecialPay().getAvailableToAd()){
									c=c+" and has invalid AD Special Pay selection ("+atp.getSpecialPay().getCode()+" - "+atp.getSpecialPay().getDescription()+")";
								}
						}
						if(newEmploymentType.equalsIgnoreCase("OTHER")){
								if(!atp.getSpecialPay().getAvailableToOther()){
									c=c+" and has invalid OTHER Special Pay selection ("+atp.getSpecialPay().getCode()+" - "+atp.getSpecialPay().getDescription()+")";
								}
						}
					}
					c=c+".";
					OF288ConflictVo v = new OF288ConflictVo();
					v.setConflict(c);
					conflicts.add(v);
				}
			}
		}

		if(CollectionUtility.hasValue(adjustVos) && StringUtility.hasValue(this.newEmploymentType)){
			for(IncidentResourceTimeAdustDataVo v : adjustVos){
				if(StringUtility.hasValue(v.getAdjustmentCategoryDesc())
						&& !this.newEmploymentType.equalsIgnoreCase("AD")){
					String c=DateUtil.toDateString(v.getActivityDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + 
					" - Adjustment Posting has AD specific Category selection ("+v.getAdjustmentCategoryDesc()+").";

					OF288ConflictVo v2 = new OF288ConflictVo();
					v2.setConflict(c);
					conflicts.add(v2);
				}
				if(!StringUtility.hasValue(v.getAdjustmentCategoryDesc())
						&& this.newEmploymentType.equalsIgnoreCase("AD")){
					String c=DateUtil.toDateString(v.getActivityDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY) + 
					" - Adjustment Posting has missing Category selection. Required field for "+this.newEmploymentType + " Employment Types.";

					OF288ConflictVo v2 = new OF288ConflictVo();
					v2.setConflict(c);
					conflicts.add(v2);
				}
			}
		}
		
		return conflicts;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
		
		if(dialogueVo.getCourseOfActionVo().getStoredObject1() != null){
			String s=(String)dialogueVo.getCourseOfActionVo().getStoredObject1();
			if(s.equalsIgnoreCase("OK")){
				dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
				return _OK;
			}else{
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incidentResources", "text.abortProcess" , new String[]{"save"}, MessageTypeEnum.INFO));

				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_MSG_FINISHED);
		coaVo.setIsDialogueEnding(Boolean.TRUE);
		coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coaVo.setMessageVo(new MessageVo("text.incidentResources", "text.abortProcess" , new String[]{"save"}, MessageTypeEnum.INFO));

		dialogueVo.setCourseOfActionVo(coaVo);
		
		return _FAIL;
	}
	
	/**
	 * Returns whether or not the resource's emp type is getting changed to or from OF288 Type
	 * @return
	 */
	private Boolean isOF288EmpTypeChanged(){
		WorkPeriod wpEntity = irEntity.getWorkPeriod();
		WorkPeriodVo wpVo = vo.getWorkPeriodVo();
		
		if(null != wpEntity && null != wpVo){
			Assignment assignEntity = null;
			AssignmentVo assignVo = null;
			
			// get current assignments
			for(Assignment a : wpEntity.getAssignments()){
				if(null==a.getEndDate()){
					assignEntity=a;
					break;
				}
			}
			
			for(AssignmentVo avo : wpVo.getAssignmentVos()){
				if(null==avo.getEndDate()){
					assignVo=avo;
					break;
				}
			}
			
			if(null != assignEntity && null != assignVo){
				AssignmentTime atEntity = assignEntity.getAssignmentTime();
				AssignmentTimeVo atVo = assignVo.getAssignmentTimeVo();
				
				if(null != atEntity && null != atVo){
					/*
					 * Are they different?
					 */
					if(atEntity.getEmploymentType() != atVo.getEmploymentType()){
						boolean rtn=true;
						if(atEntity.getEmploymentType() != null)
							this.originalEmploymentType=atEntity.getEmploymentType().name();
						if(atVo.getEmploymentType() != null)
							this.newEmploymentType=atVo.getEmploymentType().name();
						
						return rtn;
					}
					
				}
			}			
		}
	
		return false;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
