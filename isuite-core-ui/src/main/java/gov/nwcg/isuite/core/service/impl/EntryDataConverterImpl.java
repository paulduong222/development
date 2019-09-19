package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.impl.EntryImpl;
import gov.nwcg.isuite.core.service.EntryDataConverter;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.input.InputData;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EntryDataConverterImpl implements EntryDataConverter {

   public EntryDataConverterImpl() {
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.service.update.enterprise.impl.SyncDataConverter#createEntry(gov.nwcg.isuite.domain.sync.SyncData, gov.nwcg.isuite.domain.update.enterprise.UpdateDataTypeEnum)
    */
   public Entry createEntry(InputData inputData, UpdateDataTypeEnum dataType) throws ServiceException {
         try {
             ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream stream = new ObjectOutputStream(byteStream);
             stream.writeObject(inputData);
             EntryImpl entryImpl = new EntryImpl();
             
             return entryImpl;
             //return new EntryImpl(byteStream.toByteArray(), inputData.getUniqueIdentifier(), dataType, inputData.getSource());
         }
         catch (IOException e) {
            throw new ServiceException(e);
         }
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.service.update.enterprise.impl.SyncDataConverter#extractData(byte[])
    */
   public Object extractData(byte[] data) throws ServiceException {
      try {
      ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
      ObjectInputStream inputStream = new ObjectInputStream(byteStream);
      return inputStream.readObject();
      }
      catch (IOException ioe) {
         throw new ServiceException("could not read data from entry", ioe);
      }
      catch (ClassNotFoundException cnfe) {
         throw new ServiceException("could not read data from entry", cnfe);
      }
   }

}