package gov.nwcg.isuite.core.rules.rossimportend2;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.AssignmentDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class MatchResourcesRules extends AbstractRossImportEndRule implements IRule{
	public static final String _RULE_NAME=RossImportProcessEndRuleFactory2.RuleEnum.MATCH_RESOURCES.name();
	
	public MatchResourcesRules(ApplicationContext ctx)
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
		Long incidentId = (Long)dialogueVo.getResultObjectAlternate4();
		IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
		Incident incident = incDao.getById(incidentId);
		incDao.flushAndEvict(incident);

		ResourceDao resDao = (ResourceDao)context.getBean("resourceDao");
		IncidentResourceDao incResDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		AssignmentDao assignmentDao = (AssignmentDao)context.getBean("assignmentDao");
		
		super.gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
		
		/*
		 * Get all resource that are matched and not excluded.
		 */
		Collection<RossResourceVo> resourcesToMatch=new ArrayList<RossResourceVo>();
		for(RossResourceVo rossResourceVo : rossImportVo.getRossResourceVos()){
			
			if(BooleanUtility.isFalse(rossResourceVo.getExcluded()) && BooleanUtility.isTrue(rossResourceVo.getMatched())){
				resourcesToMatch.add(rossResourceVo);
			}
		}
		
		for(RossResourceVo vo : resourcesToMatch){
			if(LongUtility.hasValue(vo.getEisuiteResourceId())){
				Resource r = resDao.getById(vo.getEisuiteResourceId(), ResourceImpl.class);
				if(null != r){
					r.setRossResId(vo.getRossResId());
					(r.getIncidentResources().iterator().next()).setRossResReqId(vo.getRossResReqId());
					Long irId=(r.getIncidentResources().iterator().next()).getId();
					resDao.save(r);
					resDao.flushAndEvict(r);
					IncidentResource ir = incResDao.getById(irId, IncidentResourceImpl.class);
					Assignment assignment = null;
					for(Assignment a : ir.getWorkPeriod().getAssignments()){
						if(!DateUtil.hasValue(a.getEndDate())){
							assignment = a;
							assignment.setRequestNumber(vo.getRequestNumber());
							break;
						}
					}
					incResDao.flushAndEvict(ir);
					assignmentDao.save(assignment);
					assignmentDao.flushAndEvict(assignment);
				}
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
