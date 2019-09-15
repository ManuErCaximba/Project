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
    <legend><b><spring:message code="tutorial.data"/></b></legend>
    <p><acme:showtext code="tutorial.title" value="${tutorial.title}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.startMoment" value="${tutorial.startMoment}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.startMoment" value="${tutorial.speakerName}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.duration" value="${tutorial.duration}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.room" value="${tutorial.room}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.summary" value="${tutorial.summary}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.attachments" value="${tutorial.attachments}" fieldset="false"/></p>
    <p><acme:showtext code="tutorial.conference" value="${tutorial.conference.title}" fieldset="false"/></p>

    <fieldset>
        <legend><b><spring:message code="tutorial.sections"/></b></legend>
            <p>
                <display:table name="sections" id="row" requestURI="tutorial/show.do?tutorialId=${tutorial.id}" >
                    <spring:message code="tutorial.section.title" var="personalNameHeader"/>
                    <display:column property="title" title="${personalNameHeader}" />

                    <display:column>
                        <a
                                href="section/show.do?sectionId=${row.id}">
                            <spring:message code="tutorial.show"/>
                        </a>
                    </display:column>


                </display:table>
            </p>
    </fieldset>

    <p><a
            href="comment/listCommentsTutorial.do?tutorialId=${tutorial.id}">
        <spring:message code="conference.comments"/>
    </a></p>

</fieldset>
<br>
<security:authorize access="hasRole('ADMIN')">
    <acme:cancel code="button.edit" url="tutorial/administrator/edit.do?tutorialId=${tutorial.id}"/>
</security:authorize>

<input type="button" name="cancel"
       value="<spring:message code="button.goBack" />"
       onclick="javascript: window.history.back();" />
</body>
</html>
