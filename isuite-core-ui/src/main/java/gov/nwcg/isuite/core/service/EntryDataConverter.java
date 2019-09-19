package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.input.InputData;
import gov.nwcg.isuite.framework.input.UpdateDataTypeEnum;

public interface EntryDataConverter {

   public Entry createEntry(InputData inputData, UpdateDataTypeEnum dataType) throws ServiceException;

   public Object extractData(byte [] data) throws ServiceException;

}