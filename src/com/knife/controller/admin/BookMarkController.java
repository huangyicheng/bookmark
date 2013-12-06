package com.knife.controller.admin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.knife.interceptor.SessionInterceptor;
import com.knife.model.Tags;
import com.knife.model.Website;
import com.knife.model.WebsiteToTags;
import com.knife.model.Webtype;

@Before(SessionInterceptor.class)
public class BookMarkController extends Controller {
	private Webtype webtype;
	private Website website;
	private Tags t;
	
	public void index() 
	{
		Page<Webtype> page = Webtype.dao.paginate(getParaToInt(0, 1), 10, "select *", "from webtype");
		setAttr("webtypes", page);
    	render("/view/admin/website/webtypelist.html");
	}
	
	public void webtypelist()
    {
		Page<Webtype> page = Webtype.dao.paginate(getParaToInt(0, 1), 10, "select *", "from webtype");
		setAttr("webtypes", page);
    	render("/view/admin/website/webtypelist.html");
    }
	
	public void addWebtype()
	{
		render("/view/admin/website/addWebtype.html");
	}
	
	
	public void add()
	{
		webtype = getModel(Webtype.class);
		webtype.save();
		setAttr("error", 0);
		setAttr("msg", "success");
		renderJson();
	}
	public void editWebtype()
	{
		Webtype webtype = Webtype.dao.findById(getPara(0));
		setAttr("webtype", webtype);
		render("/view/admin/website/editWebtype.html");
	}
	
	public void edit()
	{
		webtype = getModel(Webtype.class);
		webtype.update();
		setAttr("error", 0);
		setAttr("msg", "success");
		renderJson();
	}
	
	public void deleteWebtype()
	{
        webtype = getModel(Webtype.class);
		
		Boolean flag = webtype.delete();
		if(flag)
		{
			setAttr("error", 0);
			setAttr("msg", "success");
		}
		else
		{
			setAttr("error", 1);
			setAttr("msg", "fail");
		}
		renderJson();
	}
	
	public void websitelist()
    {
		Page<Website> page = Website.dao.paginate(getParaToInt(1,1), 10, "select * ","from website where wid=?",getPara(0));
		Webtype webtype = Webtype.dao.findById(getPara(0));
		setAttr("websites",page);
		setAttr("webtype",webtype);
		render("/view/admin/website/websitelist.html");
    }
	
	public void addWebsite()
	{
		setAttr("wid",getPara(0));
		render("/view/admin/website/addWebsite.html");
	}
	
	public void add2() throws Exception
	{
		website = getModel(Website.class);
		String tags = URLDecoder.decode(getPara("tags"),"UTF-8");
		//String tags = getPara("tags");
		Boolean wsflag = website.save();
		if(tags != null && !"".equals(tags))
		{
			String[] stag = tags.split("#");
			int wid = website.getInt("id");
			for(String tag:stag)
			{
				WebsiteToTags websiteToTags = new WebsiteToTags();
				//List<Tags> ts = Tags.dao.find("select * from tags where name='"+tag+"'");
				t = Tags.dao.findFirst("select * from tags where name=?",tag);
				if(t == null )
				{
					Tags addtag = new Tags();
					addtag.set("name", tag);
					Boolean tagflag = addtag.save();
					if(tagflag)
					{
						int tid = addtag.getInt("id");
						websiteToTags.set("tid", tid);
						websiteToTags.set("wid", wid);
						Boolean wttflag = websiteToTags.save();
					}
				}
				else
				{
					//int tid = ts.get(0).getInt("id");
					int tid = t.getInt("id");
					websiteToTags.set("tid", tid);
					websiteToTags.set("wid", wid);
					Boolean wttflag = websiteToTags.save();
				}
			}
		}
		
		setAttr("error", 0);
		setAttr("msg", "success");
		renderJson();
	}
	
	public void editWebsite()
	{
		Website website = Website.dao.findById(getPara(0));
		List<WebsiteToTags> wtts = WebsiteToTags.dao.find("select * from website_to_tags where wid=?",getPara(0));
		List<Tags> tags = new ArrayList<Tags>();
		for(WebsiteToTags wtt:wtts)
		{
			Tags tag = Tags.dao.findById(wtt.get("tid"));
			tags.add(tag);
		}
		setAttr("tags",tags);
		setAttr("website", website);
		render("/view/admin/website/editWebsite.html");
	}
	
	public void edit2()
	{
		website = getModel(Website.class);
		website.update();
		setAttr("error", 0);
		setAttr("msg", "success");
		renderJson();
	}
	
	public void deleteWebsite()
	{
		website = getModel(Website.class);
		
		Boolean flag = website.delete();
		if(flag)
		{
			setAttr("error", 0);
			setAttr("msg", "success");
		}
		else
		{
			setAttr("error", 1);
			setAttr("msg", "fail");
		}
		renderJson();
		
	}
}
