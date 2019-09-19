package gov.nwcg.isuite.framework.core.persistence.hibernate;

import java.util.Properties;

/**
 * @author dougAnderson
 */
public class HibernateProperties extends Properties {

	private static final long serialVersionUID = -6142585422148367656L;

	public void setUse_outer_join(String data) {
		super.setProperty("hibernate.use_outer_join", data);
	}

	public String getUse_outer_join() {
		return super.getProperty("hibernate.use_outer_join");
	}

	public void setCacheProviderClass(String data) {
		super.setProperty("hibernate.cacheProviderClass", data);
	}

	public String getCacheProviderClass() {
		return super.getProperty("hibernate.cacheProviderClass");
	}

	public void setJdbc_batch_size(String data) {
		super.setProperty("hibernate.jdbc.batch_size", data);
	}

	public String getJdbc_batch_size() {
		return super.getProperty("hibernate.jdbc.batch_size");
	}

	public void setDialect(String data) {
		super.setProperty("hibernate.dialect", data);
	}

	public String getDialect() {
		return super.getProperty("hibernate.dialect");
	}

	public String getShowSql() {
		return super.getProperty("hibernate.show_sql");
	}

	public void setShowSql(String value) {
		super.setProperty("hibernate.show_sql", value);
	}
	
	public String getHbm2ddl() {
		return super.getProperty("hibernate.hbm2ddl.auto");
	}
	
	public void setHbm2ddl(String value) {
		super.setProperty("hibernate.hbm2ddl.auto", value);
	}
	
	public String getDefault_schema() {
		return super.getProperty("hibernate.default_schema");
	}
	
	public void setDefault_schema(String value) {
		super.setProperty("hibernate.default_schema", value);
	}
	
}
