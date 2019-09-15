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
<security:authorize access="hasRole('SPONSOR')">

    <display:table name="sponsorships" id="row" requestURI="${requestURI}"
                   pagesize="5" class="displaytag">

        <spring:message code="sponsorship.target" var="targetURL"/>
        <display:column title="${targetURL}">
            <a href="${row.targetURL}">Link</a>
        </display:column>

        <display:column>
            <acme:cancel url="sponsorship/sponsor/show.do?sponsorshipId=${row.id}" code="button.show"/>
        </display:column>

        <display:column>
            <acme:cancel url="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}" code="button.edit"/>
        </display:column>

        <display:column>
            <acme:cancel url="sponsorship/sponsor/delete.do?sponsorshipId=${row.id}" code="button.delete"/>
        </display:column>

    </display:table>

    <div>
        <acme:cancel url="sponsorship/sponsor/create.do" code="button.create"/>
    </div>

</security:authorize>
</body>
</html>
