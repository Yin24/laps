<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<h3>New Leave Type</h3>
<form:form method="POST" commandName="leavetype"
	action="${pageContext.request.contextPath}/admin/leavetype/create.html">
	<div class="table-responsive">
	<table class="table">
		<tbody>
			<tr>
				<td>Leave Type</td>
				<td><form:input  path="leaveType" /></td>
				<td><form:errors path="leaveType" cssStyle="color: red;" cssClass="error" /></td>

			</tr>
			<tr>
				<td>Leave Name</td>
				<td><form:input path="leaveName" /></td>
				<td><form:errors path="leaveName" cssStyle="color: red;" cssClass="error" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="Create" class="btn btn-primary" onclick="myFunction()"/></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
</form:form>
