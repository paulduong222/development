/**
 * Implementation of a DataStreamDao.
 */
package gov.nwcg.isuite.framework.stream.impl;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.exceptions.DataStreamException;
import gov.nwcg.isuite.framework.stream.DataStreamDao;
import gov.nwcg.isuite.framework.stream.DataStreamStrategy;
import gov.nwcg.isuite.framework.stream.StreamIterator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * Implementation of a DataStreamDao.
 * 
 * @author dougAnderson
 */
public class DataStreamDaoImpl implements DataStreamDao {

   private DataStreamStrategy strategy;
   
   public DataStreamDaoImpl(DataStreamStrategy strategy){
	   setStrategy(strategy);
   }

   /**
    * @return the strategy
    */
   public final DataStreamStrategy getStrategy() {
     
      return strategy;
   }

   /**
    * @param strategy
    *           the strategy to set
    */
   public final void setStrategy(DataStreamStrategy strategy) {
      if ( strategy == null ) {
         throw new IllegalArgumentException("strategy can not be null");
      }
      this.strategy = strategy;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.stream.DataStreamDao#getIterator(java.io.DataInputStream)
    */
   public Iterator getIterator(DataInputStream stream)
            throws DataStreamException {
      return new StreamIterator(getStrategy(), stream);
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.stream.DataStreamDao#write(java.util.Collection,
    *      java.io.DataOutputStream)
    */
   public int write(Collection<Transferable>items, DataOutputStream stream)
            throws DataStreamException {
      int count = 0;
      try {
         DataStreamStrategy dss = getStrategy();
         stream.writeUTF(dss.getHeader(items));
         for ( Transferable item : items ) {
            stream.writeUTF(dss.getItemData(item));
            count++;
         }
         stream.writeUTF(dss.getFooter());
         return count;
      }
      catch ( Exception e ) {
         throw new DataStreamException(e);
      }
   }

}
