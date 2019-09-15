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
<display:table name="conferences" id="row" requestURI="${requestURI}"
               pagesize="5" class="displaytag">

    <spring:message code="conference.title" var="title"/>
    <display:column title="${title}">
        <jstl:out value="${row.title}"/>
    </display:column>

    <spring:message code="conference.venue" var="title"/>
    <display:column title="${title}">
        <jstl:out value="${row.venue}"/>
    </display:column>

    <spring:message code="conference.startDate" var="title"/>
    <display:column title="${title}">
        <jstl:out value="${row.startDate}"/>
    </display:column>

    <spring:message code="conference.fee" var="title"/>
    <display:column title="${title}">
        <jstl:out value="${row.fee}"/>
    </display:column>

    <jstl:if test="${lang == 'es'}">
        <spring:message code="conference.category" var="title"/>
        <display:column title="${title}" sortable="true">
            <jstl:out value="${row.category.nameEs}"/>
        </display:column>
    </jstl:if>

    <jstl:if test="${lang == 'en'}">
        <spring:message code="conference.category" var="title"/>
        <display:column title="${title}" sortable="true">
            <jstl:out value="${row.category.nameEn}"/>
        </display:column>
    </jstl:if>

    <spring:message code="conference.show" var="showTitle"/>
    <display:column title="${showTitle}">
        <a href="conference/showNotLogged.do?conferenceId=${row.id}">
            <spring:message code="conference.show"/>
        </a>
    </display:column>


</display:table>


</body>
</html>
