
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<html>
<head>
<title>Insert title here</title>
</head>
<body>

	<h3>User List</h3>
	<br>
	<c:if test="${fn:length(userList) gt 0}">
		<div class="table-responsive">
			<table class="table table-hover ">
				<thead>
					<tr class="listHeading">
						<th>User ID</th>
						<th>Password</th>
						<th>Staff ID</th>
						<th>Role</th>

						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${userList}">
						<tr class="listHeading">

							<td>${user.userId}</td>
							<td>${user.password}</td>
							<td>${user.staffId}</td>
							<c:choose>
								<c:when test="${user.isAdmin eq true }">
									<td>Admin</td>
								</c:when>
								<c:when test="${user.isManager eq true }">
									<td>Manager</td>
								</c:when>
								<c:when test="${user.isStaff eq true }">
									<td>Staff</td>
								</c:when>
							</c:choose>
							<td><a
								href="${pageContext.request.contextPath}/admin/user/edit/${user.userId}.html"
								class="btn btn-success">Edit</a></td>
							<td><a
								href="${pageContext.request.contextPath}/admin/user/delete/${user.userId}.html"
								class="btn btn-danger">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<a class="btn btn-primary"
		href="${pageContext.request.contextPath}/admin/user/create">Add
		New User</a>
</body>
</html>