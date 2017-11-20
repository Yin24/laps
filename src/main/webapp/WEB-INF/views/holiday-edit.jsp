
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
	
</script>

<h3>Edit Holiday</h3>
<form:form method="POST" commandName="hlist"
	action="${pageContext.request.contextPath}/admin/holiday/edit/${hlist.holidayId}.html">
	<table class="table">
		<tbody>
			<tr>
				<td>Holiday ID</td>
				<td><form:input path="holidayId"  readonly="true"/></td>
				<td><form:errors path="holidayId" cssStyle="color: red;" /></td>
			</tr>
			
			<tr>
				<td>Holiday Name</td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" cssStyle="color: red;" /></td>
			</tr>
			<tr>
				<td>Date</td>
				<td><form:input size="16" path="date" id="datepicker1" />
					<form:errors path="date" cssStyle="color: red;" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="Edit" class="btn btn-primary"/></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</form:form>