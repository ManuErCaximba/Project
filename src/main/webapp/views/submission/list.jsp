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

    <display:table name="submissions" id="row" requestURI="${requestURI}"
                   pagesize="5" class="displaytag">

        <spring:message code="submission.ticker" var="ticker"/>
        <display:column title="${ticker}">
            <jstl:out value="${row.ticker}"/>
        </display:column>

        <spring:message code="submission.status" var="status"/>
        <display:column title="${status}" sortable="true">
            <jstl:out value="${row.status}"/>
        </display:column>

        <spring:message code="submission.conference" var="conference"/>
        <display:column title="${conference}">
            <jstl:out value="${row.conference.title}"/>
        </display:column>

        <display:column>
            <acme:cancel url="submission/show.do?submissionId=${row.id}" code="button.show"/>
        </display:column>

        <security:authorize access="hasRole('AUTHOR')">

            <display:column>
                <jstl:if test="${now gt row.conference.submissionDeadline &&
                 now lt row.conference.cameraReadyDeadline && row.status == 'ACCEPTED' && row.isCameraReady == false}">
                    <acme:cancel url="submission/author/cameraReady.do?submissionId=${row.id}" code="button.cameraReady"/>
                </jstl:if>
            </display:column>

            <display:column>
                <jstl:if test="${row.status == 'ACCEPTED' || row.status == 'REJECTED'}">
                    <acme:cancel url="report/author/list.do?submissionId=${row.id}" code="button.showReport"/>
                </jstl:if>
            </display:column>

        </security:authorize>

        <security:authorize access="hasRole('ADMIN')">

            <display:column>
                <jstl:if test="${now lt row.conference.submissionDeadline && row.isAssigned == false}">
                    <acme:cancel url="submission/administrator/autoassign.do?submissionId=${row.id}" code="button.autoassign"/>
                </jstl:if>
            </display:column>

            <display:column>
                <jstl:if test="${now lt row.conference.submissionDeadline && row.isAssigned == false}">
                    <acme:cancel url="submission/administrator/assign.do?submissionId=${row.id}" code="button.assign"/>
                </jstl:if>
            </display:column>

        </security:authorize>

    </display:table>

    <div>
        <acme:cancel url="/" code="button.goBack"/>
    </div>

</body>
</html>
