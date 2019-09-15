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

    <form:form id="finder" action="finder/save.do" modelAttribute="finder">

        <form:hidden path="id" />

        <jstl:if test="${errorNumber == 1}">
            <form:errors cssClass="error" path="endDate" element="div" /> <div id="error.rangeDate" class="error"><spring:message code="error.rangeDate"/></div>
        </jstl:if>

        <br>
        <fieldset>

            <legend><spring:message code="finder.data" /></legend>

            <acme:textboxbs code="finder.keyword" path="keyword"/>
            <acme:textboxbs code="finder.maximumFee" path="maximumFee"/>

            <form:label path="categoryName">
                <b><spring:message code="finder.category" /></b>
            </form:label>
            <br>
            <form:select path="categoryName" items="${cNames}"/>
            <form:errors cssClass="error" path="categoryName"/>

            <br>
            <br>
            <b><spring:message code="finder.dateRange" /></b>
            <br>
            <spring:message code="finder.between"/>
            <form:input path="startDate" placeholder="dd/MM/yy HH:mm"/>
            <spring:message code="finder.and"/>
            <form:input path="endDate" placeholder="dd/MM/yy HH:mm"/>
            <form:errors cssClass="error" path="startDate"/>
            <form:errors cssClass="error" path="endDate"/>

        </fieldset>

        <br>
        <br>

        <acme:submit name="save" code="button.save"/>
        <acme:cancel code="button.cancel" url="/"/>

    </form:form>

</body>
</html>