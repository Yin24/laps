<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Employee Leave History</h3>
	<br />
	<p style="color: red; font-size: 1em;">${ valError }</p>
	<c:if test="${fn:length(lhistory) gt 0}">
		<div class="table-responsive">
			<table class="table table-hover ">
				<thead>
					<tr>

						<th><spring:message code="Leave ID" /></th>
						<th><spring:message code="Leave Type" /></th>
						<th><spring:message code="Start Date" /></th>
						<th><spring:message code="End Date" /></th>
						<th><spring:message code="Status" /></th>
						<th><spring:message code="Details" /></th>
						<th><spring:message code="Update" /></th>
						<th><spring:message code="Cancel/Delete" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="leave" items="${lhistory}">
						<tr>
							<td>${leave.leaveId}</td>
							<td>${leave.leaveTypeModel.leaveName}</td>
							<td>${leave.startDate}</td>
							<td>${leave.endDate}</td>
							<td>${leave.status}</td>
							<c:if
								test="${leave.status eq 'PENDING'|| leave.status eq 'UPDATED'}">
								<td><a
									href="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html"
									class="btn btn-primary"><spring:message code="details" /></a></td>
								<td><a
									href="${pageContext.request.contextPath}/staff/history/update/${leave.leaveId}.html"
									class="btn btn-success"><spring:message code="update" /></a></td>
								<td><a
									href="${pageContext.request.contextPath}/staff/history/delete/${leave.leaveId}.html"
									class="btn btn-danger"><spring:message code="delete" /></a></td>
							</c:if>
							<c:if test="${leave.status eq 'APPROVED'}">
								<td><a
									href="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html"
									class="btn btn-primary"><spring:message code="details" /></a></td>
								<td></td>
								<td><a
									href="${pageContext.request.contextPath}/staff/history/cancel/${leave.leaveId}.html"
									class="btn btn-danger"><spring:message code="cancel" /></a></td>
							</c:if>
							<c:if
								test="${leave.status eq 'REJECTED'|| leave.status eq 'CANCELLED'|| leave.status eq 'DELETED'}">
								<td><a
									href="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html"
									class="btn btn-primary"><spring:message code="details" /></a></td>
								<td></td>
								<td></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<!-- Pagination  -->
			<!-- Paging control -->
			<c:set var="buttonClasses" value="btn btn-default btn-sm" />
			<c:url var="firstBtnUrl" value="history">
				<c:param name="page" value="${ 1 }" />
				<c:param name="perPage" value="${ perPage }" />
			</c:url>
			<c:url var="prevBtnUrl" value="history">
				<c:param name="page" value="${ currentPage - 1 }" />
				<c:param name="perPage" value="${ perPage }" />
			</c:url>
			<c:url var="nextBtnUrl" value="history">
				<c:param name="page" value="${ currentPage + 1 }" />
				<c:param name="perPage" value="${ perPage }" />
			</c:url>
			<c:url var="lastBtnUrl" value="history">
				<c:param name="page" value="${ totalPages }" />
				<c:param name="perPage" value="${ perPage }" />
			</c:url>


			<div class='col-xs-6 col-xs-offset-3'>

				<div class='row'>
					<div class='btn-group'>
						<a href='${ prevBtnEnabled ? firstBtnUrl : "#" }'
							class='${ buttonClasses }' ${ prevBtnEnabled ? '' : 'disabled' }>First</a>
						<a href='${ prevBtnEnabled ? prevBtnUrl : "#" }'
							class='${ buttonClasses }' ${ prevBtnEnabled ? '' : 'disabled' }>Previous</a>
					</div>
					<!-- Change page dropdown -->
					<div class="btn-group dropup">
						<button type="button"
							class="btn btn-sm btn-default dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Page ${ currentPage } / ${ totalPages } <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li class="dropdown-header">Total ${ totalPages } page<c:if
									test="${ totalPages > 1 }">s</c:if>
							</li>
							<c:forEach var="i" begin="1" end="${ totalPages }">

								<c:url var="changePageUrl" value="history">
									<c:param name="page" value="${ i }" />
									<c:param name="perPage" value="${ perPage }" />
								</c:url>

								<c:choose>
									<c:when test="${ i == currentPage }">
										<li class="disabled"><a>Page ${ i }</a></li>
									</c:when>
									<c:when test="${ i != currentPage }">
										<li><a href="${ changePageUrl }">Page ${ i }</a></li>
									</c:when>
								</c:choose>
							</c:forEach>
						</ul>
					</div>
					<!-- Change page dropdown -->

					<div class='btn-group'>
						<a href='${ nextBtnEnabled ? nextBtnUrl : "#" }'
							class='${ buttonClasses }' ${ nextBtnEnabled ? '' : 'disabled' }>Next</a>
						<a href='${ nextBtnEnabled ? lastBtnUrl : "#" }'
							class='${ buttonClasses }' ${ nextBtnEnabled ? '' : 'disabled' }>Last</a>
					</div>
				</div>

				<!-- Per page -->
				<div class='row margin-10'>
					<span>Results per page:</span>
					<div class="btn-group dropup">
						<button type="button"
							class="btn btn-sm btn-default dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							${ perPage } <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<c:forEach items="${ perPageList }" var="perPageItem">

								<c:url var="changePerPageUrl" value="history">
									<c:param name="page" value="${ currentPage }" />
									<c:param name="perPage" value="${ perPageItem }" />
								</c:url>

								<c:choose>
									<c:when test="${ perPageItem == perPage }">
										<li class="disabled"><a>${ perPageItem }</a></li>
									</c:when>
									<c:when test="${ perPageItem != perPage }">
										<li><a href="${ changePerPageUrl }">${ perPageItem }</a></li>
									</c:when>
								</c:choose>
							</c:forEach>

						</ul>
					</div>
				</div>

			</div>
			<!-- End Pagination  -->

		</div>
	</c:if>
</body>
</html>