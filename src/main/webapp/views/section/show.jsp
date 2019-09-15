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
    <legend><b><spring:message code="section.data"/></b></legend>
    <p><acme:showtext code="section.title" value="${section.title}" fieldset="false"/></p>
    <p><acme:showtext code="section.summary" value="${section.summary}" fieldset="false"/></p>
    <p><acme:showtext code="section.pictures" value="${section.pictures}" fieldset="false"/></p>
    <p><acme:showtext code="section.tutorial" value="${section.tutorial.title}" fieldset="false"/></p>
    <p><acme:showtext code="section.conference" value="${section.tutorial.conference.title}" fieldset="false"/> </p>
</fieldset>
<br>
<security:authorize access="hasRole('ADMIN')">
    <acme:cancel code="button.edit" url="section/administrator/edit.do?sectionId=${section.id}"/>
</security:authorize>

<input type="button" name="cancel"
       value="<spring:message code="button.goBack" />"
       onclick="javascript: window.history.back();" />
</body>
</html>
