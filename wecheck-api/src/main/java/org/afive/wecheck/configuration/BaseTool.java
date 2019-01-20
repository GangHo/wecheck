package org.afive.wecheck.configuration;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.afive.wecheck.user.bean.AccessTokenBean;

public class BaseTool {
	
	public static String base64Encode(String token) {
	    byte[] encodedBytes = Base64.getEncoder().encode(token.getBytes());
	    return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	public static AccessTokenBean createAccessToken(int snsLoginID, String uuid, int deviceType) {
		Long currentTime=System.currentTimeMillis();
		String token = currentTime+snsLoginID+"y"+uuid+"o"+deviceType+"yoman";
		
	    byte[] encodedBytes = Base64.getEncoder().encode(token.getBytes());
	    
	    
	    String str=new String(encodedBytes, Charset.forName("UTF-8"));
	    str+="secretttttKeyDaYoooou!";
	    
	    byte[] encodedBytes2 = Base64.getEncoder().encode(str.getBytes());
	    
	    AccessTokenBean accessToken = new AccessTokenBean();
        accessToken.setAccessTokenID(new String(encodedBytes2, Charset.forName("UTF-8")));
        accessToken.setSnsLoginID(snsLoginID);
        accessToken.setUuid(uuid);
        accessToken.setDeviceType(deviceType);
        
        /**
    	 * 2019-01-17 edited by gangho - 시간등록 DB now() 가 아닌 자바에서 등록
    	 */
    	String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    	
    	accessToken.setRegisteredTime(localDateTime);
        
        
	    return accessToken;
	}
	
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
         
        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }
 
        return (dist);
    }
     
 
    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}
