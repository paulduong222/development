package gov.nwcg.isuite.framework.core.xmltransfer.importer;

import gov.nwcg.isuite.core.persistence.DataTransferDao;
import gov.nwcg.isuite.framework.core.xmltransfer.AbstractXmlTransfer;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlField;
import gov.nwcg.isuite.framework.core.xmltransfer.data.XmlTable;
import gov.nwcg.isuite.framework.core.xmltransfer.utility.XmlTransferUtil;
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
	
	private static final String _PATH="gov.nwcg.isuite.core.domain.xml.";

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
	protected Object populateDataObjects(String nodeName, Element node, String nodeNameParsing, String targetClass) throws Exception {
		Object xmlObject=null;
		
		if(StringUtility.hasValue(nodeName)){
			super.log(Level.DEBUG,"Parsing: "+nodeNameParsing);

			if(nodeNameParsing.equals("Incident.IncidentQuestion")){
				//System.out.println("");
			}
			
			if(targetClass.equals("IncidentCostRateStateKind")){
				//counter++;
				//System.out.println("counter: "+counter);
			}
			
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
						//System.out.println(nn);
						//if(nn.equals("IapPositionItemCode")){
						//	System.out.println("");
						//}
								
						// does this node have children?
						if(cn.getChildNodes().getLength()>1){
							XmlField xmlField=null;
							for(XmlField f : xt.xmlFields){
								if(f.type.equals("COMPLEX") && f.name.equals(nn)){
									xmlField=f;
									break;
								}
							}
							
							Element child=(Element)cn;
							if(null != xmlField){
								Object childObject=this.populateDataObjects(nn, child,nodeNameParsing+"."+nn,xmlField.target.getSimpleName());
								
								Method methodSetter = getSetterMethod(nn,methods);
								
								if(null != methodSetter){
									methodSetter.invoke(xmlObject, childObject);
								}else{
									// add s to end of name and check for setter
									methodSetter = getSetterMethod(nn+"s",methods);
									if(null != methodSetter){
										if(this.isMethodParameterCollection(methodSetter)==true){
											// invoke get method 
											Method methodGetter = getGetterMethod(nn+"s",methods);
											Collection<Object> objects = (Collection<Object>)methodGetter.invoke(xmlObject);
											if(!CollectionUtility.hasValue(objects)){
												objects = new ArrayList<Object>();
											}
											objects.add(childObject);
											
											// invoke setter method
											methodSetter.invoke(xmlObject, objects);
										}
									}else{
										// unknown field/setter
									}
								}
							}
							
						}else{
							// invoke the setter method and set the value
							Method methodSetter = getSetterMethod(nn,methods);
							if(null != methodSetter){
								methodSetter.invoke(xmlObject, ParameterTypeConverter.convertToParameterType(methodSetter, cn.getTextContent()));
							}
						}
					}
				}
			}
		}
		
		return xmlObject;
	}
	
	
}
