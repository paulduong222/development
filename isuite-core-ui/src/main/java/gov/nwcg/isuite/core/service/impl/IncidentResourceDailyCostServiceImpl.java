package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.cost.CostFlowdown;
import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.cost.CostGeneratorOther;
import gov.nwcg.isuite.core.cost.CostGeneratorProcess;
import gov.nwcg.isuite.core.cost.CostOtherUpdateRatesGenerator;
import gov.nwcg.isuite.core.cost.CostUpdateRatesGenerator;
import gov.nwcg.isuite.core.cost.DailyCostGenerator;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.rules.DailyCostDeleteRulesHandler;
import gov.nwcg.isuite.core.rules.DailyCostResourceRunManualRulesHandler;
import gov.nwcg.isuite.core.rules.DailyCostSaveRulesHandler;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.service.IncidentResource2Service;
import gov.nwcg.isuite.core.service.IncidentResourceDailyCostService;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class IncidentResourceDailyCostServiceImpl extends BaseService implements IncidentResourceDailyCostService {
	private IncidentResourceDailyCostDao dao;
	
	public IncidentResourceDailyCostServiceImpl(){

	}

	public void initialization(){
		dao = (IncidentResourceDailyCostDao)super.context.getBean("incidentResourceDailyCostDao");
	}

	private Boolean isLocked(Long id, String type, DialogueVo dialogueVo) throws Exception {

		for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
			IRule rule = SiteManagedRuleFactory.getInstance(re, context, id, type);
			
			if(null != rule){
				if(AbstractRule._OK != rule.executeRules(dialogueVo)){
					return true;
				}
			}
		}
		
		return false;
	}

	public DialogueVo runDailyCostsNextSequence(DialogueVo dialogueVo ) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Long processId=0L;
			int sequence=0;
			if(null != dialogueVo.getResultObjectAlternate3()){
				String val=String.valueOf(dialogueVo.getResultObjectAlternate3());
				processId=Long.valueOf(val);
			}
			if(null != dialogueVo.getResultObjectAlternate4()){
				String val=String.valueOf(dialogueVo.getResultObjectAlternate4());
				sequence=Integer.parseInt(val);
			}

			if(LongUtility.hasValue(processId)){
				if(CostGeneratorProcess.hasNext(processId, sequence)){
					// get all resources for the process id
					Collection<CostResourceDataVo> costResourceDataVos =
						CostGeneratorProcess.getCompleteList(processId);
					
					// from all resources above, extract only resources with ir.id
					Collection<CostResourceDataVo> regCostResourceDataVos =
						new ArrayList<CostResourceDataVo>();
					for(CostResourceDataVo v : costResourceDataVos){
						if(LongUtility.hasValue(v.getIncidentResourceId())){
							regCostResourceDataVos.add(v);
						}
					}
					
					// get batch of vos for the sequence
					Collection<CostResourceDataVo> vosToProcess =
						CostGeneratorProcess.getSequenceGroup(processId, sequence);

					Collection<CostResourceDataVo> regVosToProcess = new ArrayList<CostResourceDataVo>();
					Collection<CostResourceDataVo> otherVosToProcess = new ArrayList<CostResourceDataVo>();

					CostGenerator costGen = new CostGenerator(this.context);
					CostGeneratorOther costGenOther = new CostGeneratorOther(this.context);
					for(CostResourceDataVo v : vosToProcess){
						
						if(LongUtility.hasValue(v.getIncidentResourceId())){
							// process only top level resources here
							if(!LongUtility.hasValue(v.getParentResourceId())){
								regVosToProcess.add(v);
							}
						}
						if(LongUtility.hasValue(v.getIncidentResourceOtherId())){
							otherVosToProcess.add(v);
						}
					}
					for(CostResourceDataVo v : regVosToProcess){
						costGen.generateCosts(v, regCostResourceDataVos,false);
					}
					for(CostResourceDataVo v : otherVosToProcess){
						costGenOther.generateCosts(v);
					}
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("RUN_COST_NEXT");
					coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setResultObjectAlternate3(processId);
					dialogueVo.setResultObjectAlternate4(sequence);
					dialogueVo.setCourseOfActionVo(coaVo);
					
				}else{
					CostGeneratorProcess.removeProcess(processId);
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("RUN_INCIDENT_RESOURCE_DAILY_COSTS");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(coaVo);
				}
			}else{
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_INCIDENT_RESOURCE_DAILY_COSTS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#runManualIncidentDailyCosts(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runManualIncidentDailyCosts(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			IncidentResourceDao incResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao incResourceOtherDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

			if(this.isLocked(incidentId, "INCIDENT", dialogueVo)==true){
				return dialogueVo;
			}

			Long processId=0L;
			int sequence=1;
			
			// update daily cost exception for all resources first
			incResourceDao.updateDailyCostExceptions(incidentId, null);

			Collection<CostResourceDataVo> costResourceDataVos = new ArrayList<CostResourceDataVo>();
			costResourceDataVos.addAll(incResourceDao.getCostResourceData(null,incidentId,null));
			costResourceDataVos.addAll(incResourceOtherDao.getCostResourceData(null, incidentId, null));
			
			if(CollectionUtility.hasValue(costResourceDataVos)){
				processId=CostGeneratorProcess.addProcess(costResourceDataVos);
				if(CostGeneratorProcess.hasNext(processId, sequence)){
					Collection<CostResourceDataVo> vosToProcess =
						CostGeneratorProcess.getSequenceGroup(processId, sequence);

					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("RUN_COST_NEXT");
					coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setResultObjectAlternate2(CostGeneratorProcess.getProcessSequenceSize(processId));
					dialogueVo.setResultObjectAlternate3(processId);
					dialogueVo.setResultObjectAlternate4(0);
					dialogueVo.setCourseOfActionVo(coaVo);
					
				}else{
					CostGeneratorProcess.removeProcess(processId);
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("RUN_INCIDENT_RESOURCE_DAILY_COSTS");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(coaVo);
				}
			}else{
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_INCIDENT_RESOURCE_DAILY_COSTS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo runManualIncidentDailyCostsOrig(Long incidentId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			IncidentResourceDao incResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao incResourceOtherDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

			if(this.isLocked(incidentId, "INCIDENT", dialogueVo)==true){
				return dialogueVo;
			}
			
			// update daily cost exception for all resources first
			incResourceDao.updateDailyCostExceptions(incidentId, null);
			
			Collection<CostResourceDataVo> costResourceDataVos = incResourceDao.getCostResourceData(null,incidentId,null);
			System.out.println(costResourceDataVos.size());
			
			CostGenerator costGen = new CostGenerator(this.context);
			for(CostResourceDataVo v : costResourceDataVos){
				// process only top level resources here
				if(!LongUtility.hasValue(v.getParentResourceId())){
					costGen.generateCosts(v, costResourceDataVos,false);
				}
			}

			CostGeneratorOther costGenOther = new CostGeneratorOther(this.context);
			Collection<CostResourceDataVo> costResourceOtherDataVos = incResourceOtherDao.getCostResourceData(null, incidentId, null);
			System.out.println(costResourceOtherDataVos.size());
			for(CostResourceDataVo v : costResourceOtherDataVos){
				costGenOther.generateCosts(v);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RUN_INCIDENT_RESOURCE_DAILY_COSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo runManualIncidentGroupDailyCosts(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			if(this.isLocked(incidentGroupId, "INCIDENTGROUP", dialogueVo)==true){
				return dialogueVo;
			}
			
			IncidentResourceDao incResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao incResourceOtherDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

			// update daily cost exception for all resources first
			incResourceDao.updateDailyCostExceptions(null, incidentGroupId);

			Long processId=0L;
			int sequence=1;
			
			Collection<CostResourceDataVo> costResourceDataVos = new ArrayList<CostResourceDataVo>();
			costResourceDataVos.addAll(incResourceDao.getCostResourceData(null,null,incidentGroupId));
			costResourceDataVos.addAll(incResourceOtherDao.getCostResourceData(null, null, incidentGroupId));
			
			if(CollectionUtility.hasValue(costResourceDataVos)){
				processId=CostGeneratorProcess.addProcess(costResourceDataVos);
				if(CostGeneratorProcess.hasNext(processId, sequence)){
					Collection<CostResourceDataVo> vosToProcess =
						CostGeneratorProcess.getSequenceGroup(processId, sequence);

					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("RUN_COST_NEXT");
					coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setResultObjectAlternate2(CostGeneratorProcess.getProcessSequenceSize(processId));
					dialogueVo.setResultObjectAlternate3(processId);
					dialogueVo.setResultObjectAlternate4(0);
					dialogueVo.setCourseOfActionVo(coaVo);
					
				}else{
					CostGeneratorProcess.removeProcess(processId);
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("RUN_INCIDENT_GROUP_RESOURCE_DAILY_COSTS");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
					coaVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(coaVo);
				}
			}else{
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_INCIDENT_GROUP_RESOURCE_DAILY_COSTS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#runManualIncidentGroupDailyCosts(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runManualIncidentGroupDailyCostsOrig(Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			if(this.isLocked(incidentGroupId, "INCIDENTGROUP", dialogueVo)==true){
				return dialogueVo;
			}
			
			IncidentResourceDao incResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao incResourceOtherDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

			// update daily cost exception for all resources first
			incResourceDao.updateDailyCostExceptions(null, incidentGroupId);
			
			Collection<CostResourceDataVo> costResourceDataVos = incResourceDao.getCostResourceData(null,null, incidentGroupId);
			Collection<CostResourceDataVo> costResourceOtherDataVos = incResourceOtherDao.getCostResourceData(null, null, incidentGroupId);
			
			CostGenerator costGen = new CostGenerator(this.context);
			for(CostResourceDataVo v : costResourceDataVos){
				// process only top level resources here
				if(!LongUtility.hasValue(v.getParentResourceId())){
					costGen.generateCosts(v, costResourceDataVos,false);
				}
			}
			
			CostGeneratorOther costGenOther = new CostGeneratorOther(this.context);
			for(CostResourceDataVo v : costResourceOtherDataVos){
				costGenOther.generateCosts(v);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RUN_INCIDENT_GROUP_RESOURCE_DAILY_COSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#runManualResourceDailyCosts(gov.nwcg.isuite.core.vo.IncidentResourceGridVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runManualResourceDailyCosts(IncidentResourceGridVo irGridVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Long incResId=0L;
			if(null != irGridVo && LongUtility.hasValue(irGridVo.getIncidentResourceId())){
				incResId=irGridVo.getIncidentResourceId();
			}
			if(this.isLocked(incResId, "INCIDENTRESOURCE", dialogueVo)==true){
				return dialogueVo;
			}

			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			CostResourceDataVo voToProcess=null;
			
			Collection<CostResourceDataVo> costResourceDataVos = irDao.getCostResourceData(irGridVo.getIncidentResourceId(),null,null);
			CostGenerator costGen = new CostGenerator(this.context);
			for(CostResourceDataVo v : costResourceDataVos){
				if(v.getIncidentResourceId().compareTo(irGridVo.getIncidentResourceId())==0){
					voToProcess=v;
					break;
				}
			}
			
			DailyCostResourceRunManualRulesHandler ruleHandler = new DailyCostResourceRunManualRulesHandler(context);
			if(ruleHandler.execute(voToProcess, null,dialogueVo)==AbstractRule._OK){
				
				// Try and run the cost process for this resource
				costGen.generateCosts(voToProcess, costResourceDataVos,false);

				/*
				 * if voToProcess has parent, processEachParent up the tree
				 */
				if(LongUtility.hasValue(voToProcess.getParentResourceId())){
					costGen.generateCostsForParent(voToProcess.getParentResourceId(), costResourceDataVos);
				}
				
				// get list of daily cost entities
				Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceId(voToProcess.getIncidentResourceId());
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities)){
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);
					Collection<IncidentResourceDailyCostVo> newvos = IncidentResourceDailyCostVo.getHierarchicalInstances(vos);
					dialogueVo.setRecordset(newvos);
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_MANUAL_RESOURCE_DAILY_COSTS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// get update incidentresourcevo
				IncidentResource2Service ir2Service = (IncidentResource2Service)context.getBean("incidentResource2Service");
				DialogueVo tmpDvo = ir2Service.getById(irGridVo.getIncidentResourceId(),new DialogueVo());
				if(null != tmpDvo && null != tmpDvo.getResultObject()){
					IncidentResourceVo irvo = (IncidentResourceVo)tmpDvo.getResultObject();
					dialogueVo.setResultObjectAlternate(irvo);
				}
				
			}else{
				if(null != dialogueVo.getCourseOfActionVo().getStoredObject3()){
					IncidentResource entity = null;
					
					if(LongUtility.hasValue(irGridVo.getIncidentResourceId()))
						entity=irDao.getById(irGridVo.getIncidentResourceId(), IncidentResourceImpl.class);
					
					entity.setDailyCostException((String)dialogueVo.getCourseOfActionVo().getStoredObject3());
					irDao.save(entity);
					irDao.flushAndEvict(entity);
				}
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo runManualResourcesDailyCosts(IncidentResourceGridVo primarySelectedGridVo,Collection<IncidentResourceGridVo> irGridVos, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Long incidentId=0L;
			if(null != primarySelectedGridVo && LongUtility.hasValue(primarySelectedGridVo.getIncidentId())){
				incidentId=primarySelectedGridVo.getIncidentId();
			}
			if(this.isLocked(incidentId, "INCIDENT", dialogueVo)==true){
				return dialogueVo;
			}

			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");

			CostResourceDataVo voToProcess=null;
			
			CostGenerator costGen = new CostGenerator(this.context);
			Collection<CostResourceDataVo> costResourceDataVos = irDao.getCostResourceData(primarySelectedGridVo.getIncidentResourceId(),null,null);


			for(CostResourceDataVo v : costResourceDataVos){
				for(IncidentResourceGridVo gvo : irGridVos){
					if(v.getIncidentResourceId().compareTo(gvo.getIncidentResourceId())==0){
						voToProcess=v;
						
						DailyCostResourceRunManualRulesHandler ruleHandler = new DailyCostResourceRunManualRulesHandler(context);
						if(ruleHandler.execute(voToProcess, null,dialogueVo)==AbstractRule._OK){
							
							// Try and run the cost process for this resource
							costGen.generateCosts(voToProcess, costResourceDataVos,false);

							/*
							 * if voToProcess has parent, processEachParent up the tree
							 */
							if(LongUtility.hasValue(voToProcess.getParentResourceId())){
								costGen.generateCostsForParent(voToProcess.getParentResourceId(), costResourceDataVos);
							}
						}
					}
				}
			}
			
			// get list of daily cost entities
			Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceId(primarySelectedGridVo.getIncidentResourceId());
			Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
			if(CollectionUtility.hasValue(entities)){
				vos=IncidentResourceDailyCostVo.getInstances(entities, true);
				Collection<IncidentResourceDailyCostVo> newvos = IncidentResourceDailyCostVo.getHierarchicalInstances(vos);
				dialogueVo.setRecordset(newvos);
			}

			// get update incidentresourcevo
			IncidentResource2Service ir2Service = (IncidentResource2Service)context.getBean("incidentResource2Service");
			DialogueVo tmpDvo = ir2Service.getById(primarySelectedGridVo.getIncidentResourceId(),new DialogueVo());
			if(null != tmpDvo && null != tmpDvo.getResultObject()){
				IncidentResourceVo irvo = (IncidentResourceVo)tmpDvo.getResultObject();
				dialogueVo.setResultObjectAlternate(irvo);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RUN_MANUAL_RESOURCES_DAILY_COSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#runManualResourceOtherDailyCosts(gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runManualResourceOtherDailyCosts(IncidentResourceOtherGridVo iroGridVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Long resOtherId=0L;
			if(null != iroGridVo && LongUtility.hasValue(iroGridVo.getId())){
				resOtherId=iroGridVo.getId();
			}
			if(this.isLocked(resOtherId, "RESOURCEOTHER", dialogueVo)==true){
				return dialogueVo;
			}

			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			
			IncidentResourceOther entity = null;
			
			if(LongUtility.hasValue(iroGridVo.getId()))
				entity=iroDao.getById(iroGridVo.getId(), IncidentResourceOtherImpl.class);
			
			DailyCostResourceRunManualRulesHandler ruleHandler = new DailyCostResourceRunManualRulesHandler(context);
			if(ruleHandler.execute(null,entity, dialogueVo)==AbstractRule._OK){
				
				// Try and run the cost process for this resource
				CostGeneratorOther costGenOther = new CostGeneratorOther(this.context);
				Collection<CostResourceDataVo> costResourceOtherDataVos = iroDao.getCostResourceData(iroGridVo.getId(), null, null);
				for(CostResourceDataVo v : costResourceOtherDataVos){
					if(v.getIncidentResourceOtherId().compareTo(iroGridVo.getId())==0)
						costGenOther.generateCosts(v);
				}
				
				// get list of daily cost entities
				Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceOtherId(entity.getId());
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities))
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_MANUAL_RESOURCEOTHER_DAILY_COSTS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo runManualResourcesOtherDailyCosts(IncidentResourceOtherGridVo primVo, Collection<IncidentResourceOtherGridVo> gridVos, Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Long resOtherId=0L;
			if(null != primVo && LongUtility.hasValue(primVo.getId())){
				resOtherId=primVo.getId();
			}
			if(this.isLocked(resOtherId, "RESOURCEOTHER", dialogueVo)==true){
				return dialogueVo;
			}

			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

			
			if(CollectionUtility.hasValue(gridVos) && gridVos.size()==1){
				// single other resource
				
				IncidentResourceOtherGridVo iroGridVo=gridVos.iterator().next();
				
				IncidentResourceOther entity = null;
				
				if(LongUtility.hasValue(iroGridVo.getId()))
					entity=iroDao.getById(iroGridVo.getId(), IncidentResourceOtherImpl.class);
				
				DailyCostResourceRunManualRulesHandler ruleHandler = new DailyCostResourceRunManualRulesHandler(context);
				if(ruleHandler.execute(null,entity, dialogueVo)==AbstractRule._OK){
					
					// Try and run the cost process for this resource
					CostGeneratorOther costGenOther = new CostGeneratorOther(this.context);
					Collection<CostResourceDataVo> costResourceOtherDataVos = iroDao.getCostResourceData(iroGridVo.getId(), null, null);
					for(CostResourceDataVo v : costResourceOtherDataVos){
						if(v.getIncidentResourceOtherId().compareTo(iroGridVo.getId())==0)
							costGenOther.generateCosts(v);
					}
					
					// get list of daily cost entities
					Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceOtherId(entity.getId());
					Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
					if(CollectionUtility.hasValue(entities)){
						vos=IncidentResourceDailyCostVo.getInstances(entities, true);
						dialogueVo.setRecordset(vos);
					}
					
				}
			}else{
				// multiple resources
				CostGeneratorOther costGenOther = new CostGeneratorOther(this.context);
				
				Collection<CostResourceDataVo> costResourceOtherDataVos = null;
				
				if(LongUtility.hasValue(incidentId)){
					costResourceOtherDataVos = iroDao.getCostResourceData(null, incidentId, null);
				}else if(LongUtility.hasValue(incidentGroupId)){
					costResourceOtherDataVos = iroDao.getCostResourceData(null, null, incidentGroupId);
				}
				
				for(IncidentResourceOtherGridVo iroGridVo : gridVos){
					for(CostResourceDataVo v : costResourceOtherDataVos){
						if(v.getIncidentResourceOtherId().compareTo(iroGridVo.getId())==0){
							costGenOther.generateCosts(v);
							
							if(v.getIncidentResourceOtherId().compareTo(primVo.getId())==0){
								Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceOtherId(primVo.getId());
								Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
								if(CollectionUtility.hasValue(entities)){
									vos=IncidentResourceDailyCostVo.getInstances(entities, true);
									dialogueVo.setRecordset(vos);
								}
							}
							
						}
					}
					
				}
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("RUN_MANUAL_RESOURCEOTHER_DAILY_COSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"The Daily Cost process is complete."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#getResourceDailyCosts(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getResourceDailyCosts(Long incidentResourceId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
		
			Collection<IncidentResourceDailyCostVo> vos = dao.getResourceCosts(incidentResourceId);
			Collection<IncidentResourceDailyCostVo> newvos = IncidentResourceDailyCostVo.getHierarchicalInstances(vos);
			
			BigDecimal pTotal=new BigDecimal(0);
			BigDecimal sTotal=new BigDecimal(0);
			BigDecimal gTotal=new BigDecimal(0);
			
			for(IncidentResourceDailyCostVo v : newvos){
				pTotal=pTotal.add(v.getPrimaryTotalAmount());
				sTotal=sTotal.add(v.getSubordinateTotalAmount());
			}
			gTotal=gTotal.add(pTotal).add(sTotal);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_RESOURCE_DAILY_COSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setRecordset(newvos);
			dialogueVo.setResultObjectAlternate2(pTotal);
			dialogueVo.setResultObjectAlternate3(sTotal);
			dialogueVo.setResultObjectAlternate4(gTotal);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getResourceOtherDailyCosts(Long incidentResourceOtherId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
		
			Collection<IncidentResourceDailyCostVo> vos = dao.getResourceOtherCosts(incidentResourceOtherId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_RESOURCE_OTHER_DAILY_COSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setRecordset(vos);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#saveDailyCost(gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveDailyCost(IncidentResourceDailyCostVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long resId=0L;
			if(null != vo && null != vo.getIncidentResourceVo() && LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
				resId=vo.getIncidentResourceVo().getId();
			}
			if(this.isLocked(resId, "INCIDENTRESOURCE", dialogueVo)==true){
				return dialogueVo;
			}
			
			DailyCostGenerator costGenerator = new DailyCostGenerator(this.context);
			
			// get entity if avail
			IncidentResourceDailyCost entity=null;
			IncidentResourceDailyCostVo preSaveVo=null;
			DailyCostVo preSaveDailyCostVo=null;

			if(LongUtility.hasValue(vo.getId())){
				entity=dao.getById(vo.getId(), IncidentResourceDailyCostImpl.class);
				preSaveDailyCostVo=DailyCostVo.getInstance(entity);
			}

			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			IncidentResource irEntity = null;
			IncidentResourceOther iroEntity = null;
			
			if(null != vo.getIncidentResourceVo())
				irEntity=irDao.getById(vo.getIncidentResourceVo().getId() , IncidentResourceImpl.class);
			else if(null != vo.getIncidentResourceOtherVo())
				iroEntity=iroDao.getById(vo.getIncidentResourceOtherVo().getId(), IncidentResourceOtherImpl.class);
			
			DailyCostSaveRulesHandler ruleHandler = new DailyCostSaveRulesHandler(super.context);
			if(ruleHandler.execute(vo, entity,irEntity, iroEntity, dialogueVo)==AbstractRule._OK){
				
				// set costLevel=M if manually added cost record
				if(!LongUtility.hasValue(vo.getId())){
					// set to M (Manual User)
					vo.setCostLevel("M");
				}else{
					if(null != vo.getCostLevel() && !vo.getCostLevel().equals("M")){
						// always to U (User) if updating a system generated record and core cost data changed
						if(IncidentResourceDailyCostVo.isCostDataDifferent(vo, entity))
							vo.setCostLevel("U");
							//entity.setCostLevel(CostLevelEnum.U);
					}
				}
				
				entity=IncidentResourceDailyCostVo.toEntity(entity, vo, true);
				
				dao.save(entity);
				dao.flushAndEvict(entity);

				if(null != vo.getIncidentResourceVo() 
						&& LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
					String sqlLock="update isw_inc_res_daily_cost ";
					if(BooleanUtility.isTrue(entity.getIsLocked())){
						 sqlLock=sqlLock+"set is_locked = " + (dao.isOracleDialect() ? 1 : true) + " ";
					}else{
						 sqlLock=sqlLock+"set is_locked = " + (dao.isOracleDialect() ? 0 : false) + " ";
					}
					sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
						 "  to_date('"+DateUtil.toDateString(entity.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
						 "and incident_resource_id = " + entity.getIncidentResourceId() + " "+
						 "";
					Collection<String> sqls = new ArrayList<String>();
					sqls.add(sqlLock);
					dao.persistSqls(sqls);
				}
				
				if(null != vo.getIncidentResourceOtherVo() 
						&& LongUtility.hasValue(vo.getIncidentResourceOtherVo().getId())){
					String sqlLock="update isw_inc_res_daily_cost ";
					if(BooleanUtility.isTrue(entity.getIsLocked())){
						 sqlLock=sqlLock+"set is_locked = " + (dao.isOracleDialect() ? 1 : true) + " ";
					}else{
						 sqlLock=sqlLock+"set is_locked = " + (dao.isOracleDialect() ? 0 : false) + " ";
					}
					sqlLock=sqlLock+"where to_date(to_char(activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') = " +
						 "  to_date('"+DateUtil.toDateString(entity.getActivityDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
						 "and incident_resource_other_id = " + entity.getIncidentResourceOtherId() + " "+
						 "";
					Collection<String> sqls = new ArrayList<String>();
					sqls.add(sqlLock);
					dao.persistSqls(sqls);
				}
				
				// run post process actions
				ruleHandler.executeProcessedActions(vo, entity,irEntity, iroEntity,dialogueVo);
				
				entity=dao.getById(entity.getId(), IncidentResourceDailyCostImpl.class);

				// if flowdown is checked, apply flowdown
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(BooleanUtility.isTrue(entity.getIsFlowdown())){
					CostFlowdown costFlowdown = new CostFlowdown(super.context);
					costFlowdown.irdcDao=this.dao;
					
					if(null != irEntity){
						DailyCostVo flowdownSourceVo=DailyCostVo.getInstance(entity);

						// get all costs >= date
						Collection<DailyCostVo> dailyCostVos=dao.getDailyCosts(irEntity.getId(), null, entity.getActivityDate());
						costFlowdown.dailyCostVos=dailyCostVos;
						costFlowdown.userFlowdown(preSaveDailyCostVo, flowdownSourceVo);
						
						Collection<IncidentResourceDailyCost> entities=dao.getByIncidentResourceId(entity.getIncidentResource().getId());
						vos=IncidentResourceDailyCostVo.getInstances(entities, true);
					}
					
					if(null != iroEntity){
						DailyCostVo flowdownSourceVo=DailyCostVo.getInstance(entity);

						// get all costs >= date
						Collection<DailyCostVo> dailyCostVos=dao.getDailyCosts(null, iroEntity.getId(), entity.getActivityDate());
						costFlowdown.dailyCostVos=dailyCostVos;
						costFlowdown.userFlowdown(preSaveDailyCostVo, flowdownSourceVo);
						
						Collection<IncidentResourceDailyCost> entities=dao.getByIncidentResourceOtherId(entity.getIncidentResourceOther().getId());
						vos=IncidentResourceDailyCostVo.getInstances(entities, true);
					}
				}else{
					if(null != irEntity){
						// get all costs >= date
						Collection<DailyCostVo> dailyCostVos=dao.getDailyCosts(irEntity.getId(), null, entity.getActivityDate());
						Collection<IncidentResourceDailyCost> entities=dao.getByIncidentResourceId(entity.getIncidentResource().getId());
						vos=IncidentResourceDailyCostVo.getInstances(entities, true);
					}
					if(null != iroEntity){
						// get all costs >= date
						Collection<DailyCostVo> dailyCostVos=dao.getDailyCosts(null, iroEntity.getId(), entity.getActivityDate());
						Collection<IncidentResourceDailyCost> entities=dao.getByIncidentResourceOtherId(entity.getIncidentResourceOther().getId());
						vos=IncidentResourceDailyCostVo.getInstances(entities, true);
					}
					
				}
				
				// update parent costs
				if(null != irEntity && null != irEntity.getResource().getParentResource()){
					Long parentResourceId=irEntity.getResource().getParentResource().getId();
					Collection<CostResourceDataVo> costResourceDataVos = irDao.getCostResourceData(irEntity.getId(),null, null);
					CostGenerator costGen = new CostGenerator(this.context);
					for(CostResourceDataVo v : costResourceDataVos){
						if(parentResourceId.compareTo(v.getResourceId())==0){
							costGen.generateCostsForParent(parentResourceId, costResourceDataVos);
						}
					}
				}
				
				vo=IncidentResourceDailyCostVo.getInstance(entity, true);

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_DAILY_COST");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.0030" , new String[]{"Cost Record"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setResultObject(vo);
				if(CollectionUtility.hasValue(vos))
					dialogueVo.setResultObjectAlternate(vos);
				
				// get all resource costs for all dates
				if(null != vo.getIncidentResourceVo()){
					DialogueVo dvo2 = this.getResourceDailyCosts(vo.getIncidentResourceVo().getId(), null);
					dialogueVo.setResultObjectAlternate(dvo2.getRecordset());
				}
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#deleteDailyCost(gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteDailyCost(IncidentResourceDailyCostVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long resId=0L;
			if(null != vo && null != vo.getIncidentResourceVo() && LongUtility.hasValue(vo.getIncidentResourceVo().getId())){
				resId=vo.getIncidentResourceVo().getId();
			}
			if(this.isLocked(resId, "INCIDENTRESOURCE", dialogueVo)==true){
				return dialogueVo;
			}
			
			IncidentResourceDailyCostDao dao = (IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
			
			DailyCostDeleteRulesHandler ruleHandler = new DailyCostDeleteRulesHandler(super.context);
			if(ruleHandler.execute(vo, dao, dialogueVo)==AbstractRule._OK){
				
				IncidentResourceDailyCost entity = dao.getById(vo.getId(), IncidentResourceDailyCostImpl.class);

				this.dao.delete(entity);

				ruleHandler.executeProcessedActions(vo, dao, dialogueVo);
				
				if(null != vo.getIncidentResourceVo()){
					IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
					IncidentResource irEntity = irDao.getById(vo.getIncidentResourceVo().getId() , IncidentResourceImpl.class);
					
					// update parent costs
					DailyCostGenerator costGenerator = new DailyCostGenerator(this.context);
					if(null != irEntity && null != irEntity.getResource().getParentResource()){
						Collection<CostResourceDataVo> costResourceDataVos = irDao.getCostResourceData(irEntity.getId(),null,null);
						
						CostGenerator costGen = new CostGenerator(this.context);
						/*
						for(CostResourceDataVo v : costResourceDataVos){
							if(LongUtility.hasValue(v.getParentResourceId())
										&& v.getParentResourceId().compareTo(irEntity.getResource().getParentResource().getId())==0){
								costGen.generateCostsForParent(v.getParentResourceId(), costResourceDataVos);
							}
						}
						*/
					}
				}

				DialogueVo dvo2 = new DialogueVo();
				if(null != vo.getIncidentResourceVo())
					dvo2=this.getResourceDailyCosts(vo.getIncidentResourceVo().getId(), dvo2);
				else if(null != vo.getIncidentResourceOtherVo())
					dvo2=this.getResourceOtherDailyCosts(vo.getIncidentResourceOtherVo().getId(), dvo2);
					
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_DAILY_COST");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.0028" , new String[]{"Cost Record"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setResultObjectAlternate(dvo2.getRecordset());
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#getIncidentTotalCost(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIncidentTotalCost(Long incidentId, Long incidentGroupId,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
			if(LongUtility.hasValue(incidentId)){
				BigDecimal incidentTotalCost=this.dao.getIncidentTotalCostAmount(incidentId);
				if(null != incidentTotalCost)
					dialogueVo.setResultObject(incidentTotalCost);
			}
			
			if(LongUtility.hasValue(incidentGroupId)){
				BigDecimal incidentTotalCost=this.dao.getIncidentGroupTotalCostAmount(incidentGroupId);
				if(null != incidentTotalCost)
					dialogueVo.setResultObject(incidentTotalCost);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENT_TOTAL_COST");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#runUpdateRatesResource(gov.nwcg.isuite.core.vo.IncidentResourceVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runUpdateRatesResourceOld(IncidentResourceVo irVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long resId=0L;
			if(null != irVo && LongUtility.hasValue(irVo.getId())){
				resId=irVo.getId();
			}
			if(this.isLocked(resId, "INCIDENTRESOURCE", dialogueVo)==true){
				return dialogueVo;
			}
	
			// run update on selected resource
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResource entity = null;
			
			if(LongUtility.hasValue(irVo.getId()))
				entity=irDao.getById(irVo.getId(), IncidentResourceImpl.class);

				// Try and run the cost process and set updateRatesOnly to true for this resource
				CostGenerator costGenerator = new CostGenerator(this.context);
				costGenerator.setUpdateRatesOnly(true);
				//costGenerator.runResourceDailyCosts(entity,null,true);
	
				// get list of daily cost entities
				Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceId(entity.getId());
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities))
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);

				Collection<IncidentResourceDailyCostVo> newvos = IncidentResourceDailyCostVo.getHierarchicalInstances(vos);
			
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_UPDATE_RATES_RESOURCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"Cost Records updated with new rates"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setRecordset(newvos);
				dialogueVo.setCourseOfActionVo(coaVo);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo runUpdateRatesResource(IncidentResourceVo irVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long resId=0L;
			if(null != irVo && LongUtility.hasValue(irVo.getId())){
				resId=irVo.getId();
			}
			if(this.isLocked(resId, "INCIDENTRESOURCE", dialogueVo)==true){
				return dialogueVo;
			}
	
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			CostResourceDataVo voToProcess=null;
			
			// run update on selected resource
			IncidentResource entity = null;
			
			if(LongUtility.hasValue(irVo.getId()))
				entity=irDao.getById(irVo.getId(), IncidentResourceImpl.class);

				// Try and run the cost process and set updateRatesOnly to true for this resource
				CostUpdateRatesGenerator costUpdateRatesGenerator = new CostUpdateRatesGenerator(this.context);

				Collection<CostResourceDataVo> costResourceDataVos = irDao.getCostResourceData(irVo.getId(),null,null);
				for(CostResourceDataVo v : costResourceDataVos){
					if(v.getIncidentResourceId().compareTo(irVo.getId())==0){
						voToProcess=v;
						break;
					}
				}
				
				costUpdateRatesGenerator.updateCostRates(voToProcess, costResourceDataVos, false);
	
				// get list of daily cost entities
				Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceId(entity.getId());
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities))
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);

				Collection<IncidentResourceDailyCostVo> newvos = IncidentResourceDailyCostVo.getHierarchicalInstances(vos);
			
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_UPDATE_RATES_RESOURCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"Cost Records updated with new rates"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setRecordset(newvos);
				dialogueVo.setCourseOfActionVo(coaVo);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo runUpdateRatesResourceOtherOld(IncidentResourceOtherVo iroVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
	
			// run update on selected resource
			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			IncidentResourceOther entity = null;
			
			if(LongUtility.hasValue(iroVo.getId()))
				entity=iroDao.getById(iroVo.getId(), IncidentResourceOtherImpl.class);

				// Try and run the cost process and set updateRatesOnly to true for this resource
				DailyCostGenerator costGenerator = new DailyCostGenerator(this.context);
				costGenerator.setUpdateRatesOnly(true);
				costGenerator.runResourceOtherDailyCosts(entity,null,true);
	
				// get list of daily cost entities
				Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceOtherId(entity.getId());
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities))
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);
			
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_UPDATE_RATES_RESOURCEOTHER");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"Cost Records updated with new rates"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo runUpdateRatesResourceOther(IncidentResourceOtherVo iroVo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long resId=0L;
			if(null != iroVo && LongUtility.hasValue(iroVo.getId())){
				resId=iroVo.getId();
			}
			if(this.isLocked(resId, "RESOURCEOTHER", dialogueVo)==true){
				return dialogueVo;
			}
	
			// run update on selected other resource
			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			IncidentResourceOther entity = null;
			
			if(LongUtility.hasValue(iroVo.getId()))
				entity=iroDao.getById(iroVo.getId(), IncidentResourceOtherImpl.class);

				CostOtherUpdateRatesGenerator gen = new CostOtherUpdateRatesGenerator(context);
				
				Collection<CostResourceDataVo> costResourceOtherDataVos = iroDao.getCostResourceData(iroVo.getId(), null, null);
				for(CostResourceDataVo v : costResourceOtherDataVos){
					if(v.getIncidentResourceOtherId().compareTo(iroVo.getId())==0)
						gen.updateCostRates(v, costResourceOtherDataVos,false);
				}
				
				// get list of daily cost entities
				Collection<IncidentResourceDailyCost> entities = this.dao.getByIncidentResourceOtherId(entity.getId());
				Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities))
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);
			
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("RUN_UPDATE_RATES_RESOURCEOTHER");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"Cost Records updated with new rates"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setRecordset(vos);
				dialogueVo.setCourseOfActionVo(coaVo);
				
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentResourceDailyCostService#runUpdateRates(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo runUpdateRates(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			Long id=0L;
			String type="NONE";
			
			if(LongUtility.hasValue(incidentId)){
				id=incidentId;
				type="INCIDENT";
			}else if(LongUtility.hasValue(incidentGroupId)){
				id=incidentGroupId;
				type="INCIDENTGROUP";
			}
			if(this.isLocked(id, type, dialogueVo)==true){
				return dialogueVo;
			}
		
			IncidentResourceDao incResourceDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			IncidentResourceOtherDao incResourceOtherDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			Collection<IncidentResource> entities = new ArrayList<IncidentResource>();
			Collection<IncidentResourceOther> iroentities = new ArrayList<IncidentResourceOther>();
			String mode="";
			
			// get all resources in incident
			if(LongUtility.hasValue(incidentId)){
				entities = incResourceDao.getTopLevelResources(incidentId);
				iroentities=incResourceOtherDao.getTopLevelResources(incidentId);
				mode="RUN_UPDATE_RATES_INCIDENT";
			}
			
			// get all resources in incident group
			if(LongUtility.hasValue(incidentGroupId)){
				entities = incResourceDao.getTopLevelResourcesIG(incidentGroupId);
				iroentities=incResourceOtherDao.getTopLevelResourcesIG(incidentGroupId);
				mode="RUN_UPDATE_RATES_INCIDENT_GROUP";
			}
			
			// run update rates for all resources
			DailyCostGenerator costGenerator = new DailyCostGenerator(this.context);
			for(IncidentResource entity : entities){
				costGenerator.setUpdateRatesOnly(true);
				costGenerator.runResourceDailyCosts(entity,null,true);
			}

			for(IncidentResourceOther entity : iroentities){
				costGenerator.setUpdateRatesOnly(true);
				costGenerator.runResourceOtherDailyCosts(entity,null,true);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(mode);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.dailyCost", "info.generic" , new String[]{"Cost Records updated with new rates"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
}
