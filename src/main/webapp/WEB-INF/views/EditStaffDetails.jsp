<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Staff Details</title>
</head>
<body>

	<center>
		<h2>Edit Staff Details</h2>
	</center>

	<div align="center">
		<table>
			<tr>
				<td>Staff ID:</td>
				<td><c:out value="${staff_list.staff_id}" />
			</tr>
			<tr>
				<td>Name:</td>
				<td><input style="" value="${staff_list.name}" /></td>
			</tr>
			<tr>
				<td>Contact No:</td>
				<td><input style="" value="${staff_list.contact_no}" /></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input style="" value="${staff_list.email}" /></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><input style="" value="${staff_list.address}" /></td>
			</tr>
			<tr>
				<td>Designation:</td>
				<td><select>
						<option value="Manager">Manager</option>
						<option value="Employee">Employee</option>
						<option value="Admin">Administrator</option>
				</select></td>
			</tr>


			<tr>
				<td>Reports To</td>
				<td>
					<%-- <jsp:useBean id="managerbean" class="com.teamtwo.laps.controller.ManagerController" /> --%>
					<select>
						<c:forEach items="${managers}" var="manager">
							<option value="${manager.name}">${manager.name}</option>
						</c:forEach>
				</select>
				</td>
			</tr>

		</table>

<br/>
<input type="submit" value="Undo Changes" class="btn btn-primary"> &nbsp;&nbsp; <input type="submit" value="Submit" class="btn btn-primary">

	</div>
</body>
</html>