<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="container-fluid">
	<div class="row">
		<h1 align="right">Leave Application System</h1>

	</div>

	<c:if test="${not empty sessionScope.USERSESSION.getSessionId()}">

		<div class="row">
			<nav class="navbar navbar-default">
				<div class="container">
					<div class="navbar-header">

						<c:choose>
							<c:when test="${sessionScope.USERSESSION.user.isAdmin eq true }">
								<spring:url value="/staff/dashboard" var="ulist"
									htmlEscape="true" />
								<a href="${ulist}" class="navbar-brand"> Admin Panel </a>
							</c:when>

							<c:when
								test="${sessionScope.USERSESSION.user.isManager eq true }">
								<spring:url value="/manager/dashboard" var="ulist"
									htmlEscape="true" />
								<a href="${ulist}" class="navbar-brand" > Manager Panel </a>
							</c:when>

							<c:when test="${sessionScope.USERSESSION.user.isStaff eq true }">
								<spring:url value="/staff/dashboard" var="ulist"
									htmlEscape="true" />
								<a href="${ulist}" class="navbar-brand" > Staff Panel </a>
							</c:when>
						</c:choose>



					</div>
					<p class="navbar-text navbar-right">
						Signed in as
						<c:out value="${sessionScope.USERSESSION.user.userId}" />
					</p>
				</div>
			</nav>
		</div>
	</c:if>
</div>



