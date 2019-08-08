<%--Form for update user--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container-fluid">
    <h3>Edit User</h3>
    <form name="updateForm" method="post" action="update">
        <input type="hidden" name="login" value="<c:out value="${user}"/>"/>
        <input type="hidden" name="command" value="update_user"/>
        First Name:<br/>
        <input type="text" name="firstName" value="" required="required" pattern="[A-Za-z]{1,20}"/>
        <br/>Last Name:<br/>
        <input type="text" name="lastName" value="" required="required" pattern="[A-Za-z]{1,20}"/>
        <br/>Password:<br/>
        <input type="text" name="password" value="" required="required" pattern="[A-Za-z0-9]{1,20}"/>
        <br/>Email:<br/>
        <input type="text" name="email" value="" required="required" pattern="[A-Za-z@0-9]{1,20}"/>
        <br/>
        <p style="color: #FF0000;">${errorLoginPassMessage}</p>
        <br/>
        ${wrongAction}
        <br/>
        ${nullPage}
        <br/>
        <input type="submit" value="Update"/>
    </form>
</div>
</body>
</html>
