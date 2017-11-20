
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h3>Edit ${staff.name}' employment information</h3>
<form:form method="POST" commandName="staff"
	action="${pageContext.request.contextPath}/admin/staff/create.html">
	<table class="table">
		<tbody>
			<tr>
				<td>Staff ID</td>
				<td><form:input path="staffId" readonly="true"/></td>
				<td><form:errors path="staffId" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td>Name</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td>Contact No</td>
				<td><form:input path="contactNo" /></td>
				<td><form:errors path="contactNo" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td>Email</td>
				<td><form:input path="email" /></td>
				<td><form:errors path="email" cssStyle="color: red;" /></td>
			</tr>
	
			<tr>
				<td>Home Address</td>
				<td><form:input path="homeAddress" /></td>
				<td><form:errors path="homeAddress" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td>Max Annual Leave</td>
				<td><form:input path="aLeave" /></td>
				<td><form:errors path="aLeave" cssStyle="color: red;" /></td>
				<td>Max Medical Leave</td>
				<td><form:input path="mLeave" /></td>
				<td><form:errors path="mLeave" cssStyle="color: red;" /></td>
				<td>Max Compensation Leave</td>
				<td><form:input path="cLeave" /></td>
				<td><form:errors path="cLeave" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td>Designation</td>
				<td><form:input path="designation" /></td>
				<td><form:errors path="designation" cssStyle="color: red;" /></td>
			</tr>

			<tr>
				<td>Manager Name</td>
				<td><form:select path="managerId">
						<form:option value="0" label="...." />
						<c:forEach var="manager" items="${mlist}">
							<form:option value="${ manager.staffId }">${manager.name}</form:option>
						</c:forEach>
					</form:select>
				</td>
				<td><form:errors path="managerId" cssStyle="color: red;" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="Edit" class="btn btn-primary" /></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</form:form>
