<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> Patientshelf :: Add patient</title>
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/javascript/dijit/themes/tundra/tundra.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/blueprint/screen.css" />" type="text/css" media="screen, projection" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/blueprint/print.css" />" type="text/css" media="print" />
	<!--[if lt IE 8]>
	        <link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection" />
	<![endif]-->
	<link rel="stylesheet" href="<c:url value="/resources/styles/travel.css" />" type="text/css" media="screen" />
	<script type="text/javascript" src="<c:url value="/resources/javascript/dojo/dojo.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/javascript/spring/Spring.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/javascript/spring/Spring-Dojo.js" />"></script>
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
				Add a patient 
			</h1>

<div id="bookForm">
	<div class="span-12">
		<spring:hasBindErrors name="patient">
			<div class="error">
				<spring:bind path="patient.*">
					<c:forEach items="${status.errorMessages}" var="error">
						<c:out value="${error}"/><br/>
					</c:forEach>
				</spring:bind>
			</div>
		</spring:hasBindErrors>
		<form:form modelAttribute="patient" enctype="multipart/form-data">
			<fieldset>
				<legend>New Patient</legend>
		        <div>
					<div class="span-3">
						<label for="fname">First Name:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="fname"/></p>
					</div>
				</div>
					<div>
					<div class="span-3">
						<label for="lname">Last Name:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="lname"/></p>
					</div>
				</div>
				
				<div>
					<div class="span-3">
						<label for="p_id">Patient ID:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="p_id"/></p>
					</div>
				</div>
				
				<div>
					<div class="span-3">
						<label for="dob">Date of Birth:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="dob"/></p>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "dob",
								widgetType : "dijit.form.DateTextBox",
								widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
						</script>
					</div> 
				</div>
				
			
				<div>
					<div class="span-3">
						<label for="arrived">Arrival:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="arrived"/></p>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "arrived",
								widgetType : "dijit.form.DateTextBox",
								widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
						</script>
					</div> 
				</div>
				
				<div>
					<div class="span-3">
						<label for="discharge">Discharge Date:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="discharge"/></p>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "discharge",
								widgetType : "dijit.form.DateTextBox",
								widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
						</script>
					</div> 
				</div>
				
				<div>
					<div class="span-3">
						<label for="categories">Categories:</label>
					</div>
					<div class="span-7 last">
						<p><form:select path="categories" multiple="true" items="${categories}"/></p>
					</div>
				</div>
				
	
				<div>
					<div class="span-3">
						<label for="ident.dis">Note:</label>
					</div>
					<div class="span-7 last">
						<p><form:input path="ident.dis" style="width:190px; height:110px;" placeholder="Write a short note on patient"/></p>
					</div>
				</div>
			<div>
			
				    <div class="span-3">
						<label for="file">Upload Image:</label>
					</div>
					<div class="span-7 last">
						<p><input type="file" name="file" /></p>
					</div>
			</div>
			
			<div>
			
				    <div class="span-3">
						<label for="filename">Profile Name: </label>
					</div>
					<div class="span-7 last">
						<p><input type="text" name="filename" /></p>
					</div>
			</div>
			
				<div>
				
					<p>
					<button type="submit" id="proceed" name="_proceed">Add</button>
					<button type="submit" name="_cancel" >Cancel</button>
					</p>
				</div>
			</fieldset>
		</form:form>
	<!--	
		<form:form method="POST" action="uploadFile" enctype="multipart/form-data">
		Upload the Image: <input type="file" name="file"/><br /> 
		Name: <input type="text" name="name"/><br /> <br /> 
		<input type="submit" value="Upload"/> Press here to upload the file!
	</form:form>


				
		<div>
			<form:form method="POST" action="uploadFile" enctype="multipart/form-data">
				<div>
				  <p>
	        	Upload profile image: <input type="file" name="file"></input>
	        	  </p>
	        	</div>
	        	<div>
	        	  <p>
	        	Name: <input type="text" name="name"/><br /> 
	        	  </p>
	        	</div>
	        	<div>
	        	  <p>
	        	<input type="submit" value="Upload"/> Press here to upload the file!
	        	  </p>
	        	</div>
            </form:form>
		</div>-->
			
	</div>
</div>



			<br/>
			<br/>
			<br/>
			<br/>
			<br/>

			<!-- END PAGE CONTENT-->

		</div>
	</div>
	<hr />
	<div id="footer">
	</div>
</div>

</body>
</html>