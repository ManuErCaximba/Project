<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="styles/table.css" type="text/css">
</head>
<body>

<!-- Single Attributes -->
<fieldset>
    <legend><b><spring:message code="actor.username"/></b></legend>
    <p><jstl:out value="${nickname}"/></p>
</fieldset>
<fieldset>
    <legend><b><spring:message code="actor.personalData"/></b></legend>
    <p><acme:showtext code="actor.name" value="${actor.name}" fieldset="false"/> <jstl:out value="${actor.middleName}"/></p>
    <p><acme:showtext code="actor.surname" value="${actor.surname}" fieldset="false"/></p>
    <jstl:if test="${actor.email!=null && actor.email!=''}">
        <p><acme:showtext code="actor.email" value="${actor.email}" fieldset="false"/></p>
    </jstl:if>
    <jstl:if test="${actor.phoneNumber!=null && actor.phoneNumber!=''}">
        <p><acme:showtext code="actor.phoneNumber" value="${actor.phoneNumber}" fieldset="false"/></p>
    </jstl:if>
    <jstl:if test="${actor.address!=null && actor.address!=''}">
        <p><acme:showtext code="actor.address" value="${actor.address}" fieldset="false"/></p>
    </jstl:if>
    <jstl:if test="${actor.photo!=null && actor.photo!=''}">
        <p>
            <b><spring:message code="actor.photo"/>:</b>
            <br>
            <a><img src="${actor.photo}" alt="Photo" height="500" width="500"/></a>
        </p>
    </jstl:if>
</fieldset>
<br>
<security:authorize access="hasRole('REVIEWER')">
    <!-- Table Attributes -->

    <table>
        <tr>
            <th><spring:message code="actor.keyword" /></th>
        </tr>
        <jstl:forEach items="${keywords}"
                      var="keyword">
            <tr>
                <td><jstl:out value="${keyword}"/></td>
            </tr>
        </jstl:forEach>
    </table>

</security:authorize>

<security:authorize access="hasRole('AUTHOR')">
    <jstl:if test="${score != null}">
        <fieldset>
            <legend><b><spring:message code="actor.score"/></b></legend>
            <jstl:out value="${score}"/>
        </fieldset>
    </jstl:if>

</security:authorize>

<!-- Buttons -->
<security:authorize access="hasRole('REVIEWER')">
    <acme:cancel url="/reviewer/reviewer/edit.do" code="master.page.profile.edit"/>
</security:authorize>
<security:authorize access="hasRole('SPONSOR')">
    <acme:cancel url="/sponsor/sponsor/edit.do" code="master.page.profile.edit"/>
</security:authorize>
<security:authorize access="hasRole('AUTHOR')">
    <acme:cancel url="/author/author/edit.do" code="master.page.profile.edit"/>
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    <acme:cancel url="/administrator/administrator/edit.do" code="master.page.profile.edit"/>
</security:authorize>
</body>
</html>
