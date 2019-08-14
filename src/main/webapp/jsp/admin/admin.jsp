<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<%--Administrator page--%>
<html>
<head>
    <title><fmt:message key="label.title"/></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <span class="navbar-brand mb-0 h1"><fmt:message key="label.greeting"/> <c:out value="${user}"/> .</span>
        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="navbar-brand" href="controller?command=logout" onclick="logOutClick"><fmt:message key="label.buttonLogout"/></a>
                </li>
            </ul>
        </div>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="language">
                <input type="hidden" name="page" value="admin">
                <select name="locale" onchange="submit()">
                    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>English</option>
                    <option value="ru_BY" ${locale == 'ru_BY' ? 'selected' : ''}>Белорусский</option>
                </select>
            </form>
    </nav>
</header>
<hr/>
<%--Get all users command--%>
    <div class="container-fluid bg-light">
    <form method="post" action="controller">
        <input type="hidden" name="command" value="all_users" />
        <input class="btn btn-primary" type="submit" value="<fmt:message key="label.buttonGetUsers"/>" />
    </form>
        <c:if test="${requestScope.users ne null }">
    <h5>Все пользователи</h5><br />
        <table class="table table-dark">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="label.id"/></th>
                <th scope="col"><fmt:message key="label.firstName"/></th>
                <th scope="col"><fmt:message key="label.lastName"/></th>
                <th scope="col"><fmt:message key="label.login"/></th>
                <th scope="col"><fmt:message key="label.role"/></th>
                <th scope="col"><fmt:message key="label.email"/></th>
                <th scope="col"><fmt:message key="label.createdAt"/></th>
            </tr>
            </thead>
            <tbody>
        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><c:out value="${user.userId}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.role}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.createdAt}"/></td>
                <td><a href="update?command=to_update_role&user_id=${user.userId}"><fmt:message key="label.updateRole"/></a></td>
                <td><a href="delete?command=delete_user&user_id=${user.userId}"><fmt:message key="label.delete"/></a></td>
            </tr>
        </c:forEach>
            </tbody>
        </table>
            <nav>
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item">
                            <a class="page-link" href="controller?command=all_users&currentPage=${currentPage-1}">Previous</a>
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
                                    <a class="page-link" href="controller?command=all_users&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item">
                            <a class="page-link" href="controller?command=all_users&currentPage=${currentPage+1}">Next</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>
    </div>
    <hr/>
<%--Get all tracks command--%>
    <div class="container-fluid bg-light">
    <form method="post" action="controller">
        <input type="hidden" name="command" value="all_tracks" />
        <input class="btn btn-danger" type="submit" value="<fmt:message key="label.buttonGetTracks"/>"/>
    </form>
        <c:if test="${requestScope.tracks ne null }">
    <h5>All Tracks</h5><br />
        <table class="table table-dark">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="label.id"/></th>
                <th scope="col"><fmt:message key="label.trackName"/></th>
                <th scope="col"><fmt:message key="label.trackSinger"/></th>
                <th scope="col"><fmt:message key="label.trackGenre"/></th>
                <th scope="col"><fmt:message key="label.trackPath"/></th>
            </tr>
            </thead>
            <tbody>
    <c:forEach var="track" items="${requestScope.tracks}">
        <tr>
            <td><c:out value="${track.trackId}"/></td>
            <td><c:out value="${track.name}"/></td>
            <td><c:out value="${track.singer.name}"/></td>
            <td><c:out value="${track.genre.name}"/></td>
            <td><c:out value="${track.path}"/></td>
            <td>
                <audio controls="controls">
                <source src="<c:out value="${track.path}"/>" type="audio/mp3" />
                Your browser does not support the audio tag.
                </audio>
            </td>
            <td><a href="update?command=to_update_track&track_id=${track.trackId}"><fmt:message key="label.update"/></a></td>
            <td><a href="delete?command=delete_track&track_id=${track.trackId}"><fmt:message key="label.delete"/></a></td>
        </tr>
    </c:forEach>
            </tbody>
        </table>
<%--        Pagination for list of tracks--%>
            <nav>
                <ul class="pagination">
                    <c:if test="${currentPage != 1}">
                        <li class="page-item">
                            <a class="page-link" href="controller?command=all_tracks&currentPage=${currentPage-1}"><fmt:message key="label.previous"/></a>
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
                                    <a class="page-link" href="controller?command=all_tracks&currentPage=${i}">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${currentPage lt noOfPages}">
                        <li class="page-item">
                            <a class="page-link" href="controller?command=all_tracks&currentPage=${currentPage+1}"><fmt:message key="label.next"/></a>
                        </li>
                    </c:if>
                </ul>
            </nav>
    </c:if>
    </div>
<%--Form to add track--%>
    <div class="container-fluid bg-light">
        <h5><fmt:message key="label.buttonAddTrack"/></h5><br/>
        <form action="upload" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="trackName"><fmt:message key="label.trackName"/></label>
                <input class="form-control" id="trackName" type="text" name="track_name" value="" required="required" pattern="^[a-zA-Z0-9 -]{4,20}$"/>
                <small class="form-text text-muted"><fmt:message key="input.fieldNameInfo"/></small>
            </div>
            <div class="form-group">
                <label for="trackGenre"><fmt:message key="label.trackGenre"/></label>
                <input class="form-control" id="trackGenre" type="text" name="genre" value="" required="required" pattern="^[A-Z]{3,20}"/>
                <small class="form-text text-muted"><fmt:message key="input.genreFieldInfo"/></small>
            </div>
            <div class="form-group">
                <label for="trackSinger"><fmt:message key="label.trackSinger"/></label>
                <input class="form-control" id="trackSinger" type="text" name="singer" value="" required="required" pattern="^[a-zA-Z0-9 -]{4,20}$"/>
                <small class="form-text text-muted"><fmt:message key="input.fieldNameInfo"/></small>
            </div>
            <div class="form-group">
                <label for="trackFile"><fmt:message key="label.trackFile"/></label>
                <input class="form-control" id="trackFile" type="file" name="file"/>
            </div>
            <p style="color: #FF0000;">${errorInputMessage}</p>
            <br/>
            <input class="btn btn-secondary" type="submit" name="submit" value="<fmt:message key="label.buttonUpload"/>"/>
        </form>
    </div>
</body>
<%--Footer of the page--%>
<footer class="page-footer font-small bg-light pt-4">
    <div class="footer-copyright text-center py-3"><fmt:message key="label.footer"/>
        <a href="${pageContext.request.contextPath}"><fmt:message key="label.title"/></a>
    </div>
</footer>
</html>
