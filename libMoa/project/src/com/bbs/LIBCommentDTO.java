package com.bbs;

public class LIBCommentDTO {
	
	private int uid ;
	private int memberUID;
	private String userName;
	private int contentsUID;
	private String contents;
	private String regDate;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getMemberUID() {
		return memberUID;
	}
	public void setMemberUID(int memberUID) {
		this.memberUID = memberUID;
	}
	public int getContentsUID() {
		return contentsUID;
	}
	public void setContentsUID(int contentsUID) {
		this.contentsUID = contentsUID;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}	
	
	
}
