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
<security:authorize access="hasRole('SPONSOR')">

    <spring:message code="actor.firstMessage" />

    <form:form id="sponsorshipForm" action="sponsorship/sponsor/save.do" modelAttribute="sponsorshipForm">

        <form:hidden path="id" />

        <jstl:if test="${errorNumber == 1}">
            <form:errors cssClass="error" path="expirationYear" element="div" /> <div id="error.expirationYear" class="error"><spring:message code="error.expirationYear"/></div>
        </jstl:if>
        <jstl:if test="${errorNumber == 2}">
            <form:errors cssClass="error" path="expirationMonth" element="div" /> <div id="error.expirationMonth" class="error"><spring:message code="error.expirationMonth"/></div>
        </jstl:if>

        <br>
        <fieldset>

            <legend><spring:message code="sponsorship.data" /></legend>

            <acme:textboxbsa code="sponsorship.banner" path="banner"/>
            <acme:textboxbsa code="sponsorship.target" path="targetURL"/>

        </fieldset>

        <br>
        <fieldset>

            <legend><spring:message code="sponsorship.creditCard" /></legend>

            <acme:textboxbsa code="sponsorship.holderName" path="holderName"/>

            <form:label path="brandName">
                <b><spring:message code="sponsorship.brandName" /> *</b>
            </form:label>
            <br>
            <form:select path="brandName" items="${configuration.creditCardMakes}"/>
            <form:errors cssClass="error" path="brandName"/>

            <acme:textboxbsa code="sponsorship.number" path="number"/>
            <acme:textboxbsa code="sponsorship.expirationMonth" path="expirationMonth"/>

            <form:label path="expirationYear">
                <b><spring:message code="sponsorship.expirationYear" /> *</b>
            </form:label>
            <br>
            <form:input path="expirationYear" placeholder="YYYY"/>
            <form:errors cssClass="error" path="expirationYear"/>

            <acme:textboxbsa code="sponsorship.CVV" path="CVV"/>

        </fieldset>

        <br>
        <br>

        <acme:submit name="save" code="button.save"/>
        <acme:cancel code="button.cancel" url="/"/>

    </form:form>

</security:authorize>
</body>
</html>
