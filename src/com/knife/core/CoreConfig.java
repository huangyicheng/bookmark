package com.knife.core;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.knife.controller.admin.AdminController;
import com.knife.controller.admin.BookMarkController;
import com.knife.controller.admin.LoginController;
import com.knife.controller.blog.BlogController;
import com.knife.handler.HtmlExtensionHandler;
import com.knife.model.Article;
import com.knife.model.Tags;
import com.knife.model.User;
import com.knife.model.Website;
import com.knife.model.WebsiteToTags;
import com.knife.model.Webtype;

/**
 * API引导式配置
 */
public class CoreConfig extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode", false));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		//me.add("/", CommonController.class);
		//me.add("/blog", BlogController.class);
		me.add("/",LoginController.class);
		me.add("/bookmark",BookMarkController.class);
		me.add("/admin",AdminController.class);
		me.add("/blog",BlogController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("webtype", Webtype.class);	
		arp.addMapping("website", Website.class);
		arp.addMapping("user", User.class);
		arp.addMapping("article", Article.class);
		arp.addMapping("website_to_tags", WebsiteToTags.class);
		arp.addMapping("tags", Tags.class);
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		//me.add(new ContextPathHandler());
		me.add(new HtmlExtensionHandler());
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
