<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <br/>Track Name:<br/>
    <input type="text" name="firstName" value="" required="required" pattern="[A-Za-z]{1,20}"/>
    <br/>Genre:<br/>
    <input type="text" name="lastName" value="" required="required" pattern="[A-Za-z]{1,20}"/>
    <br/>Input File<br/>
    <input type="file" name="file"/>
    <input type="submit" name="submit" value="upload"/>
</body>
</html>
