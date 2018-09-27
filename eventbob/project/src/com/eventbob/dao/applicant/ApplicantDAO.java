package com.eventbob.dao.applicant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.eventbob.dto.applicant.ApplicantDTO;
import com.eventbob.dto.event.EventDTO;
import com.eventbob.util.database.DBConn;

public class ApplicantDAO {


	private Connection conn=DBConn.getConnection(); // db 연결

	PreparedStatement pstmt = null; // 실행
	ResultSet rs = null; // 출력

	int count = 0;
	
	
	public ArrayList<ApplicantDTO> select() throws Exception
	{
		ArrayList<ApplicantDTO> dtoList = new ArrayList<ApplicantDTO>();
		ApplicantDTO dto = null;
		
		String name;
		String tel;
		
		try
		{
			String sql = "SELECT NAME,TEL FROM EOB_APPLICANT";
			pstmt = conn.prepareStatement(sql); 
			
			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new ApplicantDTO();
				
				// resultset 에서 해당 컬럼값을 가져온다.
				name = rs.getString("NAME");
				tel = rs.getString("TEL");
				
				
				// 가져온 컬럼값을 dto에 삽입
				
				dto.setName(name);
				dto.setTel(tel);
				
				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}
		
		return dtoList;

	}
	
	public ArrayList<ApplicantDTO> selectByUID(int uid) throws Exception
	{
		ArrayList<ApplicantDTO> dtoList = new ArrayList<ApplicantDTO>();
		ApplicantDTO dto = null;
		
		String name;
		String tel;
		
		try
		{
			String sql = "SELECT NAME,TEL FROM EOB_APPLICANT WHERE APPLICANT_UID=?";
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, uid);
			
			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new ApplicantDTO();
				
				// resultset 에서 해당 컬럼값을 가져온다.
				name = rs.getString("NAME");
				tel = rs.getString("TEL");
				
				
				// 가져온 컬럼값을 dto에 삽입
				
				dto.setName(name);
				dto.setTel(tel);
				
				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}
		
		return dtoList;

	}
	
	
	public ArrayList<ApplicantDTO> selectByNameAndTel(String searchName, String searchTel) throws Exception
	{
		ArrayList<ApplicantDTO> dtoList = new ArrayList<ApplicantDTO>();
		ApplicantDTO dto = null;
		
		int uid;
		String name;
		String tel;
		
		try
		{
			String sql = "SELECT APPLICANT_UID, NAME,TEL FROM EOB_APPLICANT WHERE NAME=? AND TEL=?";
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1, searchName);
			pstmt.setString(2, searchTel);
			
			rs = pstmt.executeQuery();

			while(rs.next()){

				// dto 생성
				dto = new ApplicantDTO();
				
				// resultset 에서 해당 컬럼값을 가져온다.
				uid = rs.getInt("APPLICANT_UID");
				name = rs.getString("NAME");
				tel = rs.getString("TEL");
				
				
				// 가져온 컬럼값을 dto에 삽입
				
				dto.setUid(uid);
				dto.setName(name);
				dto.setTel(tel);
				
				dtoList.add(dto);
			}

		}
		catch(Exception e)
		{
			throw e;
		}
		
		return dtoList;

	}
	
	public boolean isExist(ApplicantDTO dto) throws Exception
	
	{
		try
		{
			String sql = "SELECT COUNT(*) TOTAL_COUNT FROM EOB_APPLICANT WHERE NAME=? AND TEL=?";
			pstmt = conn.prepareStatement(sql); 

			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getTel());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){

				count = rs.getInt("TOTAL_COUNT");
			}
			
			if(count == 0)
				return false;
			
		}
		catch(Exception e)
		{
			throw e;
		}
		
		return true;
	}
	
	
	public void insert(ApplicantDTO dto) throws Exception
	{
		try
		{
			String sql = "INSERT INTO EOB_APPLICANT(APPLICANT_UID,NAME,TEL)VALUES(EOB_APPLICANT_SEQ.NEXTVAL,?,?)";
			pstmt = conn.prepareStatement(sql); 

			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getTel());
			
			rs = pstmt.executeQuery();
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	

	public ArrayList<ApplicantDTO> selectPerson()
	{

		return null;
	}

	public void insertPerson(ApplicantDTO dto)
	{

	}

	public ArrayList<ApplicantDTO> selectEvent()
	{
		return null;
	}
}
