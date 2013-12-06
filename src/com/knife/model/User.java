package com.knife.model;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>
{
	public static final User dao = new User();

	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

}
