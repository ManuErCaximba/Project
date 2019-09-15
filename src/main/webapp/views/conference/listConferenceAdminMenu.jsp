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
    <legend><b><spring:message code="conference.admin.lists"/></b></legend>
    <a href="conference/administrator/listDeadline5Days.do" > <spring:message code="conference.deadline5Days"/></a><br>
    <a href="conference/administrator/listNotification4Days.do"> <spring:message code="conference.notification4Days"/></a><br>
    <a href="conference/administrator/listCamera4Days.do"> <spring:message code="conference.camera4Days"/></a><br>
    <a href="conference/administrator/listStartNext4Days.do"> <spring:message code="conference.startNext4Days"/></a>

</fieldset>
<br>
<input type="button" name="cancel"
       value="<spring:message code="button.goBack" />"
       onclick="javascript: window.history.back();" />
</body>
</html>