package gov.nwcg.isuite.framework.stream;

import java.util.Collection;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;

public class DataStreamXstream {

	public DataStreamXstream() {
	}

	private XStream xStream = new XStream();


	/**
	 * @param converters
	 *            the converters to set
	 */
	public void setConverters(Collection<Converter> converters) {

		for (Converter converter : converters) {
			xStream.registerConverter(converter);
		}
	}
	public XStream getXStream(){
		return this.xStream;
	}

}
