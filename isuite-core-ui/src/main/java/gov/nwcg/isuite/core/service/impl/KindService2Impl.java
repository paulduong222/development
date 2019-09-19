package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.impl.KindFilterImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteItemCodeRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveItemCodeRulesHandler;
import gov.nwcg.isuite.core.service.KindService2;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class KindService2Impl extends BaseService implements KindService2 {
	private KindDao dao;

	public KindService2Impl(){

	}

	public void initialization(){
		dao = (KindDao)super.context.getBean("kindDao");
	}

	public DialogueVo deleteKind(KindVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown item code.");
			
			Kind entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), KindImpl.class);
			
			ReferenceDataDeleteItemCodeRulesHandler ruleHandler = new ReferenceDataDeleteItemCodeRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<KindVo> newList = new ArrayList<KindVo>();
				
				for(KindVo kvo : gvo.getKindVos()) {
					if(kvo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(kvo);
				}
				
				gvo.setKindVos(newList);
				((GlobalCacheVo)context.getBean("globalCacheVo")).setKindVos(newList);				
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_KIND");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.itemCode", "info.0028" , new String[]{"Item Code"}, MessageTypeEnum.INFO));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setResultObject(vo);
				dialogueVo.setCourseOfActionVo(coaVo);
			}
			

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo save(KindVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{	
			
			Kind entity = null;
			Boolean bExitingItemCode=false;
			if(LongUtility.hasValue(vo.getId())){
				entity = dao.getById(vo.getId(), KindImpl.class);
				bExitingItemCode=true;
			}
			
			ReferenceDataSaveItemCodeRulesHandler ruleHandler = new ReferenceDataSaveItemCodeRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				
				entity = KindVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), KindImpl.class);
				vo=KindVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<KindVo> kindVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getKindVos();

				if(!bExitingItemCode){
					//add records to isw_inccost_rate_kind and isw_inccost_rate_state_kind 
					if(!entity.isStandard()) {
						dao.executeInsertIncidentCostRateKind(entity.getId(), entity.getRequestCategory().getCode(), entity.getIncidentId());
						dao.executeInsertIncidentCostRateStateKind(entity.getId(), entity.getRequestCategory().getCode(), entity.getIncidentId());
					}
					
					// add to cache
					kindVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setKindVos(kindVos);			
				}else{
					KindVo kindVo = null;
					
					for(KindVo kvo : kindVos) {
						if(kvo.getId().compareTo(vo.getId())==0) {
							kindVo = kvo;
							break;
						}
					}
					
					kindVos.remove(kindVo);
					kindVos.add(vo);
					((GlobalCacheVo)context.getBean("globalCacheVo")).setKindVos(kindVos);				
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_KIND");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.itemCode", "info.generic" , 
						new String[]{"The item code was saved.  Cost rates must be added in the Cost Rate area."}, 
						MessageTypeEnum.INFO));
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

	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			
			Collection<KindVo> vos = new ArrayList<KindVo>();
			
			vos = dao.getPicklist(new KindFilterImpl());
			
			((GlobalCacheVo)context.getBean("globalCacheVo")).setKindVos(vos);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_KINDS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getGridIncidentorGroup(Long incidentId, Long incidentGroupId, Boolean incidentOnly, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(incidentId) && BooleanUtility.isTrue(incidentOnly)){
				incidentIds.add(incidentId);
			}
			if(LongUtility.hasValue(incidentId) && BooleanUtility.isFalse(incidentOnly)){
				// determine if incident is part of a group
				IncidentDao idao = (IncidentDao)context.getBean("incidentDao");
				Long incGroupId=idao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(incGroupId)){
					incidentId=null;
					incidentGroupId=incGroupId;
				}
			}
			if(LongUtility.hasValue(incidentGroupId)){
				// get all incidentids for group
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentIds=igdao.getIncidentIdsInGroup(incidentGroupId);
			}
			
			Collection<KindVo> vos = this.dao.getStandardAndNonStandard(incidentIds, incidentGroupId);
			Collection<KindVo> newvos = new ArrayList<KindVo>();
			for(KindVo v : vos){
				if(BooleanUtility.isTrue(v.getActive())){
					newvos.add(v);
				}
			}

			/*
			Collection<KindVo> vos = new ArrayList<KindVo>();
			for(KindVo vo : super.getGlobalCacheVo().getKindVos()){
				if(BooleanUtility.isTrue(vo.getActive())){
					if(BooleanUtility.isTrue(vo.getStandard())){
						vos.add(vo);
					}else{
						if(LongUtility.hasValue(incidentId) && null != vo.getIncidentVo()){
							if(incidentId.compareTo(vo.getIncidentVo().getId())==0)
								vos.add(vo);
						}
			
						if(LongUtility.hasValue(incidentGroupId) && null != vo.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(vo.getIncidentVo().getId())==0)
									vos.add(vo);
							}
						}
					}
				}
			}
			*/
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_KINDS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(newvos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
}
