<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    <%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
    <%@taglib prefix="display" uri="http://displaytag.sf.net"%>
    <%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

</head>
<body>
<security:authorize access="hasRole('AUTHOR')">

    <spring:message code="actor.firstMessage" />

    <form:form id="submission" action="submission/author/cameraReady.do" modelAttribute="submission">

        <form:hidden path="id" />

        <br>
        <fieldset>

            <legend><spring:message code="submission.data" /></legend>

            <form:label path="cameraReadyPaper">
                <b><spring:message code="submission.crPaper" /> *</b>
            </form:label>
            <br>
            <form:select path="cameraReadyPaper" items="${papers}"/>
            <form:errors cssClass="error" path="cameraReadyPaper"/>

        </fieldset>

        <br>
        <br>

        <acme:submit name="cameraReady" code="button.cameraReady"/>
        <acme:cancel code="button.cancel" url="/"/>

    </form:form>

</security:authorize>
</body>
</html>