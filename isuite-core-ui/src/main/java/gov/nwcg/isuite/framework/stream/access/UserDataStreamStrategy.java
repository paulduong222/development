package gov.nwcg.isuite.framework.stream.access;

import gov.nwcg.isuite.framework.stream.AbstractXStreamDataStreamStrategy;
import gov.nwcg.isuite.framework.stream.DataStreamXstream;

/**
 * @author mpaskett
 *
 */
public class UserDataStreamStrategy extends AbstractXStreamDataStreamStrategy {

	/**
	 * @param dataStreamXStream
	 */
	public UserDataStreamStrategy(DataStreamXstream dataStreamXStream) {
		super(dataStreamXStream);
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.stream.AbstractXStreamDataStreamStrategy#getClassNameIdentifier()
	 */
	@Override
	protected String getClassNameIdentifier() {
		return "users";
	}
	
}
