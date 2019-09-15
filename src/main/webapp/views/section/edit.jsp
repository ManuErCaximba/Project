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

<security:authorize access="hasRole('ADMIN')">
    <form:form action="section/administrator/edit.do" modelAttribute="section">


        <form:input type ="hidden" path="id" readonly="true"/>

        <acme:textbox code="section.title" path="title" />

        <acme:textbox code="section.summary" path="summary" />

        <acme:textarea path="pictures" code="section.pictures"/>

        <acme:select path="tutorial" code="section.tutorial" items="${tutorials}" id="id" itemLabel="title"/>

        <acme:submit name="save" code="section.save"/>

        <jstl:if test="${section.id != 0}">
            <acme:cancel code="section.delete" url="section/administrator/delete.do?sectionId=${section.id}"/>
        </jstl:if>


        <input type="button" name="cancel"
               value="<spring:message code="button.goBack" />"
               onclick="javascript: window.history.back();" />

    </form:form>
</security:authorize>