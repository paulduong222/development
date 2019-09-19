package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.core.domain.SysCostRateOvhd;
import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.domain.SysCostRateStateKind;
import gov.nwcg.isuite.core.domain.impl.SysCostRateKindImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateOvhdImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateKindImpl;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.filter.impl.CostRateFilterImpl;
import gov.nwcg.isuite.core.persistence.SysCostRateDao;
import gov.nwcg.isuite.core.persistence.SysCostRateKindDao;
import gov.nwcg.isuite.core.persistence.SysCostRateOvhdDao;
import gov.nwcg.isuite.core.persistence.SysCostRateStateDao;
import gov.nwcg.isuite.core.persistence.SysCostRateStateKindDao;
import gov.nwcg.isuite.core.rules.SysCostRateOvhdSaveRulesHandler;
import gov.nwcg.isuite.core.rules.SysCostRateStateSaveRulesHandler;
import gov.nwcg.isuite.core.service.SysCostRateService;
import gov.nwcg.isuite.core.vo.SysCostRateGridVo;
import gov.nwcg.isuite.core.vo.SysCostRateKindVo;
import gov.nwcg.isuite.core.vo.SysCostRateOvhdVo;
import gov.nwcg.isuite.core.vo.SysCostRateStateKindVo;
import gov.nwcg.isuite.core.vo.SysCostRateStateVo;
import gov.nwcg.isuite.core.vo.SysCostRateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.math.BigDecimal;
import java.util.Collection;


public class SysCostRateServiceImpl extends BaseService implements SysCostRateService {

