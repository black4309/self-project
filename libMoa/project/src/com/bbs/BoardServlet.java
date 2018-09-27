package com.bbs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUtil;

@WebServlet("/bbs/*")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd=req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri=req.getRequestURI();
		
		// 로그인 정보
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("board_manage.do")!=-1) {
			boardManageForm(req, resp);
		} else if(uri.indexOf("board_create_ok.do")!=-1) {
			boardCreateForm(req, resp);
		} else if(uri.indexOf("board_delete_ok.do")!=-1) {
			boardDeleteForm(req, resp);
		} else if(uri.indexOf("replyCreate_ok.do")!=-1) {
			replyCreate(req, resp);
		} else if(uri.indexOf("replyDelete_ok.do")!=-1) {
			replyDelete(req, resp);
		} else if(uri.indexOf("replyList_ok.do")!=-1) {
			replyList(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("insert.do")!=-1) {
			delete(req, resp);
		}
		
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 리스트

		LIBBoardDAO lIbBoardDAO = new LIBBoardDAO();
		LIBContentDAO libContentDAO = new LIBContentDAO();

		BoardDAO dao = new BoardDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();

		String page=req.getParameter("page");

		String boardUID=req.getParameter("id");

		if(boardUID == null)
		{
			boardUID = lIbBoardDAO.selectFirstBoardUID() + "";
		}
		
		int current_page=1;
		if(page!=null)
			current_page=Integer.parseInt(page);


		// 검색
		String searchKey=req.getParameter("searchKey");
		String searchValue=req.getParameter("searchValue");
		String search=req.getParameter("search");
		if(searchKey==null) {
			searchKey="subject";
			searchValue="";
		}
		
		if(search==null) {
			search="";
		}
		
		// GET 방식인 경우 디코딩
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue=URLDecoder.decode(searchValue, "utf-8");
		}

		// GET 방식인 경우 디코딩
		if(req.getMethod().equalsIgnoreCase("GET")) {
			search=URLDecoder.decode(search, "utf-8");
		}

		

		// 전체 데이터 개수
		int dataCount;
		if(search.length()==0)
			dataCount=libContentDAO.contentDataCount(Integer.parseInt(boardUID));
		else
			dataCount=libContentDAO.contentDataCount(Integer.parseInt(boardUID), search);

		// 전체 페이지 수
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);

		if(current_page>total_page)
			current_page=total_page;

		// 게시물 가져올 시작과 끝
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;

		/*
		// 게시물 가져오기
		List<BoardDTO> list=null;
		if(searchValue.length()==0)
			list=dao.listBoard(start, end);
		else
			list=dao.listBoard(start, end, searchKey, searchValue);
		 */
		
		/*
		// 리스트 글번호 만들기
		int listNum, n=0;
		for(BoardDTO dto : list) {
			listNum=dataCount-(start+n-1);
			dto.setListNum(listNum);
			n++;
		}
		*/

		// 게시판 가져오기
		List<LIBBoardDTO> libBoardList=null;
		libBoardList=lIbBoardDAO.listCategory();

		String boardDesc = lIbBoardDAO.getDescription(Integer.parseInt(boardUID));

		// 게시물 가져오기
		List<LIBContentDTO> libContentDTOList=null;
		libContentDTOList = libContentDAO.listContent(Integer.parseInt(boardUID), start, end);

		
		if(search.length()==0)
			libContentDTOList = libContentDAO.listContent(Integer.parseInt(boardUID), start, end);
		else
			libContentDTOList = libContentDAO.listContent(Integer.parseInt(boardUID), start, end, search);
		
		
		// 리스트 글번호 만들기
		int listNum, n=0;
		for(LIBContentDTO dto : libContentDTOList) {
			listNum=dataCount-(start+n-1);
			dto.setListNum(listNum);
			n++;
		}

		String query="";
		if(searchValue.length()!=0) {
			// 검색인 경우 검색값 인코딩
			searchValue=URLEncoder.encode(searchValue, "utf-8");
			query="searchKey="+searchKey+
					"&searchValue="+searchValue;
		}

		// 페이징 처리
		String listUrl=cp+"/bbs/list.do";
		String articleUrl=cp+"/bbs/article.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?id=" +boardUID + "&"+query;
			articleUrl+="&"+query;
		}

		String paging=util.libPaging(current_page,Integer.parseInt(boardUID),  total_page, listUrl);

		// 포워딩할 JSP로 넘길 속성
		req.setAttribute("lib_board_list", libBoardList); // 추가 
		req.setAttribute("lib_conetnts_list", libContentDTOList); // 추가 
		//req.setAttribute("list", list);
		req.setAttribute("board_desc", boardDesc); // 추가 
		req.setAttribute("board_uid", boardUID); // 추가

		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);

		forward(req, resp, "/WEB-INF/views/bbs/list.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		
		int boardUID=Integer.parseInt(req.getParameter("id"));
		
		req.setAttribute("board_uid", boardUID);
		req.setAttribute("mode", "created");
		String path = "/WEB-INF/views/bbs/created.jsp"; // 추가 
		forward(req, resp, path);
	} 

	
	protected void boardManageForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시판 관리
		LIBBoardDAO libBoardDAO = new LIBBoardDAO();
		LIBBoardDTO libBoardDTO = new LIBBoardDTO();
		
		List<LIBBoardDTO> libBoardList=null;
		libBoardList=libBoardDAO.listCategory();
		
		req.setAttribute("lib_board_list", libBoardList); // 추가 
		
		String path = "/WEB-INF/views/bbs/board_manage.jsp"; // 추가 
		forward(req, resp, path);
	} 
	

	protected void boardCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시판 생성
		String cp = req.getContextPath();
		
		LIBBoardDAO libBoardDAO = new LIBBoardDAO();
		LIBBoardDTO libBoardDTO = new LIBBoardDTO();
		
		String board_name = req.getParameter("board_name");
		String board_desc = req.getParameter("board_desc");
	
		libBoardDTO.setSubject(board_name);
		libBoardDTO.setDescription(board_desc);
		
		libBoardDAO.insertBoard(libBoardDTO);
		
		resp.sendRedirect(cp+"/bbs/board_manage.do" );
	} 
	
	
	protected void replyCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 생성

		String cp = req.getContextPath();
		
		LIBCommentDAO libCommentDAO = new LIBCommentDAO();
		LIBCommentDTO libCommentDTO = new LIBCommentDTO();
		
		
		int a = 5;
		System.out.println(a);
		String contentUID = req.getParameter("reply_content_uid");
		String memberUID = req.getParameter("reply_member_uid");
		String contets = req.getParameter("reply_content");
		
		libCommentDTO.setContentsUID(Integer.parseInt(contentUID));
		libCommentDTO.setMemberUID(Integer.parseInt(memberUID));
		libCommentDTO.setContents(contets);
		
		libCommentDAO.insertComment(libCommentDTO);
		
		List<LIBCommentDTO> LIBCommentDTOList=null;
		LIBCommentDTOList = libCommentDAO.listComment(Integer.parseInt(contentUID));
		
		String outString = "";
		
		for(int i=0; i<LIBCommentDTOList.size(); i++)
		{
			String getUserName =  LIBCommentDTOList.get(i).getUserName();
			String getRegDate =  LIBCommentDTOList.get(i).getRegDate();
			String getContents =  LIBCommentDTOList.get(i).getContents();
			int getCommentUID =  LIBCommentDTOList.get(i).getUid();
			
			outString += String.format("[%s /  %s] %s &nbsp;&nbsp;<span onclick='replyDelete(%s)'><font color='red'><u>삭제</u></font></span><br/>"
					, getUserName, getRegDate, getContents, getCommentUID);
		}
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.println(outString);
		
	} 
	
	

	protected void replyDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 삭제

		String cp = req.getContextPath();
		
		LIBCommentDAO libCommentDAO = new LIBCommentDAO();
		int commentUID = Integer.parseInt(req.getParameter("id"));
		String contentUID = req.getParameter("con_id");
		
		libCommentDAO.deleteComment(commentUID);
		
		
		List<LIBCommentDTO> LIBCommentDTOList=null;
		LIBCommentDTOList = libCommentDAO.listComment(Integer.parseInt(contentUID));
		
		String outString = "";
		
		for(int i=0; i<LIBCommentDTOList.size(); i++)
		{
			String getUserName =  LIBCommentDTOList.get(i).getUserName();
			String getRegDate =  LIBCommentDTOList.get(i).getRegDate();
			String getContents =  LIBCommentDTOList.get(i).getContents();
			int getCommentUID =  LIBCommentDTOList.get(i).getUid();
			
			outString += String.format("[%s /  %s] %s &nbsp;&nbsp;<span onclick='replyDelete(%s)'><font color='red'><u>삭제</u></font></span><br/>"
					, getUserName, getRegDate, getContents, getCommentUID);
		}
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.println(outString);
		
		
	} 
	
	
	

	protected void replyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 댓글 생성

		String cp = req.getContextPath();
		
		LIBCommentDAO libCommentDAO = new LIBCommentDAO();
		LIBCommentDTO libCommentDTO = new LIBCommentDTO();
		
		
		String contentUID = req.getParameter("id");
		
		
		List<LIBCommentDTO> LIBCommentDTOList=null;
		LIBCommentDTOList = libCommentDAO.listComment(Integer.parseInt(contentUID));
		
		String outString = "";
		
		for(int i=0; i<LIBCommentDTOList.size(); i++)
		{
			String getUserName =  LIBCommentDTOList.get(i).getUserName();
			String getRegDate =  LIBCommentDTOList.get(i).getRegDate();
			String getContents =  LIBCommentDTOList.get(i).getContents();
			int getCommentUID =  LIBCommentDTOList.get(i).getUid();

			outString += String.format("[%s /  %s] %s &nbsp;&nbsp;<span onclick='replyDelete(%s)'><font color='red'><u>삭제</u></font></span><br/>"
					, getUserName, getRegDate, getContents, getCommentUID);
		}
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out=resp.getWriter();
		out.println(outString);
		
	} 
	
	
	

	protected void boardDeleteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시판 삭제
		String cp = req.getContextPath();
		
		LIBBoardDAO libBoardDAO = new LIBBoardDAO();
		
		String id = req.getParameter("id");
		
		libBoardDAO.deleteBoard(Integer.parseInt(id));
		
		resp.sendRedirect(cp+"/bbs/board_manage.do" );
	} 
	
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		String cp = req.getContextPath();
		
		/*
		BoardDAO dao = new BoardDAO();
		BoardDTO dto=new BoardDTO();
		
		dto.setUserId(req.getParameter("userId"));
		dto.setContent(req.getParameter("Content"));

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");


		// userId는 세션에 저장된 정보
		dto.setUserId(info.getUserId());

		// 파라미터
		dto.setSubject(req.getParameter("subject"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));

		dao.insertBoard(dto);
		 */
		
		
		LIBContentDAO libContentDAO = new LIBContentDAO();
		LIBContentDTO libContentDTO = new LIBContentDTO();
		
		int boardUID = Integer.parseInt(req.getParameter("board_uid"));
		int memberUID = Integer.parseInt(req.getParameter("member_uid"));
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		
		libContentDTO.setBoardUID(boardUID);
		libContentDTO.setMemberUID(memberUID);
		libContentDTO.setTitle(subject);
		libContentDTO.setContents(content);
		
		libContentDAO.insertContent(libContentDTO);
		
		resp.sendRedirect(cp+"/bbs/list.do?id=" + boardUID );
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 보기
		String cp = req.getContextPath();
		BoardDAO dao = new BoardDAO();
		MyUtil myUtil = new MyUtil();

		LIBContentDAO libContentDAO = new LIBContentDAO();
		LIBContentDTO libContentDTO = new LIBContentDTO();
		
		
		int num=Integer.parseInt(req.getParameter("content"));
		int board_uid=Integer.parseInt(req.getParameter("id"));
		String page=req.getParameter("page");
		String searchKey=req.getParameter("searchKey");
		String searchValue=req.getParameter("searchValue");
		if(searchKey==null) {
			searchKey="subject";
			searchValue="";
		}

		searchValue=URLDecoder.decode(searchValue, "utf-8");

		// 조회수 증가
		//dao.updateHitCount(num);


		// 게시물의 조회수 1 증가
		libContentDAO.updateContentCount(num);
		

		// 게시내용 가져오기 (기존소스변경안 ) 
		libContentDTO = libContentDAO.selectContent(num);
		if(libContentDTO==null) { // 게시물이 없으면 다시 리스트로
			
			String query="page="+page + "&id=" + board_uid;
			resp.sendRedirect(cp+"/bbs/list.do?"+query);
			return;
		}

		/*
		// 게시물 가져오기
		BoardDTO dto=dao.readBoard(num);
		if(dto==null) { // 게시물이 없으면 다시 리스트로
			resp.sendRedirect(cp+"/bbs/list.do?page="+page);
			return;
		}
		*/
		
		//BoardDTO dto=dao.readBoard(num);
		//dto.setContent(myUtil.htmlSymbols(dto.getContent()));

		// 이전글 다음글
