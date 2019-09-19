/**
 * Implementation of a DataStreamStrategy that uses XStream to generate the item data.
 */
package gov.nwcg.isuite.framework.stream;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.exceptions.DataStreamException;

import java.util.Collection;

import com.thoughtworks.xstream.XStream;

/**
 * Implementation of a DataStreamStrategy that uses XStream to generate the item
 * data.
 * 
 * @author dougAnderson
 *         <p>
 *         Format of xml: the 'className' comes from the protected method
 *         getClassNameIdentifier() <br>
 *         the 'className' comes from the protected method
 *         getClassNameIdentifier()
 * 
 * <code>
 * <classNameCollection  count=xxx>
 *    <className>
 *    .... data ...
 *    </className>
 * </classNameCollection>
 * </code>
 * </p>
 * 
 */
public abstract class AbstractXStreamDataStreamStrategy implements
		DataStreamStrategy {

	private DataStreamXstream dataStreamXStream = null;

	public AbstractXStreamDataStreamStrategy(DataStreamXstream dataStreamXStream){
		setDataStreamXStream(dataStreamXStream);
	}
	
	

	/**
	 * @return the dataStreamXStream
	 */
	public DataStreamXstream getDataStreamXStream() {
		return dataStreamXStream;
	}

	/**
	 * @param dataStreamXStream
	 *            the dataStreamXStream to set
	 */
	public void setDataStreamXStream(DataStreamXstream dataStreamXStream) {
		if (dataStreamXStream == null) {
			throw new IllegalArgumentException("dataStreamXStream cannot be null");
		}
		this.dataStreamXStream = dataStreamXStream;

	}

	/**
	 * @return the xStream
	 */
	public XStream getXStream() {
		return dataStreamXStream.getXStream();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.stream.DataStreamStrategy#getCount(java.lang.String)
	 */
	public final int getCount(String header) {
		Integer startPoint;
		Integer endPoint;
		Integer count;
		startPoint = header.indexOf("count=\"") + 7;
		endPoint = header.indexOf("\"", startPoint);
		count = Integer.valueOf(header.substring(startPoint, endPoint));
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.stream.DataStreamStrategy#getFooter()
	 */
	public final String getFooter() {
		StringBuffer buf = new StringBuffer();
		buf.append("</");
		buf.append(getClassNameIdentifier());
		buf.append(">");
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.stream.DataStreamStrategy#getHeader(java.util.Collection)
	 */
	public final String getHeader(Collection items) {
		StringBuffer buf = new StringBuffer();
		buf.append("<");
		buf.append(getClassNameIdentifier());
		buf.append(" count=\"");
		buf.append(items.size());
		buf.append("\">");

		return buf.toString();
	}

	/**
	 * Get the xml identifer for the name of the objects in the collection
	 * 
	 * @return the xml identifer for the name of the objects in the collection
	 */
	abstract protected String getClassNameIdentifier();

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.stream.DataStreamStrategy#getItemData(java.lang.Object)
	 */
	public String getItemData(Transferable item) throws DataStreamException {

		return getXStream().toXML(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.persistence.stream.DataStreamStrategy#getObject(java.lang.String)
	 */
	public Transferable getObject(String string) throws DataStreamException {
		return (Transferable) getXStream().fromXML(string);

	}
}
