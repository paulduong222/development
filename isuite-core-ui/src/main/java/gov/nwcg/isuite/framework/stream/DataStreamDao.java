/**
 * Persists objects to/from a DataOutputStream/DataInputStream.
 */
package gov.nwcg.isuite.framework.stream;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.exceptions.DataStreamException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * Persists objects to a DataOutputStream.
 * @author dougAnderson
 */
public interface DataStreamDao {
   
   /**
    * Write the items to the specified stream
    * @param items what is to be written
    * @param stream where it is to be written
    * @return number of items written
    * @throws PersistenceException if needed
    */
   public int write(Collection<Transferable> items, DataOutputStream stream) throws DataStreamException;
   
   /**
    * Create an iterator over the DataInputStream
    * @param stream stream containing the data
    * @param messageFilter filter defining which messages to retrieve
    * @return Iterator
    * @throws PersistenceException if needed
    */
   public Iterator getIterator(DataInputStream stream) throws DataStreamException;

}
