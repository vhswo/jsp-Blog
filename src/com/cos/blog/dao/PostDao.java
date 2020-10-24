package com.cos.blog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DBConn;
import com.cos.blog.model.Post;
import com.cos.blog.model.User;


public class PostDao {
	
	private static PostDao instance = new PostDao();
	
	public static PostDao getInstance() {
		return instance;
	}
	
	private PostDao() {}
	
	public int 글개수(String keyword) {
		// 1. 회원가입 진행(Insert), Model로 이동
		String sql = "SELECT count(*) FROM post WHERE title like ? OR content like ?";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setString(2, "%"+keyword+"%");
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);  // 번호 로 찾을수 있음
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public int 글개수() {
		// 1. 회원가입 진행(Insert), Model로 이동
		String sql = "SELECT count(*) FROM post";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);  // 번호 로 찾을수 있음
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public int 삭제하기(int id) {
		String sql = "DELETE FROM post WHERE id =?";
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int 글수정하기(Post post) {
		// 1. 회원가입 진행(Insert), Model로 이동
		String sql = "UPDATE post SET title =?, content =?, WHERE id=?";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getContent());
			pstmt.setInt(3, post.getId());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	

	public int 글쓰기(Post post) {
		// 1. 회원가입 진행(Insert), Model로 이동
		String sql = "INSERT INTO post(userId,title,content,readCount, createDate) VALUES(?,?,?,?,now())";
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, post.getUserId());
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getContent());
			pstmt.setInt(4, post.getReadCount());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int 조회수증가(int id) {
		String sql = "UPDATE post SET readCount = readCount+1 WHERE id =?";
		Connection conn = DBConn.getInstance();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	

	public Post 상세보기(int id) {

		String sql = "SELECT * FROM post WHERE id = ?";
		Connection conn = DBConn.getInstance();

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Post post = Post.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.createDate(rs.getTimestamp("createDate"))
						.userId(rs.getInt("userId"))
						.build();
				return post;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Post> 글목록(int page, String keyword) { //page 글목록 갯수
		List<Post> posts = new ArrayList<>();

		// ORDER 순서대로 , DESC 내림 차순 or ASC 정상순
		String sql = "SELECT * FROM post WHERE title like ? OR content like ? ORDER BY id DESC limit ?,3";

		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql); //preparedStatement 는 왠만한건 다 막아줌
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setString(2, "%"+keyword+"%");
			pstmt.setInt(3, page*3);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Post post = Post.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.createDate(rs.getTimestamp("createDate"))
						.userId(rs.getInt("userId"))
						.build();
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Post> 글목록(int page) { //page 글목록 갯수
		List<Post> posts = new ArrayList<>();

		// ORDER 순서대로 , DESC 내림 차순 or ASC 정상순
		String sql = "SELECT * FROM post ORDER BY id DESC limit ?,3";

		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql); //preparedStatement 는 왠만한건 다 막아줌
			pstmt.setInt(1, page*3);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Post post = Post.builder()
						.id(rs.getInt("id"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.readCount(rs.getInt("readCount"))
						.createDate(rs.getTimestamp("createDate"))
						.userId(rs.getInt("userId"))
						.build();
				posts.add(post);
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
