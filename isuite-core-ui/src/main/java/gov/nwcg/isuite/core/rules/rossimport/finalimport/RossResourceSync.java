package gov.nwcg.isuite.core.rules.rossimport.finalimport;

import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.RossXmlFileDataVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class RossResourceSync {

	/**
	 * Loop through all of the rossimportprocess resource collections and create
	 * a new collection of resources that has all of the resource synchronized.
	 * 
	 * @param ctx
	 * @param rxfVo
	 * @param dialogueVo
	 * @throws Exception
	 */
	public static Collection<RossImportProcessResourceVo> synchronizeResources(ApplicationContext ctx, RossXmlFileVo rxfVo, DialogueVo dialogueVo) throws Exception {
		Collection<RossImportProcessResourceVo> vos = new ArrayList<RossImportProcessResourceVo>();
		
		/*
		 * Remove all excluded resources
		 */
		vos = removeExcludedResources(ctx,rxfVo,dialogueVo);
	
		/*
		 * Synchronize matches by Req Number / Name
		 */
		synchronizeMatchesReqNumName(vos,rxfVo,dialogueVo);
		
		/*
		 * Synchronize matches by Req Number
		 */
		synchronizeMatchesReqNum(vos,rxfVo,dialogueVo);
		
		/*
		 * Synchronize matches by Name / Item Code
		 */
		synchronizeMatchesNameItemCode(vos,rxfVo,dialogueVo);
	
		/*
		 * Synchronize all remaining 
		 */
		synchronizeUnmatchedResources(vos,rxfVo,dialogueVo);
		
		/*
		 * Synchronize resource qualifications
		 */
		//synchronizeQualifications(ctx,vos,rxfVo,dialogueVo);

		/*
		 * Synchronize all data conflicts
		 */
		synchronizeDataConflicts(vos,rxfVo,dialogueVo);
		
		return vos;
	}
	
	/**
	 * Loops through the ripVo
	 * @param rxfVo
	 * @param dialogueVo
	 * @return
	 */
	private static Collection<RossImportProcessResourceVo> removeExcludedResources(ApplicationContext ctx,RossXmlFileVo rxfVo, DialogueVo dialogueVo) throws Exception{
		Collection<RossImportProcessResourceVo> vos = new ArrayList<RossImportProcessResourceVo>();

		RossXmlFileDataDao rxfdDao = (RossXmlFileDataDao)ctx.getBean("rossXmlFileDataDao");
		
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		for( RossImportProcessResourceVo ripResVo : ripVo.getRossImportProcessResourceVos()){
			if(!ripResVo.getExcludeResource())
			{
				// get the rxfdVo 
				//RossXmlFileData entity = rxfdDao.getById(ripResVo.getRossXmlFileDataId(), RossXmlFileDataImpl.class);
				//ripResVo.setRossXmlFileDataVo(RossXmlFileDataVo.getInstance(entity, true));
				vos.add(ripResVo);
			}
		}
		
		return vos;
	}

	private static void synchronizeMatchesReqNumName(Collection<RossImportProcessResourceVo> vos, RossXmlFileVo rxfVo, DialogueVo dialogueVo) {
		
	}
	
	private static void synchronizeMatchesNameItemCode(Collection<RossImportProcessResourceVo> vos, RossXmlFileVo rxfVo, DialogueVo dialogueVo) {
		
	}
	
	private static void synchronizeMatchesReqNum(Collection<RossImportProcessResourceVo> vos, RossXmlFileVo rxfVo, DialogueVo dialogueVo) {
		
	}	

	/**
	 * @param vos
	 * @param rxfVo
	 * @param dialogueVo
	 */
	private static void synchronizeUnmatchedResources(Collection<RossImportProcessResourceVo> vos, RossXmlFileVo rxfVo, DialogueVo dialogueVo) {

	}	
	
	/**
	 * @param ctx
	 * @param vos
	 * @param rxfVo
	 * @param dialogueVo
	 * @throws Exception
	 */
	private static void synchronizeQualifications(ApplicationContext ctx,Collection<RossImportProcessResourceVo> vos, RossXmlFileVo rxfVo, DialogueVo dialogueVo) throws Exception {
		Collection<RossImportProcessResourceVo> newVos = new ArrayList<RossImportProcessResourceVo>();

		RossXmlFileDao rxfdDao = (RossXmlFileDao)ctx.getBean("rossXmlFileDao");
		
		RossXmlFile rxfEntity = rxfdDao.getById(rxfVo.getId(), RossXmlFileImpl.class);

		/*
		 * Build the qualifications list for each ripResVo
		 */
		if(null != rxfEntity && null != rxfEntity.getRossXmlFileDatas())
		{
			for(RossImportProcessResourceVo ripResVo : vos){
				for(RossXmlFileData rxfd : rxfEntity.getRossXmlFileDatas()){
					
					Long rxfdReqId = 0L;
					try{
						rxfdReqId=TypeConverter.convertToLong(rxfd.getReqId());
					}catch(Exception e){}
					
					if(ripResVo.getRossResReqId().compareTo(rxfdReqId) == 0){
						
						// is this the rxfd record a qual?
						if(BooleanUtility.isFalse(ShortUtil.toBoolean(rxfd.getRossAssignment()))){
								//!rxfd.getRossAssignment()){
							ripResVo.getQualifications().add(RossXmlFileDataVo.getInstance(rxfd, true));
						}
					}
					
					break;
				}
				newVos.add(ripResVo);
			}
		}
		
		vos=newVos;
		
	}	

	/**
	 * @param vos
	 * @param rxfVo
	 * @param dialogueVo
	 * @throws Exception
	 */
	private static void synchronizeDataConflicts(Collection<RossImportProcessResourceVo> vos, RossXmlFileVo rxfVo, DialogueVo dialogueVo) throws Exception {
		Collection<RossImportProcessResourceVo> newVos = new ArrayList<RossImportProcessResourceVo>();
		
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		for(RossImportProcessResourceVo ripResVo : vos){
			
			// does the ripResVo have resolved conflicts?
			for(RossImportProcessDataErrorVo ripDataErrorVo : ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()){

				
				if(ripResVo.getRossXmlFileDataId().compareTo(ripDataErrorVo.getRossXmlFileDataId())==0){
					
					/*
					 * Was the data error resolved?
					 */
					if(null != ripDataErrorVo.getNewValue()){
						
						// set new value
						
					}
					
				}
				
			}
			
			newVos.add(ripResVo);
			
		}

		vos=newVos;
	}	
	
}
