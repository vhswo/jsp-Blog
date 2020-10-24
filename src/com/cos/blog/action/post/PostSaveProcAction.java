package com.cos.blog.action.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.action.Action;
import com.cos.blog.dao.PostDao;
import com.cos.blog.model.Post;
import com.cos.blog.utils.Script;

public class PostSaveProcAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 세션확인
		HttpSession session = request.getSession();
		if(session.getAttribute("principal") == null) return;
		
		// 2. 공백, null 확인
		
		// 3. 값 검증 ( title에 <  > 코드가 들어오는걸 방지 )
		String title = request.getParameter("title");
		title = title.replaceAll("<", "&lt;");
		title = title.replaceAll(">", "&gt;");
		int userId = Integer.parseInt(request.getParameter("userId"));
		String content = request.getParameter("content");
		
		Post post = Post.builder()
				.title(title)
				.content(content)
				.readCount(0)
				.userId(userId)
				.build();
		
		PostDao postDao = PostDao.getInstance();
		int result = postDao.글쓰기(post);
			Script.href(response, "/", "글쓰기성공");
		if(result == 1) {
			Script.back(response, "글쓰기살패");
		}else {
	
		}
		
		response.sendRedirect("index.jsp");
	}

}
