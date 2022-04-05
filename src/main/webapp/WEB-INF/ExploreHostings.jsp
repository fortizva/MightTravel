<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <title>Explorar - MightTravel</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/explore.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shared.css">
</head>

<body>
    <!-- Barra de navegación -->
    <div class="navbar">
        <a href="ExploreHostingsServlet.do" class="nounderline bannerLogoLink"><img alt="Logo" src="${pageContext.request.contextPath}/img/logoWhite.png" class="bannerlogo"></a>
        <a href="ExploreHostingsServlet.do" class="bannertitle">MightTravel</a>
        <form class="searchform" method="get" action="HostingLocationListServlet.do">
            <input type="text" placeholder="Buscar" name="q" class="fbannersearch">
            <select name="category" class="fbannerselect">
            	<option value="">Categoría</option>
            	<c:forEach var="Category" items="${Categories}">
            		<option value="${Category.id}">${Category.name}</option>
            	</c:forEach> 
            </select>
            <select name="availability" class="fbannerselect">
            	<option value="">Disponibilidad</option>
           		<option value="1">Sólo disponibles</option>
            </select>
            <button type="submit" class="submitbutton bannersearchbutton">Buscar</button>
        </form>
        <c:choose>
        <c:when test="${user==null}">
	        <form action="LoginServlet.do" method="POST" class="banneraccount">
	        	<button type="submit" class="submitbutton banneraccount">Login</button>
	        </form>
        </c:when>
        <c:otherwise>
            <form action="UserPanelServlet.do" method="POST" class="banneraccount">
        		<button type="submit" class="submitbutton banneraccount">Panel</button>
       		</form>
        </c:otherwise>
        </c:choose>
    </div>


    <div class="maindiv">
        <section class="categorycontainer backgrounddiv">
            <b>Categoría:</b>
            <c:forEach var="Category" items="${Categories}">
            	<a href="HostingLocationListServlet.do?category=${Category.id}" title="${Category.description}">${Category.name}</a>
            </c:forEach>
        </section>
        <br>
        <section class="backgrounddiv">
            <h1>Explorar:</h1>
            <section class="housecelllist">
                <!-- Mostrar listado de casas -->
                <c:forEach var="Hosting" items="${HostingsList}">
	                <div class="housecell">
	                    <a href="HostingDetailsServlet.do?id=${Hosting.id}"  class="nounderline"><img alt="HousePreview" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/img/hostingNotFound.png'" src="${Hosting.image}"></a>
	                    <div class="housecellinfo">
	                        <i>&#9733; ${Hosting.likes} likes</i>
	                        <br>
	                        <a href="HostingDetailsServlet.do?id=${Hosting.id}"><b>${Hosting.title}</b></a>
	                    </div>
	                </div>
                </c:forEach>                
            </section>
        </section>
    </div>
</body>

</html>