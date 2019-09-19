package gov.nwcg.isuite.core.rules.rossimportend;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossIncDataBlacklistImpl;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import org.springframework.context.ApplicationContext;

public class CreateExcludeResourcesRules extends AbstractRossImportEndRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessEndRuleFactory.RuleEnum.CREATE_EXCLUDE_RESOURCES.name();
	
	public CreateExcludeResourcesRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if(super.rossImportVo.getIsReimport()==true){
			return _OK;
		}
		
		Long incidentId = (Long)dialogueVo.getResultObjectAlternate4();
		IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
		Incident incident = incDao.getById(incidentId);
		incDao.flushAndEvict(incident);
		
		super.gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");

		RossIncDataBlacklistDao blacklistDao=(RossIncDataBlacklistDao)context.getBean("rossIncDataBlacklistDao");
		RossXmlFileDao rxfDao = (RossXmlFileDao)context.getBean("rossXmlFileDao");
		RossXmlFileDataDao rxfdDao=(RossXmlFileDataDao)context.getBean("rossXmlFileDataDao");
		
		RossXmlFile rxfEntity = rxfDao.getById(super.rossImportVo.getRossXmlFileId(), RossXmlFileImpl.class);
		rxfDao.flushAndEvict(rxfEntity);
		
		/*
		 * Get all resource that are excluded and create blacklist data records.
		 */
		Collection<Long> rossXmlFileDataIds = new ArrayList<Long>();
		
		for(RossResourceVo rossResourceVo : rossImportVo.getRossResourceVos()){
			if(BooleanUtility.isTrue(rossResourceVo.getExcluded())){
				rossXmlFileDataIds.add(rossResourceVo.getRossXmlFileDataId());
				/*
				RossIncDataBlacklist ridbEntity = new RossIncDataBlacklistImpl();
				ridbEntity.setResId(rossResourceVo.getRossResReqId());
				ridbEntity.setRossResReqId(rossResourceVo.getRossResReqId());
				ridbEntity.setRossIncId(rxfEntity.getRossIncId());
				ridbEntity.setImportStatus("");
				
				blacklistDao.save(ridbEntity);
				blacklistDao.flushAndEvict(ridbEntity);
				
				// update isw_ross_xml_file_data record
				rxfdDao.updateResourceImportStatus(rossResourceVo.getRossResReqId(), rxfEntity.getRossIncId(), "EXCLUDED");
				*/
			}
		}

		if(CollectionUtility.hasValue(rossXmlFileDataIds)){
			if(rossXmlFileDataIds.size()>999){
				/*
				 * Split out collection in chunks of 999
				 */
				Hashtable table = CollectionUtility.splitCollection(rossXmlFileDataIds, 999);
				
				for(int i=1;i<(table.size()+1);i++){
					Collection<Long> ids = (Collection<Long>)table.get(i);
					blacklistDao.createExcludedResources(ids, rxfEntity.getRossIncId());
				}
			}else{
				blacklistDao.createExcludedResources(rossXmlFileDataIds, rxfEntity.getRossIncId());
			}
		}
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}


}
