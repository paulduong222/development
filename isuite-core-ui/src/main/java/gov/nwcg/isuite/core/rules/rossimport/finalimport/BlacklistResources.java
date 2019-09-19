package gov.nwcg.isuite.core.rules.rossimport.finalimport;

import gov.nwcg.isuite.core.domain.RossIncDataBlacklist;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.core.domain.impl.RossIncDataBlacklistImpl;
import gov.nwcg.isuite.core.persistence.RossIncDataBlacklistDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.RossXmlFileVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class BlacklistResources {

	public static void blacklistResources(ApplicationContext ctx, RossXmlFileVo rxfVo, DialogueVo dialogueVo) throws Exception {

		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
		
		RossXmlFileDataDao rxfdDao = (RossXmlFileDataDao)ctx.getBean("rossXmlFileDataDao");
		
		Collection<Long> rossResourceIds = new ArrayList<Long>();

		// Check the primary resource collection
		for(RossImportProcessResourceVo ripRvo : ripVo.getRossImportProcessResourceVos()){
			
			if(ripRvo.getExcludeResource()){
				if(!rossResourceIds.contains(ripRvo.getRossResReqId())){
					rossResourceIds.add(ripRvo.getRossResId()); // .getRossResReqId());
				}
			}
		}

		
		Collection<RossXmlFileData> rxfdEntities = rxfdDao.getAllByRossXmlFileId(rxfVo.getId(), true);
		
		// check the data conflicts for other exclusions
		for(RossImportProcessDataErrorVo ripDataErrVo : ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()){
			if(ripDataErrVo.getExcludeFromImport()){

				// need to get rossResId from the ripDataErrVo.getRXFDId
				Long rossResId = 0L;
				for(RossXmlFileData rxfd : rxfdEntities){
					if(rxfd.getId().compareTo(ripDataErrVo.getRossXmlFileDataId())==0){
						try{
							rossResId=TypeConverter.convertToLong(rxfd.getResId());
						}catch(Exception e){}
					}
				}
				
				if(null != rossResId && rossResId > 0){
					
					// see if the resource is already in the exclusion list before adding it
					if(!rossResourceIds.contains(rossResId)){
						rossResourceIds.add(rossResId);
					}
					
				}
			}
		}
		
		if(rossResourceIds.size()>0){
			RossIncDataBlacklistDao ridbDao = (RossIncDataBlacklistDao)ctx.getBean("rossIncDataBlacklistDao");
			
			ridbDao.blacklistResources(rxfVo.getId(), rossResourceIds);
			
			// update the isw_ross_xml_file_data blacklisted resources as excluded
			ridbDao.updateStatuses(rossResourceIds, rxfVo.getId(), "EXCLUDED");
		}
	}
}
