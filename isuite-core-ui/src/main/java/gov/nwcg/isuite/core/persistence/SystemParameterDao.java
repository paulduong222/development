package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SystemParameterDao extends TransactionSupport, CrudDao<SystemParameter> {
   
	public SystemParameter getByParameterName(String nm) throws PersistenceException;
	
	public Collection<SystemParameter> getGrid() throws PersistenceException;
	
	public void saveAll(Collection<SystemParameter> persistables) throws PersistenceException;
	
	public void save(SystemParameter persistable) throws PersistenceException;
	
	public SystemParameter getById(Long id, Class clazz) throws PersistenceException;
	
	public void delete(SystemParameter persistable) throws PersistenceException;
	
	public void updateParameter(SystemParameterVo vo) throws PersistenceException;

	public Collection<SystemParameterVo> getParametersForSync() throws PersistenceException;

	public void persistSqls(Collection<String> sqls) throws PersistenceException;

	public void updateByName(String name, String value) throws PersistenceException;
}
