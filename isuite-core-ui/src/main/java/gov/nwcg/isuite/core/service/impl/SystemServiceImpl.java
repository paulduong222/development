/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.RateClassDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.rules.TrainingDateSaveRulesHandler;
import gov.nwcg.isuite.core.service.SystemService;
import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.AdjustCategoryVo;
import gov.nwcg.isuite.core.vo.AdjustmentTypeVo;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.ComplexityVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.core.vo.EmploymentTypeVo;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.IncidentRestrictedStatusVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RateAreaVo;
import gov.nwcg.isuite.core.vo.RateClassRateVo;
import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.core.vo.RateTypeVo;
import gov.nwcg.isuite.core.vo.RecommendationVo;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.ResourceClassificationVo;
import gov.nwcg.isuite.core.vo.SectionVo;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.SystemTypeVo;
import gov.nwcg.isuite.core.vo.TrainingSystemVo;
import gov.nwcg.isuite.core.vo.UnitOfMeasureVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.wrapper.StaticDataWrapperVo;
import gov.nwcg.isuite.core.vo.wrapper.StaticDataWrapperVo2;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.dbutil.RecoverAccountUtil;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.IapSectionEnum;
import gov.nwcg.isuite.framework.types.IncidentRestrictedStatusTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.RateAreaEnum;
import gov.nwcg.isuite.framework.types.RateTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.SystemTypeEnum;
import gov.nwcg.isuite.framework.types.UnitOfMeasureEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SuppressWarnings("unchecked")
public class SystemServiceImpl extends BaseService implements SystemService {

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getRunMode()
	 */
	@Override
	public String getRunMode() throws Exception {
		String runMode=super.getRunMode();
		
		try{
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			if(null != attr){
			    HttpSession session=attr.getRequest().getSession(false);
			    if (session!=null && !session.isNew()) {
			        session.invalidate();
			    }			
			}
		}catch(Exception e){
			// smother
			System.out.println(e.getMessage());
		}
		
		return runMode;
		//SystemVo vo = super.getGlobalCacheVo().getSystemVo();
		//return vo.getRunMode();
	}

