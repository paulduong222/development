package gov.nwcg.isuite.core.rules.rossimportbegin;

import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossImportVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

public class LoadRossResourcesRules extends AbstractRossImportBeginRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessBeginRuleFactory.RuleEnum.LOAD_ROSS_RESOURCES.name();
	private String option1="";
	
	public LoadRossResourcesRules(ApplicationContext ctx)
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
			option1=(String)dialogueVo.getResultObjectAlternate3();
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
		/*
		 * This rule is only applicable to normal importing, return ok when reimport
		 */
		if(CollectionUtility.hasValue(super.reimportReqIds))
			return _OK;
		
		RossImportVo rossImportVo = (RossImportVo)dialogueVo.getResultObjectAlternate4();

		super.gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
		
		/*
		 * Include all rossXmlFileDataVo's
		 */
		RossXmlFileDataDao rxfdDao = (RossXmlFileDataDao)context.getBean("rossXmlFileDataDao");
		Collection<RossResourceVo> resourceVos = new ArrayList<RossResourceVo>();

		if(LongUtility.hasValue(rossImportVo.getEisuiteIncidentId())){
		//if(BooleanUtility.isTrue(rossImportVo.getHasIncidentMatch())){
			/*
			 * If there is a matching eisuite incident, only load new rossXmlFileDataVo's that have not been excluded
			 */
			resourceVos = rxfdDao.getNewResourcesByRossXmlFileId(rxfVo.getId(), rossImportVo.getEisuiteIncidentId(), true);
			
			Long incidentId=rossImportVo.getEisuiteIncidentId();
			
			RossImportProcessDao ripDao = (RossImportProcessDao)super.context.getBean("rossImportProcessDao");
				
			// Get the list of unmatched resources for the incident in eisuite
			Collection<RossEISuiteResourceVo> eisuiteResources = ripDao.getEISuiteResources2(incidentId);
			Collection<RossEISuiteResourceVo> eisuiteResources2 = new ArrayList<RossEISuiteResourceVo>();
			for(RossEISuiteResourceVo v : eisuiteResources){
				RossEISuiteResourceVo v2=v;
				if(BooleanUtility.isTrue(v.getIsPerson())){
					v2.setResourceName(v.getLastName()+", "+v.getFirstName());
				}
				eisuiteResources2.add(v2);
			}
			
			rossImportVo.setEisuiteResourceVos(eisuiteResources2);
			
		}else{
			/*
			 * Include all rossXmlFileDataVo's
			 */
			resourceVos = rxfdDao.getNewResourcesByRossXmlFileId(rxfVo.getId(), null, true);
			
		}
		
		rossImportVo.setRossResourceVos(resourceVos);
			
		checkResources(rossImportVo);
			
		// update roa4 with updated rossimportvo
		dialogueVo.setResultObjectAlternate4(rossImportVo);
		
		return _OK;
	}

	private void checkResources(RossImportVo rossImportVo) {
		Collection<RossResourceVo> newRossResourceVos = new ArrayList<RossResourceVo>();
		
		for(RossResourceVo vo : rossImportVo.getRossResourceVos()){

			/*
			 * the ross file sometimes contains duplicates
			 * if vo is already in newRossResourceVos, then skip it
			 */
			Boolean alreadyAdded=false;
			for(RossResourceVo v : newRossResourceVos){
				if(String.valueOf(v.getRossResReqId()).equals(String.valueOf(vo.getRossResReqId()))){
					alreadyAdded=true;
					break;
				}
			}
			
			if(alreadyAdded==false){
				vo.setIsPerson(false);

				/* 
				 * If item code is empty, try to match it with a kind vo 
				 */
				if(!StringUtility.hasValue(vo.getItemCode()) && StringUtility.hasValue(vo.getItemName())){
					for(KindVo kindVo : super.gcVo.getKindVos()){
						if(kindVo.getDescription().equalsIgnoreCase(vo.getItemName())){
							vo.setItemCode(kindVo.getCode());
						}
					}
				}
				
				// trim the name fields
				if(StringUtility.hasValue(vo.getFirstName())){
					vo.setFirstName(vo.getFirstName().trim());
					vo.setFirstName(vo.getFirstName().replaceAll("&#39;", "'"));
					vo.setFirstName(vo.getFirstName().replaceAll("&amp;", "&"));
					vo.setFirstName(vo.getFirstName().replaceAll("&AMP;", "&"));
				}
				if(StringUtility.hasValue(vo.getLastName())){
					vo.setLastName(vo.getLastName().trim());
					vo.setLastName(vo.getLastName().replaceAll("&#39;", "'"));
					vo.setLastName(vo.getLastName().replaceAll("&amp;", "&"));
					vo.setLastName(vo.getLastName().replaceAll("&AMP;", "&"));
				}
				
				if(StringUtility.hasValue(vo.getMiddleName())){
					vo.setMiddleName(vo.getMiddleName().trim());
					//vo.setFirstName(vo.getFirstName() + " " + vo.getMiddleName());
				}
				if(StringUtility.hasValue(vo.getResourceName())){
					vo.setResourceName(vo.getResourceName().trim());
					vo.setResourceName(vo.getResourceName().replaceAll("&#39;", "'"));
					vo.setResourceName(vo.getResourceName().replaceAll("&amp;", "&"));
					vo.setResourceName(vo.getResourceName().replaceAll("&AMP;", "&"));
				}
				if(StringUtility.hasValue(vo.getAssignmentName())){
					vo.setAssignmentName(vo.getAssignmentName().trim());
					vo.setAssignmentName(vo.getAssignmentName().replaceAll("&#39;", "'"));
					vo.setAssignmentName(vo.getAssignmentName().replaceAll("&amp;", "&"));
					vo.setAssignmentName(vo.getAssignmentName().replaceAll("&AMP;", "&"));
				}
				
				/*
				 * Try and set resource name fields
				 */
				if(StringUtility.hasValue(vo.getRequestCatalogName())){
					/*
					 * Persons are when:
					 * 	request_catalog_name = 'OVERHEAD'
					 *  and
					 *  (request_category_name != 'GROUPS')
					 */
					if(vo.getRequestCatalogName().equalsIgnoreCase("OVERHEAD")){
						
						if(StringUtility.hasValue(vo.getRequestCategoryName())){
							String categoryName=vo.getRequestCategoryName();
							
							//if(categoryName.equals("POSITIONS") ){
							if(!categoryName.equalsIgnoreCase("GROUPS") ){
								vo.setIsPerson(true);
								
								// verify we have first and last name
								// sometimes lastname and firstname are set 
								// sometimes we have to parse resourcename
								if(!StringUtility.hasValue(vo.getFirstName())){
									
									// do we have a resourcename
									if(StringUtility.hasValue(vo.getResourceName()) && vo.getResourceName().indexOf(",")>0){
										
										StringTokenizer tokens = new StringTokenizer(vo.getResourceName(),",");
										int i=0;
										while(tokens.hasMoreTokens()){
											String token = (String)tokens.nextToken();
											if(i==0){
												vo.setLastName(token.trim());
											}else{
												vo.setFirstName(token.trim());
											}
											
											i++;
										}
									}
								}
								
							}else{
								/*
								 * if resourceName is empty and not person
								 * , check for assignmentName
								 */
								if(!StringUtility.hasValue(vo.getResourceName())){
									vo.setResourceName(vo.getAssignmentName());
								}
								
							}
						}
						
					}else{
						
						/*
						 * if resourceName is empty and not person
						 * , check for assignmentName
						 */
						if(!StringUtility.hasValue(vo.getResourceName())){
							vo.setResourceName(vo.getAssignmentName());
						}
						
					}
					
					/*
					 * if resourceName is empty and not person
					 * , check for assignmentName
					 */
					if(!vo.getIsPerson()){
						if(!StringUtility.hasValue(vo.getResourceName())){
							if(StringUtility.hasValue(vo.getAssignmentName())){
								vo.setResourceName(vo.getAssignmentName());
							}
						}
						
						// temporary
						if(option1.equals("Process Normal")){
							// do nothing
						}else if(option1.equals("Auto Truncate to 55 chars")){
							if(vo.getResourceName().length()>55){
								String s=vo.getResourceName().substring(0, 54);
								vo.setResourceName(s);
							}
						}else if(option1.equals("Auto Truncate to first parenthesis")){
							if(vo.getResourceName().length()>55){
								
								int idx=vo.getResourceName().indexOf("(");
								if(idx > 0){
									String s=vo.getResourceName().substring(0,idx);
									vo.setResourceName(s);
								}
							}
							
						}
					}
				}
			
				newRossResourceVos.add(vo);
				
			}
		}
		
		rossImportVo.setRossResourceVos(newRossResourceVos);
		
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
