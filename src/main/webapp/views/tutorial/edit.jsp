<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">
    <form:form action="tutorial/administrator/edit.do" modelAttribute="tutorial">

        <form:input type ="hidden" path="id" readonly="true"/>

        <acme:textbox code="tutorial.title" path="title" />

        <acme:textbox code="tutorial.startMoment" path="startMoment" />

        <acme:textbox code="tutorial.duration" path="duration" />

        <acme:textbox code="tutorial.room" path="room" />

        <acme:textbox code="tutorial.summary" path="summary"  />

        <acme:textarea path="attachments" code="tutorial.attachments"/>

        <acme:select path="conference" code="tutorial.conference" items="${conferences}" id="id" itemLabel="title"/>

        <acme:textbox code="tutorial.speakerName" path="speakerName"  />

        <acme:submit name="save" code="tutorial.save"/>

        <jstl:if test="${section.id != 0}">
            <acme:cancel code="tutorial.delete" url="tutorial/administrator/delete.do?tutorialId=${tutorial.id}"/>
        </jstl:if>

        <input type="button" name="cancel"
               value="<spring:message code="button.goBack" />"
               onclick="javascript: window.history.back();" />


    </form:form>
</security:authorize>