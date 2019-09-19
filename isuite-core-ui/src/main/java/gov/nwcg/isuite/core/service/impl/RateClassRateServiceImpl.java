package gov.nwcg.isuite.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.impl.RateClassRateImpl;
import gov.nwcg.isuite.core.persistence.RateClassRateDao;
import gov.nwcg.isuite.core.rules.ReferenceDataDeleteADRateRulesHandler;
import gov.nwcg.isuite.core.rules.ReferenceDataSaveADRateRulesHandler;
import gov.nwcg.isuite.core.service.RateClassRateService;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

public class RateClassRateServiceImpl extends BaseService implements RateClassRateService {
	private RateClassRateDao dao;
	
	public RateClassRateServiceImpl() {
		super();
	}
	
	public void initialization() {
		dao = (RateClassRateDao)super.context.getBean("rateClassRateDao");
	}
	
	public DialogueVo deleteRateClassRate(RateClassRateVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			if((null == vo) || !LongUtility.hasValue(vo.getId()))
				throw new ServiceException("Unable to delete unknown AD Rate.");
			
			RateClassRate entity = null;
			if(LongUtility.hasValue(vo.getId()))
				entity = dao.getById(vo.getId(), RateClassRateImpl.class);
			
			ReferenceDataDeleteADRateRulesHandler ruleHandler = new ReferenceDataDeleteADRateRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){
				
				dao.delete(entity);
				
				//Remove from GlobalCacheVo
				GlobalCacheVo gvo = ((GlobalCacheVo)context.getBean("globalCacheVo"));
				Collection<RateClassRateVo> newList = new ArrayList<RateClassRateVo>();
				
				for(RateClassRateVo rvo : gvo.getRateClassRateVos()) {
					if(rvo.getId().compareTo(vo.getId())==0) {
						//skip this one since we are removing it
					}else
						newList.add(rvo);
				}
				
				gvo.setRateClassRateVos(newList);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("DELETE_AD_CLASS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.adClass", "info.0028" , new String[]{"AD Class"}, MessageTypeEnum.INFO));
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
	 * @see gov.nwcg.isuite.core.service.RateClassRateService#save(gov.nwcg.isuite.core.vo.RateClassRateVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo save(RateClassRateVo vo , DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{		
			
			RateClassRate entity = null;
			if(LongUtility.hasValue(vo.getId())) {
				entity = dao.getById(vo.getId(), RateClassRateImpl.class);
			}else {
				String defaultRateClassId = super.getSystemParamValue(SystemParameterTypeEnum.DEFAULT_RATE_CLASS_ID);
				vo.setRateClassId(TypeConverter.convertToLong(defaultRateClassId));
			}
			
			ReferenceDataSaveADRateRulesHandler ruleHandler = new ReferenceDataSaveADRateRulesHandler(context);
			if(ruleHandler.execute(vo, entity, dialogueVo)==AbstractRule._OK){

				entity = RateClassRateVo.toEntity(entity, vo, true);
				dao.save(entity);
				dao.flushAndEvict(entity);
				entity = dao.getById(entity.getId(), RateClassRateImpl.class);
				
				vo=RateClassRateVo.getInstance(entity, true);
				
				//update GlobalCacheVo
				Collection<RateClassRateVo> rateClassRateVos = ((GlobalCacheVo)context.getBean("globalCacheVo")).getRateClassRateVos();
				RateClassRateVo rateClassRateVo = null;
				
				for(RateClassRateVo rvo : rateClassRateVos) {
					if(rvo.getId().compareTo(vo.getId())==0) {
						rateClassRateVo = rvo;
						break;
					}
				}
				
				rateClassRateVos.remove(rateClassRateVo);
				rateClassRateVos.add(vo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_AD_CLASS");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.adClass", "info.0030" , null, MessageTypeEnum.INFO));
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

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RateClassRateService#getById(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getById(Long id, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
	
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.RateClassRateService#getGrid(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getGrid(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			Collection<RateClassRateVo> vos = new ArrayList<RateClassRateVo>();
			
			String defaultRateClassId = super.getSystemParamValue(SystemParameterTypeEnum.DEFAULT_RATE_CLASS_ID);

			vos = dao.getPicklist(TypeConverter.convertToLong(defaultRateClassId));
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_AD_RATES");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

}
