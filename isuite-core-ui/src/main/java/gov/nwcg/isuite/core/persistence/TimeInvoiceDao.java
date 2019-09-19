package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.vo.TimeInvoiceVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface TimeInvoiceDao extends TransactionSupport, CrudDao<TimeInvoice>{

	Collection<TimeInvoiceVo> getForResource(Long resourceId) throws PersistenceException;
	
	/**
	 * @param adminOfficeId
	 * @return
	 * @throws PersistenceException
	 */
//	public Collection<TimeInvoice> getByAdminId(Long adminOfficeId) throws PersistenceException;
	
	/**
	 * @param incidentIds
	 * @param contractor
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<TimeInvoice> getUnexportedInvoices(Collection<Long> incidentIds, Boolean contractor)throws PersistenceException;
	
	/**
	 * @param invoiceNumber
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<TimeInvoice> getInvoiceByInvoiceNumber(String invoiceNumber) throws PersistenceException;
	
  	public Collection<String> getAllInvoiceFileNames() throws PersistenceException;

  	public Long getResourceId(Long invoiceTimeId) throws PersistenceException;

  	public Long getResNumId(Long incResId) throws PersistenceException;

  	public Collection<Long> getADSubordinateResourceIds(Long parentResourceId) throws PersistenceException;

  	public void cleanDuplicateInvoicePostings(Long sourceId) throws PersistenceException;
  	
  	public void cleanDuplicateInvoiceAdjustments(Long sourceId) throws PersistenceException;
  	
}
