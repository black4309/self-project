package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class AdminDAO {
	private Connection conn=DBConn.getConnection();


	public AdminDTO loginAdmin(String userId) {
		AdminDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;

		try {
			String query = "SELECT ID, PASSWORD FROM LIB_ADMIN WHERE ID=?";

			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto=new AdminDTO();
				dto.setUserId(rs.getString("ID"));
				dto.setUserPwd(rs.getString("PASSWORD"));
			}
			rs.close();
			pstmt.close();
			pstmt=null;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return dto;
	}


}
