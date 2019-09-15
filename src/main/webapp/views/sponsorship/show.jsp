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
<fieldset>
    <legend><b><spring:message code="sponsorship.data"/></b></legend>
    <b><spring:message code="sponsorship.banner"/>:</b>
    <br />
    <a><img src="${sponsorship.banner}" alt="Photo" height="300" width="600"/></a>
    <br>
    <b><spring:message code="sponsorship.target"/>:</b>
    <br />
    <a href="${sponsorship.targetURL}"><jstl:out value="${sponsorship.targetURL}"/></a>
</fieldset>
<fieldset>
    <legend><b><spring:message code="sponsorship.creditCard"/></b></legend>
    <p><acme:showtext code="sponsorship.holderName" value="${sponsorship.creditCard.holderName}" fieldset="false"/></p>
    <p><acme:showtext code="sponsorship.brandName" value="${sponsorship.creditCard.brandName}" fieldset="false"/></p>
    <p><acme:showtext code="sponsorship.number" value="${sponsorship.creditCard.number}" fieldset="false"/></p>
    <b><spring:message code="sponsorship.expirationDate"/>:</b>
    <br />
    <jstl:out value="${sponsorship.creditCard.expirationMonth}"/>/<jstl:out value="${sponsorship.creditCard.expirationYear}"/>
    <p><acme:showtext code="sponsorship.CVV" value="${sponsorship.creditCard.CVV}" fieldset="false"/></p>
</fieldset>
<br>
<acme:cancel code="button.cancel" url="/"/>
</body>
</html>
