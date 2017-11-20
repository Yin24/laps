<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="false"%>
<html>
<head>
<title><spring:message code="title.leaveDetails" /></title>
</head>
<body>
	<h3>
		${leave.staffMember.name}'s
		<spring:message code="fieldLabel.leave" />
		${leave.leaveId}
	</h3>
	<form:form method="POST" modelAttribute="approve"
		action="${pageContext.request.contextPath}/manager/pending/edit/${leave.leaveId}.html">
		<div class="row">
			<div class="col-md-10">

				<div class="table-responsive">

					<table class="table table-striped">
						<tr>
							<th align="right"><spring:message
									code="fieldLabel.employeeName" /></th>
							<td colspan="3"><c:out value="${leave.staffMember.name}" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message code="fieldLabel.leaveId" /></th>
							<td colspan="3"><c:out value="${leave.leaveId}" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message code="fieldLabel.reason" /></th>
							<td colspan="3"><c:out value="${leave.reason}" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message
									code="fieldLabel.leaveType" /></th>
							<td colspan="3"><c:out
									value="${leave.leaveTypeModel.leaveName}" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message
									code="fieldLabel.startDate" /></th>
							<td><c:out value="${leave.startDate }" /></td>
							<th align="right"><spring:message code="fieldLabel.endDate" /></th>
							<td><c:out value="${leave.endDate }" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message
									code="fieldLabel.dissemTask" /></th>
							<td><c:out value="${leave.dissemination}" /></td>
							<th align="right"><spring:message
									code="fieldLabel.dissemination" /></th>
							<td><c:out value="${leave.disseminationMember.name}" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message
									code="fieldLabel.contactInfo" /></th>
							<td colspan="3"><c:out value="${leave.contactDetails}" /></td>
						</tr>
						<tr>
							<th align="right"><spring:message code="fieldLabel.status" /></th>
							<td colspan="3"><c:out value="${leave.status}" /></td>
						</tr>

					</table>
				</div>
			</div>
		</div>
		<%-- add current staff movement registry --%>
		<c:if test="${fn:length(subLeave) gt 0}">
			<h4>Current subordinates on leave during same period</h4>
			<table class="table table-hover">
				<thead>
					<tr class="listHeading">
						<th><spring:message code="fieldLabel.leaveId" /></th>
						<th><spring:message code="fieldLabel.employeeId" /></th>
						<th><spring:message code="fieldLabel.leaveType" /></th>
						<th><spring:message code="fieldLabel.startDate" /></th>
						<th><spring:message code="fieldLabel.endDate" /></th>
						<th><spring:message code="fieldLabel.dissemination" /></th>
						<th><spring:message code="fieldLabel.status" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="sub_leave" items="${subLeave}">
						<tr class="listRecord">
							<td>${sub_leave.leaveId}</td>
							<td>${sub_leave.staffMember.name}</td>
							<td>${sub_leave.leaveTypeModel.leaveName}</td>
							<td>${sub_leave.startDate}</td>
							<td>${sub_leave.endDate}</td>
							<td>${sub_leave.disseminationMember.name}</td>
							<td>${sub_leave.status.toString()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

		<p style="color: red; font-size: 1em;">${ valError }</p>
		<h4>Approve or Reject</h4>
			${valdError}
		<table class="table table-hover">
			<tr>
				<td><form:radiobutton path="decision" value="APPROVED"
						id="decision" /> <spring:message code="caption.approve" />
					&nbsp;&nbsp; <form:radiobutton path="decision" value="REJECTED"
						id="decision" /> <spring:message code="caption.reject" /></td>
			</tr>
			<tr>
				<td><spring:message code="fieldLabel.actionComment" /><br>
					<form:textarea path="comment" cols="60" rows="4" id="comment" /></td>
			</tr>
		</table>


		<td><form:button name="submit" type="submit" value="submit"
				class="btn btn-primary">
			 Submit
		</form:button></td>

		<td><form:button name="cancel" type="submit" value="submit"
				class="btn btn-primary">
			Cancel
		</form:button></td>

	</form:form>
</body>
</html>
