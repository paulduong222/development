package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessImpl;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;

import org.springframework.context.ApplicationContext;

/**
 *  This class represents a failsafe rule for making sure that the
 *  dialogueVo.resultObject has a valid RossImportProcessVo object
 *  before executing the other rules.
 */
public class GetRossImportProcessRules extends AbstractRossImportRule implements IWizardRule {
	private static final String _RULE_NAME="GET_ROSS_IMPORT_PROCESS";

	public GetRossImportProcessRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
	
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			/*
			 * Determine if we already have a rossImportProcessVo
			 */
			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){

				if(null == rxfVo.getRossImportProcessVo()){
					/*
					 * Need to start the import process
					 * , delete any existing import processes for the ross xml file,
					 * and create a new isw_ross_import_process entity
					 */
					RossXmlFileDao rxfDao = (RossXmlFileDao)super.context.getBean("rossXmlFileDao");

					RossXmlFile rxfEntity = rxfDao.getById(rxfVo.getId(), RossXmlFileImpl.class);
					
					RossImportProcess entity = new RossImportProcessImpl();
					entity.setCompletedStage("NA");
					entity.setRossXmlFile(rxfEntity);
					
					rxfEntity.setRossImportProcess(entity);
					
					rxfDao.save(rxfEntity);
					
					/*
					 * Add the RossImportProcessVo to dialogueVo
					 */
					RossImportProcessVo rossImportProcessVo = RossImportProcessVo.getInstance(rxfEntity.getRossImportProcess(), true);
					dialogueVo.setResultObject(rossImportProcessVo);
			
					/*
					 * add rule to processedCoa's
					 */
					dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
					
				}else{
					/*
					 * Add to dialogueVo if not already there.
					 */
					if(null == dialogueVo.getResultObject()){
						dialogueVo.setResultObject(rxfVo.getRossImportProcessVo());
					}
					
					/*
					 * add rule to processedCoa's
					 */
					dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME,true));
					
				}
				
			}else{
				/*
				 * This rule has already been processed, 
				 * continue and return OK.
				 */
			}
			
		}catch(Exception e){
			throw e;
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
