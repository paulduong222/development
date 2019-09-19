/**
 * Iterates over a dataInputStream, retriving objects as needed.
 * <p>
 * This class uses a DataStreamStrategy implementation to transform strings read
 * from the dataStream into objects.
 * </p>
 */
package gov.nwcg.isuite.framework.stream;

import gov.nwcg.isuite.framework.exceptions.DataStreamException;

import java.io.DataInputStream;
import java.util.Iterator;

/**
 * Iterates over a dataInputStream, retriving objects as needed.
 * <p>
 * This class uses a DataStreamStrategy implementation to transform strings read
 * from the dataStream into objects.
 * 
 * @author dougAnderson
 */
public class StreamIterator implements Iterator {

   private DataStreamStrategy strategy;
   private int count;
   private int countRead = 0;
   private DataInputStream inputStream;

   /**
    * Constructor.
    * 
    * @param strategy
    *           strategy to use for converting from strings to objects
    * @param inputStream
    *           source of strings for conversion
    * @throws DataStreamException
    *            if needed
    */
   public StreamIterator(DataStreamStrategy strategy,
            DataInputStream inputStream) throws DataStreamException {
      setStrategy(strategy);
      setInputStream(inputStream);
      try {
         count = strategy.getCount(inputStream.readUTF());
      }
      catch ( Exception e ) {
         throw new DataStreamException(e);
      }
   }

   /**
    * Return strategy used to transform strings into objects.
    * 
    * @return the strategy
    * @see #setStrategy(DataStreamStrategy)
    */
   public final DataStreamStrategy getStrategy() {
      return strategy;
   }

   /**
    * Set the strategy used to transform strings into objects.
    * 
    * @param strategy
    *           strategy used to transform strings into objects.strategy
    * @see #getStrategy()
    */
   public final void setStrategy(DataStreamStrategy strategy) {
      if ( strategy == null ) {
         throw new IllegalArgumentException("strategy can not be null");
      }
      this.strategy = strategy;
   }

   /**
    * Accessor for the stream that contains the strings of object
    * representations.
    * 
    * @return the inputStream stream that contains the strings of object
    *         representations.
    * @see #setInputStream(DataInputStream)
    */
   public final DataInputStream getInputStream() {
      return inputStream;
   }

   /**
    * Accessor for the stream that contains the strings of object
    * representations.
    * 
    * @param inputStream
    *           stream that contains the strings of object representations
    * @see #getInputStream()
    */
   public final void setInputStream(DataInputStream inputStream) {
      if ( inputStream == null ) {
         throw new IllegalArgumentException("inputStream can not be null");
      }
      this.inputStream = inputStream;
   }


   /*
    * (non-Javadoc)
    * 
    * @see java.util.Iterator#hasNext()
    */
   public boolean hasNext() {
      return count > countRead;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.util.Iterator#next()
    */
   public Object next() {
      Object result = null;
      try {
         result = getStrategy().getObject(getInputStream().readUTF());
      }
      catch ( Exception e ) {
         ; // can't do anything because of Iterator::next does not throw
         // exceptions
      }
      countRead++;
      return result;
   }

   /*
    * (non-Javadoc)
    * 
    * @see java.util.Iterator#remove()
    */
   public void remove() {
      throw new UnsupportedOperationException("remove is not supported");
   }

}