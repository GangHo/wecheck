package org.afive.wecheck.configuration;

public class ResponseCode {
	public final static int SUCCESS = 0;
	public final static int FAILED_NO_MATCH = 200;
	
	public final static int ACCESS_DENIED_WRONG_ACCESSCODE = 300;
	public final static int ACCESS_DENIED_SNSID_DOESNT_MATCH = 301;
	
	public final static int FAILED_FILE_UPLOAD = 400;
	public final static int FAILED_FILE_NOT_FOUND=401;
	
	public final static int FAILED_SERVER_ERROR = 500;
	
	public final static int SNS_LOGIN_INSERT_SUCCESS = 1;
	public final static int SNS_LOGIN_INSERT_FAIL = 2;
	public final static int FCM_INSERT_SUCCESS = 10;
	public final static int FCM_UPDATE_SUCCESS = 11;
	
}
