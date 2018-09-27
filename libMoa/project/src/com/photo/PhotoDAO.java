package com.photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;


public class PhotoDAO {

	public void insertPhoto(PhotoDTO dto) {

		Connection conn = DBCPConn.getConection();

		PreparedStatement pstmt = null;
		String sql;

		try {

			sql="INSERT INTO photo(num ,userId,subject,content,imageFilename)VALUES(photo_seq.NEXTVAL,?, ?, ?, ?)";

			pstmt=conn.prepareStatement(sql);


			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getImageFilename());

			pstmt.executeUpdate();


		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			DBCPConn.close(conn);

		}
	}

	public int dataCount() {
		int result = 0;
		Connection conn = DBCPConn.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql="SELECT NVL(COUNT(*), 0) FROM photo ";

			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}

			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			DBCPConn.close(conn);
		}

		return result;
	}

	public List<PhotoDTO> listPhoto(int start,int end){
		List<PhotoDTO> list = new ArrayList<>();
		Connection conn = DBCPConn.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		StringBuffer sb = new StringBuffer();


		try {
			// num , userId , userName , subject , imageFilename

			sb.append("SELECT * FROM( ");
			sb.append("    SELECT  ROWNUM rnum , tb.* FROM (");
			sb.append("       SELECT num, p.userId ,userName , subject , imageFilename");
			sb.append("       FROM photo p JOIN member1 m ON p.userId = m.userId");
			sb.append("       ORDER BY num DESC");
			sb.append("  ) tb WHERE ROWNUM <= ? ");
			sb.append(")WHERE rnum >=?");

			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs=pstmt.executeQuery();
			while(rs.next()) {
				PhotoDTO dto = new PhotoDTO();

				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setImageFilename(rs.getString("imageFilename"));
				list.add(dto);
			}



		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {

			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}

			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			DBCPConn.close(conn);
		}

		return list;

	}


	public PhotoDTO readPhoto(int num) {

		PhotoDTO dto = null;
		Connection conn = DBCPConn.getConection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("SELECT num , p.userId,userName ,subject,content,");
			sb.append(" TO_CHAR(created, 'YYYY-MM-DD')created,imageFilename");
			sb.append("    FROM photo p");
			sb.append("     JOIN member1 m ON p.userId=m.userId");
			sb.append("     WHERE num=?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				dto = new PhotoDTO();
				dto.setNum(rs.getInt("num"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setCreated(rs.getString("created"));
				dto.setImageFilename(rs.getString("imageFilename"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

			DBCPConn.close(conn);

		}

		return dto;

	}



	public void updatePhoto(PhotoDTO dto) {
		Connection conn =DBCPConn.getConection();
		PreparedStatement pstmt = null;
		StringBuffer sb = new StringBuffer();

		try {
			sb.append("UPDATE photo SET subject=?");
			sb.append("  ,content=? ,imageFilename=?");
			sb.append(" WHERE num=?");
			pstmt=conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getImageFilename());
			pstmt.setInt(4, dto.getNum());

			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {

				}
			}
			DBCPConn.close(conn);
		}
	}

	public void deletePhoto(int num) {
		
		Connection conn = DBCPConn.getConection();
		PreparedStatement pstmt = null;
		String sql; 
		
		try {
			sql="DELETE FROM photo WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
				
			DBCPConn.close(conn);
		}
	}


}