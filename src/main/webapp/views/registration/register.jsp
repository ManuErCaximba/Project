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

<security:authorize access="hasRole('AUTHOR')">
    <form:form action="registration/author/create.do" modelAttribute="registration">
        <input type="hidden" name="conferenceId" value="${conferenceId}" readonly>
        <br>

        <form:input type ="hidden" path="id" readonly="true"/>

        <acme:textbox code="registration.creditCard.holderName" path="creditCard.holderName" />

        <form:label path="creditCard.brandName">
            <spring:message code="registration.creditCard.brandName" />
        </form:label>

        <form:select path="creditCard.brandName" items="${brandList}"/>
        <form:errors cssClass="error" path="creditCard.brandName"/>

        <acme:textbox code="registration.creditCard.number" path="creditCard.number" />

        <acme:textbox code="registration.creditCard.expirationMonth" path="creditCard.expirationMonth"/>

        <acme:textbox code="registration.creditCard.expirationYear" path="creditCard.expirationYear" />

        <acme:textbox code="registration.creditCard.CVV" path="creditCard.CVV" />

        <acme:submit name="save" code="registration.save"/>
    </form:form>
</security:authorize>