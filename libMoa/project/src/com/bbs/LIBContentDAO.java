package com.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class LIBContentDAO {
	private Connection conn=DBConn.getConnection();

	public List<LIBContentDTO> listContent(int id, int start, int end) {
		List<LIBContentDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			
			sql = " SELECT * "
			 + " FROM (SELECT * "
			 + "         FROM ( "
			 + " SELECT ROWNUM RNUM, LC.CONTENTS_UID, LM.NAME USERNAME, LC.TITLE, LC.CONTENTS, LC.COUNTS, LC.REG_DATE "
			 + " FROM LIB_CONTENTS LC, LIB_BOARD LB, LIB_MEMBER LM "
			 + " WHERE LC.BOARD_UID = LB.BOARD_UID "
			 + " AND LC.MEMBER_UID=LM.MEMBER_UID "
			 + " AND LB.BOARD_UID=? "
			 + " ORDER BY REG_DATE DESC "
			 + " ) PAGETABLE "
			 + "         WHERE RNUM <= ? "
			 + "     ) "
			 + " WHERE RNUM >= ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				LIBContentDTO dto = new LIBContentDTO();

				dto.setUid(rs.getInt("CONTENTS_UID"));
				dto.setUserName(rs.getString("USERNAME"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setContents(rs.getString("CONTENTS"));
				dto.setCounts(rs.getInt("COUNTS"));
				dto.setRegDate(rs.getString("REG_DATE"));

				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (Exception e2) {
				}

			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return list;
	} 

	
	
	public List<LIBContentDTO> listContent(int id, int start, int end, String search) {
		List<LIBContentDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			
			sql = " SELECT * "
			 + " FROM (SELECT * "
			 + "         FROM ( "
			 + " SELECT ROWNUM RNUM, LC.CONTENTS_UID, LM.NAME USERNAME, LC.TITLE, LC.CONTENTS, LC.COUNTS, LC.REG_DATE "
			 + " FROM LIB_CONTENTS LC, LIB_BOARD LB, LIB_MEMBER LM "
			 + " WHERE LC.BOARD_UID = LB.BOARD_UID "
			 + " AND LC.MEMBER_UID=LM.MEMBER_UID "
			 + " AND LB.BOARD_UID=? "
			 + " AND LC.TITLE LIKE '%' || ? || '%'"
			 + " ORDER BY REG_DATE DESC "
			 + " ) PAGETABLE "
			 + "         WHERE RNUM <= ? "
			 + "     ) "
			 + " WHERE RNUM >= ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.setString(2, search);
			pstmt.setInt(3, end);
			pstmt.setInt(4, start);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				LIBContentDTO dto = new LIBContentDTO();

				dto.setUid(rs.getInt("CONTENTS_UID"));
				dto.setUserName(rs.getString("USERNAME"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setContents(rs.getString("CONTENTS"));
				dto.setCounts(rs.getInt("COUNTS"));
				dto.setRegDate(rs.getString("REG_DATE"));

				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (Exception e2) {
				}

			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return list;
	} 
	

	public  LIBContentDTO selectContent(int id) {
		LIBContentDTO dto = new LIBContentDTO();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			
			sql  =" SELECT LC.CONTENTS_UID, LM.NAME USERNAME, LC.TITLE, LC.CONTENTS, LC.COUNTS, LC.REG_DATE" 
			+ " FROM LIB_CONTENTS LC, LIB_MEMBER LM"   
			+ " WHERE LC.MEMBER_UID=LM.MEMBER_UID " 
			+ " AND LC.CONTENTS_UID=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				dto.setUid(rs.getInt("CONTENTS_UID"));
				dto.setUserName(rs.getString("USERNAME"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setContents(rs.getString("CONTENTS"));
				dto.setCounts(rs.getInt("COUNTS"));
				dto.setRegDate(rs.getString("REG_DATE"));

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (Exception e2) {
				}

			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
		return dto;
	} 

	

	public int contentDataCount(int id, String search) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM LIB_CONTENTS WHERE BOARD_UID=? AND TITLE LIKE '%' || ? || '%'";
			pstmt=conn.prepareStatement(sql);

			pstmt.setInt(1, id);
			pstmt.setString(2, search);
			
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
	

	public int contentDataCount(int id) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM LIB_CONTENTS WHERE BOARD_UID=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
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

	public  void updateContentCount(int id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			
			sql  =" UPDATE LIB_CONTENTS SET COUNTS=COUNTS+1 WHERE CONTENTS_UID=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (Exception e2) {
				}

			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (Exception e) {
				}
		}
	} 


	public  int insertContent(LIBContentDTO libContentDTO) {
		int result =0;
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();
		String sql;

		try {

			sql = "INSERT INTO LIB_CONTENTS (CONTENTS_UID,BOARD_UID,MEMBER_UID,TITLE,CONTENTS,COUNTS,REG_DATE) " 
					+ " VALUES"
					+ " (LIB_CONTENTS_SEQ.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE)";
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,libContentDTO.getBoardUID() );
			pstmt.setInt(2, libContentDTO.getMemberUID());
			pstmt.setString(3, libContentDTO.getTitle());
			pstmt.setString(4, libContentDTO.getContents());
			
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
}
