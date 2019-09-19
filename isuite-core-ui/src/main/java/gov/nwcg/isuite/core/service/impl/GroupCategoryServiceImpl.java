package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.domain.impl.GroupCategoryImpl;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteGroupCategoryRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveCategoryGroupRulesHandler;
import gov.nwcg.isuite.core.service.GroupCategoryService;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.GroupCategoryVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

public class GroupCategoryServiceImpl extends BaseService implements GroupCategoryService {
	private GroupCategoryDao dao;
	
	public GroupCategoryServiceImpl() {
		
	}
	
	public void initialization() {
		dao = (GroupCategoryDao)super.context.getBean("groupCategoryDao");
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GroupCategoryService#deleteGroupCategory(gov.nwcg.isuite.core.vo.GroupCategoryVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteGroupCategory(GroupCategoryVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown Group Category.");

			GroupCategory entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), GroupCategoryImpl.class);
			
			ReferenceDataDeleteGroupCategoryRulesHandler ruleHandler = new ReferenceDataDeleteGroupCategoryRulesHandler(context);
			if(ruleHandler.execute(vo, entity,dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<GroupCategoryVo> newList = new ArrayList<GroupCategoryVo>();
				
				for(GroupCategoryVo groupCategoryVo : gvo.getGroupCategoryVos()) {
					if(groupCategoryVo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(groupCategoryVo);
				}
				
				gvo.setGroupCategoryVos(newList);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_GROUP_CATEGORY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.groupCategory", "info.0028" , new String[]{"Group Category"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GroupCategoryService#geById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo geById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try {
			
		}catch(Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GroupCategoryService#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<GroupCategoryVo> vos = new ArrayList<GroupCategoryVo>();

			vos = dao.getPicklist();
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_GROUP_CATEGORIES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.GroupCategoryService#save(gov.nwcg.isuite.core.vo.GroupCategoryVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(GroupCategoryVo vo, DialogueVo dialogueVo) throws ServiceException, ValidationException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		   

			GroupCategory entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), GroupCategoryImpl.class);
			
			ReferenceDataSaveCategoryGroupRulesHandler ruleHandler = new ReferenceDataSaveCategoryGroupRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				entity = GroupCategoryVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), GroupCategoryImpl.class);
				
				vo=GroupCategoryVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<GroupCategoryVo> groupCategoryVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getGroupCategoryVos();
				GroupCategoryVo groupCategoryVo = null;
				
				for(GroupCategoryVo gvo : groupCategoryVos) {
					if(gvo.getId().compareTo(vo.getId())==0) {
						groupCategoryVo = gvo;
						break;
					}
				}
				
				groupCategoryVos.remove(groupCategoryVo);
				groupCategoryVos.add(vo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_GROUP_CATEGORY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.groupCategory", "info.0030" , null, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);

				dialogueVo.setCourseOfActionVo(coaVo);

				// set the result object with updated vo
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
}
