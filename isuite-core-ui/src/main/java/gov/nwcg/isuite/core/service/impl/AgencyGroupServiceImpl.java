package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.domain.impl.AgencyGroupImpl;
import gov.nwcg.isuite.core.filter.AgencyGroupFilter;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveAgencyGroupRulesHandler;
import gov.nwcg.isuite.core.service.AgencyGroupService;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

/**
 * This class allows a user
 * to add/edit/and otherwise modify a AgencyGroup record in the system
 * 
 * @author mpoll
 */
public class AgencyGroupServiceImpl extends BaseService implements AgencyGroupService {
	private AgencyGroupDao agencyGroupDao;

	/**
	 * Default Constructor
	 */
	public AgencyGroupServiceImpl() {
	}

	public void initialization(){
		agencyGroupDao = (AgencyGroupDao)super.context.getBean("agencyGroupDao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService#getById(java.lang.Long)
	 */
	@Override
	public AgencyGroupVo getById(Long id) throws ServiceException {
		
		try {
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"AgencyGroup[id=null]");

			AgencyGroup entity = agencyGroupDao.getById(id, AgencyGroupImpl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"AgencyGroup[id=null]");
				
			return AgencyGroupVo.getInstance(entity, true);         
			
		} catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService#getGrid(gov.nwcg.isuite.core.filter.AgencyGroupFilter)
	 */
	@Override
	public Collection<AgencyGroupVo> getGrid(AgencyGroupFilter filter) throws ServiceException {
		try {
			return agencyGroupDao.getGrid(filter);
		} catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService#save(gov.nwcg.isuite.core.vo.AgencyGroupVo)
	 */
	@Override
	public AgencyGroupVo save(AgencyGroupVo vo) throws ServiceException {
		AgencyGroup entity = null;

		try {

			if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				/*
				 *  Updating existing one
				 */
				entity = agencyGroupDao.getById(vo.getId(), AgencyGroupImpl.class);

				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"AgencyGroup["+vo.getId()+"]");
				if (entity.isStandard()) {
					super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
				}

			} else {
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new AgencyGroupImpl();
				vo.setStandard(false);
			}

			if( (null == vo.getId()) || (vo.getId() < 1 ) ){
				if (!agencyGroupDao.isCodeUnique(vo.getCode())) 
					super.handleException(ErrorEnum._0219_DUPLICATE_REFERENCE_DATA);
			}
			
			entity = AgencyGroupVo.toEntity(entity, vo, true);
			
			agencyGroupDao.save(entity);
			
			//entity=agencyGroupDao.getById(entity.getId(), AgencyGroupImpl.class);
				
			return AgencyGroupVo.getInstance(entity, true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	public DialogueVo save(DialogueVo dialogueVo, AgencyGroupVo agencyGroupVo) throws ServiceException {
		try{
			if(null == dialogueVo) {
				dialogueVo=new DialogueVo();
			}

			ReferenceDataSaveAgencyGroupRulesHandler rulesHandler = new ReferenceDataSaveAgencyGroupRulesHandler(context);

			AgencyGroup agencyGroupEntity = null;
			if(LongUtility.hasValue(agencyGroupVo.getId())){
				agencyGroupEntity = agencyGroupDao.getById(agencyGroupVo.getId(), AgencyGroupImpl.class);
			}
			
			if(rulesHandler.execute(agencyGroupVo, agencyGroupEntity,dialogueVo)==ReferenceDataSaveAgencyGroupRulesHandler._OK){

				agencyGroupEntity = AgencyGroupVo.toEntity(agencyGroupEntity, agencyGroupVo, true);

				agencyGroupDao.save(agencyGroupEntity);
				agencyGroupDao.flushAndEvict(agencyGroupEntity);

				agencyGroupEntity=agencyGroupDao.getById(agencyGroupEntity.getId(), AgencyGroupImpl.class);

				agencyGroupVo = AgencyGroupVo.getInstance(agencyGroupEntity, true);

				// execute follow up actions
				rulesHandler.executeProcessedActions(agencyGroupVo, agencyGroupEntity,dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(agencyGroupVo);
			}


		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyGroupService#delete(java.util.Collection)
	 */
	@Override
	public void delete(Collection<Long> agencyGroupIds) throws ServiceException {
		try {
			if ((agencyGroupIds == null) || (agencyGroupIds.size() < 1)) {
				throw new ServiceException("invalid or missing Ids");
			}
			for (Long agencyGroupId : agencyGroupIds) {
				AgencyGroup persistable = agencyGroupDao.getById(agencyGroupId, AgencyGroupVo.class);
				if (persistable != null) {
					if (!persistable.isStandard()) {
						agencyGroupDao.delete(persistable);
					} else {
						super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
					}
				} else {
					throw new ServiceException("cannot find record with id: " + agencyGroupId);
				}
			}
		} catch (ServiceException se){
			throw se;
		} catch ( Exception e ) {
			super.handleException(e);
		}
	}

}
