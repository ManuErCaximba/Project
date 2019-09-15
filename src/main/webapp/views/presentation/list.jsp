<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
             pageEncoding="ISO-8859-1" %>

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
    <%@taglib prefix="display" uri="http://displaytag.sf.net" %>
    <%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

</head>
<body>
    <display:table name="presentations" id="row" requestURI="${presentation}"
                   pagesize="5" class="displaytag">

        <spring:message code="presentation.title" var="title"/>
        <display:column title="${title}">
            <jstl:out value="${row.title}"/>
        </display:column>

        <display:column>
            <acme:cancel url="presentation/show.do?presentationId=${row.id}" code="button.show"/>
        </display:column>

        <security:authorize access="hasRole('ADMIN')">
            <display:column>
                <jstl:if test="${now gt row.conference.cameraReadyDeadline && now lt row.conference.startDate}">
                    <acme:cancel url="presentation/administrator/edit.do?presentationId=${row.id}" code="button.edit"/>
                </jstl:if>
            </display:column>

            <display:column>
                <jstl:if test="${now gt row.conference.cameraReadyDeadline && now lt row.conference.startDate}">
                    <acme:cancel url="presentation/administrator/delete.do?presentationId=${row.id}" code="button.delete"/>
                </jstl:if>
            </display:column>
        </security:authorize>

    </display:table>
</body>
</html>
