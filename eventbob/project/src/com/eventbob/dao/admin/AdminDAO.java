package com.eventbob.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eventbob.dto.admin.AdminDTO;
import com.eventbob.util.database.DBConn;

public class AdminDAO {
	
  private Connection conn = DBConn.getConnection(); // db����
  
  PreparedStatement pstmt = null; // ����
  
  ResultSet rs = null; // ���
  
  int count = 0;
  
  /*
   * admin ���̵� �� �н����带 �����ϴ� �����Դϴ�.
   * 
   */
  public boolean isExist(AdminDTO dto) throws Exception
	
	{
		try
		{
			String sql = "SELECT COUNT(*) TOTAL_COUNT FROM EOB_ADMIN WHERE ID=? AND PASSWORD=?";
			pstmt = conn.prepareStatement(sql); 
			

			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPassword());
			

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

}
