package com.knife.model;

import com.jfinal.plugin.activerecord.Model;

public class WebsiteToTags extends Model<WebsiteToTags>
{
	public static final WebsiteToTags dao = new WebsiteToTags();

	public static final String ID = "id";
	public static final String WID = "wid";
	public static final String TID = "tid";

}
