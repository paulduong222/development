package gov.nwcg.isuite.framework.core.persistence.hibernate;

import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.core.persistence.datefilter.CustomDateFilterFactory;
import gov.nwcg.isuite.framework.core.persistence.datefilter.ICustomDateFilter;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;
import gov.nwcg.isuite.framework.util.ClassCaseUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Level;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

/**
 * Support class for Hibernate based dao classes.
 * @author doug
 * 
 */
public class TransactionSupportImpl extends HibernateDaoSupport implements TransactionSupport, ApplicationContextAware {

	protected ApplicationContext context=null;
	
	private static final int MAX_RESULT_SIZE = 1000;

	protected String providerDialect="";
    
	protected Boolean skipSetAuditInfo=false;
	protected Boolean skipFixCasing=false;
	
	protected LoggingInterceptor logger = null;
	
	public void setProviderDialect(String type){
		providerDialect=type;
	}

	public Boolean isOracleDialect(){
		if(providerDialect.indexOf("Oracle") > 0)
			return true;
		else
			return false;
			
	}
	
	protected Object getBooleanComparison(Boolean val) {
		if(isOracleDialect()){
			if(val)
				return 1;
			else
				return 0;
		}else{
			return val;
		}
	}

	protected String getDateStringComparison(String val) {
		if(isOracleDialect()){
			return "TO_DATE('"+val+"','MM/DD/YYYY')";
		}else{
			return "'"+val+"'";
		}
	}
	
   /**
    * Return the maximum number of items in a result set.
    * @return the maximum number of items in a result set.
    */
   public int getMaxResultSize() {
      return MAX_RESULT_SIZE;
   }
   
   /**
    * Helper method to get the hibernate session
    * @return hibernate session
    * @throws PersistenceException if needed
    */
   public Session getHibernateSession() throws PersistenceException {
      try {
          return getSession();
      }
      catch (Throwable t) {
         throw new PersistenceException("could not get session", t);
      }
   }

   /**
    * Clean strings handed in to avoid sql-injections.
    * 
    * @param sql
    * @return
    */
   protected String createSafeString(String sql) {
      String ret;
      if ( sql.contains("'") ) {
         ret = sql.replaceAll("'", "''");
      }
      else {
         ret = sql;
      }
      return ret;
   }

   /**
    * Determine whether an "and" or a "where" should go into the SQL clause
    * @param sql
    * @param where
    * @return 
    */
   protected boolean where(StringBuffer sql, boolean where) {
      sql.append(where ? " and " : " where ");
      return true;
   }
   
   protected void setAuditableInfo(Persistable persistent) {
      SecurityContext ctx = SecurityContextHolder.getContext();
      /* 
       * 12/7/2012 - switching to userId
      String name = (ctx != null && ctx.getAuthentication() != null && 
               ctx.getAuthentication().getName() != null) 
         ? ctx.getAuthentication().getName() 
         : "UNIDENTIFIED";
	  */
      
      UserSessionVo sessionVo = null;
      Long userId=null;
      String name="";
      try{
    	  sessionVo=(UserSessionVo)context.getBean("userSessionVo");
          if(null != sessionVo){
        	  userId=sessionVo.getUserId();
        	  name=sessionVo.getUserLoginName();
          }
            
      }catch(Exception ie){
    	  return;
      }
      
      Date dt = new Date();
      if (persistent.getId() == null || persistent.getId().equals(0L)) {
         if (persistent.getCreatedBy() == null) {
            persistent.setCreatedBy(name);
         }
         if (null == persistent.getCreatedById() ){
             if(!this.skipSetAuditInfo){
            	 persistent.setCreatedById(userId);
             }
         }
         persistent.setCreatedDate(dt);
      }

      if(!this.skipSetAuditInfo){
	      persistent.setLastModifiedBy(name);
	   	  persistent.setLastModifiedDate(dt);
	      persistent.setLastModifiedById(userId);
      }
   }
   
   protected void setAuditableInfo(Collection<Persistable> persistents) {
      for (Persistable p : persistents) {
         setAuditableInfo(p);
      }
   }

	public void flushAndEvict(Persistable entity) throws Exception{
		getHibernateSession().flush();
		if(null != entity)
			getHibernateSession().evict(entity);
	}

