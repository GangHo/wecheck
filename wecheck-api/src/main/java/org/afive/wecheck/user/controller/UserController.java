package org.afive.wecheck.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.afive.wecheck.user.bean.UserBean;
import org.afive.wecheck.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
public class UserController {

//	@Autowired
//	private UserMapper userMapper;
//	
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	private Map<String, Object> getList(){
//		Map<String, Object> result = new HashMap<>();
//		result.put("data", userMapper.getList());
//		return result;
//	}
//	
//	@RequestMapping(value = "/is-user", method = RequestMethod.POST)
//	private UserBean getUserIfExists() {
//		return null;
//		
//	}
//	
//	@RequestMapping(value = "/{userID}", method = RequestMethod.GET)
//	private UserBean get(@PathVariable(value = "userID") String userID){
//		return userMapper.get(userID);
//	}
//	
//	@RequestMapping(value = "", method = RequestMethod.PUT)
//	private void update(UserBean userBean){
//		
//		if(userBean.getUserType()==0) userBean.setUserType(1);
//		userMapper.update(userBean);
//
//	}
//	
//	@RequestMapping(value = "/{userID}", method = RequestMethod.DELETE)
//	private void delete(@PathVariable(value = "userID") String userID){
//		userMapper.delete(userID);
//	}
	
	
	
}
