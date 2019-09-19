package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.impl.Sit209Impl;
import gov.nwcg.isuite.core.filter.Sit209Filter;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.rules.ReferenceDataSave209CodeRulesHandler;
import gov.nwcg.isuite.core.service.Sit209Service;
import gov.nwcg.isuite.core.vo.Sit209Vo;
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
 * to add/edit/and otherwise modify a Sit209 record in the system
 * 
 * @author mpoll
 */
public class Sit209ServiceImpl extends BaseService implements Sit209Service {
	private Sit209Dao sit209Dao;

	/**
	 * Default Constructor
	 */
	public Sit209ServiceImpl() {
	}

	public void initialization(){
		sit209Dao = (Sit209Dao)super.context.getBean("sit209Dao");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.Sit209Service#getById(java.lang.Long)
	 */
	@Override
	public Sit209Vo getById(Long id) throws ServiceException {
		
		try {
			if (id == null) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Sit209[id=null]");

			Sit209 entity = sit209Dao.getById(id, Sit209Impl.class);

			if (null == entity) 
				super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Sit209[id=null]");
				
			return Sit209Vo.getInstance(entity, true);         
			
		} catch ( Exception e ) {
			super.handleException(e);
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.Sit209Service#getGrid(gov.nwcg.isuite.core.filter.Sit209Filter)
	 */
	@Override
	public Collection<Sit209Vo> getGrid(Sit209Filter filter) throws ServiceException {
		try {
			return sit209Dao.getGrid(filter);
		} catch ( PersistenceException e ) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.Sit209Service#save(gov.nwcg.isuite.core.vo.Sit209Vo)
	 */
	@Override
	public Sit209Vo save(Sit209Vo vo) throws ServiceException {
		Sit209 entity = null;

		try {

			if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				/*
				 *  Updating existing one
				 */
				entity = sit209Dao.getById(vo.getId(), Sit209Impl.class);

				if (entity == null) 
					super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Sit209["+vo.getId()+"]");
				if (entity.isStandard()) {
					super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
				}

			} else {
				/*
				 * Adding new one, new ones are always non-standard
				 */
				entity = new Sit209Impl();
				vo.setStandard(false);
			}

			if( (null == vo.getId()) || (vo.getId() < 1 ) ){
				if (!sit209Dao.isCodeUnique(vo.getCode())) 
					super.handleException(ErrorEnum._0219_DUPLICATE_REFERENCE_DATA);
			}
			
			entity = Sit209Vo.toEntity(entity, vo, true);
			
			sit209Dao.save(entity);
			
			//entity=sit209Dao.getById(entity.getId(), Sit209Impl.class);
				
			return Sit209Vo.getInstance(entity, true);

		} catch(ServiceException se){
			throw se;
		}catch ( Exception e ) {
			super.handleException(e);
		}
		return null;
	}

	@Override
	public DialogueVo save(DialogueVo dialogueVo, Sit209Vo vo) throws ServiceException {
		try{
			if(null == dialogueVo) {
				dialogueVo=new DialogueVo();
			}

			ReferenceDataSave209CodeRulesHandler rulesHandler = new ReferenceDataSave209CodeRulesHandler(context);

			Sit209 entity = null;
			if(LongUtility.hasValue(vo.getId())){
				entity = sit209Dao.getById(vo.getId(), Sit209Impl.class);
			}
			
			if(rulesHandler.execute(vo, entity,dialogueVo)==ReferenceDataSave209CodeRulesHandler._OK){

				entity = Sit209Vo.toEntity(entity, vo, true);

				sit209Dao.save(entity);
				sit209Dao.flushAndEvict(entity);

				entity=sit209Dao.getById(entity.getId(), Sit209Impl.class);

				vo = Sit209Vo.getInstance(entity, true);

				// execute follow up actions
				rulesHandler.executeProcessedActions(vo, entity,dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(vo);
			}


		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.Sit209Service#delete(java.util.Collection)
	 */
	@Override
	public void delete(Collection<Long> sit209Ids) throws ServiceException {
		try {
			if ((sit209Ids == null) || (sit209Ids.size() < 1)) {
				throw new ServiceException("invalid or missing Ids");
			}
			for (Long sit209Id : sit209Ids) {
				Sit209 persistable = sit209Dao.getById(sit209Id, Sit209Vo.class);
				if (persistable != null) {
					if (!persistable.isStandard()) {
						sit209Dao.delete(persistable);
					} else {
						super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
					}
				} else {
					throw new ServiceException("cannot find record with id: " + sit209Id);
				}
			}
		} catch (ServiceException se){
			throw se;
		} catch ( Exception e ) {
			super.handleException(e);
		}
	}

}
