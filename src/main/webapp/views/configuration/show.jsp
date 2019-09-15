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

    <link rel="stylesheet" href="styles/table.css" type="text/css">
</head>
<body>
<fieldset>
    <legend><b><spring:message code="configuration.data"/></b></legend>
    <p><acme:showtext code="configuration.systemName" value="${configuration.systemName}" fieldset="false"/></p>

    <b><spring:message code="configuration.banner"/>:</b>
    <br />
    <a><img src="${configuration.banner}" alt="Photo" height="120" width="800"/></a>
    <br>

    <p><acme:showtext code="configuration.welcomeEn" value="${configuration.welcomeEn}" fieldset="false"/></p>
    <p><acme:showtext code="configuration.welcomeEs" value="${configuration.welcomeEs}" fieldset="false"/></p>

    <b><spring:message code="configuration.defaultCC"/>:</b>
    <br />
    + <jstl:out value="${configuration.defaultCC}"/>
</fieldset>

<table>
    <tr>
        <th><spring:message code="configuration.creditCardMakes" /></th>
    </tr>
    <jstl:forEach items="${configuration.creditCardMakes}"
                  var="x">
        <tr>
            <td><jstl:out value="${x}"/></td>
        </tr>
    </jstl:forEach>
</table>

<br>
<acme:cancel code="button.goBack" url="/"/>
<acme:cancel code="button.edit" url="configuration/administrator/edit.do"/>
<acme:cancel code="button.calcScores" url="configuration/administrator/scoring.do"/>
<acme:cancel code="button.listTopics" url="topic/administrator/list.do"/>
<acme:cancel code="button.showVoidWordsEs" url="configuration/administrator/showVoidWordsEs.do"/>
<acme:cancel code="button.showVoidWordsEn" url="configuration/administrator/showVoidWordsEn.do"/>
</body>
</html>
