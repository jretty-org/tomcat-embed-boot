package com.zollty.tomcat.demo;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 5701117482475493759L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().append("hello").close();
	}
}