<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.login"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<%--Navigation bar for user--%>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <span class="navbar-brand mb-0 h1"><fmt:message key="label.greeting"/> <c:out value="${user}"/> .</span>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item">
                    <a class="navbar-brand" href="controller?command=all_tracks&page=home"><fmt:message key="label.allMusic"/></a>
                </li>
                <li class="nav-item">
                    <a class="navbar-brand" href="controller?command=favorite&page=home"><fmt:message key="label.favoritesMusic"/></a>
                </li>
                <li class="nav-item active">
                    <a class="navbar-brand" href="update?command=to_update_user&user_id=${user}"><fmt:message key="label.updateUser"/></a>
                </li>
                <li class="nav-item active">
                    <a class="navbar-brand" href="controller?command=logout" onclick="logOutClick"><fmt:message key="label.buttonLogout"/></a>
                </li>
            </ul>
        </div>
    <%--    Form for change language--%>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="language">
            <input type="hidden" name="page" value="home">
            <select name="locale" onchange="submit()">
                <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
                <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>English</option>
                <option value="ru_BY" ${locale == 'ru_BY' ? 'selected' : ''}>Белорусский</option>
            </select>
        </form>
    </nav>
</header>
<%--All tracks--%>
<c:if test="${sessionScope.tracks ne null }">
    <div class="container-fluid">
        <table class="table table-light">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="label.trackName"/></th>
                <th scope="col"><fmt:message key="label.trackSinger"/></th>
                <th scope="col"><fmt:message key="label.trackGenre"/></th>
            </tr>
            </thead>
            <tbody>
    <c:forEach var="track" items="${sessionScope.tracks}">
    <tr>
        <td><c:out value="${track.name}"/></td>
        <td><c:out value="${track.singer.name}"/></td>
        <td><c:out value="${track.genre.name}"/></td>
        <td>
        <audio controls="controls">
            <source src="<c:out value="${track.path}"/>" type="audio/mp3" />
            Your browser does not support the audio tag.
        </audio>
        </td>
        <c:if test="${sessionScope.noOfPages ne null }">
        <td><a href="update?command=add_to_favorites&track_id=${track.trackId}"><fmt:message key="label.addFavorite"/></a></td>
        </c:if>
        <c:if test="${sessionScope.noOfPages eq null }">
        <td><a href="update?command=delete_favorite&track_id=${track.trackId}"><fmt:message key="label.deleteFavorite"/></a></td>
        </c:if>
    </tr>
    </c:forEach>
    <p style="color: #FF0000;">${errorMessage}</p>
            </tbody>
        </table>
<%--        Pagination for list of tracks--%>
    <c:if test="${sessionScope.noOfPages ne null }">
        <nav>
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item">
                        <a class="page-link" href="controller?page=home&command=all_tracks&currentPage=${currentPage-1}"><fmt:message key="label.previous"/></a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active">
                                <a class="page-link">${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="controller?page=home&command=all_tracks&currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <li class="page-item">
                        <a class="page-link" href="controller?page=home&command=all_tracks&currentPage=${currentPage+1}"><fmt:message key="label.next"/></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </c:if>
    </div>
</c:if>
</body>
<footer class="page-footer font-small bg-light pt-4">
    <div class="footer-copyright text-center py-3"><fmt:message key="label.footer"/>
        <a href="${pageContext.request.contextPath}"><fmt:message key="label.title"/></a>
    </div>
</footer>
</html>
