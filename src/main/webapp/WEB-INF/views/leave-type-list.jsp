<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<h3>Leave Type List</h3>


	<c:if test="${fn:length(leaveTypeList) gt 0}">
		<div class="table-responsive">
			<table class="table table-hover ">
				<thead>
					<tr class="listHeading">
						<th>Leave Type</th>
						<th>Leave Name</th>

						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="leave" items="${leaveTypeList}">
						<tr class="listHeading">

							<td>${leave.leaveType}</td>
							<td>${leave.leaveName}</td>
							<td><a
								href="${pageContext.request.contextPath}/admin/leavetype/edit/${leave.leaveType}.html"
								class="btn btn-success">Edit</a></td>
							<td><a
								href="${pageContext.request.contextPath}/admin/leavetype/delete/${leave.leaveType}.html"
								class="btn btn-danger">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<a class="btn btn-primary"
		href="${pageContext.request.contextPath}/admin/leavetype/create">Add
		Leave Type</a>
</body>
</html>