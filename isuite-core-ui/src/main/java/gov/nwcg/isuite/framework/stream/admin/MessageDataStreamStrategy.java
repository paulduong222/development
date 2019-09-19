package gov.nwcg.isuite.framework.stream.admin;

import gov.nwcg.isuite.framework.stream.AbstractXStreamDataStreamStrategy;
import gov.nwcg.isuite.framework.stream.DataStreamXstream;

/**
 * Implementation of a DataStreamStrategy for Messages.
 * 
 * @author dougAnderson
 * 
 */
public class MessageDataStreamStrategy extends AbstractXStreamDataStreamStrategy{

	// use XStream for implementation to xml

	public MessageDataStreamStrategy(DataStreamXstream dataStreamXStream){
		super(dataStreamXStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.stream.AbstractXStreamDataStreamStrategy#getClassNameIdentifier()
 */	
	@Override
	protected String getClassNameIdentifier() {
		return "messages";
	}



//	@Override
//	public Transferable getObject(String string) throws DataStreamException {
//		return (Message) getXStream().fromXML(string);
//	}

}
