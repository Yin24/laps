
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<html>
<head>
<title>Insert title here</title>
</head>
<body>

	<h3>Holiday List</h3>
	<br>
	<c:if test="${fn:length(hlist) gt 0}">
		<div class="table-responsive">
			<table class="table table-hover ">
				<thead>
					<tr class="listHeading">
						<th>ID</th>
						<th>Name</th>
						<th>Date</th>



						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="holiday" items="${hlist}">
						<tr class="listHeading">
							<td>${holiday.holidayId}</td>
							<td>${holiday.name}</td>
							<td>${holiday.date}</td>


							<td><a
								href="${pageContext.request.contextPath}/admin/holiday/edit/${holiday.holidayId}.html" class="btn btn-success">Edit</a></td>
							<td><a
								href="${pageContext.request.contextPath}/admin/holiday/delete/${holiday.holidayId}.html" class="btn btn-danger">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<a class="btn btn-primary"
		href="${pageContext.request.contextPath}/admin/holiday/create">Add
		New Holiday</a>
</body>
</html>