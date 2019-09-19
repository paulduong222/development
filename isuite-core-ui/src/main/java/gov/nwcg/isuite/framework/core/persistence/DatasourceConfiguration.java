package gov.nwcg.isuite.framework.core.persistence;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DatasourceConfiguration implements ApplicationContextAware{
	protected ApplicationContext context=null;
	public static Boolean isLoading=true;
	public static String dsname="";
	public static String dbname="";
	
	public void initialize() throws Exception{
		DbAvailDao dao = (DbAvailDao)context.getBean("dbAvailDao");

		Collection<DbAvail> list = dao.getAll();

		DatasourceConfiguration.dbname="isuite_site_master";
		DatasourceConfiguration.dsname="default0Datasource";

		GlobalCacheVo gcoVo2 = (GlobalCacheVo)context.getBean("globalCacheVo");
		
		DatasourceConfiguration.isLoading=true;
		
		if(CollectionUtility.hasValue(list)){
			AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry)factory;
			
			for(DbAvail db : list){
				//((BasicDataSource)context.getBean(db.getDatasource())).setUrl("jdbc:postgresql://localhost/"+db.getName());
				((BasicDataSource)context.getBean(db.getDatasource())).setUrl("jdbc:postgresql://127.0.0.1/"+db.getName());
				DatasourceConfiguration.dbname=db.getName();
				DatasourceConfiguration.dsname=db.getDatasource();

				GlobalCacheVo gcoVo = (GlobalCacheVo)context.getBean("globalCacheVo");
				
				//uncomment below for remote debugging, 192.168.1.211 is site db ip address for example
				//((BasicDataSource)context.getBean(db.getDatasource())).setUrl("jdbc:postgresql://192.168.1.211/"+db.getName());
			}
		}
		
		DatasourceConfiguration.isLoading=false;
		
	}
	
	private GenericBeanDefinition buildDataSourceBean(String name) throws Exception {
		MutablePropertyValues values = new MutablePropertyValues();
		values.addPropertyValue("url", "jdbc:postgresql://127.0.0.1/"+name);
		//uncomment below for remote debugging, 192.168.1.211 is site db ip address for example
		//values.addPropertyValue("url", "jdbc:postgresql://192.168.1.211/"+name);
		values.addPropertyValue("driverClassName", "org.postgresql.Driver");
		values.addPropertyValue("username", "isw");
		values.addPropertyValue("password", "sabiosoPassword");
		values.addPropertyValue("testOnBorrow", "true");
		
		GenericBeanDefinition bean = new GenericBeanDefinition();
		bean.setBeanClass(BasicDataSource.class);
		bean.setPropertyValues(values);
		
		return bean;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context=arg0;
	}
	
	
}
