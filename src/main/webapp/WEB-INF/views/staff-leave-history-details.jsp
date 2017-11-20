<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


	<h3>Your Leave Details</h3>
	<form:form method="POST" commandName="leave"
		action="${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html">

		<div class="table-responsive">
			<table class="table">
				<tbody>
					<tr>
						<td>Leave ID</td>
						<td>${leave.leaveId}</td>
					<tr>
						<td>Status</td>
						<td>${leave.status}</td>
					</tr>
					<tr>
						<td>Leave Type</td>
						<td>${leave.leaveTypeModel.leaveName}</td>
					</tr>
					<tr>
						<td>Start Date</td>
						<td>${leave.startDate}</td>
					</tr>
					<tr>
						<td>End Date</td>
						<td>${leave.endDate}</td>
					</tr>
					<tr>
						<td>Reason</td>
						<td>${leave.reason}</td>
					</tr>
					<tr>
						<td>Contact Details</td>
						<td>${leave.contactDetails}</td>
					</tr>
					<tr>
						<td>Dissemination Name</td>
						<td>${leave.disseminationMember.name}</td>
					</tr>
					<tr>
						<td>Dissemination</td>
						<td>${leave.dissemination}</td>
					</tr>
					<tr>
						<td>Comment</td>
						<td>${leave.comment}</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Back" class="btn btn-primary" /></td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>

	</form:form>
