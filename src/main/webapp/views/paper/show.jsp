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
    <legend><b><spring:message code="paper.data"/></b></legend>
    <p><acme:showtext code="paper.title" value="${paper.title}" fieldset="false"/></p>
    <p><acme:showtext code="paper.summary" value="${paper.summary}" fieldset="false"/></p>
    <b><spring:message code="paper.attachment"/>:</b>
    <br />
    <a href="${paper.documentURL}"><jstl:out value="${paper.documentURL}"/></a>
</fieldset>
<fieldset>
    <legend><b><spring:message code="paper.author"/></b></legend>
    <p>
        <jstl:forEach items="${paper.authors}" var="author">
            <b>-</b> <jstl:out value="${author.name}"/>
            <br>
        </jstl:forEach>
    </p>
</fieldset>
<br>
<acme:cancel code="button.goBack" url="paper/author/list.do"/>
</body>
</html>
