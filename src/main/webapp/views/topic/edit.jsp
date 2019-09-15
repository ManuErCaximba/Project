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
    <form:form action="topic/administrator/edit.do" modelAttribute="topic">


        <form:input type ="hidden" path="id" readonly="true"/>

        <acme:textbox code="topic.nameEN" path="nameEn" />

        <acme:textbox code="topic.nameES" path="nameEs" />

        <acme:submit name="save" code="topic.save"/>

        <jstl:if test="${topic.id != 0}">
            <acme:cancel code="topic.delete" url="topic/administrator/delete.do?topicId=${topic.id}"/>
        </jstl:if>

        <acme:cancel code="topic.back" url="/topic/administrator/list.do"/>

    </form:form>
</security:authorize>