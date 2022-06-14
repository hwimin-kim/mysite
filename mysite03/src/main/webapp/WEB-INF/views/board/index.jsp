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
					<input type="text" id="keyWord" name="kwd" value="" placeholder="${keyWord }">
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
						<td>${totalCount-(pagingVo.currentPage-1)*pagingVo.pageCount-status.index }</td>
						<td style="text-align:left; padding-left:${(vo.depth-1) *10}px">
							<c:if test="${vo.depth != 1}">
								<img src='${pageContext.servletContext.contextPath }/assets/images/reply.png' />
							</c:if>
								<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}?p=${pagingVo.currentPage }&kwd=${keyWord }">${vo.title }</a>
						</td>
						<td>${vo.name }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<c:if test="${vo.user_no eq authUser.no}">
								<td><a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no}" class="del">삭제</a></td>
						</c:if>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${pagingVo.minPage < pagingVo.currentPage and empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagingVo.currentPage -1 }">◀</a></li>
						</c:if>
						<c:if test="${pagingVo.minPage < pagingVo.currentPage and not empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagingVo.currentPage -1 }&kwd=${keyWord }">◀</a></li>
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
										<li><a href="${pageContext.servletContext.contextPath }/board?p=${i }">${i }</a></li>
								</c:when>
								<c:otherwise>
										<li><a href="${pageContext.servletContext.contextPath }/board?p=${i }&kwd=${keyWord }">${i }</a></li>
								</c:otherwise>				
						</c:choose>
						</c:forEach>
						<c:if test="${pagingVo.maxPage > pagingVo.currentPage and empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagingVo.currentPage +1 }">▶</a></li>
						</c:if>
						<c:if test="${pagingVo.maxPage > pagingVo.currentPage and not empty keyWord}">
								<li><a href="${pageContext.servletContext.contextPath }/board?p=${pagingVo.currentPage +1 }&kwd=${keyWord }">▶</a></li>
						</c:if>	
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
						<c:if test="${not empty authUser }">
								<a href="${pageContext.servletContext.contextPath }/board/write?p=${param.p }&kwd=${param.kwd }" id="new-book">글쓰기</a>
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