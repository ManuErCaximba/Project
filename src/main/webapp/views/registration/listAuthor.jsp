<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>

<security:authorize access="hasRole('AUTHOR')">
    <display:table name="conferences" id="row" requestURI="${requestURI}" pagesize="10" class="displaytag">

        <spring:message code="registration.conference" var="columTitle"/>
        <display:column title="${columTitle}">
            <jstl:out value="${row.title}"/>
        </display:column>

        <spring:message code="registration.moment" var="columTitle"/>
        <display:column title="${columTitle}">
            <jstl:forEach var="x" items="${row.registrations}">
                <jstl:if test="${x.author == author}">
                    <jstl:out value="${x.moment}"/>
                </jstl:if>
            </jstl:forEach>
        </display:column>


    </display:table>
</security:authorize>