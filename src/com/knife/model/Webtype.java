package com.knife.model;

import com.jfinal.plugin.activerecord.Model;

public class Webtype extends Model<Webtype>
{

	public static final Webtype dao = new Webtype();

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ORDERBY = "orderby";

}
