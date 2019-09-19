package gov.nwcg.isuite.core.rules.dbmgmt.savedb;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.service.impl.DatabaseMgmtServiceImpl;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DatasourceUsageUtil;

import org.springframework.context.ApplicationContext;

public class CheckDatasourceUsageRules extends AbstractDbMgmtSaveRule implements IRule {
	public static final String _RULE_NAME = DbMgmtSaveRuleFactory.RuleEnum.CHECK_DATASOURCE_USAGE_RULES.name();

	public CheckDatasourceUsageRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Since, we are unable to determine why the connection pool connections cannot reopen after being closed,
		 * we have a pool of 20 data source connections avail, once all 20 have been used during a tomcat session,
		 * we have to force the user to restart tomcat and reset the connection pools.
		 */
		Boolean hasOneOpen=false;

		this.syncDatasourceUsage();
		
		for(DatabaseMgmtServiceImpl.DataSourceEnum dsEnum : DatabaseMgmtServiceImpl.DataSourceEnum.values()){
			Boolean bFound=false;
			for(String ds : DatasourceUsageUtil.datasourcesUsed){
				if(dsEnum.name().equals(ds)){
					bFound=true;
				}
			}
			if(bFound==false)hasOneOpen=true;
		}
		
		if(hasOneOpen==false){
			String msg="The system has exceeded the available connections for the server e-ISuite session.  "+
						"Please restart the tomcat server to reset available connections.";
			
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.800000"
										  , new String[]{msg}));	
			return _FAIL;
		}
		
		return _OK;
	}

	private void syncDatasourceUsage() throws Exception {
		DbAvailDao dbAvailDao=null;
		dbAvailDao = (DbAvailDao)context.getBean("dbAvailDao");
		
		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
		Collection<DbAvail> entities = dbAvailDao.getAll();
		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		
		Collection<DbAvailVo> vos = DbAvailVo.getInstances(entities, true);

		// synce datasource usage
		for(DbAvailVo dbvo : vos){
			Boolean bfound=false;
			for(String ds : DatasourceUsageUtil.datasourcesUsed){
				if(ds.equals(dbvo.getDatasource()))
					bfound=true;
			}
			// if not found, add to datasource usage map
			if(bfound==false){
				DatasourceUsageUtil.datasourcesUsed.add(dbvo.getDatasource());
			}
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

	
}
