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
	<div class="row">
		<div class="col-md-8">

			<div class="table-responsive">

				<table class="table">
					<tr>
						<th align="right"><spring:message code="fieldLabel.employeeName" /></th>
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
						<th align="right"><spring:message code="fieldLabel.leaveType" /></th>
						<td colspan="3"><c:out value="${leave.leaveTypeModel.leaveName}" /></td>
					</tr>
					<tr>
						<th align="right"><spring:message code="fieldLabel.startDate" /></th>
						<td><c:out value="${leave.startDate }" /></td>
						<th align="right"><spring:message code="fieldLabel.endDate" /></th>
						<td><c:out value="${leave.endDate }" /></td>
					</tr>
					<tr>
						<th align="right"><spring:message code="fieldLabel.dissemTask" /></th>
						<td><c:out value="${leave.dissemination}" /></td>
						<th align="right"><spring:message
								code="fieldLabel.dissemination" /></th>
						<td><c:out value="${leave.disseminationMember.name}" /></td>
					</tr>
					<tr>
						<th align="right"><spring:message code="fieldLabel.contactInfo" /></th>
						<td colspan="3"><c:out value="${leave.contactDetails}" /></td>
					</tr>
					<tr>
						<th align="right"><spring:message code="fieldLabel.status" /></th>
						<td colspan="3"><c:out value="${leave.status}" /></td>
					</tr>
					<tr>
						<th align="right"><spring:message
								code="fieldLabel.actionComment" /></th>
						<td colspan="3"><c:out value="${leave.comment}" /></td>
					</tr>
				</table>

			</div>
		</div>
	</div>

	<form:form method="POST" modelAttribute="leave"
		action="${pageContext.request.contextPath}/manager/pending/edit/${leave.leaveId}.html">
		<form:button name="cancel" type="submit" value="submit"
			class="btn btn-primary">
			Cancel
		</form:button>
	</form:form>

</body>
</html>
