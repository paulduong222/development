package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.IncidentCostRateStateKind;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateKindImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateOvhdImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateKindImpl;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.filter.impl.CostRateFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentCostRateDao;
import gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao;
import gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao;
import gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao;
import gov.nwcg.isuite.core.persistence.IncidentCostRateStateKindDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.rules.IncidentCostRateOvhdSaveRulesHandler;
import gov.nwcg.isuite.core.rules.IncidentCostRateStateSaveRulesHandler;
import gov.nwcg.isuite.core.service.IncidentCostRateService;
import gov.nwcg.isuite.core.vo.IncidentCostRateGridVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateKindVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateKindVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.core.vo.IncidentCostRateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;


public class IncidentCostRateServiceImpl extends BaseService implements IncidentCostRateService {

	public IncidentCostRateServiceImpl(){
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#getDefaultRatesGrid(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getDefaultRatesGrid(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {

		if(null==dialogueVo)dialogueVo = new DialogueVo();

		try{

			/*
			 * Init dao
			 */
			IncidentCostRateDao dao = (IncidentCostRateDao)context.getBean("incidentCostRateDao");

			/*
			 * Query for the vos
			 */
			Collection<IncidentCostRateGridVo> vos = dao.getDefaultRatesGrid(filter);

			/*
			 * Build course of action for handling a recordset
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_INCIDENTCOST_RATE_GRID");
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
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#getIncidentCostRateKinds(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIncidentCostRateKinds(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateDao dao = (IncidentCostRateDao)context.getBean("incidentCostRateDao");

			/*
			 * Query for the entities
			 */
			Collection<IncidentCostRateKind> entities = dao.getIncidentCostRateKinds(filter);
			Collection<IncidentCostRateKindVo> vos = IncidentCostRateKindVo.getInstances(entities, true);

			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_COST_RATE_KINDS");
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
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#getIncidentCostRateOvhd(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIncidentCostRateOvhd(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateDao dao = (IncidentCostRateDao)context.getBean("incidentCostRateDao");

			/*
			 * Query for the entity
			 */
			IncidentCostRateOvhd entity = dao.getIncidentCostRateOvhd(filter);
			IncidentCostRateOvhdVo vo = null;
			if(null != entity){
				vo=IncidentCostRateOvhdVo.getInstance(entity, true);
			}
			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_COST_RATE_OVHD");
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
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#getIncidentCostRate(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIncidentCostRate(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateDao dao = (IncidentCostRateDao)context.getBean("incidentCostRateDao");

			/*
			 * Query for the entity
			 */
			IncidentCostRate entity = dao.getIncidentCostRate(filter);
			IncidentCostRateVo vo = null;

			if(null != entity)
				vo = IncidentCostRateVo.getInstance(entity, true);

			Collection<IncidentCostRateKind> entities = dao.getIncidentCostRateKinds(filter);
			vo.setCostRateKindVos(IncidentCostRateKindVo.getInstances(entities, true));

			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_COST_RATE");
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
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#saveIncidentCostRateKind(gov.nwcg.isuite.core.vo.IncidentCostRateKindVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentCostRateKind(IncidentCostRateKindVo vo, String category, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo)dialogueVo = new DialogueVo();

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateKindDao dao = (IncidentCostRateKindDao)context.getBean("incidentCostRateKindDao");

			IncidentCostRateKind entity = IncidentCostRateKindVo.toEntity(vo, true);

			/*
			 * Persist the entity and flush
			 */
			dao.save(entity);
			dao.flushAndEvict(entity);

			
			/*
			 * Query for the entity
			 */
			entity = dao.getById(entity.getId(), IncidentCostRateKindImpl.class);

			Long incidentId=dao.getIncidentId(entity.getId());
			Long groupId=dao.getIncidentGroupId(entity.getId());
			
			// sync incident/group rates
			if(LongUtility.hasValue(groupId)){
				// propagate incgroup rate values to all corresponding ones in incidents in group
				// dan: 2/7/2015 todo  defect 4042
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				Collection<Long> incidentIds = igDao.getIncidentIdsInGroup(groupId);
				if(CollectionUtility.hasValue(incidentIds)){
					try{
						dao.updateIncidents(entity, incidentIds);
					}catch(Exception smother){}
				}
			}else if(LongUtility.hasValue(incidentId)){
				// if incident is part of group then
				// propagate inc rate value to corresponding one in incidentgroup
				// dan: 2/7/2015 todo  defect 4042
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentDao iDao = (IncidentDao)context.getBean("incidentDao");
				
				groupId=iDao.getIncidentGroupId(incidentId);

				if(LongUtility.hasValue(groupId)){
					Collection<Long> incidentIds = igDao.getIncidentIdsInGroup(groupId);
					try{
						dao.updateGroupAndIncidents(entity, incidentIds,groupId);
					}catch(Exception smother){}
				}
				
			}

			if(null != entity){
				IncidentCostRateKindVo scrkVo = IncidentCostRateKindVo.getInstance(entity, true);
				dialogueVo.setResultObject(scrkVo);
			}

			/*
			 * Build course of action for SHOWMESSAGE 
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_COST_RATE_KIND");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setMessageVo(new MessageVo("text.incidentCostRates","info.0030",null,MessageTypeEnum.INFO ));
			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#saveIncidentCostRateKind(gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentCostRateOvhd(IncidentCostRateOvhdVo vo, String category, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo)dialogueVo = new DialogueVo();

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateOvhdDao dao = (IncidentCostRateOvhdDao)context.getBean("incidentCostRateOvhdDao");
			
			Long incidentId=dao.getIncidentId(vo.getId());
			Long groupId=dao.getIncidentGroupId(vo.getId());
			
			/*
			 * Get the rules handler for saving ovhd rates.
			 */
			IncidentCostRateOvhdSaveRulesHandler rulesHandler = new IncidentCostRateOvhdSaveRulesHandler(context);

			/*
			 * Do the pre save business rules
			 */
			if(rulesHandler.execute(vo, category, incidentId, groupId, dialogueVo) == AbstractRule._OK){

				IncidentCostRateOvhd entity = IncidentCostRateOvhdVo.toEntity(vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity=dao.getById(vo.getId(), IncidentCostRateOvhdImpl.class);
				
				// sync incident/group rates
				Collection<Long> incidentIds = new ArrayList<Long>();
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				if(LongUtility.hasValue(groupId)){
					incidentIds = igDao.getIncidentIdsInGroup(groupId);
				}else{
					IncidentDao iDao = (IncidentDao)context.getBean("incidentDao");
					Long igid = iDao.getIncidentGroupId(incidentId);
					
					if(LongUtility.hasValue(igid)){
						dao.saveGroupRecord(entity, igid);
						incidentIds = igDao.getIncidentIdsInGroup(igid);
					}
				}
				if(CollectionUtility.hasValue(incidentIds)){
					for(Long incid : incidentIds){
						dao.saveGroupIncRecord(entity, incid);
					}
				}
				/*
				 * Do any post save business rules 
				 */
				rulesHandler.executeProcessedActions(vo, category, incidentId, groupId, dialogueVo);

				/*
				 * Get updated collection
				 */
				CostRateFilter filter = new CostRateFilterImpl();
				filter.setIncidentId(entity.getIncidentCostRate().getIncidentId());
				filter.setCostRateCategory(entity.getIncidentCostRate().getCostRateCategory());
				DialogueVo dvo2 = this.getIncidentCostRateKinds(filter, null);
				if(null != dvo2){
					dialogueVo.setResultObjectAlternate(dvo2.getRecordset());
				}
				
				dao.flushAndEvict(entity);

				/*
				 * Prepare the dialogueVo for the return value 
				 */
				entity = dao.getById(entity.getId(), IncidentCostRateOvhdImpl.class);

				if(null != entity){
					IncidentCostRateOvhdVo scrOvhdVo = IncidentCostRateOvhdVo.getInstance(entity, true);
					dialogueVo.setResultObject(scrOvhdVo);
				}

				/*
				 * Build course of action for SHOWMESSAGE 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_COST_RATE_OVHD");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVo.setMessageVo(new MessageVo("text.incidentCostRates","info.0030",null,MessageTypeEnum.INFO ));
				coaVo.setIsDialogueEnding(Boolean.TRUE);

				dialogueVo.setCourseOfActionVo(coaVo);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#saveIncidentCostRateState(gov.nwcg.isuite.core.vo.IncidentCostRateStateVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentCostRateState(IncidentCostRateStateVo vo, String category, DialogueVo dialogueVo) throws ServiceException {

		if(null == dialogueVo)dialogueVo = new DialogueVo();

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateStateDao dao = (IncidentCostRateStateDao)context.getBean("incidentCostRateStateDao");

			Long incidentId=dao.getIncidentId(vo.getId());
			Long groupId=dao.getIncidentGroupId(vo.getId());
			
			/*
			 * Get copy of original data
			 */
			IncidentCostRateState origEntity = dao.getById(vo.getId(), IncidentCostRateStateImpl.class);
			IncidentCostRateStateVo originalVo = IncidentCostRateStateVo.getInstance(origEntity, true);
			dao.flushAndEvict(origEntity);


			/*
			 * Get the rules handler for saving state ovhd rates.
			 */
			IncidentCostRateStateSaveRulesHandler rulesHandler = new IncidentCostRateStateSaveRulesHandler(context);

			/*
			 * Do the pre save business rules
			 */
			if(rulesHandler.execute(vo, "", incidentId, groupId,dialogueVo) == AbstractRule._OK){

				IncidentCostRateState entity = IncidentCostRateStateVo.toEntity(vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity=dao.getById(vo.getId(), IncidentCostRateStateImpl.class);

				// sync incident/group rates
				Collection<Long> incidentIds = new ArrayList<Long>();
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				if(LongUtility.hasValue(groupId)){
					incidentIds = igDao.getIncidentIdsInGroup(groupId);
				}else if(LongUtility.hasValue(incidentId)){
					// if incident is part of group then
					IncidentDao iDao = (IncidentDao)context.getBean("incidentDao");
					Long igid = iDao.getIncidentGroupId(incidentId);
					
					if(LongUtility.hasValue(igid)){
						dao.saveGroupRecord(entity, igid);
						incidentIds = igDao.getIncidentIdsInGroup(igid);
					}
				}
				if(CollectionUtility.hasValue(incidentIds)){
					for(Long incid : incidentIds){
						dao.saveGroupIncRecord(entity, incid);
					}
				}
				
				/*
				 * Do any post save business rules 
				 */
				rulesHandler.executeProcessedActions(vo, entity.getIncidentCostRate().getCostRateCategory(), incidentId, groupId, dialogueVo);

				/*
				 * Get updated collection
				 */
				CostRateFilter filter = new CostRateFilterImpl();
				filter.setIncidentId(entity.getIncidentCostRate().getIncidentId());
				filter.setCostRateCategory("STATE_COOP_CUSTOM");
				filter.setAgencyId(vo.getAgencyVo().getId());
				DialogueVo dvo2 = this.getIncidentCostRateStateKinds(filter, null);
				if(null != dvo2){
					dialogueVo.setResultObjectAlternate(dvo2.getRecordset());
				}
				
				dao.flushAndEvict(entity);

				/*
				 * Prepare the dialogueVo for the return value 
				 */
				entity = dao.getById(entity.getId(), IncidentCostRateStateImpl.class);

				if(null != entity){
					IncidentCostRateStateVo scrStateVo = IncidentCostRateStateVo.getInstance(entity, true);
					dialogueVo.setResultObject(scrStateVo);
				}

				/*
				 * Build course of action for SHOWMESSAGE 
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_COST_RATE_STATE");
				coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coaVo.setMessageVo(new MessageVo("text.incidentCostRates","info.0030",null,MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#saveIncidentCostRateStateKind(gov.nwcg.isuite.core.vo.IncidentCostRateStateKindVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo saveIncidentCostRateStateKind(IncidentCostRateStateKindVo vo, String category,DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) {
			dialogueVo = new DialogueVo();
		}

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateStateKindDao dao = (IncidentCostRateStateKindDao)context.getBean("incidentCostRateStateKindDao");

			Long incidentId=dao.getIncidentId(vo.getId());
			Long groupId=dao.getIncidentGroupId(vo.getId());
			
			/*
			 * Get copy of original data
			 */
			IncidentCostRateStateKind origEntity = dao.getById(vo.getId(), IncidentCostRateStateKindImpl.class);
			dao.flushAndEvict(origEntity);


			IncidentCostRateStateKind entity = IncidentCostRateStateKindVo.toEntity(vo, true);
			dao.save(entity);
			dao.flushAndEvict(entity);

			/*
			 * Prepare the dialogueVo for the return value 
			 */
			entity = dao.getById(entity.getId(), IncidentCostRateStateKindImpl.class);

			// sync incident/group rates
			Collection<Long> incidentIds = new ArrayList<Long>();
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			if(LongUtility.hasValue(groupId)){
				incidentIds = igDao.getIncidentIdsInGroup(groupId);
			}else if(LongUtility.hasValue(incidentId)){
				// if incident is part of group then
				IncidentDao iDao = (IncidentDao)context.getBean("incidentDao");
				Long igid = iDao.getIncidentGroupId(incidentId);
				
				if(LongUtility.hasValue(igid)){
					dao.saveGroupRecord(entity, igid);
					incidentIds = igDao.getIncidentIdsInGroup(igid);
				}
			}
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long incid : incidentIds){
					dao.saveGroupIncRecord(entity, incid);
				}
			}

			if(null != entity){
				IncidentCostRateStateKindVo scrStateVo = IncidentCostRateStateKindVo.getInstance(entity, true);
				dialogueVo.setResultObject(scrStateVo);
			}

			/*
			 * Build course of action for SHOWMESSAGE 
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_COST_RATE_STATE_KIND");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setMessageVo(new MessageVo("text.incidentCostRates","info.0030",null,MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IncidentCostRateService#getIncidentCostRateState(gov.nwcg.isuite.core.filter.CostRateFilter, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getIncidentCostRateState(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		try {
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			IncidentCostRateStateDao costRateStateDao = (IncidentCostRateStateDao)super.context.getBean("incidentCostRateStateDao");
			// return a single state record
			IncidentCostRateState entity = costRateStateDao.getIncidentCostRateState(filter);
			IncidentCostRateStateVo vo = null;
			if(entity != null) {
				vo = IncidentCostRateStateVo.getInstance(entity, true);
			}

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_COST_RATE_STATE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);

		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		return dialogueVo;
	}

	public DialogueVo getIncidentCostRateStateKinds(CostRateFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) { 
			dialogueVo = new DialogueVo();
		}

		try{
			/*
			 * Init dao
			 */
			IncidentCostRateStateKindDao dao = (IncidentCostRateStateKindDao)context.getBean("incidentCostRateStateKindDao");

			/*
			 * Query for the entities
			 */
			Collection<IncidentCostRateStateKind> entities = dao.getIncidentCostRateStateKinds(filter);
			Collection<IncidentCostRateStateKindVo> vos = IncidentCostRateStateKindVo.getInstances(entities, true);

			/*
			 * Build course of action for NOACTION and set resultObject
			 */
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_COST_RATE_STATE_KINDS");
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
