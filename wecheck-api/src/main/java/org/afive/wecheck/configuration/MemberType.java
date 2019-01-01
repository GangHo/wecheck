package org.afive.wecheck.configuration;

public class MemberType {
	public static final int MEMBER_TYPE_NONE = 0; // sns로 처음 로그인
	public static final int MEMBER_TYPE_USER = 1; // sns,userID가 있는 로그인
	public static final int MEMBER_TYPE_CONFIRM_REQUEST = 2; // sns,USERID가 없는 로그인

}
