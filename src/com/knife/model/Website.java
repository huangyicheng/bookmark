package com.knife.model;

import com.jfinal.plugin.activerecord.Model;

public class Website extends Model<Website>
{

	public static final Website dao = new Website();

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String URL = "url";
	public static final String WID = "wid";
	public static final String READCOUNT = "readcount";
	public static final String TAGS = "tags";

}
