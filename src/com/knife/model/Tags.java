package com.knife.model;

import com.jfinal.plugin.activerecord.Model;

public class Tags extends Model<Tags>
{
	public static final Tags dao = new Tags();

	public static final String ID = "id";
	public static final String NAME = "name";

}
