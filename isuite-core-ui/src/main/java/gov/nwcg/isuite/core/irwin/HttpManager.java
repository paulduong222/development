package gov.nwcg.isuite.core.irwin;

import gov.nwcg.isuite.framework.exceptions.TaskException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpManager {

	public static String getData(RequestPackage p) throws TaskException {
		
		BufferedReader reader = null;
		String uri = p.getUri();
		//System.out.println("ready to GET");
		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}
		
		try {
			URL url = new URL(uri);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
			//con.setReadTimeout(0);
			
			con.setRequestMethod(p.getMethod());
			//System.out.println("User-Agent Mozilla/5.0");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			//System.out.println("Accept-Language en-US,en;q=0.5");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			//System.out.println("Content-Type application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			if (p.getMethod().equals("POST")) {
				con.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
				writer.write(p.getEncodedParams());
				writer.flush();
			}
			
			StringBuilder sb = new StringBuilder();
			//System.out.println("con.getInputStream()");
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			//System.out.println("con.getInputStream() after");
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			//return null;
			//System.out.println("Exception e: " + e.getMessage());
			throw new TaskException(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					//return null;
					throw new TaskException(e.getMessage());
				}
			}
		}
		
	}
	
public static String getData2(RequestPackage p) throws TaskException {
		
		BufferedReader reader = null;
		String uri = p.getUri();
		//System.out.println("ready to GET");
		if (p.getMethod().equals("GET")) {
			uri += "?" + p.getEncodedParams();
		}
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			//con.setReadTimeout(0);
			
			con.setRequestMethod(p.getMethod());
			//System.out.println("User-Agent Mozilla/5.0");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			//System.out.println("Accept-Language en-US,en;q=0.5");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			//System.out.println("Content-Type application/x-www-form-urlencoded");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			if (p.getMethod().equals("POST")) {
				con.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
				writer.write(p.getEncodedParams());
				writer.flush();
			}
			
			StringBuilder sb = new StringBuilder();
			//System.out.println("con.getInputStream()");
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			//System.out.println("con.getInputStream() after");
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			//return null;
			//System.out.println("Exception e: " + e.getMessage());
			throw new TaskException(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					//return null;
					throw new TaskException(e.getMessage());
				}
			}
		}
		
	}
	
}