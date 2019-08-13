<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
</head>
<body>
    <input type="hidden" name="command" value="sign_up"/>
    <div class="form-group">
        <label for="firstName"><fmt:message key="label.firstName"/></label>
        <input class="form-control" id="firstName" type="text" name="firstName" value="" required="required" pattern="[a-zA-Zа-яА-ЯёЁ]{4,20}"/>
        <small id="firstNameId" class="form-text text-muted"><fmt:message key="input.fieldinfo"/></small>
    </div>
    <div class="form-group">
        <label for="lastName"><fmt:message key="label.lastName"/></label>
        <input class="form-control" id="lastName" type="text" name="lastName" value="" required="required" pattern="[a-zA-Zа-яА-ЯёЁ]{4,20}"/>
        <small id="lastNameId" class="form-text text-muted"><fmt:message key="input.fieldinfo"/></small>
    </div>
    <div class="form-group">
        <label for="loginReg"><fmt:message key="label.loginReg"/></label>
        <input class="form-control" id="loginReg" type="text" name="login" value="" required="required" pattern="^[(\w)-]{4,20}"/>
        <small id="loginRegId" class="form-text text-muted"><fmt:message key="input.loginfo"/></small>
    </div>
    <div class="form-group">
        <label for="passwordReg"><fmt:message key="label.passwordReg"/></label>
        <input class="form-control" id="passwordReg" type="password" name="password" value="" required="required"
               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=\S+$).{6,12}$" />
        <small id="emailHelp" class="form-text text-muted"><fmt:message key="input.passinfo"/></small>
    </div>
    <div class="form-group">
        <label for="email"><fmt:message key="label.email"/></label>
        <input class="form-control" id="email" type="email" name="email" value="" required="required" pattern="^(?=.{2,30}$).{1,30}@.{2,30}$"/>
    </div>
    <br/>
    <p style="color: #FF0000;">${errorLoginPassMessage}</p>
    <br/>
    <input class="btn btn-primary" type="submit" value="<fmt:message key="label.buttonReg"/>"/>
</body>
</html>
