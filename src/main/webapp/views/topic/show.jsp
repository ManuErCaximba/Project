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
    <legend><b><spring:message code="topic.data"/></b></legend>

    <jstl:if test="${lang == 'es'}">
        <p><acme:showtext code="topic.name" value="${topic.nameEs}" fieldset="false"/></p>
    </jstl:if>

    <jstl:if test="${lang == 'en'}">
        <p><acme:showtext code="topic.name" value="${topic.nameEn}" fieldset="false"/></p>
    </jstl:if>

</fieldset>
<br>

<acme:cancel code="topic.back" url="/topic/administrator/list.do"/>

</body>
</html>