<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<link rel="STYLESHEET" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-ui.theme.css" />
<link href="${pageContext.request.contextPath}/css/leonard-styles.css"
	rel="STYLESHEET" type="text/css">

<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$("#datepicker1").datepicker({
			dateFormat : "dd/mm/yy"
		});
		$("#datepicker2").datepicker({
			dateFormat : "dd/mm/yy"
		});
		checkLeaveType();
	});

	function checkLeaveType(event) {
		var value = $("#leaveTypeSelect").val();
		if (value == 3) {
			$(".comp-conditional").removeClass("hidden");
		} else {
			$(".comp-conditional").addClass("hidden");
		}
	}
</script>
<c:choose>
	<c:when test="${ isEdit eq true }">
		<h3>Edit Leave page</h3>
	</c:when>
	<c:otherwise>
		<h3>New Leave page</h3>
	</c:otherwise>
</c:choose>
<form:form method="POST" commandName="leave"
	action="${pageContext.request.contextPath}/staff/leave/created">

			<form:input path="staffId" type="hidden"
					value="${leave.staffId}" />
			<form:input path="leaveId" type="hidden"
					value="${leave.leaveId}" />
	<table class="table table-striped">
		<tr>
			
			<td>LeaveType</td>
			
			<td colspan="3">
			<form:select id="leaveTypeSelect" path="leaveType" onchange="checkLeaveType(event);">
					<c:forEach var="lType" items="${leaveTypes}">
						<form:option value="${lType.leaveType }">${lType.leaveName}</form:option>
					</c:forEach>
				</form:select> <form:errors path="leaveType" cssStyle="color: red;" />
				<div class="margin-15">
					<h4><span class="label label-danger">${ leaveDaysError }</span></h4>
				<c:if test="${ not leaveDaysError.isEmpty() }">
				</c:if>
				</div>
			</td>
			
		</tr>
		<tr>
			<td colspan="2">
				<span class="label label-danger">${ compError }</span>
				<div class="comp-conditional hidden">
					<div class="margin-10">
						<p class="label label-info">You have ${ compHours } hours of
							approved overtime</p>
						<br /> <br />
						<p class="label label-info">Eligible for ${ compDays } days of
							compensation leave</p>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<c:set var="fmtStartDate">
				<fmt:formatDate value="${ leave.startDate }" pattern="dd/MM/yyyy" />
			</c:set>
			<td>Start Date</td>
			<td><form:input size="16" path="startDate" id="datepicker1"
					value="${ fmtStartDate }" />
<!-- 				<div class="comp-conditional hidden"> -->
<!-- 					Half day <input type="checkbox" name="startDateHalfDay" /> -->
<!-- 				</div> -->
				<form:errors path="startDate" cssStyle="color: red;" /></td>
		</tr>
		<tr>
			<c:set var="fmtEndDate">
				<fmt:formatDate value="${ leave.endDate }" pattern="dd/MM/yyyy" />
			</c:set>
			<c:set var="isHalfDayCheck">
				<c:if test="${ leave.isHalfDay == 1 }">
					checked="checked"
				</c:if>
			</c:set>
			<td>End Date</td>
			<td><form:input size="16" path="endDate" id="datepicker2"
					value="${ fmtEndDate }" />
				<div class="comp-conditional hidden">
					Half day less <input type="checkbox" name="endDateHalfDay" ${ isHalfDayCheck } />
				</div> <form:errors path="endDate" cssStyle="color: red;" /></td>
		</tr>
		<tr>
			<td>Reason</td>
		<td colspan="3"><form:textarea cols="31" rows="3" path="reason" />
			<form:errors path="reason" cssStyle="color: red;" /></td>
		</tr>
		<tr>
			<td>Contact Details</td>
			<td colspan="3"><form:input size="40" path="contactDetails" /></td>
		</tr>
		<tr>
			<td>Dissemination Staff</td>
			<td colspan="3">
				<%-- 			<form:input size="40" path="disseminationId" /> --%>
				 <form:select path="disseminationId">
					<c:forEach var="staff" items="${staffMembers}">
						<form:option value="${staff.staffId }">${staff.name}
			    </form:option>
					</c:forEach>
					
					
				</form:select> <form:errors path="disseminationId" cssStyle="color: red;" />

			</td>
		</tr>
		<tr>
			<td>Dissemination</td>
			<td colspan="3"><form:input size="40" path="dissemination" /> <form:errors
					path="dissemination" cssStyle="color: red;" /></td>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${ isEdit eq true }">
					<c:set var="submitBtnText">
						Update
					</c:set>
					<spring:url value="/staff/history"
							var="backToDash" htmlEscape="true" />
				</c:when>
				<c:otherwise>
					<c:set var="submitBtnText">
						Submit
					</c:set>
					<spring:url value="/staff/dashboard"
							var="backToDash" htmlEscape="true" />
				</c:otherwise>
			</c:choose>
			<td><form:button type="submit" class="btn btn-primary">
					${ submitBtnText }
				</form:button></td>
			<td><a href="${ backToDash }"
				class="btn btn-primary"> Cancel</a></td>
		</tr>
	</table>
	<input type="hidden" name="edit" value="${ isEdit }" />
</form:form>
