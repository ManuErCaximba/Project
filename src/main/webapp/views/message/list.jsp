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


<security:authorize access="hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER', 'SPONSOR')">
    <display:table name="messages" id="row" requestURI="${requestURI}" pagesize="10" class="displaytag">

        <spring:message code="message.moment" var="title"/>
        <display:column title="${title}" sortable="true">
            <jstl:out value="${row.moment}"/>
        </display:column>

        <spring:message code="message.sender" var="title"/>
        <display:column title="${title}" sortable="true">
            <jstl:out value="${row.sender.userAccount.username}"/>
        </display:column>

        <spring:message code="message.recipient" var="title"/>
        <display:column title="${title}" sortable="true">
            <jstl:out value="${row.recipient.userAccount.username}"/>
        </display:column>

        <spring:message code="message.topic" var="columnTitle"/>
        <jstl:if test="${lang=='es' }">
            <display:column title="${columnTitle}" sortable="true">
                <jstl:out value="${row.topic.nameEs}"/>
            </display:column>
        </jstl:if>

        <jstl:if test="${lang=='en' }">
            <display:column title="${columnTitle}" sortable="true">
                <jstl:out value="${row.topic.nameEn}"/>
            </display:column>
        </jstl:if>

        <spring:message code="message.view" var="columnTitle"/>
        <display:column title="${columnTitle}">
            <a href="message/administrator,author,reviewer,sponsor/show.do?messageId=${row.id}">
                <spring:message code="category.view"/>
            </a>
        </display:column>


        <spring:message code="message.delete" var="columnTitle"/>
        <display:column title="${columnTitle}">
                <acme:cancel code="message.delete" url="message/administrator,author,reviewer,sponsor/delete.do?messageId=${row.id}"/>
        </display:column>
        </display:table>

    <input type="button" value="<spring:message code="message.create" />"
           onclick="javascript: relativeRedir('message/administrator,author,reviewer,sponsor/create.do');" />
</security:authorize>