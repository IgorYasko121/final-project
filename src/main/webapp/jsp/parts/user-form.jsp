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
    <input type="hidden" name="command" value="sign_up"/>
    <fmt:message key="label.firstName"/><br/>
    <input type="text" name="firstName" value="" required="required" pattern="[A-Za-z]{1,20}"/>
    <br/><fmt:message key="label.lastName"/><br/>
    <input type="text" name="lastName" value="" required="required" pattern="[A-Za-z]{1,20}"/>
    <br/><fmt:message key="label.loginReg"/><br/>
    <input type="text" name="login" value="" required="required" pattern="[A-Za-z0-9]{1,20}"/>
    <br/><fmt:message key="label.passwordReg"/><br/>
    <input type="text" name="password" value="" required="required" pattern="[A-Za-z0-9]{1,20}"/>
    <br/><fmt:message key="label.email"/><br/>
    <input type="text" name="email" value="" required="required" pattern="[A-Za-z@0-9]{1,20}"/>
    <br/>
    <p style="color: #FF0000;">${errorLoginPassMessage}</p>
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input class="btn btn-primary" type="submit" value="<fmt:message key="label.buttonReg"/>"/>
</body>
</html>
