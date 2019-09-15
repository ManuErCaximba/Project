<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
    <jstl:when test="${language == 'es'}">
        <p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" />
            <jstl:out value="${configuration.welcomeEs}"/></p>
    </jstl:when>
    <jstl:otherwise>
        <p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" />
            <jstl:out value="${configuration.welcomeEn}"/></p>
    </jstl:otherwise>
</jstl:choose>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p>

<security:authorize access="isAnonymous()">
    <input type="button" name="registerAuthor"
           value="<spring:message code="welcome.register.author" />"
           onclick="javascript: relativeRedir('author/create.do');" />&nbsp;

    <input type="button" name="registerSponsor"
           value="<spring:message code="welcome.register.sponsor" />"
           onclick="javascript: relativeRedir('sponsor/create.do');" />

    <input type="button" name="registerReviewer"
           value="<spring:message code="welcome.register.reviewer" />"
           onclick="javascript: relativeRedir('reviewer/register.do');" />

</security:authorize>