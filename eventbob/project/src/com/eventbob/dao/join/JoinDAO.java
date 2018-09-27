package com.eventbob.dao.join;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.print.attribute.SetOfIntegerSyntax;

import com.eventbob.dto.admin.AdminDTO;
import com.eventbob.dto.event.EventDTO;
import com.eventbob.dto.join.JoinDTO;
import com.eventbob.util.database.DBConn;

public class JoinDAO {


	private Connection conn = DBConn.getConnection(); // db연결

	PreparedStatement pstmt = null; // 실행
	Statement stmt = null;

	ResultSet rs = null; // 출력

	int count = 0;


	public ArrayList<JoinDTO> select() throws Exception
	{
		ArrayList<JoinDTO> dtoList = new ArrayList<JoinDTO>();
		JoinDTO dto = null;

		int joinUID;
		String joinTime;
		int isWin;
		int eventUID;
		int applicantUID;


		try
		{
			String sql = "SELECT JOIN_UID,JOIN_TIME,IS_WIN,EVENT_UID,APPLICANT_UID FROM EOB_JOIN";
			pstmt = conn.prepareStatement(sql); 

			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new JoinDTO();

				// resultset 에서 해당 컬럼값을 가져온다.

				joinUID = rs.getInt("JOIN_UID");
				joinTime = rs.getString("JOIN_TIME");
				isWin = rs.getInt("IS_WIN");
				eventUID = rs.getInt("EVENT_UID");
				applicantUID = rs.getInt("APPLICANT_UID");

				// 가져온 컬럼값을 dto에 삽입
				dto.setUid(joinUID);
				dto.setJointime(joinTime);
				dto.setIsWin(isWin);
				dto.setEventUID(eventUID);
				dto.setApplicantUID(applicantUID);

				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}

		return dtoList;

	}


	public ArrayList<JoinDTO> selectByEvent(int searchEventUID) throws Exception
	{
		ArrayList<JoinDTO> dtoList = new ArrayList<JoinDTO>();
		JoinDTO dto = null;

		int joinUID;
		String joinTime;
		int isWin;
		int eventUID;
		int applicantUID;


		try
		{
			String sql = "SELECT JOIN_UID,JOIN_TIME,IS_WIN,EVENT_UID,APPLICANT_UID FROM EOB_JOIN WHERE EVENT_UID=?";
			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1, searchEventUID);

			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new JoinDTO();

				// resultset 에서 해당 컬럼값을 가져온다.

				joinUID = rs.getInt("JOIN_UID");
				joinTime = rs.getString("JOIN_TIME");
				isWin = rs.getInt("IS_WIN");
				eventUID = rs.getInt("EVENT_UID");
				applicantUID = rs.getInt("APPLICANT_UID");

				// 가져온 컬럼값을 dto에 삽입
				dto.setUid(joinUID);
				dto.setJointime(joinTime);
				dto.setIsWin(isWin);
				dto.setEventUID(eventUID);
				dto.setApplicantUID(applicantUID);

				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}

		return dtoList;

	}

	public ArrayList<JoinDTO> selectByEventAndApplicant(int searchEventUID, int searchApplicantUID) throws Exception
	{
		ArrayList<JoinDTO> dtoList = new ArrayList<JoinDTO>();
		JoinDTO dto = null;

		int joinUID;
		String joinTime;
		int isWin;
		int eventUID;
		int applicantUID;


		try
		{
			String sql = "SELECT JOIN_UID,JOIN_TIME,IS_WIN,EVENT_UID,APPLICANT_UID FROM EOB_JOIN WHERE EVENT_UID=? AND APPLICANT_UID=?";
			pstmt = conn.prepareStatement(sql); 

			pstmt.setInt(1, searchEventUID);
			pstmt.setInt(2, searchApplicantUID);

			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new JoinDTO();

				// resultset 에서 해당 컬럼값을 가져온다.

				joinUID = rs.getInt("JOIN_UID");
				joinTime = rs.getString("JOIN_TIME");
				isWin = rs.getInt("IS_WIN");
				eventUID = rs.getInt("EVENT_UID");
				applicantUID = rs.getInt("APPLICANT_UID");

				// 가져온 컬럼값을 dto에 삽입
				dto.setUid(joinUID);
				dto.setJointime(joinTime);
				dto.setIsWin(isWin);
				dto.setEventUID(eventUID);
				dto.setApplicantUID(applicantUID);

				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}

		return dtoList;

	}


	public void insert(int EventUID , int ApplicantUID , int isWin ) throws Exception
	{
		try {


			String sql = "INSERT INTO EOB_JOIN (JOIN_UID,JOIN_TIME,IS_WIN,EVENT_UID,APPLICANT_UID) VALUES(EOB_JOIN_SEQ.NEXTVAL,CURRENT_TIMESTAMP, ? ,?,?)";


			pstmt =conn.prepareStatement(sql); 

			pstmt.setInt(1, isWin);
			pstmt.setInt(2, EventUID);
			pstmt.setInt(3, ApplicantUID);

			pstmt.executeQuery();

		} catch (Exception e) {
			throw e; 
		}
	}
	public void update (int EventUID , int ApplicantUID , int isWin ) throws Exception

	{
		try {

			String sql = "UPDATE EOB_JOIN SET IS_WIN=?,JOIN_TIME=CURRENT_TIMESTAMP WHERE EVENT_UID=? AND APPLICANT_UID=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1,isWin);
			pstmt.setInt(2, EventUID);
			pstmt.setInt(3, ApplicantUID);

			pstmt.executeQuery();

		} catch (Exception e) {
			throw e;
		}
	}



	public int countWinByEvent(int eventUID) throws Exception
	{
		int count = 0;
		
		try
		{
			String sql = "SELECT COUNT(*) TOTAL_COUNT FROM EOB_JOIN WHERE IS_WIN=1 AND EVENT_UID=?";
			pstmt = conn.prepareStatement(sql); 


			pstmt.setInt(1, eventUID);
			
			rs = pstmt.executeQuery();

			while(rs.next()){
				count = rs.getInt("TOTAL_COUNT");
			}
		}
		catch(Exception e)
		{
			throw e;
		}

		return count;
	}

}