	/**
	 * Returns the skipSetAuditInfo.
	 *
	 * @return 
	 *		the skipSetAuditInfo to return
	 */
	public Boolean getSkipSetAuditInfo() {
		return skipSetAuditInfo;
	}

	/**
	 * Sets the skipSetAuditInfo.
	 *
	 * @param skipSetAuditInfo 
	 *			the skipSetAuditInfo to set
	 */
	public void setSkipSetAuditInfo(Boolean skipSetAuditInfo) {
		this.skipSetAuditInfo = skipSetAuditInfo;
	}

	public void fixCasing(Persistable p) {
		try{
			Class cls = p.getClass();
			
			ClassCaseUtility.toUpperCase(p,cls.getName());
		}catch(Exception e){}
	}
	
	protected void applyCrypticDateFilter(Criteria crit, String crypticDateFilterCode, String dbFieldName) throws Exception {
		if(null != crypticDateFilterCode && crypticDateFilterCode.length()>0){
			ICustomDateFilter dateFilter = CustomDateFilterFactory.getCustomDateFilterInstance(crypticDateFilterCode);
		
			if(null != dateFilter)
				dateFilter.applyDateFiltersOracle(crit, crypticDateFilterCode, dbFieldName);
		}

	}

	protected String getCrypticDateFilterSql(String crypticDateFilterCode, String dbFieldName) throws Exception {
		String sqlFilter = "";
		
		if(null != crypticDateFilterCode && crypticDateFilterCode.length()>0){
			ICustomDateFilter dateFilter = CustomDateFilterFactory.getCustomDateFilterInstance(crypticDateFilterCode);
		
			if(null != dateFilter)
				sqlFilter = dateFilter.getDateFiltersOracle(crypticDateFilterCode, dbFieldName);
		}
		
		return sqlFilter;
	}
	
	protected void applyTimeFilter(Criteria crit, String time, String dbFieldName) throws Exception {
		if(StringUtility.hasValue(time)){
			String formattedTime = StringUtility.leftPad(time, "0", 4);
			
			if(formattedTime.length()==4){
				Integer militaryHour = Integer.parseInt(formattedTime.substring(0,2));
				Integer minute=Integer.parseInt(formattedTime.substring(2,4));
				
				String sqlH = " to_char("+dbFieldName+", 'HH') = " + militaryHour + " ";
				crit.add(Restrictions.sqlRestriction(sqlH));
				String sqlM = " to_char("+dbFieldName+", 'MI') = " + minute + " ";
				crit.add(Restrictions.sqlRestriction(sqlM));
			}
			
		}
	}

	protected String getTimeFilterHourSql(String time, String dbFieldName) throws Exception {
		if(StringUtility.hasValue(time)){
			String formattedTime = StringUtility.leftPad(time, "0", 4);
			
			if(formattedTime.length()==4){
				Integer militaryHour = Integer.parseInt(formattedTime.substring(0,2));
				Integer minute=Integer.parseInt(formattedTime.substring(2,4));
				
				String sqlH = " to_char("+dbFieldName+", 'HH') = " + militaryHour + " ";
				return sqlH;
			}
			
		}
		
		return "";
	}

	protected String getTimeFilterMinuteSql(String time, String dbFieldName) throws Exception {
		if(StringUtility.hasValue(time)){
			String formattedTime = StringUtility.leftPad(time, "0", 4);
			
			if(formattedTime.length()==4){
				Integer militaryHour = Integer.parseInt(formattedTime.substring(0,2));
				Integer minute=Integer.parseInt(formattedTime.substring(2,4));
				
				String sqlM = " to_char("+dbFieldName+", 'MI') = " + minute + " ";
				return sqlM;
			}
			
		}
		
		return "";
	}
	
	public void setLoggingInterceptor(LoggingInterceptor logger) {
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
			logger.addLog(message, level);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context=arg0;
	}

	/**
	 * @return the skipFixCasing
	 */
	public Boolean getSkipFixCasing() {
		return skipFixCasing;
	}

	/**
	 * @param skipFixCasing the skipFixCasing to set
	 */
	public void setSkipFixCasing(Boolean skipFixCasing) {
		this.skipFixCasing = skipFixCasing;
	}
	
}
