package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.impl.MessageImpl;
import gov.nwcg.isuite.core.persistence.MessageDao;
import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

public class MessageDaoHibernate extends TransactionSupportImpl implements MessageDao {

   private static final Logger LOG = Logger.getLogger(MessageDaoHibernate.class);
   private static final String STATIC = "STATIC";
   private static final String POPUP = "POPUP";

   private final CrudDao<Message>  crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    */
   public MessageDaoHibernate(final CrudDao<Message> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public Message getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, MessageImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Perstistable)
    */
   public void save(Message persistable) throws PersistenceException {
	   crudDao.setSkipFixCasing(Boolean.TRUE);
	   crudDao.save(persistable);
	   crudDao.setSkipFixCasing(Boolean.FALSE);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.hibernate.CrudDaoHibernate#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(Message persistable) throws PersistenceException {
	   crudDao.delete(persistable); 
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<Message> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }
   
   public MessageBoardVo getStaticMessage() throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(MessageImpl.class);
	   
	   crit.add(Expression.eq("type", MessageDaoHibernate.STATIC));
	   
	   Message entity = (Message)crit.uniqueResult();
	   
	   try {
		   return MessageBoardVo.getInstance(entity, true);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
	   
   }
   
   @SuppressWarnings("unchecked")
   public Collection<MessageBoardVo> getPopUpGrid() throws PersistenceException {
	   
	   Criteria crit = getHibernateSession().createCriteria(MessageImpl.class);
	   crit.add(Expression.eq("type", MessageDaoHibernate.POPUP));
	   crit.addOrder(Order.asc("effectiveDate"));
	   Collection<Message> entities = crit.list();
	   
	   try {
		   return MessageBoardVo.getInstances(entities, true);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
   }
   
   @SuppressWarnings("unchecked")
   public Collection<MessageBoardVo> getPopUpMessages(String clientLocalDate) throws PersistenceException {
//	   Date today = new Date();
	   Criteria crit = getHibernateSession().createCriteria(MessageImpl.class);
	   crit.add(Expression.eq("type", MessageDaoHibernate.POPUP));
	   
	   String sql1="to_date(to_char(this_.effective_date, 'MM/DD/YYYY'),'MM/DD/YYYY') <= " +
	   		"to_date('" + clientLocalDate + "', 'MM/DD/YYYY')";
	   		
	   crit.add(Expression.sqlRestriction(sql1));
	   
	   String sql2="(to_date(to_char(this_.expire_date, 'MM/DD/YYYY'),'MM/DD/YYYY') >= " +
  		"to_date('" + clientLocalDate + "', 'MM/DD/YYYY') or this_.expire_date is null)";
	   
	   crit.add(Expression.sqlRestriction(sql2));
	   
	   crit.addOrder(Order.asc("effectiveDate"));
	   Collection<Message> entities = crit.list();
	   
	   try {
		   return MessageBoardVo.getInstances(entities, true);
	   }catch(Exception e){
		   throw new PersistenceException(e);
	   }
   }
   
   public void createDefaultStaticMessage() throws PersistenceException {
		String sql="INSERT INTO ISW_MESSAGE (ID, MESSAGE_TEXT, TYPE) " +
					"VALUES (nextVal('SEQ_MESSAGE'), '<p class=ql-align-center><strong class=ql-size-large>WELCOME TO e-ISUITE!</strong></p><p><br></p><p><strong>On this Messages Board, the ITSS or Account Manager will post helpful information concerning application updates, outages or other situations that may impact the availability of the system.</strong></p><p><br></p><p>Help is available by calling the Interagency Incident Helpdesk at <strong>1-866-224-7677.</strong></p><p><br></p><p>Known issues are provided in a document available on the e-ISuite web page, as well as additional information about e-ISuite: <strong>https://famit.nwcg.gov/applications/eISuite.</strong></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p class=ql-align-center><strong>We welcome feedback on ways to improve e-ISuite.</strong></p><p><br></p><p class=ql-align-center><strong>Please continue to send comments and suggestions to the e-ISuite Suggestion Box: i-suite-suggestions@dms.nwcg.gov.</strong></p><p class=ql-align-center><strong>A Change Request form is available on the website above, under the Change Management link.</strong></p><p><br></p><p class=ql-align-center><strong>Information on the e-ISuite Suggestion Box will be available soon.</strong></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p>', 'STATIC') ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
   }
}
