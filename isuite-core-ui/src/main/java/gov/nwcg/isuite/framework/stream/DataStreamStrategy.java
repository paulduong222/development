/**
 * Used to convert collections of items to/from dataStreams.
 */
package gov.nwcg.isuite.framework.stream;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.exceptions.DataStreamException;

import java.util.Collection;

/**
 * Used to convert collections of items to/from dataStreams.
 * <p>
 * The format of the data written is:
 * <ol>
 * <li>header (includes count of items)</li>
 * <li>data item (repeated for each data item) </li>
 * <li>footer </li>
 * </ol>
 * 
 * @author dougAnderson
 */
public interface DataStreamStrategy {

   /**
    * Generate a header string for the dataStream representation of items.
    * <p>
    * Header should contain the count of items that will be persited based on
    * the collection passed in.
    * <p>
    * 
    * @param items
    *           collection of items to be written.
    * @return header string.
    * @see #getFooter()
    * @see #getCount(String)
    */
   public String getHeader(Collection items);

   /**
    * Get a representation of the item as a string.
    * 
    * @param item
    *           item to be transformed
    * @return transformation of the item as a string
    * @throws DataStreamException
    *            if needed
    * @see #getObject(String)
    */
   public String getItemData(Transferable item) throws DataStreamException;

   /**
    * Generate a footer string for the dataStream representation of items.
    * 
    * @return footer string for the dataStream representation of items.
    * @see #getHeader(Collection)
    */
   public String getFooter();

   /**
    * Get the number of items in the dataStream based on the header information.
    * This header information was created by the getHeader() method.
    * 
    * @param header
    *           header information
    * @return number of items in teh dataStream
    * @see #getHeader(Collection)
    */
   public int getCount(String header);

   /**
    * Transform a string representation of an object into the object. This
    * string representation was created by the getItemData() method.
    * 
    * @param string
    *           string representation of an object
    * @return the object represented by the string.
    * @throws DataStreamException
    *            if needed
    * @see #getItemData(Object)
    */
   public Transferable getObject(String string) throws DataStreamException;

}
