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
<security:authorize access="hasRole('ADMIN')">

    <form:form id="assignForm" action="submission/administrator/assign.do" modelAttribute="assignForm">

        <form:hidden path="submissionId"/>

        <br>
        <fieldset>

            <legend><spring:message code="reviewers.data" /></legend>

            <form:label path="reviewers">
                <b><spring:message code="reviewers.data" /> *</b>
            </form:label>
            <br>
            <form:select path="reviewers">
                <form:options items="${assignForm.reviewers}"/>
            </form:select>
            <form:errors cssClass="error" path="reviewers"/>

        </fieldset>

        <br>
        <br>

        <acme:submit name="assign" code="button.assign"/>
        <acme:cancel code="button.cancel" url="/"/>

    </form:form>

</security:authorize>
</body>
</html>