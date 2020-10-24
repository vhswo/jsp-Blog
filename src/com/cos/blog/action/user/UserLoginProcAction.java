package com.cos.blog.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.action.Action;
import com.cos.blog.dao.UserDao;
import com.cos.blog.model.User;
import com.cos.blog.utils.Script;

public class UserLoginProcAction implements Action {
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(request.getParameter("remember"));  // 되는지 체크확인  체크하면 on 이라고 뜸
		
		
		
		
		User user = User.builder()
				.username(request.getParameter("username"))
				.password(request.getParameter("password"))
				.build();
		System.out.println(user);
		UserDao userDao = UserDao.getInstance();
		User userEntity = userDao.로그인(user);
		
		//로그인 성공부분
		if (userEntity != null) {
			
			if(request.getParameter("remember") !=null) {
				Cookie cookie = new Cookie("remember", userEntity.getUsername());
				response.addCookie(cookie);
			}else {
					Cookie cookie = new Cookie("remember", "");
					cookie.setMaxAge(0);  // 쿠키를 가지고 있을 시간
					//response.setGeader("Set-Cookie", remember="+userEntity.getUsername()); 기본 
					response.addCookie(cookie);			 
			}
			
			
			HttpSession session = request.getSession();
			session.setAttribute("principal", userEntity);
			Script.href(response, "/", "로그인성공");
			response.sendRedirect("/index.jsp");
		}else {
			Script.back(response, "아이디 혹은 비밀번호가 틀렸습니다.");
		}
		
		
		
		
		// 1. 로그인 진행 (select) Model로 이동
		// 2. 세션에 저장
		// 3. 메인 페이지로 이동 Redirect
	}
}
