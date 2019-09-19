package gov.nwcg.isuite.framework.core.rules;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.NavigateVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;

import org.springframework.context.ApplicationContext;

public abstract class AbstractRule {
	protected ApplicationContext context=null;
	protected String ruleName="";
	protected static final String _MSG_FINISHED="MSG_FINISHED";
	
	public static final int _OK=1;
	public static final int _FAIL=-1;
	public static final int _SKIP=5;
	
	public AbstractRule(){
		
	}

	public AbstractRule(ApplicationContext ctx){
		context=ctx;
	}

	public AbstractRule(ApplicationContext ctx, String rname){
		context=ctx;
		ruleName=rname;
	}
	
	/**
	 * @param ctx
	 */
	public void setContext(ApplicationContext ctx){
		this.context=ctx;
	}
	
	public String getRuleName(){
		return ruleName;
	}
	
	protected Boolean hasCourseOfAction(DialogueVo dvo, String coaName){
		CourseOfActionVo coa = dvo.getCourseOfActionByName(coaName);
		
		return (null != coa ? true : false);
	}
	
	/**
	 * @param dvo
	 * @param coaName
	 * @return
	 */
	protected Boolean isCourseOfActionComplete(DialogueVo dvo, String coaName) {
		
		CourseOfActionVo coa = dvo.getCourseOfActionByName(coaName);
		
		return ((null != coa) && coa.getIsComplete());
	}
	
	protected Boolean isCourseOfActionNoAction(DialogueVo dvo, String coaName) {
		
		CourseOfActionVo coa = dvo.getCourseOfActionByName(coaName);
		
		return ((null != coa) && coa.getCoaType()==CourseOfActionTypeEnum.NOACTION );
	}

	protected Boolean hasCourseOfActionAdditionalAction(DialogueVo dvo, String coaName) {
		
		CourseOfActionVo coa = dvo.getCourseOfActionByName(coaName);
		
		return ((null != coa) && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED );
	}
	
	/**
	 * @param dvo
	 * @param coaName
	 * @return
	 */
	protected Boolean isCurrentCourseOfAction(DialogueVo dvo, String coaName) {
	
		if(null != dvo.getCourseOfActionVo() 
				&& dvo.getCourseOfActionVo().getCoaName().equals(coaName)){
			return true;
		}else
			return false;
				
	}
	
	protected Boolean needsProcessing(DialogueVo dialogueVo, String coaName) {

		if(null == dialogueVo || null == dialogueVo.getProcessedCourseOfActionVos())
			return false;
		
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(coaName)){
				PromptVo promptVo = coaVo.getPromptVo();
				if(null != promptVo && promptVo.getPromptValue()==PromptVo._YES){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * @param dvo
	 * @return
	 */
	protected int getPromptResult(DialogueVo dvo){
		if(null != dvo.getCourseOfActionVo() && null != dvo.getCourseOfActionVo().getPromptVo()){
			return dvo.getCourseOfActionVo().getPromptVo().getPromptResult().intValue();
		}else
			return -1;
	}
	
	protected int getCustomPromptResult(DialogueVo dvo){
		if(null != dvo.getCourseOfActionVo() && null != dvo.getCourseOfActionVo().getCustomPromptVo()){
			return dvo.getCourseOfActionVo().getCustomPromptVo().getPromptResult().intValue();
		}else
			return -1;
	}
	
	/**
	 * @param dvo
	 * @return
	 */
	protected int getPromptResult(CourseOfActionVo coaVo){
		if(null != coaVo && null != coaVo.getPromptVo()){
			return coaVo.getPromptVo().getPromptResult().intValue();
		}else
			return -1;
	}

	protected CourseOfActionVo buildNoActionCoaVo(String name, Boolean isComplete){
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(name);
		coaVo.setIsComplete(isComplete);
		coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
		
		return coaVo;
	}

	protected CourseOfActionVo buildAdditionalActionCoaVo(String name, Boolean isComplete){
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(name);
		coaVo.setIsComplete(isComplete);
		coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
		
		return coaVo;
	}
	
	protected CourseOfActionVo buildErrorCoaVo(String title, String err,String msgId, String[] params) {
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(err);
		coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
		coaVo.setIsDialogueEnding(true);
		ErrorObject errorObject = new ErrorObject(msgId,params);
		coaVo.getErrorObjectVos().add(errorObject);

		return coaVo;
		
	}
	
	protected CourseOfActionVo buildValidationErrorCoaVo(Collection<ErrorObject> validationErrors) {
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		
		coaVo.setCoaName("ValidationError");
		coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
		coaVo.setIsDialogueEnding(Boolean.TRUE);
		coaVo.setErrorObjectVos(validationErrors);
		
		return coaVo;
	}

	protected NavigateVo buildNavigateVo(String dest){
		NavigateVo vo = new NavigateVo();
		vo.setDestination(dest);
		return vo;
	}
	
	protected UserSessionVo getUserSessionVo(){
		return (UserSessionVo)context.getBean("userSessionVo");
	}
	
	protected String getUserSessionDbName(){
		return ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName();
	}

	protected UserVo getUserVo() throws ServiceException {
		Long id = getUserSessionVo().getUserId();
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setUserRoleVos(getUserSessionVo().getUserRoleVos());
		vo.setLoginName(getUserSessionVo().getUserLoginName());

		return vo;
	}

	protected void handleException(ErrorEnum error,String... params) throws ServiceException {
		throw new ServiceException(new ErrorObject(error,params));
	}
	
	/**
	 * Returns the system parameter value if found.
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {
		
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
		
		SystemParameter entity = spDao.getByParameterName(paramName.name());
		
		if(null == entity)
			this.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter["+paramName.name()+"]");
		
		return entity.getParameterValue();
	}
	
	protected GlobalCacheVo getGlobalCacheVo() throws ServiceException {
		try {
			return (GlobalCacheVo)this.context.getBean("globalCacheVo");
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	protected String getRunMode() throws ServiceException {
		try {
			return this.getGlobalCacheVo().getSystemVo().getParameter(SystemParameterTypeEnum.RUN_MODE.name());
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}
	
	protected Boolean isEnterpriseMode() throws ServiceException {
		try {
			if(this.getRunMode().equals("ENTERPRISE")) {
				return true;
			}
		}catch(Exception e) {
			throw new ServiceException(e);
		}

		return false;
	}

	public void addAdditionalMessages(DialogueVo dialogueVo) throws Exception{
		
	}

	protected Boolean hasRole(String role){
		for(SystemRoleVo roleVo : this.getUserSessionVo().getUserRoleVos()){
			if(roleVo.getRoleName().equals(role)){
				return true;
			}
		}
		
		return false;
	}
}
