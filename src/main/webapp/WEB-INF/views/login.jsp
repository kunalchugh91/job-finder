<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>

<h2> Welcome to Job Finder</h2><br/><br/>

<div = "row" >
<div class = "col-md-6">
<form:form action="login.htm" method="post" modelAttribute="credential">
${message}<br/>
Username: <form:input path="username"/><form:errors path="username"></form:errors><br/>
Password: <form:password path="password" /><form:errors path="password"></form:errors><br/>
<input type="submit" value="Login"/>
</form:form>
</div>
<br/><br/><br/><br/>
<div class = "col-md-6">
<form:form action="logup.htm" method="post" modelAttribute="credential">
${message2}<br/>
Username: <form:input path="username"/><form:errors path="username"></form:errors><br/>
Password: <form:password path="password" /><form:errors path="password"></form:errors><br/>
<form:radiobutton path="userType" value="JOBSEEKER"/> I am a Job Seeker
<form:radiobutton path="userType" value="RECRUITER"/> I am a Recruiter <br/>
<form:errors path="userType" ></form:errors>
<input type="submit" value="Sign Up"/>
</form:form>
</div>
</div>
</body>
</html>