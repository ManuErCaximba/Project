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

        <spring:message code="conference.fee" var="fee"/>
        <display:column title="${fee}">
            <jstl:out value="${row.fee}"/>
        </display:column>

        <spring:message code="conference.startDate" var="startDate"/>
        <display:column title="${startDate}">
            <jstl:out value="${row.startDate}"/>
        </display:column>

        <display:column>
            <acme:cancel url="conference/administrator/show.do?conferenceId=${row.id}" code="button.show"/>
        </display:column>

    </display:table>

</body>
</html>
