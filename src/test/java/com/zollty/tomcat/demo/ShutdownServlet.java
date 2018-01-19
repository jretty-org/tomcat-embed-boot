package com.zollty.tomcat.demo;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jretty.tomcat.TomcatManage;

@WebServlet(name = "shutdown", urlPatterns = { "/shutdown" })
public class ShutdownServlet extends HttpServlet {
	private static final long serialVersionUID = -7327285584754507249L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().append("shutdown server...").close();
		// 关闭服务器
		TomcatManage.shutdown();
	}

}