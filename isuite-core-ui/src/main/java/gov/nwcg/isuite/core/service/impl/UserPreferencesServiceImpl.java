package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.domain.PasswordHistory;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.PasswordHistoryImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.core.filter.impl.GridColumnUserFilterImpl;
import gov.nwcg.isuite.core.persistence.GridColumnUserDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.PasswordHistoryDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.rules.ChangePasswordRulesHandler;
import gov.nwcg.isuite.core.rules.UserSaveRulesHandler;
import gov.nwcg.isuite.core.service.UserPreferencesService;
import gov.nwcg.isuite.core.vo.GridColumnUserVo;
import gov.nwcg.isuite.core.vo.UserGridVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.GridNameEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class UserPreferencesServiceImpl extends BaseService implements UserPreferencesService {
   private GridColumnUserDao gridColumnUserDao;
   
   protected UserDao userDao;
   
   public UserPreferencesServiceImpl(){
	   
   }
   
   public void initialization(){
	   gridColumnUserDao = (GridColumnUserDao)super.context.getBean("gridColumnUserDao");
	   userDao = (UserDao)super.context.getBean("userDao");
   }

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserPreferencesService#getUserGridColumns(gov.nwcg.isuite.core.filter.GridColumnUserFilter)
	 */
   public Collection<GridColumnUserVo> getUserGridColumns(GridColumnUserFilter filter) throws ServiceException {

	   try{
		   Collection<GridColumnUser> entities = gridColumnUserDao.getGridColumns(filter);

		   if(null!=entities){
			   return GridColumnUserVo.getInstances(entities, true);
		   }

	   }catch(Exception e){
		   super.handleException(e);
	   }

	   return null;
   }

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserPreferencesService#save(java.util.Collection)
	 */
	//public void saveAll(Collection<GridColumnUserVo> vos) throws ServiceException {
   public Collection<GridColumnUserVo> saveAll(Collection<GridColumnUserVo> vos) throws ServiceException {
   
		if(null != vos){
			try{
				
				Collection<GridColumnUser> entities = GridColumnUserVo.toEntityList(vos, true);
				
				gridColumnUserDao.saveAll(entities);
			}catch(PersistenceException pe){
				throw new ServiceException(pe);
			}catch(Exception e){
				throw new ServiceException(e);
			}
		}
		return vos;
	}

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.service.UserPreferencesService#saveAll2(java.util.Collection, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
    */
   public DialogueVo saveAll2(Collection<GridColumnUserVo> vos,DialogueVo dialogueVo) throws ServiceException {
	   if(null==dialogueVo)dialogueVo = new DialogueVo();
	   
	   try{
		
			Collection<GridColumnUser> entities = GridColumnUserVo.toEntityList(vos, true);
			
			gridColumnUserDao.saveAll(entities);
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE_USER_COLUMNS");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.user", "info.generic" , new String[]{"The User grid column changes were saved."}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
		   
	   }catch(Exception e){
		   super.dialogueException(dialogueVo, e);
	   }
	   
	   return dialogueVo;
   }
   
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.UserPreferencesService#restoreDefaults(java.lang.String, java.lang.Long)
	 */
   public Collection<GridColumnUserVo> restoreDefaults(String gridName, Long userId) throws ServiceException {
      GridColumnUserFilterImpl gcuFilter = new GridColumnUserFilterImpl();
      gcuFilter.setUserId(userId);
      
      if(gridName.equals(GridNameEnum.RESOURCE.name())) {
        gcuFilter.setGridName(GridNameEnum.RESOURCE);
      }
      if(gridName.equals(GridNameEnum.OTHERCOST.name())) {
        gcuFilter.setGridName(GridNameEnum.OTHERCOST);
      }
      if(gridName.equals(GridNameEnum.RESOURCECOST.name())) {
        gcuFilter.setGridName(GridNameEnum.RESOURCECOST);
      }
      if(gridName.equals(GridNameEnum.RESOURCEDEMOB.name())) {
        gcuFilter.setGridName(GridNameEnum.RESOURCEDEMOB);
      }
      if(gridName.equals(GridNameEnum.RESOURCETIME.name())) {
          gcuFilter.setGridName(GridNameEnum.RESOURCETIME);
       }
      
      try {
         this.gridColumnUserDao.restoreDefaults(gcuFilter.getGridName(), userId);
      }
      catch (PersistenceException e) {
         super.handleException(e);
      }
      Collection<GridColumnUserVo> gcUserVos = this.getUserGridColumns(gcuFilter);
      for(GridColumnUserVo vo : gcUserVos) {
         if(vo.getGridColumnVo().getIsDefault()) {
            vo.setVisible(true);
//            vo.setPosition(vo.getGridColumnVo().getDefaultPosition());
         } else {
            vo.setVisible(false);
         }
      }
//      gcUserVos = this.reOrderColumns(gcUserVos);
      return gcUserVos;
   }
   
	@Override
	public DialogueVo updateShowDataSavedMsg(Long userId, Boolean showDataSavedMsg, DialogueVo dialogueVo) throws ServiceException {
		try{
			if(dialogueVo == null) {
				dialogueVo = new DialogueVo();
			}

			User userEntity = null;
			//get User from DB
			userEntity = userDao.getById(userId, UserImpl.class);
			userEntity.setShowDataSavedMsg(StringBooleanEnum.toEnumValue(showDataSavedMsg));
			userDao.save(userEntity);
     
			// build coa
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("CHANGE_SHOWDATASAVEDMSG_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(
						new MessageVo(
								"text.changeUserPreference",
								"info.generic", 
								new String[] {"Data saved message is turned " + (showDataSavedMsg == true ? "On" : "Off")}, 
								MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				
				dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		  
		return dialogueVo;
		
	}
   
//   private Collection<GridColumnUserVo> reOrderColumns(Collection<GridColumnUserVo> gridColumnUserVos) throws ServiceException {
//	   ArrayList<GridColumnUserVo> gcuvos = new ArrayList<GridColumnUserVo>();
//	   this.initializeCollection(gcuvos, gridColumnUserVos.size());
//	   for(GridColumnUserVo gcuvo : gridColumnUserVos) {
//		   try{	
//			   gcuvos.remove(gcuvo.getPosition() - 1);
//			   gcuvos.add(gcuvo.getPosition() - 1, gcuvo);
//		   } catch (Exception e) {
//			   super.handleException(e);
//		   }
//	   }
//	   return gcuvos;
//   }
   
//   private void initializeCollection(Collection<GridColumnUserVo> gcuVos, int numColumns) {
//	   for(int i = 0; i < numColumns - 1; i++) {
//		   gcuVos.add(new GridColumnUserVo());
//	   }
//   }
}
