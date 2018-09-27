package com.eventbob.dto.join;

public class JoinDTO {
	
	
	private int uid;
	private String jointime;
	private int isWin;
	private int eventUID;
    private int applicantUID;
	public int getUid() {
		
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getJointime() {
		return jointime;
	}
	public void setJointime(String jointime) {
		this.jointime = jointime;
	}
	public int getIsWin() {
		return isWin;
	}
	public void setIsWin(int isWin) {
		this.isWin = isWin;
	}
	public int getEventUID() {
		return eventUID;
	}
	public void setEventUID(int eventUID) {
		this.eventUID = eventUID;
	}
	public int getApplicantUID() {
		return applicantUID;
	}
	public void setApplicantUID(int applicantUID) {
		this.applicantUID = applicantUID;
	}


}

	
