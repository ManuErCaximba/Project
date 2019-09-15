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
<security:authorize access="hasRole('REVIEWER')">

    <spring:message code="actor.firstMessage" />

    <form:form id="report" action="report/reviewer/save.do" modelAttribute="report">

        <form:hidden path="id" />
        <form:hidden path="submission"/>

        <br>
        <fieldset>

            <legend><spring:message code="report.data" /></legend>

            <acme:textboxbsa code="report.originalityScore" path="originalityScore"/>
            <acme:textboxbsa code="report.qualityScore" path="qualityScore"/>
            <acme:textboxbsa code="report.readabilityScore" path="readabilityScore"/>

            <form:label path="decision">
                <b><spring:message code="report.decision" /> *</b>
            </form:label>
            <br>
            <form:select path="decision" items="${states}"/>
            <form:errors cssClass="error" path="decision"/>

            <acme:textboxbs code="report.comment" path="comment"/>

        </fieldset>

        <br>
        <br>

        <acme:submit name="save" code="button.save"/>
        <acme:cancel code="button.cancel" url="/"/>

    </form:form>

</security:authorize>
</body>
</html>
