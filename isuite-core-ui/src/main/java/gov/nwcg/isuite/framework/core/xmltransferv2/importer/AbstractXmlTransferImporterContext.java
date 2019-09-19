package gov.nwcg.isuite.framework.core.xmltransferv2.importer;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.framework.core.xmltransferv2.AbstractXmlTransfer;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransferv2.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransferv2.utility.XmlTransferUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class AbstractXmlTransferImporterContext extends AbstractXmlTransfer {
	protected DataTransferDao dao = null;
	
	private static int counter=0;
	
	protected static final String _PATH="gov.nwcg.isuite.core.domain.xmlv2.";

	/**
	 * @param field
	 * @param methods
	 * @return
	 */
	private Method getSetterMethod(String field,Method methods[]){
		
		for(Method methodSetter : methods){
			String methodName=methodSetter.getName();
			if(methodName.equals("set"+field)){
				return methodSetter;
			}
		}	
	
		return null;
	}

	/**
	 * @param field
	 * @param methods
	 * @return
	 */
	private Method getGetterMethod(String field,Method methods[]){
		
		for(Method methodGetter : methods){
			String methodName=methodGetter.getName();
			if(methodName.equals("get"+field)){
				return methodGetter;
			}
		}	
	
		return null;
	}

	/**
	 * @param methodSetter
	 * @return
	 */
	private Boolean isMethodParameterCollection(Method methodSetter) {
		Class<?> param[]=methodSetter.getParameterTypes();
		String ptype=param[0].getSimpleName();
		//System.out.println("Parameter :"+ptype);
		if(ptype.equals("Collection")){
			return true;
		}
		return false;
	}

	/**
	 * @param nodeName
	 * @param node
	 * @return
	 * @throws Exception
	 */
	protected Object populateDataObjects(String nodeName, Element node, String targetClass) throws Exception {
		Object xmlObject=null;
		
		if(StringUtility.hasValue(nodeName)){
			
			Class xmlClass=Class.forName(_PATH+targetClass);
			
			Method methods[] = xmlClass.getMethods();
			
			xmlObject=Class.forName(_PATH+targetClass).newInstance();
			
			XmlTable xt = XmlTransferUtil.getXmlTableDefinition(xmlObject.getClass());
			xt.xmlFields = XmlTransferUtil.getXmlFields(xmlObject.getClass());
			
			if(null != xmlObject){
				NodeList childNodes = node.getChildNodes();
				for(int i=0;i<childNodes.getLength();i++){
					Node cn = childNodes.item(i);
					
					if(cn.getNodeType()==1){
						String nn=cn.getNodeName();
						// invoke the setter method and set the value
						Method methodSetter = getSetterMethod(nn,methods);
						if(null != methodSetter){
							methodSetter.invoke(xmlObject, ParameterTypeConverter.convertToParameterType(methodSetter, cn.getTextContent()));
						}
					}
				}
			}
		}
		
		return xmlObject;
	}
	
	
}
