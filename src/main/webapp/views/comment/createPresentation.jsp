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

<form:form action="comment/createPresentation.do" modelAttribute="comment">


    <form:input type ="hidden" path="id" readonly="true"/>
    <input type="hidden" name="presentationId" value="${presentationId}" readonly>
    <br>

    <acme:textbox code="comment.title" path="title" />

    <acme:textbox code="comment.body" path="text" />

    <acme:submit name="savePresentation" code="comment.send"/>

    <input type="button" name="cancel"
           value="<spring:message code="button.goBack" />"
           onclick="javascript: window.history.back();" />

</form:form>