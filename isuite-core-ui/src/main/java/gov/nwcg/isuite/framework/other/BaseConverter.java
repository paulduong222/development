package gov.nwcg.isuite.framework.other;

import java.util.Date;
import java.util.GregorianCalendar;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Responsible for marshalling Java objects to/from textual data.
 */
public class BaseConverter implements Converter {

	/**
	 * Converts an object to textual data.
	 */
	public void marshal(Object object, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		// Method must be overridden in extender class

	}

	/**
	 * Converts textual data back into an object
	 * 
	 * @param reader
	 * 
	 * @return the object
	 */
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context){
		// Method must be overridden in extender class
		return null;
	}

	/**
	 * Determines whether the converter can marshall a particular type
	 * 
	 * @param Class representing the object type to be converted
	 */
	public boolean canConvert(Class clazz) {
		// Method must be overridden in extender class
		return false;
	}
	
	/**
	 * @param Date 
	 * 
	 * @return a <code>GregorianCalendar</code> with the time set to the date
	 */
	protected GregorianCalendar getCalendar(Date date){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}
	
	/**
	 * @param GregorianCalendar
	 * 
	 * @return a <code>Date</code> set to the time of the <code>GregorianCalendar</code>
	 */
	protected Date getDate(GregorianCalendar cal){
		Date date;
		date = cal.getTime();
		return date;
	}

}
