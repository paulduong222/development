/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.cost.utilities.DefaultAccrualCodeHandler;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceOtherImpl;
import gov.nwcg.isuite.core.filter.OtherResourceCostFilter;
import gov.nwcg.isuite.core.filter.impl.OtherResourceCostFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.persistence.ResourceOtherDao;
import gov.nwcg.isuite.core.rules.IncidentResourceOtherSaveRulesHandler;
import gov.nwcg.isuite.core.rules.common.sitemanaged.SiteManagedRuleFactory;
import gov.nwcg.isuite.core.service.OtherResourceCostService;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherVo;
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
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class OtherResourceCostServiceImpl extends BaseService implements OtherResourceCostService {

	protected IncidentResourceOtherDao dao;
	protected ResourceOtherDao resourceOtherDao;
	
	public OtherResourceCostServiceImpl(){
		super();
	}

	public void initialization(){
		dao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
		resourceOtherDao = (ResourceOtherDao)context.getBean("resourceOtherDao");
	}

	@Override
	public DialogueVo getOtherResourceById(Long otherResourceId, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			IncidentResourceOther entity = null;
			
			if(LongUtility.hasValue(otherResourceId)){
				entity = dao.getById(otherResourceId, IncidentResourceOtherImpl.class);
			}
			
			IncidentResourceOtherVo vo = new IncidentResourceOtherVo();

			Collection<IncidentResourceDailyCostVo> vos = new ArrayList<IncidentResourceDailyCostVo>();
			
			if(null != entity){
				vo = IncidentResourceOtherVo.getInstance(entity, true);

				dao.flushAndEvict(entity);
				
				// get list of daily cost entities
				IncidentResourceDailyCostDao irdcDao =(IncidentResourceDailyCostDao)context.getBean("incidentResourceDailyCostDao");
				Collection<IncidentResourceDailyCost> entities = irdcDao.getByIncidentResourceOtherId(entity.getId());
				vos = new ArrayList<IncidentResourceDailyCostVo>();
				if(CollectionUtility.hasValue(entities))
					vos=IncidentResourceDailyCostVo.getInstances(entities, true);
			}
			
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_BY_ID_OTHER_RESOURCE");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(vo);
			dialogueVo.setResultObjectAlternate(vos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	@Override
	public DialogueVo saveOtherResource(IncidentResourceOtherVo vo, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IncidentResourceOther entity=null;
			
			// Get entity if it was previously saved
			if(LongUtility.hasValue(vo.getId())){
				entity=dao.getById(vo.getId(), IncidentResourceOtherImpl.class);
			}

			// Validate inputs and proceed if all rules pass
			IncidentResourceOtherSaveRulesHandler ruleHandler = new IncidentResourceOtherSaveRulesHandler(super.context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK) {
				
				// set default accrual code
				if(null != vo.getCostDataVo() && BooleanUtility.isFalse(vo.getCostDataVo().getAccrualLocked())){
					DefaultAccrualCodeHandler acHandler = new DefaultAccrualCodeHandler(super.context);
					acHandler.setDefaultAccrualCodeVo(vo);
				}
				
				entity=IncidentResourceOtherVo.toEntity(entity, vo, true);
				dao.save(entity);
				if(LongUtility.hasValue(entity.getId()))
					dao.flushAndEvict(entity);
				entity=dao.getById(entity.getId(), IncidentResourceOtherImpl.class);

				ruleHandler.executeProcessedActions(vo,entity,dialogueVo);

				
				vo=IncidentResourceOtherVo.getInstance(entity, true);
				
				//Get the GridVO for IncidentResourceOther, and save in dialogueVo.setResultObjectAlternate
				OtherResourceCostFilter otherResourceCostFilter = new OtherResourceCostFilterImpl();
				otherResourceCostFilter.setIncidentId(vo.getIncidentVo().getId());
				otherResourceCostFilter.setIncidentResourceOtherId(vo.getId());
				
				// Call getGrid and extract the first gridVo, if it exists
				DialogueVo dvo = this.getGrid(otherResourceCostFilter, null);
				Collection<IncidentResourceOtherGridVo> vos = (Collection<IncidentResourceOtherGridVo>)dvo.getRecordset();
				if(CollectionUtility.hasValue(vos)){
					dialogueVo.setResultObjectAlternate((IncidentResourceOtherGridVo)vos.iterator().next());
				}

				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_OTHER RESOURCE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.otherCost", "info.0030" , new String[]{"Other Resource Record"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);
			}
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	@Override
	public DialogueVo getGrid(OtherResourceCostFilter otherResourceCostFilter,
			DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<IncidentResourceOtherGridVo> vos = new ArrayList<IncidentResourceOtherGridVo>();

			vos = dao.getGrid(otherResourceCostFilter);

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_INCIDENT_RESOURCE_OTHER_GRID");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coa.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setRecordset(vos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	@Override
	public DialogueVo deleteOtherResource(Long otherResourceId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IncidentResourceOther entity = dao.getById(otherResourceId, IncidentResourceOtherImpl.class);

			if(null != entity){
				Long incId=0L;
				if(null != entity.getIncident() && LongUtility.hasValue(entity.getIncident().getId())){
					incId=entity.getIncident().getId();
				}
				for(SiteManagedRuleFactory.RuleEnum re : SiteManagedRuleFactory.RuleEnum.values()){
					IRule rule = SiteManagedRuleFactory.getInstance(re, context, incId, "INCIDENT");
					
					if(null != rule){
						if(AbstractRule._OK != rule.executeRules(dialogueVo)){
							dao.flushAndEvict(entity);
							return dialogueVo;
						}
					}
				}
				
			}
			
			this.dao.delete(entity);
		
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("DELETE_OTHER_RESOURCE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.otherCost", "info.0028" , new String[]{"Other Resource"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setResultObject(otherResourceId);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
