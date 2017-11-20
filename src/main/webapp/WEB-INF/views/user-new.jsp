<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>New User</h3>
<form:form method="POST" commandName="user"
	action="${pageContext.request.contextPath}/admin/user/create.html">

	<div class="table-responsive">
		<table class="table">
			<tbody>
				<tr>
					<td>User ID</td>
					<td><form:input path="userId" /></td>
					<td><form:errors path="userId" cssStyle="color: red;" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><form:input path="password" /></td>
					<td><form:errors path="password" cssStyle="color: red;" /></td>
				</tr>
				<tr>

					<td>Staff ID</td>

					<td><form:select path="staffId" items="${sidlist}" /></td>

					<td><form:errors path="staffId" cssStyle="color: red;" /></td>
				</tr>

				<tr>
					<td>Role</td>
					<td><form:checkbox path="isAdmin" /> Admin <form:checkbox
							path="isManager" /> Manager <form:checkbox path="isStaff" />
						Staff
				</tr>

				<tr>
					<td><input type="submit" value="Create"
						class="btn btn-primary" /></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</form:form>


