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

<script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">



function replySubmit(){    
 var param = $("form[name=reply_form]").serialize();

 $.ajax({   
   type: "POST"  
  ,url: "<%=cp%>/bbs/replyCreate_ok.do"
  ,data: param

  ,success:function(data){
    $("#replyOut").html(data);
    $('#reply_content').val("");
  }
  ,error:function(data){
    alert("error");
  }
  });
} 


function replyDelete(id)
{
	msg = "정말 해당 댓글을 삭제하시겠습니까??";
    if (confirm(msg)!=0) {
    } else {
    	return;
    }
    
	var reqURL = "<%=cp%>/bbs/replyDelete_ok.do?con_id=${con_uid}&id=" + id;
	
	$.ajax({   
	  url: reqURL
	  ,success:function(data){
	    $("#replyOut").html(data);
	  }
	  ,error:function(data){
	    alert("error");
	  }
	  });
}




function replyList(){    

 $.ajax({   
  url: "<%=cp%>/bbs/replyList_ok.do?id=${con_uid}"
  ,success:function(data){
    $("#replyOut").html(data);
  }
  ,error:function(data){
    alert("error");
  }
  });
} 

$(document).ready(function() {

	replyList();
	
	$('#reply_content').keydown(function(evt){
		if((evt.keyCode) && (evt.keyCode==13)) {
			replySubmit();
		}
	});
	
});



function deleteBoard(num) {
<c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
    var page = "${page}";
    var query = "num="+num+"&page="+page;
    var url = "<%=cp%>/bbs/delete.do?" + query;

    if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
    	location.href=url;
</c:if>    
<c:if test="${sessionScope.member.userId!='admin' && sessionScope.member.userId!=dto.userId}">
    alert("게시물을 삭제할 수  없습니다.");
</c:if>
}

function updateBoard(num) {
<c:if test="${sessionScope.member.userId==dto.userId}">
    var page = "${page}";
    var query = "num="+num+"&page="+page;
    var url = "<%=cp%>/bbs/update.do?" + query;

    location.href=url;
</c:if>

<c:if test="${sessionScope.member.userId!=dto.userId}">
   alert("게시물을 수정할 수  없습니다.");
</c:if>
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
            <h3><span style="font-family: Webdings">2</span> 게시판 </h3>
        </div>
        
        <div>
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				   ${con_title}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       이름 :  ${con_user_name}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${con_reg_date} | 조회 ${con_count}
			    </td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="200">
			      ${con_note}
			   </td>
			</tr>
			
			
			<table style="width: 100%; margin: 0px auto 20px; border-spacing: 0px;">
			<tr height="45">
			    <td width="300" align="left">
			       <c:if test="${sessionScope.member.userId==dto.userId}">				    
			          <button type="button" class="btn" onclick="updateBoard('${dto.num}');">수정</button>
			       </c:if>
			       <c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">				    
			          <button type="button" class="btn" onclick="deleteBoard('${dto.num}');">삭제</button>
			       </c:if>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn btn-sm btn-outline-secondary" onclick="javascript:location.href='<%=cp%>/bbs/list.do?${query}';">리스트</button>
			    </td>
			</tr>
        </div>
		
		<span id="replyOut"></span>
	
<form name="reply_form" id="reply_form">
	<table style="width:1200px">
	  <tr>
	      
	      <td>
	            <input type="text" name="reply_content" id="reply_content" class="form-control">
				<input type="hidden" name="reply_content_uid" id="reply_content_uid" class="form-control" value="${con_uid}">
				<input type="hidden" name="reply_member_uid" id="reply_member_uid" class="form-control" value="${sessionScope.member.userId}">
	      </td>
	      <td>
	            &nbsp;&nbsp;<button type="button" name="sendReply" class="btn btn-primary" onclick="replySubmit();">댓글 등록</button>
	  
	      </td>
	  </tr>  
	</table>
</form>  
	
			
    </div>
    
    

			  
</div>


			  
<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>