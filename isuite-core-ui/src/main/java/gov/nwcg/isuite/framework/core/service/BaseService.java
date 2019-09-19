package gov.nwcg.isuite.framework.core.service;

import gov.nwcg.isuite.core.domain.DataAuditConfig;
import gov.nwcg.isuite.core.domain.DataAuditTracking;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.impl.DataAuditTrackingImpl;
import gov.nwcg.isuite.core.persistence.DataAuditConfigDao;
import gov.nwcg.isuite.core.persistence.DataAuditTrackingDao;
import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.SystemVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.IsuiteException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.DataAuditEvent;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.log4j.Level;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.ServletContextAware;

public class BaseService implements ApplicationContextAware, ServletContextAware {
	protected ApplicationContext context;
	protected ServletContext servletContext;
	private UserSessionVo userSessionVo=null;
	protected LoggingInterceptor logger = null;
	private static ReloadableResourceBundleMessageSource isuiteProperties=null;
	
	public BaseService(){

	}

	/*
	 *  TODO: This might be helpful to put in a Utility or something so these can be leveraged
	 *  in the IsuiteExceptionAdvice class. 
	 */
	protected void dialogueException(DialogueVo dialogueVo, Exception e){
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("ServiceException");
		coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
		coaVo.setIsDialogueEnding(true);
		
		if(e instanceof IsuiteException){
			IsuiteException ie = (IsuiteException)e;
			coaVo.setErrorObjectVos(ie.getErrorObjects());
		}else{
			ErrorObject errorObject = new ErrorObject(ErrorEnum._90000_ERROR,new String[]{e.getMessage()});
			coaVo.getErrorObjectVos().add(errorObject);
		}
		dialogueVo.setCourseOfActionVo(coaVo);
	}
	
	/**
	 * @param e
	 * @throws ServiceException
	 */
	protected void handleException(Exception e) throws ServiceException {
		this.log(Level.ERROR, e.getMessage());
		
		ServiceException se = new ServiceException();

		if(e instanceof PersistenceException){
			// handle accordingly
			se = new ServiceException(new ErrorObject(ErrorEnum._900006_PERSISTENCE_ERROR,e.getMessage()));
		}else if (e instanceof IsuiteException){
			se.setErrorObjects(((IsuiteException)e).getErrorObjects());
		}else if (e instanceof JAXBException) {
			se = new ServiceException(new ErrorObject(ErrorEnum.UNABLE_TO_IMPORT_DATA,e.getMessage()));
		}else if (e instanceof UnmarshalException) {
			se = new ServiceException(new ErrorObject(ErrorEnum.UNABLE_TO_IMPORT_DATA,e.getMessage()));
		} else {
			// Generic exception message
			se = new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}

		throw se;
	}

