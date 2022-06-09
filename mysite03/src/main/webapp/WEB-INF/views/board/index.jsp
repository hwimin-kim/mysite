<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="" placeholder="${keyWord }">
					<input type="hidden" id="page" name="page" value="1">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>		
					<c:set var='count' value='${fn:length(list)}'/>
					<c:forEach items='${list }' var='vo' varStatus='status'>
					<tr>
						<td>${count-status.index }</td>
						<td style="text-align:left; padding-left:${(vo.depth-1) *10}px">
							<c:if test="${vo.depth != 1}">
								<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
							</c:if>
								<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${vo.no}">${vo.title }</a>
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<c:if test="${vo.userNo eq authUser.no}">
								<td><a href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pagingVo.minPage < pagingVo.currentPage and empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board/${pagingVo.currentPage -1 }">◀</a></li>
						</c:if>
						<c:if test="${pagingVo.minPage < pagingVo.currentPage and not empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board/${pagingVo.currentPage -1 }/${keyWord }">◀</a></li>
						</c:if>
						<c:forEach var="i" begin="${pagingVo.startPage}" end="${pagingVo.endPage}">
						<c:choose>
								<c:when test="${pagingVo.currentPage == i}">
										<li class="selected">${i }</li>
								</c:when>
								<c:when test="${i > pagingVo.maxPage}">
										<li>${i }</li>
								</c:when>
								<c:when test="${empty keyWord}">
										<li><a href="${pageContext.servletContext.contextPath }/board/${i }">${i }</a></li>
								</c:when>
								<c:otherwise>
										<li><a href="${pageContext.servletContext.contextPath }/board/${i }/${keyWord }">${i }</a></li>
								</c:otherwise>				
						</c:choose>
						</c:forEach>
						<c:if test="${pagingVo.maxPage > pagingVo.currentPage and empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board/${pagingVo.currentPage +1 }">▶</a></li>
						</c:if>
						<c:if test="${pagingVo.maxPage > pagingVo.currentPage and not empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board/${pagingVo.currentPage +1 }/${keyWord }">▶</a></li>
						</c:if>	
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
						<c:if test="${not empty authUser }">
								<a href="${pageContext.servletContext.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
						</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>