	public DialogueVo getTrainingMode(DialogueVo dialogueVo) throws Exception {
		if(null == dialogueVo) dialogueVo=new DialogueVo();
		
		try{
			String mode="0";
			
			if(BooleanUtility.isTrue(EISuiteCalendar.isTrainingMode))
				mode="1";
			
			TrainingSystemVo vo = new TrainingSystemVo();
			if(mode.equals("1")){
				vo.setTrainingMode("yes");
				Date dt=EISuiteCalendar.getCalendarDate(super.getUserSessionDbName());
				DateTransferVo.populateDate(vo.getTrainingDateVo(), dt);
			}else
				vo.setTrainingMode("no");
			
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_TRAINING_MODE");
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vo);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	private TrainingSystemVo getTrainingVo() throws Exception {
		
		try{
			String mode="0";
			
			if(BooleanUtility.isTrue(EISuiteCalendar.isTrainingMode))
				mode="1";
			
			TrainingSystemVo vo = new TrainingSystemVo();
			if(mode.equals("1")){
				vo.setTrainingMode("yes");
				Date dt=EISuiteCalendar.getCalendarDate(super.getUserSessionDbName());
				DateTransferVo.populateDate(vo.getTrainingDateVo(), dt);
			}else
				vo.setTrainingMode("no");
			

			return vo;
			
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public DialogueVo saveTrainingSystemDate(TrainingSystemVo vo,DialogueVo dialogueVo) throws Exception {
		if(null == dialogueVo) dialogueVo=new DialogueVo();
		
		try{
			Date newDt=null;
			
			if(DateTransferVo.hasDateString(vo.getTrainingDateVo())){
				newDt=DateTransferVo.getDate(vo.getTrainingDateVo());
			}

			TrainingDateSaveRulesHandler ruleHandler = new TrainingDateSaveRulesHandler(context);
			if(ruleHandler.execute(newDt, dialogueVo)==AbstractRule._OK){
				EISuiteCalendar.setDbCalendarDate(super.getUserSessionDbName(), newDt);
				String sdt="0";
				
				if(DateUtil.hasValue(newDt)){
					sdt=DateUtil.toDateString(newDt, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				}
				
				//((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
				SystemParameterDao spdao = (SystemParameterDao)context.getBean("systemParameterDao");
				spdao.updateByName("TRAINING_SYSTEM_DATE", sdt);
				//((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

				ruleHandler.executeProcessedActions(newDt, dialogueVo);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_TRAINING_SYSTEM_DATE");
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.trainingAdmin", "info.0030.01" , new String[]{"Training System Date"}, MessageTypeEnum.INFO));

				dialogueVo.setCourseOfActionVo(coaVo);
				dialogueVo.setResultObject(vo);
			}
			
		}catch(Exception e){
			//((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	
	public DialogueVo getSiteDatabaseList(DialogueVo dialogueVo) {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES"); 
			DbAvailDao dbAvailDao = (DbAvailDao)context.getBean("dbAvailDao");
			Collection<DbAvail> entities = dbAvailDao.getAll();

			Collection<DbAvailVo> vos = DbAvailVo.getInstances(entities, true);

			for(DbAvail dba : entities){
				dbAvailDao.flushAndEvict(dba);
			}
			
			// synce datasource usage
			GlobalCacheVo gcVo = (GlobalCacheVo)context.getBean("globalCacheVo");
			Collection<String> dataSourceConnectionsUsed = gcVo.getDataSourceConnectionsUsed();
			Boolean bUpdateContext=false;
			
			for(DbAvailVo dbvo : vos){
				Boolean bfound=false;
				for(String ds : dataSourceConnectionsUsed){
					if(ds.equals(dbvo.getDatasource()))
						bfound=true;
				}
				// if not found, add to datasource usage map
				if(bfound==false){
					dataSourceConnectionsUsed.add(dbvo.getDatasource());
					bUpdateContext=true;
				}
			}
			if(bUpdateContext==true)
				((GlobalCacheVo)context.getBean("globalCacheVo")).setDataSourceConnectionsUsed(dataSourceConnectionsUsed);
			
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_SITE_DATABASE_LIST");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);

			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(vos);
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("NO"); 
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#connectToSiteDatabase(gov.nwcg.isuite.core.vo.DbAvailVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo connectToSiteDatabase(DbAvailVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo)dialogueVo=new DialogueVo();

		try{
			UserSessionVo v = (UserSessionVo)context.getBean("userSessionVo");
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatabaseName(vo.getDatabaseName());
			((UserSessionVo)context.getBean("userSessionVo")).setSiteDatasourceName(vo.getDatasource());

			// Verify Site System 
			String systemCheck=this.siteSystemCheck(vo);

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("CONNECT_TO_SITE_DATABASE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	private String siteSystemCheck(DbAvailVo vo) {
		String result="";
		String nwcgPath="c:/NWCG/e-ISuite/";

		try{
			/*
			SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

			SystemParameter entity = spDao.getByParameterName("PG_BASE_DIR");
			if(null != entity){
				entity.setParameterValue(nwcgPath+"pgsql/");
				spDao.setSkipSetAuditInfo(true);
				spDao.save(entity);
				spDao.setSkipSetAuditInfo(false);
				spDao.flushAndEvict(entity);
			}
			*/
			
			// site incident group default iap settings
			IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
			igDao.checkCreateSiteGroupIap();

			String dbName=super.getUserSessionDbName();
			
			// check site training
			if(EISuiteCalendar.isTrainingMode==true 
					&& EISuiteCalendar.hasDbCalendarDate(dbName)==false){
				// set default training calendar date from database
				String trainingDate=super.getSystemParamValue(SystemParameterTypeEnum.TRAINING_SYSTEM_DATE);
				if(StringUtility.hasValue(trainingDate)){
					Date dt=DateUtil.toDate(trainingDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					EISuiteCalendar.setDbCalendarDate(dbName, dt);
				}
			}
		}catch(Exception e){
			result=e.getMessage();
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getSessionTimout()
	 */
	public String getSessionTimeout() throws Exception {

		try{
			return super.getSystemParamValue(SystemParameterTypeEnum.SESSION_TIMEOUT);

		}catch(Exception e){
			super.handleException(e);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getSystemRoles()
	 */
	public Collection<SystemRoleVo> getSystemRoles() throws Exception {
		return super.getGlobalCacheVo().getSystemRoleVos();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getPrivilegedSystemRoles()
	 */
	public Collection<SystemRoleVo> getPrivilegedSystemRoles() throws Exception {
		Collection<SystemRoleVo> vos = new ArrayList<SystemRoleVo>();

		for(SystemRoleVo vo : super.getGlobalCacheVo().getSystemRoleVos()){
			if(vo.getPrivilegedRole())
				vos.add(vo);
		}

		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getNonPrivilegedSystemRoles()
	 */
	public Collection<SystemRoleVo> getNonPrivilegedSystemRoles() throws Exception{
		Collection<SystemRoleVo> vos = new ArrayList<SystemRoleVo>();

		for(SystemRoleVo vo : super.getGlobalCacheVo().getSystemRoleVos()){
			if(!vo.getPrivilegedRole())
				vos.add(vo);
		}

		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getAdminUserStatus()
	 */
	public String getAdminUserStatus() throws Exception {

		try{
			String siteAdminUser=null;

			try{
				siteAdminUser=super.getSystemParamValue(SystemParameterTypeEnum.SITE_ADMIN_USER);
			}catch(ServiceException se){
				// ignore
			}

			if(null == siteAdminUser)
				return "NA";
			else
				return "OK";
		}catch(Exception e){
			super.handleException(e);
		}

		return "NA";
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getRequestCategories()
	 */
	public Collection<RequestCategoryVo> getRequestCategories() throws Exception {
		Collection<RequestCategoryVo> vos = new ArrayList<RequestCategoryVo>();

		vos = super.getGlobalCacheVo().getRequestCategoryVos();

		return vos;
	}
	
	public Collection<ComplexityVo> getComplexityVos() throws Exception {
		Collection<ComplexityVo> vos = new ArrayList<ComplexityVo>();
		
		vos = super.getGlobalCacheVo().getComplexityVos();
		
		return vos;
	}
	
	public Collection<RecommendationVo> getRecommendationVos() throws Exception {
		Collection<RecommendationVo> vos = new ArrayList<RecommendationVo>();
		
		vos = super.getGlobalCacheVo().getRecommendationVos();
		
		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getAssignmentStatuses()
	 */
	public Collection<AssignmentStatusVo> getAssignmentStatuses() throws Exception {
		Collection<AssignmentStatusVo> vos = new ArrayList<AssignmentStatusVo>();

		vos = AssignmentStatusTypeEnum.getAssignmentVoList(false);

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getAssignmentStatuses()
	 */
	public Collection<AdjustmentTypeVo> getAdjustmentTypes() throws Exception {
		Collection<AdjustmentTypeVo> vos = new ArrayList<AdjustmentTypeVo>();

		vos = AdjustmentTypeEnum.getAdjustmentTypeList();

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getRateTypes()
	 */
	public Collection<RateTypeVo> getRateTypes() throws Exception {
		Collection<RateTypeVo> vos = new ArrayList<RateTypeVo>();

		vos = RateTypeEnum.getRateTypeList();

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getSystemTypes()
	 */
	public Collection<SystemTypeVo> getSystemTypes() throws Exception {
		Collection<SystemTypeVo> vos = new ArrayList<SystemTypeVo>();

		vos = SystemTypeEnum.getSystemTypeList();

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getSections()
	 */
	public Collection<SectionVo> getSections() throws Exception {
		Collection<SectionVo> vos = new ArrayList<SectionVo>();

		vos = IapSectionEnum.getSectionList();

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getUnitsOfMeasure()
	 */
	public Collection<UnitOfMeasureVo> getUnitsOfMeasure() throws Exception {
		Collection<UnitOfMeasureVo> vos = new ArrayList<UnitOfMeasureVo>();

		vos = UnitOfMeasureEnum.getUnitOfMeasureTypeList();

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getAssignmentStatuses()
	 */
	public Collection<EmploymentTypeVo> getEmploymentTypes() throws Exception {
		Collection<EmploymentTypeVo> vos = new ArrayList<EmploymentTypeVo>();

		vos = EmploymentTypeEnum.getEmploymentTypeList();

		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getIncidentRestrictedStatuses()
	 */
	public Collection<IncidentRestrictedStatusVo> getIncidentRestrictedStatuses() throws Exception {
		Collection<IncidentRestrictedStatusVo> vos = new ArrayList<IncidentRestrictedStatusVo>();

		vos = IncidentRestrictedStatusTypeEnum.getIncidentRestrictedStatusVoList();

		return vos;
	}

	public Collection<ResourceClassificationVo> getResourceClassifications() throws Exception {
		Collection<ResourceClassificationVo> vos = ResourceClassificationEnum.getResourceClassificationVoList();

		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getRegionCodes()
	 */
	public Collection<RegionCodeVo> getRegionCodes() throws Exception {
		return super.getGlobalCacheVo().getRegionCodeVos();
	}

	public Collection<SpecialPayVo> getSpecialPays() throws Exception {
		return super.getGlobalCacheVo().getSpecialPayVos();
	}

	public Collection<AdjustCategoryVo> getAdjustCategories() throws Exception {
		return super.getGlobalCacheVo().getAdjustCategoryVos();
	}

	public Collection<RateAreaVo> getRateAreaVos() throws Exception {
		return RateAreaEnum.getRateAreaList(this.getRateClassRates());
	}

	public Collection<RateClassVo> getRateClasses() throws Exception{
		try{

			RateClassDao rateClassDao = (RateClassDao)super.context.getBean("rateClassDao");

			return rateClassDao.getPicklist();
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}

	public Collection<RateClassRateVo> getRateClassRates() throws Exception{
		try{
//			SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");
			String defaultRateClassId = super.getSystemParamValue(SystemParameterTypeEnum.DEFAULT_RATE_CLASS_ID);

			RateClassDao rateClassDao = (RateClassDao)super.context.getBean("rateClassDao");
			RateClass entity = rateClassDao.getById(TypeConverter.convertToLong(defaultRateClassId));
			Collection<RateClassRateVo> rcrVos = new ArrayList<RateClassRateVo>();
			
			if(null != entity) {
				for(RateClassRate rcr : entity.getRateClassRates()) {
					if(StringBooleanEnum.toBooleanValue(rcr.isActive())) {
						if(rcr.getArea().equalsIgnoreCase("CONUS")) {
							rcrVos.add(RateClassRateVo.getInstance(rcr, true));
						}
					}
				}
			}
			
			return rcrVos;
			
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}

	public Collection<SystemParameterVo> getSystemParams() throws Exception {
		try{

			Collection<SystemParameterVo> vos = new ArrayList<SystemParameterVo>();

			SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");

			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_XML_URL_PATH.name());
			SystemParameter spEntity2 = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_UPLOAD_SERVLET_URL.name());
			SystemParameter userSessionPingInterval = spDao.getByParameterName(SystemParameterTypeEnum.USER_SESSION_PING_INTERVAL.name());
			SystemParameter sessionTimeOutWarning = spDao.getByParameterName(SystemParameterTypeEnum.SESSION_TIMEOUT_WARNING.name());
			SystemParameter sessionTimeOut = spDao.getByParameterName(SystemParameterTypeEnum.SESSION_TIMEOUT.name());
			SystemParameter sessionTimeOutTick = spDao.getByParameterName(SystemParameterTypeEnum.SESSION_TIMEOUT_TICK.name());

			if(null != spEntity)vos.add(SystemParameterVo.getInstance(spEntity, true));
			if(null != spEntity2)vos.add(SystemParameterVo.getInstance(spEntity2, true));
			if(null != userSessionPingInterval) {
				vos.add(SystemParameterVo.getInstance(userSessionPingInterval, true));
			}
			if(null != sessionTimeOutWarning) {
				vos.add(SystemParameterVo.getInstance(sessionTimeOutWarning, true));
			}
			if(null != sessionTimeOut) {
				vos.add(SystemParameterVo.getInstance(sessionTimeOut, true));
			}
			if(null != sessionTimeOutTick) {
				vos.add(SystemParameterVo.getInstance(sessionTimeOutTick, true));
			}

			return vos;
		}catch(Exception e){
			super.handleException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getAccrualCodes()
	 */
	public Collection<AccrualCodeVo> getAccrualCodes() throws Exception {
		return super.getGlobalCacheVo().getAccrualCodeVos();
	}

	public Collection<Integer> getRateYears() throws Exception {
		Collection<Integer> rateYears = new ArrayList<Integer>();

		RateClassDao dao = (RateClassDao)context.getBean("rateClassDao");

		rateYears = dao.getRateYears();

		return rateYears;

	}

	public GlobalCacheVo getGlobalCache() throws ServiceException {
		return super.getGlobalCacheVo();
	}

	public StaticDataWrapperVo getStaticDataWrapper() throws ServiceException {
		String lastLoad="";
		
		StaticDataWrapperVo vo =  StaticDataWrapperVo.getInstance(super.getGlobalCacheVo());
		lastLoad="1";
		try{
			vo.setRateClassVos(this.getRateClasses());
			lastLoad="2";
			vo.setRateClassRateVos(this.getRateClassRates());
			lastLoad="3";
			vo.setRateAreaVos(this.getRateAreaVos());
			lastLoad="4";
			vo.setRateTypes(this.getRateTypes());
			lastLoad="5";
			vo.setUnitsOfMeasure(this.getUnitsOfMeasure());
			lastLoad="6";
			vo.setSysParamVos(this.getSystemParams());
			lastLoad="7";
			vo.setSystemTypeVos(this.getSystemTypes());
			lastLoad="8";
			vo.setSectionVos(this.getSections());
			lastLoad="9";
			vo.setTrainingVo(getTrainingVo());
		}catch(Exception e){
			this.log(Level.ERROR, "[Last Load: " + lastLoad + "] " +e.getMessage());
			// smother
		}
		return vo;
	}

	public StaticDataWrapperVo2 getStaticDataWrapper2() throws ServiceException {
		String lastLoad="";
		StaticDataWrapperVo2 vo = null; 
		
		try{
			vo=StaticDataWrapperVo2.getInstance(super.getGlobalCacheVo());
			lastLoad="1";
			/*
			vo.setRateClassVos(this.getRateClasses());
			lastLoad="2";
			vo.setRateClassRateVos(this.getRateClassRates());
			lastLoad="3";
			vo.setRateAreaVos(this.getRateAreaVos());
			lastLoad="4";
			vo.setRateTypes(this.getRateTypes());
			lastLoad="5";
			vo.setUnitsOfMeasure(this.getUnitsOfMeasure());
			lastLoad="6";
			vo.setSysParamVos(this.getSystemParams());
			lastLoad="7";
			vo.setSystemTypeVos(this.getSystemTypes());
			lastLoad="8";
			vo.setSectionVos(this.getSections());
			lastLoad="9";
			vo.setTrainingVo(getTrainingVo());
		*/
		}catch(Exception e){
			this.log(Level.ERROR, "[Last Load: " + lastLoad + "] " +e.getMessage());
			// smother
		}
		return vo;
	}

	public DialogueVo getNonPdcOrgs(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{

			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_NON_PDC_ORGS");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(super.getGlobalCacheVo().getNonPDCOrgs());

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo getIncidentRefData(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		//System.out.println("getIncidentRefData() Start - " + Calendar.getInstance().getTime());
		try{
			Collection<Long> incidentIds = new ArrayList<Long>();
			if(LongUtility.hasValue(incidentId)){
				// determine if incident is part of a group
				IncidentDao idao = (IncidentDao)context.getBean("incidentDao");
				Long incGroupId=idao.getIncidentGroupId(incidentId);
				if(LongUtility.hasValue(incGroupId)){
					incidentId=null;
					incidentGroupId=incGroupId;
				}
			}
			if(LongUtility.hasValue(incidentGroupId)){
				// get all incidentids for group
				IncidentGroupDao igdao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incidentIds=igdao.getIncidentIdsInGroup(incidentGroupId);
			}

			// build agencyvos
			Collection<AgencyVo> agencyVos = new ArrayList<AgencyVo>();
			for(AgencyVo agVo : super.getGlobalCacheVo().getAgencyVos()){
				if(BooleanUtility.isTrue(agVo.getActive())){
					if(BooleanUtility.isTrue(agVo.getStandard())){
						agencyVos.add(agVo);
					}else{
						if(LongUtility.hasValue(incidentId) && null != agVo.getIncidentVo()){
							if(incidentId.compareTo(agVo.getIncidentVo().getId())==0)
								agencyVos.add(agVo);
						}

						if(LongUtility.hasValue(incidentGroupId) && null != agVo.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(agVo.getIncidentVo().getId())==0)
									agencyVos.add(agVo);
							}
						}
					}
				}
			}
			
			// add all standard orgs and all non-standard for the inc/group
			Collection<OrganizationVo> orgVos = new ArrayList<OrganizationVo>();
			for(OrganizationVo v : super.getGlobalCacheVo().getOrganizationVos()){
				//if(BooleanUtility.isTrue(v.getActive()) && BooleanUtility.isFalse(v.getDispatchCenter())){
				if(BooleanUtility.isTrue(v.getActive())) {
					if(BooleanUtility.isTrue(v.getStandard())){
						orgVos.add(v);
					}else{
						if(LongUtility.hasValue(incidentId) && null != v.getIncidentVo()){
							if(incidentId.compareTo(v.getIncidentVo().getId())==0)
								orgVos.add(v);
						}

						if(LongUtility.hasValue(incidentGroupId) && null != v.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(v.getIncidentVo().getId())==0)
									orgVos.add(v);
							}
						}
					}
				}
			}
			
			Collection<JetPortVo> jetPortVos = new ArrayList<JetPortVo>();
			for(JetPortVo vo : super.getGlobalCacheVo().getJetPortVos()){
				if(BooleanUtility.isTrue(vo.getActive())){
					if(BooleanUtility.isTrue(vo.getStandard())){
						jetPortVos.add(vo);
					}else{
						if(LongUtility.hasValue(incidentId) && null != vo.getIncidentVo()){
							if(incidentId.compareTo(vo.getIncidentVo().getId())==0)
								jetPortVos.add(vo);
						}
						
						if(LongUtility.hasValue(incidentGroupId) && null != vo.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(vo.getIncidentVo().getId())==0)
									jetPortVos.add(vo);
							}
						}
					}
				}
			}
			
			Collection<KindVo> kindVos = new ArrayList<KindVo>();
			for(KindVo vo : super.getGlobalCacheVo().getKindVos()){
				if(BooleanUtility.isTrue(vo.getActive())){
					if(BooleanUtility.isTrue(vo.getStandard())){
						kindVos.add(vo);
					}else{
						if(LongUtility.hasValue(incidentId) && null != vo.getIncidentVo()){
							if(incidentId.compareTo(vo.getIncidentVo().getId())==0)
								kindVos.add(vo);
						}
			
						if(LongUtility.hasValue(incidentGroupId) && null != vo.getIncidentVo()){
							for(Long incid : incidentIds){
								if(incid.compareTo(vo.getIncidentVo().getId())==0)
									kindVos.add(vo);
							}
						}
					}
				}
			}
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_INCIDENT_REF_DATA");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);

			//System.out.println("getIncidentRefData() Before Sort - " + Calendar.getInstance().getTime());
			
			Collections.sort((List)agencyVos, new Comparator<AgencyVo>(){public int compare(AgencyVo ob1, AgencyVo ob2){return ob1.getAgencyCd().compareTo(ob2.getAgencyCd());}});			
			Collections.sort((List)kindVos, new Comparator<KindVo>(){public int compare(KindVo ob1, KindVo ob2){return ob1.getCode().compareTo(ob2.getCode());}});			
			Collections.sort((List)jetPortVos, new Comparator<JetPortVo>(){public int compare(JetPortVo ob1, JetPortVo ob2){return ob1.getCode().compareTo(ob2.getCode());}});			
			Collections.sort((List)orgVos, new Comparator<OrganizationVo>(){public int compare(OrganizationVo ob1, OrganizationVo ob2){return ob1.getUnitCode().compareTo(ob2.getUnitCode());}});			
			
			//System.out.println("getIncidentRefData() After Sort - " + Calendar.getInstance().getTime());
			
			dialogueVo.setResultObject(agencyVos);
			dialogueVo.setResultObjectAlternate(kindVos);
			dialogueVo.setResultObjectAlternate2(jetPortVos);
			dialogueVo.setResultObjectAlternate3(orgVos);

			//System.out.println("getIncidentRefData() End - " + Calendar.getInstance().getTime());
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#getLastRecoverCode(java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getLastRecoverCode(String dbName, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			String lastCode="";
			
			// get last code for the dbname from the dbavail table
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			DbAvailDao dbAvailDao = (DbAvailDao)context.getBean("dbAvailDao");
			DbAvail entity = dbAvailDao.getByDatabaseName(dbName.toLowerCase());
			if(null != entity){
				// if lastrecovercode is not empty - use it, otherwise create a new one
				if(StringUtility.hasValue(entity.getLastRecoverCode())){
					lastCode=entity.getLastRecoverCode();
				}else{
					String newCodeEncoded=RecoverAccountUtil.generateRecoverCode(entity);
					entity.setLastRecoverCode(newCodeEncoded);
					lastCode=newCodeEncoded;
					dbAvailDao.save(entity);
				}
				dbAvailDao.flushAndEvict(entity);
			}
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_LAST_RECOVER_CODE");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setResultObject(lastCode);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.SystemService#authenticateRecoverCode(java.lang.String, java.lang.String, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo authenticateRecoverCode(String dbName,String authCode, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			String result="FAIL";

			// get last code for the dbname from the dbavail table
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			DbAvailDao dbAvailDao = (DbAvailDao)context.getBean("dbAvailDao");
			DbAvail entity = dbAvailDao.getByDatabaseName(dbName.toLowerCase());
			if(null != entity){
				String lastCode=entity.getLastRecoverCode();
				
				if(RecoverAccountUtil.isSiteKeyAuthentic(dbName, lastCode, authCode)){
					result="SUCCESS";
					// reset lastRecoverCode
					entity.setLastRecoverCode("");
					dbAvailDao.save(entity);
				}
				dbAvailDao.flushAndEvict(entity);
			}
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

			if(result.equals("FAIL")){
				String msg="Either the Access Key is invalid, was previously used, or the wrong database was selected. Please enter a valid Access Key, generate a new Access Key, or select the correct database.";
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("validationerror");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				ErrorObject errorObject = new ErrorObject("error.800000",new String[]{msg});
				coaVo.getErrorObjectVos().add(errorObject);

				dialogueVo.setCourseOfActionVo(coaVo);
				
				return dialogueVo;
				
			}else{
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName("AUTHENTICATE_RECOVER_CODE");
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
				courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				dialogueVo.setResultObject(result);
			}

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}

	public DialogueVo saveSystemTraining(TrainingSystemVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
}
