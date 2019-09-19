package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SavePrimaryDailyPostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SavePrimaryEachPostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SavePrimaryHourlyPostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SavePrimaryMileagePostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SaveSpecialDailyPostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SaveSpecialEachPostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SaveSpecialHourlyPostings;
import gov.nwcg.isuite.core.rules.timepost.contractor.finalactions.SaveSpecialMileagePostings;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class ExecuteFinalActionRules extends AbstractContractorRule implements IRule {
	public static final String _RULE_NAME="EXECUTE_FINAL_ACTION_RULES";
	private String contractorPostType="";
	
	
	public ExecuteFinalActionRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{

			if(null == dialogueVo.getResultObjectAlternate3())
				dialogueVo.setResultObjectAlternate3(new ArrayList<AssignmentTimePostVo>()) ;
			
			if(null == dialogueVo.getResultObjectAlternate2())
				dialogueVo.setResultObjectAlternate2(new ArrayList<AssignmentTimePostVo>()) ;

			contractorPostType = super.postType; //vo.getContractorPostType();
			
			/*
			 * Check for 
			 * 		primary time posting
			 * 			or
			 *      guarantee time posting
			 * NOTE: 
			 * 		treat guarantee time posting as a primary posting
			 */
			if(!postType.equals("SPECIAL")){
				saveAsPrimaryPosting(dialogueVo);
			}
			
			/*
			 * Check for exlusive special time posting
			 */
			
			if(postType.equals("SPECIAL") || postType.equals("BOTH")){
				this.saveAsSpecialPosting(dialogueVo);
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	private void saveAsPrimaryPosting(DialogueVo dialogueVo) throws Exception {
		String uom = super.getPrimaryUnitOfMeasure();

		super.tpDao.flushAndEvict(assignmentTimeEntity);
		super.tpDao.flushAndEvict(incidentResourceEntity);
		
		if(isExclusiveGuaranteeContractorPost())
			vo.setPrimaryPosting(true);

		vo.setAssignmentTimeId(assignmentTimeEntity.getId());
		
		if(this.contractorPostType.equals("BOTH")){
			vo.setContractorPostType("PRIMARY");
		}
		
		if(uom.equals("DAILY"))
		{
			SavePrimaryDailyPostings finalAction = 
				new SavePrimaryDailyPostings(tpDao,vo,incidentResourceEntity,assignmentTimeEntity);
			finalAction.save(null,dialogueVo);
		}
				
		if(uom.equals("HOURLY"))
		{
			SavePrimaryHourlyPostings finalAction = new SavePrimaryHourlyPostings(tpDao,vo,incidentResourceEntity);
			finalAction.save(null,dialogueVo);
		}

		if(uom.equals("MILEAGE"))
		{
			SavePrimaryMileagePostings finalAction = new SavePrimaryMileagePostings(tpDao,vo,incidentResourceEntity);
			finalAction.save(null,dialogueVo);
		}
				
		if(uom.equals("EACH"))
		{
			SavePrimaryEachPostings finalAction = new SavePrimaryEachPostings(tpDao,vo,incidentResourceEntity);
			finalAction.save(null,dialogueVo);
		}
		
	}
	
	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	private void saveAsSpecialPosting(DialogueVo dialogueVo) throws Exception {
		String uom = super.getSpecialUnitOfMeasure();

		AssignmentTimePost entity = null;
		
		specialVo.setAssignmentTimeId(assignmentTimeEntity.getId());
		specialVo.setContractorPostType("SPECIAL");
		
		if(uom.equals("DAILY"))
		{
			SaveSpecialDailyPostings finalAction = 
				new SaveSpecialDailyPostings(tpDao,specialVo,incidentResourceEntity);
		
			finalAction.save(entity, dialogueVo);
		}
				
		if(uom.equals("HOURLY"))
		{
			if(postType.equals("BOTH")){
				if(StringUtility.hasValue(vo.getPostStartDateString()) && StringUtility.hasValue(vo.getPostStopDateString())){
					Date dt1=DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
					Date dt2=DateUtil.toDate(vo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
					dt1=DateUtil.addMilitaryTimeToDate(dt1, "1200");
					dt2=DateUtil.addMilitaryTimeToDate(dt2, "1200");
					int diffDays = (int)DateUtil.diffDays(dt1, dt2);
					//int diffDays = (int)DateUtil.diffDays(vo.getPostStartDate(), vo.getPostStopDate());
					for(int x=0;x<(diffDays+1);x++){
						if(x>0){
							specialVo.setPostStartDate(DateUtil.addDays(dt1, x));
							specialVo.setPostStopDate(DateUtil.addDays(dt1, x));
						}else{
							specialVo.setPostStopDate(vo.getPostStartDate());
						}
						
						SaveSpecialHourlyPostings finalAction = 
							new SaveSpecialHourlyPostings(tpDao,specialVo,incidentResourceEntity);
					
						finalAction.save(entity,dialogueVo);
					}
					
				}else if(DateUtil.hasValue(vo.getPostStartDate())){
					SaveSpecialHourlyPostings finalAction = 
						new SaveSpecialHourlyPostings(tpDao,specialVo,incidentResourceEntity);
				
					finalAction.save(entity,dialogueVo);
				}
			}else{
				SaveSpecialHourlyPostings finalAction = 
					new SaveSpecialHourlyPostings(tpDao,specialVo,incidentResourceEntity);
			
				finalAction.save(entity,dialogueVo);
			}
		}

		if(uom.equals("MILEAGE"))
		{
			if(postType.equals("BOTH")){
				if(StringUtility.hasValue(vo.getPostStartDateString()) && StringUtility.hasValue(vo.getPostStopDateString())){
					Date dt1=DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
					Date dt2=DateUtil.toDate(vo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
					dt1=DateUtil.addMilitaryTimeToDate(dt1, "1200");
					dt2=DateUtil.addMilitaryTimeToDate(dt2, "1200");
					int diffDays = (int)DateUtil.diffDays(dt1, dt2);
					//int diffDays = (int)DateUtil.diffDays(vo.getPostStartDate(), vo.getPostStopDate());
					for(int x=0;x<(diffDays+1);x++){
						if(x>0){
							specialVo.setPostStartDate(DateUtil.addDays(dt1, x));
							specialVo.setPostStopDate(DateUtil.addDays(dt1, x));
						}else{
							specialVo.setPostStopDate(vo.getPostStartDate());
						}
						
						SaveSpecialMileagePostings finalAction = 
							new SaveSpecialMileagePostings(tpDao,specialVo,incidentResourceEntity);
					
						finalAction.save(entity,dialogueVo);
					}
					
				}else if(DateUtil.hasValue(vo.getPostStartDate())){
					specialVo.setPostStopDate(vo.getPostStartDate());
					SaveSpecialMileagePostings finalAction = 
						new SaveSpecialMileagePostings(tpDao,specialVo,incidentResourceEntity);
				
					finalAction.save(entity,dialogueVo);
				}
			}else{
				SaveSpecialMileagePostings finalAction = 
					new SaveSpecialMileagePostings(tpDao,specialVo,incidentResourceEntity);
			
				finalAction.save(entity,dialogueVo);
			}
		}
				
		if(uom.equals("EACH"))
		{
			
			if(postType.equals("BOTH")){
				//if(DateUtil.hasValue(vo.getPostStartDate()) && DateUtil.hasValue(vo.getPostStopDate())){
				if(StringUtility.hasValue(vo.getPostStartDateString()) && StringUtility.hasValue(vo.getPostStopDateString())){
					Date dt1=DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
					Date dt2=DateUtil.toDate(vo.getPostStopDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
					dt1=DateUtil.addMilitaryTimeToDate(dt1, "1200");
					dt2=DateUtil.addMilitaryTimeToDate(dt2, "1200");
					int diffDays = (int)DateUtil.diffDays(dt1, dt2);
					//int diffDays = (int)DateUtil.diffDays(vo.getPostStartDate(), vo.getPostStopDate());
					for(int x=0;x<(diffDays+1);x++){
						if(x>0){
							specialVo.setPostStartDate(DateUtil.addDays(dt1, x));
							specialVo.setPostStopDate(DateUtil.addDays(dt1, x));
						}else{
							specialVo.setPostStopDate(vo.getPostStartDate());
						}
						
						SaveSpecialEachPostings finalAction = 
							new SaveSpecialEachPostings(tpDao,specialVo,incidentResourceEntity);
					
						finalAction.save(entity,dialogueVo);
					}
				}else if(DateUtil.hasValue(vo.getPostStartDate())){
					specialVo.setPostStopDate(vo.getPostStartDate());
					SaveSpecialEachPostings finalAction = 
						new SaveSpecialEachPostings(tpDao,specialVo,incidentResourceEntity);
				
					finalAction.save(entity,dialogueVo);
				}
			}else{
				SaveSpecialEachPostings finalAction = 
					new SaveSpecialEachPostings(tpDao,specialVo,incidentResourceEntity);
			
				finalAction.save(entity,dialogueVo);
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}


	
	
}
