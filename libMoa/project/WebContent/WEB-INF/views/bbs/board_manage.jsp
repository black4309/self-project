<%@ page contentType="text/html; charset=UTF-8" %>
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

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>

<script type="text/javascript">
function dataSubmit() {
	
	var str = $("#board_name").val();
    if(str == "") {
        alert("게시판명을 입력하세요. ");
        return;
    }

    var str = $("#board_desc").val();
    if(str == "") {
        alert("게시판 설명을 입력하세요");
        return;
    }
    
    
	var f = document.boardManageForm;
    f.action = "<%=cp%>/bbs/board_create_ok.do";
    f.submit();
}

function dataDelete(uid)
{
	msg = "정말 해당 게시판을 삭제하시겠습니까??";
    if (confirm(msg)!=0) {

    	var url = "<%=cp%>/bbs/board_delete_ok.do?id=" + uid;
    	location.href = url;

    } else {
    }
        
}


</script>
</head>
<body>

<div class="header">

    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
   
</div>
	
<div class="container">
    <div class="body-container" style="width: 900px;">
        <div class="body-title">
            <h3><span style="font-family: Webdings">2</span> 게시판 관리 </h3>
        </div>
        
        <div>
			<form name="boardManageForm" method="post">
			  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			  
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">게시판명</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            
			            <input type="text" name="board_name" class="form-control" id="board_name">
			        </p>
			      </td>
			  </tr>
			  
			  <tr>
			      <td width="100" valign="top" style="text-align: right; padding-top: 5px;">
			            <label style="font-weight: 900;">게시판 설명</label>
			      </td>
			      <td style="padding: 0 0 15px 15px;">
			        <p style="margin-top: 1px; margin-bottom: 5px;">
			            <input type="text" name="board_desc" class="form-control" id="board_desc">
			        </p>
			      </td>
			  </tr>
			  
			  </table>
			
			  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			        <button type="button" name="sendButton" class="btn btn-primary" onclick="dataSubmit();">게시판 생성</button>
			      </td>
			    </tr>
			    
			  </table>
			</form>

				<hr>		
				<br/>
						
			<table class="table table-bordered">
			    <thead>
			      <tr style="">
			        <th><center>게시판명</center></th>
			        <th><center>게시판 설명</center></th>
			        <th><center>생성일시</center></th>
			        <th  style="width:70px"><center>삭제</center></th>
			      </tr>
			    </thead>
			    <tbody>
			      <c:forEach var="dto" items="${lib_board_list}">
					  <tr> 
					      <td style="width:200px"><center>${dto.subject}</center></td>
					      <td>${dto.description}</td>
					      <td style="width:200px"><center>${dto.regDate}</center></td>
					      <td>
					      <center>
					      <button type="button" name="sendButton" class="btn btn-sm btn-info" onclick="dataDelete(${dto.uid});">삭제</button>
					      </center>
					      </td>
					  </tr>
					</c:forEach> 
			    </tbody>
			  </table>
			  
			  <br/>
			  
			  
			  

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