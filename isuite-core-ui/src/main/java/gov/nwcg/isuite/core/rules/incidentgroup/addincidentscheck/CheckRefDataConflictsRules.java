package gov.nwcg.isuite.core.rules.incidentgroup.addincidentscheck;

import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupConflictVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckRefDataConflictsRules extends AbstractAddIncidentsCheckRule implements IRule{
	public static final String _RULE_NAME=AddIncidentsCheckRuleFactory.RuleEnum.CHECK_REF_DATA_CONFLICTS.name();

	public CheckRefDataConflictsRules(ApplicationContext ctx)
	{
		super(ctx);
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
		IncidentGroupConflictVo igConflictVo = new IncidentGroupConflictVo();
		
		int cnt=0;
		
		Collection<Long> incidentIds = new ArrayList<Long>();
		for(IncidentGridVo incGridVo : super.incGridVos){
			incidentIds.add(incGridVo.getId());
		}
		
		// check for duplicate non-standard agency codes
		AgencyDao agencyDao = (AgencyDao)context.getBean("agencyDao");
		Collection<AgencyVo> agencyVos = agencyDao.getIncidentGroupAgencyDuplicates(super.incidentGroupVo.getId(), incidentIds);
		if(CollectionUtility.hasValue(agencyVos)){
			igConflictVo.getTextLines().add("The Non-Standard Agency duplicate conflicts are:");
			for(AgencyVo vo : agencyVos){
				igConflictVo.getTextLines().add("  "+vo.getAgencyCd()+"   "+vo.getAgencyNm()+"     " + vo.getTempString1());
				cnt++;
			}
		}else{
			igConflictVo.getTextLines().add("There are no Non-Standard Agency duplicate conflicts.");
		}
		igConflictVo.getTextLines().add("");
		
		// check for duplicate non-standard unit codes
		OrganizationDao orgDao = (OrganizationDao)context.getBean("organizationDao");
		Collection<OrganizationVo> orgVos = orgDao.getIncidentGroupOrganizationDuplicates(super.incidentGroupVo.getId(), incidentIds);
		if(CollectionUtility.hasValue(orgVos)){
			igConflictVo.getTextLines().add("The Non-Standard Unit Code duplicate conflicts are:");
			for(OrganizationVo vo : orgVos){
				igConflictVo.getTextLines().add("  "+vo.getUnitCode()+"   "+vo.getName()+"     " + vo.getTempString1());
				cnt++;
			}
		}else{
			igConflictVo.getTextLines().add("There are no Non-Standard Unit Code duplicate conflicts.");
		}
		igConflictVo.getTextLines().add("");
		
		// check for duplicate non-standard jetport codes
		JetPortDao jetPortDao = (JetPortDao)context.getBean("jetPortDao");
		Collection<JetPortVo> jetPortVos = jetPortDao.getIncidentGroupJetPortDuplicates(super.incidentGroupVo.getId(), incidentIds);
		if(CollectionUtility.hasValue(jetPortVos)){
			igConflictVo.getTextLines().add("The Non-Standard JetPort duplicate conflicts are:");
			for(JetPortVo vo : jetPortVos){
				igConflictVo.getTextLines().add("  "+vo.getCode()+"   "+vo.getDescription()+"     " + vo.getTempString1());
				cnt++;
			}
		}else{
			igConflictVo.getTextLines().add("There are no Non-Standard JetPort duplicate conflicts.");
		}
		igConflictVo.getTextLines().add("");
		
		// check for duplicate non-standard item codes
		KindDao kindDao = (KindDao)context.getBean("kindDao");
		Collection<KindVo> kindVos = kindDao.getIncidentGroupKindDuplicates(super.incidentGroupVo.getId(), incidentIds);
		if(CollectionUtility.hasValue(kindVos)){
			igConflictVo.getTextLines().add("The Non-Standard Item Code duplicate conflicts are:");
			for(KindVo vo : kindVos){
				igConflictVo.getTextLines().add("  "+vo.getCode()+"   "+vo.getDescription()+"     " + vo.getTempString1());
				cnt++;
			}
		}else{
			igConflictVo.getTextLines().add("There are no Non-Standard Item Code duplicate conflicts.");
		}
		igConflictVo.getTextLines().add("");
		igConflictVo.setConflictCount(cnt);
		dialogueVo.setResultObject(igConflictVo);
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
