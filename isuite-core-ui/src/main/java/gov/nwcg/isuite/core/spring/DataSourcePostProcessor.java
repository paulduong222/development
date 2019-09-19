package gov.nwcg.isuite.core.spring;

import gov.nwcg.isuite.core.vo.UserSessionVo;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DataSourcePostProcessor implements BeanPostProcessor,ApplicationContextAware {
	private static final Logger LOG = Logger.getLogger(DataSourcePostProcessor.class);

	protected ApplicationContext context;
	protected DatasourceMap datasourceMap = null;
	protected UserSessionVo userSessionVo=null;

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
		if(bean.getClass().getName().equals(BasicDataSource.class.getName())){
			String dbName = "";
			BasicDataSource newBean = new BasicDataSource();
			
			if(null == userSessionVo){
				//System.out.println("");
			}
			
			// does this datasource already exists?
			if(null != userSessionVo && null != userSessionVo.getSiteDatabaseName()){
				dbName=userSessionVo.getSiteDatabaseName();
				if(this.datasourceMap.hasDatasource(dbName)){
					bean = this.datasourceMap.getDatasource(dbName);
					return bean;
				}else{
					
					try{
						// init new Bean
						newBean.addConnectionProperty("url", "jdbc:postgresql://127.0.0.1/"+dbName);
						newBean.addConnectionProperty("driverClassName", "org.postgresql.Driver");
						newBean.addConnectionProperty("username", "isw");
						newBean.addConnectionProperty("password", "ISSCPassword");
						newBean.addConnectionProperty("testOnBorrow", "true");
						
						// add to map
						this.datasourceMap.addDatasource(dbName, newBean);
					}catch(Exception e){
						throw new RuntimeException(e);
					}
					
				}
			}else{
				return bean;
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

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context=arg0;
	}

	/**
	 * @param datasourceMap the datasourceMap to set
	 */
	public void setDatasourceMap(DatasourceMap datasourceMap) {
		this.datasourceMap = datasourceMap;
	}

	/**
	 * @param userSessionVo the userSessionVo to set
	 */
	public void setUserSessionVo(UserSessionVo userSessionVo) {
		this.userSessionVo = userSessionVo;
	}


}
