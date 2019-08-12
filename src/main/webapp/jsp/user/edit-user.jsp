<%--Form for update user--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.title"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <header>
        <nav class="navbar navbar-light bg-light">
            <a class="navbar-brand" href="controller?command=guest"><fmt:message key="label.guestPage"/></a>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="language">
                <input type="hidden" name="page" value="main">
                <select name="locale" onchange="submit()">
                    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>English</option>
                    <option value="ru_BY" ${locale == 'ru_BY' ? 'selected' : ''}>Белорусский</option>
                </select>
            </form>
        </nav>
    </header>
    <h5><fmt:message key="label.edit"/></h5><br/>
    <form name="updateForm" method="post" action="update">
        <input type="hidden" name="login" value="<c:out value="${user}"/>"/>
        <input type="hidden" name="command" value="update_user"/>
        <div class="form-group">
            <label for="firstName"><fmt:message key="label.firstName"/></label>
            <input class="form-control" id="firstName" type="text" name="firstName" value="" required="required" pattern="[a-zA-Zа-яА-ЯёЁ]{2,20}"/>
        </div>
        <div class="form-group">
            <label for="lastName"><fmt:message key="label.lastName"/></label>
            <input class="form-control" id="lastName" type="text" name="lastName" value="" required="required" pattern="[a-zA-Zа-яА-ЯёЁ]{2,20}"/>
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
        <p style="color: #FF0000;">${errorLoginPassMessage}</p>
        <br/>
        <input type="submit" value="Update"/>
    </form>
</div>
</body>
</html>
