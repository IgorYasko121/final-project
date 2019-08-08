<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
    <div class="container-fluid">
        <h5>Edit Track</h5><br/>
        <form action="upload" method="post" enctype="multipart/form-data">
            <br/><fmt:message key="label.trackName"/><br/>
            <input type="text" name="track_name" value="" required="required" pattern="[A-Za-z]{1,20}"/>
            <br/><fmt:message key="label.trackGenre"/><br/>
            <input type="text" name="genre" value="" required="required" pattern="[A-Za-z]{1,20}"/>
            <br/><fmt:message key="label.trackSinger"/><br/>
            <input type="text" name="singer" value="" required="required" pattern="[A-Za-z]{1,20}"/>
            <br/><fmt:message key="label.trackFile"/><br/>
            <input type="file" name="file"/>
            <input class="btn btn-secondary" type="submit" name="submit" value="<fmt:message key="label.buttonUpload"/>"/>
        </form>
    </div>
</body>
</html>
