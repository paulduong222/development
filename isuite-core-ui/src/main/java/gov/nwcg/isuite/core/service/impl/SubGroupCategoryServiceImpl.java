package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.domain.impl.SubGroupCategoryImpl;
import gov.nwcg.isuite.core.persistence.SubGroupCategoryDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteSubGroupCategoryRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveSubGroupCategoryRulesHandler;
import gov.nwcg.isuite.core.service.SubGroupCategoryService;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.SubGroupCategoryVo;
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

public class SubGroupCategoryServiceImpl extends BaseService implements SubGroupCategoryService {
	private SubGroupCategoryDao dao;
	
	public SubGroupCategoryServiceImpl() {
		
	}
	
	public void initialization() {
		dao = (SubGroupCategoryDao)super.context.getBean("subGroupCategoryDao");
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SubGroupCategoryService#deleteSubGroupCategory(gov.nwcg.isuite.core.vo.SubGroupCategoryVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo deleteSubGroupCategory(SubGroupCategoryVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown Sub-Group Category.");

			SubGroupCategory entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), SubGroupCategoryImpl.class);
			
			ReferenceDataDeleteSubGroupCategoryRulesHandler ruleHandler = new ReferenceDataDeleteSubGroupCategoryRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<SubGroupCategoryVo> newList = new ArrayList<SubGroupCategoryVo>();
				
				for(SubGroupCategoryVo svo : gvo.getSubGroupCategoryVos()) {
					if(svo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(svo);
				}
				
				gvo.setSubGroupCategoryVos(newList);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_SUB_GROUP_CATEGORY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.subGroupCategory", "info.0028" , new String[]{"Sub Group Category"}, MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.core.service.SubGroupCategoryService#geById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
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
	 * @see gov.nwcg.isuite.core.service.SubGroupCategoryService#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<SubGroupCategoryVo> vos = new ArrayList<SubGroupCategoryVo>();

			vos = dao.getPicklist();
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_SUB_GROUP_CATEGORIES");
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
	 * @see gov.nwcg.isuite.core.service.SubGroupCategoryService#save(gov.nwcg.isuite.core.vo.SubGroupCategoryVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(SubGroupCategoryVo vo, DialogueVo dialogueVo) throws ServiceException, ValidationException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		   

			SubGroupCategory entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), SubGroupCategoryImpl.class);
			
			ReferenceDataSaveSubGroupCategoryRulesHandler ruleHandler = new ReferenceDataSaveSubGroupCategoryRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				entity = SubGroupCategoryVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), SubGroupCategoryImpl.class);
				
				vo=SubGroupCategoryVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<SubGroupCategoryVo> subGroupCategoryVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getSubGroupCategoryVos();
				SubGroupCategoryVo subGroupCategoryVo = null;
				
				for(SubGroupCategoryVo svo : subGroupCategoryVos) {
					if(svo.getId().compareTo(vo.getId())==0) {
						subGroupCategoryVo = svo;
						break;
					}
				}
				
				subGroupCategoryVos.remove(subGroupCategoryVo);
				subGroupCategoryVos.add(vo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_SUB_GROUP_CATEGORY");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.subGroupCategory", "info.0030" , null, MessageTypeEnum.INFO));
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
