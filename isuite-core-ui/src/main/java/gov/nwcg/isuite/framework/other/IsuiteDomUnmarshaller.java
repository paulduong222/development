package gov.nwcg.isuite.framework.other;

import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The commonly used DOM unmarshalling method calls.
 * 
 * @author bsteiner
 *
 */
public class IsuiteDomUnmarshaller {

   /**
    * 
    * @param textNode
    * @return
    */
   protected static String unmarshallText(Node textNode) {
      StringBuffer buf = new StringBuffer();

      Node n;
      NodeList nodes = textNode.getChildNodes();

      for ( int i = 0; i < nodes.getLength(); i++ ) {
         n = nodes.item(i);

         if ( n.getNodeType() == Node.TEXT_NODE ) {
            buf.append(n.getNodeValue());
         }
         else {
            // expected a text-only node!
         }
      }
      return buf.toString();
   }

   /**
    * 
    * @param node
    * @param name
    * @param defaultValue
    * @return
    * @throws ServiceException
    */
   protected static String unmarshallAttribute(Node node, String name, String defaultValue) throws ServiceException {
      Node n = node.getAttributes().getNamedItem(name);
      return (n != null) ? (n.getNodeValue()) : (defaultValue);
   }
}
