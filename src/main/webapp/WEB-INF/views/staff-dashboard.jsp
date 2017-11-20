<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- <%@ page session="false" %> --%>

<link
	href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'
	rel='stylesheet'
	integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u'
	crossorigin='anonymous'>
<link href="../css/leonard-styles.css" rel="STYLESHEET" type="text/css">

<div class='container'>
	<div class='col-xs-8'>
		<div class='row margin-10'>
			<a href="${pageContext.request.contextPath}/staff/leave/create"
				class='btn btn-success btn-lg'>Make new request</a>
		</div>
		<div class='row margin-10'>
			<div class="panel panel-primary">
				<div class="panel-heading float-wrapper">
					<span class="h4">${ numToShow } of the most recent requests</span>
					<!-- 					Recent requests -->

					<span class="float-vertical-align"><a
						class="btn btn-info"
						href="${pageContext.request.contextPath}/staff/history">
						View all your applications (${totalLeavesNum})</a></span>
				</div>
				<!-- 					<div class="panel-body text-center float-wrapper"> -->
				<!-- 					</div> -->
				<table class='table table-striped'>
					<thead>
						<tr>
							<th>LeaveID</th>
							<th>Time</th>
							<th>Reason</th>
							<th width=20px>Status</th>
							<th width=20px></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ leaves }" var="leave">

							<%-- 							<c:set var="start-month" value=""/> --%>
							<c:choose>
								<c:when
									test="${ leave.startDate.month.equals(leave.endDate.month)
								&& leave.startDate.year.equals(leave.endDate.year) }">
									<%-- 									<c:set var="fmtStartDate" value="${ leaves.startDate.day }" /> --%>
									<c:set var="fmtStartDate">
										<fmt:formatDate value="${ leave.startDate }" pattern="dd" />
									</c:set>
								</c:when>
								<c:when test="${ leave.startDate.year == leave.endDate.year }">
									<c:set var="fmtStartDate">
										<fmt:formatDate value="${ leave.startDate }" pattern="dd MMM" />
									</c:set>
								</c:when>
								<c:otherwise>
									<c:set var="fmtStartDate">
										<fmt:formatDate value="${ leave.startDate }"
											pattern="dd MMM yyyy" />
									</c:set>
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test='${ leave.status == "APPROVED" }'>
									<c:set var="statusStyle" value="success" />
								</c:when>
								<c:when test='${ leave.status == "REJECTED" }'>
									<c:set var="statusStyle" value="danger" />
								</c:when>
								<c:otherwise>
									<c:set var="statusStyle" value='${ "warning" }' />
								</c:otherwise>
							</c:choose>

							<c:set var="fmtEndDate">
								<fmt:formatDate value="${ leave.endDate }" pattern="dd MMM yyyy" />
							</c:set>

							<tr class="${ statusStyle }">
								<td>${ leave.leaveId }</td>
								<td><span class=''>${ fmtStartDate }</span> - <span
									class=''>${ fmtEndDate }</span></td>
								<td>${ leave.reason }</td>
								<td class="<c:out value = "${statusStyle}"/>">${ leave.status.toString() }</td>
								<td><a
									href='${pageContext.request.contextPath}/staff/history/details/${leave.leaveId}.html'
									class="btn btn-info btn-xs">Details...</a>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- <div class="panel-footer">
						<span>Put link here for more</span>
						<div class="float-vertical-align">
							<span><a class="btn btn-primary"
								href="#">View all</a></span>
						</div>
					</div> -->

			</div>
		</div>

	</div>

	<div class='col-xs-3'>
		<div class='row'>
			<div class='panel-group margin-10'>
				<div class="panel panel-info">
					<div class="panel-heading">Compensation entitlement</div>
					<div class="panel-body">
						<div class='margin-10'>
							Unclaimed hours: <b>${ compHoursUnclaimed }</b> h
							<br />
							Available: <b>${ availableCompLeaveDays }</b> days
							<br />
							Pending: <b>${ pendingCompLeaveDays }</b> days
							<br />
							<br />
							<a href="${pageContext.request.contextPath}/staff/comp/loghours.html" class="btn btn-primary btn-sm">Log overtime</a>
							<br />
							<a href="${pageContext.request.contextPath}/staff/comp/history.html" class="btn btn-primary btn-sm">View past overtime</a>
						</div>
					</div>
				</div>
				<div class="panel panel-success">
					<div class="panel-heading">Annual leave entitlement</div>
					<div class="panel-body">
						<div class='margin-10'>
							Available: <b>${ staffMember.aLeave - annualLeaveDays }</b> days
							<br />
							Pending: <b>${ annualLeavePending }</b> days
						</div>
					</div>
				</div>
				<div class="panel panel-warning">
					<div class="panel-heading">Medical leave</div>
					<div class="panel-body">
						<div class='margin-10'>
							Available: <b>${ staffMember.mLeave - medicalLeaveDays }</b> days
							<br />
							Pending: <b>${ medicalLeavePending }</b> days
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>