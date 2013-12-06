package com.knife.controller.blog;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.knife.model.Article;

public class BlogController extends Controller{

	public void index() 
	{
		List<Map<String,Object>> articles = new ArrayList<Map<String,Object>>();
		String path = PathKit.getWebRootPath()+"\\upload\\article";
		File floder = new File(path);
		if( floder.exists() )
		{
			File[] files = floder.listFiles(new FileFilter(){
				@Override
				public boolean accept(File file) {
					return file.isDirectory();
				}});
			for(File file:files)
			{
				Map<String,Object> filemap = new HashMap<String,Object>();
				List<String> posts = new ArrayList<String>();
				String[] lists = file.list(new FilenameFilter(){
					@Override
					public boolean accept(File arg0, String name) {
						return name.endsWith(".md");
					}});
				for(String list:lists)
				{
					
					String post = list.substring(0,list.lastIndexOf(".md"));
					posts.add(post);
				}
				filemap.put("name", file.getName());
				filemap.put("posts", posts);
				articles.add(filemap);
			}
		}
		setAttr("articles", articles);
		render("/view/blog/index.html");
	}
	
	public void read() throws Exception
	{
		
		String a = URLDecoder.decode(getPara(0),"UTF-8");//new String(getPara(0).getBytes("ISO-8859-1"),"utf-8");
		String b = URLDecoder.decode(getPara(1),"UTF-8");//new String(getPara(1).getBytes("ISO-8859-1"),"utf-8");
		String filename = PathKit.getWebRootPath()+"\\upload\\article"+"\\"+a+"\\"+b+".md";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuffer sb = new StringBuffer();
		while((s = in.readLine()) != null)
		{
			sb.append(s+"\n");
		}
		in.close();
		Map<String,Object> article = new HashMap<String,Object>();
		article.put("content", sb.toString());
		setAttr("article",sb.toString());
		render("/view/blog/post.html");
	}
	
	public void manage()
	{
		render("/view/blog/upload.html");
	}
	
	public void upload() {
		List<UploadFile> files = getFiles(PathKit.getWebRootPath()+"\\upload",1024*1024*1024,"utf-8");
		setAttr("msg", "<div id='status'>success</div><div id='message'>Successfully uploaded.</div><div id='upload_data'>ik</div>");
		renderJson();
	}
	
	public void writer()
	{
		render("/view/blog/writer.html");
	}
	
	public void post()
	{
		Object userId = getSessionAttr("userid");
		String title = getPara("title");
		String tags = getPara("tags");
		String text = getPara("text");
		Article article = new Article();
		article.set("title", title);
		article.set("tags", tags);
		article.set("replyContent", text);
		article.set("createDateTime", new Date());
		article.set("uid", userId);
		article.save();
		renderJson();
	}

	public void list()
	{
		render("/view/blog/list.html");
	}
}
