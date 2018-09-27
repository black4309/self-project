package com.photo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;



@WebServlet("/photo/*")
public class PhoroServlet extends MyServlet{

	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info =(SessionInfo) session.getAttribute("member");
		if(info == null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}

		// �̹����� ������ ��� 
		// �̹����� �ݵ�� ����ο� �����ؾ� img �±׷� �����ִ� 

		String root = session.getServletContext().getRealPath("/"); //����� ��Ʈ�� ���� ��ġ 
		pathname = root+"uploads"+File.separator+"photo";
		File f = new File(pathname);
		if(! f.exists()) {// �����ΰ� ������ ���� ����� 
			f.mkdirs();
		}

		if(uri.indexOf("list.do")!=-1) {
			list(req,resp);
		}else if(uri.indexOf("created.do")!=-1) {
			createdForm(req,resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req,resp);
		}else if(uri.indexOf("article.do")!=-1) {
			article(req,resp);
		}else if(uri.indexOf("update.do")!=-1) {
			updateForm(req,resp);
		}else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req,resp);
		}else if(uri.indexOf("delete.do")!=-1) {
			delete(req,resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PhotoDAO dao = new PhotoDAO();
		MyUtil util = new MyUtil();
		String page = req.getParameter("page");
		String cp = req.getContextPath();
		
		int current_page =1;
		if(page != null)
			current_page = Integer.parseInt(page);
		
		int dataCount=dao.dataCount();
		
		int rows = 6;
		int total_page = util.pageCount(rows, dataCount);
		if (current_page > total_page)
			current_page = total_page;
		
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		

		List<PhotoDTO> list=dao.listPhoto(start, end);
		
		String listIUrl =cp+"/photo/list.do";
		String articleUrl=cp+"/photo/article.do?page="+current_page;
		String paging = util.paging(current_page, total_page,listIUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("total_page", total_page);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		
	  forward(req, resp, "/WEB-INF/views/photo/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   req.setAttribute("mode", "created");
	   forward(req, resp, "/WEB-INF/views/photo/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//�Խù� ���� 
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		 SessionInfo info =(SessionInfo)session.getAttribute("member");
		
		 PhotoDAO dao = new PhotoDAO();
		 String encType = "utf-8";
		 int maxSize=5*1024*1024;
		 
		 //<form enctype = "multipart/form-data"...> ��� �����ؾ� ������ ���ε� �Ҽ��ְ� ,
		 // requset ��ü�� �Ķ���͸� �Ѱܹ��� �� ���� 
		 MultipartRequest mreq = new MultipartRequest(req , pathname, maxSize ,encType, new DefaultFileRenamePolicy());
		 
		PhotoDTO dto = new PhotoDTO();
		
		if(mreq.getFile("upload") != null) {
			
			dto.setUserId(info.getUserId());
			
			
			dto.setSubject(mreq.getParameter("subject")); // req. �� �ۼ��� null���� �̤� 
			dto.setContent(mreq.getParameter("content"));
			// ������ ����� ���ϸ� 
			String saveFilename=mreq.getFilesystemName("upload");
			
			//���� �̸� ���� 
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			dto.setImageFilename(saveFilename);
			
			dao.insertPhoto(dto);
			
		}
		
		resp.sendRedirect(cp+"/photo/list.do"); // sendRedirect �� <%=cp%> ��ߵ� ->list 

	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cp = req.getContextPath();
		PhotoDAO dao = new PhotoDAO();
		
		// num , page �Ķ���� �ޱ� 
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		// num �� �ش�Ǵ� �ڷ� ��������
		PhotoDTO dto = dao.readPhoto(num);
		
		// �����Ͱ� ������ list.do�� �����̷�Ʈ 
		if(dto == null) {
			resp.sendRedirect(cp+"/photo/list.do?page ="+page);
			return; 
		}
		
		// content�� ���͸� <br>�� �ٲٱ�
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		// jsp�� �ѱ� �� 
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		
		forward(req, resp, "/WEB-INF/views/photo/article.jsp");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		PhotoDAO dao = new PhotoDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info =(SessionInfo)session.getAttribute("member");
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		PhotoDTO dto = dao.readPhoto(num);
		if(dto == null) {
			resp.sendRedirect(cp+"/photo/list.do?page="+page);
			return; 
		}
		
		if(! dto.getUserId().equals(info.getUserId())) {
			resp.sendRedirect(cp+"/photo/list.do?page="+page);
			return;
			
		}

		// jsp�� �ѱ� �� 
		req.setAttribute("dto", dto);
		req.setAttribute("page", page);
		req.setAttribute("mode", "update");
		
		forward(req, resp, "/WEB-INF/views/photo/created.jsp");
		
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cp = req.getContextPath();
		PhotoDAO dao = new PhotoDAO();
		
		String encType="utf-8";
		int maxSize=5*1024*1024;
		
		MultipartRequest mreq = new MultipartRequest(req,pathname,
				maxSize,encType, new DefaultFileRenamePolicy());
		
		PhotoDTO dto = new PhotoDTO();
		dto.setNum(Integer.parseInt(mreq.getParameter("num")));
		dto.setSubject(mreq.getParameter("subject"));
		dto.setContent(mreq.getParameter("content"));
		
		String imageFilename =mreq.getParameter("imageFilename");
		
		if(mreq.getFile("upload")!=null) {
			//���� ���� ����� 
			FileManager.doFiledelete(pathname, imageFilename);
			
			//������ ����� ���ϸ� 
			
			String saveFilename = mreq.getFilesystemName("upload");
			saveFilename=FileManager.doFilerename(pathname, saveFilename);
			dto.setImageFilename(saveFilename);
			
		}else {
			// ������ �������� ���� ��� 
			dto.setImageFilename(imageFilename);
		}
		
		dao.updatePhoto(dto);
		
		String page = mreq.getParameter("page");
		resp.sendRedirect(cp+"/photo/article.do?page="+page+"&num="+dto.getNum());
		

		
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String cp = req.getContextPath();
		PhotoDAO dao = new PhotoDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info =(SessionInfo)session.getAttribute("member");
		
		int num = Integer.parseInt(req.getParameter("num"));
		String page = req.getParameter("page");
		
		PhotoDTO dto = dao.readPhoto(num);
		if(dto == null) {
			resp.sendRedirect(cp+"/photo/list.do?page="+page);
			return; 
		}
		
		if((! info.getUserId().equals("admin"))&&(! dto.getUserId().equals(info.getUserId()))) {
			resp.sendRedirect(cp+"/photo/list.do?page="+page);
			return;
			
		}
		
		FileManager.doFiledelete(pathname, dto.getImageFilename());
		
		dao.deletePhoto(num);
		
		resp.sendRedirect(cp+"/photo/list.do?page="+page);
		
	}
}
