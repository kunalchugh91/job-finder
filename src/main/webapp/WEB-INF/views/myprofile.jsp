<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile</title>
</head>
<body>
<c:out value="Welcome ${user.name}"></c:out><br/><br/>
<a href="userhome.htm">Go to Home Page</a><br/><br/>

<h2> My Profile </h2><br/>

<form:form method="POST" modelAttribute="userProfile" enctype="multipart/form-data">
<h4> About me </h4><br/>
 Name: <form:input path="name"/><form:errors path="name"/><br/>
Email: <form:input path="email"/><form:errors path="email"/><br/>
Phone: <form:input path="phone"/><form:errors path="phone"/><br/>
Headline: <form:input path="headline"/><form:errors path="headline"/><br/>
Job category: <form:select path="ind" required="required">

<c:forEach var="cat" items="${categories}">
<form:option value="${cat.category}">${cat.category}</form:option>
</c:forEach>
</form:select><br/>

Country: <form:input path="country"/><form:errors path="country"/><br/>
Postal Code: <form:input path="postalCode"/><form:errors path="postalCode"/><br/>
LinkedIn URL: <form:input path="linkedInURL"/><form:errors path="linkedInURL"/><br/>
Facebook URL: <form:input path="facebookURL"/><form:errors path="facebookURL"/><br/>
Twitter URL: <form:input path="twitterURL"/><form:errors path="twitterURL"/><br/>

<br/>
<h4> My Resume </h4><br/>
Select Resume:<input type="file" name="photo"/><br/>


<input type="submit" value="Save" name="save"/>
</form:form>

</body>
</html>