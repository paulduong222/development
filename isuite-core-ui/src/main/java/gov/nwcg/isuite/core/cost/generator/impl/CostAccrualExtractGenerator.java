package gov.nwcg.isuite.core.cost.generator.impl;


import gov.nwcg.isuite.core.domain.impl.CostAccrualExtractImpl;
import gov.nwcg.isuite.core.domain.impl.CostAccrualExtractRscImpl;
import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class CostAccrualExtractGenerator extends BaseService {
	private CostAccrualExtractDao costAccrualExtractDao;
	
	public CostAccrualExtractGenerator(CostAccrualExtractDao costAccrualExtractDao){
		this.costAccrualExtractDao = costAccrualExtractDao;
	}
	
	/*
	public DialogueVo runExtract(Date extractDate, Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException,PersistenceException{
		if(null==dialogueVo)dialogueVo=new DialogueVo();
		
		try {
			
			// 1. check for the current day do we already have an finalized extract for this the 
			List<CostAccrualExtractImpl> curentDayExtracts = (List<CostAccrualExtractImpl>) costAccrualExtractDao.getExtractByDate(incidentId, incidentGroupId, extractDate);
			
			// 2. check finalize			
			if(isFinalized(curentDayExtracts)) {
				return getMessageDialogVo( "error.extract",
										   new String[] {"The cost accrual is finalized."}, 
										   "text.costAccrualFinalized",
										   dialogueVo);
			}
			
			// 3. run extract
			// check the business rules
			// ruleHandle
			List<CostAccrualExtractRscImpl> newExtractRscs = (List<CostAccrualExtractRscImpl>) costAccrualExtractDao.runExtract(incidentId, incidentGroupId, extractDate);
			
			List<CostAccrualExtractImpl> preFinalizedExtracts = (List<CostAccrualExtractImpl>)costAccrualExtractDao.getLastFinalizedExtract(incidentId, incidentGroupId);
			
			// 4. prepare extract for save
			CostAccrualExtractImpl extract;
			
			// no extract for the current day
			if(curentDayExtracts == null || curentDayExtracts.isEmpty())
				extract = prepareNewExtract(newExtractRscs,
											preFinalizedExtracts.get(0),
						                    extractDate,
						                    incidentId,
						                    incidentGroupId);
			else // has an extract but not finalized
				extract = prepareExtract(newExtractRscs,
										 preFinalizedExtracts.get(0),
										 curentDayExtracts.get(0),
						                 extractDate,
						                 incidentId,
						                 incidentGroupId);
			
			// 5. save the new extract
			costAccrualExtractDao.save(extract);
			
			CostAccrualExtractVo extractVo = new CostAccrualExtractVo();
			extractVo.getInstance(extract, true);
							
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RUN_EXTRACT");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.extract","info.0030.01", new String[]{"Cost Accrual Extract " + incidentId} , MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
					
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(extractVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
			
		return dialogueVo;
	}

	private boolean isFinalized(List<CostAccrualExtractImpl> extracts){
		if(extracts != null && !extracts.isEmpty() && extracts.get(0).isFinalized().equals(StringBooleanEnum.Y)) 
			return true;
		else
			return false;
	}
	
	private CostAccrualExtractImpl prepareNewExtract(List<CostAccrualExtractRscImpl> newExtractRscs,
													 CostAccrualExtractImpl preFinalizedExtracts,
												  	 Date extractDate,
												  	 Long incidentId,
												  	 Long incidentGroupId) {
		
		CostAccrualExtractImpl extract = new CostAccrualExtractImpl();
		setChangeAmount(newExtractRscs,preFinalizedExtracts);
		
		extract.setExtractDate(extractDate);
		extract.setIncidentId(incidentId);
		extract.setIncidentGroupId(incidentGroupId);
		extract.setFinalized(StringBooleanEnum.N);	
		//extract.setCostAccrualExtractRscs(newExtractRscs);
		
		return extract; 
		
	}
	
	private CostAccrualExtractImpl prepareExtract(List<CostAccrualExtractRscImpl> newExtractRscs,
												  CostAccrualExtractImpl preFinalizedExtract,
												  CostAccrualExtractImpl preExtract,
												  Date extractDate,
												  Long incidentId,
												  Long incidentGroupId) {
			
		setChangeAmount(newExtractRscs,preFinalizedExtract);
        //setExtractRscId(newExtractRscs,(List<CostAccrualExtractRsc>)preExtract.getCostAccrualExtractRscs());
        preExtract.setExtractDate(extractDate);
        preExtract.getCostAccrualExtractRscs().clear();
        preExtract.getCostAccrualExtractRscs().addAll(newExtractRscs);
		return preExtract;
	}
	
	private void setChangeAmount(List<CostAccrualExtractRscImpl> newExtractRscs,CostAccrualExtractImpl preFinalizedExtract){
		for(CostAccrualExtractRscImpl newExtractRsc :newExtractRscs) {
			final Long incidentResourceId = newExtractRsc.getIncidentResourceId();
			
			// get matching preOne from extractRscPre list 
			CostAccrualExtractRscImpl preFinalizedOne = (CostAccrualExtractRscImpl)CollectionUtils.select(preFinalizedExtract.getCostAccrualExtractRscs(), new Predicate() {
					@Override
					public boolean evaluate(Object object) {
						CostAccrualExtractRscImpl data = (CostAccrualExtractRscImpl) object;
						return data.getIncidentResourceId().equals(incidentResourceId);
					}
			});
				
			// for a new resource extract  	
			if(preFinalizedOne == null)
				newExtractRsc.setChangeAmount(BigDecimal.ZERO);
			else 
				newExtractRsc.setChangeAmount(newExtractRsc.getTotalAmount().subtract(preFinalizedOne.getTotalAmount()));
		}
	}
	
	private void setExtractRscId(List<CostAccrualExtractRscImpl> newExtractRscs,
			 					 List<CostAccrualExtractRscImpl> preExtractRscs){

		for(CostAccrualExtractRscImpl newExtractRsc :newExtractRscs) {

			final Long incidentResourceId = newExtractRsc.getIncidentResourceId(); 	

			// get matching preOne from extractRscPre list 
			CostAccrualExtractRscImpl preOne = (CostAccrualExtractRscImpl)CollectionUtils.select(preExtractRscs, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					CostAccrualExtractRscImpl data = (CostAccrualExtractRscImpl) object;
					return data.getIncidentResourceId().equals(incidentResourceId);
				}	
			});

			// for a new resource extract  	
			if(preOne != null)
				newExtractRsc.setId(preOne.getId());
		}
	}
	
	private List<CostAccrualExtractImpl> getIncidentExtract() {
		return new ArrayList<CostAccrualExtractImpl>();
	}
	
	public DialogueVo runFinalize(CostAccrualExtractVo extractVo, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		// 1. update extract
		try {
			if(null==dialogueVo)dialogueVo=new DialogueVo();
			
			// 1. check for the extractDate we already have an finalized extract for this the 
			//    incidentId/IncidentGroupId
			//costAccrualExtractDao = (CostAccrualExtractDao)super.context.getBean("costAccrualExtractDao");
			CostAccrualExtractImpl extract = (CostAccrualExtractImpl)costAccrualExtractDao.getExtractByDate(extractVo.getIncidentId(), 
					                                                                                        extractVo.getIncidentGroupId(), 
					                                                                                        extractVo.getExtractDate());
			
			if(extract != null && extract.getFinalizedDate() != null)
				return getMessageDialogVo( "error.extract",
										   new String[] {"The cost accrual is finalized."}, 
										   "text.costAccrualFinalized",
										   dialogueVo);
					
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RUN_EXTRACT");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.extract","info.0030.01", new String[]{"Cost Accrual Extract " + extractVo.getIncidentId()} , MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
					
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(extractVo);

		}catch(Exception e){
				super.dialogueException(dialogueVo, e);
		}
			
		return dialogueVo;
	}
	
	public DialogueVo editAccrualAccoutingCode(CostAccrualExtractVo extract, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		// 1. update extract to final
		
		return new DialogueVo();
	}
	
	public DialogueVo generateCostAccrualSummaryReport(Object filter, DialogueVo dialogueVo) throws ServiceException,PersistenceException {
	
		return new DialogueVo();
	}
	
	public DialogueVo generateCostAccrualDetailReport(Object filter, DialogueVo dialogueVo) throws ServiceException,PersistenceException {
		
		return new DialogueVo();
	}
	
	public DialogueVo generateCostAccrualDetailAllReport(Object filter, DialogueVo dialogueVo) throws ServiceException,PersistenceException {
		
		return new DialogueVo();
	}
	
	private DialogueVo getMessageDialogVo(String property, String[] parms, String titleProperty, DialogueVo dialogueVo) {
		MessageVo messageVo = new MessageVo();
		messageVo.setMessageProperty(property);
		messageVo.setParameters(parms);
	    messageVo.setTitleProperty(titleProperty);    
		
//		MessageVo messageVo = new MessageVo();
//		messageVo.setMessageProperty("error.900010");
//		messageVo.setParameters(new String[] { parm });
//	    messageVo.setTitleProperty("text.costAccrualFinalized");
	    
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coa.setMessageVo(messageVo);
		coa.setIsDialogueEnding(true);
		    
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
    }
	
	*/
    /*
     *          a) if one exists, regenerate that record
     *                      TODO: need a query to see if we have a record
     *                              on this date for the incidentId/IncidentGroupid
     *                      TODO: if it exists, return the entity
     *          
      *                1.a.1 --> If incident Id is null and incident group id is passed in,
     *                               if one incident in the group already has a finalized extract for that date,
     *                              and other incidents do not have a finalized extract,
     *                              do we only run the extract for non-finalized extract records that the incident?
     * 
      *              1.a.2 -->  question #2:
     *                                  Incident Group Id ==> 10000
     *                                        incident id ==> 10001
     *                                      incident id ==> 10002
     *                                  Extract Date ==> 05/13/2013
     * 
      *                                        incidentId 10001 has a finalized extract for 5/13/2013
     *                                        incidentId 10002 has a finalized extract for 5/13/2013
     *                                        
      *                                        BUT
     *                                        incidentGroupId 10000 DOES NOT have a finalized extract for 5/13/2013
     * 
      *                                  How to handle this?
     * 
      *    
      *          b) otherwise, generate a new one for that date
     *                      TODO: instantiate a new entity
     *                    TODO: rule check--> make sure the entity.finalize is false
                             TODO: 
                             
      * 2- Get the sum of daily cost totals for each resource where the
     *         daily cost activity_date is <= the extract date
                 and store that information in the resource_extract table
     *          
      *          to_date(dc.activity_date,'MM/DD/YYYY') <= 
      *                to_date('extractdatepassedin','MM/DD/YYYY')

     *      2.a - If the incidentId is null and incidentGroupId is passed in,
     *                run step 2 for each resource in each incident in the incident group
     *          
      * 3- Get the sum of daily cost totals for the incident where the 
      *    daily cost activity_date is <= the extract date
     *          and store that information in the cost_accrual_extract table
     * 
      *          to_date(dc.activity_date,'MM/DD/YYYY') <= 
      *                to_date('extractdatepassedin','MM/DD/YYYY')
     * 
      *      3.a - If the incidentId is null and incidentGroupId is passed in,
     *                run step 3 for each incident in the incident group
     * 
      * 4 - Calculate the Change Amount from the previous extract
     * 
      * 5 - Save the cost_accrual_extract record
     * 
      * 6 - Save the resource detail accrual extract records
     * 
      * 7 - What are the rules for finalizing an extract for a incident group?
     *          do all incidents also get finalized?
     * 
      * 8 - 
      * 
      * 
      */
}
