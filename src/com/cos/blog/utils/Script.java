package com.cos.blog.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class Script {

	public static void href(HttpServletResponse response, String url, String msg) throws IOException {
		//BufferedWriter 는 내려쓰기가 없다.("안녕 |n")
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();   
		out.print("<script>");
			out.print("alert('"+msg+"');");
			out.print("location.href= '"+url+"';");
		out.print("</script>");
	}
	
	public static void back(HttpServletResponse response, String msg) throws IOException {
	
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
			out.print("alert('"+msg+"');");
			out.print("history.back();");
		out.print("</script>");
	}
	
}
