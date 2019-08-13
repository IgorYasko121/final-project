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
<header></header>
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
<div class="container-fluid">
    <div class="row">
        <div class="col-sm">
            <h4><fmt:message key="label.enter"/></h4>
            <form name="loginForm" method="post" action="login">
                <input type="hidden" name="command" value="login"/>
                <div class="form-group">
                    <label for="login"><fmt:message key="label.login"/>:</label>
                    <input class="form-control" id="login" type="text" name="login" value="" required="required"
                           pattern="^[(\w)-]{4,20}" placeholder="<fmt:message key="label.login"/>"/>
                </div>
                <div class="form-group">
                    <label for="pass"><fmt:message key="label.password"/>:</label>
                    <input class="form-control" id="pass" type="password" name="password" value="" required="required"
                           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=\S+$).{6,12}$" placeholder="<fmt:message key="label.password"/>" />
                </div>
                <br/>
                <p style="color: #FF0000;">${errorLoginPassMessageAut}</p>
                <br/>
                <input class="btn btn-primary" type="submit" value="<fmt:message key="label.buttonLogin"/>"/>
            </form>
            <h4><fmt:message key="label.regLabel"/></h4>
            <h5><fmt:message key="label.register"/></h5>
            <form name="registerForm" method="post" action="login">
                <c:import url="../parts/user-form.jsp"/>
            </form>
        </div>
        <div class="col-sm">
<%--For new feature--%>
        </div>
        <div class="col-sm">
            <%--For new feature--%>
        </div>
    </div>
</div>
</body>
<%--Footer of the page--%>
<footer class="page-footer font-small bg-light pt-4">
    <div class="footer-copyright text-center py-3"><fmt:message key="label.footer"/>
        <a href="${pageContext.request.contextPath}"><fmt:message key="label.title"/></a>
    </div>
</footer>
</html>
