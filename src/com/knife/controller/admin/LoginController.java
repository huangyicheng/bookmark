package com.knife.controller.admin;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.knife.interceptor.SessionInterceptor;
import com.knife.model.User;
@Before(SessionInterceptor.class)
public class LoginController extends Controller
{
	private User user;
	public void index() 
	{
		if(getSessionAttr("user")!= null) 
		{
			redirect("/admin");
		} 
		else 
		{
			render("/view/admin/login.html");
		}
	}
	@ClearInterceptor(ClearLayer.ALL)
	public void login()
	{
		user = getModel(User.class);
		user = User.dao.findFirst(
				"select * from user where username=? and password=?",
				user.getStr(User.USERNAME), user.getStr(User.PASSWORD));
		if (user == null) 
		{
			setAttr("error", 1);
			setAttr("msg", "用户名或密码错误");
		} 
		else 
		{
			setSessionAttr("user", user);
			setSessionAttr("userid",user.get("id"));
			setAttr("error", 0);
			setAttr("msg", "登陆成功");
		}
		renderJson();
	}


}
