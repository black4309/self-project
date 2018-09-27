package com.eventbob.dao.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.eventbob.dto.applicant.ApplicantDTO;
import com.eventbob.dto.event.EventDTO;
import com.eventbob.util.database.DBConn;

public class EventDAO {


	private Connection conn = DBConn.getConnection(); // db연결

	PreparedStatement pstmt = null; // 실행
	Statement stmt = null;

	ResultSet rs = null; // 출력


	public ArrayList<EventDTO> select() throws Exception
	{
		ArrayList<EventDTO> dtoList = new ArrayList<EventDTO>();
		EventDTO dto = null;
		
		int eventUID;
		String eventName;
		String startDate;
		int startHour;
		int quantity;
		
		try
		{
			String sql = "SELECT EVENT_UID,EVENT_NAME,TO_CHAR(START_DATE, 'YYYYMMDD') START_DATE,START_HOUR,QUANTITY FROM EOB_EVENT";
			pstmt = conn.prepareStatement(sql); 
			
			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new EventDTO();
				
				// resultset 에서 해당 컬럼값을 가져온다.
				eventUID = rs.getInt("EVENT_UID");
				eventName = rs.getString("EVENT_NAME");
				startDate = rs.getString("START_DATE");
				startHour = rs.getInt("START_HOUR");
				quantity = rs.getInt("QUANTITY");
				
				// 가져온 컬럼값을 dto에 삽입
				dto.setUid(eventUID);
				dto.setEventName(eventName);
				dto.setStartDate(startDate);
				dto.setStartHour(startHour);
				dto.setQuantity(quantity);
				
				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}
		
		return dtoList;

	}
	
	public void insert (EventDTO dto) throws Exception
	{
		try {

			String sql = "INSERT INTO EOB_EVENT (EVENT_UID,EVENT_NAME,START_DATE,START_HOUR,QUANTITY) VALUES(EOB_EVENT_SEQ.NEXTVAL,?,TO_DATE(?, 'YYYYMMDD'),?,?)";

			
			pstmt =conn.prepareStatement(sql); 

		
			pstmt.setString(1, dto.getEventName());
			pstmt.setString(2, dto.getStartDate());
			pstmt.setInt(3, dto.getStartHour());
			pstmt.setInt(4, dto.getQuantity());

			
			
			
			pstmt.executeQuery();

		} catch (Exception e) {

			throw e; 
		}

	}


	public void update(EventDTO dto) throws Exception 

	{

		try {
			
			String sql = "UPDATE EOB_EVENT SET EVENT_NAME=?, START_DATE= TO_DATE(?, 'YYYYMMDD'),START_HOUR=?,QUANTITY=? WHERE EVENT_UID=?";
			pstmt =conn.prepareStatement(sql);
		
			pstmt.setString(1,dto.getEventName());
			pstmt.setString(2, dto.getStartDate());
			pstmt.setInt(3, dto.getStartHour());
			pstmt.setInt(4, dto.getQuantity());
			pstmt.setInt(5, dto.getUid());
		
			pstmt.executeUpdate();
		
			
		} catch (Exception e) {

			throw e; 
		}
		

	}


	public void delete (int uid) throws Exception
	{

		try {
			
			String sql = "DELETE FROM EOB_EVENT WHERE EVENT_UID=?";
			
			
			pstmt =conn.prepareStatement(sql);
			
			pstmt.setInt(1,uid);
			
			rs=pstmt.executeQuery();

		} catch (Exception e) {

			throw e; 
		}

	}


}
