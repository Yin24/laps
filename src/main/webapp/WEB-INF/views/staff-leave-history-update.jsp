<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link rel="STYLESHEET" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-ui.theme.css" />

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$("#datepicker1").datepicker({
			dateFormat : "dd/mm/yy"
		});
	});
	$(document).ready(function() {
		$("#datepicker2").datepicker({
			dateFormat : "dd/mm/yy"
		});
	});
</script>
<h3>Update Your Leave</h3>
<form:form method="POST" commandName="leave"
	action="${pageContext.request.contextPath}/staff/history/update/${leave.leaveId}.html">
	<form:hidden path="leaveId" />
	<form:hidden path="status" />
	<form:hidden path="leaveType" />
	<form:hidden path="comment" />
	<div class="table-responsive">
		<table class="table">
			<tbody>
				<%-- <tr>
				<td>Leave Type</td>
				<td colspan="3">
 			    <form:select path="leaveType">
				      <c:forEach var="lType" items="${leavename}">
				        <form:option value="${lType.leaveType}">${lType.leaveName}</form:option></c:forEach>
				</form:select> 
				</td>
			</tr>--%>
				<tr>
					<td>Staff ID</td>
					<td><form:input path="staffId" readonly="true" /></td>
					<td><form:errors path="staffId" /></td>
				</tr>
				<tr>
					<td>Start Date</td>
					<td><form:input size="20" path="startDate" type="date"
							id="datepicker1" /></td>
					<td><form:errors path="startDate" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>End Date</td>
					<td><form:input size="20" path="endDate" type="date"
							id="datepicker2" /></td>
					<td><form:errors path="endDate" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>Reason</td>
					<td><form:input path="reason" /></td>
					<td><form:errors path="reason" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>Contact Details</td>
					<td><form:input path="contactDetails" /></td>
					<td><form:errors path="contactDetails" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>Dissemination Name</td>
					<td colspan="5"><form:select path="disseminationId">
							<c:forEach var="staff" items="${staffMembers}">
								<form:option value="${staff.staffId}">${staff.name}</form:option>
							</c:forEach>
						</form:select></td>

					<td><form:errors path="disseminationId" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>Dissemination</td>
					<td><form:input path="dissemination" /></td>
					<td><form:errors path="dissemination" cssStyle="color: red;" /></td>
				</tr>
				<%-- <tr>
				<td>Comment</td>
				<td><form:input path="comment" /></td>
				<td><form:errors path="comment" cssStyle="color: red;" /></td>
			</tr>--%>
				<tr>
					<td><input type="submit" value="Update"
						class="btn btn-primary" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>

</form:form>