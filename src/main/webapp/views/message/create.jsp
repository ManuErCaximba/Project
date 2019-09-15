<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>


<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER', 'SPONSOR')">
    <form:form action="message/administrator,author,reviewer,sponsor/create.do" modelAttribute="mesage">

        <form:input type ="hidden" path="id" readonly="true"/>
        <form:hidden path="recipient"/>

        <spring:message code="message.recipient" />:
        <input id="recipient" name="recipient"  placeholder="User account"/>
        <br />

        <acme:textbox code="message.subject" path="subject" />

        <form:label path="topic">
            <spring:message code="message.topic"/>
        </form:label>
        <form:select path="topic">
            <jstl:if test="${lang =='es'}">
                <form:options items="${topics}" itemValue="id" itemLabel="nameEs" />
            </jstl:if>
            <jstl:if test="${lang == 'en'}">
                <form:options items="${topics}" itemValue="id" itemLabel="nameEn"/>
            </jstl:if>
        </form:select>

        <acme:textarea path="body" code="message.body"/>
        <br/>

        <acme:submit name="save" code="message.send"/>

        <input type="button" name="cancel"
               value="<spring:message code="button.goBack" />"
               onclick="javascript: window.history.back();" />


    </form:form>
</security:authorize>