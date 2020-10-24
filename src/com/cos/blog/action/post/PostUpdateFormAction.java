package com.cos.blog.action.post;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.action.Action;
import com.cos.blog.dao.PostDao;
import com.cos.blog.model.Post;

public class PostUpdateFormAction implements Action {
 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.세션확인
			HttpSession session = request.getSession();
			if(session.getAttribute("principal") == null) 
				return;
		// 2. 공백, null 확인
		
		//3 . 값 검증
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		//String title = request.getParameter("title");
		//title = title.replace("<", "&lt");
		//title = title.replace(">", "&gt");
		
		PostDao postDao = PostDao.getInstance();
		Post postEntity = postDao.상세보기(id);
		
		request.setAttribute("post",postEntity);
		
		RequestDispatcher dis 
			= request.getRequestDispatcher("post/updateForm.jsp");  // 디스패처는 리퀘스트가 사라지지 않게 들고오는거
		dis.forward(request, response);
		
	}

}
