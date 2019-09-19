package gov.nwcg.isuite.framework.util;

public class DelimiterUtil {
	
	public static String[] getTokens(String buffer, String delimiter) throws Exception {
		
		String[] tokens = buffer.split("["+delimiter+"]+");

		return tokens;
	}
}
