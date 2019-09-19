package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.impl.MessageImpl;
import gov.nwcg.isuite.framework.other.BaseConverter;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;
import gov.nwcg.isuite.framework.types.MessageSeverityEnum;

import java.util.Date;
import java.util.GregorianCalendar;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Responsible for marshalling message objects to/from textual data.
 */
public class MessageConverter extends BaseConverter {

	public MessageConverter() {
	}

	/**
	 * Converts message object to textual data.
	 */
	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Message message = (Message) object;
//		if (message.getIdentity() != null) {
//			writer.startNode("identity");
//			writer.setValue(message.getIdentity());
//			writer.endNode();
//		}
//		if (message.getTitle() != null) {
//			writer.startNode("title");
//			writer.setValue(message.getTitle());
//			writer.endNode();
//		}
//		if (message.getDetails() != null) {
//			writer.startNode("details");
//			writer.setValue(message.getDetails());
//			writer.endNode();
//		}
//		if (message.getCause() != null) {
//			writer.startNode("cause");
//			writer.setValue(message.getCause().toString());
//			writer.endNode();
//		}
//		if (message.getSeverity() != null) {
//			writer.startNode("severity");
//			writer.setValue(message.getSeverity().toString());
//			writer.endNode();
//		}
//		if (message.getOccurranceDate() != null) {
//			writer.startNode("occurranceDate");
//			context.convertAnother(getCalendar(message.getOccurranceDate()));
//			writer.endNode();
//		}
	}

	/**
	 * Converts textual data back into a message
	 * 
	 * @return the user
	 */
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		MessageImpl msg = null;
		String title = null;
//		String identity = null;
		String details = null;
		MessageCauseEnum cause = null;
		MessageSeverityEnum severity = null;
		Date occurranceDate = null;

		while (reader.hasMoreChildren() == true) {
			reader.moveDown();
			if ("title".equals(reader.getNodeName())) {
				title = reader.getValue();
//			} else if ("identity".equals(reader.getNodeName())) {
//				identity = reader.getValue();
			} else if ("details".equals(reader.getNodeName())) {
				details = reader.getValue();
			} else if ("cause".equals(reader.getNodeName())) {
				cause = MessageCauseEnum.valueOf(reader.getValue());
			} else if ("severity".equals(reader.getNodeName())) {
				severity = MessageSeverityEnum.valueOf(reader.getValue());
			} else if ("occurranceDate".equals(reader.getNodeName())) {
				occurranceDate = getDate((GregorianCalendar) context
						.convertAnother(msg, GregorianCalendar.class));
			}
			reader.moveUp();
		}
//		msg = new MessageImpl(title,details,cause,severity);
//		msg.setOccurranceDate(occurranceDate);
//		msg.setIdentity(identity);

		return msg;
	}

	@Override
	public boolean canConvert(Class clazz) {
		return MessageImpl.class.equals(clazz);

	}

}
