package gov.nwcg.isuite.core.spring;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.filter.impl.AgencyFilterImpl;
import gov.nwcg.isuite.core.filter.impl.CountryCodeFilterImpl;
import gov.nwcg.isuite.core.filter.impl.EventTypeFilterImpl;
import gov.nwcg.isuite.core.filter.impl.KindFilterImpl;
import gov.nwcg.isuite.core.persistence.AccrualCodeDao;
import gov.nwcg.isuite.core.persistence.AdjustCategoryDao;
import gov.nwcg.isuite.core.persistence.AgencyDao;
import gov.nwcg.isuite.core.persistence.AgencyGroupDao;
import gov.nwcg.isuite.core.persistence.ComplexityDao;
import gov.nwcg.isuite.core.persistence.CountryCodeDao;
import gov.nwcg.isuite.core.persistence.CountryCodeSubdivisionDao;
import gov.nwcg.isuite.core.persistence.DailyFormDao;
import gov.nwcg.isuite.core.persistence.DepartmentDao;
import gov.nwcg.isuite.core.persistence.EventTypeDao;
import gov.nwcg.isuite.core.persistence.FontDao;
import gov.nwcg.isuite.core.persistence.GroupCategoryDao;
import gov.nwcg.isuite.core.persistence.JetPortDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.persistence.OrganizationDao;
import gov.nwcg.isuite.core.persistence.RateGroupDao;
import gov.nwcg.isuite.core.persistence.RecommendationDao;
import gov.nwcg.isuite.core.persistence.RegionCodeDao;
import gov.nwcg.isuite.core.persistence.RequestCategoryDao;
import gov.nwcg.isuite.core.persistence.Sit209Dao;
import gov.nwcg.isuite.core.persistence.SpecialPayDao;
import gov.nwcg.isuite.core.persistence.SubGroupCategoryDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.SystemRoleDao;
import gov.nwcg.isuite.core.persistence.TaskQueueDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.SystemVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.core.persistence.DatasourceConfiguration;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class GlobalCacheVoPostProcessor implements BeanPostProcessor,ApplicationContextAware {
	private static final Logger LOG = Logger.getLogger(GlobalCacheVoPostProcessor.class);
	private static boolean runOnce=false;
	protected ApplicationContext context;
	protected GlobalCacheMap globalCacheMap = null;
	protected UserSessionVo userSessionVo=null;
	private SystemParameterDao systemParameterDao=null;
	private SystemRoleDao systemRoleDao=null;
	private Sit209Dao sit209Dao=null;
	private JetPortDao jetPortDao=null;
	private KindDao kindDao = null;
	private EventTypeDao eventTypeDao = null;
	private AgencyDao agencyDao = null;
	private AgencyGroupDao agencyGroupDao = null;
	private RequestCategoryDao requestCategoryDao=null;
	private OrganizationDao organizationDao = null;
	private CountryCodeDao countryCodeDao = null;
	private CountryCodeSubdivisionDao countryCodeSubdivisionDao = null;
	private RegionCodeDao regionCodeDao = null;
	private SpecialPayDao specialPayDao = null;
	private AdjustCategoryDao adjustCategoryDao = null;
	private AccrualCodeDao accrualCodeDao = null;
	private DepartmentDao departmentDao = null;
	private DailyFormDao dailyFormDao = null;
	private GroupCategoryDao groupCategoryDao = null;
	private SubGroupCategoryDao subGroupCategoryDao = null;
	private RateGroupDao rateGroupDao = null;
	private FontDao fontDao = null;
	private ComplexityDao complexityDao = null;
	private RecommendationDao recommendationDao = null;

	public static GlobalCacheVo tempGcVo = null;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//System.out.println("GlobalCacheVoPostProcessor.postProcessAfterInitialization() " + beanName);
		Boolean bmatch=false;
		
		if(bean.getClass().getName().startsWith(GlobalCacheVo.class.getName()))
			bmatch=true;
		
		//System.out.println(bean.getClass().getName());
		
		if(bean.getClass().getName().equals(GlobalCacheVo.class.getName()) || bmatch==true){
			String dbName = "";
			GlobalCacheVo newBean = new GlobalCacheVo();
			
			if(DatasourceConfiguration.isLoading==true){
				dbName=DatasourceConfiguration.dbname;
				if(this.globalCacheMap.hasVo(dbName)){
					bean = this.globalCacheMap.getVo(dbName);
					return bean;
				}else{
					/*
					 * Load the global cache objects
					 */
					try{
						if(runOnce==false){
							resetAutoBackupTask(dbName);
							runOnce=true;
						}
						this.setServerURL();
						this.loadSystemInfo(newBean,dbName);
						this.loadSystemRoles(newBean);
						this.loadRequestCategories(newBean);
						//this.loadRoles(newBean);
						this.loadOrganizations(newBean);
						this.loadAgencies(newBean);
						this.loadAgencyGroups(newBean);
						this.loadCountryCodeVos(newBean);
						this.loadCountryCodeSubdivisionVos(newBean);
						this.loadEventTypes(newBean);
						this.loadSit209s(newBean);
						this.loadJetPorts(newBean);
						this.loadKinds(newBean);
						this.loadRegionCodes(newBean);
						this.loadSpecialPay(newBean);
						this.loadAdjustCategories(newBean);
						this.loadAccrualCodes(newBean);
						this.loadDepartments(newBean);
						this.loadDailyForms(newBean);
						this.loadGroupCategories(newBean);
						this.loadSubGroupCategories(newBean);
						this.loadRateGroups(newBean);
						this.loadFonts(newBean);
						this.loadComplexityVos(newBean);
						this.loadRecommendationVos(newBean);
						newBean.setLoaded(Boolean.TRUE);
						
						// add to map
						this.globalCacheMap.addVo(dbName, newBean);
						
						return newBean;
					}catch(Exception e){
						throw new RuntimeException(e);
					}
				}
				
			}
			
			// does this map already exists for datasource?
			if(null != userSessionVo && null != userSessionVo.getSiteDatabaseName()){
				dbName=userSessionVo.getSiteDatabaseName();
				if(this.globalCacheMap.hasVo(dbName)){
					bean = this.globalCacheMap.getVo(dbName);
					return bean;
				}else{
					/*
					 * Load the global cache objects
					 */
					try{
						
						this.setServerURL();
						this.loadSystemInfo(newBean,dbName);
						this.loadSystemRoles(newBean);
						this.loadRequestCategories(newBean);
						//this.loadRoles(newBean);
						this.loadOrganizations(newBean);
						this.loadAgencies(newBean);
						this.loadAgencyGroups(newBean);
						this.loadCountryCodeVos(newBean);
						this.loadCountryCodeSubdivisionVos(newBean);
						this.loadEventTypes(newBean);
						this.loadSit209s(newBean);
						this.loadJetPorts(newBean);
						this.loadKinds(newBean);
						this.loadRegionCodes(newBean);
						this.loadSpecialPay(newBean);
						this.loadAdjustCategories(newBean);
						this.loadAccrualCodes(newBean);
						this.loadDepartments(newBean);
						this.loadDailyForms(newBean);
						this.loadGroupCategories(newBean);
						this.loadSubGroupCategories(newBean);
						this.loadRateGroups(newBean);
						this.loadFonts(newBean);
						this.loadComplexityVos(newBean);
						this.loadRecommendationVos(newBean);
						newBean.setLoaded(Boolean.TRUE);
						
						// add to map
						this.globalCacheMap.addVo(dbName, newBean);
					}catch(Exception e){
						throw new RuntimeException(e);
					}
				}
			}else{
				if(null != GlobalCacheVoPostProcessor.tempGcVo){
					return GlobalCacheVoPostProcessor.tempGcVo;
				}
				
				/*
				 * Load the global cache objects
				 */
				try{
					this.loadSystemInfo(newBean,"");
					this.loadSystemRoles(newBean);
					this.loadRequestCategories(newBean);
					//this.loadRoles(newBean);
					this.loadOrganizations(newBean);
					this.loadAgencies(newBean);
					this.loadAgencyGroups(newBean);
					this.loadCountryCodeVos(newBean);
					this.loadCountryCodeSubdivisionVos(newBean);
					this.loadEventTypes(newBean);
					this.loadJetPorts(newBean);
					this.loadKinds(newBean);
					this.loadRegionCodes(newBean);
					this.loadSpecialPay(newBean);
					this.loadAdjustCategories(newBean);
					this.loadAccrualCodes(newBean);
					this.loadDepartments(newBean);
					this.loadDailyForms(newBean);
					this.loadGroupCategories(newBean);
					this.loadSubGroupCategories(newBean);
					this.loadRateGroups(newBean);
					this.loadFonts(newBean);
					this.loadComplexityVos(newBean);
					this.loadRecommendationVos(newBean);
					newBean.setLoaded(Boolean.TRUE);

					GlobalCacheVoPostProcessor.tempGcVo=newBean;
					
					// add to map
					if(StringUtility.hasValue(dbName))
						this.globalCacheMap.addVo(dbName, newBean);
				}catch(Exception e){
					throw new RuntimeException(e);
				}
			}
			

			return newBean;
		}

		return bean;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	/**
	 * Sets the SystemRoleDao
	 * 
	 * @param srDao
	 * 		the systemRoleDao to set
	 */
	public void setSystemRoleDao(SystemRoleDao srDao) {
		this.systemRoleDao = srDao;
	}

	/**
	 * @param sit209Dao the sit209Dao to set
	 */
	public void setSit209Dao(Sit209Dao sit209Dao) {
		this.sit209Dao = sit209Dao;
	}

	/**
	 * @return the sit209Dao
	 */
	public Sit209Dao getSit209Dao() {
		return sit209Dao;
	}
	
	/**
	 * Sets the jetPortDao.
	 *
	 * @param jetPortDao 
	 *			the jetPortDao to set
	 */
	public void setJetPortDao(JetPortDao jetPortDao) {
		this.jetPortDao = jetPortDao;
	}

	/**
	 * @param departmentDao
	 */
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	private void loadSystemInfo(GlobalCacheVo bean,String dbName) throws Exception {
		SystemVo vo = (SystemVo) context.getBean("systemVo");

		// set default values
		vo.setParameter(SystemParameterTypeEnum.RUN_MODE.name(), "ENTERPRISE");
		vo.setParameter(SystemParameterTypeEnum.SESSION_TIMEOUT.name(), "30000");
		vo.setParameter(SystemParameterTypeEnum.SESSION_TIMEOUT_WARNING.name(), "27000");
		vo.setParameter(SystemParameterTypeEnum.STALE_SESSION_SCHEDULE.name(), "5");
		vo.setParameter(SystemParameterTypeEnum.STALE_SESSION_AGE.name(), "5");

		Boolean isTrainingMode=false;
		Date systemTrainingDate=null;

		// set database values
		Collection<SystemParameter> entities = systemParameterDao.getGrid();
		for (SystemParameter sp : entities) {
			vo.setParameter(sp.getParameterName(), sp.getParameterValue());
			
			if(sp.getParameterName().equals(SystemParameterTypeEnum.TRAINING_MODE.name())){
				if(StringUtility.hasValue(sp.getParameterValue())){
					if(sp.getParameterValue().equals("1")){
						isTrainingMode=true;
						EISuiteCalendar.isTrainingMode=true;
					}
				}
			}
			
			if(sp.getParameterName().equals(SystemParameterTypeEnum.TRAINING_SYSTEM_DATE.name())){
				if(null != sp.getParameterValue()){
					if(!sp.getParameterValue().equals("0")){
						String dt=sp.getParameterValue();
						
						systemTrainingDate=DateUtil.toDate(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY);
						EISuiteCalendar.setDbCalendarDate(dbName, systemTrainingDate);
					}
				}
			}
		}

		if(isTrainingMode==true && null != systemTrainingDate){
			EISuiteCalendar.setCalendarDate(systemTrainingDate);
			EISuiteCalendar.hasTrainingDate=true;
		}
		
		// load flex side specific variables
		vo.setSessionTimeout(Integer.valueOf((vo.getParameter(SystemParameterTypeEnum.SESSION_TIMEOUT.name()))));
		vo.setSessionTimeoutTick(Integer.valueOf((vo.getParameter(SystemParameterTypeEnum.SESSION_TIMEOUT_TICK.name()))));
		vo.setSessionTimeoutWarning(Integer.valueOf((vo.getParameter(SystemParameterTypeEnum.SESSION_TIMEOUT_WARNING.name()))));
		vo.setUserSessionPingInterval(Integer.valueOf((vo.getParameter(SystemParameterTypeEnum.USER_SESSION_PING_INTERVAL.name()))));
		vo.setAutoPopulateGrids(Boolean.valueOf((vo.getParameter(SystemParameterTypeEnum.AUTO_POPULATE_GRIDS.name()))));
		
		bean.setSystemVo(vo);
	}

	private void loadSystemRoles(GlobalCacheVo bean) throws Exception {
		Collection<SystemRole> sroles = systemRoleDao.getAllRoles();
		bean.setSystemRoleVos(SystemRoleVo.getInstances(sroles,true));
	}

	private void loadCountryCodeVos(GlobalCacheVo bean) throws Exception {
		bean.setCountryCodeVos(countryCodeDao.getPicklist(new CountryCodeFilterImpl()));
	}

	private void loadRequestCategories(GlobalCacheVo bean) throws Exception {
		Collection<RequestCategoryVo> rcVos = requestCategoryDao.getAll();
		bean.setRequestCategoryVos(rcVos);
	}
	
	private void loadComplexityVos(GlobalCacheVo bean) throws Exception {
		bean.setComplexityVos(complexityDao.getPicklist());
	}
	
	private void loadRecommendationVos(GlobalCacheVo bean) throws Exception {
		bean.setRecommendationVos(recommendationDao.getPicklist());
	}

	private void loadOrganizations(GlobalCacheVo bean) throws Exception {
		Collection<Organization> orgs = organizationDao.getAll(null);
		if( (null != orgs) && (orgs.size()>1)){
			bean.setOrganizationVos(OrganizationVo.getInstances(orgs,true));

			for(Organization org : orgs){
				if(!org.isDispatchCenter()){
					bean.getNonPDCOrgs().add(OrganizationVo.getInstance(org, true));
				}else
					bean.getPdcVos().add(OrganizationVo.getInstance(org,true));
			}
		}
	}

	private void loadAgencies(GlobalCacheVo bean) throws Exception {
		bean.setAgencyVos(agencyDao.getAgencies(new AgencyFilterImpl()));
	}

	private void loadAgencyGroups(GlobalCacheVo bean) throws Exception {
		bean.setAgencyGroupVos(agencyGroupDao.getPicklist());
	}

	private void loadCountryCodeSubdivisionVos(GlobalCacheVo bean) throws Exception {
		bean.setCountryCodeSubdivisionVos(countryCodeSubdivisionDao.getPicklist(null));
	}

	private void loadEventTypes(GlobalCacheVo bean) throws Exception {
		bean.setEventTypeVos(eventTypeDao.getPicklist(new EventTypeFilterImpl()));
	}

	private void loadKinds(GlobalCacheVo bean) throws Exception {
		bean.setKindVos(kindDao.getPicklist(new KindFilterImpl()));
	}

	private void loadJetPorts(GlobalCacheVo bean) throws Exception {
		bean.setJetPortVos(jetPortDao.getPicklist());
	}

	private void loadSit209s(GlobalCacheVo bean) throws Exception {
		bean.setSit209CodeVos(sit209Dao.getPicklist());
	}
	
	
	private void loadDepartments(GlobalCacheVo bean) throws Exception {
		bean.setDepartmentVos(departmentDao.getPicklist());
	}

	private void loadDailyForms(GlobalCacheVo bean) throws Exception {
		bean.setDailyFormVos(dailyFormDao.getPicklist());
	}

	private void loadGroupCategories(GlobalCacheVo bean) throws Exception {
		bean.setGroupCategoryVos(groupCategoryDao.getPicklist());
	}

	private void loadSubGroupCategories(GlobalCacheVo bean) throws Exception {
		bean.setSubGroupCategoryVos(subGroupCategoryDao.getPicklist());
	}

	private void loadRegionCodes(GlobalCacheVo bean) throws Exception {
		bean.setRegionCodeVos(regionCodeDao.getRegionCodes());
	}

	private void loadSpecialPay(GlobalCacheVo bean) throws Exception {
		bean.setSpecialPayVos(specialPayDao.getPicklist(null));
	}

	private void loadAdjustCategories(GlobalCacheVo bean) throws Exception {
		bean.setAdjustCategoryVos(adjustCategoryDao.getPicklist(null));
	}

	private void loadAccrualCodes(GlobalCacheVo bean) throws Exception {
		bean.setAccrualCodeVos(accrualCodeDao.getPicklist());
	}

	private void loadRateGroups(GlobalCacheVo bean) throws Exception {
		bean.setRateGroupVos(rateGroupDao.getPicklist());
	}

	private void loadFonts(GlobalCacheVo bean) throws Exception {
		bean.setFontVos(fontDao.getPicklist());
	}

	/**
	 * Sets the kindDao.
	 *
	 * @param kindDao 
	 *			the kindDao to set
	 */
	public void setKindDao(KindDao kindDao) {
		this.kindDao = kindDao;
	}

	/**
	 * Sets the eventTypeDao.
	 *
	 * @param eventTypeDao 
	 *			the eventTypeDao to set
	 */
	public void setEventTypeDao(EventTypeDao eventTypeDao) {
		this.eventTypeDao = eventTypeDao;
	}

	/**
	 * Sets the agencyDao.
	 *
	 * @param agencyDao 
	 *			the agencyDao to set
	 */
	public void setAgencyDao(AgencyDao agencyDao) {
		this.agencyDao = agencyDao;
	}

	/**
	 * sets the agencyGroupDao.
	 * 
	 * @param agencyGroupDao
	 */
	public void setAgencyGroupDao(AgencyGroupDao agencyGroupDao) {
		this.agencyGroupDao = agencyGroupDao;
	}

	/**
	 * Sets the organizationDao.
	 *
	 * @param organizationDao 
	 *			the organizationDao to set
	 */
	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	/**
	 * Sets the countryCodeSubdivisionDao.
	 *
	 * @param countryCodeSubdivisionDao 
	 *			the countryCodeSubdivisionDao to set
	 */
	public void setCountryCodeSubdivisionDao(CountryCodeSubdivisionDao countryCodeSubdivisionDao) {
		this.countryCodeSubdivisionDao = countryCodeSubdivisionDao;
	}

	/**
	 * Sets the countryCodeDao.
	 *
	 * @param countryCodeDao 
	 *			the countryCodeDao to set
	 */
	public void setCountryCodeDao(CountryCodeDao countryCodeDao) {
		this.countryCodeDao = countryCodeDao;
	}

	/**
	 * Returns the systemParameterDao.
	 *
	 * @return 
	 *		the systemParameterDao to return
	 */
	public SystemParameterDao getSystemParameterDao() {
		return systemParameterDao;
	}

	/**
	 * Sets the systemParameterDao.
	 *
	 * @param systemParameterDao 
	 *			the systemParameterDao to set
	 */
	public void setSystemParameterDao(SystemParameterDao systemParameterDao) {
		this.systemParameterDao = systemParameterDao;
	}

	/**
	 * Sets the rqDao.
	 *
	 * @param rqDao 
	 *			the rqDao to set
	 */
	public void setRequestCategoryDao(RequestCategoryDao rqDao) {
		this.requestCategoryDao = rqDao;
	}

	/**
	 * @return the regionCodeDao
	 */
	public RegionCodeDao getRegionCodeDao() {
		return regionCodeDao;
	}

	/**
	 * @param regionCodeDao the regionCodeDao to set
	 */
	public void setRegionCodeDao(RegionCodeDao regionCodeDao) {
		this.regionCodeDao = regionCodeDao;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)throws BeansException {
		this.context=arg0;
	}

	public SpecialPayDao getSpecialPayDao() {
		return specialPayDao;
	}

	public void setSpecialPayDao(SpecialPayDao specialPayDao) {
		this.specialPayDao = specialPayDao;
	}

	public AdjustCategoryDao getAdjustCategoryDao() {
		return adjustCategoryDao;
	}

	public void setAdjustCategoryDao(AdjustCategoryDao adjustCategoryDao) {
		this.adjustCategoryDao = adjustCategoryDao;
	}

	/**
	 * @return the accrualCodeDao
	 */
	public AccrualCodeDao getAccrualCodeDao() {
		return accrualCodeDao;
	}

	/**
	 * @param accrualCodeDao the accrualCodeDao to set
	 */
	public void setAccrualCodeDao(AccrualCodeDao accrualCodeDao) {
		this.accrualCodeDao = accrualCodeDao;
	}

	/**
	 * @param dailyFormDao the dailyFormDao to set
	 */
	public void setDailyFormDao(DailyFormDao dailyFormDao) {
		this.dailyFormDao = dailyFormDao;
	}

	/**
	 * @return the dailyFormDao
	 */
	public DailyFormDao getDailyFormDao() {
		return dailyFormDao;
	}

	/**
	 * @param subGroupCategoryDao the subGroupCategoryDao to set
	 */
	public void setSubGroupCategoryDao(SubGroupCategoryDao subGroupCategoryDao) {
		this.subGroupCategoryDao = subGroupCategoryDao;
	}

	/**
	 * @return the subGroupCategoryDao
	 */
	public SubGroupCategoryDao getSubGroupCategoryDao() {
		return subGroupCategoryDao;
	}

	/**
	 * @param groupCategoryDao the groupCategoryDao to set
	 */
	public void setGroupCategoryDao(GroupCategoryDao groupCategoryDao) {
		this.groupCategoryDao = groupCategoryDao;
	}

	/**
	 * @return the groupCategoryDao
	 */
	public GroupCategoryDao getGroupCategoryDao() {
		return groupCategoryDao;
	}

	/**
	 * @param rateGroupDao the rateGroupDao to set
	 */
	public void setRateGroupDao(RateGroupDao rateGroupDao) {
		this.rateGroupDao = rateGroupDao;
	}

	/**
	 * @return the rateGroupDao
	 */
	public RateGroupDao getRateGroupDao() {
		return rateGroupDao;
	}
	
	/**
	 * @param fontDao the fontDao to set
	 */
	public void setFontDao(FontDao fontDao) {
		this.fontDao = fontDao;
	}

	/**
	 * @return the fontDao
	 */
	public FontDao getFontDao() {
		return fontDao;
	}

	/**
	 * @param complexityDao the complexityDao to set
	 */
	public void setComplexityDao(ComplexityDao complexityDao) {
		this.complexityDao = complexityDao;
	}

	/**
	 * @return the complexityDao
	 */
	public ComplexityDao getComplexityDao() {
		return complexityDao;
	}
	
	/**
	 * @param recommendationDao the recommendationDao to set
	 */
	public void setRecommendationDao(RecommendationDao recommendationDao) {
		this.recommendationDao = recommendationDao;
	}

	/**
	 * @return the recommendationDao
	 */
	public RecommendationDao getRecommendationDao() {
		return recommendationDao;
	}


	/**
	 * @return the globalCacheMap
	 */
	public GlobalCacheMap getGlobalCacheMap() {
		return globalCacheMap;
	}

	/**
	 * @param globalCacheMap the globalCacheMap to set
	 */
	public void setGlobalCacheMap(GlobalCacheMap globalCacheMap) {
		this.globalCacheMap = globalCacheMap;
	}

	/**
	 * @param userSessionVo the userSessionVo to set
	 */
	public void setUserSessionVo(UserSessionVo userSessionVo) {
		this.userSessionVo = userSessionVo;
	}

	/**
	 * @return the current IP address of the server or localhost on failure.
	 */
	public String getMyIP () {
		  InetAddress myip;
		  try {
	 
			myip = InetAddress.getLocalHost();
			return myip.getHostAddress();
	 
		  } catch (UnknownHostException e) {
			  //return "127.0.0.1"; 
			  return "localhost"; 
		  }
	}

	public void resetAutoBackupTask(String dbName) {
		if(StringUtility.hasValue(dbName)
				&& dbName.equalsIgnoreCase("isuite_site_master")){
			
			try{
				TaskQueueDao taskQueueDao = (TaskQueueDao) context.getBean("taskQueueDao");
				taskQueueDao.resetAutoBackup();
			}catch(Exception e){
				
			}
		}
	}
	
	public void setServerURL() {
		SystemParameter sysParamEntity = null;
		SystemParameterDao sysParamDao = (SystemParameterDao) context.getBean("systemParameterDao");

		Boolean devMode=false;  
		
		try {
			SystemParameter spDevMode = sysParamDao.getByParameterName(SystemParameterTypeEnum.DEVMODE.toString());
			if(null==spDevMode || spDevMode.getParameterValue().equals("1"))
				devMode=true;
			
			sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.RUN_MODE.toString());
			if (sysParamEntity.getParameterValue().equals("SITE") && devMode==false) {
				sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.PROXY_MODE.toString());
				if (sysParamEntity.getParameterValue().equals("FALSE")) {
					sysParamEntity = sysParamDao.getByParameterName(SystemParameterTypeEnum.REPORT_OUTPUT_URL.toString());
					sysParamEntity.setParameterValue("http://" + getMyIP()+ "/isuite/reportsoutput/");
					sysParamDao.setSkipSetAuditInfo(true);
					sysParamEntity.setCreatedById(null);
					sysParamEntity.setLastModifiedById(null);
					sysParamDao.save(sysParamEntity);
					sysParamDao.setSkipSetAuditInfo(false);
					sysParamDao.flushAndEvict(sysParamEntity);
				}
			}
		} catch (Exception e) {

		}
	}
	
}
