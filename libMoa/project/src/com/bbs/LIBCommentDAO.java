package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class LIBCommentDAO {
	private Connection conn=DBConn.getConnection();



	public  int insertComment(LIBCommentDTO libCommentDTO) {
		int result =0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		String sql;

		try {

			sql = "INSERT INTO LIB_COMMENT (COMMENT_UID, MEMBER_UID, CONTENTS_UID, CONTENTS, REG_DATE)" 
					+ " VALUES" 
					+ " (LIB_COMMENT_SEQ.NEXTVAL, ?, ?, ?, SYSDATE)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1,libCommentDTO.getMemberUID() );
			pstmt.setInt(2,libCommentDTO.getContentsUID() );
			pstmt.setString(3,libCommentDTO.getContents() );

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

	
	public  int deleteComment(int id) {
		int result =0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		String sql;

		try {

			sql = "DELETE LIB_COMMENT WHERE COMMENT_UID=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1,id );

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
	

	public List<LIBCommentDTO> listComment(int id) {
		List<LIBCommentDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;

		try {
			String query = "SELECT LC.COMMENT_UID, LM.NAME, LC.CONTENTS, LC.REG_DATE"
					+ " FROM LIB_COMMENT LC, LIB_MEMBER LM" 
					+ " WHERE LC.MEMBER_UID=LM.MEMBER_UID"
					+ " AND LC.CONTENTS_UID=?";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1,id );
			
			rs=pstmt.executeQuery();

			while(rs.next()) {
				LIBCommentDTO dto=new LIBCommentDTO();

				dto.setUid(rs.getInt("COMMENT_UID"));
				dto.setUserName(rs.getString("NAME"));
				dto.setContents(rs.getString("CONTENTS"));
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

}
