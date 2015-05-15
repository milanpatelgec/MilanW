<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Patients Record :: Monitoring Page</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/javascript/dijit/themes/tundra/tundra.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/blueprint/screen.css" />" type="text/css" media="screen, projection" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/blueprint/print.css" />" type="text/css" media="print" />
	<!--[if lt IE 8]>
	        <link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection" />
	<![endif]-->
	<link rel="stylesheet" href="<c:url value="/resources/styles/travel.css" />" type="text/css" media="screen" />
</head>
<body class="tundra">
<div id="page" class="container">
	<div id="header">
		<div id="topbar">
			<p>
			</p>
		</div>
		<div id="logo">
			<p>
				<a href="<c:url value="/" />">
					<img src="<c:url value="/resources/images/header.jpg"/>" alt="Spring Travel" />
				</a>
			</p>
		</div>
	</div>
	<div id="content">
		<div id="main" class="span-18 last">

			<!-- START PAGE CONTENT-->
			<h1>
				Records of Patients  
			</h1>
			<p><i>${version}</i></p>

			<table class="summary">
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Patient ID</th>
						<th>Date of Birth</th>
						<th>Arrived</th>
						<th>Discharged</th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="patient" items="${patientList}">
						<tr>
							<td>${patient.fname}</td>
							<td>${patient.lname}</td>
							<td><a href="editpatient/${patient.p_id}">${patient.p_id}</a></td>
							<td><fmt:formatDate value="${patient.dob}" type="both" pattern="MM/dd/yyyy" /></td>
							<td><fmt:formatDate value="${patient.arrived}" type="both" pattern="MM/dd/yyyy" /></td>
							<td><fmt:formatDate value="${patient.discharge}" type="both" pattern="MM/dd/yyyy" /></td>
							<td><a href="editpatient/${patient.p_id}">Edit Patient Data</a></td>
						</tr>
					</c:forEach>
					<c:if test="${empty patientList}">
						<tr>
							<td colspan="6">No patients found</td>
						</tr>
					</c:if> 
				</tbody>
			</table>

			<br/>
			<br/>
			
			<a href="newpatient">Add a patient</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="searchpatient">Search for patients</a>
			
			<br/>
			<br/>
			<br/>

			<!-- END PAGE CONTENT-->

		</div>
	</div>
	<hr />
	<div id="footer">
		<a href="http://www.springsource.org/spring-data/mongodb">
			<img src="<c:url value="/resources/images/powered-by-spring-data.png"/>" alt="Powered by Spring Data MongoDB" />
		</a>
	</div>
</div>
</body>
</html>