package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.domain.RossImportProcessDataError;
import gov.nwcg.isuite.core.persistence.RossImportProcessDataErrorDao;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RossImportProcessDataErrorVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport.DataConflictWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class DataErrorRules extends AbstractRossImportRule implements IWizardRule {
	private static final String _RULE_NAME="DATA_ERRORS";
 
	public DataErrorRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coaVo){
			if(coaVo.getCoaType()==CourseOfActionTypeEnum.SKIP)
				return _FAIL;
			
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(coaVo);
		}
	
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		/*
		 * Need to determine if there is any data in the isw_ross_xml_file_data table
		 * that the system cannot use when creating the incident resources.
		 */
		try{
			RossImportProcessDataErrorDao ripDataErrDao = (RossImportProcessDataErrorDao)context.getBean("rossImportProcessDataErrorDao");
			
			/*
			 * Determine if we already have completed this rule
			 */
			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){
				
				if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
					
					/*
					 * verify all errors have been resolved
					 */
					RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
					for(RossImportProcessDataErrorVo vo : ripVo.getDataConflictWizardVo().getRossImportProcessDataErrorVos()){
						if(null==vo.getNewValue()){
							dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
							dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"All data conflicts must be resolved before continuing."},MessageTypeEnum.CRITICAL));
							
							return _FAIL;
						}
					}

					// add to the processed collection
					dialogueVo.getCourseOfActionVo().setIsComplete(true);
					dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
					
					// continue
					
				}else{

					ripDataErrDao.checkForIncidentErrors(this.rxfVo.getId()
															, getIncidentUnitCode()
															, getIncidentAgencyCode()
															, this.rxfVo.getIncidentEventType());
					
					ripDataErrDao.checkForResourceErrors(this.rxfVo.getId());
					
					Collection<RossImportProcessDataError> entities = 
							ripDataErrDao.getByRossXmlFileId(rxfVo.getId());
					
					Collection<RossImportProcessDataErrorVo> vos =
						RossImportProcessDataErrorVo.getInstances(entities, true);

					DataConflictWizardVo dcWizVo = new DataConflictWizardVo();
					RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();
					Collection<RossImportProcessResourceVo> resvos = ripVo.getRossImportProcessResourceVos();
					HashMap<Long,RossImportProcessResourceVo> map = RossImportProcessResourceVo.toHashMapRxfdId(resvos);
					
					/*
					 * only add resource data errors for resources not already marked as excluded
					 */
					for(RossImportProcessDataErrorVo evo : vos){
						RossImportProcessResourceVo rvo = map.get(evo.getRossXmlFileDataId());
						if(null != rvo){
							if(!rvo.getExcludeResource())
								dcWizVo.getRossImportProcessDataErrorVos().add(evo);
						}else{
							System.out.println(evo.getConflictCode());
							if(evo.getConflictCode().equals("UNKNOWN_INCIDENT_PDC")){
								OrganizationVo org = 
									OrganizationVo.getByUnitCode(getIncidentUnitCode(), super.getGlobalCacheVo().getOrganizationVos());

								if(null != org){
									evo.setPdcs(org.getDispatchCenters());
								}
							}
							dcWizVo.getRossImportProcessDataErrorVos().add(evo);
						}
					}
					
					// update the rossImportProcessVo
					((RossImportProcessVo)dialogueVo.getResultObject())
								.setDataConflictWizardVo(dcWizVo);
					
					Boolean hasConflicts=false;
					
					if(null != vos && vos.size()>0){
						/*
						 * determine if there any conflict records
						 */
						for(RossImportProcessDataErrorVo vo : vos){
							if(vo.getExcludeFromImport()
									|| null==vo.getNewValue()){
								hasConflicts=true;
								break;
							}
						}
					}
					
					if(hasConflicts){
							// create a navigate courseofaction
							CourseOfActionVo coaVo = new CourseOfActionVo();
							coaVo.setCoaName(_RULE_NAME);
							coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
							coaVo.setNavigateVo(
									buildNavigateVo(
											RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
							dialogueVo.setCourseOfActionVo(coaVo);
						
							return _FAIL;
					}else{
						// add to processed as complete
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setIsComplete(true);
						coaVo.setCoaType(CourseOfActionTypeEnum.SKIP);
						
						dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
						
						return _OK;
					}
				}
				
			}else{
				/*
				 * Rule has been completed.
				 * continue, return OK
				 */
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw new Exception(e);
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
	private String getIncidentUnitCode() {
		String unitCode="";
		StringTokenizer st = new StringTokenizer(this.rxfVo.getIncidentNumber(),"-");
		int i=0;
		while(st.hasMoreTokens()){
			String val = (String)st.nextToken();
			if(i>0)val="-"+val;
			if(i<2)
				unitCode=unitCode+val;
			i++;
		}
		return unitCode;
	}

	private String getIncidentAgencyCode() {
		String agency="";
		StringTokenizer st = new StringTokenizer(this.rxfVo.getIncidentNumber(),"-");
		int i=0;
		while(st.hasMoreTokens()){
			String val = (String)st.nextToken();
			if(i<1)
				agency=val;
			i++;
		}
		
		return agency;
	}

	
}
