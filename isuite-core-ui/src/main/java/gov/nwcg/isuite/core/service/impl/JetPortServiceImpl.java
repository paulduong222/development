package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.impl.JetPortImpl;
import gov.nwcg.isuite.core.filter.JetPortFilter;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveJetPortRulesHandler;
import gov.nwcg.isuite.core.service.JetPortService;
import gov.nwcg.isuite.core.vo.JetPortVo;
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
 * to add/edit/and otherwise modify a JetPort record in the system
 * 
 * @author mpoll
 */
public class JetPortServiceImpl extends BaseService implements JetPortService {
   private JetPortDao jetPortDao;

   /**
    * Default Constructor
    */
   public JetPortServiceImpl() {
   }

   public void initialization(){
      jetPortDao = (JetPortDao)super.context.getBean("jetPortDao");
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.JetPortService#getById(java.lang.Long)
    */
   @Override
   public JetPortVo getById(Long id) throws ServiceException {
      try {
         if (id == null) {
            throw new ServiceException("id cannot be null");
         }
         
         JetPort jetPort = jetPortDao.getById(id, JetPortImpl.class);
         if (jetPort != null) {
            return JetPortVo.getInstance(jetPort, true);         
         }
         throw new ServiceException("Could not find JetPort with Id: " + id);
      } catch ( PersistenceException e ) {
         throw new ServiceException(e);
      } catch ( Exception e ) {
         throw new ServiceException(e);
      }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.JetPortService#getGrid(gov.nwcg.isuite.core.filter.JetPortFilter)
    */
   @Override
   public Collection<JetPortVo> getGrid(JetPortFilter filter) throws ServiceException {
      try {
         return jetPortDao.getGrid(filter);
      } catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.JetPortService#save(gov.nwcg.isuite.core.vo.JetPortVo)
    */
   @Override
   public void save(JetPortVo vo) throws ServiceException {
	   JetPort entity = null;
	   
	   try {
		   
		   if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
			   /*
			    *  Updating existing one
			    */
			   entity = jetPortDao.getById(vo.getId(), JetPortImpl.class);
			   
			   if (entity == null) 
				   super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"JetPort["+vo.getId()+"]");
			   if (entity.isStandard()) {
	            super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
			   }
			   
		   } else {
			   /*
			    * Adding new one, new ones are always non-standard
			    */
            entity = new JetPortImpl();
            vo.setStandard(false);
		   }
		   
         if (jetPortDao.isCodeUnique(vo.getCode())) {
            entity = JetPortVo.toEntity(entity, vo, true);
            jetPortDao.save(entity);
         } else {
            super.handleException(ErrorEnum._0219_DUPLICATE_REFERENCE_DATA);
         }
         
	   } catch(ServiceException se){
		   throw se;
	   }catch ( Exception e ) {
		   super.handleException(e);
	   }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.JetPortService#save(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.JetPortVo)
    */
   public DialogueVo save(DialogueVo dialogueVo, JetPortVo jetPortVo) throws ServiceException {
	   try{
			if(null == dialogueVo) {
				dialogueVo=new DialogueVo();
			}

			ReferenceDataSaveJetPortRulesHandler rulesHandler = new ReferenceDataSaveJetPortRulesHandler(context);

			JetPort jetPortEntity = null;
			if(LongUtility.hasValue(jetPortVo.getId())){
				jetPortEntity = jetPortDao.getById(jetPortVo.getId(), JetPortImpl.class);
			}
			
			if(rulesHandler.execute(jetPortVo, jetPortEntity,dialogueVo)==ReferenceDataSaveJetPortRulesHandler._OK){

				jetPortEntity = JetPortVo.toEntity(jetPortEntity, jetPortVo, true);

				jetPortDao.save(jetPortEntity);
				jetPortDao.flushAndEvict(jetPortEntity);

				jetPortEntity=jetPortDao.getById(jetPortEntity.getId(), JetPortImpl.class);

				jetPortVo = JetPortVo.getInstance(jetPortEntity, true);

				// execute follow up actions
				rulesHandler.executeProcessedActions(jetPortVo, jetPortEntity,dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(jetPortVo);
			}


		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.JetPortService#delete(java.util.Collection)
    */
   @Override
   public void delete(Collection<Long> jetPortIds) throws ServiceException {
      try {
         if ((jetPortIds == null) || (jetPortIds.size() < 1)) {
            throw new ServiceException("invalid or missing Ids");
         }
         for (Long jetPortId : jetPortIds) {
            JetPort persistable = jetPortDao.getById(jetPortId, JetPortVo.class);
            if (persistable != null) {
               if (!persistable.isStandard()) {
                  jetPortDao.delete(persistable);
               } else {
                  super.handleException(ErrorEnum.CANNOT_EDIT_STANDARD_RECORDS);
               }
            } else {
               throw new ServiceException("cannot find record with id: " + jetPortId);
            }
         }
      } catch ( PersistenceException e ) {
         throw new ServiceException(e);
      }
   }

}
