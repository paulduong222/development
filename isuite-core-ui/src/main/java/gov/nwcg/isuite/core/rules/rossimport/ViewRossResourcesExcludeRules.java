package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class ViewRossResourcesExcludeRules extends AbstractRossImportRule implements IWizardRule {
	private static final String _RULE_NAME=RossImportProcessRuleFactory.RuleEnum.VIEW_ROSS_RESOURCES_EXCLUDE.name();
	 
	public ViewRossResourcesExcludeRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {

		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		Collection<RossImportProcessResourceVo> vos = ripVo.getRossImportProcessResourceVos();//Dao.getValidRossResources(this.rxfVo.getId());
		Collection<RossImportProcessResourceVo> exVos = ripVo.getExcludeResourceWizardVo().getExcludedRossImportProcessResourceVos();
		
		Collection<Long> excludeRxfdIds = new ArrayList<Long>();
		
		for(RossImportProcessDataErrorVo ripDataErrorVo : ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()){
			if(ripDataErrorVo.getExcludeFromImport()){
				excludeRxfdIds.add(ripDataErrorVo.getRossXmlFileDataId());
			}
		}

		HashMap<Long,RossImportProcessResourceVo> map = RossImportProcessResourceVo.toHashMapRxfdId(vos);

		for(Long id : excludeRxfdIds){
			if(map.containsKey(id)){
				RossImportProcessResourceVo rvo = map.get(id);
				rvo.setExcludeResource(true);
				map.put(id, rvo);
			}
		}
		

		
		for(RossImportProcessResourceVo ripresvo : vos){
			if(map.containsKey(ripresvo.getRossXmlFileDataId())){
				boolean isExcluded=false;
				for(RossImportProcessResourceVo exvo : exVos){
					if(exvo.getRossXmlFileDataId().compareTo(ripresvo.getRossXmlFileDataId())==0){
						isExcluded=true;
					}
				}
				ripresvo.setExcludeResource(isExcluded);
				map.put(ripresvo.getRossXmlFileDataId(),ripresvo);
			}
		}
		
		vos = map.values();
		
		((RossImportProcessVo)dialogueVo.getResultObject())
			.setRossImportProcessResourceVos(vos);

		/*
		// create a navigate courseofaction
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(null != coaVo){
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(coaVo);
		}else{
			return _FAIL;
		}
		*/
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if( (isCourseOfActionComplete(dialogueVo, _RULE_NAME))
					|| super.isCourseOfActionNoAction(dialogueVo, _RULE_NAME))
				return _OK;
				
			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
	
				// add to the processed collection
				dialogueVo.getCourseOfActionVo().setIsComplete(true);
				dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());


				updateRossImportProcessVo(dialogueVo);
				return _OK;
			}
			
			if(buildWizardData(dialogueVo)==_FAIL)
				return _FAIL;
			
		}catch(Exception e){
			throw new Exception(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int buildWizardData(DialogueVo dialogueVo) throws Exception {
		RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");
		
		//Collection<RossImportProcessResourceVo> vos = ripDao.getValidRossResources(this.rxfVo.getId());
		
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		Collection<RossImportProcessResourceVo> vos = ripVo.getRossImportProcessResourceVos();//Dao.getValidRossResources(this.rxfVo.getId());
		//ripVo.setRossImportProcessResourceVos(vos);

		Collection<Long> excludeRxfdIds = new ArrayList<Long>();
		
		for(RossImportProcessDataErrorVo ripDataErrorVo : ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()){
			if(ripDataErrorVo.getExcludeFromImport()){
				excludeRxfdIds.add(ripDataErrorVo.getRossXmlFileDataId());
			}
		}

		HashMap<Long,RossImportProcessResourceVo> map = RossImportProcessResourceVo.toHashMapRxfdId(vos);

		for(Long id : excludeRxfdIds){
			if(map.containsKey(id)){
				RossImportProcessResourceVo rvo = map.get(id);
				rvo.setExcludeResource(true);
				map.put(id, rvo);
			}
		}
		
		vos = map.values();
		
		((RossImportProcessVo)dialogueVo.getResultObject())
			.setRossImportProcessResourceVos(vos);
		
		// create a navigate courseofaction
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_RULE_NAME);
		coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
		coaVo.setNavigateVo(
				buildNavigateVo(
						RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
		dialogueVo.setCourseOfActionVo(coaVo);
	
		return _FAIL;
	}
	
	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	private void updateRossImportProcessVo(DialogueVo dialogueVo) throws Exception {
		/*
		 * update ripvo resource vos
		 */
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		Collection<RossImportProcessResourceVo> vos = ripVo.getRossImportProcessResourceVos();
		HashMap<Long,RossImportProcessResourceVo> map = RossImportProcessResourceVo.toHashMapRxfdId(vos);
			
		for(RossImportProcessResourceVo rvo : ripVo.getExcludeResourceWizardVo().getExcludedRossImportProcessResourceVos()){
			rvo.setExcludeResource(true);
			map.put(rvo.getRossXmlFileDataId(), rvo);
		}
			
		ripVo.setRossImportProcessResourceVos(map.values());

		dialogueVo.setResultObject(ripVo);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
