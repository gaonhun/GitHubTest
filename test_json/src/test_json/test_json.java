package test_json;

import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class test_json {

	public static void main(String[] args){
		String jsonText = "{\"acs_nastech_000\":{\"inrfId\":\"aco_nastech_u22\"},\"acs_nastech_999\":{\"sqlErrMesgCtn\":\"\",\"cct\":\"12642\",\"sqlErrNo\":\"0\"}}";
		 
		/*
		 
		JSONParser parser = new JSONParser();
		JSONArray arr = null;
		 
		 
		try {
		     arr = (JSONArray)parser.parse(jsonText);
		} catch (ParseException e) {
		     System.out.println("변환에 실패");
		     e.printStackTrace();
		}
		
		System.out.println(arr);
		System.out.println(arr.get(0));
		System.out.println(arr.get(1));

*/     
		
		
		
		JSONParser parser = new JSONParser();
		String jsonString = "{\"stat\":{\"sdr\": \"aaaaaaaaaaaaaaaaaaaaa\",\"rcv\": \"bbbbbbbbbbbbbbbbbbbb\",\"time\": \"UTC in millis\"" + ",\"type\": 1,\"subt\": 1,\"argv\": [{\"1\":2},{\"2\":3}]},\"sstat\":\"1\"}"; 
		try { 
			Map json = (Map)parser.parse(jsonText); 
			Iterator iter = json.entrySet().iterator(); 
			Object entry = json.get("acs_nastech_000"); 
			System.out.println(entry); 
			System.out.println( entry.getClass() );
			} catch (ParseException e) {
				System.out.println("에러");
				e.printStackTrace(); 
			}
		
		
		

	}
}