	public SysCostRateServiceImpl(){
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#getDefaultRatesGrid(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getDefaultRatesGrid(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		
		if(null==dialogueVo)dialogueVo = new DialogueVo();
		
		try{

			/*
			 * Init dao
			 */
			SysCostRateDao dao = (SysCostRateDao)context.getBean("sysCostRateDao");

			/*
			 * Query for the vos
			 */
			Collection<SysCostRateGridVo> vos = dao.getDefaultRatesGrid(filter);

			/*
			 * Apply state rate filters if applicable 
			 */
			//vos = SysCostRateGridVo.applyStateFilters(vos, (CostRateGridFilter)filter.getSubFilter());
			
			/*
			 * Apply advanced search if applicable
			 */
			//if(null != filter && StringUtility.hasValue(filter.getAdvancedSearch())){
			//	vos = SysCostRateGridVo.applyAdvancedSearch(vos, filter.getAdvancedSearch());
			//}
			
			/*
			 * Build course of action for handling a recordset
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SYSCOST_RATE_GRID");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);

			/*
			 * Populate the dialogueVo with coa 
			 */
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#getSysCostRateKinds(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSysCostRateKinds(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			/*
			 * Init dao
			 */
			SysCostRateDao dao = (SysCostRateDao)context.getBean("sysCostRateDao");

			/*
			 * Query for the entities
			 */
			Collection<SysCostRateKind> entities = dao.getSysCostRateKinds(filter);
			Collection<SysCostRateKindVo> vos = SysCostRateKindVo.getInstances(entities, true);
			
			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SYS_COST_RATE_KINDS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET );
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#getSysCostRateOvhd(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSysCostRateOvhd(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			/*
			 * Init dao
			 */
			SysCostRateDao dao = (SysCostRateDao)context.getBean("sysCostRateDao");

			/*
			 * Query for the entity
			 */
			SysCostRateOvhd entity = dao.getSysCostRateOvhd(filter);
			
			SysCostRateOvhdVo vo =null;
			if(null != entity)
				vo = SysCostRateOvhdVo.getInstance(entity, true);
			else{
				vo = new SysCostRateOvhdVo();
				vo.setIndirectRate(new BigDecimal(0.0));
				vo.setDirectRate(new BigDecimal(0.0));
				vo.setSingleRate(new BigDecimal(0.0));
				vo.setSubordinateRate(new BigDecimal(0.0));
				
				SysCostRate scr=dao.getSysCostRate(filter);
				if(null != scr)
					vo.setSysCostRateId(scr.getId());
				
			}
			
			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SYS_COST_RATE_OVHD");
			if(null != vo){
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET );
				dialogueVo.setResultObject(vo);
				coaVo.setIsDialogueEnding(Boolean.TRUE);
			}else{
				coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION );
			}
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#getSysCostRate(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSysCostRate(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			/*
			 * Init dao
			 */
			SysCostRateDao dao = (SysCostRateDao)context.getBean("sysCostRateDao");

			/*
			 * Query for the entity
			 */
			SysCostRate entity = dao.getSysCostRate(filter);
			SysCostRateVo vo = null;
			
			if(null != entity)
				vo = SysCostRateVo.getInstance(entity, true);
			
			Collection<SysCostRateKind> entities = dao.getSysCostRateKinds(filter);
			vo.setSysCostRateKindVos(SysCostRateKindVo.getInstances(entities, true));
			
			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SYS_COST_RATE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET );
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#saveSysCostRateKind(gov.nwcg.isuite.core.vo.SysCostRateKindVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveSysCostRateKind(SysCostRateKindVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			/*
			 * Init dao
			 */
			SysCostRateKindDao dao = (SysCostRateKindDao)context.getBean("sysCostRateKindDao");
			
			SysCostRateKind entity = SysCostRateKindVo.toEntity(vo, true);

			/*
			 * Persist the entity and flush
			 */
			dao.save(entity);
			dao.flushAndEvict(entity);

			/*
			 * Query for the entity
			 */
			entity = dao.getById(entity.getId(), SysCostRateKindImpl.class);
			
			if(null != entity){
				SysCostRateKindVo scrkVo = SysCostRateKindVo.getInstance(entity, true);
				dialogueVo.setResultObject(scrkVo);
			}

			/*
			 * Build course of action for SHOWMESSAGE 
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_SYSCOST_RATE_KIND");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setMessageVo(new MessageVo("text.globalCostRates","info.0030",null,MessageTypeEnum.INFO ));
			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#saveSysCostRateKind(gov.nwcg.isuite.core.vo.SysCostRateOvhdVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveSysCostRateOvhd(SysCostRateOvhdVo vo, DialogueVo dialogueVo) throws ServiceException {
		
		if(null == dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			/*
			 * Init dao
			 */
			SysCostRateOvhdDao dao = (SysCostRateOvhdDao)context.getBean("sysCostRateOvhdDao");

			/*
			 * Get the rules handler for saving ovhd rates.
			 */
			SysCostRateOvhdSaveRulesHandler rulesHandler = new SysCostRateOvhdSaveRulesHandler(context);
			
			/*
			 * Do the pre save business rules
			 */
			if(rulesHandler.execute(vo, "", dialogueVo) == AbstractRule._OK){
				
				SysCostRateOvhd entity = SysCostRateOvhdVo.toEntity(vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(entity.getId(), SysCostRateOvhdImpl.class);
				
				if(null != entity){
					SysCostRateOvhdVo scrOvhdVo = SysCostRateOvhdVo.getInstance(entity, true);
					dialogueVo.setResultObject(scrOvhdVo);
				}
				
				/*
				 * Do any post save business rules 
				 */
				rulesHandler.executeProcessedActions(vo, entity.getSysCostRate().getCostRateCategory(), dialogueVo);

				/*
				 * Get updated collection
				 */
				CostRateFilter filter = new CostRateFilterImpl();
				filter.setCostRateCategory(entity.getSysCostRate().getCostRateCategory());
				DialogueVo dvo2 = this.getSysCostRateKinds(filter, null);
				if(null != dvo2){
					dialogueVo.setResultObjectAlternate(dvo2.getRecordset());
				}
				
				/*
				 * Build course of action for SHOWMESSAGE 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_SYS_COST_RATE_OVHD");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVo.setMessageVo(new MessageVo("text.globalCostRates","info.0030",null,MessageTypeEnum.INFO ));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#saveSysCostRateState(gov.nwcg.isuite.core.vo.SysCostRateStateVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveSysCostRateState(SysCostRateStateVo vo, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo)dialogueVo = new DialogueVo();
		
		try{
			/*
			 * Init dao
			 */
			SysCostRateStateDao dao = (SysCostRateStateDao)context.getBean("sysCostRateStateDao");

			/*
			 * Get the rules handler for saving state ovhd rates.
			 */
			SysCostRateStateSaveRulesHandler rulesHandler = new SysCostRateStateSaveRulesHandler(context);
			
			/*
			 * Do the pre save business rules
			 */
			if(rulesHandler.execute(vo, dialogueVo) == AbstractRule._OK){
				
				SysCostRateState entity = SysCostRateStateVo.toEntity(vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				
				entity = dao.getById(entity.getId(), SysCostRateStateImpl.class);
				
				/*
				 * Do any post save business rules 
				 */
				rulesHandler.executeProcessedActions(vo, dialogueVo);

				if(null != entity){
					SysCostRateStateVo scrStateVo = SysCostRateStateVo.getInstance(entity, true);
					dialogueVo.setResultObject(scrStateVo);
				}
				
				/*
				 * Get updated collection
				 */
				CostRateFilter filter = new CostRateFilterImpl();
				filter.setCostRateCategory("STATE_COOP_CUSTOM");
				filter.setAgencyId(vo.getAgencyVo().getId());
				DialogueVo dvo2 = this.getSysCostRateStateKinds(filter, null);
				if(null != dvo2){
					dialogueVo.setResultObjectAlternate(dvo2.getRecordset());
				}
				
				/*
				 * Build course of action for SHOWMESSAGE 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_SYSCOST_RATE_STATE");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVo.setMessageVo(new MessageVo("text.globalCostRates","info.0030",null,MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#saveSysCostRateStateKind(gov.nwcg.isuite.core.vo.SysCostRateStateKindVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveSysCostRateStateKind(SysCostRateStateKindVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}

		try{
			/*
			 * Init dao
			 */
			SysCostRateStateKindDao dao = (SysCostRateStateKindDao)context.getBean("sysCostRateStateKindDao");

			/*
			 * Get copy of original data
			 */
			SysCostRateStateKind origEntity = dao.getById(vo.getId(), SysCostRateStateKindImpl.class);
//			SysCostRateStateKindVo originalVo = SysCostRateStateKindVo.getInstance(origEntity, true);
			dao.flushAndEvict(origEntity);

			/*
			 * Get the rules handler for saving state kind rates.
			 */
			//SysCostRateStateKindSaveRulesHandler rulesHandler = new SysCostRateStateSaveRulesHandler(context);

			/*
			 * Do the pre save business rules
			 */
			//if(rulesHandler.execute(vo, dialogueVo) == AbstractRule._OK){

				SysCostRateStateKind entity = SysCostRateStateKindVo.toEntity(vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);

				/*
				 * Do any post save business rules 
				 */
				//rulesHandler.executeProcessedActions(vo, originalVo, dialogueVo);

				/*
				 * Prepare the dialogueVo for the return value 
				 */
				entity = dao.getById(entity.getId(), SysCostRateStateKindImpl.class);

				if(null != entity){
					SysCostRateStateKindVo scrStateVo = SysCostRateStateKindVo.getInstance(entity, true);
					dialogueVo.setResultObject(scrStateVo);
				}

				/*
				 * Build course of action for SHOWMESSAGE 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_SYSCOST_RATE_STATE_KIND");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVo.setMessageVo(new MessageVo("text.globalCostRates","info.0030",null,MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
			//}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SysCostRateService#getSysCostRateState(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getSysCostRateState(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}
			
			SysCostRateStateDao sysCostRateStateDao = (SysCostRateStateDao)super.context.getBean("sysCostRateStateDao");
			// return a single state record
			SysCostRateState entity = sysCostRateStateDao.getSysCostRateState(filter);
			SysCostRateStateVo vo = null;
			if(entity != null) {
				vo = SysCostRateStateVo.getInstance(entity, true);
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SYS_COST_RATE_STATE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}
	
	public DialogueVo getSysCostRateStateKinds(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) { 
			dialogueVo = new DialogueVo();
		}

		try{
			/*
			 * Init dao
			 */
			SysCostRateStateKindDao dao = (SysCostRateStateKindDao)context.getBean("sysCostRateStateKindDao");

			/*
			 * Query for the entities
			 */
			Collection<SysCostRateStateKind> entities = dao.getSysCostRateStateKinds(filter);
			Collection<SysCostRateStateKindVo> vos = SysCostRateStateKindVo.getInstances(entities, true);

			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SYS_COST_RATE_STATE_KINDS");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setRecordset(vos);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;

	}

	
}
