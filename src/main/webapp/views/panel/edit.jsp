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

    <spring:message code="actor.firstMessage" />

    <form:form id="panel" action="panel/administrator/save.do" modelAttribute="panel">

        <form:hidden path="id" />
        <form:hidden path="conference" />

        <jstl:if test="${errorNumber == 1}">
            <form:errors cssClass="error" path="startMoment" element="div" /> <div id="error.startMoment" class="error"><spring:message code="error.startMoment"/></div>
        </jstl:if>
        <jstl:if test="${errorNumber == 2}">
            <form:errors cssClass="error" path="duration" element="div" /> <div id="error.duration" class="error"><spring:message code="error.duration"/></div>
        </jstl:if>
        <jstl:if test="${errorNumber == 3}">
            <form:errors cssClass="error" path="attachments" element="div" /> <div id="error.URL" class="error"><spring:message code="error.URL"/></div>
        </jstl:if>

        <br>
        <fieldset>

            <legend><spring:message code="presentation.data" /></legend>

            <acme:textboxbsa code="presentation.title" path="title"/>

            <form:label path="startMoment">
                <b><spring:message code="presentation.startMoment" /> *</b>
            </form:label>
            <br>
            <form:input path="startMoment" placeholder="dd/MM/yyyy HH:mm"/>
            <form:errors cssClass="error" path="startMoment"/>
            <br>

            <form:label path="duration">
                <b><spring:message code="presentation.durationInMin" /> *</b>
            </form:label>
            <br>
            <form:input path="duration" placeholder="dd/MM/yyyy HH:mm"/>
            <form:errors cssClass="error" path="duration"/>

            <acme:textboxbsa code="presentation.room" path="room"/>
            <acme:textboxbsa code="presentation.summary" path="summary"/>

            <form:label path="attachments">
                <b><spring:message code="presentation.attachments" /></b>
                <br>
                <spring:message code="presentation.attachments2"/>
                <br>
            </form:label>
            <form:textarea path="attachments" />
            <form:errors cssClass="error" path="attachments" />

        </fieldset>

        <br>
        <fieldset>

            <legend><spring:message code="presentation.actors" /></legend>

            <acme:textboxbsa code="presentation.actors" path="speakerName"/>

        </fieldset>

        <br>

        <acme:submit name="save" code="button.save"/>
        <acme:cancel code="button.cancel" url="/"/>

    </form:form>

</security:authorize>
</body>
</html>

