<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Job applications</title>
</head>
<body>
<c:out value="Welcome ${user.name}"></c:out><br/><br/>
<a href="rechome.htm">Go to Home Page</a><br/><br/>


<script type="text/javascript">
function myfunc(decision, userid, jobid ){
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var e = document.getElementById(userid);
			e.innerHTML = xmlhttp.responseText;;
		}
	}
	xmlhttp.open("POST", "updatecandidates.htm?t="+Math.random(), true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("user="+userid+"&dec="+decision+"&jobID="+jobid);
}
</script>
<h3>
<c:out value="JobID: ${jobID}  Job Title: ${jobTitle} "></c:out>
</h3>
<br/><br/>

<table border="1">
<c:forEach var="u" items="${userapps}">

<tr id="${u.userid}">
<td>${u.name}</td>
<td><input type="button" value="Accept" onclick="javascript:myfunc(1, ${u.userid}, ${jobID});"/></td>
<td><input type="button" value="Reject" onclick="javascript:myfunc(2, ${u.userid}, ${jobID});"/></td>
<td><a href="resume.pdf?userid=${u.userid}&jobID=${jobID}">View Resume</a></td>
<td>${u.decision}</td>
</tr>
</c:forEach>
</table>

<form action="closejob.htm" method="post">
<input type="hidden" value="${jobID}" name="jobID"/>
<input type="submit" value="Close job"/>
</form>

</body>
</html>