	protected ServiceException getServiceException(Exception e) throws ServiceException {
		this.log(Level.ERROR, e.getMessage());
		
		ServiceException se = new ServiceException();

		if(e instanceof PersistenceException){
			// handle accordingly
			se = new ServiceException(new ErrorObject(ErrorEnum._900006_PERSISTENCE_ERROR,e.getMessage()));
		}else {
			// Generic exception message
			se = new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR,e.getMessage()));
		}

		return se;
	}

	/**
	 * @param error
	 * @throws ServiceException
	 */
	protected void handleException(ErrorEnum error,String... params) throws ServiceException {
		throw new ServiceException(new ErrorObject(error,params));
	}

	protected void handleException(ErrorObject errorObject) throws ServiceException {
		throw new ServiceException(errorObject);
	}

	/**
	 * Sets the LoggingInterceptor.
	 * Should be injected through spring.
	 * 
	 * @param logger
	 * 			the logger interceptor to set
	 */
	public void setLoggingInterceptor(LoggingInterceptor logger){
		this.logger = logger;
	}

	/**
	 * Convenience method to add custom messages to the log.
	 * 
	 * @param message
	 * 			the message to log
	 * @param level
	 * 			the log level (logging priority)
	 */
	protected void log(String message, Level level){
		if(null != logger){

			if(null != this.userSessionVo){
				logger.addLog("[USER: "+userSessionVo.getFirstName()+" " + userSessionVo.getLastName()+"] " + message,level);
			}else
				logger.addLog(message, level);
		}
	}

	/**
	 * Convenience method to add custom messages to the log.
	 * 
	 * @param level
	 * 			the log level (logging priority)
	 * @param message
	 * 			the message to log
	 */
	protected void log(Level level,String message){
		if(null != logger){

			if(null != this.userSessionVo){
				logger.addLog("[USER: "+userSessionVo.getFirstName()+" " + userSessionVo.getLastName()+"] " + message,level);
			}else
				logger.addLog(message, level);
		}
	}

	/**
	 * Session scoped UserSessionVo.
	 * Should be injected from the spring service config.
	 * 
	 * @param vo
	 * 		the session scoped userSessionVo to set
	 */
	public void setUserSessionVo(UserSessionVo vo ){
		this.userSessionVo=vo;
	}

	/**
	 * Returns the session scoped UserSessionVo if available.
	 * 
	 * @return
	 * 		the session scoped UserSessionVo
	 * @throws Exception
	 */
	protected UserSessionVo getUserSessionVo() throws ServiceException{
		if(null==this.userSessionVo){
			throw new ServiceException("UserSessionVo not set.");
		}
		return this.userSessionVo;
	}

	protected UserVo getUserVo() throws ServiceException {
		Long id = getUserSessionVo().getUserId();
		if( !LongUtility.hasValue(id))
			id = 10052L;
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setUserRoleVos(getUserSessionVo().getUserRoleVos());

		return vo;
	}

	protected GlobalCacheVo getGlobalCacheVo() throws ServiceException {
		return (GlobalCacheVo)context.getBean("globalCacheVo");
	}

	protected SystemVo getSystemVo() throws ServiceException {
		return (SystemVo)context.getBean("systemVo");
	}	
	
	protected String getRunMode() throws ServiceException,Exception {
		try {
			return this.getGlobalCacheVo().getSystemVo().getParameter(SystemParameterTypeEnum.RUN_MODE.name());
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	protected String getTrainingMode() throws ServiceException,Exception {
		try {
			return this.getGlobalCacheVo().getSystemVo().getParameter(SystemParameterTypeEnum.TRAINING_MODE.name());
		}catch(Exception e) {
			throw new ServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
   protected Persistable getEntity(CrudDao dao, Long id, Class clz) throws PersistenceException {
		Persistable p = dao.getById(id,clz);
		if(null==p)
			throw new PersistenceException("Unable to get entity for (" + clz + ") with id = " + id);

		return p;
	}

	/**
	 * Returns an array of persistable objects from a collection of persistable objects.
	 * 
	 * @param persistables
	 * @return
	 */
	protected Persistable[] toPersistableArray(Collection<? extends Object> persistables){
		Persistable[] persistableArray = new Persistable[persistables.size()];

		persistables.toArray(persistableArray);

		return persistableArray;
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
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.context=ctx;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext ctx) {
		servletContext=ctx;
	}

	protected String getIsuiteProperty(String name, String... args){
		Object argsArray[] = null;
		if(null!=args)
			argsArray=(Object[])args;
		return this.getIsuiteProperties().getMessage(name, argsArray,null);
	}
	
	/**
	 * Returns the isuiteProperties.
	 *
	 * @return 
	 *		the isuiteProperties to return
	 */
	private ReloadableResourceBundleMessageSource getIsuiteProperties() {
		if(null==isuiteProperties){
			isuiteProperties=(ReloadableResourceBundleMessageSource)context.getBean("messageSource");
		}
		return this.isuiteProperties;
	}

	protected Boolean isEnterprise() {
		try{
			if(this.getGlobalCacheVo().getSystemVo().getRunMode().equals("ENTERPRISE"))
				return true;
			else
				return false;
		}catch(Exception e){
			return false;
		}
	}
	
	protected void createAuditEventRec(String oldValue, String newValue, Long primaryKey, User user){
		DataAuditConfigDao dacDao = (DataAuditConfigDao)context.getBean("dataAuditConfigDao");
		DataAuditConfig dac = null;
		try{
			dac=dacDao.getByEventName("ISW_USER", DataAuditEvent.ACCOUNT_LOGIN.name());
		}catch(Exception e){
			//smother
		}
		try{
			if(null != dac && dac.getEnabled()==StringBooleanEnum.Y ){
				// log the event
				DataAuditTracking dataAuditTracking = new DataAuditTrackingImpl();
				dataAuditTracking.setDataAuditConfig(dac);
				dataAuditTracking.setChangeDate(Calendar.getInstance().getTime());
				dataAuditTracking.setOldValue(oldValue);
				dataAuditTracking.setNewValue(newValue);
				dataAuditTracking.setTablePrimaryKeyId(primaryKey);
				dataAuditTracking.setUserName(user.getLoginName());
				dataAuditTracking.setAuditField1(user.getLoginName());
				dataAuditTracking.setAuditField2(user.getLastName());
				dataAuditTracking.setAuditField3(user.getFirstName());
				dataAuditTracking.setAuditField4(user.getHomeUnit().getUnitCode());
				dataAuditTracking.setAuditField5(user.getPrimaryDispatchCenter().getUnitCode());
				DataAuditTrackingDao datDao = (DataAuditTrackingDao)context.getBean("dataAuditTrackingDao");
				datDao.save(dataAuditTracking);
			}
		}catch(Exception e){
			System.out.println("createAuditEventRec() Exception:"+e.getMessage());
		}
	}
	
	protected String getIpFromRequest(HttpServletRequest request) {
		String ip="";

		if ((ip = request.getHeader("x-forwarded-for")) != null) {
			StrTokenizer tokenizer = new StrTokenizer(ip, ",");
			while (tokenizer.hasNext()) {
				ip = tokenizer.nextToken().trim();
				break;
			}
		}

		if(!StringUtility.hasValue(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;

	}	
	
	protected String getRevisionLevel() throws Exception {
		String level="";
		DataTransferDao dao = (DataTransferDao)context.getBean("dataTransferDao");
		
		String sql="SELECT revisionlevel from revision";
		ArrayList rslt=(ArrayList)dao.executeQuery(sql);
		if(null != rslt){
			level=TypeConverter.convertToString(rslt.get(0));
		}
		
		return level;
	}
	
	protected String getUserSessionDbName(){
		return ((UserSessionVo)context.getBean("userSessionVo")).getSiteDatabaseName();
	}
	
}