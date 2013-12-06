package com.knife.controller.admin;

import java.util.List;

import com.jfinal.core.Controller;
import com.knife.model.Tags;
import com.knife.model.User;
import com.knife.model.Website;
import com.knife.model.Webtype;

public class AdminController extends Controller
{
    public void index()
    {
    	Object user = getSessionAttr("user");
    	List<Webtype> webtypes = Webtype.dao.find("select * from webtype");
    	List<Tags> tags = Tags.dao.find("select * from Tags");
		for(Webtype wt:webtypes)
		{
			List<Website> websites = Website.dao.find("select * from website where wid=?",wt.get("id"));
			wt.put("websites", websites);
		}
		setAttr("tags",tags);
		setAttr("user",user);
		setAttr("webtypes", webtypes);
		render("/view/common/index.html");
    }
    
    public void detail()
    {
    	//System.out.println(getPara("id"));
    	User user = User.dao.findById(getPara(0));
    	setAttr("user",user);
    	//renderJson();
    	render("/view/admin/user.html");
    }
	

}
