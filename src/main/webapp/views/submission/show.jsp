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
    <legend><b><spring:message code="submission.data"/></b></legend>
    <p><acme:showtext code="submission.ticker" value="${submission.ticker}" fieldset="false"/></p>
    <p><acme:showtext code="submission.moment" value="${submission.moment}" fieldset="false"/></p>
    <p><acme:showtext code="submission.status" value="${submission.status}" fieldset="false"/></p>
    <b><spring:message code="submission.paper"/>:</b>
    <br />
    <a href="paper/author/show.do?paperId=${submission.paper.id}">Link</a>
    <jstl:if test="${submission.cameraReadyPaper != null}">
        <br>
        <br>
        <b><spring:message code="submission.crPaper"/>:</b>
        <br />
        <a href="paper/author/show.do?paperId=${submission.paper.id}">Link</a>
    </jstl:if>
</fieldset>
<br>
<acme:cancel code="button.goBack" url="/"/>
</body>
</html>
