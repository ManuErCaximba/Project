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
    <legend><b><spring:message code="conference.data"/></b></legend>
    <p><acme:showtext code="conference.title" value="${conference.title}" fieldset="false"/></p>
    <p><acme:showtext code="conference.acronym" value="${conference.acronym}" fieldset="false"/></p>
    <p><acme:showtext code="conference.venue" value="${conference.venue}" fieldset="false"/></p>
    <p><acme:showtext code="conference.submissionDeadline" value="${conference.submissionDeadline}" fieldset="false"/></p>
    <p><acme:showtext code="conference.notificationDeadline" value="${conference.notificationDeadline}" fieldset="false"/></p>
    <p><acme:showtext code="conference.cameraReadyDeadline" value="${conference.cameraReadyDeadline}" fieldset="false"/></p>
    <p><acme:showtext code="conference.startDate" value="${conference.startDate}" fieldset="false"/></p>
    <p><acme:showtext code="conference.endDate" value="${conference.endDate}" fieldset="false"/></p>
    <p><acme:showtext code="conference.summary" value="${conference.summary}" fieldset="false"/></p>
    <p><acme:showtext code="conference.fee" value="${conference.fee}" fieldset="false"/></p>
    <jstl:if test="${lang == 'es'}">
        <p><acme:showtext code="conference.category" value="${conference.category.nameEs}" fieldset="false"/></p>
    </jstl:if>

    <jstl:if test="${lang == 'en'}">
        <p><acme:showtext code="conference.category" value="${conference.category.nameEn}" fieldset="false"/></p>
    </jstl:if>
    <fieldset>
    <legend><b><spring:message code="conference.activity.data"/></b></legend>
        <fieldset>
            <legend><b><spring:message code="conference.tutorials"/> </b></legend>
            <p>
                <display:table name="tutorials" id="row" requestURI="conference/administrator/show.do?conferenceId=${conference.id}" >
                    <spring:message code="conference.activity.title" var="personalNameHeader"/>
                    <display:column property="title" title="${personalNameHeader}"/>

                    <spring:message code="conference.activity.startMoment" var="personalNameHeader"/>
                    <display:column property="startMoment" title="${personalNameHeader}"/>

                    <spring:message code="conference.activity.duration" var="personalNameHeader"/>
                    <display:column property="duration" title="${personalNameHeader}"/>

                    <display:column>
                        <a
                                href="tutorial/show.do?tutorialId=${row.id}">
                            <spring:message code="conference.show"/>
                        </a>
                    </display:column>


                </display:table>
            </p>
        </fieldset>
        <fieldset>
            <legend><b><spring:message code="conference.presentations"/> </b></legend>
            <p>
                <display:table name="presentations" id="row" requestURI="conference/administrator/show.do?conferenceId=${conference.id}" >
                    <spring:message code="conference.activity.title" var="personalNameHeader"/>
                    <display:column property="title" title="${personalNameHeader}"/>

                    <spring:message code="conference.activity.startMoment" var="personalNameHeader"/>
                    <display:column property="startMoment" title="${personalNameHeader}"/>

                    <spring:message code="conference.activity.duration" var="personalNameHeader"/>
                    <display:column property="duration" title="${personalNameHeader}"/>

                    <display:column>
                        <a
                                href="presentation/show.do?presentationId=${row.id}">
                            <spring:message code="conference.show"/>
                        </a>
                    </display:column>

                    <display:column>
                        <a
                                href="presentation/administrator/edit.do?presentationId=${row.id}">
                            <spring:message code="button.edit"/>
                        </a>
                    </display:column>

                    <display:column>
                        <a
                                href="presentation/administrator/delete.do?presentationId=${row.id}">
                            <spring:message code="button.delete"/>
                        </a>
                    </display:column>


                </display:table>
            </p>
        </fieldset>
        <fieldset>
            <legend><b><spring:message code="conference.panels"/> </b></legend>
            <p>
                <display:table name="panels" id="row" requestURI="conference/administrator/show.do?conferenceId=${conference.id}" >
                    <spring:message code="conference.activity.title" var="personalNameHeader"/>
                    <display:column property="title" title="${personalNameHeader}"/>

                    <spring:message code="conference.activity.startMoment" var="personalNameHeader"/>
                    <display:column property="startMoment" title="${personalNameHeader}"/>

                    <spring:message code="conference.activity.duration" var="personalNameHeader"/>
                    <display:column property="duration" title="${personalNameHeader}"/>

                    <display:column>
                        <acme:cancel code="button.show" url="panel/show.do?panelId=${row.id}"/>
                    </display:column>
                </display:table>
            </p>
            <br>
        </fieldset>

    </fieldset>
    <br>
    <p><a
            href="comment/list.do?conferenceId=${conference.id}">
        <spring:message code="conference.comments"/>
    </a></p>

    <security:authorize access="hasRole('ADMIN')">
        <br>
        <p><a
                href="registration/administrator/listAdmin.do?conferenceId=${conference.id}">
            <spring:message code="conference.registration.list"/>
        </a></p>
    </security:authorize>
</fieldset>
<br>
<input type="button" name="cancel"
       value="<spring:message code="button.goBack" />"
       onclick="javascript: window.history.back();" />
<br>
<br>
<br>
<a href="${sponsorship.targetURL}"><img src="${sponsorship.banner}" alt="Link" width="800" height="120"/></a>

</body>
</html>
