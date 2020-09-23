<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" type="text/css"
	href="/resources/css/common.css?ver=3">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<c:forEach items="${css}" var="item">
	<link rel="stylesheet" type="text/css"
		href="/resources/css/${item}.css?ver=17">
</c:forEach>
</head>
<body>
	<div id="container">
		<header>
			<div id="headerLeft">
				<c:if test="${loginUser !=null}">
					<div class="containerPImg">
						<c:choose>
							<c:when test="${loginUser.profile_img != null}">
								<img class="pImg"
									src="/resources/img/user/${loginUser.i_user}/${loginUser.profile_img}">
							</c:when>
							<c:otherwise>
								<img class="pImg" src="/resources/img/default_profile.png">
							</c:otherwise>
						</c:choose>
					</div>
					<div class="ml5">${loginUser.nm}님환영합니다.</div>
					<div class="ml15" id="headerLogout">
						<a href="/user/logout">로그아웃</a>
					</div>
				</c:if>
				<c:if test="${loginUser == null }">
					<div class="ml15" id="headerLogout">
						<a href="/user/login">로그인</a>
					</div>
				</c:if>
			</div>
			<div id="headerRight">
				<a href="/rest/map">지도</a>
				<c:if test="${loginUser==null}" >
				<a class="ml15" href="/user/login">등록</a>
				<a class="ml15" href="/user/login" >찜</a>
				</c:if>
				<c:if test="${loginUser!=null}" >
				<a class="ml15" href="/rest/reg">등록</a>
				<a class="ml15" href="/rest/faver">찜</a>
				</c:if>
			</div>
		</header>
		<section>
			<jsp:include page="/WEB-INF/views/${view}.jsp"></jsp:include>
		</section>
		<footer>
			<span>회사 정보</span>
		</footer>
	</div>

	</body>
</html>