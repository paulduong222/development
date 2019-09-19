package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteAgencyCodeRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveAgencyCodeRulesHandler;
import gov.nwcg.isuite.core.service.AgencyService;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
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

public class AgencyServiceImpl extends BaseService implements AgencyService {

   private AgencyDao agencyDao;
   
   public AgencyServiceImpl() {
	   
   }
   
   public void initialization(){
	   agencyDao = (AgencyDao)super.context.getBean("agencyDao");
   }
   
   
    /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.AgencyService#delete(java.util.Collection)
    */
   	@Override
	public void delete(Collection<Long> agencyIds) throws ServiceException {
   		try {
            if ((agencyIds == null) || (agencyIds.size() < 1)) {
               throw new ServiceException("invalid or missing Ids");
            }
            for (Long agencyId : agencyIds) {
               Agency persistable = agencyDao.getById(agencyId, AgencyVo.class);
               if (persistable != null) {
            	   agencyDao.delete(persistable);
               } 
               else 
               {
                  throw new ServiceException("cannot find record with id: " + agencyId);
               }
            }
         } catch (ServiceException se){
       	  throw se;
         } catch ( Exception e ) {
       	  super.handleException(e);
         }
	}
	
   	/*
   	 * (non-Javadoc)
   	 * @see gov.nwcg.isuite.core.service.AgencyService#delete(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.AgencyVo)
   	 */
   	public DialogueVo delete(DialogueVo dialogueVo, AgencyVo agencyVo) throws ServiceException {
   		//TODO:  Should we be receiving a collection of AgencyVos?
   		
   		try{
			if(null == dialogueVo)dialogueVo=new DialogueVo();

			ReferenceDataDeleteAgencyCodeRulesHandler rulesHandler = new ReferenceDataDeleteAgencyCodeRulesHandler(context);

			Agency agencyEntity = null;
			agencyEntity = agencyDao.getById(agencyVo.getId(), AgencyImpl.class);

			agencyVo = AgencyVo.getInstance(agencyEntity, true);

			if(rulesHandler.execute(agencyVo, agencyEntity,dialogueVo) == ReferenceDataDeleteAgencyCodeRulesHandler._OK) {
				agencyDao.delete(agencyEntity);

				// execute follow up actions
				rulesHandler.executeProcessedActions(agencyVo, agencyEntity,dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo(
						"text.incident", 
						"text.affirmDeleteIncident", 
						new String[]{agencyVo.getAgencyNm()}, 
						MessageTypeEnum.INFO));
				
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(agencyVo.getId());
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
   	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyService#getById(java.lang.Long)
	 */
	@Override
	public AgencyVo getById(Long id) throws ServiceException {
		try {
	        if (id == null) {
	           throw new ServiceException("id cannot be null");
	        }
	        
	        Agency agency = agencyDao.getById(id, AgencyImpl.class);
	        if (agency != null) {
	           return AgencyVo.getInstance(agency, true);         
	        }
	        throw new ServiceException("Could not find Agency with Id: " + id);
	     } catch ( PersistenceException e ) {
	        throw new ServiceException(e);
	     } catch ( Exception e ) {
	        throw new ServiceException(e);
	     }
	}
	
	/* (non-Javadoc)
	* @see gov.nwcg.isuite.core.service.AgencyService#getGrid(gov.nwcg.isuite.core.filter.AgencyFilter)
	*/
	@Override //TODO:  getGrid methods should return GridVo's which in turn populate a Flex DataGrid.  -dbudge
	public Collection<AgencyVo> getGrid(AgencyFilter agencyFilter) throws ServiceException 
	{
		GlobalCacheVo globalCacheVo = (GlobalCacheVo) super.context.getBean("globalCacheVo");
	      if(agencyFilter == null) {
	         return globalCacheVo.getAgencyVos();
	      }
		try {
	         return agencyDao.getGrid(agencyFilter);
	      } catch ( PersistenceException e ) {
	         throw new ServiceException(e);
	      }
	}
	
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.impl.AgencyService#getAgencies(gov.nwcg.isuite.core.filter.AgencyFilter)
    */
   public Collection<AgencyVo> getAgencies(AgencyFilter agencyFilter) throws ServiceException {
      GlobalCacheVo globalCacheVo = (GlobalCacheVo) super.context.getBean("globalCacheVo");
      if(agencyFilter == null) {
         return globalCacheVo.getAgencyVos();
      }
      try {
         return agencyDao.getAgencies(agencyFilter);
      }
      catch ( PersistenceException e ) {
         super.handleException(e); 
      }
      return null;
   }


	
	
	
	/* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.AgencyService#save(gov.nwcg.isuite.core.vo.AgencyVo)
    */
	@Override
	public void save(AgencyVo vo) throws ServiceException {
		Agency entity = null;
		   
		   try {
			   
			   if( (null != vo.getId()) && (vo.getId().compareTo(0L) > 0)) {
				   /*
				    *  Updating existing one
				    */
				   entity = agencyDao.getById(vo.getId(), AgencyImpl.class);
				   
				   if (entity == null) 
					   super.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"Agency["+vo.getId()+"]");
			   } 
			   else 
			   {
				   /*
				    * Adding new one, new ones are always non-standard
				    */
		            entity = AgencyVo.toEntity(entity,vo, true);
		       }
			   
			   agencyDao.save(entity);
	       } catch(ServiceException se){
			   throw se;
		   }catch ( Exception e ) {
			   super.handleException(e);
		   }
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.AgencyService#save(gov.nwcg.isuite.core.vo.dialogue.DialogueVo, gov.nwcg.isuite.core.vo.AgencyVo)
	 */
	public DialogueVo save(DialogueVo dialogueVo, AgencyVo agencyVo) throws ServiceException {
		try{
			if(null == dialogueVo) {
				dialogueVo=new DialogueVo();
			}

			ReferenceDataSaveAgencyCodeRulesHandler rulesHandler = new ReferenceDataSaveAgencyCodeRulesHandler(context);

			Agency entity = null;
			if(LongUtility.hasValue(agencyVo.getId())){
				entity = agencyDao.getById(agencyVo.getId(), AgencyImpl.class);
			}
			
			if(rulesHandler.execute(agencyVo, entity,dialogueVo)==ReferenceDataSaveAgencyCodeRulesHandler._OK){

				entity = AgencyVo.toEntity(entity, agencyVo, true);

				agencyDao.save(entity);
				agencyDao.flushAndEvict(entity);

				entity=agencyDao.getById(entity.getId(), AgencyImpl.class);

				agencyVo = AgencyVo.getInstance(entity, true);

				// execute follow up actions
				rulesHandler.executeProcessedActions(agencyVo, entity,dialogueVo);

				// build coa
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("COMPLETE");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.incident", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				dialogueVo.setResultObject(agencyVo);
			}


		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
}
