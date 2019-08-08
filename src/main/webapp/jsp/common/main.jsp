<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<div class="container-fluid">
    <div class="row">
        <div class="col-sm">
            <h3><fmt:message key="label.login"/></h3>
            <form name="loginForm" method="post" action="login">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group">
                    <label for="login"><fmt:message key="label.login"/>:</label>
                    <input class="form-control" id="login" type="text" name="login" value="" required="required"
                           pattern="^[а-яА-ЯёЁa-zA-Z0-9]{1,20}" placeholder="<fmt:message key="label.login"/>"/>
<%--                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
                </div>
                <div class="form-group">
                    <label for="pass"><fmt:message key="label.password"/>:</label>
                    <input class="form-control" id="pass" type="password" name="password" value="" required="required" pattern="[A-Za-z0-9]{1,20}"/>
<%--                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>--%>
                </div>
                <br/>
                <p style="color: #FF0000;">${errorLoginPassMessageAut}</p>
                <br/>
<%--                ${wrongAction}--%>
<%--                <br/>--%>
<%--                ${nullPage}--%>
<%--                <br/>--%>
                <input class="btn btn-primary" type="submit" value="<fmt:message key="label.buttonLogin"/>"/>
            </form>
            <h3><fmt:message key="label.regLabel"/></h3>
            <p><fmt:message key="label.register"/></p>
            <form name="registerForm" method="post" action="login">
                <c:import url="../parts/user-form.jsp"/>
            </form>
        </div>
        <div class="col-sm">

        </div>
    </div>
</div>

<%--        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>--%>
<%--        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>--%>
<%--        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>--%>
</body>

<footer class="page-footer font-small bg-light pt-4">
    <c:import url="../parts/footer.jsp"/>
</footer>
</html>
