
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>
		<spring:message code="title.selectEmployee" />
	</h3>

	<c:if test="${fn:length(subordinateList) gt 0}">

		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr class="listHeading">
						<th><spring:message code="fieldLabel.employeeId" /></th>
						<th><spring:message code="fieldLabel.employeeName" /></th>
						<th><spring:message code="fieldLabel.contactInfo" /></th>
						<th><spring:message code="fieldLabel.email" /></th>
						<th><spring:message code="fieldLabel.homeAddress" /></th>
						<th><spring:message code="fieldLabel.designation" /></th>
						<th><spring:message code="fieldLabel.viewEmployee" /></th>
						<th>View Employee's Overtime claims</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="staff_list" items="${subordinateList}">
						<tr class="listRecord">
							<td>${staff_list.staffId}</td>
							<td>${staff_list.name}</td>
							<td>${staff_list.contactNo}</td>
							<td>${staff_list.email}</td>
							<td>${staff_list.homeAddress}</td>
							<td>${staff_list.designation}</td>
							<td><a
								href="${pageContext.request.contextPath}/manager/subordinate/history/${staff_list.staffId}.html"
								class="btn btn-success">Detail</a></td>
							<td><a
								href="${pageContext.request.contextPath}/manager/comp/history/${staff_list.staffId}.html"
								class="btn btn-success">Pending OT</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>


	</c:if>
</body>

