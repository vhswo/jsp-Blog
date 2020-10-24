package com.cos.blog.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@Builder
public class User {
	private int id; // 넘버링 1,2,3,4
	private String username; // ssarmango
	private String password;
	private String email;
	private String address;
	private Timestamp createData;
	
	
}