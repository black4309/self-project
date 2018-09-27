package com.eventbob.dto.event;

public class EventDTO {
	
	private int uid;
	private String eventName;
	private String startDate;
	private int startHour;
	private int quantity;
	public int getUid() {
		
		
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	

}
