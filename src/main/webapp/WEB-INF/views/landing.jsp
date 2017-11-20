<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<spring:url value="/css/simple.css" var="style" />
<link rel="STYLESHEET" type="text/css" href="${style}" />
<form:form modelAttribute="user" method="GET"
	action="${pageContext.request.contextPath}/home/login">

	<div class="panel panel-primary">
		<div class="panel-body">
			<div class="row">
				<div class="col-md-6 col-md-offset-4">

					<table style="text-align: center;">
						<tr>
							<td><h2>Welcome to the LAPS System!</h2></td>
						</tr>
						<tr>
							<td>Click Login to get STARTED!</td>
						</tr>
						<tr>
							<td><form:button name="submit" type="submit" value="s"
									class="btn btn-primary">
									Login
								</form:button></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</form:form>
</html>