package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class LIBBoardDAO {
	private Connection conn=DBConn.getConnection();


	

	public int selectFirstBoardUID() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT BOARD_UID FROM LIB_BOARD WHERE ROWNUM=1 ORDER BY BOARD_UID";
			pstmt=conn.prepareStatement(sql);

			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	



	public  int insertBoard(LIBBoardDTO libBoardDTO) {
		int result =0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		String sql;

		try {

			sql = "INSERT INTO LIB_BOARD (BOARD_UID, SUBJECT, DESCRIPTION, REG_DATE) VALUES (LIB_BOARD_SEQ.NEXTVAL, ?, ?, SYSDATE)";


			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,libBoardDTO.getSubject() );
			pstmt.setString(2, libBoardDTO.getDescription());

			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return result;
	} 




	public  int deleteBoard(int uid) {
		int result =0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		String sql;

		try {

			sql = "DELETE LIB_BOARD WHERE BOARD_UID=?";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, uid);

			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return result;
	} 

	


	public List<LIBBoardDTO> listCategory() {
		List<LIBBoardDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;

		try {
			String query = "SELECT BOARD_UID, SUBJECT, DESCRIPTION, REG_DATE FROM LIB_BOARD";

			pstmt = conn.prepareStatement(query);

			rs=pstmt.executeQuery();

			while(rs.next()) {
				LIBBoardDTO dto=new LIBBoardDTO();

				dto.setUid(rs.getInt("BOARD_UID"));
				dto.setSubject(rs.getString("SUBJECT"));
				dto.setDescription(rs.getString("DESCRIPTION"));
				dto.setRegDate(rs.getString("REG_DATE"));

				list.add(dto);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return list;
	}

	public String getDescription(int id) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;

		String description = "";

		try {
			String query = "SELECT DESCRIPTION FROM LIB_BOARD WHERE BOARD_UID=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			rs=pstmt.executeQuery();

			while(rs.next()) {
				description = rs.getString("DESCRIPTION");

			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return description;
	}

}
