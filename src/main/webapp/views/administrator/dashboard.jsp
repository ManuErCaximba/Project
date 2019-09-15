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
        <p><acme:showtext code="dashboard.Q11" value="${Q11}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q12" value="${Q12}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q13" value="${Q13}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q14" value="${Q14}" fieldset="true"/></p>

        <p><acme:showtext code="dashboard.Q21" value="${Q21}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q22" value="${Q22}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q23" value="${Q23}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q24" value="${Q24}" fieldset="true"/></p>

        <p><acme:showtext code="dashboard.Q31" value="${Q31}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q32" value="${Q32}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q33" value="${Q33}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q34" value="${Q34}" fieldset="true"/></p>

        <fieldset>
            <legend><b><spring:message code="dashboard.Q41"/></b></legend>
            <jstl:out value="${Q41}"/> <spring:message code="dashboard.days"/>
        </fieldset>
        <fieldset>
            <legend><b><spring:message code="dashboard.Q42"/></b></legend>
            <jstl:out value="${Q42}"/> <spring:message code="dashboard.days"/>
        </fieldset>
        <fieldset>
            <legend><b><spring:message code="dashboard.Q43"/></b></legend>
            <jstl:out value="${Q43}"/> <spring:message code="dashboard.days"/>
        </fieldset>
        <fieldset>
            <legend><b><spring:message code="dashboard.Q44"/></b></legend>
            <jstl:out value="${Q44}"/> <spring:message code="dashboard.days"/>
        </fieldset>

        <p><acme:showtext code="dashboard.Q51" value="${Q51}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q52" value="${Q52}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q53" value="${Q53}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q54" value="${Q54}" fieldset="true"/></p>

        <p><acme:showtext code="dashboard.Q61" value="${Q61}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q62" value="${Q62}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q63" value="${Q63}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q64" value="${Q64}" fieldset="true"/></p>

        <p><acme:showtext code="dashboard.Q71" value="${Q71}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q72" value="${Q72}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q73" value="${Q73}" fieldset="true"/></p>
        <p><acme:showtext code="dashboard.Q74" value="${Q74}" fieldset="true"/></p>
    </security:authorize>
</body>
</html>
