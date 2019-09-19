package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class SubordinateAssignDateRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="DISABLED"; //IncidentResourceSaveRuleFactory.RuleEnum.SUBORDINATE_ASSIGN_DATE.name();

	public SubordinateAssignDateRules(ApplicationContext ctx)
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
		
		/*
				If a Subordinate Resource has an activity date 
				(e.g., Check-In Date, Hired Date, Time Posting Date) that is prior to an activity date 
				for the Primary Resource, then the system must set the Primary Resource's Assign Date 
				to the date of activity for the Subordinate Resource.
		 */
		
		/*
		 * 1) determine if incident resource is subordinate 
		 * 2) recursively check each parent's assign date in the roster 
		 */
		Date assignDate2=null;
		if(null != vo.getCostDataVo() && DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
			assignDate2=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
		}
		
		if( (null != vo.getCostDataVo() && DateUtil.hasValue(assignDate2)) 
				|| DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
			if(LongUtility.hasValue(vo.getResourceVo().getParentResourceId())){
					Collection<Object> parentResIdsToUpdate = new ArrayList<Object>();
					
					Long currentParentResourceId=vo.getResourceVo().getParentResourceId();
					Boolean bContinue=true;

					Date checkinDate=null;
					Date assignDate=null;
					
					if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
						checkinDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());
						checkinDate=DateUtil.addMilitaryTimeToDate(checkinDate, "2359");
						assignDate=checkinDate; //DateUtil.addMilitaryTimeToDate(vo.getWorkPeriodVo().getCiCheckInDate(), "2359");
					}
					
					if(null != vo.getCostDataVo() && DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
						Date dt=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
						dt=DateUtil.addMilitaryTimeToDate(dt, "2359");
						
						if(dt.before(checkinDate)){
							assignDate=dt;
						}
					}
					
					if(null != assignDate){
						ResourceDao resDao = (ResourceDao)context.getBean("resourceDao");
						
						while(bContinue==true){
							// check this parent assign date
							Date parentAssignDate=irDao.getParentAssignDate(currentParentResourceId,vo.getIncidentVo().getId());
							if(null != parentAssignDate){
								Date dt1=DateUtil.addMilitaryTimeToDate(parentAssignDate, "2359");
								if(assignDate.before(dt1)){
									// update it
									parentResIdsToUpdate.add((Object)currentParentResourceId);
								}
							}else{
								// update it
								parentResIdsToUpdate.add((Object)currentParentResourceId);
							}
							
							// does currentParentResourceId also have a parent?
							Resource r = resDao.getById(currentParentResourceId, ResourceImpl.class);
							Boolean hasParent=false;
							if(LongUtility.hasValue(r.getParentResourceId()))
								hasParent=true;
							
							if(hasParent==true){
								currentParentResourceId=r.getParentResourceId();
								resDao.flushAndEvict(r);
							}else{
								resDao.flushAndEvict(r);
								bContinue=false;
							}
							
						}
						
						if(CollectionUtility.hasValue(parentResIdsToUpdate)){
							CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
							courseOfActionVo.setCoaName(_RULE_NAME);
							courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
							courseOfActionVo.setIsComplete(true);
							courseOfActionVo.setStoredObject(parentResIdsToUpdate);
							courseOfActionVo.setStoredObject1((Object)assignDate);
							dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
						}
						
					}
			}
			
		}
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			Date assignDate = null;
			if(null != coaVo.getStoredObject1()){
				assignDate=(Date)coaVo.getStoredObject1();
			}
			if(null != coaVo.getStoredObject()){
				Collection<Object> coaIds=(Collection<Object>)coaVo.getStoredObject();
				Collection<Long> parentResIds=LongUtility.convertToLongs(coaIds);

				// update parent with earlier assign date
				for(Long id : parentResIds){
					super.irDao.updateCostAssignDate(id,vo.getIncidentVo().getId(),assignDate);
				}
			}

			
		}

	}
}
