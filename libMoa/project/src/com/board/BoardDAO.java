package com.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BoardDAO {
	private Connection conn=DBConn.getConnection();
	
	public void insertBoard(BoardDTO dto, String mode) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		int seq=0;
		
		try {
			sql="SELECT board_seq.NEXTVAL FROM dual";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				seq=rs.getInt(1);
			rs.close();
			pstmt.close();
			rs=null;
			pstmt=null;

			dto.setBoardNum(seq);
			if(mode.equals("created")) {
				// ���۵��
				dto.setGroupNum(seq);
				dto.setOrderNo(0);
				dto.setDepth(0);
				dto.setParent(0);
			} else {
				// ��� ���
				updateOrderNo(dto.getGroupNum(), dto.getOrderNo());
				
				dto.setDepth(dto.getDepth()+1);
				dto.setOrderNo(dto.getOrderNo()+1);
			}
			
			sql="INSERT INTO board(boardNum, userId, subject, content, groupNum, orderNo, ";
			sql+="  depth, parent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBoardNum());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getOrderNo());
			pstmt.setInt(7, dto.getDepth());
			pstmt.setInt(8, dto.getParent());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM board";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// �˻� ���� ��ü�� ����
    public int dataCount(String searchKey, String searchValue) {
        int result=0;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String sql;

        try {
        	if(searchKey.equals("created")) {
        		searchValue=searchValue.replaceAll("-", "");
        		sql="SELECT NVL(COUNT(*), 0) FROM board b JOIN member1 m ON b.userId=m.userId WHERE TO_CHAR(created, 'YYYYMMDD') = ?  ";
        	} else if(searchKey.equals("userName")) {
        		sql="SELECT NVL(COUNT(*), 0) FROM board b JOIN member1 m ON b.userId=m.userId WHERE INSTR(userName, ?) = 1 ";
        	} else {
        		sql="SELECT NVL(COUNT(*), 0) FROM board b JOIN member1 m ON b.userId=m.userId WHERE INSTR(" + searchKey + ", ?) >= 1 ";
        	}
        	
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, searchValue);

            rs=pstmt.executeQuery();

            if(rs.next())
                result=rs.getInt(1);
        } catch (Exception e) {
            System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
        
        return result;
    }
	
	public List<BoardDTO> listBoard(int start, int end) {
		List<BoardDTO> list=new ArrayList<BoardDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("         SELECT boardNum, b.userId, userName,");
			sb.append("               subject, groupNum, orderNo, depth, hitCount,");
			sb.append("               TO_CHAR(created, 'YYYY-MM-DD') created");
			sb.append("               FROM board b");
			sb.append("               JOIN member1 m ON b.userId=m.userId");
			sb.append("               ORDER BY groupNum DESC, orderNo ASC ");
			sb.append("    ) tb WHERE ROWNUM <= ? ");
			sb.append(") WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs=pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO dto=new BoardDTO();
				dto.setBoardNum(rs.getInt("boardNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	// �˻����� ����Ʈ
    public List<BoardDTO> listBoard(int start, int end, String searchKey, String searchValue) {
        List<BoardDTO> list=new ArrayList<BoardDTO>();

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
			sb.append("SELECT * FROM (");
			sb.append("    SELECT ROWNUM rnum, tb.* FROM (");
			sb.append("         SELECT boardNum, b.userId, userName,");
			sb.append("               subject, groupNum, orderNo, depth, hitCount,");
			sb.append("               TO_CHAR(created, 'YYYY-MM-DD') created");
			sb.append("               FROM board b");
			sb.append("               JOIN member1 m ON b.userId=m.userId");
			if(searchKey.equals("created")) {
				searchValue=searchValue.replaceAll("-", "");
				sb.append("           WHERE TO_CHAR(created, 'YYYYMMDD') = ? ");
			} else if(searchKey.equals("userName")) {
				sb.append("           WHERE INSTR(userName, ?) = 1 ");
			} else {
				sb.append("           WHERE INSTR(" + searchKey + ", ?) >= 1 ");
			}
			
			sb.append("               ORDER BY groupNum DESC, orderNo ASC ");
			sb.append("    ) tb WHERE ROWNUM <= ?");
			sb.append(") WHERE rnum >= ?");
            
			pstmt=conn.prepareStatement(sb.toString());
            
			pstmt.setString(1, searchValue);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
            
            rs=pstmt.executeQuery();
            
            while(rs.next()) {
                BoardDTO dto=new BoardDTO();
				dto.setBoardNum(rs.getInt("boardNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
            
                list.add(dto);
            }
            
        }catch (Exception e) {
            System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
        return list;
    }
	
	public BoardDTO readBoard(int boardNum) {
		BoardDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			sb.append("SELECT boardNum, b.userId, userName, subject, ");
			sb.append("    content, created, hitCount, groupNum, depth, orderNo, parent ");
			sb.append("    FROM board b");
			sb.append("    JOIN member1 m ON b.userId=m.userId");
			sb.append("    WHERE boardNum=?");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, boardNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new BoardDTO();
				dto.setBoardNum(rs.getInt("boardNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setDepth(rs.getInt("depth"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setParent(rs.getInt("parent"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setCreated(rs.getString("created"));
			}
					
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
    // ������
    public BoardDTO preReadBoard(int groupNum, int orderNo, String searchKey, String searchValue) {
        BoardDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(searchValue!=null && searchValue.length() != 0) {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("  SELECT boardNum, subject  ");
    			sb.append("               FROM board b");
    			sb.append("               JOIN member1 m ON b.userId=m.userId");
    			if(searchKey.equals("created")) {
    				searchValue=searchValue.replaceAll("-", "");
    				sb.append("           WHERE (TO_CHAR(created, 'YYYYMMDD') = ? ) AND ");
    			} else if(searchKey.equals("userName")) {
    				sb.append("           WHERE (INSTR(userName, ?) = 1 ) AND ");
    			} else {
    				sb.append("           WHERE (INSTR(" + searchKey + ", ?) >= 1 ) AND ");
            	}
            
                sb.append("     (( groupNum = ? AND orderNo < ?) ");
                sb.append("         OR (groupNum > ? )) ");
                sb.append("         ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                
                pstmt.setString(1, searchValue);
                pstmt.setInt(2, groupNum);
                pstmt.setInt(3, orderNo);
                pstmt.setInt(4, groupNum);
			} else {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("     SELECT boardNum, subject FROM board b JOIN member1 m ON b.userId=m.userId ");                
                sb.append("  WHERE (groupNum = ? AND orderNo < ?) ");
                sb.append("         OR (groupNum > ? ) ");
                sb.append("         ORDER BY groupNum ASC, orderNo DESC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setInt(3, groupNum);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new BoardDTO();
                dto.setBoardNum(rs.getInt("boardNum"));
                dto.setSubject(rs.getString("subject"));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
    
        return dto;
    }

    // ������
    public BoardDTO nextReadBoard(int groupNum, int orderNo, String searchKey, String searchValue) {
        BoardDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuffer sb = new StringBuffer();

        try {
            if(searchValue!=null && searchValue.length() != 0) {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("  SELECT boardNum, subject ");
    			sb.append("               FROM board b");
    			sb.append("               JOIN member1 m ON b.userId=m.userId");
    			if(searchKey.equals("created")) {
    				searchValue=searchValue.replaceAll("-", "");
    				sb.append("           WHERE (TO_CHAR(created, 'YYYYMMDD') = ? ) AND ");
    			} else if(searchKey.equals("userName")) {
    				sb.append("           WHERE (INSTR(userName, ?) = 1) AND ");
    			} else {
    				sb.append("           WHERE (INSTR(" + searchKey + ", ?) >= 1) AND ");
    			}
    			
                sb.append("     (( groupNum = ? AND orderNo > ?) ");
                sb.append("         OR (groupNum < ? )) ");
                sb.append("         ORDER BY groupNum DESC, orderNo ASC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, searchValue);
                pstmt.setInt(2, groupNum);
                pstmt.setInt(3, orderNo);
                pstmt.setInt(4, groupNum);

			} else {
                sb.append("SELECT ROWNUM, tb.* FROM ( ");
                sb.append("     SELECT boardNum, subject FROM board b JOIN member1 m ON b.userId=m.userId ");
                sb.append("  WHERE (groupNum = ? AND orderNo > ?) ");
                sb.append("         OR (groupNum < ? ) ");
                sb.append("         ORDER BY groupNum DESC, orderNo ASC) tb WHERE ROWNUM = 1 ");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, groupNum);
                pstmt.setInt(2, orderNo);
                pstmt.setInt(3, groupNum);
            }

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new BoardDTO();
                dto.setBoardNum(rs.getInt("boardNum"));
                dto.setSubject(rs.getString("subject"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
        return dto;
    }
    
	public void updateHitCount(int boardNum) {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE board SET hitCount=hitCount+1 WHERE boardNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	public void updateOrderNo(int groupNum, int orderNo) {
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			// �亯�� �� orderNo ����
			sql="UPDATE board SET orderNo=orderNo+1 WHERE groupNum=? AND orderNo > ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, orderNo);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
	}
    
	public void updateBoard(BoardDTO dto) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="UPDATE board SET subject=?,content=? WHERE boardNum =? AND userId=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getBoardNum());
			pstmt.setString(4, dto.getUserId());
			
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
		}
	}
	public void deleteBoard(int boardNum , String userId) {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			BoardDTO dto = readBoard(boardNum);
			if(dto!=null&&(userId.equals("admin") || userId.equals(dto.getUserId()))) {
				sql="DELETE FROM board WHERE";
				sql+=" boardNum IN(SELECT boardNum FROM board ";
				sql+=" START WITH boardNum=? CONNECT BY PRIOR boardNum=parent)";
				 
				 pstmt=conn.prepareStatement(sql);
				 pstmt.setInt(1, boardNum);
				 pstmt.executeUpdate();	 
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	
	
	
}
