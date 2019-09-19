package gov.nwcg.isuite.framework.util;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.axis.utils.ByteArrayOutputStream;

 
/**
 * This utility class provides an abstraction layer for sending multipart HTTP
 * POST requests to a web server.
 * @author www.codejava.net
 *
 */
public class MultipartUtility {
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private URLConnection httpConn;
    private Boolean isHttps=false;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;
    private static final int BUFFER_SIZE = 4096; 
    public String attachmentMessage="";
    
    
    /**
     * This constructor initializes a new HTTP POST request with content type
     * is set to multipart/form-data
     * @param requestURL
     * @param charset
     * @throws IOException
     */
    public MultipartUtility(String requestURL, String charset)
            throws IOException {
        this.charset = charset;
         
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";
         
        URL url = new URL(requestURL);
        if(requestURL.startsWith("https"))
        	this.isHttps=true;
        
        if(this.isHttps==true){
            httpConn = (HttpsURLConnection) url.openConnection();
        }else{
            httpConn = (HttpURLConnection) url.openConnection();
        }
        
        if(this.isHttps==true){
        	try{
	        	TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						public void checkClientTrusted(X509Certificate[] certs, String authType) {
						}
						public void checkServerTrusted(X509Certificate[] certs, String authType) {
						}
					}
	        	};        	
	        	SSLContext sc = SSLContext.getInstance("SSL");
	        	sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        	
	        	((HttpsURLConnection)httpConn).setSSLSocketFactory(sc.getSocketFactory());
        	}catch(Exception e){
        		throw new IOException("MultipartUtility Constructor() " + e.getMessage());
        	}
        }
        
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        httpConn.setRequestProperty("User-Agent", "e-ISuite");
        httpConn.setRequestProperty("Test", "Test");
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);
    }
 
    /**
     * Adds a form field to the request
     * @param name field name
     * @param value field value
     */
    public void addFormField(String name, String value) {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(
                LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }
 
    /**
     * Adds a upload file section to the request
     * @param fieldName name attribute in <input type="file" name="..." />
     * @param uploadFile a File to be uploaded
     * @throws IOException
     */
    public void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
 
        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();
         
        writer.append(LINE_FEED);
        writer.flush();    
    }
 
    /**
     * Adds a header field to the request.
     * @param name - name of the header field
     * @param value - value of the header field
     */
    public void addHeaderField(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }
     
    /**
     * Completes the request and receives response from the server.
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    public List<String> finish() throws IOException {
        List<String> response = new ArrayList<String>();
 
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
 
        // checks server's status code first
        int status = -1;
        if(this.isHttps==true){
            status=((HttpsURLConnection)httpConn).getResponseCode();
        }else{
            status=((HttpURLConnection)httpConn).getResponseCode();
        }
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
            reader.close();
            if(this.isHttps==true){
                ((HttpsURLConnection)httpConn).disconnect();
            }else{
                ((HttpURLConnection)httpConn).disconnect();
            }
            //httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
 
        return response;
    }
    
    public byte[] getAttachment() throws IOException {
        List<String> response = new ArrayList<String>();
 
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
 
        // checks server's status code first
        int status = -1;
        if(this.isHttps==true){
            status=((HttpsURLConnection)httpConn).getResponseCode();
        }else{
            status=((HttpURLConnection)httpConn).getResponseCode();
        }
        if (status == HttpURLConnection.HTTP_OK) {
        	
        	String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            
            //System.out.println(disposition);
            //System.out.println(contentType);

            if(contentType.equals("text")){
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                attachmentMessage=response.toString();
                
                reader.close();
                if(this.isHttps==true){
                    ((HttpsURLConnection)httpConn).disconnect();
                }else{
                    ((HttpURLConnection)httpConn).disconnect();
                }
                
                return null;
            }
            
            int contentLength = httpConn.getContentLength();
 
            System.out.println(contentLength);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
             
            int bytesRead = -1;
            byte[] filebytes=new byte[contentLength];
            byte[] buffer = new byte[BUFFER_SIZE];
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
            	baos.write(buffer,0,bytesRead);
            	//outputStream.write(filebytes,0,bytesRead);
                //outputStream.write(buffer, 0, bytesRead);
            }
            
            filebytes=baos.toByteArray();
            baos.close();
            outputStream.close();
            inputStream.close();            
            if(this.isHttps==true){
                ((HttpsURLConnection)httpConn).disconnect();
            }else{
                ((HttpURLConnection)httpConn).disconnect();
            }
            //httpConn.disconnect();
            
            return filebytes;
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
 
    }    
}