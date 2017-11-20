<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="table-responsive">
	<table class="table table-hover">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>ContactNo</th>
			<th>Email</th>
			<th>Home Address</th>
			<th>Designation</th>
			<th>Reports To</th>

		</tr>
		<tr>
			<td>${ staffMember.staffId }</td>
			<td>${ staffMember.name }</td>
			<td>${ staffMember.contactNo }</td>
			<td>${ staffMember.contactNo }</td>
			<td>${ staffMember.homeAddress }</td>
			<td>${ staffMember.designation }</td>
			<td>${ staffMember.managerId }</td>
		</tr>


	</table>
</div>


