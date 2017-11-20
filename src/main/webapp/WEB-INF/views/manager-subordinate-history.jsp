<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<body>
	<h1>
		<spring:message code="title.leaveDetailsHistory" />
	</h1>


	<h3>Staff Name: ${staffMember.name}</h3>
	<c:if test="${fn:length(leaveHistoryList) gt 0}">

		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr class="listHeading">
						<th><spring:message code="fieldLabel.leaveId" /></th>
						<th><spring:message code="fieldLabel.employeeId" /></th>
						<th><spring:message code="fieldLabel.leaveType" /></th>
						<th><spring:message code="fieldLabel.startDate" /></th>
						<th><spring:message code="fieldLabel.endDate" /></th>
						<th><spring:message code="fieldLabel.dissemination" /></th>
						<th><spring:message code="fieldLabel.status" /></th>
						<th><spring:message code="title.leaveDetails" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="leave_history" items="${leaveHistoryList}">
						<tr class="listRecord">
							<td>${leave_history.leaveId}</td>
							<td>${leave_history.staffMember.name}</td>
							<td>${leave_history.leaveTypeModel.leaveName}</td>
							<td><fmt:formatDate value="${ leave_history.startDate }"
									pattern="dd MMM" /></td>
							<td><fmt:formatDate value="${ leave_history.endDate }"
									pattern="dd MMM" /></td>
							<td>${leave_history.disseminationMember.name}</td>
							<td>${leave_history.status.toString()}</td>
							<td><a
								href="${pageContext.request.contextPath}/manager/subordinate/history/detail/${leave_history.leaveId}.html"
								class="btn btn-success">Detail</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</c:if>
</body>
