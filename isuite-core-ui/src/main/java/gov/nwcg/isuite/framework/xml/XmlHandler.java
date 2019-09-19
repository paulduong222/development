package gov.nwcg.isuite.framework.xml;

import gov.nwcg.isuite.xml.ross.Dataset;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class XmlHandler {
	private XmlSchemaTypeEnum xmlSchemaType;
	private String xsdBasePath="";
	private Boolean formatXml=false;
	private String xmlSchema=null;
	
	public XmlHandler(){
		
	}
	
	public XmlHandler(XmlSchemaTypeEnum xsType, String xsdpath){
		this();
		xmlSchemaType=xsType;
		xsdBasePath=xsdpath;
	}
	
	/**
	 * @return the xmlSchemaType
	 */
	public XmlSchemaTypeEnum getXmlSchemaType() {
		return xmlSchemaType;
	}



	/**
	 * @return the xsdBasePath
	 */
	public String getXsdBasePath() {
		return xsdBasePath;
	}

	/**
	 * @param xsdBasePath the xsdBasePath to set
	 */
	public void setXsdBasePath(String xsdBasePath) {
		this.xsdBasePath = xsdBasePath;
	}

	/**
	 * @param xmlSchemaType the xmlSchemaType to set
	 */
	public void setXmlSchemaType(XmlSchemaTypeEnum xmlSchemaType) {
		this.xmlSchemaType = xmlSchemaType;
	}



	/**
	 * Returns the formatXml.
	 *
	 * @return 
	 *		the formatXml to return
	 */
	public Boolean getFormatXml() {
		return formatXml;
	}

	/**
	 * Sets the formatXml.
	 *
	 * @param formatXml 
	 *			the formatXml to set
	 */
	public void setFormatXml(Boolean formatXml) {
		this.formatXml = formatXml;
	}

	/**
	 * Returns the xmlSchema.
	 *
	 * @return 
	 *		the xmlSchema to return
	 */
	public String getXmlSchema() {
		return xmlSchema;
	}

	/**
	 * Sets the xmlSchema.  If left null, validation is turned off.
	 * 
	 * @param xmlSchema 
	 *			the xmlSchema to set
	 */
	public void setXmlSchema(String xmlSchema) {
		this.xmlSchema = xmlSchema;
	}

	/**
	 * @param root
	 * @return
	 * @throws Exception
	 */
	public StringBuffer marshall(Object rootNode) throws Exception {

		if(null == this.xmlSchemaType)
			throw new Exception("Unable to proceed, xmlSchemaType is null.");
		
		JAXBContext jc = JAXBContext.newInstance( this.xmlSchemaType.getContextPath());		
		
		ByteArrayOutputStream f = new ByteArrayOutputStream(); 
		
		Marshaller m = jc.createMarshaller();
		
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatXml);
		
		m.marshal(rootNode, f);

		return new StringBuffer().append(f.toString());
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	private Schema getSchema() throws Exception {
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		File file = new File(xsdBasePath + xmlSchema);
		Schema schema = null;
		
		if(file.isFile())
			schema=schemaFactory.newSchema(file);
		else
			throw new Exception("Schema file not found.");
		
		return schema;
	}

	/**
	 * @param buffer
	 * @return
	 * @throws Exception
	 */
	public Object unmarshall(StringBuffer buffer) throws Exception {

		if(null == this.xmlSchemaType)
			throw new Exception("Unable to proceed, xmlSchemaType is null.");
		
		if(null == xsdBasePath)
			throw new Exception("Unable to proceed, xsdBasePath is null.");
		
		JAXBContext jc = JAXBContext.newInstance( this.xmlSchemaType.getContextPath());		
		
		
		Unmarshaller u = jc.createUnmarshaller();
		
		ByteArrayInputStream stream = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));

		if(null != xmlSchema)
			u.setSchema(getSchema());
			
		Object rootNode = u.unmarshal(stream);
		
		return rootNode;
	}

	public Object unmarshall(File file) throws Exception {

		if(null == this.xmlSchemaType)
			throw new Exception("Unable to proceed, xmlSchemaType is null.");
		
		if(null == xsdBasePath)
			throw new Exception("Unable to proceed, xsdBasePath is null.");
		
		JAXBContext jc = JAXBContext.newInstance(this.getXmlSchemaType().getContextPath() );		
		
		Unmarshaller u = jc.createUnmarshaller();
		
		if(null != xmlSchema)
			u.setSchema(getSchema());
			
		Object rootNode = u.unmarshal(file);
		
		return rootNode;
	}
	
	public static void main(String[] args){
		try{
			File file = new File("c:\\workfiles\\rossxsd\\sample_ross2.xml");
			
			if(null != file && file.exists()){
				XmlHandler handler = new XmlHandler();
				handler.setXmlSchemaType(XmlSchemaTypeEnum.ROSS_XML_DATA);
				
				Dataset dataset = (Dataset)handler.unmarshall(file);
				
				if(null != dataset){
					if(null != dataset.getData()){
						if(null != dataset.getData().getRow() ){
							System.out.println(dataset.getData().getRow().size());
						}
					}
				}
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	
}
