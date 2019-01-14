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
	
	public final static int CHURCHSERVICE_NULL = 20;
	public final static int CHURCHSERVICE_BEFORE = 21;
	public final static int CHURCHSERVICE_FINISHED = 22;
	public final static int CHURCHSERVICE_ALREADY_ATTENDED = 23;
	public final static int CHURCHSERVICE_FAILED_TOO_FAR = 24;

	public final static int ACCESS_DENIED_USERID_DOESNT_MATCH = 302;
	
	public final static int ARTICLE_STATE_DELETED = 30;
	public final static int COMMENT_STATE_DELETED = 31;
	
	public final static int COMFIRMREQUESTID_IS_NULL = 61;
	public final static int USER_IS_NOT_APPROVED = 62;
}
