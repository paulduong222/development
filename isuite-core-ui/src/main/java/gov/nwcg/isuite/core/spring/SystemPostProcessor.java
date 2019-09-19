package gov.nwcg.isuite.core.spring;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.core.session.SessionManagementRunner;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.system.EISuiteTaskRunner;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.timer.ScheduledTimerTask;

public class SystemPostProcessor implements BeanPostProcessor,ApplicationContextAware {
	private static final Logger LOG = Logger.getLogger(SystemPostProcessor.class);

	protected ApplicationContext context;
	private SystemParameterDao systemParameterDao=null;

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean.getClass().getName().equals(ScheduledTimerTask.class.getName())){
			SystemParameterDao sysParamDao = (SystemParameterDao)context.getBean("systemParameterDao");
			SystemParameter sysParamEntity = null;

			try{
				sysParamEntity=sysParamDao.getByParameterName(SystemParameterTypeEnum.STALE_SESSION_SCHEDULE.toString());
				if(null != sysParamEntity && StringUtility.hasValue(sysParamEntity.getParameterValue())) {
					((ScheduledTimerTask)bean).setPeriod(Long.parseLong(sysParamEntity.getParameterValue()));
				} else {
					((ScheduledTimerTask)bean).setPeriod(300000L);
				}
			}catch(PersistenceException pe){
				// smother
				((ScheduledTimerTask)bean).setPeriod(300000L);
			}
			return bean;
		}

		if(bean.getClass().getName().equals(SessionManagementRunner.class.getName())){
			SystemParameterDao sysParamDao = (SystemParameterDao)context.getBean("systemParameterDao");
			SystemParameter sysParamEntity = null;

			SessionManagementRunner smRunner = new SessionManagementRunner();
			long interval=(60000 * 1);

			try{

				smRunner.setApplicationContext(this.context);

				try{
					sysParamEntity=sysParamDao.getByParameterName(SystemParameterTypeEnum.SESSION_SYNC_INTERVAL_MINUTES.toString());
					if( (null != sysParamEntity) && (!sysParamEntity.getParameterValue().equals(""))){
						interval=(60000 * Integer.parseInt(sysParamEntity.getParameterValue()));						
					}
				}catch(PersistenceException pe){
					// smother
				}
				smRunner.setInterval(interval);
				smRunner.startSessionMgmtRunnable();
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());
			}
			return smRunner;
		}

		if(bean.getClass().getName().equals(EISuiteTaskRunner.class.getName())){

			SystemParameterDao sysParamDao = (SystemParameterDao)context.getBean("systemParameterDao");
			SystemParameter sysParamEntity = null;

			try{
				sysParamEntity=sysParamDao.getByParameterName(SystemParameterTypeEnum.RUN_TASK_QUEUE.toString());
			}catch(PersistenceException pe){
				// smother
			}

			// is the task queue turned on?
			if( (null != sysParamEntity) && (sysParamEntity.getParameterValue().equals("1")) ){
				EISuiteTaskRunner tr = new EISuiteTaskRunner();
				long interval=(60000 * 15);

				try{

					tr.setApplicationContext(this.context);

					try{
						sysParamEntity=sysParamDao.getByParameterName(SystemParameterTypeEnum.TASK_QUEUE_INTERVAL_MINUTES.toString());
						if( (null != sysParamEntity) && (!sysParamEntity.getParameterValue().equals(""))){
							interval=(60000 * Integer.parseInt(sysParamEntity.getParameterValue()));						
						}
					}catch(PersistenceException pe){
						// smother
					}
					tr.setInterval(interval);
					tr.startTaskRunnable();
				}catch(Exception e){
					throw new RuntimeException(e.getMessage());
				}
				return tr;
			}
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

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context=arg0;
	}

	/**
	 * @param systemParameterDao the systemParameterDao to set
	 */
	public void setSystemParameterDao(SystemParameterDao systemParameterDao) {
		this.systemParameterDao = systemParameterDao;
	}

}
