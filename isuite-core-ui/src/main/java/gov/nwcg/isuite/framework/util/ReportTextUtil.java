package gov.nwcg.isuite.framework.util;

public class ReportTextUtil {

	public static String formatText(String val){
		String str="";

		//System.out.println("BEFORE: " + val);
		
		//String style="<STYLE> .small{line-height:70%;margin-top:0;margin-bottom:0;}</STYLE>";
		
		if(StringUtility.hasValue(val)){
			str=val.replaceAll("SIZE=\"12\"", "SIZE=\"1\"");
			str=str.replaceAll("SIZE=\"14\"", "SIZE=\"2\"");
			str=str.replaceAll("SIZE=\"16\"", "SIZE=\"3\"");
			//str=str.replaceAll("SIZE=\"18\"", "SIZE=\"3\""); This option isn't available in the editor UI
			
			str=str.replaceAll("&apos;", "'");
			str=str.replaceAll("&amp;","&");
			str=str.replaceAll("&lt;","<");
			str=str.replaceAll("&gt;",">");
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			// NOTE:: Currently only supporting SansSerif. Other fonts don't support <B> and <I> tags in Jasper.
			///////////////////////////////////////////////////////////////////////////////////////////////////
			str = str.replaceAll("FACE=\"Courier New\"", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE=\"COURIER NEW\"", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE=\"Arial\"", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE=\"ARIAL\"", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE=\"SANSSERIF\"", "FACE=\"SansSerif\"");
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			//str=str.replaceAll("<P ALIGN=\"LEFT\">", "");
			//str=str.replaceAll("</P>", "<BR>&nbsp;");
			//str=str.replaceAll("<P ALIGN=\"LEFT\">", "<DIV class=\"small\">");
			//str=str.replaceAll("</P>", "</DIV>");
			//str=str.replaceAll("<TEXTFORMAT LEADING=\"2\">", "<TEXTFORMAT LEADING=\"0\">");
			str=str.replaceAll("<TEXTFORMAT LEADING=\"2\">", "");
			str=str.replaceAll("</TEXTFORMAT>", "");
			str=str.replaceAll(" COLOR=\"#000000\"", "");
			str=str.replaceAll(" LETTERSPACING=\"0\"", "");
			str=str.replaceAll(" KERNING=\"1\"", "");
			str=str.replaceAll(" KERNING=\"0\"", "");
			
			/* Adjustments as a result of data transfer */
			str=str.replaceAll("SIZE='14'", "SIZE=\"1\"");
			str=str.replaceAll("SIZE='16'", "SIZE=\"2\"");
			str=str.replaceAll("SIZE='18'", "SIZE=\"3\"");
			str=str.replaceAll("SIZE='12'", "SIZE=\"1\"");
			str=str.replaceAll("<TEXTFORMAT LEADING='2'>", "");
			str=str.replaceAll("COLOR='#000000' LETTERSPACING='0' KERNING='1'", "");
			str = str.replaceAll("FACE='Courier New'", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE='COURIER NEW'", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE='Arial'", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE='ARIAL'", "FACE=\"SansSerif\"");
			str = str.replaceAll("FACE='SANSSERIF'", "FACE=\"SansSerif\"");
		}
		
		//System.out.println("AFTER: " + str);
		
		//return style+str+"<br>";
		return str + "<P></P>";
	}
	

}
