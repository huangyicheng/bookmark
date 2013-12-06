package com.knife.model;

import com.jfinal.plugin.activerecord.Model;

public class Article extends Model<Article>
{

	private static final long serialVersionUID = 1L;

	public static final Article dao = new Article();

	public static final String ID = "id";
	public static final String UID = "uid";
	public static final String TITLE = "title";
	public static final String CREATE_DATE_TIME = "createDateTime";
	public static final String URL = "url";
	public static final String VIEWCOUNT = "viewCount";
	public static final String CONTENT = "replyContent";
	public static final String REPLYCOUNT = "replyCount";
	public static final String TAGS = "tags";
}
