package org.afive.wecheck.configuration;

import java.nio.charset.Charset;
import java.util.Base64;

import org.afive.wecheck.user.bean.AccessTokenBean;

public class BaseTool {
	
	public static String base64Encode(String token) {
	    byte[] encodedBytes = Base64.getEncoder().encode(token.getBytes());
	    return new String(encodedBytes, Charset.forName("UTF-8"));
	}

	public static AccessTokenBean createAccessToken(int snsLoginID, String uuid, int deviceType) {
		
		String token = snsLoginID+"yo"+uuid+"yo"+deviceType+"yoman";
		
	    byte[] encodedBytes = Base64.getEncoder().encode(token.getBytes());
	    Long currentTime=System.currentTimeMillis();
	    
	    String str=new String(encodedBytes, Charset.forName("UTF-8"));
	    str+="secretttttKeyDaYoooou!";
	    
	    byte[] encodedBytes2 = Base64.getEncoder().encode(str.getBytes());
	    
	    AccessTokenBean accessToken = new AccessTokenBean();
        accessToken.setAccessTokenID(new String(encodedBytes2, Charset.forName("UTF-8")));
        accessToken.setSnsLoginID(snsLoginID);
        accessToken.setUuid(uuid);
        accessToken.setDeviceType(deviceType);
        accessToken.setRegisteredTime(currentTime);
        
        
	    return accessToken;
	}
}
