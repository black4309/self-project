﻿<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>spring</title>

<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/css/layout.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	function searchList() {
		var str = $("#searchValue").val();
		var url = "<%=cp%>/bbs/list.do?id=${board_uid}" + "&search=" + str;
		location.href = url;
		
	}
	
	function changeBoard()
	{
		var uid = $("#selectBoard").val();	
		location.href="<%=cp%>/bbs/list.do?id=" + uid;
	}
	
	function initPage()
	{
		$("#selectBoard").val("${board_uid}").prop("selected", true);
	}
	
	$(document).ready(function() {
	    initPage();
	});
</script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
	
<div class="container">
    <div class="body-container" style="width: 900px;">
        
        <br/><br/>
        <table>
        	<td>
	       	 	게시판 선택 :&nbsp;&nbsp;
	       	</td>
	       	<td> 
				<select style="width:250px" onchange="changeBoard()" id="selectBoard" class="form-control">
				<c:forEach var="lib_board_dto" items="${lib_board_list}">
						  <option value="${lib_board_dto.uid}">${lib_board_dto.subject}</option>
				</c:forEach> 
				</select>	
			</td>
		</table>
		
		<hr>
        	게시판 설명 : ${board_desc}
        <hr>
        
        <div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="left" width="50%">
			          ${dataCount}개(${page}/${total_page} 페이지)
			      </td>
			      <td align="right">
			          &nbsp;
			      </td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="center" bgcolor="#eeeeee" height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <th width="60" style="color: #787878;">번호</th>
			      <th style="color: #787878;">제목</th>
			      <th width="50" style="color: #787878;">작성자</th>
			      <th width="200" style="color: #787878;">작성일</th>
			      <th width="60" style="color: #787878;">조회수</th>
			  </tr>
			 
			 <c:forEach var="dto" items="${lib_conetnts_list}">
			  <tr align="center" bgcolor="#ffffff" height="35" style="border-bottom: 1px solid #cccccc;"> 
			      <td>${dto.uid}</td>
			      <td align="left" style="padding-left: 10px;">
			           <a href="<%=cp%>/bbs/article.do?id=${board_uid}&content=${dto.uid}&page=${page}">${dto.title}</a>
			      </td>
			      <td>${dto.userName}</td>
			      <td>${dto.regDate}</td>
			      <td>${dto.counts}</td>
			  </tr>
			</c:forEach> 
			</table>
			 
			<table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			   <tr height="35">
				<td align="center">
			        <c:if test="${dataCount==0 }">
			                등록된 게시물이 없습니다.
			         </c:if>
			        <c:if test="${dataCount!=0 }">
			               ${paging}
			         </c:if>
				</td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin: 10px auto; border-spacing: 0px;">
			   <tr height="40">
			      <td align="left" width="100">
			          <button type="button" class="btn btn-outline-secondary" onclick="javascript:location.href='<%=cp%>/bbs/list.do?id=${board_uid}';">새로고침</button>
			      </td>
			      <td align="center">
			          <form name="searchForm" action="<%=cp%>/bbs/list.do" method="post">
			              
			                  제목 :&nbsp;&nbsp;
			             
			            <input type="text" name="searchValue" id="searchValue" class="boxTF">
			            <button type="button" class="btn btn-outline-secondary" onclick="searchList()">검색</button>
			        </form>
			      </td>
			      <td align="right" width="100">
			          <button type="button" class="btn btn-outline-secondary" onclick="javascript:location.href='<%=cp%>/bbs/created.do?id=${board_uid}';">글올리기</button>
			      </td>
			   </tr>
			</table>
        </div>

    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>