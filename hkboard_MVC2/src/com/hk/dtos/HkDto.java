package com.hk.dtos;

import java.util.Date;

//DTO클래스 구현: data를 담아서 전달하는 객체[펜1,펜2,...]
public class HkDto {
	//접근제한자: public>protected>default>private
	private int seq;
	private String id;
	private String title;
	private String content;
	private Date regdate;
	
	public HkDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	  
	//생성자를 오버로딩하면 default생성자가 자동생성 안됨---> default생성자 직접작성
	public HkDto(int seq, String id, String title, String content, Date regdate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
	}
	
	public HkDto(String id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "HkDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", regdate=" + regdate
				+ "]";
	}
	
	
	
}
