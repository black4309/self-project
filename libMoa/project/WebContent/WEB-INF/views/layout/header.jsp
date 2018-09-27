<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">


<script type="text/javascript">
//엔터 처리
$(function(){
	   $("input").not($(":button")).keypress(function (evt) {
	        if (evt.keyCode == 13) {
	            var fields = $(this).parents('form,body').find('button,input,textarea,select');
	            var index = fields.index(this);
	            if ( index > -1 && ( index + 1 ) < fields.length ) {
	                fields.eq( index + 1 ).focus();
	            }
	            return false;
	        }
	     });
});
</script>

<div class="header-top">
    <div class="header-left">
        <p style="margin: 2px;">
            <a href="<%=cp%>/" style="text-decoration: none;">
                <img src="<%=cp%>/resource/images/LibingMoa.png"> 
            </a>
        </p>
    </div>
    <div class="header-right">
        <div style="padding-top: 20px;  float: right;">
            <c:if test="${empty sessionScope.member}">
                <a href="<%=cp%>/member/login.do">회원 로그인</a>
                    &nbsp;|&nbsp;
                <a href="<%=cp%>/member/admin.do">관리자 로그인</a>
                    &nbsp;|&nbsp;
                <a href="<%=cp%>/member/member.do">회원가입</a>
            </c:if>
            <c:if test="${not empty sessionScope.member}">
                <span style="color:blue;">${sessionScope.member.userName}</span>님
                    &nbsp;|&nbsp;
                    <a href="<%=cp%>/member/logout.do">로그아웃</a>
                    &nbsp;|&nbsp;
                    <a href="<%=cp%>/member/pwd.do?mode=update">정보수정</a>

            </c:if>
        </div>
    </div>
</div>

<div class="menu">
    <ul class="nav">
        
        <li>
            <a href="<%=cp%>/">HOME</a>
            
        </li>
        
        <li>
        
        <c:if test="${sessionScope.member.userName eq '관리자'}">
			<a href="<%=cp%>/bbs/board_manage.do">게시판관리</a>
		</c:if>
		<c:if test="${sessionScope.member.userName ne '관리자'}">
		  <a href="<%=cp%>/bbs/list.do">커뮤니티</a>
		</c:if>
		
					
            
            
        </li>
    </ul>      
</div>