//		BoardDTO dto = new BoardDTO();
		
		//BoardDTO preReadDto=dao.preReadBoard(dto.getNum());
	//BoardDTO nextReadDto=dao.nextReadBoard(dto.getNum());

		// 리스트나 이전글/다음글에서 사용할 파라미터
		String query="page="+page + "&id=" + board_uid;
		if(searchValue.length()!=0) {
			query+="&searchKey="+searchKey
					+"&searchValue="+URLEncoder.encode(searchValue, "utf-8");
		}

		
		// JSP로 전달할 속성
		//req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("query", query);
		//req.setAttribute("preReadDto", preReadDto);
		//req.setAttribute("nextReadDto", nextReadDto);

		// 게시글
		req.setAttribute("con_uid", num);
		req.setAttribute("con_title", libContentDTO.getTitle());
		req.setAttribute("con_user_name", libContentDTO.getUserName());
		req.setAttribute("con_note", libContentDTO.getContents());
		req.setAttribute("con_reg_date", libContentDTO.getRegDate());
		req.setAttribute("con_count", libContentDTO.getCounts());
		
		
		// 포워딩
		forward(req, resp, "/WEB-INF/views/bbs/article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		BoardDAO dao = new BoardDAO();

		String page=req.getParameter("page");
		int num=Integer.parseInt(	req.getParameter("num"));
		BoardDTO dto=dao.readBoard(num);

		if(dto==null) {
			resp.sendRedirect(cp+"/bbs/list.do?page="+page);
			return;
		}

		// 게시물을 올린 사용자가 아니면
		if(! dto.getUserId().equals(info.getUserId())) {
			resp.sendRedirect(cp+"/bbs/list.do?page="+page);
			return;
		}

		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("mode", "update");

		forward(req, resp, "/WEB-INF/views/bbs/created.jsp");
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		String cp = req.getContextPath();
		BoardDAO dao = new BoardDAO();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");

		String page=req.getParameter("page");

		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/bbs/list.do?page="+page);
			return;
		}

		BoardDTO dto=new BoardDTO();
		dto.setUserId( info.getUserId()); // 글을 수정하는 사람
		dto.setNum(Integer.parseInt(req.getParameter("num")));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));

		dao.updateBoard(dto);

		resp.sendRedirect(cp+"/bbs/list.do?page="+page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		LIBBoardDAO lIbBoardDAO = new LIBBoardDAO();

		String page=req.getParameter("page");
		int num=Integer.parseInt(req.getParameter("num"));

		//dao.deleteBoard(num, info.getUserId());

		resp.sendRedirect(cp+"/bbs/list.do?page="+page);
	}

}
